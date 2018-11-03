package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Attachment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@Controller
@RequestMapping("/doc/attachments")
public class AttachmentsController extends BaseController {

    @RequestMapping(value = "/download/{attachmentId}", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @PathVariable("attachmentId") Long attachmentId) throws IOException {

        Attachment attachment = attachmentService.getById(attachmentId);

        File file = new File(path + attachment.getInternalName());
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());

        if(mimeType==null){
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + attachment.getName() +"\""));
        response.setContentLength((int)file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping(value = "/delete/{attachmentId}", method = RequestMethod.GET)
    public void delete(@PathVariable("attachmentId") Long attachmentId) {

        Attachment attachment = attachmentService.getById(attachmentId);
        File file = new File(path + attachment.getInternalName());
        file.delete();

        attachmentService.remove(attachment);
    }
}
