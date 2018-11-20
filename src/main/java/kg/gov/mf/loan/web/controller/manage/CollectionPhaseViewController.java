package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.manage.service.collection.PhaseStatusService;
import kg.gov.mf.loan.manage.service.collection.PhaseTypeService;
import kg.gov.mf.loan.manage.service.collection.ProcedureStatusService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CollectionPhaseViewController {

    @Autowired
    RegionService regionService;

    @Autowired
    DistrictService districtService;

    @Autowired
    PhaseTypeService phaseTypeService;

    @Autowired
    PhaseStatusService phaseStatusService;

    @RequestMapping("/collectionPhaseViews")
    public String collectionPhaseViewList(ModelMap model){
        model.addAttribute("regions",regionService.findAll());
        model.addAttribute("districts",districtService.findAll());
        model.addAttribute("phaseTypes",phaseTypeService.list());
        model.addAttribute("procedures",phaseStatusService.list());

        model.addAttribute("loggedinuser", Utils.getPrincipal());

        return "manage/debtor/collectionprocedure/collectionphase/collectionPhaseViewList";
    }
}
