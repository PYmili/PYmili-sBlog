package icu.pymiliblog.pymillsblog.interceptor;

import com.alibaba.fastjson2.JSONObject;
import icu.pymiliblog.pymillsblog.common.ApiResponseCommon;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * 身份验证拦截器
 * @author PYmili
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    /**
     * 拦截器预处理
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler {@link Object}
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        log.info("The authentication pre-handling.");
        log.info("身份验证拦截器预处理。");

        // 输出请求的数据日志
        String remoteAddr = request.getRemoteAddr();
        String requestURL = request.getRequestURL().toString();
        log.info("remote address: {}, request URL: {}", remoteAddr, requestURL);

        // 获取身份验证的头
        String authentication = request.getHeader("Authentication");
        // 验证身份
        if (JwtUtils.VerifyJwtIsValid(authentication)) {
            log.warn("Authentication failed!");
            // 构造错误返回json
            ApiResponseCommon notLogin = new ApiResponseCommon(
                    HttpStatus.NOT_FOUND.value(), "NOT_LOGIN", "未登录");
            response.getWriter().write(JSONObject.toJSONString(notLogin));
            return false;
        }

        // 放行
        return true;
    }

    /**
     * 目标资源方法运行后
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler  {@link Object}
     * @param modelAndView {@link ModelAndView}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        log.info("After the interceptor completes authentication.");
        log.info("身份验证完成。");
    }

    /**
     * 身份验证拦截器最后
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param handler {@link Object}
     * @param ex {@link Exception}
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) {
        log.info("Authentication interceptor Over.");
        log.info("身份验证拦截器结束");
    }
}
