package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.manage.service.collection.*;
import kg.gov.mf.loan.output.report.model.ReferenceView;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

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

    @Autowired
    ReferenceViewService referenceViewService;

    @Autowired
    CollectionPhaseGroupService collectionPhaseGroupService;

    @Autowired
    CollectionPhaseIndexService collectionPhaseIndexService;

    @Autowired
    CollectionPhaseSubIndexService collectionPhaseSubIndexService;

    @Autowired
    ProcedureStatusService procedureStatusService;

    @RequestMapping("/collectionPhaseViews")
    public String collectionPhaseViewList(ModelMap model){

        List<ReferenceView> collectionPhaseGroups=referenceViewService.findByParameter("collection_phase_group");
        model.addAttribute("collectionPhaseGroups",collectionPhaseGroups);

        List<ReferenceView> collectionPhaseIndexes=referenceViewService.findByParameter("collection_phase_index");
        model.addAttribute("collectionPhaseIndexes",collectionPhaseIndexes);

        List<ReferenceView> collectionPhaseSubIndexes=referenceViewService.findByParameter("collection_phase_sub_index");
        model.addAttribute("collectionPhaseSubIndexes",collectionPhaseSubIndexes);

        List<ReferenceView> finGroups=referenceViewService.findByParameter("fin_group");
        model.addAttribute("finGroups",finGroups);

        model.addAttribute("regions",regionService.findAll());
        model.addAttribute("districts",districtService.findAll());
        model.addAttribute("phaseTypes",phaseTypeService.list());
        model.addAttribute("procedures",phaseStatusService.list());

        model.addAttribute("loggedinuser", Utils.getPrincipal());

        return "manage/debtor/collectionprocedure/collectionphase/collectionPhaseViewList";
    }

    @RequestMapping("/collectionPhaseViews/claim")
    public String collectionPhaseViewListClaim(ModelMap model){

        List<ReferenceView> collectionPhaseGroups=referenceViewService.findByParameter("collection_phase_group");
        model.addAttribute("collectionPhaseGroups",collectionPhaseGroups);

        List<ReferenceView> collectionPhaseIndexes=referenceViewService.findByParameter("collection_phase_index");
        model.addAttribute("collectionPhaseIndexes",collectionPhaseIndexes);

        List<ReferenceView> collectionPhaseSubIndexes=referenceViewService.findByParameter("collection_phase_sub_index");
        model.addAttribute("collectionPhaseSubIndexes",collectionPhaseSubIndexes);

        List<ReferenceView> finGroups=referenceViewService.findByParameter("fin_group");
        model.addAttribute("finGroups",finGroups);

        model.addAttribute("regions",regionService.findAll());
        model.addAttribute("districts",districtService.findAll());
        model.addAttribute("phaseTypes",phaseTypeService.list());
        model.addAttribute("procedures",phaseStatusService.list());

        model.addAttribute("loggedinuser", Utils.getPrincipal());

        return "manage/debtor/collectionprocedure/collectionphase/collectionPhaseViewListClaim";
    }

    @RequestMapping("/collectionPhaseViews/second")
    public String collectionPhaseViewList1(ModelMap model){


        model.addAttribute("collectionPhaseGroups",collectionPhaseGroupService.list());

        model.addAttribute("collectionPhaseIndexes",collectionPhaseIndexService.list());

        model.addAttribute("collectionPhaseSubIndexes",collectionPhaseSubIndexService.list());

        List<ReferenceView> finGroups=referenceViewService.findByParameter("fin_group");
        model.addAttribute("finGroups",finGroups);

        List<ReferenceView> phaseTypes=referenceViewService.findByParameter("collection_phase_type");
        List<ReferenceView> phaseStatuses=referenceViewService.findByParameter("collection_phase_status");

        HashMap<Long,String> typeNames=new HashMap<>();
        for (ReferenceView r:phaseTypes){
            typeNames.put(r.getId(),r.getName());
        }
        HashMap<Long,String> statusNames=new HashMap<>();
        for (ReferenceView r:phaseStatuses){
            statusNames.put(r.getId(),r.getName());
        }
        model.addAttribute("typeList",typeNames);
        model.addAttribute("statusList",statusNames);


        model.addAttribute("regions",regionService.findAll());
        model.addAttribute("districts",districtService.findAll());
        model.addAttribute("phaseTypes",phaseTypes);
        model.addAttribute("procedures",procedureStatusService.list());

        model.addAttribute("loggedinuser", Utils.getPrincipal());

        return "manage/debtor/collectionprocedure/collectionphase/collectionPhaseViewList1";
    }


    @RequestMapping("/collectionPhase/{id}/details")
    public String getDetails(ModelMap model, @PathVariable("id") Long id){
        model.addAttribute("som",id);

        return  "/manage/debtor/collectionprocedure/collectionphase/collectionPhaseViewDetails";

    }

}
