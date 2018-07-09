package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;

import kg.gov.mf.loan.manage.repository.orderterm.AgreementTemplateRepository;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.orderterm.AgreementTemplate;
import kg.gov.mf.loan.manage.model.orderterm.OrderTerm;
import kg.gov.mf.loan.manage.service.orderterm.AgreementTemplateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermService;

@Controller
public class AgreementTemplateController {
	
	@Autowired
	OrderTermService termService;
	
	@Autowired
	AgreementTemplateService templateService;

	@Autowired
	CreditOrderService orderService;

	@Autowired
	AgreementTemplateRepository templateRepository;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/{termId}/agreementtemplate/{templateId}/save", method=RequestMethod.GET)
	public String formeAppliedEntityList(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("termId")Long termId, @PathVariable("templateId")Long templateId)
	{
		if(templateId == 0)
		{
			model.addAttribute("template", new AgreementTemplate());
		}
			
		
		if(templateId > 0)
		{
			model.addAttribute("template", templateService.getById(templateId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
		model.addAttribute("termId", termId);
		model.addAttribute("term", termService.getById(termId));
			
		return "/manage/order/orderterm/agreementtemplate/save";
	}

	@RequestMapping(value="/manage/order/{orderId}/orderterm/{termId}/agreementtemplate/save", method=RequestMethod.POST)
	public String saveOrderDocument(
			AgreementTemplate template, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("termId")Long termId, 
			ModelMap model)
	{
		
		OrderTerm term = termService.getById(termId);
		template.setOrderTerm(term);
		
		if(template.getId() == 0)
			templateService.add(template);
		else
			templateService.update(template);
		
		return "redirect:" + "/manage/order/{orderId}/orderterm/{termId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/{termId}/agreementtemplate/{templateId}/delete", method=RequestMethod.GET)
    public String deleteOrderDocument(@PathVariable("orderId")Long orderId, @PathVariable("termId")Long termId,  @PathVariable("templateId")Long templateId) {
		if(templateId > 0) {
			templateRepository.delete(templateRepository.findOne(templateId));
		}
			
		return "redirect:" + "/manage/order/{orderId}/orderterm/{termId}/view";
    }

}
