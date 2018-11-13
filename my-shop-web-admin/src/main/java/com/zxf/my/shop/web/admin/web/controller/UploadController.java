package com.zxf.my.shop.web.admin.web.controller;

import org.omg.CORBA.MARSHAL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传controller
 * @author zxf
 * @date 2018/11/9 9:25
 */
@Controller
public class UploadController {

    private static final String UPLOAD_PATH = "/static/upload/";

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map<String, Object> upload(MultipartFile dropzFile, MultipartFile editorFile,  HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();

        //前端上传的文件
        MultipartFile myFile = dropzFile==null?editorFile:dropzFile;
        //文件名
        String fileName = myFile.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        //拿到当前项目路径, 文件存放路径
        String filePath = "E:/ThusandMoutains/my-shop/my-shop-web-admin/src/main/webapp" + UPLOAD_PATH;
        //String filePath = request.getSession().getServletContext().getRealPath("/");
        //System.out.println(filePath);
        File file = new File(filePath);
        if (!file.exists()) {//判断文件是否存在，不存在则创建
            file.mkdir();
        }
        fileName = UUID.randomUUID() + fileSuffix;
        //将文件写入目标目录
        file = new File(filePath, fileName);
        try {
            myFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Dropzone上传
        if (dropzFile!=null){
            map.put("fileName", UPLOAD_PATH + fileName);
        } else {
            String serverPath = String.format("%s://%s:%s%s%s", request.getScheme(), request.getServerName(), request.getServerPort(),
                    request.getContextPath(), UPLOAD_PATH);;
            System.out.println(serverPath);
            map.put("errno", 0);
            map.put("data", new String[]{serverPath + file.getName()});
        }

        return map;
    }
}