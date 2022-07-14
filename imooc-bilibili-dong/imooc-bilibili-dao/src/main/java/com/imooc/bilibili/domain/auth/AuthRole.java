package com.imooc.bilibili.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 权限控制--角色表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRole {

    private Long id;

    private String name;

    private String code;

    private Date createTime;

    private Date updateTime;


}
