package kg.gov.mf.loan.web.components;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UserRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        UserRevision userRevision = (UserRevision) revisionEntity;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRevision.setUsername(authentication.getName());
    }
}