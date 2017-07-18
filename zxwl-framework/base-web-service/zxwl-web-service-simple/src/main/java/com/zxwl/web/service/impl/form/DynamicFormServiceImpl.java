package com.zxwl.web.service.impl.form;

import com.alibaba.fastjson.JSON;
import org.hsweb.commons.StringUtils;
import com.zxwl.concurrent.lock.annotation.LockName;
import com.zxwl.concurrent.lock.annotation.ReadLock;
import com.zxwl.concurrent.lock.annotation.WriteLock;
import org.hsweb.expands.office.excel.ExcelIO;
import org.hsweb.expands.office.excel.config.Header;
import org.hsweb.ezorm.core.*;
import org.hsweb.ezorm.rdb.RDBDatabase;
import org.hsweb.ezorm.rdb.RDBQuery;
import org.hsweb.ezorm.rdb.RDBTable;
import org.hsweb.ezorm.rdb.meta.RDBColumnMetaData;
import org.hsweb.ezorm.rdb.meta.RDBTableMetaData;
import org.hsweb.ezorm.rdb.meta.builder.TableBuilder;
import org.hsweb.ezorm.rdb.meta.builder.simple.SimpleTableBuilder;
import org.hsweb.ezorm.rdb.meta.parser.TableMetaParser;
import com.zxwl.web.bean.common.DeleteParam;
import com.zxwl.web.bean.common.PagerResult;
import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.common.UpdateParam;
import com.zxwl.web.bean.po.GenericPo;
import com.zxwl.web.bean.po.form.Form;
import com.zxwl.web.bean.po.history.History;
import com.zxwl.web.core.authorize.ExpressionScopeBean;
import com.zxwl.web.core.exception.BusinessException;
import com.zxwl.web.core.exception.NotFoundException;
import com.zxwl.web.service.form.DynamicFormDataValidator;
import com.zxwl.web.service.form.DynamicFormService;
import com.zxwl.web.service.form.FormParser;
import com.zxwl.web.service.form.FormService;
import com.zxwl.web.service.history.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by zhouhao on 16-4-14.
 */
@Service("dynamicFormService")
@Transactional(rollbackFor = Throwable.class)
public class DynamicFormServiceImpl implements DynamicFormService, ExpressionScopeBean {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    protected FormParser formParser;

    @Autowired
    protected RDBDatabase database;

    @Resource
    protected FormService formService;

    @Resource
    protected HistoryService historyService;

    @Autowired(required = false)
    protected List<DynamicFormDataValidator> dynamicFormDataValidator;

    @Autowired(required = false)
    protected Map<String, ExpressionScopeBean> expressionScopeBeanMap;

    @Autowired(required = false)
    protected TableMetaParser tableMetaParser;

    protected void initDefaultField(RDBTableMetaData metaData) {
        metaData.setDatabaseMetaData(database.getMeta());
        TableBuilder builder = new SimpleTableBuilder(metaData, database, null);
        builder.addColumn().name("u_id").varchar(32).primaryKey().comment("主键").commit();
        metaData.setProperty("primaryKey", "u_id");
    }

    @Override
    public RDBDatabase getDefaultDatabase() {
        return database;
    }

    @Override
    public RDBTableMetaData parseMeta(Form form) {
        RDBTableMetaData metaData = formParser.parse(form);
        initDefaultField(metaData);
        return metaData;
    }

    @Override
    @WriteLock
    @LockName(value = "'form.lock.'+#form.name", isExpression = true)
    public void deploy(Form form) throws SQLException {
        RDBTableMetaData metaData = formParser.parse(form);
        metaData.setProperty("version", form.getRevision());
        initDefaultField(metaData);
        RDBTableMetaData lastDeployMetaData;
        if (tableMetaParser == null) {
            History history = historyService.selectLastHistoryByType("form.deploy." + form.getName());
            Form lastDeploy = JSON.parseObject(history.getChangeAfter(), Form.class);
            lastDeployMetaData = formParser.parse(lastDeploy);
            initDefaultField(lastDeployMetaData);
        } else {
            lastDeployMetaData = tableMetaParser.parse(form.getName());
        }
        //首次部署
        if (lastDeployMetaData == null || lastDeployMetaData.getColumns().isEmpty()) {
            try {
                database.createTable(metaData);
            } catch (Exception e) {
                throw new BusinessException("发布失败:" + e.getMessage());
            }
        } else {
            //向上发布
            database.reloadTable(lastDeployMetaData);//先放入旧的结构
            //更新结构
            database.alterTable(metaData);
        }

    }

    @Override
    @WriteLock
    @LockName(value = "'form.lock.'+#form.name", isExpression = true)
    public void unDeploy(Form form) {
        database.removeTable(form.getName());
    }

