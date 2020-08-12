package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.RoleService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.output.report.model.Comparator;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.*;
import kg.gov.mf.loan.output.report.utils.*;
import kg.gov.mf.loan.web.fetchModels.CompareParametersModel;
import kg.gov.mf.loan.web.fetchModels.ReportTemplateModel;
import kg.gov.mf.loan.web.fetchModels.jsTreeModel;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
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

		List<Long> filterParameterIds=new ArrayList<>();
		for (FilterParameter filterParameter:modelReport.getFilterParameters()){
			filterParameterIds.add(filterParameter.getId());
		}
		model.addAttribute("reportFilterParameters", filterParameterIds);
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

        String comparatorString = "";


        List<String> fieldNameList = new ArrayList<>();
        List<String> comparatorList = new ArrayList<>();
        List<String> comparedValueList = new ArrayList<>();

        List<CompareParametersModel> compareParametersModelsList = new ArrayList<>();

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
			if(filterParameter.getFilterParameterType() == FilterParameterType.CONTENT_COMPARE)
            {
                fieldNameList.add(filterParameter.getFieldName());
                comparatorList.add(filterParameter.getComparator().toString());
                comparedValueList.add(filterParameter.getComparedValue().toString());

                String comparatorStringTemp = "(=="+filterParameter.getFieldName()+filterParameter.getComparator().toString()+"==)";
                if(!comparatorString.contains(comparatorStringTemp))
                {
                    comparatorString+=comparatorStringTemp;

                    CompareParametersModel compareParametersModel = new CompareParametersModel();

                    compareParametersModel.setFieldName(filterParameter.getFieldName());
                    compareParametersModel.setComparator(filterParameter.getComparator().toString());
                    compareParametersModel.setComparedValue(filterParameter.getComparedValue().toString());
					compareParametersModel.setComparedValueType((short)1);

                    if(filterParameter.getComparator().toString().contains("DATE"))
                    {
                        compareParametersModel.setComparedValueType((short)2);

                        SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

                        try
                        {
                            Date date = DateFormatShort.parse(filterParameter.getComparedValue().toString());
                            compareParametersModel.setComparedDate(date);
                        }
                        catch (java.text.ParseException e)
                        {

                        }


                    }
                    compareParametersModelsList.add(compareParametersModel);
                }


            }
		}




		model.addAttribute("fieldNames", fieldNameList);
        model.addAttribute("comparators", comparatorList);
        model.addAttribute("comparedValues", comparedValueList);

        model.addAttribute("compareParametersModels", compareParametersModelsList);





		Report modelReport = this.reportService.findById(id);


		model.addAttribute("id",id);

		ReportTemplate reportTemplate = this.reportTemplateService.findByReportId(id);


		ReportTemplateModel reportTemplateModel = new ReportTemplateModel();

		if(reportTemplate.getOnDate()!=null) reportTemplateModel.setOnDate(reportTemplate.getOnDate());
		if(reportTemplate.getAdditionalDate()!=null) reportTemplateModel.setAdditionalDate(reportTemplate.getAdditionalDate());
		reportTemplateModel.setShowGroup1(reportTemplate.getShowGroup1());
		reportTemplateModel.setShowGroup2(reportTemplate.getShowGroup2());
		reportTemplateModel.setShowGroup3(reportTemplate.getShowGroup3());
		reportTemplateModel.setShowGroup4(reportTemplate.getShowGroup4());
		reportTemplateModel.setShowGroup5(reportTemplate.getShowGroup5());
		reportTemplateModel.setShowGroup6(reportTemplate.getShowGroup6());

		reportTemplateModel.setGroupType1(reportTemplate.getGroupType1());
		reportTemplateModel.setGroupType2(reportTemplate.getGroupType2());
		reportTemplateModel.setGroupType3(reportTemplate.getGroupType3());
		reportTemplateModel.setGroupType4(reportTemplate.getGroupType4());
		reportTemplateModel.setGroupType5(reportTemplate.getGroupType5());
		reportTemplateModel.setGroupType6(reportTemplate.getGroupType6());

		Date onDate = new Date();
