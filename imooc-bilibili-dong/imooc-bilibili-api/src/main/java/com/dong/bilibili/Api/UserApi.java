package com.dong.bilibili.Api;

import com.alibaba.fastjson.JSONObject;
import com.dong.bilibili.Api.support.UserSupport;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.PageResult;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.domain.UserInfo;
import com.imooc.bilibili.service.UserInfoService;
import com.imooc.bilibili.service.UserService;
import com.imooc.bilibili.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@Lazy
public class UserApi {
        @Autowired
        private UserService userService;
        @Autowired
        private UserSupport     userSupport;
        @Autowired
        private UserInfoService userInfoService;
        @PutMapping("/users")
        public JsonResponse<User> getUserInfo(){
                Long userId = userSupport.getCurrentUserId();
                User user=userService.getUserInfo(userId);
                return new JsonResponse<>(user);
        }
        @GetMapping("/rsa-pks")
        public JsonResponse<String> getRsaPulicKey(){
                String pk = RSAUtil.getPublicKeyStr();
                return  new JsonResponse<>(pk);
        }

        /**\
         * 插入新用户
         * @param user
         * @return
         */
        @PostMapping("/users")
        public JsonResponse<String> addUsers(@RequestBody  User user){
                 userService.addUsers(user);
                return JsonResponse.success();
        }

        /**
         * 登录
         * @param user
         * @return
         */
        @PostMapping("/user-tokens")
        public JsonResponse<String> login(@RequestBody User user) throws Exception{
                String tokens=userService.login(user);
                return JsonResponse.success(tokens);
        }

        /**
         * 更新用户详细信息
         * @param userInfo
         * @return
         */
        @PutMapping("/user-infos")
        public JsonResponse<String> pageListUserInfos(@RequestBody UserInfo userInfo ){
                Long userId = userSupport.getCurrentUserId();
                userInfo.setUserId(userId);
                userService.updateUserInfo(userInfo);
                return JsonResponse.success();
        }
        @GetMapping("/user-infos")
        public JsonResponse<PageResult<UserInfo>> pageListUserInfos(@RequestParam Integer no, @RequestParam Integer size, String nick){
                Long userId = userSupport.getCurrentUserId();
                JSONObject params = new JSONObject();
                params.put("no", no);
                params.put("size", size);
                params.put("nick", nick);
                params.put("userId", userId);
                PageResult<UserInfo> result = userService.pageListUserInfos(params);
                if(result.getTotal() > 0){
                        List<UserInfo> checkedUserInfoList = userInfoService.checkFollowingStatus(result.getList(), userId);
                        result.setList(checkedUserInfoList);
                }
                return new JsonResponse<>(result);
        }
        @PostMapping("/user-dts")
        public JsonResponse<Map<String, Object>> loginForDts(@RequestBody User user) throws Exception {
                Map<String, Object> map = userService.loginForDts(user);
                return new JsonResponse<>(map);
        }

        @DeleteMapping("/refresh-tokens")
        public JsonResponse<String> logout(HttpServletRequest request){
                String refreshToken = request.getHeader("refreshToken");
                Long userId = userSupport.getCurrentUserId();
                userService.logout(refreshToken, userId);
                return JsonResponse.success();
        }

        @PostMapping("/access-tokens")
        public JsonResponse<String> refreshAccessToken(HttpServletRequest request) throws Exception {
                String refreshToken = request.getHeader("refreshToken");
                String accessToken = userService.refreshAccessToken(refreshToken);
                return new JsonResponse<>(accessToken);
        }
}
