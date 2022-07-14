package com.imooc.bilibili.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 视频二值图记录表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TVideoBinaryPicture implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 帧数
     */
    private Integer frameNo;

    /**
     * 图片链接
     */
    private String url;

    /**
     * 视频时间戳
     */
    private Long videoTimestamp;

    private Date createTime;


}
