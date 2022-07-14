package com.imooc.bilibili.domain.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户角色关联表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user_role")
public class UserRole {

    private Long id;

    private Long userId;

    private Long roleId;

    private String roleName;

    private String roleCode;

    private Date createTime;


}
