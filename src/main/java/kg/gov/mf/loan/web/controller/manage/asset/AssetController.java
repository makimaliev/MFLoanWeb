package kg.gov.mf.loan.web.controller.manage.asset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.asset.Asset;
import kg.gov.mf.loan.manage.model.asset.AssetItem;
import kg.gov.mf.loan.manage.model.asset.AssetStatus;
import kg.gov.mf.loan.manage.model.asset.AssetType;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.service.asset.AssetItemService;
import kg.gov.mf.loan.manage.service.asset.AssetService;
import kg.gov.mf.loan.manage.service.asset.AssetStatusService;
import kg.gov.mf.loan.manage.service.asset.AssetTypeService;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.manage.service.loan.PaymentService;
import kg.gov.mf.loan.web.fetchModels.LoanModel2;
import kg.gov.mf.loan.web.fetchModels.PaymentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/asset")
public class AssetController {

    //region services
    @Autowired
    CollectionPhaseService collectionPhaseService;

    @Autowired
    AssetService assetService;

    @Autowired
    AssetTypeService assetTypeService;

    @Autowired
    AssetStatusService assetStatusService;

    @Autowired
    AssetItemService assetItemService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    LoanRepository loanRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @PersistenceContext
    EntityManager entityManager;

    //endregion

    @GetMapping("/list")
    public String list(Model model){

        List<Asset> assets=new ArrayList<>();
        for (Asset asset:assetService.list()){
            assets.add(assetService.getById(asset.getId()));
        }
        List<AssetType> assetTypelist=assetTypeService.list();
        List<AssetStatus> assetStatuslist=assetStatusService.list();

        model.addAttribute("list",assets);
        model.addAttribute("types",assetTypelist);
        model.addAttribute("statuses",assetStatuslist);

        return "/manage/asset/list";
    }

    @GetMapping("/{id}/view")
    public String view(Model model, @PathVariable("id") Long id){

        Asset asset=assetService.getById(id);
        Set<AssetItem> assetItemList=asset.getAssetItems();

        model.addAttribute("assetId",asset.getId());
        model.addAttribute("asset",asset);
        model.addAttribute("items",assetItemList);

        return "/manage/asset/view";
    }

    @GetMapping("/{id}/save")
    public String getSave(Model model,@PathVariable("id") Long id){

        if(id==null || id==0){
            model.addAttribute("asset",new Asset());
        }
        else if(id>0){
            Asset asset=assetService.getById(id);
            model.addAttribute("asset",asset);
        }

        List<AssetType> assetTypelist=assetTypeService.list();
        List<AssetStatus> assetStatuslist=assetStatusService.list();

        model.addAttribute("types",assetTypelist);
        model.addAttribute("statuses",assetStatuslist);

        return "/manage/asset/save";
    }

    @PostMapping("/save")
    public String postSave(Asset asset) throws ParseException {

        if (asset.getId()==0){
            assetService.add(asset);
        }
        else{
            assetService.update(asset);
        }

        return "redirect:/asset/"+asset.getId()+"/view";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id){

        Asset asset=assetService.getById(id);
        assetService.remove(asset);

        return "redirect:/asset/list";
    }

    @GetMapping("/{assetId}/payment/add")
    public String getSaveLoan(Model model,@PathVariable("assetId") Long assetId){

        Asset asset=assetService.getById(assetId);

        model.addAttribute("asset",asset);

        return "/manage/asset/loanAddForm";
    }

    @PostMapping("/{assetId}/payment/save")
    public String postSaveLoan(@PathVariable("assetId") Long assetId,Asset asset){

        Asset asset1=assetService.getById(assetId);
        asset1.setFromDebtorIds(asset.getFromDebtorIds());
        asset1.setFromLoanIds(asset.getFromLoanIds());
        asset1.setPaymentIds(asset.getPaymentIds());
        assetService.update(asset1);

        assetAmount12Saver(asset1);


        return "redirect:/asset/{assetId}/view";
    }

