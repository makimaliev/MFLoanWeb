package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.manage.repository.loan.LoanGoodsRepository;
import kg.gov.mf.loan.manage.service.collateral.QuantityTypeService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.GoodTypeService;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.LoanGoods;
import kg.gov.mf.loan.manage.service.loan.LoanGoodsService;
import kg.gov.mf.loan.manage.service.loan.LoanService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class LoanGoodsController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	LoanGoodsService lgService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	LoanGoodsRepository loanGoodsRepository;

	@Autowired
	LoanGoodsService loanGoodsService;

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

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/loangoods/{lgId}/view", method=RequestMethod.GET)
	public String view(ModelMap model,
								 @PathVariable("debtorId")Long debtorId,
								 @PathVariable("loanId")Long loanId,
								 @PathVariable("lgId")Long lgId){
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonSysFiles= gson.toJson(getSystemFilesByPaymentId(lgId));
		model.addAttribute("files", jsonSysFiles);

		LoanGoods loanGoods=loanGoodsService.getById(lgId);

		model.addAttribute("good",loanGoods);
		model.addAttribute("loanId",loanId);
		model.addAttribute("debtorId",debtorId);

		Attachment attachment=new Attachment();
		model.addAttribute("attachment",attachment);

		return "/manage/debtor/loan/loangoods/view";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/loangoods/{lgId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("lgId")Long lgId)
	{
		
		if(lgId == 0)
		{
			model.addAttribute("lg", new LoanGoods());
			model.addAttribute("thisUnit","");
			model.addAttribute("thisGoodType","");
		}
			
		if(lgId > 0)
		{
			LoanGoods lg = (LoanGoods) lgService.getById(lgId);

			model.addAttribute("lg", lg);
//			model.addAttribute("thisUnit",quantityTypeService.getById(lgService.getById(lgId).getUnitTypeId()).getName());
//			model.addAttribute("thisGoodType",goodTypeService.getById(lgService.getById(lgId).getGoodsTypeId()).getName());
		}



		model.addAttribute("unitTypes",quantityTypeService.list());
		model.addAttribute("goodTypes",goodTypeService.list());
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/loangoods/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/loangoods/save"}, method=RequestMethod.POST)
    public String saveLoanGoods(LoanGoods lg, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		lg.setLoan(loan);

		if(lg.getId() == 0)
			lgService.add(lg);
		else		
			lgService.update(lg);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/loangoods/{goodsId}/delete", method=RequestMethod.GET)
    public String deleteLoanGoods(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("goodsId")Long goodsId) {
		if(goodsId > 0)
		    loanGoodsRepository.delete(loanGoodsRepository.findOne(goodsId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }


	//	add information form
	@RequestMapping("/manage/debtor/{debtorId}/loan/{loanId}/loangoods/{lgId}/addInformation")
	public String getAddInformationForm(Model model, @PathVariable("debtorId") Long debtorId, @PathVariable("loanId") Long loanId, @PathVariable("lgId") Long lgId){


		String ids="";
		ids=ids+"debtorId:"+debtorId+",";
		ids=ids+"loanId:"+loanId;
		model.addAttribute("ids",ids);

		LoanGoods loanGoods=lgService.getById(lgId);
		model.addAttribute("object",loanGoods);
		model.addAttribute("systemObjectTypeId",14);

		model.addAttribute("attachment",new Attachment());

		return "/manage/debtor/loan/payment/addInformationForm";

	}

	private List<SystemFileModel> getSystemFilesByPaymentId(Long id){

		List<SystemFileModel> list=new ArrayList<>();
		List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(14,id);
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
