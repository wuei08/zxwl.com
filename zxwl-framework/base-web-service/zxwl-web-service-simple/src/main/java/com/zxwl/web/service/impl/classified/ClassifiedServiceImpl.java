package com.zxwl.web.service.impl.classified;

import com.zxwl.web.bean.po.classified.Classified;
import com.zxwl.web.dao.classified.ClassifiedMapper;
import com.zxwl.web.service.classified.ClassifiedService;
import com.zxwl.web.service.impl.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 数据分类服务类
 * Created by generator
 */
@Service("classifiedService")
public class ClassifiedServiceImpl extends AbstractServiceImpl<Classified, String> implements ClassifiedService {

    //默认数据映射接口
    @Resource
    protected ClassifiedMapper classifiedMapper;

    @Override
    protected ClassifiedMapper getMapper() {
        return this.classifiedMapper;
    }

}
