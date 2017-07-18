package com.zxwl.web.service.impl.template;

import com.alibaba.fastjson.JSON;
import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.po.history.History;
import com.zxwl.web.bean.po.template.Template;
import com.zxwl.web.bean.po.template.Template.Property;
import com.zxwl.web.dao.template.TemplateMapper;
import com.zxwl.web.service.history.HistoryService;
import com.zxwl.web.service.impl.AbstractServiceImpl;
import com.zxwl.web.service.template.TemplateService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.zxwl.web.bean.po.GenericPo.Property.id;
import static com.zxwl.web.bean.po.template.Template.Property.release;
import static com.zxwl.web.bean.po.template.Template.Property.using;
import static com.zxwl.web.bean.po.template.Template.Property.version;

/**
 * Created by zhouhao on 16-5-20.
 */
@Service("templateService")
public class TemplateServiceImpl extends AbstractServiceImpl<Template, String> implements TemplateService {

    private static final String CACHE_NAME = "template";

    @Resource
    private TemplateMapper templateMapper;

    @Resource
    private HistoryService historyService;

    @Override
    protected TemplateMapper getMapper() {
        return templateMapper;
    }

    @Override
    public String insert(Template data) {
        data.setVersion(1);
        data.setUsing(false);
        data.setRelease(0);
        data.setRevision(0);
        return super.insert(data);
    }

    @Override
    public String createNewVersion(String oldVersionId) {
        Template old = templateMapper.selectByPk(oldVersionId);
        assertNotNull(old, "模板不存在");
        old.setId(null);
        old.setVersion(old.getVersion() + 1);
        old.setRevision(0);
        old.setRelease(0);
        old.setUsing(false);
        insert(old);
        return old.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Template> selectLatestList(QueryParam param) {
        return templateMapper.selectLatestList(param);
    }

    @Override
    @Transactional(readOnly = true)
    public int countLatestList(QueryParam param) {
        return templateMapper.countLatestList(param);
    }

    @Override
    @CacheEvict(value = CACHE_NAME,
            key = "'template.name.using'+target.selectByPk(#id).getName()",
            condition = "target.selectByPk(#id).isUsing()")
    public int update(Template data) {
        Template old = selectByPk(data.getId());
        assertNotNull(old, "模板不存在");
        data.setRevision(old.getRevision() + 1);
        return createUpdate()
                .excludes(version, release, release, using)
                .where(id, data.getId())
                .exec();
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = CACHE_NAME, key = "'template.using.name.'+target.selectByPk(#id).getName()"),
                    @CacheEvict(value = CACHE_NAME, key = "'template.deploy.name.'+target.selectByPk(#id).getName()")
            }
    )
    public void deploy(String id) {
        Template old = templateMapper.selectByPk(id);
        assertNotNull(old, "模板不存在");
        Template usingTemplate = selectUsing(old.getName());
        if (usingTemplate != null) {
            usingTemplate.setUsing(true);
            createUpdate(usingTemplate).includes(using).fromBean().where(Property.id).exec();
        }
        old.setUsing(true);
        createUpdate(old).includes(using).fromBean().where(Property.id).exec();
        History history = new History();
        history.setPrimaryKeyName("id");
        history.setPrimaryKeyValue(id);
        history.setChangeAfter(JSON.toJSONString(old));
        history.setDescribe("模板发布历史");
        history.setType("template.deploy." + old.getName());
        history.setCreateDate(new Date());
        historyService.insert(history);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = CACHE_NAME, key = "'template.using.name.'+target.selectByPk(#id).getName()")
                    , @CacheEvict(value = CACHE_NAME, key = "'template.deploy.name'+target.selectByPk(#id).getName()")
            }
    )
    public void unDeploy(String id) {
        Template old = templateMapper.selectByPk(id);
        assertNotNull(old, "模板不存在");
        old.setUsing(false);
        createUpdate(old).includes(using).fromBean().where(Property.id);
    }

    @Override
    @Transactional(readOnly = true)
    public Template selectLatest(String name) {
        return createQuery()
                .where(Property.name, name)
                .orderByDesc(version)
                .list(templateMapper::selectLatestList)
                .stream().findFirst().orElse(null);
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'template.name.'+#name+':'+#version")
    public Template selectByVersion(String name, int version) {
        return this.createQuery().where(Property.name, name).and(Property.version, version).single();
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'template.deploy.name.'+#name")
    public Template selectDeploy(String name) {
        Template deployed = selectUsing(name);
        assertNotNull(deployed, "模板不存在或未部署");
        History history = historyService.selectLastHistoryByType("template.deploy." + name);
        assertNotNull(history, "模板不存在或未部署");
        return JSON.parseObject(history.getChangeAfter(), Template.class);
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'template.using.name.'+#name")
    public Template selectUsing(String name) {
        return this.createQuery().where(Property.name, name).and(Property.using, true).single();
    }
}
