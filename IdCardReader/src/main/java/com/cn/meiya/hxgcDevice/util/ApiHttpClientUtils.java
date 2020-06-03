/*
 * Copyright (c) 2012-2032 ACCA.
 * All Rights Reserved.
 */
package com.cn.meiya.hxgcDevice.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * api接口调用工具类
 * 
 * @version Smartrix v1.0
 * @author Liu Hao, 2019年1月11日 下午4:18:42
 */
@Slf4j
public class ApiHttpClientUtils {
	
	private static  final  String CERTIFICATE_PATH="classpath:client.p12";
    private static  final  String  CERTIFICATE_TYPE="PKCS12";
    private static  final  String CERTIFICATE_PASSWORD="123456";
	
	private static PoolingHttpClientConnectionManager connManager;
    private static RequestConfig requestConfig;
    private static SSLConnectionSocketFactory sslSocketFactory;
    /** 连接超时时间（默认3秒 3000ms） 单位毫秒（ms） */
	private static final int CONNECTION_TIMEOUT = 6000;
	/** 读取数据超时时间（默认30秒 30000ms） 单位毫秒（ms） */
	private static final int SO_TIMEOUT = 30000;
	/** 字符集设置，默认UTF-8 */
	private static final String CHARSET = "UTF-8";
 
