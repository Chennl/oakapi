package com.oak.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;



@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

    public static final Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);
    private static final String RESOURCE_ID = "SPRING_REST_API";
//    @Autowired
//    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources){
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception{
        logger.debug("ResourceServerConfiguration LOADED!!!");
        http
            .requestMatchers().antMatchers("/api/**")
            .and()
                .authorizeRequests()
                .anyRequest().access("#oauth2.hasScope('read')");

        http.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated();
    }

}
