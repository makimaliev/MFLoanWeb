package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;

@Controller
public class CollateralAgreementController {
	
	@Autowired
	CollateralAgreementService agreementService;
	
	@Autowired
	DebtorService debtorService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/view"})
    public String viewAgreement(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("agreementId")Long agreementId) {
		
		CollateralAgreement agreement = agreementService.getById(agreementId);
		model.addAttribute("agreement", agreement);
		model.addAttribute("items", agreement.getCollateralItems());
		
		return "/manage/debtor/collateralagreement/view";
		
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/save", method=RequestMethod.GET)
	public String formCollateralAgreement(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("agreementId")Long agreementId)
	{
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("tLoans", debtor.getLoans());
		
		if(agreementId == 0)
		{
			model.addAttribute("agreement", new CollateralAgreement());
		}
			
		if(agreementId > 0)
		{
			model.addAttribute("agreement", agreementService.getById(agreementId));
		}
		
		return "/manage/debtor/collateralagreement/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/save"})
    public String saveCollateralAgreement(CollateralAgreement agreement, 
    		@PathVariable("debtorId")Long debtorId,
    		String[] loanIdList,
    		ModelMap model)
    {
		if(agreement.getId() == 0)
			agreementService.add(agreement);
		else
			agreementService.update(agreement);
		
		return "redirect:" + "/manage/debtor/{debtorId}/view";
    }

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/delete"})
    public String deleteCollateralAgreement(long id, @PathVariable("debtorId")Long debtorId)
    {
		if(id > 0)
			agreementService.remove(agreementService.getById(id));
		
		return "redirect:" + "/manage/debtor/{debtorId}/view";
    }
	
	/*
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/inspection/save"})
    public String saveCollateralInspection(CollateralInspection inspection, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId)
    {
		CollateralAgreement agreement = agreementService.getById(agreementId);
		inspection.setCollateralAgreement(agreement);
		
		if(inspection.getId() == 0)
			inspectionService.add(inspection);
		else		
			inspectionService.update(inspection);
		
		return "redirect:" + "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/view#tab_0";
    }
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/inspection/delete"})
    public String deleteCollateralInspection(long id, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId)
    {
		if(id > 0)
			inspectionService.remove(inspectionService.getById(id));
		
		return "redirect:" + "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/view#tab_0";
    }
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/af/save"})
    public String saveArrestFree(CollateralArrestFree af, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId)
    {
		CollateralAgreement agreement = agreementService.getById(agreementId);
		af.setCollateralAgreement(agreement);
		
		if(af.getId() == 0)
			afService.add(af);
		else
			afService.update(af);
		
		return "redirect:" + "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/view#tab_1";
    }
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/af/delete"})
    public String deleteCollateralArrestFree(long id, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId)
    {
		if(id > 0)
			afService.remove(afService.getById(id));
		
		return "redirect:" + "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/view#tab_1";
    }
	*/
}
