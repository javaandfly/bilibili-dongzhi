package com.dong.bilibili.Api;

import com.dong.bilibili.Api.support.UserSupport;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.UserMoment;
import com.imooc.bilibili.domain.annotation.ApiLimitedRole;
import com.imooc.bilibili.domain.annotation.DataLimited;
import com.imooc.bilibili.domain.constant.AuthRoleConstant;
import com.imooc.bilibili.service.UserMomentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Lazy
public class UserMomentsApi {
    @Autowired
    private UserMomentsService userMomentsService;
    @Autowired
    private UserSupport userSupport;


    /**
     * 用来保存用户的动态 0:视频 1:直播 2：专栏动态 并用RabbitMQ发送
     * @param userMoment
     * @return
     */
    //自定义注解 来判断用户的等级
    @ApiLimitedRole(limitedRoleCodeList = {AuthRoleConstant.ROLE_LV0})
    @DataLimited
    @PostMapping("user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoment userMoment){
        Long userId = userSupport.getCurrentUserId();
        userMoment.setUserId(userId);
        userMomentsService.addUserMoments(userMoment);
        return JsonResponse.success();
    }

    /**
     * 用户点击关注后 从消息队列中拿数据
     * @return
     */
    @GetMapping("user-subscribed-moments")
    public JsonResponse<List<UserMoment>> getUserSubscribedMoments(){
        Long userId = userSupport.getCurrentUserId();
        //返回的是list数组
      return new JsonResponse<>(userMomentsService.getUserSubscribedMoments(userId));
    }
}
