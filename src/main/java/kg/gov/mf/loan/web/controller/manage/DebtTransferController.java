package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.loan.DebtTransfer;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.loan.DebtTransferRepository;
import kg.gov.mf.loan.manage.service.collateral.QuantityTypeService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.manage.service.loan.DebtTransferService;
import kg.gov.mf.loan.manage.service.loan.GoodTypeService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DebtTransferController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	DebtTransferService dtService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	DebtTransferRepository debtTransferRepository;

	@Autowired
	QuantityTypeService quantityTypeService;

	@Autowired
	GoodTypeService goodTypeService;

	@Autowired
	InformationService informationService;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	SystemFileService systemFileService;

	@Autowired
	OwnerService ownerService;

	static final Logger loggerDT = LoggerFactory.getLogger(DebtTransfer.class);

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/{dtId}/view", method=RequestMethod.GET)
	public String view(ModelMap model,
								 @PathVariable("debtorId")Long debtorId,
								 @PathVariable("loanId")Long loanId,
								 @PathVariable("dtId")Long dtId){
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonSysFiles= gson.toJson(getSystemFilesByPaymentId(dtId));
		model.addAttribute("files", jsonSysFiles);

		DebtTransfer debtTransfer=dtService.getById(dtId);

		model.addAttribute("dt",debtTransfer);
		model.addAttribute("loanId",loanId);
		model.addAttribute("debtorId",debtorId);

		Attachment attachment=new Attachment();
		model.addAttribute("attachment",attachment);

		return "/manage/debtor/loan/debttransfer/view";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/{dtId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("dtId")Long dtId)
	{
		
		if(dtId == 0)
		{
			DebtTransfer debtTransfer=new DebtTransfer();
			debtTransfer.setDate(new Date());
			debtTransfer.setQuantityType(quantityTypeService.getById(Long.valueOf(1)));
			model.addAttribute("dt", debtTransfer);
			model.addAttribute("thisUnit","");
			model.addAttribute("thisGoodType","");
			model.addAttribute("ownerText","");
		}
			
		if(dtId > 0)
		{
//			[166] АВП "Биримдик-1" (Организация)
			DebtTransfer debtTransfer=dtService.getById(dtId);
			Debtor debtor=debtorService.getByOwnerId(debtTransfer.getTransferPersonId());
			Owner owner=ownerService.getById(debtTransfer.getTransferPersonId());
			String ownerType="Организация";
			if(owner.getOwnerType().equals("PERSON")){
				ownerType="Физ. лицо";
			}
			String fromName="["+owner.getId()+"] "+owner.getName()+" ("+ownerType+")";
			model.addAttribute("ownerText",fromName);
			model.addAttribute("dt", debtTransfer);
//			model.addAttribute("thisUnit",quantityTypeService.getById(dtService.getById(dtId).getUnitTypeId()).getName());
//			model.addAttribute("thisGoodType",goodTypeService.getById(dtService.getById(dtId).getGoodsTypeId()).getName());
		}

		model.addAttribute("unitTypes",quantityTypeService.list());
		model.addAttribute("goodTypes",goodTypeService.list());
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/debttransfer/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/save"}, method=RequestMethod.POST)
    public String saveDebtTransfer(DebtTransfer dt, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		dt.setLoan(loan);
//		dt.setTransferCreditId(Long.valueOf(0));
		
		if(dt.getId() == 0)
			dtService.add(dt);
		else
			dtService.update(dt);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/{transferId}/delete", method=RequestMethod.GET)
    public String deleteDebtTransfer(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("transferId")Long transferId) {
		if(transferId > 0)
		    debtTransferRepository.delete(debtTransferRepository.findOne(transferId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }

	//	add information form
	@RequestMapping("/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/{dId}/addInformation")
	public String getAddInformationForm(Model model, @PathVariable("debtorId") Long debtorId, @PathVariable("loanId") Long loanId, @PathVariable("dId") Long dId){


		String ids="";
		ids=ids+"debtorId:"+debtorId+",";
		ids=ids+"loanId:"+loanId;
		model.addAttribute("ids",ids);

		DebtTransfer debtTransfer=dtService.getById(dId);
		model.addAttribute("object",debtTransfer);
		model.addAttribute("systemObjectTypeId",15);

		model.addAttribute("attachment",new Attachment());

		return "/manage/debtor/loan/payment/addInformationForm";

	}


	private List<SystemFileModel> getSystemFilesByPaymentId(Long id){

		List<SystemFileModel> list=new ArrayList<>();
		List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(15,id);
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
