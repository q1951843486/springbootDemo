package com.cn.meiya.hxgcDevice.util;

import com.cn.meiya.hxgcDevice.bean.CheckResult;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * @Description
 * @ClassName PDFUtils
 * @Author Administrator
 * @date 2020.06.01 17:04
 */
public class PDFUtils {

	

    public static String cratePDFByClass(String targetPath, CheckResult checkResult,InputStream inputStream){
        String path = new String();
        PdfReader pdfReader = null;
        ByteArrayOutputStream bos = null;
        PdfStamper pdfStamper = null;
        AcroFields acroFields = null;
       /* //获取当前jar包所在路径
        String jarPath = System.getProperty("java.class.path");
        int firstIndex = jarPath.lastIndexOf(System.getProperty("path.separator")) + 1;
        int lastIndex = jarPath.lastIndexOf(File.separator) + 1;
        jarPath = jarPath.substring(firstIndex, lastIndex);
        System.out.println(jarPath);*/
       //File file = new File("C:" + File.separator + "pdf" + File.separator);
       //File file = new File("D:");
       String fileName = checkResult.getName() + checkResult.getIdCard()+ ".pdf";
       path = targetPath+ File.separator + fileName;
       try {
           //获取pdf模板
           pdfReader = new PdfReader(inputStream);
           bos = new ByteArrayOutputStream();
           pdfStamper = new PdfStamper(pdfReader,bos);
           acroFields = pdfStamper.getAcroFields();
           acroFields.setField("name",checkResult.getName());
           acroFields.setField("idCard",checkResult.getIdCard());
           acroFields.setField("startDate",checkResult.getBegindate());
           acroFields.setField("result",checkResult.getResult());
         /*  Iterator<String> iterator = acroFields.getFields().keySet().iterator();
           while (iterator.hasNext()){
               String name = iterator.next().toString();
               System.out.println(name);
           }*/
           acroFields.setGenerateAppearances(true);
           pdfStamper.setFormFlattening(true);
           pdfStamper.close();
           FileOutputStream fileOutputStream = new FileOutputStream(path);
           fileOutputStream.write(bos.toByteArray());
           fileOutputStream.flush();
           fileOutputStream.close();
       }catch (Exception e){
           e.printStackTrace();
           return "";
       }finally {
           try {
               bos.close();
               pdfReader.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }




       /* try {
            //读取pdf模板
            pdfReader = new PdfReader("IdentityTemplate.pdf");
            bos = new ByteArrayOutputStream();
            pdfStamper = new PdfStamper(pdfReader,bos);


        }catch (Exception e){
            e.printStackTrace();
        }*/



        return path;


    }


    public String createPDF(String pdfName, Map<String, Object> data) {
        PdfReader reader = null;
        AcroFields s = null;
        PdfStamper ps = null;
        ByteArrayOutputStream bos = null;

        String realPath = ResourceBundle.getBundle("systemconfig").getString("upLoadFolder") + File.separator + "comfirmationDoc";
        String folderPath = realPath + File.separator ;
        //创建上传文件目录
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //设置文件名
        String fileName = pdfName ;
        String savePath = folderPath + File.separator + fileName;

        try {
            String file = this.getClass().getClassLoader().getResource("comfirmTemplate.pdf").getPath();
            //设置字体
            String font = this.getClass().getClassLoader().getResource("YaHei.ttf").getPath();
            reader = new PdfReader(file);
            bos = new ByteArrayOutputStream();
            ps = new PdfStamper(reader, bos);
            s = ps.getAcroFields();
            //使用中文字体 使用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体 Adobe 宋体 std L
            BaseFont bfChinese = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //设置编码格式
            s.addSubstitutionFont(bfChinese);
            // 遍历data 给pdf表单表格赋值
            for (String key : data.keySet()) {
                if (data.get(key) != null) {
                    s.setField(key, data.get(key).toString());
                }
            }

            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            ps.setFormFlattening(true);
            ps.close();

            FileOutputStream fos = new FileOutputStream(savePath);

            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
            return savePath;
        } catch (IOException | DocumentException e) {

            e.printStackTrace();
            return "";
        } finally {
            try {
                bos.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void PDFprint(File file ,String printerName) throws Exception {
        PDDocument document = null;
        try {
            document = PDDocument.load(file);
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(file.getName());
            if (printerName != null) {
                // 查找并设置打印机
                //获得本台电脑连接的所有打印机
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                if(printServices == null || printServices.length == 0) {
                    System.out.print("打印失败，未找到可用打印机，请检查。");
                    return ;
                }
                PrintService printService = null;
                //匹配指定打印机
                for (int i = 0;i < printServices.length; i++) {
                    System.out.println(printServices[i].getName());
                    if (printServices[i].getName().contains(printerName)) {
                        printService = printServices[i];
                        break;
                    }
                }
                if(printService!=null){
                    printJob.setPrintService(printService);
                }else{
                    System.out.print("打印失败，未找到名称为" + printerName + "的打印机，请检查。");
                    return ;
                }
            }
            //设置纸张及缩放
            PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);
            //设置多页打印
            Book book = new Book();
            PageFormat pageFormat = new PageFormat();
            //设置打印方向
            pageFormat.setOrientation(PageFormat.PORTRAIT);//纵向
            pageFormat.setPaper(getPaper());//设置纸张
            book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
            printJob.setPageable(book);
            printJob.setCopies(1);//设置打印份数
            //添加打印属性
            HashPrintRequestAttributeSet pars = new HashPrintRequestAttributeSet();
            pars.add(Sides.DUPLEX); //设置单双页
            printJob.print(pars);
        }finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static Paper getPaper() {
        Paper paper = new Paper();
        // 默认为A4纸张，对应像素宽和高分别为 595, 842
        int width = 595;
        int height = 842;
        // 设置边距，单位是像素，10mm边距，对应 28px
        int marginLeft = 10;
        int marginRight = 0;
        int marginTop = 10;
        int marginBottom = 0;
        paper.setSize(width, height);
        // 下面一行代码，解决了打印内容为空的问题
        paper.setImageableArea(marginLeft, marginRight, width - (marginLeft + marginRight), height - (marginTop + marginBottom));
        return paper;
    }


}
