package com.zxwl.web.controller;

import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.bean.UserInfo;
import org.springframework.web.bind.annotation.RestController;
import com.zxwl.web.service.UserInfoService;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 用户信息控制器
 * Created by generator
 */
@RestController
@RequestMapping(value = "/userInfo")
@AccessLogger("用户信息")
@Authorize(module = "userInfo")
public class UserInfoController extends GenericController<UserInfo, String> {

    @Resource
    private  UserInfoService userInfoService;

    @Override
    public  UserInfoService getService() {
        return this.userInfoService;
    }
}
