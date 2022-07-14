package com.imooc.bilibili.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 视频操作表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TVideoOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 操作类型：0点赞、1收藏、2投币
     */
    private String operationType;

    /**
     * 创建时间
     */
    private Date createTime;
    @TableField(exist = false)
    private Float value;


}
