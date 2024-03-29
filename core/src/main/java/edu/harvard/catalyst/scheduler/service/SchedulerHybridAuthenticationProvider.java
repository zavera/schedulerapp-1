//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.harvard.catalyst.scheduler.service;

import edu.harvard.catalyst.scheduler.core.SchedulerRuntimeException;
import edu.harvard.catalyst.scheduler.entity.User;
import edu.harvard.catalyst.scheduler.security.SchedulerUserDetails;
import edu.harvard.catalyst.scheduler.util.OneWayPasswordEncoder;
import java.util.Hashtable;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class SchedulerHybridAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final Logger LOGGER = Logger.getLogger(SchedulerDbAuthenticationProvider.class);
    private final AuthExtensionService authExtensionService;
    private final String ldapServerUrl;
    private final String ldapDefaultDomain;

    private final String chcoLdapServerUrl;
    private final String chcoLdapDefaultDomain;


    @Autowired
    public SchedulerHybridAuthenticationProvider(AuthExtensionService authExtensionService, String ldapServerUrl, String ldapDefaultDomain,String chcoLdapServerUrl, String chcoLdapDefaultDomain) {
        this.authExtensionService = authExtensionService;
        this.ldapServerUrl = ldapServerUrl;
        this.ldapDefaultDomain = ldapDefaultDomain;
        this.chcoLdapServerUrl = chcoLdapServerUrl;
        this.chcoLdapDefaultDomain = chcoLdapDefaultDomain;
        LOGGER.debug(String.format("ldapServerUrl = %1$s", ldapServerUrl));
        LOGGER.debug(String.format("ldapDefaultDomain = %1$s", ldapDefaultDomain));
        LOGGER.debug(String.format("chcoLdapServerUrl = %1$s", chcoLdapServerUrl));
        LOGGER.debug(String.format("chcoLdapDefaultDomain = %1$s", chcoLdapDefaultDomain));
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        SchedulerUserDetails user = (SchedulerUserDetails)userDetails;
        if(user.getUser().getActiveDirectory() != null) {
            if (user.getUser().getActiveDirectory().equals("UC Denver")) {
                LOGGER.debug(String.format("Verifying user %1$s's password against ldap", userDetails.getUsername()));
                this.authenticateWithActiveDirectory(userDetails.getUsername(), usernamePasswordAuthenticationToken.getCredentials().toString());
                LOGGER.debug(String.format("Verified user %1$s's password against ldap", userDetails.getUsername()));
            } else if (user.getUser().getActiveDirectory().equals("CHCO")) {

                LOGGER.debug(String.format("Verifying user %1$s's password against ldap", userDetails.getUsername()));
                this.authenticateWithCHCOActiveDirectory(userDetails.getUsername(), usernamePasswordAuthenticationToken.getCredentials().toString());
                LOGGER.debug(String.format("Verified user %1$s's password against ldap", userDetails.getUsername()));

            }
        }

        else {
            LOGGER.debug(String.format("Verifying user %1$s's password against database", userDetails.getUsername()));
            String encodedPassword = encodePassword(usernamePasswordAuthenticationToken, user);
            if (!userDetails.getPassword().equals(encodedPassword)) {
                SchedulerRuntimeException.logAndThrow("Credentials don't match");
            }

            LOGGER.debug(String.format("Verified user %1$s's password against database", userDetails.getUsername()));
        }

    }

    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails)usernamePasswordAuthenticationToken.getDetails();
        LOGGER.debug(String.format("Attempting to find user %1$s in database (first attempt)", username));
        User user = this.authExtensionService.findUserByEcommonsId(username);
        if (user == null && (!isActiveDirectoryUsername(username))) {
            //first try UC Denver username
            String usernameCU = String.format("%1$s\\%2$s", this.ldapDefaultDomain, username);
            LOGGER.debug(String.format("Attempting to find user %1$s in database (second attempt)", usernameCU));
            user = this.authExtensionService.findUserByEcommonsId(usernameCU);
            //if not found then try CHCO
            if(user == null){
                String usernameCHCO = String.format("%1$s\\%2$s", this.chcoLdapDefaultDomain, username);
                LOGGER.debug(String.format("Attempting to find user %1$s in database (second attempt)", usernameCHCO));
                user = this.authExtensionService.findUserByEcommonsId(usernameCHCO);
            }
        }

        //did not find any combination of domain + username /ecommonsId in database
        if (user == null) {
            throw new BadCredentialsException("Invalid Username or Password");
        } else {
            LOGGER.debug(String.format("Found user %1$s in database", user.getEcommonsId()));
            String password;
            if (user.getActiveDirectory() != null && isActiveDirectoryUsername(user.getEcommonsId())) {
                password = "";
            } else {
                password = toPassword(usernamePasswordAuthenticationToken);
            }
            LOGGER.debug(String.format("Attempting to authenticate user %1$s in database", user.getEcommonsId()));
            SchedulerUserDetails userDetails = this.authExtensionService.authenticateUser(user.getEcommonsId(), password, authenticationDetails.getSessionId(), authenticationDetails.getRemoteAddress());
            if (userDetails == null) {
                LOGGER.debug(String.format("Unable to authenticate user %1$s in database", user.getEcommonsId()));
                SchedulerRuntimeException.logAndThrow("Unable to authenticate user");
            }

            LOGGER.debug(String.format("Authenticated user %1$s in database", userDetails.getUsername()));
            return userDetails;
        }
    }

    private static String encodePassword(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken, SchedulerUserDetails user) {
        OneWayPasswordEncoder encoder = OneWayPasswordEncoder.getInstance();
        String password = toPassword(usernamePasswordAuthenticationToken);
        return encoder.encode(password, user.getSalt());
    }

    private static String toPassword(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        return usernamePasswordAuthenticationToken.getCredentials().toString();
    }

    //generic AD username check for both UC Denver and CHCO
    private static boolean isActiveDirectoryUsername(String username) {

        return Pattern.matches("^\\w+\\\\\\w+$", username);
    }


    private void authenticateWithCHCOActiveDirectory(String username, String password) throws AuthenticationException {
        if (username != null && username.length() != 0 && password != null && password.length() != 0) {
            Hashtable props = new Hashtable();
            props.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
            props.put("java.naming.provider.url", this.chcoLdapServerUrl);
            props.put("java.naming.security.authentication", "simple");
            props.put("java.naming.security.principal", username);
            props.put("java.naming.security.credentials", password);
            DirContext context = null;

            try {
                context = new InitialDirContext(props);
            } catch (javax.naming.AuthenticationException var14) {
                throw new BadCredentialsException("Unable to authenticate user.");
            } catch (NamingException var15) {
                throw new AuthenticationServiceException(var15.getMessage());
            } finally {
                if (context != null) {
                    try {
                        context.close();
                    } catch (NamingException var13) {
                    }
                }

            }

        } else {
            throw new BadCredentialsException("Unable to authenticate user.");
        }
    }



    private void authenticateWithActiveDirectory(String username, String password) throws AuthenticationException {
        if (username != null && username.length() != 0 && password != null && password.length() != 0) {
            Hashtable props = new Hashtable();
            props.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
            props.put("java.naming.provider.url", this.ldapServerUrl);
            props.put("java.naming.security.authentication", "simple");
            props.put("java.naming.security.principal", username);
            props.put("java.naming.security.credentials", password);
            DirContext context = null;

            try {
                context = new InitialDirContext(props);
            } catch (javax.naming.AuthenticationException var14) {
                throw new BadCredentialsException("Unable to authenticate user.");
            } catch (NamingException var15) {
                throw new AuthenticationServiceException(var15.getMessage());
            } finally {
                if (context != null) {
                    try {
                        context.close();
                    } catch (NamingException var13) {
                    }
                }

            }

        } else {
            throw new BadCredentialsException("Unable to authenticate user.");
        }
    }
}
