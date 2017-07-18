package com.zxwl.web.controller;

import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.bean.GoodsClass;
import org.springframework.web.bind.annotation.RestController;
import com.zxwl.web.service.GoodsClassService;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 商品类别控制器
 * Created by generator
 */
@RestController
@RequestMapping(value = "/goodsclass")
@AccessLogger("商品类别")
@Authorize(module = "goodsclass")
public class GoodsClassController extends GenericController<GoodsClass, String> {

    @Resource
    private  GoodsClassService goodsClassService;

    @Override
    public  GoodsClassService getService() {
        return this.goodsClassService;
    }


}
