package kg.gov.mf.loan.web.controller.manage;


import kg.gov.mf.loan.manage.model.collateral.AdditionalAgreement;
import kg.gov.mf.loan.manage.model.collateral.Collateral;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.service.collateral.AdditionalAgreementService;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AdditionalAgreementController {

    @Autowired
    AdditionalAgreementService additionalAgreementService;

    @Autowired
    DebtorService debtorService;

    @Autowired
    CollateralAgreementService collateralAgreementService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping("/manage/debtor/{debtorId}/collateralagreement/{agreementId}/additionalAgreement/{addId}/save")
    public String save(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("agreementId")Long agreementId,@PathVariable("addId")Long addId){

        model.addAttribute("debtor",debtorService.getById(debtorId));
        model.addAttribute("debtorId",debtorId);
        model.addAttribute("agreementId",agreementId);
        if(addId==0){
            AdditionalAgreement additionalAgreement=new AdditionalAgreement();
            additionalAgreement.setDate(new Date());
            additionalAgreement.setCollateralAgreement(collateralAgreementService.getById(agreementId));
            model.addAttribute("additionalAgreement",additionalAgreement);

        }
        else{
            model.addAttribute("additionalAgreement",additionalAgreementService.getById(addId));
        }
        return "/manage/debtor/collateralagreement/additionalAgreement/save";
    }

    @RequestMapping(value = "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/additionalAgreement/save",method = RequestMethod.POST)
    public String saveAdditionalAgreement(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("agreementId")Long agreementId,AdditionalAgreement additionalAgreement){

        if(additionalAgreement.getId()==0){
            CollateralAgreement collateralAgreement=collateralAgreementService.getById(agreementId);
            additionalAgreement.setCollateralAgreement(collateralAgreement);
            additionalAgreementService.add(additionalAgreement);
        }
        else{
            CollateralAgreement collateralAgreement=collateralAgreementService.getById(agreementId);
            additionalAgreement.setCollateralAgreement(collateralAgreement);
            additionalAgreementService.update(additionalAgreement);
        }
        return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/view";
    }
}
