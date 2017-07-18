package com.zxwl.platform.service;

import com.zxwl.platform.bean.ShopDecoration;
import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.service.GenericService;

/**
 * 店铺装修表 服务类接口
 * Created by generator
 */
public interface ShopDecorationService extends GenericService<ShopDecoration, String> {

    public ShopDecoration selectByShopID(String id);

    public int deleteByShopID(String id);

}
