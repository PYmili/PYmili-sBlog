package icu.pymiliblog.pymillsblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("icu.pymiliblog.pymillsblog")
public class PYmillSBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PYmillSBlogApplication.class, args);
    }

}
