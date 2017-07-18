package com.zxwl.web.service.commons;

import com.zxwl.web.bean.po.GenericPo;
import com.zxwl.web.dao.CRUMapper;
import com.zxwl.web.dao.InsertMapper;
import com.zxwl.web.dao.QueryMapper;
import com.zxwl.web.dao.UpdateMapper;

/**
 * @author zhouhao
 */
public interface CRUService<Po extends GenericPo<Pk>, Pk> extends
        SimpleInsertService<Po, Pk>,
        SimpleQueryService<Po, Pk>,
        SimpleUpdateService<Po,Pk> {
    CRUMapper<Po, Pk> getCRUMapper();

    @Override
    default InsertMapper<Po> getInsertMapper() {
        return getCRUMapper();
    }

    @Override
    default QueryMapper<Po, Pk> getQueryMapper() {
        return getCRUMapper();
    }

    @Override
    default UpdateMapper<Po> getUpdateMapper() {
        return getCRUMapper();
    }
}
