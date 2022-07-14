package com.imooc.bilibili.domain.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 权限控制--页面元素操作表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_auth_element_operation")
public class AuthElementOperation {

    private Long id;

    private String elementName;

    private String elementCode;

    private String operationType;

    private Date createTime;

    private Date updateTime;


}
