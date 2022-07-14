package com.imooc.bilibili.domain.auth;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 权限控制--角色与元素操作关联表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_auth_role_element_operation")
public class AuthRoleElementOperation {

    private Long id;

    private Long roleId;

    private Long elementOperationId;

    private Date createTime;
    @TableField(exist = false)
    private AuthElementOperation authElementOperation;


}
