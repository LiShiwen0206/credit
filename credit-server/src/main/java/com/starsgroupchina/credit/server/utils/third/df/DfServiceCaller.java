package com.starsgroupchina.credit.server.utils.third.df;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsgroupchina.credit.bean.enums.ThirdCreditPlatform;
import com.starsgroupchina.credit.bean.model.ThirdRequestHistory;
import com.starsgroupchina.credit.bean.third.DfRepsonseData;
import com.starsgroupchina.credit.bean.third.ThirdRequestParam;
import com.starsgroupchina.credit.server.service.third.ThirdRequestHistoryService;
import com.starsgroupchina.credit.server.utils.SpringContextUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

//import com.google.gson.GsonBuilder;

public class DfServiceCaller {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * 异步提交请求
     * @param baseUrl 请求URL（大蜂提供）
     * @param clientId 用户ID（大蜂提供）
     * @param appCode 业务代码（大蜂提供）
     * @param apiVersion api版本号（大蜂提供）
     * @param businessParam 业务参数（调用者提供）
     * @param connectTimeoutMs 连接超时（调用者按需要设置）
     * @param readTimeoutMs 读超时（调用者按需要设置）
     * @return
     * @throws Exception
     */
    public static String asyncRequest(String baseUrl, String clientId, String appCode,
            String apiVersion, Object businessParam,int connectTimeoutMs, int readTimeoutMs,ThirdRequestParam thirdRequestParam) throws Exception {
        String param = generateParam(clientId,appCode,apiVersion,null,businessParam);
        String url = baseUrl + "/" + apiVersion + "/prd/check/asyncRequest";
        ThirdRequestHistory thirdRequestHistory = new ThirdRequestHistory();
        thirdRequestHistory.setMethod(url);
        thirdRequestHistory.setPlatform(ThirdCreditPlatform.PLATFORM_DF_REQUEST.key());
        thirdRequestHistory.setParams(param);
        ThirdRequestHistoryService historyService = SpringContextUtils.getBean(ThirdRequestHistoryService.class);
        String result = post(url, param, connectTimeoutMs, readTimeoutMs);
        thirdRequestHistory.setResult(result);
        thirdRequestHistory.setProjectId(thirdRequestParam.getProjectId());
        thirdRequestHistory.setProjectNo(thirdRequestParam.getProjectNo());
        historyService.create(thirdRequestHistory);
        return result;
    }

    /**
     * 获取结果
     * @param baseUrl 请求URL（大蜂提供）
     * @param clientId 用户ID（大蜂提供）
     * @param appCode 业务代码（大蜂提供）
     * @param apiVersion api版本号（大蜂提供）
     * @param transactionId 交易ID（从asyncRequest接口获取）
     * @param connectTimeoutMs 连接超时（调用者按需要设置）
     * @param readTimeoutMs 读超时（调用者按需要设置）
     * @return
     * @throws Exception
     */
    public static DfRepsonseData getResult(String baseUrl, String clientId, String appCode,
                                           String apiVersion, String transactionId, int connectTimeoutMs, int readTimeoutMs, ThirdRequestParam thirdRequestParam) throws Exception {
        String param = generateParam(clientId,appCode,apiVersion,transactionId,null);
        String url = baseUrl + "/" + apiVersion + "/prd/check/getAsyncResult";
        String response = post(url, param, connectTimeoutMs, readTimeoutMs);
        ThirdRequestHistory thirdRequestHistory = new ThirdRequestHistory();
        thirdRequestHistory.setMethod(url);
        thirdRequestHistory.setPlatform(ThirdCreditPlatform.PLATFORM_DF_RESPONSE.key());
        thirdRequestHistory.setParams(param);
        ThirdRequestHistoryService historyService = SpringContextUtils.getBean(ThirdRequestHistoryService.class);
        thirdRequestHistory.setResult(response);
        thirdRequestHistory.setProjectId(thirdRequestParam.getProjectId());
        thirdRequestHistory.setProjectNo(thirdRequestParam.getProjectNo());
        historyService.create(thirdRequestHistory);
        return JSON.parseObject(response, DfRepsonseData.class);
    }
    
    private static String generateParam(String clientId,String appCode,String apiVersion,String transactionId,Object businessParam){
        Map<String,Object> apiParam = new HashMap<String,Object>();
        Map<String,Object> paramHeader = new HashMap<String,Object>();
        Map<String,Object> paramBody = new HashMap<String,Object>();
        paramHeader.put("api_version", apiVersion);
        paramHeader.put("client_id", clientId);
        paramHeader.put("app_code", appCode);
        paramHeader.put("source", "API");
        paramHeader.put("transaction_id", transactionId);
        paramBody.put("param", businessParam);
        apiParam.put("header", paramHeader);
        apiParam.put("body", paramBody);
        try {
            return OBJECT_MAPPER.writeValueAsString(apiParam);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        return new GsonBuilder().create().toJson(apiParam);
        return null;
    }

    private static String post(String url, String param, int connectTimeoutMs,int readTimeoutMs) throws Exception {
        String responseStr = null;
        SSLContext sslcontext = SSLContexts.custom().build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeoutMs)
                .setSocketTimeout(readTimeoutMs).build();
        HttpClientBuilder builder = HttpClients.custom();
        builder.setSSLSocketFactory(sslsf);
        builder.setDefaultRequestConfig(requestConfig);
        CloseableHttpClient httpclient = builder.build();
        try {
            HttpPost httpPost = new HttpPost(url);
            Charset charset = Charset.forName("UTF-8");
            StringEntity entity = new StringEntity(param, charset);
            entity.setContentEncoding(charset.displayName());
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity2 = response.getEntity();
                responseStr = EntityUtils.toString(entity2, "UTF-8");
                EntityUtils.consume(entity2);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return responseStr;
    }

}
