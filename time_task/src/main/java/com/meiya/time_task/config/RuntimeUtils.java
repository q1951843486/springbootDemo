package com.meiya.time_task.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @Description
 * @ClassName RuntimeUtils
 * @Author Administrator
 * @date 2020.03.02 09:56
 */
public class RuntimeUtils {
    /**
     * 调用python脚本，并向python脚本中传递参数
     * @param
     * @param params
     */
    public static void  runPython(String scriptPath, String... params) {

        Process process = null;
        BufferedReader bufferedReader = null;
        BufferedReader bufrError = null;
        try {
            // 脚本类型及位置
            String cmdScript = "python " + scriptPath;
            for (String param : params) {
                cmdScript = cmdScript + " " + param;
            }
            process = Runtime.getRuntime().exec(cmdScript);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {

                buffer.append(line).append("\n");
                System.out.println(buffer);
            }
            while ((line = bufrError.readLine()) != null) {

                buffer.append(line).append("\n");
                System.out.println(buffer);
            }
            int resultVul = process.waitFor();
            if(resultVul != 0){

            }


        } catch (IOException | InterruptedException e) {

        }finally {

            if (process != null) {
                process.destroy();
            }
        }
    }

    public static void main(String[] args) {
        runPython("D:\\xiangmu\\python\\pythondemo\\demo\\pageList.py");
    }


}
