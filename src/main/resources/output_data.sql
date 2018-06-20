

INSERT INTO `edu`.`report` (`name`, `reportType`) VALUES ('Отчет по задолженности', 'LOAN_SUMMARY');
INSERT INTO `edu`.`report` (`name`, `reportType`) VALUES ('Отчет по погашениям', 'LOAN_PAYMENT');
INSERT INTO `edu`.`report` (`name`, `reportType`) VALUES ('Отчет по графикам', 'LOAN_SCHEDULE');
INSERT INTO `edu`.`report` (`name`, `reportType`) VALUES ('Отчет по плану', 'LOAN_PLAN');
INSERT INTO `edu`.`report` (`name`, `reportType`) VALUES ('Отчет по залогу', 'COLLATERAL_ITEM');
INSERT INTO `edu`.`report` (`name`, `reportType`) VALUES ('Отчет по взысканию', 'COLLECTION_PHASE');

INSERT INTO `edu`.`generation_parameter_type` (`name`) VALUES ('Дата расчета');
INSERT INTO `edu`.`generation_parameter_type` (`name`) VALUES ('Дата состояния');
INSERT INTO `edu`.`generation_parameter_type` (`name`) VALUES ('Единица валюты');
INSERT INTO `edu`.`generation_parameter_type` (`name`) VALUES ('1. разрез');
INSERT INTO `edu`.`generation_parameter_type` (`name`) VALUES ('2. разрез');
INSERT INTO `edu`.`generation_parameter_type` (`name`) VALUES ('3. разрез');
INSERT INTO `edu`.`generation_parameter_type` (`name`) VALUES ('4. разрез');
INSERT INTO `edu`.`generation_parameter_type` (`name`) VALUES ('5. разрез');
INSERT INTO `edu`.`generation_parameter_type` (`name`) VALUES ('6. разрез');


