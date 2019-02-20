package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.doc.model.Attachment;
import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.doc.service.AttachmentService;
import kg.gov.mf.loan.doc.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.UUID;

@Controller
@RequestMapping("/doc/attachments")
public class AttachmentsController extends BaseController {

    private AttachmentService attachmentService;
    private UserService userService;
    private DocumentService documentService;

    @Autowired
    public AttachmentsController(AttachmentService attachmentService, UserService userService, DocumentService documentService) {
        this.attachmentService = attachmentService;
        this.userService = userService;
        this.documentService = documentService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity upload(MultipartHttpServletRequest request,
                                 @RequestParam(value = "documentId", required = false) Long documentId) {

        Attachment attachment = new Attachment();
        User user = userService.findById(getUser().getId());

        try {
            Iterator<String> itr = request.getFileNames();

            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile part = request.getFile(uploadedFile);

                String mimeType = part.getContentType();
                String filename = part.getOriginalFilename();

                String uuid = UUID.randomUUID().toString();
                String fsname = uuid + ".atach";

                File file = new File(path + fsname);
                part.transferTo(file);

                attachment.setName(filename);
                attachment.setInternalName(fsname);
                attachment.setMimeType(mimeType);

                attachment.setAuthor(user.getId());

                attachmentService.add(attachment);

                if(documentId != null)
                {
                    Document document = documentService.getById(documentId);
                    document.getAttachments().add(attachment);
                    documentService.update(document);
                }

            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(attachment, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(attachment, HttpStatus.OK);
    }

    @RequestMapping(value = "/download/{attachmentId}", method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response, @PathVariable("attachmentId") Long attachmentId) throws IOException {

        Attachment attachment = attachmentService.getById(attachmentId);

        File file = new File(path + attachment.getInternalName());
        String mimeType = attachment.getMimeType() != null && !attachment.getMimeType().isEmpty()
                ? attachment.getMimeType()
                : "application/octet-stream";

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", attachment.getName()));
        response.setContentLength((int)file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping(value = "/delete/{attachmentId}", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable("attachmentId") Long attachmentId) {

        Attachment attachment = attachmentService.getById(attachmentId);
        File file = new File(path + attachment.getInternalName());
        file.delete();

        attachmentService.remove(attachment);
        return "OK";
    }
}
