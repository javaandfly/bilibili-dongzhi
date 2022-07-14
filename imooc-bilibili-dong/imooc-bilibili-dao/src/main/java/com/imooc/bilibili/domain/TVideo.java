package com.imooc.bilibili.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 视频投稿记录表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document(indexName = "videos")
public class TVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    /**
     * 用户id
     */
    @Field(type = FieldType.Long)
    private Long userId;

    /**
     * 视频链接
     */

    private String url;

    /**
     * 封面链接
     */
    private String thumbnail;

    /**
     * 视频标题
     */
    @Field(type = FieldType.Text)
    private String title;

    /**
     * 视频类型：0原创 1转载
     */
    private String type;

    /**
     * 视频时长
     */
    private String duration;

    /**
     * 所在分区：0鬼畜 1音乐 2电影
     */
    private String area;

    @TableField(exist = false)
    private List<TVideoTag> videoTagList;

    /**
     * 视频简介
     */
    @Field(type = FieldType.Text)
    private String description;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date)
    private Date updateTime;




}
