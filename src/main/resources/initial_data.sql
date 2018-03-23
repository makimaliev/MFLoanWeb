



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

/* ADD REGION */





/* ADD DISTRICT */





/* ADD AOKMOTU */





/* ADD VILLAGE */





/* ADD IDENTITY DOC GIVEN BY */


INSERT INTO `mfloan`.`identity_doc_given_by` (`enabled`, `name`) VALUES (true, 'MKK');
INSERT INTO `mfloan`.`identity_doc_given_by` (`enabled`, `name`) VALUES (true, 'Min Just');


/* ADD IDENTITY DOC TYPE */


/* ADD EMPLOYMENT HISTORY EVENT TYPE */



/* ADD cSYSTEM */

INSERT INTO `mfloan`.`c_system` (`name`) VALUES ('ASUBK');
INSERT INTO `mfloan`.`c_system` (`name`) VALUES ('Rm1');



/* ADD OBJECT TYPE */

INSERT INTO `mfloan`.`object_type` (`name`,`code`) VALUES ('System','cSystem');
INSERT INTO `mfloan`.`object_type` (`name`,`code`) VALUES ('Organization','Organization');

/* ADD OBJECT EVENT */



/* ADD OBJECT FIELD */


/* ADD OBJECT VALIDATION TERM */







/* ADD IDENTITY DOC */ 


/* ADD IDENTITY DOC DETAILS */ 




/* ADD ADDRESS DETAILS */ 


/* ADD ADDRESS */ 


/* ADD CONTACT */ 



/* ADD ORGANIZATION */ 


/* ADD PERSON */ 

/* ADD BANK DATA */ 


/* ADD DEPARTMENT */ 


/* ADD STAFF */



/* ADD EMPLOYMENT HISTORY */




/* ADD EMPLOYMENT HISTORY EVENT */


/* ADD MESSAGE RESOURCE */

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Please login', 'Авторизация болуңуз', 'login.form.title', 'Авторизируйтесь пожалуйста');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Username', 'Кыска атыңыз', 'login.form.input.username', 'Имя пользователя');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Password', 'Сыр сөз', 'login.form.input.password', 'Пароль');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Enter', 'Кирүү', 'login.form.button.login', 'Вход');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', 'Аталышы', 'label.orgForm.name', 'Наименование');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Forgot password?', 'Сыр сөздү унуттуңузбу?', 'login.forgot.password', 'Забыли пароль?');

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name2', 'Аталышы2', 'asdf', 'Наименование2');










/* ADD ENTITY LIST STATE AND TYPE */

INSERT INTO `mfloan`.`appliedEntityListState` (`version`,`name`) VALUES (1,'Разнарядка');
INSERT INTO `mfloan`.`appliedEntityListState` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`appliedEntityListType` (`version`,`name`) VALUES (1,'Список получателей');
INSERT INTO `mfloan`.`appliedEntityListType` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`appliedentitystate` (`version`,`name`) VALUES (1,'На стадии оформления');
INSERT INTO `mfloan`.`appliedentitystate` (`version`,`name`) VALUES (1,'Оформление завершено');
INSERT INTO `mfloan`.`appliedentitystate` (`version`,`name`) VALUES (1,'Отказ получателя');
INSERT INTO `mfloan`.`appliedentitystate` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`documentpackagestate` (`version`,`name`) VALUES (1,'На стадии комплектации');
INSERT INTO `mfloan`.`documentpackagestate` (`version`,`name`) VALUES (1,'Комплектация завершена');

INSERT INTO `mfloan`.`documentpackagetype` (`version`,`name`) VALUES (1,'Кредитный пакет документации');
INSERT INTO `mfloan`.`documentpackagetype` (`version`,`name`) VALUES (1,'Залоговый пакет документации');
INSERT INTO `mfloan`.`documentpackagetype` (`version`,`name`) VALUES (1,'Пакет документации для перевод долга');
INSERT INTO `mfloan`.`documentpackagetype` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`entitydocumentstate` (`version`,`name`) VALUES (1,'На стадии коплектации');
INSERT INTO `mfloan`.`entitydocumentstate` (`version`,`name`) VALUES (1,'Копмлектация завершена');
INSERT INTO `mfloan`.`entitydocumentstate` (`version`,`name`) VALUES (1,'На стадии проверки');
INSERT INTO `mfloan`.`entitydocumentstate` (`version`,`name`) VALUES (1,'Проверка зварешена');

