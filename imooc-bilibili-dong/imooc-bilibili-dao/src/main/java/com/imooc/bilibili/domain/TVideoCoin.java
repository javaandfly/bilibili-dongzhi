package com.imooc.bilibili.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 视频硬币表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TVideoCoin implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 视频投稿id
     */
    private Long videoId;

    /**
     * 投币数
     */
    private Integer amount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
