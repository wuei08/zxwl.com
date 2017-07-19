package com.zxwl.web.controller;

import com.zxwl.web.bean.common.PagerResult;
import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.po.user.User;
import com.zxwl.web.core.exception.NotFoundException;
import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.bean.UserInfo;
import com.zxwl.web.core.message.ResponseMessage;
import com.zxwl.web.service.user.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.zxwl.web.service.UserInfoService;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.List;

import static com.zxwl.web.core.message.ResponseMessage.ok;

/**
 * 用户信息控制器
 * Created by generator
 */
@RestController
@RequestMapping(value = "/userInfo")
@AccessLogger("用户信息")
@Authorize(module = "userInfo")
public class UserInfoController extends GenericController<UserInfo, String> {

    @Resource
    private  UserInfoService userInfoService;

    @Resource
    private UserService userService;

    @Override
    public  UserInfoService getService() {
        return this.userInfoService;
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
        Object data;
        List<UserInfo> userInfoList = null;
        List<User> userList = null;

        if (!param.isPaging()){
            //不分页
            userInfoList = getService().select(param);
            userList = userService.select(param);
            for(UserInfo userInfo:userInfoList){
                if(userInfo.getSex().equals("0")){ //设置性别
                    userInfo.setSex("女");
                }
                else{
                    if(userInfo.getSex().equals("1")){
                        userInfo.setSex("男");
                    }
                    else{
                        userInfo.setSex("人妖");
                    }
                }
                for(User user:userList){
                    if(userInfo != null && user != null && userInfo.getUserId().equals(user.getId())){
                        userInfo.setStatus(user.getStatus());
                    }
                }
            }
            return ok(userInfoList)
                    .include(getPOType(), param.getIncludes())
                    .exclude(getPOType(), param.getExcludes())
                    .onlyData();
        }
        else{
            PagerResult<UserInfo> userInfoPagerResult = getService().selectPager(param);
            userList = userService.select(param);
            userInfoList = userInfoPagerResult.getData();
            for(UserInfo userInfo:userInfoList){
                if(userInfo.getSex().equals("0")){
                    userInfo.setSex("女");
                }
                else{
                    if(userInfo.getSex().equals("1")){
                        userInfo.setSex("男");
                    }
                    else{
                        userInfo.setSex("人妖");
                    }
                }
                for(User user:userList){
                    if(userInfo != null && user != null && userInfo.getUserId().equals(user.getId())){
                        userInfo.setStatus(user.getStatus());
                    }
                }
            }
            userInfoPagerResult.setData(userInfoList);
            return ok(userInfoPagerResult)
                    .include(getPOType(), param.getIncludes())
                    .exclude(getPOType(), param.getExcludes())
                    .onlyData();
        }
    }

    /**
     * 请求删除指定id的数据，请求方式为DELETE，使用rest风格，如请求 /put/close/1 ，将删除id为1的数据
     *
     * @param id 要启用的id标识
     * @return 启用结果
     * @throws NotFoundException 要启用的数据不存在
     */
    @RequestMapping(value = "/close/{id}", method = RequestMethod.DELETE)
    @AccessLogger("禁用")
    @Authorize(action = "U")
    public ResponseMessage close(@PathVariable("id") String id) {
        UserInfo old = getService().selectByPk(id);
        assertFound(old, "data is not found!");
        userService.disableUser(old.getUserId());
        return ok();
    }

    /**
     * 请求启用指定id的数据，请求方式为put，使用rest风格，如请求 /put/open/1 ，将删除id为1的数据
     *
     * @param id 要启用的id标识
     * @return 启用结果
     * @throws NotFoundException 要启用的数据不存在
     */
    @RequestMapping(value = "/open/{id}", method = RequestMethod.DELETE)
    @AccessLogger("启用")
    @Authorize(action = "U")
    public ResponseMessage open(@PathVariable("id") String id) {
        UserInfo old = getService().selectByPk(id);
        assertFound(old, "data is not found!");
        userService.enableUser(old.getUserId());
        return ok();
    }

}
