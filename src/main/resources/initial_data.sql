



/* ADD USERS */

INSERT INTO `mfloan`.`users` (`enabled`, `password`, `username`) VALUES (true, 'password', 'admin');
INSERT INTO `mfloan`.`users` (`enabled`, `password`, `username`) VALUES (true, 'password', 'user');



/* ADD PERMISSIONS */

INSERT INTO `mfloan`.`permission` (`name`) VALUES ('PERM_ADD_USER');
INSERT INTO `mfloan`.`permission` (`name`) VALUES ('PERM_VIEW_USER');
INSERT INTO `mfloan`.`permission` (`name`) VALUES ('PERM_UPDATE_USER');
INSERT INTO `mfloan`.`permission` (`name`) VALUES ('PERM_DELETE_USER');




/* ADD ROLE */

INSERT INTO `mfloan`.`role` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `mfloan`.`role` (`name`) VALUES ('ROLE_USER');



/* ADD ROLE_PERMISSION */

INSERT INTO `mfloan`.`role_permission` (`role_id`,`permission_id`) VALUES (1,1);
INSERT INTO `mfloan`.`role_permission` (`role_id`,`permission_id`) VALUES (1,2);
INSERT INTO `mfloan`.`role_permission` (`role_id`,`permission_id`) VALUES (1,3);
INSERT INTO `mfloan`.`role_permission` (`role_id`,`permission_id`) VALUES (1,4);
INSERT INTO `mfloan`.`role_permission` (`role_id`,`permission_id`) VALUES (2,2);



/* ADD USER_ROLE */

INSERT INTO `mfloan`.`user_role` (`user_id`,`role_id`) VALUES (1,1);
INSERT INTO `mfloan`.`user_role` (`user_id`,`role_id`) VALUES (2,2);



/* ADD ORG FORM */

INSERT INTO `mfloan`.`orgform` (`enabled`, `name`) VALUES (true, '��. ����');
INSERT INTO `mfloan`.`orgform` (`enabled`, `name`) VALUES (true, '���. ����');



/* ADD REGION */

INSERT INTO `mfloan`.`region` (`name`,`code`) VALUES ('���������� �������','����� ��� �������');
INSERT INTO `mfloan`.`region` (`name`,`code`) VALUES ('�����-�������� �������','����� ��� �������');
INSERT INTO `mfloan`.`region` (`name`,`code`) VALUES ('������ �������','����� ��� �������');
INSERT INTO `mfloan`.`region` (`name`,`code`) VALUES ('��������� �������','����� ��� �������');
INSERT INTO `mfloan`.`region` (`name`,`code`) VALUES ('��������� �������','����� ��� �������');
INSERT INTO `mfloan`.`region` (`name`,`code`) VALUES ('�����-�������� �������','����� ��� �������');
INSERT INTO `mfloan`.`region` (`name`,`code`) VALUES ('������� �������','����� ��� �������');
INSERT INTO `mfloan`.`region` (`name`,`code`) VALUES ('�.������','����� ��� ������');

/* ADD DISTRICT */

INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('���������� �����','����� ��� ������',1);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('���������� �����','����� ��� ������',1);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('������������ �����','����� ��� ������',1);

INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('��������� �����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('���-��������� �����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('��������� �����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('�����-���������� �����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('���������� �����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('����������� �����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('������������� �����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('�����-���������� �����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('�.�����-����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('�.����-����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('�.���-�����','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('�.���-������','����� ��� ������',2);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('�.������-���','����� ��� ������',2);


INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('���������� �����','����� ��� ������',3);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('�������� �����','����� ��� ������',3);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('����-������� �����','����� ��� ������',3);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('����-������������ �����','����� ��� ������',3);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('���-�������� �����','����� ��� ������',3);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('��������� �����','����� ��� ������',3);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('���������� �����','����� ��� ������',3);
INSERT INTO `mfloan`.`district` (`name`,`code`,`region_id`) VALUES ('�.��','����� ��� ������',3);



/* ADD AOKMOTU */

INSERT INTO `mfloan`.`aokmotu` (`name`,`code`,`district_id`) VALUES ('������� �1 1.������','����� ��� �������',1);
INSERT INTO `mfloan`.`aokmotu` (`name`,`code`,`district_id`) VALUES ('������� �2 1.������','����� ��� �������',1);
INSERT INTO `mfloan`.`aokmotu` (`name`,`code`,`district_id`) VALUES ('������� �3 1.������','����� ��� �������',1);

INSERT INTO `mfloan`.`aokmotu` (`name`,`code`,`district_id`) VALUES ('������� �1 2.������','����� ��� �������',2);
INSERT INTO `mfloan`.`aokmotu` (`name`,`code`,`district_id`) VALUES ('������� �2 2.������','����� ��� �������',2);
INSERT INTO `mfloan`.`aokmotu` (`name`,`code`,`district_id`) VALUES ('������� �3 2.������','����� ��� �������',2);



/* ADD VILLAGE */

INSERT INTO `mfloan`.`village` (`name`,`code`,`aokmotu_id`) VALUES ('���� �1 1.�������','����� ��� ����',1);
INSERT INTO `mfloan`.`village` (`name`,`code`,`aokmotu_id`) VALUES ('���� �2 1.�������','����� ��� ����',1);
INSERT INTO `mfloan`.`village` (`name`,`code`,`aokmotu_id`) VALUES ('���� �3 1.�������','����� ��� ����',1);

INSERT INTO `mfloan`.`village` (`name`,`code`,`aokmotu_id`) VALUES ('���� �1 2.�������','����� ��� ����',2);
INSERT INTO `mfloan`.`village` (`name`,`code`,`aokmotu_id`) VALUES ('���� �2 2.�������','����� ��� ����',2);
INSERT INTO `mfloan`.`village` (`name`,`code`,`aokmotu_id`) VALUES ('���� �3 2.�������','����� ��� ����',2);



/* ADD IDENTITY DOC GIVEN BY */

INSERT INTO `mfloan`.`identity_doc_given_by` (`enabled`, `name`) VALUES (true, '���');
INSERT INTO `mfloan`.`identity_doc_given_by` (`enabled`, `name`) VALUES (true, '������������ �������');



/* ADD IDENTITY DOC TYPE */

INSERT INTO `mfloan`.`identity_doc_type` (`enabled`, `name`) VALUES (true, '�������');
INSERT INTO `mfloan`.`identity_doc_type` (`enabled`, `name`) VALUES (true, '������������� � �����������');



/* ADD EMPLOYMENT HISTORY EVENT TYPE */

INSERT INTO `mfloan`.`employment_history_event_type` (`name`) VALUES ('���� ��������');
INSERT INTO `mfloan`.`employment_history_event_type` (`name`) VALUES ('����� - ������ �����������');
INSERT INTO `mfloan`.`employment_history_event_type` (`name`) VALUES ('�������� �� ������');
INSERT INTO `mfloan`.`employment_history_event_type` (`name`) VALUES ('���������� � ������');
INSERT INTO `mfloan`.`employment_history_event_type` (`name`) VALUES ('�����������');
INSERT INTO `mfloan`.`employment_history_event_type` (`name`) VALUES ('�������������� ���������');




/* ADD cSYSTEM */

INSERT INTO `mfloan`.`c_system` (`name`) VALUES ('�����');
INSERT INTO `mfloan`.`c_system` (`name`) VALUES ('Rm1');



/* ADD OBJECT TYPE */

INSERT INTO `mfloan`.`object_type` (`name`,`code`) VALUES ('�������','cSystem');
INSERT INTO `mfloan`.`object_type` (`name`,`code`) VALUES ('�����������','Organization');

/* ADD OBJECT EVENT */

INSERT INTO `mfloan`.`object_event` (`description`, `name`, `object_type_id`) VALUES ('System add event', 'EVENT_SYSTEM_ADD', '1');
INSERT INTO `mfloan`.`object_event` (`description`, `name`, `object_type_id`) VALUES ('System view event', 'EVENT_SYSTEM_VIEW', '1');
INSERT INTO `mfloan`.`object_event` (`description`, `name`, `object_type_id`) VALUES ('System update event', 'EVENT_SYSTEM_UPDATE', '1');
INSERT INTO `mfloan`.`object_event` (`description`, `name`, `object_type_id`) VALUES ('System delete event', 'EVENT_SYSTEM_DELETE', '1');


/* ADD OBJECT FIELD */

INSERT INTO `mfloan`.`object_field` (`name`,`description`,`method_name`,`object_type_id`) VALUES ('id','����������������� ����� �������','getId',1);
INSERT INTO `mfloan`.`object_field` (`name`,`description`,`method_name`,`object_type_id`) VALUES ('name','������������ �������','getName',1);



/* ADD OBJECT VALIDATION TERM */

INSERT INTO `mfloan`.`validation_term` (`object_field_id`,`name`,`description`,`max_length`,`min_length`) VALUES (2,'�������� ������������','����� ������������ ',2,10);







/* ADD IDENTITY DOC */ 

INSERT INTO `mfloan`.`identity_doc` (`enabled`, `name`, `number`, `pin`,  `identity_doc_given_by_id`, `identity_doc_type_id`) VALUES (true, 'id doc1', 'id doc number1', 'id doc pin1', '1', '1');
INSERT INTO `mfloan`.`identity_doc` (`enabled`, `name`, `number`, `pin`,  `identity_doc_given_by_id`, `identity_doc_type_id`) VALUES (true, 'id doc2', 'id doc number2', 'id doc pin2', '1', '1');

/* ADD IDENTITY DOC DETAILS */ 

INSERT INTO `mfloan`.`identity_doc_details` (`firstname`, `fullname`, `lastname`, `midname`, `identity_doc_id`) VALUES ('1', '������������', '1', '1', '1');
INSERT INTO `mfloan`.`identity_doc_details` (`firstname`, `fullname`, `lastname`, `midname`, `identity_doc_id`) VALUES ('������', '��������� �.�.', '���������', '������������', '2');

UPDATE `mfloan`.`identity_doc` SET `identity_doc_details_id`='1' WHERE `id`='1';
UPDATE `mfloan`.`identity_doc` SET `identity_doc_details_id`='2' WHERE `id`='2';


/* ADD ADDRESS DETAILS */ 

INSERT INTO `mfloan`.`address_details` (`line1`, `line2`, `name`, `soate_code`) VALUES ('line1', 'line2', 'addess name1', 'soate code1');
INSERT INTO `mfloan`.`address_details` (`line1`, `line2`, `name`, `soate_code`) VALUES ('line1', 'line2', 'addess name2', 'soate code2');



/* ADD ADDRESS */ 

INSERT INTO `mfloan`.`address` (`line`, `address_details_id`, `aokmotu_id`, `district_id`, `region_id`, `village_id`) VALUES ('line1', '1', '1', '1', '1', '1');
INSERT INTO `mfloan`.`address` (`line`, `address_details_id`, `aokmotu_id`, `district_id`, `region_id`, `village_id`) VALUES ('line2', '2', '2', '2', '2', '2');


/* ADD CONTACT */ 

INSERT INTO `mfloan`.`contact` (`name`) VALUES ('phone number1');
INSERT INTO `mfloan`.`contact` (`name`) VALUES ('phone number2');


/* ADD ORGANIZATION */ 

INSERT INTO `mfloan`.`organization` (`description`, `enabled`, `name`, `address_id`, `contact_id`, `identity_doc_id`, `org_form_id`) VALUES ('description', true, 'GAUBK', '1', '1', '1', '1');

/* ADD PERSON */ 

INSERT INTO `mfloan`.`person` (`description`, `enabled`, `name`, `address_id`, `contact_id`, `identity_doc_id`) VALUES ('Fiz', true, '���������', '2', '2', '2');

/* ADD BANK DATA */ 

INSERT INTO `mfloan`.`bank_data` (`account_number`, `bik`, `description`, `is_primary`, `name`, `organization_id`) VALUES ('number1', 'bik1', 'description1', true, 'name1', '1');
INSERT INTO `mfloan`.`bank_data` (`account_number`, `bik`, `description`, `is_primary`, `name`, `organization_id`) VALUES ('number2', 'bik2', 'description2', false, 'name2', '1');

/* ADD DEPARTMENT */ 

INSERT INTO `mfloan`.`department` (`description`, `enabled`, `name`, `organization_id`) VALUES ('����������� �����', true, '�����������', '1');
INSERT INTO `mfloan`.`department` (`description`, `enabled`, `name`, `organization_id`) VALUES ('���������� �1', true, '���������� ����', '1');
INSERT INTO `mfloan`.`department` (`description`, `enabled`, `name`, `organization_id`) VALUES ('���������� �2', true, '���������� ���', '1');
INSERT INTO `mfloan`.`department` (`description`, `enabled`, `name`, `organization_id`) VALUES ('���������� �3', true, '���������� ��', '1');
INSERT INTO `mfloan`.`department` (`description`, `enabled`, `name`, `organization_id`) VALUES ('���������� �4', true, '���������� ���', '1');

/* ADD DEPARTMENT */ 

INSERT INTO `mfloan`.`position` (`name`, `department_id`) VALUES ('��������', '1');
INSERT INTO `mfloan`.`position` (`name`, `department_id`) VALUES ('��� ���������', '1');
INSERT INTO `mfloan`.`position` (`name`, `department_id`) VALUES ('��������� ����������', '2');
INSERT INTO `mfloan`.`position` (`name`, `department_id`) VALUES ('��������� ����������', '3');
INSERT INTO `mfloan`.`position` (`name`, `department_id`) VALUES ('��������� ����������', '4');
INSERT INTO `mfloan`.`position` (`name`, `department_id`) VALUES ('��������� ����������', '5');

/* ADD STAFF */

INSERT INTO `mfloan`.`staff` (`enabled`, `name`, `department_id`, `organization_id`, `person_id`, `position_id`) VALUES (true, '��������� �.�.', '1', '1', '1', '1');


/* ADD EMPLOYMENT HISTORY */

INSERT INTO `mfloan`.`employmenthistory` (`number`, `staff_id`) VALUES ('number', '1');
UPDATE `mfloan`.`staff` SET `employment_history_id`='1' WHERE `id`='1';




/* ADD EMPLOYMENT HISTORY EVENT */

INSERT INTO `mfloan`.`employment_history_event` (`date`,`name`, `employmentHistory_id`, `employment_history_event_type_id`) VALUES ('2017/01/01','name', '1', '1');

/* ADD MESSAGE RESOURCE */

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Please login', '����������� �������', 'login.form.title', '��������������� ����������');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Username', '����� ������', 'login.form.input.username', '��� ������������');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Password', '��� ���', 'login.form.input.password', '������');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Enter', '�����', 'login.form.button.login', '����');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', '�������', 'label.orgForm.name', '������������');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Forgot password?', '��� ����� �����������?', 'login.forgot.password', '������ ������?');

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name2', '�������2', 'asdf', '������������2');

/* ADD CREDIT ORDER STATE AND TYPE */

INSERT INTO `mfloan`.`creditorderstate` (`version`,`name`) VALUES (1,'Order State 1');
INSERT INTO `mfloan`.`creditordertype` (`version`,`name`) VALUES (1,'Order Type 1');

/* ADD ENTITY LIST STATE AND TYPE */
INSERT INTO `mfloan`.`appliedentityliststate` (`version`,`name`) VALUES (1,'List State 1');
INSERT INTO `mfloan`.`appliedentitylisttype` (`version`,`name`) VALUES (1,'List Type 1');

/* ADD ORDER DOC TYPE */
INSERT INTO `mfloan`.`orderdocumenttype` (`version`,`name`) VALUES (1,'Order Document Type 1');

/* ADD ORDER TERM PROPS */
INSERT INTO `mfloan`.`ordertermfund` (`version`,`name`) VALUES (1,'Fund 1');
INSERT INTO `mfloan`.`ordertermcurrency` (`version`,`name`) VALUES (1,'KGS');
INSERT INTO `mfloan`.`ordertermcurrency` (`version`,`name`) VALUES (1,'RUB');
INSERT INTO `mfloan`.`ordertermcurrency` (`version`,`name`) VALUES (1,'USD');
INSERT INTO `mfloan`.`ordertermfrequencytype` (`version`,`name`) VALUES (1,'Freq Type 1');
INSERT INTO `mfloan`.`ordertermrateperiod` (`version`,`name`) VALUES (1,'Rate Period 1');
INSERT INTO `mfloan`.`ordertermfloatingratetype` (`version`,`name`) VALUES (1,'Floating Rate Type 1');
INSERT INTO `mfloan`.`ordertermtransactionorder` (`version`,`name`) VALUES (1,'Tx Order 1');
INSERT INTO `mfloan`.`ordertermdaysmethod` (`version`,`name`) VALUES (1,'Days Method 1');
INSERT INTO `mfloan`.`ordertermaccrmethod` (`version`,`name`) VALUES (1,'Accr Method 1');

/* ADD DEBTOR PROPS */
INSERT INTO `mfloan`.`debtortype` (`version`,`name`) VALUES (1,'Debtor Type 1');
INSERT INTO `mfloan`.`orgform` (`version`,`name`) VALUES (1,'Org Form 1');
INSERT INTO `mfloan`.`worksector` (`version`,`name`) VALUES (1,'Work Sector 1');

/* ADD LOAN PROPS */
INSERT INTO `mfloan`.`loanstate` (`version`,`name`) VALUES (1,'Loan State 1');
INSERT INTO `mfloan`.`loantype` (`version`,`name`) VALUES (1,'Loan Type 1');

/* ADD INSTALLMENT STATE AND PAYMENT TYPE*/
INSERT INTO `mfloan`.`installmentstate` (`version`,`name`) VALUES (1,'Installment State 1');
INSERT INTO `mfloan`.`paymenttype` (`version`,`name`) VALUES (1,'Payment Type 1');

/* ADD COLLATERAL PROPS */
INSERT INTO `mfloan`.`collateralitemtype` (`version`,`name`) VALUES (1,'Item Type 1');
INSERT INTO `mfloan`.`collateralquantitytype` (`version`,`name`) VALUES (1,'Quantity Type 1');
INSERT INTO `mfloan`.`collateralconditiontype` (`version`,`name`) VALUES (1,'Condition  Type 1');

INSERT INTO `mfloan`.`inspectionresulttype` (`version`,`name`) VALUES (1,'Inspection Result Type 1');

/* ADD ORDERS AND SUBS */

INSERT INTO `mfloan`.`creditorder` (`version`, `description`, `regDate`, `regNumber`, `creditOrderStateId`, `creditOrderTypeId`) values (1, 'Order desc 1', '2018-01-01', 'REG-1234',1,1);
INSERT INTO `mfloan`.`creditorder` (`version`, `description`, `regDate`, `regNumber`, `creditOrderStateId`, `creditOrderTypeId`) values (1, 'New order', '2017-10-20', 'REG-111',1,1);
INSERT INTO `mfloan`.`creditorder` (`version`, `description`, `regDate`, `regNumber`, `creditOrderStateId`, `creditOrderTypeId`) values (1, 'Credit Order 333', '2018-01-01', 'REG-333',1,1);

INSERT INTO `mfloan`.`appliedentitylist` (`version`,`listDate`,`listNumber`,`appliedEntityListStateId`,`appliedEntityListTypeId`,`creditOrderId`)VALUES(1,'2018-01-01','List 1',1,1,1);

INSERT INTO `mfloan`.`orderdocumentpackage` (`version`,`name`,`creditOrderId`) VALUES (1,'Document Package 1',1);

INSERT INTO `mfloan`.`orderdocument` (`version`,`name`,`orderDocumentPackageId`,`orderDocumentTypeId`) VALUES (1, 'Passport', 1,1);
INSERT INTO `mfloan`.`orderdocument` (`version`,`name`,`orderDocumentPackageId`,`orderDocumentTypeId`) VALUES (1, 'Driver License', 1,1);

INSERT INTO `mfloan`.`orderterm` (`version`,`amount`,`collateralFree`,`description`,`earlyRepaymentAllowed`,`firstInstallmentDate`,`frequencyQuantity`,`graceOnInterestAccrDays`,`graceOnInterestAccrInst`,`graceOnInterestPaymentDays`,`graceOnInterestPaymentInst`,`graceOnPrinciplePaymentDays`,`graceOnPrinciplePaymentInst`,`installmentFirstDay`,`installmentQuantity`,`interestRateValue`,`lastInstallmentDate`,`maxDaysDisbFirstInst`,`minDaysDisbFirstInst`,`penaltyLimitPercent`,`penaltyOnInterestOverdueRateValue`,`penaltyOnPrincipleOverdueRateValue`,`creditOrderId`,`currencyId`,`daysInMonthMethodId`,`daysInYearMethodId`,`frequencyTypeId`,`fundId`,`interestAccrMethodId`,`interestRateValuePerPeriodId`,`interestTypeId`,`penaltyOnInterestOverdueTypeId`,`penaltyOnPrincipleOverdueTypeId`,`transactionOrderId`) 
VALUES (1, 1000, 1, 'Order Term 1', 1, '2018-01-01', 2, 9, 8, 7, 6, 5, 4, 10, 100, 11, '2018-01-01', 3, 2, 1223, 33, 22, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

/* ADD COLLECTION PROPS */
INSERT INTO `mfloan`.`procedurestatus` (`version`,`name`) VALUES (1,'Proc Status 1');
INSERT INTO `mfloan`.`proceduretype` (`version`,`name`) VALUES (1,'Proc Type 1');
INSERT INTO `mfloan`.`phasestatus` (`version`,`name`) VALUES (1,'Phase Status 1');
INSERT INTO `mfloan`.`phasetype` (`version`,`name`) VALUES (1,'Phase Type 1');
INSERT INTO `mfloan`.`eventstatus` (`version`,`name`) VALUES (1,'Event Status 1');
INSERT INTO `mfloan`.`eventtype` (`version`,`name`) VALUES (1,'Event Type 1');