//		model.addAttribute("onDate",reportTemplate.getOnDate());
		model.addAttribute("onDate",onDate);

		reportTemplateModel.setOnDate(onDate);



		model.addAttribute("onDate",reportTemplate.getOnDate());
		if(reportTemplate.getAdditionalDate()!=null)
		{
			model.addAttribute("showAdditionalDate",true);
		}
		else
		{
			model.addAttribute("showAdditionalDate",false);
		}
		User user=userService.findByUsername(Utils.getPrincipal());
		List<ObjectList> objectLists=objectListService.findAllByGroupTypeAndUser(groupTypeService.findById(8),user.getId());
		model.addAttribute("objectLists",objectLists);


//		model.addAttribute("additionalDate",reportTemplate.getAdditionalDate());

		model.addAttribute("groupTypeList", modelReport.getGroupTypes());

		model.addAttribute("reportTemplateModel",reportTemplateModel);


		model.addAttribute("showInThousands", reportTemplate.getReport().getId()==15 );

//		model.addAttribute("showGroup1", reportTemplate.getShowGroup1());



		return "/output/report/reportCustomView";

	}


	@GetMapping("/api/jsTree")
	@ResponseBody
	public List<jsTreeModel> getTree(@RequestParam(value = "id") Long id){


    	List<Long> groupTypeIds = new ArrayList<>();

		ReportTool reportTool=new ReportTool();
		List<jsTreeModel> jsTreeModels=new ArrayList<>();
		for (FilterParameter filterParameter:reportService.findById(Long.valueOf(id)).getFilterParameters())
		{
			GroupType groupType=filterParameter.getObjectList().getGroupType();

			if(!groupTypeIds.contains(groupType.getId()))
			{
				groupTypeIds.add(groupType.getId());

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
		}
		return jsTreeModels;

	}

	@RequestMapping("/api/report/{id}/filterParameter")
	public void  saveFilterParameter(@PathVariable(value = "id") Long id,
									 @RequestParam(value = "selecteds") String selecteds,
									 @RequestParam(value = "onDate" , required = false) String onDate,
									 @RequestParam(value = "groupType1Select" , required = false) String groupType1select,
									 @RequestParam(value = "showGroup1" , required = false) String showGroup1,
                                     @RequestParam(value = "groupType2Select" , required = false) String groupType2select,
                                     @RequestParam(value = "showGroup2" , required = false) String showGroup2,
                                     @RequestParam(value = "groupType3Select" , required = false) String groupType3select,
                                     @RequestParam(value = "showGroup3" , required = false) String showGroup3,
                                     @RequestParam(value = "groupType4Select" , required = false) String groupType4select,
                                     @RequestParam(value = "showGroup4" , required = false) String showGroup4,
                                     @RequestParam(value = "groupType5Select" , required = false) String groupType5select,
                                     @RequestParam(value = "showGroup5" , required = false) String showGroup5,
                                     @RequestParam(value = "groupType6Select" , required = false) String groupType6select,
                                     @RequestParam(value = "showGroup6" , required = false) String showGroup6,
                                     @RequestParam(value = "fieldName" , required = false) String[] fieldName,
                                     @RequestParam(value = "comparator" , required = false) String[] comparator,
                                     @RequestParam(value = "loanses" , required = false) String[] loanses,
                                     @RequestParam(value = "comparedValue" , required = false) String[] comparedValue,
                                     @RequestParam(value = "comparedDate" , required = false) Date[] comparedDate,
									 @RequestParam(value = "inThousands" , required = false) String inThousands,
                                     HttpServletResponse response) {

		ReportTool reportTool=new ReportTool();

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

		if(loanses!=null)
        if(loanses.length>0)
		{
			try
			{
				for (String objectListIdString: loanses)
				{
					long objectListId = Long.parseLong(objectListIdString);

					if(objectListId > 0)
					{
						ObjectList objectList = this.objectListService.findById(objectListId);

						if(objectList.getGroupType().getId() == 8)
						{

							FilterParameter filterParameter = new FilterParameter();

							filterParameter.setId(c++);
							filterParameter.setFilterParameterType(FilterParameterType.OBJECT_LIST);
							filterParameter.setName("temp list of loans");
							filterParameter.setObjectList(objectList);
							filterParameters.add(filterParameter);
						}

					}

				}


			}
			catch (Exception ex)
			{

			}
		}

        int counter =0;
		int counterDate =0;
		int counterValue =0;

        if(fieldName!=null)
        if(fieldName.length>0)

		for (String fieldNameinLoop:fieldName) {


			FilterParameter filterParameter = new FilterParameter();

			filterParameter.setId(counter+1);
			filterParameter.setComparator(Comparator.valueOf(comparator[counter]));

			if(filterParameter.getComparator().name().contains("DATE"))
			{
				if(comparedDate!=null)
					if(comparedDate.length>counterDate)
					{
						if(comparedDate[counterDate]!=null)
						{
							filterParameter.setComparedValue(reportTool.DateToString(comparedDate[counterDate]));
							counterDate++;

							filterParameter.setFieldName(fieldName[counter]);

							filterParameter.setFilterParameterType(FilterParameterType.CONTENT_COMPARE);
							filterParameter.setName("-");
							filterParameter.setObjectList(null);
							filterParameters.add(filterParameter);
							counter++;

						}

					}
			}
			else
			{
				if(comparedValue!=null)
					if(comparedValue.length>counterValue)
					{
						if(comparedValue[counterValue]!=null)
						{
							filterParameter.setComparedValue(comparedValue[counterValue]);
							counterValue++;

							filterParameter.setFieldName(fieldName[counter]);

							filterParameter.setFilterParameterType(FilterParameterType.CONTENT_COMPARE);
							filterParameter.setName("-");
							filterParameter.setObjectList(null);
							filterParameters.add(filterParameter);
							counter++;

						}
					}
			}








		}

		double inThousandsValue = 1000;
        try{
            if(inThousands!=null) inThousandsValue = Double.parseDouble(inThousands);
        }
        catch (Exception ex)
        {

        }



        ReportTemplate reportTemplate1=new ReportTemplate();
        reportTemplate1.setUsers(reportTemplate.getUsers());
        reportTemplate1.setFilterParameters(filterParameters);
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
        reportTemplate1.setInThousands(inThousandsValue);

		reportTemplate1.setShowGroup1(Boolean.FALSE);
		reportTemplate1.setShowGroup2(Boolean.FALSE);
		reportTemplate1.setShowGroup3(Boolean.FALSE);
		reportTemplate1.setShowGroup4(Boolean.FALSE);
		reportTemplate1.setShowGroup5(Boolean.FALSE);
		reportTemplate1.setShowGroup6(Boolean.FALSE);

        if(showGroup1.equals("true"))
        {
			reportTemplate1.setShowGroup1(Boolean.TRUE);
        }

		if(showGroup2.equals("true"))
		{
			reportTemplate1.setShowGroup2(Boolean.TRUE);
		}
		if(showGroup3.equals("true"))
		{
			reportTemplate1.setShowGroup3(Boolean.TRUE);
		}
		if(showGroup4.equals("true"))
		{
			reportTemplate1.setShowGroup4(Boolean.TRUE);
		}
		if(showGroup5.equals("true"))
		{
			reportTemplate1.setShowGroup5(Boolean.TRUE);
		}
		if(showGroup6.equals("true"))
		{
			reportTemplate1.setShowGroup6(Boolean.TRUE);
		}


		if(groupType1select!=null)
		{
			GroupType groupType1 = groupTypeService.findById(Long.valueOf(groupType1select));
			reportTemplate1.setGroupType1(groupType1);
		}

		if(groupType2select!=null)
		{
			GroupType groupType2 = groupTypeService.findById(Long.valueOf(groupType2select));
			reportTemplate1.setGroupType2(groupType2);
		}


		if(groupType3select!=null)
		{
			GroupType groupType3 = groupTypeService.findById(Long.valueOf(groupType3select));
			reportTemplate1.setGroupType3(groupType3);
		}


		if(groupType4select!=null)
		{
			GroupType groupType4 = groupTypeService.findById(Long.valueOf(groupType4select));
			reportTemplate1.setGroupType4(groupType4);
		}


		if(groupType5select!=null)
		{
			if(!groupType6select.equals(""))
			{
				GroupType groupType5 = groupTypeService.findById(Long.valueOf(groupType5select));
				reportTemplate1.setGroupType5(groupType5);
			}

		}


		if(groupType6select!=null)
		{
			if(!groupType6select.equals(""))
			{
				GroupType groupType6 = groupTypeService.findById(Long.valueOf(groupType6select));
				reportTemplate1.setGroupType6(groupType6);
			}
		}


        Date onDateFormatted = reportTool.StringToDate(onDate);
        if(onDateFormatted!=null)
			reportTemplate1.setOnDate(onDateFormatted);
        else
        	reportTemplate1.setOnDate(reportTemplate.getOnDate());

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment; filename=report.xls");
		OutputStream out = null;





		switch(reportTemplate.getReport().getReportType().toString())
		{
			case "LOAN_SUMMARY":
				generateReport(out,response,new ReportGeneratorLoanSummary(), reportTemplate1);

				break;

			case "LOAN_PAYMENT":
				generateReport(out,response,new ReportGeneratorPayment(), reportTemplate1);
				break;

			case "COLLATERAL_ITEM":
				generateReport(out,response,new ReportGeneratorCollateralItem(), reportTemplate1);
				break;

			case "COLLATERAL_INSPECTION":
				generateReport(out,response,new ReportGeneratorCollateralInspection(), reportTemplate1);
				break;

			case "COLLATERAL_ARREST_FREE":
				generateReport(out,response,new ReportGeneratorCollateralArrestFree(), reportTemplate1);
				break;

			case "STAFF":
				generateReport(out,response,new ReportGeneratorStaffEvent(), reportTemplate1);
				break;

			case "DOCUMENT":
				generateReport(out,response,new ReportGeneratorDocument(), reportTemplate1);
				break;


			case "LOAN_SCHEDULE":
				generateReport(out,response,new ReportGeneratorPaymentSchedule(), reportTemplate1);
				break;

			case "LOAN_PLAN":
				generateReport(out,response,new ReportGeneratorSupervisorPlan(), reportTemplate1);
				break;

			case "LOAN_WRITE_OFF":
				generateReport(out,response,new ReportGeneratorLoanWriteOff(), reportTemplate1);
				break;


			case "LOAN_DEBT_TRANSFER":
				generateReport(out,response,new ReportGeneratorLoanDebtTransfer(), reportTemplate1);
				break;

			case "COLLECTION_PHASE":
				generateReport(out,response,new ReportGeneratorCollectionPhase(), reportTemplate1);
				break;

			case "ENTITY_DOCUMENT":
				generateReport(out,response,new ReportGeneratorEntityDocument(), reportTemplate1);


				break;

		}

        //reportTemplateService.create(reportTemplate1);


//        return String.valueOf(reportTemplate1.getId());
    }

    @PostMapping("/report/reportTemplate/{data}/delete")
    @ResponseBody
    public String deleteReportTemplate(@PathVariable(value = "data") Long data){
        //reportTemplateService.deleteById(data);
        return "OK";
    }

	public void generateReport(OutputStream out, HttpServletResponse response, ReportGenerator reportGenerator, ReportTemplate reportTemplate  )
	{
		try {
			out = response.getOutputStream();
			reportGenerator.generateReportByTemplate(reportTemplate).write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	@RequestMapping("/report/{id}/clone")
	public String cloneByReport(@PathVariable("id") long id, HttpServletResponse response) {

		Report report = this.reportService.findById(id);

		reportService.clone(report);

		return "redirect:/report/list";
	}
}
