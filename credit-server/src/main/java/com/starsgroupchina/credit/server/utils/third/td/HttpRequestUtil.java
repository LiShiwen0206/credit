package com.starsgroupchina.credit.server.utils.third.td;

import com.alibaba.fastjson.JSON;
import com.starsgroupchina.credit.bean.enums.ThirdCreditPlatform;
import com.starsgroupchina.credit.bean.model.ThirdRequestHistory;
import com.starsgroupchina.credit.bean.third.ThirdRequestParam;
import com.starsgroupchina.credit.server.conf.TdConfig;
import com.starsgroupchina.credit.server.service.third.ThirdRequestHistoryService;
import com.starsgroupchina.credit.server.utils.SpringContextUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


public class HttpRequestUtil {
	private static final Log log =LogFactory.getLog(HttpRequestUtil.class);
	
	private static final String submitUrl ;
	private static final String queryUrl;
	private static final String PARTNER_CODE ;
	private static final String PARTNER_KEY;
	private static final String PARTNER_APP ;
	private static HttpsURLConnection conn;
	private static SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
	static  {
		TdConfig tdConfig = SpringContextUtils.getBean(TdConfig.class);
		submitUrl=tdConfig.getSubmitUrl();
		queryUrl = tdConfig.getQueryUrl();
		PARTNER_CODE = "hxzc";
		PARTNER_KEY = tdConfig.getPartnerKey();
		PARTNER_APP = "hxzc_web";
	}

	 public static TdPreloanSubmitResponse apply(Map<String, Object> params, ThirdRequestParam thirdRequestParam) {
		 TdPreloanSubmitResponse submitResponse = new TdPreloanSubmitResponse();
	        try {
	            String urlString = new StringBuilder().append(submitUrl).append("?partner_code=").append(PARTNER_CODE).append("&partner_key=").append(PARTNER_KEY).append("&app_name=").append(PARTNER_APP).toString();
	            URL url = new URL(urlString);
	            StringBuilder postBody = new StringBuilder();
	            for (Map.Entry<String, Object> entry : params.entrySet()) {
	                if (entry.getValue() == null) continue;
	                postBody.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),
	                                                                                     "utf-8")).append("&");
	            }

	            if (!params.isEmpty()) {
	                postBody.deleteCharAt(postBody.length() - 1);
	            }

	            conn = (HttpsURLConnection) url.openConnection();
	            //����https
	            conn.setSSLSocketFactory(ssf);
	            // ���ó�����
	            conn.setRequestProperty("Connection", "Keep-Alive");
	            // �������ӳ�ʱ
	            conn.setConnectTimeout(1000);
	            // ���ö�ȡ��ʱ����������Ϊ3000ms����ͬʱ��������Ϣ�����������ͻ�����Э��ȷ�Ͼ���ʱ�䡱
	            conn.setReadTimeout(3000);
	            // �ύ����
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            conn.getOutputStream().write(postBody.toString().getBytes());
	            conn.getOutputStream().flush();
	            int responseCode = conn.getResponseCode();
	            if (responseCode == 200) {
	                BufferedReader bufferedReader = new BufferedReader(
	                                                                   new InputStreamReader(conn.getInputStream(), "utf-8"));
	                StringBuilder result = new StringBuilder();
	                String line;
	                while ((line = bufferedReader.readLine()) != null) {
	                    result.append(line).append("\n");
	                }

					ThirdRequestHistory thirdRequestHistory = new ThirdRequestHistory();
					thirdRequestHistory.setMethod(urlString);
					thirdRequestHistory.setPlatform(ThirdCreditPlatform.PLATFORM_TD_REQUEST.key());
					thirdRequestHistory.setParams(JSON.toJSONString(params));
					thirdRequestHistory.setResult(result.toString());
					thirdRequestHistory.setProjectId(thirdRequestParam.getProjectId());
					thirdRequestHistory.setProjectNo(thirdRequestParam.getProjectNo());
					ThirdRequestHistoryService historyService = SpringContextUtils.getBean(ThirdRequestHistoryService.class);
					historyService.create(thirdRequestHistory);
	                return JSON.parseObject(result.toString().trim(), TdPreloanSubmitResponse.class);
	            }
	        } catch (Exception e) {
	            log.error("[RiskServicePreloan] apply throw exception, details: " + e);
	        }
	        return submitResponse;
	 }

	/**
	 * 根据返回的流水号查询结果
	 * @param reportId
	 * @param thirdRequestParam
	 * @return
	 */
	public static TdPreloanQueryResponse query(String reportId, ThirdRequestParam thirdRequestParam) {
		TdPreloanQueryResponse queryResponse = new TdPreloanQueryResponse();
		try {
			String urlString = new StringBuilder().append(queryUrl).append("?partner_code=").append(PARTNER_CODE).append("&partner_key=").append(PARTNER_KEY).append("&report_id=").append(reportId).toString();
			URL url = new URL(urlString);

			conn = (HttpsURLConnection) url.openConnection();
			//����https
			conn.setSSLSocketFactory(ssf);
			// ���ó�����
			conn.setRequestProperty("Connection", "Keep-Alive");
			// �������ӳ�ʱ
			conn.setConnectTimeout(1000);
			// ���ö�ȡ��ʱ
			conn.setReadTimeout(500);
			// �ύ����
			conn.setRequestMethod("GET");
			int responseCode = conn.getResponseCode();
			if (responseCode == 200) {
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "utf-8"));
				StringBuilder result = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					result.append(line).append("\n");
				}
				ThirdRequestHistory thirdRequestHistory = new ThirdRequestHistory();
				thirdRequestHistory.setMethod(urlString);
				thirdRequestHistory.setPlatform(ThirdCreditPlatform.PLATFORM_TD_RESPONSE.key());
				thirdRequestHistory.setParams(reportId);
				thirdRequestHistory.setResult(result.toString());
				thirdRequestHistory.setProjectId(thirdRequestParam.getProjectId());
				thirdRequestHistory.setProjectNo(thirdRequestParam.getProjectNo());
				ThirdRequestHistoryService historyService = SpringContextUtils.getBean(ThirdRequestHistoryService.class);
				historyService.create(thirdRequestHistory);
				return JSON.parseObject(result.toString().trim(),TdPreloanQueryResponse.class);
			}
		} catch (Exception e) {
			log.error("[RiskServicePreloan] query throw exception, details: " + e);
		}
		return queryResponse;
	}
}
