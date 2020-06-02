package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Description
 * @ClassName FileController
 * @Author Administrator
 * @date 2020.05.26 11:15
 */
@Controller
public class FileController {




    @RequestMapping(value = "/file/input")
    public boolean inputImg(MultipartFile multipartFile){

        String name = multipartFile.getName();
        try {
            File file = new File("D://a.jpg");
            FileOutputStream os =new FileOutputStream(file);
            os.write(multipartFile.getBytes());
            os.flush();
            os.close();


            boolean delete = file.delete();
            return delete;
        }catch (Exception e){
            System.out.println(e);
        }

        return false;
    }

}
