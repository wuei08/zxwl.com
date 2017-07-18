package com.zxwl.web.core.jsonp;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import com.zxwl.web.core.utils.ThreadLocalUtils;
import com.zxwl.web.core.utils.WebUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.hsweb.commons.StringUtils;

/**
 * Created by zhouhao on 16-5-26.
 */
@Aspect
@Order
@Component
public class AopJsonpSupport {
    @Before(value = "@annotation(jsonp)")
    public void around(Jsonp jsonp) throws Throwable {
        String callback = WebUtil.getHttpServletRequest().getParameter(jsonp.value());
        if (!StringUtils.isNullOrEmpty(callback)) {
            ThreadLocalUtils.put("jsonp-callback", callback);
        }
    }
}
