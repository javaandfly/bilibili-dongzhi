package com.imooc.bilibili.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TTag implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 标签名称
     */
    private String name;

    private Date createTime;


}
