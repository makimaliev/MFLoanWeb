package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.web.util.Utils;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    class Prop {

        public boolean title;
        public boolean description;
        public boolean documentType;
        public boolean documentSubType;
        public boolean generalStatus;

        public boolean senderRegisteredDate;
        public boolean senderRegisteredNumber;
        public boolean senderDueDate;
        public boolean senderExecutorType;
        public boolean senderExecutor;
        public boolean senderResponsibleType;
        public boolean senderResponsible;
        public boolean senderStatus;
        public boolean senderInformation;

        public boolean receiverRegisteredDate;
        public boolean receiverRegisteredNumber;
        public boolean receiverDueDate;
        public boolean receiverExecutorType;
        public boolean receiverExecutor;
        public boolean receiverResponsibleType;
        public boolean receiverResponsible;
        public boolean receiverStatus;
        public boolean receiverInformation;

        public Prop(boolean state) {
            title = state;
            description = state;
            documentType = state;
            documentSubType = state;
            generalStatus = state;
            senderRegisteredDate = state;
            senderRegisteredNumber = state;
            senderDueDate = state;
            senderExecutorType = state;
            senderExecutor = state;
            senderResponsibleType = state;
            senderResponsible = state;
            senderStatus = state;
            senderInformation = state;

            receiverRegisteredDate = state;
            receiverRegisteredNumber = state;
            receiverDueDate = state;
            receiverExecutorType = state;
            receiverExecutor = state;
            receiverResponsibleType = state;
            receiverResponsible = state;
            receiverStatus = state;
            receiverInformation = state;
        }
    }

    protected ProcessEngine processEngine;
    protected UserService userService;

    @Autowired
    protected void setProcessEngine() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }

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

    protected User getUser(Organization organization)
    {
        return userService.findByUsername(Utils.getPrincipal());
    }

    protected User getUser(Department department)
    {
        return userService.findByUsername(Utils.getPrincipal());
    }

    protected User getUser(Person person)
    {
        return userService.findByUsername(Utils.getPrincipal());
    }
}
