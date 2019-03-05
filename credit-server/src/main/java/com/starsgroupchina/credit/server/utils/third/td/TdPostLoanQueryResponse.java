package com.starsgroupchina.credit.server.utils.third.td;

import java.io.Serializable;
import java.util.List;


/**
 * 
* @ClassName: TdPostLoanQueryResponse 
* @Description: 同盾风险报告返回结果包装类
* @author hb 
* @date 2017年11月8日 上午9:07:53 
*
 */
public class TdPostLoanQueryResponse implements Serializable {

	/** 
	* @Fields serialVersionUID : 序列码
	*/ 
	private static final long serialVersionUID = 7159936073387346324L;
	
	/** 是否调用成功(false:调用失败;true:调用 成功)*/
	private Boolean success;
	
	/** 调用完成状态码*/
	private String reason_code;
	
	/** 状态描述*/
	private String reason_desc;
	
	/** 风险列表*/
	private Data data;
	
	
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

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public static class Data implements Serializable{

		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -6507618220161511668L;
		
		/** 监控编号*/
		private String monitor_id;
		
		/** 报告编号*/
		private String report_id;
		
		/** 申请编号*/
		private String application_id;
		
		/**合作方*/
		private String partner_code;
		
		/**个人基本信息*/
		private PersonInfo person_info;
		
		/** 风险信息列表*/
		private List<RiskList> riskList;
		
		
		public String getMonitor_id() {
			return monitor_id;
		}

		public void setMonitor_id(String monitor_id) {
			this.monitor_id = monitor_id;
		}

		public String getReport_id() {
			return report_id;
		}

		public void setReport_id(String report_id) {
			this.report_id = report_id;
		}

		public String getApplication_id() {
			return application_id;
		}

		public void setApplication_id(String application_id) {
			this.application_id = application_id;
		}

		public String getPartner_code() {
			return partner_code;
		}

		public void setPartner_code(String partner_code) {
			this.partner_code = partner_code;
		}

		public PersonInfo getPerson_info() {
			return person_info;
		}

		public void setPerson_info(PersonInfo person_info) {
			this.person_info = person_info;
		}

		public List<RiskList> getRiskList() {
			return riskList;
		}

		public void setRiskList(List<RiskList> riskList) {
			this.riskList = riskList;
		}

		public static class PersonInfo implements Serializable{

			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 6572232175437730480L;
			
			/** 姓名*/
			private String name;
			
			/** 身份证*/
			private String id_number;
			
			/** 手机号*/
			private String mobile;
			
			/** 座机号*/
			private String phone;
			
			/**邮箱*/
			private String email;
			
			/** QQ号*/
			private String qq;
			
			/** 银行卡号*/
			private String card_number;
			
			/** 第一联系人姓名*/
			private String contact1_name;
			
			/** 第一联系人身份证号*/
			private String contact1_id_number;
			
			/** 第一联系人手机号码*/
			private String contact1_mobile;
			
			/** 第二联系人姓名*/
			private String contact2_name;
			
			/** 第二联系人身份证号*/
			private String contact2_id_number;
			
			/** 第二联系人手机号码*/
			private String contact2_mobile;
			
			/** 第三联系人姓名*/
			private String contact3_name;
			
			/** 第三联系人身份证号*/
			private String contact3_id_number;
			
			/** 第三联系人手机号码*/
			private String contact3_mobile;
			
			/** 第四联系人姓名*/
			private String contact4_name;
			
			/** 第四联系人身份证号*/
			private String contact4_id_number;
			
			/** 第四联系人手机号码*/
			private String contact4_mobile;
			
			/** 第五联系人姓名*/
			private String contact5_name;
			
			/** 第五联系人身份证号*/
			private String contact5_id_number;
			
			/** 第五联系人手机号码*/
			private String contact5_mobile;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getId_number() {
				return id_number;
			}

			public void setId_number(String id_number) {
				this.id_number = id_number;
			}

			public String getMobile() {
				return mobile;
			}

			public void setMobile(String mobile) {
				this.mobile = mobile;
			}

			public String getPhone() {
				return phone;
			}

			public void setPhone(String phone) {
				this.phone = phone;
			}

			public String getEmail() {
				return email;
			}

			public void setEmail(String email) {
				this.email = email;
			}

			public String getQq() {
				return qq;
			}

			public void setQq(String qq) {
				this.qq = qq;
			}

			public String getCard_number() {
				return card_number;
			}

			public void setCard_number(String card_number) {
				this.card_number = card_number;
			}

			public String getContact1_name() {
				return contact1_name;
			}

			public void setContact1_name(String contact1_name) {
				this.contact1_name = contact1_name;
			}

			public String getContact1_id_number() {
				return contact1_id_number;
			}

			public void setContact1_id_number(String contact1_id_number) {
				this.contact1_id_number = contact1_id_number;
			}

			public String getContact1_mobile() {
				return contact1_mobile;
			}

			public void setContact1_mobile(String contact1_mobile) {
				this.contact1_mobile = contact1_mobile;
			}

			public String getContact2_name() {
				return contact2_name;
			}

			public void setContact2_name(String contact2_name) {
				this.contact2_name = contact2_name;
			}

			public String getContact2_id_number() {
				return contact2_id_number;
			}

			public void setContact2_id_number(String contact2_id_number) {
				this.contact2_id_number = contact2_id_number;
			}

			public String getContact2_mobile() {
				return contact2_mobile;
			}

			public void setContact2_mobile(String contact2_mobile) {
				this.contact2_mobile = contact2_mobile;
			}

			public String getContact3_name() {
				return contact3_name;
			}

			public void setContact3_name(String contact3_name) {
				this.contact3_name = contact3_name;
			}

			public String getContact3_id_number() {
				return contact3_id_number;
			}

			public void setContact3_id_number(String contact3_id_number) {
				this.contact3_id_number = contact3_id_number;
			}

			public String getContact3_mobile() {
				return contact3_mobile;
			}

			public void setContact3_mobile(String contact3_mobile) {
				this.contact3_mobile = contact3_mobile;
			}

			public String getContact4_name() {
				return contact4_name;
			}

			public void setContact4_name(String contact4_name) {
				this.contact4_name = contact4_name;
			}

			public String getContact4_id_number() {
				return contact4_id_number;
			}

			public void setContact4_id_number(String contact4_id_number) {
				this.contact4_id_number = contact4_id_number;
			}

			public String getContact4_mobile() {
				return contact4_mobile;
			}

			public void setContact4_mobile(String contact4_mobile) {
				this.contact4_mobile = contact4_mobile;
			}

			public String getContact5_name() {
				return contact5_name;
			}

			public void setContact5_name(String contact5_name) {
				this.contact5_name = contact5_name;
			}

			public String getContact5_id_number() {
				return contact5_id_number;
			}

			public void setContact5_id_number(String contact5_id_number) {
				this.contact5_id_number = contact5_id_number;
			}

			public String getContact5_mobile() {
				return contact5_mobile;
			}

			public void setContact5_mobile(String contact5_mobile) {
				this.contact5_mobile = contact5_mobile;
			}
		}
		
		public static class RiskList implements Serializable{

			/** 
			* @Fields serialVersionUID : 序列码 
			*/ 
			private static final long serialVersionUID = 8705310934948602073L;
			
			/** 扫描时间(精确到天)*/
			private Long gmt_create;
			
			/** 风险ID*/
			private String uuid;
			
			/** "坏"客户占比*/
			private String bad_rate;
			
			/** 用户行为分*/
			private Integer score;
			
			/** 逾期风险等级(低，较低，中等，较高，高)*/
			private String overdue_level;
			
			private List<RiskDetail> risk_detail;
			
			public String getBad_rate() {
				return bad_rate;
			}


			public void setBad_rate(String bad_rate) {
				this.bad_rate = bad_rate;
			}


			public Integer getScore() {
				return score;
			}


			public void setScore(Integer score) {
				this.score = score;
			}


			public Long getGmt_create() {
				return gmt_create;
			}


			public void setGmt_create(Long gmt_create) {
				this.gmt_create = gmt_create;
			}


			public String getUuid() {
				return uuid;
			}


			public void setUuid(String uuid) {
				this.uuid = uuid;
			}


			public String getOverdue_level() {
				return overdue_level;
			}


			public void setOverdue_level(String overdue_level) {
				this.overdue_level = overdue_level;
			}

			public List<RiskDetail> getRisk_detail() {
				return risk_detail;
			}


			public void setRisk_detail(List<RiskDetail> risk_detail) {
				this.risk_detail = risk_detail;
			}




			public static class RiskDetail implements Serializable{

				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -6349202542179463084L;
				
				/** 风险类型*/
				private String fraud_type;
				
				/** 风险详情*/
				private String detail;
				
				/** 监控字段*/
				private String monitor_field;
				
				/** 监控字段值*/
				private String monitor_value;
				
				/** 规则名称*/
				private String rule_name;
				
				/** 规则类型*/
				private String rule_type;
				
				/** 证据类型*/
				private String evidence_type;
				
				/** 监控字段对应的中文描述*/
				private String monitor_field_zh;
				
				/** 证据类型对应的中文描述*/
				private String evidence_type_zh;
				
				/** 风险类型对应的中文描述*/
				private String fraud_type_zh;
				
				/** 模糊身份证号*/
				private String fuzzy_id;
				
				/** 风险类型*/
				private List<String> risk_type;
				
				/** 风险类型列表对应的中文描述*/
				private List<String> risk_type_zh;
				
				/** 风险等级*/
				private String risk_level;
				
				/** 风险等级对应的中文描述*/
				private String risk_level_zh;
				
				/** 计算类型(信贷逾期名单的计算类 型，overdueAmount， count，platformCount)*/
				private String cal_type;
				
				/** 计算值(根据计算类型计算得到的值)*/
				private String cal_value;
				
				/** 第一行业类型*/
				private List<String> industry;
				
				/** 第一行业对应的中文描述*/
				private List<String> industry_zh;
				
				/** 第二行业类型*/
				private List<String> industry_second;
				
				/** 第二行业对应的中文描述*/
				private List<String> industry_second_zh;
				
				/** 命中时间*/
				private String hit_time;
				
				/** 风险详情类标*/
				private List<Details> details;
				
				/** 新增平台总数*/
				private Integer new_platform_total;
				
				/**新增行业平台详情*/
				private List<PlatformIndustryDetails> platform_industry_details;
				
				
				public Integer getNew_platform_total() {
					return new_platform_total;
				}

				public void setNew_platform_total(Integer new_platform_total) {
					this.new_platform_total = new_platform_total;
				}

				public String getFraud_type() {
					return fraud_type;
				}

				public void setFraud_type(String fraud_type) {
					this.fraud_type = fraud_type;
				}

				public String getDetail() {
					return detail;
				}

				public void setDetail(String detail) {
					this.detail = detail;
				}

				public String getMonitor_field() {
					return monitor_field;
				}

				public void setMonitor_field(String monitor_field) {
					this.monitor_field = monitor_field;
				}

				public String getMonitor_value() {
					return monitor_value;
				}

				public void setMonitor_value(String monitor_value) {
					this.monitor_value = monitor_value;
				}

				public String getRule_name() {
					return rule_name;
				}

				public void setRule_name(String rule_name) {
					this.rule_name = rule_name;
				}

				public String getRule_type() {
					return rule_type;
				}

				public void setRule_type(String rule_type) {
					this.rule_type = rule_type;
				}

				public String getEvidence_type() {
					return evidence_type;
				}

				public void setEvidence_type(String evidence_type) {
					this.evidence_type = evidence_type;
				}

				public String getMonitor_field_zh() {
					return monitor_field_zh;
				}

				public void setMonitor_field_zh(String monitor_field_zh) {
					this.monitor_field_zh = monitor_field_zh;
				}

				public String getEvidence_type_zh() {
					return evidence_type_zh;
				}

				public void setEvidence_type_zh(String evidence_type_zh) {
					this.evidence_type_zh = evidence_type_zh;
				}

				public String getFraud_type_zh() {
					return fraud_type_zh;
				}

				public void setFraud_type_zh(String fraud_type_zh) {
					this.fraud_type_zh = fraud_type_zh;
				}

				public String getFuzzy_id() {
					return fuzzy_id;
				}

				public void setFuzzy_id(String fuzzy_id) {
					this.fuzzy_id = fuzzy_id;
				}

				public List<String> getRisk_type() {
					return risk_type;
				}

				public void setRisk_type(List<String> risk_type) {
					this.risk_type = risk_type;
				}

				public List<String> getRisk_type_zh() {
					return risk_type_zh;
				}

				public void setRisk_type_zh(List<String> risk_type_zh) {
					this.risk_type_zh = risk_type_zh;
				}

				
				public String getRisk_level() {
					return risk_level;
				}

				public void setRisk_level(String risk_level) {
					this.risk_level = risk_level;
				}

				public String getRisk_level_zh() {
					return risk_level_zh;
				}

				public void setRisk_level_zh(String risk_level_zh) {
					this.risk_level_zh = risk_level_zh;
				}

				public String getCal_type() {
					return cal_type;
				}

				public void setCal_type(String cal_type) {
					this.cal_type = cal_type;
				}

				public String getCal_value() {
					return cal_value;
				}

				public void setCal_value(String cal_value) {
					this.cal_value = cal_value;
				}

				public List<String> getIndustry() {
					return industry;
				}

				public void setIndustry(List<String> industry) {
					this.industry = industry;
				}

				public List<String> getIndustry_zh() {
					return industry_zh;
				}

				public void setIndustry_zh(List<String> industry_zh) {
					this.industry_zh = industry_zh;
				}

				public List<String> getIndustry_second() {
					return industry_second;
				}

				public void setIndustry_second(List<String> industry_second) {
					this.industry_second = industry_second;
				}

				public List<String> getIndustry_second_zh() {
					return industry_second_zh;
				}

				public void setIndustry_second_zh(List<String> industry_second_zh) {
					this.industry_second_zh = industry_second_zh;
				}

				public String getHit_time() {
					return hit_time;
				}

				public void setHit_time(String hit_time) {
					this.hit_time = hit_time;
				}

				public List<Details> getDetails() {
					return details;
				}

				public void setDetails(List<Details> details) {
					this.details = details;
				}


				public List<PlatformIndustryDetails> getPlatform_industry_details() {
					return platform_industry_details;
				}

				public void setPlatform_industry_details(List<PlatformIndustryDetails> platform_industry_details) {
					this.platform_industry_details = platform_industry_details;
				}

				public static class PlatformIndustry implements Serializable{

					/** 
					* @Fields serialVersionUID : 序列码
					*/ 
					private static final long serialVersionUID = -4879484353486804047L;
					
					/** 行业*/
					private String industry;
					
					/** 行业类型对应的中文描述*/
					private String industry_zh;
					
					/** 行业平台总数*/
					private String total;

					public String getIndustry() {
						return industry;
					}

					public void setIndustry(String industry) {
						this.industry = industry;
					}

					public String getIndustry_zh() {
						return industry_zh;
					}

					public void setIndustry_zh(String industry_zh) {
						this.industry_zh = industry_zh;
					}

					public String getTotal() {
						return total;
					}

					public void setTotal(String total) {
						this.total = total;
					}
					
				}
				
				public static class PlatformIndustryDetails implements Serializable{

					/** 
					* @Fields serialVersionUID : 序列码
					*/ 
					private static final long serialVersionUID = 733271834444931851L;
					
					/** 命中时间*/
					private String hit_time;
					
					/** 命中行业详情*/
					private List<PlatformIndustry> hitDetails;

					public String getHit_time() {
						return hit_time;
					}

					public void setHit_time(String hit_time) {
						this.hit_time = hit_time;
					}

					public List<PlatformIndustry> getHitDetails() {
						return hitDetails;
					}

					public void setHitDetails(List<PlatformIndustry> hitDetails) {
						this.hitDetails = hitDetails;
					}
				}
				
				public static class Details implements Serializable{

					/** 
					* @Fields serialVersionUID : 序列码
					*/ 
					private static final long serialVersionUID = 2451747641906100619L;
					
					/** 逾期金额*/
					private String overdue_amount_range;
					
					/** 逾期笔数*/
					private Integer overdue_count;
					
					/** 逾期天数*/
					private String overdue_day_range;

					public String getOverdue_amount_range() {
						return overdue_amount_range;
					}

					public void setOverdue_amount_range(String overdue_amount_range) {
						this.overdue_amount_range = overdue_amount_range;
					}

					public Integer getOverdue_count() {
						return overdue_count;
					}

					public void setOverdue_count(Integer overdue_count) {
						this.overdue_count = overdue_count;
					}

					public String getOverdue_day_range() {
						return overdue_day_range;
					}

					public void setOverdue_day_range(String overdue_day_range) {
						this.overdue_day_range = overdue_day_range;
					}
					
					
				}
			}
		}
	}


	@Override
	public String toString() {
		return "TdPostLoanQueryResponse [success=" + success + ", reason_code=" + reason_code + ", reason_desc="
				+ reason_desc + ", data=" + data + "]";
	}
	
}
