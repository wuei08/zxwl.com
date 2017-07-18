package com.zxwl.web.controller.area;

import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.po.area.Area;
import com.zxwl.web.controller.GenericController;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.core.exception.NotFoundException;
import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.message.ResponseMessage;
import com.zxwl.web.service.area.AreaService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.LinkedList;
import java.util.List;

import static com.zxwl.web.core.message.ResponseMessage.ok;

/**
 * 区域分配控制器
 * Created by generator
 */
@RestController
@RequestMapping(value = "/area")
@AccessLogger("区域分配")
//@Authorize(module = "Area")
public class AreaController extends GenericController<Area, String> {

    @Resource
    private  AreaService areaService;

    @Override
    public AreaService getService() {
        return this.areaService;
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
        Area old = getService().selectByPk(id);
        assertFound(old, "data is not found!");
        //删除子节点
        deleteAllChildren(old);
        getService().delete(old.getId());
        return ok();
    }

    private void deleteAllChildren(Area root){
        List<Area> nodes = getService().selectByParentId(root.getId());
        if(nodes == null){
        }
        else{
            for(Area node:nodes){
                deleteAllChildren(node);
                getService().delete(node.getId());
            }
        }

    }
}
