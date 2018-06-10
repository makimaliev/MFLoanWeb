package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Attachment;
import kg.gov.mf.loan.doc.service.AttachmentService;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Iterator;

@CrossOrigin
@RestController("restAttachmentController")
@RequestMapping("/attachments")
public class AttachmentController {

    private String path =  SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "C:/temp/";

    private AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity downloadFile(@RequestParam("id") Long id) {

        Attachment attachment = attachmentService.findById(id);

        if (attachment == null) {
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment; filename=" + attachment.getName());

        String primaryType, subType;
        try {
            primaryType = attachment.getMimeType().split("/")[0];
            subType = attachment.getMimeType().split("/")[1];
        }
        catch (IndexOutOfBoundsException | NullPointerException ex) {
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        File file = new File(path + attachment.getInternalName());

        headers.setContentType( new MediaType(primaryType, subType) );
        headers.setContentLength((int)file.length());

        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(inputStream, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity uploadFile(HttpServletRequest request) {

        try {

            String uuid = request.getParameterValues("uuid")[0];
            String side = request.getParameterValues("side")[0];
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            Iterator<String> itr = multipartRequest.getFileNames();

            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile file = multipartRequest.getFile(uploadedFile);
                String mimeType = file.getContentType();
                String filename = file.getOriginalFilename();
                byte[] bytes = file.getBytes();

                File f = new File(path + uuid + ".atach");
                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(f));
                buffStream.write(bytes);
                buffStream.close();

                Attachment attachment = new Attachment();
                attachment.setName(filename);
                attachment.setInternalName(uuid);
                attachment.setMimeType(mimeType);
                attachmentService.save(attachment);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
