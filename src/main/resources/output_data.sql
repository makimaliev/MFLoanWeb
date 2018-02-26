

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


INSERT INTO `mfloan`.`generation_parameter` (`name`, `position`, `position_in_list`, `ref_id`, `generation_parameter_type__id`) VALUES ('1. разрезе (отрасль)', '2', '6', '0', '3');
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









DROP TABLE IF EXISTS mfloan.person_view;
DROP TABLE IF EXISTS mfloan.organization_view;
DROP TABLE IF EXISTS mfloan.owner_view;
DROP TABLE IF EXISTS mfloan.debtor_view;
DROP TABLE IF EXISTS mfloan.loan_view;
DROP TABLE IF EXISTS mfloan.payment_view;

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
    `o`.`id`                                     AS `v_owner_id`,
    `o`.`version`                                AS `v_owner_version`,
    `o`.`entityId`                               AS `v_owner_entityId`,
    `o`.`name`                                   AS `v_owner_name`,
    `o`.`ownerType`                              AS `v_owner_ownerType`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN `pv`.`v_person_address_id`
     ELSE `ov`.`v_organization_address_id` END)  AS `v_owner_address_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN `pv`.`v_person_region_id`
     ELSE `ov`.`v_organization_region_id` END)   AS `v_owner_region_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN `pv`.`v_person_district_id`
     ELSE `ov`.`v_organization_district_id` END) AS `v_owner_district_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN `pv`.`v_person_aokmotu_id`
     ELSE `ov`.`v_organization_aokmotu_id` END)  AS `v_owner_aokmotu_id`,
    (CASE WHEN (`o`.`ownerType` = 'PERSON')
      THEN `pv`.`v_person_village_id`
     ELSE `ov`.`v_organization_village_id` END)  AS `v_owner_village_id`
  FROM ((`mfloan`.`owner` `o`
    JOIN `mfloan`.`person_view` `pv` ON ((`o`.`entityId` = `pv`.`v_person_id`))) JOIN `mfloan`.`organization_view` `ov`
      ON ((`ov`.`v_organization_id` = `o`.`entityId`)));




CREATE VIEW debtor_view AS
  SELECT
    `d`.`id`                   AS `v_debtor_id`,
    `d`.`name`                 AS `v_debtor_name`,
    `d`.`debtorTypeId`         AS `v_debtor_type_id`,
    `d`.`orgFormId`            AS `v_debtor_org_form_id`,
    `d`.`ownerId`              AS `v_debtor_owner_id`,
    `d`.`workSectorId`         AS `v_debtor_work_sector_id`,
    `ov`.`v_owner_entityId`    AS `v_debtor_entity_id`,
    `ov`.`v_owner_ownerType`   AS `v_debtor_owner_type`,
    `ov`.`v_owner_address_id`  AS `v_debtor_address_id`,
    `ov`.`v_owner_region_id`   AS `v_debtor_region_id`,
    `ov`.`v_owner_district_id` AS `v_debtor_district_id`,
    `ov`.`v_owner_aokmotu_id`  AS `v_debtor_aokmotu_id`,
    `ov`.`v_owner_village_id`  AS `v_debtor_village_id`
  FROM (`mfloan`.`debtor` `d`
    JOIN `mfloan`.`owner_view` `ov` ON ((`ov`.`v_owner_id` = `d`.`ownerId`)));

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
    `lv`.`v_work_sector_name`         AS `v_work_sector_name`
  FROM ((`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`payment` `p` ON ((`p`.`loanId` = `lv`.`v_loan_id`))) JOIN `mfloan`.`paymentType` `pt`
      ON ((`pt`.`id` = `p`.`paymentTypeId`)));