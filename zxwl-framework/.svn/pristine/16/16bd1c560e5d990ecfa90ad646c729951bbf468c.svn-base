package com.zxwl.platform.ui.controller;


import com.google.common.collect.ImmutableMap;
import com.zxwl.platform.ui.utils.CPUInfo;
import com.zxwl.platform.ui.utils.DiskInfo;
import com.zxwl.platform.ui.utils.RAMInfo;
import com.zxwl.web.core.message.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Project: zxwl-framework
 * Author: Sendya <18x@loacg.com>
 * Date: 2017/6/21 10:36
 */
@RestController
@RequestMapping(value = "/monitor")
public class SystemMonitorController {

    @RequestMapping(value = "/info")
    public ResponseMessage info() {

        double cpuLoad = CPUInfo.getProcessCpuLoad();

        double diskLoad = DiskInfo.getDiskPercentUsage(0);

        double ramLoad = RAMInfo.getRamPercentUsage();

        Map<String, Object> data = ImmutableMap.of("cpu", cpuLoad, "disk", diskLoad, "ram", ramLoad);

        return ResponseMessage.ok(data);
    }
}
