package com.imooc.bilibili.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 视频标签关联表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TVideoTag implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 创建时间
     */
    private Date createTime;


}
