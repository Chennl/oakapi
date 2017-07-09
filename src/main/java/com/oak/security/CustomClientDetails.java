package com.oak.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * Created by Chennl on 7/1/2017.
 */
public class CustomClientDetails implements ClientDetails {

    private String clientId;//appId
    private String clientSecret;// secret
    private String role;
    private Set<String> scope;
    private Set<String> types;
    private Set<String> autoApproveScopes;
    private Set<String> resourceIds;
    private Map<String, Object> additionalInformation;
    private Set<String> registeredRedirectUris;
    private List<GrantedAuthority> authorities;



    public Set<String> getAutoApproveScopes() {
        return this.autoApproveScopes;
    }
    public void setAutoApproveScopes( Set<String> autoApproveScopes) {
         this.autoApproveScopes =autoApproveScopes;
    }

    public void setAppId(String appId){
        this.clientId = appId;
    }
    public void setSecret(String secret){
        this.clientSecret = secret;
    }

    public void setClientId(String clientId){
        this.clientId =  clientId;
    }
    @Override
    public String getClientId() {
        return this.clientId;
    }



    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }


    public void setResourceIds(Set<String> resourceIds) {
       this.resourceIds = resourceIds;
    }
    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }



    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    /**
     * values ["read", "write"]
     * @return
     */
    public void setScope(Set<String> scope){
        this.scope = scope;
    }
    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    /**
     * values ["authorization_code", "password", "refresh_token", "implicit"]
     * @return
     */
    public void setAuthorizedGrantTypes(Set<String> types){
        this.types = types;
    }
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.types;
    }

    /**
     * www.localhost/callback
     * @param registeredRedirectUris
     */
    public void setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris == null?null:new LinkedHashSet(registeredRedirectUris);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUris;
    }

    /**
     * new SimpleGrantedAuthority(role)
     * @param authorities
     */
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = new ArrayList(authorities);
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return  this.authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 7200;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 7200;
    }

    @Override
    public boolean isAutoApprove(String s) {
        if(this.autoApproveScopes == null) {
            return false;
        } else {
            Iterator var2 = this.autoApproveScopes.iterator();
            String auto;
            do {
                if(!var2.hasNext()) {
                    return false;
                }
                auto = (String)var2.next();
            } while(!auto.equals("true") );
            return true;
        }

    }

    /**
     *  additional information
     * @param key
     * @param value
     */
    public void addAdditionalInformation(String key, Object value) {
        this.additionalInformation.put(key, value);
    }
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    }






}
