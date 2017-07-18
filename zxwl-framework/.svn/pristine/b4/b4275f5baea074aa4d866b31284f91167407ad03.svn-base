package com.zxwl.web.dao.role;

import com.zxwl.web.dao.GenericMapper;
import com.zxwl.web.bean.po.role.UserRole;

import java.util.List;

/**
 * 后台管理用户角色绑定数据映射接口
 * Created by generator
 */
public interface UserRoleMapper extends GenericMapper<UserRole, String> {
    /**
     * 根据用户id查询用户的角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<UserRole> selectByUserId(String userId);

    int deleteByUserId(String userId);
}
