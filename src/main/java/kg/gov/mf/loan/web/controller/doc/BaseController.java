package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.doc.service.AttachmentService;
import kg.gov.mf.loan.web.util.Utils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    protected String path =  SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "C:/temp/";
    protected UserService userService;


    @Autowired
    protected void setUserService(UserService userService) {
        this.userService = userService;
    }

    protected User getUser() {
        String usr = Utils.getPrincipal();
        if(usr.equals("anonymousUser")) {
            return null;
        }
        else
        {
            return userService.findByUsername(usr);
        }
    }
    protected User getUser(Long userId)
    {
        return userService.findById(userId);
    }
    protected User getUser(Staff staff)
    {
        return userService.findByStaff(staff);
    }
    protected User getUser(Department department)
    {
        return userService.findByDepartment(department);
    }
    /*
    protected User getUser(Organization organization)
    {
        return userService.findById(staticUser);
    }
    */
}
