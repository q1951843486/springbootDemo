package com.meiya.authorization.utils;




import cn.hutool.core.img.Img;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfImage;
import com.spire.pdf.PdfPageBase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName PDFPrint
 * @Author Administrator
 * @date 2020.06.02 11:55
 */
public class PDFPrint {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("attorneysName","李四");
        map.put("attorneysIdCard","1111111111");
        map.put("authorizerName","张三");
        map.put("authorizerIdCard","111111111111");
        map.put("vehicle_info_id","111111111111");
        map.put("type","1");
        map.put("effectiveTime","111111111");
        map.put("year","1");
        map.put("month","1");
        map.put("day","1");



/*
        try {
            map.put("electronicSignature",PDFPrint.class.getClassLoader().getResource("timg.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        try {
            map.put("electronicSignature_af_image",PDFPrint.class.getClassLoader().getResource("timg.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }*/
        InputStream inputStream = FileUtil.getFileInputStream("/authorize.pdf");
        PDFUtils.cratePDFByMap(new PDFPrint().getTargetPath(),map,inputStream);
    }

    public String print(Map map){
        InputStream inputStream = FileUtil.getFileInputStream("/authorize.pdf");
        String pdfPath = PDFUtils.cratePDFByMap(getTargetPath(), map, inputStream);
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
