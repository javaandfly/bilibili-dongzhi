package com.imooc.bilibili.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TFile implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 文件存储路径
     */
    private String url;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件md5唯一标识串
     */
    private String md5;

    /**
     * 创建时间
     */
    private Date createTime;


}
