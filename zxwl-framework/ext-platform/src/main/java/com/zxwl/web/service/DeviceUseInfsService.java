package com.zxwl.web.service;

import com.zxwl.web.bean.DeviceUseInfs;
import com.zxwl.web.bean.common.QueryParam;

import java.util.List;

/**
 * 设备使用记录表 服务类接口
 * Created by generator
 */
public interface DeviceUseInfsService extends GenericService<DeviceUseInfs, String> {

    List<DeviceUseInfs> getDeviceInfs( String deviceId);

}
