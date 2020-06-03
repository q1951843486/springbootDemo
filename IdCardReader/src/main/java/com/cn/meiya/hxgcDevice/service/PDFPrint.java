package com.cn.meiya.hxgcDevice.service;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.cn.meiya.hxgcDevice.bean.CheckResult;
import com.cn.meiya.hxgcDevice.util.FileUtil;
import com.cn.meiya.hxgcDevice.util.PDFUtils;

/**
 * @Description
 * @ClassName PDFPrint
 * @Author Administrator
 * @date 2020.06.02 11:55
 */
public class PDFPrint {


    public String print(CheckResult checkResult){
        InputStream inputStream = FileUtil.getFileInputStream("/IdentityTemplate.pdf");
        String pdfPath = PDFUtils.cratePDFByClass(getTargetPath(), checkResult, inputStream);
        return pdfPath;
    }

    //获取当前jar所在路径并在当前路径下创建PDFlog 文件夹
    public String getTargetPath(){
        String targetPath = new String();

        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        try {
            path =java.net.URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        path = new File(path).getParentFile().getAbsolutePath();
        targetPath = path + File.separator+ "PDFlog";

        System.out.println(targetPath);
        File file = new File(targetPath);
        if (!file.exists()){
            file.mkdirs();
        }
        return targetPath;
    }
}
