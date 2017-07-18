/*
 * Copyright 2015-2016 http://zxwl.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zxwl.web.mybatis;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * mybatis配置,继承官方配置类,增加一些属性以拓展更多功能
 * <ul>
 * <li>是否启用动态数据源{@link this#dynamicDatasource}</li>
 * <li>可设置不加载的配置{@link this#mapperLocationExcludes}</li>
 * </ul>
 *
 * @author zhouhao
 * @see org.mybatis.spring.boot.autoconfigure.MybatisProperties
 * @since 2.1
 */
public class MybatisProperties extends org.mybatis.spring.boot.autoconfigure.MybatisProperties {
    /**
     * 默认支持的zxwl mapper
     */
    private static final String   defaultMapperLocation  = "classpath*:com/zxwl/web/dao/impl/mybatis/mapper/**/*.xml";
    /**
     * 是否启用动态数据源
     * 启用后调用{@link com.zxwl.web.core.datasource.DynamicDataSource#use(String)},mybatis也会进行数据源切换
     *
     * @see com.zxwl.web.core.datasource.DynamicDataSource
     */
    private              boolean  dynamicDatasource      = false;
    /**
     * 排除加载的mapper.xml
     * 想自定义mapper并覆盖原始mapper的场景下，通过设置此属性来排除配置文件。
     * 排除使用{@link Resource#getURL()#toString()}进行对比
     */
    private              String[] mapperLocationExcludes = null;

    public String[] getMapperLocationExcludes() {
        return mapperLocationExcludes;
    }

    public void setMapperLocationExcludes(String[] mapperLocationExcludes) {
        this.mapperLocationExcludes = mapperLocationExcludes;
    }

    public boolean isDynamicDatasource() {
        return dynamicDatasource;
    }

    public void setDynamicDatasource(boolean dynamicDatasource) {
        this.dynamicDatasource = dynamicDatasource;
    }

    public Resource[] resolveMapperLocations() {
        Map<String, Resource> resources = new HashMap<>();
        Set<String> locations;

        if (this.getMapperLocations() == null)
            locations = new HashSet<>();
        else
            locations = Arrays.stream(getMapperLocations()).collect(Collectors.toSet());
        locations.add(defaultMapperLocation);
        for (String mapperLocation : locations) {
            Resource[] mappers;
            try {
                mappers = new PathMatchingResourcePatternResolver().getResources(mapperLocation);
                for (Resource mapper : mappers) {
                    resources.put(mapper.getURL().toString(), mapper);
                }
            } catch (IOException e) {
            }
        }
        //排除不需要的配置
        if (mapperLocationExcludes != null && mapperLocationExcludes.length > 0) {
            for (String mapperLocationExclude : mapperLocationExcludes) {
                try {
                    Resource[] excludesMappers = new PathMatchingResourcePatternResolver().getResources(mapperLocationExclude);
                    for (Resource excludesMapper : excludesMappers) {
                        resources.remove(excludesMapper.getURL().toString());
                    }
                } catch (IOException e) {
                }
            }
        }
        Resource[] mapperLocations = new Resource[resources.size()];
        mapperLocations = resources.values().toArray(mapperLocations);
        return mapperLocations;
    }

}
