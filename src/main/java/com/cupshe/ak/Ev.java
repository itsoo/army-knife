package com.cupshe.ak;

import java.lang.annotation.*;

/**
 * Enum value and label
 *
 * @author zxy
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ev {

    /**
     * value
     *
     * @return String
     */
    String value();

    /**
     * label
     *
     * @return String
     */
    String label();
}
