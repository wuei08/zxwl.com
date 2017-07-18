package com.zxwl.web.controller.profile;

import com.zxwl.web.bean.po.profile.UserProfile;
import com.zxwl.web.bean.po.user.User;
import com.zxwl.web.core.authorize.annotation.Authorize;
import com.zxwl.web.core.exception.NotFoundException;
import com.zxwl.web.core.logger.annotation.AccessLogger;
import com.zxwl.web.core.message.ResponseMessage;
import com.zxwl.web.core.utils.WebUtil;
import com.zxwl.web.service.profile.UserProfileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user-profile")
@AccessLogger("用户配置")
@Authorize
public class UserProfileController {

    @Resource
    private UserProfileService userProfileService;

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public ResponseMessage getProfile(@PathVariable String type) {
        User user = WebUtil.getLoginUser();
        UserProfile userProfile = userProfileService.selectByUserIdAndType(user.getId(), type);
        if (null == userProfile) throw new NotFoundException("配置不存在");
        return ResponseMessage.ok(userProfile);
    }

    @RequestMapping(value = "/{type}", method = RequestMethod.PATCH)
    public ResponseMessage createOrUpdateProfile(@PathVariable String type,
                                                 @RequestBody(required = true) String content) {
        User user = WebUtil.getLoginUser();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(user.getId());
        userProfile.setType(type);
        userProfile.setContent(content);
        return ResponseMessage.ok(userProfileService.saveOrUpdate(userProfile));
    }


}
