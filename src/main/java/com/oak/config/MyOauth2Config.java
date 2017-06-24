//package com.oak.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
///**
// * Created by Chennl on 6/23/2017.
// */
//@Configuration
//public class MyOauth2Config {
//
//    /*protected static final String RESOURCE_ID = "api_resource";
//
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            // @formatter:off
//            http
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                    .and()
//                    .requestMatchers().antMatchers("/se","/api/users")
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/se").access("#oauth2.hasScope('read')");
//            // @formatter:on
//        }
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources)
//                throws Exception {
//            resources.resourceId(RESOURCE_ID).stateless(false);
//        }
//    }*/
//}