    static {
    	Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
		try {
			KeyStore keyStore = KeyStore.getInstance(CERTIFICATE_TYPE);
			InputStream instream = new FileInputStream(ResourceUtils.getFile(CERTIFICATE_PATH));
			keyStore.load(instream, CERTIFICATE_PASSWORD.toCharArray());
			SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore,CERTIFICATE_PASSWORD.toCharArray()).build();
			sslSocketFactory = new SSLConnectionSocketFactory(sslContext
                    , new String[]{"TLSv1"}    // supportedProtocols ,这里可以按需要设置
                    , null    // supportedCipherSuites
                    , SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			//new AllowAllHostnameVerifier()不验证服务器证书与服务器ip之间关联
			//注册
			socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", sslSocketFactory).build();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
//				e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
//				e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // 设置连接池大小
		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(connManager.getMaxTotal());
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(CONNECTION_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(SO_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(CONNECTION_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        //configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }
    
    
    public static <T> T doGet(Class<T> classz,String apiServerPath,String apiUrl,Object ...urlParams){
    	JSONObject object = doGet(apiServerPath, apiUrl, urlParams);
		return JSONObject.toJavaObject(object, classz);
    }
    
    public static <T> T doPost(Class<T> classz,String apiServerPath,String apiUrl,Map<String, String> params){
    	JSONObject object = doPost(apiServerPath, apiUrl, params);
		return JSONObject.toJavaObject(object, classz);
    }
    
    
    /**
     * 发送 GET 请求（HTTPS[带证书验证]/HTTP）
     * @param apiServerPath
     * @param apiUrl
     * @param urlParams [参数1,参数2,参数3]形式 
     * @return
     */
    public static JSONObject doGet(String apiServerPath,String apiUrl,Object ...urlParams){
    	apiUrl = formatDoGetUrlParams(apiUrl, urlParams);
		return getResponseResults(doGet(apiServerPath + apiUrl));
    }
    
    public static JSONObject doGet(String apiServerPath,String apiUrl,Map<String, String> params){
    	apiUrl = formatDoGetUrlParams(apiUrl, params);
		return getResponseResults(doGet(apiServerPath + apiUrl));
    }
    
    /**
     * 发送  POST 请求（HTTPS[带证书验证]/HTTP）
     * @param apiServerPath
     * @param apiUrl
     * @param params K-V形式 
     * @return
     */
    public static JSONObject doPost(String apiServerPath,String apiUrl,Map<String, String> params){
		return getResponseResults(doPost(apiServerPath + apiUrl, params));
    }
    
    /**
     * 
     * Description: 处理响应结果
     * @param responseWrapper
     * @return
     */
    public static JSONObject getResponseResults(HttpResponseWrapper responseWrapper) {
    	JSONObject jsonResults = new JSONObject();
    	int statusCode = -1;//调用失败
    	String results = "";//返回结果
    	String errormsg = "系统异常，接口调用失败";
    	if(responseWrapper != null) {
    		statusCode = responseWrapper.getStatusCode();
        	try {
				results = responseWrapper.getResponseString(CHARSET);
				System.out.println(results);
				errormsg = "";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				statusCode = -2;//解析参数异常
				errormsg = "系统错误，解析参数异常";
				log.error("调用接口失败！ 返回结果处理错误信息{}", e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				statusCode = -2;
				errormsg = "系统错误，解析参数异常";
				log.error("调用接口失败！ 返回结果处理错误信息{}", e.getMessage());
			}finally {
				responseWrapper.close();
			}
    	}
    	jsonResults.put("statusCode", statusCode);
    	jsonResults.put("resultData", results);
    	jsonResults.put("errormsg", errormsg);
		return jsonResults;
    }
   
    
    /**
     * 发送 GET 请求（HTTPS/HTTP）
     * Content-Type=application/json
     * @param apiUrl api地址   接口地址+/参数1/参数2/参数3/...
     * @return
     */
    private static HttpResponseWrapper doGet(String apiUrl){
    	HttpResponseWrapper responseWrapper = null;
        try {
        	HttpGet httpGet = new HttpGet(apiUrl);
        	httpGet.setConfig(requestConfig);
        	httpGet.setHeader("Content-Type","application/json");
//        	httpGet.setHeader("Content-Encoding", CHARSET);
//        	httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        	CloseableHttpClient httpClient = getHttpClient(apiUrl);
        	CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        	responseWrapper = new HttpResponseWrapper(httpClient, httpResponse);
//        	int statusCode = responseWrapper.getStatusCode();
//        	String results = responseWrapper.getResponseString(CHARSET);
        } catch (Exception e) {
			e.printStackTrace();
			responseWrapper.close();
			log.error("调用接口{}失败！ 错误信息{}", apiUrl,e.getMessage());
		} finally {
			
		}
		return responseWrapper;
    }
    
    
    /**
     * 发送 POST 请求（HTTPS/HTTP）
     * Content-Type=application/json
     * @param apiUrl api地址
     * @param params K-V形式
     * @return
     */
    private static HttpResponseWrapper doPost(String apiUrl,Map<String, String> params){
    	HttpResponseWrapper responseWrapper = null;
    	HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setConfig(requestConfig);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(initNameValuePair(params), Charset.forName(CHARSET));
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        httpPost.addHeader("charset", "utf-8");
        httpPost.setEntity(entity);
        try {
        	CloseableHttpClient httpClient = getHttpClient(apiUrl);
        	CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        	responseWrapper = new HttpResponseWrapper(httpClient, httpResponse);
//        	int statusCode = responseWrapper.getStatusCode();
//        	String result = responseWrapper.getResponseString(CHARSET);
        } catch (Exception e) {
			e.printStackTrace();
			responseWrapper.close();
			log.error("调用接口{}失败！ 错误信息{}", apiUrl,e.getMessage());
		} finally {
			
	    }
		return responseWrapper;
    }
    
    /**
     * 获取HttpClient连接
     * @param apiUrl
     * @return
     */
    private static CloseableHttpClient getHttpClient(String apiUrl){
    	CloseableHttpClient httpClient = null;
    	if (apiUrl.startsWith("https")) {
    		httpClient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory)
            		.setDefaultRequestConfig(requestConfig).build();
    	}else{
    		httpClient = HttpClientBuilder.create().
                    setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy()).
                    setRedirectStrategy(new DefaultRedirectStrategy()).
                    setDefaultCookieStore(new BasicCookieStore()).
                    setDefaultRequestConfig(requestConfig).build();
    	}
		return httpClient;
    }
    
    /**
	 * 请求的参数处理 
	 * @param params  Map<String, String> params 参数名=参数值  键值对
	 * @return  List<NameValuePair>   参数名=参数值  键值对
	 */
	private static List<NameValuePair> initNameValuePair(Map<String, String> params) {
		List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }
        System.out.println(pairList);
		return pairList;
	}
	
	/**
	 * 接口地址+？参数1&参数2&参数3&...   
	 * /XXX/XXX/参数1/参数2/参数3/参数4/...
	 * @param url
	 * @param parameters K-V
	 * @return
	 */
	private static String formatDoGetUrlParams(String url,Map<String, String> parameters){
		StringBuffer param = new StringBuffer();
        int i = 0;
        if(url.matches("^.*[{].*[}].*$")){
        	String[] urls = url.substring(url.indexOf("/") + 1).split("/");
        	url = "/";
        	for (String key : urls) {
        		if(key.matches("^[{]{1}.*}$")){
        			key = key.substring(1, key.length() - 1);
        			url += (parameters.containsKey(key) ? parameters.get(key) : null) + "/";
        		}else{
        			url += key + (i == (urls.length - 1) ? "" : "/");
        		}
        		i++;
			}
        }else{
        	for (String key : parameters.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                try {
					param.append(key).append("=").append(URLEncoder.encode(parameters.get(key), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					param.append(key).append("=").append(parameters.get(key));
				}
                i++;
            }
        	url += param.toString();
        }
		return url;
	}
	
	/**
	 * 拼接url参数
	 * /XXX/XXX/{参数1}/{参数2}/{参数3}/{参数4}/...
	 * @param url
	 * @param urlVariables
	 * @return
	 */
	private static String formatDoGetUrlParams(String url,Object... urlVariables){
		String newUrl = "/";
		if(urlVariables != null){
			String[] urls = url.substring(url.indexOf("/") + 1).split("/");
			int index = 0;
			for (int i = 0; i < urls.length; i++) {
				String string = urls[i];
				if(string.matches("^[{]{1}.*}$")){
					try {
						newUrl += urlVariables[index] + "/";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						newUrl += null + "/";
					}
					index++;
				}else{
					newUrl += string + (i == (urls.length - 1) ? "" : "/");
				}
			}
		}else{
			newUrl = url;
		}
		return newUrl;
	}
    
    /**
	 * 请求响应对象类
	 *
	 */
	public static class HttpResponseWrapper {
		private CloseableHttpResponse httpResponse;
		private CloseableHttpClient httpClient;
		
		public HttpResponseWrapper(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) {
			this.httpClient = httpClient;
			this.httpResponse = httpResponse;
		}
		
		/**
		 * 将请求响应结果转字符串
		 * @param responseCharacter  响应编码
		 * @return
		 * @throws ParseException
		 * @throws IOException
		 */
		public String getResponseString(String responseCharacter) throws ParseException, IOException {
			HttpEntity entity = getEntity();
			String responseStr = EntityUtils.toString(entity, responseCharacter);
			responseStr = responseStr.replace("null", "\"\"");
			if (entity.getContentType() == null) {
				responseStr = new String(responseStr.getBytes("iso-8859-1"), responseCharacter);
			}
			EntityUtils.consume(entity);
			return responseStr;
		}
		/**
		 * 返回状态值
		 * @return
		 */
		public int getStatusCode() {
			return httpResponse.getStatusLine().getStatusCode();
		}
		
		public HttpEntity getEntity() {
			return httpResponse.getEntity();
		}
		
		public void close() {
			HttpClientUtils.closeQuietly(httpResponse);
			HttpClientUtils.closeQuietly(httpClient);
		}
	}
}
