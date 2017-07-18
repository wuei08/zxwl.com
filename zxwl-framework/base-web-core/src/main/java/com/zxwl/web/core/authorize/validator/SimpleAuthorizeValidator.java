package com.zxwl.web.core.authorize.validator;

import org.hsweb.commons.StringUtils;
import org.hsweb.expands.script.engine.DynamicScriptEngine;
import org.hsweb.expands.script.engine.DynamicScriptEngineFactory;
import com.zxwl.web.bean.po.user.User;
import com.zxwl.web.core.authorize.AuthorizeValidator;
import com.zxwl.web.core.authorize.AuthorizeValidatorConfig;
import com.zxwl.web.core.authorize.ExpressionScopeBean;
import com.zxwl.web.core.authorize.annotation.Authorize;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 权限验证器
 * Created by zhouhao on 16-4-28.
 */
public class SimpleAuthorizeValidator implements AuthorizeValidator {

    @Autowired(required = false)
    private Map<String, ExpressionScopeBean> expressionScopeBeanMap;

    @Override
    public boolean validate(User user, Map<String, Object> param, AuthorizeValidatorConfig config) {
        SimpleAuthorizeValidatorConfig validatorConfig = ((SimpleAuthorizeValidatorConfig) config);
        Set<String> modules = validatorConfig.getModules();
        Set<String> roles = validatorConfig.getRoles();
        Set<String> actions = validatorConfig.getActions();
        Set<SimpleAuthorizeValidatorConfig.Expression> expressions = validatorConfig.getExpressions();
        Authorize.MOD mod = validatorConfig.getMod();
        boolean access = false;
        //验证模块
        if (!modules.isEmpty()) {
            if (mod == Authorize.MOD.AND)
                access = modules.stream().allMatch(module ->
                        user.hasAccessModuleAction(module, actions.toArray(new String[actions.size()])));
            else access = modules.stream().anyMatch(module ->
                    user.hasAccessModuleAction(module, actions.toArray(new String[actions.size()])));
        }
        //验证角色
        if (!roles.isEmpty()) {
            if (mod == Authorize.MOD.AND)
                access = roles.stream().allMatch(user::hasAccessRole);
            else
                access = roles.stream().anyMatch(user::hasAccessRole);
        }
        //验证表达式
        if (!expressions.isEmpty()) {
            if (mod == Authorize.MOD.AND)
                access = expressions.stream().allMatch(expression -> {
                    DynamicScriptEngine engine = DynamicScriptEngineFactory.getEngine(expression.getLanguage());
                    Map<String, Object> var = getExpressionRoot(user);
                    var.putAll(param);
                    return StringUtils.isTrue(engine.execute(expression.getId(), var).get());
                });
            else
                access = expressions.stream().anyMatch(expression -> {
                    DynamicScriptEngine engine = DynamicScriptEngineFactory.getEngine(expression.getLanguage());
                    Map<String, Object> var = getExpressionRoot(user);
                    var.putAll(param);
                    return StringUtils.isTrue(engine.execute(expression.getId(), var).get());
                });
        }

        return access;
    }


    public Map<String, Object> getExpressionRoot(User user) {
        Map<String, Object> root = new HashMap<>();
        if (expressionScopeBeanMap != null)
            root.putAll(expressionScopeBeanMap);
        root.put("user", user);
        return root;
    }

    @Override
    public AuthorizeValidatorConfig createConfig() {
        return new SimpleAuthorizeValidatorConfig();
    }

}
