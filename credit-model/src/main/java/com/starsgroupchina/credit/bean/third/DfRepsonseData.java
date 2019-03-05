package com.starsgroupchina.credit.bean.third;

import java.io.Serializable;
import java.util.List;

public class DfRepsonseData implements Serializable{
	
	
	/** 
	* @Fields serialVersionUID : 序列码
	*/ 
	private static final long serialVersionUID = 1838121703440033508L;

	/** 结果体信息*/
    private Body body;
    
    /** 结果头信息*/
    private Header header;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public static class Body implements Serializable{
    	/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -70599678856888411L;
		/** 报告数据节点*/
        private Data data;

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
			private static final long serialVersionUID = -1459635065670203087L;

			/** 自定义主题*/
            private B0070 b0070;
            
            /** 手机号码互联网标识模块*/
            private B0006 b0006;
            
            /** 固话号码互联网标识*/
            private B0007 b0007;
            /** 手机号码归属人模块*/
            private B0003 b0003;
            
            /** 社保信息核查（暂时只支持深圳）*/
            private B0030 b0030;
            
            /** 社交关键字信息核查*/
            private B0041 b0041;
            
            /** P2P黑名单信息*/
            private B0040 b0040;
            
            /** 企业工商信息*/
            private B0063 b0063;
            
            /** 公积金信息核查（暂时只支持深圳）*/
            private B0029 b0029;
            
            /** 银行卡信息核查*/
            private B0028 b0028;
            
            /** 保单信息验证*/
            private B0039 b0039;
            
            /** 法院被执行人信息*/
            private B0023 b0023;
            
            /** 个人电话号码查询量统计信息*/
            private B0067 b0067;
            
            /** 个人地址查询量统计信息*/
            private B0066 b0066;
            
            /**公司查询量统计信息*/
            private B0065 b0065;
            
            /** 全国组织机构信息*/
            private B0031 b0031;
            
            /** 固话号码归属地*/
            private B0005 b0005;
            
            /** 薪资评估*/
            private B0027 b0027;
            
            /** 手机号码归属地*/
            private B0004 b0004;
            
            /** 房产估价*/
            private B0036 b0036;

            /** 地址信息核查*/
            private B0002 b0002;
            
            /** 法院失信被执行人信息*/
            private B0024 b0024;
            
            /** 欠税信息*/
            private B0035 b0035;
            
            /** 个人证件号查询量统计信息*/
            private B0068 b0068;
            
            /** 身份证信息核查*/
            private B0025 b0025;
            
            
            public B0025 getB0025() {
				return b0025;
			}

			public void setB0025(B0025 b0025) {
				this.b0025 = b0025;
			}

			public B0007 getB0007() {
				return b0007;
			}

			public void setB0007(B0007 b0007) {
				this.b0007 = b0007;
			}

			public B0030 getB0030() {
				return b0030;
			}

			public void setB0030(B0030 b0030) {
				this.b0030 = b0030;
			}

			public B0041 getB0041() {
				return b0041;
			}

			public void setB0041(B0041 b0041) {
				this.b0041 = b0041;
			}

			public B0040 getB0040() {
				return b0040;
			}

			public void setB0040(B0040 b0040) {
				this.b0040 = b0040;
			}

			public B0063 getB0063() {
				return b0063;
			}

			public void setB0063(B0063 b0063) {
				this.b0063 = b0063;
			}

			public B0029 getB0029() {
				return b0029;
			}

			public void setB0029(B0029 b0029) {
				this.b0029 = b0029;
			}

			public B0028 getB0028() {
				return b0028;
			}

			public void setB0028(B0028 b0028) {
				this.b0028 = b0028;
			}

			public B0039 getB0039() {
				return b0039;
			}

			public void setB0039(B0039 b0039) {
				this.b0039 = b0039;
			}

			public B0023 getB0023() {
				return b0023;
			}

			public void setB0023(B0023 b0023) {
				this.b0023 = b0023;
			}

			public B0067 getB0067() {
				return b0067;
			}

			public void setB0067(B0067 b0067) {
				this.b0067 = b0067;
			}

			public B0066 getB0066() {
				return b0066;
			}

			public void setB0066(B0066 b0066) {
				this.b0066 = b0066;
			}

			public B0065 getB0065() {
				return b0065;
			}

			public void setB0065(B0065 b0065) {
				this.b0065 = b0065;
			}

			public B0031 getB0031() {
				return b0031;
			}

			public void setB0031(B0031 b0031) {
				this.b0031 = b0031;
			}

			public B0005 getB0005() {
				return b0005;
			}

			public void setB0005(B0005 b0005) {
				this.b0005 = b0005;
			}

			public B0027 getB0027() {
				return b0027;
			}

			public void setB0027(B0027 b0027) {
				this.b0027 = b0027;
			}

			public B0004 getB0004() {
				return b0004;
			}

			public void setB0004(B0004 b0004) {
				this.b0004 = b0004;
			}

			public B0036 getB0036() {
				return b0036;
			}

			public void setB0036(B0036 b0036) {
				this.b0036 = b0036;
			}

			public B0002 getB0002() {
				return b0002;
			}

			public void setB0002(B0002 b0002) {
				this.b0002 = b0002;
			}

			public B0024 getB0024() {
				return b0024;
			}

			public void setB0024(B0024 b0024) {
				this.b0024 = b0024;
			}

			public B0035 getB0035() {
				return b0035;
			}

			public void setB0035(B0035 b0035) {
				this.b0035 = b0035;
			}

			public B0068 getB0068() {
				return b0068;
			}

			public void setB0068(B0068 b0068) {
				this.b0068 = b0068;
			}

			public B0070 getB0070() {
                return b0070;
            }

            public void setB0070(B0070 b0070) {
                this.b0070 = b0070;
            }

            public B0006 getB0006() {
                return b0006;
            }

            public void setB0006(B0006 b0006) {
                this.b0006 = b0006;
            }

            public B0003 getB0003() {
                return b0003;
            }

            public void setB0003(B0003 b0003) {
                this.b0003 = b0003;
            }
            
            
            public static class B0070 implements Serializable{
            	
            	/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 3818271879505788201L;

				/** 模块描述*/
                private String description;
                
                /** 结果项列表节点*/
                private List<Items> items;

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public List<Items> getItems() {
                    return items;
                }

                public void setItems(List<Items> items) {
                    this.items = items;
                }

                public static class Items implements Serializable{
                	
                	/** 
					* @Fields serialVersionUID : 序列码
					*/ 
					private static final long serialVersionUID = -5323900329362543792L;

					/** 结果码：700/701/702/703/704*/
                    private int code;
                    
                    /** 参数记录块*/
                    private Param param;
                    
                    /** 结果码描述*/
                    private String message;
                    
                    /** 结果项描述节点*/
                    private List<Result> result;

                    public int getCode() {
                        return code;
                    }

                    public void setCode(int code) {
                        this.code = code;
                    }

                    public Param getParam() {
                        return param;
                    }

                    public void setParam(Param param) {
                        this.param = param;
                    }

                    public String getMessage() {
                        return message;
                    }

                    public void setMessage(String message) {
                        this.message = message;
                    }

                    public List<Result> getResult() {
                        return result;
                    }

                    public void setResult(List<Result> result) {
                        this.result = result;
                    }

                    public static class Param implements Serializable{
                    	
                    	/** 
						* @Fields serialVersionUID : 序列码
						*/ 
						private static final long serialVersionUID = -3643348932882849195L;

						/**关键字关键字*/
                        private String subject_kw;
                        
                        /** 搜索关键字*/
                        private String kw;

                        public String getSubject_kw() {
                            return subject_kw;
                        }

                        public void setSubject_kw(String subject_kw) {
                            this.subject_kw = subject_kw;
                        }

                        public String getKw() {
                            return kw;
                        }

                        public void setKw(String kw) {
                            this.kw = kw;
                        }
                    }

                    public static class Result implements Serializable{
                    	
                    	/** 
						* @Fields serialVersionUID : 序列码
						*/ 
						private static final long serialVersionUID = -310814105303515079L;

						/** 更新时间*/
                        private String update_time;
                        
                        /** 发布时间*/
                        private String publish_time;
                        
                        /** 原文链接*/
                        private String link;
                        
                        /** 主要内容*/
                        private String main_content;
                        
                        /** 标题*/
                        private String title;
                        
                        /** 搜索引擎来源*/
                        private String search_engine;

                        public String getUpdate_time() {
                            return update_time;
                        }

                        public void setUpdate_time(String update_time) {
                            this.update_time = update_time;
                        }

                        public String getPublish_time() {
                            return publish_time;
                        }

                        public void setPublish_time(String publish_time) {
                            this.publish_time = publish_time;
                        }

                        public String getLink() {
                            return link;
                        }

                        public void setLink(String link) {
                            this.link = link;
                        }

                        public String getMain_content() {
                            return main_content;
                        }

                        public void setMain_content(String main_content) {
                            this.main_content = main_content;
                        }

                        public String getTitle() {
                            return title;
                        }

                        public void setTitle(String title) {
                            this.title = title;
                        }

                        public String getSearch_engine() {
                            return search_engine;
                        }

                        public void setSearch_engine(String search_engine) {
                            this.search_engine = search_engine;
                        }
                    }
                }
            }

            public static class B0006 implements Serializable{
            	
            	/** 
				* @Fields serialVersionUID : 序列码 
				*/ 
				private static final long serialVersionUID = -3715350556268516735L;

				/** 模块描述*/
                private String description;
                
                /** 结果项列表节点*/
                private List<ItemsX> items;

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public List<ItemsX> getItems() {
                    return items;
                }

                public void setItems(List<ItemsX> items) {
                    this.items = items;
                }

                public static class ItemsX implements Serializable{
                	
                	/** 
					* @Fields serialVersionUID :序列码
					*/ 
					private static final long serialVersionUID = 6115145600207911320L;

					/** 结果码：700/701/702/703/704*/
                    private int code;
                    
                    /** 参数记录块*/
                    private ParamX param;
                    
                    /** 结果码描述*/
                    private String message;
                    
                    /** 结果项描述节点*/
                    private List<ResultX> result;

                    public int getCode() {
                        return code;
                    }

                    public void setCode(int code) {
                        this.code = code;
                    }

                    public ParamX getParam() {
                        return param;
                    }

                    public void setParam(ParamX param) {
                        this.param = param;
                    }

                    public String getMessage() {
                        return message;
                    }

                    public void setMessage(String message) {
                        this.message = message;
                    }

                    public List<ResultX> getResult() {
                        return result;
                    }

                    public void setResult(List<ResultX> result) {
                        this.result = result;
                    }

                    public static class ParamX implements Serializable{
                    	
                    	/** 
						* @Fields serialVersionUID : 序列码
						*/ 
						private static final long serialVersionUID = -6896344445125631138L;

						/** 电话号码*/
                        private String phone_no;
                        
                        /** 手机所属人类型：personalMobilePhone（申请人）、linkmanMobilePhone（联系人）*/
                        private String type;

                        public String getPhone_no() {
                            return phone_no;
                        }

                        public void setPhone_no(String phone_no) {
                            this.phone_no = phone_no;
                        }

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }
                    }

                    public static class ResultX implements Serializable{
                    	
                    	/** 
						* @Fields serialVersionUID : 序列码
						*/ 
						private static final long serialVersionUID = 6983942269128670523L;

						/** 更新时间*/
                        private String update_time;
                        
                        /** 是否为诈骗手机号：false(非诈骗),true(诈骗)*/
                        private String is_fraud;
                        
                        /** 手机号标识，可能标识为某公司、某个人或其他*/
                        private String tag;

                        public String getUpdate_time() {
                            return update_time;
                        }

                        public void setUpdate_time(String update_time) {
                            this.update_time = update_time;
                        }

                        public String getIs_fraud() {
                            return is_fraud;
                        }

                        public void setIs_fraud(String is_fraud) {
                            this.is_fraud = is_fraud;
                        }

                        public String getTag() {
                            return tag;
                        }

                        public void setTag(String tag) {
                            this.tag = tag;
                        }
                    }
                }
            }
            	
