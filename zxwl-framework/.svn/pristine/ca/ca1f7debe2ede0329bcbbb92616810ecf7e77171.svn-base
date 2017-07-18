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

package com.zxwl.web.service.quartz;


import com.zxwl.web.bean.common.PagerResult;
import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.po.quartz.QuartzJobHistory;

import java.util.List;

/**
 * 定时调度任务执行记录服务类
 * Created by generator
 */
public interface QuartzJobHistoryService {

    PagerResult<QuartzJobHistory> selectPager(QueryParam param);

    List<QuartzJobHistory> select(QueryParam param);

    int total(QueryParam param);

    String createAndInsertHistory(String jobId);

    boolean endHistory(String historyId, String result, QuartzJobHistory.Status status);
}
