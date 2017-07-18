package com.zxwl.web.service.impl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zhouhao on 16-4-20.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.zxwl.web"})
@EnableTransactionManagement(proxyTargetClass = true)
//@MapperScan("com.zxwl.web.dao")
public class SpringApplication {

}
