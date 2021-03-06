package com.zxwl.web.service.impl;

import com.zxwl.web.bean.DeviceUseInfs;
import com.zxwl.web.dao.DeviceUseInfsMapper;
import com.zxwl.web.service.DeviceUseInfsService;
import com.zxwl.web.service.QueryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 设备使用记录表 服务类实现
 * Created by generator
 */
@Service("deviceUseInfsService")
public class DeviceUseInfsServiceImpl extends AbstractServiceImpl<DeviceUseInfs, String> implements DeviceUseInfsService {

    @Resource
    protected DeviceUseInfsMapper deviceUseInfsMapper;

    @Override
    protected DeviceUseInfsMapper getMapper() {
        return this.deviceUseInfsMapper;
    }


    @Override
    public List<DeviceUseInfs> getDeviceInfs(String deviceId) {
        List<DeviceUseInfs> list = QueryService.createQuery(deviceUseInfsMapper)
                .where(DeviceUseInfs.Property.deviceId, deviceId)
                .list();
        return list;
    }
}
