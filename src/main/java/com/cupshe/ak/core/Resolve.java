package com.cupshe.ak.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * @author zxy
 */
public enum Resolve implements VirtualEnum<Resolve> {

    /*** OKAY */
    @Ev(value = "01", label = "是") OKAY,

    /*** NONE */
    @Ev(value = "02", label = "否") NONE;

    @JsonValue
    public String getLabel() {
        return (String) Helper.of(Resolve.class).get(this);
    }

    @JsonCreator
    public static Resolve getEnum(String value) {
        return (Resolve) Helper.of(Resolve.class).get(value);
    }

    @SuppressWarnings("unchecked")
    public static List<Kv> getAll() {
        return (List<Kv>) Helper.of(Resolve.class).get(ENUM_ALL);
    }
}
