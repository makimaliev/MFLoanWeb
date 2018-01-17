package kg.gov.mf.loan.web.config;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
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
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import kg.gov.mf.loan.admin.org.converter.*;
import kg.gov.mf.loan.admin.sys.converter.*;
import kg.gov.mf.loan.admin.sys.service.MessageResourceService;
import kg.gov.mf.loan.web.controller.manage.LoanFormatter;



@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "kg.gov.mf.loan")
public class AppConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{
     


    @Autowired
    IdentityDocGivenByConverter identityDocGivenByConverter;    
    
  
    @Autowired
    IdentityDocTypeConverter identityDocTypeConverter;    

    @Autowired
    RoleFormatter roleFormatter;      

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
        engine.setTemplateResolver(templateResolver());
        engine.setMessageSource(getMessageSource());
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
        registry.addResourceHandler("/assets/**").addResourceLocations("/static/assets/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));;
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));;
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
        registry.addFormatter(new LoanFormatter());

        
        
        
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
}