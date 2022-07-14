package com.dong.bilibili.Api;

import com.dong.bilibili.Api.support.UserSupport;
import com.imooc.bilibili.domain.FollowingGroup;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.UserFollowing;
import com.imooc.bilibili.service.UserFollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Lazy
public class UserFollowingApi {

    @Autowired
    private UserFollowingService userFollowingService;
    @Autowired
    private UserSupport userSupport;

    /**
     * 添加用户
     * @param userFollowing
     * @return
     */
    @PostMapping("user-followings")
    public JsonResponse<String> addUserFollowings(@RequestBody UserFollowing userFollowing){
        Long userId = userSupport.getCurrentUserId();
        userFollowing.setUserId(userId);
        userFollowingService.addUserFollowings(userFollowing);
        return JsonResponse.success();
    }

    /**
     * 查询用户关注列表
     * @return
     */
    @GetMapping("user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowing(){
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> userFollowings = userFollowingService.getUserFollowings(userId);
        return new JsonResponse<>(userFollowings);
    }

    /**
     * 获取粉丝信息
     * @return
     */
    @GetMapping("user-fan")
    public JsonResponse<List<UserFollowing>> getUserFan(){
        Long userId = userSupport.getCurrentUserId();
        List<UserFollowing> userFan = userFollowingService.getUserFan(userId);
        return new JsonResponse<>(userFan);
    }

    @PostMapping("/user-following-groups")
    public JsonResponse<Long> addUserFollowingGroups(@RequestBody FollowingGroup followingGroup){
        Long userId = userSupport.getCurrentUserId();
        followingGroup.setUserId(userId);
        Long groupId = userFollowingService.addUserFollowingGroups(followingGroup);
        return new JsonResponse<>(groupId);
    }

}