INSERT INTO `mfloan`.`entitydocumentregisteredby` (`version`,`name`) VALUES (1,'ГРС');
INSERT INTO `mfloan`.`entitydocumentregisteredby` (`version`,`name`) VALUES (1,'ЦЗРК');
INSERT INTO `mfloan`.`entitydocumentregisteredby` (`version`,`name`) VALUES (1,'Нотариус');
INSERT INTO `mfloan`.`entitydocumentregisteredby` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`orderdocumenttype` (`version`,`name`) VALUES (1,'Кредитный договор');
INSERT INTO `mfloan`.`orderdocumenttype` (`version`,`name`) VALUES (1,'Залоговый договор договор');
INSERT INTO `mfloan`.`orderdocumenttype` (`version`,`name`) VALUES (1,'Паспорт или свидетельство о регистрации');

INSERT INTO `mfloan`.`orderTermFrequencyType` (`version`,`name`) VALUES (1,'Год');
INSERT INTO `mfloan`.`orderTermFrequencyType` (`version`,`name`) VALUES (1,'Полугодие');
INSERT INTO `mfloan`.`orderTermFrequencyType` (`version`,`name`) VALUES (1,'Квартал');
INSERT INTO `mfloan`.`orderTermFrequencyType` (`version`,`name`) VALUES (1,'Месяц');
INSERT INTO `mfloan`.`orderTermFrequencyType` (`version`,`name`) VALUES (1,'Неделя');
INSERT INTO `mfloan`.`orderTermFrequencyType` (`version`,`name`) VALUES (1,'День');

INSERT INTO `mfloan`.`orderTermRatePeriod` (`version`,`name`) VALUES (1,'Год');
INSERT INTO `mfloan`.`orderTermRatePeriod` (`version`,`name`) VALUES (1,'Полугодие');
INSERT INTO `mfloan`.`orderTermRatePeriod` (`version`,`name`) VALUES (1,'Квартал');
INSERT INTO `mfloan`.`orderTermRatePeriod` (`version`,`name`) VALUES (1,'Месяц');
INSERT INTO `mfloan`.`orderTermRatePeriod` (`version`,`name`) VALUES (1,'Неделя');
INSERT INTO `mfloan`.`orderTermRatePeriod` (`version`,`name`) VALUES (1,'День');

INSERT INTO `mfloan`.`orderTermTransactionOrder` (`version`,`name`) VALUES (1,'Основная сумма, проценты, штрафы');
INSERT INTO `mfloan`.`orderTermTransactionOrder` (`version`,`name`) VALUES (1,'Проценты, штрафы, основная сумма');
INSERT INTO `mfloan`.`orderTermTransactionOrder` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`orderTermAccrMethod` (`version`,`name`) VALUES (1,'Простая ставка');
INSERT INTO `mfloan`.`orderTermAccrMethod` (`version`,`name`) VALUES (1,'Аннуитет');
INSERT INTO `mfloan`.`orderTermAccrMethod` (`version`,`name`) VALUES (1,'Другое');




INSERT INTO `mfloan`.`orderDocumentType` (`version`,`name`) VALUES (1,'Кредитный договор');
INSERT INTO `mfloan`.`orderDocumentType` (`version`,`name`) VALUES (1,'Залоговый договор');
INSERT INTO `mfloan`.`orderDocumentType` (`version`,`name`) VALUES (1,'Паспорт или свидетельство о регистрации');






INSERT INTO `mfloan`.`debtorType` (`version`,`name`) VALUES (1,'Debtor Type 1');
INSERT INTO `mfloan`.`orgForm` (`version`,`name`) VALUES (1,'Org Form 1');






INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Не подтвержден');
INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Не наступивший');
INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Наступивший');
INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Непогашенный');
INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Погашенный');



