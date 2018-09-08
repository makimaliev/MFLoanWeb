package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.*;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.WorkSectorService;
import kg.gov.mf.loan.manage.service.loan.LoanTypeService;
import kg.gov.mf.loan.output.report.model.GroupType;
import kg.gov.mf.loan.output.report.model.ObjectList;
import kg.gov.mf.loan.output.report.model.ObjectListValue;
import kg.gov.mf.loan.output.report.model.ReferenceView;
import kg.gov.mf.loan.output.report.service.GroupTypeService;
import kg.gov.mf.loan.output.report.service.ObjectListService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.output.report.service.ReportTemplateService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.omg.CORBA.ORB;
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
public class ObjectListController {
	
	@Autowired
    private ObjectListService objectListService;

	@Autowired
    private RegionService regionService;

	@Autowired
    private DistrictService districtService;

	@Autowired
    private WorkSectorService workSectorService;

	@Autowired
	private LoanTypeService loanTypeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private StaffService staffService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private GroupTypeService groupTypeService;

	@Autowired
	ReferenceViewService referenceViewService;

   public void setObjectListService(ObjectListService rs)
    {
        this.objectListService = rs;
    }

	@RequestMapping(value = "/objectList/list", method = RequestMethod.GET)
	public String listObjectLists(Model model) {
		model.addAttribute("objectList", new ObjectList());
		model.addAttribute("objectListList", this.objectListService.findAll());

		return "output/report/objectListList";
	}
	
	@RequestMapping("/objectList/{id}/view")
	public String viewObjectListById(@PathVariable("id") long id, Model model) {

		ObjectList objectList = this.objectListService.findById(id);
		
		ObjectList copyObjectList = new ObjectList();


		ReportTool reportTool = new ReportTool();

		List<ReferenceView> referenceViewList = referenceViewService.findByParameter(reportTool.getMapNameOfGroupType(objectList.getGroupType()));

		for(ObjectListValue objectListValue : objectList.getObjectListValues())
		{
			for (ReferenceView referenceView: referenceViewList)
			{
				if(referenceView.getId()==Long.parseLong(objectListValue.getName()))
				{
					copyObjectList.getObjectListValues().add(new ObjectListValue(objectListValue.getId(),referenceView.getName(),objectListValue.getObjectList()));
				}

			}
		}
		model.addAttribute("objectList", copyObjectList);

		return "output/report/objectListView";
	}
	
	@RequestMapping("/objectList/{id}/details")
	public String viewObjectListDetailsById(@PathVariable("id") long id, Model model) {

		ObjectList objectList = this.objectListService.findById(id);

		model.addAttribute("objectList", objectList);

		return "output/report/objectListDetails";
		}	
    
	
	@RequestMapping(value = "/objectList/add", method = RequestMethod.GET)
	public String getObjectListAddForm(Model model) {

		model.addAttribute("objectList", new ObjectList());

		return "output/report/objectListForm";
	}

	@RequestMapping(value = "/{object_type}/objectList/add", method = RequestMethod.GET)
	public String getObjectListAddForm(@PathVariable("object_type") String object_type,Model model) {

		
		ObjectList modelObjectList = new ObjectList();
		modelObjectList.setObjectTypeId(Long.parseLong(object_type));

		GroupType groupType = groupTypeService.findById(Long.valueOf(object_type));

		modelObjectList.setGroupType(groupType);

		model.addAttribute("objectList", modelObjectList);

		Organization gaubk = new Organization();

		ReportTool reportTool = new ReportTool();

		model.addAttribute("objectListValueList", referenceViewService.findByParameter(reportTool.getMapNameOfGroupType(groupType)));


		
		List<Long> selectedObjectListValuesIds = new ArrayList<Long>();
		
		for (ObjectListValue objectListValue: modelObjectList.getObjectListValues())
		{
			selectedObjectListValuesIds.add(Long.parseLong(objectListValue.getName()));
		}
		
		model.addAttribute("selectedObjectListValues", selectedObjectListValuesIds);

		model.addAttribute("selectedObjectList", modelObjectList.getObjectListValues());

		return "output/report/objectListForm";
	}

	@RequestMapping(value = "/objectList/{id}/edit", method = RequestMethod.GET)
	public String getObjectListEditForm(@PathVariable("id") long id, Model model) {

		Organization gaubk = new Organization();
		
		model.addAttribute("objectList", this.objectListService.findById(id));
		
		
		
		ObjectList modelObjectList = this.objectListService.findById(id);

        ReportTool reportTool = new ReportTool();

        model.addAttribute("objectListValueList", referenceViewService.findByParameter(reportTool.getMapNameOfGroupType(modelObjectList.getGroupType())));


		
		List<Long> selectedObjectListValuesIds = new ArrayList<Long>();
		
		for (ObjectListValue objectListValue: modelObjectList.getObjectListValues())
		{
			selectedObjectListValuesIds.add(Long.parseLong(objectListValue.getName()));
		}
		
		model.addAttribute("selectedObjectListValues", selectedObjectListValuesIds);

		model.addAttribute("selectedObjectList", modelObjectList.getObjectListValues());
		
		
		return "output/report/objectListForm";

	}

	@RequestMapping(value = "/objectList/save", method = RequestMethod.POST)
	public String saveObjectListAndRedirectToObjectListList(@Validated @ModelAttribute("objectList") ObjectList objectList, 
			String[] ObjectListValuesIds,BindingResult result) {


   		String groupTypeId = "";
		if (result.hasErrors()) 
		{
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} 
		else 
		if (objectList.getId() == 0) {
			if(ObjectListValuesIds!=null)
				for (String id: ObjectListValuesIds)
				{
					objectList.getObjectListValues().add(new ObjectListValue(id, objectList));
				}
			this.objectListService.create(objectList);

			groupTypeId = String.valueOf(objectList.getGroupType().getId());
		}
		else 
		{
			ObjectList modelObjectList = this.objectListService.findById(objectList.getId());

			modelObjectList.getObjectListValues().clear();
			modelObjectList.setName(objectList.getName());
			modelObjectList.setObjectTypeId(objectList.getObjectTypeId());
			

			if(ObjectListValuesIds!=null)
				for (String id: ObjectListValuesIds)
				{
					modelObjectList.getObjectListValues().add(new ObjectListValue(id, objectList));
				}

			this.objectListService.edit(modelObjectList);

			groupTypeId = String.valueOf(modelObjectList.getGroupType().getId());

		}

//		return "redirect:/objectList/list";
		return "redirect:/groupType/"+groupTypeId+"/details";

	}

	@RequestMapping("/objectList/{id}/remove")
	public String removeObjectListAndRedirectToObjectListList(@PathVariable("id") long id) {

		this.objectListService.deleteById(id);

		return "redirect:/objectList/list";
	}

     

     

}
