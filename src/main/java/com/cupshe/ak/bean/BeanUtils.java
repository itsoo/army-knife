package com.cupshe.ak.bean;

import com.cupshe.ak.core.Kv;
import com.cupshe.ak.text.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BeanUtils
 *
 * @author zxy
 */
public class BeanUtils {

    public static List<Kv> getObjectProperties(Object obj) {
        if (obj == null) {
            return Collections.emptyList();
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields.length == 0) {
            return Collections.emptyList();
        }

        List<Kv> result = new ArrayList<>();
        for (Field f : fields) {
            result.add(new Kv(f.getName(), getFieldValueByName(f.getName(), obj)));
        }

        return result;
    }

    public static Object getFieldValueByName(String filedName, Object o) {
        String methodName = getterMethodName(filedName);

        try {
            return o.getClass().getDeclaredMethod(methodName).invoke(o);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getterMethodName(String fieldName) {
        return "get" + StringUtils.upperFirstLetter(fieldName);
    }

    public static String getBeanName(String qualifier) {
        if (qualifier == null) {
            return null;
        }

        int i = qualifier.lastIndexOf('.');
        if (i < 0) {
            return qualifier;
        }

        return StringUtils.lowerFirstLetter(qualifier.substring(i + 1));
    }
}
