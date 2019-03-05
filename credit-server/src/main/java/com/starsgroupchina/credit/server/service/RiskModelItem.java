package com.starsgroupchina.credit.server.service;

import com.starsgroupchina.credit.bean.enums.ScoreType;

import java.util.Map;

/**
 * @Author: QinHaoHao
 * @Description:
 * @Date: Created in 15:57 2018/7/11
 * @Modifed By:
 */
public interface RiskModelItem {

    /**
     * 根据项目编号和type获取分数计算的map
     */
    Map<String,String> getRiskModelItem(String projectNo, ScoreType type);
}
