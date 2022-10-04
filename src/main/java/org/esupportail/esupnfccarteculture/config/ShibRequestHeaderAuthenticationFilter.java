package org.esupportail.esupnfccarteculture.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShibRequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShibRequestHeaderAuthenticationFilter.class);

    private String credentialsRequestHeader4thisClass;

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, authResult);
    }

    /*
     * Surcharge de la méthode initiale : si pas d'attributs correspondant à credentialsRequestHeader (shib) ; on continue  :
     * 	credentials ldap suffisent (et pas de credentials du tout aussi ...).
     *
     * @see org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter#getPreAuthenticatedCredentials(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        String credentials = null;
        if (credentialsRequestHeader4thisClass != null) {
            credentials = request.getHeader(credentialsRequestHeader4thisClass);
        }
        if(credentials == null) {
            return "N/A";
        } else {
            return credentials;
        }
    }

    public void setCredentialsRequestHeader(String credentialsRequestHeader) {
        super.setCredentialsRequestHeader(credentialsRequestHeader);
        this.credentialsRequestHeader4thisClass = credentialsRequestHeader;
    }

}
