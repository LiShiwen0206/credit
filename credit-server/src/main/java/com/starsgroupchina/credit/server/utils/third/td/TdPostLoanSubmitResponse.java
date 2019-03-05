package com.starsgroupchina.credit.server.utils.third.td;

import java.io.Serializable;

/**
 * 
* @ClassName: TdPostLoanSubmitResponse 
* @Description: 同盾风险监控返回包装类
* @author hb 
* @date 2017年11月8日 上午11:25:18 
*
 */
public class TdPostLoanSubmitResponse implements Serializable {

	/** 
	* @Fields serialVersionUID : 序列码
	*/ 
	private static final long serialVersionUID = -1997405765390575004L;
	
	/** 是否调用成功*/
	private Boolean success;
	
	/** 返回吗*/
	private String reason_code;
	
	/** 代码描述(调用成功时不包含此字段)*/
	private String reason_desc;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getReason_desc() {
		return reason_desc;
	}

	public void setReason_desc(String reason_desc) {
		this.reason_desc = reason_desc;
	}

	@Override
	public String toString() {
		return "TdPostLoanSubmitResponse [success=" + success + ", reason_code=" + reason_code + ", reason_desc="
				+ reason_desc + "]";
	}
	
}
