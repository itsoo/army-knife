package com.cupshe.ak.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Key value mapping
 *
 * @author zxy
 */
@Getter
@AllArgsConstructor
public class Kv {

    /*** KEY */
    private final String key;

    /*** VALUE */
    private final Object value;

    @Override
    public String toString() {
        return String.format("{key: %s, value: %s}", key, value);
    }
}
