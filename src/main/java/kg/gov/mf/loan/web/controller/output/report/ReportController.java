package kg.gov.mf.loan.web.controller.output.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.District;
import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.sys.model.Role;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.RoleService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.output.report.model.Comparator;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import kg.gov.mf.loan.web.fetchModels.jsTreeModel;
import kg.gov.mf.loan.web.fetchModels.jsTreeStateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Filter;

@Controller
public class ReportController {
	
	@Autowired
    private ReportService reportService;


	@Autowired
	private ReportTemplateService reportTemplateService;

	@Autowired
	private FilterParameterService filterParameterService;

	@Autowired
	private UserService userService;

	@Autowired
	private ContentParameterService contentParameterService;

	@Autowired
	private OutputParameterService outputParameterService;

	@Autowired
	private GroupTypeService groupTypeService;

	@Autowired
	DistrictService districtService;

	@Autowired
	ReferenceViewService referenceViewService;

	@Autowired
	ObjectListService objectListService;

	@Autowired
    RoleService roleService;
     
    public void setReportService(ReportService rs)
    {
        this.reportService = rs;
    }

	@RequestMapping(value = "/report/list", method = RequestMethod.GET)
	public String listReports(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUserName = authentication.getName();

		User currentUser = this.userService.findByUsername(currentUserName);

    	Report modelReport = new Report();
		model.addAttribute("report", modelReport);
		model.addAttribute("reportList", this.reportService.findByUser(currentUser));

//		model.addAttribute("groupTypeList", this.groupTypeService.findAll());
//		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
//		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
//
//
//		model.addAttribute("reportFilterParameters", modelReport.getFilterParameters());
//		model.addAttribute("reportContentParameters", modelReport.getContentParameters());
//		model.addAttribute("reportContentParameters", modelReport.getGroupTypes());

		return "output/report/reportList";
	}
	
	@RequestMapping("report/{id}/view")
	public String viewReportById(@PathVariable("id") long id, Model model) {


		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUserName = authentication.getName();

		User currentUser = this.userService.findByUsername(currentUserName);


		Report report = this.reportService.findById(id);

		List<ReportTemplate> reportTemplates = new ArrayList<>();

		reportTemplates = this.reportTemplateService.findByUser(currentUser);

		if(currentUser.getId()!=1)
		{
			Set<ReportTemplate> reportReportTemplates = new HashSet<>();

			for (ReportTemplate reportTemplate: reportTemplates)
			{
				if(report.getReportTemplates().contains(reportTemplate))
				{
					reportReportTemplates.add(this.reportTemplateService.findById(reportTemplate.getId()));
				}
			}

			report.setReportTemplates(reportReportTemplates);


		}

		model.addAttribute("report", report);

		return "output/report/reportView";
	}
	
	@RequestMapping("report/{id}/details")
	public String viewReportDetailsById(@PathVariable("id") long id, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUserName = authentication.getName();

		User currentUser = this.userService.findByUsername(currentUserName);


		Report report = this.reportService.findById(id);

		List<ReportTemplate> reportTemplates = new ArrayList<>();

		reportTemplates = this.reportTemplateService.findByUser(currentUser);

		for (ReportTemplate reportTemplate: reportTemplates)
		{
			if(currentUser.getId()!=1)
				if(!report.getReportTemplates().contains(reportTemplate))
				{
					report.getReportTemplates().remove(reportTemplate);
				}
		}

		model.addAttribute("report", report);

		return "output/report/reportDetails";
	}	
    
	
	@RequestMapping(value = "/report/add", method = RequestMethod.GET)
	public String getReportAddForm(Model model) {


		Report modelReport = new Report();
		model.addAttribute("report", modelReport);
		model.addAttribute("reportList", this.reportService.findAll());

		model.addAttribute("userList", this.userService.findAll());

		model.addAttribute("groupTypeList", this.groupTypeService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());


		model.addAttribute("reportFilterParameters", modelReport.getFilterParameters());
		model.addAttribute("reportContentParameters", modelReport.getContentParameters());
		model.addAttribute("reportGroupTypes", modelReport.getGroupTypes());
		model.addAttribute("reportOutputParameters", modelReport.getOutputParameters());

		return "output/report/reportForm";
	}

	@RequestMapping("/report/{id}/edit")
	public String getReportEditForm(@PathVariable("id") long id, Model model) {

		Report modelReport = this.reportService.findById(id);

		model.addAttribute("report", modelReport);
		model.addAttribute("reportList", this.reportService.findAll());

		model.addAttribute("userList", this.userService.findAll());
		model.addAttribute("groupTypeList", this.groupTypeService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());


		model.addAttribute("reportFilterParameters", modelReport.getFilterParameters());
		model.addAttribute("reportContentParameters", modelReport.getContentParameters());
		model.addAttribute("reportGroupTypes", modelReport.getGroupTypes());
		model.addAttribute("reportOutputParameters", modelReport.getOutputParameters());

		return "output/report/reportForm";

	}

