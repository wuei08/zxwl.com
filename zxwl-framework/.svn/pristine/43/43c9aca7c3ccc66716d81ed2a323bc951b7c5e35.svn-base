package com.zxwl.web.service.commons;

import org.hsweb.ezorm.core.dsl.Delete;
import com.zxwl.web.bean.common.DeleteParam;
import com.zxwl.web.bean.po.GenericPo;
import com.zxwl.web.dao.DeleteMapper;
import com.zxwl.web.service.DeleteService;
import com.zxwl.web.service.GenericService;

/**
 * @author zhouhao
 */
public interface SimpleDeleteService<Pk> extends DeleteService<Pk> {
    DeleteMapper getDeleteMapper();

    default int delete(Pk pk) {
        return createDelete().where(GenericPo.Property.id, pk).exec();
    }

    /**
     * 创建dsl删除操作对象
     *
     * @return {@link Delete}
     * @see Delete
     * @see GenericService#createDelete(DeleteMapper)
     */
    default Delete<DeleteParam> createDelete() {
        return DeleteService.createDelete(getDeleteMapper());
    }

}
