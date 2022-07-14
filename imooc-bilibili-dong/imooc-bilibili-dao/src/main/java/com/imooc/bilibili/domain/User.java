package com.imooc.bilibili.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class User {

    private Long id;

    private Long phone;

    private String email;

    private String password;

    private String salt;

    private Date createTime;

    private Date updateTime;
    @TableField(exist = false)
    private UserInfo userInfo;


}
