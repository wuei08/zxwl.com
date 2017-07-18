package com.zxwl.web.mybatis.dynamic;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.transaction.Transaction;
import com.zxwl.web.core.datasource.DataSourceHolder;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * mybatis 同一事务，同一个mapper，动态数据源切换支持
 *
 * @author zhouhao
 */
public class DynamicSpringManagedTransaction implements Transaction {

    private static final Log LOGGER = LogFactory.getLog(SpringManagedTransaction.class);

    private Map<String, TransactionProxy> connectionMap = new HashMap<>();

    /**
     * 当前数据源对应的事务代理
     *
     * @return {@link TransactionProxy}
     */
    protected TransactionProxy getProxy() {
        return connectionMap.get(DataSourceHolder.getActiveSourceId());
    }

    /**
     * 添加一个事务代理
     *
     * @param proxy
     */
    protected void addProxy(TransactionProxy proxy) {
        connectionMap.put(DataSourceHolder.getActiveSourceId(), proxy);
    }

    /**
     * 获取所有代理
     *
     * @return
     */
    protected Collection<TransactionProxy> getAllProxy() {
        return connectionMap.values();
    }

    @Override
    public Connection getConnection() throws SQLException {
        TransactionProxy proxy = getProxy();
        if (proxy != null) {
            return proxy.getConnection();
        }
        //根据当前激活的数据源 获取jdbc链接
        DataSource dataSource = DataSourceHolder.getActiveSource();
        String dsId = DataSourceHolder.getActiveSourceId();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        proxy = new TransactionProxy(dsId, connection, dataSource);
        addProxy(proxy);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
                    "DataSource (" + DataSourceHolder.getActiveSourceId() + ") JDBC Connection ["
                            + connection
                            + "] will"
                            + (proxy.isConnectionTransactional ? " " : " not ")
                            + "be managed by Spring");
        }

        return connection;
    }

    @Override
    public void commit() throws SQLException {
        for (TransactionProxy proxy : getAllProxy()) {
            proxy.commit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollback() throws SQLException {
        for (TransactionProxy proxy : getAllProxy()) {
            proxy.rollback();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws SQLException {
        SQLException tmp = null;
        for (TransactionProxy proxy : getAllProxy()) {
            try {
                proxy.close();
                //保证每个链接都能被释放
            } catch (SQLException e) {
                tmp = e;
            }
        }
        connectionMap.clear();
        if (null != tmp) throw tmp;
    }

    @Override
    public Integer getTimeout() throws SQLException {
        return getProxy().getTimeout();
    }

    class TransactionProxy implements Transaction {
        Connection connection;
        DataSource dataSource;
        boolean    isConnectionTransactional;
        boolean    autoCommit;
        String     dataSourceId;

        public TransactionProxy(String dataSourceId, Connection connection, DataSource dataSource) {
            this.connection = connection;
            this.dataSource = dataSource;
            this.dataSourceId = dataSourceId;
            this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(connection, dataSource);
            try {
                this.autoCommit = connection.getAutoCommit();
            } catch (SQLException e) {
            }
        }

        @Override
        public Connection getConnection() throws SQLException {
            return connection;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void commit() throws SQLException {
            if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Committing DataSource (" + dataSourceId + ") JDBC Connection [" + this.connection + "]");
                }
                this.connection.commit();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void rollback() throws SQLException {
            if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Rolling back DataSource (" + dataSourceId + ") JDBC Connection [" + this.connection + "]");
                }
                this.connection.rollback();
            }
        }

        @Override
        public void close() throws SQLException {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }

        @Override
        public Integer getTimeout() throws SQLException {
            ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
            if (holder != null && holder.hasTimeout()) {
                return holder.getTimeToLiveInSeconds();
            }
            return null;
        }
    }
}
