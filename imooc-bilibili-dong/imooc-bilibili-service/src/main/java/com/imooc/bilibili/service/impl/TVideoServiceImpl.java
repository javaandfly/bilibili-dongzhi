package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.*;
import com.imooc.bilibili.domain.exception.ConditionException;
import com.imooc.bilibili.mapper.TVideoMapper;
import com.imooc.bilibili.service.*;
import com.imooc.bilibili.util.FastDFSUtil;
import com.imooc.bilibili.util.IpUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频投稿记录表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Service
public class TVideoServiceImpl extends ServiceImpl<TVideoMapper, TVideo> implements ITVideoService {
    @Autowired
    private TVideoMapper tVideoMapper;
    @Autowired
    private ITVideoTagService itVideoTagService;
    @Autowired
    private FastDFSUtil fastDFSUtil;
    @Autowired
    private ITVideoLikeService itVideoLikeService;
    @Autowired
    private ITVideoCollectionService itVideoCollectionService;
    @Autowired
    private ITVideoCoinService itVideoCoinService;
    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private VideoCommentService videoCommentService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITVideoViewService itVideoViewService;
    @Autowired
    private ITVideoOperationService itVideoOperationService;

    @Override
    @Transactional
    public void addVideos(TVideo tVideo) {
        tVideo.setCreateTime(new Date());
        this.save(tVideo);
        Long videoId = tVideo.getId();
        List<TVideoTag> list=tVideo.getVideoTagList();
        list.forEach(item->{
            item.setVideoId(videoId);
            item.setCreateTime(new Date());
            itVideoTagService.save(item);
        });
    }

    @Override
    public PageResult<TVideo> pageListVideos(Integer size, Integer no, String area) {
        if (size==null || no==null){
            throw new ConditionException("参数校验异常");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("start",(no-1)*size);
        map.put("limit",size);
        map.put("area",area);
        List<TVideo> videos=new ArrayList<>();
       Integer total= tVideoMapper.pageCountVideos(map);
       if (total>0){
            videos=tVideoMapper.pageListVideos(map);
       }
        return new PageResult<>(total,videos);
    }

    @Override
    public void viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response, String url) throws Exception {
        fastDFSUtil.viewVideoOnlineBySlices(request,response,url);
    }

    @Override
    public void addVideoLike(Long videoId, Long userId) {
        TVideo video = this.getById(videoId);
        if (video==null){
            throw new ConditionException("视频不存在");
        }
       TVideoLike videoLikes= itVideoLikeService.getVideoLikeByUserId(userId,videoId);
        if (videoLikes!=null){
            throw new ConditionException("已经点赞该视频");
        }
        TVideoLike videoLike = new TVideoLike();
        videoLike.setVideoId(videoId);
        videoLike.setUserId(userId);
        videoLike.setCreateTime(new Date());
        itVideoLikeService.save(videoLike);

    }

    @Override
    public void deleteVideoLike(Long videoId, Long userId) {
        itVideoLikeService.deleteVideoLike(videoId,userId);
    }

    @Override
    public Map<String, Object> getVideoLikes(Long videoId, Long userId) {
        Integer count= itVideoLikeService.getVideoLikes(videoId);
        TVideoLike videoLikes= itVideoLikeService.getVideoLikeByUserId(userId,videoId);
        Boolean like=videoLikes != null;
        Map<String, Object> map = new HashMap<>();
        map.put("count",count);
        map.put("like",like);
        return map;
    }

    @Override
    @Transactional
    public void addVideoCollection(TVideoCollection videoCollection, Long userId) {
        Long videoId = videoCollection.getVideoId();
        Long groupId = videoCollection.getGroupId();
        if (videoId==null || groupId==null){
            throw new ConditionException("参数异常");
        }
        TVideo video = this.getById(videoId);
        if (video==null){
            throw new ConditionException("视频不存在");
        }
        //先删再添加
        itVideoCollectionService.deleteVideoCollection(userId,videoId);
        videoCollection.setCreateTime(new Date());
        videoCollection.setUserId(userId);
        itVideoCollectionService.save(videoCollection);
    }

