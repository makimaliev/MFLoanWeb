#
#
# INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по задолженности', 'LOAN_SUMMARY');
# INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по погашениям', 'LOAN_PAYMENT');
# INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по графикам', 'LOAN_SCHEDULE');
# INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по плану', 'LOAN_PLAN');
# INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по залогу', 'COLLATERAL_ITEM');
# INSERT INTO `mfloan`.`report` (`name`, `reportType`) VALUES ('Отчет по взысканию', 'COLLECTION_PHASE');
#
# INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('Дата расчета');
# INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('Дата состояния');
# INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('Единица валюты');
# INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('1. разрез');
# INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('2. разрез');
# INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('3. разрез');
# INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('4. разрез');
# INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('5. разрез');
# INSERT INTO `mfloan`.`generation_parameter_type` (`name`) VALUES ('6. разрез');
#
#
# INSERT INTO `mfloan`.`generation_parameter` (`date`, `name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2018-01-01', 'на 01.01.2018г.', '1', '1', '0', '1');
#
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (область)', '2', '1', '0', '4');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (область)', '2', '1', '0', '5');
#
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (район)', '2', '2', '0', '4');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (район)', '2', '2', '0', '5');
#
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (заемщик)', '2', '3', '0', '4');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (заемщик)', '2', '3', '0', '5');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('3. разрез (заемщик)', '2', '3', '0', '6');
#
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (кредит)', '2', '4', '0', '4');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (кредит)', '2', '4', '0', '5');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('3. разрез (кредит)', '2', '4', '0', '6');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('4. разрез (кредит)', '2', '4', '0', '7');
#
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (погашение)', '2', '5', '0', '4');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (погашение)', '2', '5', '0', '5');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('3. разрез (погашение)', '2', '5', '0', '6');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('4. разрез (погашение)', '2', '5', '0', '7');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('5. разрез (погашение)', '2', '5', '0', '8');
#
#
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрезе (отрасль)', '2', '6', '0', '4');
# INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрезе (отрасль)', '2', '6', '0', '4');
#
#
# INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По республике', '1');
#
# INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Баткенской области', '1');
# INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Ошской области', '1');
# INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Таласской области', '1');
# INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Нарынской области', '1');
# INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Иссык-Кульской области', '1');
# INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По Чуйской области', '1');
# INSERT INTO `mfloan`.`object_list` (`name`, `object_type_id`) VALUES ('По г.Бишкек', '1');
#
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('1', '1');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('2', '1');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('3', '1');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('4', '1');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('5', '1');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('6', '1');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('7', '1');
#
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('1', '2');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('2', '3');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('3', '4');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('4', '5');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('5', '6');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('6', '7');
# INSERT INTO `mfloan`.`object_list_value` (`name`, `object_list_id`) VALUES ('7', '8');
#
# INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По республике', '1');
# INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Баткенской области', '2');
# INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Ошской области', '3');
# INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Таласской области', '4');
# INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Нарынской области', '5');
# INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Иссык-Кульской области', '6');
# INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Чуйской области', '7');
# INSERT INTO `mfloan`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По г.Бишкек', '8');
#
# INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по республике (область, район, заемщик, кредит', '1');
# INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по Баткенской области (область, район, заемщик, кредит', '1');
# INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по Ошской области (область, район, заемщик, кредит', '1');
#
# INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по республике (отрасль, область, заемщик, кредит', '1');
#
# INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('1', '1');
# INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('2', '2');
# INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('3', '3');
# INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('4', '1');
#
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '1');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '2');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '5');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '8');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '12');
#
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '1');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '2');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '5');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '8');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '12');
#
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '1');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '2');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '5');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '8');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '12');
#
#
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '1');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '18');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '3');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '8');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '12');
#
#
#
#
# INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по республике (область, район, заемщик, кредит', '2');
# INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по Баткенской области (область, район, заемщик, кредит', '2');
# INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по Ошской области (область, район, заемщик, кредит', '2');
#
# INSERT INTO `mfloan`.`report_template` (`name`, `report_id`) VALUES ('по республике (отрасль, область, заемщик, кредит', '2');
#
# INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('5', '1');
# INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('6', '2');
# INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('7', '3');
# INSERT INTO `mfloan`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('8', '1');
#
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '1');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '2');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '5');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '8');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '12');
#
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '1');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '2');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '5');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '8');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '12');
#
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '1');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '2');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '5');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '8');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '12');
#
#
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '1');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '18');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '3');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '8');
# INSERT INTO `mfloan`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '12');
#
#
#
#
#
#
# INSERT INTO printout (id, name, printoutType) VALUES (1, 'Реестр погашений', 'PAYMENT_SUMMARY');
# INSERT INTO printout (id, name, printoutType) VALUES (2, 'Детальный расчет', 'LOAN_DETAILED_SUMMARY');
# INSERT INTO printout (id, name, printoutType) VALUES (3, 'Расчет', 'LOAN_SUMMARY');
# INSERT INTO printout (id, name, printoutType) VALUES (4, 'Акт сверки', 'LOAN_SUMMARY');
# INSERT INTO printout (id, name, printoutType) VALUES (5, 'Претензия', 'COLLECTION_SUMMARY');
#
# INSERT INTO printout_template (id, name, printout_id) VALUES (1, 'Реестр погашений', 1);
# INSERT INTO printout_template (id, name, printout_id) VALUES (2, 'Детальный расчет', 2);
# INSERT INTO printout_template (id, name, printout_id) VALUES (3, 'На исковое заявление', 3);
# INSERT INTO printout_template (id, name, printout_id) VALUES (4, 'Акт сверки Пром', 4);
# INSERT INTO printout_template (id, name, printout_id) VALUES (5, 'Претензия Пром', 5);





DROP TABLE IF EXISTS person_view;
DROP TABLE IF EXISTS organization_view;
DROP TABLE IF EXISTS owner_view;
DROP TABLE IF EXISTS debtor_view;
DROP TABLE IF EXISTS loan_view;
DROP TABLE IF EXISTS payment_view;

DROP TABLE IF EXISTS collateral_agreement_view;
DROP TABLE IF EXISTS collateral_item_view;

DROP TABLE IF EXISTS collection_phase_view;
DROP TABLE IF EXISTS collection_procedure_debtor_view;
DROP TABLE IF EXISTS collection_procedure_view;

DROP TABLE IF EXISTS loan_detailed_summary_view;
DROP TABLE IF EXISTS loan_summary_view;

DROP TABLE IF EXISTS payment_schedule_view;

DROP TABLE IF EXISTS applied_entity_view;
DROP TABLE IF EXISTS document_package_view;
DROP TABLE IF EXISTS entity_document_view;

DROP TABLE IF EXISTS reference_view;







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
    `ov`.`id`                  AS `v_debtor_entity_id`,
    `ov`.`ownerType`           AS `v_debtor_owner_type`,
    `ov`.`v_owner_address_id`  AS `v_debtor_address_id`,
    `ov`.`v_owner_region_id`   AS `v_debtor_region_id`,
    `ov`.`v_owner_district_id` AS `v_debtor_district_id`,
    `ov`.`v_owner_aokmotu_id`  AS `v_debtor_aokmotu_id`,
    `ov`.`v_owner_village_id`  AS `v_debtor_village_id`
  FROM (`mfloan`.`debtor` `d`
    JOIN `mfloan`.`owner_view` `ov`)
  WHERE (`d`.`ownerId` = `ov`.`id`);








CREATE VIEW loan_view AS
  SELECT
    `l`.`id`                       AS `v_loan_id`,
    `l`.`amount`                   AS `v_loan_amount`,
    `l`.`hasSubLoan`               AS `v_loan_has_subLoan`,
    `l`.`regDate`                  AS `v_loan_reg_date`,
    `l`.`regNumber`                AS `v_loan_reg_number`,
    `l`.`supervisorId`             AS `v_loan_supervisor_id`,
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







CREATE VIEW collection_procedure_debtor_view AS
  SELECT DISTINCT
    `pr`.`id` AS `v_cp_id`,
    `d`.`id`  AS `v_debtor_id`
  FROM ((((`mfloan`.`collectionProcedure` `pr`
    JOIN `mfloan`.`collectionPhase` `ph`) JOIN `mfloan`.`phaseDetails` `pd`) JOIN `mfloan`.`loan` `l`) JOIN
    `mfloan`.`debtor` `d`)
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



CREATE VIEW collection_phase_view AS
  SELECT
    `cpv`.`v_cp_id`                                             AS `v_cp_id`,
    `cpv`.`v_cp_version`                                        AS `v_cp_version`,
    `cpv`.`v_cp_closeDate`                                      AS `v_cp_closeDate`,
    `cpv`.`v_cp_lastPhase`                                      AS `v_cp_lastPhase`,
    `cpv`.`v_cp_lastStatusId`                                   AS `v_cp_lastStatusId`,
    `cpv`.`v_cp_startDate`                                      AS `v_cp_startDate`,
    `cpv`.`v_cp_procedureStatusId`                              AS `v_cp_procedureStatusId`,
    `cpv`.`v_cp_procedureTypeId`                                AS `v_cp_procedureTypeId`,
    `cpv`.`v_debtor_id`                                         AS `v_debtor_id`,
    `cpv`.`v_debtor_name`                                       AS `v_debtor_name`,
    `cpv`.`v_debtor_type_id`                                    AS `v_debtor_type_id`,
    `cpv`.`v_debtor_org_form_id`                                AS `v_debtor_org_form_id`,
    `cpv`.`v_debtor_owner_id`                                   AS `v_debtor_owner_id`,
    `cpv`.`v_debtor_work_sector_id`                             AS `v_debtor_work_sector_id`,
    `cpv`.`v_debtor_entity_id`                                  AS `v_debtor_entity_id`,
    `cpv`.`v_debtor_owner_type`                                 AS `v_debtor_owner_type`,
    `cpv`.`v_debtor_address_id`                                 AS `v_debtor_address_id`,
    `cpv`.`v_debtor_region_id`                                  AS `v_debtor_region_id`,
    `cpv`.`v_debtor_district_id`                                AS `v_debtor_district_id`,
    `cpv`.`v_debtor_aokmotu_id`                                 AS `v_debtor_aokmotu_id`,
    `cpv`.`v_debtor_village_id`                                 AS `v_debtor_village_id`,
    `cph`.`id`                                                  AS `v_cph_id`,
    `cph`.`version`                                             AS `v_cph_version`,
    `cph`.`closeDate`                                           AS `v_cph_closeDate`,
    `cph`.`lastEvent`                                           AS `v_cph_lastEvent`,
    `cph`.`lastStatusId`                                        AS `v_cph_lastStatusId`,
    `cph`.`startDate`                                           AS `v_cph_startDate`,
    `cph`.`collectionProcedureId`                               AS `v_cph_collectionProcedureId`,
    `cph`.`phaseStatusId`                                       AS `v_cph_phaseStatusId`,
    `cph`.`phaseTypeId`                                         AS `v_cph_phaseTypeId`,
    `r`.`name`                                                  AS `v_region_name`,
    `d`.`name`                                                  AS `v_district_name`,
    `ws`.`name`                                                 AS `v_work_sector_name`,
    ifnull((SELECT sum(`phd`.`closeTotalAmount`)
            FROM `mfloan`.`phaseDetails` `phd`
            WHERE (`phd`.`collectionPhaseId` = `cph`.`id`)), 0) AS `v_cph_close_total_amount`,
    (SELECT sum(`phd`.`startTotalAmount`)
     FROM `mfloan`.`phaseDetails` `phd`
     WHERE (`phd`.`collectionPhaseId` = `cph`.`id`))            AS `v_cph_start_total_amount`
  FROM ((((`mfloan`.`collection_procedure_view` `cpv`
    JOIN `mfloan`.`collectionPhase` `cph`) JOIN `mfloan`.`region` `r`) JOIN `mfloan`.`district` `d`) JOIN
    `mfloan`.`workSector` `ws`)
  WHERE ((`cpv`.`v_cp_id` = `cph`.`collectionProcedureId`) AND (`cpv`.`v_debtor_region_id` = `r`.`id`) AND
         (`cpv`.`v_debtor_district_id` = `d`.`id`) AND (`cpv`.`v_debtor_work_sector_id` = `ws`.`id`));




CREATE VIEW loan_detailed_summary_view AS
  SELECT
    `lv`.`v_loan_id`                              AS `v_loan_id`,
    `lv`.`v_loan_amount`                          AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`                     AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`                        AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`                      AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`                   AS `v_loan_supervisor_id`,
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
    `lv`.`v_loan_id`                AS `v_loan_id`,
    `lv`.`v_loan_amount`            AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`       AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`          AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`        AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`     AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`   AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`       AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`         AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`          AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`           AS `v_loan_type_id`,
    `lv`.`v_loan_parent_loan_id`    AS `v_loan_parent_loan_id`,
    `lv`.`v_debtor_id`              AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`         AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`     AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`  AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`            AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`       AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`      AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`       AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`     AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`      AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`      AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`   AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_regNumber` AS `v_credit_order_regNumber`,
    `lv`.`v_credit_order_regDate`   AS `v_credit_order_regDate`,
    `lv`.`v_region_name`            AS `v_region_name`,
    `lv`.`v_district_name`          AS `v_district_name`,
    `lv`.`v_work_sector_name`       AS `v_work_sector_name`,
    `ls`.`id`                       AS `v_ls_id`,
    `ls`.`version`                  AS `v_ls_version`,
    `ls`.`loanAmount`               AS `v_ls_loanAmount`,
    `ls`.`onDate`                   AS `v_ls_onDate`,
    `ls`.`outstadingFee`            AS `v_ls_outstadingFee`,
    `ls`.`outstadingInterest`       AS `v_ls_outstadingInterest`,
    `ls`.`outstadingPenalty`        AS `v_ls_outstadingPenalty`,
    `ls`.`outstadingPrincipal`      AS `v_ls_outstadingPrincipal`,
    `ls`.`overdueFee`               AS `v_ls_overdueFee`,
    `ls`.`overdueInterest`          AS `v_ls_overdueInterest`,
    `ls`.`overduePenalty`           AS `v_ls_overduePenalty`,
    `ls`.`overduePrincipal`         AS `v_ls_overduePrincipal`,
    `ls`.`paidFee`                  AS `v_ls_paidFee`,
    `ls`.`paidInterest`             AS `v_ls_paidInterest`,
    `ls`.`paidPenalty`              AS `v_ls_paidPenalty`,
    `ls`.`paidPrincipal`            AS `v_ls_paidPrincipal`,
    `ls`.`totalDisbursed`           AS `v_ls_totalDisbursed`,
    `ls`.`totalOutstanding`         AS `v_ls_totalOutstanding`,
    `ls`.`totalOverdue`             AS `v_ls_totalOverdue`,
    `ls`.`totalPaid`                AS `v_ls_totalPaid`,
    `ls`.`loanId`                   AS `v_ls_loanId`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`loanSummary` `ls`)
  WHERE (`ls`.`loanId` = `lv`.`v_loan_id`);





CREATE VIEW payment_schedule_view AS
  SELECT
    `lv`.`v_loan_id`                AS `v_loan_id`,
    `lv`.`v_loan_amount`            AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`       AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`          AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`        AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`     AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`   AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`       AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`         AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`          AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`           AS `v_loan_type_id`,
    `lv`.`v_loan_parent_loan_id`    AS `v_loan_parent_loan_id`,
    `lv`.`v_debtor_id`              AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`         AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`     AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`  AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`            AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`       AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`      AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`       AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`     AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`      AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`      AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`   AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_regNumber` AS `v_credit_order_regNumber`,
    `lv`.`v_credit_order_regDate`   AS `v_credit_order_regDate`,
    `lv`.`v_region_name`            AS `v_region_name`,
    `lv`.`v_district_name`          AS `v_district_name`,
    `lv`.`v_work_sector_name`       AS `v_work_sector_name`,
    `ps`.`id`                       AS `v_ps_id`,
    `ps`.`version`                  AS `v_ps_version`,
    `ps`.`collectedInterestPayment` AS `v_ps_collectedInterestPayment`,
    `ps`.`collectedPenaltyPayment`  AS `v_ps_collectedPenaltyPayment`,
    `ps`.`disbursement`             AS `v_ps_disbursement`,
    `ps`.`expectedDate`             AS `v_ps_expectedDate`,
    `ps`.`interestPayment`          AS `v_ps_interestPayment`,
    `ps`.`principalPayment`         AS `v_ps_principalPayment`,
    `ps`.`installmentStateId`       AS `v_ps_installmentStateId`,
    `ps`.`loanId`                   AS `v_ps_loanId`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`paymentSchedule` `ps`)
  WHERE (`ps`.`loanId` = `lv`.`v_loan_id`);





CREATE VIEW payment_view AS
  SELECT
    `lv`.`v_loan_id`                AS `v_loan_id`,
    `lv`.`v_loan_amount`            AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`       AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`          AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`        AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`     AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`   AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`       AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`         AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`          AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`           AS `v_loan_type_id`,
    `lv`.`v_loan_parent_loan_id`    AS `v_loan_parent_loan_id`,
    `lv`.`v_debtor_id`              AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`         AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`     AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`  AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`            AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`       AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`      AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`       AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`     AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`      AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`      AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`   AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_regNumber` AS `v_credit_order_regNumber`,
    `lv`.`v_credit_order_regDate`   AS `v_credit_order_regDate`,
    `lv`.`v_region_name`            AS `v_region_name`,
    `lv`.`v_district_name`          AS `v_district_name`,
    `p`.`id`                        AS `v_payment_id`,
    `p`.`fee`                       AS `v_payment_fee`,
    `p`.`interest`                  AS `v_payment_interest`,
    `p`.`number`                    AS `v_payment_number`,
    `p`.`paymentDate`               AS `v_payment_date`,
    `p`.`penalty`                   AS `v_payment_penalty`,
    `p`.`principal`                 AS `v_payment_principal`,
    `p`.`totalAmount`               AS `v_payment_total_amount`,
    `p`.`paymentTypeId`             AS `v_payment_type_id`,
    `pt`.`name`                     AS `v_payment_type_name`,
    `p`.`exchange_rate`             AS `v_payment_exchange_rate`,
    `p`.`in_loan_currency`          AS `v_payment_in_loan_currency`,
    `lv`.`v_work_sector_name`       AS `v_work_sector_name`
  FROM ((`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`payment` `p` ON ((`p`.`loanId` = `lv`.`v_loan_id`))) JOIN `mfloan`.`paymentType` `pt`
      ON ((`pt`.`id` = `p`.`paymentTypeId`)));



CREATE VIEW applied_entity_view AS
  SELECT
    `ae`.`id`                        AS `v_applied_entity_id`,
    `ae`.`version`                   AS `v_applied_entity_version`,
    `ae`.`appliedEntityListId`       AS `v_applied_entity_appliedEntityListId`,
    `ae`.`appliedEntityStateId`      AS `v_applied_entity_appliedEntityStateId`,
    `ae`.`ownerId`                   AS `v_applied_entity_ownerId`,
    `ov`.`v_owner_address_id`        AS `v_owner_address_id`,
    `ov`.`v_owner_region_id`         AS `v_owner_region_id`,
    `ov`.`v_owner_district_id`       AS `v_owner_district_id`,
    `ov`.`v_owner_aokmotu_id`        AS `v_owner_aokmotu_id`,
    `ov`.`v_owner_village_id`        AS `v_owner_village_id`,
    `ov`.`entityId`                  AS `v_owner_entityId`,
    `ov`.`ownerType`                 AS `v_owner_ownerType`,
    `ov`.`name`                      AS `v_owner_name`,
    `ael`.`id`                       AS `v_ael_id`,
    `ael`.`version`                  AS `v_ael_version`,
    `ael`.`listDate`                 AS `v_ael_listDate`,
    `ael`.`listNumber`               AS `v_ael_listNumber`,
    `ael`.`appliedEntityListStateId` AS `v_ael_appliedEntityListStateId`,
    `ael`.`appliedEntityListTypeId`  AS `v_ael_appliedEntityListTypeId`,
    `ael`.`creditOrderId`            AS `v_ael_creditOrderId`,
    `co`.`id`                        AS `v_co_id`,
    `co`.`version`                   AS `v_co_version`,
    `co`.`description`               AS `v_co_description`,
    `co`.`regDate`                   AS `v_co_regDate`,
    `co`.`regNumber`                 AS `v_co_regNumber`,
    `co`.`creditOrderStateId`        AS `v_co_creditOrderStateId`,
    `co`.`creditOrderTypeId`         AS `v_co_creditOrderTypeId`
  FROM (((`mfloan`.`appliedEntity` `ae`
    JOIN `mfloan`.`owner_view` `ov`) JOIN `mfloan`.`appliedEntityList` `ael`) JOIN `mfloan`.`creditOrder` `co`)
  WHERE ((`ae`.`ownerId` = `ov`.`id`) AND (`ael`.`id` = `ae`.`appliedEntityListId`) AND
         (`co`.`id` = `ael`.`creditOrderId`));






