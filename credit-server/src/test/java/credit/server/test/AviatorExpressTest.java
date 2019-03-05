package credit.server.test;

import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zxy
 * @Date: 下午3:35 2018/6/22
 */
public class AviatorExpressTest {

    public static void main(String[] args) {
//        Expression compile = AviatorEvaluator.compile("'0'<=monthlysupply && monthlysupply<'20000'");
        Expression compile = AviatorEvaluator.compile("(组1||组3)&&(组2||组4)");
        Map<String,Object> env = Maps.newHashMap();
        env.put("组1","false");
        Object result = compile.getVariableNames();//.execute(env);
        System.out.println(result);
    }

    @Test
    public void test1(){
        StringBuilder sb = new StringBuilder();
//        sb.append("string.contains(").append("张三,李四,王五").append(",'").append("name").append("') ");
//        sb.append("string.contains(").append("name").append(",'").append("张三,李四,王五").append("') ");
        sb.append("string.contains('").append("张三,李四,王五").append("',").append("name").append(") ");
        Map<String,Object> env = Maps.newHashMap();
        env.put("b029","张三,李四,王五");
        System.out.println(sb.toString());
        Expression compile = AviatorEvaluator.compile("string.contains('父母,子女',b029)");
        Object result = compile.execute(env);
//        Long result = (Long) AviatorEvaluator.execute(sb.toString(),env);
        System.out.println(result);
        Object execute = AviatorEvaluator.execute("string.contains('hello','h')");
        System.out.println(execute);
    }

    @Test
    public void test2(){
        String yourName = "Michael";
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("yourName", yourName);
        String result = (String) AviatorEvaluator.execute(" 'hello ' + yourName ", env);
        System.out.println(result);  // hello Michael
    }

    @Test
    public void test3(){
        Expression compile = AviatorEvaluator.compile("b029=='张三,李四,王五'");
        Map<String,Object> env = Maps.newHashMap();
        env.put("b029","张三,李四,王五");
        env.put("b","null");
        Object result = compile.execute(env);
        System.out.println(result);
    }
}
