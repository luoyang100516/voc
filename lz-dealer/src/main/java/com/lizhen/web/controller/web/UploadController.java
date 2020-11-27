package com.lizhen.web.controller.web;

import com.lizhen.common.response.DataResponse;
import com.lizhen.common.util.UploadUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( "/upload" )
public class UploadController {

    @RequestMapping( "/image" )
    public DataResponse getImageList(@RequestParam("file") MultipartFile file) throws IOException {
        String name = UploadUtil.uploadImage(file);
        return new DataResponse().setData(name);
    }
}
