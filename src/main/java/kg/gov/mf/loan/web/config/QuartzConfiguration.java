package kg.gov.mf.loan.web.config;

import kg.gov.mf.loan.web.jobs.JobCreateDummyOrderState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

//@Configuration
//@ComponentScan(basePackages = "kg.gov.mf.loan")
public class QuartzConfiguration {
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(JobCreateDummyOrderState.class);
        factory.setGroup("MFLoanManageJobs");
        return factory;
    }
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailFactoryBean().getObject());
        stFactory.setCronExpression("0 * 10 * * ?"); //trigger job each day at 15:45 PM
        return stFactory;
    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        //scheduler.setTriggers(cronTriggerFactoryBean().getObject());
        return scheduler;
    }
}