package com.zxwl.web.controller;

import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.bean.GoodsInfo;
import com.zxwl.web.core.message.ResponseMessage;
import com.zxwl.web.service.GoodsClassService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.zxwl.web.service.GoodsInfoService;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 商品信息控制器
 * Created by generator
 */
@RestController
@RequestMapping(value = "/goodsinfo")
@AccessLogger("商品信息")
@Authorize(module = "goodsinfo")
public class GoodsInfoController extends GenericController<GoodsInfo, String> {

    @Resource
    private  GoodsInfoService goodsInfoService;

    @Resource
    private GoodsClassService goodsClassService;

    @Override
    public  GoodsInfoService getService() {
        return this.goodsInfoService;
    }

    @RequestMapping(value = "/goodsClassTree", method = RequestMethod.GET)
    @AccessLogger("查询商品类别树")
    @Authorize(action = "R")
    public ResponseMessage classListTree() {
        Object data = goodsClassService.getTreeNodes();
        return ResponseMessage.ok(data);
    }

    @RequestMapping(value = "/goodsClassTree/{id}", method = RequestMethod.GET)
    @AccessLogger("查询商品类别树")
    @Authorize(action = "R")
    public ResponseMessage classChildrenTree(@PathVariable("id") String classCode) {
        Object data = goodsClassService.getTreeNodeByClassCode(classCode);
        return ResponseMessage.ok(data);
    }
}
