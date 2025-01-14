package icu.pymiliblog.pymillsblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("icu.pymiliblog.pymillsblog")
public class PYmillSBlogApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PYmillSBlogApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PYmillSBlogApplication.class);
    }

}
