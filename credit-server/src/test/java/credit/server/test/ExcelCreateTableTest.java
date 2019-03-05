package credit.server.test;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Description:
 * @Author: zxy
 * @Date: 下午6:33 2018/7/9
 */
public class ExcelCreateTableTest {

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/zxy/Downloads/dd4.xlsx";
        Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));

        Sheet sheet = workbook.getSheetAt(0);

        StringBuilder sb = new StringBuilder(" CREATE TABLE `project_form_detail` ( " +
                "  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',\n" +
                "  `project_id` int(11) DEFAULT NULL,\n" +
                "  `org_id` int(11) DEFAULT NULL,\n" +
                "  `head_org_id` int(11) DEFAULT NULL,\n" +
                "  `project_no` varchar(20) DEFAULT NULL,\n" +
                "  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `create_user` varchar(50) DEFAULT NULL,\n" +
                "  `status` smallint(6) DEFAULT '0',\n" +
                "  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `modify_user` varchar(60) DEFAULT NULL,\n" +
                "  `create_user_id` int(11) DEFAULT NULL,\n" +
                "  `modify_user_id` int(11) DEFAULT NULL, ");
        int a= 0;
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            int i1 = ((int) row.getCell(5).getNumericCellValue());
            String column = "`" + row.getCell(3).getStringCellValue().toLowerCase() + "` varchar(" + i1 + ") DEFAULT NULL COMMENT '" + row.getCell(4).getStringCellValue() + "'";
            sb.append(column);
//            if( i < sheet.getLastRowNum()){
                sb.append(",");
//            }
            System.out.println(column);
        }
        System.out.println("==========" + a);

        sb.append(" PRIMARY KEY (`id`) USING BTREE ) ENGINE=MyISAM DEFAULT CHARSET=utf8;");

        workbook.close();
        System.out.println(sb.toString());
    }
}
