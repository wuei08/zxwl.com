package com.zxwl.web.service.impl.module;

import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.po.module.Module;
import com.zxwl.web.dao.module.ModuleMapper;
import com.zxwl.web.service.impl.AbstractServiceImpl;
import com.zxwl.web.service.module.ModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("moduleService")
public class ModuleServiceImpl extends AbstractServiceImpl<Module, String> implements ModuleService {

    //默认数据映射接口
    @Resource
    protected ModuleMapper moduleMapper;

    @Override
    protected ModuleMapper getMapper() {
        return this.moduleMapper;
    }

    @Override
    public List<Module> selectByPid(String pid) throws Exception {
        return this.select(new QueryParam().where("parentId", pid));
    }
}
