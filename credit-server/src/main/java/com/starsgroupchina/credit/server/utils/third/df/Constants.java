package com.starsgroupchina.credit.server.utils.third.df;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Constants {
	private static ResourceBundle bundle;

//	public static String CHARSET_SET() {
//		return getBundle().getString("CHARSET_SET");
//	}
//
//	public static String SIGN_TYPE() {
//		return getBundle().getString("SIGN_TYPE");
//	}
//
//	public static String SIGN() {
//		return getBundle().getString("SIGN");
//	}
//
//	public static String SING_SEND() {
//		return getBundle().getString("SIGN_SEND");
//	}
//
//	public static String DES_PUBLIC_ENCRYPT_KEY() {
//		return getBundle().getString("DES_PUBLIC_ENCRYPT_KEY"); // DES加密key
//	}
//
//	public static String DES_PRIVATE_ENCRYPT_KEY() {
//		return getBundle().getString("DES_PRIVATE_ENCRYPT_KEY");
//	}
//
//	public static String COMPANY_NO() {
//		return getBundle().getString("COMPANY_NO");
//	}

	public static ResourceBundle getBundle() {
		if (bundle == null) {
			bundle = PropertyResourceBundle.getBundle("thirdconfig");
		}
		return bundle;
	}
	
//	public static String getConfigValue(String webName,String type)
//	{
//		return getBundle().getString(webName+"_"+type);
//	}
	public static String getConfigValue(String key)
	{
		return getBundle().getString(key);
	}

//	/**
//	 * 资产包上传接口地址
//	 */
//	public static String PROJECT_UPLOAD() {
//		return getBundle().getString("PROJECT_UPLOAD");
//	}
//
//	/**
//	 * 投资信息下载
//	 */
//	public static String PROJECT_INVEST() {
//		return getBundle().getString("PROJECT_INVEST");
//	}
//
//	/**
//	 * 资产包状态查询
//	 */
//	public static String PROJECT_STATUS() {
//		return getBundle().getString("PROJECT_STATUS");
//	}
//
//	/**
//	 * 账户查询
//	 */
//	public static String ACCOUNT_SEARCH() {
//		return getBundle().getString("ACCOUNT_SEARCH");
//	}
//
//	/**
//	 * 撮合信息上传
//	 */
//	public static String PROJECT_MATCH() {
//		return getBundle().getString("PROJECT_MATCH");
//	}
//
//	/**
//	 * 项目状态变更
//	 */
//	public static String PROJECT_STATUS_CHANGE() {
//		return getBundle().getString("PROJECT_STATUS_CHANGE");
//	}
//
//	/**
//	 * 根据邀请码获取邀请人员信息
//	 */
//	public static String INVITE_USER() {
//		return getBundle().getString("INVITE_USER");
//	}

//	/** 手机获取通讯簿地址 **/
//	public static String PHONE_ADDR_BOOK(){
//		return getBundle().getString("PHONE_ADDR_BOOK");
//	}
//
//	/** 手机推送还款计划地址 **/
//	public static String PHONE_SEND_PLAN (){
//		return getBundle().getString("PHONE_SEND_PLAN");
//	}

}
