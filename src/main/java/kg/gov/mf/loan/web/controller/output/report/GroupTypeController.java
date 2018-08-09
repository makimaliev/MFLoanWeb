package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.output.report.model.GroupType;
import kg.gov.mf.loan.output.report.model.ObjectList;
import kg.gov.mf.loan.output.report.service.GroupTypeService;
import kg.gov.mf.loan.output.report.service.ObjectListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GroupTypeController {
	
	@Autowired
    private GroupTypeService groupTypeService;

	@Autowired
	private ObjectListService objectListService;
	

   public void setGroupTypeService(GroupTypeService rs)
    {
        this.groupTypeService = rs;
    }

	@RequestMapping(value = "/groupType/list", method = RequestMethod.GET)
	public String listGroupTypes(Model model) {
		model.addAttribute("groupType", new GroupType());
		model.addAttribute("groupTypeList", this.groupTypeService.findAll());

		return "output/report/groupTypeList";
	}
	
	@RequestMapping("groupType/{id}/view")
	public String viewGroupTypeById(@PathVariable("id") long id, Model model) {

		GroupType groupType = this.groupTypeService.findById(id);

		model.addAttribute("groupType", groupType);

		model.addAttribute("objectListList", this.objectListService.findAllByGroupType(groupType));

		return "output/report/groupTypeView";
	}
	
	@RequestMapping("groupType/{id}/details")
	public String viewGroupTypeDetailsById(@PathVariable("id") long id, Model model) {

		GroupType groupType = this.groupTypeService.findById(id);

		model.addAttribute("groupType", groupType);

		return "output/report/groupTypeDetails";
	}	
    
	
	@RequestMapping(value = "/groupType/add", method = RequestMethod.GET)
	public String getGroupTypeAddForm(Model model) {

		model.addAttribute("groupType", new GroupType());

		return "output/report/groupTypeForm";
	}
	
	@RequestMapping("/groupType/{id}/edit")
	public String getGroupTypeEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("groupType", this.groupTypeService.findById(id));
		return "output/report/groupTypeForm";

	}

	@RequestMapping(value = "/groupType/save", method = RequestMethod.POST)
	public String saveGroupTypeAndRedirectToGroupTypeList(@Validated @ModelAttribute("groupType") GroupType groupType, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (groupType.getId() == 0) {
			this.groupTypeService.create(groupType);
		} else {
			this.groupTypeService.edit(groupType);
		}

		return "redirect:/groupType/list";

	}

	@RequestMapping("/groupType/{id}/remove")
	public String removeGroupTypeAndRedirectToGroupTypeList(@PathVariable("id") long id) {

		this.groupTypeService.deleteById(id);

		return "redirect:/groupType/list";
	}

     

     

}
