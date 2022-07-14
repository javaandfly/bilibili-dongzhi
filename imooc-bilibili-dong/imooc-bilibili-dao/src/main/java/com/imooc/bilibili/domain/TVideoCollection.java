package com.imooc.bilibili.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 视频收藏记录表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TVideoCollection implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 视频投稿id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收藏分组id
     */
    private Long groupId;

    /**
     * 创建时间
     */
    private Date createTime;


}