            public static class B0007 implements Serializable{
            		
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -4109550389056646327L;

				/** 模块描述*/
                private String description;
                
                /** 结果项列表节点*/
                private List<ItemsB0007> items;

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public List<ItemsB0007> getItems() {
                    return items;
                }

                public void setItems(List<ItemsB0007> items) {
                    this.items = items;
                }

                public static class ItemsB0007 implements Serializable{
                	
					/** 
					* @Fields serialVersionUID : 序列码
					*/ 
					private static final long serialVersionUID = -7293096845436530144L;

					/** 结果码：700/701/702/703/704*/
                    private int code;
                    
                    /** 参数记录块*/
                    private ParamB0007 param;
                    
                    /** 结果码描述*/
                    private String message;
                    
                    /** 结果项描述节点*/
                    private List<ResultB0007> result;

                    public int getCode() {
                        return code;
                    }

                    public void setCode(int code) {
                        this.code = code;
                    }

                    public ParamB0007 getParam() {
                        return param;
                    }

                    public void setParam(ParamB0007 param) {
                        this.param = param;
                    }

                    public String getMessage() {
                        return message;
                    }

                    public void setMessage(String message) {
                        this.message = message;
                    }

                    public List<ResultB0007> getResult() {
                        return result;
                    }

                    public void setResult(List<ResultB0007> result) {
                        this.result = result;
                    }

                    public static class ParamB0007 implements Serializable{
						/** 
						* @Fields serialVersionUID : 序列码
						*/ 
						private static final long serialVersionUID = 912495826294849623L;

						/** 电话号码*/
                        private String phone_no;
                        
                        /** 手机所属人类型：personalMobilePhone（申请人）、linkmanMobilePhone（联系人）*/
                        private String type;

                        public String getPhone_no() {
                            return phone_no;
                        }

                        public void setPhone_no(String phone_no) {
                            this.phone_no = phone_no;
                        }

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }
                    }

                    public static class ResultB0007 implements Serializable{
						/** 
						* @Fields serialVersionUID : 序列码
						*/ 
						private static final long serialVersionUID = -6369718389572962622L;

						/** 更新时间*/
                        private String update_time;
                        
                        /** 是否为诈骗手机号：false(非诈骗),true(诈骗)*/
                        private String is_fraud;
                        
                        /** 手机号标识，可能标识为某公司、某个人或其他*/
                        private String tag;

                        public String getUpdate_time() {
                            return update_time;
                        }

                        public void setUpdate_time(String update_time) {
                            this.update_time = update_time;
                        }

                        public String getIs_fraud() {
                            return is_fraud;
                        }

                        public void setIs_fraud(String is_fraud) {
                            this.is_fraud = is_fraud;
                        }

                        public String getTag() {
                            return tag;
                        }

