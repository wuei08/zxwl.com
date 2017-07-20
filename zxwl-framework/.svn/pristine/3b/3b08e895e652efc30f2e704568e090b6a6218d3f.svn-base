package com.zxwl.web.controller;

import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.bean.DeviceUseInfs;
import org.springframework.web.bind.annotation.RestController;
import com.zxwl.web.service.DeviceUseInfsService;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备使用记录表控制器
 * Created by generator
 */
@RestController
@RequestMapping(value = "/deviceuseinfs")
@AccessLogger("设备使用记录表")
@Authorize(module = "deviceuseinfs")
public class DeviceUseInfsController extends GenericController<DeviceUseInfs, String> {

    @Resource
    private  DeviceUseInfsService deviceUseInfsService;

    @Override
    public  DeviceUseInfsService getService() {
        return this.deviceUseInfsService;
    }


}
