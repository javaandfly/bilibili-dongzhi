package com.dong.bilibili.Api;



import com.dong.bilibili.Api.support.UserSupport;
import com.imooc.bilibili.domain.*;
import com.imooc.bilibili.service.ElasticSearchService;
import com.imooc.bilibili.service.ITVideoService;
import com.imooc.bilibili.service.impl.TVideoServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@RestController
@Lazy
public class VideoApi {

    @Autowired
    private ITVideoService tVideoService;

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private ElasticSearchService elasticSearchService;
    /**
     * 添加视频
     * @param tVideo
     * @return
     */
    @PostMapping("/videos")
    public JsonResponse<String> addVideos(@RequestBody TVideo tVideo){
        Long userId = userSupport.getCurrentUserId();
        tVideo.setUserId(userId);
        tVideoService.addVideos(tVideo);
        elasticSearchService.addVideo(tVideo);
        return JsonResponse.success();
    }

    /**
     * 查询视频分页查询
     * @param size
     * @param no
     * @param area
     * @return
     */
    @GetMapping("/videos")
    public JsonResponse<PageResult<TVideo>> pageListVideos(@Param("size") Integer size,@Param("no") Integer no,@Param("area") String area){
        PageResult<TVideo> result=tVideoService.pageListVideos(size,no,area);
            return new JsonResponse<>(result);
    }

    /**
     * 在线观看视频
     * @param request
     * @param response
     * @param url
     * @throws Exception
     */
    @GetMapping("/video-slices")
    public void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String url) throws Exception{
        tVideoService.viewVideoOnlineBySlices(request,response,url);
    }

    /**
     * 点赞视频
     */
    @PostMapping("/video-likes")
    public JsonResponse<String> addVideoLike(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        tVideoService.addVideoLike(videoId, userId);
        return JsonResponse.success();
    }

    /**
     * 取消点赞视频
     */
    @DeleteMapping("/video-likes")
    public JsonResponse<String> deleteVideoLike(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        tVideoService.deleteVideoLike(videoId, userId);
        return JsonResponse.success();
    }

    /**
     * 查询视频点赞数量
     */
    @GetMapping("/video-likes")
    public JsonResponse<Map<String, Object>> getVideoLikes(@RequestParam Long videoId){
        Long userId = null;
        //当为游客模式的时候 就把异常抓起来 视频还是可以看的
        try{
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String, Object> result = tVideoService.getVideoLikes(videoId, userId);
        return new JsonResponse<>(result);
    }

    /**
     * 收藏视频
     */
    @PostMapping("/video-collections")
    public JsonResponse<String> addVideoCollection(@RequestBody TVideoCollection videoCollection){
        Long userId = userSupport.getCurrentUserId();
        tVideoService.addVideoCollection(videoCollection, userId);
        return JsonResponse.success();
    }

    /**
     * 取消收藏视频
     */
    @DeleteMapping("/video-collections")
    public JsonResponse<String> deleteVideoCollection(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        tVideoService.deleteVideoCollection(videoId, userId);
        return JsonResponse.success();
    }

    /**
     * 查询视频收藏数量
     */
    @GetMapping("/video-collections")
    public JsonResponse<Map<String, Object>> getVideoCollections(@RequestParam Long videoId){
        Long userId = null;
        try{
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String, Object> result = tVideoService.getVideoCollections(videoId, userId);
        return new JsonResponse<>(result);
    }

    /**
     * 视频投币
     */
    @PostMapping("/video-coins")
    public JsonResponse<String> addVideoCoins(@RequestBody TVideoCoin videoCoin){
        Long userId = userSupport.getCurrentUserId();
        tVideoService.addVideoCoins(videoCoin, userId);
        return JsonResponse.success();
    }

    /**
     * 查询视频投币数量
     */
    @GetMapping("/video-coins")
    public JsonResponse<Map<String, Object>> getVideoCoins(@RequestParam Long videoId){
        Long userId = null;
        try{
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){}
        Map<String, Object> result = tVideoService.getVideoCoins(videoId, userId);
        return new JsonResponse<>(result);
    }

    /**
     * 添加视频评论
     */
    @PostMapping("/video-comments")
    public JsonResponse<String> addVideoComment(@RequestBody VideoComment videoComment){
        Long userId = userSupport.getCurrentUserId();
        tVideoService.addVideoComment(videoComment, userId);
        return JsonResponse.success();
    }

    /**
     * 分页查询视频评论
     */
    @GetMapping("/video-comments")
    public JsonResponse<PageResult<VideoComment>> pageListVideoComments(@RequestParam Integer size,
                                                                        @RequestParam Integer no,
                                                                        @RequestParam Long videoId){
        PageResult<VideoComment> result = tVideoService.pageListVideoComments(size, no, videoId);
        return new JsonResponse<>(result);
    }
    /**
     * 获取视频详情
     */
    @GetMapping("/video-details")
    public JsonResponse<Map<String, Object>> getVideoDetails(@RequestParam Long videoId){
        Map<String, Object> result = tVideoService.getVideoDetails(videoId);
        return new JsonResponse<>(result);
    }

    /**
     * 添加视频观看记录
     */
    @PostMapping("/video-views")
    public JsonResponse<String> addVideoView(@RequestBody TVideoView videoView,
                                             HttpServletRequest request){
        Long userId;
        try{
            userId = userSupport.getCurrentUserId();
            videoView.setUserId(userId);
            tVideoService.addVideoView(videoView, request);
        }catch (Exception e){
            tVideoService.addVideoView(videoView, request);
        }
        return JsonResponse.success();
    }

    /**
     * 查询视频播放量
     */
    @GetMapping("/video-view-counts")
    public JsonResponse<Integer> getVideoViewCounts(@RequestParam Long videoId){
        Integer count = tVideoService.getVideoViewCounts(videoId);
        return new JsonResponse<>(count);
    }

    /**
     * 视频内容推荐
     */
    @GetMapping("/recommendations")
    public JsonResponse<List<TVideo>> recommend() throws TasteException {
        Long userId = userSupport.getCurrentUserId();
        List<TVideo> list = tVideoService.recommend(userId);
        return new JsonResponse<>(list);
    }



}
