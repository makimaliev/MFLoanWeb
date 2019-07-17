package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collateral.CollateralItemArrestFree;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemArrestFreeService;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class CollateralArrestFreeController {

    @Autowired
    CollateralItemArrestFreeService collateralItemArrestFreeService;

    @Autowired
    InformationService informationService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    SystemFileService systemFileService;

    @Autowired
    UserService userService;

    @Autowired
    StaffService staffService;

    @RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/collateralarrestfree/{freeId}/view"})
    public String viewCollateralItem(ModelMap model,
                                     @PathVariable("debtorId")Long debtorId,
                                     @PathVariable("agreementId")Long agreementId,
                                     @PathVariable("itemId")Long itemId,
                                     @PathVariable("freeId")Long freeId) {

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonSysFiles= gson.toJson(getSystemFilesByArrestFreeId(freeId));
        model.addAttribute("files", jsonSysFiles);


        Attachment attachment=new Attachment();
        model.addAttribute("attachment",attachment);

        CollateralItemArrestFree arrestFree=collateralItemArrestFreeService.getById(freeId);
        model.addAttribute("arrestFree", arrestFree);
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("agreementId", agreementId);
        model.addAttribute("itemId", itemId);

        String createdByStr=null;
        String modifiedByStr=null;

        if(arrestFree.getAuCreatedBy()!=null){
            if(arrestFree.getAuCreatedBy().equals("admin")){
                createdByStr="Система";
            }
            else{
                User createdByUser=userService.findByUsername(arrestFree.getAuCreatedBy());
                Staff createdByStaff=createdByUser.getStaff();
                createdByStr=createdByStaff.getName();
            }
        }
        if(arrestFree.getAuLastModifiedBy()!=null){
            if(arrestFree.getAuLastModifiedBy().equals("admin")){
                modifiedByStr="Система";
            }
            else{
                User lastModifiedByUser=userService.findByUsername(arrestFree.getAuLastModifiedBy());
                Staff lastModifiedByStaff=lastModifiedByUser.getStaff();
                modifiedByStr=lastModifiedByStaff.getName();
            }
        }
        model.addAttribute("createdBy",createdByStr);
        model.addAttribute("modifiedBy",modifiedByStr);



        return "/manage/debtor/collateralagreement/collateralitem/arrestfree/view";

    }


    //    add information form
    @RequestMapping("/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/collateralarrestfree/{freeId}/addInformation")
    public String getAddInformationForm(Model model, @PathVariable("debtorId") Long debtorId,
                                        @PathVariable("agreementId") Long agreementId,
                                        @PathVariable("itemId") Long itemId,
                                        @PathVariable("freeId") Long freeId){

        String ids="";
        ids=ids+"debtorId:"+debtorId+",";
        ids=ids+"agreementId:"+agreementId+",";
        ids=ids+"itemId:"+itemId;
        model.addAttribute("ids",ids);

        CollateralItemArrestFree arrestFree=collateralItemArrestFreeService.getById(freeId);
        model.addAttribute("object",arrestFree);
        model.addAttribute("systemObjectTypeId",9);

        model.addAttribute("attachment",new Attachment());

        return "/manage/debtor/loan/payment/addInformationForm";

    }

    private List<SystemFileModel> getSystemFilesByArrestFreeId(Long id){

        List<SystemFileModel> list=new ArrayList<>();
        List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(9,id);
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
