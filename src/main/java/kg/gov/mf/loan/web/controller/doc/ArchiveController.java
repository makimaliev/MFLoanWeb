package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Archive;
import kg.gov.mf.loan.doc.model.Attachment;
import kg.gov.mf.loan.doc.service.ArchiveService;
import kg.gov.mf.loan.doc.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/doc/archive")
public class ArchiveController extends BaseController {

    private ArchiveService archiveService;
    private CounterService registerService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @Autowired
    public ArchiveController(ArchiveService archiveService, CounterService registerService) {
        this.archiveService = archiveService;
        this.registerService = registerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("archives", archiveService.findAllWD());
        return "/doc/archive/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String add(Model model) {

        if(getUser() == null)
            return "/login/login";

        Archive archive = new Archive();
        archive.setRegisteredDate(new Date());
        //archive.setRegisteredNumber(registerService.generateRegistrationNumber());
        model.addAttribute("archive", archive);

        return "/doc/archive/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null)
            return "/login/login";

        model.addAttribute("archive", archiveService.findByIdWD(id));
        return "/doc/archive/edit";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {

        model.addAttribute("archive", archiveService.findByIdWD(id));
        return "/doc/archive/view";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("archive") Archive archive, @RequestParam("attachmentFiles") MultipartFile[] attachments) throws IOException {

        //region ATTACHMENTS
        if(attachments.length != 0) {
            for (MultipartFile attachment : attachments) {
                String fileName = attachment.getOriginalFilename();
                if(fileName.length() > 1) {

                    String uuid = UUID.randomUUID().toString();
                    String fsname = uuid + ".atach";

                    File file = new File(path + fsname);
                    attachment.transferTo(file);

                    Attachment a = new Attachment();
                    a.setName(attachment.getOriginalFilename());
                    a.setInternalName(fsname);
                    a.setMimeType(attachment.getContentType());
                    archive.getAttachments().add(a);
                }
            }
        }
        //endregion

        /*
        if(archive.getId() == null)
        {
            Archive arc = archiveService.findById(archive.getId());
            archive.setAttachments(arc.getAttachments());
            archiveService.save(archive);
        }
        else
        {
            archiveService.update(archive);
        }
        */
        archiveService.add(archive);

        return "redirect:/doc/archive/index";
    }

}
