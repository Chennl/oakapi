package com.oak.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);
    private static final String RESOURCE_ID = "SPRING_REST_API";

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    CustomClientDetailsService clientDetailsService;
    @Autowired
    private Environment environment;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
       // endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(jwtTokenEnhancer())
                .authenticationManager(authenticationManager);

    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        logger.info("configure ClientDetailsServiceConfigurer");
        clients.withClientDetails(clientDetailsService);
//        clients
//                .inMemory()
//                .withClient("client")
//                .secret("secret")
//                .authorizedGrantTypes("password", "refresh_token","client_credentials")
//                .scopes("read", "write")
//                .resourceIds(RESOURCE_ID).accessTokenValiditySeconds(60);
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
    {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
//    @Bean
//    public TokenEnhancer tokenEnhancer() {
//        return new CustomTokenEnhancer();
//    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        String pwd = environment.getProperty("keystore.password");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("jwt.jks"),
                pwd.toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {

       // return new InMemoryTokenStore();
        return new JwtTokenStore(jwtTokenEnhancer());
    }

}
