



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
















/* ADD ENTITY LIST STATE AND TYPE */

INSERT INTO `mfloan`.`appliedEntityListState` (`version`,`name`) VALUES (1,'Разнарядка');
INSERT INTO `mfloan`.`appliedEntityListState` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`appliedEntityListType` (`version`,`name`) VALUES (1,'Список получателей');
INSERT INTO `mfloan`.`appliedEntityListType` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`appliedEntityState` (`version`,`name`) VALUES (1,'На стадии оформления');
INSERT INTO `mfloan`.`appliedEntityState` (`version`,`name`) VALUES (1,'Оформление завершено');
INSERT INTO `mfloan`.`appliedEntityState` (`version`,`name`) VALUES (1,'Отказ получателя');
INSERT INTO `mfloan`.`appliedEntityState` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`documentPackageState` (`version`,`name`) VALUES (1,'На стадии комплектации');
INSERT INTO `mfloan`.`documentPackageState` (`version`,`name`) VALUES (1,'Комплектация завершена');

INSERT INTO `mfloan`.`documentPackageType` (`version`,`name`) VALUES (1,'Кредитный пакет документации');
INSERT INTO `mfloan`.`documentPackageType` (`version`,`name`) VALUES (1,'Залоговый пакет документации');
INSERT INTO `mfloan`.`documentPackageType` (`version`,`name`) VALUES (1,'Пакет документации для перевод долга');
INSERT INTO `mfloan`.`documentPackageType` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`entityDocumentState` (`version`,`name`) VALUES (1,'На стадии коплектации');
INSERT INTO `mfloan`.`entityDocumentState` (`version`,`name`) VALUES (1,'Копмлектация завершена');
INSERT INTO `mfloan`.`entityDocumentState` (`version`,`name`) VALUES (1,'На стадии проверки');
INSERT INTO `mfloan`.`entityDocumentState` (`version`,`name`) VALUES (1,'Проверка зварешена');

INSERT INTO `mfloan`.`entityDocumentRegisteredBy` (`version`,`name`) VALUES (1,'ГРС');
INSERT INTO `mfloan`.`entityDocumentRegisteredBy` (`version`,`name`) VALUES (1,'ЦЗРК');
INSERT INTO `mfloan`.`entityDocumentRegisteredBy` (`version`,`name`) VALUES (1,'Нотариус');
INSERT INTO `mfloan`.`entityDocumentRegisteredBy` (`version`,`name`) VALUES (1,'Другое');

INSERT INTO `mfloan`.`orderDocumentType` (`version`,`name`) VALUES (1,'Кредитный договор');
INSERT INTO `mfloan`.`orderDocumentType` (`version`,`name`) VALUES (1,'Залоговый договор договор');
INSERT INTO `mfloan`.`orderDocumentType` (`version`,`name`) VALUES (1,'Паспорт или свидетельство о регистрации');

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





INSERT INTO `mfloan`.`orgForm` (`version`,`name`) VALUES (1,'Org Form 1');






INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Не подтвержден');
INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Не наступивший');
INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Наступивший');
INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Непогашенный');
INSERT INTO `mfloan`.`installmentState` (`version`,`name`) VALUES (1,'Погашенный');

INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по задолженности', 'LOAN_SUMMARY');
INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по погашениям', 'LOAN_PAYMENT');
INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по графикам', 'LOAN_SCHEDULE');
INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по плану', 'LOAN_PLAN');
INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по залогу', 'COLLATERAL_ITEM');
INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по взысканию', 'COLLECTION_PHASE');

INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('Дата расчета');
INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('Дата состояния');
INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('Единица валюты');
INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('1. разрез');
INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('2. разрез');
INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('3. разрез');
INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('4. разрез');
INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('5. разрез');
INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('6. разрез');


