package com.zxwl.web.controller;

import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.bean.Device;
import org.springframework.web.bind.annotation.RestController;
import com.zxwl.web.service.DeviceService;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 设备管理控制器
 * Created by generator
 */
@RestController
@RequestMapping(value = "/device")
@AccessLogger("设备管理")
@Authorize(module = "device")
public class DeviceController extends GenericController<Device, String> {

    @Resource
    private  DeviceService deviceService;

    @Override
    public  DeviceService getService() {
        return this.deviceService;
    }
}
