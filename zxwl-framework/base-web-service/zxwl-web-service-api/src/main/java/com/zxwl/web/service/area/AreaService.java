package com.zxwl.web.service.area;

import com.zxwl.web.bean.po.area.Area;
import com.zxwl.web.service.GenericService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public interface AreaService extends GenericService<Area, String> {

    List<Area> selectByParentId(String id);
}
