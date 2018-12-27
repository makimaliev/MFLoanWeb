package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.CollectionPhaseGroup;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CollectionPhaseGroupController {

    @Autowired
    CollectionPhaseGroupService collectionPhaseGroupService;

    @RequestMapping(value ="/manage/debtor/collectionprocedure/collectionphase/group/list",method = RequestMethod.GET)
    public String listPhaseGroups(ModelMap model){
        model.addAttribute("groups",collectionPhaseGroupService.list());
        model.addAttribute("collectionPhaseGroup",new CollectionPhaseGroup());

        return "/manage/debtor/collectionprocedure/collectionphase/group/list";
    }

    @RequestMapping(value ="/manage/debtor/collectionprocedure/collectionphase/group/{groupId}/save",method = RequestMethod.GET)
    public String save(ModelMap model, @PathVariable(value = "groupId") Long groupId){

        if(groupId==0){
            model.addAttribute("group",new CollectionPhaseGroup());
        }
        else if(groupId>0){
            model.addAttribute("group",collectionPhaseGroupService.getById(groupId));
        }

        return "/manage/debtor/collectionprocedure/collectionphase/group/save";
    }

    @RequestMapping(value ="/manage/debtor/collectionprocedure/collectionphase/group/save",method = RequestMethod.POST)
    public String savePhaseGroup(CollectionPhaseGroup collectionPhaseGroup){

        if(collectionPhaseGroup.getId()==0){
            collectionPhaseGroupService.add(collectionPhaseGroup);
        }
        else if(collectionPhaseGroup.getId()>0){
            collectionPhaseGroupService.update(collectionPhaseGroup);
        }

        return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/group/list";
    }
}
