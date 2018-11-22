



/* ADD USERS */

INSERT INTO `users` (`id`, `enabled`, `password`, `username`) VALUES (1, true, 'password', 'admin');
INSERT INTO `users` (`id`, `enabled`, `password`, `username`) VALUES (2, true, 'password', 'user');



/* ADD PERMISSIONS */

INSERT INTO `permission` (`name`) VALUES ('PERM_ADD_USER');
INSERT INTO `permission` (`name`) VALUES ('PERM_VIEW_USER');
INSERT INTO `permission` (`name`) VALUES ('PERM_UPDATE_USER');
INSERT INTO `permission` (`name`) VALUES ('PERM_DELETE_USER');




/* ADD ROLE */

INSERT INTO `role` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `role` (`name`) VALUES ('ROLE_USER');



/* ADD ROLE_PERMISSION */

INSERT INTO `role_permission` (`role_id`,`permission_id`) VALUES (1,1);
INSERT INTO `role_permission` (`role_id`,`permission_id`) VALUES (1,2);
INSERT INTO `role_permission` (`role_id`,`permission_id`) VALUES (1,3);
INSERT INTO `role_permission` (`role_id`,`permission_id`) VALUES (1,4);
INSERT INTO `role_permission` (`role_id`,`permission_id`) VALUES (2,2);



/* ADD USER_ROLE */

INSERT INTO `user_role` (`user_id`,`role_id`) VALUES (1,1);
INSERT INTO `user_role` (`user_id`,`role_id`) VALUES (2,2);



/* ADD ORG FORM */

/* ADD REGION */





/* ADD DISTRICT */





/* ADD AOKMOTU */





/* ADD VILLAGE */





/* ADD IDENTITY DOC GIVEN BY */



/* ADD IDENTITY DOC TYPE */


/* ADD EMPLOYMENT HISTORY EVENT TYPE */



/* ADD cSYSTEM */

INSERT INTO `c_system` (`name`) VALUES ('ASUBK');
INSERT INTO `c_system` (`name`) VALUES ('Rm1');



/* ADD OBJECT TYPE */

INSERT INTO `object_type` (`name`,`code`) VALUES ('System','cSystem');
INSERT INTO `object_type` (`name`,`code`) VALUES ('Organization','Organization');

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

# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Please login', 'Авторизация болуңуз', 'login.form.title', 'Авторизируйтесь пожалуйста');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Username', 'Кыска атыңыз', 'login.form.input.username', 'Имя пользователя');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Password', 'Сыр сөз', 'login.form.input.password', 'Пароль');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Enter', 'Кирүү', 'login.form.button.login', 'Вход');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', 'Аталышы', 'label.orgForm.name', 'Наименование');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Forgot password?', 'Сыр сөздү унуттуңузбу?', 'login.forgot.password', 'Забыли пароль?');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name2', 'Аталышы2', 'asdf', 'Наименование2');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Enabled', 'Колдонууда', 'label.enabled', 'Действует');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Disabled', 'Жараксыз', 'label.disabled', 'Отменен');
#
#
#
# #  orgForm
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', 'Аталышы', 'label.orgForm.table.name', 'Наименование');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Enabled', 'Статусу', 'label.orgForm.table.enabled', 'Статус');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Edit', 'Ондоп-туздоо', 'label.orgForm.table.edit', 'Редактировать');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Delete', 'Очуруп салуу', 'label.orgForm.table.delete', 'Удалить');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Edit', 'Ондоп-туздоо', 'label.orgForm.button.edit', 'Редактировать');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Close', 'Жабып салуу', 'label.orgForm.button.close', 'Закрыть');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Submit', 'Тастыктоо', 'label.orgForm.button.submit', 'Подтвердить');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Cancel', 'Кайра кайтаруу', 'label.orgForm.button.cancel', 'Отмена');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Delete', 'Очуруп салуу', 'label.orgForm.button.delete', 'Удалить');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Save', 'Сактоо', 'label.orgForm.button.save', 'Сохранить');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Add', 'Жаны', 'label.orgForm.button.add', 'Добавить');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Organization Form registration', 'Орг.форма киргизуу', 'label.orgForm.modal.title', 'Регистрация орг. формы');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', 'Аталышы', 'label.orgForm.modal.name', 'Наименование');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Enabled', 'Статусу', 'label.orgForm.modal.enabled', 'Статус');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Org Form List', 'Орг.формалар тизмеси', 'label.orgForm.page.title', 'Список организационных форм');
#
#
# # region
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Region List', 'Областтардын тизмеси', 'label.region.page.title', 'Список областей');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('ID', 'ID', 'label.region.table.id', 'ID');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', 'Аталышы', 'label.region.table.name', 'Наименование');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Code', 'Коду', 'label.region.table.code', 'Код');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('View', 'Карап коруу', 'label.region.table.view', 'Просмотр');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Edit', 'Ондоп-туздоо', 'label.region.table.edit', 'Редактировать');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Delete', 'Очуруп салуу', 'label.region.table.delete', 'Удалить');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('View', 'Карап коруу', 'label.region.button.view', 'Просмотр');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Edit', 'Ондоп-туздоо', 'label.region.button.edit', 'Редактировать');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Delete', 'Очуруп салуу', 'label.region.button.delete', 'Удалить');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Close', 'Жабып салуу', 'label.region.button.close', 'Закрыть');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Save', 'Сактоо', 'label.region.button.save', 'Сохранить');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Add Region', 'Жаны область', 'label.region.button.addRegion', 'Добавить область');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Add District', 'Жаны район', 'label.region.button.addDistrict', 'Добавить район');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('New Region or District Registration Form', 'Жаны область же район киргизуу', 'label.region.modal.title', 'Добавить область или район');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', 'Аталышы', 'label.region.modal.name', 'Наименование');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Code', 'Коду', 'label.region.modal.code', 'Код');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Submit', 'Тастыктоо', 'label.region.button.submit', 'Подтвердить');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Cancel', 'Кайра кайтаруу', 'label.region.button.cancel', 'Отмена');
#
#
# # User
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('User List', 'Колдонуучулардын тизмеси', 'label.user.page.title', 'Список пользователей');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('ID', 'ID', 'label.user.table.id', 'ID');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', 'Аталышы', 'label.user.table.name', 'Наименование');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('View', 'Карап коруу', 'label.user.table.view', 'Просмотр');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Edit', 'Ондоп-туздоо', 'label.user.table.edit', 'Редактировать');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Delete', 'Очуруп салуу', 'label.user.table.delete', 'Удалить');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('View', 'Карап коруу', 'label.user.button.view', 'Просмотр');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Edit', 'Ондоп-туздоо', 'label.user.button.edit', 'Редактировать');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Delete', 'Очуруп салуу', 'label.user.button.delete', 'Удалить');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Close', 'Жабып салуу', 'label.user.button.close', 'Закрыть');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Save', 'Сактоо', 'label.user.button.save', 'Сохранить');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Add User', 'Жаны колдонуучу', 'label.user.button.addUser', 'Добавить пользователя');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Add SupervisorTerm', 'Жаны куратор шарты', 'label.user.button.addSupervisorTerm', 'Добавить условие кураторства');
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('New User or SupervisorTerm Registration Form', 'Жаны колдонуучу же куратор шарты киргизуу', 'label.user.modal.title', 'Добавить пользователя или условие кураторства');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('ID', 'ID', 'label.user.modal.id', 'ID');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', 'Аталышы', 'label.user.modal.name', 'Наименование');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Username', 'Аты', 'label.user.modal.username', 'Имя пользователя');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Password', 'Сыр созу', 'label.user.modal.password', 'Пароль');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Enabled', 'Статусу', 'label.user.modal.enabled', 'Статус');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Roles', 'Ролдору', 'label.user.modal.roles', 'Роли');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('SupervisorTerms', 'Куратор шарттары', 'label.user.modal.supervisorterms', 'Условия кураторства');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Staff', 'Кызматкер', 'label.user.modal.staff', 'Сотрудник');
#
#
#
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Submit', 'Тастыктоо', 'label.user.button.submit', 'Подтвердить');
# INSERT INTO message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Cancel', 'Кайра кайтаруу', 'label.user.button.cancel', 'Отмена');
#















/* ADD ENTITY LIST STATE AND TYPE */

INSERT INTO `appliedEntityListState` (`version`,`name`) VALUES (1,'Разнарядка');
INSERT INTO `appliedEntityListState` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `appliedEntityListType` (`version`,`name`) VALUES (1,'Список получателей');
INSERT INTO `appliedEntityListType` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `appliedEntityState` (`version`,`name`) VALUES (1,'На стадии оформления');
INSERT INTO `appliedEntityState` (`version`,`name`) VALUES (1,'Оформление завершено');
INSERT INTO `appliedEntityState` (`version`,`name`) VALUES (1,'Отказ получателя');
INSERT INTO `appliedEntityState` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `documentPackageState` (`version`,`name`) VALUES (1,'На стадии комплектации');
INSERT INTO `documentPackageState` (`version`,`name`) VALUES (1,'Комплектация завершена');

