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
import kg.gov.mf.loan.manage.model.collateral.CollateralSummary;
import kg.gov.mf.loan.manage.service.collateral.CollateralService;
import kg.gov.mf.loan.manage.service.collateral.CollateralSummaryService;

@Controller
public class CollateralSummaryController {
	
	@Autowired
	CollateralService collateralService;
	
	@Autowired
	CollateralSummaryService summaryService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/summary/save"})
    public String saveCollateralSummary(CollateralSummary summary, @PathVariable("collateralId")Long collateralId,  ModelMap model)
    {
		Collateral collateral = collateralService.findById(collateralId);
		if(summary != null && summary.getId() == 0)
		{
			CollateralSummary newSummary = new CollateralSummary(summary.getOnDate(), 
					summary.getAgreementQuantity(), 
					summary.getAgreementQuantity(), 
					summary.getCollateralLoanCoverRatio(), 
					summary.getCollateralAmount(), 
					summary.getLoanAmount(),
					summary.getItemAverageCondition(), 
					summary.getItemWorstCondition(), 
					summary.getItemAvgConditionByCollateral(), 
					summary.getItemAvgConditionByLoan());
			
			newSummary.setCollateral(collateral);
			summaryService.save(newSummary);
		}
		
		if(summary != null && summary.getId() > 0)
		{
			summaryService.update(summary);
		}
		
		return "redirect:" + "/manage/collateral/{collateralId}/view#tab_1";
    }

	@RequestMapping(value = { "/manage/collateral/{collateralId}/summary/delete"})
    public String deleteCollateralSummary(long id, @PathVariable("collateralId")Long collateralId)
    {
		if(id > 0)
			summaryService.deleteById(id);
		
		return "redirect:" + "/manage/collateral/{collateralId}/view#tab_1";
    }

}
