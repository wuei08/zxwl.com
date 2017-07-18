package com.zxwl.web.service.commons;

import com.zxwl.web.bean.common.InsertParam;
import com.zxwl.web.bean.po.GenericPo;
import com.zxwl.web.dao.InsertMapper;
import com.zxwl.web.service.InsertService;

/**
 * @author zhouhao
 */
public interface SimpleInsertService<Po extends GenericPo<Pk>, Pk> extends InsertService<Po, Pk> {

    InsertMapper<Po> getInsertMapper();

    void tryValidPo(Po data);

    Class<Pk> getPKType();

    @Override
    default Pk insert(Po data) {
        if (getPKType() == String.class && data.getId() == null) {
            ((GenericPo<String>) data).setId(GenericPo.createUID());
        }
        tryValidPo(data);
        getInsertMapper().insert(InsertParam.build(data));
        return data.getId();
    }
}