INSERT INTO `mfloan`.`generation_parameter` (`date`, `name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2018-01-01', 'на 01.01.2018г.', '1', '1', '0', '1');

INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (область)', '2', '1', '0', '4');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (область)', '2', '1', '0', '5');

INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (район)', '2', '2', '0', '4');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (район)', '2', '2', '0', '5');

INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (заемщик)', '2', '3', '0', '4');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (заемщик)', '2', '3', '0', '5');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('3. разрез (заемщик)', '2', '3', '0', '6');

INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (кредит)', '2', '4', '0', '4');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (кредит)', '2', '4', '0', '5');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('3. разрез (кредит)', '2', '4', '0', '6');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('4. разрез (кредит)', '2', '4', '0', '7');

INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (погашение)', '2', '5', '0', '4');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (погашение)', '2', '5', '0', '5');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('3. разрез (погашение)', '2', '5', '0', '6');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('4. разрез (погашение)', '2', '5', '0', '7');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('5. разрез (погашение)', '2', '5', '0', '8');


INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрезе (отрасль)', '2', '6', '0', '4');
INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрезе (отрасль)', '2', '6', '0', '4');


INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По республике', '1');

INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Баткенской области', '1');
INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Ошской области', '1');
INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Таласской области', '1');
INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Нарынской области', '1');
INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Иссык-Кульской области', '1');
INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Чуйской области', '1');
INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По г.Бишкек', '1');

INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('1', '1');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('2', '1');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('3', '1');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('4', '1');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('5', '1');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('6', '1');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('7', '1');

INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('1', '2');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('2', '3');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('3', '4');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('4', '5');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('5', '6');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('6', '7');
INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('7', '8');

INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По республике', '1');
INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Баткенской области', '2');
INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Ошской области', '3');
INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Таласской области', '4');
INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Нарынской области', '5');
INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Иссык-Кульской области', '6');
INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Чуйской области', '7');
INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По г.Бишкек', '8');

INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по республике (область, район, заемщик, кредит', '1');
INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по Баткенской области (область, район, заемщик, кредит', '1');
INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по Ошской области (область, район, заемщик, кредит', '1');

INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по республике (отрасль, область, заемщик, кредит', '1');

INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('1', '1');
INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('2', '2');
INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('3', '3');
INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('4', '1');

INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '1');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '2');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '5');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '8');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '12');

INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '1');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '2');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '5');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '8');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '12');

INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '1');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '2');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '5');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '8');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '12');


INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '1');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '18');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '3');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '8');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '12');




INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по республике (область, район, заемщик, кредит', '2');
INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по Баткенской области (область, район, заемщик, кредит', '2');
INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по Ошской области (область, район, заемщик, кредит', '2');

INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по республике (отрасль, область, заемщик, кредит', '2');

INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('5', '1');
INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('6', '2');
INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('7', '3');
INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('8', '1');

INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '1');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '2');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '5');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '8');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '12');

INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '1');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '2');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '5');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '8');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '12');

INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '1');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '2');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '5');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '8');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '12');


INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '1');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '18');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '3');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '8');
INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '12');







DROP TABLE IF EXISTS mfloan.person_view;
DROP TABLE IF EXISTS mfloan.organization_view;
DROP TABLE IF EXISTS mfloan.owner_view;
DROP TABLE IF EXISTS mfloan.debtor_view;
DROP TABLE IF EXISTS mfloan.loan_view;
DROP TABLE IF EXISTS mfloan.payment_view;

DROP TABLE IF EXISTS mfloan.collateral_agreement_view;
DROP TABLE IF EXISTS mfloan.collateral_item_view;

DROP TABLE IF EXISTS mfloan.collection_phase_view;
DROP TABLE IF EXISTS mfloan.collection_procedure_debtor_view;
DROP TABLE IF EXISTS mfloan.collection_procedure_view;
DROP TABLE IF EXISTS mfloan.loan_detailed_summary_view;
DROP TABLE IF EXISTS mfloan.loan_summary_view;

DROP TABLE IF EXISTS mfloan.payment_schedule_view;






CREATE VIEW person_view AS
  SELECT
    `p`.`id`                 AS `v_person_id`,
    `p`.`description`        AS `v_person_description`,
    `p`.`enabled`            AS `v_person_enabled`,
    `p`.`name`               AS `v_person_name`,
    `p`.`address_id`         AS `v_person_address_id`,
    `p`.`contact_id`         AS `v_person_contact_id`,
    `p`.`identity_doc_id`    AS `v_person_identity_doc_id`,
    `a`.`line`               AS `v_person_address_line`,
    `a`.`address_details_id` AS `v_person_address_details_id`,
    `a`.`aokmotu_id`         AS `v_person_aokmotu_id`,
    `a`.`district_id`        AS `v_person_district_id`,
    `a`.`region_id`          AS `v_person_region_id`,
    `a`.`village_id`         AS `v_person_village_id`
  FROM (`mfloan`.`address` `a`
    JOIN `mfloan`.`person` `p` ON ((`a`.`id` = `p`.`address_id`)));


CREATE VIEW organization_view AS
  SELECT
    `o`.`id`              AS `v_organization_id`,
    `o`.`description`     AS `v_organization_description`,
    `o`.`enabled`         AS `v_organization_enabled`,
    `o`.`name`            AS `v_organization_name`,
    `o`.`contact_id`      AS `v_organization_contact_id`,
    `o`.`identity_doc_id` AS `v_organization_identity_doc_id`,
    `o`.`org_form_id`     AS `v_organization_org_form_id`,
    `a`.`id`              AS `v_organization_address_id`,
    `a`.`region_id`       AS `v_organization_region_id`,
    `a`.`district_id`     AS `v_organization_district_id`,
    `a`.`aokmotu_id`      AS `v_organization_aokmotu_id`,
    `a`.`village_id`      AS `v_organization_village_id`
  FROM (`mfloan`.`address` `a`
    JOIN `mfloan`.`organization` `o` ON ((`a`.`id` = `o`.`address_id`)));


CREATE VIEW owner_view AS
  SELECT
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_address_id`
            FROM `mfloan`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_address_id`
           FROM `mfloan`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_address_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_region_id`
            FROM `mfloan`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_region_id`
           FROM `mfloan`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_region_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_district_id`
            FROM `mfloan`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_district_id`
           FROM `mfloan`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_district_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_aokmotu_id`
            FROM `mfloan`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_aokmotu_id`
           FROM `mfloan`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_aokmotu_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_village_id`
            FROM `mfloan`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_village_id`
           FROM `mfloan`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_village_id`,
    `o`.`id`                                                       AS `id`,
    `o`.`version`                                                  AS `version`,
    `o`.`entityId`                                                 AS `entityId`,
    `o`.`name`                                                     AS `name`,
    `o`.`ownerType`                                                AS `ownerType`
  FROM `mfloan`.`owner` `o`;




CREATE VIEW debtor_view AS
  SELECT
    `d`.`id`                   AS `v_debtor_id`,
    `d`.`name`                 AS `v_debtor_name`,
    `d`.`debtorTypeId`         AS `v_debtor_type_id`,
    `d`.`orgFormId`            AS `v_debtor_org_form_id`,
    `d`.`ownerId`              AS `v_debtor_owner_id`,
    `d`.`workSectorId`         AS `v_debtor_work_sector_id`,
    `ov`.id    AS `v_debtor_entity_id`,
    `ov`.ownerType   AS `v_debtor_owner_type`,
    `ov`.`v_owner_address_id`  AS `v_debtor_address_id`,
    `ov`.`v_owner_region_id`   AS `v_debtor_region_id`,
    `ov`.`v_owner_district_id` AS `v_debtor_district_id`,
    `ov`.`v_owner_aokmotu_id`  AS `v_debtor_aokmotu_id`,
    `ov`.`v_owner_village_id`  AS `v_debtor_village_id`
  FROM `debtor` `d`,owner_view `ov`
  where d.ownerId = ov.id;

CREATE VIEW loan_view AS
  SELECT
    `l`.`id`                       AS `v_loan_id`,
    `l`.`amount`                   AS `v_loan_amount`,
    `l`.`hasSubLoan`               AS `v_loan_has_subLoan`,
    `l`.`regDate`                  AS `v_loan_reg_date`,
    `l`.`regNumber`                AS `v_loan_reg_number`,
    `l`.`supervisorId`             AS `v_loan_supervisor_id`,
    `l`.`collectionPhaseId`        AS `v_loan_collection_phase_id`,
    `l`.`creditOrderId`            AS `v_loan_credit_order_id`,
    `l`.`currencyId`               AS `v_loan_currency_id`,
    `l`.`debtorId`                 AS `v_loan_debtor_id`,
    `l`.`loanStateId`              AS `v_loan_state_id`,
    `l`.`loanTypeId`               AS `v_loan_type_id`,
    `l`.`parentLoanId`             AS `v_loan_parent_loan_id`,
    `dv`.`v_debtor_id`             AS `v_debtor_id`,
    `dv`.`v_debtor_name`           AS `v_debtor_name`,
    `dv`.`v_debtor_type_id`        AS `v_debtor_type_id`,
    `dv`.`v_debtor_org_form_id`    AS `v_debtor_org_form_id`,
    `dv`.`v_debtor_owner_id`       AS `v_debtor_owner_id`,
    `dv`.`v_debtor_work_sector_id` AS `v_debtor_work_sector_id`,
    `dv`.`v_debtor_entity_id`      AS `v_debtor_entity_id`,
    `dv`.`v_debtor_owner_type`     AS `v_debtor_owner_type`,
    `dv`.`v_debtor_address_id`     AS `v_debtor_address_id`,
    `dv`.`v_debtor_region_id`      AS `v_debtor_region_id`,
    `dv`.`v_debtor_district_id`    AS `v_debtor_district_id`,
    `dv`.`v_debtor_aokmotu_id`     AS `v_debtor_aokmotu_id`,
    `dv`.`v_debtor_village_id`     AS `v_debtor_village_id`,
    `co`.`creditOrderTypeId`       AS `v_credit_order_type_id`,
    `co`.`regNumber`               AS `v_credit_order_regNumber`,
    `co`.`regDate`                 AS `v_credit_order_regDate`,
    `r`.`name`                     AS `v_region_name`,
    `d`.`name`                     AS `v_district_name`,
    `ws`.`name`                    AS `v_work_sector_name`
  FROM (((((`mfloan`.`loan` `l`
    JOIN `mfloan`.`debtor_view` `dv` ON ((`dv`.`v_debtor_id` = `l`.`debtorId`))) JOIN `mfloan`.`creditOrder` `co`
      ON ((`co`.`id` = `l`.`creditOrderId`))) JOIN `mfloan`.`region` `r`
      ON ((`r`.`id` = `dv`.`v_debtor_region_id`))) JOIN `mfloan`.`district` `d`
      ON ((`d`.`id` = `dv`.`v_debtor_district_id`))) JOIN `mfloan`.`workSector` `ws`
      ON ((`ws`.`id` = `dv`.`v_debtor_work_sector_id`)));



CREATE VIEW collateral_agreement_view AS
  SELECT
    `ca`.`id`                        AS `v_ca_id`,
    `ca`.`version`                   AS `v_ca_version`,
    `ca`.`agreementDate`             AS `v_ca_agreementDate`,
    `ca`.`agreementNumber`           AS `v_ca_agreementNumber`,
    `ca`.`arrestRegDate`             AS `v_ca_arrestRegDate`,
    `ca`.`arrestRegNumber`           AS `v_ca_arrestRegNumber`,
    `ca`.`collateralOfficeRegDate`   AS `v_ca_collateralOfficeRegDate`,
    `ca`.`collateralOfficeRegNumber` AS `v_ca_collateralOfficeRegNumber`,
    `ca`.`notaryOfficeRegDate`       AS `v_ca_notaryOfficeRegDate`,
    `ca`.`notaryOfficeRegNumber`     AS `v_ca_notaryOfficeRegNumber`,
    `ca`.`ownerId`                   AS `v_ca_ownerId`,
    `dv`.`v_debtor_id`               AS `v_debtor_id`,
    `dv`.`v_debtor_name`             AS `v_debtor_name`,
    `dv`.`v_debtor_type_id`          AS `v_debtor_type_id`,
    `dv`.`v_debtor_org_form_id`      AS `v_debtor_org_form_id`,
    `dv`.`v_debtor_owner_id`         AS `v_debtor_owner_id`,
    `dv`.`v_debtor_work_sector_id`   AS `v_debtor_work_sector_id`,
    `dv`.`v_debtor_entity_id`        AS `v_debtor_entity_id`,
    `dv`.`v_debtor_owner_type`       AS `v_debtor_owner_type`,
    `dv`.`v_debtor_address_id`       AS `v_debtor_address_id`,
    `dv`.`v_debtor_region_id`        AS `v_debtor_region_id`,
    `dv`.`v_debtor_district_id`      AS `v_debtor_district_id`,
    `dv`.`v_debtor_aokmotu_id`       AS `v_debtor_aokmotu_id`,
    `dv`.`v_debtor_village_id`       AS `v_debtor_village_id`,
    `r`.`name`                       AS `v_region_name`,
    `d`.`name`                       AS `v_district_name`,
    `ws`.`name`                      AS `v_work_sector_name`
  FROM ((((((`mfloan`.`collateralAgreement` `ca`
    JOIN `mfloan`.`debtor_view` `dv`) JOIN `mfloan`.`owner` `o1`) JOIN `mfloan`.`owner` `o2`) JOIN
    `mfloan`.`region` `r`) JOIN `mfloan`.`district` `d`) JOIN `mfloan`.`workSector` `ws`)
  WHERE (
    (`ca`.`ownerId` = `o1`.`id`) AND (`dv`.`v_debtor_owner_id` = `o2`.`id`) AND (`o1`.`entityId` = `o2`.`entityId`) AND
    (`r`.`id` = `dv`.`v_debtor_region_id`) AND (`d`.`id` = `dv`.`v_debtor_district_id`) AND
    (`ws`.`id` = `dv`.`v_debtor_work_sector_id`));




CREATE VIEW collateral_item_view AS
  SELECT
    `cav`.`v_ca_id`                        AS `v_ca_id`,
    `cav`.`v_ca_version`                   AS `v_ca_version`,
    `cav`.`v_ca_agreementDate`             AS `v_ca_agreementDate`,
    `cav`.`v_ca_agreementNumber`           AS `v_ca_agreementNumber`,
    `cav`.`v_ca_arrestRegDate`             AS `v_ca_arrestRegDate`,
    `cav`.`v_ca_arrestRegNumber`           AS `v_ca_arrestRegNumber`,
    `cav`.`v_ca_collateralOfficeRegDate`   AS `v_ca_collateralOfficeRegDate`,
    `cav`.`v_ca_collateralOfficeRegNumber` AS `v_ca_collateralOfficeRegNumber`,
    `cav`.`v_ca_notaryOfficeRegDate`       AS `v_ca_notaryOfficeRegDate`,
    `cav`.`v_ca_notaryOfficeRegNumber`     AS `v_ca_notaryOfficeRegNumber`,
    `cav`.`v_ca_ownerId`                   AS `v_ca_ownerId`,
    `cav`.`v_debtor_id`                    AS `v_debtor_id`,
    `cav`.`v_debtor_name`                  AS `v_debtor_name`,
    `cav`.`v_debtor_type_id`               AS `v_debtor_type_id`,
    `cav`.`v_debtor_org_form_id`           AS `v_debtor_org_form_id`,
    `cav`.`v_debtor_owner_id`              AS `v_debtor_owner_id`,
    `cav`.`v_debtor_work_sector_id`        AS `v_debtor_work_sector_id`,
    `cav`.`v_debtor_entity_id`             AS `v_debtor_entity_id`,
    `cav`.`v_debtor_owner_type`            AS `v_debtor_owner_type`,
    `cav`.`v_debtor_address_id`            AS `v_debtor_address_id`,
    `cav`.`v_debtor_region_id`             AS `v_debtor_region_id`,
    `cav`.`v_debtor_district_id`           AS `v_debtor_district_id`,
    `cav`.`v_debtor_aokmotu_id`            AS `v_debtor_aokmotu_id`,
    `cav`.`v_debtor_village_id`            AS `v_debtor_village_id`,
    `cav`.`v_region_name`                  AS `v_region_name`,
    `cav`.`v_district_name`                AS `v_district_name`,
    `cav`.`v_work_sector_name`             AS `v_work_sector_name`,
    `ci`.`id`                              AS `v_ci_id`,
    `ci`.`version`                         AS `v_ci_version`,
    `ci`.`collateralValue`                 AS `v_ci_collateralValue`,
    `ci`.`demand_rate`                     AS `v_ci_demand_rate`,
    `ci`.`description`                     AS `v_ci_description`,
    `ci`.`estimatedValue`                  AS `v_ci_estimatedValue`,
    `ci`.`name`                            AS `v_ci_name`,
    `ci`.`quantity`                        AS `v_ci_quantity`,
    `ci`.`risk_rate`                       AS `v_ci_risk_rate`,
    `ci`.`collateralAgreementId`           AS `v_ci_collateralAgreementId`,
    `ci`.`conditionTypeId`                 AS `v_ci_conditionTypeId`,
    `ci`.`itemTypeId`                      AS `v_ci_itemTypeId`,
    `ci`.`quantityTypeId`                  AS `v_ci_quantityTypeId`
  FROM (`mfloan`.`collateral_agreement_view` `cav`
    JOIN `mfloan`.`collateralItem` `ci`)
  WHERE (`ci`.`collateralAgreementId` = `cav`.`v_ca_id`);



CREATE VIEW collection_phase_view AS
  SELECT
    `cpv`.`v_cp_id`                 AS `v_cp_id`,
    `cpv`.`v_cp_version`            AS `v_cp_version`,
    `cpv`.`v_cp_closeDate`          AS `v_cp_closeDate`,
    `cpv`.`v_cp_lastPhase`          AS `v_cp_lastPhase`,
    `cpv`.`v_cp_lastStatusId`       AS `v_cp_lastStatusId`,
    `cpv`.`v_cp_startDate`          AS `v_cp_startDate`,
    `cpv`.`v_cp_procedureStatusId`  AS `v_cp_procedureStatusId`,
    `cpv`.`v_cp_procedureTypeId`    AS `v_cp_procedureTypeId`,
    `cpv`.`v_debtor_id`             AS `v_debtor_id`,
    `cpv`.`v_debtor_name`           AS `v_debtor_name`,
    `cpv`.`v_debtor_type_id`        AS `v_debtor_type_id`,
    `cpv`.`v_debtor_org_form_id`    AS `v_debtor_org_form_id`,
    `cpv`.`v_debtor_owner_id`       AS `v_debtor_owner_id`,
    `cpv`.`v_debtor_work_sector_id` AS `v_debtor_work_sector_id`,
    `cpv`.`v_debtor_entity_id`      AS `v_debtor_entity_id`,
    `cpv`.`v_debtor_owner_type`     AS `v_debtor_owner_type`,
    `cpv`.`v_debtor_address_id`     AS `v_debtor_address_id`,
    `cpv`.`v_debtor_region_id`      AS `v_debtor_region_id`,
    `cpv`.`v_debtor_district_id`    AS `v_debtor_district_id`,
    `cpv`.`v_debtor_aokmotu_id`     AS `v_debtor_aokmotu_id`,
    `cpv`.`v_debtor_village_id`     AS `v_debtor_village_id`,
    `cph`.`id`                      AS `v_cph_id`,
    `cph`.`version`                 AS `v_cph_version`,
    `cph`.`closeDate`               AS `v_cph_closeDate`,
    `cph`.`lastEvent`               AS `v_cph_lastEvent`,
    `cph`.`lastStatusId`            AS `v_cph_lastStatusId`,
    `cph`.`startDate`               AS `v_cph_startDate`,
    `cph`.`collectionProcedureId`   AS `v_cph_collectionProcedureId`,
    `cph`.`phaseStatusId`           AS `v_cph_phaseStatusId`,
    `cph`.`phaseTypeId`             AS `v_cph_phaseTypeId`,
    `r`.`name`                      AS `v_region_name`,
    `d`.`name`                      AS `v_district_name`,
    `ws`.`name`                     AS `v_work_sector_name`,
    ( select sum(phd.startTotalAmount) from phaseDetails phd where phd.collectionPhaseId = cph.id) as v_cph_start_total_amount
  FROM `mfloan`.`collection_procedure_view` `cpv`
    JOIN `mfloan`.`collectionPhase` `cph`
    JOIN `mfloan`.`region` `r`
    JOIN `mfloan`.`district` `d`
    JOIN `mfloan`.`workSector` `ws`
  WHERE ((`cpv`.`v_cp_id` = `cph`.`collectionProcedureId`) AND (`cpv`.`v_debtor_region_id` = `r`.`id`) AND
         (`cpv`.`v_debtor_district_id` = `d`.`id`) AND (`cpv`.`v_debtor_work_sector_id` = `ws`.`id`));




CREATE VIEW collection_procedure_debtor_view AS
  SELECT DISTINCT
    `pr`.`id` AS `v_cp_id`,
    `d`.`id`  AS `v_debtor_id`
  FROM `mfloan`.`collectionProcedure` `pr`
    JOIN `mfloan`.`collectionPhase` `ph`
    JOIN `mfloan`.`phaseDetails` `pd`
    JOIN `mfloan`.`loan` `l`
    JOIN `mfloan`.`debtor` `d`
  WHERE ((`ph`.`collectionProcedureId` = `pr`.`id`) AND (`ph`.`id` = `pd`.`id`) AND (`pd`.`loan_id` = `l`.`id`) AND
         (`l`.`debtorId` = `d`.`id`));



CREATE VIEW collection_procedure_view AS
  SELECT
    `cp`.`id`                      AS `v_cp_id`,
    `cp`.`version`                 AS `v_cp_version`,
    `cp`.`closeDate`               AS `v_cp_closeDate`,
    `cp`.`lastPhase`               AS `v_cp_lastPhase`,
    `cp`.`lastStatusId`            AS `v_cp_lastStatusId`,
    `cp`.`startDate`               AS `v_cp_startDate`,
    `cp`.`procedureStatusId`       AS `v_cp_procedureStatusId`,
    `cp`.`procedureTypeId`         AS `v_cp_procedureTypeId`,
    `dv`.`v_debtor_id`             AS `v_debtor_id`,
    `dv`.`v_debtor_name`           AS `v_debtor_name`,
    `dv`.`v_debtor_type_id`        AS `v_debtor_type_id`,
    `dv`.`v_debtor_org_form_id`    AS `v_debtor_org_form_id`,
    `dv`.`v_debtor_owner_id`       AS `v_debtor_owner_id`,
    `dv`.`v_debtor_work_sector_id` AS `v_debtor_work_sector_id`,
    `dv`.`v_debtor_entity_id`      AS `v_debtor_entity_id`,
    `dv`.`v_debtor_owner_type`     AS `v_debtor_owner_type`,
    `dv`.`v_debtor_address_id`     AS `v_debtor_address_id`,
    `dv`.`v_debtor_region_id`      AS `v_debtor_region_id`,
    `dv`.`v_debtor_district_id`    AS `v_debtor_district_id`,
    `dv`.`v_debtor_aokmotu_id`     AS `v_debtor_aokmotu_id`,
    `dv`.`v_debtor_village_id`     AS `v_debtor_village_id`
  FROM ((`mfloan`.`collectionProcedure` `cp`
    JOIN `mfloan`.`debtor_view` `dv`) JOIN `mfloan`.`collection_procedure_debtor_view` `cpdv`)
  WHERE ((`cp`.`id` = `cpdv`.`v_cp_id`) AND (`dv`.`v_debtor_id` = `cpdv`.`v_debtor_id`));




CREATE VIEW loan_detailed_summary_view AS
  SELECT
    `lv`.`v_loan_id`                              AS `v_loan_id`,
    `lv`.`v_loan_amount`                          AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`                     AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`                        AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`                      AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`                   AS `v_loan_supervisor_id`,
    `lv`.`v_loan_collection_phase_id`             AS `v_loan_collection_phase_id`,
    `lv`.`v_loan_credit_order_id`                 AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`                     AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`                       AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`                        AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`                         AS `v_loan_type_id`,
    `lv`.`v_loan_parent_loan_id`                  AS `v_loan_parent_loan_id`,
    `lv`.`v_debtor_id`                            AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`                       AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`                   AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`                AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`                          AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`                     AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`                    AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`                     AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`                   AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`                    AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`                    AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`                 AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_regNumber`               AS `v_credit_order_regNumber`,
    `lv`.`v_credit_order_regDate`                 AS `v_credit_order_regDate`,
    `lv`.`v_region_name`                          AS `v_region_name`,
    `lv`.`v_district_name`                        AS `v_district_name`,
    `lv`.`v_work_sector_name`                     AS `v_work_sector_name`,
    `lds`.`id`                                    AS `v_lds_id`,
    `lds`.`version`                               AS `v_lds_version`,
    `lds`.`collectedInterestDisbursed`            AS `v_lds_collectedInterestDisbursed`,
    `lds`.`collectedInterestPayment`              AS `v_lds_collectedInterestPayment`,
    `lds`.`collectedPenaltyDisbursed`             AS `v_lds_collectedPenaltyDisbursed`,
    `lds`.`collectedPenaltyPayment`               AS `v_lds_collectedPenaltyPayment`,
    `lds`.`daysInPeriod`                          AS `v_lds_daysInPeriod`,
    `lds`.`disbursement`                          AS `v_lds_disbursement`,
    `lds`.`interestAccrued`                       AS `v_lds_interestAccrued`,
    `lds`.`interestOutstanding`                   AS `v_lds_interestOutstanding`,
    `lds`.`interestOverdue`                       AS `v_lds_interestOverdue`,
    `lds`.`interestPaid`                          AS `v_lds_interestPaid`,
    `lds`.`interestPayment`                       AS `v_lds_interestPayment`,
    `lds`.`onDate`                                AS `v_lds_onDate`,
    `lds`.`penaltyAccrued`                        AS `v_lds_penaltyAccrued`,
    `lds`.`penaltyOutstanding`                    AS `v_lds_penaltyOutstanding`,
    `lds`.`penaltyOverdue`                        AS `v_lds_penaltyOverdue`,
    `lds`.`penaltyPaid`                           AS `v_lds_penaltyPaid`,
    `lds`.`principalOutstanding`                  AS `v_lds_principalOutstanding`,
    `lds`.`principalOverdue`                      AS `v_lds_principalOverdue`,
    `lds`.`principalPaid`                         AS `v_lds_principalPaid`,
    `lds`.`principalPayment`                      AS `v_lds_principalPayment`,
    `lds`.`principalWriteOff`                     AS `v_lds_principalWriteOff`,
    `lds`.`totalCollectedInterestPayment`         AS `v_lds_totalCollectedInterestPayment`,
    `lds`.`totalCollectedPenaltyPayment`          AS `v_lds_totalCollectedPenaltyPayment`,
    `lds`.`totalDisbursement`                     AS `v_lds_totalDisbursement`,
    `lds`.`totalInterestAccrued`                  AS `v_lds_totalInterestAccrued`,
    `lds`.`totalInterestAccruedOnInterestPayment` AS `v_lds_totalInterestAccruedOnInterestPayment`,
    `lds`.`totalInterestPaid`                     AS `v_lds_totalInterestPaid`,
    `lds`.`totalInterestPayment`                  AS `v_lds_totalInterestPayment`,
    `lds`.`totalPenaltyAccrued`                   AS `v_lds_totalPenaltyAccrued`,
    `lds`.`totalPenaltyPaid`                      AS `v_lds_totalPenaltyPaid`,
    `lds`.`totalPrincipalPaid`                    AS `v_lds_totalPrincipalPaid`,
    `lds`.`totalPrincipalPayment`                 AS `v_lds_totalPrincipalPayment`,
    `lds`.`totalPrincipalWriteOff`                AS `v_lds_totalPrincipalWriteOff`,
    `lds`.`loanId`                                AS `v_lds_loanId`
  FROM `mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`loanDetailedSummary` `lds`
  WHERE (`lds`.`loanId` = `lv`.`v_loan_id`);





CREATE VIEW loan_summary_view AS
  SELECT
    `lv`.`v_loan_id`                  AS `v_loan_id`,
    `lv`.`v_loan_amount`              AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`         AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`            AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`          AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`       AS `v_loan_supervisor_id`,
    `lv`.`v_loan_collection_phase_id` AS `v_loan_collection_phase_id`,
    `lv`.`v_loan_credit_order_id`     AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`         AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`           AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`            AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`             AS `v_loan_type_id`,
    `lv`.`v_loan_parent_loan_id`      AS `v_loan_parent_loan_id`,
    `lv`.`v_debtor_id`                AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`           AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`       AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`    AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`              AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`         AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`        AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`         AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`       AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`        AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`        AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`     AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_regNumber`   AS `v_credit_order_regNumber`,
    `lv`.`v_credit_order_regDate`     AS `v_credit_order_regDate`,
    `lv`.`v_region_name`              AS `v_region_name`,
    `lv`.`v_district_name`            AS `v_district_name`,
    `lv`.`v_work_sector_name`         AS `v_work_sector_name`,
    `ls`.`id`                         AS `v_ls_id`,
    `ls`.`version`                    AS `v_ls_version`,
    `ls`.`loanAmount`                 AS `v_ls_loanAmount`,
    `ls`.`onDate`                     AS `v_ls_onDate`,
    `ls`.`outstadingFee`              AS `v_ls_outstadingFee`,
    `ls`.`outstadingInterest`         AS `v_ls_outstadingInterest`,
    `ls`.`outstadingPenalty`          AS `v_ls_outstadingPenalty`,
    `ls`.`outstadingPrincipal`        AS `v_ls_outstadingPrincipal`,
    `ls`.`overdueFee`                 AS `v_ls_overdueFee`,
    `ls`.`overdueInterest`            AS `v_ls_overdueInterest`,
    `ls`.`overduePenalty`             AS `v_ls_overduePenalty`,
    `ls`.`overduePrincipal`           AS `v_ls_overduePrincipal`,
    `ls`.`paidFee`                    AS `v_ls_paidFee`,
    `ls`.`paidInterest`               AS `v_ls_paidInterest`,
    `ls`.`paidPenalty`                AS `v_ls_paidPenalty`,
    `ls`.`paidPrincipal`              AS `v_ls_paidPrincipal`,
    `ls`.`totalDisbursed`             AS `v_ls_totalDisbursed`,
    `ls`.`totalOutstanding`           AS `v_ls_totalOutstanding`,
    `ls`.`totalOverdue`               AS `v_ls_totalOverdue`,
    `ls`.`totalPaid`                  AS `v_ls_totalPaid`,
    `ls`.`loanId`                     AS `v_ls_loanId`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`loanSummary` `ls`)
  WHERE (`ls`.`loanId` = `lv`.`v_loan_id`);





CREATE VIEW payment_schedule_view AS
  SELECT
    `lv`.`v_loan_id`                  AS `v_loan_id`,
    `lv`.`v_loan_amount`              AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`         AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`            AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`          AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`       AS `v_loan_supervisor_id`,
    `lv`.`v_loan_collection_phase_id` AS `v_loan_collection_phase_id`,
    `lv`.`v_loan_credit_order_id`     AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`         AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`           AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`            AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`             AS `v_loan_type_id`,
    `lv`.`v_loan_parent_loan_id`      AS `v_loan_parent_loan_id`,
    `lv`.`v_debtor_id`                AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`           AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`       AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`    AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`              AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`         AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`        AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`         AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`       AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`        AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`        AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`     AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_regNumber`   AS `v_credit_order_regNumber`,
    `lv`.`v_credit_order_regDate`     AS `v_credit_order_regDate`,
    `lv`.`v_region_name`              AS `v_region_name`,
    `lv`.`v_district_name`            AS `v_district_name`,
    `lv`.`v_work_sector_name`         AS `v_work_sector_name`,
    `ps`.`id`                         AS `v_ps_id`,
    `ps`.`version`                    AS `v_ps_version`,
    `ps`.`collectedInterestPayment`   AS `v_ps_collectedInterestPayment`,
    `ps`.`collectedPenaltyPayment`    AS `v_ps_collectedPenaltyPayment`,
    `ps`.`disbursement`               AS `v_ps_disbursement`,
    `ps`.`expectedDate`               AS `v_ps_expectedDate`,
    `ps`.`interestPayment`            AS `v_ps_interestPayment`,
    `ps`.`principalPayment`           AS `v_ps_principalPayment`,
    `ps`.`installmentStateId`         AS `v_ps_installmentStateId`,
    `ps`.`loanId`                     AS `v_ps_loanId`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`paymentSchedule` `ps`)
  WHERE (`ps`.`loanId` = `lv`.`v_loan_id`);





CREATE VIEW payment_view AS
  SELECT
    `lv`.`v_loan_id`                  AS `v_loan_id`,
    `lv`.`v_loan_amount`              AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`         AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`            AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`          AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`       AS `v_loan_supervisor_id`,
    `lv`.`v_loan_collection_phase_id` AS `v_loan_collection_phase_id`,
    `lv`.`v_loan_credit_order_id`     AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`         AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`           AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`            AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`             AS `v_loan_type_id`,
    `lv`.`v_loan_parent_loan_id`      AS `v_loan_parent_loan_id`,
    `lv`.`v_debtor_id`                AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`           AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`       AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`    AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`              AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`         AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`        AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`         AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`       AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`        AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`        AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`     AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_regNumber`   AS `v_credit_order_regNumber`,
    `lv`.`v_credit_order_regDate`     AS `v_credit_order_regDate`,
    `lv`.`v_region_name`              AS `v_region_name`,
    `lv`.`v_district_name`            AS `v_district_name`,
    `p`.`id`                          AS `v_payment_id`,
    `p`.`fee`                         AS `v_payment_fee`,
    `p`.`interest`                    AS `v_payment_interest`,
    `p`.`number`                      AS `v_payment_number`,
    `p`.`paymentDate`                 AS `v_payment_date`,
    `p`.`penalty`                     AS `v_payment_penalty`,
    `p`.`principal`                   AS `v_payment_principal`,
    `p`.`totalAmount`                 AS `v_payment_total_amount`,
    `p`.`paymentTypeId`               AS `v_payment_type_id`,
    `pt`.`name`                       AS `v_payment_type_name`,
    `p`.exchange_rate                 AS `v_payment_exchange_rate`,
    `p`.in_loan_currency AS `v_payment_in_loan_currency`,
    `lv`.`v_work_sector_name`         AS `v_work_sector_name`
  FROM ((`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`payment` `p` ON ((`p`.`loanId` = `lv`.`v_loan_id`))) JOIN `mfloan`.`paymentType` `pt`
      ON ((`pt`.`id` = `p`.`paymentTypeId`)));


