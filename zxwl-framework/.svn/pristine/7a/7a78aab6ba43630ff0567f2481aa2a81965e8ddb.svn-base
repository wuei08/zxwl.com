package com.zxwl.web.service.impl;

import org.hsweb.commons.ClassUtils;
import com.zxwl.web.bean.po.GenericPo;
import com.zxwl.web.bean.validator.ValidateResults;
import com.zxwl.web.core.exception.NotFoundException;
import com.zxwl.web.core.exception.ValidationException;
import com.zxwl.web.dao.*;
import com.zxwl.web.service.GenericService;
import com.zxwl.web.service.commons.SimpleDeleteService;
import com.zxwl.web.service.commons.SimpleInsertService;
import com.zxwl.web.service.commons.SimpleQueryService;
import com.zxwl.web.service.commons.SimpleUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 抽象通用服务实现类，通过指定{@link GenericMapper} 实现通用的增删改查方法
 *
 * @param <Po> PO类型
 * @param <PK> 主键类型
 * @author zhouhao
 * @see GenericService
 * @since 1.0
 */
@Transactional(rollbackFor = Throwable.class)
public abstract class AbstractServiceImpl<Po extends GenericPo<PK>, PK> implements GenericService<Po, PK>
        , SimpleQueryService<Po, PK>
        , SimpleUpdateService<Po, PK>
        , SimpleDeleteService<PK>
        , SimpleInsertService<Po, PK> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Validator validator;

    protected abstract GenericMapper<Po, PK> getMapper();

    @Override
    public Class<PK> getPKType() {
        return (Class<PK>) ClassUtils.getGenericType(org.springframework.util.ClassUtils.getUserClass(this.getClass()), 1);
    }

    @Override
    public QueryMapper<Po, PK> getQueryMapper() {
        return getMapper();
    }

    @Override
    public UpdateMapper<Po> getUpdateMapper() {
        return getMapper();
    }

    @Override
    public DeleteMapper getDeleteMapper() {
        return getMapper();
    }

    @Override
    public InsertMapper<Po> getInsertMapper() {
        return getMapper();
    }

    public void tryValidPo(Po data) {
        Set<ConstraintViolation<Object>> set = validator.validate(data);
        ValidateResults results = new ValidateResults();
        for (ConstraintViolation<Object> violation : set) {
            results.addResult(violation.getPropertyPath().toString(), violation.getMessage());
        }
        if (!results.isSuccess())
            throw new ValidationException(results);
    }

    protected void assertNotNull(Object po, String message) {
        if (po == null) {
            throw new NotFoundException(message);
        }
    }

    protected void assertNotNull(Object po) {
        assertNotNull(po, "数据不存在");
    }

}
