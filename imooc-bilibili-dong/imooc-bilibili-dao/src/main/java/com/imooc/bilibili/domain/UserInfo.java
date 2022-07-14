package com.imooc.bilibili.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 用户基本信息表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user_info")
@Document(indexName = "user-info")
public class UserInfo {
    @Id
    private Long id;

    private Long userId;

    @Field(type = FieldType.Text)
    private String nick;

    private String avatar;

    private String sign;

    private String gender;

    private String birth;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Date)
    private Date updateTime;

    @TableField(exist = false)
    private Boolean followed;
}
