package com.imooc.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.imooc.bilibili.domain.TFile;
import com.imooc.bilibili.mapper.TFileMapper;
import com.imooc.bilibili.service.FileService;
import com.imooc.bilibili.util.FastDFSUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FastDFSUtil fastDFSUtil;
    @Autowired
    private TFileMapper fileMapper;
    @Override
    public String getFileMD5(MultipartFile file) {
        return null;
    }

    @Override
    public String uploadFileBySlices(MultipartFile slice, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws Exception {
        LambdaQueryWrapper<TFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TFile::getMd5,fileMd5);
        TFile tFile = fileMapper.selectOne(queryWrapper);
        if (tFile!=null){
            return tFile.getUrl();
        }
        String url = fastDFSUtil.uploadFileBySlices(slice, fileMd5, sliceNo, totalSliceNo);
        if (!StringUtil.isNullOrEmpty(url)){
            TFile tFile1 = new TFile();
            tFile1.setCreateTime(new Date());
            tFile1.setMd5(fileMd5);
            tFile1.setUrl(url);
            tFile1.setType(fastDFSUtil.getFileType(slice));
            fileMapper.insert(tFile1);
        }
        return url;
    }
}
