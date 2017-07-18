package com.zxwl.web.service.template;

import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.po.template.Template;
import com.zxwl.web.service.GenericService;

import java.util.List;

public interface TemplateService extends GenericService<Template, String> {

    String createNewVersion(String oldVersionId) throws Exception;

    List<Template> selectLatestList(QueryParam param) throws Exception;

    int countLatestList(QueryParam param) throws Exception;

    void deploy(String id) throws Exception;

    void unDeploy(String id) throws Exception;

    Template selectLatest(String name) throws Exception;

    Template selectByVersion(String name, int version) throws Exception;

    Template selectUsing(String name) throws Exception;

    Template selectDeploy(String name) throws Exception;

}
