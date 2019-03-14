package kg.gov.mf.loan.web.components;

import kg.gov.mf.loan.task.model.MFLog;
import kg.gov.mf.loan.task.service.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

public class MFLoginHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    LoggerService loggerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        MFLog log = new MFLog();
        log.setCreatedBy(authentication.getName());
        log.setCreatedDate(new Date());
        log.setIp(((WebAuthenticationDetails)authentication.getDetails()).getRemoteAddress());
        log.setAction("Logged In");
        log.setUuid(UUID.randomUUID().toString());
        loggerService.add(log);

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("username", authentication.getName());
        session.setAttribute("authorities", authentication.getAuthorities());

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.sendRedirect("/");
    }
}
