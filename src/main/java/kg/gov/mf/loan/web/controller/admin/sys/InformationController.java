package kg.gov.mf.loan.web.controller.admin.sys;

import com.lowagie.text.DocumentException;
import kg.gov.mf.loan.admin.org.model.Address;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.service.AddressService;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.admin.sys.service.cSystemService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
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
import java.util.*;

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

	@Autowired
    OrganizationService organizationService;
	
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
	
	@GetMapping("/organization/{oId}/information/view")
	public String viewOrganizationInformations(Model model,@PathVariable("oId") Long oId){

    	List<SystemFileModel> list=getSystemFilesByTypeAndId(oId);

    	model.addAttribute("informationList",list);

		return "admin/sys/informationList";
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
			Information information1=informationService.findById(information.getId());
			information1.setName(information.getName());
			information1.setDescription(information.getDescription());
			this.informationService.edit(information1);
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

	@GetMapping("/information/{id}/addAttachment")
    public String addAttachment(Model model,@PathVariable("id") Long id){

        Information information=informationService.findById(id);

        model.addAttribute("object",information);
        model.addAttribute("systemObjectTypeId",2);

        Attachment attachment=new Attachment();
        attachment.setInformation(information);
        model.addAttribute("attachment",attachment);

        return "/manage/debtor/loan/payment/addInformationForm";

    }


	@PostMapping("/object/{objectId}/attachment/{type}/add")
	public String addAttachment(MultipartFile[] files,MultipartHttpServletRequest request,@RequestParam(value = "ids") String ids, @PathVariable("objectId") Long objectId, @PathVariable("type") int type, Attachment attachment, @RequestParam(value = "documentIds",required = false) Long[] documentIds) {

		String name="";
		HashMap<String,Long> idsMap=new HashMap<>();
		String[] splitedByCommaIds=ids.split(",");
		for(String s:splitedByCommaIds){
			String[] splitted=s.split(":");
			try {
                idsMap.put(splitted[0],Long.valueOf(splitted[1]));
            }
            catch (Exception e){
                System.out.println(e);
            }
		}

		String path=path = getPathForAttachment(idsMap, objectId, type);
		Information information;
		List<Information> informationList=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(type,objectId);
		if(informationList.size()>0){
			information=informationList.get(0);
		}
		else{
			information=new Information();
			information.setName(attachment.getName());
			information.setDate(new Date());
			information.setParentInformation(null);
			information.setSystemObjectId(objectId);
			information.setSystemObjectTypeId(type);
			informationService.create(information);
		}
		Set<Long> docIdSet=new HashSet<>();
		for (Long docId:documentIds){
			docIdSet.add(docId);
		}
		attachment.setDocumentIds(docIdSet);
		if(attachment.getInformation()==null || attachment.getInformation().getId()==0){
			attachment.setInformation(information);
		}
		attachmentService.create(attachment);
		Set<SystemFile> systemFileSet=new HashSet<>();
		try {
//			Iterator<String> itr = request.getFileNames();
			boolean exists=false;
			for (MultipartFile part:files) {

				SystemFile systemFile=new SystemFile();
//				String uploadedFile = itr.next();
//				MultipartFile part = request.getFile(uploadedFile);

				String filename = part.getOriginalFilename();
				name=filename;
				File folder=new File(path+filename);
				exists = folder.exists();
				if(exists){
				}
				else{
					File file = new File(path + filename);

					part.transferTo(file);

					systemFile.setName(filename);
					systemFile.setPath(path + filename);
					systemFile.setAttachment(attachment);
					systemFileService.create(systemFile);
					systemFileSet.add(systemFile);
				}
			}
			if(attachment.getName()==null || attachment.getName().equals("")){
				attachment.setName(name);
				if (information.getName()==null || information.getName().equals("")){
					information.setName(name);
				}
			}
			attachment.setSystemFile(systemFileSet);
			attachmentService.edit(attachment);
			informationService.edit(information);
		}
		catch (Exception e) {
			System.out.println(e);
		}

		switch (type){

			case 2:
				return "redirect:/organization/"+objectId+"/information/view";
			case 5:
				return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/loan/"+idsMap.get("loanId")+"/payment/"+objectId+"/view";
			case 6:
				return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/collateralagreement/"+objectId+"/view";
			case 7:
				return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/collateralagreement/"+idsMap.get("agreementId")+"/collateralitem/"+objectId+"/view";
            case 8:
                return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/collateralagreement/"+idsMap.get("agreementId")+"/collateralitem/"+idsMap.get("itemId")+"/collateralinspection/"+objectId+"/view";
            case 9:
                return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/collateralagreement/"+idsMap.get("agreementId")+"/collateralitem/"+idsMap.get("itemId")+"/collateralarrestfree/"+objectId+"/view";
            case 10:
                return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/loan/"+idsMap.get("loanId")+"/guarantoragreement/"+objectId+"/view";
			case 11:
				return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/collectionprocedure/"+idsMap.get("procedureId")+"/collectionphase/"+objectId+"/view";
            case 12:
                return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/collectionprocedure/"+idsMap.get("procedureId")+"/collectionphase/"+idsMap.get("phaseId")+"/collectionevent/"+objectId+"/view";
			case 13:
				return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/loan/"+idsMap.get("loanId")+"/wo/"+objectId+"/view";
			case 14:
				return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/loan/"+idsMap.get("loanId")+"/loangoods/"+objectId+"/view";
            case 15:
                return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/loan/"+idsMap.get("loanId")+"/debttransfer/"+objectId+"/view";
            case 16:
                return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/loan/"+idsMap.get("loanId")+"/bankrupt/"+objectId+"/view";
			default:
				return "redirect:/manage/debtor/"+idsMap.get("debtorId")+"/loan/"+idsMap.get("loanId")+"/view";

		}
	}

	@RequestMapping("/systemFile/{id}/download")
	@ResponseBody
	public void downloadSystemFile(@PathVariable("id") Long id, HttpServletResponse response) throws IOException, DocumentException {

		SystemFile systemFile=systemFileService.findById(id);
		File file = new File(systemFile.getPath());
        String fileExtension="png";
		try{
			String[] splittedFileName=systemFile.getName().split("\\.");
            fileExtension=splittedFileName[splittedFileName.length-1];
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }
		String mimeType=Files.probeContentType(file.toPath());

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

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", "attachment; filename=xx."+fileExtension);
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}


//	get path to upload attached files
	public String getPathForAttachment(HashMap<String, Long> ids, long objectId, int type){


		List<String> pathValues=new ArrayList<>();
		if(type==2){
			Organization organization=organizationService.findById(objectId);
			Address address = addressService.findById(organization.getAddress().getId());
			Long regionId = address.getRegion().getId();
			Long districtId = address.getDistrict().getId();
			pathValues.add("organizations");
			pathValues.add(String.valueOf(regionId));
			pathValues.add(String.valueOf(districtId));
			pathValues.add(String.valueOf(objectId));
		}
		else if(type==6){
			Debtor debtor = debtorService.getById(ids.get("debtorId"));
			Address address = addressService.findById(debtor.getAddress_id());
			Long regionId = address.getRegion().getId();
			Long districtId = address.getDistrict().getId();
			pathValues.add(String.valueOf(regionId));
			pathValues.add(String.valueOf(districtId));
			pathValues.add(String.valueOf(ids.get("debtorId")));
			pathValues.add("agreements");
			pathValues.add(String.valueOf(objectId));
		}
		else if(type==7) {
			Debtor debtor = debtorService.getById(ids.get("debtorId"));
			Address address = addressService.findById(debtor.getAddress_id());
			Long regionId = address.getRegion().getId();
			Long districtId = address.getDistrict().getId();
			pathValues.add(String.valueOf(regionId));
			pathValues.add(String.valueOf(districtId));
			pathValues.add(String.valueOf(ids.get("debtorId")));
			pathValues.add("agreements");
			pathValues.add(String.valueOf(ids.get("agreementId")));
            pathValues.add(String.valueOf(type));
			pathValues.add(String.valueOf(objectId));
		}
        else if(type==8||type==9) {
            Debtor debtor = debtorService.getById(ids.get("debtorId"));
            Address address = addressService.findById(debtor.getAddress_id());
            Long regionId = address.getRegion().getId();
            Long districtId = address.getDistrict().getId();
            pathValues.add(String.valueOf(regionId));
            pathValues.add(String.valueOf(districtId));
            pathValues.add(String.valueOf(ids.get("debtorId")));
            pathValues.add("agreements");
            pathValues.add(String.valueOf(ids.get("agreementId")));
            pathValues.add(String.valueOf(ids.get("itemId")));
            pathValues.add(String.valueOf(type));
            pathValues.add(String.valueOf(objectId));
        }
		else if(type==11||type==12) {
			Debtor debtor = debtorService.getById(ids.get("debtorId"));
			Address address = addressService.findById(debtor.getAddress_id());
			Long regionId = address.getRegion().getId();
			Long districtId = address.getDistrict().getId();
			pathValues.add(String.valueOf(regionId));
			pathValues.add(String.valueOf(districtId));
			pathValues.add(String.valueOf(ids.get("debtorId")));
			pathValues.add("procedures");
			pathValues.add(String.valueOf(ids.get("procedureId")));
            pathValues.add(String.valueOf(type));
			pathValues.add(String.valueOf(objectId));
		}
		else{
			Debtor debtor = debtorService.getById(ids.get("debtorId"));
			Address address = addressService.findById(debtor.getAddress_id());
			Long regionId = address.getRegion().getId();
			Long districtId = address.getDistrict().getId();
			pathValues.add(String.valueOf(regionId));
			pathValues.add(String.valueOf(districtId));
			pathValues.add(String.valueOf(ids.get("debtorId")));
			pathValues.add(String.valueOf(ids.get("loanId")));
			if(type!=4) {
				pathValues.add(String.valueOf(type));
				pathValues.add(String.valueOf(objectId));
			}
		}

		return attachmentsPathMaker(pathValues);

	}

//	generates folders if !exists and gives path
	public String attachmentsPathMaker(List<String> pathValues){

		String path =  SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "C:/temp/";
		File folder=null;
		for(String pathValue:pathValues){
			path = path + pathValue + "/";
			folder = new File(path);
			if (!folder.exists()) {
				folder.mkdir();
			}
		}
		return path;
	}
//get system files of
private List<SystemFileModel> getSystemFilesByTypeAndId(Long id){

	List<SystemFileModel> list=new ArrayList<>();
	List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2,id);
	for (Information information1:informations){
		Information information=informationService.findById(information1.getId());
		Set<Attachment> attachments= information.getAttachment();
		for (Attachment attachment1:attachments){
			Attachment attachment=attachmentService.findById(attachment1.getId());
			for (SystemFile systemFile1:attachment.getSystemFile()){
				SystemFile systemFile=systemFileService.findById(systemFile1.getId());
				SystemFileModel systemFileModel=new SystemFileModel();
				systemFileModel.setAttachment_id(attachment.getId());
				systemFileModel.setSys_name(systemFile.getName());
				systemFileModel.setSystem_file_id(systemFile.getId());
				systemFileModel.setAttachment_name(attachment.getName());
				systemFileModel.setPath(systemFile.getPath());
				list.add(systemFileModel);
			}
		}
	}

	return list;
}
}



