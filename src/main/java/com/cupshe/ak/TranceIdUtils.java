package com.cupshe.ak;

import javax.servlet.http.HttpServletRequest;

/**
 * TranceIdUtils
 *
 * @author zxy
 */
public class TranceIdUtils {

    public static String getTranceId() {
        try {
            return BaseConstant.TRANCE_ID_STORE.get();
        } finally {
            BaseConstant.TRANCE_ID_STORE.remove();
        }
    }

    public static String getTranceId(HttpServletRequest req) {
        String tranceId = req.getHeader(BaseConstant.TRANCE_ID_KEY);
        if (tranceId == null || "".equals(tranceId.trim())) {
            return getTranceId();
        }

        return tranceId;
    }
}
