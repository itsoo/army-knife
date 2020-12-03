package com.cupshe.ak.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cupshe.ak.dto.CustomerInfoDto;
import com.cupshe.ak.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.cupshe.ak.constants.SystemConstants.*;
import static com.cupshe.ak.enums.ExceptionEnums.TOKENERROR;
import static com.cupshe.ak.enums.ExceptionEnums.TOKENEXPIRED;

/**
 * Description: TODO
 * User: chenzuofu
 * Date: 2020-07-29
 * Time: 下午4:54
 */
@Slf4j
public class JwtUtil {

    /**
     * 密钥
     */
    private static final String SECRET = "CUPSHE:MALL:CUSTOMER:TOKEN";

    /**
     * 校验解析token，并返回用户信息
     * @param token
     * @return
     */
    public static CustomerInfoDto getCustomerFromToken(String token){
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (TokenExpiredException e) {
            //token过期
            throw new BusinessException(TOKENEXPIRED.getErrCode() ,TOKENEXPIRED.getErrorMessage()) ;
        } catch (Exception e) {
            //解析token异常
            log.error(e.getMessage());
            throw new BusinessException(TOKENERROR.getErrCode() ,TOKENERROR.getErrorMessage()) ;
        }
        Map<String, Claim> map = jwt.getClaims();
        CustomerInfoDto customerInfoDto = new CustomerInfoDto();
        if( !map.containsKey(CUSTOMERID) || !map.containsKey(CUSTOMEREMAIL) || !map.containsKey(CUSTOMERLOGINSTATUS) || !map.containsKey(CUSTOMERNAME)){
            //如果token信息缺少key，抛异常
            throw new BusinessException(TOKENERROR.getErrCode() ,TOKENERROR.getErrorMessage()) ;
        }
        //封装并返回用户信息
        customerInfoDto.setCustomerId(map.get(CUSTOMERID).asLong());
        customerInfoDto.setLoginStatus(map.get(CUSTOMERLOGINSTATUS).asInt());
        customerInfoDto.setEmail(map.get(CUSTOMEREMAIL).asString());
        customerInfoDto.setCustomerName(map.get(CUSTOMERNAME).asString());
        return customerInfoDto ;
    }
}
