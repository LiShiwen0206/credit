package credit.server.test;

import com.starsgroupchina.common.context.Context;
import com.starsgroupchina.common.context.ContextHolder;
import com.starsgroupchina.common.exception.AppException;
import com.starsgroupchina.credit.bean.AuthMember;
import com.starsgroupchina.credit.bean.enums.BuildReportSource;
import com.starsgroupchina.credit.bean.extend.ReportExtend;
import com.starsgroupchina.credit.key.ErrorCode;
import com.starsgroupchina.credit.server.Application;
import com.starsgroupchina.credit.server.controller.project.ProjectReportController;
import com.starsgroupchina.credit.server.service.project.ReportService;
import com.starsgroupchina.credit.server.service.project.ScoreService;
import com.starsgroupchina.credit.server.service.system.MemberService;
import com.starsgroupchina.credit.server.service.third.ThirdDataValidService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @Description:
 * @Author: zxy
 * @Date: 下午4:09 2018/6/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles({"uat"})
public class RiskModelFieldCheckTest {

    @Autowired
    private ScoreService scoreService;

//    private static final String projectNo = "SHJG201807200001";
    private static final String projectNo = "HXPH201811090021";

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReportService projectReportController;
    @Autowired
    private ThirdDataValidService thirdDataValidService;
    @Test
    public void testValid() {

        ReportExtend shjg201811220004 = projectReportController.getReport("SHJG201811220004");
        System.out.println(shjg201811220004);
//        String token = "700D2999AD8243AE92C3D71F646667AC";
//        AuthMember authMember = Optional.ofNullable(memberService.getAuthMember(token))
//                .orElseThrow(() -> new AppException(ErrorCode.AUTH_ERROR));
//
//        Context context = new Context(authMember);
//        context.setUserId(authMember.getMember().getId());
//        context.setUserName(authMember.getMember().getName());
//        ContextHolder.setContext(context);
//        ReportExtend report = projectReportController.getReport(projectNo);
//        projectReportController.buildReport(projectNo, BuildReportSource.NORMAL);
//        System.out.println(report);
//        scoreService.computeScore(projectNo);
//        thirdDataValidService.validThirdData("SHJG201810240001");
    }
}
