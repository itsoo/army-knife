package com.cupshe.ak.request;

import com.cupshe.ak.dto.CustomerInfoDto;
import com.cupshe.ak.exception.BusinessException;
import com.cupshe.ak.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.cupshe.ak.constants.SystemConstants.TOKEN;
import static com.cupshe.ak.enums.ExceptionEnums.NOTLOGIN;

/**
 *
 */
@Component
@Slf4j
public class TokenResolver implements HandlerMethodArgumentResolver {


    /**
     * 判断当前方法的参数是否声明了 CustomerInfoDto类型
     *
     * @param parameter 被修饰的方法参数。
     * @return 当且仅当参数声明了 Api类型，返回 true；否则，返回 false。
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == CustomerInfoDto.class;
    }

    /**
     * 为控制器方法参数设置Api的值。
     * <p>
     * 返回 token 对应 CustomerInfoDto 对象。
     *
     * @param parameter 控制器方法的参数
     * @param container 容器
     * @param web       原生的请求
     * @param binder    binder工厂类。
     * @return token 对应 CustomerInfoDto 对象
     * @throws Exception 必需的值不存在。
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest web, WebDataBinderFactory binder) throws Exception {
        if (!(web.getNativeRequest() instanceof HttpServletRequest)) {
            log.warn("received non-http request!");
            return null;
        }
        String token = web.getHeader(TOKEN);
        if (token == null ) {
            //未登录用户，返回空对象，交给业务处理
            return new CustomerInfoDto();
            //throw new BusinessException(NOTLOGIN.getErrCode(),NOTLOGIN.getErrorMessage());
        }
        return JwtUtil.getCustomerFromToken(token);
    }
}