package kg.gov.mf.loan.web.controller.admin.sys;

import com.lowagie.text.DocumentException;
import kg.gov.mf.loan.output.report.utils.CollectionPhaseTool;
import kg.gov.mf.loan.output.report.utils.LoanFieldsMigration;
import kg.gov.mf.loan.output.report.utils.SecondMigrationTool;
import kg.gov.mf.loan.web.components.FileSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/*
    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
    
    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;    
	*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) 
	{
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		 model.addAttribute("loggedinuser", getPrincipal());
	        return "index";
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
		
        return "login/login";
    }

	
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //return authenticationTrustResolver.isAnonymous(authentication);
        return false;
    }
    
    
    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }
	
	
	
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String goToAdminPage(ModelAndView model) 
	{
		model.addObject("message", "Admin Page1");
		
		return "admin";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String goToUserPage(ModelAndView model) 
	{
		model.addObject("message","User page1");
		
		return "user";
	}
	
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            //persistentTokenBasedRememberMeServices.logout(request, response, auth);
            //SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/startMigration", method = RequestMethod.GET)
    public String startMigration()
    {
//        MigrationTool migrationTool = new MigrationTool();
//        migrationTool.doMigrate();


        CollectionPhaseTool collectionPhaseTool = new CollectionPhaseTool();
        collectionPhaseTool.doStartPhaseDetailsUpdate();

        return "index";

    }

    @RequestMapping("/doMigration/files")
    public String doSomething() throws DocumentException {
        FileSetter fileSetter=new FileSetter();
        fileSetter.setFilesToDirectories();
        return "index";
    }


    @RequestMapping("/doSecondMigrationTool/{loanId}")
    public String doSecondMigrationtool(@PathVariable("loanId") Long loanId){
        SecondMigrationTool secondMigrationTool=new SecondMigrationTool();
        secondMigrationTool.loanMigrate(loanId,"150.0.0.89","migration_test","postgres","armad27raptor");
        return "index";
    }

    @RequestMapping("/migrate/loan/fields")
    public String loanFieldsMigrate(){
        LoanFieldsMigration loanFieldsMigration=new LoanFieldsMigration();
        loanFieldsMigration.migrateLoanField("150.0.0.89","migration_test","postgres","armad27raptor");
        return "index";
    }

    @RequestMapping("/migrate/loan/supervisorPlan")
    public String loanSupervisorPlanMigrate(){
        LoanFieldsMigration loanFieldsMigration=new LoanFieldsMigration();
        loanFieldsMigration.migrateSupervisorPlans("150.0.0.89","migration2019","postgres","armad27raptor");
        return "index";
    }
	
	
	
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

}
