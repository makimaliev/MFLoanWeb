package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    protected UserService userService;

    @Autowired
    protected void setUserService(UserService userService) {
        this.userService = userService;
    }

    protected User getUser()
    {
        return userService.findByUsername(Utils.getPrincipal());
    }

    protected User getUser(Staff staff)
    {
        return userService.findByStaff(staff);
    }
    protected User getUser(Department department)
    {
        return userService.findByDepartment(department);
    }




    protected User getUser(Organization organization)
    {
        return userService.findByUsername(Utils.getPrincipal());
    }
}
