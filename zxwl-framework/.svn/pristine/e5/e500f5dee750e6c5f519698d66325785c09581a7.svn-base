package com.zxwl.web.service.impl.role;

import com.zxwl.web.bean.common.InsertParam;
import com.zxwl.web.bean.po.role.Role;
import com.zxwl.web.bean.po.role.RoleModule;
import com.zxwl.web.dao.role.RoleMapper;
import com.zxwl.web.dao.role.RoleModuleMapper;
import com.zxwl.web.service.impl.AbstractServiceImpl;
import com.zxwl.web.service.module.ModuleService;
import com.zxwl.web.service.role.RoleService;
import com.zxwl.web.core.utils.RandomUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台管理角色服务类
 * Created by zh.sqy@qq.com
 */
@Service("roleService")
public class RoleServiceImpl extends AbstractServiceImpl<Role, String> implements RoleService {

    //默认数据映射接口
    @Resource
    protected RoleMapper roleMapper;

    @Resource
    protected RoleModuleMapper roleModuleMapper;

    @Resource
    protected ModuleService moduleService;

    @Override
    protected RoleMapper getMapper() {
        return this.roleMapper;
    }

    @Override
    public String insert(Role data) {
        String id = super.insert(data);
        List<RoleModule> roleModule = data.getModules();
        if (roleModule != null && roleModule.size() > 0) {
            //保存角色模块关联
            for (RoleModule module : roleModule) {
                module.setId(RandomUtil.randomChar(6));
                module.setRoleId(data.getId());
                roleModuleMapper.insert(new InsertParam<>(module));
            }
        }
        return id;
    }

    @Override
    public int update(Role data){
        int l = super.update(data);
        List<RoleModule> roleModule = data.getModules();
        if (roleModule != null && roleModule.size() > 0) {
            //先删除所有roleModule
            roleModuleMapper.deleteByRoleId(data.getId());
            //保存角色模块关联
            for (RoleModule module : roleModule) {
                module.setId(RandomUtil.randomChar(6));
                module.setRoleId(data.getId());
                roleModuleMapper.insert(new InsertParam<>(module));
            }
        }
        return l;
    }
}
