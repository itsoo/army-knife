package com.cupshe.ak.request;

import com.cupshe.ak.common.BaseConstant;
import com.cupshe.ak.core.Kv;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * RequestParameterUtils
 *
 * @author zxy
 */
public class RequestParameterUtils {

    public static String[] getParam(String key) {
        Map<String, String[]> store = BaseConstant.REQ_PARAMS_STORE.get();
        return store != null ? store.get(key) : null;
    }

    public static List<Kv> getParams() {
        Map<String, String[]> store = BaseConstant.REQ_PARAMS_STORE.get();
        return Optional.ofNullable(store)
                .orElse(Collections.emptyMap())
                .entrySet()
                .parallelStream()
                .map(t -> new Kv(t.getKey(), t.getValue()))
                .collect(Collectors.toList());
    }
}