INSERT INTO `edu`.`generation_parameter` (`date`, `name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2018-01-01', 'на 01.01.2018г.', '1', '1', '0', '1');

INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (область)', '2', '1', '0', '4');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (область)', '2', '1', '0', '5');

INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (район)', '2', '2', '0', '4');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (район)', '2', '2', '0', '5');

INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (заемщик)', '2', '3', '0', '4');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (заемщик)', '2', '3', '0', '5');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('3. разрез (заемщик)', '2', '3', '0', '6');

INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (кредит)', '2', '4', '0', '4');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (кредит)', '2', '4', '0', '5');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('3. разрез (кредит)', '2', '4', '0', '6');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('4. разрез (кредит)', '2', '4', '0', '7');

INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрез (погашение)', '2', '5', '0', '4');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрез (погашение)', '2', '5', '0', '5');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('3. разрез (погашение)', '2', '5', '0', '6');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('4. разрез (погашение)', '2', '5', '0', '7');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('5. разрез (погашение)', '2', '5', '0', '8');


INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрезе (отрасль)', '2', '6', '0', '4');
INSERT INTO `edu`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('2. разрезе (отрасль)', '2', '6', '0', '4');


INSERT INTO `edu`.`object_list` (`name`, `object_type_id`) VALUES ('По республике', '1');

INSERT INTO `edu`.`object_list` (`name`, `object_type_id`) VALUES ('По Баткенской области', '1');
INSERT INTO `edu`.`object_list` (`name`, `object_type_id`) VALUES ('По Ошской области', '1');
INSERT INTO `edu`.`object_list` (`name`, `object_type_id`) VALUES ('По Таласской области', '1');
INSERT INTO `edu`.`object_list` (`name`, `object_type_id`) VALUES ('По Нарынской области', '1');
INSERT INTO `edu`.`object_list` (`name`, `object_type_id`) VALUES ('По Иссык-Кульской области', '1');
INSERT INTO `edu`.`object_list` (`name`, `object_type_id`) VALUES ('По Чуйской области', '1');
INSERT INTO `edu`.`object_list` (`name`, `object_type_id`) VALUES ('По г.Бишкек', '1');

INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('1', '1');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('2', '1');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('3', '1');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('4', '1');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('5', '1');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('6', '1');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('7', '1');

INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('1', '2');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('2', '3');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('3', '4');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('4', '5');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('5', '6');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('6', '7');
INSERT INTO `edu`.`object_list_value` (`name`, `object_list_id`) VALUES ('7', '8');

INSERT INTO `edu`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По республике', '1');
INSERT INTO `edu`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Баткенской области', '2');
INSERT INTO `edu`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Ошской области', '3');
INSERT INTO `edu`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Таласской области', '4');
INSERT INTO `edu`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Нарынской области', '5');
INSERT INTO `edu`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Иссык-Кульской области', '6');
INSERT INTO `edu`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По Чуйской области', '7');
INSERT INTO `edu`.`filter_parameter` (`comparator`, `compared_value`, `filterParameterType`, `name`, `object_list_id`) VALUES ('EQUALS', '', 'OBJECT_LIST', 'По г.Бишкек', '8');

INSERT INTO `edu`.`report_template` (`name`, `report_id`) VALUES ('по республике (область, район, заемщик, кредит', '1');
INSERT INTO `edu`.`report_template` (`name`, `report_id`) VALUES ('по Баткенской области (область, район, заемщик, кредит', '1');
INSERT INTO `edu`.`report_template` (`name`, `report_id`) VALUES ('по Ошской области (область, район, заемщик, кредит', '1');

INSERT INTO `edu`.`report_template` (`name`, `report_id`) VALUES ('по республике (отрасль, область, заемщик, кредит', '1');

INSERT INTO `edu`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('1', '1');
INSERT INTO `edu`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('2', '2');
INSERT INTO `edu`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('3', '3');
INSERT INTO `edu`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('4', '1');

INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '1');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '2');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '5');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '8');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('1', '12');

INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '1');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '2');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '5');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '8');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('2', '12');

INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '1');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '2');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '5');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '8');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('3', '12');


INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '1');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '18');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '3');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '8');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('4', '12');




INSERT INTO `edu`.`report_template` (`name`, `report_id`) VALUES ('по республике (область, район, заемщик, кредит', '2');
INSERT INTO `edu`.`report_template` (`name`, `report_id`) VALUES ('по Баткенской области (область, район, заемщик, кредит', '2');
INSERT INTO `edu`.`report_template` (`name`, `report_id`) VALUES ('по Ошской области (область, район, заемщик, кредит', '2');

INSERT INTO `edu`.`report_template` (`name`, `report_id`) VALUES ('по республике (отрасль, область, заемщик, кредит', '2');

INSERT INTO `edu`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('5', '1');
INSERT INTO `edu`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('6', '2');
INSERT INTO `edu`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('7', '3');
INSERT INTO `edu`.`report_template_filter_parameter` (`report_template_id`, `filter_parameter_id`) VALUES ('8', '1');

INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '1');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '2');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '5');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '8');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('5', '12');

INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '1');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '2');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '5');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '8');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('6', '12');

INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '1');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '2');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '5');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '8');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('7', '12');


INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '1');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '18');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '3');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '8');
INSERT INTO `edu`.`report_template_generation_parameter` (`report_template_id`, `generation_parameter_id`) VALUES ('8', '12');






INSERT INTO printout (id, name, printoutType) VALUES (1, 'Реестр погашений', 'PAYMENT_SUMMARY');
INSERT INTO printout (id, name, printoutType) VALUES (2, 'Детальный расчет', 'LOAN_DETAILED_SUMMARY');
INSERT INTO printout (id, name, printoutType) VALUES (3, 'Расчет', 'LOAN_SUMMARY');
INSERT INTO printout (id, name, printoutType) VALUES (4, 'Акт сверки', 'LOAN_SUMMARY');
INSERT INTO printout (id, name, printoutType) VALUES (5, 'Претензия', 'COLLECTION_SUMMARY');

INSERT INTO printout_template (id, name, printout_id) VALUES (1, 'Реестр погашений', 1);
INSERT INTO printout_template (id, name, printout_id) VALUES (2, 'Детальный расчет', 2);
INSERT INTO printout_template (id, name, printout_id) VALUES (3, 'На исковое заявление', 3);
INSERT INTO printout_template (id, name, printout_id) VALUES (4, 'Акт сверки Пром', 4);
INSERT INTO printout_template (id, name, printout_id) VALUES (5, 'Претензия Пром', 5);





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
  FROM (`edu`.`address` `a`
    JOIN `edu`.`person` `p` ON ((`a`.`id` = `p`.`address_id`)));


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
  FROM (`edu`.`address` `a`
    JOIN `edu`.`organization` `o` ON ((`a`.`id` = `o`.`address_id`)));


CREATE VIEW owner_view AS
  SELECT
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_address_id`
            FROM `edu`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_address_id`
           FROM `edu`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_address_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_region_id`
            FROM `edu`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_region_id`
           FROM `edu`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_region_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_district_id`
            FROM `edu`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_district_id`
           FROM `edu`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_district_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_aokmotu_id`
            FROM `edu`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_aokmotu_id`
           FROM `edu`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_aokmotu_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN (SELECT `pv`.`v_person_village_id`
            FROM `edu`.`person_view` `pv`
            WHERE (`pv`.`v_person_id` = `o`.`entityId`))
     ELSE (SELECT `ov`.`v_organization_village_id`
           FROM `edu`.`organization_view` `ov`
           WHERE (`ov`.`v_organization_id` = `o`.`entityId`)) END) AS `v_owner_village_id`,
    `o`.`id`                                                       AS `id`,
    `o`.`version`                                                  AS `version`,
    `o`.`entityId`                                                 AS `entityId`,
    `o`.`name`                                                     AS `name`,
    `o`.`ownerType`                                                AS `ownerType`
  FROM `edu`.`owner` `o`;




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
  FROM (((((`edu`.`loan` `l`
    JOIN `edu`.`debtor_view` `dv` ON ((`dv`.`v_debtor_id` = `l`.`debtorId`))) JOIN `edu`.`creditOrder` `co`
      ON ((`co`.`id` = `l`.`creditOrderId`))) JOIN `edu`.`region` `r`
      ON ((`r`.`id` = `dv`.`v_debtor_region_id`))) JOIN `edu`.`district` `d`
      ON ((`d`.`id` = `dv`.`v_debtor_district_id`))) JOIN `edu`.`workSector` `ws`
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
  FROM ((((((`edu`.`collateralAgreement` `ca`
    JOIN `edu`.`debtor_view` `dv`) JOIN `edu`.`owner` `o1`) JOIN `edu`.`owner` `o2`) JOIN
    `edu`.`region` `r`) JOIN `edu`.`district` `d`) JOIN `edu`.`workSector` `ws`)
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
  FROM (`edu`.`collateral_agreement_view` `cav`
    JOIN `edu`.`collateralItem` `ci`)
  WHERE (`ci`.`collateralAgreementId` = `cav`.`v_ca_id`);







CREATE VIEW collection_procedure_debtor_view AS
  SELECT DISTINCT
    `pr`.`id` AS `v_cp_id`,
    `d`.`id`  AS `v_debtor_id`
  FROM `edu`.`collectionProcedure` `pr`
    JOIN `edu`.`collectionPhase` `ph`
    JOIN `edu`.`phaseDetails` `pd`
    JOIN `edu`.`loan` `l`
    JOIN `edu`.`debtor` `d`
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
  FROM ((`edu`.`collectionProcedure` `cp`
    JOIN `edu`.`debtor_view` `dv`) JOIN `edu`.`collection_procedure_debtor_view` `cpdv`)
  WHERE ((`cp`.`id` = `cpdv`.`v_cp_id`) AND (`dv`.`v_debtor_id` = `cpdv`.`v_debtor_id`));



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
  FROM `edu`.`collection_procedure_view` `cpv`
    JOIN `edu`.`collectionPhase` `cph`
    JOIN `edu`.`region` `r`
    JOIN `edu`.`district` `d`
    JOIN `edu`.`workSector` `ws`
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
  FROM `edu`.`loan_view` `lv`
    JOIN `edu`.`loanDetailedSummary` `lds`
  WHERE (`lds`.`loanId` = `lv`.`v_loan_id`);





CREATE VIEW loan_summary_view AS
  SELECT
    `lv`.`v_loan_id`                  AS `v_loan_id`,
    `lv`.`v_loan_amount`              AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`         AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`            AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`          AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`       AS `v_loan_supervisor_id`,
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
  FROM (`edu`.`loan_view` `lv`
    JOIN `edu`.`loanSummary` `ls`)
  WHERE (`ls`.`loanId` = `lv`.`v_loan_id`);





CREATE VIEW payment_schedule_view AS
  SELECT
    `lv`.`v_loan_id`                  AS `v_loan_id`,
    `lv`.`v_loan_amount`              AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`         AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`            AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`          AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`       AS `v_loan_supervisor_id`,
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
  FROM (`edu`.`loan_view` `lv`
    JOIN `edu`.`paymentSchedule` `ps`)
  WHERE (`ps`.`loanId` = `lv`.`v_loan_id`);





CREATE VIEW payment_view AS
  SELECT
    `lv`.`v_loan_id`                  AS `v_loan_id`,
    `lv`.`v_loan_amount`              AS `v_loan_amount`,
    `lv`.`v_loan_has_subLoan`         AS `v_loan_has_subLoan`,
    `lv`.`v_loan_reg_date`            AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`          AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`       AS `v_loan_supervisor_id`,
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
  FROM ((`edu`.`loan_view` `lv`
    JOIN `edu`.`payment` `p` ON ((`p`.`loanId` = `lv`.`v_loan_id`))) JOIN `edu`.`paymentType` `pt`
      ON ((`pt`.`id` = `p`.`paymentTypeId`)));



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
       `edu`.`organization`.`id` AS `id`,
       `edu`.`organization`.`name` AS `name`,
       'organization' AS `internalName`
     FROM
       `edu`.`organization` UNION ALL SELECT
                                           `edu`.`department`.`id` AS `id`,
                                           `edu`.`department`.`name` AS `name`,
                                           'department' AS `internalName`
                                         FROM
                                           `edu`.`department`
                                         WHERE
                                           (`edu`.`department`.`organization_id` = 1) UNION ALL SELECT
                                                                                                     `edu`.`staff`.`id` AS `id`,
                                                                                                     `edu`.`staff`.`name` AS `name`,
                                                                                                     'staff' AS `internalName`
                                                                                                   FROM
                                                                                                     `edu`.`staff`
                                                                                                   WHERE
                                                                                                     (`edu`.`staff`.`organization_id` = 1) UNION ALL SELECT
                                                                                                                                                          `edu`.`person`.`id` AS `id`,
                                                                                                                                                          `edu`.`person`.`name` AS `name`,
                                                                                                                                                          'person' AS `internalName`
                                                                                                                                                        FROM
                                                                                                                                                          `edu`.`person`) `data`
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








