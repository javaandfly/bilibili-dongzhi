package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.TDanmu;

import java.util.List;


/**
 * <p>
 * 弹幕记录表 服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
public interface ITDanmuService extends IService<TDanmu> {

    void addDanmu(TDanmu danmu);

    void asyncAddDanmu(TDanmu danmu);

    List<TDanmu> getDanmus(Long videoId, String startTime, String endTime) throws Exception;


    void addDanmusToRedis(TDanmu danmu);


}
