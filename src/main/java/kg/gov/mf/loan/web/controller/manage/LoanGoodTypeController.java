package kg.gov.mf.loan.web.controller.manage;


import kg.gov.mf.loan.manage.model.loan.GoodType;
import kg.gov.mf.loan.manage.service.loan.GoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LoanGoodTypeController {

    //region services

    @Autowired
    GoodTypeService goodTypeService;

    //endregion

    @GetMapping("/goodType/list")
    public String list(Model model){

        List<GoodType> goodTypeList=goodTypeService.list();

        model.addAttribute("list",goodTypeList);

        return "/manage/debtor/loan/loangoods/goodType/list";
    }

    @GetMapping("/goodType/{id}/save")
    public String getSave(Model model,@PathVariable("id") Long id){

        if(id==0){
            model.addAttribute("type",new GoodType());
        }
        else {
            GoodType goodType=goodTypeService.getById(id);
            model.addAttribute("type",goodType);
        }

        return "/manage/debtor/loan/loangoods/goodType/save";
    }

    @PostMapping("/goodType/save")
    public String postSave(GoodType type){

        if (type.getId()==0){
            goodTypeService.add(type);
        }
        else {
            goodTypeService.update(type);
        }

        return "redirect:/goodType/list";
    }

}
