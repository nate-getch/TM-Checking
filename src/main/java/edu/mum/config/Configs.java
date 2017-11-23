package edu.mum.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Erdenebayar on 11/22/2017
 */
@Configuration
@Import({
        JavaMailConfig.class,
        SchedulingConfig.class
})
@ImportResource("classpath:/context/*ontext.xml")
@ComponentScan({
        "edu.mum.service",
//        "edu.mum.repository",
        "edu.mum.controller",
        "edu.mum.aop"
})
public class Configs {
}
