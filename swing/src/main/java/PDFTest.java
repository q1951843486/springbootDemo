import modul.IdentityPrint;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @ClassName PDF
 * @Author Administrator
 * @date 2020.06.02 13:35
 */
public class PDFTest {

    public static void main(String[] args) throws Exception {
        PDFPrint pdfPrint = new PDFPrint();
        IdentityPrint identityPrint = new IdentityPrint();
        identityPrint.setName("张三");
        identityPrint.setIdCard("123456789876543212");
        identityPrint.setStartDate("2020年06月01日");
        identityPrint.setResult("您的证件已过有效期");

        Map map = new HashMap();
        map.put("name","李四");
        map.put("idCard","123456789876543212");
        map.put("startDate","2020年06月01日");
        map.put("result","一致");
        String print = pdfPrint.printMap(map);
        File file = new File(print);
        PDFUtils.PDFprint(file,"dsafjgdsalg");
        file.delete();
    }
}
