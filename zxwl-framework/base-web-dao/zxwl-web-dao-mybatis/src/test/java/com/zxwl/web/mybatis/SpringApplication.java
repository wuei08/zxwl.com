/*
 * Copyright 2015-2016 http://zxwl.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zxwl.web.mybatis;

import org.hsweb.ezorm.rdb.executor.AbstractJdbcSqlExecutor;
import org.hsweb.ezorm.rdb.executor.SqlExecutor;
import com.zxwl.web.core.datasource.DataSourceHolder;
import com.zxwl.web.core.datasource.DatabaseType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by zhouhao on 16-4-20.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.zxwl.web")
@MapperScan({"com.zxwl.web.dao", "com.zxwl.web.mybatis.mappers"})
public class SpringApplication {

    @Bean
    public SqlExecutor sqlExecutor(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            DataSourceHolder.install(dataSource, DatabaseType.fromJdbcUrl(connection.getMetaData().getURL()));
        } finally {
            connection.close();
        }
        return new AbstractJdbcSqlExecutor() {
            @Override
            public Connection getConnection() {
                return DataSourceUtils.getConnection(dataSource);
            }

            @Override
            public void releaseConnection(Connection connection) throws SQLException {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        };
    }
}
