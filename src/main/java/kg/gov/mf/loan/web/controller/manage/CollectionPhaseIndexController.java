package kg.gov.mf.loan.web.controller.manage;


import kg.gov.mf.loan.manage.model.collection.CollectionPhaseIndex;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CollectionPhaseIndexController {

    @Autowired
    CollectionPhaseIndexService collectionPhaseIndexService;

    @RequestMapping(value = "/manage/debtor/collectionprocedure/collectionphase/index/list")
    public String list(ModelMap model){

        model.addAttribute("indices",collectionPhaseIndexService.list());

        return "/manage/debtor/collectionprocedure/collectionphase/index/list";
    }

    @RequestMapping(value ="/manage/debtor/collectionprocedure/collectionphase/index/{indexId}/save",method = RequestMethod.GET)
    public String save(ModelMap model, @PathVariable(value = "indexId") Long indexId){

        if(indexId==0){
            model.addAttribute("index",new CollectionPhaseIndex());
        }
        else if(indexId>0){
            model.addAttribute("index",collectionPhaseIndexService.getById(indexId));
        }

        return "/manage/debtor/collectionprocedure/collectionphase/index/save";
    }

    @RequestMapping(value ="/manage/debtor/collectionprocedure/collectionphase/index/save",method = RequestMethod.POST)
    public String savePhaseGroup(CollectionPhaseIndex collectionPhaseIndex){

        if(collectionPhaseIndex.getId()==0){
            collectionPhaseIndexService.add(collectionPhaseIndex);
        }
        else if(collectionPhaseIndex.getId()>0){
            collectionPhaseIndexService.update(collectionPhaseIndex);
        }

        return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/index/list";
    }
}
