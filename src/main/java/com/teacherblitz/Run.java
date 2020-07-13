package com.teacherblitz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 程序启动类
 *
 * @author: teacherblitz
 * @since: 2020/7/10
 */
@SpringBootApplication
public class Run {

    public static ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx){
        context = ctx;
        return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            AtomicInteger index = new AtomicInteger(1);
            Arrays.stream(beanNames).sorted().forEach(item -> {
                System.out.println(String.format("%s:%s", index, item));
                index.incrementAndGet();
            });
        };
    }
}
