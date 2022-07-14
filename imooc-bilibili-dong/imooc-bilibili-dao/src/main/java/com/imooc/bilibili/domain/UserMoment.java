package com.imooc.bilibili.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 关注实时推送，用户动态表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user_moments")
public class UserMoment {

    private Long id;

    private Long userId;

    private String type;

    private Long contentId;

    private Date createTime;

    private Date updateTime;


}
