package com.zxwl.web.core.session.redis;

import com.zxwl.web.core.session.HttpSessionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import javax.annotation.Resource;

@Configuration
@ConditionalOnBean(value = RedisOperationsSessionRepository.class, name = "sessionRedisTemplate")
@ConditionalOnWebApplication
public class RedisHttpSessionManagerConfiguration {

    @Resource(name = "sessionRedisTemplate")
    private RedisTemplate sessionRedisTemplate;

    @Bean(name = "httpSessionManager")
    public HttpSessionManager redisHttpSessionManager(RedisOperationsSessionRepository repository) {
        RedisHttpSessionManager redisHttpSessionManager = new RedisHttpSessionManager();
        redisHttpSessionManager.setSessionRedisTemplate(sessionRedisTemplate);
        redisHttpSessionManager.setRedisOperationsSessionRepository(repository);
        return redisHttpSessionManager;
    }
}
