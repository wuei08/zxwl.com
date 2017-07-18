package com.zxwl.platform.service.impl;

import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.platform.bean.Shop;
import com.zxwl.platform.dao.ShopMapper;
import com.zxwl.web.service.impl.AbstractServiceImpl;
import com.zxwl.platform.service.ShopService;
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
