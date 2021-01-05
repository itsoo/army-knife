package com.cupshe.ak.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * JsonUtils
 *
 * @author zxy
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return MAPPER.readValue(json, clazz);
    }

    public static <T> T jsonToObject(String json, JavaType javaType) throws JsonProcessingException {
        return MAPPER.readValue(json, javaType);
    }

    public static <T> String objectToJson(T params) throws JsonProcessingException {
        return MAPPER.writeValueAsString(params);
    }

    public static <T> List<T> convertList(Object list, Class<T> clazz) {
        return MAPPER.convertValue(list, getListType(clazz));
    }

    public static <T> List<T> convertList(Object list, JavaType javaType) {
        return MAPPER.convertValue(list, javaType);
    }

    public static JavaType getListType(Class<?> clazz) {
        return MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
    }

    public static JavaType getObjectType(Type genericType) {
        if (genericType instanceof ParameterizedType) {
            Type[] actualTypes = ((ParameterizedType) genericType).getActualTypeArguments();
            JavaType[] javaTypes = new JavaType[actualTypes.length];
            for (int i = 0; i < actualTypes.length; i++) {
                javaTypes[i] = getObjectType(actualTypes[i]);
            }

            Class<?> rowClass = (Class<?>) ((ParameterizedType) genericType).getRawType();
            return TypeFactory.defaultInstance().constructParametricType(rowClass, javaTypes);
        }

        return TypeFactory.defaultInstance().constructParametricType((Class<?>) genericType, new JavaType[0]);
    }
}
