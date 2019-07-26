package com.osyunge2.controller;

import com.osyunge2.dataobject.PicResult;
import com.osyunge2.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public PicResult uploadFile(MultipartFile uploadFile){
        PicResult result=pictureService.uploadFile(uploadFile);
        return result;

    }

}
