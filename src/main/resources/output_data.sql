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

DROP TABLE IF EXISTS organization_view;
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

DROP TABLE IF EXISTS owner_view;
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

DROP TABLE IF EXISTS debtor_view;
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

DROP TABLE IF EXISTS loan_view;
CREATE VIEW loan_view AS
  SELECT
    `l`.`id`                       AS `v_loan_id`,
    `l`.`amount`                   AS `v_loan_amount`,

    `l`.`regDate`                  AS `v_loan_reg_date`,
    `l`.`regNumber`                AS `v_loan_reg_number`,
    `l`.`supervisorId`             AS `v_loan_supervisor_id`,
    `l`.`creditOrderId`            AS `v_loan_credit_order_id`,
    `l`.`currencyId`               AS `v_loan_currency_id`,
    `l`.`debtorId`                 AS `v_loan_debtor_id`,
    `l`.`loanStateId`              AS `v_loan_state_id`,
    `l`.`loanTypeId`               AS `v_loan_type_id`,

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

DROP TABLE IF EXISTS payment_view;
CREATE VIEW payment_view AS
  SELECT
    `lv`.`v_loan_id`                AS `v_loan_id`,
    `lv`.`v_loan_amount`            AS `v_loan_amount`,

    `lv`.`v_loan_reg_date`          AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`        AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`     AS `v_loan_supervisor_id`,

    `lv`.`v_loan_currency_id`       AS `v_loan_currency_id`,

    `lv`.`v_loan_state_id`          AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`           AS `v_loan_type_id`,

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

DROP TABLE IF EXISTS collateral_agreement_view;
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

DROP TABLE IF EXISTS collateral_item_view;
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

DROP TABLE IF EXISTS collection_procedure_debtor_view;
CREATE VIEW collection_procedure_debtor_view AS
  SELECT DISTINCT
    `pr`.`id` AS `v_cp_id`,
    `d`.`id`  AS `v_debtor_id`
  FROM ((((`mfloan`.`collectionProcedure` `pr`
    JOIN `mfloan`.`collectionPhase` `ph`) JOIN `mfloan`.`phaseDetails` `pd`) JOIN `mfloan`.`loan` `l`) JOIN
    `mfloan`.`debtor` `d`)
  WHERE ((`ph`.`collectionProcedureId` = `pr`.`id`) AND (`ph`.`id` = `pd`.`id`) AND (`pd`.`loan_id` = `l`.`id`) AND
         (`l`.`debtorId` = `d`.`id`));

DROP TABLE IF EXISTS collection_procedure_view;
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

DROP TABLE IF EXISTS collection_phase_view;
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

DROP TABLE IF EXISTS loan_detailed_summary_view;
CREATE VIEW loan_detailed_summary_view AS
  SELECT
    `lv`.`v_loan_id`                              AS `v_loan_id`,
    `lv`.`v_loan_amount`                          AS `v_loan_amount`,

    `lv`.`v_loan_reg_date`                        AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`                      AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`                   AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`                 AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`                     AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`                       AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`                        AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`                         AS `v_loan_type_id`,

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

DROP TABLE IF EXISTS loan_summary_view;
CREATE VIEW loan_summary_view AS
  SELECT
    `lv`.`v_loan_id`                AS `v_loan_id`,
    `lv`.`v_loan_amount`            AS `v_loan_amount`,

    `lv`.`v_loan_reg_date`          AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`        AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`     AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`   AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`       AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`         AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`          AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`           AS `v_loan_type_id`,

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

DROP TABLE IF EXISTS payment_schedule_view;
CREATE VIEW payment_schedule_view AS
  SELECT
    `lv`.`v_loan_id`                AS `v_loan_id`,
    `lv`.`v_loan_amount`            AS `v_loan_amount`,

    `lv`.`v_loan_reg_date`          AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`        AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`     AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`   AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`       AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`         AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`          AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`           AS `v_loan_type_id`,

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

DROP TABLE IF EXISTS applied_entity_view;
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

DROP TABLE IF EXISTS document_package_view;
CREATE VIEW document_package_view AS
  SELECT
    `dp`.`id`                               AS `v_document_package_id`,
    `dp`.`approvedDate`                     AS `v_document_package_approvedDate`,
    `dp`.`approvedRatio`                    AS `v_document_package_approvedRatio`,
    `dp`.`completedDate`                    AS `v_document_package_completedDate`,
    `dp`.`completedRatio`                   AS `v_document_package_completedRatio`,
    `dp`.`name`                             AS `v_document_package_name`,
    `dp`.`orderDocumentPackageId`           AS `v_document_package_orderDocumentPackageId`,
    `dp`.`registeredRatio`                  AS `v_document_package_registeredRatio`,
    `dp`.`appliedEntityId`                  AS `v_document_package_appliedEntityId`,
    `dp`.`documentPackageStateId`           AS `v_document_package_documentPackageStateId`,
    `dp`.`documentPackageTypeId`            AS `v_document_package_documentPackageTypeId`,
    `aev`.`v_applied_entity_id`                   AS `v_applied_entity_id`,
    `aev`.`v_applied_entity_version`              AS `v_applied_entity_version`,
    `aev`.`v_applied_entity_appliedEntityListId`  AS `v_applied_entity_appliedEntityListId`,
    `aev`.`v_applied_entity_appliedEntityStateId` AS `v_applied_entity_appliedEntityStateId`,
    `aev`.`v_applied_entity_ownerId`              AS `v_applied_entity_ownerId`,
    `aev`.`v_owner_address_id`                    AS `v_owner_address_id`,
    `aev`.`v_owner_region_id`                     AS `v_owner_region_id`,
    `aev`.`v_owner_district_id`                   AS `v_owner_district_id`,
    `aev`.`v_owner_aokmotu_id`                    AS `v_owner_aokmotu_id`,
    `aev`.`v_owner_village_id`                    AS `v_owner_village_id`,
    `aev`.`v_owner_entityId`                      AS `v_owner_entityId`,
    `aev`.`v_owner_ownerType`                     AS `v_owner_ownerType`,
    `aev`.`v_owner_name`                          AS `v_owner_name`,
    `aev`.`v_ael_id`                              AS `v_ael_id`,
    `aev`.`v_ael_listDate`                        AS `v_ael_listDate`,
    `aev`.`v_ael_listNumber`                      AS `v_ael_listNumber`,
    `aev`.`v_ael_appliedEntityListStateId`        AS `v_ael_appliedEntityListStateId`,
    `aev`.`v_ael_appliedEntityListTypeId`         AS `v_ael_appliedEntityListTypeId`,
    `aev`.`v_ael_creditOrderId`                   AS `v_ael_creditOrderId`,
    `aev`.`v_co_id`                               AS `v_co_id`,
    `aev`.`v_co_regDate`                          AS `v_co_regDate`,
    `aev`.`v_co_regNumber`                        AS `v_co_regNumber`,
    `aev`.`v_co_creditOrderStateId`               AS `v_co_creditOrderStateId`,
    `aev`.`v_co_creditOrderTypeId`                AS `v_co_creditOrderTypeId`
  FROM (`mfloan`.`documentPackage` `dp`
    JOIN `mfloan`.`applied_entity_view` `aev`)
  WHERE (dp.`appliedEntityId` = `aev`.`v_applied_entity_id`);

