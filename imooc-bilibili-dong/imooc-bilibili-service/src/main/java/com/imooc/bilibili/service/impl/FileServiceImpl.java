package com.imooc.bilibili.service.impl;

import com.imooc.bilibili.service.FileService;
import com.imooc.bilibili.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Override
    public String getFileMD5(MultipartFile file) {
        return null;
    }

    @Override
    public String uploadFileBySlices(MultipartFile slice, String fileMd5, Integer sliceNo, Integer totalSliceNo) throws Exception {

        return fastDFSUtil.uploadFileBySlices(slice,fileMd5,sliceNo,totalSliceNo);
    }
}
