package credit.server.test;


import com.starsgroupchina.credit.bean.model.NumberRule;
import com.starsgroupchina.credit.bean.model.NumberRuleExample;
import com.starsgroupchina.credit.server.Application;
import com.starsgroupchina.credit.server.service.NumberService;
import com.starsgroupchina.credit.server.service.system.OrgService;
import com.starsgroupchina.credit.server.service.third.ThirdDataValidService;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(classes = Application.class)
@ActiveProfiles(value = "test")
public class TestOrg {

    @Autowired
    private OrgService orgService;

    @Autowired
    private NumberService numberRuleService;

    @Autowired
    private ThirdDataValidService thirdDataValidService;

    @Test
    public void testOrg() {
        String orgLayer = orgService.getOrgLayer(1045, Strings.EMPTY);
    }

    @Test
    public void testCredit() {
        String orgLayer = orgService.getOrgLayer(1045, Strings.EMPTY);
    }

    @Test
    public void testGetNumber(){
        NumberRuleExample example = new NumberRuleExample();
        example.createCriteria().andNoNameEqualTo("申报编号").andOrgIdEqualTo(10109);
        NumberRule numberRule = com.starsgroupchina.common.utils.Utils.getFirst(numberRuleService.query(example));
        String number = numberRuleService.getNumber(numberRule,10);
        System.out.println(number);
    }

    @Test
    public void testThird(){

        thirdDataValidService.validThirdData("SHJG201810240001");
    }
}
