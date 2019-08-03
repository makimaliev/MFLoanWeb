package kg.gov.mf.loan.web.controller.manage.asset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.asset.Asset;
import kg.gov.mf.loan.manage.model.asset.AssetItem;
import kg.gov.mf.loan.manage.model.asset.AssetStatus;
import kg.gov.mf.loan.manage.model.asset.AssetType;
import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.manage.service.asset.AssetItemService;
import kg.gov.mf.loan.manage.service.asset.AssetService;
import kg.gov.mf.loan.manage.service.asset.AssetStatusService;
import kg.gov.mf.loan.manage.service.asset.AssetTypeService;
import kg.gov.mf.loan.manage.service.loan.PaymentService;
import kg.gov.mf.loan.web.fetchModels.PaymentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
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
    AssetService assetService;

    @Autowired
    AssetTypeService assetTypeService;

    @Autowired
    AssetStatusService assetStatusService;

    @Autowired
    AssetItemService assetItemService;

    @Autowired
    PaymentService paymentService;

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

        List<Asset> assets=assetService.list();
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

        return "redirect:/asset/{assetId}/view";
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
        String searchQuery="select *\n" +
                "from payment where id in ("+strPaymentIds+")";

        Query query=entityManager.createNativeQuery(searchQuery, Payment.class);

        List<Payment> paymentList=query.getResultList();
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
}