    @GetMapping("/{assetId}/secondaryPayment/add")
    public String getSaveSecondaryPayments(Model model,@PathVariable("assetId") Long assetId){

        Asset asset=assetService.getById(assetId);

        model.addAttribute("asset",asset);
        String assetLoanIds="";
        for(Long id:asset.getFromLoanIds()){
            assetLoanIds=assetLoanIds+id+",";
        }
        model.addAttribute("assetLoans",assetLoanIds);

        return "/manage/asset/secondaryPaymentAddForm";
    }

    @PostMapping("/{assetId}/secondaryPayment/save")
    public String postSaveSecondaryPayment(@PathVariable("assetId") Long assetId,Asset asset){

        Asset asset1=assetService.getById(assetId);
        asset1.setSecondaryPaymentIds(asset.getSecondaryPaymentIds());
        assetService.update(asset1);
        assetAmount4Saver(asset1);


        return "redirect:/asset/{assetId}/view";
    }

    @GetMapping("/{assetId}/toLoan/add")
    public String getSaveToLoan(Model model,@PathVariable("assetId") Long assetId){

        Asset asset=assetService.getById(assetId);

        model.addAttribute("asset",asset);

        return "/manage/asset/toLoanAddForm";
    }

    @PostMapping("/{assetId}/toLoan/save")
    public String postSaveToLoan(@PathVariable("assetId") Long assetId,Asset asset){

        Asset asset1=assetService.getById(assetId);
        asset1.setToDebtorIds(asset.getToDebtorIds());
        asset1.setLoanIds(asset.getLoanIds());
        assetService.update(asset1);

        return "redirect:/asset/{assetId}/view";
    }

    @GetMapping("/debtor/{debtorId}/loans/{type}/{assetId}")
    public String getLoansByDebtor(Model model,@PathVariable("debtorId") String debtorIds,@PathVariable("assetId") Long assetId,@PathVariable("type") int type){

        Asset asset=assetService.getById(assetId);
        List<Loan> listOfLoans=new ArrayList<>();

        String[] splittedDebtorIds=debtorIds.split(",");
        for (String id:splittedDebtorIds){
            List<Loan> loans=loanRepository.findByDebtorIdAndLoanStateId(Long.valueOf(id),2L);
            listOfLoans.addAll(loans);
        }

        model.addAttribute("loans",listOfLoans);
        model.addAttribute("asset",asset);


        if(type==1){
            return "/manage/asset/loanDropDown";
        }
        else{
            return "/manage/asset/toLoanDropDown";
        }
    }

    //select phase
    @GetMapping("/{assetId}/selectphase")
    public String getPhaseSelectForm(@PathVariable Long assetId,Model model){

        Asset asset = assetService.getById(assetId);

        if(asset.getPhase_id() != null){

            CollectionPhase phase = collectionPhaseService.getById(asset.getPhase_id());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

            model.addAttribute("phaseText","["+phase.getId()+"] "+phase.getPhaseType().getName()+" - "+simpleDateFormat.format(phase.getStartDate()));
        }
        else{
            model.addAttribute("phaseText",null);
        }
        model.addAttribute("asset",asset);

        return "/manage/asset/selectPhaseForm";
    }

    //save selected phase to asset
    @PostMapping("/selectphase/save")
    public String saveSelectedPhase(Asset asset){

        Asset oldAsset = assetService.getById(asset.getId());

        oldAsset.setPhase_id(asset.getPhase_id());

        assetService.update(oldAsset);

        return "redirect:/asset/"+asset.getId()+"/view";
    }


    //******************************************************************************************************************
    //REST REQUESTS
    //******************************************************************************************************************