                        public void setTag(String tag) {
                            this.tag = tag;
                        }
                    }
                }
            }
            
            
            
            public static class B0003 implements Serializable{
            	
            	/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -7449152698097573822L;

				/** 模块描述*/
                private String description;
                
                /** 结果项列表节点*/
                private List<ItemsXX> items;

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public List<ItemsXX> getItems() {
                    return items;
                }

                public void setItems(List<ItemsXX> items) {
                    this.items = items;
                }

                public static class ItemsXX implements Serializable{
                	
                	/** 
					* @Fields serialVersionUID : 序列码
					*/ 
					private static final long serialVersionUID = 3195347300761599692L;

					/** 结果码：700/701/702/703/704*/
                    private int code;
                    
                    /** 参数记录块*/
                    private ParamXX param;
                    
                    /** 结果码描述*/
                    private String message;
                    
                    /** 结果项描述节点*/
                    private List<ResultXX> result;

                    public int getCode() {
                        return code;
                    }

                    public void setCode(int code) {
                        this.code = code;
                    }

                    public ParamXX getParam() {
                        return param;
                    }

                    public void setParam(ParamXX param) {
                        this.param = param;
                    }

                    public String getMessage() {
                        return message;
                    }

                    public void setMessage(String message) {
                        this.message = message;
                    }

                    public List<ResultXX> getResult() {
                        return result;
                    }

                    public void setResult(List<ResultXX> result) {
                        this.result = result;
                    }

                    public static class ParamXX implements Serializable{
                    	
                    	/** 
						* @Fields serialVersionUID : 序列码
						*/ 
						private static final long serialVersionUID = -8325126281030602482L;

						/** 手机号*/
                        private String phone_no;
                        
                        /** 手机所属人类型：personalMobilePhone（申请人）、linkmanMobilePhone（联系人）*/
                        private String type;

                        public String getPhone_no() {
                            return phone_no;
                        }

                        public void setPhone_no(String phone_no) {
                            this.phone_no = phone_no;
                        }

                        public String getType() {
                            return type;
                        }

                        public void setType(String type) {
                            this.type = type;
                        }
                    }
                    public static class ResultXX implements Serializable {
                    	
                    	/** 
						* @Fields serialVersionUID : 序列码
						*/ 
						private static final long serialVersionUID = 3149057208810936607L;

						/** 更新时间*/
                        private String update_time;
                        
                        /** 是否为诈骗手机号：false(非诈骗),true(诈骗)*/
                        private String owner;

                        public String getUpdate_time() {
                            return update_time;
                        }

                        public void setUpdate_time(String update_time) {
                            this.update_time = update_time;
                        }

						public String getOwner() {
							return owner;
						}

						public void setOwner(String owner) {
							this.owner = owner;
						}

                    }
                }
            }
        }
    }
    
	public static class B0030 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -7884759240995440258L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0030> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0030> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0030> items) {
            this.items = items;
        }

        public static class ItemsB0030 implements Serializable{
        	
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 6531233133231099422L;

			/** 结果码：700/701/702/703/704*/
            private int code;
            
            /** 参数记录块*/
            private ParamB0030 param;
            
            /** 结果码描述*/
            private String message;
            
            /** 结果项描述节点*/
            private List<ResultB0030> result;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public ParamB0030 getParam() {
                return param;
            }

            public void setParam(ParamB0030 param) {
                this.param = param;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public List<ResultB0030> getResult() {
                return result;
            }

            public void setResult(List<ResultB0030> result) {
                this.result = result;
            }

            public static class ParamB0030 implements Serializable{
            	
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -5888429603340503808L;

				/** 证件号*/
                private String id_card_no;
                
                /** 姓名*/
                private String name;
                
                /** 社保电脑号 */
                private String computer_no;

                
				public String getId_card_no() {
					return id_card_no;
				}

				public void setId_card_no(String id_card_no) {
					this.id_card_no = id_card_no;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getComputer_no() {
					return computer_no;
				}

				public void setComputer_no(String computer_no) {
					this.computer_no = computer_no;
				}
            }
            public static class ResultB0030 implements Serializable {
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -6541796589416093001L;

				/** 养老余额 */
                private String pension_account_balance;
                
                /** 参保状态*/
                private String payment_status;
                
                /** 工伤参保状态*/
                	private String workinjury_insurance_status;
                	
                	/** 养老参保状态*/
                	private String pension_insurance_status;
                	
                	/** 医保余额*/
                private String medical_account_balance;	
                
                /** 医疗参保状态*/
                private String medical_insurance_status;
                
                /** 更新时间*/
                private String update_time;

				public String getPension_account_balance() {
					return pension_account_balance;
				}

				public void setPension_account_balance(String pension_account_balance) {
					this.pension_account_balance = pension_account_balance;
				}

				public String getPayment_status() {
					return payment_status;
				}

				public void setPayment_status(String payment_status) {
					this.payment_status = payment_status;
				}

				public String getWorkinjury_insurance_status() {
					return workinjury_insurance_status;
				}

				public void setWorkinjury_insurance_status(String workinjury_insurance_status) {
					this.workinjury_insurance_status = workinjury_insurance_status;
				}

				public String getPension_insurance_status() {
					return pension_insurance_status;
				}

				public void setPension_insurance_status(String pension_insurance_status) {
					this.pension_insurance_status = pension_insurance_status;
				}

				public String getMedical_account_balance() {
					return medical_account_balance;
				}

				public void setMedical_account_balance(String medical_account_balance) {
					this.medical_account_balance = medical_account_balance;
				}

				public String getMedical_insurance_status() {
					return medical_insurance_status;
				}

				public void setMedical_insurance_status(String medical_insurance_status) {
					this.medical_insurance_status = medical_insurance_status;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
            }
        }
    }
	
	public static class B0041 implements Serializable{
		
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = 2176234218505886176L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0041> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0041> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0041> items) {
            this.items = items;
        }
		public static class ItemsB0041 implements Serializable{
	    	
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 5586532522166825075L;
	
			/** 结果码：700/701/702/703/704*/
	        private int code;
	        
	        /** 参数记录块*/
	        private ParamB0041 param;
	        
	        /** 结果码描述*/
	        private String message;
	        
	        /** 结果项描述节点*/
	        private List<ResultB0041> result;
	
	        public int getCode() {
	            return code;
	        }
	
	        public void setCode(int code) {
	            this.code = code;
	        }
	
	        public ParamB0041 getParam() {
	            return param;
	        }
	
	        public void setParam(ParamB0041 param) {
	            this.param = param;
	        }
	
	        public String getMessage() {
	            return message;
	        }
	
	        public void setMessage(String message) {
	            this.message = message;
	        }
	
	        public List<ResultB0041> getResult() {
	            return result;
	        }
	
	        public void setResult(List<ResultB0041> result) {
	            this.result = result;
	        }
	
	        public static class ParamB0041 implements Serializable{
	        	
				/** 
				* @Fields serialVersionUID : 序列码 
				*/ 
				private static final long serialVersionUID = -360665726626575733L;
	
				/** 搜索关键字*/
	            private String kw;
	
				public String getKw() {
					return kw;
				}
	
				public void setKw(String kw) {
					this.kw = kw;
				}
	        }
	        public static class ResultB0041 implements Serializable {
	
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -4402177915378138187L;
				
				/** 发布时间*/
				private String publish_time;
				
				/** 信息来源*/
				private String source;
				
				/** 内容摘要*/
				private String content;
				
				/** 更新时间*/
				private String update_time;
	
				public String getPublish_time() {
					return publish_time;
				}
	
				public void setPublish_time(String publish_time) {
					this.publish_time = publish_time;
				}
	
				public String getSource() {
					return source;
				}
	
				public void setSource(String source) {
					this.source = source;
				}
	
				public String getContent() {
					return content;
				}
	
				public void setContent(String content) {
					this.content = content;
				}
	
				public String getUpdate_time() {
					return update_time;
				}
	
				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
	        }
		}
	}
	
	public static class B0040 implements Serializable{
			
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -6307707720814043460L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0040> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0040> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0040> items) {
            this.items = items;
        }
		public static class ItemsB0040 implements Serializable{
	    		
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 5586532522166825075L;
	
			/** 结果码：700/701/702/703/704*/
	        private int code;
	        
	        /** 参数记录块*/
	        private ParamB0040 param;
	        
	        /** 结果码描述*/
	        private String message;
	        
	        /** 结果项描述节点*/
	        private List<ResultB0040> result;
	
	        public int getCode() {
	            return code;
	        }
	
	        public void setCode(int code) {
	            this.code = code;
	        }
	
	        public ParamB0040 getParam() {
	            return param;
	        }
	
	        public void setParam(ParamB0040 param) {
	            this.param = param;
	        }
	
	        public String getMessage() {
	            return message;
	        }
	
	        public void setMessage(String message) {
	            this.message = message;
	        }
	
	        public List<ResultB0040> getResult() {
	            return result;
	        }
	
	        public void setResult(List<ResultB0040> result) {
	            this.result = result;
	        }
	
	        public static class ParamB0040 implements Serializable{
	        	
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -360665726626575733L;
	
				/** 申请人证件号*/
	            private String id_card_no;
	            
	            /** 申请人姓名*/
	            private String name;
	
				public String getId_card_no() {
					return id_card_no;
				}
	
				public void setId_card_no(String id_card_no) {
					this.id_card_no = id_card_no;
				}
	
				public String getName() {
					return name;
				}
	
				public void setName(String name) {
					this.name = name;
				}
	            
	        }
	        public static class ResultB0040 implements Serializable {
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -4402177915378138187L;
				
				/** 更新时间*/
				private String update_time;
				
				/** 家庭地址*/
				private String address;
				
				/** 单位地址*/
				private String comp_addr;
				
				/** 逾期天数*/
				private String overdue_day;
				
				/** 借款时间*/
				private String borrow_date;
				
				/** 来源*/
				private String source;
				
				/** 曝光内容*/
				private String content;
				
				/** 本金*/
				private String principal;
				
				/** 欠款总额*/
				private String debts;
				
				/** 家庭电话*/
				private String home_tel;
				
				/** 工作电话*/
				private String work_tel;
				
				/** 手机号码*/
				private String mobile_phone;
				
				/** 网站垫还期数*/
				private String web_pay_period;
				
				/** 工作单位*/
				private String company;
				
				/** 逾期总罚息*/
				private String punitive_interest;
				
				/** 已还金额*/
				private String pay_back;
				
				/** 邮箱地址*/
				private String email;
				
				/** 逾期期数*/
				private String overdue_period;
				
				public String getUpdate_time() {
					return update_time;
				}
	
				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
	
				public String getAddress() {
					return address;
				}
	
				public void setAddress(String address) {
					this.address = address;
				}
	
				public String getComp_addr() {
					return comp_addr;
				}
	
				public void setComp_addr(String comp_addr) {
					this.comp_addr = comp_addr;
				}
	
				public String getOverdue_day() {
					return overdue_day;
				}
	
				public void setOverdue_day(String overdue_day) {
					this.overdue_day = overdue_day;
				}
	
				public String getBorrow_date() {
					return borrow_date;
				}
	
				public void setBorrow_date(String borrow_date) {
					this.borrow_date = borrow_date;
				}
	
				public String getSource() {
					return source;
				}
	
				public void setSource(String source) {
					this.source = source;
				}
	
				public String getContent() {
					return content;
				}
	
				public void setContent(String content) {
					this.content = content;
				}
	
				public String getPrincipal() {
					return principal;
				}
	
				public void setPrincipal(String principal) {
					this.principal = principal;
				}
	
				public String getDebts() {
					return debts;
				}
	
				public void setDebts(String debts) {
					this.debts = debts;
				}
	
				public String getHome_tel() {
					return home_tel;
				}
	
				public void setHome_tel(String home_tel) {
					this.home_tel = home_tel;
				}
	
				public String getWork_tel() {
					return work_tel;
				}
	
				public void setWork_tel(String work_tel) {
					this.work_tel = work_tel;
				}
	
				public String getMobile_phone() {
					return mobile_phone;
				}
	
				public void setMobile_phone(String mobile_phone) {
					this.mobile_phone = mobile_phone;
				}
	
				public String getWeb_pay_period() {
					return web_pay_period;
				}
	
				public void setWeb_pay_period(String web_pay_period) {
					this.web_pay_period = web_pay_period;
				}
	
				public String getCompany() {
					return company;
				}
	
				public void setCompany(String company) {
					this.company = company;
				}
	
				public String getPunitive_interest() {
					return punitive_interest;
				}
	
				public void setPunitive_interest(String punitive_interest) {
					this.punitive_interest = punitive_interest;
				}
	
				public String getPay_back() {
					return pay_back;
				}
	
				public void setPay_back(String pay_back) {
					this.pay_back = pay_back;
				}
	
				public String getEmail() {
					return email;
				}
	
				public void setEmail(String email) {
					this.email = email;
				}
	
				public String getOverdue_period() {
					return overdue_period;
				}
	
				public void setOverdue_period(String overdue_period) {
					this.overdue_period = overdue_period;
				}
	        }
		}
	}
	
	public static class B0063 implements Serializable{
		
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = 8361518430687975867L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0063> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0063> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0063> items) {
            this.items = items;
        }
		public static class ItemsB0063 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 5586532522166825075L;
	
			/** 结果码：700/701/702/703/704*/
	        private int code;
	        
	        /** 参数记录块*/
	        private ParamB0063 param;
	        
	        /** 结果码描述*/
	        private String message;
	        
	        /** 结果项描述节点*/
	        private List<ResultB0063> result;
	
	        public int getCode() {
	            return code;
	        }
	
	        public void setCode(int code) {
	            this.code = code;
	        }
	
	        public ParamB0063 getParam() {
	            return param;
	        }
	
	        public void setParam(ParamB0063 param) {
	            this.param = param;
	        }
	
	        public String getMessage() {
	            return message;
	        }
	
	        public void setMessage(String message) {
	            this.message = message;
	        }
	
	        public List<ResultB0063> getResult() {
	            return result;
	        }
	
	        public void setResult(List<ResultB0063> result) {
	            this.result = result;
	        }
	
	        public static class ParamB0063 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 5109312567216832266L;
				/** 公司名*/
	            private String corp_name;
	
				public String getCorp_name() {
					return corp_name;
				}
	
				public void setCorp_name(String corp_name) {
					this.corp_name = corp_name;
				}
	            
	        }
	        public static class ResultB0063 implements Serializable {
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -4402177915378138187L;
				
				/** 企业基本信息*/
				private List<Base> base;
				
				/** 企业主要人员信息*/
				private List<Member> member;
				
				/** 股东及出资信息*/
				private List<Investor> investor;
				
				/** 发布时间*/
				private String update_time;
				
				public List<Base> getBase() {
					return base;
				}
				public void setBase(List<Base> base) {
					this.base = base;
				}
				public List<Member> getMember() {
					return member;
				}
				public void setMember(List<Member> member) {
					this.member = member;
				}
				public List<Investor> getInvestor() {
					return investor;
				}
				public void setInvestor(List<Investor> investor) {
					this.investor = investor;
				}
				
				public String getUpdate_time() {
					return update_time;
				}
				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
	
				public static class Base implements Serializable {
					/** 
					* @Fields serialVersionUID : 序列码
					*/ 
					private static final long serialVersionUID = -2050861544575338106L;
					
					/** 实收资本*/
					private String paid_capital;
					
					/** 核准日期*/
					private String appr_date;
					
					/** 企业电话*/
					private List<String> phone_no;
					
					/**企业网址*/
					private List<String> website;
					
					/**注册号*/
					private String regist_no;
					
					/**公司注册地址*/
					private String address;
					
					/**注册资本*/
					private String regist_capital;
					
					/** 营业期限至*/
					private String op_to;
					
					/** 企业类型*/
					private String type;
					
					/** 注销原因*/
					private String cancel_reason;
					
					/** 登记机关*/
					private String regist_org;
					
					/** 组成形式*/
					private String form;
					
					/** 一般经营范围*/
					private String genaral_lic_scope;
					
					/** 营业期限自*/
					private String op_from;
					
					/** 信用代码*/
					private String credit_code;
					
					/**企业名称*/
					private String name;
					
					/** 注销日期*/
					private String cancel_date;
					
					/** 吊销原因*/
					private String revocation_reason;
					
					/** 吊销日期*/
					private String revocation_date;
					
					/** 法人*/
					private String legal_name;
					
					/** 企业邮址*/
					private List<String> email;
					
					/** 公司注册时间*/
					private String found_date;
					
					/** 登记状态*/
					private String status;
	
					public String getPaid_capital() {
						return paid_capital;
					}
	
					public void setPaid_capital(String paid_capital) {
						this.paid_capital = paid_capital;
					}
	
					public String getAppr_date() {
						return appr_date;
					}
	
					public void setAppr_date(String appr_date) {
						this.appr_date = appr_date;
					}
	
					public List<String> getPhone_no() {
						return phone_no;
					}
	
					public void setPhone_no(List<String> phone_no) {
						this.phone_no = phone_no;
					}
	
					public List<String> getWebsite() {
						return website;
					}
	
					public void setWebsite(List<String> website) {
						this.website = website;
					}
	
					public String getRegist_no() {
						return regist_no;
					}
	
					public void setRegist_no(String regist_no) {
						this.regist_no = regist_no;
					}
	
					public String getAddress() {
						return address;
					}
	
					public void setAddress(String address) {
						this.address = address;
					}
	
					public String getRegist_capital() {
						return regist_capital;
					}
	
					public void setRegist_capital(String regist_capital) {
						this.regist_capital = regist_capital;
					}
	
					public String getOp_to() {
						return op_to;
					}
	
					public void setOp_to(String op_to) {
						this.op_to = op_to;
					}
	
					public String getType() {
						return type;
					}
	
					public void setType(String type) {
						this.type = type;
					}
	
					public String getCancel_reason() {
						return cancel_reason;
					}
	
					public void setCancel_reason(String cancel_reason) {
						this.cancel_reason = cancel_reason;
					}
	
					public String getRegist_org() {
						return regist_org;
					}
	
					public void setRegist_org(String regist_org) {
						this.regist_org = regist_org;
					}
	
					public String getForm() {
						return form;
					}
	
					public void setForm(String form) {
						this.form = form;
					}
	
					public String getGenaral_lic_scope() {
						return genaral_lic_scope;
					}
	
					public void setGenaral_lic_scope(String genaral_lic_scope) {
						this.genaral_lic_scope = genaral_lic_scope;
					}
	
					public String getOp_from() {
						return op_from;
					}
	
					public void setOp_from(String op_from) {
						this.op_from = op_from;
					}
	
					public String getCredit_code() {
						return credit_code;
					}
	
					public void setCredit_code(String credit_code) {
						this.credit_code = credit_code;
					}
	
					public String getName() {
						return name;
					}
	
					public void setName(String name) {
						this.name = name;
					}
	
					public String getCancel_date() {
						return cancel_date;
					}
	
					public void setCancel_date(String cancel_date) {
						this.cancel_date = cancel_date;
					}
	
					public String getRevocation_reason() {
						return revocation_reason;
					}
	
					public void setRevocation_reason(String revocation_reason) {
						this.revocation_reason = revocation_reason;
					}
	
					public String getRevocation_date() {
						return revocation_date;
					}
	
					public void setRevocation_date(String revocation_date) {
						this.revocation_date = revocation_date;
					}
	
					public String getLegal_name() {
						return legal_name;
					}
	
					public void setLegal_name(String legal_name) {
						this.legal_name = legal_name;
					}
	
					public List<String> getEmail() {
						return email;
					}
	
					public void setEmail(List<String> email) {
						this.email = email;
					}
	
					public String getFound_date() {
						return found_date;
					}
	
					public void setFound_date(String found_date) {
						this.found_date = found_date;
					}
	
					public String getStatus() {
						return status;
					}
	
					public void setStatus(String status) {
						this.status = status;
					}
		        }
				
				public static class Member implements Serializable {
					/** 
					* @Fields serialVersionUID : 序列码
					*/ 
					private static final long serialVersionUID = 1087041877766022188L;
					
					/** 姓名*/
					private String name;
					/** 职务*/
					private String title;
					
					public String getName() {
						return name;
					}
					public void setName(String name) {
						this.name = name;
					}
					public String getTitle() {
						return title;
					}
					public void setTitle(String title) {
						this.title = title;
					}
				}
				public static class Investor implements Serializable {
					
					/** 
					* @Fields serialVersionUID : 序列码
					*/ 
					private static final long serialVersionUID = -3674741712553731887L;
					/** 认缴详细*/
					private List<SubInvestDetail> sub_invest_detail;
					
					/** 实缴详情*/
					private List<InvestDetail> invest_detail;
					
					/** 出资日期*/
					private String invest_date;
					
					/** 认缴投资金额*/
					private String sub_invest_amount;
					
					
					public List<SubInvestDetail> getSub_invest_detail() {
						return sub_invest_detail;
					}

					public void setSub_invest_detail(List<SubInvestDetail> sub_invest_detail) {
						this.sub_invest_detail = sub_invest_detail;
					}

					public List<InvestDetail> getInvest_detail() {
						return invest_detail;
					}

					public void setInvest_detail(List<InvestDetail> invest_detail) {
						this.invest_detail = invest_detail;
					}

					public String getInvest_date() {
						return invest_date;
					}
	
					public void setInvest_date(String invest_date) {
						this.invest_date = invest_date;
					}
	
					public String getSub_invest_amount() {
						return sub_invest_amount;
					}
	
					public void setSub_invest_amount(String sub_invest_amount) {
						this.sub_invest_amount = sub_invest_amount;
					}
	
					public static class InvestDetail implements Serializable {
	
						/** 
						* @Fields serialVersionUID : 序列号
						*/ 
						private static final long serialVersionUID = 5870412719030944495L;
						
						/** 实缴金额*/
						private String invest_money;
						
						/** 公示日期*/
						private String publicity_date;
						
						/** 实缴方式*/
						private String invest_form;
						
						/** 实缴日期*/
						private String invest_date;
						
						/** 股东证件类型*/
						private String investor_certificate_type;
	
						public String getInvest_money() {
							return invest_money;
						}
	
						public void setInvest_money(String invest_money) {
							this.invest_money = invest_money;
						}
	
						public String getPublicity_date() {
							return publicity_date;
						}
	
						public void setPublicity_date(String publicity_date) {
							this.publicity_date = publicity_date;
						}
	
						public String getInvest_form() {
							return invest_form;
						}
	
						public void setInvest_form(String invest_form) {
							this.invest_form = invest_form;
						}
	
						public String getInvest_date() {
							return invest_date;
						}
	
						public void setInvest_date(String invest_date) {
							this.invest_date = invest_date;
						}
	
						public String getInvestor_certificate_type() {
							return investor_certificate_type;
						}
	
						public void setInvestor_certificate_type(String investor_certificate_type) {
							this.investor_certificate_type = investor_certificate_type;
						}
					}
					
					public static class SubInvestDetail implements Serializable {
						
						/** 
						* @Fields serialVersionUID : 序列码
						*/ 
						private static final long serialVersionUID = 8210168041555126782L;
						
						/** 认缴时间*/
						private String sub_invest_date;
						
						/** 认缴形式*/
						private String sub_invest_form;
						
						/** 公示日期*/
						private String publicity_date;
						
						/**认缴金额*/
						private String sub_invest_money;
						
						/**股东名称*/
						private String investor_name;
						
						/** 实缴投资金额*/
						private String invest_amount;
						
						/** 股东证件号*/
						private String investor_certificate_no;
						
						/** 实缴形式*/
						private String invest_form;
						
						/** 股东类型*/
						private String investor_type;
	
						public String getSub_invest_date() {
							return sub_invest_date;
						}
	
						public void setSub_invest_date(String sub_invest_date) {
							this.sub_invest_date = sub_invest_date;
						}
	
						public String getSub_invest_form() {
							return sub_invest_form;
						}
	
						public void setSub_invest_form(String sub_invest_form) {
							this.sub_invest_form = sub_invest_form;
						}
	
						public String getPublicity_date() {
							return publicity_date;
						}
	
						public void setPublicity_date(String publicity_date) {
							this.publicity_date = publicity_date;
						}
	
						public String getSub_invest_money() {
							return sub_invest_money;
						}
	
						public void setSub_invest_money(String sub_invest_money) {
							this.sub_invest_money = sub_invest_money;
						}
	
						public String getInvestor_name() {
							return investor_name;
						}
	
						public void setInvestor_name(String investor_name) {
							this.investor_name = investor_name;
						}
	
						public String getInvest_amount() {
							return invest_amount;
						}
	
						public void setInvest_amount(String invest_amount) {
							this.invest_amount = invest_amount;
						}
	
						public String getInvestor_certificate_no() {
							return investor_certificate_no;
						}
	
						public void setInvestor_certificate_no(String investor_certificate_no) {
							this.investor_certificate_no = investor_certificate_no;
						}
	
						public String getInvest_form() {
							return invest_form;
						}
	
						public void setInvest_form(String invest_form) {
							this.invest_form = invest_form;
						}
	
						public String getInvestor_type() {
							return investor_type;
						}
	
						public void setInvestor_type(String investor_type) {
							this.investor_type = investor_type;
						}
					}
				}
	        }
		}
	}
	
	public static class B0029 implements Serializable{
		/** 
		* @Fields serialVersionUID : 	序列码
		*/ 
		private static final long serialVersionUID = -4026057750107641284L;

			/** 模块描述*/
	        private String description;
	        
	        /** 结果项列表节点*/
	        private List<ItemsB0029> items;
	
	        public String getDescription() {
	            return description;
	        }
	
	        public void setDescription(String description) {
	            this.description = description;
	        }
	
	        public List<ItemsB0029> getItems() {
	            return items;
	        }
	
	        public void setItems(List<ItemsB0029> items) {
	            this.items = items;
	        }
		public static class ItemsB0029 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 5876542745507909708L;
			
			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0029 param;
			
			/** 结果项描述节点*/
			private List<ResultB0029> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0029 getParam() {
				return param;
			}

			public void setParam(ParamB0029 param) {
				this.param = param;
			}


			public List<ResultB0029> getResult() {
				return result;
			}

			public void setResult(List<ResultB0029> result) {
				this.result = result;
			}



			public static class ResultB0029 implements Serializable{
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 7215172735809628026L;
				
				/** 账号余额*/
				private String account_balance;
				
				/** 社保转公积金额*/
				private String social_security_transfer_amount;
				
				/** 状态*/
				private String status;
				
				/** 更新时间*/
				private String update_time;

				public String getAccount_balance() {
					return account_balance;
				}

				public void setAccount_balance(String account_balance) {
					this.account_balance = account_balance;
				}

				public String getSocial_security_transfer_amount() {
					return social_security_transfer_amount;
				}

				public void setSocial_security_transfer_amount(String social_security_transfer_amount) {
					this.social_security_transfer_amount = social_security_transfer_amount;
				}

				public String getStatus() {
					return status;
				}

				public void setStatus(String status) {
					this.status = status;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0029 implements Serializable{

				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 1285247153999209600L;
				
				/** 证件号*/
				private String id_card_no;
				
				/** 姓名*/
				private String name;
				
				/** 公积金账号*/
				private String housing_fund_account;
				
				/** 社保电脑号*/
				private String computer_no;

				public String getId_card_no() {
					return id_card_no;
				}

				public void setId_card_no(String id_card_no) {
					this.id_card_no = id_card_no;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getHousing_fund_account() {
					return housing_fund_account;
				}

				public void setHousing_fund_account(String housing_fund_account) {
					this.housing_fund_account = housing_fund_account;
				}

				public String getComputer_no() {
					return computer_no;
				}

				public void setComputer_no(String computer_no) {
					this.computer_no = computer_no;
				}
			}
		}
	}
	
	public static class B0028 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = 6981980608848581124L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0028> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0028> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0028> items) {
            this.items = items;
        }
		public static class ItemsB0028 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = -1789588908754152998L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0028 param;
			
			/** 结果项描述节点*/
			private List<ResultB0028> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0028 getParam() {
				return param;
			}

			public void setParam(ParamB0028 param) {
				this.param = param;
			}

			
			public List<ResultB0028> getResult() {
				return result;
			}

			public void setResult(List<ResultB0028> result) {
				this.result = result;
			}



			public static class ResultB0028 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 3579510644103865970L;

				/** 类型*/
				private String bank_card_class;
				
				/** 官方网站*/
				private String official_website;
				
				/** 官方电话*/
				private String consumer_hotline;
				
				/** 归属地*/
				private String attribution;
				
				/** 更新时间*/
				private String update_time;

				public String getBank_card_class() {
					return bank_card_class;
				}

				public void setBank_card_class(String bank_card_class) {
					this.bank_card_class = bank_card_class;
				}

				public String getOfficial_website() {
					return official_website;
				}

				public void setOfficial_website(String official_website) {
					this.official_website = official_website;
				}

				public String getConsumer_hotline() {
					return consumer_hotline;
				}

				public void setConsumer_hotline(String consumer_hotline) {
					this.consumer_hotline = consumer_hotline;
				}

				public String getAttribution() {
					return attribution;
				}

				public void setAttribution(String attribution) {
					this.attribution = attribution;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0028 implements Serializable{
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -1629351426364667096L;

				/** 银行卡号*/
				private String bank_card_no;

				public String getBank_card_no() {
					return bank_card_no;
				}

				public void setBank_card_no(String bank_card_no) {
					this.bank_card_no = bank_card_no;
				}
			}
		}
	}
	
	public static class B0039 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -3545822461091430677L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0039> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0039> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0039> items) {
            this.items = items;
        }
		public static class ItemsB0039 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = -616421634026243038L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0039 param;
			
			/** 结果项描述节点*/
			private List<ResultB0039> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0039 getParam() {
				return param;
			}

			public void setParam(ParamB0039 param) {
				this.param = param;
			}

			
			public List<ResultB0039> getResult() {
				return result;
			}

			public void setResult(List<ResultB0039> result) {
				this.result = result;
			}


			public static class ResultB0039 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 4836493366141712011L;

				/** 验证结果（查得此单、查无此单)*/
				private String result;
				
				/** 更新时间*/
				private String update_time;
				
				public String getResult() {
					return result;
				}

				public void setResult(String result) {
					this.result = result;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0039 implements Serializable{

				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -7765320824012293675L;
				
				/** 证件号*/
				private String id_card_no;
				
				/** 姓名*/
				private String name;
				
				/** 保单号*/
				private String policy_no;
				
				/** 承保公司*/
				private String type;

				public String getId_card_no() {
					return id_card_no;
				}

				public void setId_card_no(String id_card_no) {
					this.id_card_no = id_card_no;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getPolicy_no() {
					return policy_no;
				}

				public void setPolicy_no(String policy_no) {
					this.policy_no = policy_no;
				}

				public String getType() {
					return type;
				}

				public void setType(String type) {
					this.type = type;
				}
			}
		}
	}
	
	public static class B0023 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -6169866552141480978L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0023> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0023> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0023> items) {
            this.items = items;
        }
		public static class ItemsB0023 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 4655884520981632285L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0023 param;
			
			/** 结果项描述节点*/
			private List<ResultB0023> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0023 getParam() {
				return param;
			}

			public void setParam(ParamB0023 param) {
				this.param = param;
			}

			
			public List<ResultB0023> getResult() {
				return result;
			}

			public void setResult(List<ResultB0023> result) {
				this.result = result;
			}


			public static class ResultB0023 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -1574690480194937406L;

				/** 执行法院名称*/
				private String exec_court_name;
				
				/** 案件创建时间*/
				private String case_create_time;
				
				/** 案件号*/
				private String case_code;
				
				/** 案件状态*/
				private String case_state;
				
				/** 执行标的*/
				private String exec_money;
				
				/** 更新时间*/
				private String update_time;
				
				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}

				public String getExec_court_name() {
					return exec_court_name;
				}

				public void setExec_court_name(String exec_court_name) {
					this.exec_court_name = exec_court_name;
				}

				public String getCase_create_time() {
					return case_create_time;
				}

				public void setCase_create_time(String case_create_time) {
					this.case_create_time = case_create_time;
				}

				public String getCase_code() {
					return case_code;
				}

				public void setCase_code(String case_code) {
					this.case_code = case_code;
				}

				public String getCase_state() {
					return case_state;
				}

				public void setCase_state(String case_state) {
					this.case_state = case_state;
				}

				public String getExec_money() {
					return exec_money;
				}

				public void setExec_money(String exec_money) {
					this.exec_money = exec_money;
				}
			}
			
			public static class ParamB0023 implements Serializable{

				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -1940125936508090399L;

				/** 被执行人名称*/
				private String name;
				
				/** 被执行人证件号*/
				private String party_card_no;
				
				/** 被执行人类型，person为个人，corp为公司*/
				private String type;

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getParty_card_no() {
					return party_card_no;
				}

				public void setParty_card_no(String party_card_no) {
					this.party_card_no = party_card_no;
				}

				public String getType() {
					return type;
				}

				public void setType(String type) {
					this.type = type;
				}
			}
		}
	}
	
	public static class B0067 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -8335835822284707518L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0067> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0067> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0067> items) {
            this.items = items;
        }
		public static class ItemsB0067 implements Serializable{
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 4655884520981632285L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0067 param;
			
			/** 结果项描述节点*/
			private List<ResultB0067> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public List<ResultB0067> getResult() {
				return result;
			}

			public void setResult(List<ResultB0067> result) {
				this.result = result;
			}

			public ParamB0067 getParam() {
				return param;
			}

			public void setParam(ParamB0067 param) {
				this.param = param;
			}


			public static class ResultB0067 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -1574690480194937406L;

				/** 查询量*/
				private String count;
				
				/** 最近一次查询时间*/
				private String latest_time;
				
				/** 最早一次查询时间*/
				private String earliest_time;
				
				/** 统计时间*/
				private String statistics_time;

				public String getCount() {
					return count;
				}

				public void setCount(String count) {
					this.count = count;
				}

				public String getLatest_time() {
					return latest_time;
				}

				public void setLatest_time(String latest_time) {
					this.latest_time = latest_time;
				}

				public String getEarliest_time() {
					return earliest_time;
				}

				public void setEarliest_time(String earliest_time) {
					this.earliest_time = earliest_time;
				}

				public String getStatistics_time() {
					return statistics_time;
				}

				public void setStatistics_time(String statistics_time) {
					this.statistics_time = statistics_time;
				}
			}
			
			public static class ParamB0067 implements Serializable{

				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -1940125936508090399L;

				/** 电话号码*/
				private String phone_no;

				public String getPhone_no() {
					return phone_no;
				}

				public void setPhone_no(String phone_no) {
					this.phone_no = phone_no;
				}
			}
		}
	}
	
	public static class B0066 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = 5834572424425364856L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0066> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0066> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0066> items) {
            this.items = items;
        }
		public static class ItemsB0066 implements Serializable{
			/** 
			* @Fields serialVersionUID : 序列号
			*/ 
			private static final long serialVersionUID = 7394042769981238139L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0066 param;
			
			/** 结果项描述节点*/
			private List<ResultB0066> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0066 getParam() {
				return param;
			}

			public void setParam(ParamB0066 param) {
				this.param = param;
			}

			
			public List<ResultB0066> getResult() {
				return result;
			}

			public void setResult(List<ResultB0066> result) {
				this.result = result;
			}


			public static class ResultB0066 implements Serializable{

				/** 
				* @Fields serialVersionUID : 序列号
				*/ 
				private static final long serialVersionUID = -4827028321393348402L;

				/** 查询量*/
				private String count;
				
				/** 最近一次查询时间*/
				private String latest_time;
				
				/** 最早一次查询时间*/
				private String earliest_time;
				
				/** 统计时间*/
				private String statistics_time;
				
				public String getCount() {
					return count;
				}

				public void setCount(String count) {
					this.count = count;
				}

				public String getLatest_time() {
					return latest_time;
				}

				public void setLatest_time(String latest_time) {
					this.latest_time = latest_time;
				}

				public String getEarliest_time() {
					return earliest_time;
				}

				public void setEarliest_time(String earliest_time) {
					this.earliest_time = earliest_time;
				}

				public String getStatistics_time() {
					return statistics_time;
				}

				public void setStatistics_time(String statistics_time) {
					this.statistics_time = statistics_time;
				}
			}
			
			public static class ParamB0066 implements Serializable{
				/** 
				* @Fields serialVersionUID : 序列码 
				*/ 
				private static final long serialVersionUID = 4152779266460996177L;
				/** 地址*/
				private String address;
				public String getAddress() {
					return address;
				}
				public void setAddress(String address) {
					this.address = address;
				}
			}
		}
	}
	
	public static class B0065 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = 3196672611387107825L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0065> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0065> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0065> items) {
            this.items = items;
        }
		public static class ItemsB0065 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = -2107391882567190108L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0065 param;
			
			/** 结果项描述节点*/
			private List<ResultB0065> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0065 getParam() {
				return param;
			}

			public void setParam(ParamB0065 param) {
				this.param = param;
			}

			
			public List<ResultB0065> getResult() {
				return result;
			}

			public void setResult(List<ResultB0065> result) {
				this.result = result;
			}


			public static class ResultB0065 implements Serializable{
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 7486632317733137146L;
				
				/** 查询量*/
				private String count;
				
				/** 最近一次查询时间*/
				private String latest_time;
				
				/** 最早一次查询时间*/
				private String earliest_time;
				
				/** 统计时间*/
				private String statistics_time;

				public String getCount() {
					return count;
				}

				public void setCount(String count) {
					this.count = count;
				}

				public String getLatest_time() {
					return latest_time;
				}

				public void setLatest_time(String latest_time) {
					this.latest_time = latest_time;
				}

				public String getEarliest_time() {
					return earliest_time;
				}

				public void setEarliest_time(String earliest_time) {
					this.earliest_time = earliest_time;
				}

				public String getStatistics_time() {
					return statistics_time;
				}

				public void setStatistics_time(String statistics_time) {
					this.statistics_time = statistics_time;
				}
				
			}
			
			public static class ParamB0065 implements Serializable{
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -2163689307976190539L;
				/** 公司名称*/
				private String corp_name;

				public String getCorp_name() {
					return corp_name;
				}

				public void setCorp_name(String corp_name) {
					this.corp_name = corp_name;
				}
			}
		}
	}
	
	public static class B0031 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -7836479755236230723L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0031> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0031> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0031> items) {
            this.items = items;
        }
		public static class ItemsB0031 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = -2958928886613097773L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0031 param;
			
			/** 结果项描述节点*/
			private List<ResultB0031> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0031 getParam() {
				return param;
			}

			public void setParam(ParamB0031 param) {
				this.param = param;
			}


			public List<ResultB0031> getResult() {
				return result;
			}

			public void setResult(List<ResultB0031> result) {
				this.result = result;
			}


			public static class ResultB0031 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -1902438617971212925L;

				/** 注册号*/
				private String regist_no;
				
				/** 机构名称*/
				private String issue_org;
				
				/** 机构类型*/
				private String org_type;
				
				/** 机构地址*/
				private String company_address;
				
				/** 有效期*/
				private String company_validate;
				
				/** 更新时间*/
				private String update_time;

				public String getRegist_no() {
					return regist_no;
				}

				public void setRegist_no(String regist_no) {
					this.regist_no = regist_no;
				}

				public String getIssue_org() {
					return issue_org;
				}

				public void setIssue_org(String issue_org) {
					this.issue_org = issue_org;
				}

				public String getOrg_type() {
					return org_type;
				}

				public void setOrg_type(String org_type) {
					this.org_type = org_type;
				}

				public String getCompany_address() {
					return company_address;
				}

				public void setCompany_address(String company_address) {
					this.company_address = company_address;
				}

				public String getCompany_validate() {
					return company_validate;
				}

				public void setCompany_validate(String company_validate) {
					this.company_validate = company_validate;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0031 implements Serializable{
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 4676748762142398407L;
				
				/** 机构名称*/
				private String org_name;
				
				public String getOrg_name() {
					return org_name;
				}
				public void setOrg_name(String org_name) {
					this.org_name = org_name;
				}
			}
		}
	}
	
	public static class B0005 implements Serializable{
		
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -6359159437833201063L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0005> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0005> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0005> items) {
            this.items = items;
        }
		public static class ItemsB0005 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = -7336059573714901266L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0005 param;
			
			/** 结果项描述节点*/
			private List<ResultB0005> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0005 getParam() {
				return param;
			}

			public void setParam(ParamB0005 param) {
				this.param = param;
			}


			public List<ResultB0005> getResult() {
				return result;
			}

			public void setResult(List<ResultB0005> result) {
				this.result = result;
			}


			public static class ResultB0005 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -2678184946178511992L;

				/** 归属地*/
				private String attribution;
				
				/** 更新时间*/
				private String update_time;

				public String getAttribution() {
					return attribution;
				}

				public void setAttribution(String attribution) {
					this.attribution = attribution;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0005 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -571397491521457736L;
				/** 固话号码*/
				private String phone_no;
				
				/** 固话所属人类型：personalTelephone（申请人）、linkmanTelephone（联系人）*/
				private String type;

				public String getPhone_no() {
					return phone_no;
				}

				public void setPhone_no(String phone_no) {
					this.phone_no = phone_no;
				}

				public String getType() {
					return type;
				}

				public void setType(String type) {
					this.type = type;
				}
			}
		}
	}
	
	public static class B0027 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -1685738336839066642L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0027> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0027> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0027> items) {
            this.items = items;
        }
		public static class ItemsB0027 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 756574032831477936L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0027 param;
			
			/** 结果项描述节点*/
			private List<ResultB0027> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0027 getParam() {
				return param;
			}

			public void setParam(ParamB0027 param) {
				this.param = param;
			}


			public List<ResultB0027> getResult() {
				return result;
			}

			public void setResult(List<ResultB0027> result) {
				this.result = result;
			}


			public static class ResultB0027 implements Serializable{

				/** 
				* @Fields serialVersionUID : 序列码 
				*/ 
				private static final long serialVersionUID = 5682243545593668695L;

				/** 职位评估薪资*/
				private String avg_salary;
				
				/** 平均评估薪资*/
				private String salary;
				
				/** 备注*/
				private String remark;
				
				/** 更新时间*/
				private String update_time;
				
				public String getAvg_salary() {
					return avg_salary;
				}

				public void setAvg_salary(String avg_salary) {
					this.avg_salary = avg_salary;
				}

				public String getSalary() {
					return salary;
				}

				public void setSalary(String salary) {
					this.salary = salary;
				}

				public String getRemark() {
					return remark;
				}

				public void setRemark(String remark) {
					this.remark = remark;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0027 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码 
				*/ 
				private static final long serialVersionUID = 6254303600892754699L;
				
				/** 职位*/
				private String office;
				public String getOffice() {
					return office;
				}
				public void setOffice(String office) {
					this.office = office;
				}
			}
		}
	}
	
	public static class B0004 implements Serializable{
		/** 
		* @Fields serialVersionUID : 	序列码
		*/ 
		private static final long serialVersionUID = 7922749993994614767L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0004> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0004> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0004> items) {
            this.items = items;
        }
		public static class ItemsB0004 implements Serializable{
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = -3084441269285499279L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0004 param;
			
			/** 结果项描述节点*/
			private List<ResultB0004> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0004 getParam() {
				return param;
			}

			public void setParam(ParamB0004 param) {
				this.param = param;
			}


			public List<ResultB0004> getResult() {
				return result;
			}

			public void setResult(List<ResultB0004> result) {
				this.result = result;
			}


			public static class ResultB0004 implements Serializable{
				
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 2753574174351569491L;

				/** 归属地*/
				private String attribution;
				
				/** 更新时间*/
				private String update_time;

				public String getAttribution() {
					return attribution;
				}

				public void setAttribution(String attribution) {
					this.attribution = attribution;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0004 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 487445860025770631L;
				/** 手机号*/
				private String phone_no;
				
				/** 手机所属人类型：personalMobilePhone（申请人）、linkmanMobilePhone（联系人）*/
				private String type;

				public String getPhone_no() {
					return phone_no;
				}

				public void setPhone_no(String phone_no) {
					this.phone_no = phone_no;
				}

				public String getType() {
					return type;
				}

				public void setType(String type) {
					this.type = type;
				}
			}
		}
	}
	
	public static class B0036 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = 3141701273025685549L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0036> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0036> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0036> items) {
            this.items = items;
        }
		public static class ItemsB0036 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			
			private static final long serialVersionUID = 5661705065423022354L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0036 param;
			
			/** 结果项描述节点*/
			private List<ResultB0036> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0036 getParam() {
				return param;
			}

			public void setParam(ParamB0036 param) {
				this.param = param;
			}


			public List<ResultB0036> getResult() {
				return result;
			}

			public void setResult(List<ResultB0036> result) {
				this.result = result;
			}


			public static class ResultB0036 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 4806659461193409933L;

				/** 环比上月趋势*/
				private String chain_relative_ratio;
				
				/** 房产总估价*/
				private String total_price;
				
				/** 房产名称*/
				private String village_name;
				
				/** 同比去年趋势*/
				private String year_on_year_basis;
				
				/** 当前每平方估价*/
				private String unit_price;
				
				/** 房产所在城市*/
				private String village_city;
				
				/** 房产地址*/
				private String village_address;
				
				/** 更新时间*/
				private String update_time;

				public String getChain_relative_ratio() {
					return chain_relative_ratio;
				}

				public void setChain_relative_ratio(String chain_relative_ratio) {
					this.chain_relative_ratio = chain_relative_ratio;
				}

				public String getTotal_price() {
					return total_price;
				}

				public void setTotal_price(String total_price) {
					this.total_price = total_price;
				}

				public String getVillage_name() {
					return village_name;
				}

				public void setVillage_name(String village_name) {
					this.village_name = village_name;
				}

				public String getYear_on_year_basis() {
					return year_on_year_basis;
				}

				public void setYear_on_year_basis(String year_on_year_basis) {
					this.year_on_year_basis = year_on_year_basis;
				}

				public String getUnit_price() {
					return unit_price;
				}

				public void setUnit_price(String unit_price) {
					this.unit_price = unit_price;
				}

				public String getVillage_city() {
					return village_city;
				}

				public void setVillage_city(String village_city) {
					this.village_city = village_city;
				}

				public String getVillage_address() {
					return village_address;
				}

				public void setVillage_address(String village_address) {
					this.village_address = village_address;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0036 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -4253586945277650931L;

				/** 房产名称*/
				private String village_name;
				
				/** 房产所在城市*/
				private String village_city;
				
				/** 房产面积大小（平方）*/
				private String village_area;

				public String getVillage_name() {
					return village_name;
				}

				public void setVillage_name(String village_name) {
					this.village_name = village_name;
				}

				public String getVillage_city() {
					return village_city;
				}

				public void setVillage_city(String village_city) {
					this.village_city = village_city;
				}

				public String getVillage_area() {
					return village_area;
				}

				public void setVillage_area(String village_area) {
					this.village_area = village_area;
				}
			}
		}
	}
	
	public static class B0002 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = 7658937528564346938L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0002> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0002> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0002> items) {
            this.items = items;
        }
		public static class ItemsB0002 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = -20000221253245988L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0002 param;
			
			/** 结果项描述节点*/
			private List<ResultB0002> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0002 getParam() {
				return param;
			}

			public void setParam(ParamB0002 param) {
				this.param = param;
			}


			public List<ResultB0002> getResult() {
				return result;
			}

			public void setResult(List<ResultB0002> result) {
				this.result = result;
			}


			public static class ResultB0002 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 2141875038148686139L;

				/** 校验结果*/
				private String validate;
				
				/** 相似地址*/
				private List<String> similar;

				public String getValidate() {
					return validate;
				}

				public void setValidate(String validate) {
					this.validate = validate;
				}

				public List<String> getSimilar() {
					return similar;
				}

				public void setSimilar(List<String> similar) {
					this.similar = similar;
				}
			}
			
			public static class ParamB0002 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -4322723355528433859L;

				/** 地址*/
				private String address;
				
				/** 地址类型有两种：corpAddress（公司地址）和homeAddress（家庭地址）*/
				private String type;

				public String getAddress() {
					return address;
				}

				public void setAddress(String address) {
					this.address = address;
				}

				public String getType() {
					return type;
				}

				public void setType(String type) {
					this.type = type;
				}
			}
		}
	}
	
	public static class B0024 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -186504308963566649L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0024> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0024> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0024> items) {
            this.items = items;
        }
		public static class ItemsB0024 implements Serializable{
			
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = -3202463921454384367L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0024 param;
			
			/** 结果项描述节点*/
			private List<ResultB0024> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0024 getParam() {
				return param;
			}

			public void setParam(ParamB0024 param) {
				this.param = param;
			}


			public List<ResultB0024> getResult() {
				return result;
			}

			public void setResult(List<ResultB0024> result) {
				this.result = result;
			}


			public static class ResultB0024 implements Serializable{
				
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 1739756499445228989L;

				/** 省份*/
				private String area_name;
				
				/** 案件号*/
				private String case_code;
				
				/** 性别*/
				private String sexy;
				
				/** 法定代表人或者负责人姓名*/
				private String business_entity;
				
				/** 执行依据文号*/
				private String gist_id;
				
				/** 立案时间*/
				private String reg_date;
				
				/** 做出执行依据单位*/
				private String gist_unit;
				
				/** 失信被执行人行为具体情形*/
				private String disrupt_type_name;
				
				/** 生效法律文书确定的义务*/
				private String duty;
				
				/** 执行法院*/
				private String court_name;
				
				/** 失信被执行人的履行情况*/
				private String performance;
				
				/** 失信被执行人已履行部分*/
				private String performed_part;
				
				/** 失信被执行人未履行部分*/
				private String unperform_part;
				
				/** 发布时间*/
				private String publish_date;
				
				/** 失信被执行人年龄*/
				private String age;
				
				/** 更新时间*/
				private String update_time;

				public String getArea_name() {
					return area_name;
				}

				public void setArea_name(String area_name) {
					this.area_name = area_name;
				}

				public String getCase_code() {
					return case_code;
				}

				public void setCase_code(String case_code) {
					this.case_code = case_code;
				}

				public String getSexy() {
					return sexy;
				}

				public void setSexy(String sexy) {
					this.sexy = sexy;
				}

				public String getBusiness_entity() {
					return business_entity;
				}

				public void setBusiness_entity(String business_entity) {
					this.business_entity = business_entity;
				}

				public String getGist_id() {
					return gist_id;
				}

				public void setGist_id(String gist_id) {
					this.gist_id = gist_id;
				}

				public String getReg_date() {
					return reg_date;
				}

				public void setReg_date(String reg_date) {
					this.reg_date = reg_date;
				}

				public String getGist_unit() {
					return gist_unit;
				}

				public void setGist_unit(String gist_unit) {
					this.gist_unit = gist_unit;
				}

				public String getDisrupt_type_name() {
					return disrupt_type_name;
				}

				public void setDisrupt_type_name(String disrupt_type_name) {
					this.disrupt_type_name = disrupt_type_name;
				}

				public String getDuty() {
					return duty;
				}

				public void setDuty(String duty) {
					this.duty = duty;
				}

				public String getCourt_name() {
					return court_name;
				}

				public void setCourt_name(String court_name) {
					this.court_name = court_name;
				}

				public String getPerformance() {
					return performance;
				}

				public void setPerformance(String performance) {
					this.performance = performance;
				}

				public String getPerformed_part() {
					return performed_part;
				}

				public void setPerformed_part(String performed_part) {
					this.performed_part = performed_part;
				}

				public String getUnperform_part() {
					return unperform_part;
				}

				public void setUnperform_part(String unperform_part) {
					this.unperform_part = unperform_part;
				}

				public String getPublish_date() {
					return publish_date;
				}

				public void setPublish_date(String publish_date) {
					this.publish_date = publish_date;
				}

				public String getAge() {
					return age;
				}

				public void setAge(String age) {
					this.age = age;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
				
			}
			
			public static class ParamB0024 implements Serializable{

				/** 
				* @Fields serialVersionUID :序列码
				*/ 
				private static final long serialVersionUID = 8791270076551469424L;

				/** 失信被执行人名称*/
				private String name;
				
				/** 失信被执行人证件号*/
				private String party_card_no;
				
				/** 失信被执行人类型，person为个人，corp为公司*/
				private String type;

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getParty_card_no() {
					return party_card_no;
				}

				public void setParty_card_no(String party_card_no) {
					this.party_card_no = party_card_no;
				}

				public String getType() {
					return type;
				}

				public void setType(String type) {
					this.type = type;
				}
			}
		}
	}
	
	public static class B0035 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -8918588636331784516L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0035> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0035> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0035> items) {
            this.items = items;
        }
		public static class ItemsB0035 implements Serializable{
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 8081664708807918270L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0035 param;
			
			/** 结果项描述节点*/
			private List<ResultB0035> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0035 getParam() {
				return param;
			}

			public void setParam(ParamB0035 param) {
				this.param = param;
			}


			public List<ResultB0035> getResult() {
				return result;
			}

			public void setResult(List<ResultB0035> result) {
				this.result = result;
			}


			public static class ResultB0035 implements Serializable{
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 3075117689256651846L;

				/** 记录数*/
				private String tax_num;
				
				/** 链接*/
				private String tax_link;
				
				/** 纳税人名称*/
				private String taxpayer_name;
				
				/** 更新时间*/
				private String update_time;

				public String getTax_num() {
					return tax_num;
				}

				public void setTax_num(String tax_num) {
					this.tax_num = tax_num;
				}

				public String getTax_link() {
					return tax_link;
				}

				public void setTax_link(String tax_link) {
					this.tax_link = tax_link;
				}

				public String getTaxpayer_name() {
					return taxpayer_name;
				}

				public void setTaxpayer_name(String taxpayer_name) {
					this.taxpayer_name = taxpayer_name;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0035 implements Serializable{

				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -7655769963778847282L;

				/** 申请人证件号*/
				private String id_card_no;
				
				/** 申请人姓名*/
				private String name;
				
				/** 公司名*/
				private String corp_name;

				public String getId_card_no() {
					return id_card_no;
				}

				public void setId_card_no(String id_card_no) {
					this.id_card_no = id_card_no;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getCorp_name() {
					return corp_name;
				}

				public void setCorp_name(String corp_name) {
					this.corp_name = corp_name;
				}
			}
		}
	}
	
	public static class B0068 implements Serializable{
		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -4982023663673372906L;

		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0068> items;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<ItemsB0068> getItems() {
            return items;
        }

        public void setItems(List<ItemsB0068> items) {
            this.items = items;
        }
		public static class ItemsB0068 implements Serializable{
			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = 9193182958975606313L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0068 param;
			
			/** 结果项描述节点*/
			private List<ResultB0068> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0068 getParam() {
				return param;
			}

			public void setParam(ParamB0068 param) {
				this.param = param;
			}


			public List<ResultB0068> getResult() {
				return result;
			}

			public void setResult(List<ResultB0068> result) {
				this.result = result;
			}


			public static class ResultB0068 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 4857139312558747062L;

				/** 查询量*/
				private String count;
				
				/** 最近一次查询时间*/
				private String latest_time;
				
				/** 最早一次查询时间*/
				private String earliest_time;
				
				/** 统计时间*/
				private String statistics_time;

				public String getCount() {
					return count;
				}

				public void setCount(String count) {
					this.count = count;
				}

				public String getLatest_time() {
					return latest_time;
				}

				public void setLatest_time(String latest_time) {
					this.latest_time = latest_time;
				}

				public String getEarliest_time() {
					return earliest_time;
				}

				public void setEarliest_time(String earliest_time) {
					this.earliest_time = earliest_time;
				}

				public String getStatistics_time() {
					return statistics_time;
				}

				public void setStatistics_time(String statistics_time) {
					this.statistics_time = statistics_time;
				}
			}
			
			public static class ParamB0068 implements Serializable{

				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 3437135837562593365L;

				/** 申请人证件号*/
				private String id_card_no;

				public String getId_card_no() {
					return id_card_no;
				}

				public void setId_card_no(String id_card_no) {
					this.id_card_no = id_card_no;
				}
			}
		}
	}
	
	public static class B0025 implements Serializable{

		/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -6285227436680220523L;
    		
		/** 模块描述*/
        private String description;
        
        /** 结果项列表节点*/
        private List<ItemsB0025> items;
        
        public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<ItemsB0025> getItems() {
			return items;
		}

		public void setItems(List<ItemsB0025> items) {
			this.items = items;
		}

		public static class ItemsB0025 implements Serializable{

			/** 
			* @Fields serialVersionUID : 序列码
			*/ 
			private static final long serialVersionUID = -127515553004475006L;

			/** 结果码：700/701/702/703/704*/
			private Integer code;
			
			/** 结果码描述。对应上面code值：查询成功，有数据；查询成功，没有数据；查询成功，参数错误；查询失败，没有数据*/
			private String message;
			
			/** 参数记录块*/
			private ParamB0025 param;
			
			/** 结果项描述节点*/
			private List<ResultB0025> result;
			
			public Integer getCode() {
				return code;
			}

			public void setCode(Integer code) {
				this.code = code;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public ParamB0025 getParam() {
				return param;
			}

			public void setParam(ParamB0025 param) {
				this.param = param;
			}


			public List<ResultB0025> getResult() {
				return result;
			}

			public void setResult(List<ResultB0025> result) {
				this.result = result;
			}

			public static class ResultB0025 implements Serializable{
				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = -798307209726528384L;

				/** 签发地*/
				private String area;
				
				/** 年龄*/
				private String age;
				
				/** 更新时间*/
				private String update_time;

				public String getArea() {
					return area;
				}

				public void setArea(String area) {
					this.area = area;
				}

				public String getAge() {
					return age;
				}

				public void setAge(String age) {
					this.age = age;
				}

				public String getUpdate_time() {
					return update_time;
				}

				public void setUpdate_time(String update_time) {
					this.update_time = update_time;
				}
			}
			
			public static class ParamB0025 implements Serializable{

				
				/** 
				* @Fields serialVersionUID : 序列码
				*/ 
				private static final long serialVersionUID = 2948271282466789042L;
				
				/** 申请人证件号*/
				private String id_card_no;
				
				/**申请人姓名*/
				private String name;
				
				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getId_card_no() {
					return id_card_no;
				}

				public void setId_card_no(String id_card_no) {
					this.id_card_no = id_card_no;
				}
			}
		}
    }
	
    public static class Header implements Serializable{
    	
    	/** 
		* @Fields serialVersionUID : 序列码
		*/ 
		private static final long serialVersionUID = -2732676488579712817L;

		/** API版本号*/
        private String api_version;
        
        /** 产品代码*/
        private String app_code;
        
        /** 客户标识*/
        private String client_id;
        
        /** 请求时间，格式：yyyy-MM-ddhh:mm:ss*/
        private String request_time;
        
        /** 请求方式*/
        private String source;
        
        /** 状态*/
        private String status;
        
        /** 交易ID,唯一识别报告*/
        private String transaction_id;

        public String getApi_version() {
            return api_version;
        }

        public void setApi_version(String api_version) {
            this.api_version = api_version;
        }

        public String getApp_code() {
            return app_code;
        }

        public void setApp_code(String app_code) {
            this.app_code = app_code;
        }

        public String getClient_id() {
            return client_id;
        }

        public void setClient_id(String client_id) {
            this.client_id = client_id;
        }

        public String getRequest_time() {
            return request_time;
        }

        public void setRequest_time(String request_time) {
            this.request_time = request_time;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }
    }
}