CREATE VIEW document_package_view AS
  SELECT
    `mfloan`.`documentPackage`.`id`                               AS `v_document_package_id`,
    `mfloan`.`documentPackage`.`approvedDate`                     AS `v_document_package_approvedDate`,
    `mfloan`.`documentPackage`.`approvedRatio`                    AS `v_document_package_approvedRatio`,
    `mfloan`.`documentPackage`.`completedDate`                    AS `v_document_package_completedDate`,
    `mfloan`.`documentPackage`.`completedRatio`                   AS `v_document_package_completedRatio`,
    `mfloan`.`documentPackage`.`name`                             AS `v_document_package_name`,
    `mfloan`.`documentPackage`.`orderDocumentPackageId`           AS `v_document_package_orderDocumentPackageId`,
    `mfloan`.`documentPackage`.`registeredRatio`                  AS `v_document_package_registeredRatio`,
    `mfloan`.`documentPackage`.`appliedEntityId`                  AS `v_document_package_appliedEntityId`,
    `mfloan`.`documentPackage`.`documentPackageStateId`           AS `v_document_package_documentPackageStateId`,
    `mfloan`.`documentPackage`.`documentPackageTypeId`            AS `v_document_package_documentPackageTypeId`,
    `applied_entity_view`.`v_applied_entity_id`                   AS `v_applied_entity_id`,
    `applied_entity_view`.`v_applied_entity_version`              AS `v_applied_entity_version`,
    `applied_entity_view`.`v_applied_entity_appliedEntityListId`  AS `v_applied_entity_appliedEntityListId`,
    `applied_entity_view`.`v_applied_entity_appliedEntityStateId` AS `v_applied_entity_appliedEntityStateId`,
    `applied_entity_view`.`v_applied_entity_ownerId`              AS `v_applied_entity_ownerId`,
    `applied_entity_view`.`v_owner_address_id`                    AS `v_owner_address_id`,
    `applied_entity_view`.`v_owner_region_id`                     AS `v_owner_region_id`,
    `applied_entity_view`.`v_owner_district_id`                   AS `v_owner_district_id`,
    `applied_entity_view`.`v_owner_aokmotu_id`                    AS `v_owner_aokmotu_id`,
    `applied_entity_view`.`v_owner_village_id`                    AS `v_owner_village_id`,
    `applied_entity_view`.`v_owner_entityId`                      AS `v_owner_entityId`,
    `applied_entity_view`.`v_owner_ownerType`                     AS `v_owner_ownerType`,
    `applied_entity_view`.`v_owner_name`                          AS `v_owner_name`,
    `applied_entity_view`.`v_ael_id`                              AS `v_ael_id`,
    `applied_entity_view`.`v_ael_listDate`                        AS `v_ael_listDate`,
    `applied_entity_view`.`v_ael_listNumber`                      AS `v_ael_listNumber`,
    `applied_entity_view`.`v_ael_appliedEntityListStateId`        AS `v_ael_appliedEntityListStateId`,
    `applied_entity_view`.`v_ael_appliedEntityListTypeId`         AS `v_ael_appliedEntityListTypeId`,
    `applied_entity_view`.`v_ael_creditOrderId`                   AS `v_ael_creditOrderId`,
    `applied_entity_view`.`v_co_id`                               AS `v_co_id`,
    `applied_entity_view`.`v_co_regDate`                          AS `v_co_regDate`,
    `applied_entity_view`.`v_co_regNumber`                        AS `v_co_regNumber`,
    `applied_entity_view`.`v_co_creditOrderStateId`               AS `v_co_creditOrderStateId`,
    `applied_entity_view`.`v_co_creditOrderTypeId`                AS `v_co_creditOrderTypeId`
  FROM (`mfloan`.`documentPackage`
    JOIN `mfloan`.`applied_entity_view`)
  WHERE (`mfloan`.`documentPackage`.`appliedEntityId` = `applied_entity_view`.`v_applied_entity_id`);







CREATE VIEW entity_document_view AS
  SELECT
    `ed`.`id`                                                           AS `v_entity_document_id`,
    `ed`.`version`                                                      AS `v_entity_document_version`,
    `ed`.`approvedBy`                                                   AS `v_entity_document_approvedBy`,
    `ed`.`approvedDate`                                                 AS `v_entity_document_approvedDate`,
    `ed`.`approvedDescription`                                          AS `v_entity_document_approvedDescription`,
    `ed`.`completedBy`                                                  AS `v_entity_document_completedBy`,
    `ed`.`completedDate`                                                AS `v_entity_document_completedDate`,
    `ed`.`completedDescription`                                         AS `v_entity_document_completedDescription`,
    `ed`.`name`                                                         AS `v_entity_document_name`,
    `ed`.`registeredDate`                                               AS `v_entity_document_registeredDate`,
    `ed`.`registeredDescription`                                        AS `v_entity_document_registeredDescription`,
    `ed`.`registeredNumber`                                             AS `v_entity_document_registeredNumber`,
    `ed`.`documentPackageId`                                            AS `v_entity_document_documentPackageId`,
    `ed`.`entityDocumentStateId`                                        AS `v_entity_document_entityDocumentStateId`,
    `ed`.`entityDocumentRegisteredById`                                 AS `v_entity_document_entityDocumentRegisteredById`,
    `ed`.`documentTypeId`                                               AS `v_entity_document_documentTypeId`,
    `document_package_view`.`v_document_package_id`                     AS `v_document_package_id`,
    `document_package_view`.`v_document_package_approvedDate`           AS `v_document_package_approvedDate`,
    `document_package_view`.`v_document_package_approvedRatio`          AS `v_document_package_approvedRatio`,
    `document_package_view`.`v_document_package_completedDate`          AS `v_document_package_completedDate`,
    `document_package_view`.`v_document_package_completedRatio`         AS `v_document_package_completedRatio`,
    `document_package_view`.`v_document_package_name`                   AS `v_document_package_name`,
    `document_package_view`.`v_document_package_orderDocumentPackageId` AS `v_document_package_orderDocumentPackageId`,
    `document_package_view`.`v_document_package_registeredRatio`        AS `v_document_package_registeredRatio`,
    `document_package_view`.`v_document_package_appliedEntityId`        AS `v_document_package_appliedEntityId`,
    `document_package_view`.`v_document_package_documentPackageStateId` AS `v_document_package_documentPackageStateId`,
    `document_package_view`.`v_document_package_documentPackageTypeId`  AS `v_document_package_documentPackageTypeId`,
    `document_package_view`.`v_applied_entity_id`                       AS `v_applied_entity_id`,
    `document_package_view`.`v_applied_entity_version`                  AS `v_applied_entity_version`,
    `document_package_view`.`v_applied_entity_appliedEntityListId`      AS `v_applied_entity_appliedEntityListId`,
    `document_package_view`.`v_applied_entity_appliedEntityStateId`     AS `v_applied_entity_appliedEntityStateId`,
    `document_package_view`.`v_applied_entity_ownerId`                  AS `v_applied_entity_ownerId`,
    `document_package_view`.`v_owner_address_id`                        AS `v_owner_address_id`,
    `document_package_view`.`v_owner_region_id`                         AS `v_owner_region_id`,
    `document_package_view`.`v_owner_district_id`                       AS `v_owner_district_id`,
    `document_package_view`.`v_owner_aokmotu_id`                        AS `v_owner_aokmotu_id`,
    `document_package_view`.`v_owner_village_id`                        AS `v_owner_village_id`,
    `document_package_view`.`v_owner_entityId`                          AS `v_owner_entityId`,
    `document_package_view`.`v_owner_ownerType`                         AS `v_owner_ownerType`,
    `document_package_view`.`v_owner_name`                              AS `v_owner_name`,
    `document_package_view`.`v_ael_id`                                  AS `v_ael_id`,
    `document_package_view`.`v_ael_listDate`                            AS `v_ael_listDate`,
    `document_package_view`.`v_ael_listNumber`                          AS `v_ael_listNumber`,
    `document_package_view`.`v_ael_appliedEntityListStateId`            AS `v_ael_appliedEntityListStateId`,
    `document_package_view`.`v_ael_appliedEntityListTypeId`             AS `v_ael_appliedEntityListTypeId`,
    `document_package_view`.`v_ael_creditOrderId`                       AS `v_ael_creditOrderId`,
    `document_package_view`.`v_co_id`                                   AS `v_co_id`,
    `document_package_view`.`v_co_regDate`                              AS `v_co_regDate`,
    `document_package_view`.`v_co_regNumber`                            AS `v_co_regNumber`,
    `document_package_view`.`v_co_creditOrderStateId`                   AS `v_co_creditOrderStateId`,
    `document_package_view`.`v_co_creditOrderTypeId`                    AS `v_co_creditOrderTypeId`,
    (CASE WHEN (`ed`.`completedBy` > 0)
      THEN (SELECT 1)
     ELSE (SELECT 0) END)                                               AS `v_entity_document_completed_count`,
    (CASE WHEN (`ed`.`completedBy` > 0)
      THEN (SELECT 0)
     ELSE (SELECT 1) END)                                               AS `v_entity_document_not_completed_count`,
    (CASE WHEN (`ed`.`approvedBy` > 0)
      THEN (SELECT 1)
     ELSE (SELECT 0) END)                                               AS `v_entity_document_approved_count`,
    (CASE WHEN (`ed`.`approvedBy` > 0)
      THEN (SELECT 0)
     ELSE (SELECT 1) END)                                               AS `v_entity_document_not_approved_count`
  FROM (`mfloan`.`entityDocument` `ed`
    JOIN `mfloan`.`document_package_view`)
  WHERE (`ed`.`documentPackageId` = `document_package_view`.`v_document_package_id`);









CREATE VIEW reference_view AS
  SELECT
    'region'     AS `list_type`,
    `tbl`.`id`   AS `id`,
    `tbl`.`name` AS `name`
  FROM `mfloan`.`region` `tbl`
  UNION ALL SELECT
              'district'   AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`district` `tbl`
  UNION ALL SELECT
              'work_sector' AS `list_type`,
              `tbl`.`id`    AS `id`,
              `tbl`.`name`  AS `name`
            FROM `mfloan`.`workSector` `tbl`
  UNION ALL SELECT
              'loan_type'  AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`loanType` `tbl`
  UNION ALL SELECT
              'supervisor' AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`staff` `tbl`
            WHERE ((`tbl`.`organization_id` = 1) AND (`tbl`.`enabled` = TRUE))
  UNION ALL SELECT
              'department' AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`department` `tbl`
            WHERE (`tbl`.`organization_id` = 1)
  UNION ALL SELECT
              'credit_order'    AS `list_type`,
              `tbl`.`id`        AS `id`,
              `tbl`.`regNumber` AS `regNumber`
            FROM `mfloan`.`creditOrder` `tbl`
  UNION ALL SELECT
              'applied_entity_status' AS `list_type`,
              `tbl`.`id`              AS `id`,
              `tbl`.`name`            AS `name`
            FROM `mfloan`.`appliedEntityState` `tbl`
  UNION ALL SELECT
              'entity_document_status' AS `list_type`,
              `tbl`.`id`               AS `id`,
              `tbl`.`name`             AS `name`
            FROM `mfloan`.`entityDocumentState` `tbl`
  UNION ALL SELECT
              'document_package_status' AS `list_type`,
              `tbl`.`id`                AS `id`,
              `tbl`.`name`              AS `name`
            FROM `mfloan`.`documentPackageState` `tbl`
  UNION ALL SELECT
              'collection_phase_type' AS `list_type`,
              `tbl`.`id`              AS `id`,
              `tbl`.`name`            AS `name`
            FROM `mfloan`.`phaseType` `tbl`
  UNION ALL SELECT
              'collection_phase_status' AS `list_type`,
              `tbl`.`id`                AS `id`,
              `tbl`.`name`              AS `name`
            FROM `mfloan`.`phaseStatus` `tbl`
  UNION ALL SELECT
              'collection_procedure_status' AS `list_type`,
              `tbl`.`id`                    AS `id`,
              `tbl`.`name`                  AS `name`
            FROM `mfloan`.`procedureStatus` `tbl`;




















CREATE
  ALGORITHM = UNDEFINED
  DEFINER = `root`@`localhost`
  SQL SECURITY DEFINER
VIEW `accounts` AS
  SELECT
    `data`.`id` AS `id`,
    1 AS `uuid`,
    1 AS `version`,
    `data`.`internalName` AS `internalName`,
    `data`.`name` AS `name`
  FROM
    (SELECT
       `mfloan`.`organization`.`id` AS `id`,
       `mfloan`.`organization`.`name` AS `name`,
       'organization' AS `internalName`
     FROM
       `mfloan`.`organization` UNION ALL SELECT
                                           `mfloan`.`department`.`id` AS `id`,
                                           `mfloan`.`department`.`name` AS `name`,
                                           'department' AS `internalName`
                                         FROM
                                           `mfloan`.`department`
                                         WHERE
                                           (`mfloan`.`department`.`organization_id` = 1) UNION ALL SELECT
                                                                                                     `mfloan`.`staff`.`id` AS `id`,
                                                                                                     `mfloan`.`staff`.`name` AS `name`,
                                                                                                     'staff' AS `internalName`
                                                                                                   FROM
                                                                                                     `mfloan`.`staff`
                                                                                                   WHERE
                                                                                                     (`mfloan`.`staff`.`organization_id` = 1) UNION ALL SELECT
                                                                                                                                                          `mfloan`.`person`.`id` AS `id`,
                                                                                                                                                          `mfloan`.`person`.`name` AS `name`,
                                                                                                                                                          'person' AS `internalName`
                                                                                                                                                        FROM
                                                                                                                                                          `mfloan`.`person`) `data`
  ORDER BY `data`.`id`;










CREATE DEFINER=`root`@`localhost` FUNCTION `isTermFound`(loan_id BIGINT, inDate DATE) RETURNS tinyint(1)
  BEGIN

    DECLARE result BOOLEAN DEFAULT FALSE;
    DECLARE cCount INT;

    SELECT COUNT(1) FROM creditTerm cTerm where cTerm.loanId = loan_id AND cTerm.startDate <= inDate INTO cCount;

    IF cCount > 0 THEN SET result = TRUE;
    END IF;

    RETURN result;

  END;

CREATE DEFINER=`root`@`localhost` FUNCTION `isPaymentSchedulePaymentDate`(inDate DATE, loanId BIGINT) RETURNS tinyint(1)
  BEGIN

    DECLARE result BOOLEAN DEFAULT TRUE;
    DECLARE iPayment DOUBLE;

    DECLARE cur CURSOR FOR
      SELECT ps.interestPayment FROM paymentSchedule ps
      WHERE ps.loanId = loanId
            AND ps.expectedDate = inDate;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET result = FALSE;

    OPEN cur;
    FETCH cur INTO iPayment;
    CLOSE cur;

    IF iPayment <= 0 THEN SET result = false;
    END IF;

    RETURN result;

  END;



CREATE DEFINER=`root`@`localhost` FUNCTION `isPaymentScheduleLastPaymentDate`(inDate DATE, loanId BIGINT) RETURNS tinyint(1)
  BEGIN

    DECLARE result BOOLEAN DEFAULT FALSE;
    DECLARE expectedDate DATE;

    DECLARE cur CURSOR FOR
      SELECT ps.expectedDate FROM paymentSchedule ps
      WHERE ps.loanId = loanId
      ORDER BY ps.expectedDate DESC LIMIT 1;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET result = FALSE;

    OPEN cur;
    FETCH cur INTO expectedDate;
    CLOSE cur;

    IF DATEDIFF(inDate, expectedDate) >= 0 THEN SET result = TRUE;
    END IF;

    RETURN result;

  END;




CREATE DEFINER=`root`@`localhost` FUNCTION `isLoanDetailedSummaryExistsForDate`(loan_id BIGINT, inDate DATE) RETURNS tinyint(1)
  BEGIN

    DECLARE result BOOLEAN DEFAULT FALSE;
    DECLARE cCount INT;

    SELECT COUNT(1) FROM loanDetailedSummary tt where tt.loanId = loan_id AND tt.onDate = inDate INTO cCount;

    IF cCount > 0 THEN SET result = TRUE;
    END IF;

    RETURN result;

  END;




CREATE DEFINER=`root`@`localhost` FUNCTION `isDateFirstDayOfMonth`(inDate DATE) RETURNS tinyint(1)
  BEGIN

    DECLARE result BOOLEAN DEFAULT FALSE;
    DECLARE tempDate DATE;

    SELECT DATE_SUB(inDate, INTERVAL DAYOFMONTH(inDate)-1 DAY) INTO tempDate;

    IF tempDate = inDate THEN SET result = TRUE;
    END IF;

    RETURN result;

  END;




CREATE DEFINER=`root`@`localhost` FUNCTION `hasLiborType`(loan_id BIGINT, inDate DATE) RETURNS tinyint(1)
  BEGIN

    DECLARE result BOOLEAN DEFAULT FALSE;
    DECLARE rateTypeId BIGINT;

    SELECT cTerm.floatingRateTypeId
    FROM creditTerm cTerm
    WHERE cTerm.loanId = loan_id
          AND cTerm.startDate <= inDate
    ORDER BY cTerm.startDate LIMIT 1
    INTO rateTypeId;

    IF rateTypeId != 2 THEN SET result = TRUE;
    END IF;

    RETURN result;

  END;


CREATE DEFINER=`root`@`localhost` FUNCTION `getLoanAmount`(loanId BIGINT) RETURNS double
  BEGIN

    DECLARE result DOUBLE DEFAULT 0;

    SELECT loan.amount INTO result from loan where id = loanId;

    RETURN result;

  END;



CREATE DEFINER=`root`@`localhost` FUNCTION `getCollectedPenDisbursed`(loanId BIGINT) RETURNS double
  BEGIN

    DECLARE result DOUBLE DEFAULT 0;

    DECLARE cur CURSOR FOR
      select sum(ps.collectedPenaltyPayment)
      from paymentSchedule ps
      where ps.loanId = loanId;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET result = 0;

    OPEN cur;
    FETCH cur INTO result;
    CLOSE cur;

    RETURN result;

  END;





CREATE DEFINER=`root`@`localhost` FUNCTION `getCollectedIntDisbursed`(loanId BIGINT) RETURNS double
  BEGIN

    DECLARE result DOUBLE DEFAULT 0;

    DECLARE cur CURSOR FOR
      select sum(ps.collectedInterestPayment)
      from paymentSchedule ps
      where ps.loanId = loanId;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET result = 0;

    OPEN cur;
    FETCH cur INTO result;
    CLOSE cur;

    RETURN result;

  END;



CREATE DEFINER=`root`@`localhost` FUNCTION `calculatePOPO`(principalOverdue DOUBLE, daysInPeriod INT, inDate DATE, loanId BIGINT) RETURNS double
  BEGIN

    DECLARE rate DOUBLE DEFAULT 0;
    DECLARE dIYMethod INT;
    DECLARE nOD INT DEFAULT 365;

    DECLARE cur CURSOR FOR
      SELECT term.penaltyOnPrincipleOverdueRateValue, term.daysInYearMethodId
      FROM creditTerm term
      WHERE term.loanId = loanId
      ORDER BY term.startDate DESC LIMIT 1;

    OPEN cur;
    FETCH cur INTO rate, dIYMethod;
    CLOSE cur;

    IF dIYMethod != 1 THEN SET nOD = 360;
    END IF;

    RETURN (principalOverdue*rate/nOD)/100*daysInPeriod;

  END;


CREATE DEFINER=`root`@`localhost` FUNCTION `calculatePOIO`(interestOverdue DOUBLE, daysInPeriod INT, inDate DATE, loanId BIGINT) RETURNS double
  BEGIN

    DECLARE rate DOUBLE DEFAULT 0;
    DECLARE dIYMethod INT;
    DECLARE nOD INT DEFAULT 365;

    DECLARE cur CURSOR FOR
      SELECT term.penaltyOnInterestOverdueRateValue, term.daysInYearMethodId
      FROM creditTerm term
      WHERE term.loanId = loanId
      ORDER BY term.startDate DESC LIMIT 1;

    OPEN cur;
    FETCH cur INTO rate, dIYMethod;
    CLOSE cur;

    IF dIYMethod != 1 THEN SET nOD = 360;
    END IF;

    RETURN (interestOverdue*rate/nOD)/100*daysInPeriod;

  END;



