package com.starsgroupchina.credit.server.service;

import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.credit.bean.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-8.
 */
@Slf4j
@Service
public class ModelService extends AbstractService<RiskModel, RiskModelExample> {

    @Service
    public class ModelFieldService extends AbstractService<RiskModelField,RiskModelFieldExample> {

        public List<RiskModelField> getFieldByGroupId(int groupId) {
            RiskModelFieldExample example = new RiskModelFieldExample();
            example.createCriteria().andGroupIdEqualTo(groupId);
            return query(example).collect(toList());
        }

        public void deleteByModelId(int modelId) {
            RiskModelFieldExample example = new RiskModelFieldExample();
            example.createCriteria().andModelIdEqualTo(modelId);
            deleteByExample(example);
        }

    }

    @Service
    public class ModelGroupService extends AbstractService<RiskModelGroup,RiskModelGroupExample> {

        public List<RiskModelGroup> getGroupByModelId(int modelId) {
            RiskModelGroupExample example = new RiskModelGroupExample();
            example.createCriteria().andModelIdEqualTo(modelId);
            example.setOrderByClause("id asc");
            return query(example).collect(toList());
        }

        public void deleteByModelId(int modelId) {
            RiskModelGroupExample example = new RiskModelGroupExample();
            example.createCriteria().andModelIdEqualTo(modelId);
            deleteByExample(example);
        }
    }
}