INSERT INTO `documentPackageType` (`version`,`name`) VALUES (1,'Кредитный пакет документации');
INSERT INTO `documentPackageType` (`version`,`name`) VALUES (1,'Залоговый пакет документации');
INSERT INTO `documentPackageType` (`version`,`name`) VALUES (1,'Пакет документации для перевод долга');
INSERT INTO `documentPackageType` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `entityDocumentState` (`version`,`name`) VALUES (1,'На стадии коплектации');
INSERT INTO `entityDocumentState` (`version`,`name`) VALUES (1,'Копмлектация завершена');
INSERT INTO `entityDocumentState` (`version`,`name`) VALUES (1,'На стадии проверки');
INSERT INTO `entityDocumentState` (`version`,`name`) VALUES (1,'Проверка зварешена');

INSERT INTO `entityDocumentRegisteredBy` (`version`,`name`) VALUES (1,'ГРС');
INSERT INTO `entityDocumentRegisteredBy` (`version`,`name`) VALUES (1,'ЦЗРК');
INSERT INTO `entityDocumentRegisteredBy` (`version`,`name`) VALUES (1,'Нотариус');
INSERT INTO `entityDocumentRegisteredBy` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `orderDocumentType` (`version`,`name`) VALUES (1,'Кредитный договор');
INSERT INTO `orderDocumentType` (`version`,`name`) VALUES (1,'Залоговый договор договор');
INSERT INTO `orderDocumentType` (`version`,`name`) VALUES (1,'Паспорт или свидетельство о регистрации');

INSERT INTO `orderTermFrequencyType` (`version`,`name`) VALUES (1,'Год');
INSERT INTO `orderTermFrequencyType` (`version`,`name`) VALUES (1,'Полугодие');
INSERT INTO `orderTermFrequencyType` (`version`,`name`) VALUES (1,'Квартал');
INSERT INTO `orderTermFrequencyType` (`version`,`name`) VALUES (1,'Месяц');
INSERT INTO `orderTermFrequencyType` (`version`,`name`) VALUES (1,'Неделя');
INSERT INTO `orderTermFrequencyType` (`version`,`name`) VALUES (1,'День');

INSERT INTO `orderTermRatePeriod` (`version`,`name`) VALUES (1,'Год');
INSERT INTO `orderTermRatePeriod` (`version`,`name`) VALUES (1,'Полугодие');
INSERT INTO `orderTermRatePeriod` (`version`,`name`) VALUES (1,'Квартал');
INSERT INTO `orderTermRatePeriod` (`version`,`name`) VALUES (1,'Месяц');
INSERT INTO `orderTermRatePeriod` (`version`,`name`) VALUES (1,'Неделя');
INSERT INTO `orderTermRatePeriod` (`version`,`name`) VALUES (1,'День');

INSERT INTO `orderTermTransactionOrder` (`version`,`name`) VALUES (1,'Основная сумма, проценты, штрафы');
INSERT INTO `orderTermTransactionOrder` (`version`,`name`) VALUES (1,'Проценты, штрафы, основная сумма');
INSERT INTO `orderTermTransactionOrder` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `orderTermAccrMethod` (`version`,`name`) VALUES (1,'Простая ставка');
INSERT INTO `orderTermAccrMethod` (`version`,`name`) VALUES (1,'Аннуитет');
INSERT INTO `orderTermAccrMethod` (`version`,`name`) VALUES (1,'Другое');





INSERT INTO `orgForm` (`version`,`name`) VALUES (1,'Юр.лицо');
INSERT INTO `orgForm` (`version`,`name`) VALUES (1,'Физ.лицо');







INSERT INTO `installmentState` (`version`,`name`) VALUES (1,'Не подтвержден');
INSERT INTO `installmentState` (`version`,`name`) VALUES (1,'Не наступивший');
INSERT INTO `installmentState` (`version`,`name`) VALUES (1,'Наступивший');
INSERT INTO `installmentState` (`version`,`name`) VALUES (1,'Непогашенный');
INSERT INTO `installmentState` (`version`,`name`) VALUES (1,'Погашенный');










