package com.starsgroupchina.credit.server.utils.third.td;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class TdPreloanSubmitResponse implements Serializable {
	private static final long serialVersionUID = 4152462611121573434L;
	/**�ӿ��Ƿ���óɹ�*/
	private Boolean success= false;
    
	/**��ǰ������ձ�����*/
	private String report_id;
	
	/**��������*/
    private String reason_desc;
    
    /**�������*/
    private String reason_code;
    
    public String getReason_desc() {
		return reason_desc;
	}


	public void setReason_desc(String reason_desc) {
		this.reason_desc = reason_desc;
	}


	public String getReason_code() {
		return reason_code;
	}


	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}


	public Boolean getSuccess() {
		return success;
	}


	public void setSuccess(Boolean success) {
		this.success = success;
	}


	public String getReport_id() {
		return report_id;
	}


	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}


	@Override
    public String toString() {   
        if(success==true) {
        	return new ToStringBuilder(this)
                    .append("success", success)
                    .append("report_id", report_id)
                    .toString();
        }else{
        	return new ToStringBuilder(this)
                    .append("success", success)
                    .append("reason_desc",reason_desc)
                    .append("reason_code",reason_code)
                    .toString();
        }
    }
}
