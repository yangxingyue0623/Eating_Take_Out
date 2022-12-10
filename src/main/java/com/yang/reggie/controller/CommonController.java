package com.yang.reggie.controller;

import com.yang.reggie.com.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());
        //获得原始的文件名
        String originalFilename = file.getOriginalFilename();

        //动态拿后缀
        String sufix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //或者用uuid防止名称重复
        String fileName = UUID.randomUUID().toString() + sufix;
        //创建一个目录
        File dir = new File(basePath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //不存在就创建
            dir.mkdirs();
        }


        try {
            file.transferTo(new File(basePath+new File(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);

//        if(!headerImg.isEmpty()){
//            //保存到文件服务器，OSS服务器
//            String originalFilename = headerImg.getOriginalFilename();
//            headerImg.transferTo(new File("H:\\cache\\"+originalFilename));
//        }
//
//        if(photos.length > 0){
//            for (MultipartFile photo : photos) {
//                if(!photo.isEmpty()){
//                    String originalFilename = photo.getOriginalFilename();
//                    photo.transferTo(new File("H:\\cache\\"+originalFilename));
//                }
//            }
//        }

    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
            try {
                //输入流，通过输入流读取文件内容
                FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

                //输出流，通过输出流将文件写回浏览器
                ServletOutputStream outputStream = response.getOutputStream();

                response.setContentType("image/jpeg");
                //代表的是图片文件
                int len = 0;
                byte[] bytes = new byte[1024];
                while ((len=fileInputStream.read(bytes)) != -1){
                    outputStream.write(bytes, 0, len);
                    outputStream.flush();
                }


            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch(Exception e) {
                e.printStackTrace();
            }

    }
}
