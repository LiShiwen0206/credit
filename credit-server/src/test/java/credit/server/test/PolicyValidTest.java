package credit.server.test;

import com.starsgroupchina.common.objects.Tuple;
import com.starsgroupchina.common.utils.Utils;
import com.starsgroupchina.credit.FormParser;
import com.starsgroupchina.credit.bean.model.*;
import com.starsgroupchina.credit.server.Application;
import com.starsgroupchina.credit.server.service.OrgConditionService;
import com.starsgroupchina.credit.server.service.PolicyService;
import com.starsgroupchina.credit.server.service.ProductService;
import com.starsgroupchina.credit.server.service.project.FormService;
import com.starsgroupchina.credit.server.service.project.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: zxy
 * @Date: 下午4:09 2018/6/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles({"uat"})
//@Ignore
public class PolicyValidTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FormService formService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private ProductService.ProductConditionConfigService productConditionConfigService;
    @Autowired
    private OrgConditionService orgConditionService;

//    private static final String projectNo = "SHJG201807240001";
    private static final String projectNo = "HXPH201811150033";
//     private static final String projectNo = "SHJG201808010001";
    @Test
    public void testValid() {
        Project project = projectService.getProjectByProjectNo(projectNo);


        ProjectFormExample projectFormExample = new ProjectFormExample();
        projectFormExample.createCriteria().andProjectNoEqualTo(projectNo);

        ProjectForm projectForm = formService.query(projectFormExample).collect(Collectors.toList()).get(0);

        Map<String, String> form = FormParser.parse(projectForm.getProjectForm());
        Tuple<Boolean, Map<PolicyGroup, List<PolicyField>>> result = policyService.validByPolicyGroup(form, project.getPolicyId(),project);
//        ProductConditionConfigExample example = new ProductConditionConfigExample();
//        example.createCriteria().andProductIdEqualTo(project.getProductId()).andOrgIdEqualTo(project.getOrgId());
//        ProductConditionConfig config = Utils.getFirst(productConditionConfigService.query(example));
//        Tuple<Boolean, Map<OrgConditionGroup, List<OrgConditionField>>> result = orgConditionService.validByConditonGroup(form, config.getConditionId());
        System.out.println(result);

    }


}
