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

package com.zxwl.web.service.impl.user;

import com.zxwl.web.bean.common.DeleteParam;
import com.zxwl.web.bean.common.InsertParam;
import com.zxwl.web.bean.common.UpdateParam;
import com.zxwl.web.bean.po.user.User;
import com.zxwl.web.dao.role.UserRoleMapper;
import com.zxwl.web.dao.user.UserMapper;
import com.zxwl.web.service.module.ModuleService;
import com.zxwl.web.service.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * @author zhouhao
 */
public class UserServiceImplTest {

    @InjectMocks
    UserService userService = new UserServiceImpl();
    @Mock
    UserMapper                 userMapper;
    @Mock
    ModuleService              moduleService;
    @Mock
    UserRoleMapper             userRoleMapper;
    @Mock
    javax.validation.Validator validator;

    InsertParam<User> insertParam;

    @Before
    public void setup() {
        User user = new User();
        insertParam = new InsertParam<>(user);
        user.setUsername("admin");
        MockitoAnnotations.initMocks(this);
        when(userMapper.insert(insertParam)).thenReturn(1);
        when(userMapper.update(UpdateParam.build(new User()))).thenReturn(1);
        when(userMapper.delete(DeleteParam.build().where("id", "test"))).thenReturn(1);
    }

    @Test
    public void insert() throws Exception {
        Assert.assertNotNull(userService.insert(new User()));
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(userService.update(new User()), 1);
    }

}