package com.imooc.bilibili.domain.auth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 权限控制--角色页面菜单关联表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_auth_role_menu")
public class AuthRoleMenu {

    private Long id;

    private Long roleId;

    private Long menuId;

    private Date createTime;
    @TableField(exist = false)
    private AuthMenu authMenu;


}
