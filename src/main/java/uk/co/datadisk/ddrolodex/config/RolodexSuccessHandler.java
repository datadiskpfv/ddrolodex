package uk.co.datadisk.ddrolodex.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uk.co.datadisk.ddrolodex.domain.security.User;
import uk.co.datadisk.ddrolodex.jwt.JWTTokenProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RolodexSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //set our response to OK status
        //response.setStatus(HttpServletResponse.SC_OK);

        boolean admin = false;

        logger.info("AT onAuthenticationSuccess(...) function!");

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ADMIN".contains(auth.getAuthority())){
                admin = true;
            }
        }

        if(admin){
            System.out.println("User type: ADMIN");
            response.sendRedirect("/user/user");
        }else{
            System.out.println("User type: USER");
            response.sendRedirect("/contact/contact");
        }
    }
}