	@RequestMapping(value = "/report/save", method = RequestMethod.POST)
	public String saveReportAndRedirectToReportList(@Validated @ModelAttribute("report") Report report,
													String[] reportFilterParametersIds,
													String[] reportContentParametersIds,
													String[] reportOutputParametersIds,
													String[] reportGroupTypeIds,
													BindingResult result) {



		if(reportGroupTypeIds!=null)
			for (String id: reportGroupTypeIds)
			{
				report.getGroupTypes().add(this.groupTypeService.findById(Long.valueOf(id)));
			}

		if(reportFilterParametersIds!=null)
			for (String id: reportFilterParametersIds)
			{
				report.getFilterParameters().add(this.filterParameterService.findById(Long.valueOf(id)));
			}


		if(reportContentParametersIds!=null)
			for (String id: reportContentParametersIds)
			{
				report.getContentParameters().add(this.contentParameterService.findById(Long.valueOf(id)));
			}

		if(reportOutputParametersIds!=null)
			for (String id: reportOutputParametersIds)
			{
				report.getOutputParameters().add(this.outputParameterService.findById(Long.valueOf(id)));
			}


			if(report.getUsers().size()==0)
			{
				Set<User> users = new HashSet<>();

				users.add(userService.findByUsername("admin"));

				report.setUsers(users);
			}

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (report.getId() == 0) {
			this.reportService.create(report);
		} else {
			this.reportService.edit(report);
		}

		return "redirect:/report/list";

	}

	@RequestMapping("/report/{id}/remove")
	public String removeReportAndRedirectToReportList(@PathVariable("id") long id) {

		this.reportService.deleteById(id);

		return "redirect:/report/list";
	}


	@RequestMapping("/report/{id}/customView")
	public String jsTree(ModelMap model,@PathVariable(value = "id") Long id){

		ReportTool reportTool=new ReportTool();
//		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
//		List<District> districts=districtService.findByRegionId(Long.valueOf(1));
//		List<jsTreeModel> jsTreeModels=new ArrayList<>();
//		jsTreeModel jsTree= new jsTreeModel();
//		jsTree.setId("region_1");
//		jsTree.setParent("#");
//		jsTree.setChildren(false);
//		jsTreeStateModel jsTreeStateModel= new jsTreeStateModel(true,false);
//		jsTree.setState(jsTreeStateModel.toString());
//		jsTree.setText("Баткен");
//		jsTreeModels.add(jsTree);
//		for (District district:districtService.findByRegionId(Long.valueOf(1))){
//			jsTreeModel jsTree1= new jsTreeModel();
//			jsTree1.setId("district_"+district.getId());
//			jsTree1.setText("region_1");
//			jsTreeStateModel jsTreeStateModel1= new jsTreeStateModel(false,true);
////            jsTree1.setChildren(true);
//			jsTree1.setState(jsTreeStateModel1.toString());
//			jsTree1.setParent("region_1");
//			jsTree1.setText(district.getName());
//			jsTreeModels.add(jsTree1);
//		}
//		String jsonJsTree = gson.toJson(jsTreeModels);
//		model.addAttribute("jsonJsTree",jsonJsTree);
		for (FilterParameter filterParameter:reportService.findById(Long.valueOf(id)).getFilterParameters())
		{
			System.out.println(filterParameter.getObjectList().getGroupType().getName());
			System.out.println(filterParameter.getObjectList().getGroupType().getRow_name());
			System.out.println(referenceViewService.findByParameter(reportTool.getMapNameOfGroupType(filterParameter.getObjectList().getGroupType())));
		}
		model.addAttribute("id",id);

		return "/output/report/reportCustomView";

	}


	@GetMapping("/api/jsTree")
	@ResponseBody
	public List<jsTreeModel> getTree(@RequestParam(value = "id") Long id){

		ReportTool reportTool=new ReportTool();
		List<jsTreeModel> jsTreeModels=new ArrayList<>();
		for (FilterParameter filterParameter:reportService.findById(Long.valueOf(id)).getFilterParameters())
		{
			GroupType groupType=filterParameter.getObjectList().getGroupType();
			jsTreeModel jsTree= new jsTreeModel();


			jsTree.setId(String.valueOf(groupType.getId()));
			jsTree.setParent("#");
			jsTree.setChildren(false);
			jsTree.setText(groupType.getName());
			jsTreeModels.add(jsTree);
			for (ReferenceView referenceView:referenceViewService.findByParameter(reportTool.getMapNameOfGroupType(filterParameter.getObjectList().getGroupType()))){
				jsTreeModel jsTree1= new jsTreeModel();
				jsTree1.setId(groupType.getId()+"_"+referenceView.getId());
				jsTree1.setText(referenceView.getName());
				jsTree1.setParent(String.valueOf(groupType.getId()));
				jsTreeModels.add(jsTree1);
			}
		}
		return jsTreeModels;

	}

