package com.imooc.bilibili.domain.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 权限控制-页面访问表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_auth_menu")
public class AuthMenu {

    private Long id;

    private String name;

    private String code;

    private Date createTime;

    private Date updateTime;


}
