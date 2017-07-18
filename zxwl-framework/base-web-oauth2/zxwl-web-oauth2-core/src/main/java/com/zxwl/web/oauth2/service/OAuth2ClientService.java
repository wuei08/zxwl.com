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

package com.zxwl.web.oauth2.service;

import com.zxwl.web.bean.common.PagerResult;
import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.oauth2.po.OAuth2Access;
import com.zxwl.web.oauth2.po.OAuth2Client;
import com.zxwl.web.service.GenericService;

/**
 * OAuth2客户端服务类
 * Created by generator
 */
public interface OAuth2ClientService extends GenericService<OAuth2Client, String> {

    PagerResult<OAuth2Access> selectAccessList(QueryParam param);

    int deleteAccess(String accessId);

    String refreshSecret(String clientId);

    void enable(String id);

    void disable(String id);

}
