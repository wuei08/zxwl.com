package com.zxwl.web.service.form;

import org.hsweb.ezorm.rdb.RDBDatabase;
import org.hsweb.ezorm.rdb.meta.RDBTableMetaData;
import com.zxwl.web.bean.common.*;
import com.zxwl.web.bean.po.form.Form;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouhao on 16-4-14.
 */
public interface DynamicFormService {

    RDBDatabase getDefaultDatabase();

    RDBTableMetaData parseMeta(Form form);

    void deploy(Form form) throws SQLException;

    void unDeploy(Form form);

    <T> PagerResult<T> selectPager(String name, QueryParam param) throws SQLException;

    <T> List<T> select(String name, QueryParam param) throws SQLException;

    int total(String name, QueryParam param) throws SQLException;

    String insert(String name, Map<String, Object> data) throws SQLException;

    List<String> insert(String name, List<Map<String, Object>> dataList) throws SQLException;

    String saveOrUpdate(String name, Map<String, Object> map) throws SQLException;

    String saveOrUpdate(String name, List<Map<String, Object>> map) throws SQLException;

    int delete(String name, DeleteParam param) throws SQLException;

    boolean deleteByPk(String name, String pk) throws SQLException;

    int update(String name, UpdateParam<Map<String, Object>> param) throws SQLException;

    int updateByPk(String name, String pk, UpdateParam<Map<String, Object>> param) throws SQLException;

    <T> T selectByPk(String name, Object pk) throws SQLException;

    void exportExcel(String name, QueryParam param, OutputStream outputStream) throws Exception;

    Map<String, Object> importExcel(String name, InputStream inputStream);
}
