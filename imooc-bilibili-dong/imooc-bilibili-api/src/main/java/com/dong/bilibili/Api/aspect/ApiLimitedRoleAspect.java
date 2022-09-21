package com.dong.bilibili.Api.aspect;


import com.dong.bilibili.Api.support.UserSupport;
import com.imooc.bilibili.domain.annotation.ApiLimitedRole;
import com.imooc.bilibili.domain.auth.UserRole;
import com.imooc.bilibili.domain.exception.ConditionException;
import com.imooc.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//优先级
@Order(1)
@Component
//标注切面
@Aspect
public class ApiLimitedRoleAspect {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    //切点  按照注解进行
    @Pointcut("@annotation(com.imooc.bilibili.domain.annotation.ApiLimitedRole)")
    public void check(){
    }

    //在方法执行前
    @Before("check() && @annotation(apiLimitedRole)")
    public void doBefore(JoinPoint joinPoint, ApiLimitedRole apiLimitedRole){
        Long userId = userSupport.getCurrentUserId();
        //通过id查出等级
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        //传进来的是0 所以只要有交集 就说明权限不足
        String[] limitedRoleCodeList = apiLimitedRole.limitedRoleCodeList();
        //对数组进行去重
        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        //对查出来的数组进行去重
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        //取交集
        roleCodeSet.retainAll(limitedRoleCodeSet);
        if(roleCodeSet.size() > 0){
            throw new ConditionException("权限不足！");
        }
    }
}
