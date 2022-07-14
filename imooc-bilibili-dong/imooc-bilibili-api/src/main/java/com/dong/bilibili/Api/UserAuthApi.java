package com.dong.bilibili.Api;

import com.dong.bilibili.Api.support.UserSupport;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.auth.UserAuthorities;
import com.imooc.bilibili.service.UserAuthService;
import com.imooc.bilibili.service.impl.UserAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Lazy
public class UserAuthApi {
    @Autowired
    private UserSupport     userSupport;
    @Autowired
    private UserAuthService userAuthService;
    @GetMapping("/user-authorities")
    public JsonResponse<UserAuthorities> getUserAuthorities(){
        Long userId = userSupport.getCurrentUserId();
       UserAuthorities userAuthorities= userAuthService.getUserAuthorities(userId);
       return new JsonResponse<>(userAuthorities);
    }
}
