package com.zxwl.platform.ui.utils;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

/**
 * Project: zxwl-framework
 * Author: Sendya <18x@loacg.com>
 * Date: 2017/6/21 10:34
 */
public class RAMInfo {

    private static final OperatingSystemMXBean mxbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public static double getRamPercentUsage(){
        double maxvalue= (mxbean.getTotalPhysicalMemorySize()/1024/1024);
        double current= ((mxbean.getTotalPhysicalMemorySize()-mxbean.getFreePhysicalMemorySize())/1024/1024);
        double percent=(current/maxvalue)*100;
        double result=Math.round(percent*100.0)/100.0;

        return result;
    }

}
