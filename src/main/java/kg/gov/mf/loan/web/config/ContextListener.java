package kg.gov.mf.loan.web.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("I N I T I A L I Z E D");
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}
}