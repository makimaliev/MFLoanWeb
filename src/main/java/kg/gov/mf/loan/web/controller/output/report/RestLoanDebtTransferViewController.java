package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.output.report.model.LoanDebtTransferView;
import kg.gov.mf.loan.output.report.service.LoanDebtTransferViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestLoanDebtTransferViewController {

    @Autowired
    LoanDebtTransferViewService loanDebtTransferViewService;


    @PostMapping("/loanDebtTransferViews")
    public List<LoanDebtTransferView> getAll(@RequestParam Map<String,String> datatable){

        LinkedHashMap<String,List<String>> parameters=new LinkedHashMap<>();


        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");

        boolean searchByDebtorName= datatable.containsKey("datatable[query][debtorName]");
        if(searchByDebtorName){
            String debtorName = datatable.get("datatable[query][debtorName]");
            List<String> searchWord= Arrays.asList(debtorName);
            parameters.put("r=ycv_debtor_name",searchWord);
        }

        boolean searchByNumber= datatable.containsKey("datatable[query][number]");
        if(searchByNumber){
            String number = datatable.get("datatable[query][number]");
            List<String> searchWord= Arrays.asList(number );
            parameters.put("r=ycv_dt_number",searchWord);
        }

        boolean searchByDistrict = datatable.containsKey("datatable[query][districtId]");
        if (searchByDistrict){
            String districtStr = datatable.get("datatable[query][districtId]");
            List<String> districtIds=Arrays.asList(districtStr);
            parameters.put("r=inv_debtor_district_id",districtIds);
        }

        boolean searchByRegion = datatable.containsKey("datatable[query][regionId]");
        if (searchByRegion){
            String regionStr = datatable.get("datatable[query][regionId]");
            List<String> regionIds=Arrays.asList(regionStr );
            parameters.put("r=inv_debtor_region_id",regionIds);
        }

        boolean getFromDate= datatable.containsKey("datatable[query][fromDater]");
        if (getFromDate){
            String fromDateStr = datatable.get("datatable[query][fromDater]");
            List<String> froms=Arrays.asList(fromDateStr);
            parameters.put("r=aov_dt_date",froms);
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDater]");
        if (getToDate){
            String toDateStr = datatable.get("datatable[query][toDater]");
            List<String> tos=Arrays.asList(toDateStr);
            parameters.put("r=bov_dt_date",tos);
        }

        boolean getFromAmount= datatable.containsKey("datatable[query][fromAmounter]");
        if (getFromAmount){
            String fromAmountStr = datatable.get("datatable[query][fromAmounter]");
            List<String> froms=Arrays.asList(fromAmountStr);
            parameters.put("r=gev_dt_totalCost",froms);
        }


        boolean getToAmount= datatable.containsKey("datatable[query][toAmounter]");
        if (getToAmount){
            String toAmountStr = datatable.get("datatable[query][toAmounter]");
            List<String> tos=Arrays.asList(toAmountStr);
            parameters.put("r=lev_dt_totalCost",tos);
        }

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

        List<LoanDebtTransferView> all=loanDebtTransferViewService.findByParameter(parameters,offset,perPage);

        return all;
    }


}