    public <T> RDBTable<T> getTableByName(String name) {
        try {
            RDBTable<T> table = database.getTable(name);
            if (table == null) {
                throw new NotFoundException("表单[" + name + "]不存在");
            }
            return table;
        } catch (Exception e) {
            throw new NotFoundException("表单[" + name + "]不存在");
        }
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    @Transactional(readOnly = true)
    public <T> PagerResult<T> selectPager(String name, QueryParam param) throws SQLException {
        PagerResult<T> result = new PagerResult<>();
        RDBTable<T> table = getTableByName(name);
        RDBQuery<T> query = table.createQuery();
        query.setParam(param);
        int total = query.total();
        result.setTotal(total);
        if (total == 0) {
            result.setData(new ArrayList<>());
        } else {
            //根据实际记录数量重新指定分页参数
            param.rePaging(total);
            result.setData(query.list(param.getPageIndex(), param.getPageSize()));
        }
        return result;
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    @Transactional(readOnly = true)
    public <T> List<T> select(String name, QueryParam param) throws SQLException {
        RDBTable<T> table = getTableByName(name);
        RDBQuery<T> query = table.createQuery().setParam(param);
        return query.list();
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    @Transactional(readOnly = true)
    public int total(String name, QueryParam param) throws SQLException {
        RDBTable table = getTableByName(name);
        RDBQuery query = table.createQuery().setParam(param);
        return query.total();
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    public String insert(String name, Map<String, Object> data) throws SQLException {
        RDBTable table = getTableByName(name);
        String primaryKeyName = getPrimaryKeyName(name);
        String pk = GenericPo.createUID();
        data.put(primaryKeyName, pk);
        Insert insert = table.createInsert().value(data);
        insert.exec();
        return pk;
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    public List<String> insert(String name, List<Map<String, Object>> dataList) throws SQLException {
        RDBTable table = getTableByName(name);
        String primaryKeyName = getPrimaryKeyName(name);
        List<String> idList = new ArrayList<>();
        dataList.forEach(data -> {
            String pk = GenericPo.createUID();
            data.put(primaryKeyName, pk);
            idList.add(pk);
        });
        table.createInsert().values(dataList).exec();
        return idList;
    }

    @Override
    public String saveOrUpdate(String name, Map<String, Object> data) throws SQLException {
        String id = (String) data.get(getPrimaryKeyName(name));
        if (id == null)
            id = getRepeatDataId(name, data);
        if (id != null) {
            updateByPk(name, id, UpdateParam.build(data));
        } else {
            id = insert(name, data);
        }
        return id;
    }

    @Override
    public String saveOrUpdate(String name, List<Map<String, Object>> map) throws SQLException {
        StringBuilder builder = new StringBuilder();
        for (Map<String, Object> objectMap : map) {
            String id = saveOrUpdate(name, objectMap);
            builder.append(id).append(",");
        }
        return builder.substring(0, builder.length());
    }

    protected String getRepeatDataId(String name, Map<String, Object> data) {
        RDBTable table = getTableByName(name);
        if (dynamicFormDataValidator != null) {
            for (DynamicFormDataValidator validator : dynamicFormDataValidator) {
                String id = validator.getRepeatDataId(table, data);
                if (id != null) {
                    return id;
                }
            }
        }
        return null;
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    public boolean deleteByPk(String name, String pk) throws SQLException {
        String primaryKeyName = getPrimaryKeyName(name);
        RDBTable table = getTableByName(name);
        Delete delete = table.createDelete().where(primaryKeyName, pk);
        return delete.exec() == 1;
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    public int delete(String name, DeleteParam where) throws SQLException {
        RDBTable table = getTableByName(name);
        Delete delete = table.createDelete();
        delete.setParam(where);
        return delete.exec();
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    public int updateByPk(String name, String pk, UpdateParam<Map<String, Object>> param) throws SQLException {
        RDBTable table = getTableByName(name);
        String pkName = getPrimaryKeyName(name);
        Update update = table.createUpdate().setParam(param);
        ((Map) param.getData()).put(pkName, pk);
        update.where(pkName, pk);
        return update.exec();
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    public int update(String name, UpdateParam<Map<String, Object>> param) throws SQLException {
        RDBTable table = getTableByName(name);
        Update update = table.createUpdate().setParam(param);
        return update.exec();
    }

    @ReadLock
    @LockName(value = "'form.lock.'+#tableName", isExpression = true)
    public String getPrimaryKeyName(String tableName) {
        RDBTable table = getTableByName(tableName);
        return table.getMeta().getProperty("primaryKey", "u_id").toString();
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    public <T> T selectByPk(String name, Object pk) throws SQLException {
        Table<T> table = getTableByName(name);
        Query<T> query = table.createQuery().where(getPrimaryKeyName(name), pk);
        return query.single();
    }

    protected void putExcelHeader(String fieldPrefix, RDBColumnMetaData fieldMetaData, List<Header> headers) {
        if (fieldMetaData == null) return;
        PropertyWrapper valueWrapper = fieldMetaData.getProperty("export-excel", false);
        if (fieldPrefix.length() > 0) fieldPrefix += ".";
        if (valueWrapper.isTrue()) {
            String title = fieldMetaData.getProperty("excel-header").getValue();
            if (StringUtils.isNullOrEmpty(title)) {
                title = fieldMetaData.getComment();
            }
            if (StringUtils.isNullOrEmpty(title)) {
                title = fieldMetaData.getName();
            }
            String field = fieldMetaData.getName();
            OptionConverter converter = fieldMetaData.getOptionConverter();
            if (converter != null) {
                field = converter.getFieldName();
            }
            headers.add(new Header(title, fieldPrefix + field));
        }
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    @Transactional(readOnly = true)
    public void exportExcel(String name, QueryParam param, OutputStream outputStream) throws Exception {
        List<Object> dataList = select(name, param);
        RDBTable table = getTableByName(name);
        RDBTableMetaData metaData = table.getMeta();
        List<Header> headers = new LinkedList<>();
        Map<String, Object> sample = dataList.isEmpty() ? new HashMap<>() : (Map) dataList.get(0);
        sample.forEach((key, value) -> {
            if (value instanceof Map) {
                ((Map) value).forEach((k, v) -> {
                    String fieldName = key + "." + k;
                    RDBColumnMetaData field = metaData.findColumn(fieldName);
                    putExcelHeader(fieldName, field, headers);
                });
            } else {
                RDBColumnMetaData field = metaData.findColumn(key);
                putExcelHeader("", field, headers);
            }
        });
        if (metaData.triggerIsSupport("export.excel")) {
            Map<String, Object> var = new HashMap<>();
            if (expressionScopeBeanMap != null)
                var.putAll(expressionScopeBeanMap);
            var.put("database", database);
            var.put("table", table);
            var.put("dataList", dataList);
            var.put("headers", headers);
            metaData.on("export.excel", var);
        }
        ExcelIO.write(outputStream, headers, dataList);
    }

    @Override
    @ReadLock
    @LockName(value = "'form.lock.'+#name", isExpression = true)
    public Map<String, Object> importExcel(String name, InputStream inputStream) {
        RDBTable<Map<String, Object>> table = getTableByName(name);
        Map<String, Object> result = new HashMap<>();
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> excelData;
        try {
            excelData = ExcelIO.read2Map(inputStream);
        } catch (Exception e) {
            throw new BusinessException("解析excel失败,请确定文件格式正确!", e, 500);
        }
        List<Map<String, Object>> dataList = new LinkedList<>();
        Map<String, String> headerMapper = new HashMap<>();
        RDBTableMetaData metaData = table.getMeta();
        metaData.getColumns().forEach(fieldMetaData -> {
            PropertyWrapper valueWrapper = fieldMetaData.getProperty("importExcel", true);
            if (valueWrapper.isTrue()) {
                String title = fieldMetaData.getProperty("excel-header").getValue();
                if (StringUtils.isNullOrEmpty(title)) {
                    title = fieldMetaData.getComment();
                }
                if (StringUtils.isNullOrEmpty(title)) {
                    title = fieldMetaData.getName();
                }
                String field = fieldMetaData.getName();
                headerMapper.put(title, field);
            }
        });
        if (metaData.triggerIsSupport("export.import.before")) {
            Map<String, Object> var = new HashMap<>();
            var.put("headerMapper", headerMapper);
            var.put("excelData", excelData);
            var.put("dataList", dataList);
            var.put("database", database);
            var.put("table", table);
            if (expressionScopeBeanMap != null)
                var.putAll(expressionScopeBeanMap);
            metaData.on("export.import.before", var);
        }
        excelData.forEach(data -> {
            Map<String, Object> newData = new HashMap<>();
            data.forEach((k, v) -> {
                String field = headerMapper.get(k);
                if (field != null) {
                    newData.put(field, v);
                } else {
                    newData.put(k, v);
                }
            });
            dataList.add(newData);
        });
        List<Map<String, Object>> errorMessage = new LinkedList<>();
        int index = 0, success = 0;
        for (Map<String, Object> map : dataList) {
            index++;
            try {
                if (metaData.triggerIsSupport("export.import.each")) {
                    Map<String, Object> var = new HashMap<>();
                    var.put("headerMapper", headerMapper);
                    var.put("excelData", excelData);
                    var.put("dataList", dataList);
                    var.put("data", map);
                    var.put("database", database);
                    var.put("table", table);
                    if (expressionScopeBeanMap != null)
                        var.putAll(expressionScopeBeanMap);
                    metaData.on("export.import.each", var);
                }
                saveOrUpdate(name, map);
                success++;
            } catch (Exception e) {
                Map<String, Object> errorMsg = new HashMap<>();
                errorMsg.put("index", index);
                errorMsg.put("message", e.getMessage());
                errorMessage.add(errorMsg);
            }
        }
        long endTime = System.currentTimeMillis();
        result.put("startTime", startTime);
        result.put("endTime", endTime);
        result.put("total", dataList.size());
        result.put("success", success);
        result.put("errorMessage", errorMessage);
        return result;
    }

}
