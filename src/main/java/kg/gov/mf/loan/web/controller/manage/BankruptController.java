package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.manage.model.loan.Bankrupt;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.orderterm.CurrencyRate;
import kg.gov.mf.loan.manage.repository.loan.BankruptRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.BankruptService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
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
public class BankruptController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	BankruptService bankruptService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	BankruptRepository bankruptRepository;

	@Autowired
	CurrencyRateService currencyRateService;

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

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/bankrupt/{bankruptId}/view", method=RequestMethod.GET)
	public String view(ModelMap model,
								 @PathVariable("debtorId")Long debtorId,
								 @PathVariable("loanId")Long loanId,
								 @PathVariable("bankruptId")Long bankruptId){
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonSysFiles= gson.toJson(getSystemFilesByPaymentId(bankruptId));
		model.addAttribute("files", jsonSysFiles);

		Bankrupt bankrupt=bankruptService.getById(bankruptId);

		model.addAttribute("bankrupt",bankrupt);
		model.addAttribute("loanId",loanId);
		model.addAttribute("debtorId",debtorId);

		Attachment attachment=new Attachment();
		model.addAttribute("attachment",attachment);

		return "/manage/debtor/loan/bankrupt/view";
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/bankrupt/{bankruptId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("bankruptId")Long bankruptId)
	{
		
		if(bankruptId == 0)
		{
			model.addAttribute("bankrupt", new Bankrupt());
		}
			
		if(bankruptId > 0)
		{
			model.addAttribute("bankrupt", bankruptService.getById(bankruptId));
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/bankrupt/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/bankrupt/save"}, method=RequestMethod.POST)
    public String saveBankrupt(Bankrupt bankrupt, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		bankrupt.setLoan(loan);
		
		if(bankrupt.getId() == 0)
			bankruptService.add(bankrupt);
		else

			bankruptService.update(bankrupt);

        CurrencyRate rate = currencyRateService.findByDateAndType(bankrupt.getStartedOnDate(), loan.getCurrency());
        loan.setCloseRate(rate.getRate());

		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/bankrupt/{bankruptId}/delete", method=RequestMethod.GET)
    public String deleteBankrupt(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("bankruptId")Long bankruptId) {
		if(bankruptId > 0)
		    bankruptRepository.delete(bankruptRepository.findOne(bankruptId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }


	//	add information form
	@RequestMapping("/manage/debtor/{debtorId}/loan/{loanId}/bankrupt/{bankruptId}/addInformation")
	public String getAddInformationForm(Model model, @PathVariable("debtorId") Long debtorId, @PathVariable("loanId") Long loanId, @PathVariable("bankruptId") Long bankruptId){


		String ids="";
		ids=ids+"debtorId:"+debtorId+",";
		ids=ids+"loanId:"+loanId;
		model.addAttribute("ids",ids);

		Bankrupt bankrupt=bankruptService.getById(bankruptId);
		model.addAttribute("object",bankrupt);
		model.addAttribute("systemObjectTypeId",16);

		model.addAttribute("attachment",new Attachment());

		return "/manage/debtor/loan/payment/addInformationForm";

	}


	private List<SystemFileModel> getSystemFilesByPaymentId(Long id){

		List<SystemFileModel> list=new ArrayList<>();
		List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(16,id);
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