DROP TABLE IF EXISTS entity_document_view;
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
    `dpv`.`v_document_package_id`                     AS `v_document_package_id`,
    `dpv`.`v_document_package_approvedDate`           AS `v_document_package_approvedDate`,
    `dpv`.`v_document_package_approvedRatio`          AS `v_document_package_approvedRatio`,
    `dpv`.`v_document_package_completedDate`          AS `v_document_package_completedDate`,
    `dpv`.`v_document_package_completedRatio`         AS `v_document_package_completedRatio`,
    `dpv`.`v_document_package_name`                   AS `v_document_package_name`,
    `dpv`.`v_document_package_orderDocumentPackageId` AS `v_document_package_orderDocumentPackageId`,
    `dpv`.`v_document_package_registeredRatio`        AS `v_document_package_registeredRatio`,
    `dpv`.`v_document_package_appliedEntityId`        AS `v_document_package_appliedEntityId`,
    `dpv`.`v_document_package_documentPackageStateId` AS `v_document_package_documentPackageStateId`,
    `dpv`.`v_document_package_documentPackageTypeId`  AS `v_document_package_documentPackageTypeId`,
    `dpv`.`v_applied_entity_id`                       AS `v_applied_entity_id`,
    `dpv`.`v_applied_entity_version`                  AS `v_applied_entity_version`,
    `dpv`.`v_applied_entity_appliedEntityListId`      AS `v_applied_entity_appliedEntityListId`,
    `dpv`.`v_applied_entity_appliedEntityStateId`     AS `v_applied_entity_appliedEntityStateId`,
    `dpv`.`v_applied_entity_ownerId`                  AS `v_applied_entity_ownerId`,
    `dpv`.`v_owner_address_id`                        AS `v_owner_address_id`,
    `dpv`.`v_owner_region_id`                         AS `v_owner_region_id`,
    `dpv`.`v_owner_district_id`                       AS `v_owner_district_id`,
    `dpv`.`v_owner_aokmotu_id`                        AS `v_owner_aokmotu_id`,
    `dpv`.`v_owner_village_id`                        AS `v_owner_village_id`,
    `dpv`.`v_owner_entityId`                          AS `v_owner_entityId`,
    `dpv`.`v_owner_ownerType`                         AS `v_owner_ownerType`,
    `dpv`.`v_owner_name`                              AS `v_owner_name`,
    `dpv`.`v_ael_id`                                  AS `v_ael_id`,
    `dpv`.`v_ael_listDate`                            AS `v_ael_listDate`,
    `dpv`.`v_ael_listNumber`                          AS `v_ael_listNumber`,
    `dpv`.`v_ael_appliedEntityListStateId`            AS `v_ael_appliedEntityListStateId`,
    `dpv`.`v_ael_appliedEntityListTypeId`             AS `v_ael_appliedEntityListTypeId`,
    `dpv`.`v_ael_creditOrderId`                       AS `v_ael_creditOrderId`,
    `dpv`.`v_co_id`                                   AS `v_credit_order_id`,
    `dpv`.`v_co_regDate`                              AS `v_co_regDate`,
    `dpv`.`v_co_regNumber`                            AS `v_co_regNumber`,
    `dpv`.`v_co_creditOrderStateId`                   AS `v_co_creditOrderStateId`,
    `dpv`.`v_co_creditOrderTypeId`                    AS `v_co_creditOrderTypeId`,
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
    JOIN `mfloan`.`document_package_view` `dpv`)
  WHERE (`ed`.`documentPackageId` = `document_package_view`.`v_document_package_id`);














CREATE VIEW collateral_inspection_view AS
  SELECT
    `civ`.`v_ca_id`                        AS `v_ca_id`,
    `civ`.`v_ca_version`                   AS `v_ca_version`,
    `civ`.`v_ca_agreementDate`             AS `v_ca_agreementDate`,
    `civ`.`v_ca_agreementNumber`           AS `v_ca_agreementNumber`,
    `civ`.`v_ca_arrestRegDate`             AS `v_ca_arrestRegDate`,
    `civ`.`v_ca_arrestRegNumber`           AS `v_ca_arrestRegNumber`,
    `civ`.`v_ca_collateralOfficeRegDate`   AS `v_ca_collateralOfficeRegDate`,
    `civ`.`v_ca_collateralOfficeRegNumber` AS `v_ca_collateralOfficeRegNumber`,
    `civ`.`v_ca_notaryOfficeRegDate`       AS `v_ca_notaryOfficeRegDate`,
    `civ`.`v_ca_notaryOfficeRegNumber`     AS `v_ca_notaryOfficeRegNumber`,
    `civ`.`v_ca_ownerId`                   AS `v_ca_ownerId`,
    `civ`.`v_debtor_id`                    AS `v_debtor_id`,
    `civ`.`v_debtor_name`                  AS `v_debtor_name`,
    `civ`.`v_debtor_type_id`               AS `v_debtor_type_id`,
    `civ`.`v_debtor_org_form_id`           AS `v_debtor_org_form_id`,
    `civ`.`v_debtor_owner_id`              AS `v_debtor_owner_id`,
    `civ`.`v_debtor_work_sector_id`        AS `v_debtor_work_sector_id`,
    `civ`.`v_debtor_entity_id`             AS `v_debtor_entity_id`,
    `civ`.`v_debtor_owner_type`            AS `v_debtor_owner_type`,
    `civ`.`v_debtor_address_id`            AS `v_debtor_address_id`,
    `civ`.`v_debtor_region_id`             AS `v_debtor_region_id`,
    `civ`.`v_debtor_district_id`           AS `v_debtor_district_id`,
    `civ`.`v_debtor_aokmotu_id`            AS `v_debtor_aokmotu_id`,
    `civ`.`v_debtor_village_id`            AS `v_debtor_village_id`,
    `civ`.`v_region_name`                  AS `v_region_name`,
    `civ`.`v_district_name`                AS `v_district_name`,
    `civ`.`v_work_sector_name`             AS `v_work_sector_name`,
    `civ`.`v_ci_id`                        AS `v_ci_id`,
    `civ`.`v_ci_version`                   AS `v_ci_version`,
    `civ`.`v_ci_collateralValue`           AS `v_ci_collateralValue`,
    `civ`.`v_ci_demand_rate`               AS `v_ci_demand_rate`,
    `civ`.`v_ci_description`               AS `v_ci_description`,
    `civ`.`v_ci_estimatedValue`            AS `v_ci_estimatedValue`,
    `civ`.`v_ci_name`                      AS `v_ci_name`,
    `civ`.`v_ci_quantity`                  AS `v_ci_quantity`,
    `civ`.`v_ci_risk_rate`                 AS `v_ci_risk_rate`,
    `civ`.`v_ci_collateralAgreementId`     AS `v_ci_collateralAgreementId`,
    `civ`.`v_ci_conditionTypeId`           AS `v_ci_conditionTypeId`,
    `civ`.`v_ci_itemTypeId`                AS `v_ci_itemTypeId`,
    `civ`.`v_ci_quantityTypeId`            AS `v_ci_quantityTypeId`,
    `cir`.`details`                        AS `v_cir_details`,
    `cir`.`onDate`                         AS `v_cir_onDate`,
    `cir`.`inspectionResultTypeId`         AS `v_cir_inspectionResultTypeId`
  FROM (`mfloan`.`collateral_item_view` `civ`
    JOIN `mfloan`.`collateralItemInspectionResult` `cir`)
  WHERE (`cir`.`collateralItemId` = `civ`.`v_ci_id`);









DROP TABLE IF EXISTS reference_view;
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

INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (1, 'TEXT', 0, 0, 1, 7, null, 0, 'Информация по оформлению документации', 0, 'CONSTANT', '', 'Оформление. Заголовок страницы. Строка 1. ', 1, 'PAGE_TITLE', 2, 2, 6, 0);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (3, 'TEXT', 0, 0, 1, 7, null, 0, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Оформление. Заголовок страницы. Строка 2.', 2, 'PAGE_TITLE', 3, 3, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (4, 'TEXT', 0, 0, 1, 1, null, 0, 'Кол-во пол.', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Кол-во получателей', 1, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (5, 'TEXT', 0, 0, 2, 2, null, 0, 'Наименование', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Наименование', 2, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (6, 'TEXT', 0, 0, 4, 7, null, 0, 'в том числе', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Кол-во документов в том числе', 4, 'TABLE_HEADER', 5, 5, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (7, 'TEXT', 0, 0, 3, 3, null, 0, 'Кол-во док.', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Кол-во документов', 3, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (8, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Итого. Кол-во получателей', 1, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (9, 'TEXT', 0, 0, 0, 0, null, 0, 'ИТОГО', 0, 'CONSTANT', '', 'Оформление. Итого. Итого', 2, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (10, 'INTEGER', 0, 0, 0, 0, null, 0, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Итого. Кол-во документов', 3, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (11, 'TEXT', 0, 0, 4, 4, null, 0, 'укомлектовано', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Укомлектовано', 5, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (12, 'TEXT', 0, 0, 5, 5, null, 0, 'не укомпл.', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Не укомплектовано', 6, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (13, 'TEXT', 0, 0, 6, 6, null, 0, 'проверено', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Проверено', 7, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (14, 'TEXT', 0, 0, 7, 7, null, 0, 'не проверено', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Не проверено', 8, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (15, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Итого. Кол-во укомплектованных документов', 4, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (16, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Итого. Кол-во проверенных документов', 6, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (17, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Итого. Кол-во не укопмлетованных документов', 5, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (18, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Итого. Кол-во непроверенных документов', 7, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (19, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Разрез 1. - кол-во получателей', 1, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (20, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 1. Наименование', 2, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (21, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 2. Кол-во документов', 3, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (22, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Разрез 2. кол-во получателей', 1, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (23, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Разрез 3. Кол-во получателей', 1, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (24, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'CONSTANT', 'EntityCount', 'Оформление. Разрез 4. Кол-во получателей', 1, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (25, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'CONSTANT', 'EntityCount', 'Оформление. Разрез 5. Кол-во получателей', 1, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (26, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'CONSTANT', 'EntityCount', 'Оформление. Разрез 6. Кол-во получателей', 1, 'TABLE_GROUP6', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (27, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 2. Наименование', 2, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (28, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 2. Наименование', 2, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (29, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 3. Наименование', 2, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (30, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 4. Наименование', 2, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (31, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 5. Наименование', 2, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (32, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 1. Кол-во укомплектованных документов ', 4, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (33, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 1. Количество проверенных документов', 6, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (34, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 1. Кол-во не укопмлетованных документов ', 5, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (35, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 1. Кол-во непроверенных документов ', 7, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (36, 'INTEGER', 0, 0, 0, 0, null, 0, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 1. Кол-во док.', 3, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (37, 'INTEGER', 0, 0, 0, 0, null, 0, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 2. Кол-во док.', 3, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (38, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 2. Кол-во укомплектованных документов ', 4, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (39, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 2. Количество проверенных документов', 6, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (40, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 2. Кол-во не укопмлетованных документов ', 5, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (41, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 2. Кол-во непроверенных документов ', 7, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (42, 'INTEGER', 0, 0, 0, 0, null, 0, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 3. Кол-во док.', 3, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (43, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 3. Кол-во укомплектованных документов ', 4, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (44, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 3. Количество проверенных документов', 6, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (45, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 3. Кол-во не укопмлетованных документов ', 5, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (46, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 3. Кол-во непроверенных документов ', 7, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (47, 'INTEGER', 0, 0, 0, 0, null, 0, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 4. Кол-во док.', 3, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (48, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 4. Кол-во укомплектованных документов ', 4, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (49, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 4. Количество проверенных документов', 6, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (50, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 4. Кол-во не укопмлетованных документов ', 5, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (51, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 4. Кол-во непроверенных документов ', 7, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (52, 'INTEGER', 0, 0, 0, 0, null, 0, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 5. Кол-во док.', 3, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (53, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 5. Кол-во укомплектованных документов ', 4, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (54, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 5. Количество проверенных документов', 6, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (55, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 5. Кол-во не укопмлетованных документов ', 5, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (56, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 5. Кол-во непроверенных документов ', 7, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (57, 'TEXT', 0, 0, 8, 8, null, 0, 'Статус', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Статус', 8, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (61, 'TEXT', 0, 0, 0, 0, null, 21, 'applied_entity_status', 0, 'ENTITY_DOCUMENT', 'V_applied_entity_appliedEntityStateId', 'Оформление. Разрез 3. Статус', 8, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (62, 'TEXT', 0, 0, 0, 0, null, 23, 'document_package_status', 0, 'ENTITY_DOCUMENT', 'v_document_package_documentPackageStateId', 'Оформление. Разрез 4. Статус', 8, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (63, 'TEXT', 0, 0, 0, 0, null, 22, 'entity_document_status', 0, 'ENTITY_DOCUMENT', 'V_entity_document_entityDocumentStateId', 'Оформление. Разрез 5. Статус', 8, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (64, 'TEXT', 0, 0, 9, 9, null, 0, 'Укомплектовано', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Укомплектовано', 9, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (65, 'TEXT', 0, 0, 10, 10, null, 0, 'Дата комплектации', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Дата комплектации', 10, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (66, 'TEXT', 0, 0, 11, 11, null, 0, 'Примечание', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Примечание', 11, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (67, 'TEXT', 0, 0, 12, 12, null, 0, 'Проверено', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Проверено', 12, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (68, 'TEXT', 0, 0, 13, 13, null, 0, 'Дата проверки', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Дата проверки', 13, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (69, 'TEXT', 0, 0, 14, 14, null, 0, 'Примечание', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Примечание2', 14, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (75, 'TEXT', 0, 0, 0, 0, null, 5, 'supervisor', 0, 'ENTITY_DOCUMENT', 'v_entity_document_completedBy', 'Оформление. Разрез 5. Укомплектовано', 9, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (81, 'TEXT', 0, 0, 0, 0, null, 5, 'supervisor', 0, 'ENTITY_DOCUMENT', 'v_entity_document_approvedBy', 'Оформление. Разрез 5. Проверено', 12, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (87, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_completedDate', 'Оформление. Разрез 5. Дата комплектации', 10, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (93, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_approvedDate', 'Оформление. Разрез 5. Дата проверки', 13, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (99, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_completedDescription', 'Оформление. Разрез 5. Примечание', 11, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (105, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_approvedDescription', 'Оформление. Разрез 5. Примечание', 14, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (106, 'TEXT', 0, 0, 1, 8, null, 0, 'Информация по взысканию', 0, 'CONSTANT', '', 'Взыскание. Заголовок страницы. Строка 1.', 1, 'PAGE_TITLE', 1, 1, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (107, 'TEXT', 0, 0, 1, 8, null, 0, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Взыскание. Заголовок страницы. Строка 2.', 1, 'PAGE_TITLE', 2, 2, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (108, 'TEXT', 0, 0, 1, 1, null, 0, 'Кол-во суб.', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Кол-во субъектов', 1, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (109, 'TEXT', 0, 0, 2, 2, null, 0, 'Наименование', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Наименование', 2, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (110, 'TEXT', 0, 0, 3, 6, null, 0, 'Стадия', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Стадия', 3, 'TABLE_HEADER', 5, 5, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (111, 'TEXT', 0, 0, 4, 4, null, 0, 'Вид', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Вид', 4, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (112, 'TEXT', 0, 0, 5, 5, null, 0, 'Дата', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Дата.', 5, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (113, 'TEXT', 0, 0, 6, 6, null, 0, 'Сумма.', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Сумма.', 5, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (114, 'TEXT', 0, 0, 7, 10, null, 0, 'Результат', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Результат', 7, 'TABLE_HEADER', 5, 5, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (115, 'TEXT', 0, 0, 8, 8, null, 0, 'Вид', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Вид.', 8, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (116, 'TEXT', 0, 0, 9, 9, null, 0, 'Дата.', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Дата.', 9, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (117, 'TEXT', 0, 0, 10, 10, null, 0, 'Сумма', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Сумма', 10, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (118, 'TEXT', 0, 0, 11, 11, null, 0, 'Статус', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Статус.', 11, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (119, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Итого. Кол-во суб.', 1, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (120, 'TEXT', 0, 0, 0, 0, null, 0, 'ИТОГО', 0, 'CONSTANT', '', 'Взыскание. Итого. Итого.', 2, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (121, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 1. Кол-во субъектов', 1, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (122, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'COLLECTION_PHASE', 'name', 'Взыскание. Разрез 1. Наименование', 2, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (123, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 2. Кол-во субъектов', 1, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (124, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'COLLECTION_PHASE', 'name', 'Взыскание. Разрез 2. Наименование', 2, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (125, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 3. Кол-во субъектов', 1, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (126, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'COLLECTION_PHASE', 'name', 'Взыскание. Разрез 3. Наименование', 2, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (127, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 4. Кол-во субъектов.', 1, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (128, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'ENTITY_DOCUMENT', 'name', 'Взыскание. Разрез 4. Наименование.', 2, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (129, 'TEXT', 0, 0, 4, 4, null, 28, 'collection_phase_type', 0, 'COLLECTION_PHASE', 'collection_phase_type_id', 'Взыскание. Разрез 5. Вид стадии', 4, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (130, 'DATE', 0, 0, 5, 5, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_date', 'Взыскание. Разрез 5. Дата стадии', 5, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (131, 'DOUBLE', 0, 0, 6, 6, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 5. Сумма стадии.', 6, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (132, 'TEXT', 0, 0, 8, 8, null, 29, 'collection_phase_status', 0, 'COLLECTION_PHASE', 'collection_phase_status_id', 'Взыскание. Разрез 5. Вид результата.', 8, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (133, 'DATE', 0, 0, 9, 9, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_date', 'Взыскание. Разрез 5. Дата результата.', 9, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (134, 'DOUBLE', 0, 0, 10, 10, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 5. Сумма результата', 10, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (135, 'TEXT', 0, 0, 11, 11, null, 30, 'collection_procedure_status', 0, 'COLLECTION_PHASE', 'collection_procedure_status_id', 'Взыскание. Разрез 5. Статус', 11, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (136, 'TEXT', 0, 0, 3, 3, null, 0, 'Кол-во', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Кол-во стадий', 4, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (137, 'TEXT', 0, 0, 7, 7, null, 0, 'Кол-во', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Кол-во результатов.', 9, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (138, 'TEXT', 0, 0, 3, 3, null, 0, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 5. Кол-во стадии', 3, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (139, 'INTEGER', 0, 0, 7, 7, null, 0, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 5. Кол-во результатов', 7, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (140, 'TEXT', 0, 0, 3, 3, null, 0, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 1. Кол-во стадии', 3, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (141, 'TEXT', 0, 0, 7, 7, null, 0, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 1. Кол-во результатов', 7, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (142, 'TEXT', 0, 0, 3, 3, null, 0, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 2. Кол-во стадии', 3, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (143, 'TEXT', 0, 0, 7, 7, null, 0, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 2. Кол-во результатов', 7, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (144, 'TEXT', 0, 0, 3, 3, null, 0, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 3. Кол-во стадии', 3, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (145, 'TEXT', 0, 0, 7, 7, null, 0, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 3. Кол-во результатов', 7, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (146, 'TEXT', 0, 0, 3, 3, null, 0, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 4. Кол-во стадии', 3, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (147, 'TEXT', 0, 0, 7, 7, null, 0, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 4. Кол-во результатов', 7, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (148, 'DOUBLE', 0, 0, 6, 6, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 4. Сумма стадии.', 6, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (149, 'DOUBLE', 0, 0, 10, 10, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 4. Сумма результата', 10, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (150, 'DOUBLE', 0, 0, 6, 6, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 3. Сумма стадии.', 6, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (151, 'DOUBLE', 0, 0, 10, 10, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 3. Сумма результата', 10, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (152, 'DOUBLE', 0, 0, 6, 6, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 2. Сумма стадии.', 6, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (153, 'DOUBLE', 0, 0, 10, 10, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 2. Сумма результата', 10, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (154, 'DOUBLE', 0, 0, 6, 6, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 1. Сумма стадии.', 6, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (155, 'DOUBLE', 0, 0, 10, 10, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 1. Сумма результата', 10, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (156, 'DOUBLE', 0, 0, 6, 6, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Итого. Сумма стадии.', 6, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (157, 'DOUBLE', 0, 0, 10, 10, null, 0, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Итого. Сумма результата', 10, 'TABLE_SUM', 0, 0, null, null);

# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (1, 0, 0, null, 'Информация по оформлению документации', 0, 'CONSTANT', '', 'Оформление. Заголовок страницы. Строка 1. ', 6, 0, 1, 'PAGE_TITLE', 0, 1, 7, 2, 2, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (3, 0, 0, null, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Оформление. Заголовок страницы. Строка 2.', null, 0, 2, 'PAGE_TITLE', null, 1, 7, 3, 3, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (4, 0, 0, null, 'Кол-во пол.', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Кол-во получателей', null, 0, 1, 'TABLE_HEADER', null, 1, 1, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (5, 0, 0, null, 'Наименование', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Наименование', null, 0, 2, 'TABLE_HEADER', null, 2, 2, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (6, 0, 0, null, 'в том числе', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Кол-во документов в том числе', null, 0, 4, 'TABLE_HEADER', null, 4, 7, 5, 5, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (7, 0, 0, null, 'Кол-во док.', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Кол-во документов', null, 0, 3, 'TABLE_HEADER', null, 3, 3, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (8, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Итого. Кол-во получателей', null, 0, 1, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (9, 0, 0, null, 'ИТОГО', 0, 'CONSTANT', '', 'Оформление. Итого. Итого', null, 0, 2, 'TABLE_SUM', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (10, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Итого. Кол-во документов', null, 0, 3, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (11, 0, 0, null, 'укомлектовано', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Укомлектовано', null, 0, 5, 'TABLE_HEADER', null, 4, 4, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (12, 0, 0, null, 'не укомпл.', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Не укомплектовано', null, 0, 6, 'TABLE_HEADER', null, 5, 5, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (13, 0, 0, null, 'проверено', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Проверено', null, 0, 7, 'TABLE_HEADER', null, 6, 6, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (14, 0, 0, null, 'не проверено', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Не проверено', null, 0, 8, 'TABLE_HEADER', null, 7, 7, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (15, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Итого. Кол-во укомплектованных документов', null, 0, 4, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (16, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Итого. Кол-во проверенных документов', null, 0, 6, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (17, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Итого. Кол-во не укопмлетованных документов', null, 0, 5, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (18, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Итого. Кол-во непроверенных документов', null, 0, 7, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (19, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Разрез 1. - кол-во получателей', null, 0, 1, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (20, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 1. Наименование', null, 0, 2, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (21, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 2. Кол-во документов', null, 0, 3, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (22, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Разрез 2. кол-во получателей', null, 0, 1, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (23, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityCount', 'Оформление. Разрез 3. Кол-во получателей', null, 0, 1, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (24, 0, 0, null, '', 0, 'CONSTANT', 'EntityCount', 'Оформление. Разрез 4. Кол-во получателей', null, 0, 1, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (25, 0, 0, null, '', 0, 'CONSTANT', 'EntityCount', 'Оформление. Разрез 5. Кол-во получателей', null, 0, 1, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (26, 0, 0, null, '', 0, 'CONSTANT', 'EntityCount', 'Оформление. Разрез 6. Кол-во получателей', null, 0, 1, 'TABLE_GROUP6', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (27, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 2. Наименование', null, 0, 2, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (28, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 2. Наименование', null, 0, 2, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (29, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 3. Наименование', null, 0, 2, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (30, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 4. Наименование', null, 0, 2, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (31, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'Name', 'Оформление. Разрез 5. Наименование', null, 0, 2, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (32, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 1. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (33, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 1. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (34, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 1. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (35, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 1. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (36, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 1. Кол-во док.', null, 0, 3, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (37, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 2. Кол-во док.', null, 0, 3, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (38, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 2. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (39, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 2. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (40, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 2. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (41, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 2. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (42, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 3. Кол-во док.', null, 0, 3, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (43, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 3. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (44, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 3. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (45, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 3. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (46, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 3. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (47, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 4. Кол-во док.', null, 0, 3, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (48, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 4. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (49, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 4. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (50, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 4. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (51, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 4. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (52, 0, 0, null, '-', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCount', 'Оформление. Разрез 5. Кол-во док.', null, 0, 3, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (53, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentCompletedCount', 'Оформление. Разрез 5. Кол-во укомплектованных документов ', null, 0, 4, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (54, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentApprovedCount', 'Оформление. Разрез 5. Количество проверенных документов', null, 0, 6, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (55, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotCompletedCount', 'Оформление. Разрез 5. Кол-во не укопмлетованных документов ', null, 0, 5, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (56, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'EntityDocumentNotApprovedCount', 'Оформление. Разрез 5. Кол-во непроверенных документов ', null, 0, 7, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (57, 0, 0, null, 'Статус', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Статус', null, 0, 8, 'TABLE_HEADER', null, 8, 8, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (61, 0, 0, null, 'applied_entity_status', 0, 'ENTITY_DOCUMENT', 'V_applied_entity_appliedEntityStateId', 'Оформление. Разрез 3. Статус', null, 21, 8, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (62, 0, 0, null, 'document_package_status', 0, 'ENTITY_DOCUMENT', 'v_document_package_documentPackageStateId', 'Оформление. Разрез 4. Статус', null, 23, 8, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (63, 0, 0, null, 'entity_document_status', 0, 'ENTITY_DOCUMENT', 'V_entity_document_entityDocumentStateId', 'Оформление. Разрез 5. Статус', null, 22, 8, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (64, 0, 0, null, 'Укомплектовано', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Укомплектовано', null, 0, 9, 'TABLE_HEADER', null, 9, 9, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (65, 0, 0, null, 'Дата комплектации', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Дата комплектации', null, 0, 10, 'TABLE_HEADER', null, 10, 10, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (66, 0, 0, null, 'Примечание', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Примечание', null, 0, 11, 'TABLE_HEADER', null, 11, 11, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (67, 0, 0, null, 'Проверено', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Проверено', null, 0, 12, 'TABLE_HEADER', null, 12, 12, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (68, 0, 0, null, 'Дата проверки', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Дата проверки', null, 0, 13, 'TABLE_HEADER', null, 13, 13, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (69, 0, 0, null, 'Примечание', 0, 'CONSTANT', '', 'Оформление. Шапка таблицы. Примечание2', null, 0, 14, 'TABLE_HEADER', null, 14, 14, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (75, 0, 0, null, 'supervisor', 0, 'ENTITY_DOCUMENT', 'v_entity_document_completedBy', 'Оформление. Разрез 5. Укомплектовано', null, 5, 9, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (81, 0, 0, null, 'supervisor', 0, 'ENTITY_DOCUMENT', 'v_entity_document_approvedBy', 'Оформление. Разрез 5. Проверено', null, 5, 12, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (87, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_completedDate', 'Оформление. Разрез 5. Дата комплектации', null, 0, 10, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (93, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_approvedDate', 'Оформление. Разрез 5. Дата проверки', null, 0, 13, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (99, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_completedDescription', 'Оформление. Разрез 5. Примечание', null, 0, 11, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (105, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'v_entity_document_approvedDescription', 'Оформление. Разрез 5. Примечание', null, 0, 14, 'TABLE_GROUP5', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (106, 0, 0, null, 'Информация по взысканию', 0, 'CONSTANT', '', 'Взыскание. Заголовок страницы. Строка 1.', null, 0, 1, 'PAGE_TITLE', null, 1, 8, 1, 1, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (107, 0, 0, null, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Взыскание. Заголовок страницы. Строка 2.', null, 0, 1, 'PAGE_TITLE', null, 1, 8, 2, 2, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (108, 0, 0, null, 'Кол-во суб.', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Кол-во субъектов', null, 0, 1, 'TABLE_HEADER', null, 1, 1, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (109, 0, 0, null, 'Наименование', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Наименование', null, 0, 2, 'TABLE_HEADER', null, 2, 2, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (110, 0, 0, null, 'Стадия', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Стадия', null, 0, 3, 'TABLE_HEADER', null, 3, 6, 5, 5, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (111, 0, 0, null, 'Вид', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Вид', null, 0, 4, 'TABLE_HEADER', null, 4, 4, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (112, 0, 0, null, 'Дата', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Дата.', null, 0, 5, 'TABLE_HEADER', null, 5, 5, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (113, 0, 0, null, 'Сумма.', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Сумма.', null, 0, 5, 'TABLE_HEADER', null, 6, 6, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (114, 0, 0, null, 'Результат', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Результат', null, 0, 7, 'TABLE_HEADER', null, 7, 10, 5, 5, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (115, 0, 0, null, 'Вид', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Вид.', null, 0, 8, 'TABLE_HEADER', null, 8, 8, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (116, 0, 0, null, 'Дата.', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Дата.', null, 0, 9, 'TABLE_HEADER', null, 9, 9, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (117, 0, 0, null, 'Сумма', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Сумма', null, 0, 10, 'TABLE_HEADER', null, 10, 10, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (118, 0, 0, null, 'Статус', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Статус.', null, 0, 11, 'TABLE_HEADER', null, 11, 11, 5, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (119, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Итого. Кол-во суб.', null, 0, 1, 'TABLE_SUM', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (120, 0, 0, null, 'ИТОГО', 0, 'CONSTANT', '', 'Взыскание. Итого. Итого.', null, 0, 2, 'TABLE_SUM', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (121, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 1. Кол-во субъектов', null, 0, 1, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (122, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'name', 'Взыскание. Разрез 1. Наименование', null, 0, 2, 'TABLE_GROUP1', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (123, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 2. Кол-во субъектов', null, 0, 1, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (124, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'name', 'Взыскание. Разрез 2. Наименование', null, 0, 2, 'TABLE_GROUP2', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (125, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 3. Кол-во субъектов', null, 0, 1, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (126, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'name', 'Взыскание. Разрез 3. Наименование', null, 0, 2, 'TABLE_GROUP3', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (127, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'count', 'Взыскание. Разрез 4. Кол-во субъектов.', null, 0, 1, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (128, 0, 0, null, '', 0, 'ENTITY_DOCUMENT', 'name', 'Взыскание. Разрез 4. Наименование.', null, 0, 2, 'TABLE_GROUP4', null, 0, 0, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (129, 0, 0, null, 'collection_phase_type', 0, 'COLLECTION_PHASE', 'collection_phase_type_id', 'Взыскание. Разрез 5. Вид стадии', null, 28, 4, 'TABLE_GROUP5', null, 4, 4, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (130, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_date', 'Взыскание. Разрез 5. Дата стадии', null, 0, 5, 'TABLE_GROUP5', null, 5, 5, 0, 0, 'DATE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (131, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 5. Сумма стадии.', null, 0, 6, 'TABLE_GROUP5', null, 6, 6, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (132, 0, 0, null, 'collection_phase_status', 0, 'COLLECTION_PHASE', 'collection_phase_status_id', 'Взыскание. Разрез 5. Вид результата.', null, 29, 8, 'TABLE_GROUP5', null, 8, 8, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (133, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_date', 'Взыскание. Разрез 5. Дата результата.', null, 0, 9, 'TABLE_GROUP5', null, 9, 9, 0, 0, 'DATE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (134, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 5. Сумма результата', null, 0, 10, 'TABLE_GROUP5', null, 10, 10, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (135, 0, 0, null, 'collection_procedure_status', 0, 'COLLECTION_PHASE', 'collection_procedure_status_id', 'Взыскание. Разрез 5. Статус', null, 30, 11, 'TABLE_GROUP5', null, 11, 11, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (136, 0, 0, null, 'Кол-во', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Кол-во стадий', null, 0, 4, 'TABLE_HEADER', null, 3, 3, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (137, 0, 0, null, 'Кол-во', 0, 'CONSTANT', '', 'Взыскание. Шапка таблицы. Кол-во результатов.', null, 0, 9, 'TABLE_HEADER', null, 7, 7, 6, 6, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (138, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 5. Кол-во стадии', null, 0, 3, 'TABLE_SUM', null, 3, 3, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (139, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 5. Кол-во результатов', null, 0, 7, 'TABLE_SUM', null, 7, 7, 0, 0, 'INTEGER');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (140, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 1. Кол-во стадии', null, 0, 3, 'TABLE_GROUP1', null, 3, 3, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (141, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 1. Кол-во результатов', null, 0, 7, 'TABLE_GROUP1', null, 7, 7, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (142, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 2. Кол-во стадии', null, 0, 3, 'TABLE_GROUP2', null, 3, 3, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (143, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 2. Кол-во результатов', null, 0, 7, 'TABLE_GROUP2', null, 7, 7, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (144, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 3. Кол-во стадии', null, 0, 3, 'TABLE_GROUP3', null, 3, 3, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (145, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 3. Кол-во результатов', null, 0, 7, 'TABLE_GROUP3', null, 7, 7, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (146, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'phaseCount', 'Взыскание. Разрез 4. Кол-во стадии', null, 0, 3, 'TABLE_GROUP4', null, 3, 3, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (147, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'resultCount', 'Взыскание. Разрез 4. Кол-во результатов', null, 0, 7, 'TABLE_GROUP4', null, 7, 7, 0, 0, 'TEXT');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (148, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 4. Сумма стадии.', null, 0, 6, 'TABLE_GROUP4', null, 6, 6, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (149, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 4. Сумма результата', null, 0, 10, 'TABLE_GROUP4', null, 10, 10, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (150, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 3. Сумма стадии.', null, 0, 6, 'TABLE_GROUP3', null, 6, 6, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (151, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 3. Сумма результата', null, 0, 10, 'TABLE_GROUP3', null, 10, 10, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (152, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 2. Сумма стадии.', null, 0, 6, 'TABLE_GROUP2', null, 6, 6, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (153, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 2. Сумма результата', null, 0, 10, 'TABLE_GROUP2', null, 10, 10, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (154, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Разрез 1. Сумма стадии.', null, 0, 6, 'TABLE_GROUP1', null, 6, 6, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (155, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Разрез 1. Сумма результата', null, 0, 10, 'TABLE_GROUP1', null, 10, 10, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (156, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_start_total_amount', 'Взыскание. Итого. Сумма стадии.', null, 0, 6, 'TABLE_SUM', null, 6, 6, 0, 0, 'DOUBLE');
# INSERT INTO mfloan.content_parameter (id, classificator_id, classificator_value_id, constantDate, constant_text, constant_value, contentType, field_name, name, col_shift, constant_int, position, rowType, row_shift, col_from, col_to, row_from, row_to, cellType) VALUES (157, 0, 0, null, '', 0, 'COLLECTION_PHASE', 'collection_phase_close_total_amount', 'Взыскание. Итого. Сумма результата', null, 0, 10, 'TABLE_SUM', null, 10, 10, 0, 0, 'DOUBLE');




INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (1, 'Альбомный вид', 'PAGE_ORIENTATION', 0, '', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (2, 'Книжный вид', 'PAGE_ORIENTATION', 0, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (3, 'Высота нижнего поля', 'SHEET_BOTTOM_MARGIN', 0, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (4, 'Высота верхнего поля', 'SHEET_TOP_MARGIN', 0, '', 0.1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (5, 'Ширина правого поля', 'SHEET_RIGHT_MARGIN', 0, '', 0.1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (6, 'Ширина левого поля', 'SHEET_LEFT_MARGIN', 0, '', 0.1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (7, 'Показывать разрыв страниц', 'SHEET_AUTOBREAKS', 0, '', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (9, 'Разместить на кол-во стр. в ширину', 'SHEET_FIT_WIDTH', 0, '', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (10, 'Разместить на кол-во стр. в высоту', 'SHEET_FIT_HEIGHT', 0, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (11, 'Поле верхнего колонтитула', 'SHEET_HEADER_MARGIN', 0, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (12, 'Поле нижнего колонтитула', 'SHEET_FOOTER_MARGIN', 0, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (13, 'Нижний колонтитул (кол-во страниц)', 'SHEET_FOOTER_TEXT', 0, 'Страница page() из numPages()', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (14, 'Верхний колонтитул (оперативные данные)', 'SHEET_HEADER_TEXT', 0, 'Оперативные данные', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (15, 'Ширина всех столбцов', 'DEFAULT_COLUMN_WIDTH', 0, '', 12);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (16, 'ширина 1. столбца', 'COLUMN_WIDTH', 1, '', 6);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (17, 'ширина 2. столбца', 'COLUMN_WIDTH', 2, '', 40);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (18, 'Заголовок. шрифт. жирный.', 'FONT_BOLD', 10, '', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (19, 'Заголовок. шрифт.', 'CELL_FONT', 10, 'string', 10);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (20, 'Заголовок. шрифт. размер. ', 'FONT_HEIGHT', 10, '', 12);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (21, 'Заголовок. шрифт. цвет.', 'FONT_COLOR', 10, '', 12);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (22, 'Шапка таблицы. шрифт. жирный', 'FONT_BOLD', 11, '', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (23, 'Шапка таблицы. шрифт. нормальный', 'FONT_BOLD', 11, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (24, 'Заголовок (текст). Выравнивание.', 'CELL_ALIGNMENT', 10, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (25, 'Заголовок (тескт). рамки.', 'CELL_BORDER', 10, 'string', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (26, 'Заголовок (тескт). Верктикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 10, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (27, 'Шапка. шрифт. размер', 'FONT_HEIGHT', 11, '', 8);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (28, 'Шапка. шрифт. цвет.', 'FONT_COLOR', 11, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (29, 'Шапка. шрифт.', 'CELL_FONT', 11, 'string', 11);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (30, 'Итоговые данные. шрифт. жирный.', 'FONT_BOLD', 12, '', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (31, 'Итоговые данные. шрифт. размер.', 'FONT_HEIGHT', 12, '', 9);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (32, 'Итоговые данные. шрифт. цвет.', 'FONT_COLOR', 12, '', 10);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (33, 'Итоговые данные. шрифт.', 'CELL_FONT', 12, 'string', 12);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (34, 'Итоговые данные (текст). выравнивание.', 'CELL_ALIGNMENT', 12, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (35, 'Итоговые данные (текст). рамки.', 'CELL_BORDER', 12, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (36, 'Итоговые данные (текст). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 12, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (40, 'Итоговые данные (целое число). шрифт.', 'CELL_FONT', 12, 'int', 12);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (41, 'Итоговые данные (целое число). выравнивание.', 'CELL_ALIGNMENT', 12, 'int', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (42, 'Итоговые данные (целое число). рамки.', 'CELL_BORDER', 12, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (43, 'Итоговые данные (целое число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 12, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (44, 'Итоговые данные (целое число). Формат.', 'CELL_DATA_FORMAT', 12, '#,#0', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (45, 'Итоговые данные (целое число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 12, 'int', 49);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (46, 'Итоговые данные (целое число). Вид заливки.', 'CELL_PATTERN', 12, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (50, 'Итоговые данные (дробное число). шрифт.', 'CELL_FONT', 12, 'double', 12);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (51, 'Итоговые данные (дробное число). выравнивание.', 'CELL_ALIGNMENT', 12, 'double', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (52, 'Итоговые данные (дробное число). рамки.', 'CELL_BORDER', 12, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (53, 'Итоговые данные (дробное число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 12, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (54, 'Итоговые данные (дробное число). Формат.', 'CELL_DATA_FORMAT', 12, '#,#0.00', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (55, 'Итоговые данные (дробное число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 12, 'double', 49);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (56, 'Итоговые данные (дробное число). Вид заливки.', 'CELL_PATTERN', 12, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (57, 'Разрез №1. шрифт. жирный.', 'FONT_BOLD', 1, '', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (58, 'Разрез №1. шрифт. размер.', 'FONT_HEIGHT', 1, '', 8);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (59, 'Разрез №1. шрифт. цвет.', 'FONT_COLOR', 1, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (60, 'Разрез №1 (текcт). шрифт.', 'CELL_FONT', 1, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (61, 'Разрез №1 (текcт). выравнивание.', 'CELL_ALIGNMENT', 1, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (62, 'Разрез №1 (текcт). рамки.', 'CELL_BORDER', 1, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (63, 'Разрез №1 (текcт). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 1, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (64, 'Разрез №1 (текcт). цвет заливки.', 'CELL_FOREGROUND_COLOR', 1, 'string', 44);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (65, 'Разрез №1 (текcт). Вид заливки.', 'CELL_PATTERN', 1, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (66, 'Разрез №1 (дата). шрифт.', 'CELL_FONT', 1, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (67, 'Разрез №1 (дата). выравнивание.', 'CELL_ALIGNMENT', 1, 'date', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (68, 'Разрез №1 (дата). рамки.', 'CELL_BORDER', 1, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (69, 'Разрез №1 (дата). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 1, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (70, 'Разрез №1 (дата). Формат.', 'CELL_DATA_FORMAT', 1, 'dd.mm.yyyy', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (71, 'Разрез №1 (дата). цвет заливки.', 'CELL_FOREGROUND_COLOR', 1, 'date', 44);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (72, 'Разрез №1 (дата). Вид заливки.', 'CELL_PATTERN', 1, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (73, 'Разрез №1 (целое число). шрифт.', 'CELL_FONT', 1, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (74, 'Разрез №1 (целое число). выравнивание.', 'CELL_ALIGNMENT', 1, 'int', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (75, 'Разрез №1 (целое число). рамки.', 'CELL_BORDER', 1, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (76, 'Разрез №1 (целое число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 1, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (77, 'Разрез №1 (целое число). Формат.', 'CELL_DATA_FORMAT', 1, '#,#0', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (78, 'Разрез №1 (целое число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 1, 'int', 44);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (79, 'Разрез №1 (целое число). Вид заливки.', 'CELL_PATTERN', 1, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (80, 'Разрез №1 (дробное число). шрифт.', 'CELL_FONT', 1, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (81, 'Разрез №1 (дробное число). выравнивание. ', 'CELL_ALIGNMENT', 1, 'double', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (82, 'Разрез №1 (дробное число). рамки.', 'CELL_BORDER', 1, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (83, 'Разрез №1 (дробное число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 1, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (84, 'Разрез №1 (дробное число). Формат.', 'CELL_DATA_FORMAT', 1, '#,#0.00', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (85, 'Разрез №1 (дробное число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 1, 'double', 44);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (86, 'Разрез №1 (дробное число). Вид заливки.', 'CELL_PATTERN', 1, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (87, 'Шапка (текcт). выравнивание.', 'CELL_ALIGNMENT', 11, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (88, 'Шапка (текcт). рамки.', 'CELL_BORDER', 11, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (89, 'Шапка (текcт). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 11, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (90, 'Шапка (текcт). цвет заливки.', 'CELL_FOREGROUND_COLOR', 11, 'string', 22);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (91, 'Шапка (текcт). Вид заливки.', 'CELL_PATTERN', 11, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (92, 'Шапка (текcт). Перенос текста.', 'CELL_WRAP_TEXT', 11, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (93, 'Итоговые данные (текст). цвет заливки.', 'CELL_FOREGROUND_COLOR', 12, 'string', 49);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (94, 'Итоговые данные (текст). Вид заливки.', 'CELL_PATTERN', 12, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (95, 'Итоговые данные (текст). Перенос текста.', 'CELL_WRAP_TEXT', 12, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (96, 'Итоговые данные (целое число). Перенос текста.', 'CELL_WRAP_TEXT', 12, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (97, 'Итоговые данные (дробное число). Перенос текста.', 'CELL_WRAP_TEXT', 12, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (98, 'Разрез №1 (текcт). Перенос текста.', 'CELL_WRAP_TEXT', 1, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (99, 'Разрез №1 (дата). Перенос текста.', 'CELL_WRAP_TEXT', 1, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (100, 'Разрез №1 (целое число). Перенос текста.', 'CELL_WRAP_TEXT', 1, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (101, 'Разрез №1 (дробное число). Перенос текста.', 'CELL_WRAP_TEXT', 1, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (102, 'Разрез №2. шрифт. жирный.', 'FONT_BOLD', 2, '', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (103, 'Разрез №2. шрифт. размер.', 'FONT_HEIGHT', 2, '', 8);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (104, 'Разрез №2. шрифт. цвет.', 'FONT_COLOR', 2, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (105, 'Разрез №2 (текcт). шрифт.', 'CELL_FONT', 2, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (106, 'Разрез №2 (текcт). выравнивание.', 'CELL_ALIGNMENT', 2, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (107, 'Разрез №2 (текcт). рамки.', 'CELL_BORDER', 2, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (108, 'Разрез №2 (текcт). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 2, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (109, 'Разрез №2 (текcт). цвет заливки.', 'CELL_FOREGROUND_COLOR', 2, 'string', 13);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (110, 'Разрез №2 (текcт). Вид заливки.', 'CELL_PATTERN', 2, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (111, 'Разрез №2 (текcт). Перенос текста.', 'CELL_WRAP_TEXT', 2, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (112, 'Разрез №2 (дата). шрифт.', 'CELL_FONT', 2, 'date', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (113, 'Разрез №2 (дата). выравнивание.', 'CELL_ALIGNMENT', 2, 'date', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (114, 'Разрез №2 (дата). рамки.', 'CELL_BORDER', 2, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (115, 'Разрез №2 (дата). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 2, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (116, 'Разрез №2 (дата). Формат.', 'CELL_DATA_FORMAT', 2, 'dd.mm.yyyy', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (117, 'Разрез №2 (дата). цвет заливки.', 'CELL_FOREGROUND_COLOR', 2, 'date', 13);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (118, 'Разрез №2 (дата). Вид заливки.', 'CELL_PATTERN', 2, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (119, 'Разрез №2 (дата). Перенос текста.', 'CELL_WRAP_TEXT', 2, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (120, 'Разрез №2 (целое число). шрифт.', 'CELL_FONT', 2, 'int', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (121, 'Разрез №2 (целое число). выравнивание.', 'CELL_ALIGNMENT', 2, 'int', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (122, 'Разрез №2 (целое число). рамки.', 'CELL_BORDER', 2, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (123, 'Разрез №2 (целое число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 2, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (124, 'Разрез №2 (целое число). Формат.', 'CELL_DATA_FORMAT', 2, '#,#0', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (125, 'Разрез №2 (целое число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 2, 'int', 13);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (126, 'Разрез №2 (целое число). Вид заливки.', 'CELL_PATTERN', 2, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (127, 'Разрез №2 (целое число). Перенос текста.', 'CELL_WRAP_TEXT', 2, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (128, 'Разрез №2 (дробное число). шрифт.', 'CELL_FONT', 2, 'double', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (129, 'Разрез №2 (дробное число). выравнивание.', 'CELL_ALIGNMENT', 2, 'double', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (130, 'Разрез №2 (дробное число). рамки.', 'CELL_BORDER', 2, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (131, 'Разрез №2 (дробное число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 2, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (132, 'Разрез №2 (дробное число). Формат.', 'CELL_DATA_FORMAT', 2, '#,#0.00', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (133, 'Разрез №2 (дробное число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 2, 'double', 13);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (134, 'Разрез №2 (дробное число). Вид заливки.', 'CELL_PATTERN', 2, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (135, 'Разрез №2 (дробное число). Перенос текста.', 'CELL_WRAP_TEXT', 2, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (136, 'Разрез №3. шрифт. жирный.', 'FONT_BOLD', 3, '', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (137, 'Разрез №3. шрифт. размер.', 'FONT_HEIGHT', 3, '', 8);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (138, 'Разрез №3. шрифт. цвет.', 'FONT_COLOR', 3, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (139, 'Разрез №3 (текcт). шрифт.', 'CELL_FONT', 3, 'string', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (140, 'Разрез №3 (текcт). выравнивание. ', 'CELL_ALIGNMENT', 3, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (141, 'Разрез №3 (текcт). рамки.', 'CELL_BORDER', 3, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (142, 'Разрез №3 (текcт). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 3, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (143, 'Разрез №3 (текcт). цвет заливки.', 'CELL_FOREGROUND_COLOR', 3, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (144, 'Разрез №3 (текcт). Вид заливки.', 'CELL_PATTERN', 3, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (145, 'Разрез №3 (текcт). Перенос текста.', 'CELL_WRAP_TEXT', 3, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (146, 'Разрез №3 (дата). шрифт.', 'CELL_FONT', 3, 'date', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (147, 'Разрез №3 (дата). выравнивание. ', 'CELL_ALIGNMENT', 3, 'date', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (148, 'Разрез №3 (дата). рамки.', 'CELL_BORDER', 3, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (149, 'Разрез №3 (дата). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 3, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (150, 'Разрез №3 (дата). Формат.', 'CELL_DATA_FORMAT', 3, 'dd.mm.yyyy', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (151, 'Разрез №3 (дата). цвет заливки.', 'CELL_FOREGROUND_COLOR', 3, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (152, 'Разрез №3 (дата). Вид заливки.', 'CELL_PATTERN', 3, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (153, 'Разрез №3 (дата). Перенос текста.', 'CELL_WRAP_TEXT', 3, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (154, 'Разрез №3 (целое число). шрифт.', 'CELL_FONT', 3, 'int', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (155, 'Разрез №3 (целое число). выравнивание. ', 'CELL_ALIGNMENT', 3, 'int', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (156, 'Разрез №3 (целое число). рамки.', 'CELL_BORDER', 3, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (157, 'Разрез №3 (целое число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 3, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (158, 'Разрез №3 (целое число). Формат.', 'CELL_DATA_FORMAT', 3, '#,#0', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (159, 'Разрез №3 (целое число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 3, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (160, 'Разрез №3 (целое число). Вид заливки.', 'CELL_PATTERN', 3, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (161, 'Разрез №3 (целое число). Перенос текста.', 'CELL_WRAP_TEXT', 3, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (162, 'Разрез №3 (дробное число). шрифт.', 'CELL_FONT', 3, 'double', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (163, 'Разрез №3 (дробное число). выравнивание. ', 'CELL_ALIGNMENT', 3, 'double', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (164, 'Разрез №3 (дробное число). рамки.', 'CELL_BORDER', 3, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (165, 'Разрез №3 (дробное число). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 3, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (166, 'Разрез №3 (дробное число). Формат.', 'CELL_DATA_FORMAT', 3, '#,#0.00', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (167, 'Разрез №3 (дробное число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 3, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (168, 'Разрез №3 (дробное число). Вид заливки.', 'CELL_PATTERN', 3, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (169, 'Разрез №3 (дробное число). Перенос текста.', 'CELL_WRAP_TEXT', 3, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (170, 'Разрез №4. шрифт. жирный.', 'FONT_BOLD', 4, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (171, 'Разрез №4. шрифт. размер.', 'FONT_HEIGHT', 4, '', 8);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (172, 'Разрез №4. шрифт. цвет.', 'FONT_COLOR', 4, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (173, 'Разрез №4 (текcт). шрифт.', 'CELL_FONT', 4, 'string', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (174, 'Разрез №4 (текcт). выравнивание. ', 'CELL_ALIGNMENT', 4, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (175, 'Разрез №4 (текcт). рамки.', 'CELL_BORDER', 4, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (176, 'Разрез №4 (текcт). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 4, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (177, 'Разрез №4 (текcт). цвет заливки.', 'CELL_FOREGROUND_COLOR', 4, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (178, 'Разрез №4 (текcт). Вид заливки.', 'CELL_PATTERN', 4, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (179, 'Разрез №4 (текcт). Перенос текста.', 'CELL_WRAP_TEXT', 4, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (180, 'Разрез №4 (дата). шрифт.', 'CELL_FONT', 4, 'date', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (181, 'Разрез №4 (дата). выравнивание. ', 'CELL_ALIGNMENT', 4, 'date', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (182, 'Разрез №4 (дата). рамки.', 'CELL_BORDER', 4, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (183, 'Разрез №4 (дата). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 4, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (184, 'Разрез №4 (дата). Формат.', 'CELL_DATA_FORMAT', 4, 'dd.mm.yyyy', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (185, 'Разрез №4 (дата). цвет заливки.', 'CELL_FOREGROUND_COLOR', 4, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (186, 'Разрез №4 (дата). Вид заливки.', 'CELL_PATTERN', 4, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (187, 'Разрез №4 (дата). Перенос текста.', 'CELL_WRAP_TEXT', 4, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (188, 'Разрез №4 (целое число). шрифт.', 'CELL_FONT', 4, 'int', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (189, 'Разрез №4 (целое число). выравнивание.', 'CELL_ALIGNMENT', 4, 'int', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (190, 'Разрез №4 (целое число). рамки.', 'CELL_BORDER', 4, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (191, 'Разрез №4 (целое число). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 4, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (192, 'Разрез №4 (целое число). Формат.', 'CELL_DATA_FORMAT', 4, '#,#0', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (193, 'Разрез №4 (целое число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 4, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (194, 'Разрез №4 (целое число). Вид заливки.', 'CELL_PATTERN', 4, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (195, 'Разрез №4 (целое число). Перенос текста.', 'CELL_WRAP_TEXT', 4, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (196, 'Разрез №4 (дробное число). шрифт.', 'CELL_FONT', 4, 'double', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (197, 'Разрез №4 (дробное число). выравнивание.', 'CELL_ALIGNMENT', 4, 'double', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (198, 'Разрез №4 (дробное число). рамки.', 'CELL_BORDER', 4, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (199, 'Разрез №4 (дробное число). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 4, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (200, 'Разрез №4 (дробное число). Формат.', 'CELL_DATA_FORMAT', 4, '#,#0.00', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (201, 'Разрез №4 (дробное число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 4, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (202, 'Разрез №4 (дробное число). Вид заливки.', 'CELL_PATTERN', 4, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (203, 'Разрез №4 (дробное число). Перенос текста.', 'CELL_WRAP_TEXT', 4, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (204, 'Разрез №5. шрифт. жирный.', 'FONT_BOLD', 5, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (205, 'Разрез №5. шрифт. размер.', 'FONT_HEIGHT', 5, '', 8);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (206, 'Разрез №5. шрифт. цвет.', 'FONT_COLOR', 5, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (207, 'Разрез №5 (текcт). шрифт.', 'CELL_FONT', 5, 'string', 5);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (208, 'Разрез №5 (текcт). выравнивание. ', 'CELL_ALIGNMENT', 5, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (209, 'Разрез №5 (текcт). рамки.', 'CELL_BORDER', 5, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (210, 'Разрез №5 (текcт). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 5, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (211, 'Разрез №5 (текcт). цвет заливки.', 'CELL_FOREGROUND_COLOR', 5, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (212, 'Разрез №5 (текcт). Вид заливки.', 'CELL_PATTERN', 5, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (213, 'Разрез №5 (текcт). Перенос текста.', 'CELL_WRAP_TEXT', 5, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (214, 'Разрез №5 (дата). шрифт.', 'CELL_FONT', 5, 'date', 5);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (215, 'Разрез №5 (дата). выравнивание. ', 'CELL_ALIGNMENT', 5, 'date', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (216, 'Разрез №5 (дата). рамки.', 'CELL_BORDER', 5, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (217, 'Разрез №5 (дата). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 5, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (218, 'Разрез №5 (дата). Формат.', 'CELL_DATA_FORMAT', 5, 'dd.mm.yyyy', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (219, 'Разрез №5 (дата). цвет заливки.', 'CELL_FOREGROUND_COLOR', 5, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (220, 'Разрез №5 (дата). Вид заливки.', 'CELL_PATTERN', 5, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (221, 'Разрез №5 (дата). Перенос текста.', 'CELL_WRAP_TEXT', 5, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (222, 'Разрез №5 (целое число). шрифт.', 'CELL_FONT', 5, 'int', 5);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (223, 'Разрез №5 (целое число). выравнивание. ', 'CELL_ALIGNMENT', 5, 'int', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (224, 'Разрез №5 (целое число). рамки.', 'CELL_BORDER', 5, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (225, 'Разрез №5 (целое число). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 5, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (226, 'Разрез №5 (целое число). Формат.', 'CELL_DATA_FORMAT', 5, '#,#0', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (227, 'Разрез №5 (целое число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 5, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (228, 'Разрез №5 (целое число). Вид заливки.', 'CELL_PATTERN', 5, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (229, 'Разрез №5 (целое число). Перенос текста.', 'CELL_WRAP_TEXT', 5, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (230, 'Разрез №5 (дробное число). шрифт.', 'CELL_FONT', 5, 'double', 5);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (231, 'Разрез №5 (дробное число). выравнивание. ', 'CELL_ALIGNMENT', 5, 'double', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (232, 'Разрез №5 (дробное число). рамки.', 'CELL_BORDER', 5, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (233, 'Разрез №5 (дробное число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 5, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (234, 'Разрез №5 (дробное число). Формат.', 'CELL_DATA_FORMAT', 5, '#,#0.00', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (235, 'Разрез №5 (дробное число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 5, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (236, 'Разрез №5 (дробное число). Вид заливки.', 'CELL_PATTERN', 5, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (237, 'Разрез №5 (дробное число). Перенос текста.', 'CELL_WRAP_TEXT', 5, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (238, 'Разрез №6. шрифт. жирный.', 'FONT_BOLD', 6, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (239, 'Разрез №6. шрифт. размер.', 'FONT_HEIGHT', 6, '', 8);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (240, 'Разрез №6. шрифт. цвет.', 'FONT_COLOR', 6, '', 0);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (241, 'Разрез №6 (текcт). шрифт.', 'CELL_FONT', 6, 'string', 6);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (242, 'Разрез №6 (текcт). выравнивание.', 'CELL_ALIGNMENT', 6, 'string', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (243, 'Разрез №6 (текcт). рамки.', 'CELL_BORDER', 6, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (244, 'Разрез №6 (текcт). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 6, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (245, 'Разрез №6 (текcт). цвет заливки.', 'CELL_FOREGROUND_COLOR', 6, 'string', 44);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (246, 'Разрез №6 (текcт). Вид заливки.', 'CELL_PATTERN', 6, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (247, 'Разрез №6 (текcт). Перенос текста.', 'CELL_WRAP_TEXT', 6, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (248, 'Разрез №6 (дата). шрифт.', 'CELL_FONT', 6, 'date', 6);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (249, 'Разрез №6 (дата). выравнивание. ', 'CELL_ALIGNMENT', 6, 'date', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (250, 'Разрез №6 (дата). рамки.', 'CELL_BORDER', 6, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (251, 'Разрез №6 (дата). вертикальное выравнивание. ', 'CELL_VERTICAL_ALIGNMENT', 6, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (252, 'Разрез №6 (дата). Формат.', 'CELL_DATA_FORMAT', 6, 'dd.mm.yyyy', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (253, 'Разрез №6 (дата). цвет заливки.', 'CELL_FOREGROUND_COLOR', 6, 'date', 44);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (254, 'Разрез №6 (дата). Вид заливки.', 'CELL_PATTERN', 6, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (255, 'Разрез №6 (дата). Перенос текста.', 'CELL_WRAP_TEXT', 6, 'date', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (256, 'Разрез №6 (целое число). шрифт.', 'CELL_FONT', 6, 'int', 6);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (257, 'Разрез №6 (целое число). выравнивание. ', 'CELL_ALIGNMENT', 6, 'int', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (258, 'Разрез №6 (целое число). рамки.', 'CELL_BORDER', 6, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (259, 'Разрез №6 (целое число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 6, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (260, 'Разрез №6 (целое число). Формат.', 'CELL_DATA_FORMAT', 6, '#,#0', 3);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (261, 'Разрез №6 (целое число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 6, 'int', 44);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (262, 'Разрез №6 (целое число). Вид заливки.', 'CELL_PATTERN', 6, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (263, 'Разрез №6 (целое число). Перенос текста.', 'CELL_WRAP_TEXT', 6, 'int', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (264, 'Разрез №6 (дробное число). шрифт.', 'CELL_FONT', 6, 'double', 6);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (265, 'Разрез №6 (дробное число). выравнивание. ', 'CELL_ALIGNMENT', 6, 'double', 2);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (266, 'Разрез №6 (дробное число). рамки.', 'CELL_BORDER', 6, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (267, 'Разрез №6 (дробное число). вертикальное выравнивание.', 'CELL_VERTICAL_ALIGNMENT', 6, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (268, 'Разрез №6 (дробное число). Формат.', 'CELL_DATA_FORMAT', 6, '#,#0.00', 4);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (269, 'Разрез №6 (дробное число). цвет заливки.', 'CELL_FOREGROUND_COLOR', 6, 'double', 44);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (270, 'Разрез №6 (дробное число). Вид заливки.', 'CELL_PATTERN', 6, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (271, 'Разрез №6 (дробное число). Перенос текста.', 'CELL_WRAP_TEXT', 6, 'double', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (272, 'Заголовок (текстк). перенос текста', 'CELL_WRAP_TEXT', 10, 'string', 1);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (273, 'Высота строки ( Заголовок таблицы)', 'ROW_HEIGHT', 6, '', 30);







