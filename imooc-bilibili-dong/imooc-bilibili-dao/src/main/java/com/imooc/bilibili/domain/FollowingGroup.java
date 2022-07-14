package com.imooc.bilibili.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 用户关注分组表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_following_group")
public class FollowingGroup {

    private Long id;

    private Long userId;

    private String name;

    private String type;

    private Date createTime;

    private Date updateTime;
    @TableField(exist = false)
    private List<UserInfo> followingUserInfoList;


}
