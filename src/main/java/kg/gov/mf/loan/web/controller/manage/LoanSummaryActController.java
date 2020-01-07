package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.Counter;
import kg.gov.mf.loan.manage.model.loan.LoanSummaryAct;
import kg.gov.mf.loan.manage.model.loan.LoanSummaryActState;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.repository.CounterRepository;
import kg.gov.mf.loan.manage.service.loan.LoanSummaryActService;
import kg.gov.mf.loan.manage.service.loan.LoanSummaryActStateService;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.report.model.ReferenceView;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.web.fetchModels.LoanSummaryActMetaModel;
import kg.gov.mf.loan.web.fetchModels.LoanSummaryActModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class LoanSummaryActController {

    //region services

    @Autowired
    CounterRepository counterRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ReferenceViewService referenceViewService;

    @Autowired
    LoanSummaryActService loanSummaryActService;

    @Autowired
    LoanSummaryActStateService loanSummaryActStateService;

    @Autowired
    LoanSummaryService loanSummaryService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }
    //endregion

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

    @GetMapping("/loanSummaryAct/list/after2020")
    public String listAfter2020(ModelMap model){

        List<ReferenceView> districts=referenceViewService.findByParameter("district");
        model.addAttribute("districts",districts);

        List<ReferenceView> regions=referenceViewService.findByParameter("region");
        model.addAttribute("regions",regions);

        List<LoanSummaryActState> loanSummaryActStates=loanSummaryActStateService.list();
        model.addAttribute("states",loanSummaryActStates);


        return "manage/debtor/loan/loansummaryact/list1";
    }

    @GetMapping("/loanSummaryAct/{id}/delete")
    public String delete(@PathVariable("id") Long id){

        LoanSummaryAct loanSummaryAct=loanSummaryActService.getById(id);
        loanSummaryActService.remove(loanSummaryAct);
        for (LoanSummary l:loanSummaryAct.getLoanSummaries()){
            LoanSummary loanSummary=loanSummaryService.getById(l.getId());
            loanSummaryService.remove(loanSummary);
        }
        return "redirect:/loanSummaryAct/list";
    }

    @GetMapping("/loanSummaryAct/{id}/partialedit")
    public String regFieldEditForm(@PathVariable Long id, Model model){

        LoanSummaryAct loanSummaryAct = loanSummaryActService.getById(id);
        List<LoanSummaryActState> loanSummaryActStateList = loanSummaryActStateService.list();

        model.addAttribute("object",loanSummaryAct);
        model.addAttribute("list",loanSummaryActStateList);

        return "/manage/debtor/loan/loansummaryact/editRegFields";
    }

    @PostMapping("/loanSummaryAct/saveregfields")
    public String saveRegFields(LoanSummaryAct loanSummaryAct,Date date){

        LoanSummaryAct realLoanSummaryAct = loanSummaryActService.getById(loanSummaryAct.getId());

        if(loanSummaryActService.isUneque(loanSummaryAct.getReg_number())) {
            realLoanSummaryAct.setReg_number(loanSummaryAct.getReg_number());
            realLoanSummaryAct.setRegisteredDate(date);
            realLoanSummaryAct.setLoanSummaryActState(loanSummaryAct.getLoanSummaryActState());

            loanSummaryActService.update(realLoanSummaryAct);
        }

        return  "redirect:/loanSummaryAct/list";
    }

