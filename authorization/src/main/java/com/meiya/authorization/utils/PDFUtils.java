package com.meiya.authorization.utils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;


/**
 * @Description
 * @ClassName PDFUtils
 * @Author Administrator
 * @date 2020.06.01 17:04
 */
public class PDFUtils {


    public static void main(String[] args) throws Exception {
        // 模板文件路径
        String templatePath =PDFUtils.class.getClassLoader().getResource("authorize.pdf").getPath();
        // 生成的文件路径
        String targetPath = "E:\\a.pdf";
        // 书签名
        String fieldName = "electronicSignature_af_image";
        // 图片路径

        // 读取模板文件
        InputStream input = new FileInputStream(new File(templatePath));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetPath));
        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();

        // 读图片
        Image image = Image.getInstance(PDFUtils.class.getClassLoader().getResource("timg.jpg").getPath());
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);

        stamper.close();
        reader.close();
    }


    /**
     * 填充 图片域
     */

    public static String inputImg(Image image,AcroFields form,String fieldName,PdfStamper stamper){

        try {
            form.addSubstitutionFont(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();

        // 读图片
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        try {
            under.addImage(image);
            stamper.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ll";
    }


    /**
     * 根据pdf模板文本域 对应的map 生成pdf文件
     * @param targetPath 输出目标文件夹
     * @param map 文本域map
     * @param inputStream 模板文件流
     * @return
     */
    public static String cratePDFByMap(String targetPath, Map<String,Object> map,InputStream inputStream){
        String path = new String();
        PdfReader pdfReader = null;
        ByteArrayOutputStream bos = null;
        PdfStamper pdfStamper = null;
        AcroFields acroFields = null;
        String fileName = System.currentTimeMillis() + new Random(10000).nextInt() + ".pdf";
        path = targetPath+ File.separator + fileName;
        try {
            //获取pdf模板
            pdfReader = new PdfReader(inputStream);
            bos = new ByteArrayOutputStream();
            pdfStamper = new PdfStamper(pdfReader,bos);
            acroFields = pdfStamper.getAcroFields();
            Iterator<String> iterator = acroFields.getFields().keySet().iterator();
            while (iterator.hasNext()){
                String name = iterator.next();
                System.out.println(name);
                String value = map.get(name)!=null?map.get(name).toString():null;
                acroFields.setField(name,value);
            }
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
        return path;
    }
    /**
     * 根据pdf模板文本域 对应的map 生成pdf文件
     * @param targetPath 输出目标文件夹
     * @param map 文本域map
     * @param inputStream 模板文件流
     * @return
     */
    public static String cratePDFInputImgByMap(String targetPath, Map<String,Object> map,InputStream inputStream){
        String path = new String();
        PdfReader pdfReader = null;
        ByteArrayOutputStream bos = null;
        PdfStamper pdfStamper = null;
        AcroFields acroFields = null;
        String fileName = System.currentTimeMillis() + new Random(10000).nextInt() + ".pdf";
        path = targetPath+ File.separator + fileName;
        try {
            //获取pdf模板
            pdfReader = new PdfReader(inputStream);
            bos = new ByteArrayOutputStream();
            pdfStamper = new PdfStamper(pdfReader,bos);
            acroFields = pdfStamper.getAcroFields();
            Iterator<String> iterator = acroFields.getFields().keySet().iterator();
            while (iterator.hasNext()){
                String name = iterator.next();
                if (StringUtils.equals("electronicSignature_af_image",name)){

                    inputImg((Image) map.get("electronicSignature_af_image"),acroFields,"electronicSignature",pdfStamper);
                }
                if (StringUtils.equals("authorizerPhoto",name)){

                    inputImg((Image) map.get("authorizerPhoto"),acroFields,"electronicSignature",pdfStamper);
                }

                String value = map.get(name)!=null?map.get(name).toString():null;
                acroFields.setField(name,value);
            }
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
        return path;
    }


   /*
    public static String cratePDFByClass(String targetPath, CheckResult checkResult, InputStream inputStream) {
        String path = new String();
        PdfReader pdfReader = null;
        ByteArrayOutputStream bos = null;
        PdfStamper pdfStamper = null;
        AcroFields acroFields = null;
        String fileName = System.currentTimeMillis() + ".pdf";
        path = targetPath + File.separator + fileName;
        try {
            //获取pdf模板
            pdfReader = new PdfReader(inputStream);
            bos = new ByteArrayOutputStream();
            pdfStamper = new PdfStamper(pdfReader, bos);
            acroFields = pdfStamper.getAcroFields();
            acroFields.setField("name", checkResult.getName());
            acroFields.setField("idCard", checkResult.getIdCard());
            acroFields.setField("startDate", checkResult.getBegindate());
            acroFields.setField("result", checkResult.getResult());
            acroFields.setGenerateAppearances(true);
            pdfStamper.setFormFlattening(true);
            pdfStamper.close();
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(bos.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                bos.close();
                pdfReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }
*/

    public String createPDF(String pdfName, Map<String, Object> data) {
        PdfReader reader = null;
        AcroFields s = null;
        PdfStamper ps = null;
        ByteArrayOutputStream bos = null;

        String realPath = ResourceBundle.getBundle("systemconfig").getString("upLoadFolder") + File.separator + "comfirmationDoc";
        String folderPath = realPath + File.separator;
        //创建上传文件目录
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //设置文件名
        String fileName = pdfName;
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

    /**
     * 获取打印机列表
     *
     * @return
     */
    public static List<String> getAllPrinter() {

        PrintService[] printServices = PrinterJob.lookupPrintServices();
        List list = new ArrayList();
        for (int i = 0; i < printServices.length; i++) {
            String name = printServices[i].getName();
            list.add(name);
        }
        return list;
    }

    public static void PDFprint(File file, String printerName) throws Exception {
        PDDocument document = null;
        try {
            document = PDDocument.load(file);
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(file.getName());
            if (printerName != null) {
                // 查找并设置打印机
                //获得本台电脑连接的所有打印机
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                if (printServices == null || printServices.length == 0) {
                    System.out.print("打印失败，未找到可用打印机，请检查。");
                    return;
                }
                PrintService printService = null;
                //匹配指定打印机
                for (int i = 0; i < printServices.length; i++) {
                    System.out.println(printServices[i].getName());
                    if (printServices[i].getName().contains(printerName)) {
                        printService = printServices[i];
                        break;
                    }
                }
                if (printService != null) {
                    printJob.setPrintService(printService);
                } else {
                    System.out.print("打印失败，未找到名称为" + printerName + "的打印机，请检查。");
                    return;
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
        } finally {
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
