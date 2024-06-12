package com.fho.digitalpec.security.authentication;

import com.fho.digitalpec.exception.UserNotAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggedUserUtil {

    public static CustomUserDetails getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            return customUserDetails;
        }
        throw new UserNotAuthenticatedException("User is not authenticated");
    }
}
