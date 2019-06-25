package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.WriteOff;
import kg.gov.mf.loan.manage.repository.loan.WriteOffRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.WriteOffService;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class WriteOffController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	WriteOffService woService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	WriteOffRepository writeOffRepository;

	@Autowired
	WriteOffService writeOffService;

	@Autowired
	InformationService informationService;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	SystemFileService systemFileService;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/wo/{woId}/view", method=RequestMethod.GET)
	public String view(ModelMap model,
								 @PathVariable("debtorId")Long debtorId,
								 @PathVariable("loanId")Long loanId,
								 @PathVariable("woId")Long woId)
	{
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonSysFiles= gson.toJson(getSystemFilesByPaymentId(woId));
		model.addAttribute("files", jsonSysFiles);

		WriteOff writeOff=writeOffService.getById(woId);

		model.addAttribute("writeOff",writeOff);
		model.addAttribute("loanId",loanId);
		model.addAttribute("debtorId",debtorId);

		Attachment attachment=new Attachment();
		model.addAttribute("attachment",attachment);

		return "/manage/debtor/loan/wo/view";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/wo/{woId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("woId")Long woId)
	{
		
		if(woId == 0)
		{
			WriteOff writeOff=new WriteOff();
			writeOff.setDate(new Date());
			writeOff.setFee(0.0);
			writeOff.setInterest(0.0);
			writeOff.setPenalty(0.0);
			writeOff.setTotalAmount(0.0);
			writeOff.setPrincipal(0.0);
			writeOff.setDescription("-");
			writeOff.setRecord_status(1);
			model.addAttribute("wo",writeOff );
		}
			
		if(woId > 0)
		{
			model.addAttribute("wo", woService.getById(woId));
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/wo/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/wo/save"}, method=RequestMethod.POST)
    public String saveWriteOff(WriteOff wo, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		wo.setLoan(loan);
		
		if(wo.getId() == 0)
			woService.add(wo);
		else
			woService.update(wo);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/wo/{woId}/delete", method=RequestMethod.GET)
    public String deleteWriteOff(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("woId")Long woId) {
		if(woId > 0)
            writeOffRepository.delete(writeOffRepository.findOne(woId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }

	//	add information form
	@RequestMapping("/manage/debtor/{debtorId}/loan/{loanId}/writeOff/{wId}/addInformation")
	public String getAddInformationForm(Model model, @PathVariable("debtorId") Long debtorId, @PathVariable("loanId") Long loanId, @PathVariable("wId") Long wId){


		String ids="";
		ids=ids+"debtorId:"+debtorId+",";
		ids=ids+"loanId:"+loanId;
		model.addAttribute("ids",ids);

		WriteOff writeOff=writeOffService.getById(wId);
		model.addAttribute("object",writeOff);
		model.addAttribute("systemObjectTypeId",13);

		model.addAttribute("attachment",new Attachment());

		return "/manage/debtor/loan/payment/addInformationForm";

	}


	private List<SystemFileModel> getSystemFilesByPaymentId(Long id){

		List<SystemFileModel> list=new ArrayList<>();
		List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(13,id);
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
