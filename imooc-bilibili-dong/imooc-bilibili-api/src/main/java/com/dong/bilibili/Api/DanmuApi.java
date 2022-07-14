package com.dong.bilibili.Api;




import com.dong.bilibili.Api.support.UserSupport;
import com.imooc.bilibili.domain.JsonResponse;
import com.imooc.bilibili.domain.TDanmu;
import com.imooc.bilibili.service.ITDanmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Lazy
public class DanmuApi {

    @Autowired
    private ITDanmuService danmuService;

    @Autowired
    private UserSupport userSupport;

    @GetMapping("/danmus")
    public JsonResponse<List<TDanmu>> getDanmus(@RequestParam Long videoId,
                                                String startTime,
                                                String endTime) throws Exception {
        List<TDanmu> list;
        try{
            //判断当前是游客模式还是用户登录模式
            userSupport.getCurrentUserId();
            //若是用户登录模式，则允许用户进行时间段筛选
            list = danmuService.getDanmus(videoId, startTime, endTime);
        }catch (Exception ignored){
            //若为游客模式，则不允许用户进行时间段筛选
            list = danmuService.getDanmus(videoId, null, null);
        }
        return new JsonResponse<>(list);
    }

}
