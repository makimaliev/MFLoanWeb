package kg.gov.mf.loan.web.controller.admin.sys;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;


@Controller
public class DbController {

	@Autowired
	private SystemFileService systemFileService;

	public void setSystemFileService(SystemFileService rs)
	{
	    this.systemFileService = rs;
	}

	
	
     
    @RequestMapping(value = { "/db/manage/" }, method = RequestMethod.GET)
    public String dbManagementPage(ModelMap model) {
        
		model.addAttribute("dbList", this.systemFileService.findAll());
		
        return "admin/sys/dbManagement";
    }
    
    
    
  //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "c://temp//";
        

    @RequestMapping(value = { "/db/backup/" }, method = RequestMethod.GET)
    public String dbBackupAction(ModelMap model) {
        
    	Process p = null;
        try {
        	
        	Date today = new Date();
        	
        	
            SimpleDateFormat df2 = new SimpleDateFormat("dd_MM_yy_HH_mm_ss");
            String dateText = df2.format(today);
            System.out.println(dateText);
        	
            Runtime runtime = Runtime.getRuntime();
            
            
            String username = "root";
            String password = "nbuser";
            String database = "mfloan";
            String fileName = "mfloan_"+dateText;
            
            
//            String executeCmd = "mysqldump -u " + username + " -p" + password + " --add-drop-database -B " + database + " -r c:\\temp\\mfloan.sql";
            String executeCmd = "mysqldump --user=root --password=nbuser mfloan > c:\\temp\\"+fileName+".sql";

            
            
            p = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", executeCmd });
            
            int processComplete = p.waitFor();

            if (processComplete == 0) {

            	
            	SystemFile dbBackupFile = new SystemFile();
            	
                
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                

                dbBackupFile.setPath(path.toString());
                dbBackupFile.setName(fileName);
                
                this.systemFileService.create(dbBackupFile);            

                System.out.println("Backup created successfully!");

            } else {
            	System.out.println("Backup NOT created !");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		model.addAttribute("dbList", this.systemFileService.findAll());
		
        return "redirect:/db/manage/";
    }

    @RequestMapping(value = { "/db/restore/{systemFileId}" }, method = RequestMethod.GET)
    public String dbRestoreAction(@PathVariable("systemFileId") long systemFileId,ModelMap model) {
        
    	Process p = null;
        try {
        	
        	
        	
        	
        	SystemFile dbRestoreFile = new SystemFile();
        	
        	dbRestoreFile =  this.systemFileService.findById(systemFileId);
        	
        	System.out.println(" == "+dbRestoreFile.getName()+" == "+dbRestoreFile.getPath() );
        	
        	Date today = new Date();
        	
        	
            SimpleDateFormat df2 = new SimpleDateFormat("dd_MM_yy_HH_mm_ss");
            String dateText = df2.format(today);
            System.out.println(dateText);
        	
            Runtime runtime = Runtime.getRuntime();
            
            
            String username = "root";
            String password = "nbuser";
            String database = "mfloan";
            String fileName = dbRestoreFile.getName();
            
            
//            String executeCmd = "mysqldump -u " + username + " -p" + password + " --add-drop-database -B " + database + " -r c:\\temp\\mfloan.sql";
            String executeCmd = "mysql --user=root --password=nbuser mfloan < c:\\temp\\"+fileName+".sql";

            //String[] restoreCmd = new String[]{"mysql ", "--user=" + dbUserName, "--password=" + dbPassword, "-e", "source " + source};
            
            
            p = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", executeCmd });
            
            int processComplete = p.waitFor();

            if (processComplete == 0) {

            	
            	/*
            	SystemFile dbBackupFile = new SystemFile();
            	
                
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                

                dbBackupFile.setPath(path.toString());
                dbBackupFile.setName(fileName);
                
                this.systemFileService.create(dbBackupFile);
                
                */

                System.out.println("Backup restored successfully!");

            } else {
            	System.out.println("Backup NOT restored !");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		model.addAttribute("dbList", this.systemFileService.findAll());
		
        return "redirect:/db/manage/";
    }

}