CREATE DEFINER=`root`@`localhost` FUNCTION `calculatePenaltyAccrued`(principalOverdue DOUBLE, interestOverdue DOUBLE, daysInPeriod INT, inDate DATE,
                                                                     loanId           BIGINT) RETURNS double
  BEGIN

    DECLARE pOIO DOUBLE DEFAULT 0;
    DECLARE pOPO DOUBLE DEFAULT 0;
    DECLARE dIYMethod INT;
    DECLARE nOD INT DEFAULT 365;
    DECLARE result_pOPO DOUBLE DEFAULT 0;
    DECLARE result_pOIO DOUBLE DEFAULT 0;

    DECLARE cur CURSOR FOR
      SELECT term.penaltyOnInterestOverdueRateValue, term.penaltyOnPrincipleOverdueRateValue
      FROM creditTerm term
      WHERE term.loanId = loanId
            AND term.startDate < inDate
      ORDER BY term.startDate DESC LIMIT 1;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND
    BEGIN
      SET pOIO = 0;
      SET pOPO = 0;
    END;

    OPEN cur;
    FETCH cur INTO pOIO, pOPO;
    CLOSE cur;

    IF dIYMethod != 1 THEN SET nOD = 360;
    END IF;

    IF principalOverdue > 0 THEN SET result_pOPO = principalOverdue*pOPO/100*daysInPeriod/nOD;
    END IF;

    IF interestOverdue > 0 THEN SET result_pOIO = interestOverdue*pOIO/100*daysInPeriod/nOD;
    END IF;

    RETURN result_pOIO + result_pOPO;

  END;




CREATE DEFINER=`root`@`localhost` FUNCTION `calculateLiborPO`(val DOUBLE, fromDate DATE, toDate DATE, loan_id BIGINT) RETURNS double
  BEGIN

    DECLARE total DOUBLE DEFAULT 0;
    DECLARE tRate DOUBLE;
    DECLARE prevRate DOUBLE DEFAULT 0;
    DECLARE tDate DATE;
    DECLARE curDate DATE;
    DECLARE prevDate DATE;
    DECLARE daysInPer INT DEFAULT 0;
    DECLARE dIYMethod INT;
    DECLARE nOD INT DEFAULT 365;
    DECLARE flag BOOLEAN DEFAULT TRUE;

    DECLARE v_finished INTEGER DEFAULT 0;

    DECLARE tCursor CURSOR FOR
      (SELECT fr.rate, fr.date, term.daysInYearMethodId
       FROM floating_rate fr, creditTerm term
       WHERE term.loanId = loan_id
             AND term.penaltyOnPrincipleOverdueRateTypeId = fr.floating_rate_type_id
             AND fr.date >= fromDate
             AND fr.date <= toDate
             AND term.id =
                 (SELECT tt.id from creditTerm tt
                 WHERE tt.loanId = loan_id
                       AND tt.startDate <= fr.date
                  ORDER BY tt.startDate DESC LIMIT 1))
      UNION
      (SELECT fr.rate, fr.date, term.daysInYearMethodId
       FROM floating_rate fr, creditTerm term
       WHERE term.loanId = loan_id
             AND term.penaltyOnPrincipleOverdueRateTypeId = fr.floating_rate_type_id
             AND fr.date <= fromDate
             AND term.startDate <= fr.date
       ORDER BY fr.date DESC LIMIT 1)
      ORDER BY date;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

    /*
    DECLARE CONTINUE HANDLER
    FOR SQLEXCEPTION
    SELECT CONCAT('Error occured on loanId:', loan_id);
    */

    OPEN tCursor;

    get_data: LOOP

      FETCH tCursor INTO tRate, tDate, dIYMethod;

      IF v_finished = 1 THEN
        LEAVE get_data;
      END IF;

      IF flag THEN
        SET curDate = fromDate;
        SET prevDate = curDate;
        SET flag = FALSE;
      ELSE
        SET curDate = tDate;
      END IF;

      IF dIYMethod != 1 THEN SET nOD = 360;
      END IF;

      SET daysInPer = DATEDIFF(curDate, prevDate);

      SET total = total + val*prevRate*daysInPer/nOD/100;

      #INSERT INTO temp1(tRate, tDate, daysInPer, exp, total) VALUES (prevRate, curDate, daysInPer, CONCAT(val,'*',prevRate,'*',daysInPer), total);

      #SELECT tRate, curDate, daysInPer, total;

      SET prevDate = curDate;

      SET prevRate = tRate;

    END LOOP get_data;

    CLOSE tCursor;

    SET daysInPer = DATEDIFF(toDate, prevDate);

    SET total = total + val*tRate*daysInPer/nOD/100;

    #INSERT INTO temp1(tRate, tDate, daysInPer, exp, total) VALUES (tRate, curDate, daysInPer, CONCAT(val,'*',tRate,'*',daysInPer), total);

    #SELECT tRate, toDate, daysInPer, total;

    IF val < 0 THEN
      SET total = 0;
    END IF;

    RETURN total;

  END;



CREATE DEFINER=`root`@`localhost` FUNCTION `calculateLiborIO`(val DOUBLE, fromDate DATE, toDate DATE, loan_id BIGINT) RETURNS double
  BEGIN

    DECLARE total DOUBLE DEFAULT 0;
    DECLARE tRate DOUBLE;
    DECLARE prevRate DOUBLE DEFAULT 0;
    DECLARE tDate DATE;
    DECLARE curDate DATE;
    DECLARE prevDate DATE;
    DECLARE daysInPer INT DEFAULT 0;
    DECLARE dIYMethod INT;
    DECLARE nOD INT DEFAULT 365;
    DECLARE flag BOOLEAN DEFAULT TRUE;

    DECLARE v_finished INTEGER DEFAULT 0;

    DECLARE tCursor CURSOR FOR
      (SELECT fr.rate, fr.date, term.daysInYearMethodId
       FROM floating_rate fr, creditTerm term
       WHERE term.loanId = loan_id
             AND term.penaltyOnInterestOverdueRateTypeId = fr.floating_rate_type_id
             AND fr.date >= fromDate
             AND fr.date <= toDate
             AND term.id =
                 (SELECT tt.id from creditTerm tt
                 WHERE tt.loanId = loan_id
                       AND tt.startDate <= fr.date
                  ORDER BY tt.startDate DESC LIMIT 1))
      UNION
      (SELECT fr.rate, fr.date, term.daysInYearMethodId
       FROM floating_rate fr, creditTerm term
       WHERE term.loanId = loan_id
             AND term.penaltyOnInterestOverdueRateTypeId = fr.floating_rate_type_id
             AND fr.date <= fromDate
             AND term.startDate <= fr.date
       ORDER BY fr.date DESC LIMIT 1)
      ORDER BY date;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

    /*
    DECLARE CONTINUE HANDLER
    FOR SQLEXCEPTION
    SELECT CONCAT('Error occured on loanId:', loan_id);
    */

    OPEN tCursor;

    get_data: LOOP

      FETCH tCursor INTO tRate, tDate, dIYMethod;

      IF v_finished = 1 THEN
        LEAVE get_data;
      END IF;

      IF flag THEN
        SET curDate = fromDate;
        SET prevDate = curDate;
        SET flag = FALSE;
      ELSE
        SET curDate = tDate;
      END IF;

      IF dIYMethod != 1 THEN SET nOD = 360;
      END IF;

      SET daysInPer = DATEDIFF(curDate, prevDate);

      SET total = total + val*prevRate*daysInPer/nOD/100;

      #INSERT INTO temp1(tRate, tDate, daysInPer, exp, total) VALUES (prevRate, curDate, daysInPer, CONCAT(val,'*',prevRate,'*',daysInPer), total);

      #SELECT tRate, curDate, daysInPer, total;

      SET prevDate = curDate;

      SET prevRate = tRate;

    END LOOP get_data;

    CLOSE tCursor;

    SET daysInPer = DATEDIFF(toDate, prevDate);

    SET total = total + val*tRate*daysInPer/nOD/100;

    #INSERT INTO temp1(tRate, tDate, daysInPer, exp, total) VALUES (tRate, curDate, daysInPer, CONCAT(val,'*',tRate,'*',daysInPer), total);

    #SELECT tRate, toDate, daysInPer, total;

    IF val < 0 THEN
      SET total = 0;
    END IF;

    RETURN total;

  END;



CREATE DEFINER=`root`@`localhost` FUNCTION `calculateLibor`(val DOUBLE, fromDate DATE, toDate DATE, loan_id BIGINT) RETURNS double
  BEGIN

    DECLARE total DOUBLE DEFAULT 0;
    DECLARE tRate DOUBLE;
    DECLARE prevRate DOUBLE DEFAULT 0;
    DECLARE tDate DATE;
    DECLARE curDate DATE;
    DECLARE prevDate DATE;
    DECLARE daysInPer INT DEFAULT 0;
    DECLARE dIYMethod INT;
    DECLARE nOD INT DEFAULT 365;
    DECLARE flag BOOLEAN DEFAULT TRUE;

    DECLARE v_finished INTEGER DEFAULT 0;

    DECLARE tCursor CURSOR FOR
      (SELECT fr.rate, fr.date, term.daysInYearMethodId
       FROM floating_rate fr, creditTerm term
       WHERE term.loanId = loan_id
             AND term.floatingRateTypeId = fr.floating_rate_type_id
             AND fr.date >= fromDate
             AND fr.date <= toDate
             AND term.id =
                 (SELECT tt.id from creditTerm tt
                 WHERE tt.loanId = loan_id
                       AND tt.startDate <= fr.date
                  ORDER BY tt.startDate DESC LIMIT 1))
      UNION
      (SELECT fr.rate, fr.date, term.daysInYearMethodId
       FROM floating_rate fr, creditTerm term
       WHERE term.loanId = loan_id
             AND term.floatingRateTypeId = fr.floating_rate_type_id
             AND fr.date <= fromDate
             AND term.startDate <= fr.date
       ORDER BY fr.date DESC LIMIT 1)
      ORDER BY date;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

    /*
    DECLARE CONTINUE HANDLER
    FOR SQLEXCEPTION
    SELECT CONCAT('Error occured on loanId:', loan_id);
    */

    OPEN tCursor;

    get_data: LOOP

      FETCH tCursor INTO tRate, tDate, dIYMethod;

      IF v_finished = 1 THEN
        LEAVE get_data;
      END IF;

      IF flag THEN
        SET curDate = fromDate;
        SET prevDate = curDate;
        SET flag = FALSE;
      ELSE
        SET curDate = tDate;
      END IF;

      IF dIYMethod != 1 THEN SET nOD = 360;
      END IF;

      SET daysInPer = DATEDIFF(curDate, prevDate);

      SET total = total + val*prevRate*daysInPer/nOD/100;

      #INSERT INTO temp1(tRate, tDate, daysInPer, exp, total) VALUES (prevRate, curDate, daysInPer, CONCAT(val,'*',prevRate,'*',daysInPer), total);

      #SELECT tRate, curDate, daysInPer, total;

      SET prevDate = curDate;

      SET prevRate = tRate;

    END LOOP get_data;

    CLOSE tCursor;

    SET daysInPer = DATEDIFF(toDate, prevDate);

    SET total = total + val*tRate*daysInPer/nOD/100;

    #INSERT INTO temp1(tRate, tDate, daysInPer, exp, total) VALUES (tRate, curDate, daysInPer, CONCAT(val,'*',tRate,'*',daysInPer), total);

    #SELECT tRate, toDate, daysInPer, total;

    IF val < 0 THEN
      SET total = 0;
    END IF;

    RETURN total;

  END;



CREATE DEFINER=`root`@`localhost` FUNCTION `calculateInterestAccrued`(principalOutstanding DOUBLE, daysInPeriod INT, inDate DATE, loanId BIGINT) RETURNS double
  BEGIN

    DECLARE rate DOUBLE DEFAULT 0;
    DECLARE dIYMethod INT DEFAULT 2;
    DECLARE nOD INT DEFAULT 365;

    DECLARE cur CURSOR FOR
      SELECT term.interestRateValue, term.daysInYearMethodId
      FROM creditTerm term
      WHERE term.loanId = loanId
            AND term.startDate < inDate
      ORDER BY term.startDate DESC LIMIT 1;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET rate = 0;

    OPEN cur;
    FETCH cur INTO rate, dIYMethod;
    CLOSE cur;

    IF dIYMethod != 1 THEN SET nOD = 360;
    END IF;

    RETURN (principalOutstanding*rate/nOD)/100*daysInPeriod;

  END;


CREATE DEFINER=`root`@`localhost` PROCEDURE `updateOwners`()
  BEGIN

    DECLARE v_finished INTEGER DEFAULT 0;
    DECLARE tOwnerId LONG;
    DECLARE tAddressId LONG;

    DEClARE tCursor CURSOR FOR

      SELECT id, v_owner_address_id from owner_view ORDER BY id;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

    OPEN tCursor;

    run_update: LOOP

      FETCH tCursor INTO tOwnerId, tAddressId;

      IF v_finished = 1 THEN
        LEAVE run_update;
      END IF;

      UPDATE owner SET addressId = tAddressId WHERE id = tOwnerId;

    END LOOP run_update;

    CLOSE tCursor;

  END;




CREATE DEFINER=`root`@`localhost` PROCEDURE `runCalculateLoanDetailedSummaryForSelectedLoans`(IN inDate DATE)
  BEGIN

    DECLARE v_finished INTEGER DEFAULT 0;
    DECLARE loan_id BIGINT;
    DECLARE tempDate DATE;

    DEClARE tCursor CURSOR FOR

      SELECT
        loanId, onDate
      FROM loansToEvaluate ORDER BY loanId;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

    OPEN tCursor;

    run_calculate: LOOP

      FETCH tCursor INTO loan_id, tempDate;

      IF v_finished = 1 THEN
        LEAVE run_calculate;
      END IF;

      CALL calculateLoanDetailedSummaryUntilOnDate(loan_id, tempDate, 1);

    END LOOP run_calculate;

    CLOSE tCursor;

  END;




CREATE DEFINER=`root`@`localhost` PROCEDURE `runCalculateLoanDetailedSummaryForAllLoans`(IN inDate DATE)
  BEGIN

    DECLARE v_finished INTEGER DEFAULT 0;
    DECLARE loanId BIGINT;
    DECLARE countLoans INT DEFAULT 0;

    DECLARE start_time bigint;
    DECLARE end_time bigint;

    DEClARE tCursor CURSOR FOR

      SELECT
        loan.id
      FROM loan loan ORDER BY loan.id;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

    SET start_time = (UNIX_TIMESTAMP(NOW()) * 1000000 + MICROSECOND(NOW(6)));

    OPEN tCursor;

    run_calculate: LOOP

      FETCH tCursor INTO loanId;

      IF v_finished = 1 THEN
        LEAVE run_calculate;
      END IF;

      CALL calculateLoanDetailedSummaryUntilOnDate(loanId, inDate, 0);

      SET countLoans = countLoans + 1;

      #select last_insert_id();

    END LOOP run_calculate;

    CLOSE tCursor;

    SET end_time = (UNIX_TIMESTAMP(NOW()) * 1000000 + MICROSECOND(NOW(6)));

    select (end_time - start_time) / 1000 as runningTime, countLoans as numberOfLoans;

  END;


CREATE DEFINER=`root`@`localhost` PROCEDURE `runCalculateForDate`(IN inDate DATE)
  BEGIN

    DECLARE start_time bigint;
    DECLARE end_time bigint;

    SET start_time = (UNIX_TIMESTAMP(NOW()) * 1000000 + MICROSECOND(NOW(6)));

    DELETE FROM loansToEvaluate;
    COMMIT;

    CALL fillLoansToEvaluateTable(inDate);
    COMMIT;

    SELECT CONCAT((SELECT count(1) from loansToEvaluate), ' loans will be processed') as message;

    CALL runCalculateLoanDetailedSummaryForSelectedLoans(inDate);
    COMMIT;

    SET end_time = (UNIX_TIMESTAMP(NOW()) * 1000000 + MICROSECOND(NOW(6)));

    select (end_time - start_time) / 1000 as runningTime;

  END;


CREATE DEFINER=`root`@`localhost` PROCEDURE `fillLoansToEvaluateTable`(IN inDate DATE)
  BEGIN

    DECLARE loan_id BIGINT;
    DECLARE prevLoan_id BIGINT DEFAULT 0;
    DECLARE descrip TEXT;
    DECLARE tempDate DATE;

    DECLARE v_finished INTEGER DEFAULT 0;

    DEClARE tCursor CURSOR FOR

      SELECT
        pp.loanId,
        pp.description,
        pp.onDate
      FROM
        (SELECT
           'term' AS description,
           loan.id AS loanId,
           term.startDate AS onDate
         FROM loan loan, creditTerm term
         WHERE loan.id = term.loanId
               AND term.startDate = inDate
         UNION
         SELECT
           'payment' AS description,
           loan.id,
           payment.paymentDate AS onDate
         FROM loan loan, payment payment
         WHERE loan.id = payment.loanId
               AND payment.paymentDate = inDate
         UNION
         SELECT
           'ps' AS description,
           loan.id,
           ps.expectedDate AS onDate
         FROM loan loan, paymentSchedule ps
         WHERE loan.id = ps.loanId
               AND ps.expectedDate = inDate
         UNION DISTINCT
         SELECT
           'startOfMonth' AS description,
           loan.id,
           inDate AS onDate
         FROM loan loan
         WHERE isDateFirstDayOfMonth(inDate)=1) pp
      ORDER BY pp.loanId;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

    OPEN tCursor;

    get_data: LOOP

      FETCH tCursor INTO loan_id, descrip, tempDate;

      IF v_finished = 1 THEN
        LEAVE get_data;
      END IF;

      #LOANS TO EVALUATE INSERT
      IF loan_id != prevLoan_id THEN
        INSERT INTO loansToEvaluate(version, loanId, description, onDate)
        VALUES (1, loan_id, descrip, tempDate);
      END IF;

      SET prevLoan_id = loan_id;

    END LOOP get_data;

    CLOSE tCursor;
  END;