//    **********************************************************************************************************
//    REST
//    **********************************************************************************************************

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

        String baseQuery="select l.id,l.amount,l.onDate,l.registeredDate,l.signedDate,l.reg_number,dist.name as districtName,r.name as regionName,d.id as debtorId,d.name as debtorName,\n" +
                "       l.au_created_by, l.au_created_date,l.au_last_modified_by, l.au_last_modified_date,\n" +
                "       l.loanSummaryActStateId as state, loan.supervisorId\n" +
                "from loanSummaryAct l,debtor d,address a,district dist,region r, loanSummary ls, loan, loanSummaryAct_loanSummary lslsa where l.debtorId=d.id\n" +
                "                                                                  and a.district_id=dist.id\n" +
                "                                                                  and a.region_id=r.id\n" +
                "                                                                  and d.address_id=a.id\n" +
                "                                                                  and lslsa.LoanSummaryAct_id=l.id and ls.id in (lslsa.loanSummaries_id)\n" +
                "                                                                  and ls.loanId = loan.id\n"+
                                                                                   searchQueries+
                "order by id desc, debtorName asc,l.onDate desc,l.amount desc LIMIT " + offset +"," + perPage;
        Query query=entityManager.createNativeQuery(baseQuery, LoanSummaryActModel.class);
        List<LoanSummaryActModel> list=query.getResultList();

        String countQuery="select count(1)\n" +
                "from loanSummaryAct l,debtor d,address a,district dist,region r, loanSummary ls, loan, loanSummaryAct_loanSummary lslsa where l.debtorId=d.id \n" +
                "                                                                  and a.district_id=dist.id \n" +
                "                                                                  and a.region_id=r.id  \n" +
                "                                                                  and d.address_id=a.id " +
                "                                                                  and lslsa.LoanSummaryAct_id=l.id and ls.id in (lslsa.loanSummaries_id)\n" +
                "                                                                  and ls.loanId = loan.id \n"+
                                                                                   searchQueries;
        BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);
        Meta meta = new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

        LoanSummaryActMetaModel metaModel=new LoanSummaryActMetaModel();
        metaModel.setData(list);
        metaModel.setMeta(meta);

        return metaModel;
    }

    @PostMapping("/api/loanSummaryActViews/after2020")
    @ResponseBody
    public LoanSummaryActMetaModel getAllAfter2020(@RequestParam Map<String,String> datatable){

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

        String baseQuery="select l.id,l.amount,l.onDate,l.registeredDate,l.signedDate,l.reg_number,dist.name as districtName,r.name as regionName,d.id as debtorId,d.name as debtorName,\n" +
                "       l.au_created_by, l.au_created_date,l.au_last_modified_by, l.au_last_modified_date,\n" +
                "       l.loanSummaryActStateId as state, loan.supervisorId\n" +
                "from loanSummaryAct l,debtor d,address a,district dist,region r, loanSummary ls, loan, loanSummaryAct_loanSummary lslsa where l.debtorId=d.id\n" +
                "                                                                  and a.district_id=dist.id\n" +
                "                                                                  and a.region_id=r.id\n" +
                "                                                                  and d.address_id=a.id\n" +
                "                                                                  and lslsa.LoanSummaryAct_id=l.id and ls.id in (lslsa.loanSummaries_id)\n" +
                "                                                                  and ls.loanId = loan.id and DATEDIFF('2020-01-01',l.onDate) <= 0\n"+
                searchQueries+
                "order by id desc, debtorName asc,l.onDate desc,l.amount desc LIMIT " + offset +"," + perPage;
        Query query=entityManager.createNativeQuery(baseQuery, LoanSummaryActModel.class);
        List<LoanSummaryActModel> list=query.getResultList();

        String countQuery="select count(1)\n" +
                "from loanSummaryAct l,debtor d,address a,district dist,region r, loanSummary ls, loan, loanSummaryAct_loanSummary lslsa where l.debtorId=d.id \n" +
                "                                                                  and a.district_id=dist.id \n" +
                "                                                                  and a.region_id=r.id  \n" +
                "                                                                  and d.address_id=a.id " +
                "                                                                  and lslsa.LoanSummaryAct_id=l.id and ls.id in (lslsa.loanSummaries_id)\n" +
                "                                                                  and ls.loanId = loan.id \n"+
                searchQueries;
        BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);
        Meta meta = new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

        LoanSummaryActMetaModel metaModel=new LoanSummaryActMetaModel();
        metaModel.setData(list);
        metaModel.setMeta(meta);

        return metaModel;
    }

    @PostMapping("api/loanSummaryAct/register")
    @ResponseBody
    public String changeState(Long id){

        registerLoanSummaryact(id);
        return "OK";
    }

    //register loanSummaryact function
    public void registerLoanSummaryact(Long id){
        LoanSummaryAct loanSummaryAct = loanSummaryActService.getById(id);

        Counter counter = counterRepository.getByEntityNameEquals(LoanSummaryAct.class.getSimpleName());


        if (counter == null){
            Counter counter1 =new Counter();
            counter1.setEntityName(LoanSummaryAct.class.getSimpleName());
            counter1.setNumber(1L);
            counterRepository.save(counter1);

            loanSummaryAct.setReg_number("№ "+counter1.getNumber());
            loanSummaryAct.setRegisteredDate(new Date());

            loanSummaryActService.update(loanSummaryAct);

            counter1.setNumber(counter1.getNumber()+1);
            counterRepository.save(counter1);
        }
        else{
            loanSummaryAct.setReg_number("№ "+counter.getNumber());
            loanSummaryAct.setRegisteredDate(new Date());

            loanSummaryActService.update(loanSummaryAct);

            counter.setNumber(counter.getNumber()+1);
            counterRepository.save(counter);
        }

    }

}