    @Override
    public void deleteVideoCollection(Long videoId, Long userId) {
        itVideoCollectionService.deleteVideoCollection(videoId,userId);
    }

    @Override
    public Map<String, Object> getVideoCollections(Long videoId, Long userId) {
       Integer count= itVideoCollectionService.getVideoCollections(videoId);
       boolean collection=userId != null;
        Map<String, Object> map = new HashMap<>();
        map.put("count",count);
        map.put("collection",collection);
        return map;
    }

    @Override
    @Transactional
    public void addVideoCoins(TVideoCoin videoCoin, Long userId) {
        Long videoId = videoCoin.getVideoId();
        TVideo video = this.getById(videoId);
        if (videoId==null){
            throw  new ConditionException("参数异常");
        }
        if (video==null){
            throw new ConditionException("视频不存在");
        }
        Integer amount = videoCoin.getAmount();
        UserCoin userCoin=userCoinService.getAmountByUserId(userId);
        Long amounts = userCoin.getAmount();
        if (amounts<amount){
            throw new ConditionException("硬币不足");
        }
      TVideoCoin tVideoCoin=  itVideoCoinService.getVideoCoinByUserId(userId);
        if (tVideoCoin !=null){
            throw new ConditionException("投币已达上限");
        }
        userCoin.setAmount(amounts-amount);
        userCoin.setUpdateTime(new Date());
        userCoinService.updateById(userCoin);
    }

    @Override
    public Map<String, Object> getVideoCoins(Long videoId, Long userId) {
       Integer count= itVideoCoinService.getAllCoinsByVideoId(videoId);
        TVideoCoin tVideoCoin=  itVideoCoinService.getVideoCoinByUserId(userId);
       boolean coin=tVideoCoin != null;
        Map<String, Object> map = new HashMap<>();
        map.put("count",count);
        map.put("coin",coin);
        return map;
    }

    @Override
    public void addVideoComment(VideoComment videoComment, Long userId) {
        Long videoId = videoComment.getVideoId();
        if (videoId==null){
            throw new ConditionException("参数异常");
        }
        TVideo video = this.getById(videoId);
        if (video==null){
            throw new ConditionException("视频不存在");
        }
        videoComment.setUserId(userId);
        videoComment.setCreateTime(new Date());
        videoCommentService.save(videoComment);
    }

