package com.cupshe.ak.objects;

import com.cupshe.ak.core.Kv;
import com.cupshe.ak.core.Kvs;
import com.cupshe.ak.text.StringUtils;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * ObjectClasses
 *
 * @author zxy
 */
public class ObjectClasses {

    public static Kvs getObjectProperties(Object object) {
        if (object == null) {
            return Kvs.emptyKvs();
        }

        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length == 0) {
            return Kvs.emptyKvs();
        }

        Kvs result = new Kvs();
        for (Field f : fields) {
            result.add(new Kv(f.getName(), getValueByFieldName(f.getName(), object)));
        }

        return result;
    }

    public static Object getValueByFieldName(String fieldName, Object instance) {
        String getterMethodName = "get" + StringUtils.upperFirstLetter(fieldName);
        return getValueByMethodName(getterMethodName, instance);
    }

    public static Object getValueByMethodName(String methodName, Object instance) {
        try {
            return getValueByMethod(
                    instance.getClass().getDeclaredMethod(methodName),
                    instance);
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getValueByMethod(Method method, Object instance) {
        try {
            int mthModifier = method.getModifiers();
            int clsModifier = method.getDeclaringClass().getModifiers();
            if ((!Modifier.isPublic(mthModifier) || !Modifier.isPublic(clsModifier))
                    && !method.isAccessible()) {
                method.setAccessible(true);
            }

            return method.invoke(instance);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getShortNameAsProperty(String qualifier) {
        if (qualifier == null) {
            return null;
        }

        int i = qualifier.lastIndexOf('.');
        if (i < 0) {
            return StringUtils.lowerFirstLetter(qualifier);
        }

        return qualifier.length() > 1
                ? StringUtils.lowerFirstLetter(qualifier.substring(i + 1))
                : qualifier;
    }

    public static boolean isInconvertibleClass(Class<?> clazz) {
        return !clazz.isPrimitive()
                && !clazz.isAssignableFrom(Boolean.class)
                && !clazz.isAssignableFrom(Character.class)
                && !clazz.isAssignableFrom(Byte.class)
                && !clazz.isAssignableFrom(Short.class)
                && !clazz.isAssignableFrom(Integer.class)
                && !clazz.isAssignableFrom(Long.class)
                && !clazz.isAssignableFrom(Float.class)
                && !clazz.isAssignableFrom(Double.class)
                && !clazz.isAssignableFrom(Void.class)
                && !clazz.isAssignableFrom(String.class)
                && !clazz.isAnnotation();
    }

    public static boolean hasAnnotation(Class<?> clazz, Class<?> annotationType) {
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            Class<? extends Annotation> at = annotation.annotationType();
            if (annotationType.isAssignableFrom(at)) {
                return true;
            }

            if (!isMetaAnnotation(at) && hasAnnotation(at, annotationType)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isMetaAnnotation(Class<?> clazz) {
        return clazz == Documented.class
                || clazz == Inherited.class
                || clazz == Native.class
                || clazz == Repeatable.class
                || clazz == Retention.class
                || clazz == Target.class;
    }
}
