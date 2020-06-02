import modul.IdentityPrint;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Description
 * @ClassName PDFPrint
 * @Author Administrator
 * @date 2020.06.02 11:55
 */
public class PDFPrint {


    public String print(IdentityPrint identityPrint){


        InputStream inputStream = this.getClass().getResourceAsStream("/IdentityTemplate.pdf");
        //String path = "E:\\java\\springbootDemo\\swing\\src\\main\\resources\\IdentityTemplate.pdf";
        String pdfPath = PDFUtils.cratePDFByClass(getTargetPath(), identityPrint, inputStream);
        System.out.println(pdfPath);
        return pdfPath;

    }

    public String printMap(Map map){


        InputStream inputStream = this.getClass().getResourceAsStream("/IdentityTemplate.pdf");
        //String path = "E:\\java\\springbootDemo\\swing\\src\\main\\resources\\IdentityTemplate.pdf";
        String pdfPath = PDFUtils.cratePDFByMap(getTargetPath(), map, inputStream);
        System.out.println(pdfPath);
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
