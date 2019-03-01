package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.collateral.GuarantorAgreement;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.collateral.GuarantorAgreementService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GuarantorAgreementController {

    @Autowired
    DebtorService debtorService;

    @Autowired
    LoanService loanService;

    @Autowired
    GuarantorAgreementService guarantorAgreementService;

    @Autowired
    OwnerService ownerService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }


    @RequestMapping("/manage/debtor/{debtorId}/loan/{loanId}/guarantoragreement/{id}/save")
    public String saveGet(ModelMap model, @PathVariable("debtorId") Long debtorId,@PathVariable("loanId") Long loanId,@PathVariable("id") Long id){

        Debtor debtor = debtorService.getById(debtorId);
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtor);

        Loan loan=loanService.getById(loanId);
        model.addAttribute("loan",loan);
        List<Loan> loans=new ArrayList<>();
        for(Loan l:debtor.getLoans()){
            if(l.getParent()==null){
                loans.add(l);
            }
        }
        model.addAttribute("tLoans", loans);

        if(id==0){
            ArrayList<Long> loanIds=new ArrayList<>();
            GuarantorAgreement guarantorAgreement=new GuarantorAgreement();
            guarantorAgreement.setNotaryOfficeRegDate(new Date());
            model.addAttribute("guarantorAgreement",guarantorAgreement);
            model.addAttribute("ownerText", "");
            model.addAttribute("notaryText", "");

            model.addAttribute("loanIds", loanIds);
        }
        else if(id>0){
            ArrayList<Long> loanIds=new ArrayList<>();
            GuarantorAgreement guarantorAgreement=guarantorAgreementService.getById(id);
            model.addAttribute("guarantorAgreement",guarantorAgreement);
            Owner notary = guarantorAgreement.getNotary();
            Owner owner = guarantorAgreement.getOwner();
            for(Loan loan1:guarantorAgreement.getLoans()){
                loanIds.add(loan1.getId());
            }
            model.addAttribute("loanIds",loanIds);
            String ownerText = "";
            String notaryText = "";

            if(owner!=null)
            {
                ownerText = "[" + owner.getId() + "] "
                        + owner.getName()
                        + " (" + (owner.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
            }
            if(notary!=null)
            {
                notaryText = "[" + notary.getId() + "] "
                        + notary.getName()
                        + " (" + (notary.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
            }
            model.addAttribute("ownerText", ownerText);
            model.addAttribute("notaryText", notaryText);
        }

        return "manage/debtor/loan/guarantoragreement/save";
    }

    @PostMapping("/manage/debtor/{debtorId}/loan/{loanId}/guarantoragreement/save")
    public String savePost(@PathVariable("debtorId") Long debtorId,@PathVariable("loanId") Long loanId,String[] loanses,GuarantorAgreement guarantorAgreement){

        Set<Loan> loanSet = new HashSet<>();
        for (String l : loanses) {
            loanSet.add(loanService.getById(Long.valueOf(l)));
        }

        if(guarantorAgreement.getId()==0){
            Owner owner = ownerService.getById(guarantorAgreement.getOwner().getId());
            guarantorAgreement.setOwner(owner);
            guarantorAgreement.setLoans(loanSet);
            guarantorAgreementService.add(guarantorAgreement);
        }
        else if(guarantorAgreement.getId()>0){
            Owner owner = ownerService.getById(guarantorAgreement.getOwner().getId());
            guarantorAgreement.setOwner(owner);
            guarantorAgreement.setLoans(loanSet);
            guarantorAgreementService.update(guarantorAgreement);
        }

        return "redirect: /manage/debtor/{debtorId}/loan/{loanId}/view";
    }
}
