package credit.server.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.starsgroupchina.common.context.Context;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.common.file.FileUploadService;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.RiskReport;
import com.starsgroupchina.credit.bean.UserInfo;
import com.starsgroupchina.credit.bean.enums.BuildReportSource;
import com.starsgroupchina.credit.bean.enums.ScoreType;
import com.starsgroupchina.credit.bean.extend.ReportExtend;
import com.starsgroupchina.credit.bean.model.Blacklist;
import com.starsgroupchina.credit.bean.model.ProjectReport;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.Application;
import com.starsgroupchina.credit.server.conf.DfConfig;
import com.starsgroupchina.credit.server.conf.QhConfig;
import com.starsgroupchina.credit.server.conf.TdConfig;
import com.starsgroupchina.credit.server.service.BlacklistService;
import com.starsgroupchina.credit.server.service.project.*;
import com.starsgroupchina.credit.server.service.system.MemberService;
import com.starsgroupchina.credit.server.service.system.MessageService;
import com.starsgroupchina.credit.server.service.third.ThirdCreditResultService;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.channels.MembershipKey;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(classes = Application.class)
@ActiveProfiles(value = "test")
public class TestCreditInfo {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ReportService reportService;

    @Autowired
    private MemberService memberService;
    @Autowired
    private CreditInfoService creditInfoService;

    @Autowired
    private ThirdCreditResultService thirdCreditResultService;

    @Autowired
    private TdConfig tdConfig;

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private QhConfig qhConfig;

    @Autowired
    private RelationService.RelationBlacklistService relationBlacklistService;

    @Autowired
    private DfConfig dfConfig;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private BlacklistService blacklistService;
    @Test
    public void testCredit() {
//        UserInfo userInfo = userService.getUserInfo("SHJG201811080004");
//        boolean b = blacklistService.validBlacklist(userInfo.getIdCard());
//        System.out.println(b);

        String projectNo = "SHJG201807230003";
        double score = scoreService.getScore(projectNo);
        System.out.println(score==0);
//        System.out.println(blacklistHitByIdCard);
//        List<Blacklist> blacklistHit = blacklistService.getBlacklistHit(userInfo);
//        relationBlacklistService.createBlacklist("HXPH201809270021");
//        System.out.println(blackListHit);
//        Map<String, String> scoreModify = creditInfoService.getRiskModelItem("SHJG201807300003", ScoreType.CREDIT_SIMPE);
//        Map<String, String> scoreModify1 = creditInfoService.getRiskModelItem("SHJG201807300003", ScoreType.CREDIT_DETAIL);
//        String isHaveValue = scoreModify1.get("isHaveValue");
//        System.out.println(isHaveValue);
//        System.out.println(Boolean.valueOf(isHaveValue));
//        System.out.println(scoreModify1);
//        System.out.println(scoreModify1);
//        Project project = projectService.getProjectByProjectNo("HXZC20180926000007");
//        Date applyDate = project.getApplyDate();
//        Instant instant = applyDate.toInstant();
//        ZoneId zoneId = ZoneId.systemDefault();
//        if (datePolicyFieldValue.indexOf(" ") > 0) {
//            date = LocalDateTime.parse("201", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
//        } else {
//        LocalDate date = LocalDate.parse("2017-04-03", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        }
//        String s = String.valueOf(DateUtil.betweenMonth(date, instant.atZone(zoneId).toLocalDate()));
//        System.out.println(s);

    }

    @Test
    public void testThird() {
        Map<String, String> scoreModify = thirdCreditResultService.getRiskModelItem("SHJG201810250001", ScoreType.THIRD_QH);
//        Map<String, String> scoreModify1 = thirdCreditResultService.getRiskModelItem("SHJG201807270003", ScoreType.THIRD_TD);
        System.out.println(scoreModify);
//        System.out.println(scoreModify1);
    }

    @Test
    public void testMessage(){
//        messageService.sendMessage(10,"HXPH201807190020","新任务", MessageTemplate.AssgnTemplate);
        MultipartFile file = null;
        try {
            file = new MockMultipartFile("测试excel上传.xlsx","测试excel上传.xlsx", null,new FileInputStream(new File("D:/testResource/测试excel上传.xlsx")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String credit = fileUploadService.uploadByByte("上传测试".getBytes(), "测试excel上传.xlsx","credit");
//      String credit1 = fileClient.upload(mockMultipartFile, "credit");
        System.out.println(credit);
    }


    @Test
    public void testReport(){
//        reportService.buildReport("HXPH201807300021", BuildReportSource.NORMAL);
        RiskReport riskReport = new RiskReport();
//        reportService.setCheckPhone(riskReport,"HXPH201807300021");
//        System.out.println(riskReport.getCheckPhone());
//        UserInfo userInfo = userService.getUserInfo("HXPH201809030020");
//        System.out.println(userInfo.getCompanyName());
//        String token = "F852C80772AB4C53AADAB08955734620";
        String token = "832104102E0A435BBAB430EB873901DD";
        AuthMember authMember = Optional.ofNullable(memberService.getAuthMember(token))
                .orElseThrow(() -> new AppException(ErrorCode.AUTH_ERROR));

        Context context = new Context(authMember);
        context.setUserId(authMember.getMember().getId());
        context.setUserName(authMember.getMember().getName());
        ContextHolder.setContext(context);
//        reportService.setWebsiteInfo(riskReport,userInfo,"HXPH201807300021");
        reportService.buildReport("SHJG201811070004",BuildReportSource.BLACK_HIT);
    }



    @Test
    public void testProject() {
        String projectNo = "HXPH201810100033";
        double score = scoreService.getScore(projectNo);
        ProjectReport report = reportService.getReport(projectNo);
        String body = report.getBody();
        ReportExtend reportExtend = new ReportExtend(report);
        try {
            RiskReport riskReport = MAPPER.readValue(body, RiskReport.class);
            RiskReport.CreditResult creditResult = riskReport.getCreditResult();
            //实时获取模型打分,并计算授信评分
            DecimalFormat df = new DecimalFormat("#.0000");
//            BigDecimal score = new BigDecimal(scoreService.getScore(projectNo));
//            BigDecimal creditScore = new BigDecimal(creditResult.getCreditScore());
//            double score = scoreService.getScore(projectNo);
            Double creditScore = Double.valueOf(df.format(creditResult.getCreditScore()));
//            double creditScore = creditResult.getCreditScore();
            NumberFormat nf = NumberFormat.getNumberInstance();
            // 保留两位小数
            nf.setMaximumFractionDigits(4);
            // 如果不需要四舍五入，可以使用RoundingMode.DOWN
            nf.setRoundingMode(RoundingMode.UP);
            System.out.println(nf.format(score*creditScore));
            double v = reportService.handlePoint(score * creditScore);
//            String format = ;
//           = Double.valueOf(df.format(score*creditScore));
            creditResult.setModelScore(score);
            creditResult.setCreditScore(score * creditResult.getCreditScore());
            reportExtend.setRiskReport(riskReport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
