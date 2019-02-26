package kg.gov.mf.loan.web.config;

import java.util.*;
import java.util.concurrent.TimeUnit;

import kg.gov.mf.loan.admin.org.converter.*;
import kg.gov.mf.loan.admin.sys.converter.UserFormatter;
import kg.gov.mf.loan.doc.formatter.DocumentFormatter;
import kg.gov.mf.loan.doc.formatter.DocumentSubTypeFormatter;
import kg.gov.mf.loan.doc.formatter.DocumentTypeFormatter;
import kg.gov.mf.loan.manage.converter.*;
import kg.gov.mf.loan.output.report.converter.*;

import kg.gov.mf.loan.output.report.service.GroupTypeService;
import kg.gov.mf.loan.process.job.CalculateDailyJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.quartz.*;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import kg.gov.mf.loan.admin.sys.converter.PermissionFormatter;
import kg.gov.mf.loan.admin.sys.converter.RoleFormatter;
import kg.gov.mf.loan.admin.sys.converter.SupervisorTermFormatter;
import kg.gov.mf.loan.admin.sys.service.MessageResourceService;


@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan(basePackages = "kg.gov.mf.loan")
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{


    @Autowired
    StaffFormatter staffFormatter;

    @Autowired
    DocumentFormatter documentFormatter;

    @Autowired
    DocumentTypeFormatter documentTypeFormatter;

    @Autowired
    DocumentSubTypeFormatter documentSubTypeFormatter;


    @Autowired
    ObjectListFormatter objectListFormatter;

    @Autowired
    ContentParameterFormatter contentParameterFormatter;


    @Autowired
    GenerationParameterFormatter generationParameterFormatter;

    @Autowired
    GenerationParameterTypeFormatter generationParameterTypeFormatter;

    @Autowired
    DebtorTypeFormatter debtorTypeFormatter;

    @Autowired
    LoanFormatter loanFormatter;


    @Autowired
    OrganizationFormFormatter orgFormFormatter;

    @Autowired
    OwnerFormatter ownerFormatter;

    @Autowired
    WorkSectorFormatter workSectorFormatter;

    @Autowired
    PhaseStatusFormatter phaseStatusFormatter;

    @Autowired
    PhaseTypeFormatter phaseTypeFormatter;

    @Autowired
    IdentityDocGivenByConverter identityDocGivenByConverter;


    @Autowired
    IdentityDocTypeConverter identityDocTypeConverter;

    @Autowired
    RoleFormatter roleFormatter;

    @Autowired
    UserFormatter userFormatter;

    @Autowired
    DepartmentFormatter departmentFormatter;

    @Autowired
    PositionFormatter positionFormatter;

    @Autowired
    PersonFormatter personFormatter;

    @Autowired
    RegionFormatter regionFormatter;

    @Autowired
    DistrictFormatter districtFormatter;

    @Autowired
    OrganizationFormatter organizationFormatter;

    @Autowired
    PermissionFormatter permissionFormatter;

    @Autowired
    EmploymentHistoryFormatter employmentHistoryFormatter;

    @Autowired
    EmploymentHistoryEventTypeFormatter employmentHistoryEventTypeFormatter;

    @Autowired
    SupervisorTermFormatter supervisorTermFormatter;

    @Autowired
    GroupTypeFormatter groupTypeFormatter;


    @Autowired
    private MessageResourceService messageResourceService;

    private static final String UTF8 = "UTF-8";

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Configure ViewResolvers to deliver preferred views.
     */
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding(UTF8);
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setCache(false);
        return resolver;
    }

    private TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();

        Set<IDialect> dialects = new HashSet<>();
        dialects.add(new SpringSecurityDialect());


        engine.setTemplateResolver(templateResolver());
        engine.setMessageSource(getMessageSource());

        engine.setAdditionalDialects(dialects);
        return engine;
    }

    private ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding(UTF8);
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Bean(name = "localeResolver")
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        Locale defaultLocale = new Locale("ru");
        localeResolver.setDefaultLocale(defaultLocale);
        return localeResolver;
    }

    /*
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
            ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
            messageSource.setBasename("classpath:lang/messages");
            messageSource.setCacheSeconds(10); //reload messages every 10 seconds
            return messageSource;
    }
    */
    @Bean(name = "messageSource")
    public DatabaseDrivenMessageSource getMessageSource() {
        DatabaseDrivenMessageSource resource = new DatabaseDrivenMessageSource(messageResourceService);
        ReloadableResourceBundleMessageSource databaseDrivenMessageSourceProperties = new ReloadableResourceBundleMessageSource();
        databaseDrivenMessageSourceProperties.setBasename("classpath:/locales/messages");
        databaseDrivenMessageSourceProperties.setDefaultEncoding("UTF-8");
        databaseDrivenMessageSourceProperties.setCacheSeconds(0);
        databaseDrivenMessageSourceProperties.setFallbackToSystemLocale(false);
        resource.setParentMessageSource(databaseDrivenMessageSourceProperties);
        return resource;
    }


    /*
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }
    */

    /**
     * Configure ResourceHandlers to serve static resources like CSS/ Javascript etc...
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler("/assets/**").addResourceLocations("/static/assets/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler("/js/**").addResourceLocations("/static/js/").setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
        registry.addResourceHandler("/css/**").addResourceLocations("/static/css/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver createMultipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * Configure Converter to be used.
     * In our example, we need a converter to convert string values[Roles] to UserProfiles in newUser.jsp
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {




        registry.addConverter(identityDocTypeConverter);
        registry.addConverter(identityDocGivenByConverter);

        registry.addFormatter(roleFormatter);
        registry.addFormatter(userFormatter);
        registry.addFormatter(permissionFormatter);
        registry.addFormatter(departmentFormatter);
        registry.addFormatter(organizationFormatter);
        registry.addFormatter(positionFormatter);
        registry.addFormatter(personFormatter);
        registry.addFormatter(regionFormatter);
        registry.addFormatter(districtFormatter);
        registry.addFormatter(employmentHistoryFormatter);
        registry.addFormatter(employmentHistoryEventTypeFormatter);

        registry.addFormatter(supervisorTermFormatter);

        registry.addFormatter(loanFormatter);
        registry.addFormatter(debtorTypeFormatter);
        registry.addFormatter(orgFormFormatter);
        registry.addFormatter(workSectorFormatter);
        registry.addFormatter(ownerFormatter);
        registry.addFormatter(phaseStatusFormatter);
        registry.addFormatter(phaseTypeFormatter);
        registry.addFormatter(generationParameterFormatter);
        registry.addFormatter(generationParameterTypeFormatter);
        registry.addFormatter(objectListFormatter);
        registry.addFormatter(contentParameterFormatter);


        registry.addFormatter(staffFormatter);
        registry.addFormatter(documentFormatter);
        registry.addFormatter(documentTypeFormatter);
        registry.addFormatter(documentSubTypeFormatter);
        registry.addFormatter(groupTypeFormatter);




        //      registry.addConverter(roleToStringConverter);

    }




    /**
     * Configure MessageSource to lookup any validation/error message in internationalized property files
     */
    
    /*
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("classpath:lang/messages");
        return messageSource;
    }
    */

    /**Optional. It's only required when handling '.' in @PathVariables which otherwise ignore everything after last '.' in @PathVaidables argument.
     * It's a known bug in Spring [https://jira.spring.io/browse/SPR-6164], still present in Spring 4.1.7.
     * This is a workaround for this issue.
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(true);
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(CalculateDailyJob.class);
        Map<String,Object> map = new HashMap<>();
        map.put("name", "RAM");
        factory.setJobDataAsMap(map);
        factory.setGroup("mygroup");
        factory.setName("myjob");
        return factory;
    }
    //Job is scheduled after every 1 minute
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailFactoryBean().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setName("mytrigger");
        stFactory.setGroup("mygroup");
        stFactory.setCronExpression("0 0 2 ? * * *");
        return stFactory;
    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(cronTriggerFactoryBean().getObject());
        return scheduler;
    }
}