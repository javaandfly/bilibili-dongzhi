package com.imooc.bilibili.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 弹幕记录表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TDanmu implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 视频Id
     */
    private Long videoId;

    /**
     * 弹幕内容
     */
    private String content;

    /**
     * 弹幕出现时间
     */
    private String danmuTime;

    /**
     * 创建时间
     */
    private Date createTime;


}
