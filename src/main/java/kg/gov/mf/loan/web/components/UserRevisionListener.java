package kg.gov.mf.loan.web.components;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UserRevisionListener implements RevisionListener {

    @Autowired
    UserService userService;

    @Override
    public void newRevision(Object revisionEntity) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        UserRevision userRevision = (UserRevision) revisionEntity;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.findByUsername(authentication.getName());
        userRevision.setUser_id(user.getId());
    }
}