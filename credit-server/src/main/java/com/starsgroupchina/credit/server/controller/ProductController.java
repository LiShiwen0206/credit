package com.starsgroupchina.credit.server.controller;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.Config;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.Field;
import com.starsgroupchina.credit.bean.FileGroup;
import com.starsgroupchina.credit.bean.extend.ProductConditionConfigExtend;
import com.starsgroupchina.credit.bean.extend.ProductExtend;
import com.starsgroupchina.credit.bean.extend.ProductFileExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.conf.Const;
import com.starsgroupchina.credit.server.service.CategoryService;
import com.starsgroupchina.credit.server.service.OrgConditionService;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.service.system.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-10.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER38", description = "产品管理 - ProductController")
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService.ProductFieldService fieldService;
    @Autowired
    private ProductService.ProductFileService fileService;
    @Autowired
    private ProductService.ProductFormService formService;
    @Autowired
    private OrgService.OrgFileService orgFileService;
    @Autowired
    private ProductService.ProductLoanConfigService productLoanConfigService;
    @Autowired
    private ProductService.ProductConditionConfigService conditionConfigService;

    @Autowired
    private OrgConditionService orgConditionService;

    /**
     * 产品列表
     */
    @ApiOperation("产品列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<ProductExtend> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                             @RequestParam(value = "limit", defaultValue = "20") int limit,
                                             @ApiParam("产品名称") @RequestParam(value = "title", required = false) String title,
                                             @ApiParam("产品大类") @RequestParam(value = "categoryId", required = false) String categoryId,
                                             @ApiParam("产品名称") @RequestParam(value = "orgId", required = false) String orgId,
                                             @RequestParam(value = "status", required = false) List<Short> status) {
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();

        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(title)).isTrue(() -> criteria.andTitleLike("%" + title.trim() + "%"));
        If.of(!StringUtils.isEmpty(categoryId)).isTrue(() -> criteria.andCategoryIdEqualTo(Integer.valueOf(categoryId)));
        If.of(!StringUtils.isEmpty(orgId)).isTrue(() -> criteria.andOrgIdEqualTo(Integer.valueOf(orgId)));
        if (!authMember.getMember().getLoginName().equals(Config.getConfig(Const.CONF_ADMIN)))
            criteria.andHeadOrgIdEqualTo(authMember.getMember().getHeadOrgId());

        long count = productService.count(example);

        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);

        List<ProductExtend> result = Lists.newArrayList();
        Optional.ofNullable(productService.query(example)).ifPresent(products ->
                products.forEach(product -> {
                    ProductExtend productExtend = new ProductExtend(product);
                    Org org = orgService.getById(product.getOrgId());
                    Role roleAudit = roleService.getById(product.getAuditRoleId());
                    Role roleAuditRecheck = roleService.getById(product.getAuditRecheckRoleId());
                    Category category = categoryService.getById(product.getCategoryId());
                    productExtend.setOrg(org);
                    productExtend.setRoleAudit(roleAudit);
                    productExtend.setRoleAuditRecheck(roleAuditRecheck);
                    productExtend.setCategory(category);
                    result.add(productExtend);
                })
        );
        return ListResponse.success(result, count, index, limit);
    }

    /**
     * 根据产品ID 查询产品
     */
    @ApiOperation("根据产品ID 查询产品")
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public SimpleResponse<ProductExtend> get(@ApiParam("产品ID") @PathVariable("productId") Integer productId) {
        Product response = productService.getById(productId);
        ProductExtend result = new ProductExtend(response);
        ProductConditionConfigExample example = new ProductConditionConfigExample();
        example.createCriteria().andProductIdEqualTo(productId);
        List<ProductConditionConfigExtend> confList = Lists.newArrayList();
        List<ProductConditionConfig> conditionConfigList = conditionConfigService.query(example).collect(toList());
        conditionConfigList.forEach(productConditionConfig->{
            ProductConditionConfigExtend productConditionConfigExtend = new ProductConditionConfigExtend(productConditionConfig);
           OrgConditionExample conditionExample = new OrgConditionExample();
           conditionExample.createCriteria().andOrgIdEqualTo(productConditionConfig.getOrgId());
            List<OrgCondition> list = orgConditionService.query(conditionExample).collect(toList());
            productConditionConfigExtend.setConditionList(list);
            confList.add(productConditionConfigExtend);
        });
        result.setConditionConfigList(confList);
        return SimpleResponse.success(result);
    }

    /**
     * 创建 产品
     */
    @ApiOperation("创建 产品")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<Product> create(@RequestBody ProductExtend product) {
        Product result = productService.create(product);
        List<ProductConditionConfigExtend> conditionConfigList = product.getConditionConfigList();
        if (CollectionUtils.isNotEmpty(conditionConfigList)){
            conditionConfigList.forEach(conditionConfig->{
                ProductConditionConfig config = conditionConfig.getConditionConfig();
                config.setProductId(result.getId());
                conditionConfigService.create(config);
            });
        }
        return SimpleResponse.success(result);
    }

    /**
     * 根据 产品ID 删除产品
     */
    @ApiOperation("根据 产品ID 删除产品")
    @RequestMapping(value = "/{productId}", method = RequestMethod.DELETE)
    @Transactional
    public SimpleResponse delete(@PathVariable("productId") Integer productId) {
        productService.deleteById(productId);
        ProductConditionConfigExample example = new ProductConditionConfigExample();
        example.createCriteria().andProductIdEqualTo(productId);
        conditionConfigService.deleteByExample(example);
        return SimpleResponse.success();
    }

    /**
     * 修改 产品基本信息
     */
    @ApiOperation("修改 产品基本信息")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Transactional
    public SimpleResponse<ProductExtend> update(@RequestBody ProductExtend product) {
        productService.update(product);
        ProductConditionConfigExample example = new ProductConditionConfigExample();
        example.createCriteria().andProductIdEqualTo(product.getId());
        conditionConfigService.deleteByExample(example);
        List<ProductConditionConfigExtend> conditionConfigList = product.getConditionConfigList();
        if (CollectionUtils.isNotEmpty(conditionConfigList)){
            conditionConfigList.forEach(conditionConfig->{
                conditionConfig.setProductId(product.getId());
                conditionConfigService.create(conditionConfig.getConditionConfig());
            });
        }
        return SimpleResponse.success(product);
    }

    /**
     * 获取产品全部字段（进件表单字段）
     */
    @ApiOperation(" 获取产品全部字段（进件表单字段）")
    @RequestMapping(value = "/{productId}/fields", method = RequestMethod.GET)
    public SimpleResponse<List<Field>> fields(@PathVariable("productId") Integer productId) {
        List<Field> fields = productService.getFields(productId);
        return SimpleResponse.success(fields);
    }

    /*******************************************************/
    /****************   product    field   *****************/
    /*******************************************************/

    /**
     * 根据产品 ID field
     */
    @ApiOperation("根据模型ID 获取模型 明细")
    @RequestMapping(value = "/field/{productId}", method = RequestMethod.GET)
    public SimpleResponse<List<ProductField>> getFields(
            @ApiParam("产品ID") @PathVariable("productId") Integer productId,
            @ApiParam("字段类型:产品PRODUCT,用户 USER,车辆 CAR") @RequestParam(value = "type", required = false) String type) {
        ProductFieldExample example = new ProductFieldExample();
        example.createCriteria().andProductIdEqualTo(productId);
        Stream<ProductField> fieldStream = fieldService.query(example);

        if (!StringUtils.isEmpty(type))
            return SimpleResponse.success(
                    fieldStream.filter(f -> f.getItemType().equals(type)).collect(toList()));
        return SimpleResponse.success(fieldStream.collect(toList()));
    }

    /**
     * 批量创建 产品 field
     */
    @ApiOperation("批量创建 模型 明细")
    @RequestMapping(value = "/field", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<List<ProductField>> create(@RequestBody List<ProductField> fields) {
        List<ProductField> response = fieldService.create(fields);
        return SimpleResponse.success(response);
    }

    /**
     * 根据  ID 删除
     */
    @ApiOperation("根据ID")
    @RequestMapping(value = "/field/{fieldId}", method = RequestMethod.DELETE)
    public SimpleResponse deleteField(@PathVariable("fieldId") Integer fieldId) {
        fieldService.getById(fieldId);
        return SimpleResponse.success();
    }

    /**
     * 批量更新 产品field
     */
    @ApiOperation("修改 政策")
    @RequestMapping(value = "/field", method = RequestMethod.PUT)
    @Transactional
    public SimpleResponse<List<ProductField>> updateField(
            @ApiParam("产品id") @RequestParam("productId") Integer productId,
            @ApiParam("字段类型:产品PRODUCT,用户 USER,车辆 CAR") @RequestParam("type") String type,
            @RequestBody List<ProductField> fields) {
        if (CollectionUtils.isEmpty(fields))
            return SimpleResponse.success();

        ProductFieldExample example = new ProductFieldExample();
        example.createCriteria().andProductIdEqualTo(fields.get(0).getProductId()).andItemTypeEqualTo(fields.get(0).getItemType());
        fieldService.deleteByExample(example);
        fieldService.create(fields);
        return SimpleResponse.success(fields);
    }


    /*******************************************************/
    /****************   product file       *****************/
    /*******************************************************/


    /**
     * 根据产品id获取产品附件列表
     */
    @ApiOperation("根据产品ID获取产品附件列表")
    @RequestMapping(value = "/{productId}/files", method = RequestMethod.GET)
    public SimpleResponse<List<ProductFileExtend>> getFiles(@ApiParam("产品id") @PathVariable("productId") Integer productId) {
        List<ProductFileExtend> result = fileService.queryExtend(productId);
        return SimpleResponse.success(result);
    }

    /**
     * 根据产品id产品附件树
     */
    @ApiOperation("根据产品id产品附件树")
    @RequestMapping(value = "/{productId}/files/tree", method = RequestMethod.GET)
    public SimpleResponse<List<FileGroup>> getFilesTree(@ApiParam("产品id") @PathVariable("productId") Integer productId) {
        List<ProductFileExtend> files = getFiles(productId).getData();
        List<FileGroup> fileGroups = fileService.transformationFilesTree(files);
        return SimpleResponse.success(fileGroups);
    }

    /**
     * 新增产品附件
     */
    @ApiOperation("新增产品附件")
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<List<ProductFile>> createFile(@RequestBody List<ProductFileExtend> productFiles) {
        List<ProductFile> result = Lists.newArrayList();
        productFiles.forEach(productFile -> {

            OrgFileExample example = new OrgFileExample();
            example.createCriteria().andFileKeyEqualTo(productFile.getFileKey()).andOrgIdEqualTo(productFile.getOrgFile().getOrgId());
            orgFileService.query(example).findFirst().ifPresent(
                    file -> productFile.setOrgFileId(file.getId())
            );
            result.add(productFile);
        });

        fileService.create(result);
        return SimpleResponse.success(result);
    }

    /**
     * 根据产品附件ID删除产品附件
     */
    @ApiOperation("根据产品附件ID删除产品附件")
    @RequestMapping(value = "/file/{fileId}", method = RequestMethod.DELETE)
    public SimpleResponse deleteFile(@PathVariable("fileId") Integer fileId) {
        fileService.deleteById(fileId);
        return SimpleResponse.success();
    }

    /**
     * 修改附件
     */
    @ApiOperation("修改附件")
    @RequestMapping(value = "/file", method = RequestMethod.PUT)
    @Transactional
    public SimpleResponse<List<ProductFile>> updateFile(
            @ApiParam("产品id") @RequestParam("productId") Integer productId,
            @RequestBody List<ProductFileExtend> files) {
        if (CollectionUtils.isEmpty(files))
            return SimpleResponse.success();

        ProductFile productFile = files.get(0);
        ProductFileExample example = new ProductFileExample();
        example.createCriteria().andProductIdEqualTo(productFile.getProductId());
        fileService.deleteByExample(example);
        return createFile(files);
    }


    /*******************************************************/
    /****************   product form       *****************/
    /*******************************************************/

    /**
     * 根据表单id获取表单
     */
    @ApiOperation("根据产品id获取表单")
    @RequestMapping(value = "/{productId}/form", method = RequestMethod.GET)
    public SimpleResponse<List<ProductForm>> getForm(
            @ApiParam("产品表单ID") @PathVariable("productId") Integer productId,
            @ApiParam("表单类型:产品表单 FORM_PRODUCT,用户表单 FORM_USER,车辆表单 FORM_CAR") @RequestParam(value = "type", required = false) String type) {
        ProductFormExample example = new ProductFormExample();
        example.createCriteria().andProductIdEqualTo(productId);
        Stream<ProductForm> formStream = formService.query(example);

        if (!StringUtils.isEmpty(type))
            return SimpleResponse.success(
                    formStream.filter(f -> type.equals(f.getFormType())).collect(Collectors.toList()));

        return SimpleResponse.success(formStream.collect(Collectors.toList()));
    }

    /**
     * 新增产品附件
     */
    @ApiOperation("新增产品表单")
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public SimpleResponse<ProductForm> createForm(@RequestBody ProductForm form) {
        ProductForm productForm = formService.create(form);
        return SimpleResponse.success(productForm);
    }

    /**
     * 修改产品表单
     */
    @ApiOperation("修改产品表单")
    @RequestMapping(value = "/form", method = RequestMethod.PUT)
    public SimpleResponse<ProductForm> updateForm(@RequestBody ProductForm form) {
        formService.update(form);
        return SimpleResponse.success(form);
    }

    /**
     * 创建 产品 评分转换
     */
    @ApiOperation("创建 产品评分转换")
    @PostMapping(value = "/loan/config")
    @Transactional
    public SimpleResponse<List<ProductLoanConfig>> createLoanConfig(@RequestBody List<ProductLoanConfig> loanConfigs) {
        List<ProductLoanConfig> response = productLoanConfigService.create(loanConfigs);
        return SimpleResponse.success(response);
    }

    @ApiOperation("获取 产品评分转换")
    @GetMapping(value = "/{productId}/loan/config")
    public ListResponse<ProductLoanConfig> queryProductLoadConfig(
            @ApiParam("产品ID") @PathVariable("productId") Integer productId) {

        ProductLoanConfigExample example = new ProductLoanConfigExample();
        example.or().andProductIdEqualTo(productId);
        List<ProductLoanConfig> result = productLoanConfigService.query(example).collect(toList());

        return ListResponse.success(result, 0, 0, 0);
    }

    @ApiOperation("更新 产品评分转换")
    @PutMapping(value = "/loan/config")
    @Transactional
    public SimpleResponse<List<ProductLoanConfig>> updateProductLoanConfig(
            @ApiParam("产品id") @RequestParam("productId") Integer productId,
            @RequestBody List<ProductLoanConfig> loanConfigs){

        ProductLoanConfigExample example = new ProductLoanConfigExample();
        example.or().andProductIdEqualTo(productId);
        productLoanConfigService.deleteByExample(example);
        return createLoanConfig(loanConfigs);
    }
}
