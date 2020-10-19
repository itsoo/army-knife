package com.cupshe.ak.core;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Helper
 *
 * @author zxy
 */
public class Helper extends ClassValue<Map<Object, Object>> {

    private static final Helper INSTANCE = new Helper();

    public static Map<Object, Object> of(Class<? extends Enum<?>> type) {
        return INSTANCE.get(type);
    }

    @Override
    protected Map<Object, Object> computeValue(Class<?> type) {
        Map<Object, Object> map = new HashMap<>(1 << 4);
        List<Kv> collection = new LinkedList<>();
        for (Field field : type.getDeclaredFields()) {
            if (field.isEnumConstant()) {
                addValue(map, field, collection);
            }
        }

        map.put(VirtualEnum.ENUM_ALL, collection);
        return Collections.unmodifiableMap(map);
    }

    private void addValue(Map<Object, Object> map, Field field, List<Kv> collection) {
        try {
            Object constant = field.get(null);
            Ev ev = field.getAnnotation(Ev.class);
            map.put(ev.value(), constant);
            map.put(constant, ev.label());
            map.put(field.getName() + VirtualEnum.ENUM_NAME, ev.value());
            collection.add(new Kv(ev.value(), ev.label()));
        } catch (IllegalAccessException ignore) {}
    }
}
