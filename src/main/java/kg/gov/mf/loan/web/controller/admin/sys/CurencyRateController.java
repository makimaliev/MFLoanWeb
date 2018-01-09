package kg.gov.mf.loan.web.controller.admin.sys;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;





@Controller
public class CurencyRateController {

	@Autowired
	private SystemFileService systemFileService;

	public void setSystemFileService(SystemFileService rs)
	{
	    this.systemFileService = rs;
	}

	


    @RequestMapping(value = { "/currencyRate/manage/" }, method = RequestMethod.GET)
    public String dbManagementPage(ModelMap model) {
        
		
        return "admin/sys/currencyRateManagement";
    }
    
    
    
    @RequestMapping(value = { "/currencyRate/getData/" }, method = RequestMethod.GET)
    public String dbBackupAction(ModelMap model) {
        

        try {
        	

        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();
        	Document doc = db.parse(new URL("http://www.nbkr.kg/XML/daily.xml").openStream());

        	doc.getDocumentElement().normalize();
        	
        	
//        	System.out.println(doc.getDocumentElement().getNodeName()+" "+doc.getDocumentElement().getAttribute("Name") +"Date:"+doc.getDocumentElement().getAttribute("Date"));
  
        	String rateDate = doc.getDocumentElement().getAttribute("Date");
        	
        	
        	
        	List<String> currencyRates = new ArrayList<String>();
        	
        	NodeList nodeList = doc.getElementsByTagName(doc.getDocumentElement().getChildNodes().item(1).getNodeName());
        	
        	System.out.println("--------------------");
        	
        	for(int tmp = 0; tmp < nodeList.getLength(); tmp++)
            {
                Node node = nodeList.item(tmp);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    
/*                    
                    System.out.println(element.getNodeName()+" ISO Code " + element.getAttribute("ISOCode").toString() + ":");
                    System.out.println("Nominal: " + element.getElementsByTagName("Nominal").item(0).getChildNodes().item(0).getNodeValue());
                    System.out.println("Value: " + element.getElementsByTagName("Value").item(0).getChildNodes().item(0).getNodeValue());
  */
                    
                    currencyRates.add(" ���� ������ "+element.getAttribute("ISOCode").toString()+" �� ��������� �� "+rateDate+ " = "+element.getElementsByTagName("Value").item(0).getChildNodes().item(0).getNodeValue());	
                }
            }
        	
        	model.addAttribute("currencyRateList",currencyRates);
        	
        	System.out.println(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    	

		
        return "redirect:/currencyRate/manage/";
    }

    

}
