package edu.mum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.concurrent.DelegatingSecurityContextScheduledExecutorService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        registrar.setScheduler(taskScheduler());
    }

    @Bean(name = "scheduledExecutor", destroyMethod = "shutdownNow")
    public ScheduledExecutorService taskScheduler(){
        return new DelegatingSecurityContextScheduledExecutorService(
                Executors.newScheduledThreadPool(4),
                creatorSchedulerSecurityContext()
        );
    }

    private SecurityContext creatorSchedulerSecurityContext(){
        String role = "ROLE_SYSTEM";
        SecurityContext sc = SecurityContextHolder.createEmptyContext();
        List<GrantedAuthority> auth = AuthorityUtils.createAuthorityList(role);
        sc.setAuthentication(new UsernamePasswordAuthenticationToken("system", role, auth));
        return sc;
    }
}
