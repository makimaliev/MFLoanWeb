package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.output.report.model.ObjectList;
import kg.gov.mf.loan.output.report.model.ObjectListValue;
import kg.gov.mf.loan.output.report.service.ObjectListService;
import kg.gov.mf.loan.output.report.service.ReportTemplateService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private DebtorService debtorService;
	
	

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

		for(ObjectListValue objectListValue : objectList.getObjectListValues())
		{
			switch (String.valueOf(objectListValue.getObjectList().getObjectTypeId()))
			{
				case "1": 
					copyObjectList.getObjectListValues().add(new ObjectListValue(objectListValue.getId(),this.regionService.findById(Long.parseLong(objectListValue.getName())).getName(),objectListValue.getObjectList()));
					break;
				case "2": 
					copyObjectList.getObjectListValues().add(new ObjectListValue(objectListValue.getId(),this.districtService.findById(Long.parseLong(objectListValue.getName())).getName(),objectListValue.getObjectList()));
					break;
				case "3":
					copyObjectList.getObjectListValues().add(new ObjectListValue(objectListValue.getId(),this.regionService.findById(Long.parseLong(objectListValue.getName())).getName(),objectListValue.getObjectList()));
					break;
				
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
		model.addAttribute("objectList", modelObjectList);
		
		switch (object_type)
		{
			case "1": 
				model.addAttribute("objectListValueList", this.regionService.findAll());
				break;
			case "2": 
				model.addAttribute("objectListValueList", this.districtService.findAll());
				break;
			case "3":
				model.addAttribute("objectListValueList", this.debtorService.list());
				break;
			
		}
	
		
		List<Long> selectedObjectListValuesIds = new ArrayList<Long>();
		
		for (ObjectListValue objectListValue: modelObjectList.getObjectListValues())
		{
			selectedObjectListValuesIds.add(Long.parseLong(objectListValue.getName()));
		}
		
		model.addAttribute("selectedObjectListValues", selectedObjectListValuesIds);

		model.addAttribute("selectedObjectList", modelObjectList.getObjectListValues());

		return "output/report/objectListForm";
	}

	
	@RequestMapping("/objectList/{id}/edit")
	public String getObjectListEditForm(@PathVariable("id") long id, Model model) {
		
		
		
		model.addAttribute("objectList", this.objectListService.findById(id));
		
		
		
		ObjectList modelObjectList = this.objectListService.findById(id);
		
	
		switch (String.valueOf(modelObjectList.getObjectTypeId()))
		{
			case "1": 
				model.addAttribute("objectListValueList", this.regionService.findAll());
				break;
			case "2": 
				model.addAttribute("objectListValueList", this.districtService.findAll());
				break;
			case "3":
				model.addAttribute("objectListValueList", this.debtorService.list());
				break;
			
		}
	
		
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

		
		
		
		if (objectList.getId() > 0)
		{

		}
		
		
		
		
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
		} 
		else 
		{
			ObjectList modelObjectList = this.objectListService.findById(objectList.getId());

			modelObjectList.getObjectListValues().clear();

			if(ObjectListValuesIds!=null)
				for (String id: ObjectListValuesIds)
				{
					modelObjectList.getObjectListValues().add(new ObjectListValue(id, objectList));
				}

			this.objectListService.edit(modelObjectList);
		}

		return "redirect:/objectList/list";

	}

	@RequestMapping("/objectList/{id}/remove")
	public String removeObjectListAndRedirectToObjectListList(@PathVariable("id") long id) {

		this.objectListService.deleteById(id);

		return "redirect:/objectList/list";
	}

     

     

}
