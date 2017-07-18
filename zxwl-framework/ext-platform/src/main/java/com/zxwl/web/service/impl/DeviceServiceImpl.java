package com.zxwl.web.service.impl;

import com.zxwl.web.bean.Device;
import com.zxwl.web.dao.DeviceMapper;
import com.zxwl.web.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设备管理 服务类实现
 * Created by generator
 */
@Service("deviceService")
public class DeviceServiceImpl extends AbstractServiceImpl<Device, String> implements DeviceService {

    @Resource
    protected DeviceMapper deviceMapper;

    @Override
    protected DeviceMapper getMapper() {
        return this.deviceMapper;
    }
}
