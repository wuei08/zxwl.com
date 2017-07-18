package com.zxwl.platform.service.impl;

import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.platform.bean.ShopDecoration;
import com.zxwl.platform.dao.ShopDecorationMapper;
import com.zxwl.web.service.impl.AbstractServiceImpl;
import com.zxwl.platform.service.ShopDecorationService;
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
