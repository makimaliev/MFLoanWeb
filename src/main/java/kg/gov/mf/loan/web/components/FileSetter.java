package kg.gov.mf.loan.web.components;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import kg.gov.mf.loan.admin.org.model.Address;
import kg.gov.mf.loan.admin.org.service.AddressService;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSetter {

    @Autowired
    LoanService loanService;

    @Autowired
    DebtorService debtorService;

    @Autowired
    AddressService addressService;

    @Autowired
    InformationService informationService;

    @Autowired
    SystemFileService systemFileService;

    @Autowired
    AttachmentService attachmentService;

    public List<String> errorss=new ArrayList<>();

    public void setFilesToDirectories() throws DocumentException {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        String smtPath=SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "C:/temp/";
        String mainPath=smtPath+"/docs";



        try (Stream<Path> walk = Files.walk(Paths.get(mainPath))) {

            List<String> result = walk.filter(Files::isDirectory)
                    .map(x -> x.toString()).collect(Collectors.toList());

            for(String path:result){
                String[] splitted=path.split("/");
                int loo=splitted.length;
                if(loo==8){
                    try (Stream<Path> walk1 = Files.walk(Paths.get(path))) {

                        List<String> result1 = walk1.filter(Files::isRegularFile)
                                .map(x -> x.toString()).collect(Collectors.toList());

                        for(String fileName:result1){
                            String pathOS =  SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "C:/temp/";
                            File file = new File(fileName);
                            System.out.println();
                            long loanId;
                            Loan loan;
                            try{
                                String firstPart=file.getName().split("=")[0];
//                                if(firstPart.contains("-")){
//                                    firstPart=firstPart.split("-")[0];
//                                }
//                                else{
//                                    firstPart=firstPart.replaceAll("[^\\d]", "" );
//                                }

                                loanId=Long.valueOf(firstPart);

                                loan=loanService.getById(loanService.getByVersion(loanId).getId());
                            }
                            catch (Exception e){
                                errorss.add(file.getName());
                                break;
                            }
                            Debtor debtor=debtorService.getById(loan.getDebtor().getId());
                            Address address =addressService.findById(debtor.getAddress_id());
                            Long regionId=address.getRegion().getId();
                            Long districtId=address.getDistrict().getId();
                            File folder = new File(pathOS);
                            pathOS=pathOS+regionId+"/";
                            folder=new File(pathOS);
                            boolean exists = folder.exists();
                            if(!exists){
                                folder.mkdir();
                            }
                            pathOS=pathOS+districtId+"/";
                            folder=new File(pathOS);
                            exists = folder.exists();
                            if(!exists){
                                folder.mkdir();
                            }
                            pathOS=pathOS+debtor.getId()+"/";
                            folder=new File(pathOS);
                            exists = folder.exists();
                            if(!exists){
                                folder.mkdir();
                            }
                            pathOS=pathOS+loanId+"/";
                            folder=new File(pathOS);
                            exists = folder.exists();
                            if(!exists){
                                folder.mkdir();
                            }
                            pathOS=pathOS+4+"/";
                            folder=new File(pathOS);
                            exists = folder.exists();
                            if(!exists){
                                folder.mkdir();
                            }
                            File file1=new File(pathOS+file.getName());

                            saveAttachments(loan.getId(),file1);
                            Document document = new Document();
                            PdfCopy copy = new PdfCopy(document, new FileOutputStream(file1));
                            document.open();
                            PdfReader reader = new PdfReader(fileName);
                            for (int i = 1; i <= reader.getNumberOfPages(); i++){
                                // optionally write an if statement to include the page
                                copy.addPage(copy.getImportedPage(reader, i));
                            }
                            copy.freeReader(reader);
                            reader.close();
                            document.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(errorss);
    }
    private void saveAttachments(Long loanId,File file) {
        Information information;
        List<Information> informationList=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(4,loanId);
        if(informationList.size()>0){
            information=informationList.get(0);
        }
        else{
            information=new Information();
            information.setDate(new Date());
            information.setParentInformation(null);
            information.setSystemObjectId(loanId);
            information.setSystemObjectTypeId(4);
            information.setName(loanId+"info");
            informationService.create(information);
        }

        Attachment attachment = new Attachment();
        try {
                SystemFile systemFile=new SystemFile();


                String filename =file.getName() ;

                systemFile.setName(filename);
                systemFile.setPath(file.getPath());

                attachment.setName(filename);
                attachment.setInformation(information);


                systemFileService.create(systemFile);
                systemFile.setAttachment(attachment);
                attachmentService.create(attachment);
                systemFileService.edit(systemFile);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}