	@PostMapping("/api/report/{id}/filterParameter")
	@ResponseBody
	public String  saveFilterParameter(@PathVariable(value = "id") Long id,@RequestParam(value = "selecteds") String selecteds) {


        HashMap<String, List<String>> alls = new HashMap<>();
        List<String> splitted = Arrays.asList(selecteds.split(","));
        for (String s : splitted) {
            if (s.contains("_")) {
                String key = s.split("_")[0];
                String value = s.split("_")[1];
                if (alls.containsKey(key)) {
                    alls.get(key).add(value);
                } else {
                    List<String> asa = new ArrayList<>();
                    asa.add(value);
                    alls.put(key, asa);
                }
            }
        }

        ReportTemplate reportTemplate = reportTemplateService.findByReportId(id);
        reportTemplate.setFilterParameters(null);
        Set<FilterParameter> filterParameters = new HashSet<FilterParameter>();
        int c = 1;
        for (String key : alls.keySet()) {
            List<String> values = alls.get(key);

            FilterParameter filterParameter = new FilterParameter();
            filterParameter.setId(c++);
            filterParameter.setComparator(Comparator.EQUALS);
            filterParameter.setComparedValue(" ");
            filterParameter.setFieldName(" ");
            filterParameter.setFilterParameterType(FilterParameterType.OBJECT_LIST);
            filterParameter.setName("MEN");
            ObjectList objectList = new ObjectList();
            objectList.setGroupType(groupTypeService.findById(Long.valueOf(key)));
            objectList.setName("Bir nerse");
            Set<ObjectListValue> objectListValues = new HashSet<ObjectListValue>();
            for (String s : values) {
                ObjectListValue objectListValue = new ObjectListValue();
                objectListValue.setName(s);
                objectListValue.setObjectList(objectList);
                objectListValues.add(objectListValue);
            }
            objectList.setObjectListValues(objectListValues);
            filterParameter.setObjectList(objectList);
            filterParameters.add(filterParameter);

        }
        reportTemplate.setFilterParameters(filterParameters);

        ReportTemplate reportTemplate1=new ReportTemplate();
        reportTemplate1.setUsers(reportTemplate.getUsers());
        reportTemplate1.setFilterParameters(reportTemplate.getFilterParameters());
        reportTemplate1.setAdditionalDate(reportTemplate.getAdditionalDate());
        reportTemplate1.setContentParameters(reportTemplate.getContentParameters());
        reportTemplate1.setGenerationParameters(reportTemplate.getGenerationParameters());
        reportTemplate1.setGroupType1(reportTemplate.getGroupType1());
        reportTemplate1.setGroupType2(reportTemplate.getGroupType2());
        reportTemplate1.setGroupType3(reportTemplate.getGroupType3());
        reportTemplate1.setGroupType4(reportTemplate.getGroupType4());
        reportTemplate1.setGroupType5(reportTemplate.getGroupType5());
        reportTemplate1.setGroupType6(reportTemplate.getGroupType6());
        reportTemplate1.setName(reportTemplate.getName());
        reportTemplate1.setOutputParameters(reportTemplate.getOutputParameters());
        reportTemplate1.setShowGroup1(reportTemplate.getShowGroup1());
        reportTemplate1.setShowGroup2(reportTemplate.getShowGroup2());
        reportTemplate1.setShowGroup3(reportTemplate.getShowGroup3());
        reportTemplate1.setShowGroup4(reportTemplate.getShowGroup4());
        reportTemplate1.setShowGroup5(reportTemplate.getShowGroup5());
        reportTemplate1.setShowGroup6(reportTemplate.getShowGroup6());
        reportTemplate1.setOnDate(new Date());
        reportTemplate1.setReport(reportTemplate.getReport());
        reportTemplateService.create(reportTemplate1);


        return String.valueOf(reportTemplate1.getId());
    }

    @PostMapping("/report/reportTemplate/{data}/delete")
    @ResponseBody
    public String deleteReportTemplate(@PathVariable(value = "data") Long data){
        reportTemplateService.deleteById(data);
        return "OK";
    }
}