    @PostMapping("/assetPayments/{assetId}")
    @ResponseBody
    public String apiGetAssetPaymentsByAssetId(@PathVariable("assetId") Long assetId){

        Asset asset=assetService.getById(assetId);
        String strPaymentIds="";
        for (Long paymentId:asset.getPaymentIds()){
            if(strPaymentIds.equals("")){
                strPaymentIds=String.valueOf(paymentId);
            }
            else{
                strPaymentIds=strPaymentIds+","+String.valueOf(paymentId);
            }
        }

        List<Payment> paymentList = new ArrayList<>();
        if(!strPaymentIds.equals("")) {
            String searchQuery = "select *\n" +
                    "from payment where id in (" + strPaymentIds + ")";

            Query query = entityManager.createNativeQuery(searchQuery, Payment.class);

            paymentList = query.getResultList();
        }
        List<PaymentModel> paymentModelList=new ArrayList<>();
        for(Payment payment:paymentList){
            PaymentModel model=new PaymentModel();

            Payment p=paymentService.getById(payment.getId());

            model.setId(p.getId());
            model.setPaymentDate(p.getPaymentDate());
            model.setTotalAmount(p.getTotalAmount());
            model.setPrincipal(p.getPrincipal());
            model.setInterest(p.getInterest());
            model.setPenalty(p.getPenalty());
            model.setFee(p.getFee());
            model.setExchange_rate(p.getExchange_rate());
            model.setNumber(p.getNumber());
            model.setIn_loan_currency(p.isIn_loan_currency());
            model.setDetails(p.getDetails());
            model.setPaymentTypeId((p.getPaymentType() == null) ? 0 :p.getPaymentType().getId());
            model.setPaymentTypeName((p.getPaymentType() == null) ? "" :p.getPaymentType().getName());
            model.setRecord_status(p.getRecord_status());

            paymentModelList.add(model);
        }

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();

        String result = gson.toJson(paymentModelList);
        return result;
    }

    @PostMapping("/secondaryPayments/{assetId}")
    @ResponseBody
    public String apiGetAssetSecondaryPaymentsByAssetId(@PathVariable("assetId") Long assetId){

        Asset asset=assetService.getById(assetId);
        String strPaymentIds="";
        for (Long paymentId:asset.getSecondaryPaymentIds()){
            if(strPaymentIds.equals("")){
                strPaymentIds=String.valueOf(paymentId);
            }
            else{
                strPaymentIds=strPaymentIds+","+String.valueOf(paymentId);
            }
        }

        List<Payment> paymentList = new ArrayList<>();
        if(!strPaymentIds.equals("")) {
            String searchQuery = "select *\n" +
                    "from payment where id in (" + strPaymentIds + ")";

            Query query = entityManager.createNativeQuery(searchQuery, Payment.class);

            paymentList = query.getResultList();
        }
        List<PaymentModel> paymentModelList=new ArrayList<>();
        for(Payment payment:paymentList){
            PaymentModel model=new PaymentModel();

            Payment p=paymentService.getById(payment.getId());

            model.setId(p.getId());
            model.setPaymentDate(p.getPaymentDate());
            model.setTotalAmount(p.getTotalAmount());
            model.setPrincipal(p.getPrincipal());
            model.setInterest(p.getInterest());
            model.setPenalty(p.getPenalty());
            model.setFee(p.getFee());
            model.setExchange_rate(p.getExchange_rate());
            model.setNumber(p.getNumber());
            model.setIn_loan_currency(p.isIn_loan_currency());
            model.setDetails(p.getDetails());
            model.setPaymentTypeId((p.getPaymentType() == null) ? 0 :p.getPaymentType().getId());
            model.setPaymentTypeName((p.getPaymentType() == null) ? "" :p.getPaymentType().getName());
            model.setRecord_status(p.getRecord_status());

            paymentModelList.add(model);
        }

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();

        String result = gson.toJson(paymentModelList);
        return result;
    }


    @PostMapping("/toLoans/{assetId}")
    @ResponseBody
    public String getToLoansOfAsset(@PathVariable("assetId") Long assetId){

        Asset asset=assetService.getById(assetId);
        String loanIds="";
        for (Long id:asset.getLoanIds()){
            if (loanIds.equals("")){
                loanIds=String.valueOf(id);
            }
            else{
                loanIds=loanIds+","+id;
            }
        }

        List<LoanModel2> loans = new ArrayList<>();

        if(!loanIds.equals("")) {

            String baseQuery = "SELECT loan.id, loan.loan_class_id, loan.regNumber, loan.regDate, loan.amount, loan.currencyId, currency.name as currencyName,\n" +
                    "  loan.loanTypeId, type.name as loanTypeName, loan.loanStateId, state.name as loanStateName,\n" +
                    "  loan.supervisorId, IFNULL(loan.parent_id, 0) as parentLoanId, loan.creditOrderId,\n" +
                    "  d.id as debtorId,d.name as debtorName\n" +
                    "FROM loan loan, orderTermCurrency currency, loanType type, loanState state,debtor d\n" +
                    "WHERE loan.currencyId = currency.id\n" +
                    "  AND loan.loanTypeId = type.id\n" +
                    "  AND loan.loanStateId = state.id and loan.loanStateId = 2 \n" +
                    "  AND loan.id in (" + loanIds + ")\n" +
                    "  AND  loan.parent_id is null \n" +
                    "  AND  loan.debtorId=d.id \n" +
                    "ORDER BY  loan.regDate DESC";

            Query query = entityManager.createNativeQuery(baseQuery, LoanModel2.class);

            loans = query.getResultList();
        }

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(loans);

        return result;

    }


