package com.osyunge2.service;

import com.osyunge2.dataobject.PicResult;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    PicResult uploadFile(MultipartFile picFile);
}
