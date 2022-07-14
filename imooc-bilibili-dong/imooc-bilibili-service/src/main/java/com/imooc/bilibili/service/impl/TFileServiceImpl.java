package com.imooc.bilibili.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.bilibili.domain.TFile;
import com.imooc.bilibili.mapper.TFileMapper;
import com.imooc.bilibili.service.ITFileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author DongZhi
 * @since 2022-07-07
 */
@Service
public class TFileServiceImpl extends ServiceImpl<TFileMapper, TFile> implements ITFileService {

}
