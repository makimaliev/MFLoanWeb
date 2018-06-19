



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












INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (1,'Please login123','Авторизация болуңуз','login.form.title','Авторизируйтесь пожалуйста');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (2,'Username','Кыска атыңыз','login.form.input.username','Имя пользователя');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (3,'Password','Сыр сөз','login.form.input.password','Пароль');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (4,'Enter','Кирүү','login.form.button.login','Вход');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (5,'Name','Аталышы','label.orgForm.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (6,'Forgot password?','Сыр сөздү унуттуңузбу?','login.forgot.password','Забыли пароль?');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (7,'Name2','Аталышы2','asdf','Наименование2');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (8,'Enabled','Колдонууда','label.enabled','Действует');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (9,'Disabled','Жараксыз','label.disabled','Отменен');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (10,'Name','Аталышы','label.orgForm.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (11,'Enabled','Статусу','label.orgForm.table.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (12,'Edit','Оңдоп-түздөө','label.orgForm.table.edit','Редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (13,'Delete','Очуруп салуу','label.orgForm.table.delete','Удалить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (14,'Edit','Ондоп-туздоо','label.orgForm.button.edit','Редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (15,'Close','Жабып салуу','label.orgForm.button.close','Закрыть');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (16,'Submit','Тастыктоо','label.orgForm.button.submit','Подтвердить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (17,'Cancel','Кайра кайтаруу','label.orgForm.button.cancel','Отмена');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (18,'Delete','Очуруп салуу','label.orgForm.button.delete','Удалить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (19,'Save','Сактоо','label.orgForm.button.save','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (20,'Add','Жаны','label.orgForm.button.add','Добавить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (21,'Organization Form registration','Орг.форма киргизуу','label.orgForm.modal.title','Регистрация орг. формы');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (22,'Name','Аталышы','label.orgForm.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (23,'Enabled','Статусу','label.orgForm.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (24,'Org Form List','Орг.формалар тизмеси','label.orgForm.page.title','Список организационных форм');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (25,'Region List','Областтардын тизмеси','label.region.page.title','Список областей');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (26,'ID','ID','label.region.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (27,'Name','Аталышы','label.region.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (28,'Code','Коду','label.region.table.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (29,'View','Карап коруу','label.region.table.view','Просмотр');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (30,'Edit','Ондоп-туздоо','label.region.table.edit','Редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (31,'Delete','Очуруп салуу','label.region.table.delete','Удалить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (32,'View','Карап коруу','label.region.button.view','Просмотр');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (33,'Edit','Оңдоп-түздөө','label.region.button.edit','Редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (34,'Delete','Очуруп салуу','label.region.button.delete','Удалить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (35,'Close','Жабып салуу','label.region.button.close','Закрыть');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (36,'Save','Сактоо','label.region.button.save','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (37,'Add Region','Жаны область','label.region.button.addRegion','Добавить область');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (38,'Add District','Жаны район','label.region.button.addDistrict','Добавить район');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (39,'New Region or District Registration Form','Жаны область же район киргизуу','label.region.modal.title','Добавить область или район');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (40,'Name','Аталышы','label.region.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (41,'Code','Коду','label.region.modal.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (42,'Submit','Тастыктоо','label.region.button.submit','Подтвердить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (43,'Cancel','Кайра кайтаруу','label.region.button.cancel','Отмена');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (44,'User List','Колдонуучулардын тизмеси','label.user.page.title','Список пользователей');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (45,'ID','ID','label.user.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (46,'Name','Аталышы','label.user.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (47,'View','Карап коруу','label.user.table.view','Просмотр');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (48,'Edit','Ондоп-туздоо','label.user.table.edit','Редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (49,'Delete','Очуруп салуу','label.user.table.delete','Удалить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (50,'View','Карап коруу','label.user.button.view','Просмотр');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (51,'Edit','Оңдоп-түздөө','label.user.button.edit','Редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (52,'Delete','Очуруп салуу','label.user.button.delete','Удалить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (53,'Close','Жабып салуу','label.user.button.close','Закрыть');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (54,'Save','Сактоо','label.user.button.save','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (55,'Add User','Жаны колдонуучу','label.user.button.addUser','Добавить пользователя');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (56,'Add SupervisorTerm','Жаны куратор шарты','label.user.button.addSupervisorTerm','Добавить условие кураторства');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (57,'New User or SupervisorTerm Registration Form','Жаны колдонуучу же куратор шарты киргизуу','label.user.modal.title','Добавить пользователя или условие кураторства');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (58,'ID','ID','label.user.modal.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (59,'Name','Аталышы','label.user.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (60,'Username','Аты','label.user.modal.username','Имя пользователя');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (61,'Password','Сыр созу','label.user.modal.password','Пароль');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (62,'Enabled','Статусу','label.user.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (63,'Roles','Ролдору','label.user.modal.roles','Роли');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (64,'SupervisorTerms','Куратор шарттары','label.user.modal.supervisorterms','Условия кураторства');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (65,'Staff','Кызматкер','label.user.modal.staff','Сотрудник');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (66,'Submit','Тастыктоо','label.user.button.submit','Подтвердить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (67,'Cancel','Кайра кайтаруу','label.user.button.cancel','Отмена');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (68,NULL,NULL,'label.document.title','Заголовок');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (69,NULL,NULL,'label.task.openTasks','Открытые задачи');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (70,NULL,NULL,'label.task.completedTasks','Завершенные задачи');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (71,NULL,NULL,'label.open','Открыть');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (72,NULL,NULL,'label.description','Описание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (73,NULL,NULL,'label.state','Состояние');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (74,NULL,NULL,'label.priority','Приоритет');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (75,NULL,NULL,'label.owner','Владелец');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (76,NULL,NULL,'label.createdDate','Дата создания');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (77,NULL,NULL,'label.term','Срок');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (78,NULL,NULL,'label.completedDate','Дата выполнения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (79,NULL,NULL,'label.document.description','Описание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (80,NULL,NULL,'label.document.documentType','Вид документа');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (81,NULL,NULL,'label.document.documentSubType','Тип документа');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (82,NULL,NULL,'label.document.generalStatus','Состояние');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (83,'Edit','Ондоп туздоо','label.table.edit','Редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (84,'View','Карап коруу','label.table.view','Просмотр');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (85,'Delete','Очуруп салуу','label.table.delete','Удалить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (86,'ID','ID','label.district.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (87,'Name','Аталышы','label.district.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (88,'Code','Код','label.district.table.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (89,'REgion','Облусу','label.district.table.region','Область');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (90,'Add new or edit','Жаны район кошуу же алмаштыруу','label.district.modal.title','Новый район или редактировние района');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (91,'Name','Аталышы','label.district.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (92,'Code','Коду','label.district.modal.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (93,'Region','Облусу','label.district.modal.region','Область');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (94,'Cancel','Жокко чыгаруу','label.district.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (95,'Submit','Сактоо','label.district.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (96,'District list','Райондор тизмеси','label.district.page.title','Список районов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (97,'ID','ID','label.aokmotu.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (98,'Name','Аталышы','label.aokmotu.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (99,'Code','Код','label.aokmotu.table.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (100,'District','Району','label.aokmotu.table.district','Район');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (101,'Add new or edit','Жаны кошуу же алмаштыруу','label.aokmotu.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (102,'Name','Аталышы','label.aokmotu.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (103,'Code','Коду','label.aokmotu.modal.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (104,'District','Району','label.aokmotu.modal.district','Район');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (105,'Cancel','Жокко чыгаруу','label.aokmotu.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (106,'Submit','Сактоо','label.aokmotu.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (107,'Aokmotu list','Аокмоттор тизмеси','label.aokmotu.page.title','Список аокмоту');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (108,'ID','ID','label.village.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (109,'Name','Аталышы','label.village.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (110,'Code','Код','label.village.table.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (111,'Aokmotu','Аокмоту','label.village.table.aokmotu','Аокмоту');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (112,'Add new or edit','Жаны кошуу же алмаштыруу','label.village.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (113,'Name','Аталышы','label.village.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (114,'Code','Коду','label.village.modal.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (115,'Aokmotu','Аокмоту','label.village.modal.aokmotu','Аокмоту');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (116,'Cancel','Жокко чыгаруу','label.village.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (117,'Submit','Сактоо','label.village.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (118,'Village list','Айылдар тизмеси','label.village.page.title','Список сел');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (119,'ID','ID','label.iddocgivenby.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (120,'Name','Аталышы','label.iddocgivenby.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (121,'Enabled','Статусу','label.iddocgivenby.table.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (122,'Add new or edit','Жаны кошуу же алмаштыруу','label.iddocgivenby.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (123,'Name','Аталышы','label.iddocgivenby.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (124,'Enabled','Статусу','label.iddocgivenby.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (125,'Cancel','Жокко чыгаруу','label.iddocgivenby.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (126,'Submit','Сактоо','label.iddocgivenby.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (127,'Add new','Жаны кошуу','label.iddocgivenby.button.addIdDocGivenBy','Добавить новый орган выдачи документов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (128,'Identity doc given by list','Мекемелер тизмеси','label.iddocgivenby.page.title','Список органов выдачи документов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (129,'ID','ID','label.identityDocType.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (130,'Name','Аталышы','label.identityDocType.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (131,'Enabled','Статусу','label.identityDocType.table.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (132,'Add new or edit','Жаны кошуу же алмаштыруу','label.identityDocType.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (133,'Name','Аталышы','label.identityDocType.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (134,'Enabled','Статусу','label.identityDocType.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (135,'Cancel','Жокко чыгаруу','label.identityDocType.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (136,'Submit','Сактоо','label.identityDocType.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (137,'Add new','Жаны кошуу','label.identityDocType.button.addIdentityDocType','Добавить новый вид документа');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (138,'Identity doc type list','Документтер тизмеси','label.identityDocType.page.title','Список документов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (139,'ID','ID','label.employmentHistoryEventType.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (140,'Name','Аталышы','label.employmentHistoryEventType.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (141,'Enabled','Статусу','label.employmentHistoryEventType.table.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (142,'Add new or edit','Жаны кошуу же алмаштыруу','label.employmentHistoryEventType.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (143,'Name','Аталышы','label.employmentHistoryEventType.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (144,'Enabled','Статусу','label.employmentHistoryEventType.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (145,'Cancel','Жокко чыгаруу','label.employmentHistoryEventType.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (146,'Submit','Сактоо','label.employmentHistoryEventType.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (147,'Add new','Жаны кошуу','label.employmentHistoryEventType.button.addEmploymentHistoryEventType','Добавить новый');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (148,'Employment history event type list','Окуя турлорунун тизмеси','label.employmentHistoryEventType.page.title','Список видов событий');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (149,'ID','ID','label.cSystem.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (150,'Name','Аталышы','label.cSystem.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (151,'Enabled','Статусу','label.cSystem.table.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (152,'Add new or edit','Жаны кошуу же алмаштыруу','label.cSystem.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (153,'Name','Аталышы','label.cSystem.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (154,'Enabled','Статусу','label.cSystem.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (155,'Cancel','Жокко чыгаруу','label.cSystem.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (156,'Submit','Сактоо','label.cSystem.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (157,'Add new system','Жаны система кошуу','label.cSystem.button.addcSystem','Добавить систему');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (158,'Add new information','Жаны маалымат кошуу','label.cSystem.button.addInformation','Добавить информацию');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (159,'System list','Системалардын тизмеси','label.cSystem.page.title','Список систем');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (160,'Staff','Кызматкер','label.user.modal.staff','Сотрудник');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (161,'Staff','Кызматкер','label.user.table.staff','Сотрудник');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (162,NULL,NULL,'label.document.sender','Отправитель');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (163,NULL,NULL,'label.document.receiver','Получатель');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (164,'','','label.task.description','Описание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (165,'','','label.task.state','Состояние');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (166,'','','label.task.priority','Приоритет');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (167,'','','label.task.owner','Владелец');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (168,'','','label.task.createdDate','Дата создания');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (169,'','','label.task.dueDate','Срок');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (170,'','','label.task.completedDate','Дата выполнения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (171,'View',NULL,'label.view','Просмотр');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (172,'Edit',NULL,'label.edit','Редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (173,'Delete',NULL,'label.delete','Удалить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (174,'New Document Subtype','','label.documentSubType.add','Новый документ');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (175,'Document Subtypes','','label.documentSubTypes.title','Список документов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (176,'Document Subtype details','','label.documentSubType.details','Детали');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (177,'Internal name','','label.documentSubType.internalName','Идентификатор');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (178,'Save',NULL,'label.save','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (179,'Cancel',NULL,'label.cancel','Отмена');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (180,'Search...',NULL,'label.search','Поиск...');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (181,'ID','ID','label.objectType.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (182,'Name','Аталышы','label.objectType.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (183,'Code','Код','label.objectType.table.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (184,'Add new or edit','Жаны кошуу же алмаштыруу','label.objectType.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (185,'Name','Аталышы','label.objectType.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (186,'Code','Код','label.objectType.modal.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (187,'Cancel','Жокко чыгаруу','label.objectType.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (188,'Submit','Сактоо','label.objectType.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (189,'Add new','Жаны кошуу','label.objectType.button.addObjectType','Добавить ');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (190,'Add field','Жаны талаача кошуу','label.objectType.button.addObjectField','Добавить поле');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (191,'Add event','Жаны окуя кошуу','label.objectType.button.addObjectEvent','Добавить событие');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (192,'Add event','шарт кошуу','label.objectType.button.addFixTerm','Добавить условие фиксации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (193,'Object Type list','Объекттердин тизмеси','label.objectType.page.title','Список объектов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (194,'ID','ID','label.messageResource.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (195,'Name','Аталышы','label.messageResource.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (196,'Message key','-','label.messageResource.table.messageKey','ключевое слово');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (197,'Eng','Англ.','label.messageResource.table.eng','Англ.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (198,'Rus','Орусчасы','label.messageResource.table.rus','Русский');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (199,'Kgz','Кыргызчасы','label.messageResource.table.kgz','Кыргызский');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (200,'Add new or edit','Жаны кошуу же алмаштыруу','label.messageResource.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (201,'Name','Аталышы','label.messageResource.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (202,'Message key','-','label.messageResource.modal.messageKey','ключевое слово');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (203,'Eng','Англ.','label.messageResource.modal.eng','Англ.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (204,'Rus','Орусчасы','label.messageResource.modal.rus','Русский');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (205,'Kgz','Кыргызчасы','label.messageResource.modal.kgz','Кыргызский');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (206,'Cancel','Жокко чыгаруу','label.messageResource.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (207,'Submit','Сактоо','label.messageResource.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (208,'Add new message resource','Жаны которулуш','label.messageResource.button.addMessageResource','Добавить перевод');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (209,'Messsage resources list','Которулуштардын тизмеси','label.messageResource.page.title','Список переводов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (210,'ID','ID','label.objectField.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (211,'Name','Аталышы','label.objectField.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (212,'Description','Тушундурмосу','label.objectField.table.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (213,'Method','Метод','label.objectField.table.method','Метод');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (214,'Add new or edit','Жаны кошуу же алмаштыруу','label.objectField.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (215,'Name','Аталышы','label.objectField.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (216,'Code','Код','label.objectField.modal.code','Код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (217,'Cancel','Жокко чыгаруу','label.objectField.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (218,'Submit','Сактоо','label.objectField.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (219,'Add new','Жаны кошуу','label.objectField.button.addObjectType','Добавить ');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (220,'Add field','Жаны талаача кошуу','label.objectField.button.addObjectField','Добавить поле');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (221,'Add event','Жаны окуя кошуу','label.objectField.button.addObjectEvent','Добавить событие');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (222,'Add event','шарт кошуу','label.objectField.button.addFixTerm','Добавить условие фиксации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (223,'Object Type list','Объекттердин тизмеси','label.objectField.page.title','Список объектов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (224,'ID','ID','label.role.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (225,'Name','Аталышы','label.role.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (226,'Enabled','Статусу','label.role.table.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (227,'Add new or edit','Жаны кошуу же алмаштыруу','label.role.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (228,'Name','Аталышы','label.role.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (229,'Enabled','Статусу','label.role.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (230,'Cancel','Жокко чыгаруу','label.role.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (231,'Submit','Сактоо','label.role.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (232,'Add new role','Жаны роль кошуу','label.role.button.addRole','Добавить роль');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (233,'Role list','Ролдордун тизмеси','label.role.page.title','Список ролей');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (234,'ID','ID','label.permission.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (235,'Name','Аталышы','label.permission.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (236,'Enabled','Статусу','label.permission.table.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (237,'Add new or edit','Жаны кошуу же алмаштыруу','label.permission.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (238,'Name','Аталышы','label.permission.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (239,'Enabled','Статусу','label.permission.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (240,'Cancel','Жокко чыгаруу','label.permission.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (241,'Submit','Сактоо','label.permission.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (242,'Add new permission','Жаны укук кошуу','label.permission.button.addPermission','Добавить право');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (243,'Permission list','Укуктар тизмеси','label.permission.page.title','Список прав доступа');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (244,'ID','ID','label.supervisorTerm.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (245,'Name','Аталышы','label.supervisorTerm.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (246,'Enabled','Статусу','label.supervisorTerm.table.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (247,'DebtorType','Заемщик туру','label.supervisorTerm.table.debtorType','Вид заемщика');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (248,'workSector','Заемщик тармагы','label.supervisorTerm.table.workSector','Отрасль');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (249,'REgion','Облус','label.supervisorTerm.table.region','Область');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (250,'District','Район','label.supervisorTerm.table.district','Район');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (251,'Department','Болум','label.supervisorTerm.table.department','Отдел');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (252,'Add new or edit','Жаны кошуу же алмаштыруу','label.supervisorTerm.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (253,'Name','Аталышы','label.supervisorTerm.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (254,'Enabled','Статусу','label.supervisorTerm.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (255,'DebtorType','Заемщик туру','label.supervisorTerm.modal.debtorType','Вид заемщика');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (256,'workSector','Заемщик тармагы','label.supervisorTerm.modal.workSector','Отрасль');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (257,'REgion','Облус','label.supervisorTerm.modal.region','Область');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (258,'District','Район','label.supervisorTerm.modal.district','Район');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (259,'Department','Болум','label.supervisorTerm.modal.department','Отдел');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (260,'Cancel','Жокко чыгаруу','label.supervisorTerm.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (261,'Submit','Сактоо','label.supervisorTerm.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (262,'Add new supervisor term','Жаны куратор шарты кошуу','label.supervisorTerm.button.addSupervisorTerm','Добавить условие кураторства');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (263,'SupervisorTerm list','куратор шарты тизмеси','label.supervisorTerm.page.title','Список условий кураторства');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (264,'ID','ID','label.information.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (265,'Name','Аталышы','label.information.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (266,'Enabled','Статусу','label.information.table.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (267,'Add new or edit','Жаны кошуу же алмаштыруу','label.information.modal.title','Добавить или редактировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (268,'Name','Аталышы','label.information.modal.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (269,'Enabled','Статусу','label.information.modal.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (270,'Cancel','Жокко чыгаруу','label.information.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (271,'Submit','Сактоо','label.information.button.submit','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (272,'Add new','Жаны кошуу','label.information.button.addEmploymentHistoryEventType','Добавить новый');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (273,'Information type list','Маалымат тизмеси','label.information.page.title','Список информации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (274,'Debtor','Заемщиктер','label.debtor.page.title','Список заемщиков');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (275,'Search','Издоо','label.search','Поиск');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (276,'First','Биринчиси','label.pagination.first','Начало');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (277,'Previous','Буга чейинкиси','label.pagination.prev','Пред');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (278,'Next','Мындан кийинкиси','label.pagination.next','След');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (279,'Last','Акыркысы','label.pagination.last','Послед');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (280,'Page Size','Баракча чондугу','label.pageSize','Кол-во');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (281,'add Debtor','Жаны заемщик','label.debtor.addDebtor','Добавить заемщика');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (282,'add Loan','Жаны кредит','label.debtor.addLoan','Добавить кредит');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (283,'add Agreement','Жаны куроо','label.debtor.addAgreement','Добавить договор залога');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (284,'add Procedure','Жаны ондуруу процедурасы','label.debtor.addProcedure','Добавить процедуру взыскания');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (285,'add Phase','Жаны ондурусу фазасы','label.debtor.addPhase','Добавить фазу взыскания');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (286,'ID','ID','label.debtor.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (287,'Name','Аталышы','label.debtor.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (288,'Debtor Type','Заемщик туру','label.debtor.table.debtorType','Вид заемщика');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (289,'WorkSector','Тармак','label.debtor.table.workSector','Отрасль');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (290,'District','Район','label.debtor.table.district','Район');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (291,'OrgForm','Формасы','label.debtor.table.orgForm','Орг.форма');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (292,'Debtor','Заемщик','label.add.debtor.title','Заемщик');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (293,'Name','Аталышы','label.add.debtor.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (294,'Type','Туру','label.add.debtor.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (295,'Organizational Form','Формасы','label.add.debtor.orgForm','Орг.форма');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (296,'WorkSector','Тармагы','label.add.debtor.workSector','Отрасль');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (297,'Owner','заемщик','label.add.debtor.owner','Заемщик');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (298,'Save','Сактоо','label.add.debtor.save','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (299,'Cancel','Жокко чыгаруу','label.add.debtor.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (300,'Loans','Кредит маалыматы','label.debtor.tab.loans','Кредитная информация');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (301,'Collateral','Куроо маалыматы','label.debtor.tab.agreements','Залоговое обеспечение');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (302,'Collection','Ондуруу маалыматтары','label.debtor.tab.procedures','Принудительное взыскание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (303,'Loan','Насыя','label.add.loan.title','Кредит');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (304,'Save','Сактоо','label.add.loan.save','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (305,'Cancel','Жокко чыгаруу','label.add.loan.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (306,'Select Currency','Валюта танданыз','label.add.loan.selectCurrency','Выбрать валюту');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (307,'Select Type','Насыя турун танданыз','label.add.loan.selectType','Выбрать вид ');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (308,'Select State','Статусун танданыз','label.add.loan.selectState','Выбрать статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (309,'Select Parent','Башкы насыясын танданыз','label.add.loan.selectParent','Выбрать основной кредит');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (310,'Select Order','Насыя беруу чечимин танданыз','label.add.loan.selectCreditOrder','Выбрать решение о выдаче кредита');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (311,'Registration Number','Каттоо номуру','label.loan.regNumber','Регистрационный номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (312,'Registration Date','Каттоо датасы','label.loan.regDate','Дата регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (313,'Amount','Суммасы','label.loan.amount','Сумма по договору');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (314,'Currency','Валютасы','label.loan.currency','Валюта');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (315,'Type','Туру','label.loan.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (316,'State','Статусу','label.loan.state','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (317,'Supervisor','Куратору','label.loan.supervisorId','Куратор');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (318,'Parent','Башкысы','label.loan.parent','Основной кредит');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (319,'Order','Насыя беруу чечими','label.loan.creditOrderId','Решение о выдаче кредита');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (320,'Registration Number','Каттоо номуру','label.loan.table.regNumber','Регистрационный номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (321,'Registration Date','Каттоо датасы','label.loan.table.regDate','Дата регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (322,'Amount','Суммасы','label.loan.table.amount','Сумма по договору');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (323,'Currency','Валютасы','label.loan.table.currency','Валюта');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (324,'Type','Туру','label.loan.table.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (325,'State','Статусу','label.loan.table.state','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (326,'Supervisor','Куратору','label.loan.table.superId','Куратор');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (327,'Parent','Башкысы','label.loan.table.parentId','Основной кредит');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (328,'Order','Насыя беруу чечими','label.loan.table.creditOrderId','Решение о выдаче кредита');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (329,'Has SubLoan','Субкредити бар','label.loan.table.hasSubLoan','Наличие субкредитов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (330,'Credit Terms','Шарттары','label.creditTerms','Условия');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (331,'Write Offs','Списание','label.writeOffs','Списание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (332,'Payment Schedules','Графиктери','label.paymentSchedules','Срочные обязательства');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (333,'Payments','Толомдору','label.payments','Погашения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (334,'Supervisor Plans','План','label.supervisorPlans','План');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (335,'Loan goods','Товар','label.loanGoods','Товар');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (336,'Debt transfers','Перевод','label.debtTransfers','Перевод долга');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (337,'targeted uses','бар','label.targetedUses','Целевое исп.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (338,'reconstructed lists','Реструктуризвция','label.reconstructedLists','Реструктуризация');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (339,'bankrupts','Банкрот','label.bankrupts','Банкротство');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (340,'collaterals','Куроо','label.collaterals','Залог');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (341,'collection phases','Фзалары','label.collectionPhases','Фазы');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (342,'Detailed summary','Эсептери','label.loanDetailedSummary','Детальный расчет');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (343,'Summary','Эсептери','label.loanSummary','Расчет');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (344,'Accrue','Процент, штрафтары','label.accrue','Начисление');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (345,'startDate','Башталган датасы','label.creditTerm.startDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (346,'interestRateValue','Процентная ставка','label.creditTerm.interestRateValue','Процентная ставка');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (347,'ratePeriod','Период','label.creditTerm.ratePeriod','Период начисления процентов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (348,'floatingRateType','Процентке ставка','label.creditTerm.floatingRateType','Плавающая ставка');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (349,'enaltyOnPrincipleOverdueRateValue','Негизги Штраф','label.creditTerm.enaltyOnPrincipleOverdueRateValue','Штраф по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (350,'penaltyOnPrincipleOverdueRateType','Негизги Штрафка ставка','label.creditTerm.penaltyOnPrincipleOverdueRateType','Ставка на штраф по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (351,'penaltyOnInterestOverdueRateValue','Процент штраф','label.creditTerm.penaltyOnInterestOverdueRateValue','Штраф по процентам');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (352,'penaltyOnInterestOverdueRateType','Процент штрафка ставка','label.creditTerm.penaltyOnInterestOverdueRateType','Ставка на штраф по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (353,'penaltyLimitPercent','Шраф лимити','label.creditTerm.penaltyLimitPercent','Лимит начисления штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (354,'penaltyLimitEndDate','Штраф саналбай баштаган дата','label.creditTerm.penaltyLimitEndDate','Дата приост. нач.штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (355,'transactionOrder','Толоо туру','label.creditTerm.transactionOrder','Очередь погашения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (356,'daysInMonthMethod','айдагы кун саноо методу','label.creditTerm.daysInMonthMethod','Метод кол-ва дней в мес.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (357,'daysInYearMethod','жылдагы кун саноо методу','label.creditTerm.daysInYearMethod','Метод кол-ва дней в год');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (358,'Add new Credit Term','Жаны шарт','label.button.addNewCreditTerm','Добавить условие кред-я');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (359,'date','Датасы','label.writeOff.date','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (360,'total amount','Сумма','label.writeOff.totalAmount','Всего');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (361,'principal','Негизги карыз','label.writeOff.principal','Осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (362,'interest','Процент','label.writeOff.interest','Проценты');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (363,'penalty','Штраф','label.writeOff.penalty','Штрафы');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (364,'fee','Комиссия','label.writeOff.fee','Комиссия');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (365,'description','Тушундурмосу','label.writeOff.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (366,'addNewWriteOff','Жаны списание','label.button.addNewWriteOff','Добавить списание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (367,'expectedDate','Датасы','label.paymentSchedule.expectedDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (368,'disbursement','Алынганы','label.paymentSchedule.disbursement','Освоение');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (369,'principalPayment','Негизги карыз','label.paymentSchedule.principalPayment','Осн.сумма');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (370,'interestPayment','Процент','label.paymentSchedule.interestPayment','Проценты');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (371,'collectedIneterestPayment','Топт.процент','label.paymentSchedule.collectedInterestPayment','Нак.проценты');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (372,'collectedPenaltyPayment','Топт.штраф','label.paymentSchedule.collectedPenaltyPayment','Нак.штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (373,'installmentState','Статусу','label.paymentSchedule.installmentState','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (374,'addNewPaymentSchedule','Жаны график','label.button.addNewPaymentSchedule','Добавить ср.обязательство');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (375,'paymentDate','Датасы','label.payment.paymentDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (376,'totalAmount','Сумма','label.payment.totalAmount','Итого');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (377,'principal','Негизги карыз','label.payment.principal','Осн.сумма');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (378,'ineterest','Процент','label.payment.interest','Проценты');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (379,'penalty','Штраф','label.payment.penalty','Штрафы');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (380,'fee','Комиссия','label.payment.fee','Комиссия');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (381,'number','Номер','label.payment.number','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (382,'paymentType','Туру','label.payment.paymentType','Вид платежа');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (383,'addPayment','Толом кошуу','label.button.addNewPayment','Добавить погашение');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (384,'paymentDate','Датасы','label.supervisorPlan.date','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (385,'paymentDate','Суммасы','label.supervisorPlan.amount','Итого');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (386,'paymentDate','Негизги карыз','label.supervisorPlan.principal','Осн.сумма');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (387,'paymentDate','Процент','label.supervisorPlan.interest','Проценты');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (388,'paymentDate','Штраф','label.supervisorPlan.penalty','Штрафы');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (389,'paymentDate','Комиссия','label.supervisorPlan.fee','Комиссия');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (390,'paymentDate','Тушундурмосу','label.supervisorPlan.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (391,'addPlan','План кошуу','label.button.addNewSupervisorPlan','Добавить план');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (392,'ID','ID','label.loanGoods.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (393,'quantity','Кол-во','label.loanGoods.quantity','Кол-во');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (394,'unitType','Ед. измерения','label.loanGoods.unitType','Ед. измерения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (395,'goodType','Туру','label.loanGoods.goodType','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (396,'addLoanGood','Товар кошуу','label.button.addNewLoanGood','Добавить товар');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (397,'ID','ID','label.debtTransfer.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (398,'number','Номери','label.debtTransfer.number','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (399,'date','Датасы','label.debtTransfer.date','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (400,'quantity','Саны','label.debtTransfer.quantity','Кол-во');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (401,'price per unit','Баасы','label.debtTransfer.pricePerUnit','Цена');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (402,'unit type','Ед. изм.','label.debtTransfer.unitType','Ед. изм.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (403,'total cost','Суммасы','label.debtTransfer.totalCost','Сумма');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (404,'payment','Толому','label.debtTransfer.transferPayment','Платеж');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (405,'credit','Насыясы','label.debtTransfer.transferCredit','Кредит');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (406,'person ','Заемщик','label.debtTransfer.transferPerson','Должник');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (407,'goods','Товар','label.debtTransfer.goodsType','Товар');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (408,'add DebtTransfer ','Карыз откоруп берууну кошуу','label.button.addNewDebtTransfer','Добавить перевод долга');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (409,'ID','ID','label.targetedUse.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (410,'Result','Жыйынтыгы','label.targetedUse.result','Результат');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (411,'Created by','Киргизген','label.targetedUse.createdBy','Внесено');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (412,'Created Date','Кирилген дата','label.targetedUse.createdDate','Дата внесения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (413,'Approved by','Тастыктаган','label.targetedUse.approvedBy','Подтверждено');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (414,'Approved date','Тастыкталган дата','label.targetedUse.approvedDate','Дата подтверждения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (415,'Checked by','Текшерген','label.targetedUse.checkedBy','Проверено');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (416,'Checked date','Текшерилген дата','label.targetedUse.checkedDate','Дата проверки');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (417,'Attachment','Тиркеме','label.targetedUse.attachment','Приложение');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (418,'add Targeted Use ','Туура колдонуу боюнча маалымат кошуу','label.button.addNewTargetedUse','Добавить целевое использование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (419,'ID','ID','label.reconstructedList.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (420,'onDate','Датасы','label.reconstructedList.onDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (421,'oldLoan','Насыя','label.reconstructedList.oldLoan','Кредит');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (422,'add Reconstructed List','Реструктуризация кошуу','label.button.addNewReconstructedList','Добавить реструктуризацию');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (423,'ID','ID','label.bankrupt.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (424,'startedOnDate','Башталган датасы','label.bankrupt.startedOnDate','Дата инициирования');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (425,'finishedOnDate','Буткон датасы','label.bankrupt.finishedOnDate','Дата завершения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (426,'add Bankrupt','Банкрот кошуу','label.button.addNewBankrupt','Добавить банкротство');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (427,'ID','ID','label.collectionPhase.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (428,'startDate','Дата инициирования','label.collectionPhase.startDate','Дата инициирования');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (429,'closeDate','Дата завершения','label.collectionPhase.closeDate','Дата завершения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (430,'lastEvent','посл. событие','label.collectionPhase.lastEvent','посл. событие');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (431,'lastStatus','посл. статус','label.collectionPhase.lastStatus','посл. статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (432,'phaseStatus','Статус','label.collectionPhase.phaseStatus','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (433,'phaseType','Вид','label.collectionPhase.phaseType','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (434,'add Collection Phase','Фаза кошуу','label.button.addNewCollectionPhase','Добавить фазу');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (435,'ID','ID','label.loanDetailedSummary.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (436,'onDate','На дату','label.loanDetailedSummary.onDate','На дату');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (437,'disbursement','Освоение','label.loanDetailedSummary.disbursement','Освоение');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (438,'totalDisbursement','Всего освоено','label.loanDetailedSummary.totalDisbursement','Всего освоено');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (439,'principalPayment','По графику по осн.с.','label.loanDetailedSummary.principalPayment','По графику по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (440,'totalPrincipalPayment','Всего по графику по осн.с.','label.loanDetailedSummary.totalPrincipalPayment','Всего по графику по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (441,'principalPaid','Погашение по осн.с.','label.loanDetailedSummary.principalPaid','Погашение по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (442,'totalPrincipalPaid','Всего погашено по осн.с.','label.loanDetailedSummary.totalPrincipalPaid','Всего погашено по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (443,'principalWriteOff','Списание по осн.с.','label.loanDetailedSummary.principalWriteOff','Списание по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (444,'totalPrincipalWriteOff','Всего списано по осн.с.','label.loanDetailedSummary.totalPrincipalWriteOff','Всего списано по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (445,'principalOutstanding','Ост. по осн.с.','label.loanDetailedSummary.principalOutstanding','Ост. по осн.с. ');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (446,'principalOverdue','Проср. по осн.с.','label.loanDetailedSummary.principalOverdue','Проср. по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (447,'daysInPeriod','Кол-во дней','label.loanDetailedSummary.daysInPeriod','Кол-во дней');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (448,'interestAccrued','Начисление проц.','label.loanDetailedSummary.interestAccrued','Начисление проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (449,'totalInterestAccrued','Всего начислено проц.','label.loanDetailedSummary.totalInterestAccrued','Всего начислено проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (450,'totalInterestAccruedOnInterestPayment',',из них подлежит погашению','label.loanDetailedSummary.totalInterestAccruedOnInterestPayment',',из них подлежит погашению');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (451,'interestPayment','По графику по проц.','label.loanDetailedSummary.interestPayment','По графику по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (452,'totalInterestPayment','Всего по графику по проц.','label.loanDetailedSummary.totalInterestPayment','Всего по графику по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (453,'collectedInterestPayment','По графику нак.проц.','label.loanDetailedSummary.collectedInterestPayment','По графику нак.проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (454,'totalCollectedInterestPayment','Всего по графику нак.проц.','label.loanDetailedSummary.totalCollectedInterestPayment','Всего по графику нак.проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (455,'collectedInterestDisbursed','Всего нак. проц.','label.loanDetailedSummary.collectedInterestDisbursed','Всего нак. проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (456,'interestPaid','Погашение проц.','label.loanDetailedSummary.interestPaid','Погашение проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (457,'totalInterestPaid','Всего погашено проц.','label.loanDetailedSummary.totalInterestPaid','Всего погашено проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (458,'interestOutstanding','Остаток по процентам','label.loanDetailedSummary.interestOutstanding','Остаток по процентам');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (459,'interestOverdue','Проср. по проц.','label.loanDetailedSummary.interestOverdue','Проср. по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (460,'penaltyAccrued','Начисление штр.','label.loanDetailedSummary.penaltyAccrued','Начисление штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (461,'totalPenaltyAccrued','Всего начислено штр.','label.loanDetailedSummary.totalPenaltyAccrued','Всего начислено штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (462,'collectedPenaltyPayment','По графику нак.штр.','label.loanDetailedSummary.collectedPenaltyPayment','По графику нак.штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (463,'totalCollectedPenaltyPayment','Всего по графику нак.штр.','label.loanDetailedSummary.totalCollectedPenaltyPayment','Всего по графику нак.штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (464,'collectedPenaltyDisbursed','Всего нак.штр.','label.loanDetailedSummary.collectedPenaltyDisbursed','Всего нак.штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (465,'penaltyPaid','Погашение штр.','label.loanDetailedSummary.penaltyPaid','Погашение штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (466,'totalPenaltyPaid','Всего погашено штр.','label.loanDetailedSummary.totalPenaltyPaid','Всего погашено штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (467,'penaltyOutstanding','Остаток по штр.','label.loanDetailedSummary.penaltyOutstanding','Остаток по штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (468,'penaltyOverdue','Проср. по штр.','label.loanDetailedSummary.penaltyOverdue','Проср. по штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (469,'ID','ID','label.loanSummary.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (470,'onDate','На дату','label.loanSummary.onDate','На дату');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (471,'loanAmount','Сумма по договору','label.loanSummary.loanAmount','Сумма по договору');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (472,'totalDisbursed','Всего освоено','label.loanSummary.totalDisbursed','Всего освоено');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (473,'totalPaid','Всего погашено','label.loanSummary.totalPaid','Всего погашено');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (474,'paidPrincipal','Пог. по осн.с.','label.loanSummary.paidPrincipal','Пог. по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (475,'paidInterest','Пог. по проц.','label.loanSummary.paidInterest','Пог. по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (476,'paidPenalty','Пог. по штр.','label.loanSummary.paidPenalty','Пог. по штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (477,'paidFee','Пог. по комм.','label.loanSummary.paidFee','Пог. по комм.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (478,'totalOutstanding','Всего ост.','label.loanSummary.totalOutstanding','Всего ост.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (479,'outstadingPrincipal','Ост. по осн.с.','label.loanSummary.outstadingPrincipal','Ост. по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (480,'outstadingInterest','Ост. по проц.','label.loanSummary.outstadingInterest','Ост. по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (481,'outstadingPenalty','Ост. по штр.','label.loanSummary.outstadingPenalty','Ост. по штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (482,'outstadingFee','Ост. по комм.','label.loanSummary.outstadingFee','Ост. по комм.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (483,'totalOverdue','Всего проср.','label.loanSummary.totalOverdue','Всего проср.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (484,'overduePrincipal','Проср. по осн.с.','label.loanSummary.overduePrincipal','Проср. по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (485,'overdueInterest','Проср. по проц.','label.loanSummary.overdueInterest','Проср. по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (486,'overduePenalty','Проср. по штр.','label.loanSummary.overduePenalty','Проср. по штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (487,'overdueFee','Проср. по комм.','label.loanSummary.overdueFee','Проср. по комм.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (488,'ID','ID','label.accrue.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (489,'fromDate','с даты','label.accrue.fromDate','с даты');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (490,'toDate','по дату','label.accrue.toDate','по дату');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (491,'daysInPeriod','кол-во дней','label.accrue.daysInPeriod','кол-во дней');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (492,'interestAccrued','Начисление проц.','label.accrue.interestAccrued','Начисление проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (493,'penaltyAccrued','Начисление штр.','label.accrue.penaltyAccrued','Начисление штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (494,'penaltyOnPrincipalOverdue','Штр. на осн.с.','label.accrue.penaltyOnPrincipalOverdue','Штр. на осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (495,'penaltyOnInterestOverdue','Штр. на проц.','label.accrue.penaltyOnInterestOverdue','Штр. на проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (496,'lastInstPassed','Проср. график','label.accrue.lastInstPassed','Проср. график');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (497,'Number','Номери','label.agreement.table.number','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (498,'Date','Датасы','label.agreement.table.date','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (499,'Reg number','Каттоо номери','label.agreement.table.collRegNumber','Регистрационный номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (500,'Reg Date','Каттоо датасы','label.agreement.table.collRegDate','Дата регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (501,'Notary reg number','Нотариус каттоо номери','label.agreement.table.notaryRegNumber','Номер нотариальной регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (502,'Notary reg date','Нотариус каттоо датасы','label.agreement.table.notaryRegDate','Дата нотариальной регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (503,'Arrest reg number','Арест каттоо номери','label.agreement.table.arrestRegNumber','Номер ареста');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (504,'Arrest reg date','Арест каттоо датасы','label.agreement.table.arrestRegDate','Дата ареста');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (505,'ID','ID','label.procedure.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (506,'ID','ID','label.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (507,'Start date','Башталган датасы','label.procedure.table.startDate','Дата инициирования');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (508,'Close date','Буткон датасы','label.procedure.table.closeDate','Дата завершения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (509,'Last Phase','Акыркы фазасы','label.procedure.table.lastPhase','Последняя фаза');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (510,'Last status','Акыркы статусы','label.procedure.table.lastStatusId','Последний статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (511,'Status','Статусу','label.procedure.table.status','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (512,'Type','Туру','label.procedure.table.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (513,'Name','Аталышы','label.report.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (514,'Report Type','Туру','label.report.reportType','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (515,'Add Report','Жаны отчет','label.add.report','Добавить отчет');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (516,'Report Templates','Шаблондор','label.report.info','Шаблоны');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (517,'Report','Отчет','label.reports','Отчет');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (518,'Report type','Отчет туру','label.report.type','Вид отчета');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (519,'Generate','Чыгаруу','label.table.generate','Сформировать');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (520,'Printout','Кагазга чыгаруу','label.printouts','Распечатка');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (521,'Name','Аталышы','label.printout.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (522,'Type','Туру','label.printout.printoutType','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (523,'Add','Жаны','label.add.printout','Добавить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (524,'Printout','Кагазга чыгаруу','label.printouts','Настройка распечатки');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (525,'Templates','Шаблон','label.printout.templates','Шаблоны распечатки');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (526,'Name','Аталышы','label.template.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (527,'Name','Аталышы','label.printoutTemplate.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (528,'Report','Отчет','label.template.report','Отчет');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (529,'Save','Сактоо','label.button.save','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (530,'orders','Насыя беруу чечими','label.debtor.orders','Решение на выдачу кредита');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (531,'ID','ID','label.order.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (532,'regNum','Номери','label.order.table.regNum','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (533,'regDate','Датасы','label.order.table.regDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (534,'description','Тушундурмосу','label.order.table.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (535,'State','Статусу','label.order.table.creditOrderState','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (536,'Type','Туру','label.order.table.creditOrderType','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (537,'Add new','Жаны чечим','label.order.button.addNewCreditOrder','Добавить Решение на выдачу кредита');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (538,'Loans','Кредиттер','label.agreement.table.loans','Кредиты');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (539,'Owner','Куроо ээси','label.agreement.table.owner','Залогодатель');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (540,'Cancel','Жокко чыгаруу','label.button.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (541,'Collateral agreement','Куроо келишими','label.add.collateralAgreement.title','Договор залога');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (542,'organizations','Мекемелер тизмеси','label.organizations','Список организаций');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (543,'addressData','Адрес','label.addressData','Адресные данные');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (544,'identityDocData','Кимдигин аныктаган маалымат','label.identityDocData','Идентификационные данные');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (545,'contacts','Контакт','label.contacts','Контактные данные');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (546,'add organization','Жаны мекеме','label.add.organization','Добавить организацию');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (547,'add bankData','Жаны банк маалыматы','label.add.bankData','Добавить банковские данные');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (548,'add department','Жаны болум','label.add.department','Добавить подразделение');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (549,'add information','Жаны маалымат','label.add.information','Добавить информацию');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (550,'add staff','Жаны кызматкер','label.add.staff','Добавить сотрудника');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (551,'name','Аталышы','label.organization.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (552,'description','Тушундурмосу','label.organization.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (553,'name','Аталышы','label.org.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (554,'description','Тушундурмосу','label.org.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (555,'orgForm','Мекеме формасы','label.org.orgForm','Орг. форма');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (556,'address','Адрес','label.org.address','Адрес');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (557,'region','Облусу','label.org.region','Область');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (558,'district','Району','label.org.district','Район');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (559,'aokmotu','А.окмоту','label.org.aokmotu','А.окомту');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (560,'village','Айылы','label.org.village','Село');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (561,'name','Аталышы','label.identityDoc.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (562,'enabled','Статусу','label.identityDoc.enabled','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (563,'number','Номери','label.identityDoc.number','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (564,'pin','ИНН','label.identityDoc.pin','ИНН');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (565,'type','Туру','label.identityDoc.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (566,'givenBy','Берген мекеме','label.identityDoc.givenBy','Кем выдано');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (567,'date','Датасы','label.identityDoc.date','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (568,'firstname','Аты','label.identityDoc.firstname','Имя');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (569,'lastname','Фамилиясы','label.identityDoc.lastname','Фамилия');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (570,'midname','Атасынын аты','label.identityDoc.midname','Отчетство');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (571,'fullname','Толук аты','label.identityDoc.fullname','Полное наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (572,'name','Контактар','label.contact.name','Контакты');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (573,'persons','ФЛ тизмеси','label.persons','Список физ.лиц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (574,'add person','жаны ФЛ','label.add.person','Добавить физ.лицо');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (575,'name','Аталышы','label.person.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (576,'description','Тушундурмосу','label.person.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (577,'addressLine','Адрес','label.person.addressLine','Адрес');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (578,'region','Облусу','label.person.region','Область');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (579,'district','Району','label.person.district','Район');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (580,'aokmotu','А.окмоту','label.person.aokmotu','А.окмоту');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (581,'village','Айылы','label.person.village','Село');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (602,'Order','Насыя чечими','label.add.order.title','Решение на выдачу кредита');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (603,'Order','Насыя чечими','label.order.add','Решение на выдачу кредита');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (604,'registrationNumber','Номери','label.order.registrationNumber','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (605,'registrationDate','Датасы','label.order.registrationDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (606,'description','Тушундурмосу','label.order.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (607,'state','Статусу','label.order.state','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (608,'type','Туру','label.order.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (609,'save','Сактоо','label.form.save','Сохранить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (610,'cancel','Жокко чыгаруу','label.form.cancel','Отменить');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (611,'select.date','Дата тандоаныз','label.select.date','Указать дату');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (612,'select','Тизмеден танданыз','label.select','Выбрать из списка');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (613,'entityLists','Алуучу тизмелери','label.order.entityLists','Списки получателей');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (614,'documentPackages','Документ пакети','label.order.documentPackages','Пакет документации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (615,'terms','НАсыя шарттары','label.order.terms','Кредитные условия');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (616,'addAppliedEntityList','Жаны тизме','label.order.button.addAppliedEntityList','Добавить список');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (617,'listNum','Номери','label.appliedEntityList.table.listNum','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (618,'listDate','Датасы','label.appliedEntityList.table.listDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (619,'entityListState','Статусу','label.appliedEntityList.table.entityListState','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (620,'entityListType','Туру','label.appliedEntityList.table.entityListType','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (621,'listNum','Номери','label.entityList.number','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (622,'listDate','Датасы','label.entityList.date','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (623,'entityListState','Статусу','label.entityList.state','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (624,'entityListType','Туру','label.entityList.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (625,'entityListState','Статусу','label.appliedEntityList.table.entytiListState','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (626,'entityListType','Туру','label.appliedEntityList.table.entytiListType','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (627,'addDocumentPackage','Жаны документтер пакети','label.order.button.addDocumentPackage','Добавить пакет документации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (628,'name','Аталышы','label.orderDocumentPackage.table.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (629,'add documentPackage','Документтер пакети','label.documentPackage.add','Пакет документации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (630,'orderDocumentPackage','Документтер пакети','label.add.orderDocumentPackage.title','Пакет документации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (631,'name','Аталышы','label.orderDocumentPackage.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (632,'addOrderTerm','Жаны шарт кошуу','label.order.button.addOrderTerm','Добавить условия кредитования');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (633,'descrition','Тушундурмосу','label.orderTerm.table.descrition','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (634,'fund','Каржы булагы','label.orderTerm.table.fund','Источник финансирования');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (635,'amount','Суммасы','label.orderTerm.table.amount','Сумма');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (636,'currency','Валютасы','label.orderTerm.table.currency','Валюта');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (637,'frequencyQuantity','Цикл саны','label.orderTerm.table.frequencyQuantity','Кол-во циклов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (638,'frequencyType','Цикл туру','label.orderTerm.table.frequencyType','Вид цикла');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (639,'installmentQuantity','Период саны','label.orderTerm.table.installmentQuantity','Кол-во периодов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (640,'installmentFirstDay','Толомдун биринчи куну','label.orderTerm.table.installmentFirstDay','Первый день погашения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (641,'firstInstallmentDate','Толомдун биринчи датасы','label.orderTerm.table.firstInstallmentDate','Первая дата погашения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (642,'lastInstalmentdate','Толомдун акыркы датасы','label.orderTerm.table.lastInstalmentdate','Последняя дата погашения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (643,'minDaysDisbFirstInstallment','Биринчи толомго эн аз кун','label.orderTerm.table.minDaysDisbFirstInstallment','Мин. дней первого пог.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (644,'maxDaysDisbFirstInstallment','Биринчи толомго эн коп кун','label.orderTerm.table.maxDaysDisbFirstInstallment','Макс. дней первого пог.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (645,'graceOnPrinciplePaymentInstallment','Негизги карызды толоого женилдетуу периодтору','label.orderTerm.table.graceOnPrinciplePaymentInstallment','Кол-во льготных периодов (пог. осн.с.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (646,'graceOnPrinciplePaymentDays','Негизиг карызды толоого женилдетуу кундору','label.orderTerm.table.graceOnPrinciplePaymentDays','Кол-во льготных дней (пог. осн.с.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (647,'graceOnInterestPaymentInstallment','пайызды толоого женилдетуу периодтору','label.orderTerm.table.graceOnInterestPaymentInstallment','Кол-во льготных периодов (пог. проц.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (648,'graceOnInterestPaymentDays','пайызды толоого женилдетуу кундору','label.orderTerm.table.graceOnInterestPaymentDays','Кол-во льготных дней (пог. проц.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (649,'graceOnInterestAcrrInstallment','пайыз саноого женилдетуу периодтору','label.orderTerm.table.graceOnInterestAcrrInstallment','Кол-во льготных периодов (нач.проц.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (650,'graceOnInterestAccrDays','пайыз саноого женилдетуу кундору','label.orderTerm.table.graceOnInterestAccrDays','Кол-во льготных дней (нач. проц.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (651,'interestRateValue','Процент','label.orderTerm.table.interestRateValue','Процентная ставка');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (652,'interestRateValuePerPeriod','Периодтогу процент','label.orderTerm.table.interestRateValuePerPeriod','Процентная ставка в период');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (653,'interestType','Процент ставкасы','label.orderTerm.table.interestType','Ставка на нач. проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (654,'penaltyOnPrincipleOverdueRateValue','Негизги карызга штраф','label.orderTerm.table.penaltyOnPrincipleOverdueRateValue','Штраф за проср. по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (655,'penaltyOnPrincipleOverdueType','Негизги карызга штраф ставкасы','label.orderTerm.table.penaltyOnPrincipleOverdueType','Ставка на штраф за проср. по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (656,'penaltyOnInterestOverdueType','Процентке штраф ставкасы','label.orderTerm.table.penaltyOnInterestOverdueType','Ставка на штраф за проср. по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (657,'penaltyOnInterestOverdueRateValue','Процентке штраф','label.orderTerm.table.penaltyOnInterestOverdueRateValue','Штраф за проср. по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (658,'daysInYearMethod','Бир жылда кун саноо методу','label.orderTerm.table.daysInYearMethod','Метод рас. кол-ва дней в году');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (659,'daysInMonthMethod','Бир айда кун саноо методу','label.orderTerm.table.daysInMonthMethod','Метод рас. кол-ва дней в мес.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (660,'transactionOrder','Толом ','label.orderTerm.table.transactionOrder','Очередность пог.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (661,'interestAccrMethod','Процент саноо методу','label.orderTerm.table.interestAccrMethod','Метод нач. проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (662,'earlyRepaymentAllowed','Алдын ала толоо','label.orderTerm.table.earlyRepaymentAllowed','Возм. досроч. пог.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (663,'penaltyLimitPercent','Штраф чеги','label.orderTerm.table.penaltyLimitPercent','Макс. лимит. нач. штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (664,'collateralFree','Куроодон бошотулган','label.orderTerm.table.collateralFree','Освоб. от зал.об.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (665,'descrition','Тушундурмосу','label.order.term.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (666,'fund','Каржы булагы','label.order.term.fund','Источник финансирования');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (667,'amount','Суммасы','label.order.term.amount','Сумма');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (668,'currency','Валютасы','label.order.term.currency','Валюта');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (669,'frequencyQuantity','Цикл саны','label.order.term.freqQuantity','Кол-во циклов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (670,'frequencyType','Цикл туру','label.order.term.freqType','Вид цикла');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (671,'installmentQuantity','Период саны','label.order.term.installmentQuantity','Кол-во периодов');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (672,'installmentFirstDay','Толомдун биринчи куну','label.order.term.insFirstDay','Первый день погашения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (673,'firstInstallmentDate','Толомдун биринчи датасы','label.order.term.firstInstallmentDate','Первая дата погашения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (674,'lastInstalmentdate','Толомдун акыркы датасы','label.order.term.lastInstallmentDate','Последняя дата погашения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (675,'minDaysDisbFirstInstallment','Биринчи толомго эн аз кун','label.order.term.minDaysDisbFirstInst','Мин. дней первого пог.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (676,'maxDaysDisbFirstInstallment','Биринчи толомго эн коп кун','label.order.term.maxDaysDisbFirstInst','Макс. дней первого пог.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (677,'graceOnPrinciplePaymentInstallment','Негизги карызды толоого женилдетуу периодтору','label.order.term.graceOnPrinciplePaymentInst','Кол-во льготных периодов (пог. осн.с.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (678,'graceOnPrinciplePaymentDays','Негизиг карызды толоого женилдетуу кундору','label.order.term.graceOnPrinciplePaymentDays','Кол-во льготных дней (пог. осн.с.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (679,'graceOnInterestPaymentInstallment','пайызды толоого женилдетуу периодтору','label.order.term.graceOnInterestPaymentInst','Кол-во льготных периодов (пог. проц.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (680,'graceOnInterestPaymentDays','пайызды толоого женилдетуу кундору','label.order.term.graceOnInterestPaymentDays','Кол-во льготных дней (пог. проц.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (681,'graceOnInterestAcrrInstallment','пайыз саноого женилдетуу периодтору','label.order.term.graceOnInterestAccrInst','Кол-во льготных периодов (нач.проц.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (682,'graceOnInterestAccrDays','пайыз саноого женилдетуу кундору','label.order.term.graceOnInterestAccrDays','Кол-во льготных дней (нач. проц.)');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (683,'interestRateValue','Процент','label.order.term.interestRateValue','Процентная ставка');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (684,'interestRateValuePerPeriod','Периодтогу процент','label.order.term.interestRateValuePerPeriod','Процентная ставка в период');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (685,'interestType','Процент ставкасы','label.order.term.interestType','Ставка на нач. проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (686,'penaltyOnPrincipleOverdueRateValue','Негизги карызга штраф','label.order.term.penaltyOnPrincipleOverdueRateValue','Штраф за проср. по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (687,'penaltyOnPrincipleOverdueType','Негизги карызга штраф ставкасы','label.order.term.penaltyOnPrincipleOverdueType','Ставка на штраф за проср. по осн.с.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (688,'penaltyOnInterestOverdueType','Процентке штраф ставкасы','label.order.term.penaltyOnInterestOverdueType','Ставка на штраф за проср. по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (689,'penaltyOnInterestOverdueRateValue','Процентке штраф','label.order.term.penaltyOnInterestOverdueRateValue','Штраф за проср. по проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (690,'daysInYearMethod','Бир жылда кун саноо методу','label.order.term.daysInYearMethod','Метод рас. кол-ва дней в году');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (691,'daysInMonthMethod','Бир айда кун саноо методу','label.order.term.daysInMonthMethod','Метод рас. кол-ва дней в мес.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (692,'transactionOrder','Толом ','label.order.term.transactionOrder','Очередность пог.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (693,'interestAccrMethod','Процент саноо методу','label.order.term.interestAccrMethod','Метод нач. проц.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (694,'earlyRepaymentAllowed','Алдын ала толоо','label.order.term.earlyRepaymentAllowed','Возм. досроч. пог.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (695,'penaltyLimitPercent','Штраф чеги','label.order.term.penaltyLimitPercent','Макс. лимит. нач. штр.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (696,'collateralFree','Куроодон бошотулган','label.order.term.collateralFree','Освоб. от зал.об.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (697,'ID','ID','label.order.table.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (709,'ID','ID','label.entityDocument.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (710,'name','Аталышы','label.entityDocument.name','Наимнеование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (711,'completed by','Толуктаган','label.entityDocument.completedBy','Укомплектовано');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (712,'completed date','Толукталган датасы','label.entityDocument.completedDate','Дата комплектации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (713,'completed desc','Тушундурмосу','label.entityDocument.completedDescription','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (714,'approved by','Тастыктаган','label.entityDocument.approvedBy','Подтверждено');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (715,'approved date','Тастыыктоо датасы','label.entityDocument.approvedDate','Дата подтверждения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (716,'approved desc','Тушундурмосу','label.entityDocument.approvedDescription','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (717,'reg number','Каттоо номери','label.entityDocument.registeredNumber','Регистрационный номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (718,'reg date','Каттоо датасы','label.entityDocument.registeredDate','Дата регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (719,'reg by','Каттаган','label.entityDocument.registeredBy','Зарегистрировано');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (720,'reg description','Тушундурмосу','label.entityDocument.registeredDescription','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (721,'ID','ID','label.document.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (722,'name','ID','label.document.name','Наимнеование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (723,'completedBy','ID','label.document.completedBy','Укомплектовано');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (724,'completedDate','ID','label.document.completedDate','Дата комплектации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (725,'completedDescription','ID','label.document.completedDescription','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (726,'approvedBy','ID','label.document.approvedBy','Подтверждено');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (727,'approvedDate','ID','label.document.approvedDate','Дата подтверждения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (728,'approvedDescription','ID','label.document.approvedDescription','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (729,'registeredNumber','ID','label.document.registeredNumber','Регистрационный номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (730,'registeredDate','ID','label.document.registeredDate','Дата регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (731,'registeredBy','ID','label.document.registeredBy','Зарегистрировано');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (732,'registeredDescription','ID','label.document.registeredDescription','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (733,'Add document','Документ кошуу','label.document.add','Добавить документ');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (734,'name','Аталышы','label.documentPackage.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (735,'completedDate','Толукталган датасы','label.documentPackage.completedDate','Дата комплектации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (736,'approvedDate','Тастыкталган датасы','label.documentPackage.approvedDate','Дата подтверждения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (737,'completedRatio','Толтукталганы','label.documentPackage.completedRatio','Доля комплектации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (738,'approvedRatio','Тастыкталганы','label.documentPackage.approvedRatio','Доля подтверждения');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (739,'registeredRatio','Катталганы','label.documentPackage.registeredRatio','Доля регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (740,'state','Статусу','label.documentPackage.state','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (741,'type','Туру','label.documentPackage.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (742,'documents','Документтер','label.documents','Документы');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (743,'orderTerm','Насыя шарты','label.orderTerm.add','Кредитное условие');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (744,'Number','Номери','label.entityList.listNumber','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (745,'Date','Датасы','label.entityList.listDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (746,'Entities','Алуучулар','label.entities','Получатели');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (747,'Name','Аталышы','label.entity.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (748,'State','Статусу','label.entity.state','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (749,'Add new','Жаны алуучу','label.entity.add','Добавить получателя');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (750,'documentPackages','Документ пакеттери','label.documentPackages','Пакет документации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (751,'State','Статусу','label.document.state','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (752,'State','Статусу','label.entityDocument.state','Статус');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (753,'Name','Аталышы','label.entity.number','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (754,'orderDocuments','Документтер','label.orderDocuments','Документы');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (755,'Name','Аталышы','label.orderDocuments.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (756,'type','Туру','label.orderDocuments.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (757,'Name','Аталышы','label.orderDocument.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (758,'type','Туру','label.orderDocument.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (759,'Document','Документ','label.orderDocument.add','Документ');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (760,'type','Туру','label.orderDocument.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (761,'Number','Номери','label.agreement.number','Номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (762,'Date','Датасы','label.agreement.date','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (763,'Reg number','Каттоо номери','label.agreement.collateralOfficeRegNumber','Регистрационный номер');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (764,'Reg Date','Каттоо датасы','label.agreement.collateralOfficeRegDate','Дата регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (765,'Notary reg number','Нотариус каттоо номери','label.agreement.notaryOfficeRegNumber','Номер нотариальной регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (766,'Notary reg date','Нотариус каттоо датасы','label.agreement.notaryOfficeRegDate','Дата нотариальной регистрации');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (767,'Arrest reg number','Арест каттоо номери','label.agreement.arrestRegNumber','Номер ареста');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (768,'Arrest reg date','Арест каттоо датасы','label.agreement.arrestRegDate','Дата ареста');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (769,'ID','ID','label.collateralItem.id','ID');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (770,'name','Аталышы','label.collateralItem.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (771,'description','Тушундурмосу','label.collateralItem.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (772,'type','Туру','label.collateralItem.type','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (773,'quantity','Саны','label.collateralItem.quantity','Кол-во');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (774,'quantityType','Бирдиги','label.collateralItem.quantityType','Ед.изм.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (775,'Collateral value','Куроо баасы','label.collateralItem.collateralValue','Зал.ст.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (776,'Estimated value','Бычылган баасы','label.collateralItem.estimatedValue','Оценоч.ст.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (777,'Condition','Акыбалы','label.collateralItem.conditionType','Состояние');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (778,'№1','№1','label.collateralItem.details1','№1');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (779,'№2','№2','label.collateralItem.details2','№2');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (780,'№3','№3','label.collateralItem.details3','№3');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (781,'№4','№4','label.collateralItem.details4','№4');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (782,'№5','№5','label.collateralItem.details5','№5');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (783,'№6','№6','label.collateralItem.details6','№6');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (784,'document','документ','label.collateralItem.document','документ');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (785,'incomplete_reason','Себеби','label.collateralItem.incomplete_reason','Причина');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (786,'goods_type','Туру','label.collateralItem.goods_type','вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (787,'goods_address','Адреси','label.collateralItem.goods_address','адрес');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (788,'goods_id','Коду','label.collateralItem.goods_id','ид код');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (789,'arrest_by','Камакка алган','label.collateralItem.arrest_by','наложен арест');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (790,'name','Аталышы','label.agreementItem.name','Наименование');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (791,'description','Тушундурмосу','label.agreementItem.description','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (792,'type','Туру','label.agreementItem.itemType','Вид');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (793,'quantity','Саны','label.agreementItem.quantity','Кол-во');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (794,'quantityType','Бирдиги','label.agreementItem.quantityType','Ед.изм.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (795,'Collateral value','Куроо баасы','label.agreementItem.collateralValue','Зал.ст.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (796,'Estimated value','Бычылган баасы','label.agreementItem.estimatedValue','Оценоч.ст.');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (797,'Condition','Акыбалы','label.agreementItem.conditionType','Состояние');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (798,'Add item','Куроо кошуу','label.agreement.addItem','Добавить предмет залога');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (799,'arrestFreeInfo','Куроодон чыгаруу маалыматы','label.collateralItem.arrestFreeInfo','Информация о снятии с залога');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (800,'Add arrest free','Куроодон чыгаруу','label.arrestFreeInfo.add','Снять с залога');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (801,'on Date','Куроо чыккан датасы','label.arrestFreeInfo.onDate','Дата снятия');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (802,'Arrest free by','Куроодон чыгарган','label.arrestFreeInfo.arrestFreeBy','Снято');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (803,'Add inspection','Куроо текшеруусун кошуу','label.itemInspectionResult.add','Добавить акт обследования');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (804,'Date','Датасы','label.inpection.onDate','Дата обследования');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (805,'result','Жыйынтыгы','label.inpection.type','Результат');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (806,'Date','Датасы','label.arrestFree.onDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (807,'arrestFreeBy','Куроодон чыгарган','label.arrestFree.arrestFreeBy','Снято');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (808,'Inspection Result','Куроо текшеруусу','label.inspectionResult.add','Акт обследования');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (809,'Date','Датасы','label.inspectionResult.onDate','Дата');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (810,'Details','Тушундурмосу','label.inspectionResult.details','Примечание');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (811,'Type','Жыйынтыгы','label.inspectionResult.type','Результат');
INSERT INTO `message_resource` (`id`,`eng`,`kgz`,`messageKey`,`rus`) VALUES (812,'Arrest free','Куроодон чыгаруу','label.arrestFree.add','Снятие залога');
