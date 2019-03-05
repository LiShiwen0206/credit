package com.starsgroupchina.credit.server.service;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.base.AbstractService;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.credit.bean.Field;
import com.starsgroupchina.credit.bean.FileGroup;
import com.starsgroupchina.credit.bean.enums.FileType;
import com.starsgroupchina.credit.bean.extend.ProductFileExtend;
import com.starsgroupchina.credit.bean.mapper.ProductFileMapper;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.service.system.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-10.
 */
@Slf4j
@Service
public class ProductService extends AbstractService<Product, ProductExample> {

    @Autowired
    private OrgService.OrgFileService orgFileService;
    @Autowired
    private ProductFieldService prodFieldService;
    @Autowired
    private PolicyService.PolicyFieldService policyFieldService;
    @Autowired
    private ModelService.ModelFieldService modelFieldService;

    public Supplier<String> queryNewProduct = () ->
            String.format(" DATE_FORMAT(create_time, '%%Y-%%m-%%d')=DATE_FORMAT(sysdate(), '%%Y-%%m-%%d')");

    public Supplier<String> queryEffectiveProduct = () ->
            String.format(" status=0");

    public List<Field> getFields(int productId) {
        List<Field> fields = Lists.newArrayList();
        Product product = this.getById(productId);
        if (product != null) {
            ProductFieldExample productFieldExample = new ProductFieldExample();
            productFieldExample.createCriteria().andProductIdEqualTo(productId);
            List<ProductField> productFields = prodFieldService.query(productFieldExample).collect(toList());

            PolicyFieldExample policyFieldExample = new PolicyFieldExample();
            policyFieldExample.createCriteria().andPolicyIdEqualTo(product.getPolicyId());
            List<PolicyField> policyFields = policyFieldService.query(policyFieldExample).collect(toList());

            RiskModelFieldExample modelFieldExample = new RiskModelFieldExample();
            modelFieldExample.createCriteria().andModelIdEqualTo(product.getModelId());
            List<RiskModelField> modelFields = modelFieldService.query(modelFieldExample).collect(toList());

            if (!CollectionUtils.isEmpty(productFields))
                productFields.forEach(f -> fields.add(new Field(f.getId(), f.getFieldKey(), f.getFieldName())));
            if (!CollectionUtils.isEmpty(policyFields))
                policyFields.forEach(f -> fields.add(new Field(f.getId(), f.getFieldKey(), f.getFieldName())));
            if (!CollectionUtils.isEmpty(modelFields))
                modelFields.forEach(f -> fields.add(new Field(f.getId(), f.getFieldKey(), f.getFieldName())));
        }
        return fields.stream().distinct().collect(toList());
    }

    @Service
    public class ProductFieldService extends AbstractService<ProductField, ProductFieldExample> {
    }

    /*******************************************************/
    /********** product    file    service *****************/
    /*******************************************************/

    @Service
    public class ProductFileService extends AbstractService<ProductFile, ProductFileExample> {

        @Autowired
        private ProductFileMapper productFileMapper;

        public List<ProductFileExtend> queryExtend(Integer productId) {
            ProductFileExample example = new ProductFileExample();
            ProductFileExample.Criteria criteria = example.createCriteria();
            criteria.andProductIdEqualTo(productId);
            List<ProductFileExtend> result = Lists.newArrayList();
            Optional.ofNullable(query(example)).ifPresent(files ->
                    files.forEach(file -> {
                        ProductFileExtend fileExtension = new ProductFileExtend(file);
                        OrgFile orgFile = orgFileService.getById(file.getOrgFileId());
                        If.of(orgFile != null).isTrue(() -> fileExtension.setOrgFile(orgFile));
                        result.add(fileExtension);
                    })
            );
            return result;
        }

        public List<FileGroup> transformationFilesTree(List<ProductFileExtend> files) {
            Map<String, List<ProductFileExtend>> map =
                    files.stream().collect(groupingBy(fileExtend -> fileExtend.getOrgFile().getFileType()));
            List<FileGroup> fileGroups = Lists.newArrayList();
            for (Map.Entry<String, List<ProductFileExtend>> entry : map.entrySet()) {
                FileGroup productFileGroup = new FileGroup();
                productFileGroup.setCategory(entry.getKey());
                List<FileGroup.FileType> fileTypes = Lists.newArrayList();
                for (ProductFileExtend productFileExtend : entry.getValue()) {
//                    FileGroup.FileType fileType = productFileGroup.new FileType();
                    FileGroup.FileType fileType = new FileGroup.FileType();
                    fileType.setName(productFileExtend.getOrgFile().getFileName());
                    fileType.setOrgFileId(productFileExtend.getOrgFile().getId());
                    fileTypes.add(fileType);
                }
                productFileGroup.setTypes(fileTypes);
                fileGroups.add(productFileGroup);
            }
            fileGroups.add(getAuditFile());
            return fileGroups;
        }

        /**
         * 获取其他附件数
         *
         * @return
         */
        private FileGroup getAuditFile() {
            FileGroup tempCroup = new FileGroup();
            FileGroup.FileType tempType = new FileGroup.FileType();
            tempType.setOrgFileId(FileType.OTHER_FILE.getKey());
            tempType.setName("其他");
            List<FileGroup.FileType> tempList = Lists.newArrayList();
            tempList.add(tempType);
            tempCroup.setTypes(tempList);
            tempCroup.setCategory("其他");
            return tempCroup;
        }
    }

    /*******************************************************/
    /********** product    form   service *****************/
    /*******************************************************/

    @Service
    public class ProductFormService extends AbstractService<ProductForm, ProductFormExample> {

    }

    @Service
    public class ProductLoanConfigService extends AbstractService<ProductLoanConfig, ProductLoanConfigExample> {

    }

    @Service
    public class ProductConditionConfigService extends AbstractService<ProductConditionConfig,ProductConditionConfigExample>{
    }

}
