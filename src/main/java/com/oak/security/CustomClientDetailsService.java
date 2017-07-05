package com.oak.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Chennl on 7/1/2017.
 */
@Service
public class CustomClientDetailsService implements ClientDetailsService {
    private static final String RESOURCE_ID = "SPRING_REST_API";
    private final Logger logger = LoggerFactory.getLogger(CustomClientDetailsService.class);
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        logger.info("loadClientByClientId");
        CustomClientDetails app = new CustomClientDetails();
            app.setAppId("client");
            app.setSecret("secret");
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("READ"));
        authorities.add(new SimpleGrantedAuthority("WRITE"));
        app.setAuthorities(authorities);
        // 授权类型
        Set<String> authorizedGrantTypes = new TreeSet<String>();
        authorizedGrantTypes.add("password");
        authorizedGrantTypes.add("client_credentials");
        authorizedGrantTypes.add("refresh_token");
        app.setAuthorizedGrantTypes(authorizedGrantTypes);
        //Resources
        Set<String> resourceIds = new TreeSet<String>();
        resourceIds.add(RESOURCE_ID);
        app.setResourceIds(resourceIds);
        //
        Set<String> scopes = new TreeSet<String>();
        scopes.add("read");
        scopes.add("write");
        app.setScope(scopes);


        return app;
    }
}
