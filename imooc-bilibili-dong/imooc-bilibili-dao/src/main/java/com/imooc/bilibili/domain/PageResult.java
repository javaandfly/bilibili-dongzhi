package com.imooc.bilibili.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private Integer total;

    private List<T> list;


}
