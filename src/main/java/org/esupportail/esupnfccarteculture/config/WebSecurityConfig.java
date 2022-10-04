package org.esupportail.esupnfccarteculture.config;

import org.esupportail.esupnfccarteculture.security.DatabaseUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import javax.annotation.Resource;

@Configuration
public class WebSecurityConfig {

    @Resource
    private DatabaseUserDetailsService databaseUserDetailsService;

    @Bean
    public SwitchUserFilter switchUserFilter() {
        SwitchUserFilter switchUserFilter = new SwitchUserFilter();
        switchUserFilter.setUserDetailsService(databaseUserDetailsService);
        switchUserFilter.setSwitchUserUrl("/admin/su-login");
        switchUserFilter.setSwitchFailureUrl("/error");
        switchUserFilter.setExitUserUrl("/admin/su-logout");
        switchUserFilter.setTargetUrl("/");
        return switchUserFilter;
    }

}
