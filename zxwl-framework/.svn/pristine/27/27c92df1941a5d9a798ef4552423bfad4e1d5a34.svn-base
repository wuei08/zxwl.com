package com.zxwl.web.service.impl;

import com.zxwl.web.bean.UserInfo;
import com.zxwl.web.dao.UserInfoMapper;
import com.zxwl.web.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息 服务类实现
 * Created by generator
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends AbstractServiceImpl<UserInfo, String> implements UserInfoService {

    @Resource
    protected UserInfoMapper userInfoMapper;

    @Override
    protected UserInfoMapper getMapper() {
        return this.userInfoMapper;
    }
}
