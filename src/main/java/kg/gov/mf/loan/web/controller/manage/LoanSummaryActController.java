package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.loan.LoanSummaryAct;
import kg.gov.mf.loan.manage.model.loan.LoanSummaryActState;
import kg.gov.mf.loan.manage.service.loan.LoanSummaryActService;
import kg.gov.mf.loan.manage.service.loan.LoanSummaryActStateService;
import kg.gov.mf.loan.output.report.model.ReferenceView;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.web.fetchModels.LoanSummaryActMetaModel;
import kg.gov.mf.loan.web.fetchModels.LoanSummaryActModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Controller
public class LoanSummaryActController {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ReferenceViewService referenceViewService;

    @Autowired
    LoanSummaryActService loanSummaryActService;

    @Autowired
    LoanSummaryActStateService loanSummaryActStateService;

    @GetMapping("/loanSummaryAct/list")
    public String list(ModelMap model){

        List<ReferenceView> districts=referenceViewService.findByParameter("district");
        model.addAttribute("districts",districts);

        List<ReferenceView> regions=referenceViewService.findByParameter("region");
        model.addAttribute("regions",regions);

        List<LoanSummaryActState> loanSummaryActStates=loanSummaryActStateService.list();
        model.addAttribute("states",loanSummaryActStates);


        return "manage/debtor/loan/loansummaryact/list";
    }

    @GetMapping("/loanSummaryAct/{id}/delete")
    public String delete(@PathVariable("id") Long id){

        LoanSummaryAct loanSummaryAct=loanSummaryActService.getById(id);
        loanSummaryActService.remove(loanSummaryAct);
        return "redirect:/loanSummaryAct/list";
    }

    @PostMapping("/api/loanSummaryActViews")
    @ResponseBody
    public LoanSummaryActMetaModel getAll(@RequestParam Map<String,String> datatable){

        String searchQueries="";

        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

        boolean searchByDebtorName= datatable.containsKey("datatable[query][debtorName]");
        if(searchByDebtorName){
            String debtorName = datatable.get("datatable[query][debtorName]");
            searchQueries=searchQueries+" and d.name like '%"+debtorName+"%'\n";
        }

        boolean searchByRegNumber= datatable.containsKey("datatable[query][regNumber]");
        if(searchByRegNumber){
            String regNum = datatable.get("datatable[query][regNumber]");
            searchQueries=searchQueries+" and a.reg_number like '%"+regNum+"%'\n";
        }

        boolean searchByDistrict = datatable.containsKey("datatable[query][districtId]");
        if (searchByDistrict){
            String districtStr = datatable.get("datatable[query][districtId]");
            searchQueries=searchQueries+" and a.district_id="+districtStr+"\n";
        }

        boolean searchByRegion = datatable.containsKey("datatable[query][regionId]");
        if (searchByRegion){
            String regionStr = datatable.get("datatable[query][regionId]");
            searchQueries=searchQueries+" and a.region_id="+regionStr+"\n";
        }

        boolean searchByState = datatable.containsKey("datatable[query][stateId]");
        if (searchByState){
            String stateStr = datatable.get("datatable[query][stateId]");
            searchQueries=searchQueries+" and l.loanSummaryActStateId="+stateStr+"\n";
        }

        boolean getFromDate= datatable.containsKey("datatable[query][fromDater]");
        if (getFromDate){
            String fromDateStr = datatable.get("datatable[query][fromDater]");
            String[] splitted=fromDateStr.split("\\.");
            searchQueries=searchQueries+" and l.onDate>='"+splitted[2]+"-"+splitted[1]+"-"+splitted[0]+"'\n";
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDater]");
        if (getToDate){
            String toDateStr = datatable.get("datatable[query][toDater]");
            String[] splitted=toDateStr.split("\\.");
            searchQueries=searchQueries+" and l.onDate<='"+splitted[2]+"-"+splitted[1]+"-"+splitted[0]+"'\n";
        }

        String baseQuery="select l.id,l.amount,l.onDate,l.registeredDate,l.signedDate,l.reg_number,dist.name as districtName,r.name as regionName,d.id as debtorId,d.name as debtorName,l.loanSummaryActStateId as state\n" +
                "from loanSummaryAct l,debtor d,address a,district dist,region r where l.debtorId=d.id \n" +
                "                                                                  and a.district_id=dist.id \n" +
                "                                                                  and a.region_id=r.id  \n" +
                "                                                                  and d.address_id=a.id \n"+
                                                                                   searchQueries+
                "order by " + sortField + " " + sortStr + " LIMIT " + offset +"," + perPage;
        Query query=entityManager.createNativeQuery(baseQuery, LoanSummaryActModel.class);
        List<LoanSummaryActModel> list=query.getResultList();

        String countQuery="select count(1)\n" +
                "from loanSummaryAct l,debtor d,address a,district dist,region r where l.debtorId=d.id \n" +
                "                                                                  and a.district_id=dist.id \n" +
                "                                                                  and a.region_id=r.id  \n" +
                "                                                                  and d.address_id=a.id \n"+
                                                                                   searchQueries;
        BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);
        Meta meta = new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

        LoanSummaryActMetaModel metaModel=new LoanSummaryActMetaModel();
        metaModel.setData(list);
        metaModel.setMeta(meta);

        return metaModel;
    }

    @PostMapping("/api/loanSummaryActState/change")
    @ResponseBody
    public void changeState(Long id){

        LoanSummaryAct loanSummaryAct=loanSummaryActService.getById(id);
        if(loanSummaryAct.getLoanSummaryActState().getId()==1L){
            loanSummaryAct.setLoanSummaryActState(loanSummaryActStateService.getById(2L));
        }
        else{
            loanSummaryAct.setLoanSummaryActState(loanSummaryActStateService.getById(1L));
        }
        loanSummaryActService.update(loanSummaryAct);
    }

}