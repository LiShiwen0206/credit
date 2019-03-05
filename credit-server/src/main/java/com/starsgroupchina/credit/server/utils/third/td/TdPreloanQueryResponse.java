package com.starsgroupchina.credit.server.utils.third.td;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * ������������ṹ
 * @Author qbq-pc on 16/11/16.
 */
public class TdPreloanQueryResponse implements Serializable {

   
    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -7749111636229467177L;

	/**�ӿ��Ƿ���óɹ�*/
    private Boolean success= false;
    
    /**ɨ������ķ�����*/
    private JSONArray risk_items;
    
    /**�����ؽ���*/
    private JSONObject address_detect;
    
    /**������*/
    private String application_id;
    
    /**���ս��*/
    private String final_decision;
    
    /**��ǰ������ձ�����*/
    private String report_id;
    
    /**ɨ��ʱ��(UNIXʱ���,��λ����)*/
    private Long apply_time;
    
    /**����ʱ��(UNIXʱ���,��λ����)*/
    private Long report_time;
    
    /**���շ���*/
    private Integer final_score;
    
    
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public JSONArray getRisk_items() {
		return risk_items;
	}
	public void setRisk_items(JSONArray risk_items) {
		this.risk_items = risk_items;
	}
	public JSONObject getAddress_detect() {
		return address_detect;
	}
	public void setAddress_detect(JSONObject address_detect) {
		this.address_detect = address_detect;
	}
	public String getApplication_id() {
		return application_id;
	}
	public void setApplication_id(String application_id) {
		this.application_id = application_id;
	}
	public String getFinal_decision() {
		return final_decision;
	}
	public void setFinal_decision(String final_decision) {
		this.final_decision = final_decision;
	}
	public String getReport_id() {
		return report_id;
	}
	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}
	public Long getApply_time() {
		return apply_time;
	}
	public void setApply_time(Long apply_time) {
		this.apply_time = apply_time;
	}
	public Long getReport_time() {
		return report_time;
	}
	public void setReport_time(Long report_time) {
		this.report_time = report_time;
	}
	public Integer getFinal_score() {
		return final_score;
	}
	public void setFinal_score(Integer final_score) {
		this.final_score = final_score;
	}
	@Override
	public String toString() {
		return "PreloanQueryResponse [success=" + success + ", risk_items=" + risk_items + ", address_detect="
				+ address_detect + ", application_id=" + application_id + ", final_decision=" + final_decision
				+ ", report_id=" + report_id + ", apply_time=" + apply_time + ", report_time=" + report_time
				+ ", final_score=" + final_score + "]";
	}

    //ignore getter and setter
	
    
}
