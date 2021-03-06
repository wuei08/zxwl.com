package com.zxwl.web.controller;

import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.bean.Device;
import com.zxwl.web.core.message.ResponseMessage;
import com.zxwl.web.service.DeviceUseInfsService;
import org.springframework.web.bind.annotation.*;
import com.zxwl.web.service.DeviceService;

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

    @Resource
    private  DeviceUseInfsService deviceUseInfsService;

    @RequestMapping(value = "/deviceuseinfs", method = RequestMethod.GET)
    @AccessLogger("使用记录")
    @Authorize(action = "R")
    public ResponseMessage getDeviceInfs(@RequestParam(value="deviceId", required = false) String deviceId) {
        // 获取条件查询
        Object data = deviceUseInfsService.getDeviceInfs(deviceId);
        return ResponseMessage.ok(data);
    }
}
