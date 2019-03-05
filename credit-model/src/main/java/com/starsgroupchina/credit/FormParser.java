package com.starsgroupchina.credit;

import com.google.common.collect.Maps;
import com.starsgroupchina.common.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangfeng on 2018-6-8.
 */

public class FormParser {


    public static Map<String, String> parse(String form) {
        Map result = Maps.newHashMap();

        List<String> list = JsonUtil.toList(form, String.class);
        for (String json : list) {
            Map<String, Object> map = JsonUtil.toMap(json);
            Map obj = (Map) map.get("obj");
//            if (obj.get("type").equals("input")) {
//                result.put(obj.get("name"), obj.get("value"));
//            }
            String type = (String) obj.get("type");
            if (StringUtils.isEmpty(type))
                continue;

            if (type.equals("title") || type.equals("hr") || type.equals("p"))
                continue;

            if (type.equals("group")) {
                List<Map> items = (List) obj.get("items");
                for (Map item : items) {
                    result.put((item.get("name")+"").toLowerCase(), item.get("value"));
                }
            } else if (type.equals("address")) {
                result.put(obj.get("name").toString().toLowerCase(), obj.get("address_chinese")+"");
            } else {
                result.put(obj.get("name").toString().toLowerCase(), obj.get("value")+"");
            }
        }
        return result;
    }

}



