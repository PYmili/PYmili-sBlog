package icu.pymiliblog.pymillsblog.config;

import icu.pymiliblog.pymillsblog.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc configurer
 * @author PYmili
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 身份验证拦截器
    private final AuthenticationInterceptor authenticationInterceptor;

    WebConfig(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    /**
     * 通过WebMvcConfigurer添加拦截器。
     * @param registry {@link InterceptorRegistry}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/reg")
                .excludePathPatterns("/user/verify");
    }
}
