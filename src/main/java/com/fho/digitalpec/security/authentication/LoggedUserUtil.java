package com.fho.digitalpec.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoggedUserUtil {

    public UserDetails getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public Long getLoggedUserId() {
        UserDetails userDetails = getLoggedUser();
        if (userDetails != null && userDetails instanceof CustomUserDetails) {
            return ((CustomUserDetails) userDetails).getId();
        }
        return null;
    }

    public String getLoggedUsername() {
        UserDetails userDetails = getLoggedUser();
        return userDetails != null ? userDetails.getUsername() : null;
    }
}
