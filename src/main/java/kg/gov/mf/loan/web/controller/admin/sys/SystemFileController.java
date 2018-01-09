package kg.gov.mf.loan.web.controller.admin.sys;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kg.gov.mf.loan.admin.sys.model.*;
import kg.gov.mf.loan.admin.sys.service.*;

@Controller
public class SystemFileController {
	
	@Autowired
    private SystemFileService systemFileService;
	
    public void setSystemFileService(SystemFileService rs)
    {
        this.systemFileService = rs;
    }
    
	@Autowired
    private AttachmentService attachmentService;
	
    public void setAttachmentService(AttachmentService rs)
    {
        this.attachmentService = rs;
    }
    
    
  //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "c://temp//";
    
    
    
	@RequestMapping(value = "/systemFile/list", method = RequestMethod.GET)
	public String listSystemFiles(Model model) 
	{
		model.addAttribute("systemFile", new SystemFile());
		model.addAttribute("systemFileList", this.systemFileService.findAll());

		return "admin/sys/systemFileList";
	}
	
	@RequestMapping("/systemFile/{id}/view")
	public String viewSystemFileById(@PathVariable("id") long id, Model model) 
	{
		SystemFile systemFile = this.systemFileService.findById(id);

		model.addAttribute("systemFile", systemFile);

		return "admin/sys/systemFileView";
	}
	
	@RequestMapping("/systemFile/{id}/details")
	public void viewSystemFileDetailsById(@PathVariable("id") long id, HttpServletResponse response,Model model) throws IOException
	{
		SystemFile systemFile = this.systemFileService.findById(id);

		model.addAttribute("systemFile", systemFile);
		
		
		File file = new File(systemFile.getPath());
		
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
		 
		response.setContentLength((int)file.length());
		 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 

        FileCopyUtils.copy(inputStream, response.getOutputStream());
		
	}
	
	
	@RequestMapping(value = "/systemFile/add", method = RequestMethod.GET)
	public String getSystemFileAddForm(Model model) 
	{
		model.addAttribute("systemFile", new SystemFile());
		model.addAttribute("systemFileList", this.systemFileService.findAll());		

		return "admin/sys/systemFileForm";
	}
	

	@RequestMapping(value = "/attachment/{attachmentId}/systemFile/add", method = RequestMethod.GET)
	public String getSystemFileAddByAttachmentIdForm(@PathVariable("attachmentId") long attachmentId,Model model) {

		SystemFile modelSystemFile = new SystemFile();
		modelSystemFile.setAttachment(this.attachmentService.findById(attachmentId));
		model.addAttribute("systemFile",modelSystemFile);

		return "admin/sys/systemFileForm";
	}
	
	
	@RequestMapping("/systemFile/{id}/edit")
	public String getSystemFileEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("systemFile", this.systemFileService.findById(id));
		model.addAttribute("systemFileList", this.systemFileService.findAll());		
	
		
		return "admin/sys/systemFileForm";

	}

	@RequestMapping(value = "/systemFile/save", method = RequestMethod.POST)
	public ModelAndView saveSystemFileAndRedirectToInformationViewPage(@Validated @ModelAttribute("systemFile") SystemFile systemFile, BindingResult result,ModelMap model, @RequestParam("file") MultipartFile file) {

		
		if (result.hasErrors()) 
		{
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} 
		else if (systemFile.getId() == 0) 
		{
			
			
	        try {

	            
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);

	            systemFile.setPath(path.toString());
	            
	            this.systemFileService.create(systemFile);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        
		} 
		else 
		{
			this.systemFileService.edit(systemFile);
		}
		
		String url = "/attachment/"+systemFile.getAttachment().getId()+"/details";

        return new ModelAndView("redirect:"+url, model);

	}	
	

	@RequestMapping("/systemFile/{id}/remove")
	public ModelAndView removeSystemFileAndRedirectToSystemFileList(@PathVariable("id") long id,ModelMap model) {

		String url = "/attachment/"+this.systemFileService.findById(id).getAttachment().getId()+"/details";
		
		
		SystemFile systemFile = this.systemFileService.findById(id);

		model.addAttribute("systemFile", systemFile);
		
		
		File file = new File(systemFile.getPath());
		
		file.delete();
		
		this.systemFileService.deleteById(id);
		


        return new ModelAndView("redirect:"+url, model);
	}

}
