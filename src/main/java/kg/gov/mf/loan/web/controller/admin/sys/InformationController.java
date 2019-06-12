package kg.gov.mf.loan.web.controller.admin.sys;

import com.lowagie.text.DocumentException;
import kg.gov.mf.loan.admin.org.model.Address;
import kg.gov.mf.loan.admin.org.service.AddressService;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.admin.sys.service.cSystemService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class InformationController {
	
	@Autowired
    private InformationService informationService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	SystemFileService systemFileService;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	AddressService addressService;
	
    public void setInformationService(InformationService rs)
    {
        this.informationService = rs;
    }

    
	@Autowired
    private cSystemService cSystemService;
	
    public void setSystemObjectTypeService(cSystemService rs)
    {
        this.cSystemService = rs;
    }
    
    
	@RequestMapping(value = "/information/list", method = RequestMethod.GET)
	public String listInformations(Model model) {
		
		model.addAttribute("information", new Information());
		model.addAttribute("informationList", this.informationService.findAll());
		return "admin/sys/informationList";
	}
	
	
	@RequestMapping(value = "/information/table", method = RequestMethod.GET)
	public String showInformationTable(Model model) {
		model.addAttribute("information", new Information());
		model.addAttribute("informationList", this.informationService.findAll());

		return "admin/sys/informationTable";
	}	
	
	@RequestMapping("/information/{id}/view")
	public String viewInformationById(@PathVariable("id") long id, Model model) {

		Information information = this.informationService.findById(id);

		model.addAttribute("information", information);


		return "admin/sys/informationView";
	}
    
	
	@RequestMapping("/information/{id}/details")
	public String viewInformationDetailsById(@PathVariable("id") long id, Model model) {

		Information information = this.informationService.findById(id);

		model.addAttribute("information", information);


		return "admin/sys/informationDetails";
	}
	
	
	
	@RequestMapping(value = "/information/add", method = RequestMethod.GET)
	public String getInformationAddForm(Model model) {

		model.addAttribute("information", new Information());
		model.addAttribute("informationList", this.informationService.findAll());		

		
		return "admin/sys/informationForm";
	}
	
	@RequestMapping(value = "/information/{informationId}/information/add", method = RequestMethod.GET)
	public String getInformationAddFormWithParentInformation(@PathVariable("informationId") long informationId,Model model) {

		Information modelInformation = new Information();
		
		modelInformation.setParentInformation(this.informationService.findById(informationId));
		modelInformation.setSystemObjectId(this.informationService.findById(informationId).getSystemObjectId());
		modelInformation.setSystemObjectTypeId(this.informationService.findById(informationId).getSystemObjectTypeId());
		
		model.addAttribute("information",modelInformation);

		return "admin/sys/informationForm";
	}
	
	
	@RequestMapping(value = "/{systemObjectTypeId}/{systemObjectId}/information/add", method = RequestMethod.GET)
	public String getInformationAddFormWithSystemObjectType(@PathVariable("systemObjectTypeId") long systemObjectTypeId,@PathVariable("systemObjectId") long systemObjectId,Model model) {

		Information modelInformation = new Information();
		
		modelInformation.setSystemObjectTypeId(systemObjectTypeId);
		modelInformation.setSystemObjectId(systemObjectId);
		modelInformation.setParentInformation(null);
		
		model.addAttribute("information",modelInformation);

		return "admin/sys/informationForm";
	}	
	
	

	@RequestMapping("/information/{id}/edit")
	public String getInformationEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("information", this.informationService.findById(id));
		model.addAttribute("informationList", this.informationService.findAll());		
	
		
		return "admin/sys/informationForm";

	}

	@RequestMapping(value = "/information/{informationId}/information/save", method = RequestMethod.POST)
	public String saveInformationAndRedirectToInformationList(@Validated @ModelAttribute("information") Information information, @PathVariable("informationId") long informationId, BindingResult result) {

		
		
		try {
			System.out.println(information.getClass().getDeclaredField("name"));
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (result.hasErrors()) 
		{
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} 
		else if (information.getId() == 0) 
		{
			if(information.getParentInformation().getName()==null)
			{
				information.setParentInformation(this.informationService.findById(informationId));
			}
			
			this.informationService.create(information);
		} 
		else 
		{
			this.informationService.edit(information);
		}

		switch(String.valueOf(information.getSystemObjectTypeId()))
		{
			case "1": return "redirect:/cSystem/list";
			case "2": return "redirect:/organization/"+information.getSystemObjectId()+"/details";
			default: return "redirect:/information/list";
		}

	}
	
	
	@RequestMapping(value = "/information/save", method = RequestMethod.POST)
	public String saveInformation(@Validated @ModelAttribute("information") Information information, BindingResult result) throws NoSuchFieldException {

		/*
		try {
			Method method= information.getClass().getMethod("getName", null);
			
			String output = method.invoke(information).toString();
			
			
			
				Class<?> act = Class.forName("kg.gov.mf.loan.admin.sys.model.Information");

			System.out.println(output);
		} catch (SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		if (result.hasErrors()) 
		{
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} 
		else if (information.getId() == 0) 
		{
			if(information.getParentInformation().getId()==0)
			{
				information.setParentInformation(null);
			}
			else
			{
				information.setParentInformation(this.informationService.findById(information.getParentInformation().getId()));
			}
			
			this.informationService.create(information);
		} 
		else 
		{
			this.informationService.edit(information);
		}

//		switch(String.valueOf(information.getSystemObjectTypeId()))
//		{
//			case "1": return "redirect:/cSystem/list";
//			case "2": return "redirect:/organization/"+information.getSystemObjectId()+"/details";
//			default: return "redirect:/information/list";
//		}

		return "redirect:/information/"+information.getId()+"/details";

	}

	@RequestMapping("/information/{id}/remove")
	public String removeInformationAndRedirectToInformationList(@PathVariable("id") long id) {

		this.informationService.deleteById(id);

		return "redirect:/information/list";
	}


	@RequestMapping("/manage/debtor/{debtorId}/loan/{loanId}/object/{objectId}/attachment/{type}/add")
	public String addAttachment(MultipartHttpServletRequest request, @PathVariable("debtorId") Long debtorId, @PathVariable("type") int type,
								@PathVariable("loanId") String loanId,@PathVariable("objectId") Long objectId, String name) {

		String path =  SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "C:/temp/";

		Debtor debtor=debtorService.getById(debtorId);
		Address address =addressService.findById(debtor.getAddress_id());
		Long regionId=address.getRegion().getId();
		Long districtId=address.getDistrict().getId();
		File folder = new File(path);
		path=path+regionId+"/";
		folder=new File(path);
		boolean exists = folder.exists();
		if(!exists){
			folder.mkdir();
		}
		path=path+districtId+"/";
		folder=new File(path);
		exists = folder.exists();
		if(!exists){
			folder.mkdir();
		}
		path=path+debtorId+"/";
		folder=new File(path);
		exists = folder.exists();
		if(!exists){
			folder.mkdir();
		}
		path=path+loanId+"/";
		folder=new File(path);
		exists = folder.exists();
		if(!exists){
			folder.mkdir();
		}
		path=path+type+"/";
		folder=new File(path);
		exists = folder.exists();
		if(!exists){
			folder.mkdir();
		}


		Information information;
		List<Information> informationList=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(type,objectId);
		if(informationList.size()>0){
			information=informationList.get(0);
		}
		else{
			information=new Information();
			information.setName(name);
			information.setDate(new Date());
			information.setParentInformation(null);
			information.setSystemObjectId(objectId);
			information.setSystemObjectTypeId(type);
			informationService.create(information);
		}

		Attachment attachment = new Attachment();
		try {
			Iterator<String> itr = request.getFileNames();

			while (itr.hasNext()) {

				SystemFile systemFile=new SystemFile();
				String uploadedFile = itr.next();
				MultipartFile part = request.getFile(uploadedFile);

				String filename = part.getOriginalFilename();
				folder=new File(path+filename);
				exists = folder.exists();
				if(exists){
				}
				else{

//                String uuid = UUID.randomUUID().toString();
//                String fsname = uuid + ".atach";

					File file = new File(path + filename);

					part.transferTo(file);

					systemFile.setName(filename);
					systemFile.setPath(path + filename);

					attachment.setName(name);
					attachment.setInformation(information);


					systemFileService.create(systemFile);
					systemFile.setAttachment(attachment);
					attachmentService.create(attachment);
					systemFileService.edit(systemFile);
				}
			}
			informationService.edit(information);
		}
		catch (Exception e) {
			System.out.println(e);
		}

		if(type==5){
			return "redirect:/manage/debtor/{debtorId}/loan/{loanId}/payment/"+objectId+"/view";
		}
		else if(type==6){
			return "redirect:/manage/debtor/{debtorId}/collateralagreement/"+objectId+"/view";
		}
		else{
			return "redirect:/manage/debtor/{debtorId}/loan/{loanId}/view";
		}
	}

	@RequestMapping("/systemFile/{id}/download")
	@ResponseBody
	public void downloadSystemFile(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, DocumentException {

		SystemFile systemFile=systemFileService.findById(id);
		File file = new File(systemFile.getPath());
		String fileExtension=systemFile.getName().split("\\.")[1];
		String smth=Files.probeContentType(file.toPath());

		/*
		if(fileExtension.equals("pdf")){
			response.setHeader("Content-disposition","attachment; filename=xx.pdf");
		}
		else if(fileExtension.equals("doc") || fileExtension.equals("docx")){
//			response.setContentType("application/msword");
			response.setHeader("Content-Disposition", "attachment; filename=xx"+fileExtension);
		}
		else if(fileExtension.equals("rtf")){
//			response.setContentType("application/rtf");
			response.setHeader("Content-disposition","attachment; filename=xx.rtf");
		}
		else if(fileExtension.equals("png")){
//			response.setContentType("application/png");
			response.setHeader("Content-disposition","attachment; filename=xx.png");
		}

		else if(fileExtension.equals("png")){
//			response.setContentType("application/png");
			response.setHeader("Content-disposition","attachment; filename=xx.png");
		}
*/

		response.setContentType(smth);
		response.setHeader("Content-Disposition", "attachment; filename=xx."+fileExtension);
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}




}
