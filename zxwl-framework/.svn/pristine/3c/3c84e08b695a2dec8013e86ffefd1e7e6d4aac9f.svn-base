package com.zxwl.web.controller;

import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.bean.DeviceFid;
import org.springframework.web.bind.annotation.RestController;
import com.zxwl.web.service.DeviceFidService;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 射频管理控制器
 * Created by generator
 */
@RestController
@RequestMapping(value = "/radiofrequency")
@AccessLogger("射频管理")
@Authorize(module = "radiofrequency")
public class DeviceFidController extends GenericController<DeviceFid, String> {

    @Resource
    private  DeviceFidService deviceFidService;

    @Override
    public  DeviceFidService getService() {
        return this.deviceFidService;
    }
}
