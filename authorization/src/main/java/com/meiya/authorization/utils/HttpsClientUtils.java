package com.meiya.authorization.utils;

import com.alibaba.fastjson.JSONObject;
import com.meiya.authorization.entity.dto.CheckResult;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author think
 * @project_name apiclient
 * @create 2019-04-22 22:31
 **/
@Component
public class HttpsClientUtils {

    private static final String  HOST="https://www.httech-bj.com";
    //private static final String HOST="http://10.105.10.8:9010";
    private static final String CHECKPATH = "/honest/service/honestCheckSington";

    private static final String COMPARE = "/honest/service/honestCheckCompare";
    private static final String LIVECOUNT = "/honest/service/doLiveCount";
    /**
     * 设置请求头信息
     *
     * @return
     */
    private static Map<String, String> getHeadersParams() {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");
        return headers;
    }


    /**
     * 将服务端返回的信息转换为json串
     *
     * @param httpResponse
     * @return
     * @throws IOException
     */
    private static String HttpResponseEntityToString(HttpResponse httpResponse) throws IOException {

        String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        return result;
    }

    /**
     * 对返回的内容 身份证号 姓名 进行解密 得到真实的数据
     *
     * @param checkResult
     * @return
     * @throws IOException
     */
    private static CheckResult getDecryptCheckResult(CheckResult checkResult) throws IOException {

        if (checkResult != null) {
            try {
                if (checkResult.getIdCard() != null) {
                    checkResult.setIdCard(RSAUtils.publicDecrypt(checkResult.getIdCard()));
                }
                if (checkResult.getName() != null) {
                    checkResult.setName(RSAUtils.publicDecrypt(checkResult.getName()));
                }
            } catch (Exception e) {
                return new CheckResult("客户端解密出错请联系相关人员", "-203");
            }
        }
        return checkResult;
    }

    /**
     * 将请求参数的 身份证号 姓名  进行加密
     *
     * @param querys
     * @return
     * @throws IOException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    private static Map<String, String> getRSAQuerys(Map<String, String> querys) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {

        for (Map.Entry<String, String> entry : querys.entrySet()) {
            if (("idCardNum".equals(entry.getKey()) && entry.getValue() != null) || ("name".equals(entry.getKey()) && entry.getValue() != null)) {

                querys.put(entry.getKey(), RSAUtils.publicEncrypt(entry.getValue()));
            }

        }
        return querys;
    }

    /**
     * 进行调用身份核查接口
     *
     * @return
     * @throws Exception
     */
    public static CheckResult doCheck(String license, String name, String idCardNum)
            throws Exception {
        Map<String, String> querys = new HashMap<>(3);
        querys.put("license", license);
        querys.put("name", name);
        querys.put("idCardNum", idCardNum);
        querys = getRSAQuerys(querys);
        HttpResponse httpResponse = InnerUtils.doGet(HOST, CHECKPATH, getHeadersParams(), querys);
        CheckResult checkResult = JSONObject.parseObject(HttpResponseEntityToString(httpResponse), CheckResult.class);


        return getDecryptCheckResult(checkResult);
    }




    /**
     * 进行调用人像比对的接口调用
     *
     * @return
     * @throws Exception
     */
    public static CheckResult doCompare(String license, String name, String idCardNum, String photo)
            throws Exception {
        Map<String, String> querys = new HashMap<>(4);
        querys.put("license", license);
        querys.put("name", name);
        querys.put("idCardNum", idCardNum);
        querys.put("photo", photo);

        querys = getRSAQuerys(querys);
        HttpResponse httpResponse = InnerUtils.doGet(HOST, COMPARE, getHeadersParams(), querys);
        String s = HttpResponseEntityToString(httpResponse);
        CheckResult checkResult = JSONObject.parseObject(s, CheckResult.class);
        return getDecryptCheckResult(checkResult);
    }



}
