package com.zxwl.platform;


import com.zxwl.web.core.authorize.annotation.Authorize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.zxwl.platform", "com.zxwl.web","com.zxwl.pay"})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableCaching
@Controller
@MapperScan({"com.zxwl.platform.dao", "com.zxwl.web.dao","com.zxwl.pay.api.dao"})
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class Run {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Run.class, args);
    }

    @RequestMapping(value = {"/", "/index.html"})
    @Authorize
    public String index() {
        return "admin/index";
    }

}

