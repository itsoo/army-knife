package com.cupshe.ak;

import com.cupshe.ak.core.Kv;

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
        if (fields == null || fields.length == 0) {
            return Collections.emptyList();
        }

        List<Kv> result = new ArrayList<>();
        for (Field f : fields) {
            Object value = getFieldValueByName(f.getName(), obj);
            if (f.getName() != null) {
                result.add(new Kv(f.getName(), value));
            }
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

    private static String getterMethodName(String fieldName) {
        char c = fieldName.charAt(0);
        if (needUpperFirstLetter(c)) {
            c -= ('a' - 'A');
        }

        return "get" + c + (fieldName.length() > 1 ? fieldName.substring(1) : "");
    }

    private static boolean needUpperFirstLetter(char c) {
        return c >= 'a' && c <= 'z';
    }
}
