package com.imooc.bilibili.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 视频观看记录表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TVideoView implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * ip
     */
    private String ip;

    /**
     * 创建时间
     */
    private Date createTime;


}