    @PostMapping("/fromLoans/{assetId}")
    @ResponseBody
    public String getFromLoansOfAsset(@PathVariable("assetId") Long assetId){

        Asset asset=assetService.getById(assetId);
        String loanIds="";
        for (Long id:asset.getFromLoanIds()){
            if (loanIds.equals("")){
                loanIds=String.valueOf(id);
            }
            else{
                loanIds=loanIds+","+id;
            }
        }
        List<LoanModel2> loans = new ArrayList<>();

        if(!loanIds.equals("")){
            String baseQuery = "SELECT loan.id, loan.loan_class_id, loan.regNumber, loan.regDate, loan.amount, loan.currencyId, currency.name as currencyName,\n" +
                    "  loan.loanTypeId, type.name as loanTypeName, loan.loanStateId, state.name as loanStateName,\n" +
                    "  loan.supervisorId, IFNULL(loan.parent_id, 0) as parentLoanId, loan.creditOrderId,\n" +
                    "  d.id as debtorId,d.name as debtorName\n" +
                    "FROM loan loan, orderTermCurrency currency, loanType type, loanState state,debtor d\n" +
                    "WHERE loan.currencyId = currency.id\n" +
                    "  AND loan.loanTypeId = type.id\n" +
                    "  AND loan.loanStateId = state.id and loan.loanStateId = 2 \n" +
                    "  AND loan.id in (" + loanIds + ")\n" +
                    "  AND  loan.parent_id is null \n" +
                    "  AND  loan.debtorId=d.id \n" +
                    "ORDER BY  loan.regDate DESC";

            Query query = entityManager.createNativeQuery(baseQuery, LoanModel2.class);

            loans = query.getResultList();
        }

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(loans);

        return result;

    }


    //******************************************************************************************************************
    //SIMPLE FUNCTIONS
    //******************************************************************************************************************

    public void assetAmount12Saver(Asset asset){

        String getSumOfPayments="select sum(p.totalAmount)\n" +
                "from payment p where p.id in (select aP.paymentIds from Asset_paymentIds aP where Asset_id="+asset.getId()+")";
        Double paymentsTotal= Double.valueOf(String.valueOf(entityManager.createNativeQuery(getSumOfPayments).getSingleResult()));
        asset.setAmount1(paymentsTotal);

        String getSumOfFromLoans = "select sum(l.amount)\n" +
                "from loan l where l.id in (select aL.fromLoanIds from Asset_fromLoanIds aL where Asset_id="+asset.getId()+")";
        Double fromLoansTotal= Double.valueOf(String.valueOf(entityManager.createNativeQuery(getSumOfFromLoans).getSingleResult()));

        asset.setAmount1(paymentsTotal);
        asset.setAmount2(fromLoansTotal);
        assetService.update(asset);

    }

    public void assetAmount4Saver(Asset asset){

        String getSumOfSecondaryPayments="select sum(p.totalAmount)\n" +
                "from payment p where p.id in (select aSP.secondaryPaymentIds from Asset_secondaryPaymentIds aSP where Asset_id="+asset.getId()+")";
        Double secondaryPaymentsTotal= Double.valueOf(String.valueOf(entityManager.createNativeQuery(getSumOfSecondaryPayments).getSingleResult()));


        asset.setAmount4(secondaryPaymentsTotal);
        assetService.update(asset);

    }
}
