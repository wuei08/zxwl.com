package com.zxwl.web.dao;

import com.zxwl.web.bean.ShopDecoration;

/**
* MyBatis 店铺装修表 数据映射接口
* Created by generator 
*/
public interface ShopDecorationMapper extends GenericMapper<ShopDecoration,String> {

    public ShopDecoration selectByShopID(String id);

    public int deleteByShopID(String id);
}
