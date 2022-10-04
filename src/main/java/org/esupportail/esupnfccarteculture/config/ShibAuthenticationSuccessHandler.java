package org.esupportail.esupnfccarteculture.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class ShibAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(ShibAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws ServletException, IOException {
        String eppn = authentication.getName();
        String email = httpServletRequest.getHeader("mail");
        String name = httpServletRequest.getHeader("sn");
        String firstName = httpServletRequest.getHeader("givenName");
        httpServletRequest.getSession().setAttribute("securityServiceName", "ShibSecurityServiceImpl");
    }

}