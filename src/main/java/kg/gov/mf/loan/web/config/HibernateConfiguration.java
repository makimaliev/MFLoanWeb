package kg.gov.mf.loan.web.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;
 
@Configuration
@EnableTransactionManagement
@ComponentScan({"kg.gov.mf.loan.web.config"})
@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfiguration {
 
    @Autowired
    private Environment environment;
 
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "kg.gov.mf.loan" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
     }
     
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }
     
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.hbm2ddl.import_files", environment.getRequiredProperty("hibernate.hbm2ddl.import_files"));

        properties.put("hibernate.event.merge.entity_copy_observer", "allow");
        //properties.put("hibernate.search.default.directory_provider", environment.getRequiredProperty("hibernate.search.default.directory_provider"));
        //properties.put("hibernate.search.default.indexBase", environment.getRequiredProperty("hibernate.search.default.indexBase"));

//        envers properties
//        properties.put("org.hibernate.envers.audit_table_prefix", environment.getRequiredProperty("hibernate.envers.audit_table_prefix"));
//        properties.put("org.hibernate.envers.audit_table_suffix", environment.getRequiredProperty("hibernate.envers.audit_table_suffix"));
        properties.put("org.hibernate.envers.store_data_at_delete", environment.getRequiredProperty("hibernate.envers.store_data_at_delete"));
        properties.put("hibernate.integration.envers.enabled", environment.getRequiredProperty("hibernate.integration.envers.enabled"));

        return properties;        
    }
     
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
}