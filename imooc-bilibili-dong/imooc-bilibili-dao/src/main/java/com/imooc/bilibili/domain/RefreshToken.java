package com.imooc.bilibili.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_refresh_token")
public class RefreshToken {
    private Long id;

    private Long userId;

    private String refreshToken;

    private Date createTime;
}