CREATE DEFINER=`root`@`localhost` PROCEDURE `calculateLoanDetailedSummaryUntilOnDate`(IN loan_id BIGINT, IN inDate DATE, IN includeToday TINYINT(1))
  BEGIN
    DECLARE tempDate DATE;
    DECLARE prevDate DATE;
    DECLARE daysInPer INT DEFAULT 0;
    DECLARE disb DOUBLE;
    DECLARE totalDisb DOUBLE DEFAULT 0;
    DECLARE princPaid DOUBLE;
    DECLARE totalPrincPaid DOUBLE DEFAULT 0;
    DECLARE princPayment DOUBLE;
    DECLARE totalPrincPayment DOUBLE DEFAULT 0;
    DECLARE princOutstanding DOUBLE DEFAULT 0;
    DECLARE princOverdue DOUBLE DEFAULT 0;
    DECLARE intAccrued DOUBLE DEFAULT 0;
    DECLARE totalIntAccrued DOUBLE DEFAULT 0;
    DECLARE intPaid DOUBLE;
    DECLARE totalIntPaid DOUBLE DEFAULT 0;
    DECLARE intPayment DOUBLE DEFAULT 0;
    DECLARE totalIntPayment DOUBLE DEFAULT 0;
    DECLARE collIntPayment DOUBLE;
    DECLARE totalCollIntPayment DOUBLE DEFAULT 0;
    DECLARE intOverdue DOUBLE DEFAULT 0;
    DECLARE penAccrued DOUBLE DEFAULT 0;
    DECLARE totalPenAccrued DOUBLE DEFAULT 0;
    DECLARE penPaid DOUBLE;
    DECLARE totalPenPaid DOUBLE DEFAULT 0;
    DECLARE collPenPayment DOUBLE;
    DECLARE totalCollPenPayment DOUBLE DEFAULT 0;
    DECLARE penOverdue DOUBLE DEFAULT 0;
    DECLARE penOutstanding DOUBLE DEFAULT 0;
    DECLARE collPenDisbursed DOUBLE;
    DECLARE intOutstanding DOUBLE DEFAULT 0;
    DECLARE collIntDisbursed DOUBLE;
    DECLARE totalIntAccruedOnIntPayment DOUBLE DEFAULT 0;
    DECLARE pOPO DOUBLE DEFAULT 0;
    DECLARE pOIO DOUBLE DEFAULT 0;
    DECLARE pDate DATE;
    DECLARE loan_amount DOUBLE DEFAULT 0;
    DECLARE total_outstanding DOUBLE DEFAULT 0;
    DECLARE total_overdue DOUBLE DEFAULT 0;
    DECLARE total_paid DOUBLE DEFAULT 0;
    DECLARE cur_rate DOUBLE;

    DECLARE v_finished INTEGER DEFAULT 0;

    DECLARE errno INT;
    DECLARE msg TEXT;

    DECLARE flag BOOLEAN DEFAULT FALSE;
    DECLARE isAlreadyInserted BOOLEAN DEFAULT FALSE;

    DEClARE tCursor CURSOR FOR

      SELECT
        onDate,
        disbursement,
        principalPaid,
        principalPayment,
        interestPaid,
        collectedInterestPayment,
        penaltyPaid,
        collectedPenaltyPayment,
        curRate
      FROM
        (
          #GET TERMS
          SELECT
            term.startDate AS onDate,
            0              AS disbursement,
            0              AS principalPaid,
            0              AS principalPayment,
            0 as interestPaid,
            0 as collectedInterestPayment,
            0 as penaltyPaid,
            0 as collectedPenaltyPayment,
            1 as curRate
          FROM loan loan, creditTerm term
          WHERE loan.id = term.loanId
                AND loan.id = loan_id
                AND term.startDate <= inDate

          UNION

          #GET PAYMENTS
          SELECT
            MIN(payment.paymentDate) AS onDate,
            0                   AS disbursement,
            SUM(payment.principal)   AS principalPaid,
            0                   AS principalPayment,
            SUM(payment.interest) as interestPaid,
            0 as collectedInterestPayment,
            SUM(payment.penalty) as penaltyPaid,
            0 as collectedPenaltyPayment,
            MIN(payment.exchange_rate) as curRate
          FROM loan loan, payment payment
          WHERE loan.id = payment.loanId
                AND loan.id = loan_id
                AND payment.paymentDate <= inDate
          GROUP BY payment.paymentDate

          UNION

          #GET PAYMENT SCHEDULES
          SELECT
            ps.expectedDate                  AS onDate,
            ps.disbursement                  AS disbursement,
            0                                AS principalPaid,
            ps.principalPayment              AS principalPayment,
            0 as interestPaid,
            ps.collectedInterestPayment,
            0 as penaltyPaid,
            ps.collectedPenaltyPayment,
            1 as curRate
          FROM loan loan, paymentSchedule ps
          WHERE loan.id = ps.loanId
                AND loan.id = loan_id
                AND ps.expectedDate <= inDate

          UNION

          #DUMMY SELECT FOR GETTING ON DATE
          SELECT
            inDate AS onDate,
            0            AS disbursement,
            0            AS principalPaid,
            0            AS principalPayment,
            0 as interestPaid,
            0 as collectedInterestPayment,
            0 as penaltyPaid,
            0 as collectedPenaltyPayment,
            1 as curRate
          FROM dual
          WHERE includeToday = 1
        ) pp
      ORDER BY pp.onDate;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

    DECLARE CONTINUE HANDLER
    FOR SQLEXCEPTION
    BEGIN
      GET CURRENT DIAGNOSTICS CONDITION 1
      errno = MYSQL_ERRNO, msg = MESSAGE_TEXT;
      INSERT INTO logCalculateLoanSummary(version, loanId, onDate, message)
      VALUES (1,loan_id, inDate, msg);
    END;

    IF !isTermFound(loan_id, inDate) THEN
      SET v_finished = 1;
      INSERT INTO logCalculateLoanSummary(version, loanId, onDate, message)
      VALUES (1,loan_id, inDate, 'No term info found');
    END IF;

    OPEN tCursor;

    get_data: LOOP

      FETCH tCursor INTO tempDate,disb, princPaid, princPayment, intPaid, collIntPayment, penPaid, collPenPayment, cur_rate;

      IF v_finished = 1 THEN
        LEAVE get_data;
      END IF;

      IF flag = FALSE THEN
        SET prevDate = tempDate;
        SET loan_amount = getLoanAmount(loan_id);
        SET pDate = tempDate;
      END IF;

      SET princPaid = princPaid/cur_rate;
      SET intPaid = intPaid/cur_rate;
      SET penPaid = penPaid/cur_rate;

      SET daysInPer = DATEDIFF(tempDate, prevDate);

      SET intAccrued = calculateInterestAccrued(princOutstanding, daysInPer, tempDate, loan_id);

      IF hasLiborType(loan_id, tempDate) THEN
        SET intAccrued = intAccrued + calculateLibor(princOutstanding, prevDate, tempDate, loan_id);
      END IF;

      SET totalIntAccrued = totalIntAccrued + intAccrued;

      SET penAccrued = calculatePenaltyAccrued(princOverdue, intOverdue, daysInPer, tempDate, loan_id);

      IF hasLiborType(loan_id, tempDate) THEN
        SET penAccrued = penAccrued
                         + calculateLiborPO(princOverdue, prevDate, tempDate, loan_id)
                         + calculateLiborIO(intOverdue, prevDate, tempDate, loan_id);
      END IF;

      SET totalPenAccrued = totalPenAccrued + penAccrued;

      SET totalDisb = totalDisb + disb;
      SET totalPrincPaid = totalPrincPaid + princPaid;
      SET totalPrincPayment = totalPrincPayment + princPayment;
      SET prevDate = tempDate;
      SET princOutstanding = totalDisb - totalPrincPaid;
      SET princOverdue = totalPrincPayment - totalPrincPaid;
      SET totalIntPaid = totalIntPaid + intPaid;

      IF isPaymentSchedulePaymentDate(tempDate, loan_id) OR isPaymentScheduleLastPaymentDate(tempDate, loan_id) THEN
        SET intPayment = totalIntAccrued - totalIntAccruedOnIntPayment;
        SET totalIntAccruedOnIntPayment = totalIntAccrued;
      ELSE SET intPayment = 0;
      END IF;

      SET totalCollPenPayment = totalCollPenPayment + collPenPayment;
      SET totalPenPaid = totalPenPaid + penPaid;
      SET penOverdue = totalPenAccrued + totalCollPenPayment - totalPenPaid;
      SET collPenDisbursed = getCollectedPenDisbursed(loan_id);
      SET penOutstanding = totalPenAccrued + collPenDisbursed - totalPenPaid;
      SET collIntDisbursed = getCollectedIntDisbursed(loan_id);
      SET intOutstanding = totalIntAccrued + collIntDisbursed - totalIntPaid;
      SET totalCollIntPayment = totalCollIntPayment + collIntPayment;
      SET totalIntPayment = totalIntPayment + intPayment;
      SET intOverdue = totalIntPayment - totalIntPaid;

      #SELECT tempDate, disb, totalDisb, princOutstanding, princPaid, totalPrincPaid, princPayment,
      #totalPrincPayment, princOverdue, daysInPer, intAccrued, intPaid, totalIntPaid,
      #intPayment, collIntPayment, intOverdue, penAccrued, totalPenAccrued, penPaid, totalPenPaid, collPenPayment, totalCollPenPayment, penOverdue, penOutstanding, collPenDisbursed;

      SET isAlreadyInserted = isLoanDetailedSummaryExistsForDate(loan_id, tempDate);

      IF(isAlreadyInserted = FALSE) THEN
        #LOAN DETAILED SUMMARY INSERT
        INSERT INTO loanDetailedSummary(version, collectedInterestDisbursed, collectedInterestPayment, collectedPenaltyDisbursed, collectedPenaltyPayment, daysInPeriod, disbursement, interestAccrued,
                                        interestOutstanding, interestOverdue, interestPaid, interestPayment, onDate, penaltyAccrued, penaltyOutstanding, penaltyOverdue, penaltyPaid, principalOutstanding,
                                        principalOverdue, principalPaid, principalPayment, principalWriteOff, totalCollectedInterestPayment, totalCollectedPenaltyPayment, totalDisbursement,
                                        totalInterestAccrued, totalInterestAccruedOnInterestPayment, totalInterestPaid, totalInterestPayment, totalPenaltyAccrued, totalPenaltyPaid, totalPrincipalPaid,
                                        totalPrincipalPayment, totalPrincipalWriteOff, loanId)
        VALUES (1, ROUND(collIntDisbursed,2), ROUND(collIntPayment,2), ROUND(collPenDisbursed,2), ROUND(collPenPayment,2), daysInPer, ROUND(disb,2), ROUND(intAccrued,2), ROUND(intOutstanding,2),
                   ROUND(intOverdue,2), ROUND(intPaid,2), ROUND(intPayment,2), ROUND(tempDate,2), ROUND(penAccrued,2), ROUND(penOutstanding,2), ROUND(penOverdue,2), ROUND(penPaid,2), ROUND(princOutstanding,2),
                                                          ROUND(princOverdue,2), ROUND(princPaid,2), ROUND(princPayment,2), 0, ROUND(totalCollIntPayment,2), ROUND(totalCollPenPayment,2), ROUND(totalDisb,2), ROUND(totalIntAccrued,2), 0,
                                                                                                                            ROUND(totalIntPaid,2), ROUND(totalIntPayment,2), ROUND(totalPenAccrued,2), ROUND(totalPenPaid,2), ROUND(totalPrincPaid,2), ROUND(totalPrincPayment,2), 0, loan_id);
      END IF;

      IF flag = TRUE THEN
        SET pOPO = calculatePOPO(princOverdue, daysInPer, tempDate, loan_id);
        SET pOIO = calculatePOIO(intOverdue, daysInPer, tempDate, loan_id);


        IF(isAlreadyInserted = FALSE) THEN
          #ACCRUE INSERT
          INSERT INTO accrue(version, daysInPeriod, fromDate, interestAccrued, lastInstPassed, penaltyAccrued, penaltyOnInterestOverdue, penaltyOnPrincipalOverdue, toDate, loanId)
          VALUES (1, daysInPer, pDate, ROUND(intAccrued,2), FALSE , ROUND(penAccrued,2), ROUND(pOIO,2), ROUND(pOPO,2), tempDate, loan_id);
        END IF;

        SET pDate = tempDate;

        SET total_outstanding = princOutstanding + intOutstanding + penOutstanding;
        SET total_overdue = princOverdue + intOverdue + penOverdue;
        SET total_paid = total_paid + princPaid + intPaid + penPaid;

        IF(isAlreadyInserted = FALSE) THEN
          #LOAN SUMMARY INSERT
          INSERT INTO loanSummary(version, loanAmount, onDate, outstadingFee, outstadingInterest, outstadingPenalty, outstadingPrincipal, overdueFee, overdueInterest, overduePenalty,
                                  overduePrincipal, paidFee, paidInterest, paidPenalty, paidPrincipal, totalDisbursed, totalOutstanding, totalOverdue, totalPaid, totalInterestPaid, totalPenaltyPaid, totalPrincipalPaid, totalFeePaid, loanId)
          VALUES (1, loan_amount, tempDate, 0.0, intOutstanding, penOutstanding, princOutstanding, 0.0, intOverdue, penOverdue, princOverdue, 0.0, totalIntPaid, totalPrincPaid,
                                                                                                                                              princPaid, totalDisb, total_outstanding, total_overdue, total_paid, totalIntPaid, totalPenPaid, totalPrincPaid, 0, loan_id);
        END IF;

      END IF;

      IF flag = FALSE THEN
        SET flag = TRUE;
      END IF;

    END LOOP get_data;

    CLOSE tCursor;
  END;




INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (1, 'v_debtor_region_id', 'Область', '(=regionMap(v_debtor_region_id)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (2, 'v_debtor_district_id', 'Район', '(=districtMap(v_debtor_district_id)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (3, 'v_debtor_work_sector_id', 'Отрасль', '(=work_sectorMap(v_debtor_work_sector_id)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (4, 'v_loan_type_id', 'Вид кредита', '(=loan_typeMap(v_loan_type_id)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (5, 'v_loan_supervisor_id', 'Куратор', '(=supervisorMap(v_loan_supervisor_id)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (6, 'v_loan_department_id', 'Отдел', '(=departmentMap(v_loan_department_id)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (7, 'v_debtor_id', 'Заемщик', '(=v_debtor_name=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (8, 'v_loan_id', 'Кредит', '(=v_loan_reg_number=) от (=v_loan_reg_date=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (9, 'v_ls_id', 'Расчет', 'Расчет');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (10, 'v_payment_id', 'Погашение', 'Погашение');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (11, 'v_ps_id', 'График', 'График');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (12, 'v_credit_order_id', 'Решение о выдаче кредита', '(=credit_orderMap(v_credit_order_id)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (13, 'v_applied_entity_id', 'Получатель', '(=v_owner_name=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (14, 'v_ael_id', 'Список получателей', '(=v_ael_listNumber=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (15, 'v_document_package_id', 'Пакет документации', '(=v_document_package_name=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (16, 'v_entity_document_id', 'Документ (оформление)', '(=v_entity_document_name=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (17, 'v_ca_id', 'Договор залога', '(=v_ca_agreementNumber=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (18, 'v_ci_id', 'Предмет залога', '(=v_ci_name=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (19, 'v_cp_id', 'Процедура взыскания', 'Процедура взыскания');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (20, 'v_cph_id', 'Фаза взыскания', '');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (21, 'v_applied_entity_appliedEntityStateId', 'Статус получателя', '(=appliedEntityStatusMap(v_applied_entity_appliedEntityStateId)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (22, 'v_entity_document_entityDocumentStateId', 'Статус документа(оформление)', '(=entityDocumentStatusMap(v_entity_document_entityDocumentStateId)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (23, 'v_document_package_documentPackageStateId', 'Статус пакета док.', '(=documentPackageStatusMap(v_document_package_documentPackageStateId)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (24, 'v_entity_document_completedBy', 'Укомплектовано', '(=supervisorMap(v_entity_document_completedBy)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (25, 'v_entity_document_approvedBy', 'Проверено', '(=supervisorMap(v_entity_document_approvedBy)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (26, 'v_owner_region_id', 'Область (получатель)', '(=regionMap(v_owner_region_id)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (27, 'v_owner_district_id', 'Район (получатель)', '(=districtMap(v_owner_district_id)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (28, 'v_cph_phaseTypeId', 'Вид стадии', '(=collection_phase_typeMap(v_cph_phaseTypeId)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (29, 'v_cph_phaseStatusId', 'Вид результата', '(=collection_phase_statusMap(v_cph_phaseStatusId)=)');
INSERT INTO mfloan.group_type (id, field_name, name, row_name) VALUES (30, 'v_cp_procedureStatusId', 'Статус процедуры', '(=collection_procedure_statusMap(v_cp_procedureStatusId)=)');



INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (1, 0, 0, null, 'Информация по оформлению документации', 0, 'CONSTANT', '', 'Оформление. Заголовок страницы. Строка 1. ', 6, 0, 1, 'PAGE_TITLE', 0, 1, 7, 2, 2, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (3, 0, 0, null, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Оформление. Заголовок страницы. Строка 2.', null, 0, 2, 'PAGE_TITLE', null, 1, 7, 3, 3, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (4, 0, 0, null, 'Кол-во пол.', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Кол-во получателей', null, 0, 1, 'TABLE_HEADER', null, 1, 1, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (5, 0, 0, null, 'Наименование', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Наименование', null, 0, 2, 'TABLE_HEADER', null, 2, 2, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (6, 0, 0, null, 'в том числе', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Кол-во документов в том числе', null, 0, 4, 'TABLE_HEADER', null, 4, 7, 5, 5, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (7, 0, 0, null, 'Кол-во док.', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Кол-во документов', null, 0, 3, 'TABLE_HEADER', null, 3, 3, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (8, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Итого. Кол-во получателей', null, 0, 1, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (9, 0, 0, null, 'ИТОГО', 0, 'CONSTANT', '', 'Оформление. Итого. Итого', null, 0, 2, 'TABLE_SUM', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (10, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Итого. Кол-во документов', null, 0, 3, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (11, 0, 0, null, 'укомлектовано', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Укомлектовано', null, 0, 5, 'TABLE_HEADER', null, 4, 4, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (12, 0, 0, null, 'не укомпл.', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Не укомплектовано', null, 0, 6, 'TABLE_HEADER', null, 5, 5, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (13, 0, 0, null, 'проверено', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Проверено', null, 0, 7, 'TABLE_HEADER', null, 6, 6, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (14, 0, 0, null, 'не проверено', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Не проверено', null, 0, 8, 'TABLE_HEADER', null, 7, 7, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (15, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Итого. Кол-во укомплектованных документов', null, 0, 4, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (16, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Итого. Кол-во проверенных документов', null, 0, 6, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (17, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Итого. Кол-во не укопмлетованных документов', null, 0, 5, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (18, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Итого. Кол-во непроверенных документов', null, 0, 7, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (19, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Разрез 1. - кол-во получателей', null, 0, 1, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (20, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 1. Наименование', null, 0, 2, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (21, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 2. Кол-во документов', null, 0, 3, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (22, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Разрез 2. кол-во получателей', null, 0, 1, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (23, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Разрез 3. Кол-во получателей', null, 0, 1, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (24, 0, 0, null, '', 0, 'CONSTANT', 'EntityCount', 'Оформление. Разрез 4. Кол-во получателей', null, 0, 1, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (25, 0, 0, null, '', 0, 'CONSTANT', 'EntityCount', 'Оформление. Разрез 5. Кол-во получателей', null, 0, 1, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (26, 0, 0, null, '', 0, 'CONSTANT', 'EntityCount', 'Оформление. Разрез 6. Кол-во получателей', null, 0, 1, 'TABLE_GROUP6', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (27, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 2. Наименование', null, 0, 2, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (28, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 2. Наименование', null, 0, 2, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (29, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 3. Наименование', null, 0, 2, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (30, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 4. Наименование', null, 0, 2, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (31, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 5. Наименование', null, 0, 2, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (32, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 1. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (33, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 1. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (34, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 1. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (35, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 1. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (36, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 1. Кол-во док.', null, 0, 3, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (37, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 2. Кол-во док.', null, 0, 3, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (38, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 2. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (39, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 2. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (40, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 2. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (41, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 2. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (42, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 3. Кол-во док.', null, 0, 3, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (43, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 3. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (44, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 3. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (45, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 3. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (46, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 3. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (47, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 4. Кол-во док.', null, 0, 3, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (48, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 4. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (49, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 4. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (50, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 4. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (51, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 4. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (52, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 5. Кол-во док.', null, 0, 3, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (53, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 5. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (54, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 5. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (55, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 5. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (56, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 5. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (57, 0, 0, null, 'Статус', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Статус', null, 0, 8, 'TABLE_HEADER', null, 8, 8, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (61, 0, 0, null, 'applied_entity_status', 0, 'ENTITY_DOCUMENT', 'V_applied_entity_appliedEntityStateId', 'Оформление. Разрез 3. Статус', null, 21, 8, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (62, 0, 0, null, 'document_package_status', 0, 'ENTITY_DOCUMENT', 'v_document_package_documentPackageStateId', 'Оформление. Разрез 4. Статус', null, 23, 8, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (63, 0, 0, null, 'entity_document_status', 0, 'ENTITY_DOCUMENT', 'V_entity_document_entityDocumentStateId', 'Оформление. Разрез 5. Статус', null, 22, 8, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (64, 0, 0, null, 'Укомплектовано', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Укомплектовано', null, 0, 9, 'TABLE_HEADER', null, 9, 9, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (65, 0, 0, null, 'Дата комплектации', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Дата комплектации', null, 0, 10, 'TABLE_HEADER', null, 10, 10, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (66, 0, 0, null, 'Примечание', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Примечание', null, 0, 11, 'TABLE_HEADER', null, 11, 11, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (67, 0, 0, null, 'Проверено', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Проверено', null, 0, 12, 'TABLE_HEADER', null, 12, 12, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (68, 0, 0, null, 'Дата проверки', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Дата проверки', null, 0, 13, 'TABLE_HEADER', null, 13, 13, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (69, 0, 0, null, 'Примечание', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Примечание2', null, 0, 14, 'TABLE_HEADER', null, 14, 14, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (75, 0, 0, null, 'supervisor', 0, 'ENTITY_DOCUMENT', 'v_entity_document_completedBy', 'Оформление. Разрез 5. Укомплектовано', null, 5, 9, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (81, 0, 0, null, 'supervisor', 0, 'ENTITY_DOCUMENT', 'v_entity_document_approvedBy', 'Оформление. Разрез 5. Проверено', null, 5, 12, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (87, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_completedDate', 'Оформление. Разрез 5. Дата комплектации', null, 0, 10, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (93, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_approvedDate', 'Оформление. Разрез 5. Дата проверки', null, 0, 13, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (99, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_completedDescription', 'Оформление. Разрез 5. Примечание', null, 0, 11, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (105, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_approvedDescription', 'Оформление. Разрез 5. Примечание', null, 0, 14, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (106, 0, 0, null, 'Информация по взысканию', 0, 'CONSTANT', '', 'Взыскание. Заголовок страницы. Строка 1.', null, 0, 1, 'PAGE_TITLE', null, 1, 8, 1, 1, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (107, 0, 0, null, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Взыскание. Заголовок страницы. Строка 2.', null, 0, 1, 'PAGE_TITLE', null, 1, 8, 2, 2, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (108, 0, 0, null, 'Кол-во суб.', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Кол-во субъектов', null, 0, 1, 'TABLE_HEADER', null, 1, 1, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (109, 0, 0, null, 'Наименование', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Наименование', null, 0, 2, 'TABLE_HEADER', null, 2, 2, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (110, 0, 0, null, 'Стадия', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Стадия', null, 0, 3, 'TABLE_HEADER', null, 3, 6, 5, 5, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (111, 0, 0, null, 'Вид', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Вид', null, 0, 4, 'TABLE_HEADER', null, 4, 4, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (112, 0, 0, null, 'Дата', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Дата.', null, 0, 5, 'TABLE_HEADER', null, 5, 5, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (113, 0, 0, null, 'Сумма.', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Сумма.', null, 0, 5, 'TABLE_HEADER', null, 6, 6, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (114, 0, 0, null, 'Результат', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Результат', null, 0, 7, 'TABLE_HEADER', null, 7, 10, 5, 5, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (115, 0, 0, null, 'Вид', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Вид.', null, 0, 8, 'TABLE_HEADER', null, 8, 8, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (116, 0, 0, null, 'Дата.', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Дата.', null, 0, 9, 'TABLE_HEADER', null, 9, 9, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (117, 0, 0, null, 'Сумма', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Сумма', null, 0, 10, 'TABLE_HEADER', null, 10, 10, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (118, 0, 0, null, 'Статус', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Статус.', null, 0, 11, 'TABLE_HEADER', null, 11, 11, 5, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (119, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Итого. Кол-во суб.', null, 0, 1, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (120, 0, 0, null, 'ИТОГО', 0, 'CONSTANT', '', 'Взыскание. Итого. Итого.', null, 0, 2, 'TABLE_SUM', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (121, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 1. Кол-во субъектов', null, 0, 1, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (122, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'name', 'Взыскание. Разрез 1. Наименование', null, 0, 2, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (123, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 2. Кол-во субъектов', null, 0, 1, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (124, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'name', 'Взыскание. Разрез 2. Наименование', null, 0, 2, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (125, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 3. Кол-во субъектов', null, 0, 1, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (126, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'name', 'Взыскание. Разрез 3. Наименование', null, 0, 2, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (127, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 4. Кол-во субъектов.', null, 0, 1, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (128, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'name', 'Взыскание. Разрез 4. Наименование.', null, 0, 2, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (129, 0, 0, null, 'collection_phase_type', 0, 'COLLECTION_PHASE', 'collection_phase_type_id', 'Взыскание. Разрез 5. Вид стадии', null, 28, 4, 'TABLE_GROUP5', null, 4, 4, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (130, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_date', 'Взыскание. Разрез 5. Дата стадии', null, 0, 5, 'TABLE_GROUP5', null, 5, 5, 0, 0, 'DATE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (131, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 5. Сумма стадии.', null, 0, 6, 'TABLE_GROUP5', null, 6, 6, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (132, 0, 0, null, 'collection_phase_status', 0, 'COLLECTION_PHASE', 'collection_phase_status_id', 'Взыскание. Разрез 5. Вид результата.', null, 29, 8, 'TABLE_GROUP5', null, 8, 8, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (133, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_date', 'Взыскание. Разрез 5. Дата результата.', null, 0, 9, 'TABLE_GROUP5', null, 9, 9, 0, 0, 'DATE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (134, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 5. Сумма результата', null, 0, 10, 'TABLE_GROUP5', null, 10, 10, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (135, 0, 0, null, 'collection_procedure_status', 0, 'COLLECTION_PHASE', 'collection_procedure_status_id', 'Взыскание. Разрез 5. Статус', null, 30, 11, 'TABLE_GROUP5', null, 11, 11, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (136, 0, 0, null, 'Кол-во', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Кол-во стадий', null, 0, 4, 'TABLE_HEADER', null, 3, 3, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (137, 0, 0, null, 'Кол-во', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Кол-во результатов.', null, 0, 9, 'TABLE_HEADER', null, 7, 7, 6, 6, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (138, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 5. Кол-во стадии', null, 0, 3, 'TABLE_SUM', null, 3, 3, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (139, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 5. Кол-во результатов', null, 0, 7, 'TABLE_SUM', null, 7, 7, 0, 0, 'INTEGER');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (140, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 1. Кол-во стадии', null, 0, 3, 'TABLE_GROUP1', null, 3, 3, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (141, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 1. Кол-во результатов', null, 0, 7, 'TABLE_GROUP1', null, 7, 7, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (142, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 2. Кол-во стадии', null, 0, 3, 'TABLE_GROUP2', null, 3, 3, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (143, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 2. Кол-во результатов', null, 0, 7, 'TABLE_GROUP2', null, 7, 7, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (144, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 3. Кол-во стадии', null, 0, 3, 'TABLE_GROUP3', null, 3, 3, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (145, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 3. Кол-во результатов', null, 0, 7, 'TABLE_GROUP3', null, 7, 7, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (146, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 4. Кол-во стадии', null, 0, 3, 'TABLE_GROUP4', null, 3, 3, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (147, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 4. Кол-во результатов', null, 0, 7, 'TABLE_GROUP4', null, 7, 7, 0, 0, 'TEXT');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (148, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 4. Сумма стадии.', null, 0, 6, 'TABLE_GROUP4', null, 6, 6, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (149, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 4. Сумма результата', null, 0, 10, 'TABLE_GROUP4', null, 10, 10, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (150, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 3. Сумма стадии.', null, 0, 6, 'TABLE_GROUP3', null, 6, 6, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (151, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 3. Сумма результата', null, 0, 10, 'TABLE_GROUP3', null, 10, 10, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (152, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 2. Сумма стадии.', null, 0, 6, 'TABLE_GROUP2', null, 6, 6, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (153, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 2. Сумма результата', null, 0, 10, 'TABLE_GROUP2', null, 10, 10, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (154, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 1. Сумма стадии.', null, 0, 6, 'TABLE_GROUP1', null, 6, 6, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (155, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 1. Сумма результата', null, 0, 10, 'TABLE_GROUP1', null, 10, 10, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (156, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Итого. Сумма стадии.', null, 0, 6, 'TABLE_SUM', null, 6, 6, 0, 0, 'DOUBLE');
INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (157, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Итого. Сумма результата', null, 0, 10, 'TABLE_SUM', null, 10, 10, 0, 0, 'DOUBLE');












INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (1, 'Please login123', 'Авторизация болуңуз', 'login.form.title', 'Авторизируйтесь пожалуйста');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (2, 'Username', 'Кыска атыңыз', 'login.form.input.username', 'Имя пользователя');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (3, 'Password', 'Сыр сөз', 'login.form.input.password', 'Пароль');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (4, 'Enter', 'Кирүү', 'login.form.button.login', 'Вход');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (5, 'Name', 'Аталышы', 'label.orgForm.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (6, 'Forgot password?', 'Сыр сөздү унуттуңузбу?', 'login.forgot.password', 'Забыли пароль?');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (7, 'Name2', 'Аталышы2', 'asdf', 'Наименование2');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (8, 'Enabled', 'Колдонууда', 'label.enabled', 'Действует');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (9, 'Disabled', 'Жараксыз', 'label.disabled', 'Отменен');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (10, 'Name', 'Аталышы', 'label.orgForm.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (11, 'Enabled', 'Статусу', 'label.orgForm.table.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (12, 'Edit', 'Оңдоп-түздөө', 'label.orgForm.table.edit', 'Редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (13, 'Delete', 'Очуруп салуу', 'label.orgForm.table.delete', 'Удалить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (14, 'Edit', 'Ондоп-туздоо', 'label.orgForm.button.edit', 'Редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (15, 'Close', 'Жабып салуу', 'label.orgForm.button.close', 'Закрыть');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (16, 'Submit', 'Тастыктоо', 'label.orgForm.button.submit', 'Подтвердить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (17, 'Cancel', 'Кайра кайтаруу', 'label.orgForm.button.cancel', 'Отмена');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (18, 'Delete', 'Очуруп салуу', 'label.orgForm.button.delete', 'Удалить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (19, 'Save', 'Сактоо', 'label.orgForm.button.save', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (20, 'Add', 'Жаны', 'label.orgForm.button.add', 'Добавить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (21, 'Organization Form registration', 'Орг.форма киргизуу', 'label.orgForm.modal.title', 'Регистрация орг. формы');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (22, 'Name', 'Аталышы', 'label.orgForm.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (23, 'Enabled', 'Статусу', 'label.orgForm.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (24, 'Org Form List', 'Орг.формалар тизмеси', 'label.orgForm.page.title', 'Список организационных форм');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (25, 'Region List', 'Областтардын тизмеси', 'label.region.page.title', 'Список областей');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (26, 'ID', 'ID', 'label.region.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (27, 'Name', 'Аталышы', 'label.region.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (28, 'Code', 'Коду', 'label.region.table.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (29, 'View', 'Карап коруу', 'label.region.table.view', 'Просмотр');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (30, 'Edit', 'Ондоп-туздоо', 'label.region.table.edit', 'Редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (31, 'Delete', 'Очуруп салуу', 'label.region.table.delete', 'Удалить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (32, 'View', 'Карап коруу', 'label.region.button.view', 'Просмотр');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (33, 'Edit', 'Оңдоп-түздөө', 'label.region.button.edit', 'Редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (34, 'Delete', 'Очуруп салуу', 'label.region.button.delete', 'Удалить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (35, 'Close', 'Жабып салуу', 'label.region.button.close', 'Закрыть');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (36, 'Save', 'Сактоо', 'label.region.button.save', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (37, 'Add Region', 'Жаны область', 'label.region.button.addRegion', 'Добавить область');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (38, 'Add District', 'Жаны район', 'label.region.button.addDistrict', 'Добавить район');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (39, 'New Region or District Registration Form', 'Жаны область же район киргизуу', 'label.region.modal.title', 'Добавить область или район');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (40, 'Name', 'Аталышы', 'label.region.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (41, 'Code', 'Коду', 'label.region.modal.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (42, 'Submit', 'Тастыктоо', 'label.region.button.submit', 'Подтвердить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (43, 'Cancel', 'Кайра кайтаруу', 'label.region.button.cancel', 'Отмена');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (44, 'User List', 'Колдонуучулардын тизмеси', 'label.user.page.title', 'Список пользователей');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (45, 'ID', 'ID', 'label.user.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (46, 'Name', 'Аталышы', 'label.user.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (47, 'View', 'Карап коруу', 'label.user.table.view', 'Просмотр');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (48, 'Edit', 'Ондоп-туздоо', 'label.user.table.edit', 'Редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (49, 'Delete', 'Очуруп салуу', 'label.user.table.delete', 'Удалить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (50, 'View', 'Карап коруу', 'label.user.button.view', 'Просмотр');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (51, 'Edit', 'Оңдоп-түздөө', 'label.user.button.edit', 'Редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (52, 'Delete', 'Очуруп салуу', 'label.user.button.delete', 'Удалить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (53, 'Close', 'Жабып салуу', 'label.user.button.close', 'Закрыть');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (54, 'Save', 'Сактоо', 'label.user.button.save', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (55, 'Add User', 'Жаны колдонуучу', 'label.user.button.addUser', 'Добавить пользователя');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (56, 'Add SupervisorTerm', 'Жаны куратор шарты', 'label.user.button.addSupervisorTerm', 'Добавить условие кураторства');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (57, 'New User or SupervisorTerm Registration Form', 'Жаны колдонуучу же куратор шарты киргизуу', 'label.user.modal.title', 'Добавить пользователя или условие кураторства');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (58, 'ID', 'ID', 'label.user.modal.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (59, 'Name', 'Аталышы', 'label.user.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (60, 'Username', 'Аты', 'label.user.modal.username', 'Имя пользователя');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (61, 'Password', 'Сыр созу', 'label.user.modal.password', 'Пароль');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (62, 'Enabled', 'Статусу', 'label.user.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (63, 'Roles', 'Ролдору', 'label.user.modal.roles', 'Роли');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (64, 'SupervisorTerms', 'Куратор шарттары', 'label.user.modal.supervisorterms', 'Условия кураторства');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (65, 'Staff', 'Кызматкер', 'label.user.modal.staff', 'Сотрудник');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (66, 'Submit', 'Тастыктоо', 'label.user.button.submit', 'Подтвердить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (67, 'Cancel', 'Кайра кайтаруу', 'label.user.button.cancel', 'Отмена');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (68, null, null, 'label.document.title', 'Заголовок');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (69, null, null, 'label.task.openTasks', 'Открытые задачи');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (70, null, null, 'label.task.completedTasks', 'Завершенные задачи');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (71, null, null, 'label.open', 'Открыть');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (72, null, null, 'label.description', 'Описание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (73, null, null, 'label.state', 'Состояние');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (74, null, null, 'label.priority', 'Приоритет');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (75, null, null, 'label.owner', 'Владелец');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (76, null, null, 'label.createdDate', 'Дата создания');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (77, null, null, 'label.term', 'Срок');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (78, null, null, 'label.completedDate', 'Дата выполнения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (79, null, null, 'label.document.description', 'Описание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (80, null, null, 'label.document.documentType', 'Вид документа');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (81, null, null, 'label.document.documentSubType', 'Тип документа');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (82, null, null, 'label.document.generalStatus', 'Состояние');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (83, 'Edit', 'Ондоп туздоо', 'label.table.edit', 'Редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (84, 'View', 'Карап коруу', 'label.table.view', 'Просмотр');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (85, 'Delete', 'Очуруп салуу', 'label.table.delete', 'Удалить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (86, 'ID', 'ID', 'label.district.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (87, 'Name', 'Аталышы', 'label.district.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (88, 'Code', 'Код', 'label.district.table.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (89, 'REgion', 'Облусу', 'label.district.table.region', 'Область');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (90, 'Add new or edit', 'Жаны район кошуу же алмаштыруу', 'label.district.modal.title', 'Новый район или редактировние района');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (91, 'Name', 'Аталышы', 'label.district.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (92, 'Code', 'Коду', 'label.district.modal.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (93, 'Region', 'Облусу', 'label.district.modal.region', 'Область');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (94, 'Cancel', 'Жокко чыгаруу', 'label.district.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (95, 'Submit', 'Сактоо', 'label.district.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (96, 'District list', 'Райондор тизмеси', 'label.district.page.title', 'Список районов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (97, 'ID', 'ID', 'label.aokmotu.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (98, 'Name', 'Аталышы', 'label.aokmotu.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (99, 'Code', 'Код', 'label.aokmotu.table.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (100, 'District', 'Району', 'label.aokmotu.table.district', 'Район');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (101, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.aokmotu.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (102, 'Name', 'Аталышы', 'label.aokmotu.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (103, 'Code', 'Коду', 'label.aokmotu.modal.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (104, 'District', 'Району', 'label.aokmotu.modal.district', 'Район');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (105, 'Cancel', 'Жокко чыгаруу', 'label.aokmotu.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (106, 'Submit', 'Сактоо', 'label.aokmotu.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (107, 'Aokmotu list', 'Аокмоттор тизмеси', 'label.aokmotu.page.title', 'Список аокмоту');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (108, 'ID', 'ID', 'label.village.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (109, 'Name', 'Аталышы', 'label.village.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (110, 'Code', 'Код', 'label.village.table.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (111, 'Aokmotu', 'Аокмоту', 'label.village.table.aokmotu', 'Аокмоту');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (112, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.village.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (113, 'Name', 'Аталышы', 'label.village.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (114, 'Code', 'Коду', 'label.village.modal.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (115, 'Aokmotu', 'Аокмоту', 'label.village.modal.aokmotu', 'Аокмоту');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (116, 'Cancel', 'Жокко чыгаруу', 'label.village.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (117, 'Submit', 'Сактоо', 'label.village.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (118, 'Village list', 'Айылдар тизмеси', 'label.village.page.title', 'Список сел');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (119, 'ID', 'ID', 'label.iddocgivenby.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (120, 'Name', 'Аталышы', 'label.iddocgivenby.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (121, 'Enabled', 'Статусу', 'label.iddocgivenby.table.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (122, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.iddocgivenby.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (123, 'Name', 'Аталышы', 'label.iddocgivenby.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (124, 'Enabled', 'Статусу', 'label.iddocgivenby.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (125, 'Cancel', 'Жокко чыгаруу', 'label.iddocgivenby.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (126, 'Submit', 'Сактоо', 'label.iddocgivenby.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (127, 'Add new', 'Жаны кошуу', 'label.iddocgivenby.button.addIdDocGivenBy', 'Добавить новый орган выдачи документов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (128, 'Identity doc given by list', 'Мекемелер тизмеси', 'label.iddocgivenby.page.title', 'Список органов выдачи документов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (129, 'ID', 'ID', 'label.identityDocType.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (130, 'Name', 'Аталышы', 'label.identityDocType.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (131, 'Enabled', 'Статусу', 'label.identityDocType.table.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (132, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.identityDocType.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (133, 'Name', 'Аталышы', 'label.identityDocType.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (134, 'Enabled', 'Статусу', 'label.identityDocType.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (135, 'Cancel', 'Жокко чыгаруу', 'label.identityDocType.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (136, 'Submit', 'Сактоо', 'label.identityDocType.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (137, 'Add new', 'Жаны кошуу', 'label.identityDocType.button.addIdentityDocType', 'Добавить новый вид документа');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (138, 'Identity doc type list', 'Документтер тизмеси', 'label.identityDocType.page.title', 'Список документов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (139, 'ID', 'ID', 'label.employmentHistoryEventType.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (140, 'Name', 'Аталышы', 'label.employmentHistoryEventType.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (141, 'Enabled', 'Статусу', 'label.employmentHistoryEventType.table.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (142, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.employmentHistoryEventType.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (143, 'Name', 'Аталышы', 'label.employmentHistoryEventType.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (144, 'Enabled', 'Статусу', 'label.employmentHistoryEventType.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (145, 'Cancel', 'Жокко чыгаруу', 'label.employmentHistoryEventType.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (146, 'Submit', 'Сактоо', 'label.employmentHistoryEventType.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (147, 'Add new', 'Жаны кошуу', 'label.employmentHistoryEventType.button.addEmploymentHistoryEventType', 'Добавить новый');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (148, 'Employment history event type list', 'Окуя турлорунун тизмеси', 'label.employmentHistoryEventType.page.title', 'Список видов событий');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (149, 'ID', 'ID', 'label.cSystem.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (150, 'Name', 'Аталышы', 'label.cSystem.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (151, 'Enabled', 'Статусу', 'label.cSystem.table.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (152, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.cSystem.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (153, 'Name', 'Аталышы', 'label.cSystem.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (154, 'Enabled', 'Статусу', 'label.cSystem.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (155, 'Cancel', 'Жокко чыгаруу', 'label.cSystem.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (156, 'Submit', 'Сактоо', 'label.cSystem.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (157, 'Add new system', 'Жаны система кошуу', 'label.cSystem.button.addcSystem', 'Добавить систему');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (158, 'Add new information', 'Жаны маалымат кошуу', 'label.cSystem.button.addInformation', 'Добавить информацию');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (159, 'System list', 'Системалардын тизмеси', 'label.cSystem.page.title', 'Список систем');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (160, 'Staff', 'Кызматкер', 'label.user.modal.staff', 'Сотрудник');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (161, 'Staff', 'Кызматкер', 'label.user.table.staff', 'Сотрудник');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (162, null, null, 'label.document.sender', 'Отправитель');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (163, null, null, 'label.document.receiver', 'Получатель');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (164, '', '', 'label.task.description', 'Описание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (165, '', '', 'label.task.state', 'Состояние');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (166, '', '', 'label.task.priority', 'Приоритет');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (167, '', '', 'label.task.owner', 'Владелец');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (168, '', '', 'label.task.createdDate', 'Дата создания');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (169, '', '', 'label.task.dueDate', 'Срок');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (170, '', '', 'label.task.completedDate', 'Дата выполнения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (171, 'View', null, 'label.view', 'Просмотр');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (172, 'Edit', null, 'label.edit', 'Редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (173, 'Delete', null, 'label.delete', 'Удалить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (174, 'New Document Subtype', '', 'label.documentSubType.add', 'Новый документ');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (175, 'Document Subtypes', '', 'label.documentSubTypes.title', 'Список документов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (176, 'Document Subtype details', '', 'label.documentSubType.details', 'Детали');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (177, 'Internal name', '', 'label.documentSubType.internalName', 'Идентификатор');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (178, 'Save', null, 'label.save', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (179, 'Cancel', null, 'label.cancel', 'Отмена');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (180, 'Search...', null, 'label.search', 'Поиск...');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (181, 'ID', 'ID', 'label.objectType.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (182, 'Name', 'Аталышы', 'label.objectType.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (183, 'Code', 'Код', 'label.objectType.table.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (184, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.objectType.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (185, 'Name', 'Аталышы', 'label.objectType.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (186, 'Code', 'Код', 'label.objectType.modal.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (187, 'Cancel', 'Жокко чыгаруу', 'label.objectType.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (188, 'Submit', 'Сактоо', 'label.objectType.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (189, 'Add new', 'Жаны кошуу', 'label.objectType.button.addObjectType', 'Добавить ');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (190, 'Add field', 'Жаны талаача кошуу', 'label.objectType.button.addObjectField', 'Добавить поле');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (191, 'Add event', 'Жаны окуя кошуу', 'label.objectType.button.addObjectEvent', 'Добавить событие');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (192, 'Add event', 'шарт кошуу', 'label.objectType.button.addFixTerm', 'Добавить условие фиксации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (193, 'Object Type list', 'Объекттердин тизмеси', 'label.objectType.page.title', 'Список объектов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (194, 'ID', 'ID', 'label.messageResource.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (195, 'Name', 'Аталышы', 'label.messageResource.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (196, 'Message key', '-', 'label.messageResource.table.messageKey', 'ключевое слово');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (197, 'Eng', 'Англ.', 'label.messageResource.table.eng', 'Англ.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (198, 'Rus', 'Орусчасы', 'label.messageResource.table.rus', 'Русский');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (199, 'Kgz', 'Кыргызчасы', 'label.messageResource.table.kgz', 'Кыргызский');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (200, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.messageResource.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (201, 'Name', 'Аталышы', 'label.messageResource.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (202, 'Message key', '-', 'label.messageResource.modal.messageKey', 'ключевое слово');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (203, 'Eng', 'Англ.', 'label.messageResource.modal.eng', 'Англ.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (204, 'Rus', 'Орусчасы', 'label.messageResource.modal.rus', 'Русский');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (205, 'Kgz', 'Кыргызчасы', 'label.messageResource.modal.kgz', 'Кыргызский');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (206, 'Cancel', 'Жокко чыгаруу', 'label.messageResource.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (207, 'Submit', 'Сактоо', 'label.messageResource.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (208, 'Add new message resource', 'Жаны которулуш', 'label.messageResource.button.addMessageResource', 'Добавить перевод');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (209, 'Messsage resources list', 'Которулуштардын тизмеси', 'label.messageResource.page.title', 'Список переводов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (210, 'ID', 'ID', 'label.objectField.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (211, 'Name', 'Аталышы', 'label.objectField.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (212, 'Description', 'Тушундурмосу', 'label.objectField.table.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (213, 'Method', 'Метод', 'label.objectField.table.method', 'Метод');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (214, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.objectField.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (215, 'Name', 'Аталышы', 'label.objectField.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (216, 'Code', 'Код', 'label.objectField.modal.code', 'Код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (217, 'Cancel', 'Жокко чыгаруу', 'label.objectField.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (218, 'Submit', 'Сактоо', 'label.objectField.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (219, 'Add new', 'Жаны кошуу', 'label.objectField.button.addObjectType', 'Добавить ');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (220, 'Add field', 'Жаны талаача кошуу', 'label.objectField.button.addObjectField', 'Добавить поле');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (221, 'Add event', 'Жаны окуя кошуу', 'label.objectField.button.addObjectEvent', 'Добавить событие');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (222, 'Add event', 'шарт кошуу', 'label.objectField.button.addFixTerm', 'Добавить условие фиксации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (223, 'Object Type list', 'Объекттердин тизмеси', 'label.objectField.page.title', 'Список объектов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (224, 'ID', 'ID', 'label.role.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (225, 'Name', 'Аталышы', 'label.role.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (226, 'Enabled', 'Статусу', 'label.role.table.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (227, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.role.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (228, 'Name', 'Аталышы', 'label.role.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (229, 'Enabled', 'Статусу', 'label.role.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (230, 'Cancel', 'Жокко чыгаруу', 'label.role.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (231, 'Submit', 'Сактоо', 'label.role.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (232, 'Add new role', 'Жаны роль кошуу', 'label.role.button.addRole', 'Добавить роль');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (233, 'Role list', 'Ролдордун тизмеси', 'label.role.page.title', 'Список ролей');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (234, 'ID', 'ID', 'label.permission.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (235, 'Name', 'Аталышы', 'label.permission.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (236, 'Enabled', 'Статусу', 'label.permission.table.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (237, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.permission.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (238, 'Name', 'Аталышы', 'label.permission.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (239, 'Enabled', 'Статусу', 'label.permission.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (240, 'Cancel', 'Жокко чыгаруу', 'label.permission.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (241, 'Submit', 'Сактоо', 'label.permission.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (242, 'Add new permission', 'Жаны укук кошуу', 'label.permission.button.addPermission', 'Добавить право');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (243, 'Permission list', 'Укуктар тизмеси', 'label.permission.page.title', 'Список прав доступа');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (244, 'ID', 'ID', 'label.supervisorTerm.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (245, 'Name', 'Аталышы', 'label.supervisorTerm.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (246, 'Enabled', 'Статусу', 'label.supervisorTerm.table.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (247, 'DebtorType', 'Заемщик туру', 'label.supervisorTerm.table.debtorType', 'Вид заемщика');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (248, 'workSector', 'Заемщик тармагы', 'label.supervisorTerm.table.workSector', 'Отрасль');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (249, 'REgion', 'Облус', 'label.supervisorTerm.table.region', 'Область');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (250, 'District', 'Район', 'label.supervisorTerm.table.district', 'Район');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (251, 'Department', 'Болум', 'label.supervisorTerm.table.department', 'Отдел');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (252, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.supervisorTerm.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (253, 'Name', 'Аталышы', 'label.supervisorTerm.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (254, 'Enabled', 'Статусу', 'label.supervisorTerm.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (255, 'DebtorType', 'Заемщик туру', 'label.supervisorTerm.modal.debtorType', 'Вид заемщика');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (256, 'workSector', 'Заемщик тармагы', 'label.supervisorTerm.modal.workSector', 'Отрасль');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (257, 'REgion', 'Облус', 'label.supervisorTerm.modal.region', 'Область');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (258, 'District', 'Район', 'label.supervisorTerm.modal.district', 'Район');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (259, 'Department', 'Болум', 'label.supervisorTerm.modal.department', 'Отдел');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (260, 'Cancel', 'Жокко чыгаруу', 'label.supervisorTerm.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (261, 'Submit', 'Сактоо', 'label.supervisorTerm.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (262, 'Add new supervisor term', 'Жаны куратор шарты кошуу', 'label.supervisorTerm.button.addSupervisorTerm', 'Добавить условие кураторства');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (263, 'SupervisorTerm list', 'куратор шарты тизмеси', 'label.supervisorTerm.page.title', 'Список условий кураторства');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (264, 'ID', 'ID', 'label.information.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (265, 'Name', 'Аталышы', 'label.information.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (266, 'Enabled', 'Статусу', 'label.information.table.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (267, 'Add new or edit', 'Жаны кошуу же алмаштыруу', 'label.information.modal.title', 'Добавить или редактировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (268, 'Name', 'Аталышы', 'label.information.modal.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (269, 'Enabled', 'Статусу', 'label.information.modal.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (270, 'Cancel', 'Жокко чыгаруу', 'label.information.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (271, 'Submit', 'Сактоо', 'label.information.button.submit', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (272, 'Add new', 'Жаны кошуу', 'label.information.button.addEmploymentHistoryEventType', 'Добавить новый');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (273, 'Information type list', 'Маалымат тизмеси', 'label.information.page.title', 'Список информации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (274, 'Debtor', 'Заемщиктер', 'label.debtor.page.title', 'Список заемщиков');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (275, 'Search', 'Издоо', 'label.search', 'Поиск');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (276, 'First', 'Биринчиси', 'label.pagination.first', 'Начало');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (277, 'Previous', 'Буга чейинкиси', 'label.pagination.prev', 'Пред');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (278, 'Next', 'Мындан кийинкиси', 'label.pagination.next', 'След');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (279, 'Last', 'Акыркысы', 'label.pagination.last', 'Послед');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (280, 'Page Size', 'Баракча чондугу', 'label.pageSize', 'Кол-во');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (281, 'add Debtor', 'Жаны заемщик', 'label.debtor.addDebtor', 'Добавить заемщика');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (282, 'add Loan', 'Жаны кредит', 'label.debtor.addLoan', 'Добавить кредит');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (283, 'add Agreement', 'Жаны куроо', 'label.debtor.addAgreement', 'Добавить договор залога');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (284, 'add Procedure', 'Жаны ондуруу процедурасы', 'label.debtor.addProcedure', 'Добавить процедуру взыскания');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (285, 'add Phase', 'Жаны ондурусу фазасы', 'label.debtor.addPhase', 'Добавить фазу взыскания');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (286, 'ID', 'ID', 'label.debtor.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (287, 'Name', 'Аталышы', 'label.debtor.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (288, 'Debtor Type', 'Заемщик туру', 'label.debtor.table.debtorType', 'Вид заемщика');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (289, 'WorkSector', 'Тармак', 'label.debtor.table.workSector', 'Отрасль');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (290, 'District', 'Район', 'label.debtor.table.district', 'Район');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (291, 'OrgForm', 'Формасы', 'label.debtor.table.orgForm', 'Орг.форма');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (292, 'Debtor', 'Заемщик', 'label.add.debtor.title', 'Заемщик');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (293, 'Name', 'Аталышы', 'label.add.debtor.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (294, 'Type', 'Туру', 'label.add.debtor.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (295, 'Organizational Form', 'Формасы', 'label.add.debtor.orgForm', 'Орг.форма');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (296, 'WorkSector', 'Тармагы', 'label.add.debtor.workSector', 'Отрасль');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (297, 'Owner', 'заемщик', 'label.add.debtor.owner', 'Заемщик');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (298, 'Save', 'Сактоо', 'label.add.debtor.save', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (299, 'Cancel', 'Жокко чыгаруу', 'label.add.debtor.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (300, 'Loans', 'Кредит маалыматы', 'label.debtor.tab.loans', 'Кредитная информация');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (301, 'Collateral', 'Куроо маалыматы', 'label.debtor.tab.agreements', 'Залоговое обеспечение');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (302, 'Collection', 'Ондуруу маалыматтары', 'label.debtor.tab.procedures', 'Принудительное взыскание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (303, 'Loan', 'Насыя', 'label.add.loan.title', 'Кредит');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (304, 'Save', 'Сактоо', 'label.add.loan.save', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (305, 'Cancel', 'Жокко чыгаруу', 'label.add.loan.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (306, 'Select Currency', 'Валюта танданыз', 'label.add.loan.selectCurrency', 'Выбрать валюту');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (307, 'Select Type', 'Насыя турун танданыз', 'label.add.loan.selectType', 'Выбрать вид ');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (308, 'Select State', 'Статусун танданыз', 'label.add.loan.selectState', 'Выбрать статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (309, 'Select Parent', 'Башкы насыясын танданыз', 'label.add.loan.selectParent', 'Выбрать основной кредит');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (310, 'Select Order', 'Насыя беруу чечимин танданыз', 'label.add.loan.selectCreditOrder', 'Выбрать решение о выдаче кредита');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (311, 'Registration Number', 'Каттоо номуру', 'label.loan.regNumber', 'Регистрационный номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (312, 'Registration Date', 'Каттоо датасы', 'label.loan.regDate', 'Дата регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (313, 'Amount', 'Суммасы', 'label.loan.amount', 'Сумма по договору');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (314, 'Currency', 'Валютасы', 'label.loan.currency', 'Валюта');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (315, 'Type', 'Туру', 'label.loan.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (316, 'State', 'Статусу', 'label.loan.state', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (317, 'Supervisor', 'Куратору', 'label.loan.supervisorId', 'Куратор');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (318, 'Parent', 'Башкысы', 'label.loan.parent', 'Основной кредит');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (319, 'Order', 'Насыя беруу чечими', 'label.loan.creditOrderId', 'Решение о выдаче кредита');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (320, 'Registration Number', 'Каттоо номуру', 'label.loan.table.regNumber', 'Регистрационный номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (321, 'Registration Date', 'Каттоо датасы', 'label.loan.table.regDate', 'Дата регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (322, 'Amount', 'Суммасы', 'label.loan.table.amount', 'Сумма по договору');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (323, 'Currency', 'Валютасы', 'label.loan.table.currency', 'Валюта');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (324, 'Type', 'Туру', 'label.loan.table.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (325, 'State', 'Статусу', 'label.loan.table.state', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (326, 'Supervisor', 'Куратору', 'label.loan.table.superId', 'Куратор');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (327, 'Parent', 'Башкысы', 'label.loan.table.parentId', 'Основной кредит');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (328, 'Order', 'Насыя беруу чечими', 'label.loan.table.creditOrderId', 'Решение о выдаче кредита');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (329, 'Has SubLoan', 'Субкредити бар', 'label.loan.table.hasSubLoan', 'Наличие субкредитов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (330, 'Credit Terms', 'Шарттары', 'label.creditTerms', 'Условия');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (331, 'Write Offs', 'Списание', 'label.writeOffs', 'Списание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (332, 'Payment Schedules', 'Графиктери', 'label.paymentSchedules', 'Срочные обязательства');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (333, 'Payments', 'Толомдору', 'label.payments', 'Погашения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (334, 'Supervisor Plans', 'План', 'label.supervisorPlans', 'План');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (335, 'Loan goods', 'Товар', 'label.loanGoods', 'Товар');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (336, 'Debt transfers', 'Перевод', 'label.debtTransfers', 'Перевод долга');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (337, 'targeted uses', 'бар', 'label.targetedUses', 'Целевое исп.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (338, 'reconstructed lists', 'Реструктуризвция', 'label.reconstructedLists', 'Реструктуризация');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (339, 'bankrupts', 'Банкрот', 'label.bankrupts', 'Банкротство');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (340, 'collaterals', 'Куроо', 'label.collaterals', 'Залог');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (341, 'collection phases', 'Фзалары', 'label.collectionPhases', 'Фазы');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (342, 'Detailed summary', 'Эсептери', 'label.loanDetailedSummary', 'Детальный расчет');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (343, 'Summary', 'Эсептери', 'label.loanSummary', 'Расчет');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (344, 'Accrue', 'Процент, штрафтары', 'label.accrue', 'Начисление');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (345, 'startDate', 'Башталган датасы', 'label.creditTerm.startDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (346, 'interestRateValue', 'Процентная ставка', 'label.creditTerm.interestRateValue', 'Процентная ставка');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (347, 'ratePeriod', 'Период', 'label.creditTerm.ratePeriod', 'Период начисления процентов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (348, 'floatingRateType', 'Процентке ставка', 'label.creditTerm.floatingRateType', 'Плавающая ставка');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (349, 'enaltyOnPrincipleOverdueRateValue', 'Негизги Штраф', 'label.creditTerm.enaltyOnPrincipleOverdueRateValue', 'Штраф по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (350, 'penaltyOnPrincipleOverdueRateType', 'Негизги Штрафка ставка', 'label.creditTerm.penaltyOnPrincipleOverdueRateType', 'Ставка на штраф по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (351, 'penaltyOnInterestOverdueRateValue', 'Процент штраф', 'label.creditTerm.penaltyOnInterestOverdueRateValue', 'Штраф по процентам');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (352, 'penaltyOnInterestOverdueRateType', 'Процент штрафка ставка', 'label.creditTerm.penaltyOnInterestOverdueRateType', 'Ставка на штраф по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (353, 'penaltyLimitPercent', 'Шраф лимити', 'label.creditTerm.penaltyLimitPercent', 'Лимит начисления штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (354, 'penaltyLimitEndDate', 'Штраф саналбай баштаган дата', 'label.creditTerm.penaltyLimitEndDate', 'Дата приост. нач.штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (355, 'transactionOrder', 'Толоо туру', 'label.creditTerm.transactionOrder', 'Очередь погашения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (356, 'daysInMonthMethod', 'айдагы кун саноо методу', 'label.creditTerm.daysInMonthMethod', 'Метод кол-ва дней в мес.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (357, 'daysInYearMethod', 'жылдагы кун саноо методу', 'label.creditTerm.daysInYearMethod', 'Метод кол-ва дней в год');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (358, 'Add new Credit Term', 'Жаны шарт', 'label.button.addNewCreditTerm', 'Добавить условие кред-я');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (359, 'date', 'Датасы', 'label.writeOff.date', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (360, 'total amount', 'Сумма', 'label.writeOff.totalAmount', 'Всего');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (361, 'principal', 'Негизги карыз', 'label.writeOff.principal', 'Осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (362, 'interest', 'Процент', 'label.writeOff.interest', 'Проценты');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (363, 'penalty', 'Штраф', 'label.writeOff.penalty', 'Штрафы');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (364, 'fee', 'Комиссия', 'label.writeOff.fee', 'Комиссия');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (365, 'description', 'Тушундурмосу', 'label.writeOff.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (366, 'addNewWriteOff', 'Жаны списание', 'label.button.addNewWriteOff', 'Добавить списание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (367, 'expectedDate', 'Датасы', 'label.paymentSchedule.expectedDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (368, 'disbursement', 'Алынганы', 'label.paymentSchedule.disbursement', 'Освоение');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (369, 'principalPayment', 'Негизги карыз', 'label.paymentSchedule.principalPayment', 'Осн.сумма');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (370, 'interestPayment', 'Процент', 'label.paymentSchedule.interestPayment', 'Проценты');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (371, 'collectedIneterestPayment', 'Топт.процент', 'label.paymentSchedule.collectedInterestPayment', 'Нак.проценты');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (372, 'collectedPenaltyPayment', 'Топт.штраф', 'label.paymentSchedule.collectedPenaltyPayment', 'Нак.штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (373, 'installmentState', 'Статусу', 'label.paymentSchedule.installmentState', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (374, 'addNewPaymentSchedule', 'Жаны график', 'label.button.addNewPaymentSchedule', 'Добавить ср.обязательство');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (375, 'paymentDate', 'Датасы', 'label.payment.paymentDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (376, 'totalAmount', 'Сумма', 'label.payment.totalAmount', 'Итого');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (377, 'principal', 'Негизги карыз', 'label.payment.principal', 'Осн.сумма');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (378, 'ineterest', 'Процент', 'label.payment.interest', 'Проценты');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (379, 'penalty', 'Штраф', 'label.payment.penalty', 'Штрафы');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (380, 'fee', 'Комиссия', 'label.payment.fee', 'Комиссия');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (381, 'number', 'Номер', 'label.payment.number', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (382, 'paymentType', 'Туру', 'label.payment.paymentType', 'Вид платежа');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (383, 'addPayment', 'Толом кошуу', 'label.button.addNewPayment', 'Добавить погашение');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (384, 'paymentDate', 'Датасы', 'label.supervisorPlan.date', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (385, 'paymentDate', 'Суммасы', 'label.supervisorPlan.amount', 'Итого');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (386, 'paymentDate', 'Негизги карыз', 'label.supervisorPlan.principal', 'Осн.сумма');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (387, 'paymentDate', 'Процент', 'label.supervisorPlan.interest', 'Проценты');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (388, 'paymentDate', 'Штраф', 'label.supervisorPlan.penalty', 'Штрафы');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (389, 'paymentDate', 'Комиссия', 'label.supervisorPlan.fee', 'Комиссия');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (390, 'paymentDate', 'Тушундурмосу', 'label.supervisorPlan.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (391, 'addPlan', 'План кошуу', 'label.button.addNewSupervisorPlan', 'Добавить план');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (392, 'ID', 'ID', 'label.loanGoods.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (393, 'quantity', 'Кол-во', 'label.loanGoods.quantity', 'Кол-во');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (394, 'unitType', 'Ед. измерения', 'label.loanGoods.unitType', 'Ед. измерения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (395, 'goodType', 'Туру', 'label.loanGoods.goodType', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (396, 'addLoanGood', 'Товар кошуу', 'label.button.addNewLoanGood', 'Добавить товар');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (397, 'ID', 'ID', 'label.debtTransfer.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (398, 'number', 'Номери', 'label.debtTransfer.number', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (399, 'date', 'Датасы', 'label.debtTransfer.date', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (400, 'quantity', 'Саны', 'label.debtTransfer.quantity', 'Кол-во');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (401, 'price per unit', 'Баасы', 'label.debtTransfer.pricePerUnit', 'Цена');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (402, 'unit type', 'Ед. изм.', 'label.debtTransfer.unitType', 'Ед. изм.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (403, 'total cost', 'Суммасы', 'label.debtTransfer.totalCost', 'Сумма');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (404, 'payment', 'Толому', 'label.debtTransfer.transferPayment', 'Платеж');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (405, 'credit', 'Насыясы', 'label.debtTransfer.transferCredit', 'Кредит');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (406, 'person ', 'Заемщик', 'label.debtTransfer.transferPerson', 'Должник');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (407, 'goods', 'Товар', 'label.debtTransfer.goodsType', 'Товар');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (408, 'add DebtTransfer ', 'Карыз откоруп берууну кошуу', 'label.button.addNewDebtTransfer', 'Добавить перевод долга');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (409, 'ID', 'ID', 'label.targetedUse.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (410, 'Result', 'Жыйынтыгы', 'label.targetedUse.result', 'Результат');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (411, 'Created by', 'Киргизген', 'label.targetedUse.createdBy', 'Внесено');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (412, 'Created Date', 'Кирилген дата', 'label.targetedUse.createdDate', 'Дата внесения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (413, 'Approved by', 'Тастыктаган', 'label.targetedUse.approvedBy', 'Подтверждено');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (414, 'Approved date', 'Тастыкталган дата', 'label.targetedUse.approvedDate', 'Дата подтверждения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (415, 'Checked by', 'Текшерген', 'label.targetedUse.checkedBy', 'Проверено');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (416, 'Checked date', 'Текшерилген дата', 'label.targetedUse.checkedDate', 'Дата проверки');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (417, 'Attachment', 'Тиркеме', 'label.targetedUse.attachment', 'Приложение');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (418, 'add Targeted Use ', 'Туура колдонуу боюнча маалымат кошуу', 'label.button.addNewTargetedUse', 'Добавить целевое использование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (419, 'ID', 'ID', 'label.reconstructedList.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (420, 'onDate', 'Датасы', 'label.reconstructedList.onDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (421, 'oldLoan', 'Насыя', 'label.reconstructedList.oldLoan', 'Кредит');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (422, 'add Reconstructed List', 'Реструктуризация кошуу', 'label.button.addNewReconstructedList', 'Добавить реструктуризацию');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (423, 'ID', 'ID', 'label.bankrupt.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (424, 'startedOnDate', 'Башталган датасы', 'label.bankrupt.startedOnDate', 'Дата инициирования');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (425, 'finishedOnDate', 'Буткон датасы', 'label.bankrupt.finishedOnDate', 'Дата завершения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (426, 'add Bankrupt', 'Банкрот кошуу', 'label.button.addNewBankrupt', 'Добавить банкротство');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (427, 'ID', 'ID', 'label.collectionPhase.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (428, 'startDate', 'Дата инициирования', 'label.collectionPhase.startDate', 'Дата инициирования');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (429, 'closeDate', 'Дата завершения', 'label.collectionPhase.closeDate', 'Дата завершения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (430, 'lastEvent', 'посл. событие', 'label.collectionPhase.lastEvent', 'посл. событие');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (431, 'lastStatus', 'посл. статус', 'label.collectionPhase.lastStatus', 'посл. статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (432, 'phaseStatus', 'Статус', 'label.collectionPhase.phaseStatus', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (433, 'phaseType', 'Вид', 'label.collectionPhase.phaseType', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (434, 'add Collection Phase', 'Фаза кошуу', 'label.button.addNewCollectionPhase', 'Добавить фазу');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (435, 'ID', 'ID', 'label.loanDetailedSummary.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (436, 'onDate', 'На дату', 'label.loanDetailedSummary.onDate', 'На дату');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (437, 'disbursement', 'Освоение', 'label.loanDetailedSummary.disbursement', 'Освоение');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (438, 'totalDisbursement', 'Всего освоено', 'label.loanDetailedSummary.totalDisbursement', 'Всего освоено');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (439, 'principalPayment', 'По графику по осн.с.', 'label.loanDetailedSummary.principalPayment', 'По графику по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (440, 'totalPrincipalPayment', 'Всего по графику по осн.с.', 'label.loanDetailedSummary.totalPrincipalPayment', 'Всего по графику по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (441, 'principalPaid', 'Погашение по осн.с.', 'label.loanDetailedSummary.principalPaid', 'Погашение по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (442, 'totalPrincipalPaid', 'Всего погашено по осн.с.', 'label.loanDetailedSummary.totalPrincipalPaid', 'Всего погашено по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (443, 'principalWriteOff', 'Списание по осн.с.', 'label.loanDetailedSummary.principalWriteOff', 'Списание по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (444, 'totalPrincipalWriteOff', 'Всего списано по осн.с.', 'label.loanDetailedSummary.totalPrincipalWriteOff', 'Всего списано по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (445, 'principalOutstanding', 'Ост. по осн.с.', 'label.loanDetailedSummary.principalOutstanding', 'Ост. по осн.с. ');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (446, 'principalOverdue', 'Проср. по осн.с.', 'label.loanDetailedSummary.principalOverdue', 'Проср. по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (447, 'daysInPeriod', 'Кол-во дней', 'label.loanDetailedSummary.daysInPeriod', 'Кол-во дней');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (448, 'interestAccrued', 'Начисление проц.', 'label.loanDetailedSummary.interestAccrued', 'Начисление проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (449, 'totalInterestAccrued', 'Всего начислено проц.', 'label.loanDetailedSummary.totalInterestAccrued', 'Всего начислено проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (450, 'totalInterestAccruedOnInterestPayment', ',из них подлежит погашению', 'label.loanDetailedSummary.totalInterestAccruedOnInterestPayment', ',из них подлежит погашению');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (451, 'interestPayment', 'По графику по проц.', 'label.loanDetailedSummary.interestPayment', 'По графику по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (452, 'totalInterestPayment', 'Всего по графику по проц.', 'label.loanDetailedSummary.totalInterestPayment', 'Всего по графику по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (453, 'collectedInterestPayment', 'По графику нак.проц.', 'label.loanDetailedSummary.collectedInterestPayment', 'По графику нак.проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (454, 'totalCollectedInterestPayment', 'Всего по графику нак.проц.', 'label.loanDetailedSummary.totalCollectedInterestPayment', 'Всего по графику нак.проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (455, 'collectedInterestDisbursed', 'Всего нак. проц.', 'label.loanDetailedSummary.collectedInterestDisbursed', 'Всего нак. проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (456, 'interestPaid', 'Погашение проц.', 'label.loanDetailedSummary.interestPaid', 'Погашение проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (457, 'totalInterestPaid', 'Всего погашено проц.', 'label.loanDetailedSummary.totalInterestPaid', 'Всего погашено проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (458, 'interestOutstanding', 'Остаток по процентам', 'label.loanDetailedSummary.interestOutstanding', 'Остаток по процентам');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (459, 'interestOverdue', 'Проср. по проц.', 'label.loanDetailedSummary.interestOverdue', 'Проср. по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (460, 'penaltyAccrued', 'Начисление штр.', 'label.loanDetailedSummary.penaltyAccrued', 'Начисление штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (461, 'totalPenaltyAccrued', 'Всего начислено штр.', 'label.loanDetailedSummary.totalPenaltyAccrued', 'Всего начислено штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (462, 'collectedPenaltyPayment', 'По графику нак.штр.', 'label.loanDetailedSummary.collectedPenaltyPayment', 'По графику нак.штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (463, 'totalCollectedPenaltyPayment', 'Всего по графику нак.штр.', 'label.loanDetailedSummary.totalCollectedPenaltyPayment', 'Всего по графику нак.штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (464, 'collectedPenaltyDisbursed', 'Всего нак.штр.', 'label.loanDetailedSummary.collectedPenaltyDisbursed', 'Всего нак.штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (465, 'penaltyPaid', 'Погашение штр.', 'label.loanDetailedSummary.penaltyPaid', 'Погашение штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (466, 'totalPenaltyPaid', 'Всего погашено штр.', 'label.loanDetailedSummary.totalPenaltyPaid', 'Всего погашено штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (467, 'penaltyOutstanding', 'Остаток по штр.', 'label.loanDetailedSummary.penaltyOutstanding', 'Остаток по штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (468, 'penaltyOverdue', 'Проср. по штр.', 'label.loanDetailedSummary.penaltyOverdue', 'Проср. по штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (469, 'ID', 'ID', 'label.loanSummary.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (470, 'onDate', 'На дату', 'label.loanSummary.onDate', 'На дату');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (471, 'loanAmount', 'Сумма по договору', 'label.loanSummary.loanAmount', 'Сумма по договору');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (472, 'totalDisbursed', 'Всего освоено', 'label.loanSummary.totalDisbursed', 'Всего освоено');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (473, 'totalPaid', 'Всего погашено', 'label.loanSummary.totalPaid', 'Всего погашено');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (474, 'paidPrincipal', 'Пог. по осн.с.', 'label.loanSummary.paidPrincipal', 'Пог. по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (475, 'paidInterest', 'Пог. по проц.', 'label.loanSummary.paidInterest', 'Пог. по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (476, 'paidPenalty', 'Пог. по штр.', 'label.loanSummary.paidPenalty', 'Пог. по штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (477, 'paidFee', 'Пог. по комм.', 'label.loanSummary.paidFee', 'Пог. по комм.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (478, 'totalOutstanding', 'Всего ост.', 'label.loanSummary.totalOutstanding', 'Всего ост.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (479, 'outstadingPrincipal', 'Ост. по осн.с.', 'label.loanSummary.outstadingPrincipal', 'Ост. по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (480, 'outstadingInterest', 'Ост. по проц.', 'label.loanSummary.outstadingInterest', 'Ост. по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (481, 'outstadingPenalty', 'Ост. по штр.', 'label.loanSummary.outstadingPenalty', 'Ост. по штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (482, 'outstadingFee', 'Ост. по комм.', 'label.loanSummary.outstadingFee', 'Ост. по комм.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (483, 'totalOverdue', 'Всего проср.', 'label.loanSummary.totalOverdue', 'Всего проср.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (484, 'overduePrincipal', 'Проср. по осн.с.', 'label.loanSummary.overduePrincipal', 'Проср. по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (485, 'overdueInterest', 'Проср. по проц.', 'label.loanSummary.overdueInterest', 'Проср. по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (486, 'overduePenalty', 'Проср. по штр.', 'label.loanSummary.overduePenalty', 'Проср. по штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (487, 'overdueFee', 'Проср. по комм.', 'label.loanSummary.overdueFee', 'Проср. по комм.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (488, 'ID', 'ID', 'label.accrue.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (489, 'fromDate', 'с даты', 'label.accrue.fromDate', 'с даты');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (490, 'toDate', 'по дату', 'label.accrue.toDate', 'по дату');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (491, 'daysInPeriod', 'кол-во дней', 'label.accrue.daysInPeriod', 'кол-во дней');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (492, 'interestAccrued', 'Начисление проц.', 'label.accrue.interestAccrued', 'Начисление проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (493, 'penaltyAccrued', 'Начисление штр.', 'label.accrue.penaltyAccrued', 'Начисление штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (494, 'penaltyOnPrincipalOverdue', 'Штр. на осн.с.', 'label.accrue.penaltyOnPrincipalOverdue', 'Штр. на осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (495, 'penaltyOnInterestOverdue', 'Штр. на проц.', 'label.accrue.penaltyOnInterestOverdue', 'Штр. на проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (496, 'lastInstPassed', 'Проср. график', 'label.accrue.lastInstPassed', 'Проср. график');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (497, 'Number', 'Номери', 'label.agreement.table.number', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (498, 'Date', 'Датасы', 'label.agreement.table.date', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (499, 'Reg number', 'Каттоо номери', 'label.agreement.table.collRegNumber', 'Регистрационный номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (500, 'Reg Date', 'Каттоо датасы', 'label.agreement.table.collRegDate', 'Дата регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (501, 'Notary reg number', 'Нотариус каттоо номери', 'label.agreement.table.notaryRegNumber', 'Номер нотариальной регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (502, 'Notary reg date', 'Нотариус каттоо датасы', 'label.agreement.table.notaryRegDate', 'Дата нотариальной регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (503, 'Arrest reg number', 'Арест каттоо номери', 'label.agreement.table.arrestRegNumber', 'Номер ареста');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (504, 'Arrest reg date', 'Арест каттоо датасы', 'label.agreement.table.arrestRegDate', 'Дата ареста');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (505, 'ID', 'ID', 'label.procedure.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (506, 'ID', 'ID', 'label.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (507, 'Start date', 'Башталган датасы', 'label.procedure.table.startDate', 'Дата инициирования');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (508, 'Close date', 'Буткон датасы', 'label.procedure.table.closeDate', 'Дата завершения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (509, 'Last Phase', 'Акыркы фазасы', 'label.procedure.table.lastPhase', 'Последняя фаза');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (510, 'Last status', 'Акыркы статусы', 'label.procedure.table.lastStatusId', 'Последний статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (511, 'Status', 'Статусу', 'label.procedure.table.status', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (512, 'Type', 'Туру', 'label.procedure.table.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (513, 'Name', 'Аталышы', 'label.report.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (514, 'Report Type', 'Туру', 'label.report.reportType', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (515, 'Add Report', 'Жаны отчет', 'label.add.report', 'Добавить отчет');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (516, 'Report Templates', 'Шаблондор', 'label.report.info', 'Шаблоны');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (517, 'Report', 'Отчет', 'label.reports', 'Отчет');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (518, 'Report type', 'Отчет туру', 'label.report.type', 'Вид отчета');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (519, 'Generate', 'Чыгаруу', 'label.table.generate', 'Сформировать');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (520, 'Printout', 'Кагазга чыгаруу', 'label.printouts', 'Распечатка');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (521, 'Name', 'Аталышы', 'label.printout.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (522, 'Type', 'Туру', 'label.printout.printoutType', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (523, 'Add', 'Жаны', 'label.add.printout', 'Добавить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (524, 'Printout', 'Кагазга чыгаруу', 'label.printouts', 'Настройка распечатки');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (525, 'Templates', 'Шаблон', 'label.printout.templates', 'Шаблоны распечатки');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (526, 'Name', 'Аталышы', 'label.template.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (527, 'Name', 'Аталышы', 'label.printoutTemplate.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (528, 'Report', 'Отчет', 'label.template.report', 'Отчет');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (529, 'Save', 'Сактоо', 'label.button.save', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (530, 'orders', 'Насыя беруу чечими', 'label.debtor.orders', 'Решение на выдачу кредита');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (531, 'ID', 'ID', 'label.order.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (532, 'regNum', 'Номери', 'label.order.table.regNum', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (533, 'regDate', 'Датасы', 'label.order.table.regDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (534, 'description', 'Тушундурмосу', 'label.order.table.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (535, 'State', 'Статусу', 'label.order.table.creditOrderState', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (536, 'Type', 'Туру', 'label.order.table.creditOrderType', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (537, 'Add new', 'Жаны чечим', 'label.order.button.addNewCreditOrder', 'Добавить Решение на выдачу кредита');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (538, 'Loans', 'Кредиттер', 'label.agreement.table.loans', 'Кредиты');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (539, 'Owner', 'Куроо ээси', 'label.agreement.table.owner', 'Залогодатель');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (540, 'Cancel', 'Жокко чыгаруу', 'label.button.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (541, 'Collateral agreement', 'Куроо келишими', 'label.add.collateralAgreement.title', 'Договор залога');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (542, 'organizations', 'Мекемелер тизмеси', 'label.organizations', 'Список организаций');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (543, 'addressData', 'Адрес', 'label.addressData', 'Адресные данные');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (544, 'identityDocData', 'Кимдигин аныктаган маалымат', 'label.identityDocData', 'Идентификационные данные');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (545, 'contacts', 'Контакт', 'label.contacts', 'Контактные данные');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (546, 'add organization', 'Жаны мекеме', 'label.add.organization', 'Добавить организацию');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (547, 'add bankData', 'Жаны банк маалыматы', 'label.add.bankData', 'Добавить банковские данные');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (548, 'add department', 'Жаны болум', 'label.add.department', 'Добавить подразделение');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (549, 'add information', 'Жаны маалымат', 'label.add.information', 'Добавить информацию');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (550, 'add staff', 'Жаны кызматкер', 'label.add.staff', 'Добавить сотрудника');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (551, 'name', 'Аталышы', 'label.organization.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (552, 'description', 'Тушундурмосу', 'label.organization.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (553, 'name', 'Аталышы', 'label.org.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (554, 'description', 'Тушундурмосу', 'label.org.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (555, 'orgForm', 'Мекеме формасы', 'label.org.orgForm', 'Орг. форма');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (556, 'address', 'Адрес', 'label.org.address', 'Адрес');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (557, 'region', 'Облусу', 'label.org.region', 'Область');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (558, 'district', 'Району', 'label.org.district', 'Район');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (559, 'aokmotu', 'А.окмоту', 'label.org.aokmotu', 'А.окомту');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (560, 'village', 'Айылы', 'label.org.village', 'Село');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (561, 'name', 'Аталышы', 'label.identityDoc.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (562, 'enabled', 'Статусу', 'label.identityDoc.enabled', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (563, 'number', 'Номери', 'label.identityDoc.number', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (564, 'pin', 'ИНН', 'label.identityDoc.pin', 'ИНН');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (565, 'type', 'Туру', 'label.identityDoc.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (566, 'givenBy', 'Берген мекеме', 'label.identityDoc.givenBy', 'Кем выдано');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (567, 'date', 'Датасы', 'label.identityDoc.date', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (568, 'firstname', 'Аты', 'label.identityDoc.firstname', 'Имя');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (569, 'lastname', 'Фамилиясы', 'label.identityDoc.lastname', 'Фамилия');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (570, 'midname', 'Атасынын аты', 'label.identityDoc.midname', 'Отчетство');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (571, 'fullname', 'Толук аты', 'label.identityDoc.fullname', 'Полное наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (572, 'name', 'Контактар', 'label.contact.name', 'Контакты');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (573, 'persons', 'ФЛ тизмеси', 'label.persons', 'Список физ.лиц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (574, 'add person', 'жаны ФЛ', 'label.add.person', 'Добавить физ.лицо');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (575, 'name', 'Аталышы', 'label.person.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (576, 'description', 'Тушундурмосу', 'label.person.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (577, 'addressLine', 'Адрес', 'label.person.addressLine', 'Адрес');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (578, 'region', 'Облусу', 'label.person.region', 'Область');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (579, 'district', 'Району', 'label.person.district', 'Район');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (580, 'aokmotu', 'А.окмоту', 'label.person.aokmotu', 'А.окмоту');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (581, 'village', 'Айылы', 'label.person.village', 'Село');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (602, 'Order', 'Насыя чечими', 'label.add.order.title', 'Решение на выдачу кредита');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (603, 'Order', 'Насыя чечими', 'label.order.add', 'Решение на выдачу кредита');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (604, 'registrationNumber', 'Номери', 'label.order.registrationNumber', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (605, 'registrationDate', 'Датасы', 'label.order.registrationDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (606, 'description', 'Тушундурмосу', 'label.order.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (607, 'state', 'Статусу', 'label.order.state', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (608, 'type', 'Туру', 'label.order.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (609, 'save', 'Сактоо', 'label.form.save', 'Сохранить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (610, 'cancel', 'Жокко чыгаруу', 'label.form.cancel', 'Отменить');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (611, 'select.date', 'Дата тандоаныз', 'label.select.date', 'Указать дату');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (612, 'select', 'Тизмеден танданыз', 'label.select', 'Выбрать из списка');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (613, 'entityLists', 'Алуучу тизмелери', 'label.order.entityLists', 'Списки получателей');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (614, 'documentPackages', 'Документ пакети', 'label.order.documentPackages', 'Пакет документации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (615, 'terms', 'НАсыя шарттары', 'label.order.terms', 'Кредитные условия');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (616, 'addAppliedEntityList', 'Жаны тизме', 'label.order.button.addAppliedEntityList', 'Добавить список');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (617, 'listNum', 'Номери', 'label.appliedEntityList.table.listNum', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (618, 'listDate', 'Датасы', 'label.appliedEntityList.table.listDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (619, 'entityListState', 'Статусу', 'label.appliedEntityList.table.entityListState', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (620, 'entityListType', 'Туру', 'label.appliedEntityList.table.entityListType', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (621, 'listNum', 'Номери', 'label.entityList.number', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (622, 'listDate', 'Датасы', 'label.entityList.date', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (623, 'entityListState', 'Статусу', 'label.entityList.state', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (624, 'entityListType', 'Туру', 'label.entityList.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (625, 'entityListState', 'Статусу', 'label.appliedEntityList.table.entytiListState', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (626, 'entityListType', 'Туру', 'label.appliedEntityList.table.entytiListType', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (627, 'addDocumentPackage', 'Жаны документтер пакети', 'label.order.button.addDocumentPackage', 'Добавить пакет документации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (628, 'name', 'Аталышы', 'label.orderDocumentPackage.table.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (629, 'add documentPackage', 'Документтер пакети', 'label.documentPackage.add', 'Пакет документации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (630, 'orderDocumentPackage', 'Документтер пакети', 'label.add.orderDocumentPackage.title', 'Пакет документации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (631, 'name', 'Аталышы', 'label.orderDocumentPackage.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (632, 'addOrderTerm', 'Жаны шарт кошуу', 'label.order.button.addOrderTerm', 'Добавить условия кредитования');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (633, 'descrition', 'Тушундурмосу', 'label.orderTerm.table.descrition', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (634, 'fund', 'Каржы булагы', 'label.orderTerm.table.fund', 'Источник финансирования');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (635, 'amount', 'Суммасы', 'label.orderTerm.table.amount', 'Сумма');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (636, 'currency', 'Валютасы', 'label.orderTerm.table.currency', 'Валюта');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (637, 'frequencyQuantity', 'Цикл саны', 'label.orderTerm.table.frequencyQuantity', 'Кол-во циклов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (638, 'frequencyType', 'Цикл туру', 'label.orderTerm.table.frequencyType', 'Вид цикла');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (639, 'installmentQuantity', 'Период саны', 'label.orderTerm.table.installmentQuantity', 'Кол-во периодов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (640, 'installmentFirstDay', 'Толомдун биринчи куну', 'label.orderTerm.table.installmentFirstDay', 'Первый день погашения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (641, 'firstInstallmentDate', 'Толомдун биринчи датасы', 'label.orderTerm.table.firstInstallmentDate', 'Первая дата погашения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (642, 'lastInstalmentdate', 'Толомдун акыркы датасы', 'label.orderTerm.table.lastInstalmentdate', 'Последняя дата погашения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (643, 'minDaysDisbFirstInstallment', 'Биринчи толомго эн аз кун', 'label.orderTerm.table.minDaysDisbFirstInstallment', 'Мин. дней первого пог.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (644, 'maxDaysDisbFirstInstallment', 'Биринчи толомго эн коп кун', 'label.orderTerm.table.maxDaysDisbFirstInstallment', 'Макс. дней первого пог.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (645, 'graceOnPrinciplePaymentInstallment', 'Негизги карызды толоого женилдетуу периодтору', 'label.orderTerm.table.graceOnPrinciplePaymentInstallment', 'Кол-во льготных периодов (пог. осн.с.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (646, 'graceOnPrinciplePaymentDays', 'Негизиг карызды толоого женилдетуу кундору', 'label.orderTerm.table.graceOnPrinciplePaymentDays', 'Кол-во льготных дней (пог. осн.с.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (647, 'graceOnInterestPaymentInstallment', 'пайызды толоого женилдетуу периодтору', 'label.orderTerm.table.graceOnInterestPaymentInstallment', 'Кол-во льготных периодов (пог. проц.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (648, 'graceOnInterestPaymentDays', 'пайызды толоого женилдетуу кундору', 'label.orderTerm.table.graceOnInterestPaymentDays', 'Кол-во льготных дней (пог. проц.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (649, 'graceOnInterestAcrrInstallment', 'пайыз саноого женилдетуу периодтору', 'label.orderTerm.table.graceOnInterestAcrrInstallment', 'Кол-во льготных периодов (нач.проц.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (650, 'graceOnInterestAccrDays', 'пайыз саноого женилдетуу кундору', 'label.orderTerm.table.graceOnInterestAccrDays', 'Кол-во льготных дней (нач. проц.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (651, 'interestRateValue', 'Процент', 'label.orderTerm.table.interestRateValue', 'Процентная ставка');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (652, 'interestRateValuePerPeriod', 'Периодтогу процент', 'label.orderTerm.table.interestRateValuePerPeriod', 'Процентная ставка в период');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (653, 'interestType', 'Процент ставкасы', 'label.orderTerm.table.interestType', 'Ставка на нач. проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (654, 'penaltyOnPrincipleOverdueRateValue', 'Негизги карызга штраф', 'label.orderTerm.table.penaltyOnPrincipleOverdueRateValue', 'Штраф за проср. по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (655, 'penaltyOnPrincipleOverdueType', 'Негизги карызга штраф ставкасы', 'label.orderTerm.table.penaltyOnPrincipleOverdueType', 'Ставка на штраф за проср. по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (656, 'penaltyOnInterestOverdueType', 'Процентке штраф ставкасы', 'label.orderTerm.table.penaltyOnInterestOverdueType', 'Ставка на штраф за проср. по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (657, 'penaltyOnInterestOverdueRateValue', 'Процентке штраф', 'label.orderTerm.table.penaltyOnInterestOverdueRateValue', 'Штраф за проср. по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (658, 'daysInYearMethod', 'Бир жылда кун саноо методу', 'label.orderTerm.table.daysInYearMethod', 'Метод рас. кол-ва дней в году');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (659, 'daysInMonthMethod', 'Бир айда кун саноо методу', 'label.orderTerm.table.daysInMonthMethod', 'Метод рас. кол-ва дней в мес.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (660, 'transactionOrder', 'Толом ', 'label.orderTerm.table.transactionOrder', 'Очередность пог.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (661, 'interestAccrMethod', 'Процент саноо методу', 'label.orderTerm.table.interestAccrMethod', 'Метод нач. проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (662, 'earlyRepaymentAllowed', 'Алдын ала толоо', 'label.orderTerm.table.earlyRepaymentAllowed', 'Возм. досроч. пог.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (663, 'penaltyLimitPercent', 'Штраф чеги', 'label.orderTerm.table.penaltyLimitPercent', 'Макс. лимит. нач. штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (664, 'collateralFree', 'Куроодон бошотулган', 'label.orderTerm.table.collateralFree', 'Освоб. от зал.об.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (665, 'descrition', 'Тушундурмосу', 'label.order.term.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (666, 'fund', 'Каржы булагы', 'label.order.term.fund', 'Источник финансирования');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (667, 'amount', 'Суммасы', 'label.order.term.amount', 'Сумма');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (668, 'currency', 'Валютасы', 'label.order.term.currency', 'Валюта');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (669, 'frequencyQuantity', 'Цикл саны', 'label.order.term.freqQuantity', 'Кол-во циклов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (670, 'frequencyType', 'Цикл туру', 'label.order.term.freqType', 'Вид цикла');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (671, 'installmentQuantity', 'Период саны', 'label.order.term.installmentQuantity', 'Кол-во периодов');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (672, 'installmentFirstDay', 'Толомдун биринчи куну', 'label.order.term.insFirstDay', 'Первый день погашения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (673, 'firstInstallmentDate', 'Толомдун биринчи датасы', 'label.order.term.firstInstallmentDate', 'Первая дата погашения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (674, 'lastInstalmentdate', 'Толомдун акыркы датасы', 'label.order.term.lastInstallmentDate', 'Последняя дата погашения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (675, 'minDaysDisbFirstInstallment', 'Биринчи толомго эн аз кун', 'label.order.term.minDaysDisbFirstInst', 'Мин. дней первого пог.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (676, 'maxDaysDisbFirstInstallment', 'Биринчи толомго эн коп кун', 'label.order.term.maxDaysDisbFirstInst', 'Макс. дней первого пог.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (677, 'graceOnPrinciplePaymentInstallment', 'Негизги карызды толоого женилдетуу периодтору', 'label.order.term.graceOnPrinciplePaymentInst', 'Кол-во льготных периодов (пог. осн.с.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (678, 'graceOnPrinciplePaymentDays', 'Негизиг карызды толоого женилдетуу кундору', 'label.order.term.graceOnPrinciplePaymentDays', 'Кол-во льготных дней (пог. осн.с.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (679, 'graceOnInterestPaymentInstallment', 'пайызды толоого женилдетуу периодтору', 'label.order.term.graceOnInterestPaymentInst', 'Кол-во льготных периодов (пог. проц.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (680, 'graceOnInterestPaymentDays', 'пайызды толоого женилдетуу кундору', 'label.order.term.graceOnInterestPaymentDays', 'Кол-во льготных дней (пог. проц.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (681, 'graceOnInterestAcrrInstallment', 'пайыз саноого женилдетуу периодтору', 'label.order.term.graceOnInterestAccrInst', 'Кол-во льготных периодов (нач.проц.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (682, 'graceOnInterestAccrDays', 'пайыз саноого женилдетуу кундору', 'label.order.term.graceOnInterestAccrDays', 'Кол-во льготных дней (нач. проц.)');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (683, 'interestRateValue', 'Процент', 'label.order.term.interestRateValue', 'Процентная ставка');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (684, 'interestRateValuePerPeriod', 'Периодтогу процент', 'label.order.term.interestRateValuePerPeriod', 'Процентная ставка в период');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (685, 'interestType', 'Процент ставкасы', 'label.order.term.interestType', 'Ставка на нач. проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (686, 'penaltyOnPrincipleOverdueRateValue', 'Негизги карызга штраф', 'label.order.term.penaltyOnPrincipleOverdueRateValue', 'Штраф за проср. по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (687, 'penaltyOnPrincipleOverdueType', 'Негизги карызга штраф ставкасы', 'label.order.term.penaltyOnPrincipleOverdueType', 'Ставка на штраф за проср. по осн.с.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (688, 'penaltyOnInterestOverdueType', 'Процентке штраф ставкасы', 'label.order.term.penaltyOnInterestOverdueType', 'Ставка на штраф за проср. по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (689, 'penaltyOnInterestOverdueRateValue', 'Процентке штраф', 'label.order.term.penaltyOnInterestOverdueRateValue', 'Штраф за проср. по проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (690, 'daysInYearMethod', 'Бир жылда кун саноо методу', 'label.order.term.daysInYearMethod', 'Метод рас. кол-ва дней в году');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (691, 'daysInMonthMethod', 'Бир айда кун саноо методу', 'label.order.term.daysInMonthMethod', 'Метод рас. кол-ва дней в мес.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (692, 'transactionOrder', 'Толом ', 'label.order.term.transactionOrder', 'Очередность пог.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (693, 'interestAccrMethod', 'Процент саноо методу', 'label.order.term.interestAccrMethod', 'Метод нач. проц.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (694, 'earlyRepaymentAllowed', 'Алдын ала толоо', 'label.order.term.earlyRepaymentAllowed', 'Возм. досроч. пог.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (695, 'penaltyLimitPercent', 'Штраф чеги', 'label.order.term.penaltyLimitPercent', 'Макс. лимит. нач. штр.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (696, 'collateralFree', 'Куроодон бошотулган', 'label.order.term.collateralFree', 'Освоб. от зал.об.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (697, 'ID', 'ID', 'label.order.table.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (709, 'ID', 'ID', 'label.entityDocument.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (710, 'name', 'Аталышы', 'label.entityDocument.name', 'Наимнеование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (711, 'completed by', 'Толуктаган', 'label.entityDocument.completedBy', 'Укомплектовано');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (712, 'completed date', 'Толукталган датасы', 'label.entityDocument.completedDate', 'Дата комплектации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (713, 'completed desc', 'Тушундурмосу', 'label.entityDocument.completedDescription', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (714, 'approved by', 'Тастыктаган', 'label.entityDocument.approvedBy', 'Подтверждено');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (715, 'approved date', 'Тастыыктоо датасы', 'label.entityDocument.approvedDate', 'Дата подтверждения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (716, 'approved desc', 'Тушундурмосу', 'label.entityDocument.approvedDescription', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (717, 'reg number', 'Каттоо номери', 'label.entityDocument.registeredNumber', 'Регистрационный номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (718, 'reg date', 'Каттоо датасы', 'label.entityDocument.registeredDate', 'Дата регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (719, 'reg by', 'Каттаган', 'label.entityDocument.registeredBy', 'Зарегистрировано');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (720, 'reg description', 'Тушундурмосу', 'label.entityDocument.registeredDescription', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (721, 'ID', 'ID', 'label.document.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (722, 'name', 'ID', 'label.document.name', 'Наимнеование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (723, 'completedBy', 'ID', 'label.document.completedBy', 'Укомплектовано');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (724, 'completedDate', 'ID', 'label.document.completedDate', 'Дата комплектации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (725, 'completedDescription', 'ID', 'label.document.completedDescription', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (726, 'approvedBy', 'ID', 'label.document.approvedBy', 'Подтверждено');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (727, 'approvedDate', 'ID', 'label.document.approvedDate', 'Дата подтверждения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (728, 'approvedDescription', 'ID', 'label.document.approvedDescription', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (729, 'registeredNumber', 'ID', 'label.document.registeredNumber', 'Регистрационный номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (730, 'registeredDate', 'ID', 'label.document.registeredDate', 'Дата регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (731, 'registeredBy', 'ID', 'label.document.registeredBy', 'Зарегистрировано');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (732, 'registeredDescription', 'ID', 'label.document.registeredDescription', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (733, 'Add document', 'Документ кошуу', 'label.document.add', 'Добавить документ');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (734, 'name', 'Аталышы', 'label.documentPackage.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (735, 'completedDate', 'Толукталган датасы', 'label.documentPackage.completedDate', 'Дата комплектации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (736, 'approvedDate', 'Тастыкталган датасы', 'label.documentPackage.approvedDate', 'Дата подтверждения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (737, 'completedRatio', 'Толтукталганы', 'label.documentPackage.completedRatio', 'Доля комплектации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (738, 'approvedRatio', 'Тастыкталганы', 'label.documentPackage.approvedRatio', 'Доля подтверждения');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (739, 'registeredRatio', 'Катталганы', 'label.documentPackage.registeredRatio', 'Доля регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (740, 'state', 'Статусу', 'label.documentPackage.state', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (741, 'type', 'Туру', 'label.documentPackage.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (742, 'documents', 'Документтер', 'label.documents', 'Документы');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (743, 'orderTerm', 'Насыя шарты', 'label.orderTerm.add', 'Кредитное условие');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (744, 'Number', 'Номери', 'label.entityList.listNumber', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (745, 'Date', 'Датасы', 'label.entityList.listDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (746, 'Entities', 'Алуучулар', 'label.entities', 'Получатели');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (747, 'Name', 'Аталышы', 'label.entity.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (748, 'State', 'Статусу', 'label.entity.state', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (749, 'Add new', 'Жаны алуучу', 'label.entity.add', 'Добавить получателя');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (750, 'documentPackages', 'Документ пакеттери', 'label.documentPackages', 'Пакет документации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (751, 'State', 'Статусу', 'label.document.state', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (752, 'State', 'Статусу', 'label.entityDocument.state', 'Статус');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (753, 'Name', 'Аталышы', 'label.entity.number', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (754, 'orderDocuments', 'Документтер', 'label.orderDocuments', 'Документы');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (755, 'Name', 'Аталышы', 'label.orderDocuments.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (756, 'type', 'Туру', 'label.orderDocuments.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (757, 'Name', 'Аталышы', 'label.orderDocument.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (758, 'type', 'Туру', 'label.orderDocument.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (759, 'Document', 'Документ', 'label.orderDocument.add', 'Документ');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (760, 'type', 'Туру', 'label.orderDocument.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (761, 'Number', 'Номери', 'label.agreement.number', 'Номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (762, 'Date', 'Датасы', 'label.agreement.date', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (763, 'Reg number', 'Каттоо номери', 'label.agreement.collateralOfficeRegNumber', 'Регистрационный номер');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (764, 'Reg Date', 'Каттоо датасы', 'label.agreement.collateralOfficeRegDate', 'Дата регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (765, 'Notary reg number', 'Нотариус каттоо номери', 'label.agreement.notaryOfficeRegNumber', 'Номер нотариальной регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (766, 'Notary reg date', 'Нотариус каттоо датасы', 'label.agreement.notaryOfficeRegDate', 'Дата нотариальной регистрации');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (767, 'Arrest reg number', 'Арест каттоо номери', 'label.agreement.arrestRegNumber', 'Номер ареста');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (768, 'Arrest reg date', 'Арест каттоо датасы', 'label.agreement.arrestRegDate', 'Дата ареста');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (769, 'ID', 'ID', 'label.collateralItem.id', 'ID');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (770, 'name', 'Аталышы', 'label.collateralItem.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (771, 'description', 'Тушундурмосу', 'label.collateralItem.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (772, 'type', 'Туру', 'label.collateralItem.type', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (773, 'quantity', 'Саны', 'label.collateralItem.quantity', 'Кол-во');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (774, 'quantityType', 'Бирдиги', 'label.collateralItem.quantityType', 'Ед.изм.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (775, 'Collateral value', 'Куроо баасы', 'label.collateralItem.collateralValue', 'Зал.ст.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (776, 'Estimated value', 'Бычылган баасы', 'label.collateralItem.estimatedValue', 'Оценоч.ст.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (777, 'Condition', 'Акыбалы', 'label.collateralItem.conditionType', 'Состояние');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (778, '№1', '№1', 'label.collateralItem.details1', '№1');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (779, '№2', '№2', 'label.collateralItem.details2', '№2');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (780, '№3', '№3', 'label.collateralItem.details3', '№3');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (781, '№4', '№4', 'label.collateralItem.details4', '№4');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (782, '№5', '№5', 'label.collateralItem.details5', '№5');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (783, '№6', '№6', 'label.collateralItem.details6', '№6');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (784, 'document', 'документ', 'label.collateralItem.document', 'документ');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (785, 'incomplete_reason', 'Себеби', 'label.collateralItem.incomplete_reason', 'Причина');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (786, 'goods_type', 'Туру', 'label.collateralItem.goods_type', 'вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (787, 'goods_address', 'Адреси', 'label.collateralItem.goods_address', 'адрес');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (788, 'goods_id', 'Коду', 'label.collateralItem.goods_id', 'ид код');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (789, 'arrest_by', 'Камакка алган', 'label.collateralItem.arrest_by', 'наложен арест');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (790, 'name', 'Аталышы', 'label.agreementItem.name', 'Наименование');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (791, 'description', 'Тушундурмосу', 'label.agreementItem.description', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (792, 'type', 'Туру', 'label.agreementItem.itemType', 'Вид');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (793, 'quantity', 'Саны', 'label.agreementItem.quantity', 'Кол-во');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (794, 'quantityType', 'Бирдиги', 'label.agreementItem.quantityType', 'Ед.изм.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (795, 'Collateral value', 'Куроо баасы', 'label.agreementItem.collateralValue', 'Зал.ст.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (796, 'Estimated value', 'Бычылган баасы', 'label.agreementItem.estimatedValue', 'Оценоч.ст.');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (797, 'Condition', 'Акыбалы', 'label.agreementItem.conditionType', 'Состояние');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (798, 'Add item', 'Куроо кошуу', 'label.agreement.addItem', 'Добавить предмет залога');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (799, 'arrestFreeInfo', 'Куроодон чыгаруу маалыматы', 'label.collateralItem.arrestFreeInfo', 'Информация о снятии с залога');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (800, 'Add arrest free', 'Куроодон чыгаруу', 'label.arrestFreeInfo.add', 'Снять с залога');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (801, 'on Date', 'Куроо чыккан датасы', 'label.arrestFreeInfo.onDate', 'Дата снятия');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (802, 'Arrest free by', 'Куроодон чыгарган', 'label.arrestFreeInfo.arrestFreeBy', 'Снято');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (803, 'Add inspection', 'Куроо текшеруусун кошуу', 'label.itemInspectionResult.add', 'Добавить акт обследования');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (804, 'Date', 'Датасы', 'label.inpection.onDate', 'Дата обследования');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (805, 'result', 'Жыйынтыгы', 'label.inpection.type', 'Результат');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (806, 'Date', 'Датасы', 'label.arrestFree.onDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (807, 'arrestFreeBy', 'Куроодон чыгарган', 'label.arrestFree.arrestFreeBy', 'Снято');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (808, 'Inspection Result', 'Куроо текшеруусу', 'label.inspectionResult.add', 'Акт обследования');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (809, 'Date', 'Датасы', 'label.inspectionResult.onDate', 'Дата');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (810, 'Details', 'Тушундурмосу', 'label.inspectionResult.details', 'Примечание');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (811, 'Type', 'Жыйынтыгы', 'label.inspectionResult.type', 'Результат');
INSERT INTO mfloan.message_resource (id, eng, kgz, messageKey, rus) VALUES (812, 'Arrest free', 'Куроодон чыгаруу', 'label.arrestFree.add', 'Снятие залога');