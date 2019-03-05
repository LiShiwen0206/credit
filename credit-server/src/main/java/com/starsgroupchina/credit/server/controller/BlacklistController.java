package com.starsgroupchina.credit.server.controller;

import com.starsgroupchina.common.XStatus;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.objects.If;
import com.starsgroupchina.common.response.ListResponse;
import com.starsgroupchina.common.response.SimpleResponse;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.model.Blacklist;
import com.starsgroupchina.credit.bean.model.BlacklistExample;
import com.starsgroupchina.credit.bean.model.Member;
import com.starsgroupchina.credit.bean.model.MemberExample;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.service.BlacklistService;
import com.starsgroupchina.credit.server.service.system.MemberService;
import com.starsgroupchina.credit.server.utils.DateUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhangfeng on 2018-5-9.
 */
@Slf4j
@RestController
@Api(tags = "CREDIT-SWAGGER31", description = "黑名单 - BlacklistController")
@RequestMapping(value = "/black", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BlacklistController {

    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private MemberService memberService;
    /**
     * 黑名单列表
     */
    /**
     * 黑名单列表
     */
    @ApiOperation("黑名单列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ListResponse<Blacklist> query(@RequestParam(value = "index", defaultValue = "1") int index,
                                         @RequestParam(value = "limit", defaultValue = "20") int limit,
                                         @ApiParam("名称") @RequestParam(value = "name", required = false) String name,
                                         @ApiParam("证件号码") @RequestParam(value = "idCard", required = false) String idCard,
                                         @ApiParam("类型") @RequestParam(value = "userType", required = false) String userType,
                                         @ApiParam("申报人")@RequestParam(value = "userName", required = false) String userName,
                                         @RequestParam(value = "status", required = false) List<Short> status,
                                         @RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
                                         @RequestParam(value = "end", defaultValue = "1895538782000") Long end) {
        List<Integer> declareUserList =null;
        if (StringUtils.isNotEmpty(userName)) {
            MemberExample memberExample = new MemberExample();
            memberExample.createCriteria().andNameLike("%" + userName.trim() + "%");
            declareUserList = memberService.query(memberExample).map(member -> member.getId()).collect(toList());
        }

        BlacklistExample example = new BlacklistExample();
        BlacklistExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(name)).isTrue(() -> criteria.andNameLike("%" + name.trim() + "%"));
        If.of(!StringUtils.isEmpty(idCard)).isTrue(() -> criteria.andIdCardLike("%" + idCard.trim() + "%"));
        If.of(!StringUtils.isEmpty(userType)).isTrue(() -> criteria.andUserTypeEqualTo(userType.trim()));
        if (CollectionUtils.isNotEmpty(declareUserList)){
            criteria.andCreateUserIdIn(declareUserList);
        }
        example.setAdditionalWhere(String.format("(create_time BETWEEN '%s' AND '%s')", DateUtil.timestampReversDate(begin), DateUtil.timestampReversDate(end)));
        long count = blacklistService.count(example);
        example.setOrderByClause("create_time desc");
        example.setOffset((index - 1) * limit);
        example.setLimit(limit);
        List<Blacklist> result = blacklistService.query(example).collect(toList());
        return ListResponse.success(result, count, index, limit);
    }

    /**
     * 根据黑名单ID获取
     */
    @ApiOperation("根据黑名单ID获取")
    @RequestMapping(value = "/{blackId}", method = RequestMethod.GET)
    public SimpleResponse<Blacklist> get(@ApiParam("黑名单ID") @PathVariable("blackId") Integer blackId) {
        Blacklist response = blacklistService.getById(blackId);
        return SimpleResponse.success(response);
    }

    /**
     * 新增黑名单
     */
    @ApiResponses({
            @ApiResponse(code = ErrorCode.BLACKLIST_USER_WAS_EXISTS, message = "该用户已存在黑名单中")
    })
    @ApiOperation("新增黑名单")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public SimpleResponse<Blacklist> create(@RequestBody Blacklist black) {
        AuthMember authMember= (AuthMember) ContextHolder.getContext().getData();
        black.setSubmitter(authMember.getMember().getName());
        BlacklistExample blacklistExample = new BlacklistExample();
        String where = String.format(
                "(id_card='%s' and user_type='个人' and status =1) or (name='%s' and user_type='企业' and status =1)", black.getIdCard(), black.getName());
        blacklistExample.setAdditionalWhere(where);
        Blacklist blacklist = Utils.getFirst(blacklistService.query(blacklistExample));
        If.of(blacklist != null).isTrueThrow(() -> new AppException(ErrorCode.BLACKLIST_USER_WAS_EXISTS));
        blacklistService.create(black);
        return SimpleResponse.success(black);
    }

    /**
     * 根据ID 删除
     */
    @ApiOperation("根据ID 删除")
    @RequestMapping(value = "/{blackId}", method = RequestMethod.DELETE)
    public SimpleResponse delete(@PathVariable("blackId") Integer blackId) {
        blacklistService.deleteById(blackId);
        return SimpleResponse.success();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public SimpleResponse<Blacklist> update(@RequestBody Blacklist blacklist) {
        BlacklistExample blacklistExample = new BlacklistExample();
        String where = String.format(
                "((id_card='%s' and user_type='个人') or (name='%s' and user_type='企业')) and id<>%s",
                blacklist.getIdCard(), blacklist.getName(), blacklist.getId());
        blacklistExample.setAdditionalWhere(where);
        Blacklist black = Utils.getFirst(blacklistService.query(blacklistExample));
        If.of(black != null).isTrueThrow(() -> new AppException(ErrorCode.BLACKLIST_USER_WAS_EXISTS));

        blacklistService.update(blacklist);
        return SimpleResponse.success(blacklist);
    }

    @ApiOperation("excel导出")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportBlacklist(@ApiParam("姓名") @RequestParam(value = "name", required = false) String name,
                                @ApiParam("证件号码") @RequestParam(value = "idCard", required = false) String idCard,
                                @ApiParam("注册号") @RequestParam(value = "registerNo", required = false) String registerNo,
                                @ApiParam("创建人") @RequestParam(value = "createUser", required = false) String createUser,
                                @ApiParam("解除人") @RequestParam(value = "relieveUser", required = false) String relieveUser,
                                @ApiParam("类型") @RequestParam(value = "userType", required = false) String userType,
                                @RequestParam(value = "status", required = false) List<Short> status,
                                @RequestParam(value = "begin", defaultValue = "1451630690") Long begin,
                                @RequestParam(value = "end", defaultValue = "1895538782000") Long end,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        BlacklistExample example = new BlacklistExample();
        BlacklistExample.Criteria criteria = example.createCriteria();
        If.of(!CollectionUtils.isEmpty(status)).isTrue(() -> criteria.andStatusIn(status));
        If.of(!StringUtils.isEmpty(name)).isTrue(() -> criteria.andNameLike("%" + name + "%"));
        If.of(!StringUtils.isEmpty(idCard)).isTrue(() -> criteria.andIdCardLike("%" + idCard + "%"));
        If.of(!StringUtils.isEmpty(createUser)).isTrue(() -> criteria.andCreateUserLike("%" + createUser + "%"));
        If.of(!StringUtils.isEmpty(relieveUser)).isTrue(() -> criteria.andRelieveUserLike("%" + relieveUser + "%"));
        If.of(!StringUtils.isEmpty(userType)).isTrue(() -> criteria.andUserTypeEqualTo(userType));
        example.setAdditionalWhere(String.format("(create_time BETWEEN '%s' AND '%s')", DateUtil.timestampReversDate(begin), DateUtil.timestampReversDate(end)));
        example.setOrderByClause("create_time desc");
        exportExcel(request, response, example);
    }

    @ApiResponses({
            @ApiResponse(code = ErrorCode.BLACKLIST_IMPORT_UNKOWN_ERROR, message = "黑名单导入出错"),
            @ApiResponse(code = ErrorCode.BLACKLIST_IMPORT_EMPTY, message = "导入数据为空"),
            @ApiResponse(code = ErrorCode.BLACKLIST_IMPORT_ILLEGAL_TYPE, message = "类型不能为空"),
            @ApiResponse(code = ErrorCode.BLACKLIST_IMPORT_ILLEGAL_ID_CARD, message = "身份证号不能为空")
    })
    @ApiOperation("excel导入")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public SimpleResponse importBlacklist(@RequestParam(value = "file") MultipartFile file) {
        this.importExcel(file);

        return SimpleResponse.success();
    }

    private void importExcel(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row;

            //sheet.getLastRowNum() 由0开始
            if (sheet == null || sheet.getLastRowNum() < 1) {
                throw new AppException(ErrorCode.BLACKLIST_IMPORT_EMPTY);
            } else {
                log.info("import blacklist start");
                if (sheet.getLastRowNum() > 0) {
                    for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                        row = sheet.getRow(i);
                        if (row != null && row.getCell(0) != null) {
                            String name = row.getCell(0).getStringCellValue();
                            if (StringUtils.isBlank(name)) {
                                continue;
                            }
                            String icCard = row.getCell(1).getStringCellValue();
                            String type = row.getCell(2).getStringCellValue();
                            if (StringUtils.isBlank(type)) {
                                throw new AppException(ErrorCode.BLACKLIST_IMPORT_ILLEGAL_TYPE);
                            }
                            BlacklistExample blacklistExample = new BlacklistExample();
                            if ("个人".equals(type)) {
                                if (StringUtils.isBlank(icCard)) {
                                    throw new AppException(ErrorCode.BLACKLIST_IMPORT_ILLEGAL_ID_CARD);
                                }
                                String regex = "^\\d{13}\\d*(\\d|X|x)$";
                                if (!icCard.matches(regex)) {
                                    continue;
                                }
                                blacklistExample.createCriteria().andIdCardEqualTo(icCard);
                            } else {
                                blacklistExample.createCriteria().andNameEqualTo(name);
                            }
                            blacklistExample.createCriteria().andStatusEqualTo(XStatus.FORBIDDEN.key());
                            blacklistExample.createCriteria().andUserTypeEqualTo(type);
                            if (blacklistService.count(blacklistExample) > 0) {
                                continue;
                            }
                            String submitReason = row.getCell(3).getStringCellValue();
                            if (StringUtils.isBlank(submitReason)) {
                                continue;
                            }

                            String submitter = row.getCell(4).getStringCellValue();
                            Date submitterDate = row.getCell(5).getDateCellValue();
                            Blacklist blacklist = new Blacklist();
                            blacklist.setName(name);
                            blacklist.setIdCard(icCard);
                            blacklist.setUserType(type);
                            blacklist.setSubmitter(submitter);
                            blacklist.setSubmitReason(submitReason);
                            blacklist.setCreateTime(submitterDate);
                            blacklist.setStatus(XStatus.FORBIDDEN.key());
                            Member currentUser = ((AuthMember) ContextHolder.getContext().getData()).getMember();
                            blacklist.setCreateUserId(currentUser.getCreateUserId());
                            blacklist.setCreateUser(currentUser.getName());
                            this.create(blacklist);
                        }
                    }
                }
                log.info("import balcklist completed");
            }
            workbook.close();
            is.close();

        } catch (AppException e) {
            log.error("", e);
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new AppException(ErrorCode.BLACKLIST_IMPORT_UNKOWN_ERROR, e);
        }
    }

    private void exportExcel(HttpServletRequest request, HttpServletResponse response, BlacklistExample blacklistExample) {
        SXSSFWorkbook workbook = new SXSSFWorkbook(15000);
        SXSSFSheet sheet = workbook.createSheet("黑名单");
        SXSSFRow headerRow = sheet.createRow(0);

        //set style for header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(true);//加粗
        headerStyle.setFont(font);

        String[] headers = new String[]{"名称", "身份证号", "类型", "申报原因", "申报人", "申报时间", "解除原因", "解除人", "解除时间"};
        for (int i = 0; i < headers.length; i++) {
            SXSSFCell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
            headerCell.setCellStyle(headerStyle);
        }


        Optional.ofNullable(blacklistService.query(blacklistExample).collect(toList())).ifPresent(blacklists -> {
            for (int i = 0; i < blacklists.size(); i++) {
                Blacklist blacklist = blacklists.get(i);
                SXSSFRow row = sheet.createRow(i + 1);
                row.setHeight((short) (15.95 * 20));
                row.createCell(0).setCellValue(blacklist.getName());
                row.createCell(1).setCellValue(blacklist.getIdCard());
                row.createCell(2).setCellValue(blacklist.getUserType());
                row.createCell(3).setCellValue(blacklist.getSubmitReason());
                row.createCell(4).setCellValue(blacklist.getSubmitter());
                row.createCell(5).setCellValue(DateUtil.formatDate(blacklist.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                row.createCell(6).setCellValue(StringUtils.isEmpty(blacklist.getRelieveUser()) ? null : blacklist.getRelieveReason());
                row.createCell(7).setCellValue(blacklist.getRelieveUser());
                row.createCell(8).setCellValue(StringUtils.isEmpty(blacklist.getRelieveUser()) ? null : DateUtil.formatDate(blacklist.getRelieveTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        });


        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + getFileNameHeader("黑名单.xlsx", request));
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("create excel for blacklist error.", e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("close workbook error.", e);
            }
        }
    }

    /**
     * 根据不同的客户端对文件名做不同的编码
     */
    private String getFileNameHeader(String fileName, HttpServletRequest req) {
        if (req == null) {
            return fileName;
        }
        String agent = req.getHeader("User-Agent");
        try {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(agent)) {
                if (agent.toLowerCase().contains("firefox") || agent.toLowerCase().contains("chrome") || agent.toLowerCase().contains("safari")) {
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
                } else if (agent.toLowerCase().contains("msie") || agent.toLowerCase().contains("mozilla")) {
                    fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
                } else {
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                }
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException, fileName : {}, Charset: {}", fileName, "UTF-8");
        }
        return fileName;
    }

}
