package com.zxwl.web.service.impl;

import com.zxwl.web.bean.GoodsInfo;
import com.zxwl.web.dao.GoodsInfoMapper;
import com.zxwl.web.service.GoodsInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品信息 服务类实现
 * Created by generator
 */
@Service("goodsInfoService")
public class GoodsInfoServiceImpl extends AbstractServiceImpl<GoodsInfo, String> implements GoodsInfoService {

    @Resource
    protected GoodsInfoMapper goodsInfoMapper;

    @Override
    protected GoodsInfoMapper getMapper() {
        return this.goodsInfoMapper;
    }
}
