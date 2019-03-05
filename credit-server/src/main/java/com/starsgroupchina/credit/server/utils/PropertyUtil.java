package com.starsgroupchina.credit.server.utils;


import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyUtil {

    /**
     * 反射获取request中的值，并设置
     */
    public static void set(Object object, HttpServletRequest request) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            PropertyDescriptor pd = null;
            String parameter = request.getParameter(f.getName());
            if (StringUtils.isEmpty(parameter)) {
                continue;
            }
            try {
                pd = new PropertyDescriptor(f.getName(), clazz);
                Method method = pd.getWriteMethod();//获得写方法
                setValue(method, parameter, object);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void setValue(Method setMethod, String value, Object tObject) throws Exception {
        Class<?>[] parameterTypes = setMethod.getParameterTypes();
        String parameterType = parameterTypes[0].toString();
        if ("class java.lang.String".equals(parameterType)) {
            setMethod.invoke(tObject, value);
        } else if ("class java.lang.Integer".equals(parameterType)) {
            setMethod.invoke(tObject, Integer.valueOf(value));
        } else if ("class java.lang.Long".equals(parameterType)) {
            setMethod.invoke(tObject, Long.valueOf(value));
        }
    }
}  