    @Override
    public PageResult<VideoComment> pageListVideoComments(Integer size, Integer no, Long videoId) {

        TVideo video = this.getById(videoId);
        if(video == null){
            throw new ConditionException("非法视频！");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("start", (no-1)*size);
        params.put("limit", size);
        params.put("videoId", videoId);
        //查询一共多少条评论 仅限于一级评论 where条件中多加了判断
        Integer total = videoCommentService.pageCountVideoComments(params);
        List<VideoComment> list = new ArrayList<>();
        if(total > 0){
            //分页 仅限于一级评论
            list = videoCommentService.pageListVideoComments(params);
            //批量查询二级评论
            List<Long> parentIdList = list.stream().map(VideoComment::getId).collect(Collectors.toList());
            List<VideoComment> childCommentList = videoCommentService.batchGetVideoCommentsByRootIds(parentIdList);
            //批量查询用户信息
            Set<Long> userIdList = list.stream().map(VideoComment::getUserId).collect(Collectors.toSet());
            Set<Long> replyUserIdList = childCommentList.stream().map(VideoComment::getUserId).collect(Collectors.toSet());
            Set<Long> childUserIdList = childCommentList.stream().map(VideoComment::getReplyUserId).collect(Collectors.toSet());
            userIdList.addAll(replyUserIdList);
            userIdList.addAll(childUserIdList);
            //根据id查询所有
            List<UserInfo> userInfoList = userInfoService.batchGetUserInfoByUserIds(userIdList);
            Map<Long, UserInfo> userInfoMap = userInfoList.stream().collect(Collectors.toMap(UserInfo :: getUserId, userInfo -> userInfo));
            list.forEach(comment -> {
                Long id = comment.getId();
                List<VideoComment> childList = new ArrayList<>();
                childCommentList.forEach(child -> {
                    if(id.equals(child.getRootId())){
                        child.setUserInfo(userInfoMap.get(child.getUserId()));
                        child.setReplyUserInfo(userInfoMap.get(child.getReplyUserId()));
                        childList.add(child);
                    }
                });
                comment.setChildList(childList);
                comment.setUserInfo(userInfoMap.get(comment.getUserId()));
            });
        }
        return new PageResult<>(total, list);
    }

    @Override
    public Map<String, Object> getVideoDetails(Long videoId) {
        TVideo video = this.getById(videoId);
        UserInfo userInfo = userInfoService.getUserInfoByUserId(video.getUserId());
        Map<String, Object> map = new HashMap<>();
        map.put("video",video);
        map.put("userInfo",userInfo);
        return map;
    }

    @Override
    public void addVideoView(TVideoView videoView, HttpServletRequest request) {
        Long userId = videoView.getUserId();
        Long videoId = videoView.getVideoId();
        //生成clientId
        String agent = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);
        String clientId = String.valueOf(userAgent.getId());
        String ip = IpUtil.getIP(request);
        Map<String, Object> params = new HashMap<>();
        if(userId != null){
            params.put("userId", userId);
        }else{
            params.put("ip", ip);
            params.put("clientId", clientId);
        }
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        params.put("today", sdf.format(now));
        params.put("videoId", videoId);
        //添加观看记录
//        TVideoView dbVideoView = videoDao.getVideoView(params);
        TVideoView dbVideoView=itVideoViewService.getVideoView(params);
        if(dbVideoView == null){
            videoView.setIp(ip);
            videoView.setClientId(clientId);
            videoView.setCreateTime(new Date());
//            videoDao.addVideoView(videoView);
            itVideoViewService.save(videoView);
        }
    }


    @Override
    public Integer getVideoViewCounts(Long videoId) {
        return itVideoViewService.getVideoViewCounts(videoId);
    }


    /**
     * 基于用户的协同推荐
     * @param userId 用户id
     */
    public List<TVideo> recommend(Long userId) throws TasteException {
        List<UserPreference> list = itVideoOperationService.getAllUserPreference();
        //创建数据模型
        DataModel dataModel = this.createDataModel(list);
        //获取用户相似程度
        UserSimilarity similarity = new UncenteredCosineSimilarity(dataModel);
        System.out.println(similarity.userSimilarity(11111111111L,18203835859L));
        //获取用户邻居
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
        long[] ar = userNeighborhood.getUserNeighborhood(userId);
        //构建推荐器
        Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
        //推荐视频
        List<RecommendedItem> recommendedItems = recommender.recommend(userId, 5);
        List<Long> itemIds = recommendedItems.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
        if (itemIds.size()==0){
            throw new ConditionException("未找到");
        }
        return tVideoMapper.selectBatchIds(itemIds);

    }

    private DataModel createDataModel(List<UserPreference> userPreferenceList) {
        FastByIDMap<PreferenceArray> fastByIdMap = new FastByIDMap<>();
        Map<Long, List<UserPreference>> map = userPreferenceList.stream().collect(Collectors.groupingBy(UserPreference::getUserId));
        Collection<List<UserPreference>> list = map.values();
        for(List<UserPreference> userPreferences : list){
            GenericPreference[] array = new GenericPreference[userPreferences.size()];
            for(int i = 0; i < userPreferences.size(); i++){
                UserPreference userPreference = userPreferences.get(i);
                GenericPreference item = new GenericPreference(userPreference.getUserId(), userPreference.getVideoId(), userPreference.getValue());
                array[i] = item;
            }
            fastByIdMap.put(array[0].getUserID(), new GenericUserPreferenceArray(Arrays.asList(array)));
        }
        return new GenericDataModel(fastByIdMap);
    }
}
