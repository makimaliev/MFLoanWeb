package kg.gov.mf.loan.web.components;

import kg.gov.mf.loan.task.model.MFLog;
import kg.gov.mf.loan.task.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class MFLogoutHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    LoggerService loggerService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        try {
            MFLog log = new MFLog();
            log.setCreatedBy(authentication.getName());
            log.setCreatedDate(new Date());
            log.setIp(((WebAuthenticationDetails)authentication.getDetails()).getRemoteAddress());
            log.setAction("Logged Out");
            log.setUuid(UUID.randomUUID().toString());
            loggerService.add(log);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onLogoutSuccess(request, response, authentication);
    }
}
