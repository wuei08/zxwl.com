package com.zxwl.web.service.impl;

import com.zxwl.web.bean.Shop;
import com.zxwl.web.dao.ShopMapper;
import com.zxwl.web.service.ShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 店铺信息 服务类实现
 * Created by generator
 */
@Service("shopService")
public class ShopServiceImpl extends AbstractServiceImpl<Shop, String> implements ShopService {

    @Resource
    protected ShopMapper shopMapper;

    @Override
    protected ShopMapper getMapper() {
        return this.shopMapper;
    }
}
