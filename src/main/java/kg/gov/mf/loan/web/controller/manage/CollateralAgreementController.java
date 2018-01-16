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

import kg.gov.mf.loan.manage.model.collateral.Collateral;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralArrestFree;
import kg.gov.mf.loan.manage.model.collateral.CollateralInspection;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.collateral.CollateralArrestFreeService;
import kg.gov.mf.loan.manage.service.collateral.CollateralInspectionService;
import kg.gov.mf.loan.manage.service.collateral.CollateralService;

@Controller
public class CollateralAgreementController {
	
	@Autowired
	CollateralService collateralService;
	
	@Autowired
	CollateralAgreementService agreementService;
	
	@Autowired
	CollateralInspectionService inspectionService;
	
	@Autowired
	CollateralArrestFreeService afService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/view"})
    public String viewAgreement(ModelMap model, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId) {
		
		CollateralAgreement agreement = agreementService.getById(agreementId);
		
		model.addAttribute("agreement", agreement);
		
		model.addAttribute("inspections", agreement.getCollateralInspections());
	    model.addAttribute("emptyInspection", new CollateralInspection());
	    
	    model.addAttribute("AFs", agreement.getCollateralArrestFrees());
	    model.addAttribute("emptyAF", new CollateralArrestFree());
		
		return "/manage/collateral/collateralagreement/view";
		
	}
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/agreement/save"})
    public String saveCollateralAgreement(CollateralAgreement agreement, @PathVariable("collateralId")Long collateralId,  ModelMap model)
    {
		Collateral collateral = collateralService.getById(collateralId);
		agreement.setCollateral(collateral);
		
		if(agreement.getId() == null || agreement.getId() == 0)
			agreementService.add(agreement);
		else
			agreementService.update(agreement);
		
		return "redirect:" + "/manage/collateral/{collateralId}/view";
    }

	@RequestMapping(value = { "/manage/collateral/{collateralId}/agreement/delete"})
    public String deleteCollateralAgreement(long id, @PathVariable("collateralId")Long collateralId)
    {
		if(id > 0)
			agreementService.remove(agreementService.getById(id));
		
		return "redirect:" + "/manage/collateral/{collateralId}/view";
    }
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/inspection/save"})
    public String saveCollateralInspection(CollateralInspection inspection, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId)
    {
		CollateralAgreement agreement = agreementService.getById(agreementId);
		inspection.setCollateralAgreement(agreement);
		
		if(inspection.getId() == null || inspection.getId() == 0)
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
		
		if(af.getId() == null || af.getId() == 0)
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
	
}
