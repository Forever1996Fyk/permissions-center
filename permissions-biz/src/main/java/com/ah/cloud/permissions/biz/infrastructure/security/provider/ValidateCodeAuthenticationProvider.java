package com.ah.cloud.permissions.biz.infrastructure.security.provider;


import com.ah.cloud.permissions.biz.infrastructure.exception.SecurityErrorException;
import com.ah.cloud.permissions.biz.infrastructure.security.token.ValidateCodeAuthenticationToken;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-05 22:48
 **/
public class ValidateCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ValidateCodeAuthenticationToken authenticationToken = (ValidateCodeAuthenticationToken) authentication;
        String sender = (String) authenticationToken.getPrincipal();
        UserDetails userDetails = this.getUserDetailsService().loadUserByUsername(sender);
        if (Objects.isNull(userDetails)) {
            throw new SecurityErrorException(ErrorCodeEnum.VALIDATE_CODE_USER_IS_NULL);
        }
        return createSuccessAuthentication(userDetails, authentication, userDetails);
    }

    private Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        ValidateCodeAuthenticationToken result = new ValidateCodeAuthenticationToken(principal,
                authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ValidateCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
