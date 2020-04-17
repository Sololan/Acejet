package com.wejoy.wejoycms.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/kindeditor")
public class KindeditorController {
    @Value("${picturepath.upload-url}")
    private String uploadUrl;

    @Value("${picturepath.loading-url}")
    private String loadingUrl;

    @Value("${picturepath.nginx-url}")
    private String nginxUrl;

    @RequestMapping("/upload")
    @ResponseBody
    public String uploadFile(MultipartFile file) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String millis = String.valueOf(calendar.getTimeInMillis());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName = null;
        String endPath = null;
        try {
            inputStream = file.getInputStream();
            fileName = file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = uploadUrl + year + "/" + month + "/" + day + "/";
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            endPath = tempFile.getPath() + "/" + millis + fileName;
            outputStream = new FileOutputStream(endPath);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {

            Map<Object, Object> map = new HashMap<>();
            map.put("error", 0);
            map.put("url", nginxUrl + "/pic" + "/" + year + "/" + month + "/" + day + "/" + millis + file.getOriginalFilename());
            JSONObject obj = new JSONObject(map);
            System.out.println(obj.get("url"));
            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "haha";
    }
}