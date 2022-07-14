package com.imooc.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.bilibili.domain.*;
import org.apache.mahout.cf.taste.common.TasteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 视频投稿记录表 服务类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
public interface ITVideoService extends IService<TVideo> {

    void addVideos(TVideo tVideo);

    PageResult<TVideo> pageListVideos(Integer size, Integer no, String area);

    void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String url) throws Exception;

    void addVideoLike(Long videoId, Long userId);

    void deleteVideoLike(Long videoId, Long userId);

    Map<String, Object> getVideoLikes(Long videoId, Long userId);

    void addVideoCollection(TVideoCollection videoCollection, Long userId);

    void deleteVideoCollection(Long videoId, Long userId);

    Map<String, Object> getVideoCollections(Long videoId, Long userId);

    void addVideoCoins(TVideoCoin videoCoin, Long userId);

    Map<String, Object> getVideoCoins(Long videoId, Long userId);

    void addVideoComment(VideoComment videoComment, Long userId);

    PageResult<VideoComment> pageListVideoComments(Integer size, Integer no, Long videoId);

    Map<String, Object> getVideoDetails(Long videoId);

    void addVideoView(TVideoView videoView, HttpServletRequest request);

    Integer getVideoViewCounts(Long videoId);

    List<TVideo> recommend(Long userId) throws TasteException;

}
