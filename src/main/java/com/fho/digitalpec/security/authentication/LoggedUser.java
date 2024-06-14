package com.fho.digitalpec.security.authentication;

import com.fho.digitalpec.api.user.entity.User;
import com.fho.digitalpec.api.user.service.UserService;
import com.fho.digitalpec.exception.UserNotAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoggedUser {

    private final UserService userService;

    public static CustomUserDetails getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            return customUserDetails;
        }
        throw new UserNotAuthenticatedException("User is not authenticated");
    }

    public static Long getLoggedInUserId() {
        return getLoggedInUserDetails().getId();
    }

    public User getLoggedInUser() {
        Long loggedUserId = getLoggedInUserId();
        return userService.findById(loggedUserId);
    }
}
