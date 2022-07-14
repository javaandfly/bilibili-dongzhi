package com.imooc.bilibili.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoComment {

    private Long id;

    private Long videoId;

    private Long userId;

    private String comment;

    private Long replyUserId;

    private Long rootId;

    private Date createTime;

    private Date updateTime;
    @TableField(exist = false)
    private List<VideoComment> childList;
    @TableField(exist = false)
    private UserInfo userInfo;
    @TableField(exist = false)
    private UserInfo replyUserInfo;


}
