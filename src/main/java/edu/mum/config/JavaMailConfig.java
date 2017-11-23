package edu.mum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Erdenebayar on 11/22/2017
 */
@Configuration
public class JavaMailConfig {

    @Bean
    @Lazy
    JavaMailSender mailSender() throws IOException {
        Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("javamail.properties"));
        JavaMailSenderImpl b = new JavaMailSenderImpl();
        b.setJavaMailProperties(properties);
        b.setUsername(properties.getProperty("mail.smtp.user"));
        b.setPassword(properties.getProperty("mail.smtp.password"));
        return b;
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("mail/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE);
        resolver.setCacheable(true);
        return  resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(emailTemplateResolver());
        return engine;
    }

    @Bean(name = "emailPoolExecutor", destroyMethod = "shutdownNow")
    ExecutorService emailExecutorService(){
        return Executors.newFixedThreadPool(5);
    }

}
