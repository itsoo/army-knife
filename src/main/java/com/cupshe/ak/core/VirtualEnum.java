package com.cupshe.ak.core;

/**
 * 虚拟场
 *
 * @param <T> sub enum class
 * @author zxy
 */
public interface VirtualEnum<T extends VirtualEnum<?>> {

    /*** 枚举 Kv 列表 */
    String ENUM_ALL = "enumAll";

    /*** 名称后缀 */
    String ENUM_NAME = ".enumName";

    /**
     * 获取枚举名称
     *
     * @return String
     */
    String name();

    /**
     * 获取枚举类型
     *
     * @return Class&lt;? extends Enum&lt;?&gt;&gt;
     */
    Class<? extends Enum<?>> getDeclaringClass();

    /**
     * 获取枚举值
     *
     * @return String
     */
    default String getValue() {
        return (String) Helper.of(getDeclaringClass()).get(name() + ENUM_NAME);
    }

    /**
     * 判读是否相同
     *
     * @param e &lt;T extends VirtualEnum&gt;
     * @return boolean
     */
    default boolean isEquals(T e) {
        return this == e || (e != null && this.isEquals(e.getValue()));
    }

    /**
     * 判读是否相同
     *
     * @param value String
     * @return boolean
     */
    default boolean isEquals(String value) {
        return this.getValue().equals(value);
    }
}
