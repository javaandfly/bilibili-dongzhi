package com.imooc.bilibili.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.PageResult;
import com.imooc.bilibili.domain.User;
import com.imooc.bilibili.domain.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface UserService extends IService<User> {
    /**
     * 插入手机号
     * @param user
     */
    void addUsers(User user);

    /**
     * 根据phone查询
     * @param phone
     * @return
     */
    User selectByPhone(Long phone);

    /**
     * 登录
     * @param user
     * @return
     */
    String login(User user) throws Exception;

    /**
     * 根据userid获取User
     * @param userId
     * @return
     */
    User getUserInfo(Long userId);

    /**
     * 更新token
     * @param userInfo
     */
    String updateUserInfo(UserInfo userInfo);


//    PageResult<UserInfo> pageListUserInfos(JSONObject params);

//    Integer pageCountUserInfos(Map<String,Object> params);

//    List<UserInfo> pageListsUserInfos(JSONObject params);

    Map<String, Object> loginForDts(User user) throws Exception;

    void logout(String refreshToken, Long userId);

    String refreshAccessToken(String refreshToken) throws Exception;
}
