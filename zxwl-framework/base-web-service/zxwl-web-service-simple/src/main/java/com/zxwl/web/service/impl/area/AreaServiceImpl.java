package com.zxwl.web.service.impl.area;

import com.zxwl.web.bean.po.area.Area;
import com.zxwl.web.dao.area.AreaMapper;
import com.zxwl.web.service.area.AreaService;
import com.zxwl.web.service.impl.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
@Service("areaService")
public class AreaServiceImpl extends AbstractServiceImpl<Area, String> implements AreaService {

    @Resource
    private AreaMapper areaMapper;

    @Override
    public List<Area> selectByParentId(String id) {
        return getMapper().selectByParentId(id);
    }

    @Override
    protected AreaMapper getMapper() {
        return this.areaMapper;
    }
}
