package com.cupshe.ak.core;

import lombok.Getter;

/**
 * Key value mapping
 *
 * @author zxy
 */
@Getter
public class Kv {

    public static final Kv EMPTY = new Kv();

    /*** KEY */
    private final String key;

    /*** VALUE */
    private final Object value;

    private Kv next;

    private Kv() {
        this(null, null);
    }

    public Kv(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public void setNext(Kv next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return String.format("{key: %s, value: %s}", key, value);
    }
}
