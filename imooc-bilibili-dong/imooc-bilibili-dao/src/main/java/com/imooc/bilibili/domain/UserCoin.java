package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.After;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCoin {

    private Long id;

    private Long userId;

    private Long amount;

    private Date createTime;

    private Date updateTime;

}
