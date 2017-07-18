package com.zxwl.platform.ui.controller;

import com.google.common.collect.Lists;
import org.hsweb.commons.StringUtils;
import com.zxwl.web.bean.po.module.Module;
import com.zxwl.web.bean.po.user.User;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.core.utils.WebUtil;
import com.zxwl.web.service.module.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Project: zxwl-framework
 * Author: Sendya <18x@loacg.com>
 * Date: 2017/6/7 15:24
 */
@Controller
public class PageViewController {

    @Resource
    private ModuleService moduleService;

    @RequestMapping(value = "/admin/login.html", method = RequestMethod.GET)
    public ModelAndView login(String uri) throws UnsupportedEncodingException {
        return new ModelAndView("admin/login");
    }

    @RequestMapping(value = "/admin/profile.html", method = RequestMethod.GET)
    @Authorize
    public ModelAndView profile(HttpServletRequest request,
                              @RequestParam(required = false) Map<String, Object> param) {

        String path = getUri(request);
        if (path.contains("."))
            path = path.split("[.]")[0];

        User user = WebUtil.getLoginUser();
        ModelAndView modelAndView = new ModelAndView("admin/profile");
        modelAndView.addObject("menu", this.buildMenu(""));
        modelAndView.addObject("path", path);
        modelAndView.addObject("user", user);
        modelAndView.addObject("pageTitle", "个人资料");
        return modelAndView;
    }


    @RequestMapping(value = "/admin/index.html", method = RequestMethod.GET)
    @Authorize
    public ModelAndView index(HttpServletRequest request,
                              @RequestParam(required = false) Map<String, Object> param) {

        String path = getUri(request);
        if (path.contains("."))
            path = path.split("[.]")[0];

        User user = WebUtil.getLoginUser();
        ModelAndView modelAndView = new ModelAndView("admin/index");
        modelAndView.addObject("menu", this.buildMenu(""));
        modelAndView.addObject("path", path);
        modelAndView.addObject("user", user);
        modelAndView.addObject("pageTitle", "仪表盘");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/system-dev/{module}/{view}.html", method = RequestMethod.GET)
    @Authorize
    public ModelAndView systemView(HttpServletRequest request, @PathVariable("module") String module,
                             @PathVariable("view") String view,
                             @RequestParam(required = false) Map<String, Object> param) {
        String path = getUri(request);
        if (path.contains("."))
            path = path.split("[.]")[0];

        Module moduleBean = moduleService.selectByPk(module);

        ModelAndView modelAndView = new ModelAndView(path);
        modelAndView.addObject("menu", this.buildMenu(module));
        modelAndView.addObject("path", path);
        modelAndView.addObject("module", module);
        modelAndView.addObject("pageTitle", moduleBean.getName());
        modelAndView.addObject("viewName", view);
        modelAndView.addObject("user", WebUtil.getLoginUser());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/{module}/{view}.html", method = RequestMethod.GET)
    @Authorize
    public ModelAndView view(HttpServletRequest request, @PathVariable("module") String module,
                             @PathVariable("view") String view,
                             @RequestParam(required = false) Map<String, Object> param) {
        String path = getUri(request);
        if (path.contains("."))
            path = path.split("[.]")[0];

        Module moduleBean = moduleService.selectByPk(module);

        ModelAndView modelAndView = new ModelAndView(path);
        modelAndView.addObject("menu", this.buildMenu(module));
        modelAndView.addObject("path", path);
        modelAndView.addObject("module", module);
        modelAndView.addObject("pageTitle", moduleBean.getName());
        modelAndView.addObject("viewName", view);
        modelAndView.addObject("user", WebUtil.getLoginUser());

        return modelAndView;
    }

    public String getUri(HttpServletRequest request) {
        String path = request.getRequestURI();
        String content = request.getContextPath();
        if (path.startsWith(content)) {
            path = path.substring(content.length() + 1);
        }
        return path;
    }


    public List<Module> buildMenu(String activeId) {
        String[] includes = {
                "name", "id", "parentId", "icon", "uri", "optional"
        };
        User user = WebUtil.getLoginUser();
        List<Module> modules;
        if (user == null) {
            modules = moduleService.createQuery().select(includes).orderByAsc(Module.Property.sortIndex).listNoPaging();
            modules = modules.stream()
                    .filter(module -> {
                        Object obj = module.getOptionalMap().get("M");
                        if (obj instanceof Map)
                            return StringUtils.isTrue(((Map) obj).get("checked"));
                        return false;
                    })
                    .collect(Collectors.toCollection(() -> new LinkedList<>()));
        } else {
            modules = user.getModules().stream()
                    .filter(module -> user.hasAccessModuleAction(module.getId(), "M"))
                    .sorted()
                    .collect(Collectors.toList());
        }

        List<Module> moduleList = this.treeMenuList(modules, "-1", activeId);
        return moduleList;
    }

    public List<Module> treeMenuList(List<Module> modules, String parentId, String activeId) {
        List<Module> moduleList = Lists.newArrayList();

        for (Module module : modules) {

            String menuId = module.getId();
            String pid = module.getParentId();

            checkNotNull(parentId);
            if (parentId.equals(pid)) {
                List<Module> childModuleList = treeMenuList(modules, menuId, activeId);
                module.setChildList(childModuleList);

                if (module.getId().indexOf(activeId) > -1) {
                    module.setActive("menu-open");
                }

                moduleList.add(module);
            }
        }

        return moduleList;
    }
}
