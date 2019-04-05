package cn.yanglj65.www.eloth_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ServletComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan
public class ElothBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElothBackendApplication.class, args);
    }
}
