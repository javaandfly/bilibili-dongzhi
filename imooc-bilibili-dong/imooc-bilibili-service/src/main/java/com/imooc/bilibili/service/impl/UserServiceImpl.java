package com.imooc.bilibili.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.imooc.bilibili.domain.*;

import com.imooc.bilibili.domain.constant.UserConstant;
import com.imooc.bilibili.domain.exception.ConditionException;
import com.imooc.bilibili.mapper.UserMapper;
import com.imooc.bilibili.service.RefreshTokenService;
import com.imooc.bilibili.service.UserAuthService;
import com.imooc.bilibili.service.UserInfoService;
import com.imooc.bilibili.service.UserService;
import com.imooc.bilibili.util.MD5Util;
import com.imooc.bilibili.util.RSAUtil;
import com.imooc.bilibili.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
   private  UserMapper      userMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserAuthService     userAuthService;
    @Autowired
    private RefreshTokenService refreshTokenService;
     @Override
    public void addUsers(User user) {
        Long phone = user.getPhone();
        if (phone==null){
            throw new ConditionException("手机号为空");
        }
        User user01 = this.selectByPhone(phone);
        if (user01!=null){
            throw new ConditionException("此用户已经存在");
        }
        String password = user.getPassword();
        Date now = new Date();
        String salt=String.valueOf(now.getTime());
        String rawPassword;
        try {
            rawPassword= RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        String sign = MD5Util.sign(rawPassword, salt, "UTF-8");
        user.setId(phone);
        user.setSalt(salt);
        user.setPassword(sign);
        user.setCreateTime(now);
        userMapper.insert(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setCreateTime(now);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfoService.save(userInfo);
        //添加用户默认权限控制角色
         userAuthService.addUserDefaultRole(user.getId());
    }

    @Override
    public User selectByPhone(Long phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        return this.getOne(queryWrapper);
    }

    @Override
    public String login(User user) throws Exception {
        Long phone = user.getPhone();
        if (phone==null){
            throw new ConditionException("手机号为空");
        }
        User user1 = selectByPhone(phone);
        if (user1==null){
            throw new ConditionException("用户不存在");
        }
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword= RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        String salt = user1.getSalt();
        String sign = MD5Util.sign(rawPassword, salt, "UTF-8");
        String password1 = user1.getPassword();
        if (!sign.equals(password1)){
            throw new ConditionException("密码错误");
        }
        return TokenUtil.generateToken(user1.getId());
    }

    @Override
    public User getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userId);
        user.setUserInfo(userInfo);
        return user;
    }

    @Override
    public String updateUserInfo(UserInfo userInfo) {
        UserInfo userInfoByUserId = userInfoService.getUserInfoByUserId(userInfo.getUserId());
        String nick = userInfo.getNick();
        if (nick!=null && !nick.equals("")){
            userInfoByUserId.setNick(nick);
        }
        String avatar = userInfo.getAvatar();
        if (avatar!=null){
            userInfoByUserId.setAvatar(avatar);
        }
        String sign = userInfo.getSign();
        if (sign!=null){
            userInfoByUserId.setSign(sign);
        }
        String birth = userInfo.getBirth();
        if (birth!=null && !birth.equals("")){
            userInfoByUserId.setBirth(birth);
        }
        String gender = userInfo.getGender();
        if (gender!=null && gender.equals("")){
            userInfoByUserId.setGender(gender);
        }
        userInfoByUserId.setUpdateTime(new Date());
        userInfoService.updateById(userInfoByUserId);
        return "修改成功";
    }
//    @Override
//    public PageResult<UserInfo> pageListUserInfos(JSONObject params) {
//        Integer no = params.getInteger("no");
//        Integer size = params.getInteger("size");
//        params.put("start", (no-1)*size);
//        params.put("limit", size);
//        Integer total = this.pageCountUserInfos(params);
//        List<UserInfo> list = new ArrayList<>();
//        if(total > 0){
//            list = this.pageListsUserInfos(params);
//        }
//        return new PageResult<>(total,list);
//        return null;
//    }


//
//    @Override
//    public List<UserInfo> pageListsUserInfos(JSONObject params) {
//        return userMapper.pageListUserInfo(params);
//    }

    @Override
    public Map<String, Object> loginForDts(User user) throws Exception {
        Long phone = user.getPhone();
        if (phone==null){
            throw new ConditionException("手机号为空");
        }
        User user1 = selectByPhone(phone);
        if (user1==null){
            throw new ConditionException("用户不存在");
        }
        String password = user.getPassword();
        String rawPassword;
        try {
            rawPassword= RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }
        String salt = user1.getSalt();
        String sign = MD5Util.sign(rawPassword, salt, "UTF-8");
        String password1 = user1.getPassword();
        if (!sign.equals(password1)){
            throw new ConditionException("密码错误");
        }
        Long user1Id = user1.getId();
        String token = TokenUtil.generateToken(user1Id);
        String refreshToken = TokenUtil.generateRefreshToken(user1Id);
        RefreshToken refreshToken1 = new RefreshToken();
        refreshToken1.setRefreshToken(refreshToken);
        refreshToken1.setUserId(user1Id);
        refreshToken1.setCreateTime(new Date());
        refreshTokenService.deleteRefreshTokenByUserId(user1Id);
        refreshTokenService.save(refreshToken1);
        Map<String,Object> map=new HashMap<>();
        map.put("accessToken",token);
        map.put("reFreshToken",refreshToken);


        return map;
    }

    @Override
    public void logout(String refreshToken, Long userId) {
        refreshTokenService.deleteRefreshTokenByUserId(userId);
    }

    @Override
    public String refreshAccessToken(String refreshToken) throws Exception {
        RefreshToken refreshTokens =   refreshTokenService.selectByRefreshToken(refreshToken);
        if (refreshTokens==null){
            throw new ConditionException("555","token已过期!");
        }
        Long userId = refreshTokens.getUserId();
        return TokenUtil.generateToken(userId);
    }


}
