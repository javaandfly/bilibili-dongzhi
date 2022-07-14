package com.imooc.bilibili.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 粉丝表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_user_following")
public class UserFollowing {

    private Long id;

    private Long userId;

    private Long followingId;

    private Long groupId;

    private Date createTime;
    @TableField(exist = false)
    private UserInfo userInfo;


}
