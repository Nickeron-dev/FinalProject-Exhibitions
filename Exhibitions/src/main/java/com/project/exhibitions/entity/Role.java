package com.project.exhibitions.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Illia Koshkin
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    /**
     * This method gets name of the role in String
     * @return String role
     */
    @Override
    public String getAuthority() {
        return name();
    }
}
