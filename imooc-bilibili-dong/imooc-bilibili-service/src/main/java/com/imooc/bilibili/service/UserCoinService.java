package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.UserCoin;

public interface UserCoinService extends IService<UserCoin> {
    UserCoin getAmountByUserId(Long userId);

}
