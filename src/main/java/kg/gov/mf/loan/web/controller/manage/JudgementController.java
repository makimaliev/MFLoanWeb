//package kg.gov.mf.loan.web.controller.manage;
//
//import kg.gov.mf.loan.manage.model.loan.Judgement;
//import kg.gov.mf.loan.manage.repository.loan.JudgementRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller
//public class JudgementController {
//
//
//    //region services
//    @Autowired
//    JudgementRepository judgementRepository;
//    //endregion
//
//
//    @GetMapping("/manage/debtor/{debtorId}/loan/{loanId}/judgement/list")
//    public String list(@PathVariable("debtorId") Long debtorId,
//                       @PathVariable("loanId") Long loanId, Model model){
//
//        List<Judgement> judgementList = judgementRepository.findAll();
//
//        model.addAttribute("judgementList",judgementList);
//
//        return null;
//    }
//
//    @GetMapping("/manage/debtor/{debtorId}/loan/{loanId}/judgement/{judgementId}/view")
//    public String view(@PathVariable("debtorId") Long debtorId,
//                       @PathVariable("loanId") Long loanId,
//                       @PathVariable("judgementId") Long judgementId, Model model){
//
//        Judgement judgement=judgementRepository.findOne(judgementId);
//
//        model.addAttribute("judgement",judgement);
//        model.addAttribute("debtorId",debtorId);
//        model.addAttribute("loanId",loanId);
//
//        return null;
//    }
//
//    @GetMapping("/manage/debtor/{debtorId}/loan/{loanId}/judgement/{judgementId}/view")
//    public String saveForm(@PathVariable("debtorId") Long debtorId,
//                       @PathVariable("loanId") Long loanId,
//                       @PathVariable("judgementId") Long judgementId, Model model){
//
//        if(judgementId == 0){
//            model.addAttribute("judgement",new Judgement());
//        }
//        else if (judgementId > 0){
//            Judgement judgement = judgementRepository.findOne(judgementId);
//            model.addAttribute("judgement",judgement);
//        }
//
//        model.addAttribute("debtorId",debtorId);
//        model.addAttribute("loanId",loanId);
//
//        return null;
//    }
//
//    @PostMapping("manage/debtor/{debtorId}/loan/{loanId}/judgement/save")
//    public String save(Judgement judgement,@PathVariable("debtorId") Long debtorId,
//                       @PathVariable("loanId") Long loanId){
//
//        judgementRepository.save(judgement);
//
//        return "redirect:/manage/debtor/{debtorId}/loan/{loanId}/judgement/"+judgement.getId()+"/view";
//    }
//
//}
