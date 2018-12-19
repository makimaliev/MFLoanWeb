package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Attachment;
import kg.gov.mf.loan.doc.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    public AttachmentsController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity upload(MultipartHttpServletRequest request) {

        Attachment attachment = new Attachment();

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

                attachmentService.add(attachment);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(attachment, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(attachment, HttpStatus.OK);
    }

    @RequestMapping(value = "/download/{attachmentId}", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @PathVariable("attachmentId") Long attachmentId) throws IOException {

        Attachment attachment = attachmentService.getById(attachmentId);

        File file = new File(path + attachment.getInternalName());
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());

        if(mimeType==null){
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + attachment.getName() +"\""));
        response.setContentLength((int)file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping(value = "/delete/{attachmentId}")
    public void delete(@PathVariable("attachmentId") Long attachmentId) {

        Attachment attachment = attachmentService.getById(attachmentId);
        File file = new File(path + attachment.getInternalName());
        file.delete();

        attachmentService.remove(attachment);
    }
}
