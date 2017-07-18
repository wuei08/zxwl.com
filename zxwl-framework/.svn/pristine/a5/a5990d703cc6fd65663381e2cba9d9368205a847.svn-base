package com.zxwl.web.service.impl;

import com.zxwl.web.bean.ShopDecoration;
import com.zxwl.web.dao.ShopDecorationMapper;
import com.zxwl.web.service.ShopDecorationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 店铺装修表 服务类实现
 * Created by generator
 */
@Service("shopDecorationService")
public class ShopDecorationServiceImpl extends AbstractServiceImpl<ShopDecoration, String> implements ShopDecorationService {

    @Resource
    protected ShopDecorationMapper shopDecorationMapper;

    @Override
    protected ShopDecorationMapper getMapper() {
        return this.shopDecorationMapper;
    }

    @Override
    public ShopDecoration selectByShopID(String id) {
        return getMapper().selectByShopID(id);
    }

    @Override
    public int deleteByShopID(String id) {
        return getMapper().deleteByShopID(id);
    }
}
