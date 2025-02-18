package icu.pymiliblog.pymillsblog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS配置
 * @author PYmili
 */
@Slf4j
@Configuration
public class CorsConfig {

    /**
     * Cors configurer bean
     * @return {@link WebMvcConfigurer}
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        log.info("全局 CORS 配置加载 / Global CORS configuration loaded.");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允许所有路径
                        .allowedOrigins("*") // 允许所有来源，生产环境中应限制为特定来源
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                        .allowedHeaders("*"); // 允许的请求头
                        // .allowCredentials(true); // 如果需要携带凭证
            }
        };
    }
}