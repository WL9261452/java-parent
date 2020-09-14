package com.java.oss.controller;

import com.java.commonutils.api.APICODE;
import com.java.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 修改了一下
 */
@Api(tags = "OSS云存储")
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("uploadFile")
    public APICODE uploadFile(MultipartFile file){
        String uploadUrl = ossService.uploadFile(file);
        return APICODE.OK().message("文件上传成功").data("url", uploadUrl);
    }

    @DeleteMapping("deleteFile/{imageUrl}")
    public APICODE deleteFile(@PathVariable(name = "imageUrl") String imageUrl){
        try {
            ossService.deleteFile(new String(Base64.getDecoder().decode(imageUrl), "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return APICODE.OK();
    }
}
