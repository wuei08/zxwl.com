package com.zxwl.web.dao;

/**
 * @author zhouhao
 */
public interface CRUMapper<Po, Pk> extends InsertMapper<Po>, QueryMapper<Po, Pk>, UpdateMapper<Po> {
}
