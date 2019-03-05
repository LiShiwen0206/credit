package com.starsgroupchina.credit.server.controller.system;

import com.google.common.collect.Lists;
import com.starsgroupchina.common.Config;
import com.starsgroupchina.common.XStatus;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.extend.OrgExtend;
import com.starsgroupchina.credit.bean.extend.OrgFileExtend;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.conf.Const;
import com.starsgroupchina.credit.server.service.system.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-7.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER22", description = "【SYS】 - 机构管理 - OrgController")
@RequestMapping(value = "/org", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OrgController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgService.OrgFileService fileService;

    @Autowired
    private OrgService.OrgReportConfigService orgReportConfigService;

    /**
     * 获取机构列表
     */
    @ApiOperation("获取机构列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<OrgExtend> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                         @RequestParam(value = "limit", defaultValue = "20") int limit,
                                         @RequestParam(value = "status", required = false) List<Short> status,
                                         @ApiParam("机构名称：普惠") @RequestParam(value = "name", required = false) String name,
                                         @ApiParam("区域") @RequestParam(value = "area", required = false) String area,
                                         @ApiParam("省份") @RequestParam(value = "province", required = false) String province,
                                         @ApiParam("城市") @RequestParam(value = "city", required = false) String city,
                                         @ApiParam("来源：当为机构配置时需要传递") @RequestParam(value = "source", required = false) String source,
                                         @RequestParam(required = false) String parentId) {

        OrgExample example = new OrgExample();
        OrgExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(name)).isTrue(() -> criteria.andNameLike("%" + name.trim() + "%"));
        If.of(!StringUtils.isEmpty(area)).isTrue(() -> criteria.andAreaEqualTo(area.trim()));
        If.of(!StringUtils.isEmpty(province)).isTrue(() -> criteria.andProvinceEqualTo(province.trim()));
        If.of(!StringUtils.isEmpty(city)).isTrue(() -> criteria.andCityEqualTo(city.trim()));
        If.of(StringUtils.isEmpty(source)).isTrue(() -> If.of(!StringUtils.isEmpty(parentId)).isTrue(() -> criteria.andParentIdEqualTo(Integer.parseInt(parentId.trim()))));
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        if (!authMember.getMember().getLoginName().equals(Config.getConfig(Const.CONF_ADMIN)))
            criteria.andHeadOrgIdEqualTo(authMember.getOrg().getHeadOrgId());

        long count = orgService.count(example);

        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);

        List<OrgExtend> result = Lists.newArrayList();
        Optional.ofNullable(orgService.query(example)).ifPresent(orgs ->
                orgs.forEach(org -> {
                    OrgExtend orgExtend = new OrgExtend(org);
                    if (org.getHeadOrgId() <= 0)
                        orgExtend.setHeadOrg(org.getName());
                    else {
                        org.setShortName("");
                        Org o = orgService.getById(org.getHeadOrgId());
                        If.of(o != null).isTrue(() -> orgExtend.setHeadOrg(o.getName()));
                    }
                    String layer = orgService.getOrgLayer(org.getId(), Strings.EMPTY);
                    orgExtend.setLayer(layer);
                    result.add(orgExtend);
                })
        );
        return ListResponse.success(result, count, index, limit);
    }

    /**
     * 根据机构ID 查询机构
     */
    @ApiOperation("根据机构ID 查询机构")
    @RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
    public SimpleResponse<OrgExtend> get(@ApiParam("机构ID") @PathVariable("orgId") Integer orgId) {
        Org org = orgService.getById(orgId);
        if (org != null) {
            OrgExtend orgExtend = new OrgExtend(org);
            String layer = orgService.getOrgLayer(org.getId(), Strings.EMPTY);
            orgExtend.setLayer(layer);
            return SimpleResponse.success(orgExtend);
        }
        return SimpleResponse.success();
    }

    /**
     * 根据headOrgId获取所属下属机构列表
     */
    @ApiOperation(" 根据headOrgId获取所属下属机构列表")
    @RequestMapping(value = "/{headOrgId}/list", method = RequestMethod.GET)
    public SimpleResponse<List<Org>> getList(
            @ApiParam("headOrgId") @PathVariable("headOrgId") Integer headOrgId) {
        OrgExample example = new OrgExample();
        example.createCriteria().andHeadOrgIdEqualTo(headOrgId);
        List<Org> result = orgService.query(example).collect(toList());
        return SimpleResponse.success(result);
    }

    /**
     * 根据parentId 获取子机构
     */
    @ApiOperation(" 根据parentId 获取子机构 （根节点parentId=0）")
    @RequestMapping(value = "/{parentId}/children", method = RequestMethod.GET)
    public SimpleResponse<List<OrgExtend>> getChildren(
            @ApiParam("parentId") @PathVariable("parentId") Integer parentId) {
        List<OrgExtend> result = Lists.newArrayList();
        OrgExample example = new OrgExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        orgService.query(example).forEach(org -> {
            OrgExtend orgExtend = new OrgExtend(org);
            example.clear();
            example.createCriteria().andParentIdEqualTo(org.getId());
            If.of(orgService.count(example) > 0).isTrue(() -> orgExtend.setLeaf(false));
            result.add(orgExtend);
        });
        return SimpleResponse.success(result);
    }

    /**
     * 创建 机构
     */
    @ApiOperation("创建 机构")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<Org> create(@RequestBody Org org) {
        Org result = orgService.create(org);
        if (result.getParentId() == 0) {
            result.setHeadOrgId(result.getId());
            orgService.update(result);
        }
        return SimpleResponse.success(result);
    }

    /**
     * 根据 机构ID 删除机构
     */
    @ApiOperation("根据 机构ID 删除机构")
    @RequestMapping(value = "/{orgId}", method = RequestMethod.DELETE)
    public SimpleResponse delete(@PathVariable("orgId") Integer orgId) {
        orgService.deleteById(orgId);
        return SimpleResponse.success();
    }

    /**
     * 修改 机构
     */
    @ApiOperation("修改 机构")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SimpleResponse<Org> update(@RequestBody Org org) {
        orgService.update(org);
        return SimpleResponse.success(org);
    }


    /*******************************************************/
    /****************        file   ************************/
    /*******************************************************/

    /**
     * 获取文件列表
     */
    @ApiOperation("获取文件列表")
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public ListResponse<OrgFileExtend> queryFile(@RequestParam(value = "index", defaultValue = "1") int index,
                                                 @RequestParam(value = "limit", defaultValue = "20") int limit,
                                                 @ApiParam("机构id") @RequestParam(required = false) String orgId) {

        OrgFileExample example = new OrgFileExample();
        OrgFileExample.Criteria criteria = example.createCriteria();
        If.of(!StringUtils.isEmpty(orgId)).isTrue(() -> criteria.andOrgIdEqualTo(Integer.parseInt(orgId)));
        long count = fileService.count(example);

        example.setOrderByClause("org_id, create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);

        List<OrgFileExtend> result = Lists.newArrayList();
        Optional.ofNullable(fileService.query(example)).ifPresent(files ->
                files.forEach(file -> {
                    Org org = orgService.getById(file.getOrgId());
                    OrgFileExtend orgFileExtend = new OrgFileExtend(file);
                    orgFileExtend.setOrgName(org.getName());
                    result.add(orgFileExtend);
                })
        );
        return ListResponse.success(result, count, index, limit);
    }

    /**
     * 获取机构 文件列表
     */
    @ApiOperation("获取机构 文件列表")
    @RequestMapping(value = "/{orgId}/files", method = RequestMethod.GET)
    public SimpleResponse<List<OrgFile>> getFiles(@ApiParam("机构ID") @PathVariable(value = "orgId") Integer orgId) {
        OrgFileExample example = new OrgFileExample();
        example.createCriteria().andOrgIdEqualTo(orgId);
        List<OrgFile> files = fileService.query(example).filter(orgFile -> orgFile.getStatus() != XStatus.FORBIDDEN.key()).collect(toList());
        return SimpleResponse.success(files);
    }

    /**
     * 获取机构 文件列表
     */
    @ApiOperation("根据主键获取机构文件")
    @RequestMapping(value = "/{id}/file", method = RequestMethod.GET)
    public SimpleResponse<OrgFile> getFile(@ApiParam("机构文件id") @PathVariable(value = "id") Integer id) {
        OrgFile file = fileService.getById(id);
        return SimpleResponse.success(file);
    }

    /**
     * 创建 机构 文件 映射关系
     */
    @ApiOperation("创建 机构 文件 映射关系")
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @Transactional
    public SimpleResponse<List<OrgFile>> createFiles(@RequestBody List<OrgFile> files) {
        files.forEach(orgFile -> {
            OrgFileExample example = new OrgFileExample();
            example.createCriteria().andFileNameEqualTo(orgFile.getFileName()).andOrgIdEqualTo(orgFile.getOrgId());
            OrgFile file = Utils.getFirst(fileService.query(example));
            If.of(file == null).isTrue(() -> fileService.create(orgFile));
        });
        return SimpleResponse.success(files);
    }

    /**
     * 根据 文件ID 删除机构文件映射
     */
    @ApiOperation("根据 文件ID 删除机构文件映射")
    @RequestMapping(value = "/file/{fileId}", method = RequestMethod.DELETE)
    public SimpleResponse deleteFile(@PathVariable("fileId") Integer fileId) {
        fileService.deleteById(fileId);
        return SimpleResponse.success();
    }

    /**
     * 根据 文件ID 修改机构文件映射
     */
    @ApiOperation("修改 用户")
    @RequestMapping(value = "/file", method = RequestMethod.PUT)
    public SimpleResponse<OrgFile> updateFile(@RequestBody OrgFile file) {
        fileService.update(file);
        return SimpleResponse.success(file);
    }

    @ApiOperation("创建 志诚报告机构配置")
    @PostMapping(value = "/report/config")
    public SimpleResponse<OrgReportConfig> createReportConfig(@RequestBody OrgReportConfig orgReportConfig) {
        OrgReportConfigExample example = new OrgReportConfigExample();
        example.or().andOrgIdEqualTo(orgReportConfig.getOrgId());
        long count = orgReportConfigService.count(example);
        If.of(count > 0).isTrue(() -> {
            orgReportConfigService.update(orgReportConfig);
        });
        If.of(count == 0).isTrue(() -> {
            orgReportConfigService.create(orgReportConfig);
        });

        return SimpleResponse.success(orgReportConfig);
    }

    @ApiOperation("获取 志诚报告机构配置")
    @GetMapping("/{orgId}/report/config")
    public SimpleResponse<OrgReportConfig> queryReportConfig(@ApiParam("机构ID") @PathVariable("orgId") Integer orgId) {
        OrgReportConfigExample example = new OrgReportConfigExample();
        example.or().andOrgIdEqualTo(orgId);
        OrgReportConfig result = Utils.getFirst(orgReportConfigService.query(example));
        return SimpleResponse.success(result);
    }

}
