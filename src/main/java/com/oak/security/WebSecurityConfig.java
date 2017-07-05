package com.oak.security;


import com.oak.controller.UserController;
import com.oak.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
//@EnableAuthorizationServer
//@EnableOAuth2Sso
@EnableOAuth2Client
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Override
    @Bean// share AuthenticationManager for web and oauth
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    /**
     * 主过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
//                .headers().frameOptions().sameOrigin().and()
                .csrf().disable()
                  //跨域支持
                .cors().and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/","/login**","webjars/**").permitAll()
                .anyRequest().authenticated();

    }

    /**
     * 授权服务器(定义UserDetails类)
     *
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        //Configure Spring security's authenticationManager with customer user details service
        auth.userDetailsService(this.userDetailsService);
        logger.debug("AuthenticationManagerBuilder config");
    }

//    /**
//     * 本地的资源服务器
//     *
//     */
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//            http.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated();
//        }
//        private static final String RESOURCE_ID = "SPRING_REST_API";
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) {
//            resources.resourceId(RESOURCE_ID).stateless(false);
//        }
//    }

}
