package com.zxwl.web.controller;

import com.zxwl.web.bean.ShopDecoration;
import com.zxwl.web.service.ShopDecorationService;
import com.zxwl.web.bean.common.PagerResult;
import com.zxwl.web.core.exception.NotFoundException;
import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.Shop;
import com.zxwl.web.core.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.zxwl.web.service.ShopService;

import javax.annotation.Resource;

import java.util.List;

import static com.zxwl.web.core.message.ResponseMessage.created;
import static com.zxwl.web.core.message.ResponseMessage.ok;

/**
 * 店铺信息控制器
 * Created by generator
 */
@RestController
@RequestMapping(value = "/shop")
@AccessLogger("店铺信息")
@Authorize(module = "shop")
public class ShopController extends  GenericController<Shop, String>{

    @Resource
    private  ShopService shopService;

    @Resource
    private ShopDecorationService shopDecorationService;

    public  ShopService getService() {
        return this.shopService;
    }

    /**
     * 查询列表,并返回查询结果
     *
     * @param param 查询参数 {@link QueryParam}
     * @return 查询结果, 如果参数指定了分页(默认指定)将返回格式如:{total:数据总数,data:[{}]}的数据.
     * 否则返回格式[{}]
     */
    @RequestMapping(method = RequestMethod.GET)
    @AccessLogger("查询列表")
    @Authorize(action = "R")
    public ResponseMessage list(QueryParam param) {
        // 获取条件查询
        List<Shop> shopList;
        List<ShopDecoration> shopDecorationList;
        if (!param.isPaging()){

            shopList = getService().select(param);
            shopDecorationList = shopDecorationService.select(param);
            for(Shop shop:shopList){
                for(ShopDecoration shopDecoration:shopDecorationList){
                    if(shop.getId() != null && shop.getId().equals(shopDecoration.getShopId())){
                        shop.setContent(shopDecoration.getContent());
                        shop.setImg1(shopDecoration.getImg1());
                        shop.setImg2(shopDecoration.getImg2());
                        shop.setImg3(shopDecoration.getImg3());
                    }
                }
            }

            return ok(shopList)
                    .include(Shop.class, param.getIncludes())
                    .exclude(Shop.class, param.getExcludes())
                    .onlyData();
        }
        else{
            PagerResult<Shop> pagerResult = getService().selectPager(param);
            shopDecorationList = shopDecorationService.select(param);
            shopList = pagerResult.getData();
            for(Shop shop:shopList){
                for(ShopDecoration shopDecoration:shopDecorationList){
                    if(shop.getId() != null && shop.getId().equals(shopDecoration.getShopId())){
                        shop.setContent(shopDecoration.getContent());
                        shop.setImg1(shopDecoration.getImg1());
                        shop.setImg2(shopDecoration.getImg2());
                        shop.setImg3(shopDecoration.getImg3());
                    }
                }
            }
            pagerResult.setData(shopList);
            return ok(pagerResult)
                    .include(Shop.class, param.getIncludes())
                    .exclude(Shop.class, param.getExcludes())
                    .onlyData();
        }
    }


    /**
     * 根据主键修改数据
     *
     * @param id     要修改数据的主键值
     * @param shop 要修改的数据
     * @return 请求结果
     * @throws NotFoundException 要修改的数据不存在
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @AccessLogger("修改")
    @Authorize(action = "U")
    public ResponseMessage update(@PathVariable("id") String id, @RequestBody Shop shop) {
        Shop old = getService().selectByPk(id);
        assertFound(old, "data is not found!");
        int number = getService().update(shop);
        ShopDecoration shopde = shopDecorationService.selectByShopID(id);
        shopde.setImg1(shop.getImg1());
        shopde.setImg2(shop.getImg2());
        shopde.setImg3(shop.getImg3());
        shopde.setContent(shop.getContent());
        shopDecorationService.update(shopde);
        return ok(number);
    }

    /**
     * 请求添加数据，请求必须以POST方式
     *
     * @param shop 请求添加的对象
     * @return 被添加数据的主键值
     * @throws javax.validation.ValidationException 验证数据格式错误
     */
    @RequestMapping(method = RequestMethod.POST)
    @AccessLogger("新增")
    @Authorize(action = "C")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage add(@RequestBody Shop shop) {
        String id = getService().insert(shop);
        ShopDecoration shopDecoration = new ShopDecoration();
        shopDecoration.setShopId(id);
        shopDecoration.setContent(shop.getContent());
        shopDecoration.setImg1(shop.getImg1());
        shopDecoration.setImg2(shop.getImg2());
        shopDecoration.setImg3(shop.getImg3());
        shopDecorationService.insert(shopDecoration);
        return created(id);
    }

    /**
     * 请求删除指定id的数据，请求方式为DELETE，使用rest风格，如请求 /delete/1 ，将删除id为1的数据
     *
     * @param id 要删除的id标识
     * @return 删除结果
     * @throws NotFoundException 要删除的数据不存在
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @AccessLogger("删除")
    @Authorize(action = "D")
    public ResponseMessage delete(@PathVariable("id") String id) {
        Shop old = getService().selectByPk(id);
        assertFound(old, "data is not found!");
        getService().delete(id);
        shopDecorationService.deleteByShopID(id);
        return ok();
    }

}
