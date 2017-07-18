package com.zxwl.web.service.impl.profile;

import com.zxwl.web.bean.po.profile.UserProfile;
import com.zxwl.web.dao.profile.UserProfileMapper;
import com.zxwl.web.service.impl.AbstractServiceImpl;
import com.zxwl.web.service.profile.UserProfileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.zxwl.web.bean.po.profile.UserProfile.Property.*;

@Service("userProfileService")
public class UserProfileServiceImpl extends AbstractServiceImpl<UserProfile, String> implements UserProfileService {

    @Resource
    private UserProfileMapper userProfileMapper;

    @Override
    protected UserProfileMapper getMapper() {
        return userProfileMapper;
    }

    @Override
    public int saveOrUpdate(UserProfile userProfile) {
        UserProfile old = selectByUserIdAndType(userProfile.getUserId(), userProfile.getType());
        if (null != old) {
            return createUpdate(userProfile)
                    .includes(content)
                    .fromBean()
                    .where(userId)
                    .and(type)
                    .exec();
        } else {
            insert(userProfile);
        }
        return 1;
    }

}
