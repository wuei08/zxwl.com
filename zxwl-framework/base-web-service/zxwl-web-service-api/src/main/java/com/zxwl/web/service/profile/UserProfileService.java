package com.zxwl.web.service.profile;

import com.zxwl.web.bean.common.QueryParam;
import com.zxwl.web.bean.po.profile.UserProfile;
import com.zxwl.web.service.GenericService;

/**
 * Created by zhouhao on 16-7-4.
 */
public interface UserProfileService extends GenericService<UserProfile, String> {

    default UserProfile selectByUserIdAndType(String userId, String type) {
        return selectSingle(QueryParam.build().where("userId", userId).and("type", type));
    }
}
