package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.manage.model.collateral.GuarantorAgreement;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.collateral.GuarantorAgreementService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.web.fetchModels.GuarantorAgreementModel;
import kg.gov.mf.loan.web.fetchModels.GuarantorMetaModel;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class GuarantorAgreementController {

    @Autowired
    DebtorService debtorService;

    @Autowired
    LoanService loanService;

    @Autowired
    GuarantorAgreementService guarantorAgreementService;

    @Autowired
    OwnerService ownerService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    InformationService informationService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    SystemFileService systemFileService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = {"/manage/guarantoragreement/list"},method = RequestMethod.GET)
    public String list(ModelMap model){

        return "/manage/debtor/loan/guarantoragreement/list";

    }

    @RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/guarantoragreement/{agreementId}/view"})
    public String viewCollateralItem(ModelMap model,
                                     @PathVariable("debtorId")Long debtorId,
                                     @PathVariable("loanId")Long loanId,
                                     @PathVariable("agreementId")Long agreementId) {

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonSysFiles= gson.toJson(getSystemFilesByArrestFreeId(agreementId));
        model.addAttribute("files", jsonSysFiles);


        Attachment attachment=new Attachment();
        model.addAttribute("attachment",attachment);

        GuarantorAgreement agreement=guarantorAgreementService.getById(agreementId);
        model.addAttribute("agreement", agreement);
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("agreementloanId", loanId);



        return "/manage/debtor/loan/guarantoragreement/view";

    }

    @RequestMapping("/manage/debtor/{debtorId}/loan/{loanId}/guarantoragreement/{id}/save")
    public String saveGet(ModelMap model, @PathVariable("debtorId") Long debtorId,@PathVariable("loanId") Long loanId,@PathVariable("id") Long id){

        Debtor debtor = debtorService.getById(debtorId);
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtor);

        Loan loan=loanService.getById(loanId);
        model.addAttribute("loan",loan);
        List<Loan> loans=new ArrayList<>();
        for(Loan l:debtor.getLoans()){
            if(l.getParent()==null){
                loans.add(l);
            }
        }
        model.addAttribute("tLoans", loans);

        if(id==0){
            ArrayList<Long> loanIds=new ArrayList<>();
            GuarantorAgreement guarantorAgreement=new GuarantorAgreement();
            guarantorAgreement.setNotaryOfficeRegDate(new Date());
            model.addAttribute("guarantorAgreement",guarantorAgreement);
            model.addAttribute("ownerText", "");
            model.addAttribute("notaryText", "");

            model.addAttribute("loanIds", loanIds);
        }
        else if(id>0){
            ArrayList<Long> loanIds=new ArrayList<>();
            GuarantorAgreement guarantorAgreement=guarantorAgreementService.getById(id);
            model.addAttribute("guarantorAgreement",guarantorAgreement);
            Owner notary = guarantorAgreement.getNotary();
            Owner owner = guarantorAgreement.getOwner();
            for(Loan loan1:guarantorAgreement.getLoans()){
                loanIds.add(loan1.getId());
            }
            model.addAttribute("loanIds",loanIds);
            String ownerText = "";
            String notaryText = "";

            if(owner!=null)
            {
                ownerText = "[" + owner.getId() + "] "
                        + owner.getName()
                        + " (" + (owner.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
            }
            if(notary!=null)
            {
                notaryText = "[" + notary.getId() + "] "
                        + notary.getName()
                        + " (" + (notary.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
            }
            model.addAttribute("ownerText", ownerText);
            model.addAttribute("notaryText", notaryText);
        }

        return "manage/debtor/loan/guarantoragreement/save";
    }

    @PostMapping("/manage/debtor/{debtorId}/loan/{loanId}/guarantoragreement/save")
    public String savePost(@PathVariable("debtorId") Long debtorId,@PathVariable("loanId") Long loanId,String[] loanses,GuarantorAgreement guarantorAgreement){

        Set<Loan> loanSet = new HashSet<>();
        for (String l : loanses) {
            loanSet.add(loanService.getById(Long.valueOf(l)));
        }

        if(guarantorAgreement.getId()==0){
            Owner owner = ownerService.getById(guarantorAgreement.getOwner().getId());
            guarantorAgreement.setOwner(owner);
            guarantorAgreement.setLoans(loanSet);
            guarantorAgreementService.add(guarantorAgreement);
        }
        else if(guarantorAgreement.getId()>0){
            Owner owner = ownerService.getById(guarantorAgreement.getOwner().getId());
            guarantorAgreement.setOwner(owner);
            guarantorAgreement.setLoans(loanSet);
            guarantorAgreementService.update(guarantorAgreement);
        }

        return "redirect: /manage/debtor/{debtorId}/loan/{loanId}/view";
    }

    @PostMapping("/api/guarantoragreements")
    @ResponseBody
    public GuarantorMetaModel getListOfGuarantorAgreements(@RequestParam Map<String, String> datatable){

        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        String searchDebtor="";
        String searchOwner="";

        boolean searchByDebtor = datatable.containsKey("datatable[query][debtor]");
        if(searchByDebtor){
            String debtorStr = searchByDebtor?datatable.get("datatable[query][debtor]"):null;
            searchDebtor="and d.name like \'%"+debtorStr+"%\' \n";
        }

        boolean searchByOwner = datatable.containsKey("datatable[query][owner]");
        if(searchByOwner){
            String ownerStr = searchByOwner?datatable.get("datatable[query][owner]"):null;
            searchDebtor="and o.name like \'%"+ownerStr+"%\' \n";
        }

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

        String baseQuery="select ga.id,o.name as owner,n.name as notary," +
                "ga.notaryOfficeRegDate,ga.notaryOfficeRegNumber,ga.record_status,d.name as debtor,d.id as debtorId,o.id as ownerId,o.ownerType \n" +
                "from guarantor_agreement ga,loanGuarantorAgreement lga,owner o,owner n,debtor d,loan l where d.id=l.debtorId " +
                "and l.id=lga.loanId and ga.id=lga.guarantorAgreementId and o.id=ga.ownerId and n.id=ga.notary "+
                searchDebtor+searchOwner+
                "order by " + sortField + " " + sortStr + " LIMIT " + offset +"," + perPage;

        Query query=entityManager.createNativeQuery(baseQuery,GuarantorAgreementModel.class);
        List<GuarantorAgreementModel> guarantorAgreementModels=query.getResultList();

        String countQuery="select count(1)\n" +
                "from guarantor_agreement ga,loanGuarantorAgreement lga,owner o,owner n,debtor d,loan l where d.id=l.debtorId and l.id=lga.loanId and ga.id=lga.guarantorAgreementId and o.id=ga.ownerId and n.id=ga.notary "+
                searchDebtor+searchOwner;
        BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);
        Meta meta = new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

        GuarantorMetaModel metaModel=new GuarantorMetaModel();
        metaModel.setMeta(meta);
        metaModel.setData(guarantorAgreementModels);



        return metaModel;
    }

    //    add information form
    @RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/guarantoragreement/{agreementId}/addInformation"})
    public String getAddInformationForm(ModelMap model,
                                     @PathVariable("debtorId")Long debtorId,
                                     @PathVariable("loanId")Long loanId,
                                     @PathVariable("agreementId")Long agreementId) {

        String ids="";
        ids=ids+"debtorId:"+debtorId+",";
        ids=ids+"loanId:"+loanId;
        model.addAttribute("ids",ids);

        GuarantorAgreement agreement=guarantorAgreementService.getById(agreementId);
        model.addAttribute("object",agreement);
        model.addAttribute("systemObjectTypeId",10);

        model.addAttribute("attachment",new Attachment());

        return "/manage/debtor/loan/payment/addInformationForm";

    }

    private List<SystemFileModel> getSystemFilesByArrestFreeId(Long id){

        List<SystemFileModel> list=new ArrayList<>();
        List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(10,id);
        for (Information information1:informations){
            Information information=informationService.findById(information1.getId());
            Set<Attachment> attachments= information.getAttachment();
            for (Attachment attachment1:attachments){
                Attachment attachment=attachmentService.findById(attachment1.getId());
                for (SystemFile systemFile1:attachment.getSystemFile()){
                    SystemFile systemFile=systemFileService.findById(systemFile1.getId());
                    SystemFileModel systemFileModel=new SystemFileModel();
                    systemFileModel.setAttachment_id(attachment.getId());
                    systemFileModel.setSys_name(systemFile.getName());
                    systemFileModel.setSystem_file_id(systemFile.getId());
                    systemFileModel.setAttachment_name(attachment.getName());
                    systemFileModel.setPath(systemFile.getPath());
                    list.add(systemFileModel);
                }
            }
        }

        return list;
    }
}
