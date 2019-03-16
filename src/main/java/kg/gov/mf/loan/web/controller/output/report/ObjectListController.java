package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.service.*;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.debtor.WorkSectorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.LoanTypeService;
import kg.gov.mf.loan.output.report.model.GroupType;
import kg.gov.mf.loan.output.report.model.ObjectList;
import kg.gov.mf.loan.output.report.model.ObjectListValue;
import kg.gov.mf.loan.output.report.model.ReferenceView;
import kg.gov.mf.loan.output.report.service.GroupTypeService;
import kg.gov.mf.loan.output.report.service.ObjectListService;
import kg.gov.mf.loan.output.report.service.ObjectListValueService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import kg.gov.mf.loan.web.fetchModels.DebtorModel;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class ObjectListController {

	@Autowired
	LoanService loanService;
	
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

	@Autowired
	UserService userService;

	@Autowired
	ObjectListValueService objectListValueService;

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
	public String getObjectListEditForm(@PathVariable("id") long id, Model model,String fromLoan) {

		Organization gaubk = new Organization();
		
		model.addAttribute("objectList", this.objectListService.findById(id));
		
		
		
		ObjectList modelObjectList = this.objectListService.findById(id);

		if(!(fromLoan ==null)) {
			model.addAttribute("fromLoan",true);
		}
		else{
			model.addAttribute("fromLoan",false);
		}

		ReportTool reportTool = new ReportTool();

		model.addAttribute("objectListValueList", referenceViewService.findByParameter(reportTool.getMapNameOfGroupType(modelObjectList.getGroupType())));


		List<Long> selectedObjectListValuesIds = new ArrayList<Long>();

		for (ObjectListValue objectListValue : modelObjectList.getObjectListValues()) {
			selectedObjectListValuesIds.add(Long.parseLong(objectListValue.getName()));
		}

		model.addAttribute("selectedObjectListValues", selectedObjectListValuesIds);

		model.addAttribute("selectedObjectList", modelObjectList.getObjectListValues());



		return "output/report/objectListForm";

	}

	@RequestMapping(value = "/objectList/save", method = RequestMethod.POST)
	public String saveObjectListAndRedirectToObjectListList(@Validated @ModelAttribute("objectList") ObjectList objectList, 
			String[] ObjectListValuesIds,BindingResult result,String fromLoan) {


		if(!(fromLoan ==null)){
			if(objectList.getId()==0){

				objectListService.create(objectList);
			}
			else if(objectList.getId()>0){
				ObjectList newObjectList=objectListService.findById(objectList.getId());
				newObjectList.setObjectListValues(objectList.getObjectListValues());
				newObjectList.setObjectTypeId(objectList.getObjectTypeId());
				newObjectList.setGroupType(objectList.getGroupType());
				newObjectList.setUser_id(objectList.getUser_id());
				newObjectList.setName(objectList.getName());
				objectListService.edit(newObjectList);
			}
			return "redirect:/list/users/objectLists";
		}
		else {
			String groupTypeId = "";
			if (result.hasErrors()) {
				System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
			} else if (objectList.getId() == 0) {
				if (ObjectListValuesIds != null)
					for (String id : ObjectListValuesIds) {
						objectList.getObjectListValues().add(new ObjectListValue(id, objectList));
					}
				this.objectListService.create(objectList);

				groupTypeId = String.valueOf(objectList.getGroupType().getId());
			} else {
				ObjectList modelObjectList = this.objectListService.findById(objectList.getId());

				modelObjectList.getObjectListValues().clear();
				modelObjectList.setName(objectList.getName());
				modelObjectList.setObjectTypeId(objectList.getObjectTypeId());


				if (ObjectListValuesIds != null)
					for (String id : ObjectListValuesIds) {
						modelObjectList.getObjectListValues().add(new ObjectListValue(id, objectList));
					}

				this.objectListService.edit(modelObjectList);

				groupTypeId = String.valueOf(modelObjectList.getGroupType().getId());

			}

//		return "redirect:/objectList/list";

			return "redirect:/groupType/" + groupTypeId + "/details";
		}

	}

	@RequestMapping("/objectList/{id}/remove")
	public String removeObjectListAndRedirectToObjectListList(@PathVariable("id") long id) {

		this.objectListService.deleteById(id);

		return "redirect:/objectList/list";
	}

	@RequestMapping("/objectList/loan/save")
	public String saveListOfLoan(ModelMap model){

		User user = userService.findByUsername(Utils.getPrincipal());

//		model.addAttribute("userId",user.getId());
		ObjectList objectList=new ObjectList();
		objectList.setUser_id(user.getId());
		objectList.setGroupType(groupTypeService.findById(8));
		objectList.setObjectTypeId(8);
		model.addAttribute("objectList",objectList);

		return "/manage/debtor/loan/objectlist/save";
	}

	@RequestMapping("/debtor/{debtorId}/objectList/form")
	public String getForm(ModelMap model,@PathVariable("debtorId") Long debtorId){

		List<ObjectList> objectLists=objectListService.findAllByGroupType(groupTypeService.findById(8));
		model.addAttribute("objectLists",objectLists);
		model.addAttribute("debtorId",debtorId);

		return "/manage/debtor/loan/objectlist/form";
	}

	@RequestMapping(value = "/manage/debtor/{debtorId}/objectList/values/save",method = RequestMethod.POST)
	public String postSetvalues(@PathVariable("debtorId") Long debtorId,String loans,Long objectList){

		List<String> splitted= Arrays.asList(loans.split("-"));
		ObjectList objectList1=objectListService.findById(objectList);
		Set<ObjectListValue> objectListValueSet=objectList1.getObjectListValues();
   		for(String i: splitted){
   			if(!i.equals("")){
				ObjectListValue objectListValue=new ObjectListValue();
				objectListValue.setObjectList(objectList1);
				objectListValue.setName(i);
				objectListValueService.create(objectListValue);
				objectListValueSet.add(objectListValue);
			}
		}

   		return "redirect: /manage/debtor/{debtorId}/view";
	}

	@RequestMapping("/list/users/objectLists")
	public String listObjectLists(ModelMap model){

		User user = userService.findByUsername(Utils.getPrincipal());
		List<ObjectList> objectLists=objectListService.findAllByGroupTypeAndUser(groupTypeService.findById(8),user.getId());
		model.addAttribute("objectLists",objectLists);

		return "/manage/debtor/loan/objectlist/list";
	}

	@RequestMapping("/debtor/objectList/{id}/view")
	public String getObjectValues(@PathVariable("id") Long id,ModelMap model){
   		ObjectList objectList=objectListService.findById(id);
   		Set<ObjectListValue> objectListValues=objectList.getObjectListValues();
   		List<DebtorModel> list=new ArrayList<>();
   		for(ObjectListValue objectListValue:objectListValues){
   			Loan loan=loanService.getById(Long.valueOf(objectListValue.getName()));
   			DebtorModel debtorModel=new DebtorModel();
   			debtorModel.setDistrictName(loan.getRegNumber());
   			debtorModel.setDebtorName(loan.getDebtor().getName());
   			list.add(debtorModel);
		}
		model.addAttribute("list",list);
		return "/manage/debtor/loan/objectlist/view";
	}
}
