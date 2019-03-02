package kg.gov.mf.loan.web.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor()
    {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
