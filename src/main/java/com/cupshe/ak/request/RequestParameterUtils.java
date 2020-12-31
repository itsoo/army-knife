package com.cupshe.ak.request;

import com.cupshe.ak.common.BaseConstant;
import com.cupshe.ak.core.Kv;
import com.cupshe.ak.core.Kvs;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

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

    public static Kvs getParams() {
        Kvs result = new Kvs();
        Map<String, String[]> store = BaseConstant.REQ_PARAMS_STORE.get();
        Optional.ofNullable(store)
                .orElse(Collections.emptyMap())
                .forEach((key, value) -> result.add(new Kv(key, value)));
        return result;
    }
}
