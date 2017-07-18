package com.zxwl.web.dao;

import com.zxwl.web.bean.common.InsertParam;

/**
 * @author zhouhao
 */
public interface InsertMapper<Po> {
    int insert(InsertParam<Po> param);
}
