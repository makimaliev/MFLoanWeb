package kg.gov.mf.loan.web.jobs;

import kg.gov.mf.loan.manage.model.order.CreditOrderState;
import kg.gov.mf.loan.manage.service.order.CreditOrderStateService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class JobCreateDummyOrderState implements Job{

    @Autowired
    private CreditOrderStateService stateService;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        CreditOrderState state = new CreditOrderState();
        state.setVersion(1);
        state.setName("Dummy State from job");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Insertion of order state at:" + dateFormat.format(date));

        stateService.add(state);
    }
}

