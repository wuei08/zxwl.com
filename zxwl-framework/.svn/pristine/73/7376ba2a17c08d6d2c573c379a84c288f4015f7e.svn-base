package com.zxwl.web.dao.template;

import org.hsweb.ezorm.core.param.QueryParam;
import com.zxwl.web.bean.po.template.Template;
import com.zxwl.web.dao.GenericMapper;

import java.util.List;

/**
 * Created by zhouhao on 16-5-20.
 */
public interface TemplateMapper extends GenericMapper<Template, String> {
    /**
     * 查看当前正在使用的模板
     *
     * @param name 模板名字
     * @return 模板对象
     */
    Template selectUsing(String name) ;

    /**
     * 查询最新版本的模板列表
     *
     * @param param 查询参数
     * @return 模板列表
     */
    List<Template> selectLatestList(QueryParam param) ;

    /**
     * 查询最新版本的模板数量
     *
     * @param param 查询参数
     * @return 模板数量
     */
    int countLatestList(QueryParam param) ;

}
