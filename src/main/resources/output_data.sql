
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `person_view`;
DROP TABLE IF EXISTS `organization_view`;
DROP TABLE IF EXISTS `owner_view`;
DROP TABLE IF EXISTS `debtor_view`;
DROP TABLE IF EXISTS `loan_last_date_view`;
DROP TABLE IF EXISTS `loan_view`;
DROP TABLE IF EXISTS `loan_summary_view`;
DROP TABLE IF EXISTS `loan_accrue_view`;
DROP TABLE IF EXISTS `loan_debt_transfer_view`;
DROP TABLE IF EXISTS `loan_write_off_view`;
DROP TABLE IF EXISTS `payment_schedule_view`;
DROP TABLE IF EXISTS `payment_view`;
DROP TABLE IF EXISTS `supervisor_plan_view`;
DROP TABLE IF EXISTS `applied_entity_view`;
DROP TABLE IF EXISTS `document_package_view`;
DROP TABLE IF EXISTS `entity_document_view`;
DROP TABLE IF EXISTS `collateral_agreement_view`;
DROP TABLE IF EXISTS `collateral_arrest_free_view`;
DROP TABLE IF EXISTS `collateral_inspection_view`;
DROP TABLE IF EXISTS `collateral_item_view`;
DROP TABLE IF EXISTS `collection_procedure_view`;
DROP TABLE IF EXISTS `collection_phase_view`;
DROP TABLE IF EXISTS `collection_procedure_debtor_view`;
DROP TABLE IF EXISTS `staff_event_view`;
DROP TABLE IF EXISTS `staff_view`;
DROP TABLE IF EXISTS `executor_view`;
DROP TABLE IF EXISTS `responsible_view`;
DROP TABLE IF EXISTS `document_view`;
DROP TABLE IF EXISTS `reference_view`;

# VIEWS


# 1
DROP TABLE IF EXISTS `account`;
create view account as
  SELECT
    `data`.`id`           AS `id`,
    (SELECT uuid())       AS `uuid`,
    1                     AS `version`,
    `data`.`internalName` AS `internalName`,
    `data`.`name`         AS `name`
  FROM (SELECT
          `mfloan`.`organization`.`id`   AS `id`,
          `mfloan`.`organization`.`name` AS `name`,
          'organization'                 AS `internalName`
        FROM `mfloan`.`organization`
        UNION ALL SELECT
                    `mfloan`.`department`.`id`   AS `id`,
                    `mfloan`.`department`.`name` AS `name`,
                    'department'                 AS `internalName`
                  FROM `mfloan`.`department`
                  WHERE (`mfloan`.`department`.`organization_id` = 1)
        UNION ALL SELECT
                    `mfloan`.`staff`.`id`   AS `id`,
                    `mfloan`.`staff`.`name` AS `name`,
                    'staff'                 AS `internalName`
                  FROM `mfloan`.`staff`
                  WHERE (`mfloan`.`staff`.`organization_id` = 1)
        UNION ALL SELECT
                    `mfloan`.`person`.`id`   AS `id`,
                    `mfloan`.`person`.`name` AS `name`,
                    'person'                 AS `internalName`
                  FROM `mfloan`.`person`
                  WHERE (`mfloan`.`person`.`name` <> '')) `data`
  ORDER BY `data`.`id`;

#2
DROP TABLE IF EXISTS `person_view`;
create view person_view as
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

#3
DROP TABLE IF EXISTS `organization_view`;
create view organization_view as
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

#4
DROP TABLE IF EXISTS `owner_view`;
create view owner_view as
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

#5
DROP TABLE IF EXISTS `debtor_view`;
create view debtor_view as
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

#6
DROP TABLE IF EXISTS `loan_view`;
create view loan_view as
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
    `co`.`regNumber`               AS `v_credit_order_reg_number`,
    `co`.`regDate`                 AS `v_credit_order_reg_date`
  FROM ((`mfloan`.`loan` `l`
    JOIN `mfloan`.`debtor_view` `dv` ON ((`dv`.`v_debtor_id` = `l`.`debtorId`))) JOIN `mfloan`.`creditOrder` `co`
      ON ((`co`.`id` = `l`.`creditOrderId`)))
  WHERE (isnull(`l`.`parent_id`) AND (`l`.`loanStateId` = 2));

#7
DROP TABLE IF EXISTS `loan_last_date_view`;
create view loan_last_date_view as
  SELECT
    `lv`.`v_loan_id` AS `v_loan_id`,
    (SELECT `mfloan`.`paymentSchedule`.`expectedDate`
     FROM `mfloan`.`paymentSchedule`
     WHERE ((`mfloan`.`paymentSchedule`.`loanId` = `lv`.`v_loan_id`) AND
            (`mfloan`.`paymentSchedule`.`principalPayment` > 0))
     ORDER BY `mfloan`.`paymentSchedule`.`expectedDate` DESC
     LIMIT 1)        AS `last_date`
  FROM `mfloan`.`loan_view` `lv`;

#8
DROP TABLE IF EXISTS `loan_accrue_view`;
create view loan_accrue_view as
  SELECT
    `lv`.`v_loan_id`                  AS `v_loan_id`,
    `lv`.`v_loan_amount`              AS `v_loan_amount`,
    `lv`.`v_loan_reg_date`            AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`          AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`       AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`     AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`         AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`           AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`            AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`             AS `v_loan_type_id`,
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
    `lv`.`v_credit_order_reg_number`  AS `v_credit_order_reg_number`,
    `lv`.`v_credit_order_reg_date`    AS `v_credit_order_reg_date`,
    `acc`.`id`                        AS `v_acc_id`,
    `acc`.`version`                   AS `v_acc_version`,
    `acc`.`daysInPeriod`              AS `v_acc_daysInPeriod`,
    `acc`.`fromDate`                  AS `v_acc_fromDate`,
    `acc`.`interestAccrued`           AS `v_acc_interestAccrued`,
    `acc`.`lastInstPassed`            AS `v_acc_lastInstPassed`,
    `acc`.`penaltyAccrued`            AS `v_acc_penaltyAccrued`,
    `acc`.`penaltyOnInterestOverdue`  AS `v_acc_penaltyOnInterestOverdue`,
    `acc`.`penaltyOnPrincipalOverdue` AS `v_acc_penaltyOnPrincipalOverdue`,
    `acc`.`toDate`                    AS `v_acc_toDate`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`accrue` `acc`)
  WHERE (`acc`.`loanId` = `lv`.`v_loan_id`);

#9
DROP TABLE IF EXISTS `loan_debt_transfer_view`;
create view loan_debt_transfer_view as
  SELECT
    `lv`.`v_loan_id`                 AS `v_loan_id`,
    `lv`.`v_loan_amount`             AS `v_loan_amount`,
    `lv`.`v_loan_reg_date`           AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`         AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`      AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`    AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`        AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`          AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`           AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`            AS `v_loan_type_id`,
    `lv`.`v_debtor_id`               AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`          AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`      AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`   AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`             AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`        AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`       AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`        AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`      AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`       AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`       AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`    AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_reg_number` AS `v_credit_order_reg_number`,
    `lv`.`v_credit_order_reg_date`   AS `v_credit_order_reg_date`,
    `dt`.`id`                        AS `v_dt_id`,
    `dt`.`version`                   AS `v_dt_version`,
    `dt`.`date`                      AS `v_dt_date`,
    `dt`.`goodsTypeId`               AS `v_dt_goodsTypeId`,
    `dt`.`number`                    AS `v_dt_number`,
    `dt`.`pricePerUnit`              AS `v_dt_pricePerUnit`,
    `dt`.`quantity`                  AS `v_dt_quantity`,
    `dt`.`totalCost`                 AS `v_dt_totalCost`,
    `dt`.`transferCreditId`          AS `v_dt_transferCreditId`,
    `dt`.`transferPaymentId`         AS `v_dt_transferPaymentId`,
    `dt`.`transferPersonId`          AS `v_dt_transferPersonId`,
    `dt`.`unitTypeId`                AS `v_dt_unitTypeId`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`debtTransfer` `dt`)
  WHERE (`dt`.`loanId` = `lv`.`v_loan_id`);

#10
DROP TABLE IF EXISTS `loan_summary_view`;
create view loan_summary_view as
  SELECT
    `lv`.`v_loan_id`                 AS `v_loan_id`,
    `lv`.`v_loan_amount`             AS `v_loan_amount`,
    `lv`.`v_loan_reg_date`           AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`         AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`      AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`    AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`        AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`          AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`           AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`            AS `v_loan_type_id`,
    `lv`.`v_debtor_id`               AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`          AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`      AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`   AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`             AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`        AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`       AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`        AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`      AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`       AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`       AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`    AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_reg_number` AS `v_credit_order_reg_number`,
    `lv`.`v_credit_order_reg_date`   AS `v_credit_order_reg_date`,
    `ls`.`id`                        AS `v_ls_id`,
    `ls`.`loanAmount`                AS `v_ls_loan_amount`,
    `ls`.`onDate`                    AS `v_ls_on_date`,
    `ls`.`outstadingFee`             AS `v_ls_outstading_fee`,
    `ls`.`outstadingInterest`        AS `v_ls_outstading_interest`,
    `ls`.`outstadingPenalty`         AS `v_ls_outstading_penalty`,
    `ls`.`outstadingPrincipal`       AS `v_ls_outstading_principal`,
    `ls`.`overdueFee`                AS `v_ls_overdue_fee`,
    `ls`.`overdueInterest`           AS `v_ls_overdue_interest`,
    `ls`.`overduePenalty`            AS `v_ls_overdue_penalty`,
    `ls`.`overduePrincipal`          AS `v_ls_overdue_principal`,
    `ls`.`paidFee`                   AS `v_ls_paid_fee`,
    `ls`.`paidInterest`              AS `v_ls_paid_interest`,
    `ls`.`paidPenalty`               AS `v_ls_paid_penalty`,
    `ls`.`paidPrincipal`             AS `v_ls_paid_principal`,
    `ls`.`totalDisbursed`            AS `v_ls_total_disbursed`,
    `ls`.`totalOutstanding`          AS `v_ls_total_outstanding`,
    `ls`.`totalOverdue`              AS `v_ls_total_overdue`,
    `ls`.`totalPaid`                 AS `v_ls_total_paid`,
    `ls`.`totalPaidKGS`              AS `v_ls_total_paid_kgs`,
    `ls`.`loanId`                    AS `v_ls_loan_id`,
    `ldv`.`last_date`                AS `v_last_date`
  FROM ((`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`loanSummary` `ls`) JOIN `mfloan`.`loan_last_date_view` `ldv`)
  WHERE ((`ls`.`loanId` = `lv`.`v_loan_id`) AND (`ls`.`loanId` = `ldv`.`v_loan_id`));

#11
DROP TABLE IF EXISTS `loan_write_off_view`;
create view loan_write_off_view as
  SELECT
    `lv`.`v_loan_id`                 AS `v_loan_id`,
    `lv`.`v_loan_amount`             AS `v_loan_amount`,
    `lv`.`v_loan_reg_date`           AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`         AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`      AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`    AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`        AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`          AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`           AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`            AS `v_loan_type_id`,
    `lv`.`v_debtor_id`               AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`          AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`      AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`   AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`             AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`        AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`       AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`        AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`      AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`       AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`       AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`    AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_reg_number` AS `v_credit_order_reg_number`,
    `lv`.`v_credit_order_reg_date`   AS `v_credit_order_reg_date`,
    `wo`.`id`                        AS `v_wo_id`,
    `wo`.`version`                   AS `v_wo_version`,
    `wo`.`date`                      AS `v_wo_date`,
    `wo`.`description`               AS `v_wo_description`,
    `wo`.`fee`                       AS `v_wo_fee`,
    `wo`.`interest`                  AS `v_wo_interest`,
    `wo`.`penalty`                   AS `v_wo_penalty`,
    `wo`.`principal`                 AS `v_wo_principal`,
    `wo`.`totalAmount`               AS `v_wo_totalAmount`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`writeOff` `wo`)
  WHERE (`wo`.`loanId` = `lv`.`v_loan_id`);

#12
DROP TABLE IF EXISTS `payment_schedule_view`;
create view payment_schedule_view as
  SELECT
    `lv`.`v_loan_id`                 AS `v_loan_id`,
    `lv`.`v_loan_amount`             AS `v_loan_amount`,
    `lv`.`v_loan_reg_date`           AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`         AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`      AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`    AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`        AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`          AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`           AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`            AS `v_loan_type_id`,
    `lv`.`v_debtor_id`               AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`          AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`      AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`   AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`             AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`        AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`       AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`        AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`      AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`       AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`       AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`    AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_reg_number` AS `v_credit_order_reg_number`,
    `lv`.`v_credit_order_reg_date`   AS `v_credit_order_reg_date`,
    `ps`.`id`                        AS `v_ps_id`,
    `ps`.`version`                   AS `v_ps_version`,
    `ps`.`collectedInterestPayment`  AS `v_ps_collected_interest_payment`,
    `ps`.`collectedPenaltyPayment`   AS `v_ps_collected_penalty_payment`,
    `ps`.`disbursement`              AS `v_ps_disbursement`,
    `ps`.`expectedDate`              AS `v_ps_expected_date`,
    `ps`.`interestPayment`           AS `v_ps_interest_payment`,
    `ps`.`principalPayment`          AS `v_ps_principal_payment`,
    `ps`.`installmentStateId`        AS `v_ps_installment_state_id`,
    `ps`.`loanId`                    AS `v_ps_loan_id`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`paymentSchedule` `ps`)
  WHERE (`ps`.`loanId` = `lv`.`v_loan_id`);

#13
DROP TABLE IF EXISTS `payment_view`;
create view payment_view as
  SELECT
    `lv`.`v_loan_id`                 AS `v_loan_id`,
    `lv`.`v_loan_amount`             AS `v_loan_amount`,
    `lv`.`v_loan_reg_date`           AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`         AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`      AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`    AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`        AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`          AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`           AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`            AS `v_loan_type_id`,
    `lv`.`v_debtor_id`               AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`          AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`      AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`   AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`             AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`        AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`       AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`        AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`      AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`       AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`       AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`    AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_reg_number` AS `v_credit_order_reg_number`,
    `lv`.`v_credit_order_reg_date`   AS `v_credit_order_reg_date`,
    `p`.`id`                         AS `v_payment_id`,
    `p`.`fee`                        AS `v_payment_fee`,
    `p`.`interest`                   AS `v_payment_interest`,
    `p`.`number`                     AS `v_payment_number`,
    `p`.`paymentDate`                AS `v_payment_date`,
    `p`.`penalty`                    AS `v_payment_penalty`,
    `p`.`principal`                  AS `v_payment_principal`,
    `p`.`totalAmount`                AS `v_payment_total_amount`,
    `p`.`paymentTypeId`              AS `v_payment_type_id`,
    `p`.`exchange_rate`              AS `v_payment_exchange_rate`,
    `p`.`in_loan_currency`           AS `v_payment_in_loan_currency`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`payment` `p` ON ((`p`.`loanId` = `lv`.`v_loan_id`)));

#14
DROP TABLE IF EXISTS `supervisor_plan_view`;
create view supervisor_plan_view as
  SELECT
    `lv`.`v_loan_id`                 AS `v_loan_id`,
    `lv`.`v_loan_amount`             AS `v_loan_amount`,
    `lv`.`v_loan_reg_date`           AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`         AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`      AS `v_loan_supervisor_id`,
    `lv`.`v_loan_credit_order_id`    AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`        AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`          AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`           AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`            AS `v_loan_type_id`,
    `lv`.`v_debtor_id`               AS `v_debtor_id`,
    `lv`.`v_debtor_type_id`          AS `v_debtor_type_id`,
    `lv`.`v_debtor_org_form_id`      AS `v_debtor_org_form_id`,
    `lv`.`v_debtor_work_sector_id`   AS `v_debtor_work_sector_id`,
    `lv`.`v_debtor_name`             AS `v_debtor_name`,
    `lv`.`v_debtor_entity_id`        AS `v_debtor_entity_id`,
    `lv`.`v_debtor_owner_type`       AS `v_debtor_owner_type`,
    `lv`.`v_debtor_region_id`        AS `v_debtor_region_id`,
    `lv`.`v_debtor_district_id`      AS `v_debtor_district_id`,
    `lv`.`v_debtor_aokmotu_id`       AS `v_debtor_aokmotu_id`,
    `lv`.`v_debtor_village_id`       AS `v_debtor_village_id`,
    `lv`.`v_credit_order_type_id`    AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_reg_number` AS `v_credit_order_reg_number`,
    `lv`.`v_credit_order_reg_date`   AS `v_credit_order_reg_date`,
    `sp`.`id`                        AS `v_sp_id`,
    `sp`.`version`                   AS `v_sp_version`,
    `sp`.`amount`                    AS `v_sp_amount`,
    `sp`.`date`                      AS `v_sp_date`,
    `sp`.`description`               AS `v_sp_description`,
    `sp`.`fee`                       AS `v_sp_fee`,
    `sp`.`interest`                  AS `v_sp_interest`,
    `sp`.`penalty`                   AS `v_sp_penalty`,
    `sp`.`principal`                 AS `v_sp_principal`,
    `sp`.`reg_by_id`                 AS `v_sp_reg_by_id`,
    `sp`.`reg_date`                  AS `v_sp_reg_date`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`supervisorPlan` `sp`)
  WHERE (`sp`.`loanId` = `lv`.`v_loan_id`);

#15
DROP TABLE IF EXISTS `applied_entity_view`;
create view applied_entity_view as
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

#16
DROP TABLE IF EXISTS `document_package_view`;
create view document_package_view as
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

#17
DROP TABLE IF EXISTS `entity_document_view`;
create view entity_document_view as
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
    `document_package_view`.`v_co_id`                                   AS `v_credit_order_id`,
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

#18
DROP TABLE IF EXISTS `collateral_agreement_view`;
create view collateral_agreement_view as
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

#$19
DROP TABLE IF EXISTS `collateral_item_view`;
create view collateral_item_view as
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

#20
DROP TABLE IF EXISTS `collateral_arrest_free_view`;
create view collateral_arrest_free_view as
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
    `ciaf`.`id`                            AS `v_ciaf_id`,
    `ciaf`.`version`                       AS `v_ciaf_version`,
    `ciaf`.`arrestFreeBy`                  AS `v_ciaf_arrestFreeBy`,
    `ciaf`.`details`                       AS `v_ciaf_details`,
    `ciaf`.`onDate`                        AS `v_ciaf_onDate`
  FROM (`mfloan`.`collateral_item_view` `civ`
    JOIN `mfloan`.`collateralItemArrestFree` `ciaf`)
  WHERE (`ciaf`.`id` = `civ`.`v_ci_id`);

#21
DROP TABLE IF EXISTS `collateral_inspection_view`;
create view collateral_inspection_view as
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
    `cir`.`id`                             AS `v_cir_id`,
    `cir`.`details`                        AS `v_cir_details`,
    `cir`.`onDate`                         AS `v_cir_onDate`,
    `cir`.`inspectionResultTypeId`         AS `v_cir_inspectionResultTypeId`
  FROM (`mfloan`.`collateral_item_view` `civ`
    JOIN `mfloan`.`collateralItemInspectionResult` `cir`)
  WHERE (`cir`.`collateralItemId` = `civ`.`v_ci_id`);

#22
DROP TABLE IF EXISTS `collection_procedure_debtor_view`;
create view collection_procedure_debtor_view as
  SELECT DISTINCT
    `pr`.`id` AS `v_cp_id`,
    `d`.`id`  AS `v_debtor_id`
  FROM ((((`mfloan`.`collectionProcedure` `pr`
    JOIN `mfloan`.`collectionPhase` `ph`) JOIN `mfloan`.`phaseDetails` `pd`) JOIN `mfloan`.`loan` `l`) JOIN
    `mfloan`.`debtor` `d`)
  WHERE ((`ph`.`collectionProcedureId` = `pr`.`id`) AND (`ph`.`id` = `pd`.`id`) AND (`pd`.`loan_id` = `l`.`id`) AND
         (`l`.`debtorId` = `d`.`id`));

#23
DROP TABLE IF EXISTS `collection_procedure_view`;
create view collection_procedure_view as
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

#24
DROP TABLE IF EXISTS `collection_phase_view`;
create view collection_phase_view as
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

#25
DROP TABLE IF EXISTS `staff_view`;
create view staff_view as
  SELECT
    `s`.`id`                          AS `staff_id`,
    `s`.`name`                        AS `staff_name`,
    `s`.`department_id`               AS `department_id`,
    `s`.`position_id`                 AS `position_id`,
    `s`.`organization_id`             AS `organization_id`,
    `a`.`region_id`                   AS `region_id`,
    `a`.`district_id`                 AS `district_id`,
    `a`.`line`                        AS `line`,
    `c`.`name`                        AS `name`,
    `idoc`.`date`                     AS `date`,
    `idoc`.`name`                     AS `id_doc_name`,
    `idoc`.`number`                   AS `number`,
    `idoc`.`pin`                      AS `pin`,
    `idoc`.`identity_doc_given_by_id` AS `identity_doc_given_by_id`,
    `idoc`.`identity_doc_type_id`     AS `identity_doc_type_id`
  FROM ((((`mfloan`.`staff` `s`
    JOIN `mfloan`.`person` `p`) JOIN `mfloan`.`address` `a`) JOIN `mfloan`.`contact` `c`) JOIN
    `mfloan`.`identity_doc` `idoc`)
  WHERE ((`s`.`organization_id` = 1) AND (`s`.`person_id` = `p`.`id`) AND (`p`.`address_id` = `a`.`id`) AND
         (`p`.`contact_id` = `c`.`id`) AND (`p`.`identity_doc_id` = `idoc`.`id`));

#26
DROP TABLE IF EXISTS `staff_event_view`;
create view staff_event_view as
  SELECT
    `s`.`id`                                AS `v_s_id`,
    `s`.`name`                              AS `v_s_name`,
    `s`.`department_id`                     AS `v_s_department_id`,
    `s`.`position_id`                       AS `v_s_position_id`,
    `s`.`organization_id`                   AS `v_s_organization_id`,
    `a`.`region_id`                         AS `v_s_region_id`,
    `a`.`district_id`                       AS `v_s_district_id`,
    `a`.`line`                              AS `v_s_line`,
    `c`.`name`                              AS `v_s_contact_name`,
    `idoc`.`date`                           AS `v_s_id_doc_date`,
    `idoc`.`name`                           AS `v_s_id_doc_name`,
    `idoc`.`number`                         AS `v_s_id_doc_number`,
    `idoc`.`pin`                            AS `v_s_id_doc_pin`,
    `idoc`.`identity_doc_given_by_id`       AS `v_s_id_doc_given_by_id`,
    `idoc`.`identity_doc_type_id`           AS `v_s_id_doc_type_id`,
    `ev`.`id`                               AS `v_se_id`,
    `ev`.`date`                             AS `v_se_date`,
    `ev`.`name`                             AS `v_se_name`,
    `ev`.`employment_history_event_type_id` AS `v_se_type_id`
  FROM (((((`mfloan`.`staff` `s`
    JOIN `mfloan`.`person` `p`) JOIN `mfloan`.`address` `a`) JOIN `mfloan`.`contact` `c`) JOIN
    `mfloan`.`identity_doc` `idoc`) JOIN `mfloan`.`employment_history_event` `ev`)
  WHERE ((`s`.`organization_id` = 1) AND (`s`.`person_id` = `p`.`id`) AND (`p`.`address_id` = `a`.`id`) AND
         (`p`.`contact_id` = `c`.`id`) AND (`p`.`identity_doc_id` = `idoc`.`id`) AND
         (`s`.`employment_history_id` = `ev`.`employmentHistory_id`));

#27
DROP TABLE IF EXISTS `executor_view`;
create view executor_view as
  SELECT
    `es`.`Executor_id`                      AS `e_id`,
    group_concat(`s2`.`name` SEPARATOR ',') AS `e_name`
  FROM (`mfloan`.`cat_executor_staff` `es`
    JOIN `mfloan`.`staff` `s2`)
  WHERE (`es`.`staff_id` = `s2`.`id`)
  GROUP BY `e_id`
  UNION ALL SELECT
              `ed`.`Executor_id`                      AS `e_id`,
              group_concat(`d2`.`name` SEPARATOR ',') AS `e_name`
            FROM (`mfloan`.`cat_executor_department` `ed`
              JOIN `mfloan`.`department` `d2`)
            WHERE (`ed`.`departments_id` = `d2`.`id`)
            GROUP BY `e_id`
  UNION ALL SELECT
              `eo`.`Executor_id`                      AS `e_id`,
              group_concat(`o2`.`name` SEPARATOR ',') AS `e_name`
            FROM (`mfloan`.`cat_executor_organization` `eo`
              JOIN `mfloan`.`organization` `o2`)
            WHERE (`eo`.`organizations_id` = `o2`.`id`)
            GROUP BY `e_id`
  UNION ALL SELECT
              `ep`.`Executor_id`                      AS `e_id`,
              group_concat(`p2`.`name` SEPARATOR ',') AS `e_name`
            FROM (`mfloan`.`cat_executor_person` `ep`
              JOIN `mfloan`.`person` `p2`)
            WHERE (`ep`.`person_id` = `p2`.`id`)
            GROUP BY `e_id`
  ORDER BY `e_id`;

#28
DROP TABLE IF EXISTS `responsible_view`;
create view responsible_view as
  SELECT
    `rs`.`Responsible_id`                   AS `r_id`,
    group_concat(`s1`.`name` SEPARATOR ',') AS `r_name`
  FROM (`mfloan`.`cat_responsible_staff` `rs`
    JOIN `mfloan`.`staff` `s1`)
  WHERE (`rs`.`staff_id` = `s1`.`id`)
  GROUP BY `r_id`
  UNION ALL SELECT
              `rd`.`Responsible_id`                   AS `r_id`,
              group_concat(`d1`.`name` SEPARATOR ',') AS `r_name`
            FROM (`mfloan`.`cat_responsible_department` `rd`
              JOIN `mfloan`.`department` `d1`)
            WHERE (`rd`.`departments_id` = `d1`.`id`)
            GROUP BY `r_id`
  UNION ALL SELECT
              `ro`.`Responsible_id`                   AS `r_id`,
              group_concat(`o1`.`name` SEPARATOR ',') AS `r_name`
            FROM (`mfloan`.`cat_responsible_organization` `ro`
              JOIN `mfloan`.`organization` `o1`)
            WHERE (`ro`.`organizations_id` = `o1`.`id`)
            GROUP BY `r_id`
  UNION ALL SELECT
              `rp`.`Responsible_id`                   AS `r_id`,
              group_concat(`p1`.`name` SEPARATOR ',') AS `r_name`
            FROM (`mfloan`.`cat_responsible_person` `rp`
              JOIN `mfloan`.`person` `p1`)
            WHERE (`rp`.`person_id` = `p1`.`id`)
            GROUP BY `r_id`
  ORDER BY `r_id`;

#29
DROP TABLE IF EXISTS `document_view`;
create view document_view as
  SELECT
    `doc`.`id`                                        AS `v_doc_id`,
    `doc`.`description`                               AS `v_doc_description`,
    `doc`.`documentDueDate`                           AS `v_doc_documentDueDate`,
    `doc`.`documentState`                             AS `v_doc_documentState`,
    `doc`.`indexNo`                                   AS `v_doc_indexNo`,
    `doc`.`owner`                                     AS `v_doc_owner`,
    `doc`.`receiverRegisteredDate`                    AS `v_doc_receiverRegisteredDate`,
    `doc`.`receiverRegisteredNumber`                  AS `v_doc_receiverRegisteredNumber`,
    `doc`.`senderRegisteredDate`                      AS `v_doc_senderRegisteredDate`,
    `doc`.`senderRegisteredNumber`                    AS `v_doc_senderRegisteredNumber`,
    `doc`.`title`                                     AS `v_doc_title`,
    `doc`.`documentSubType`                           AS `v_doc_documentSubType`,
    `doc`.`documentType`                              AS `v_doc_documentType`,
    `doc`.`receiverExecutor`                          AS `v_doc_receiverExecutor`,
    `doc`.`receiverResponsible`                       AS `v_doc_receiverResponsible`,
    `doc`.`senderExecutor`                            AS `v_doc_senderExecutor`,
    `doc`.`senderResponsible`                         AS `v_doc_senderResponsible`,
    (SELECT `srv`.`r_name`
     FROM `mfloan`.`responsible_view` `srv`
     WHERE (`srv`.`r_id` = `doc`.`senderResponsible`)
     LIMIT 1)                                         AS `v_doc_sender_responsible_name`,
    (SELECT `rrv`.`r_name`
     FROM `mfloan`.`responsible_view` `rrv`
     WHERE (`rrv`.`r_id` = `doc`.`receiverResponsible`)
     LIMIT 1)                                         AS `v_doc_receiver_responsible_name`,
    (SELECT `sev`.`e_name`
     FROM `mfloan`.`executor_view` `sev`
     WHERE (`sev`.`e_id` = `doc`.`senderExecutor`)
     LIMIT 1)                                         AS `v_doc_sender_executor_name`,
    (SELECT `rev`.`e_name`
     FROM `mfloan`.`executor_view` `rev`
     WHERE (`rev`.`e_id` = `doc`.`receiverExecutor`)) AS `v_doc_receiver_executor_name`,
    `mfloan`.`df_document_users`.`users_id`           AS `v_doc_document_user_id`,
    (SELECT `mfloan`.`staff`.`department_id`
     FROM ((`mfloan`.`department` `dep`
       JOIN `mfloan`.`users`) JOIN `mfloan`.`staff`)
     WHERE
       ((`dep`.`id` = `mfloan`.`staff`.`department_id`) AND (`mfloan`.`users`.`staff_id` = `mfloan`.`staff`.`id`) AND
        (`mfloan`.`df_document_users`.`users_id` = `mfloan`.`users`.`id`))
     LIMIT 1)                                         AS `v_doc_document_department_id`
  FROM (`mfloan`.`df_document` `doc`
    JOIN `mfloan`.`df_document_users`)
  WHERE (`doc`.`id` = `mfloan`.`df_document_users`.`Document_id`);

#30
DROP TABLE IF EXISTS `reference_view`;
create view reference_view as
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
              'payment_type' AS `list_type`,
              `tbl`.`id`     AS `id`,
              `tbl`.`name`   AS `name`
            FROM `mfloan`.`paymentType` `tbl`
  UNION ALL SELECT
              'installment_state' AS `list_type`,
              `tbl`.`id`          AS `id`,
              `tbl`.`name`        AS `name`
            FROM `mfloan`.`installmentState` `tbl`
  UNION ALL SELECT
              'currency_type' AS `list_type`,
              `tbl`.`id`      AS `id`,
              `tbl`.`name`    AS `name`
            FROM `mfloan`.`orderTermCurrency` `tbl`
  UNION ALL SELECT
              'item_type'  AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`collateralItemType` `tbl`
  UNION ALL SELECT
              'inspection_result_type' AS `list_type`,
              `tbl`.`id`               AS `id`,
              `tbl`.`name`             AS `name`
            FROM `mfloan`.`inspectionResultType` `tbl`
  UNION ALL SELECT
              'staff'      AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`staff` `tbl`
  UNION ALL SELECT
              'staff_event_type' AS `list_type`,
              `tbl`.`id`         AS `id`,
              `tbl`.`name`       AS `name`
            FROM `mfloan`.`employment_history_event_type` `tbl`
  UNION ALL SELECT
              'staff_position' AS `list_type`,
              `tbl`.`id`       AS `id`,
              `tbl`.`name`     AS `name`
            FROM `mfloan`.`position` `tbl`
  UNION ALL SELECT
              'doc_type'   AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`cat_document_type` `tbl`
  UNION ALL SELECT
              'doc_subType' AS `list_type`,
              `tbl`.`id`    AS `id`,
              `tbl`.`name`  AS `name`
            FROM `mfloan`.`cat_document_subtype` `tbl`
  UNION ALL SELECT
              'doc_status' AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`cat_document_status` `tbl`
  UNION ALL SELECT
              'collection_procedure_status' AS `list_type`,
              `tbl`.`id`                    AS `id`,
              `tbl`.`name`                  AS `name`
            FROM `mfloan`.`procedureStatus` `tbl`;





# DATA

-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: mfloan
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `content_parameter`
--

LOCK TABLES `content_parameter` WRITE;
/*!40000 ALTER TABLE `content_parameter` DISABLE KEYS */;
INSERT INTO `content_parameter` VALUES (1,'TEXT',0,0,1,7,NULL,0,'Информация по оформлению документации',0,'CONSTANT','','Оформление. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',2,2),(3,'TEXT',0,0,1,7,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Оформление. Заголовок страницы. Строка 2.',2,'PAGE_TITLE',3,3),(4,'TEXT',0,0,1,1,NULL,0,'Кол-во пол.',0,'CONSTANT','','Оформление. Шапка таблицы. Кол-во получателей',1,'TABLE_HEADER',5,6),(5,'TEXT',0,0,2,2,NULL,0,'Наименование',0,'CONSTANT','','Оформление. Шапка таблицы. Наименование',2,'TABLE_HEADER',5,6),(6,'TEXT',0,0,4,7,NULL,0,'в том числе',0,'CONSTANT','','Оформление. Шапка таблицы. Кол-во документов в том числе',4,'TABLE_HEADER',5,5),(7,'TEXT',0,0,3,3,NULL,0,'Кол-во док.',0,'CONSTANT','','Оформление. Шапка таблицы. Кол-во документов',3,'TABLE_HEADER',5,6),(8,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityCount','Оформление. Итого. Кол-во получателей',1,'TABLE_SUM',0,0),(9,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Оформление. Итого. Итого',2,'TABLE_SUM',0,0),(10,'INTEGER',0,0,0,0,NULL,0,'-',0,'ENTITY_DOCUMENT','EntityDocumentCount','Оформление. Итого. Кол-во документов',3,'TABLE_SUM',0,0),(11,'TEXT',0,0,4,4,NULL,0,'укомлектовано',0,'CONSTANT','','Оформление. Шапка таблицы. Укомлектовано',5,'TABLE_HEADER',6,6),(12,'TEXT',0,0,5,5,NULL,0,'не укомпл.',0,'CONSTANT','','Оформление. Шапка таблицы. Не укомплектовано',6,'TABLE_HEADER',6,6),(13,'TEXT',0,0,6,6,NULL,0,'проверено',0,'CONSTANT','','Оформление. Шапка таблицы. Проверено',7,'TABLE_HEADER',6,6),(14,'TEXT',0,0,7,7,NULL,0,'не проверено',0,'CONSTANT','','Оформление. Шапка таблицы. Не проверено',8,'TABLE_HEADER',6,6),(15,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentCompletedCount','Оформление. Итого. Кол-во укомплектованных документов',4,'TABLE_SUM',0,0),(16,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentApprovedCount','Оформление. Итого. Кол-во проверенных документов',6,'TABLE_SUM',0,0),(17,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotCompletedCount','Оформление. Итого. Кол-во не укопмлетованных документов',5,'TABLE_SUM',0,0),(18,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotApprovedCount','Оформление. Итого. Кол-во непроверенных документов',7,'TABLE_SUM',0,0),(19,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityCount','Оформление. Разрез 1. - кол-во получателей',1,'TABLE_GROUP1',0,0),(20,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','Name','Оформление. Разрез 1. Наименование',2,'TABLE_GROUP1',0,0),(21,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentCount','Оформление. Разрез 2. Кол-во документов',3,'TABLE_GROUP1',0,0),(22,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityCount','Оформление. Разрез 2. кол-во получателей',1,'TABLE_GROUP2',0,0),(23,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityCount','Оформление. Разрез 3. Кол-во получателей',1,'TABLE_GROUP3',0,0),(24,'TEXT',0,0,0,0,NULL,0,'',0,'CONSTANT','EntityCount','Оформление. Разрез 4. Кол-во получателей',1,'TABLE_GROUP4',0,0),(25,'TEXT',0,0,0,0,NULL,0,'',0,'CONSTANT','EntityCount','Оформление. Разрез 5. Кол-во получателей',1,'TABLE_GROUP5',0,0),(26,'TEXT',0,0,0,0,NULL,0,'',0,'CONSTANT','EntityCount','Оформление. Разрез 6. Кол-во получателей',1,'TABLE_GROUP6',0,0),(27,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','Name','Оформление. Разрез 2. Наименование',2,'TABLE_GROUP1',0,0),(28,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','Name','Оформление. Разрез 2. Наименование',2,'TABLE_GROUP2',0,0),(29,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','Name','Оформление. Разрез 3. Наименование',2,'TABLE_GROUP3',0,0),(30,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','Name','Оформление. Разрез 4. Наименование',2,'TABLE_GROUP4',0,0),(31,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','Name','Оформление. Разрез 5. Наименование',2,'TABLE_GROUP5',0,0),(32,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentCompletedCount','Оформление. Разрез 1. Кол-во укомплектованных документов ',4,'TABLE_GROUP1',0,0),(33,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentApprovedCount','Оформление. Разрез 1. Количество проверенных документов',6,'TABLE_GROUP1',0,0),(34,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotCompletedCount','Оформление. Разрез 1. Кол-во не укопмлетованных документов ',5,'TABLE_GROUP1',0,0),(35,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotApprovedCount','Оформление. Разрез 1. Кол-во непроверенных документов ',7,'TABLE_GROUP1',0,0),(36,'INTEGER',0,0,0,0,NULL,0,'-',0,'ENTITY_DOCUMENT','EntityDocumentCount','Оформление. Разрез 1. Кол-во док.',3,'TABLE_GROUP1',0,0),(37,'INTEGER',0,0,0,0,NULL,0,'-',0,'ENTITY_DOCUMENT','EntityDocumentCount','Оформление. Разрез 2. Кол-во док.',3,'TABLE_GROUP2',0,0),(38,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentCompletedCount','Оформление. Разрез 2. Кол-во укомплектованных документов ',4,'TABLE_GROUP2',0,0),(39,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentApprovedCount','Оформление. Разрез 2. Количество проверенных документов',6,'TABLE_GROUP2',0,0),(40,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotCompletedCount','Оформление. Разрез 2. Кол-во не укопмлетованных документов ',5,'TABLE_GROUP2',0,0),(41,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotApprovedCount','Оформление. Разрез 2. Кол-во непроверенных документов ',7,'TABLE_GROUP2',0,0),(42,'INTEGER',0,0,0,0,NULL,0,'-',0,'ENTITY_DOCUMENT','EntityDocumentCount','Оформление. Разрез 3. Кол-во док.',3,'TABLE_GROUP3',0,0),(43,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentCompletedCount','Оформление. Разрез 3. Кол-во укомплектованных документов ',4,'TABLE_GROUP3',0,0),(44,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentApprovedCount','Оформление. Разрез 3. Количество проверенных документов',6,'TABLE_GROUP3',0,0),(45,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotCompletedCount','Оформление. Разрез 3. Кол-во не укопмлетованных документов ',5,'TABLE_GROUP3',0,0),(46,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotApprovedCount','Оформление. Разрез 3. Кол-во непроверенных документов ',7,'TABLE_GROUP3',0,0),(47,'INTEGER',0,0,0,0,NULL,0,'-',0,'ENTITY_DOCUMENT','EntityDocumentCount','Оформление. Разрез 4. Кол-во док.',3,'TABLE_GROUP4',0,0),(48,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentCompletedCount','Оформление. Разрез 4. Кол-во укомплектованных документов ',4,'TABLE_GROUP4',0,0),(49,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentApprovedCount','Оформление. Разрез 4. Количество проверенных документов',6,'TABLE_GROUP4',0,0),(50,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotCompletedCount','Оформление. Разрез 4. Кол-во не укопмлетованных документов ',5,'TABLE_GROUP4',0,0),(51,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotApprovedCount','Оформление. Разрез 4. Кол-во непроверенных документов ',7,'TABLE_GROUP4',0,0),(52,'INTEGER',0,0,0,0,NULL,0,'-',0,'ENTITY_DOCUMENT','EntityDocumentCount','Оформление. Разрез 5. Кол-во док.',3,'TABLE_GROUP5',0,0),(53,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentCompletedCount','Оформление. Разрез 5. Кол-во укомплектованных документов ',4,'TABLE_GROUP5',0,0),(54,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentApprovedCount','Оформление. Разрез 5. Количество проверенных документов',6,'TABLE_GROUP5',0,0),(55,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotCompletedCount','Оформление. Разрез 5. Кол-во не укопмлетованных документов ',5,'TABLE_GROUP5',0,0),(56,'INTEGER',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','EntityDocumentNotApprovedCount','Оформление. Разрез 5. Кол-во непроверенных документов ',7,'TABLE_GROUP5',0,0),(57,'TEXT',0,0,8,8,NULL,0,'Статус',0,'CONSTANT','','Оформление. Шапка таблицы. Статус',8,'TABLE_HEADER',5,6),(61,'TEXT',0,0,0,0,NULL,21,'applied_entity_status',0,'ENTITY_DOCUMENT','V_applied_entity_appliedEntityStateId','Оформление. Разрез 3. Статус',8,'TABLE_GROUP3',0,0),(62,'TEXT',0,0,0,0,NULL,23,'document_package_status',0,'ENTITY_DOCUMENT','v_document_package_documentPackageStateId','Оформление. Разрез 4. Статус',8,'TABLE_GROUP4',0,0),(63,'TEXT',0,0,0,0,NULL,22,'entity_document_status',0,'ENTITY_DOCUMENT','V_entity_document_entityDocumentStateId','Оформление. Разрез 5. Статус',8,'TABLE_GROUP5',0,0),(64,'TEXT',0,0,9,9,NULL,0,'Укомплектовано',0,'CONSTANT','','Оформление. Шапка таблицы. Укомплектовано',9,'TABLE_HEADER',5,6),(65,'TEXT',0,0,10,10,NULL,0,'Дата комплектации',0,'CONSTANT','','Оформление. Шапка таблицы. Дата комплектации',10,'TABLE_HEADER',5,6),(66,'TEXT',0,0,11,11,NULL,0,'Примечание',0,'CONSTANT','','Оформление. Шапка таблицы. Примечание',11,'TABLE_HEADER',5,6),(67,'TEXT',0,0,12,12,NULL,0,'Проверено',0,'CONSTANT','','Оформление. Шапка таблицы. Проверено',12,'TABLE_HEADER',5,6),(68,'TEXT',0,0,13,13,NULL,0,'Дата проверки',0,'CONSTANT','','Оформление. Шапка таблицы. Дата проверки',13,'TABLE_HEADER',5,6),(69,'TEXT',0,0,14,14,NULL,0,'Примечание',0,'CONSTANT','','Оформление. Шапка таблицы. Примечание2',14,'TABLE_HEADER',5,6),(75,'TEXT',0,0,0,0,NULL,5,'supervisor',0,'ENTITY_DOCUMENT','v_entity_document_completedBy','Оформление. Разрез 5. Укомплектовано',9,'TABLE_GROUP5',0,0),(81,'TEXT',0,0,0,0,NULL,5,'supervisor',0,'ENTITY_DOCUMENT','v_entity_document_approvedBy','Оформление. Разрез 5. Проверено',12,'TABLE_GROUP5',0,0),(87,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','v_entity_document_completedDate','Оформление. Разрез 5. Дата комплектации',10,'TABLE_GROUP5',0,0),(93,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','v_entity_document_approvedDate','Оформление. Разрез 5. Дата проверки',13,'TABLE_GROUP5',0,0),(99,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','v_entity_document_completedDescription','Оформление. Разрез 5. Примечание',11,'TABLE_GROUP5',0,0),(105,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','v_entity_document_approvedDescription','Оформление. Разрез 5. Примечание',14,'TABLE_GROUP5',0,0),(106,'TEXT',0,0,1,8,NULL,0,'Информация по взысканию',0,'CONSTANT','','Взыскание. Заголовок страницы. Строка 1.',1,'PAGE_TITLE',1,1),(107,'TEXT',0,0,1,8,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Взыскание. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(108,'TEXT',0,0,1,1,NULL,0,'Кол-во суб.',0,'CONSTANT','','Взыскание. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(109,'TEXT',0,0,2,2,NULL,0,'Наименование',0,'CONSTANT','','Взыскание. Шапка таблицы. Наименование',2,'TABLE_HEADER',5,6),(110,'TEXT',0,0,3,6,NULL,0,'Стадия',0,'CONSTANT','','Взыскание. Шапка таблицы. Стадия',3,'TABLE_HEADER',5,5),(111,'TEXT',0,0,4,4,NULL,0,'Вид',0,'CONSTANT','','Взыскание. Шапка таблицы. Вид',4,'TABLE_HEADER',6,6),(112,'TEXT',0,0,5,5,NULL,0,'Дата',0,'CONSTANT','','Взыскание. Шапка таблицы. Дата.',5,'TABLE_HEADER',6,6),(113,'TEXT',0,0,6,6,NULL,0,'Сумма.',0,'CONSTANT','','Взыскание. Шапка таблицы. Сумма.',5,'TABLE_HEADER',6,6),(114,'TEXT',0,0,7,10,NULL,0,'Результат',0,'CONSTANT','','Взыскание. Шапка таблицы. Результат',7,'TABLE_HEADER',5,5),(115,'TEXT',0,0,8,8,NULL,0,'Вид',0,'CONSTANT','','Взыскание. Шапка таблицы. Вид.',8,'TABLE_HEADER',6,6),(116,'TEXT',0,0,9,9,NULL,0,'Дата.',0,'CONSTANT','','Взыскание. Шапка таблицы. Дата.',9,'TABLE_HEADER',6,6),(117,'TEXT',0,0,10,10,NULL,0,'Сумма',0,'CONSTANT','','Взыскание. Шапка таблицы. Сумма',10,'TABLE_HEADER',6,6),(118,'TEXT',0,0,11,11,NULL,0,'Статус',0,'CONSTANT','','Взыскание. Шапка таблицы. Статус.',11,'TABLE_HEADER',5,6),(119,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLECTION_PHASE','count','Взыскание. Итого. Кол-во суб.',1,'TABLE_SUM',0,0),(120,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Взыскание. Итого. Итого.',2,'TABLE_SUM',0,0),(121,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLECTION_PHASE','count','Взыскание. Разрез 1. Кол-во субъектов',1,'TABLE_GROUP1',0,0),(122,'TEXT',0,0,0,0,NULL,0,'',0,'COLLECTION_PHASE','name','Взыскание. Разрез 1. Наименование',2,'TABLE_GROUP1',0,0),(123,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLECTION_PHASE','count','Взыскание. Разрез 2. Кол-во субъектов',1,'TABLE_GROUP2',0,0),(124,'TEXT',0,0,0,0,NULL,0,'',0,'COLLECTION_PHASE','name','Взыскание. Разрез 2. Наименование',2,'TABLE_GROUP2',0,0),(125,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLECTION_PHASE','count','Взыскание. Разрез 3. Кол-во субъектов',1,'TABLE_GROUP3',0,0),(126,'TEXT',0,0,0,0,NULL,0,'',0,'COLLECTION_PHASE','name','Взыскание. Разрез 3. Наименование',2,'TABLE_GROUP3',0,0),(127,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLECTION_PHASE','count','Взыскание. Разрез 4. Кол-во субъектов.',1,'TABLE_GROUP4',0,0),(128,'TEXT',0,0,0,0,NULL,0,'',0,'ENTITY_DOCUMENT','name','Взыскание. Разрез 4. Наименование.',2,'TABLE_GROUP4',0,0),(129,'TEXT',0,0,4,4,NULL,28,'collection_phase_type',0,'COLLECTION_PHASE','collection_phase_type_id','Взыскание. Разрез 5. Вид стадии',4,'TABLE_GROUP5',0,0),(130,'DATE',0,0,5,5,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_start_date','Взыскание. Разрез 5. Дата стадии',5,'TABLE_GROUP5',0,0),(131,'DOUBLE',0,0,6,6,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_start_total_amount','Взыскание. Разрез 5. Сумма стадии.',6,'TABLE_GROUP5',0,0),(132,'TEXT',0,0,8,8,NULL,29,'collection_phase_status',0,'COLLECTION_PHASE','collection_phase_status_id','Взыскание. Разрез 5. Вид результата.',8,'TABLE_GROUP5',0,0),(133,'DATE',0,0,9,9,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_close_date','Взыскание. Разрез 5. Дата результата.',9,'TABLE_GROUP5',0,0),(134,'DOUBLE',0,0,10,10,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_close_total_amount','Взыскание. Разрез 5. Сумма результата',10,'TABLE_GROUP5',0,0),(135,'TEXT',0,0,11,11,NULL,30,'collection_procedure_status',0,'COLLECTION_PHASE','collection_procedure_status_id','Взыскание. Разрез 5. Статус',11,'TABLE_GROUP5',0,0),(136,'TEXT',0,0,3,3,NULL,0,'Кол-во',0,'CONSTANT','','Взыскание. Шапка таблицы. Кол-во стадий',4,'TABLE_HEADER',6,6),(137,'TEXT',0,0,7,7,NULL,0,'Кол-во',0,'CONSTANT','','Взыскание. Шапка таблицы. Кол-во результатов.',9,'TABLE_HEADER',6,6),(138,'TEXT',0,0,3,3,NULL,0,'',0,'COLLECTION_PHASE','phaseCount','Взыскание. Разрез 5. Кол-во стадии',3,'TABLE_SUM',0,0),(139,'INTEGER',0,0,7,7,NULL,0,'',0,'COLLECTION_PHASE','resultCount','Взыскание. Разрез 5. Кол-во результатов',7,'TABLE_SUM',0,0),(140,'TEXT',0,0,3,3,NULL,0,'',0,'COLLECTION_PHASE','phaseCount','Взыскание. Разрез 1. Кол-во стадии',3,'TABLE_GROUP1',0,0),(141,'TEXT',0,0,7,7,NULL,0,'',0,'COLLECTION_PHASE','resultCount','Взыскание. Разрез 1. Кол-во результатов',7,'TABLE_GROUP1',0,0),(142,'TEXT',0,0,3,3,NULL,0,'',0,'COLLECTION_PHASE','phaseCount','Взыскание. Разрез 2. Кол-во стадии',3,'TABLE_GROUP2',0,0),(143,'TEXT',0,0,7,7,NULL,0,'',0,'COLLECTION_PHASE','resultCount','Взыскание. Разрез 2. Кол-во результатов',7,'TABLE_GROUP2',0,0),(144,'TEXT',0,0,3,3,NULL,0,'',0,'COLLECTION_PHASE','phaseCount','Взыскание. Разрез 3. Кол-во стадии',3,'TABLE_GROUP3',0,0),(145,'TEXT',0,0,7,7,NULL,0,'',0,'COLLECTION_PHASE','resultCount','Взыскание. Разрез 3. Кол-во результатов',7,'TABLE_GROUP3',0,0),(146,'TEXT',0,0,3,3,NULL,0,'',0,'COLLECTION_PHASE','phaseCount','Взыскание. Разрез 4. Кол-во стадии',3,'TABLE_GROUP4',0,0),(147,'TEXT',0,0,7,7,NULL,0,'',0,'COLLECTION_PHASE','resultCount','Взыскание. Разрез 4. Кол-во результатов',7,'TABLE_GROUP4',0,0),(148,'DOUBLE',0,0,6,6,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_start_total_amount','Взыскание. Разрез 4. Сумма стадии.',6,'TABLE_GROUP4',0,0),(149,'DOUBLE',0,0,10,10,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_close_total_amount','Взыскание. Разрез 4. Сумма результата',10,'TABLE_GROUP4',0,0),(150,'DOUBLE',0,0,6,6,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_start_total_amount','Взыскание. Разрез 3. Сумма стадии.',6,'TABLE_GROUP3',0,0),(151,'DOUBLE',0,0,10,10,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_close_total_amount','Взыскание. Разрез 3. Сумма результата',10,'TABLE_GROUP3',0,0),(152,'DOUBLE',0,0,6,6,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_start_total_amount','Взыскание. Разрез 2. Сумма стадии.',6,'TABLE_GROUP2',0,0),(153,'DOUBLE',0,0,10,10,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_close_total_amount','Взыскание. Разрез 2. Сумма результата',10,'TABLE_GROUP2',0,0),(154,'DOUBLE',0,0,6,6,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_start_total_amount','Взыскание. Разрез 1. Сумма стадии.',6,'TABLE_GROUP1',0,0),(155,'DOUBLE',0,0,10,10,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_close_total_amount','Взыскание. Разрез 1. Сумма результата',10,'TABLE_GROUP1',0,0),(156,'DOUBLE',0,0,6,6,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_start_total_amount','Взыскание. Итого. Сумма стадии.',6,'TABLE_SUM',0,0),(157,'DOUBLE',0,0,10,10,NULL,0,'',0,'COLLECTION_PHASE','collection_phase_close_total_amount','Взыскание. Итого. Сумма результата',10,'TABLE_SUM',0,0),(158,'TEXT',0,0,1,7,NULL,0,'Информация по задолженности',0,'CONSTANT','','Задолженность. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(159,'TEXT',0,0,1,7,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Задолженность. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(160,'TEXT',0,0,1,1,NULL,0,'Кол-во суб-тов.',0,'CONSTANT','','Задолженность. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(161,'TEXT',0,0,2,2,NULL,0,'Кол-во кред-тов',0,'CONSTANT','','Задолженность. Шапка таблицы. Кол-во кредитов',2,'TABLE_HEADER',5,6),(162,'TEXT',0,0,3,3,NULL,0,'Наименование',0,'CONSTANT','','Задолженность. Шапка таблицы. Наименование',3,'TABLE_HEADER',5,6),(163,'TEXT',0,0,4,4,NULL,0,'Вид кредита',0,'CONSTANT','','Задолженность. Шапка таблицы. Вид кредита',4,'TABLE_HEADER',5,6),(164,'TEXT',0,0,5,5,NULL,0,'Сумма по договору',0,'CONSTANT','','Задолженность. Шапка таблицы. Сумма по договору',5,'TABLE_HEADER',5,6),(165,'TEXT',0,0,6,6,NULL,0,'Всего получено',0,'CONSTANT','','Задолженность. Шапка таблицы. Всего получено',6,'TABLE_HEADER',5,6),(166,'TEXT',0,0,7,7,NULL,0,'Срок возврата',0,'CONSTANT','','Задолженность. Шапка таблицы. Срок возврата',7,'TABLE_HEADER',5,6),(167,'TEXT',0,0,8,8,NULL,0,'Всего погашено',0,'CONSTANT','','Задолженность. Шапка таблицы. Всего погашено',8,'TABLE_HEADER',5,6),(168,'TEXT',0,0,9,9,NULL,0,'Всего остаток',0,'CONSTANT','','Задолженность. Шапка таблицы. Всего остаток',9,'TABLE_HEADER',5,6),(169,'TEXT',0,0,10,12,NULL,0,'в том числе',0,'CONSTANT','','Задолженность. Шапка таблицы. Всего задолженность в том числе',4,'TABLE_HEADER',5,5),(170,'TEXT',0,0,10,10,NULL,0,'по основной сумме',0,'CONSTANT','','Задолженность. Шапка таблицы. По основной сумме',11,'TABLE_HEADER',6,6),(171,'TEXT',0,0,11,11,NULL,0,'по процентам',0,'CONSTANT','','Задолженность. Шапка таблицы. По процентам',12,'TABLE_HEADER',6,6),(172,'TEXT',0,0,12,12,NULL,0,'по штрафам',0,'CONSTANT','','Задолженность. Шапка таблицы. По по штрафам',13,'TABLE_HEADER',6,6),(173,'TEXT',0,0,13,13,NULL,0,'Просроченная задолженность',0,'CONSTANT','','Задолженность. Шапка таблицы. Просроченная задолженность',14,'TABLE_HEADER',5,6),(174,'TEXT',0,0,14,16,NULL,0,'в том числе',0,'CONSTANT','','Задолженность. Шапка таблицы. Просроченная задолженность в том числе',15,'TABLE_HEADER',5,5),(175,'TEXT',0,0,14,14,NULL,0,'по основной сумме',0,'CONSTANT','','Задолженность. Шапка таблицы. По основной сумме',16,'TABLE_HEADER',6,6),(176,'TEXT',0,0,15,15,NULL,0,'по процентам',0,'CONSTANT','','Задолженность. Шапка таблицы. По процентам',17,'TABLE_HEADER',6,6),(177,'TEXT',0,0,16,16,NULL,0,'по штрафам',0,'CONSTANT','','Задолженность. Шапка таблицы. По по штрафам',18,'TABLE_HEADER',6,6),(178,'TEXT',0,0,17,17,NULL,0,'Примечание',0,'CONSTANT','','Задолженность. Шапка таблицы. Примечание',19,'TABLE_HEADER',5,6),(179,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Count','Звдолженность. Итого. Кол-во субъектов',1,'TABLE_SUM',0,0),(180,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','DetailsCount','Звдолженность. Итого. Кол-во кредитов',2,'TABLE_SUM',0,0),(181,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Звдолженность. Итого. Итого',3,'TABLE_SUM',0,0),(183,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','LoanAmount','Звдолженность. Итого. Сумма по договору',5,'TABLE_SUM',0,0),(184,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalDisbursment','Звдолженность. Итого. Всего получено',6,'TABLE_SUM',0,0),(185,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalPaid','Звдолженность. Итого. Всего погашено',8,'TABLE_SUM',0,0),(186,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingSum','Задолженность. Итого. Остаток задолженности',9,'TABLE_SUM',0,0),(187,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPrincipal','Задолженность. Итого. Остаток по основной сумме',10,'TABLE_SUM',0,0),(188,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingInterest','Задолженность. Итого. Остаток по процентам',11,'TABLE_SUM',0,0),(189,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPenalty','Задолженность. Итого. Остаток по штрафам',12,'TABLE_SUM',0,0),(190,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueAll','Задолженность. Итого. Просроченная задолженность',13,'TABLE_SUM',0,0),(191,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePrincipal','Задолженность. Итого. Просрочка по основной сумме',14,'TABLE_SUM',0,0),(192,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueInterest','Задолженность. Итого. Просрочка по процентам',15,'TABLE_SUM',0,0),(193,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePenalty','Задолженность. Итого. Просрочка по штрафам',16,'TABLE_SUM',0,0),(194,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Count','Задолженность. Разрез1. Кол-во субъектов',1,'TABLE_GROUP1',0,0),(195,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','DetailsCount','Задолженность. Разрез1. Кол-во кредитов',2,'TABLE_GROUP1',0,0),(196,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Name','Задолженность. Разрез1. Наименование',3,'TABLE_GROUP1',0,0),(197,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','LoanAmount','Задолженность. Разрез1. Сумма по договору',5,'TABLE_GROUP1',0,0),(198,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalDisbursment','Задолженность. Разрез1. Всего получено',6,'TABLE_GROUP1',0,0),(199,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalPaid','Задолженность. Разрез1. Всего погашено',8,'TABLE_GROUP1',0,0),(200,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingSum','Задолженность. Разрез1. Остаток задолженности',9,'TABLE_GROUP1',0,0),(201,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPrincipal','Задолженность. Разрез1. Остаток по основной сумме',10,'TABLE_GROUP1',0,0),(202,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingInterest','Задолженность. Разрез1. Остаток по процентам',11,'TABLE_GROUP1',0,0),(203,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPenalty','Задолженность. Разрез1. Остаток по штрафам',12,'TABLE_GROUP1',0,0),(204,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueAll','Задолженность. Разрез1. Просроченная задолженность',13,'TABLE_GROUP1',0,0),(205,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePrincipal','Задолженность. Разрез1. Просрочка по основной сумме',14,'TABLE_GROUP1',0,0),(206,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueInterest','Задолженность. Разрез1. Просрочка по процентам',15,'TABLE_GROUP1',0,0),(207,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePenalty','Задолженность. Разрез1. Просрочка по штрафам',16,'TABLE_GROUP1',0,0),(208,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Count','Задолженность. Разрез 2. Кол-во субъектов',1,'TABLE_GROUP2',0,0),(209,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','DetailsCount','Задолженность. Разрез 2. Кол-во кредитов',2,'TABLE_GROUP2',0,0),(210,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Name','Задолженность. Разрез 2. Наименование',3,'TABLE_GROUP2',0,0),(211,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','LoanAmount','Задолженность. Разрез 2. Сумма по договору',5,'TABLE_GROUP2',0,0),(212,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalDisbursment','Задолженность. Разрез 2. Всего получено',6,'TABLE_GROUP2',0,0),(213,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalPaid','Задолженность. Разрез 2. Всего погашено',8,'TABLE_GROUP2',0,0),(214,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingSum','Задолженность. Разрез 2. Остаток задолженности',9,'TABLE_GROUP2',0,0),(215,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPrincipal','Задолженность. Разрез 2. Остаток по основной сумме',10,'TABLE_GROUP2',0,0),(216,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingInterest','Задолженность. Разрез 2. Остаток по процентам',11,'TABLE_GROUP2',0,0),(217,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPenalty','Задолженность. Разрез 2. Остаток по штрафам',12,'TABLE_GROUP2',0,0),(218,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueAll','Задолженность. Разрез 2. Просроченная задолженность',13,'TABLE_GROUP2',0,0),(219,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePrincipal','Задолженность. Разрез 2. Просрочка по основной сумме',14,'TABLE_GROUP2',0,0),(220,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueInterest','Задолженность. Разрез 2. Просрочка по процентам',15,'TABLE_GROUP2',0,0),(221,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePenalty','Задолженность. Разрез 2. Просрочка по штрафам',16,'TABLE_GROUP2',0,0),(222,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Count','Задолженность. Разрез 3. Кол-во субъектов',1,'TABLE_GROUP3',0,0),(223,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','DetailsCount','Задолженность. Разрез 3. Кол-во кредитов',2,'TABLE_GROUP3',0,0),(224,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Name','Задолженность. Разрез 3. Наименование',3,'TABLE_GROUP3',0,0),(225,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','LoanAmount','Задолженность. Разрез 3. Сумма по договору',5,'TABLE_GROUP3',0,0),(226,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalDisbursment','Задолженность. Разрез 3. Всего получено',6,'TABLE_GROUP3',0,0),(227,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalPaid','Задолженность. Разрез 3. Всего погашено',8,'TABLE_GROUP3',0,0),(228,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingSum','Задолженность. Разрез 3. Остаток задолженности',9,'TABLE_GROUP3',0,0),(229,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPrincipal','Задолженность. Разрез 3. Остаток по основной сумме',10,'TABLE_GROUP3',0,0),(230,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingInterest','Задолженность. Разрез 3. Остаток по процентам',11,'TABLE_GROUP3',0,0),(231,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPenalty','Задолженность. Разрез 3. Остаток по штрафам',12,'TABLE_GROUP3',0,0),(232,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueAll','Задолженность. Разрез 3. Просроченная задолженность',13,'TABLE_GROUP3',0,0),(233,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePrincipal','Задолженность. Разрез 3. Просрочка по основной сумме',14,'TABLE_GROUP3',0,0),(234,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueInterest','Задолженность. Разрез 3. Просрочка по процентам',15,'TABLE_GROUP3',0,0),(235,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePenalty','Задолженность. Разрез 3. Просрочка по штрафам',16,'TABLE_GROUP3',0,0),(236,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Count','Задолженность. Разрез 4. Кол-во субъектов',1,'TABLE_GROUP4',0,0),(237,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','DetailsCount','Задолженность. Разрез 4. Кол-во кредитов',2,'TABLE_GROUP4',0,0),(238,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Name','Задолженность. Разрез 4. Наименование',3,'TABLE_GROUP4',0,0),(239,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','LoanAmount','Задолженность. Разрез 4. Сумма по договору',5,'TABLE_GROUP4',0,0),(240,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalDisbursment','Задолженность. Разрез 4. Всего получено',6,'TABLE_GROUP4',0,0),(241,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalPaid','Задолженность. Разрез 4. Всего погашено',8,'TABLE_GROUP4',0,0),(242,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingSum','Задолженность. Разрез 4. Остаток задолженности',9,'TABLE_GROUP4',0,0),(243,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPrincipal','Задолженность. Разрез 4. Остаток по основной сумме',10,'TABLE_GROUP4',0,0),(244,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingInterest','Задолженность. Разрез 4. Остаток по процентам',11,'TABLE_GROUP4',0,0),(245,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPenalty','Задолженность. Разрез 4. Остаток по штрафам',12,'TABLE_GROUP4',0,0),(246,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueAll','Задолженность. Разрез 4. Просроченная задолженность',13,'TABLE_GROUP4',0,0),(247,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePrincipal','Задолженность. Разрез 4. Просрочка по основной сумме',14,'TABLE_GROUP4',0,0),(248,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueInterest','Задолженность. Разрез 4. Просрочка по процентам',15,'TABLE_GROUP4',0,0),(249,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePenalty','Задолженность. Разрез 4. Просрочка по штрафам',16,'TABLE_GROUP4',0,0),(250,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Count','Задолженность. Разрез 5. Кол-во субъектов',1,'TABLE_GROUP5',0,0),(251,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','DetailsCount','Задолженность. Разрез 5. Кол-во кредитов',2,'TABLE_GROUP5',0,0),(252,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','Name','Задолженность. Разрез 5. Наименование',3,'TABLE_GROUP5',0,0),(253,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','LoanAmount','Задолженность. Разрез 5. Сумма по договору',5,'TABLE_GROUP5',0,0),(254,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalDisbursment','Задолженность. Разрез 5. Всего получено',6,'TABLE_GROUP5',0,0),(255,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','TotalPaid','Задолженность. Разрез 5. Всего погашено',8,'TABLE_GROUP5',0,0),(256,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingSum','Задолженность. Разрез 5. Остаток задолженности',9,'TABLE_GROUP5',0,0),(257,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPrincipal','Задолженность. Разрез 5. Остаток по основной сумме',10,'TABLE_GROUP5',0,0),(258,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingInterest','Задолженность. Разрез 5. Остаток по процентам',11,'TABLE_GROUP5',0,0),(259,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingPenalty','Задолженность. Разрез 5. Остаток по штрафам',12,'TABLE_GROUP5',0,0),(260,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueAll','Задолженность. Разрез 5. Просроченная задолженность',13,'TABLE_GROUP5',0,0),(261,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePrincipal','Задолженность. Разрез 5. Просрочка по основной сумме',14,'TABLE_GROUP5',0,0),(262,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverdueInterest','Задолженность. Разрез 5. Просрочка по процентам',15,'TABLE_GROUP5',0,0),(263,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','OverduePenalty','Задолженность. Разрез 5. Просрочка по штрафам',16,'TABLE_GROUP5',0,0),(264,'TEXT',0,0,1,7,NULL,0,'Информация по погашениям',0,'CONSTANT','','Погашения. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(265,'TEXT',0,0,1,7,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Погашения. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(266,'TEXT',0,0,1,1,NULL,0,'Кол-во суб-тов.',0,'CONSTANT','','Погашения. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(267,'TEXT',0,0,2,2,NULL,0,'Кол-во кред-тов',0,'CONSTANT','','Погашения. Шапка таблицы. Кол-во кредитов',2,'TABLE_HEADER',5,6),(268,'TEXT',0,0,3,3,NULL,0,'Кол-во погашений',0,'CONSTANT','','Погашения. Шапка таблицы. Кол-во погашений',3,'TABLE_HEADER',5,6),(269,'TEXT',0,0,4,4,NULL,0,'Наименование',0,'CONSTANT','','Погашения. Шапка таблицы. Наименование',4,'TABLE_HEADER',5,6),(270,'TEXT',0,0,5,5,NULL,0,'Всего погашено',0,'CONSTANT','','Погашения. Шапка таблицы. Всего погашено',5,'TABLE_HEADER',5,6),(271,'TEXT',0,0,6,8,NULL,0,'в том числе',0,'CONSTANT','','Погашения. Шапка таблицы. Всего погашено в том числе',6,'TABLE_HEADER',5,5),(272,'TEXT',0,0,6,6,NULL,0,'по основной сумме',0,'CONSTANT','','Погашения. Шапка таблицы. По основной сумме',7,'TABLE_HEADER',6,6),(273,'TEXT',0,0,7,7,NULL,0,'по процентам',0,'CONSTANT','','Погашения. Шапка таблицы. По процентам',8,'TABLE_HEADER',6,6),(274,'TEXT',0,0,8,8,NULL,0,'по штрафам',0,'CONSTANT','','Погашения. Шапка таблицы. По по штрафам',9,'TABLE_HEADER',6,6),(275,'TEXT',0,0,9,9,NULL,0,'Номер плат. документа',0,'CONSTANT','','Погашения. Шапка таблицы. Номер плат. документа',5,'TABLE_HEADER',5,6),(276,'TEXT',0,0,10,10,NULL,0,'Дата платежа',0,'CONSTANT','','Погашения. Шапка таблицы. Дата платежа',5,'TABLE_HEADER',5,6),(277,'TEXT',0,0,11,11,NULL,0,'Курс на дату платежа',0,'CONSTANT','','Погашения. Шапка таблицы. Курс на дату платежа',5,'TABLE_HEADER',5,6),(278,'TEXT',0,0,12,12,NULL,0,'Вид погашения',0,'CONSTANT','','Погашения. Шапка таблицы. Вид погашения',5,'TABLE_HEADER',5,6),(279,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentTotalAmount','Погашения. Итого. Всего погашено',5,'TABLE_SUM',0,0),(280,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPrincipal','Погашения. Итого. по осн.сумме',6,'TABLE_SUM',0,0),(281,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentInterest','Погашения. Итого. по процентам',7,'TABLE_SUM',0,0),(282,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPenalty','Погашения. Итого. по штрафам',8,'TABLE_SUM',0,0),(283,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Погашения. Итого. Кол-во заемщиков',1,'TABLE_SUM',0,0),(284,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Погашения. Итого. Кол-во кредитов',2,'TABLE_SUM',0,0),(285,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Погашения. Итого. Кол-во погашений',3,'TABLE_SUM',0,0),(286,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Погашения. Итого. Итого',4,'TABLE_SUM',0,0),(287,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentTotalAmount','Погашения. Разрез1. Всего погашено',5,'TABLE_GROUP1',0,0),(288,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPrincipal','Погашения. Разрез1. по осн.сумме',6,'TABLE_GROUP1',0,0),(289,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentInterest','Погашения. Разрез1. по процентам',7,'TABLE_GROUP1',0,0),(290,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPenalty','Погашения. Разрез1. по штрафам',8,'TABLE_GROUP1',0,0),(291,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Погашения. Разрез1. Кол-во заемщиков',1,'TABLE_GROUP1',0,0),(292,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Погашения. Разрез1. Кол-во кредитов',2,'TABLE_GROUP1',0,0),(293,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Погашения. Разрез1. Кол-во погашений',3,'TABLE_GROUP1',0,0),(294,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Погашения. Разрез1. Наименование',4,'TABLE_GROUP1',0,0),(295,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentTotalAmount','Погашения. Разрез2. Всего погашено',5,'TABLE_GROUP2',0,0),(296,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPrincipal','Погашения. Разрез2. по осн.сумме',6,'TABLE_GROUP2',0,0),(297,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentInterest','Погашения. Разрез2. по процентам',7,'TABLE_GROUP2',0,0),(298,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPenalty','Погашения. Разрез2. по штрафам',8,'TABLE_GROUP2',0,0),(299,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Погашения. Разрез2. Кол-во заемщиков',1,'TABLE_GROUP2',0,0),(300,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Погашения. Разрез2. Кол-во кредитов',2,'TABLE_GROUP2',0,0),(301,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Погашения. Разрез2. Кол-во погашений',3,'TABLE_GROUP2',0,0),(302,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Погашения. Разрез2. Наименование',4,'TABLE_GROUP2',0,0),(303,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentTotalAmount','Погашения. Разрез3. Всего погашено',5,'TABLE_GROUP3',0,0),(304,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPrincipal','Погашения. Разрез3. по осн.сумме',6,'TABLE_GROUP3',0,0),(305,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentInterest','Погашения. Разрез3. по процентам',7,'TABLE_GROUP3',0,0),(306,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPenalty','Погашения. Разрез3. по штрафам',8,'TABLE_GROUP3',0,0),(307,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Погашения. Разрез3. Кол-во заемщиков',1,'TABLE_GROUP3',0,0),(308,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Погашения. Разрез3. Кол-во кредитов',2,'TABLE_GROUP3',0,0),(309,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Погашения. Разрез3. Кол-во погашений',3,'TABLE_GROUP3',0,0),(310,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Погашения. Разрез3. Наименование',4,'TABLE_GROUP3',0,0),(311,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentTotalAmount','Погашения. Разрез4. Всего погашено',5,'TABLE_GROUP4',0,0),(312,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPrincipal','Погашения. Разрез4. по осн.сумме',6,'TABLE_GROUP4',0,0),(313,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentInterest','Погашения. Разрез4. по процентам',7,'TABLE_GROUP4',0,0),(314,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPenalty','Погашения. Разрез4. по штрафам',8,'TABLE_GROUP4',0,0),(315,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Погашения. Разрез4. Кол-во заемщиков',1,'TABLE_GROUP4',0,0),(316,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Погашения. Разрез4. Кол-во кредитов',2,'TABLE_GROUP4',0,0),(317,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Погашения. Разрез4. Кол-во погашений',3,'TABLE_GROUP4',0,0),(318,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Погашения. Разрез4. Наименование',4,'TABLE_GROUP4',0,0),(319,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentTotalAmount','Погашения. Разрез5. Всего погашено',5,'TABLE_GROUP5',0,0),(320,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPrincipal','Погашения. Разрез5. по осн.сумме',6,'TABLE_GROUP5',0,0),(321,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentInterest','Погашения. Разрез5. по процентам',7,'TABLE_GROUP5',0,0),(322,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentPenalty','Погашения. Разрез5. по штрафам',8,'TABLE_GROUP5',0,0),(323,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Погашения. Разрез5. Кол-во заемщиков',1,'TABLE_GROUP5',0,0),(324,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Погашения. Разрез5. Кол-во кредитов',2,'TABLE_GROUP5',0,0),(325,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Погашения. Разрез5. Кол-во погашений',3,'TABLE_GROUP5',0,0),(326,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Погашения. Разрез5. Наименование',4,'TABLE_GROUP5',0,0),(334,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentNumber','Погашения. Разрез5. Номер платежного документа',9,'TABLE_GROUP5',0,0),(335,'DATE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentDate','Погашения. Разрез 5. Дата погашения',10,'TABLE_GROUP5',0,0),(336,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','ExchangeRate','Погашения. Разрез 5. Курс на дату погашения',11,'TABLE_GROUP5',0,0),(337,'TEXT',0,0,0,0,NULL,31,'payment_type',0,'LOAN_PAYMENT','paymentType','Погашения. Разрез 5. Вид погашения',12,'TABLE_GROUP5',0,0),(338,'TEXT',0,0,1,7,NULL,0,'Информация по графикам',0,'CONSTANT','','Графики. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(339,'TEXT',0,0,1,7,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Графики. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(340,'TEXT',0,0,1,1,NULL,0,'Кол-во суб-тов.',0,'CONSTANT','','Графики. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(341,'TEXT',0,0,2,2,NULL,0,'Кол-во кред-тов',0,'CONSTANT','','Графики. Шапка таблицы. Кол-во кредитов',2,'TABLE_HEADER',5,6),(342,'TEXT',0,0,3,3,NULL,0,'Кол-во погашений',0,'CONSTANT','','Графики. Шапка таблицы. Кол-во графиков',3,'TABLE_HEADER',5,6),(343,'TEXT',0,0,4,4,NULL,0,'Наименование',0,'CONSTANT','','Графики. Шапка таблицы. Наименование',4,'TABLE_HEADER',5,6),(344,'TEXT',0,0,5,5,NULL,0,'Дата',0,'CONSTANT','','Графики. Шапка таблицы. Дата',5,'TABLE_HEADER',5,6),(345,'TEXT',0,0,6,6,NULL,0,'Всего освоено',0,'CONSTANT','','Графики. Шапка таблицы. Всего освоено',6,'TABLE_HEADER',5,6),(346,'TEXT',0,0,7,7,NULL,0,'Всего по графику',0,'CONSTANT','','Графики. Шапка таблицы. Всего по графику',7,'TABLE_HEADER',5,6),(347,'TEXT',0,0,8,11,NULL,0,'в том числе',0,'CONSTANT','','Графики. Шапка таблицы. Всего по графику в том числе',8,'TABLE_HEADER',5,5),(348,'TEXT',0,0,8,8,NULL,0,'по основной сумме',0,'CONSTANT','','Графики. Шапка таблицы. По основной сумме',9,'TABLE_HEADER',6,6),(349,'TEXT',0,0,9,9,NULL,0,'по процентам',0,'CONSTANT','','Графики. Шапка таблицы. По процентам',10,'TABLE_HEADER',6,6),(350,'TEXT',0,0,10,10,NULL,0,'по нак. процентам',0,'CONSTANT','','Графики. Шапка таблицы. По нак. процентам',11,'TABLE_HEADER',6,6),(351,'TEXT',0,0,11,11,NULL,0,'по нак. штрафам',0,'CONSTANT','','Графики. Шапка таблицы. По нак. штрафам',12,'TABLE_HEADER',6,6),(352,'TEXT',0,0,12,12,NULL,0,'Статус',0,'CONSTANT','','Графики. Шапка таблицы. Статус',13,'TABLE_HEADER',5,6),(353,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Графики. Итого. Кол-во заемщиков',1,'TABLE_SUM',0,0),(354,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Графики. Итого. Кол-во кредитов',2,'TABLE_SUM',0,0),(355,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Графики. Итого. Кол-во графиков',3,'TABLE_SUM',0,0),(356,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Графики. Итого. Итого',4,'TABLE_SUM',0,0),(357,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Disbursement','Графики. Итого. Всего освоено',6,'TABLE_SUM',0,0),(358,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','TotalPayment','Графики. Итого. Всего по графику',7,'TABLE_SUM',0,0),(359,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Principal_payment','Графики. Итого. по осн.суме',8,'TABLE_SUM',0,0),(360,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Interest_payment','Графики. Итого. по процентам',9,'TABLE_SUM',0,0),(361,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_interest_payment','Графики. Итого. по нак. процентам',10,'TABLE_SUM',0,0),(362,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_penalty_payment','Графики. Итого. по нак. штрафам',11,'TABLE_SUM',0,0),(363,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Графики. Разрез1. Кол-во заемщиков',1,'TABLE_GROUP1',0,0),(364,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Графики. Разрез1. Кол-во кредитов',2,'TABLE_GROUP1',0,0),(365,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Графики. Разрез1. Кол-во графиков',3,'TABLE_GROUP1',0,0),(366,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Графики. Разрез1. Наименование',4,'TABLE_GROUP1',0,0),(367,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Disbursement','Графики. Разрез1. Всего освоено',6,'TABLE_GROUP1',0,0),(368,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','TotalPayment','Графики. Разрез1. Всего по графику',7,'TABLE_GROUP1',0,0),(369,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Principal_payment','Графики. Разрез1. по осн.суме',8,'TABLE_GROUP1',0,0),(370,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Interest_payment','Графики. Разрез1. по процентам',9,'TABLE_GROUP1',0,0),(371,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_interest_payment','Графики. Разрез1. по нак. процентам',10,'TABLE_GROUP1',0,0),(372,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_penalty_payment','Графики. Разрез1. по нак. штрафам',11,'TABLE_GROUP1',0,0),(373,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Графики. Разрез2. Кол-во заемщиков',1,'TABLE_GROUP2',0,0),(374,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Графики. Разрез2. Кол-во кредитов',2,'TABLE_GROUP2',0,0),(375,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Графики. Разрез2. Кол-во графиков',3,'TABLE_GROUP2',0,0),(376,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Графики. Разрез2. Наименование',4,'TABLE_GROUP2',0,0),(377,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Disbursement','Графики. Разрез2. Всего освоено',6,'TABLE_GROUP2',0,0),(378,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','TotalPayment','Графики. Разрез2. Всего по графику',7,'TABLE_GROUP2',0,0),(379,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Principal_payment','Графики. Разрез2. по осн.суме',8,'TABLE_GROUP2',0,0),(380,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Interest_payment','Графики. Разрез2. по процентам',9,'TABLE_GROUP2',0,0),(381,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_interest_payment','Графики. Разрез2. по нак. процентам',10,'TABLE_GROUP2',0,0),(382,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_penalty_payment','Графики. Разрез2. по нак. штрафам',11,'TABLE_GROUP2',0,0),(383,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Графики. Разрез3. Кол-во заемщиков',1,'TABLE_GROUP3',0,0),(384,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Графики. Разрез3. Кол-во кредитов',2,'TABLE_GROUP3',0,0),(385,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Графики. Разрез3. Кол-во графиков',3,'TABLE_GROUP3',0,0),(386,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Графики. Разрез3. Наименование',4,'TABLE_GROUP3',0,0),(387,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Disbursement','Графики. Разрез3. Всего освоено',6,'TABLE_GROUP3',0,0),(388,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','TotalPayment','Графики. Разрез3. Всего по графику',7,'TABLE_GROUP3',0,0),(389,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Principal_payment','Графики. Разрез3. по осн.суме',8,'TABLE_GROUP3',0,0),(390,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Interest_payment','Графики. Разрез3. по процентам',9,'TABLE_GROUP3',0,0),(391,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_interest_payment','Графики. Разрез3. по нак. процентам',10,'TABLE_GROUP3',0,0),(392,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_penalty_payment','Графики. Разрез3. по нак. штрафам',11,'TABLE_GROUP3',0,0),(393,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Графики. Разрез4. Кол-во заемщиков',1,'TABLE_GROUP4',0,0),(394,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Графики. Разрез4. Кол-во кредитов',2,'TABLE_GROUP4',0,0),(395,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Графики. Разрез4. Кол-во графиков',3,'TABLE_GROUP4',0,0),(396,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Графики. Разрез4. Наименование',4,'TABLE_GROUP4',0,0),(397,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Disbursement','Графики. Разрез4. Всего освоено',6,'TABLE_GROUP4',0,0),(398,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','TotalPayment','Графики. Разрез4. Всего по графику',7,'TABLE_GROUP4',0,0),(399,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Principal_payment','Графики. Разрез4. по осн.суме',8,'TABLE_GROUP4',0,0),(400,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Interest_payment','Графики. Разрез4. по процентам',9,'TABLE_GROUP4',0,0),(401,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_interest_payment','Графики. Разрез4. по нак. процентам',10,'TABLE_GROUP4',0,0),(402,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_penalty_payment','Графики. Разрез4. по нак. штрафам',11,'TABLE_GROUP4',0,0),(403,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Count','Графики. Разрез5. Кол-во заемщиков',1,'TABLE_GROUP5',0,0),(404,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','DetailsCount','Графики. Разрез5. Кол-во кредитов',2,'TABLE_GROUP5',0,0),(405,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','PaymentCount','Графики. Разрез5. Кол-во графиков',3,'TABLE_GROUP5',0,0),(406,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Name','Графики. Разрез5. Наименование',4,'TABLE_GROUP5',0,0),(407,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Disbursement','Графики. Разрез5. Всего освоено',6,'TABLE_GROUP5',0,0),(408,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','TotalPayment','Графики. Разрез5. Всего по графику',7,'TABLE_GROUP5',0,0),(409,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Principal_payment','Графики. Разрез5. по осн.суме',8,'TABLE_GROUP5',0,0),(410,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Interest_payment','Графики. Разрез5. по процентам',9,'TABLE_GROUP5',0,0),(411,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_interest_payment','Графики. Разрез5. по нак. процентам',10,'TABLE_GROUP5',0,0),(412,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PAYMENT','Collected_penalty_payment','Графики. Разрез5. по нак. штрафам',11,'TABLE_GROUP5',0,0),(413,'DATE',0,0,0,0,NULL,0,'',0,'LOAN_SCHEDULE','ExpectedDate','Графики. Разрез 5. Дата графика',5,'TABLE_GROUP5',0,0),(414,'TEXT',0,0,0,0,NULL,32,'installment_state',0,'LOAN_SCHEDULE','installment_state_id','Графики. Разрез 5. Статус',12,'TABLE_GROUP5',0,0),(415,'DATE',0,0,7,7,NULL,0,'',0,'LOAN_SUMMARY','lastDate','Задолженность. Разрез 4. Срок возврата.',7,'TABLE_GROUP4',0,0),(416,'TEXT',0,0,1,7,NULL,0,'Информация по залогу',0,'CONSTANT','','Предмет залога. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(417,'TEXT',0,0,1,7,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Предмет залога. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(418,'TEXT',0,0,0,0,NULL,4,'loan_type',0,'LOAN_SUMMARY','loanType','Задолженность. Разрез 4. Вид кредита',4,'TABLE_GROUP4',0,0),(419,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_SUMMARY','RemainingDiff','Задолженность. Разрез 4. Курсовая разница',18,'TABLE_GROUP4',0,0),(420,'TEXT',0,0,1,1,NULL,0,'Кол-во суб-тов.',0,'CONSTANT','','Предмет залога. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(421,'TEXT',0,0,2,2,NULL,0,'Кол-во дог.',0,'CONSTANT','','Предмет залога. Шапка таблицы. Кол-во договоров',2,'TABLE_HEADER',5,6),(422,'TEXT',0,0,3,3,NULL,0,'Наименование',0,'CONSTANT','','Предмет залога. Шапка таблицы. Наименование',3,'TABLE_HEADER',5,6),(423,'TEXT',0,0,4,6,NULL,0,'Договор залога',0,'CONSTANT','','Предмет залога. Шапка таблицы. Договор залога',4,'TABLE_HEADER',5,5),(424,'TEXT',0,0,4,4,NULL,0,'Дата',0,'CONSTANT','','Предмет залога. Шапка таблицы. Дата договора',5,'TABLE_HEADER',6,6),(425,'TEXT',0,0,5,5,NULL,0,'Номер',0,'CONSTANT','','Предмет залога. Шапка таблицы. Номер договора',6,'TABLE_HEADER',6,6),(426,'TEXT',0,0,6,6,NULL,0,'Арест',0,'CONSTANT','','Предмет залога. Шапка таблицы. Место наложения ареста',7,'TABLE_HEADER',6,6),(427,'TEXT',0,0,7,10,NULL,0,'Предмет залога',0,'CONSTANT','','Предмет залога. Шапка таблицы. Предмет залога',8,'TABLE_HEADER',5,5),(428,'TEXT',0,0,7,7,NULL,0,'Наименование',0,'CONSTANT','','Предмет залога. Шапка таблицы. Наименование залога',9,'TABLE_HEADER',6,6),(429,'TEXT',0,0,8,8,NULL,0,'Кол-во',0,'CONSTANT','','Предмет залога. Шапка таблицы. Количество залога',10,'TABLE_HEADER',6,6),(430,'TEXT',0,0,9,9,NULL,0,'Вид залога',0,'CONSTANT','','Предмет залога. Шапка таблицы. Вид залога',11,'TABLE_HEADER',6,6),(431,'TEXT',0,0,10,10,NULL,0,'Зал.стоимость',0,'CONSTANT','','Предмет залога. Шапка таблицы. Залоговая стоимость',12,'TABLE_HEADER',6,6),(432,'TEXT',0,0,11,11,NULL,0,'Статус',0,'CONSTANT','','Предмет залога. Шапка таблицы. Статус',13,'TABLE_HEADER',5,6),(433,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Count','Предмет залога. Итого. Кол-во заемщиков',1,'TABLE_SUM',0,0),(434,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','DetailsCount','Предмет залога. Итого. Кол-во договоров',2,'TABLE_SUM',0,0),(435,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Предмет залога. Итого. Итого',3,'TABLE_SUM',0,0),(436,'DOUBLE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralItemCollateralValue','Предмет залога. Итого. Залоговая стоимость',10,'TABLE_SUM',0,0),(437,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Count','Предмет залога. Разрез1. Кол-во заемщиков',1,'TABLE_GROUP1',0,0),(438,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','DetailsCount','Предмет залога. Разрез1. Кол-во договоров',2,'TABLE_GROUP1',0,0),(439,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Name','Предмет залога. Разрез1. Наименование',3,'TABLE_GROUP1',0,0),(440,'DOUBLE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralItemCollateralValue','Предмет залога. Разрез1. Залоговая стоимость',10,'TABLE_GROUP1',0,0),(441,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Count','Предмет залога. Разрез2. Кол-во заемщиков',1,'TABLE_GROUP2',0,0),(442,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','DetailsCount','Предмет залога. Разрез2. Кол-во договоров',2,'TABLE_GROUP2',0,0),(443,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Name','Предмет залога. Разрез2. Наименование',3,'TABLE_GROUP2',0,0),(444,'DOUBLE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralItemCollateralValue','Предмет залога. Разрез2. Залоговая стоимость',10,'TABLE_GROUP2',0,0),(445,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Count','Предмет залога. Разрез3. Кол-во заемщиков',1,'TABLE_GROUP3',0,0),(446,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','DetailsCount','Предмет залога. Разрез3. Кол-во договоров',2,'TABLE_GROUP3',0,0),(447,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Name','Предмет залога. Разрез3. Наименование',3,'TABLE_GROUP3',0,0),(448,'DOUBLE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralItemCollateralValue','Предмет залога. Разрез3. Залоговая стоимость',10,'TABLE_GROUP3',0,0),(449,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Count','Предмет залога. Разрез4. Кол-во заемщиков',1,'TABLE_GROUP4',0,0),(450,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','DetailsCount','Предмет залога. Разрез4. Кол-во договоров',2,'TABLE_GROUP4',0,0),(451,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Name','Предмет залога. Разрез4. Наименование',3,'TABLE_GROUP4',0,0),(452,'DOUBLE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralItemCollateralValue','Предмет залога. Разрез4. Залоговая стоимость',10,'TABLE_GROUP4',0,0),(453,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Count','Предмет залога. Разрез4. Кол-во заемщиков',1,'TABLE_GROUP4',0,0),(454,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','DetailsCount','Предмет залога. Разрез4. Кол-во договоров',2,'TABLE_GROUP4',0,0),(455,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','Name','Предмет залога. Разрез4. Наименование',3,'TABLE_GROUP4',0,0),(456,'DOUBLE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralItemCollateralValue','Предмет залога. Разрез4. Залоговая стоимость',10,'TABLE_GROUP4',0,0),(457,'DATE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralAgreementDate','Предмет залога. Разрез4. Дата договора',4,'TABLE_GROUP4',0,0),(458,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralAgreementNumber','Предмет залога. Разрез4. Номер договора',5,'TABLE_GROUP4',0,0),(459,'DOUBLE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralItemCollateralValue','Предмет залога. Разрез5. Залоговая стоимость',10,'TABLE_GROUP5',0,0),(460,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralItemName','Предмет залога. Разрез5. Наименование залога',7,'TABLE_GROUP5',0,0),(461,'DOUBLE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ITEM','CollateralItemQuantity','Предмет залога. Разрез5. Количество залога',8,'TABLE_GROUP5',0,0),(462,'TEXT',0,0,0,0,NULL,33,'item_type',0,'COLLATERAL_ITEM','CollateralItemTypeId','Предмет залога. Разрез5. Вид залога',9,'TABLE_GROUP5',0,0),(463,'TEXT',0,0,1,6,NULL,0,'Информация по актам обследования',0,'CONSTANT','','Акт обследования. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(464,'TEXT',0,0,1,6,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Акт обследования. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(465,'TEXT',0,0,1,1,NULL,0,'Кол-во суб-тов.',0,'CONSTANT','','Акт обследования. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(466,'TEXT',0,0,2,2,NULL,0,'Кол-во а.об.',0,'CONSTANT','','Акт обследования. Шапка таблицы. Кол-во актов обследования',2,'TABLE_HEADER',5,6),(467,'TEXT',0,0,3,3,NULL,0,'Наименование',0,'CONSTANT','','Акт обследования. Шапка таблицы. Наименование',3,'TABLE_HEADER',5,6),(468,'TEXT',0,0,4,6,NULL,0,'Акт обследования',0,'CONSTANT','','Акт обследования. Шапка таблицы. Договор залога',4,'TABLE_HEADER',5,5),(469,'TEXT',0,0,4,4,NULL,0,'Дата',0,'CONSTANT','','Акт обследования. Шапка таблицы. Дата',5,'TABLE_HEADER',6,6),(470,'TEXT',0,0,5,5,NULL,0,'Результат',0,'CONSTANT','','Акт обследования. Шапка таблицы. Результат',6,'TABLE_HEADER',6,6),(471,'TEXT',0,0,6,6,NULL,0,'Примечание',0,'CONSTANT','','Акт обследования. Шапка таблицы. Примечание',7,'TABLE_HEADER',6,6),(472,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Count','Акт обследования. Итого. Кол-во заемщиков',1,'TABLE_SUM',0,0),(473,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','InspectionCount','Акт обследования. Итого. Кол-во актов обследования',2,'TABLE_SUM',0,0),(474,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Акт обследования. Итого. Итого',3,'TABLE_SUM',0,0),(475,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Count','Акт обследования. Разрез1. Кол-во заемщиков',1,'TABLE_GROUP1',0,0),(476,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','InspectionCount','Акт обследования. Разрез1. Кол-во актов обследования',2,'TABLE_GROUP1',0,0),(477,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Name','Акт обследования. Разрез1. Наименование',3,'TABLE_GROUP1',0,0),(478,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Count','Акт обследования. Разрез2. Кол-во заемщиков',1,'TABLE_GROUP2',0,0),(479,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','InspectionCount','Акт обследования. Разрез2. Кол-во актов обследования',2,'TABLE_GROUP2',0,0),(480,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Name','Акт обследования. Разрез2. Наименование',3,'TABLE_GROUP2',0,0),(481,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Count','Акт обследования. Разрез3. Кол-во заемщиков',1,'TABLE_GROUP3',0,0),(482,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','InspectionCount','Акт обследования. Разрез3. Кол-во актов обследования',2,'TABLE_GROUP3',0,0),(483,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Name','Акт обследования. Разрез3. Наименование',3,'TABLE_GROUP3',0,0),(484,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Count','Акт обследования. Разрез4. Кол-во заемщиков',1,'TABLE_GROUP4',0,0),(485,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','InspectionCount','Акт обследования. Разрез4. Кол-во актов обследования',2,'TABLE_GROUP4',0,0),(486,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Name','Акт обследования. Разрез4. Наименование',3,'TABLE_GROUP4',0,0),(487,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Count','Акт обследования. Разрез5. Кол-во заемщиков',1,'TABLE_GROUP5',0,0),(488,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','InspectionCount','Акт обследования. Разрез5. Кол-во актов обследования',2,'TABLE_GROUP5',0,0),(489,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','Name','Акт обследования. Разрез5. Наименование',3,'TABLE_GROUP5',0,0),(490,'DATE',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','CollateralInspectionOnDate','Акт обследования. Разрез6. Дата обследования',4,'TABLE_GROUP6',0,0),(491,'TEXT',0,0,0,0,NULL,35,'inspection_result_type',0,'COLLATERAL_INSPECTION','CollateralInspectionResultTypeId','Акт обследования. Разрез6. Результат обследования',5,'TABLE_GROUP6',0,0),(492,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_INSPECTION','CollateralInspectionDetails','Акт обследования. Разрез6. Примечание',6,'TABLE_GROUP6',0,0),(493,'TEXT',0,0,1,6,NULL,0,'Информация по снятию с ареста',0,'CONSTANT','','Снятие с ареста. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(494,'TEXT',0,0,1,6,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Снятие с ареста. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(495,'TEXT',0,0,1,6,NULL,0,'Информация по снятию с ареста',0,'CONSTANT','','Снятие с ареста. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(496,'TEXT',0,0,1,6,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Снятие с ареста. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(497,'TEXT',0,0,1,1,NULL,0,'Кол-во суб-тов.',0,'CONSTANT','','Снятие с ареста. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(498,'TEXT',0,0,2,2,NULL,0,'Кол-во сн.',0,'CONSTANT','','Снятие с ареста. Шапка таблицы. Кол-во снятий с ареста',2,'TABLE_HEADER',5,6),(499,'TEXT',0,0,3,3,NULL,0,'Наименование',0,'CONSTANT','','Снятие с ареста. Шапка таблицы. Наименование',3,'TABLE_HEADER',5,6),(500,'TEXT',0,0,4,6,NULL,0,'Снятие с ареста',0,'CONSTANT','','Снятие с ареста. Шапка таблицы. Договор залога',4,'TABLE_HEADER',5,5),(501,'TEXT',0,0,4,4,NULL,0,'Дата',0,'CONSTANT','','Снятие с ареста. Шапка таблицы. Дата',5,'TABLE_HEADER',6,6),(502,'TEXT',0,0,5,5,NULL,0,'Снято',0,'CONSTANT','','Снятие с ареста. Шапка таблицы. Снято',6,'TABLE_HEADER',6,6),(503,'TEXT',0,0,6,6,NULL,0,'Примечание',0,'CONSTANT','','Снятие с ареста. Шапка таблицы. Примечание',7,'TABLE_HEADER',6,6),(504,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Count','Снятие с ареста. Итого. Кол-во заемщиков',1,'TABLE_SUM',0,0),(505,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','ArrestFreeCount','Снятие с ареста. Итого. Кол-во снятий с ареста',2,'TABLE_SUM',0,0),(506,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Снятие с ареста. Итого. Итого',3,'TABLE_SUM',0,0),(507,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Count','Снятие с ареста. Разрез1. Кол-во заемщиков',1,'TABLE_GROUP1',0,0),(508,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','ArrestFreeCount','Снятие с ареста. Разрез1. Кол-во снятий с ареста',2,'TABLE_GROUP1',0,0),(509,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Name','Снятие с ареста. Разрез1. Наименование',3,'TABLE_GROUP1',0,0),(510,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Count','Снятие с ареста. Разрез2. Кол-во заемщиков',1,'TABLE_GROUP2',0,0),(511,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','ArrestFreeCount','Снятие с ареста. Разрез2. Кол-во снятий с ареста',2,'TABLE_GROUP2',0,0),(512,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Name','Снятие с ареста. Разрез2. Наименование',3,'TABLE_GROUP2',0,0),(513,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Count','Снятие с ареста. Разрез3. Кол-во заемщиков',1,'TABLE_GROUP3',0,0),(514,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','ArrestFreeCount','Снятие с ареста. Разрез3. Кол-во снятий с ареста',2,'TABLE_GROUP3',0,0),(515,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Name','Снятие с ареста. Разрез3. Наименование',3,'TABLE_GROUP3',0,0),(516,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Count','Снятие с ареста. Разрез4. Кол-во заемщиков',1,'TABLE_GROUP4',0,0),(517,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','ArrestFreeCount','Снятие с ареста. Разрез4. Кол-во снятий с ареста',2,'TABLE_GROUP4',0,0),(518,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Name','Снятие с ареста. Разрез4. Наименование',3,'TABLE_GROUP4',0,0),(519,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Count','Снятие с ареста. Разрез5. Кол-во заемщиков',1,'TABLE_GROUP5',0,0),(520,'INTEGER',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','ArrestFreeCount','Снятие с ареста. Разрез5. Кол-во снятий с ареста',2,'TABLE_GROUP5',0,0),(521,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','Name','Снятие с ареста. Разрез5. Наименование',3,'TABLE_GROUP5',0,0),(522,'DATE',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','CollateralArrestFreeOnDate','Снятие с ареста. Разрез6. Дата снятия',4,'TABLE_GROUP6',0,0),(523,'TEXT',0,0,0,0,NULL,5,'supervisor',0,'COLLATERAL_ARREST_FREE','CollateralArrestFreeBy','Снятие с ареста. Разрез6. Снято',5,'TABLE_GROUP6',0,0),(524,'TEXT',0,0,0,0,NULL,0,'',0,'COLLATERAL_ARREST_FREE','CollateralArrestFreeDetails','Снятие с ареста. Разрез6. Примечание',6,'TABLE_GROUP6',0,0),(525,'TEXT',0,0,1,6,NULL,0,'Информация по планоым показателям',0,'CONSTANT','','План. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(526,'TEXT',0,0,1,6,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','План. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(527,'TEXT',0,0,1,1,NULL,0,'Кол-во суб-тов.',0,'CONSTANT','','План. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(528,'TEXT',0,0,2,2,NULL,0,'Кол-во пл.',0,'CONSTANT','','План. Шапка таблицы. Кол-во плановых показателей',2,'TABLE_HEADER',5,6),(529,'TEXT',0,0,3,3,NULL,0,'Наименование',0,'CONSTANT','','План. Шапка таблицы. Наименование',3,'TABLE_HEADER',5,6),(530,'TEXT',0,0,4,9,NULL,0,'План',0,'CONSTANT','','План. Шапка таблицы. План',4,'TABLE_HEADER',5,5),(531,'TEXT',0,0,4,4,NULL,0,'Дата',0,'CONSTANT','','План. Шапка таблицы. Дата',5,'TABLE_HEADER',6,6),(532,'TEXT',0,0,5,5,NULL,0,'Всего',0,'CONSTANT','','План. Шапка таблицы. Всего',6,'TABLE_HEADER',6,6),(533,'TEXT',0,0,6,6,NULL,0,'Осн.с.',0,'CONSTANT','','План. Шапка таблицы. Осн.с.',7,'TABLE_HEADER',6,6),(534,'TEXT',0,0,7,7,NULL,0,'Проценты',0,'CONSTANT','','План. Шапка таблицы. Проценты',8,'TABLE_HEADER',6,6),(535,'TEXT',0,0,8,8,NULL,0,'Штрафы',0,'CONSTANT','','План. Шапка таблицы. Штрафы',9,'TABLE_HEADER',6,6),(536,'TEXT',0,0,9,9,NULL,0,'Примечание',0,'CONSTANT','','План. Шапка таблицы. Примечание',10,'TABLE_HEADER',6,6),(537,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','Count','План. Итого. Кол-во заемщиков',1,'TABLE_SUM',0,0),(538,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_Count','План. Итого. Кол-во снятий с ареста',2,'TABLE_SUM',0,0),(539,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','План. Итого. Итого',3,'TABLE_SUM',0,0),(540,'DATE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_date','План. Итого. Дата',4,'TABLE_SUM',0,0),(541,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_amount','План. Итого. Всего план',5,'TABLE_SUM',0,0),(542,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_principal','План. Итого. По осн.с.',6,'TABLE_SUM',0,0),(543,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_interest','План. Итого. По процентам',7,'TABLE_SUM',0,0),(544,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_penalty','План. Итого. по штрафам',8,'TABLE_SUM',0,0),(545,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_description','План. Итого. Примечание',9,'TABLE_SUM',0,0),(546,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','Count','План. Разрез1. Кол-во заемщиков',1,'TABLE_GROUP1',0,0),(547,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_Count','План. Разрез1. Кол-во снятий с ареста',2,'TABLE_GROUP1',0,0),(548,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','name','План. Разрез1. Наименование',3,'TABLE_GROUP1',0,0),(549,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_amount','План. Разрез1. Всего план',5,'TABLE_GROUP1',0,0),(550,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_principal','План. Разрез1. По осн.с.',6,'TABLE_GROUP1',0,0),(551,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_interest','План. Разрез1. По процентам',7,'TABLE_GROUP1',0,0),(552,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_penalty','План. Разрез1. по штрафам',8,'TABLE_GROUP1',0,0),(553,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','Count','План. Разрез2. Кол-во заемщиков',1,'TABLE_GROUP2',0,0),(554,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_Count','План. Разрез2. Кол-во снятий с ареста',2,'TABLE_GROUP2',0,0),(555,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','name','План. Разрез2. Наименование',3,'TABLE_GROUP2',0,0),(556,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_amount','План. Разрез2. Всего план',5,'TABLE_GROUP2',0,0),(557,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_principal','План. Разрез2. По осн.с.',6,'TABLE_GROUP2',0,0),(558,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_interest','План. Разрез2. По процентам',7,'TABLE_GROUP2',0,0),(559,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_penalty','План. Разрез2. по штрафам',8,'TABLE_GROUP2',0,0),(560,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','Count','План. Разрез3. Кол-во заемщиков',1,'TABLE_GROUP3',0,0),(561,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_Count','План. Разрез3. Кол-во снятий с ареста',2,'TABLE_GROUP3',0,0),(562,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','name','План. Разрез3. Наименование',3,'TABLE_GROUP3',0,0),(563,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_amount','План. Разрез3. Всего план',5,'TABLE_GROUP3',0,0),(564,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_principal','План. Разрез3. По осн.с.',6,'TABLE_GROUP3',0,0),(565,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_interest','План. Разрез3. По процентам',7,'TABLE_GROUP3',0,0),(566,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_penalty','План. Разрез3. по штрафам',8,'TABLE_GROUP3',0,0),(567,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','Count','План. Разрез4. Кол-во заемщиков',1,'TABLE_GROUP4',0,0),(568,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_Count','План. Разрез4. Кол-во снятий с ареста',2,'TABLE_GROUP4',0,0),(569,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','name','План. Разрез4. Наименование',3,'TABLE_GROUP4',0,0),(570,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_amount','План. Разрез4. Всего план',5,'TABLE_GROUP4',0,0),(571,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_principal','План. Разрез4. По осн.с.',6,'TABLE_GROUP4',0,0),(572,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_interest','План. Разрез4. По процентам',7,'TABLE_GROUP4',0,0),(573,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_penalty','План. Разрез4. по штрафам',8,'TABLE_GROUP4',0,0),(574,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','Count','План. Разрез5. Кол-во заемщиков',1,'TABLE_GROUP5',0,0),(575,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_Count','План. Разрез5. Кол-во снятий с ареста',2,'TABLE_GROUP5',0,0),(576,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','name','План. Разрез5. Наименование',3,'TABLE_GROUP5',0,0),(577,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_amount','План. Разрез5. Всего план',5,'TABLE_GROUP5',0,0),(578,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_principal','План. Разрез5. По осн.с.',6,'TABLE_GROUP5',0,0),(579,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_interest','План. Разрез5. По процентам',7,'TABLE_GROUP5',0,0),(580,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_penalty','План. Разрез5. по штрафам',8,'TABLE_GROUP5',0,0),(581,'DATE',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_date','План. Разрез5. Дата',4,'TABLE_GROUP5',0,0),(582,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_PLAN','sp_description','План. Разрез5. Примечание',9,'TABLE_GROUP5',0,0),(583,'TEXT',0,0,1,6,NULL,0,'Информация по списанию',0,'CONSTANT','','Списание. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(584,'TEXT',0,0,1,6,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Списание. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(585,'TEXT',0,0,1,1,NULL,0,'Кол-во суб-тов.',0,'CONSTANT','','Списание. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(586,'TEXT',0,0,2,2,NULL,0,'Кол-во сп.',0,'CONSTANT','','Списание. Шапка таблицы. Кол-во списаний',2,'TABLE_HEADER',5,6),(587,'TEXT',0,0,3,3,NULL,0,'Наименование',0,'CONSTANT','','Списание. Шапка таблицы. Наименование',3,'TABLE_HEADER',5,6),(588,'TEXT',0,0,4,9,NULL,0,'Списание',0,'CONSTANT','','Списание. Шапка таблицы. Списание',4,'TABLE_HEADER',5,5),(589,'TEXT',0,0,4,4,NULL,0,'Дата',0,'CONSTANT','','Списание. Шапка таблицы. Дата',5,'TABLE_HEADER',6,6),(590,'TEXT',0,0,5,5,NULL,0,'Всего',0,'CONSTANT','','Списание. Шапка таблицы. Всего',6,'TABLE_HEADER',6,6),(591,'TEXT',0,0,6,6,NULL,0,'Осн.с.',0,'CONSTANT','','Списание. Шапка таблицы. Осн.с.',7,'TABLE_HEADER',6,6),(592,'TEXT',0,0,7,7,NULL,0,'Проценты',0,'CONSTANT','','Списание. Шапка таблицы. Проценты',8,'TABLE_HEADER',6,6),(593,'TEXT',0,0,8,8,NULL,0,'Штрафы',0,'CONSTANT','','Списание. Шапка таблицы. Штрафы',9,'TABLE_HEADER',6,6),(594,'TEXT',0,0,9,9,NULL,0,'Примечание',0,'CONSTANT','','Списание. Шапка таблицы. Примечание',10,'TABLE_HEADER',6,6),(595,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','Count','Списание. Итого. Кол-во заемщиков',1,'TABLE_SUM',0,0),(596,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','writeOffCount','Списание. Итого. Кол-во списаний',2,'TABLE_SUM',0,0),(597,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Списание. Итого. Итого',3,'TABLE_SUM',0,0),(598,'DATE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_date','Списание. Итого. Дата',4,'TABLE_SUM',0,0),(599,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_totalAmount','Списание. Итого. Всего списано',5,'TABLE_SUM',0,0),(600,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_principal','Списание. Итого. По осн.с.',6,'TABLE_SUM',0,0),(601,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_interest','Списание. Итого. По процентам',7,'TABLE_SUM',0,0),(602,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_penalty','Списание. Итого. по штрафам',8,'TABLE_SUM',0,0),(603,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_description','Списание. Итого. Примечание',9,'TABLE_SUM',0,0),(604,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','Count','Списание. Разрез1. Кол-во заемщиков',1,'TABLE_GROUP1',0,0),(605,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','writeOffCount','Списание. Разрез1. Кол-во списаний',2,'TABLE_GROUP1',0,0),(606,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','name','Списание. Разрез1. Наименование',3,'TABLE_GROUP1',0,0),(607,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_totalAmount','Списание. Разрез1. Всего списано',5,'TABLE_GROUP1',0,0),(608,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_principal','Списание. Разрез1. По осн.с.',6,'TABLE_GROUP1',0,0),(609,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_interest','Списание. Разрез1. По процентам',7,'TABLE_GROUP1',0,0),(610,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_penalty','Списание. Разрез1. по штрафам',8,'TABLE_GROUP1',0,0),(611,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','Count','Списание. Разрез2. Кол-во заемщиков',1,'TABLE_GROUP2',0,0),(612,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','writeOffCount','Списание. Разрез2. Кол-во списаний',2,'TABLE_GROUP2',0,0),(613,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','name','Списание. Разрез2. Наименование',3,'TABLE_GROUP2',0,0),(614,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_totalAmount','Списание. Разрез2. Всего списано',5,'TABLE_GROUP2',0,0),(615,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_principal','Списание. Разрез2. По осн.с.',6,'TABLE_GROUP2',0,0),(616,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_interest','Списание. Разрез2. По процентам',7,'TABLE_GROUP2',0,0),(617,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_penalty','Списание. Разрез2. по штрафам',8,'TABLE_GROUP2',0,0),(618,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','Count','Списание. Разрез3. Кол-во заемщиков',1,'TABLE_GROUP3',0,0),(619,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','writeOffCount','Списание. Разрез3. Кол-во списаний',2,'TABLE_GROUP3',0,0),(620,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','name','Списание. Разрез3. Наименование',3,'TABLE_GROUP3',0,0),(621,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_totalAmount','Списание. Разрез3. Всего списано',5,'TABLE_GROUP3',0,0),(622,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_principal','Списание. Разрез3. По осн.с.',6,'TABLE_GROUP3',0,0),(623,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_interest','Списание. Разрез3. По процентам',7,'TABLE_GROUP3',0,0),(624,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_penalty','Списание. Разрез3. по штрафам',8,'TABLE_GROUP3',0,0),(625,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','Count','Списание. Разрез4. Кол-во заемщиков',1,'TABLE_GROUP4',0,0),(626,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','writeOffCount','Списание. Разрез4. Кол-во списаний',2,'TABLE_GROUP4',0,0),(627,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','name','Списание. Разрез4. Наименование',3,'TABLE_GROUP4',0,0),(628,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_totalAmount','Списание. Разрез4. Всего списано',5,'TABLE_GROUP4',0,0),(629,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_principal','Списание. Разрез4. По осн.с.',6,'TABLE_GROUP4',0,0),(630,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_interest','Списание. Разрез4. По процентам',7,'TABLE_GROUP4',0,0),(631,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_penalty','Списание. Разрез4. по штрафам',8,'TABLE_GROUP4',0,0),(632,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','Count','Списание. Разрез5. Кол-во заемщиков',1,'TABLE_GROUP5',0,0),(633,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','writeOffCount','Списание. Разрез5. Кол-во списаний',2,'TABLE_GROUP5',0,0),(634,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','name','Списание. Разрез5. Наименование',3,'TABLE_GROUP5',0,0),(635,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_totalAmount','Списание. Разрез5. Всего списано',5,'TABLE_GROUP5',0,0),(636,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_principal','Списание. Разрез5. По осн.с.',6,'TABLE_GROUP5',0,0),(637,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_interest','Списание. Разрез5. По процентам',7,'TABLE_GROUP5',0,0),(638,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_penalty','Списание. Разрез5. по штрафам',8,'TABLE_GROUP5',0,0),(639,'DATE',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_date','Списание. Разрез5. Дата',4,'TABLE_GROUP5',0,0),(640,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_WRITE_OFF','wo_description','Списание. Разрез5. Примечание',9,'TABLE_GROUP5',0,0),(641,'TEXT',0,0,1,6,NULL,0,'Информация по переводу долга',0,'CONSTANT','','Перевод долга. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(642,'TEXT',0,0,1,6,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Перевод долга. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(643,'TEXT',0,0,1,1,NULL,0,'Кол-во суб-тов.',0,'CONSTANT','','Перевод долга. Шапка таблицы. Кол-во субъектов',1,'TABLE_HEADER',5,6),(644,'TEXT',0,0,2,2,NULL,0,'Кол-во п.',0,'CONSTANT','','Перевод долга. Шапка таблицы. Кол-во переводов долга',2,'TABLE_HEADER',5,6),(645,'TEXT',0,0,3,3,NULL,0,'Наименование',0,'CONSTANT','','Перевод долга. Шапка таблицы. Наименование',3,'TABLE_HEADER',5,6),(646,'TEXT',0,0,4,8,NULL,0,'Перевод долга',0,'CONSTANT','','Перевод долга. Шапка таблицы. Перевод долга',4,'TABLE_HEADER',5,5),(647,'TEXT',0,0,4,4,NULL,0,'Дата',0,'CONSTANT','','Перевод долга. Шапка таблицы. Дата',5,'TABLE_HEADER',6,6),(648,'TEXT',0,0,5,5,NULL,0,'Номер',0,'CONSTANT','','Перевод долга. Шапка таблицы. Номер',6,'TABLE_HEADER',6,6),(649,'TEXT',0,0,6,6,NULL,0,'Сумма',0,'CONSTANT','','Перевод долга. Шапка таблицы. Сумма',7,'TABLE_HEADER',6,6),(650,'TEXT',0,0,7,7,NULL,0,'Цена',0,'CONSTANT','','Перевод долга. Шапка таблицы. Цена',8,'TABLE_HEADER',6,6),(651,'TEXT',0,0,8,8,NULL,0,'Кол-во',0,'CONSTANT','','Перевод долга. Шапка таблицы. Кол-во',9,'TABLE_HEADER',6,6),(652,'TEXT',0,0,9,9,NULL,0,'Вид товара',0,'CONSTANT','','Перевод долга. Шапка таблицы. Вид товара',10,'TABLE_HEADER',6,6),(653,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','Count','Перевод долга. Итого. Кол-во заемщиков',1,'TABLE_SUM',0,0),(654,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_Count','Перевод долга. Итого. Кол-во списаний',2,'TABLE_SUM',0,0),(655,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Перевод долга. Итого. Итого',3,'TABLE_SUM',0,0),(656,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_totalCost','Перевод долга. Итого. Сумма перевода долга',6,'TABLE_SUM',0,0),(657,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','Count','Перевод долга. Разрез1. Кол-во заемщиков',1,'TABLE_GROUP1',0,0),(658,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_Count','Перевод долга. Разрез1. Кол-во списаний',2,'TABLE_GROUP1',0,0),(659,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','name','Перевод долга. Разрез1. Наименование',3,'TABLE_GROUP1',0,0),(660,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_totalCost','Перевод долга. Разрез1. Сумма перевода долга',6,'TABLE_GROUP1',0,0),(661,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','Count','Перевод долга. Разрез2. Кол-во заемщиков',1,'TABLE_GROUP2',0,0),(662,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_Count','Перевод долга. Разрез2. Кол-во списаний',2,'TABLE_GROUP2',0,0),(663,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','name','Перевод долга. Разрез2. Наименование',3,'TABLE_GROUP2',0,0),(664,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_totalCost','Перевод долга. Разрез2. Сумма перевода долга',6,'TABLE_GROUP2',0,0),(665,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','Count','Перевод долга. Разрез3. Кол-во заемщиков',1,'TABLE_GROUP3',0,0),(666,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_Count','Перевод долга. Разрез3. Кол-во списаний',2,'TABLE_GROUP3',0,0),(667,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','name','Перевод долга. Разрез3. Наименование',3,'TABLE_GROUP3',0,0),(668,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_totalCost','Перевод долга. Разрез3. Сумма перевода долга',6,'TABLE_GROUP3',0,0),(669,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','Count','Перевод долга. Разрез4. Кол-во заемщиков',1,'TABLE_GROUP4',0,0),(670,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_Count','Перевод долга. Разрез4. Кол-во списаний',2,'TABLE_GROUP4',0,0),(671,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','name','Перевод долга. Разрез4. Наименование',3,'TABLE_GROUP4',0,0),(672,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_totalCost','Перевод долга. Разрез4. Сумма перевода долга',6,'TABLE_GROUP4',0,0),(673,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','Count','Перевод долга. Разрез5. Кол-во заемщиков',1,'TABLE_GROUP5',0,0),(674,'INTEGER',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_Count','Перевод долга. Разрез5. Кол-во списаний',2,'TABLE_GROUP5',0,0),(675,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','name','Перевод долга. Разрез5. Наименование',3,'TABLE_GROUP5',0,0),(676,'DATE',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_date','Перевод долга. Разрез5. Дата',4,'TABLE_GROUP5',0,0),(677,'TEXT',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_number','Перевод долга. Разрез5. Номер',5,'TABLE_GROUP5',0,0),(678,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_totalCost','Перевод долга. Разрез5. Сумма перевода долга',6,'TABLE_GROUP5',0,0),(679,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_pricePerUnit','Перевод долга. Разрез5. Сумма перевода долга',7,'TABLE_GROUP5',0,0),(680,'DOUBLE',0,0,0,0,NULL,0,'',0,'LOAN_DEBT_TRANSFER','dt_quantity','Перевод долга. Разрез5. Сумма перевода долга',8,'TABLE_GROUP5',0,0),(681,'TEXT',0,0,0,0,NULL,40,'goods_type',0,'LOAN_DEBT_TRANSFER','dt_goodsTypeId','Перевод долга. Разрез5. Вид товара',9,'TABLE_GROUP5',0,0),(682,'TEXT',0,0,1,6,NULL,0,'Информация по сотрудникам',0,'CONSTANT','','Событие сотрудника. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(683,'TEXT',0,0,1,6,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Событие сотрудника. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(684,'TEXT',0,0,1,1,NULL,0,'Кол-во сот.',0,'CONSTANT','','Событие сотрудника. Шапка таблицы. Кол-во сотрудников',1,'TABLE_HEADER',5,6),(685,'TEXT',0,0,2,2,NULL,0,'Кол-во соб.',0,'CONSTANT','','Событие сотрудника. Шапка таблицы. Кол-во событий',2,'TABLE_HEADER',5,6),(686,'TEXT',0,0,3,3,NULL,0,'Наименование',0,'CONSTANT','','Событие сотрудника. Шапка таблицы. Наименование',3,'TABLE_HEADER',5,6),(687,'TEXT',0,0,4,6,NULL,0,'Событие сотрудника',0,'CONSTANT','','Событие сотрудника. Шапка таблицы. Событие сотрудника',4,'TABLE_HEADER',5,5),(688,'TEXT',0,0,4,4,NULL,0,'Дата',0,'CONSTANT','','Событие сотрудника. Шапка таблицы. Дата',5,'TABLE_HEADER',6,6),(689,'TEXT',0,0,5,5,NULL,0,'Наименование',0,'CONSTANT','','Событие сотрудника. Шапка таблицы. Наименование',6,'TABLE_HEADER',6,6),(690,'TEXT',0,0,6,6,NULL,0,'Вид',0,'CONSTANT','','Событие сотрудника. Шапка таблицы. Вид',7,'TABLE_HEADER',6,6),(691,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','Count','Событие сотрудника. Итого. Кол-во сотрудников',1,'TABLE_SUM',0,0),(692,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','staffEventCount','Событие сотрудника. Итого. Кол-во событий',2,'TABLE_SUM',0,0),(693,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Событие сотрудника. Итого. Итого',3,'TABLE_SUM',0,0),(694,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','Count','Событие сотрудника. Разрез1. Кол-во сотрудников',1,'TABLE_GROUP1',0,0),(695,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','staffEventCount','Событие сотрудника. Разрез1. Кол-во событий',2,'TABLE_GROUP1',0,0),(696,'TEXT',0,0,0,0,NULL,0,'',0,'STAFF','name','Событие сотрудника. Разрез1. Наименование',3,'TABLE_GROUP1',0,0),(697,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','Count','Событие сотрудника. Разрез2. Кол-во сотрудников',1,'TABLE_GROUP2',0,0),(698,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','staffEventCount','Событие сотрудника. Разрез2. Кол-во событий',2,'TABLE_GROUP2',0,0),(699,'TEXT',0,0,0,0,NULL,0,'',0,'STAFF','name','Событие сотрудника. Разрез2. Наименование',3,'TABLE_GROUP2',0,0),(700,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','Count','Событие сотрудника. Разрез3. Кол-во сотрудников',1,'TABLE_GROUP3',0,0),(701,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','staffEventCount','Событие сотрудника. Разрез3. Кол-во событий',2,'TABLE_GROUP3',0,0),(702,'TEXT',0,0,0,0,NULL,0,'',0,'STAFF','name','Событие сотрудника. Разрез3. Наименование',3,'TABLE_GROUP3',0,0),(703,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','Count','Событие сотрудника. Разрез4. Кол-во сотрудников',1,'TABLE_GROUP4',0,0),(704,'INTEGER',0,0,0,0,NULL,0,'',0,'STAFF','staffEventCount','Событие сотрудника. Разрез4. Кол-во событий',2,'TABLE_GROUP4',0,0),(705,'TEXT',0,0,0,0,NULL,0,'',0,'STAFF','name','Событие сотрудника. Разрез4. Наименование',3,'TABLE_GROUP4',0,0),(706,'DATE',0,0,0,0,NULL,0,'',0,'STAFF','staffEventDate','Событие сотрудника. Разрез4. Дата',4,'TABLE_GROUP4',0,0),(707,'TEXT',0,0,0,0,NULL,0,'',0,'STAFF','staffEventName','Событие сотрудника. Разрез4. Наименование',5,'TABLE_GROUP4',0,0),(708,'TEXT',0,0,0,0,NULL,43,'staff_event_type',0,'STAFF','staffEventTypeID','Событие сотрудника. Разрез4. Вид события',6,'TABLE_GROUP4',0,0),(709,'TEXT',0,0,1,6,NULL,0,'Информация по документообороту',0,'CONSTANT','','Документооборот. Заголовок страницы. Строка 1. ',1,'PAGE_TITLE',1,1),(710,'TEXT',0,0,1,6,NULL,0,'по состоянию на (=onDate=)',0,'CONSTANT','','Документооборот. Заголовок страницы. Строка 2.',1,'PAGE_TITLE',2,2),(711,'TEXT',0,0,1,1,NULL,0,'Кол-во док.',0,'CONSTANT','','Документооборот. Шапка таблицы. Кол-во документов',1,'TABLE_HEADER',5,6),(712,'TEXT',0,0,2,2,NULL,0,'Наименование',0,'CONSTANT','','Документооборот. Шапка таблицы. Наименование',2,'TABLE_HEADER',5,6),(713,'TEXT',0,0,3,3,NULL,0,'От кого',0,'CONSTANT','','Документооборот. Шапка таблицы. От кого',3,'TABLE_HEADER',5,6),(714,'TEXT',0,0,4,4,NULL,0,'Кому',0,'CONSTANT','','Документооборот. Шапка таблицы. Кому',4,'TABLE_HEADER',5,6),(715,'TEXT',0,0,5,5,NULL,0,'Тема',0,'CONSTANT','','Документооборот. Шапка таблицы. Тема',5,'TABLE_HEADER',5,6),(716,'TEXT',0,0,6,6,NULL,0,'Статус',0,'CONSTANT','','Документооборот. Шапка таблицы. Статус',6,'TABLE_HEADER',5,6),(717,'TEXT',0,0,7,7,NULL,0,'Срок',0,'CONSTANT','','Документооборот. Шапка таблицы. Срок',7,'TABLE_HEADER',5,6),(718,'INTEGER',0,0,0,0,NULL,0,'',0,'DOCUMENT','DocumentCount','Документооборот. Итого. Кол-во документов',1,'TABLE_SUM',0,0),(719,'TEXT',0,0,0,0,NULL,0,'ИТОГО',0,'CONSTANT','','Документооборот. Итого. Итого',2,'TABLE_SUM',0,0),(720,'INTEGER',0,0,0,0,NULL,0,'',0,'DOCUMENT','DocumentCount','Документооборот. Разрез1. Кол-во документов',1,'TABLE_GROUP1',0,0),(721,'TEXT',0,0,0,0,NULL,0,'',0,'DOCUMENT','name','Документооборот. Разрез1. Наименование',2,'TABLE_GROUP1',0,0),(722,'INTEGER',0,0,0,0,NULL,0,'',0,'DOCUMENT','DocumentCount','Документооборот. Разрез2. Кол-во документов',1,'TABLE_GROUP2',0,0),(723,'TEXT',0,0,0,0,NULL,0,'',0,'DOCUMENT','name','Документооборот. Разрез2. Наименование',2,'TABLE_GROUP2',0,0),(729,'INTEGER',0,0,0,0,NULL,0,'',0,'DOCUMENT','DocumentCount','Документооборот. Разрез3. Кол-во документов',1,'TABLE_GROUP3',0,0),(730,'TEXT',0,0,0,0,NULL,0,'',0,'DOCUMENT','name','Документооборот. Разрез3. Наименование',2,'TABLE_GROUP3',0,0),(731,'TEXT',0,0,0,0,NULL,0,'',0,'DOCUMENT','DocumentSenderResponsibleName','Документооборот. Разрез3. От кого',3,'TABLE_GROUP3',0,0),(732,'TEXT',0,0,0,0,NULL,0,'',0,'DOCUMENT','DocumentReceiverResponsibleName','Документооборот. Разрез3. Кому',4,'TABLE_GROUP3',0,0),(733,'TEXT',0,0,0,0,NULL,0,'',0,'DOCUMENT','DocumentTitle','Документооборот. Разрез3. Тема',5,'TABLE_GROUP3',0,0),(734,'TEXT',0,0,0,0,NULL,48,'doc_status',0,'DOCUMENT','DocumentStateID','Документооборот. Разрез3. Статус документа',6,'TABLE_GROUP3',0,0),(735,'DATE',0,0,0,0,NULL,0,'',0,'DOCUMENT','DocumentDueDate','Документооборот. Разрез3. Срок',7,'TABLE_GROUP3',0,0);
/*!40000 ALTER TABLE `content_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `filter_parameter`
--

LOCK TABLES `filter_parameter` WRITE;
/*!40000 ALTER TABLE `filter_parameter` DISABLE KEYS */;
INSERT INTO `filter_parameter` VALUES (1,'EQUALS','','','OBJECT_LIST','По Баткенской области',2),(2,'EQUALS','','','OBJECT_LIST','По Нарынской области',3),(3,'EQUALS','','','OBJECT_LIST','По Таласской области',4),(4,'EQUALS','','','OBJECT_LIST','По Жалал-Абадской области',5),(5,'EQUALS','','','OBJECT_LIST','По Ошской области',6),(6,'EQUALS','','','OBJECT_LIST','По Чуйской области',7),(7,'EQUALS','','','OBJECT_LIST','По Иссык-Кульской области',8),(8,'EQUALS','','','OBJECT_LIST','По Баткенскому району',9),(9,'EQUALS','','','OBJECT_LIST','По Ляйлякскому району',10),(10,'EQUALS','','','OBJECT_LIST','По Кадамжайскому району',11),(11,'EQUALS','','','OBJECT_LIST','По Алайскому району',12),(12,'EQUALS','','','OBJECT_LIST','По Араванскому району',13),(13,'EQUALS','','','OBJECT_LIST','По Кара-Кульджинскому району',14),(14,'EQUALS','','','OBJECT_LIST','По Кара-Суйскому району',15),(15,'EQUALS','','','OBJECT_LIST','По Ноокатскому району',16),(16,'EQUALS','','','OBJECT_LIST','По Узгенскому району',17),(17,'EQUALS','','','OBJECT_LIST','По Чон-Алайскому району',18),(18,'EQUALS','','','OBJECT_LIST','По городу Ош',19),(19,'EQUALS','','','OBJECT_LIST','По Аксыйскому району',20),(20,'EQUALS','','','OBJECT_LIST','По Ала-Букинскому району',21),(21,'EQUALS','','','OBJECT_LIST','По Базар-Коргонскому району',22),(22,'EQUALS','','','OBJECT_LIST','По Ноокенскому району',23),(23,'EQUALS','','','OBJECT_LIST','По Сузакскому району',24),(24,'EQUALS','','','OBJECT_LIST','По Тогуз-Тороузскому району',25),(25,'EQUALS','','','OBJECT_LIST','По Токтогульскому району',26),(26,'EQUALS','','','OBJECT_LIST','По Чаткальскому району',27),(27,'EQUALS','','','OBJECT_LIST','По Чаткальскому району',27),(28,'EQUALS','','','OBJECT_LIST','По городу Жалал-Абад',28),(29,'EQUALS','','','OBJECT_LIST','По Аксуйскому району',29),(30,'EQUALS','','','OBJECT_LIST','По Джети-Огузскому району',30),(31,'EQUALS','','','OBJECT_LIST','По Иссык-Кульскому району',31),(32,'EQUALS','','','OBJECT_LIST','По Тонскому району',32),(33,'EQUALS','','','OBJECT_LIST','По Тюпскому району',33),(34,'EQUALS','','','OBJECT_LIST','По Бакай-Атинскому району',34),(35,'EQUALS','','','OBJECT_LIST','По Кара-Бууринскому району',35),(36,'EQUALS','','','OBJECT_LIST','По Манасскому району',36),(37,'EQUALS','','','OBJECT_LIST','По Таласскому району',37),(38,'EQUALS','','','OBJECT_LIST','По Аламудунскому району',38),(39,'EQUALS','','','OBJECT_LIST','По Кеминскому району',39),(40,'EQUALS','','','OBJECT_LIST','По Московскому району',40),(41,'EQUALS','','','OBJECT_LIST','По Панфиловскому району',41),(42,'EQUALS','','','OBJECT_LIST','По Сокулукскому району',42),(43,'EQUALS','','','OBJECT_LIST','По Чуйскому району',43),(44,'EQUALS','','','OBJECT_LIST','По Ыссык-Атинскому району',44),(45,'EQUALS','','','OBJECT_LIST','По Жайылскому району',45),(46,'EQUALS','','','OBJECT_LIST','По Акталинскому району',46),(47,'EQUALS','','','OBJECT_LIST','По Атбашинскому району',47),(48,'EQUALS','','','OBJECT_LIST','По Джумгальскому району',48),(49,'EQUALS','','','OBJECT_LIST','По Кочкорскому району',49),(50,'EQUALS','','','OBJECT_LIST','По Нарынскому району',50),(51,'EQUALS','','','OBJECT_LIST','По городу Бишкек',51),(52,'EQUALS','','','OBJECT_LIST','По АПК',53),(53,'EQUALS','','','OBJECT_LIST','По отраслям промышленности',54),(54,'EQUALS','','','OBJECT_LIST','По бюджетным ссудам и ИК',56),(55,'EQUALS','','','OBJECT_LIST','По грантам АПК',57),(56,'EQUALS','','','OBJECT_LIST','По им. активам',59),(57,'EQUALS','','','OBJECT_LIST','По энергетическому комплексу',60),(58,'EQUALS','','','OBJECT_LIST','По строительному комплексу',61),(59,'EQUALS','','','OBJECT_LIST','по легкой промышленности',62),(60,'EQUALS','','','OBJECT_LIST','по пищевой и перераб. пром.',63),(61,'EQUALS','','','OBJECT_LIST','по банкам',64),(62,'EQUALS','','','OBJECT_LIST','по угледоб. пром.',65),(63,'EQUALS','','','OBJECT_LIST','по здравоохранению',66),(64,'EQUALS','','','OBJECT_LIST','по машиностроению',67),(65,'EQUALS','','','OBJECT_LIST','по транспорту и связи',68),(66,'EQUALS','','','OBJECT_LIST','По прочим отраслям',69),(67,'EQUALS','','','OBJECT_LIST','по водным хоз.',70),(68,'EQUALS','','','OBJECT_LIST','по част. предринимательству',71),(69,'EQUALS','','','OBJECT_LIST','по ЖСК',72),(70,'EQUALS','','','OBJECT_LIST','Чуйская область (оформление)',80),(71,'EQUALS','','','OBJECT_LIST','Таласская область (оформление)',79),(72,'EQUALS','','','OBJECT_LIST','Ошская область (оформление)',78),(73,'EQUALS','','','OBJECT_LIST','Нарынская область (оформление)',77),(74,'EQUALS','','','OBJECT_LIST','Иссык-Кульская область (оформление)',76),(75,'EQUALS','','','OBJECT_LIST','Джалал-Абадская область (оформление)',75),(76,'EQUALS','','','OBJECT_LIST','г.Бишкек (оформление)',74),(77,'EQUALS','','','OBJECT_LIST','Баткенская область (оформление)',73),(78,'AFTER_OR_ON_DATE','01.01.2018','v_sp_date','CONTENT_COMPARE','План за 2018 год',9),(79,'AFTER_OR_ON_DATE','01.01.2017','v_sp_date','CONTENT_COMPARE','План за 2017 год',1),(80,'BEFORE_OR_ON_DATE','01.01.2018','v_sp_date','CONTENT_COMPARE','План за 2017 год',1);
/*!40000 ALTER TABLE `filter_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `group_type`
--

LOCK TABLES `group_type` WRITE;
/*!40000 ALTER TABLE `group_type` DISABLE KEYS */;
INSERT INTO `group_type` VALUES (1,'v_debtor_region_id','Область','(=regionMap(v_debtor_region_id)=)'),(2,'v_debtor_district_id','Район','(=districtMap(v_debtor_district_id)=)'),(3,'v_debtor_work_sector_id','Отрасль','(=work_sectorMap(v_debtor_work_sector_id)=)'),(4,'v_loan_type_id','Вид кредита','(=loan_typeMap(v_loan_type_id)=)'),(5,'v_loan_supervisor_id','Куратор','(=supervisorMap(v_loan_supervisor_id)=)'),(6,'v_loan_department_id','Отдел','(=departmentMap(v_loan_department_id)=)'),(7,'v_debtor_id','Заемщик','(=v_debtor_name=)'),(8,'v_loan_id','Кредит','(=v_loan_reg_number=) от (=v_loan_reg_date=)'),(9,'v_ls_id','Расчет','Расчет'),(10,'v_payment_id','Погашение','Погашение'),(11,'v_ps_id','График','График'),(12,'v_credit_order_id','Решение о выдаче кредита','(=credit_orderMap(v_credit_order_id)=)'),(13,'v_applied_entity_id','Получатель','(=v_owner_name=)'),(14,'v_ael_id','Список получателей','(=v_ael_listNumber=)'),(15,'v_document_package_id','Пакет документации','(=v_document_package_name=)'),(16,'v_entity_document_id','Документ (оформление)','(=v_entity_document_name=)'),(17,'v_ca_id','Договор залога','(=v_ca_agreementNumber=)'),(18,'v_ci_id','Предмет залога','(=v_ci_name=)'),(19,'v_cp_id','Процедура взыскания','Процедура взыскания'),(20,'v_cph_id','Фаза взыскания',''),(21,'v_applied_entity_appliedEntityStateId','Статус получателя','(=applied_entity_statusMap(v_applied_entity_appliedEntityStateId)=)'),(22,'v_entity_document_entityDocumentStateId','Статус документа(оформление)','(=entity_document_statusMap(v_entity_document_entityDocumentStateId)=)'),(23,'v_document_package_documentPackageStateId','Статус пакета док.','(=document_package_statusMap(v_document_package_documentPackageStateId)=)'),(24,'v_entity_document_completedBy','Укомплектовано','(=supervisorMap(v_entity_document_completedBy)=)'),(25,'v_entity_document_approvedBy','Проверено','(=supervisorMap(v_entity_document_approvedBy)=)'),(26,'v_owner_region_id','Область (получатель)','(=regionMap(v_owner_region_id)=)'),(27,'v_owner_district_id','Район (получатель)','(=districtMap(v_owner_district_id)=)'),(28,'v_cph_phaseTypeId','Вид стадии','(=collection_phase_typeMap(v_cph_phaseTypeId)=)'),(29,'v_cph_phaseStatusId','Вид результата','(=collection_phase_statusMap(v_cph_phaseStatusId)=)'),(30,'v_cp_procedureStatusId','Статус процедуры','(=collection_procedure_statusMap(v_cp_procedureStatusId)=)'),(31,'v_payment_type_id','Вид погашения','(=payment_typeMap(v_payment_type_id)=)'),(32,'v_installment_status_id','Статус графика','(=installment_statusMap(v_installment_status_id)=)'),(33,'v_ci_itemTypeId','Вид залога','(=item_typeMap(v_ci_itemTypeId)=)'),(34,'v_cir_id','Акт обследования','Акт обследования от (=v_cir_onDate=)'),(35,'v_cir_inspectionResultTypeId','Результат обследования','(=inspection_result_typeMap(v_cir_inspectionResultTypeId)=)'),(36,'v_ciaf_id','Снятие с ареста','снятие с ареста от (=v_ciaf_onDate=)'),(37,'v_sp_id','План','План'),(38,'v_wo_id','Списание','Списание'),(39,'v_dt_id','Перевод долга','Перевод долга'),(40,'dt_goodsTypeId','Вид товара','(=goods_typeMap(dt_goodsTypeId)=)'),(41,'v_s_id','Сотрудник','(=staffMap(v_s_id)=)'),(42,'v_se_id','Событие сотрудника','Событие сотрудника'),(43,'v_se_type','Вид события сотрудника','(=staff_event_typeMap(v_se_type_id)=)'),(44,'v_s_position_id','Должность сотрудника','(=staff_positionMap(v_se_position_id)=)'),(45,'v_s_department_id','Отдел сотрудника','(=departmentMap(v_s_department_id)=)'),(46,'v_doc_documentType','Вид документа','(=doc_typeMap(v_doc_documentType)=)'),(47,'v_doc_documentSubType','Тип документа','(=doc_subTypeMap(v_doc_documentSubType)=)'),(48,'v_doc_documentState','Статус документа','(=doc_stateMap(v_doc_documentState)=)'),(49,'v_doc_id','Документ','(=doc_subTypeMap(v_doc_documentSubType)=) от (=v_doc_senderRegisteredDate=)');
/*!40000 ALTER TABLE `group_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `object_list`
--

LOCK TABLES `object_list` WRITE;
/*!40000 ALTER TABLE `object_list` DISABLE KEYS */;
INSERT INTO `object_list` VALUES (1,'По республике ( все области)',1,1),(2,'По Баткенской области',1,1),(3,'По Нарынской области',1,1),(4,'По Таласской области',1,1),(5,'По Джалал-Абадской области',1,1),(6,'По Ошской области',1,1),(7,'По Чуйской области',1,1),(8,'По Иссык-Кульской области',1,1),(9,'По Баткенскому району',2,2),(10,'По Ляйлякскому району',2,2),(11,'По Кадамжайскому району',2,2),(12,'По Алайскому району',2,2),(13,'По Араванскому району',2,2),(14,'По Кара-Кульджинскому району',2,2),(15,'По Кара-Суйскому району',2,2),(16,'По Ноокатскому району',2,2),(17,'По Узгенскому району',2,2),(18,'По Чон-Алайскому району',2,2),(19,'По городу Ош',2,2),(20,'По Аксыйскому району',2,2),(21,'По Алабукинскому району',2,2),(22,'По Базар-Коргонскому району',2,2),(23,'По Ноокенскому району',2,2),(24,'По Сузакскому району',2,2),(25,'По Тогуз-Тороузскому району',2,2),(26,'По Токтогульскому району',2,2),(27,'По Чаткальскому району',2,2),(28,'По городу Жалал-Абад',2,2),(29,'По Аксуйскому району',2,2),(30,'По Джетиогузскому району',2,2),(31,'По Иссык-Кульскому району',2,2),(32,'По Тонскому району',2,2),(33,'По Тюпскому району',2,2),(34,'По Бакай-Атинскому району',2,2),(35,'По Кара-Бууринскому району',2,2),(36,'По Манасскому району',2,2),(37,'По Таласскому району',2,2),(38,'По Аламудунскому району',2,2),(39,'По Кеминскому району',2,2),(40,'По Московскому району',2,2),(41,'По Панфиловскому району',2,2),(42,'По Сокулукскому району',2,2),(43,'По Чуйскому району',2,2),(44,'По Ыссык-Атинскому району',2,2),(45,'По Жайылскому району',2,2),(46,'По Акталинскому району',2,2),(47,'По Ат-Башынскому району',2,2),(48,'По Джумгальскому району',2,2),(49,'По Кочкорскому району',2,2),(50,'По Нарынскому району',2,2),(51,'По городу Бишкек',2,2),(52,'По всем отраслям',3,3),(53,'По АПК',3,3),(54,'По отраслям промышленности',3,3),(55,'По всем видам кредита',4,4),(56,'По бюджетным ссудам и иностранным кредитам',4,4),(57,'По грантам',4,4),(58,'По ГМР',4,4),(59,'По им. активам',4,4),(60,'По энергетическому комплексу',3,3),(61,'По строительному комплексу',3,3),(62,'По легкой промышленности',3,3),(63,'По пищевой и пераб. промыiленности',3,3),(64,'По банкам',3,3),(65,'По Угледоб. отрасли',3,3),(66,'По здравоохранению',3,3),(67,'По Машиностроению',3,3),(68,'По транспорту и связи',3,3),(69,'По предприятиям прочих отраслей',3,3),(70,'По водному хозяйству',3,3),(71,'По частному предпринимательству',3,3),(72,'по ЖСК',3,3),(73,'Баткенская область (оформление)',26,26),(74,'г. Бишкек (оформление)',26,26),(75,'Джалал-Абадская область (оформление)',26,26),(76,'Иссык-Кульская область (оформление)',26,26),(77,'Нарынская область (оформление)',26,26),(78,'Ошская область (оформление)',26,26),(79,'Таласская область (оформление)',26,26),(80,'Чуйская область (оформление)',26,26);
/*!40000 ALTER TABLE `object_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `object_list_value`
--

LOCK TABLES `object_list_value` WRITE;
/*!40000 ALTER TABLE `object_list_value` DISABLE KEYS */;
INSERT INTO `object_list_value` VALUES (1,'8',1),(2,'3',1),(3,'6',1),(4,'1',1),(5,'9',1),(6,'4',1),(7,'2',1),(8,'5',1),(9,'1',2),(10,'5',3),(11,'8',4),(12,'3',5),(13,'6',6),(15,'4',8),(18,'9',7),(19,'7',7),(20,'2',7),(23,'32',11),(26,'5',12),(27,'7',13),(29,'34',14),(31,'43',16),(32,'58',17),(33,'60',18),(34,'23',19),(35,'18',15),(36,'35',15),(37,'2',20),(38,'4',21),(39,'9',22),(42,'54',25),(44,'59',27),(45,'14',28),(46,'17',26),(47,'55',26),(48,'19',24),(49,'52',24),(50,'21',23),(51,'26',23),(52,'44',23),(53,'24',10),(54,'39',10),(55,'1',29),(56,'28',30),(57,'12',31),(58,'31',31),(59,'56',32),(60,'57',33),(61,'10',34),(62,'33',35),(63,'40',36),(64,'25',37),(65,'53',37),(66,'6',38),(67,'36',39),(68,'41',40),(69,'46',41),(70,'51',42),(71,'61',43),(73,'30',45),(74,'3',46),(75,'8',47),(76,'29',48),(77,'37',49),(79,'22',50),(80,'42',50),(81,'62',44),(82,'64',44),(83,'13',51),(84,'38',51),(85,'45',51),(86,'48',51),(87,'50',51),(88,'5',52),(89,'1',52),(90,'2',52),(91,'14',52),(92,'3',52),(93,'4',52),(94,'6',52),(95,'7',52),(96,'8',52),(97,'9',52),(98,'10',52),(99,'13',52),(100,'11',52),(101,'12',52),(102,'1',53),(103,'3',54),(104,'2',54),(105,'4',54),(106,'5',54),(107,'6',54),(108,'7',54),(109,'8',54),(110,'9',54),(111,'13',54),(112,'14',54),(113,'10',54),(114,'11',54),(115,'12',54),(116,'1',55),(117,'2',55),(118,'3',55),(119,'4',55),(120,'5',55),(121,'6',55),(122,'7',55),(123,'8',55),(124,'9',55),(127,'3',57),(128,'6',57),(129,'7',57),(130,'8',57),(131,'9',58),(132,'5',59),(133,'1',56),(134,'2',56),(135,'9',56),(136,'2',60),(137,'3',61),(138,'4',62),(140,'5',63),(141,'6',64),(142,'7',65),(143,'8',66),(144,'9',67),(145,'10',68),(146,'11',69),(147,'12',70),(148,'13',71),(149,'14',72),(150,'1',73),(151,'2',74),(152,'3',75),(153,'4',76),(154,'5',77),(155,'6',78),(156,'8',79),(157,'9',80),(158,'11',9);
/*!40000 ALTER TABLE `object_list_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `output_parameter`
--

LOCK TABLES `output_parameter` WRITE;
/*!40000 ALTER TABLE `output_parameter` DISABLE KEYS */;
INSERT INTO `output_parameter` VALUES (1,'Альбомный вид','PAGE_ORIENTATION',0,'',1),(2,'Книжный вид','PAGE_ORIENTATION',0,'',0),(3,'Высота нижнего поля','SHEET_BOTTOM_MARGIN',0,'',0),(4,'Высота верхнего поля','SHEET_TOP_MARGIN',0,'',0.1),(5,'Ширина правого поля','SHEET_RIGHT_MARGIN',0,'',0.1),(6,'Ширина левого поля','SHEET_LEFT_MARGIN',0,'',0.1),(7,'Показывать разрыв страниц','SHEET_AUTOBREAKS',0,'',1),(9,'Разместить на кол-во стр. в ширину','SHEET_FIT_WIDTH',0,'',1),(10,'Разместить на кол-во стр. в высоту','SHEET_FIT_HEIGHT',0,'',0),(11,'Поле верхнего колонтитула','SHEET_HEADER_MARGIN',0,'',0),(12,'Поле нижнего колонтитула','SHEET_FOOTER_MARGIN',0,'',0),(13,'Нижний колонтитул (кол-во страниц)','SHEET_FOOTER_TEXT',0,'Страница page() из numPages()',0),(14,'Верхний колонтитул (оперативные данные)','SHEET_HEADER_TEXT',0,'Оперативные данные',0),(15,'Ширина всех столбцов','DEFAULT_COLUMN_WIDTH',0,'',12),(16,'ширина 1. столбца','COLUMN_WIDTH',1,'',6),(17,'ширина 2. столбца','COLUMN_WIDTH',2,'',40),(18,'Заголовок. шрифт. жирный.','FONT_BOLD',10,'',1),(19,'Заголовок. шрифт.','CELL_FONT',10,'string',10),(20,'Заголовок. шрифт. размер. ','FONT_HEIGHT',10,'',12),(21,'Заголовок. шрифт. цвет.','FONT_COLOR',10,'',12),(22,'Шапка таблицы. шрифт. жирный','FONT_BOLD',11,'',1),(23,'Шапка таблицы. шрифт. нормальный','FONT_BOLD',11,'',0),(24,'Заголовок (текст). Выравнивание.','CELL_ALIGNMENT',10,'string',2),(25,'Заголовок (тескт). рамки.','CELL_BORDER',10,'string',0),(26,'Заголовок (тескт). Верктикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',10,'string',1),(27,'Шапка. шрифт. размер','FONT_HEIGHT',11,'',8),(28,'Шапка. шрифт. цвет.','FONT_COLOR',11,'',0),(29,'Шапка. шрифт.','CELL_FONT',11,'string',11),(30,'Итоговые данные. шрифт. жирный.','FONT_BOLD',12,'',1),(31,'Итоговые данные. шрифт. размер.','FONT_HEIGHT',12,'',9),(32,'Итоговые данные. шрифт. цвет.','FONT_COLOR',12,'',10),(33,'Итоговые данные. шрифт.','CELL_FONT',12,'string',12),(34,'Итоговые данные (текст). выравнивание.','CELL_ALIGNMENT',12,'string',2),(35,'Итоговые данные (текст). рамки.','CELL_BORDER',12,'string',1),(36,'Итоговые данные (текст). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',12,'string',1),(40,'Итоговые данные (целое число). шрифт.','CELL_FONT',12,'int',12),(41,'Итоговые данные (целое число). выравнивание.','CELL_ALIGNMENT',12,'int',2),(42,'Итоговые данные (целое число). рамки.','CELL_BORDER',12,'int',1),(43,'Итоговые данные (целое число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',12,'int',1),(44,'Итоговые данные (целое число). Формат.','CELL_DATA_FORMAT',12,'#,#0',3),(45,'Итоговые данные (целое число). цвет заливки.','CELL_FOREGROUND_COLOR',12,'int',49),(46,'Итоговые данные (целое число). Вид заливки.','CELL_PATTERN',12,'int',1),(50,'Итоговые данные (дробное число). шрифт.','CELL_FONT',12,'double',12),(51,'Итоговые данные (дробное число). выравнивание.','CELL_ALIGNMENT',12,'double',2),(52,'Итоговые данные (дробное число). рамки.','CELL_BORDER',12,'double',1),(53,'Итоговые данные (дробное число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',12,'double',1),(54,'Итоговые данные (дробное число). Формат.','CELL_DATA_FORMAT',12,'#,#0.00',4),(55,'Итоговые данные (дробное число). цвет заливки.','CELL_FOREGROUND_COLOR',12,'double',49),(56,'Итоговые данные (дробное число). Вид заливки.','CELL_PATTERN',12,'double',1),(57,'Разрез №1. шрифт. жирный.','FONT_BOLD',1,'',1),(58,'Разрез №1. шрифт. размер.','FONT_HEIGHT',1,'',8),(59,'Разрез №1. шрифт. цвет.','FONT_COLOR',1,'',0),(60,'Разрез №1 (текcт). шрифт.','CELL_FONT',1,'string',1),(61,'Разрез №1 (текcт). выравнивание.','CELL_ALIGNMENT',1,'string',2),(62,'Разрез №1 (текcт). рамки.','CELL_BORDER',1,'string',1),(63,'Разрез №1 (текcт). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',1,'string',1),(64,'Разрез №1 (текcт). цвет заливки.','CELL_FOREGROUND_COLOR',1,'string',44),(65,'Разрез №1 (текcт). Вид заливки.','CELL_PATTERN',1,'string',1),(66,'Разрез №1 (дата). шрифт.','CELL_FONT',1,'date',1),(67,'Разрез №1 (дата). выравнивание.','CELL_ALIGNMENT',1,'date',2),(68,'Разрез №1 (дата). рамки.','CELL_BORDER',1,'date',1),(69,'Разрез №1 (дата). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',1,'date',1),(70,'Разрез №1 (дата). Формат.','CELL_DATA_FORMAT',1,'dd.mm.yyyy',2),(71,'Разрез №1 (дата). цвет заливки.','CELL_FOREGROUND_COLOR',1,'date',44),(72,'Разрез №1 (дата). Вид заливки.','CELL_PATTERN',1,'date',1),(73,'Разрез №1 (целое число). шрифт.','CELL_FONT',1,'int',1),(74,'Разрез №1 (целое число). выравнивание.','CELL_ALIGNMENT',1,'int',3),(75,'Разрез №1 (целое число). рамки.','CELL_BORDER',1,'int',1),(76,'Разрез №1 (целое число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',1,'int',1),(77,'Разрез №1 (целое число). Формат.','CELL_DATA_FORMAT',1,'#,#0',3),(78,'Разрез №1 (целое число). цвет заливки.','CELL_FOREGROUND_COLOR',1,'int',44),(79,'Разрез №1 (целое число). Вид заливки.','CELL_PATTERN',1,'int',1),(80,'Разрез №1 (дробное число). шрифт.','CELL_FONT',1,'double',1),(81,'Разрез №1 (дробное число). выравнивание. ','CELL_ALIGNMENT',1,'double',3),(82,'Разрез №1 (дробное число). рамки.','CELL_BORDER',1,'double',1),(83,'Разрез №1 (дробное число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',1,'double',1),(84,'Разрез №1 (дробное число). Формат.','CELL_DATA_FORMAT',1,'#,#0.00',4),(85,'Разрез №1 (дробное число). цвет заливки.','CELL_FOREGROUND_COLOR',1,'double',44),(86,'Разрез №1 (дробное число). Вид заливки.','CELL_PATTERN',1,'double',1),(87,'Шапка (текcт). выравнивание.','CELL_ALIGNMENT',11,'string',2),(88,'Шапка (текcт). рамки.','CELL_BORDER',11,'string',1),(89,'Шапка (текcт). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',11,'string',1),(90,'Шапка (текcт). цвет заливки.','CELL_FOREGROUND_COLOR',11,'string',22),(91,'Шапка (текcт). Вид заливки.','CELL_PATTERN',11,'string',1),(92,'Шапка (текcт). Перенос текста.','CELL_WRAP_TEXT',11,'string',1),(93,'Итоговые данные (текст). цвет заливки.','CELL_FOREGROUND_COLOR',12,'string',49),(94,'Итоговые данные (текст). Вид заливки.','CELL_PATTERN',12,'string',1),(95,'Итоговые данные (текст). Перенос текста.','CELL_WRAP_TEXT',12,'string',1),(96,'Итоговые данные (целое число). Перенос текста.','CELL_WRAP_TEXT',12,'int',1),(97,'Итоговые данные (дробное число). Перенос текста.','CELL_WRAP_TEXT',12,'double',1),(98,'Разрез №1 (текcт). Перенос текста.','CELL_WRAP_TEXT',1,'string',1),(99,'Разрез №1 (дата). Перенос текста.','CELL_WRAP_TEXT',1,'date',1),(100,'Разрез №1 (целое число). Перенос текста.','CELL_WRAP_TEXT',1,'int',1),(101,'Разрез №1 (дробное число). Перенос текста.','CELL_WRAP_TEXT',1,'double',1),(102,'Разрез №2. шрифт. жирный.','FONT_BOLD',2,'',1),(103,'Разрез №2. шрифт. размер.','FONT_HEIGHT',2,'',8),(104,'Разрез №2. шрифт. цвет.','FONT_COLOR',2,'',0),(105,'Разрез №2 (текcт). шрифт.','CELL_FONT',2,'string',2),(106,'Разрез №2 (текcт). выравнивание.','CELL_ALIGNMENT',2,'string',2),(107,'Разрез №2 (текcт). рамки.','CELL_BORDER',2,'string',1),(108,'Разрез №2 (текcт). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',2,'string',1),(109,'Разрез №2 (текcт). цвет заливки.','CELL_FOREGROUND_COLOR',2,'string',13),(110,'Разрез №2 (текcт). Вид заливки.','CELL_PATTERN',2,'string',1),(111,'Разрез №2 (текcт). Перенос текста.','CELL_WRAP_TEXT',2,'string',1),(112,'Разрез №2 (дата). шрифт.','CELL_FONT',2,'date',2),(113,'Разрез №2 (дата). выравнивание.','CELL_ALIGNMENT',2,'date',2),(114,'Разрез №2 (дата). рамки.','CELL_BORDER',2,'date',1),(115,'Разрез №2 (дата). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',2,'date',1),(116,'Разрез №2 (дата). Формат.','CELL_DATA_FORMAT',2,'dd.mm.yyyy',2),(117,'Разрез №2 (дата). цвет заливки.','CELL_FOREGROUND_COLOR',2,'date',13),(118,'Разрез №2 (дата). Вид заливки.','CELL_PATTERN',2,'date',1),(119,'Разрез №2 (дата). Перенос текста.','CELL_WRAP_TEXT',2,'date',1),(120,'Разрез №2 (целое число). шрифт.','CELL_FONT',2,'int',2),(121,'Разрез №2 (целое число). выравнивание.','CELL_ALIGNMENT',2,'int',3),(122,'Разрез №2 (целое число). рамки.','CELL_BORDER',2,'int',1),(123,'Разрез №2 (целое число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',2,'int',1),(124,'Разрез №2 (целое число). Формат.','CELL_DATA_FORMAT',2,'#,#0',3),(125,'Разрез №2 (целое число). цвет заливки.','CELL_FOREGROUND_COLOR',2,'int',13),(126,'Разрез №2 (целое число). Вид заливки.','CELL_PATTERN',2,'int',1),(127,'Разрез №2 (целое число). Перенос текста.','CELL_WRAP_TEXT',2,'int',1),(128,'Разрез №2 (дробное число). шрифт.','CELL_FONT',2,'double',2),(129,'Разрез №2 (дробное число). выравнивание.','CELL_ALIGNMENT',2,'double',3),(130,'Разрез №2 (дробное число). рамки.','CELL_BORDER',2,'double',1),(131,'Разрез №2 (дробное число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',2,'double',1),(132,'Разрез №2 (дробное число). Формат.','CELL_DATA_FORMAT',2,'#,#0.00',4),(133,'Разрез №2 (дробное число). цвет заливки.','CELL_FOREGROUND_COLOR',2,'double',13),(134,'Разрез №2 (дробное число). Вид заливки.','CELL_PATTERN',2,'double',1),(135,'Разрез №2 (дробное число). Перенос текста.','CELL_WRAP_TEXT',2,'double',1),(136,'Разрез №3. шрифт. жирный.','FONT_BOLD',3,'',1),(137,'Разрез №3. шрифт. размер.','FONT_HEIGHT',3,'',8),(138,'Разрез №3. шрифт. цвет.','FONT_COLOR',3,'',0),(139,'Разрез №3 (текcт). шрифт.','CELL_FONT',3,'string',3),(140,'Разрез №3 (текcт). выравнивание. ','CELL_ALIGNMENT',3,'string',2),(141,'Разрез №3 (текcт). рамки.','CELL_BORDER',3,'string',1),(142,'Разрез №3 (текcт). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',3,'string',1),(143,'Разрез №3 (текcт). цвет заливки.','CELL_FOREGROUND_COLOR',3,'string',1),(144,'Разрез №3 (текcт). Вид заливки.','CELL_PATTERN',3,'string',1),(145,'Разрез №3 (текcт). Перенос текста.','CELL_WRAP_TEXT',3,'string',1),(146,'Разрез №3 (дата). шрифт.','CELL_FONT',3,'date',3),(147,'Разрез №3 (дата). выравнивание. ','CELL_ALIGNMENT',3,'date',2),(148,'Разрез №3 (дата). рамки.','CELL_BORDER',3,'date',1),(149,'Разрез №3 (дата). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',3,'date',1),(150,'Разрез №3 (дата). Формат.','CELL_DATA_FORMAT',3,'dd.mm.yyyy',2),(151,'Разрез №3 (дата). цвет заливки.','CELL_FOREGROUND_COLOR',3,'date',1),(152,'Разрез №3 (дата). Вид заливки.','CELL_PATTERN',3,'date',1),(153,'Разрез №3 (дата). Перенос текста.','CELL_WRAP_TEXT',3,'date',1),(154,'Разрез №3 (целое число). шрифт.','CELL_FONT',3,'int',3),(155,'Разрез №3 (целое число). выравнивание. ','CELL_ALIGNMENT',3,'int',3),(156,'Разрез №3 (целое число). рамки.','CELL_BORDER',3,'int',1),(157,'Разрез №3 (целое число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',3,'int',1),(158,'Разрез №3 (целое число). Формат.','CELL_DATA_FORMAT',3,'#,#0',3),(159,'Разрез №3 (целое число). цвет заливки.','CELL_FOREGROUND_COLOR',3,'int',1),(160,'Разрез №3 (целое число). Вид заливки.','CELL_PATTERN',3,'int',1),(161,'Разрез №3 (целое число). Перенос текста.','CELL_WRAP_TEXT',3,'int',1),(162,'Разрез №3 (дробное число). шрифт.','CELL_FONT',3,'double',3),(163,'Разрез №3 (дробное число). выравнивание. ','CELL_ALIGNMENT',3,'double',3),(164,'Разрез №3 (дробное число). рамки.','CELL_BORDER',3,'double',1),(165,'Разрез №3 (дробное число). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',3,'double',1),(166,'Разрез №3 (дробное число). Формат.','CELL_DATA_FORMAT',3,'#,#0.00',4),(167,'Разрез №3 (дробное число). цвет заливки.','CELL_FOREGROUND_COLOR',3,'double',1),(168,'Разрез №3 (дробное число). Вид заливки.','CELL_PATTERN',3,'double',1),(169,'Разрез №3 (дробное число). Перенос текста.','CELL_WRAP_TEXT',3,'double',1),(170,'Разрез №4. шрифт. жирный.','FONT_BOLD',4,'',0),(171,'Разрез №4. шрифт. размер.','FONT_HEIGHT',4,'',8),(172,'Разрез №4. шрифт. цвет.','FONT_COLOR',4,'',0),(173,'Разрез №4 (текcт). шрифт.','CELL_FONT',4,'string',4),(174,'Разрез №4 (текcт). выравнивание. ','CELL_ALIGNMENT',4,'string',2),(175,'Разрез №4 (текcт). рамки.','CELL_BORDER',4,'string',1),(176,'Разрез №4 (текcт). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',4,'string',1),(177,'Разрез №4 (текcт). цвет заливки.','CELL_FOREGROUND_COLOR',4,'string',1),(178,'Разрез №4 (текcт). Вид заливки.','CELL_PATTERN',4,'string',1),(179,'Разрез №4 (текcт). Перенос текста.','CELL_WRAP_TEXT',4,'string',1),(180,'Разрез №4 (дата). шрифт.','CELL_FONT',4,'date',4),(181,'Разрез №4 (дата). выравнивание. ','CELL_ALIGNMENT',4,'date',3),(182,'Разрез №4 (дата). рамки.','CELL_BORDER',4,'date',1),(183,'Разрез №4 (дата). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',4,'date',1),(184,'Разрез №4 (дата). Формат.','CELL_DATA_FORMAT',4,'dd.mm.yyyy',2),(185,'Разрез №4 (дата). цвет заливки.','CELL_FOREGROUND_COLOR',4,'date',1),(186,'Разрез №4 (дата). Вид заливки.','CELL_PATTERN',4,'date',1),(187,'Разрез №4 (дата). Перенос текста.','CELL_WRAP_TEXT',4,'date',1),(188,'Разрез №4 (целое число). шрифт.','CELL_FONT',4,'int',4),(189,'Разрез №4 (целое число). выравнивание.','CELL_ALIGNMENT',4,'int',3),(190,'Разрез №4 (целое число). рамки.','CELL_BORDER',4,'int',1),(191,'Разрез №4 (целое число). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',4,'int',1),(192,'Разрез №4 (целое число). Формат.','CELL_DATA_FORMAT',4,'#,#0',3),(193,'Разрез №4 (целое число). цвет заливки.','CELL_FOREGROUND_COLOR',4,'int',1),(194,'Разрез №4 (целое число). Вид заливки.','CELL_PATTERN',4,'int',1),(195,'Разрез №4 (целое число). Перенос текста.','CELL_WRAP_TEXT',4,'int',1),(196,'Разрез №4 (дробное число). шрифт.','CELL_FONT',4,'double',4),(197,'Разрез №4 (дробное число). выравнивание.','CELL_ALIGNMENT',4,'double',3),(198,'Разрез №4 (дробное число). рамки.','CELL_BORDER',4,'double',1),(199,'Разрез №4 (дробное число). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',4,'double',1),(200,'Разрез №4 (дробное число). Формат.','CELL_DATA_FORMAT',4,'#,#0.00',4),(201,'Разрез №4 (дробное число). цвет заливки.','CELL_FOREGROUND_COLOR',4,'double',1),(202,'Разрез №4 (дробное число). Вид заливки.','CELL_PATTERN',4,'double',1),(203,'Разрез №4 (дробное число). Перенос текста.','CELL_WRAP_TEXT',4,'double',1),(204,'Разрез №5. шрифт. жирный.','FONT_BOLD',5,'',0),(205,'Разрез №5. шрифт. размер.','FONT_HEIGHT',5,'',8),(206,'Разрез №5. шрифт. цвет.','FONT_COLOR',5,'',0),(207,'Разрез №5 (текcт). шрифт.','CELL_FONT',5,'string',5),(208,'Разрез №5 (текcт). выравнивание. ','CELL_ALIGNMENT',5,'string',2),(209,'Разрез №5 (текcт). рамки.','CELL_BORDER',5,'string',1),(210,'Разрез №5 (текcт). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',5,'string',1),(211,'Разрез №5 (текcт). цвет заливки.','CELL_FOREGROUND_COLOR',5,'string',1),(212,'Разрез №5 (текcт). Вид заливки.','CELL_PATTERN',5,'string',1),(213,'Разрез №5 (текcт). Перенос текста.','CELL_WRAP_TEXT',5,'string',1),(214,'Разрез №5 (дата). шрифт.','CELL_FONT',5,'date',5),(215,'Разрез №5 (дата). выравнивание. ','CELL_ALIGNMENT',5,'date',3),(216,'Разрез №5 (дата). рамки.','CELL_BORDER',5,'date',1),(217,'Разрез №5 (дата). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',5,'date',1),(218,'Разрез №5 (дата). Формат.','CELL_DATA_FORMAT',5,'dd.mm.yyyy',2),(219,'Разрез №5 (дата). цвет заливки.','CELL_FOREGROUND_COLOR',5,'date',1),(220,'Разрез №5 (дата). Вид заливки.','CELL_PATTERN',5,'date',1),(221,'Разрез №5 (дата). Перенос текста.','CELL_WRAP_TEXT',5,'date',1),(222,'Разрез №5 (целое число). шрифт.','CELL_FONT',5,'int',5),(223,'Разрез №5 (целое число). выравнивание. ','CELL_ALIGNMENT',5,'int',3),(224,'Разрез №5 (целое число). рамки.','CELL_BORDER',5,'int',1),(225,'Разрез №5 (целое число). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',5,'int',1),(226,'Разрез №5 (целое число). Формат.','CELL_DATA_FORMAT',5,'#,#0',3),(227,'Разрез №5 (целое число). цвет заливки.','CELL_FOREGROUND_COLOR',5,'int',1),(228,'Разрез №5 (целое число). Вид заливки.','CELL_PATTERN',5,'int',1),(229,'Разрез №5 (целое число). Перенос текста.','CELL_WRAP_TEXT',5,'int',1),(230,'Разрез №5 (дробное число). шрифт.','CELL_FONT',5,'double',5),(231,'Разрез №5 (дробное число). выравнивание. ','CELL_ALIGNMENT',5,'double',3),(232,'Разрез №5 (дробное число). рамки.','CELL_BORDER',5,'double',1),(233,'Разрез №5 (дробное число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',5,'double',1),(234,'Разрез №5 (дробное число). Формат.','CELL_DATA_FORMAT',5,'#,#0.00',4),(235,'Разрез №5 (дробное число). цвет заливки.','CELL_FOREGROUND_COLOR',5,'double',1),(236,'Разрез №5 (дробное число). Вид заливки.','CELL_PATTERN',5,'double',1),(237,'Разрез №5 (дробное число). Перенос текста.','CELL_WRAP_TEXT',5,'double',1),(238,'Разрез №6. шрифт. жирный.','FONT_BOLD',6,'',0),(239,'Разрез №6. шрифт. размер.','FONT_HEIGHT',6,'',8),(240,'Разрез №6. шрифт. цвет.','FONT_COLOR',6,'',0),(241,'Разрез №6 (текcт). шрифт.','CELL_FONT',6,'string',6),(242,'Разрез №6 (текcт). выравнивание.','CELL_ALIGNMENT',6,'string',2),(243,'Разрез №6 (текcт). рамки.','CELL_BORDER',6,'string',1),(244,'Разрез №6 (текcт). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',6,'string',1),(245,'Разрез №6 (текcт). цвет заливки.','CELL_FOREGROUND_COLOR',6,'string',44),(246,'Разрез №6 (текcт). Вид заливки.','CELL_PATTERN',6,'string',1),(247,'Разрез №6 (текcт). Перенос текста.','CELL_WRAP_TEXT',6,'string',1),(248,'Разрез №6 (дата). шрифт.','CELL_FONT',6,'date',6),(249,'Разрез №6 (дата). выравнивание. ','CELL_ALIGNMENT',6,'date',2),(250,'Разрез №6 (дата). рамки.','CELL_BORDER',6,'date',1),(251,'Разрез №6 (дата). вертикальное выравнивание. ','CELL_VERTICAL_ALIGNMENT',6,'date',1),(252,'Разрез №6 (дата). Формат.','CELL_DATA_FORMAT',6,'dd.mm.yyyy',2),(253,'Разрез №6 (дата). цвет заливки.','CELL_FOREGROUND_COLOR',6,'date',44),(254,'Разрез №6 (дата). Вид заливки.','CELL_PATTERN',6,'date',1),(255,'Разрез №6 (дата). Перенос текста.','CELL_WRAP_TEXT',6,'date',1),(256,'Разрез №6 (целое число). шрифт.','CELL_FONT',6,'int',6),(257,'Разрез №6 (целое число). выравнивание. ','CELL_ALIGNMENT',6,'int',2),(258,'Разрез №6 (целое число). рамки.','CELL_BORDER',6,'int',1),(259,'Разрез №6 (целое число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',6,'int',1),(260,'Разрез №6 (целое число). Формат.','CELL_DATA_FORMAT',6,'#,#0',3),(261,'Разрез №6 (целое число). цвет заливки.','CELL_FOREGROUND_COLOR',6,'int',44),(262,'Разрез №6 (целое число). Вид заливки.','CELL_PATTERN',6,'int',1),(263,'Разрез №6 (целое число). Перенос текста.','CELL_WRAP_TEXT',6,'int',1),(264,'Разрез №6 (дробное число). шрифт.','CELL_FONT',6,'double',6),(265,'Разрез №6 (дробное число). выравнивание. ','CELL_ALIGNMENT',6,'double',2),(266,'Разрез №6 (дробное число). рамки.','CELL_BORDER',6,'double',1),(267,'Разрез №6 (дробное число). вертикальное выравнивание.','CELL_VERTICAL_ALIGNMENT',6,'double',1),(268,'Разрез №6 (дробное число). Формат.','CELL_DATA_FORMAT',6,'#,#0.00',4),(269,'Разрез №6 (дробное число). цвет заливки.','CELL_FOREGROUND_COLOR',6,'double',44),(270,'Разрез №6 (дробное число). Вид заливки.','CELL_PATTERN',6,'double',1),(271,'Разрез №6 (дробное число). Перенос текста.','CELL_WRAP_TEXT',6,'double',1),(272,'Заголовок (текстк). перенос текста','CELL_WRAP_TEXT',10,'string',1),(273,'Высота строки ( Заголовок таблицы)','ROW_HEIGHT',6,'',30),(274,'ширина 2. столбца','COLUMN_WIDTH',2,'',6),(275,'ширина 3. столбца','COLUMN_WIDTH',3,'',40),(276,'ширина 2. столбца (кол-во)','COLUMN_WIDTH',2,'',6),(277,'ширина 3. столбца (кол-во)','COLUMN_WIDTH',3,'',6),(278,'ширина 4. столбца (наименование)','COLUMN_WIDTH',4,'',40),(279,'ширина 7. столбца (наименование залога)','COLUMN_WIDTH',7,'',40);
/*!40000 ALTER TABLE `output_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (1,'Отчет по оформлению','ENTITY_DOCUMENT'),(2,'Отчет по взысканию','COLLECTION_PHASE'),(3,'Отчет по задолженности','LOAN_SUMMARY'),(4,'Отчет по погашениям','LOAN_PAYMENT'),(5,'Отчет по графикам','LOAN_SCHEDULE'),(6,'Отчет по залогу','COLLATERAL_ITEM'),(7,'Отчет по актам обследования','COLLATERAL_INSPECTION'),(8,'Отчет по снятию с ареста','COLLATERAL_ARREST_FREE'),(9,'Отчет по плану','LOAN_PLAN'),(10,'Отчет по списанию','LOAN_WRITE_OFF'),(11,'Отчет по переводу долга','LOAN_DEBT_TRANSFER'),(12,'Отчет по кадрам','STAFF'),(13,'Отчет по документооброту','DOCUMENT');
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_content_parameter`
--

LOCK TABLES `report_content_parameter` WRITE;
/*!40000 ALTER TABLE `report_content_parameter` DISABLE KEYS */;
INSERT INTO `report_content_parameter` VALUES (1,1),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(1,46),(1,47),(1,48),(1,49),(1,50),(1,51),(1,52),(1,53),(1,54),(1,55),(1,56),(1,57),(1,61),(1,62),(1,63),(1,64),(1,65),(1,66),(1,67),(1,68),(1,69),(1,75),(1,81),(1,87),(1,93),(1,99),(1,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,117),(2,118),(2,119),(2,120),(2,121),(2,122),(2,123),(2,124),(2,125),(2,126),(2,127),(2,128),(2,129),(2,130),(2,131),(2,132),(2,133),(2,134),(2,135),(2,136),(2,137),(2,138),(2,139),(2,140),(2,141),(2,142),(2,143),(2,144),(2,145),(2,146),(2,147),(2,148),(2,149),(2,150),(2,151),(2,152),(2,153),(2,154),(2,155),(2,156),(2,157),(3,158),(3,159),(3,160),(3,161),(3,162),(3,163),(3,164),(3,165),(3,166),(3,167),(3,168),(3,169),(3,170),(3,171),(3,172),(3,173),(3,174),(3,175),(3,176),(3,177),(3,178),(3,179),(3,180),(3,181),(3,183),(3,184),(3,185),(3,186),(3,187),(3,188),(3,189),(3,190),(3,191),(3,192),(3,193),(3,194),(3,195),(3,196),(3,197),(3,198),(3,199),(3,200),(3,201),(3,202),(3,203),(3,204),(3,205),(3,206),(3,207),(3,208),(3,209),(3,210),(3,211),(3,212),(3,213),(3,214),(3,215),(3,216),(3,217),(3,218),(3,219),(3,220),(3,221),(3,222),(3,223),(3,224),(3,225),(3,226),(3,227),(3,228),(3,229),(3,230),(3,231),(3,232),(3,233),(3,234),(3,235),(3,236),(3,237),(3,238),(3,239),(3,240),(3,241),(3,242),(3,243),(3,244),(3,245),(3,246),(3,247),(3,248),(3,249),(3,250),(3,251),(3,252),(3,253),(3,254),(3,255),(3,256),(3,257),(3,258),(3,259),(3,260),(3,261),(3,262),(3,263),(4,264),(4,265),(4,266),(4,267),(4,268),(4,269),(4,270),(4,271),(4,272),(4,273),(4,274),(4,275),(4,276),(4,277),(4,278),(4,279),(4,280),(4,281),(4,282),(4,283),(4,284),(4,285),(4,286),(4,287),(4,288),(4,289),(4,290),(4,291),(4,292),(4,293),(4,294),(4,295),(4,296),(4,297),(4,298),(4,299),(4,300),(4,301),(4,302),(4,303),(4,304),(4,305),(4,306),(4,307),(4,308),(4,309),(4,310),(4,311),(4,312),(4,313),(4,314),(4,315),(4,316),(4,317),(4,318),(4,319),(4,320),(4,321),(4,322),(4,323),(4,324),(4,325),(4,326),(4,334),(4,335),(4,336),(4,337),(5,338),(5,339),(5,340),(5,341),(5,342),(5,343),(5,344),(5,345),(5,346),(5,347),(5,348),(5,349),(5,350),(5,351),(5,352),(5,353),(5,354),(5,355),(5,356),(5,357),(5,358),(5,359),(5,360),(5,361),(5,362),(5,363),(5,364),(5,365),(5,366),(5,367),(5,368),(5,369),(5,370),(5,371),(5,372),(5,373),(5,374),(5,375),(5,376),(5,377),(5,378),(5,379),(5,380),(5,381),(5,382),(5,383),(5,384),(5,385),(5,386),(5,387),(5,388),(5,389),(5,390),(5,391),(5,392),(5,393),(5,394),(5,395),(5,396),(5,397),(5,398),(5,399),(5,400),(5,401),(5,402),(5,403),(5,404),(5,405),(5,406),(5,407),(5,408),(5,409),(5,410),(5,411),(5,412),(5,413),(5,414),(3,415),(6,416),(6,417),(3,418),(3,419),(6,420),(6,421),(6,422),(6,423),(6,424),(6,425),(6,426),(6,427),(6,428),(6,429),(6,430),(6,431),(6,432),(6,433),(6,434),(6,435),(6,436),(6,437),(6,438),(6,439),(6,440),(6,441),(6,442),(6,443),(6,444),(6,445),(6,446),(6,447),(6,448),(6,453),(6,454),(6,455),(6,456),(6,457),(6,458),(6,459),(6,460),(6,461),(6,462),(7,463),(7,464),(7,465),(7,466),(7,467),(7,468),(7,469),(7,470),(7,471),(7,472),(7,473),(7,474),(7,475),(7,476),(7,477),(7,478),(7,479),(7,480),(7,481),(7,482),(7,483),(7,484),(7,485),(7,486),(7,487),(7,488),(7,489),(7,490),(7,491),(7,492),(8,493),(8,494),(8,496),(8,497),(8,498),(8,499),(8,500),(8,501),(8,502),(8,503),(8,504),(8,505),(8,506),(8,507),(8,508),(8,509),(8,510),(8,511),(8,512),(8,513),(8,514),(8,515),(8,516),(8,517),(8,518),(8,519),(8,520),(8,521),(8,522),(8,523),(8,524),(9,525),(9,526),(9,527),(9,528),(9,529),(9,530),(9,531),(9,532),(9,533),(9,534),(9,535),(9,536),(9,537),(9,538),(9,539),(9,540),(9,541),(9,542),(9,543),(9,544),(9,545),(9,546),(9,547),(9,548),(9,549),(9,550),(9,551),(9,552),(9,553),(9,554),(9,555),(9,556),(9,557),(9,558),(9,559),(9,560),(9,561),(9,562),(9,563),(9,564),(9,565),(9,566),(9,567),(9,568),(9,569),(9,570),(9,571),(9,572),(9,573),(9,574),(9,575),(9,576),(9,577),(9,578),(9,579),(9,580),(9,581),(9,582),(10,583),(10,584),(10,585),(10,586),(10,587),(10,588),(10,589),(10,590),(10,591),(10,592),(10,593),(10,594),(10,595),(10,596),(10,597),(10,599),(10,600),(10,601),(10,602),(10,604),(10,605),(10,606),(10,607),(10,608),(10,609),(10,610),(10,611),(10,612),(10,613),(10,614),(10,615),(10,616),(10,617),(10,618),(10,619),(10,620),(10,621),(10,622),(10,623),(10,624),(10,625),(10,626),(10,627),(10,628),(10,629),(10,630),(10,631),(10,632),(10,633),(10,634),(10,635),(10,636),(10,637),(10,638),(10,639),(10,640),(11,641),(11,642),(11,643),(11,644),(11,645),(11,646),(11,647),(11,648),(11,649),(11,650),(11,651),(11,652),(11,653),(11,654),(11,655),(11,656),(11,657),(11,658),(11,659),(11,660),(11,661),(11,662),(11,663),(11,664),(11,665),(11,666),(11,667),(11,668),(11,669),(11,670),(11,671),(11,672),(11,673),(11,674),(11,675),(11,676),(11,677),(11,678),(11,679),(11,680),(11,681),(12,682),(12,683),(12,684),(12,685),(12,686),(12,687),(12,688),(12,689),(12,690),(12,691),(12,692),(12,693),(12,694),(12,695),(12,696),(12,697),(12,698),(12,699),(12,700),(12,701),(12,702),(12,703),(12,704),(12,705),(12,706),(12,707),(12,708),(13,709),(13,710),(13,711),(13,712),(13,713),(13,714),(13,715),(13,716),(13,717),(13,718),(13,719),(13,720),(13,721),(13,722),(13,723),(13,729),(13,730),(13,731),(13,732),(13,733),(13,734),(13,735);
/*!40000 ALTER TABLE `report_content_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_filter_parameter`
--

LOCK TABLES `report_filter_parameter` WRITE;
/*!40000 ALTER TABLE `report_filter_parameter` DISABLE KEYS */;
INSERT INTO `report_filter_parameter` VALUES (2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(2,2),(3,2),(2,3),(3,3),(2,4),(3,4),(2,5),(3,5),(2,6),(3,6),(2,7),(3,7),(3,8),(1,70),(1,71),(1,72),(1,73),(1,74),(1,75),(1,76),(1,77),(9,79),(9,80);
/*!40000 ALTER TABLE `report_filter_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_group_type`
--

LOCK TABLES `report_group_type` WRITE;
/*!40000 ALTER TABLE `report_group_type` DISABLE KEYS */;
INSERT INTO `report_group_type` VALUES (2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(2,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(2,4),(3,4),(4,4),(5,4),(3,5),(3,6),(4,6),(5,6),(2,7),(3,7),(4,7),(5,7),(6,7),(7,7),(8,7),(9,7),(10,7),(11,7),(3,8),(4,8),(5,8),(9,8),(10,8),(11,8),(3,9),(4,10),(5,11),(1,12),(1,13),(1,14),(1,15),(1,16),(6,17),(7,17),(8,17),(6,18),(7,18),(8,18),(2,19),(2,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(2,28),(2,29),(2,30),(7,34),(8,36),(9,37),(10,38),(11,39),(12,41),(12,42),(12,43),(12,44),(12,45),(13,46),(13,47),(13,48),(13,49);
/*!40000 ALTER TABLE `report_group_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_output_parameter`
--

LOCK TABLES `report_output_parameter` WRITE;
/*!40000 ALTER TABLE `report_output_parameter` DISABLE KEYS */;
INSERT INTO `report_output_parameter` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(6,2),(8,2),(9,2),(13,2),(1,3),(2,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(9,3),(10,3),(11,3),(12,3),(13,3),(1,4),(2,4),(3,4),(4,4),(5,4),(6,4),(7,4),(8,4),(9,4),(10,4),(11,4),(12,4),(13,4),(2,5),(4,5),(5,5),(6,5),(7,5),(8,5),(9,5),(10,5),(11,5),(12,5),(13,5),(2,6),(4,6),(5,6),(6,6),(7,6),(8,6),(9,6),(10,6),(11,6),(12,6),(13,6),(2,7),(4,7),(5,7),(6,7),(7,7),(8,7),(9,7),(10,7),(12,7),(13,7),(1,9),(2,9),(3,9),(4,9),(5,9),(6,9),(7,9),(8,9),(9,9),(10,9),(11,9),(12,9),(13,9),(7,10),(8,10),(1,11),(2,11),(4,11),(5,11),(11,11),(12,11),(1,12),(2,12),(4,12),(5,12),(11,12),(12,12),(1,13),(3,13),(1,15),(2,15),(3,15),(4,15),(5,15),(6,15),(7,15),(9,15),(10,15),(11,15),(12,15),(13,15),(1,16),(2,16),(3,16),(4,16),(5,16),(6,16),(7,16),(8,16),(9,16),(10,16),(11,16),(12,16),(13,16),(1,17),(2,17),(4,17),(13,17),(2,272),(1,273),(2,273),(3,274),(7,274),(8,274),(9,274),(10,274),(11,274),(12,274),(3,275),(6,275),(7,275),(8,275),(9,275),(10,275),(11,275),(12,275),(4,276),(5,276),(6,276),(4,277),(5,277),(4,278),(5,278),(6,279);
/*!40000 ALTER TABLE `report_output_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_template`
--

LOCK TABLES `report_template` WRITE;
/*!40000 ALTER TABLE `report_template` DISABLE KEYS */;
INSERT INTO `report_template` VALUES (1,NULL,'Общий отчет по оформлению документации','2018-01-01',1,1,0,0,0,0,25,27,12,13,16,NULL,1),(2,NULL,'Общий отчет по взысканию','2018-01-01',1,1,0,0,0,0,1,2,7,19,20,NULL,2),(3,'2017-01-01','Отчет по задолженности (Баткенская область)','2018-01-01',1,1,1,1,1,0,1,2,7,8,9,NULL,3),(4,NULL,'Отчет по погашениям','2018-01-01',1,1,0,0,0,0,1,2,7,8,10,NULL,4),(5,NULL,'Отчет по графикам','2018-01-01',1,1,0,0,0,0,1,2,7,8,11,NULL,5),(6,NULL,'Отчет по залогу','2018-01-01',1,1,1,1,1,0,1,2,7,17,18,NULL,6),(7,NULL,'Отчет по актам обследования','2018-01-01',1,1,1,1,1,1,1,2,7,17,18,34,7),(8,NULL,'Отчет по снятию с ареста','2018-01-01',1,1,1,1,1,1,1,2,7,17,18,36,8),(9,NULL,'Отчет по плану','2018-01-01',1,1,1,1,1,0,1,2,7,8,37,NULL,9),(10,NULL,'Отчет по списанию','2018-01-01',1,1,1,1,1,0,1,2,7,8,38,NULL,10),(11,NULL,'Отчет по переводу долга','2018-01-01',1,1,1,1,1,0,1,2,7,8,39,NULL,11),(12,NULL,'Отчет по кадрам','2018-01-01',1,1,1,1,0,0,45,44,41,42,NULL,NULL,12),(13,NULL,'Отчет по документообороту','2018-01-01',1,1,1,0,0,0,46,47,49,49,NULL,NULL,13);
/*!40000 ALTER TABLE `report_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_template_content_parameter`
--

LOCK TABLES `report_template_content_parameter` WRITE;
/*!40000 ALTER TABLE `report_template_content_parameter` DISABLE KEYS */;
INSERT INTO `report_template_content_parameter` VALUES (1,1),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(1,46),(1,47),(1,48),(1,49),(1,50),(1,51),(1,52),(1,53),(1,54),(1,55),(1,56),(1,57),(1,61),(1,62),(1,63),(1,64),(1,65),(1,66),(1,67),(1,68),(1,69),(1,75),(1,81),(1,87),(1,93),(1,99),(1,105),(2,106),(2,107),(2,108),(2,109),(2,110),(2,111),(2,112),(2,113),(2,114),(2,115),(2,116),(2,117),(2,118),(2,119),(2,120),(2,121),(2,122),(2,123),(2,124),(2,125),(2,126),(2,127),(2,128),(2,129),(2,130),(2,131),(2,132),(2,133),(2,134),(2,135),(2,136),(2,137),(2,138),(2,139),(2,140),(2,141),(2,142),(2,143),(2,144),(2,145),(2,146),(2,147),(2,148),(2,149),(2,150),(2,151),(2,152),(2,153),(2,154),(2,155),(2,156),(2,157),(3,158),(3,159),(3,160),(3,161),(3,162),(3,163),(3,164),(3,165),(3,166),(3,167),(3,168),(3,169),(3,170),(3,171),(3,172),(3,173),(3,174),(3,175),(3,176),(3,177),(3,178),(3,179),(3,180),(3,181),(3,183),(3,184),(3,185),(3,186),(3,187),(3,188),(3,189),(3,190),(3,191),(3,192),(3,193),(3,194),(3,195),(3,196),(3,197),(3,198),(3,199),(3,200),(3,201),(3,202),(3,203),(3,204),(3,205),(3,206),(3,207),(3,208),(3,209),(3,210),(3,211),(3,212),(3,213),(3,214),(3,215),(3,216),(3,217),(3,218),(3,219),(3,220),(3,221),(3,223),(3,224),(3,225),(3,226),(3,227),(3,228),(3,229),(3,230),(3,231),(3,232),(3,233),(3,234),(3,235),(3,238),(3,239),(3,240),(3,241),(3,242),(3,243),(3,244),(3,245),(3,246),(3,247),(3,248),(3,249),(3,252),(3,253),(3,254),(3,255),(3,256),(3,257),(3,258),(3,259),(3,260),(3,261),(3,262),(3,263),(4,264),(4,265),(4,266),(4,267),(4,268),(4,269),(4,270),(4,271),(4,272),(4,273),(4,274),(4,275),(4,276),(4,277),(4,278),(4,279),(4,280),(4,281),(4,282),(4,283),(4,284),(4,285),(4,286),(4,287),(4,288),(4,289),(4,290),(4,291),(4,292),(4,293),(4,294),(4,295),(4,296),(4,297),(4,298),(4,299),(4,300),(4,301),(4,302),(4,303),(4,304),(4,305),(4,306),(4,307),(4,308),(4,309),(4,310),(4,311),(4,312),(4,313),(4,314),(4,315),(4,316),(4,317),(4,318),(4,319),(4,320),(4,321),(4,322),(4,323),(4,324),(4,325),(4,326),(4,334),(4,335),(4,336),(4,337),(5,338),(5,339),(5,340),(5,341),(5,342),(5,343),(5,344),(5,345),(5,346),(5,347),(5,348),(5,349),(5,350),(5,351),(5,352),(5,353),(5,354),(5,355),(5,356),(5,357),(5,358),(5,359),(5,360),(5,361),(5,362),(5,363),(5,364),(5,365),(5,366),(5,367),(5,368),(5,369),(5,370),(5,371),(5,372),(5,373),(5,374),(5,375),(5,376),(5,377),(5,378),(5,379),(5,380),(5,381),(5,382),(5,383),(5,384),(5,385),(5,386),(5,387),(5,388),(5,389),(5,390),(5,391),(5,392),(5,393),(5,394),(5,395),(5,396),(5,397),(5,398),(5,399),(5,400),(5,401),(5,402),(5,403),(5,404),(5,405),(5,406),(5,407),(5,408),(5,409),(5,410),(5,411),(5,412),(5,413),(5,414),(3,415),(6,416),(6,417),(3,418),(6,420),(6,421),(6,422),(6,423),(6,424),(6,425),(6,426),(6,427),(6,428),(6,429),(6,430),(6,431),(6,432),(6,433),(6,434),(6,435),(6,436),(6,437),(6,438),(6,439),(6,440),(6,441),(6,442),(6,443),(6,444),(6,445),(6,446),(6,447),(6,448),(6,453),(6,454),(6,455),(6,456),(6,457),(6,458),(6,459),(6,460),(6,461),(6,462),(7,463),(7,464),(7,465),(7,466),(7,467),(7,468),(7,469),(7,470),(7,471),(7,472),(7,473),(7,474),(7,475),(7,476),(7,477),(7,478),(7,479),(7,480),(7,481),(7,482),(7,483),(7,484),(7,485),(7,486),(7,487),(7,488),(7,489),(7,490),(7,491),(7,492),(8,493),(8,494),(8,497),(8,498),(8,499),(8,500),(8,501),(8,502),(8,503),(8,504),(8,505),(8,506),(8,507),(8,508),(8,509),(8,510),(8,511),(8,512),(8,513),(8,514),(8,515),(8,516),(8,517),(8,518),(8,519),(8,520),(8,521),(8,522),(8,523),(8,524),(9,525),(9,526),(9,527),(9,528),(9,529),(9,530),(9,531),(9,532),(9,533),(9,534),(9,535),(9,536),(9,537),(9,538),(9,539),(9,541),(9,542),(9,543),(9,544),(9,546),(9,547),(9,548),(9,549),(9,550),(9,551),(9,552),(9,553),(9,554),(9,555),(9,556),(9,557),(9,558),(9,559),(9,560),(9,561),(9,562),(9,563),(9,564),(9,565),(9,566),(9,567),(9,568),(9,569),(9,570),(9,571),(9,572),(9,573),(9,574),(9,575),(9,576),(9,577),(9,578),(9,579),(9,580),(9,581),(9,582),(10,583),(10,584),(10,585),(10,586),(10,587),(10,588),(10,589),(10,590),(10,591),(10,592),(10,593),(10,594),(10,595),(10,596),(10,597),(10,599),(10,600),(10,601),(10,602),(10,604),(10,605),(10,606),(10,607),(10,608),(10,609),(10,610),(10,611),(10,612),(10,613),(10,614),(10,615),(10,616),(10,617),(10,618),(10,619),(10,620),(10,621),(10,622),(10,623),(10,624),(10,625),(10,626),(10,627),(10,628),(10,629),(10,630),(10,631),(10,632),(10,633),(10,634),(10,635),(10,636),(10,637),(10,638),(10,639),(10,640),(11,641),(11,642),(11,643),(11,644),(11,645),(11,646),(11,647),(11,648),(11,649),(11,650),(11,651),(11,653),(11,654),(11,655),(11,656),(11,657),(11,658),(11,659),(11,660),(11,661),(11,662),(11,663),(11,664),(11,665),(11,666),(11,667),(11,668),(11,669),(11,670),(11,671),(11,672),(11,673),(11,674),(11,675),(11,676),(11,677),(11,678),(11,679),(11,680),(12,682),(12,683),(12,684),(12,685),(12,686),(12,687),(12,688),(12,689),(12,690),(12,691),(12,692),(12,693),(12,694),(12,695),(12,696),(12,697),(12,698),(12,699),(12,700),(12,701),(12,702),(12,703),(12,704),(12,705),(12,706),(12,707),(12,708),(13,709),(13,710),(13,711),(13,712),(13,713),(13,714),(13,715),(13,716),(13,717),(13,718),(13,719),(13,720),(13,721),(13,722),(13,723),(13,729),(13,730),(13,731),(13,732),(13,733),(13,734),(13,735);
/*!40000 ALTER TABLE `report_template_content_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_template_filter_parameter`
--

LOCK TABLES `report_template_filter_parameter` WRITE;
/*!40000 ALTER TABLE `report_template_filter_parameter` DISABLE KEYS */;
INSERT INTO `report_template_filter_parameter` VALUES (2,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(3,3),(9,79),(9,80);
/*!40000 ALTER TABLE `report_template_filter_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_template_output_parameter`
--

LOCK TABLES `report_template_output_parameter` WRITE;
/*!40000 ALTER TABLE `report_template_output_parameter` DISABLE KEYS */;
INSERT INTO `report_template_output_parameter` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(6,2),(8,2),(9,2),(13,2),(1,3),(2,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(9,3),(10,3),(11,3),(12,3),(13,3),(1,4),(2,4),(3,4),(4,4),(5,4),(6,4),(7,4),(8,4),(9,4),(10,4),(11,4),(12,4),(13,4),(2,5),(4,5),(5,5),(6,5),(7,5),(8,5),(9,5),(10,5),(11,5),(12,5),(13,5),(2,6),(4,6),(5,6),(6,6),(7,6),(8,6),(9,6),(10,6),(11,6),(12,6),(13,6),(2,7),(4,7),(5,7),(6,7),(7,7),(8,7),(9,7),(10,7),(12,7),(13,7),(1,9),(2,9),(3,9),(4,9),(5,9),(6,9),(7,9),(8,9),(9,9),(10,9),(11,9),(12,9),(13,9),(7,10),(8,10),(1,11),(2,11),(4,11),(5,11),(11,11),(12,11),(1,12),(2,12),(4,12),(5,12),(11,12),(12,12),(1,13),(3,13),(1,15),(2,15),(3,15),(4,15),(5,15),(6,15),(7,15),(9,15),(10,15),(11,15),(12,15),(13,15),(1,16),(2,16),(3,16),(4,16),(5,16),(6,16),(7,16),(8,16),(9,16),(10,16),(11,16),(12,16),(13,16),(1,17),(2,17),(13,17),(2,272),(1,273),(2,273),(3,274),(7,274),(8,274),(9,274),(10,274),(11,274),(12,274),(3,275),(6,275),(7,275),(8,275),(9,275),(10,275),(11,275),(12,275),(4,276),(5,276),(6,276),(4,277),(5,277),(4,278),(5,278),(6,279);
/*!40000 ALTER TABLE `report_template_output_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_template_user`
--

LOCK TABLES `report_template_user` WRITE;
/*!40000 ALTER TABLE `report_template_user` DISABLE KEYS */;
INSERT INTO `report_template_user` VALUES (1,1),(2,1),(3,1),(1,3),(2,3),(3,3),(3,85);
/*!40000 ALTER TABLE `report_template_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `report_user`
--

LOCK TABLES `report_user` WRITE;
/*!40000 ALTER TABLE `report_user` DISABLE KEYS */;
INSERT INTO `report_user` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(1,3),(2,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(9,3),(10,3),(11,3),(12,3),(13,3),(3,85);
/*!40000 ALTER TABLE `report_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-24 11:17:17





LOCK TABLES `printout` WRITE;
/*!40000 ALTER TABLE `printout` DISABLE KEYS */;
INSERT INTO `printout` VALUES (1,'Реестр погашений','PAYMENT_SUMMARY'),(2,'Детальный расчет','LOAN_DETAILED_SUMMARY'),(3,'Расчет','LOAN_SUMMARY'),(4,'Акт сверки','LOAN_SUMMARY'),(5,'Претензия','COLLECTION_SUMMARY');
/*!40000 ALTER TABLE `printout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `printout_template`
--

LOCK TABLES `printout_template` WRITE;
/*!40000 ALTER TABLE `printout_template` DISABLE KEYS */;
INSERT INTO `printout_template` VALUES (1,'Реестр погашений',1),(2,'Детальный расчет',2),(3,'На исковое заявление',3),(4,'Акт сверки Пром',4),(5,'Претензия Пром',5);
/*!40000 ALTER TABLE `printout_template` ENABLE KEYS */;
UNLOCK TABLES;



LOCK TABLES `message_resource` WRITE;
/*!40000 ALTER TABLE `message_resource` DISABLE KEYS */;
INSERT INTO `message_resource` VALUES (1,'Please login123','Авторизация болуңуз','login.form.title','Авторизируйтесь пожалуйста'),(2,'Username','Кыска атыңыз','login.form.input.username','Имя пользователя'),(3,'Password','Сыр сөз','login.form.input.password','Пароль'),(4,'Enter','Кирүү','login.form.button.login','Вход'),(5,'Name','Аталышы','label.orgForm.name','Наименование'),(6,'Forgot password?','Сыр сөздү унуттуңузбу?','login.forgot.password','Забыли пароль?'),(7,'Name2','Аталышы2','asdf','Наименование2'),(8,'Enabled','Колдонууда','label.enabled','Действует'),(9,'Disabled','Жараксыз','label.disabled','Отменен'),(10,'Name','Аталышы','label.orgForm.table.name','Наименование'),(11,'Enabled','Статусу','label.orgForm.table.enabled','Статус'),(12,'Edit','Оңдоп-түздөө','label.orgForm.table.edit','Редактировать'),(13,'Delete','Очуруп салуу','label.orgForm.table.delete','Удалить'),(14,'Edit','Ондоп-туздоо','label.orgForm.button.edit','Редактировать'),(15,'Close','Жабып салуу','label.orgForm.button.close','Закрыть'),(16,'Submit','Тастыктоо','label.orgForm.button.submit','Подтвердить'),(17,'Cancel','Кайра кайтаруу','label.orgForm.button.cancel','Отмена'),(18,'Delete','Очуруп салуу','label.orgForm.button.delete','Удалить'),(19,'Save','Сактоо','label.orgForm.button.save','Сохранить'),(20,'Add','Жаны','label.orgForm.button.add','Добавить'),(21,'Organization Form registration','Орг.форма киргизуу','label.orgForm.modal.title','Регистрация орг. формы'),(22,'Name','Аталышы','label.orgForm.modal.name','Наименование'),(23,'Enabled','Статусу','label.orgForm.modal.enabled','Статус'),(24,'Org Form List','Орг.формалар тизмеси','label.orgForm.page.title','Список организационных форм'),(25,'Region List','Областтардын тизмеси','label.region.page.title','Список областей'),(26,'ID','ID','label.region.table.id','ID'),(27,'Name','Аталышы','label.region.table.name','Наименование'),(28,'Code','Коду','label.region.table.code','Код'),(29,'View','Карап коруу','label.region.table.view','Просмотр'),(30,'Edit','Ондоп-туздоо','label.region.table.edit','Редактировать'),(31,'Delete','Очуруп салуу','label.region.table.delete','Удалить'),(32,'View','Карап коруу','label.region.button.view','Просмотр'),(33,'Edit','Оңдоп-түздөө','label.region.button.edit','Редактировать'),(34,'Delete','Очуруп салуу','label.region.button.delete','Удалить'),(35,'Close','Жабып салуу','label.region.button.close','Закрыть'),(36,'Save','Сактоо','label.region.button.save','Сохранить'),(37,'Add Region','Жаны область','label.region.button.addRegion','Добавить область'),(38,'Add District','Жаны район','label.region.button.addDistrict','Добавить район'),(39,'New Region or District Registration Form','Жаны область же район киргизуу','label.region.modal.title','Добавить область или район'),(40,'Name','Аталышы','label.region.modal.name','Наименование'),(41,'Code','Коду','label.region.modal.code','Код'),(42,'Submit','Тастыктоо','label.region.button.submit','Подтвердить'),(43,'Cancel','Кайра кайтаруу','label.region.button.cancel','Отмена'),(44,'User List','Колдонуучулардын тизмеси','label.user.page.title','Список пользователей'),(45,'ID','ID','label.user.table.id','ID'),(46,'Name','Аталышы','label.user.table.name','Наименование'),(47,'View','Карап коруу','label.user.table.view','Просмотр'),(48,'Edit','Ондоп-туздоо','label.user.table.edit','Редактировать'),(49,'Delete','Очуруп салуу','label.user.table.delete','Удалить'),(50,'View','Карап коруу','label.user.button.view','Просмотр'),(51,'Edit','Оңдоп-түздөө','label.user.button.edit','Редактировать'),(52,'Delete','Очуруп салуу','label.user.button.delete','Удалить'),(53,'Close','Жабып салуу','label.user.button.close','Закрыть'),(54,'Save','Сактоо','label.user.button.save','Сохранить'),(55,'Add User','Жаны колдонуучу','label.user.button.addUser','Добавить пользователя'),(56,'Add SupervisorTerm','Жаны куратор шарты','label.user.button.addSupervisorTerm','Добавить условие кураторства'),(57,'New User or SupervisorTerm Registration Form','Жаны колдонуучу же куратор шарты киргизуу','label.user.modal.title','Добавить пользователя или условие кураторства'),(58,'ID','ID','label.user.modal.id','ID'),(59,'Name','Аталышы','label.user.modal.name','Наименование'),(60,'Username','Аты','label.user.modal.username','Имя пользователя'),(61,'Password','Сыр созу','label.user.modal.password','Пароль'),(62,'Enabled','Статусу','label.user.modal.enabled','Статус'),(63,'Roles','Ролдору','label.user.modal.roles','Роли'),(64,'SupervisorTerms','Куратор шарттары','label.user.modal.supervisorterms','Условия кураторства'),(65,'Staff','Кызматкер','label.user.modal.staff','Сотрудник'),(66,'Submit','Тастыктоо','label.user.button.submit','Подтвердить'),(67,'Cancel','Кайра кайтаруу','label.user.button.cancel','Отмена'),(68,NULL,NULL,'label.document.title','Заголовок'),(69,NULL,NULL,'label.task.openTasks','Открытые задачи'),(70,NULL,NULL,'label.task.completedTasks','Завершенные задачи'),(71,NULL,NULL,'label.open','Открыть'),(72,NULL,NULL,'label.description','Описание'),(73,NULL,NULL,'label.state','Состояние'),(74,NULL,NULL,'label.priority','Приоритет'),(75,NULL,NULL,'label.owner','Владелец'),(76,NULL,NULL,'label.createdDate','Дата создания'),(77,NULL,NULL,'label.term','Срок'),(78,NULL,NULL,'label.completedDate','Дата выполнения'),(79,NULL,NULL,'label.document.description','Описание'),(80,NULL,NULL,'label.document.documentType','Вид документа'),(81,NULL,NULL,'label.document.documentSubType','Тип документа'),(82,NULL,NULL,'label.document.generalStatus','Состояние'),(83,'Edit','Ондоп туздоо','label.table.edit','Редактировать'),(84,'View','Карап коруу','label.table.view','Просмотр'),(85,'Delete','Очуруп салуу','label.table.delete','Удалить'),(86,'ID','ID','label.district.table.id','ID'),(87,'Name','Аталышы','label.district.table.name','Наименование'),(88,'Code','Код','label.district.table.code','Код'),(89,'REgion','Облусу','label.district.table.region','Область'),(90,'Add new or edit','Жаны район кошуу же алмаштыруу','label.district.modal.title','Новый район или редактировние района'),(91,'Name','Аталышы','label.district.modal.name','Наименование'),(92,'Code','Коду','label.district.modal.code','Код'),(93,'Region','Облусу','label.district.modal.region','Область'),(94,'Cancel','Жокко чыгаруу','label.district.button.cancel','Отменить'),(95,'Submit','Сактоо','label.district.button.submit','Сохранить'),(96,'District list','Райондор тизмеси','label.district.page.title','Список районов'),(97,'ID','ID','label.aokmotu.table.id','ID'),(98,'Name','Аталышы','label.aokmotu.table.name','Наименование'),(99,'Code','Код','label.aokmotu.table.code','Код'),(100,'District','Району','label.aokmotu.table.district','Район'),(101,'Add new or edit','Жаны кошуу же алмаштыруу','label.aokmotu.modal.title','Добавить или редактировать'),(102,'Name','Аталышы','label.aokmotu.modal.name','Наименование'),(103,'Code','Коду','label.aokmotu.modal.code','Код'),(104,'District','Району','label.aokmotu.modal.district','Район'),(105,'Cancel','Жокко чыгаруу','label.aokmotu.button.cancel','Отменить'),(106,'Submit','Сактоо','label.aokmotu.button.submit','Сохранить'),(107,'Aokmotu list','Аокмоттор тизмеси','label.aokmotu.page.title','Список аокмоту'),(108,'ID','ID','label.village.table.id','ID'),(109,'Name','Аталышы','label.village.table.name','Наименование'),(110,'Code','Код','label.village.table.code','Код'),(111,'Aokmotu','Аокмоту','label.village.table.aokmotu','Аокмоту'),(112,'Add new or edit','Жаны кошуу же алмаштыруу','label.village.modal.title','Добавить или редактировать'),(113,'Name','Аталышы','label.village.modal.name','Наименование'),(114,'Code','Коду','label.village.modal.code','Код'),(115,'Aokmotu','Аокмоту','label.village.modal.aokmotu','Аокмоту'),(116,'Cancel','Жокко чыгаруу','label.village.button.cancel','Отменить'),(117,'Submit','Сактоо','label.village.button.submit','Сохранить'),(118,'Village list','Айылдар тизмеси','label.village.page.title','Список сел'),(119,'ID','ID','label.iddocgivenby.table.id','ID'),(120,'Name','Аталышы','label.iddocgivenby.table.name','Наименование'),(121,'Enabled','Статусу','label.iddocgivenby.table.enabled','Статус'),(122,'Add new or edit','Жаны кошуу же алмаштыруу','label.iddocgivenby.modal.title','Добавить или редактировать'),(123,'Name','Аталышы','label.iddocgivenby.modal.name','Наименование'),(124,'Enabled','Статусу','label.iddocgivenby.modal.enabled','Статус'),(125,'Cancel','Жокко чыгаруу','label.iddocgivenby.button.cancel','Отменить'),(126,'Submit','Сактоо','label.iddocgivenby.button.submit','Сохранить'),(127,'Add new','Жаны кошуу','label.iddocgivenby.button.addIdDocGivenBy','Добавить новый орган выдачи документов'),(128,'Identity doc given by list','Мекемелер тизмеси','label.iddocgivenby.page.title','Список органов выдачи документов'),(129,'ID','ID','label.identityDocType.table.id','ID'),(130,'Name','Аталышы','label.identityDocType.table.name','Наименование'),(131,'Enabled','Статусу','label.identityDocType.table.enabled','Статус'),(132,'Add new or edit','Жаны кошуу же алмаштыруу','label.identityDocType.modal.title','Добавить или редактировать'),(133,'Name','Аталышы','label.identityDocType.modal.name','Наименование'),(134,'Enabled','Статусу','label.identityDocType.modal.enabled','Статус'),(135,'Cancel','Жокко чыгаруу','label.identityDocType.button.cancel','Отменить'),(136,'Submit','Сактоо','label.identityDocType.button.submit','Сохранить'),(137,'Add new','Жаны кошуу','label.identityDocType.button.addIdentityDocType','Добавить новый вид документа'),(138,'Identity doc type list','Документтер тизмеси','label.identityDocType.page.title','Список документов'),(139,'ID','ID','label.employmentHistoryEventType.table.id','ID'),(140,'Name','Аталышы','label.employmentHistoryEventType.table.name','Наименование'),(141,'Enabled','Статусу','label.employmentHistoryEventType.table.enabled','Статус'),(142,'Add new or edit','Жаны кошуу же алмаштыруу','label.employmentHistoryEventType.modal.title','Добавить или редактировать'),(143,'Name','Аталышы','label.employmentHistoryEventType.modal.name','Наименование'),(144,'Enabled','Статусу','label.employmentHistoryEventType.modal.enabled','Статус'),(145,'Cancel','Жокко чыгаруу','label.employmentHistoryEventType.button.cancel','Отменить'),(146,'Submit','Сактоо','label.employmentHistoryEventType.button.submit','Сохранить'),(147,'Add new','Жаны кошуу','label.employmentHistoryEventType.button.addEmploymentHistoryEventType','Добавить новый'),(148,'Employment history event type list','Окуя турлорунун тизмеси','label.employmentHistoryEventType.page.title','Список видов событий'),(149,'ID','ID','label.cSystem.table.id','ID'),(150,'Name','Аталышы','label.cSystem.table.name','Наименование'),(151,'Enabled','Статусу','label.cSystem.table.enabled','Статус'),(152,'Add new or edit','Жаны кошуу же алмаштыруу','label.cSystem.modal.title','Добавить или редактировать'),(153,'Name','Аталышы','label.cSystem.modal.name','Наименование'),(154,'Enabled','Статусу','label.cSystem.modal.enabled','Статус'),(155,'Cancel','Жокко чыгаруу','label.cSystem.button.cancel','Отменить'),(156,'Submit','Сактоо','label.cSystem.button.submit','Сохранить'),(157,'Add new system','Жаны система кошуу','label.cSystem.button.addcSystem','Добавить систему'),(158,'Add new information','Жаны маалымат кошуу','label.cSystem.button.addInformation','Добавить информацию'),(159,'System list','Системалардын тизмеси','label.cSystem.page.title','Список систем'),(160,'Staff','Кызматкер','label.user.modal.staff','Сотрудник'),(161,'Staff','Кызматкер','label.user.table.staff','Сотрудник'),(162,NULL,NULL,'label.document.sender','Отправитель'),(163,NULL,NULL,'label.document.receiver','Получатель'),(164,'','','label.task.description','Описание'),(165,'','','label.task.state','Состояние'),(166,'','','label.task.priority','Приоритет'),(167,'','','label.task.owner','Владелец'),(168,'','','label.task.createdDate','Дата создания'),(169,'','','label.task.dueDate','Срок'),(170,'','','label.task.completedDate','Дата выполнения'),(171,'View',NULL,'label.view','Просмотр'),(172,'Edit',NULL,'label.edit','Редактировать'),(173,'Delete',NULL,'label.delete','Удалить'),(174,'New Document Subtype','','label.documentSubType.add','Новый документ'),(175,'Document Subtypes','','label.documentSubTypes.title','Список документов'),(176,'Document Subtype details','','label.documentSubType.details','Детали'),(177,'Internal name','','label.documentSubType.internalName','Идентификатор'),(178,'Save',NULL,'label.save','Сохранить'),(179,'Cancel',NULL,'label.cancel','Отмена'),(180,'Search...',NULL,'label.search','Поиск...'),(181,'ID','ID','label.objectType.table.id','ID'),(182,'Name','Аталышы','label.objectType.table.name','Наименование'),(183,'Code','Код','label.objectType.table.code','Код'),(184,'Add new or edit','Жаны кошуу же алмаштыруу','label.objectType.modal.title','Добавить или редактировать'),(185,'Name','Аталышы','label.objectType.modal.name','Наименование'),(186,'Code','Код','label.objectType.modal.code','Код'),(187,'Cancel','Жокко чыгаруу','label.objectType.button.cancel','Отменить'),(188,'Submit','Сактоо','label.objectType.button.submit','Сохранить'),(189,'Add new','Жаны кошуу','label.objectType.button.addObjectType','Добавить '),(190,'Add field','Жаны талаача кошуу','label.objectType.button.addObjectField','Добавить поле'),(191,'Add event','Жаны окуя кошуу','label.objectType.button.addObjectEvent','Добавить событие'),(192,'Add event','шарт кошуу','label.objectType.button.addFixTerm','Добавить условие фиксации'),(193,'Object Type list','Объекттердин тизмеси','label.objectType.page.title','Список объектов'),(194,'ID','ID','label.messageResource.table.id','ID'),(195,'Name','Аталышы','label.messageResource.table.name','Наименование'),(196,'Message key','-','label.messageResource.table.messageKey','ключевое слово'),(197,'Eng','Англ.','label.messageResource.table.eng','Англ.'),(198,'Rus','Орусчасы','label.messageResource.table.rus','Русский'),(199,'Kgz','Кыргызчасы','label.messageResource.table.kgz','Кыргызский'),(200,'Add new or edit','Жаны кошуу же алмаштыруу','label.messageResource.modal.title','Добавить или редактировать'),(201,'Name','Аталышы','label.messageResource.modal.name','Наименование'),(202,'Message key','-','label.messageResource.modal.messageKey','ключевое слово'),(203,'Eng','Англ.','label.messageResource.modal.eng','Англ.'),(204,'Rus','Орусчасы','label.messageResource.modal.rus','Русский'),(205,'Kgz','Кыргызчасы','label.messageResource.modal.kgz','Кыргызский'),(206,'Cancel','Жокко чыгаруу','label.messageResource.button.cancel','Отменить'),(207,'Submit','Сактоо','label.messageResource.button.submit','Сохранить'),(208,'Add new message resource','Жаны которулуш','label.messageResource.button.addMessageResource','Добавить перевод'),(209,'Messsage resources list','Которулуштардын тизмеси','label.messageResource.page.title','Список переводов'),(210,'ID','ID','label.objectField.table.id','ID'),(211,'Name','Аталышы','label.objectField.table.name','Наименование'),(212,'Description','Тушундурмосу','label.objectField.table.description','Примечание'),(213,'Method','Метод','label.objectField.table.method','Метод'),(214,'Add new or edit','Жаны кошуу же алмаштыруу','label.objectField.modal.title','Добавить или редактировать'),(215,'Name','Аталышы','label.objectField.modal.name','Наименование'),(216,'Code','Код','label.objectField.modal.code','Код'),(217,'Cancel','Жокко чыгаруу','label.objectField.button.cancel','Отменить'),(218,'Submit','Сактоо','label.objectField.button.submit','Сохранить'),(219,'Add new','Жаны кошуу','label.objectField.button.addObjectType','Добавить '),(220,'Add field','Жаны талаача кошуу','label.objectField.button.addObjectField','Добавить поле'),(221,'Add event','Жаны окуя кошуу','label.objectField.button.addObjectEvent','Добавить событие'),(222,'Add event','шарт кошуу','label.objectField.button.addFixTerm','Добавить условие фиксации'),(223,'Object Type list','Объекттердин тизмеси','label.objectField.page.title','Список объектов'),(224,'ID','ID','label.role.table.id','ID'),(225,'Name','Аталышы','label.role.table.name','Наименование'),(226,'Enabled','Статусу','label.role.table.enabled','Статус'),(227,'Add new or edit','Жаны кошуу же алмаштыруу','label.role.modal.title','Добавить или редактировать'),(228,'Name','Аталышы','label.role.modal.name','Наименование'),(229,'Enabled','Статусу','label.role.modal.enabled','Статус'),(230,'Cancel','Жокко чыгаруу','label.role.button.cancel','Отменить'),(231,'Submit','Сактоо','label.role.button.submit','Сохранить'),(232,'Add new role','Жаны роль кошуу','label.role.button.addRole','Добавить роль'),(233,'Role list','Ролдордун тизмеси','label.role.page.title','Список ролей'),(234,'ID','ID','label.permission.table.id','ID'),(235,'Name','Аталышы','label.permission.table.name','Наименование'),(236,'Enabled','Статусу','label.permission.table.enabled','Статус'),(237,'Add new or edit','Жаны кошуу же алмаштыруу','label.permission.modal.title','Добавить или редактировать'),(238,'Name','Аталышы','label.permission.modal.name','Наименование'),(239,'Enabled','Статусу','label.permission.modal.enabled','Статус'),(240,'Cancel','Жокко чыгаруу','label.permission.button.cancel','Отменить'),(241,'Submit','Сактоо','label.permission.button.submit','Сохранить'),(242,'Add new permission','Жаны укук кошуу','label.permission.button.addPermission','Добавить право'),(243,'Permission list','Укуктар тизмеси','label.permission.page.title','Список прав доступа'),(244,'ID','ID','label.supervisorTerm.table.id','ID'),(245,'Name','Аталышы','label.supervisorTerm.table.name','Наименование'),(246,'Enabled','Статусу','label.supervisorTerm.table.enabled','Статус'),(247,'DebtorType','Заемщик туру','label.supervisorTerm.table.debtorType','Вид заемщика'),(248,'workSector','Заемщик тармагы','label.supervisorTerm.table.workSector','Отрасль'),(249,'REgion','Облус','label.supervisorTerm.table.region','Область'),(250,'District','Район','label.supervisorTerm.table.district','Район'),(251,'Department','Болум','label.supervisorTerm.table.department','Отдел'),(252,'Add new or edit','Жаны кошуу же алмаштыруу','label.supervisorTerm.modal.title','Добавить или редактировать'),(253,'Name','Аталышы','label.supervisorTerm.modal.name','Наименование'),(254,'Enabled','Статусу','label.supervisorTerm.modal.enabled','Статус'),(255,'DebtorType','Заемщик туру','label.supervisorTerm.modal.debtorType','Вид заемщика'),(256,'workSector','Заемщик тармагы','label.supervisorTerm.modal.workSector','Отрасль'),(257,'REgion','Облус','label.supervisorTerm.modal.region','Область'),(258,'District','Район','label.supervisorTerm.modal.district','Район'),(259,'Department','Болум','label.supervisorTerm.modal.department','Отдел'),(260,'Cancel','Жокко чыгаруу','label.supervisorTerm.button.cancel','Отменить'),(261,'Submit','Сактоо','label.supervisorTerm.button.submit','Сохранить'),(262,'Add new supervisor term','Жаны куратор шарты кошуу','label.supervisorTerm.button.addSupervisorTerm','Добавить условие кураторства'),(263,'SupervisorTerm list','куратор шарты тизмеси','label.supervisorTerm.page.title','Список условий кураторства'),(264,'ID','ID','label.information.table.id','ID'),(265,'Name','Аталышы','label.information.table.name','Наименование'),(266,'Enabled','Статусу','label.information.table.enabled','Статус'),(267,'Add new or edit','Жаны кошуу же алмаштыруу','label.information.modal.title','Добавить или редактировать'),(268,'Name','Аталышы','label.information.modal.name','Наименование'),(269,'Enabled','Статусу','label.information.modal.enabled','Статус'),(270,'Cancel','Жокко чыгаруу','label.information.button.cancel','Отменить'),(271,'Submit','Сактоо','label.information.button.submit','Сохранить'),(272,'Add new','Жаны кошуу','label.information.button.addEmploymentHistoryEventType','Добавить новый'),(273,'Information type list','Маалымат тизмеси','label.information.page.title','Список информации'),(274,'Debtor','Заемщиктер','label.debtor.page.title','Список заемщиков'),(275,'Search','Издоо','label.search','Поиск'),(276,'First','Биринчиси','label.pagination.first','Начало'),(277,'Previous','Буга чейинкиси','label.pagination.prev','Пред'),(278,'Next','Мындан кийинкиси','label.pagination.next','След'),(279,'Last','Акыркысы','label.pagination.last','Послед'),(280,'Page Size','Баракча чондугу','label.pageSize','Кол-во'),(281,'add Debtor','Жаны заемщик','label.debtor.addDebtor','Добавить заемщика'),(282,'add Loan','Жаны кредит','label.debtor.addLoan','Добавить кредит'),(283,'add Agreement','Жаны куроо','label.debtor.addAgreement','Добавить договор залога'),(284,'add Procedure','Жаны ондуруу процедурасы','label.debtor.addProcedure','Добавить процедуру взыскания'),(285,'add Phase','Жаны ондурусу фазасы','label.debtor.addPhase','Добавить фазу взыскания'),(286,'ID','ID','label.debtor.table.id','ID'),(287,'Name','Аталышы','label.debtor.table.name','Наименование'),(288,'Debtor Type','Заемщик туру','label.debtor.table.debtorType','Вид заемщика'),(289,'WorkSector','Тармак','label.debtor.table.workSector','Отрасль'),(290,'District','Район','label.debtor.table.district','Район'),(291,'OrgForm','Формасы','label.debtor.table.orgForm','Орг.форма'),(292,'Debtor','Заемщик','label.add.debtor.title','Заемщик'),(293,'Name','Аталышы','label.add.debtor.name','Наименование'),(294,'Type','Туру','label.add.debtor.type','Вид'),(295,'Organizational Form','Формасы','label.add.debtor.orgForm','Орг.форма'),(296,'WorkSector','Тармагы','label.add.debtor.workSector','Отрасль'),(297,'Owner','заемщик','label.add.debtor.owner','Заемщик'),(298,'Save','Сактоо','label.add.debtor.save','Сохранить'),(299,'Cancel','Жокко чыгаруу','label.add.debtor.cancel','Отменить'),(300,'Loans','Кредит маалыматы','label.debtor.tab.loans','Кредитная информация'),(301,'Collateral','Куроо маалыматы','label.debtor.tab.agreements','Залоговое обеспечение'),(302,'Collection','Ондуруу маалыматтары','label.debtor.tab.procedures','Принудительное взыскание'),(303,'Loan','Насыя','label.add.loan.title','Кредит'),(304,'Save','Сактоо','label.add.loan.save','Сохранить'),(305,'Cancel','Жокко чыгаруу','label.add.loan.cancel','Отменить'),(306,'Select Currency','Валюта танданыз','label.add.loan.selectCurrency','Выбрать валюту'),(307,'Select Type','Насыя турун танданыз','label.add.loan.selectType','Выбрать вид '),(308,'Select State','Статусун танданыз','label.add.loan.selectState','Выбрать статус'),(309,'Select Parent','Башкы насыясын танданыз','label.add.loan.selectParent','Выбрать основной кредит'),(310,'Select Order','Насыя беруу чечимин танданыз','label.add.loan.selectCreditOrder','Выбрать решение о выдаче кредита'),(311,'Registration Number','Каттоо номуру','label.loan.regNumber','Регистрационный номер'),(312,'Registration Date','Каттоо датасы','label.loan.regDate','Дата регистрации'),(313,'Amount','Суммасы','label.loan.amount','Сумма по договору'),(314,'Currency','Валютасы','label.loan.currency','Валюта'),(315,'Type','Туру','label.loan.type','Вид'),(316,'State','Статусу','label.loan.state','Статус'),(317,'Supervisor','Куратору','label.loan.supervisorId','Куратор'),(318,'Parent','Башкысы','label.loan.parent','Основной кредит'),(319,'Order','Насыя беруу чечими','label.loan.creditOrderId','Решение о выдаче кредита'),(320,'Registration Number','Каттоо номуру','label.loan.table.regNumber','Регистрационный номер'),(321,'Registration Date','Каттоо датасы','label.loan.table.regDate','Дата регистрации'),(322,'Amount','Суммасы','label.loan.table.amount','Сумма по договору'),(323,'Currency','Валютасы','label.loan.table.currency','Валюта'),(324,'Type','Туру','label.loan.table.type','Вид'),(325,'State','Статусу','label.loan.table.state','Статус'),(326,'Supervisor','Куратору','label.loan.table.superId','Куратор'),(327,'Parent','Башкысы','label.loan.table.parentId','Основной кредит'),(328,'Order','Насыя беруу чечими','label.loan.table.creditOrderId','Решение о выдаче кредита'),(329,'Has SubLoan','Субкредити бар','label.loan.table.hasSubLoan','Наличие субкредитов'),(330,'Credit Terms','Шарттары','label.creditTerms','Условия'),(331,'Write Offs','Списание','label.writeOffs','Списание'),(332,'Payment Schedules','Графиктери','label.paymentSchedules','Срочные обязательства'),(333,'Payments','Толомдору','label.payments','Погашения'),(334,'Supervisor Plans','План','label.supervisorPlans','План'),(335,'Loan goods','Товар','label.loanGoods','Товар'),(336,'Debt transfers','Перевод','label.debtTransfers','Перевод долга'),(337,'targeted uses','бар','label.targetedUses','Целевое исп.'),(338,'reconstructed lists','Реструктуризвция','label.reconstructedLists','Реструктуризация'),(339,'bankrupts','Банкрот','label.bankrupts','Банкротство'),(340,'collaterals','Куроо','label.collaterals','Залог'),(341,'collection phases','Фзалары','label.collectionPhases','Фазы'),(342,'Detailed summary','Эсептери','label.loanDetailedSummary','Детальный расчет'),(343,'Summary','Эсептери','label.loanSummary','Расчет'),(344,'Accrue','Процент, штрафтары','label.accrue','Начисление'),(345,'startDate','Башталган датасы','label.creditTerm.startDate','Дата'),(346,'interestRateValue','Процентная ставка','label.creditTerm.interestRateValue','Процентная ставка'),(347,'ratePeriod','Период','label.creditTerm.ratePeriod','Период начисления процентов'),(348,'floatingRateType','Процентке ставка','label.creditTerm.floatingRateType','Плавающая ставка'),(349,'enaltyOnPrincipleOverdueRateValue','Негизги Штраф','label.creditTerm.enaltyOnPrincipleOverdueRateValue','Штраф по осн.с.'),(350,'penaltyOnPrincipleOverdueRateType','Негизги Штрафка ставка','label.creditTerm.penaltyOnPrincipleOverdueRateType','Ставка на штраф по осн.с.'),(351,'penaltyOnInterestOverdueRateValue','Процент штраф','label.creditTerm.penaltyOnInterestOverdueRateValue','Штраф по процентам'),(352,'penaltyOnInterestOverdueRateType','Процент штрафка ставка','label.creditTerm.penaltyOnInterestOverdueRateType','Ставка на штраф по проц.'),(353,'penaltyLimitPercent','Шраф лимити','label.creditTerm.penaltyLimitPercent','Лимит начисления штр.'),(354,'penaltyLimitEndDate','Штраф саналбай баштаган дата','label.creditTerm.penaltyLimitEndDate','Дата приост. нач.штр.'),(355,'transactionOrder','Толоо туру','label.creditTerm.transactionOrder','Очередь погашения'),(356,'daysInMonthMethod','айдагы кун саноо методу','label.creditTerm.daysInMonthMethod','Метод кол-ва дней в мес.'),(357,'daysInYearMethod','жылдагы кун саноо методу','label.creditTerm.daysInYearMethod','Метод кол-ва дней в год'),(358,'Add new Credit Term','Жаны шарт','label.button.addNewCreditTerm','Добавить условие кред-я'),(359,'date','Датасы','label.writeOff.date','Дата'),(360,'total amount','Сумма','label.writeOff.totalAmount','Всего'),(361,'principal','Негизги карыз','label.writeOff.principal','Осн.с.'),(362,'interest','Процент','label.writeOff.interest','Проценты'),(363,'penalty','Штраф','label.writeOff.penalty','Штрафы'),(364,'fee','Комиссия','label.writeOff.fee','Комиссия'),(365,'description','Тушундурмосу','label.writeOff.description','Примечание'),(366,'addNewWriteOff','Жаны списание','label.button.addNewWriteOff','Добавить списание'),(367,'expectedDate','Датасы','label.paymentSchedule.expectedDate','Дата'),(368,'disbursement','Алынганы','label.paymentSchedule.disbursement','Освоение'),(369,'principalPayment','Негизги карыз','label.paymentSchedule.principalPayment','Осн.сумма'),(370,'interestPayment','Процент','label.paymentSchedule.interestPayment','Проценты'),(371,'collectedIneterestPayment','Топт.процент','label.paymentSchedule.collectedInterestPayment','Нак.проценты'),(372,'collectedPenaltyPayment','Топт.штраф','label.paymentSchedule.collectedPenaltyPayment','Нак.штр.'),(373,'installmentState','Статусу','label.paymentSchedule.installmentState','Статус'),(374,'addNewPaymentSchedule','Жаны график','label.button.addNewPaymentSchedule','Добавить ср.обязательство'),(375,'paymentDate','Датасы','label.payment.paymentDate','Дата'),(376,'totalAmount','Сумма','label.payment.totalAmount','Итого'),(377,'principal','Негизги карыз','label.payment.principal','Осн.сумма'),(378,'ineterest','Процент','label.payment.interest','Проценты'),(379,'penalty','Штраф','label.payment.penalty','Штрафы'),(380,'fee','Комиссия','label.payment.fee','Комиссия'),(381,'number','Номер','label.payment.number','Номер'),(382,'paymentType','Туру','label.payment.paymentType','Вид платежа'),(383,'addPayment','Толом кошуу','label.button.addNewPayment','Добавить погашение'),(384,'paymentDate','Датасы','label.supervisorPlan.date','Дата'),(385,'paymentDate','Суммасы','label.supervisorPlan.amount','Итого'),(386,'paymentDate','Негизги карыз','label.supervisorPlan.principal','Осн.сумма'),(387,'paymentDate','Процент','label.supervisorPlan.interest','Проценты'),(388,'paymentDate','Штраф','label.supervisorPlan.penalty','Штрафы'),(389,'paymentDate','Комиссия','label.supervisorPlan.fee','Комиссия'),(390,'paymentDate','Тушундурмосу','label.supervisorPlan.description','Примечание'),(391,'addPlan','План кошуу','label.button.addNewSupervisorPlan','Добавить план'),(392,'ID','ID','label.loanGoods.id','ID'),(393,'quantity','Кол-во','label.loanGoods.quantity','Кол-во'),(394,'unitType','Ед. измерения','label.loanGoods.unitType','Ед. измерения'),(395,'goodType','Туру','label.loanGoods.goodType','Вид'),(396,'addLoanGood','Товар кошуу','label.button.addNewLoanGood','Добавить товар'),(397,'ID','ID','label.debtTransfer.id','ID'),(398,'number','Номери','label.debtTransfer.number','Номер'),(399,'date','Датасы','label.debtTransfer.date','Дата'),(400,'quantity','Саны','label.debtTransfer.quantity','Кол-во'),(401,'price per unit','Баасы','label.debtTransfer.pricePerUnit','Цена'),(402,'unit type','Ед. изм.','label.debtTransfer.unitType','Ед. изм.'),(403,'total cost','Суммасы','label.debtTransfer.totalCost','Сумма'),(404,'payment','Толому','label.debtTransfer.transferPayment','Платеж'),(405,'credit','Насыясы','label.debtTransfer.transferCredit','Кредит'),(406,'person ','Заемщик','label.debtTransfer.transferPerson','Должник'),(407,'goods','Товар','label.debtTransfer.goodsType','Товар'),(408,'add DebtTransfer ','Карыз откоруп берууну кошуу','label.button.addNewDebtTransfer','Добавить перевод долга'),(409,'ID','ID','label.targetedUse.id','ID'),(410,'Result','Жыйынтыгы','label.targetedUse.result','Результат'),(411,'Created by','Киргизген','label.targetedUse.createdBy','Внесено'),(412,'Created Date','Кирилген дата','label.targetedUse.createdDate','Дата внесения'),(413,'Approved by','Тастыктаган','label.targetedUse.approvedBy','Подтверждено'),(414,'Approved date','Тастыкталган дата','label.targetedUse.approvedDate','Дата подтверждения'),(415,'Checked by','Текшерген','label.targetedUse.checkedBy','Проверено'),(416,'Checked date','Текшерилген дата','label.targetedUse.checkedDate','Дата проверки'),(417,'Attachment','Тиркеме','label.targetedUse.attachment','Приложение'),(418,'add Targeted Use ','Туура колдонуу боюнча маалымат кошуу','label.button.addNewTargetedUse','Добавить целевое использование'),(419,'ID','ID','label.reconstructedList.id','ID'),(420,'onDate','Датасы','label.reconstructedList.onDate','Дата'),(421,'oldLoan','Насыя','label.reconstructedList.oldLoan','Кредит'),(422,'add Reconstructed List','Реструктуризация кошуу','label.button.addNewReconstructedList','Добавить реструктуризацию'),(423,'ID','ID','label.bankrupt.id','ID'),(424,'startedOnDate','Башталган датасы','label.bankrupt.startedOnDate','Дата инициирования'),(425,'finishedOnDate','Буткон датасы','label.bankrupt.finishedOnDate','Дата завершения'),(426,'add Bankrupt','Банкрот кошуу','label.button.addNewBankrupt','Добавить банкротство'),(427,'ID','ID','label.collectionPhase.id','ID'),(428,'startDate','Дата инициирования','label.collectionPhase.startDate','Дата инициирования'),(429,'closeDate','Дата завершения','label.collectionPhase.closeDate','Дата завершения'),(430,'lastEvent','посл. событие','label.collectionPhase.lastEvent','посл. событие'),(431,'lastStatus','посл. статус','label.collectionPhase.lastStatus','посл. статус'),(432,'phaseStatus','Статус','label.collectionPhase.phaseStatus','Статус'),(433,'phaseType','Вид','label.collectionPhase.phaseType','Вид'),(434,'add Collection Phase','Фаза кошуу','label.button.addNewCollectionPhase','Добавить фазу'),(435,'ID','ID','label.loanDetailedSummary.id','ID'),(436,'onDate','На дату','label.loanDetailedSummary.onDate','На дату'),(437,'disbursement','Освоение','label.loanDetailedSummary.disbursement','Освоение'),(438,'totalDisbursement','Всего освоено','label.loanDetailedSummary.totalDisbursement','Всего освоено'),(439,'principalPayment','По графику по осн.с.','label.loanDetailedSummary.principalPayment','По графику по осн.с.'),(440,'totalPrincipalPayment','Всего по графику по осн.с.','label.loanDetailedSummary.totalPrincipalPayment','Всего по графику по осн.с.'),(441,'principalPaid','Погашение по осн.с.','label.loanDetailedSummary.principalPaid','Погашение по осн.с.'),(442,'totalPrincipalPaid','Всего погашено по осн.с.','label.loanDetailedSummary.totalPrincipalPaid','Всего погашено по осн.с.'),(443,'principalWriteOff','Списание по осн.с.','label.loanDetailedSummary.principalWriteOff','Списание по осн.с.'),(444,'totalPrincipalWriteOff','Всего списано по осн.с.','label.loanDetailedSummary.totalPrincipalWriteOff','Всего списано по осн.с.'),(445,'principalOutstanding','Ост. по осн.с.','label.loanDetailedSummary.principalOutstanding','Ост. по осн.с. '),(446,'principalOverdue','Проср. по осн.с.','label.loanDetailedSummary.principalOverdue','Проср. по осн.с.'),(447,'daysInPeriod','Кол-во дней','label.loanDetailedSummary.daysInPeriod','Кол-во дней'),(448,'interestAccrued','Начисление проц.','label.loanDetailedSummary.interestAccrued','Начисление проц.'),(449,'totalInterestAccrued','Всего начислено проц.','label.loanDetailedSummary.totalInterestAccrued','Всего начислено проц.'),(450,'totalInterestAccruedOnInterestPayment',',из них подлежит погашению','label.loanDetailedSummary.totalInterestAccruedOnInterestPayment',',из них подлежит погашению'),(451,'interestPayment','По графику по проц.','label.loanDetailedSummary.interestPayment','По графику по проц.'),(452,'totalInterestPayment','Всего по графику по проц.','label.loanDetailedSummary.totalInterestPayment','Всего по графику по проц.'),(453,'collectedInterestPayment','По графику нак.проц.','label.loanDetailedSummary.collectedInterestPayment','По графику нак.проц.'),(454,'totalCollectedInterestPayment','Всего по графику нак.проц.','label.loanDetailedSummary.totalCollectedInterestPayment','Всего по графику нак.проц.'),(455,'collectedInterestDisbursed','Всего нак. проц.','label.loanDetailedSummary.collectedInterestDisbursed','Всего нак. проц.'),(456,'interestPaid','Погашение проц.','label.loanDetailedSummary.interestPaid','Погашение проц.'),(457,'totalInterestPaid','Всего погашено проц.','label.loanDetailedSummary.totalInterestPaid','Всего погашено проц.'),(458,'interestOutstanding','Остаток по процентам','label.loanDetailedSummary.interestOutstanding','Остаток по процентам'),(459,'interestOverdue','Проср. по проц.','label.loanDetailedSummary.interestOverdue','Проср. по проц.'),(460,'penaltyAccrued','Начисление штр.','label.loanDetailedSummary.penaltyAccrued','Начисление штр.'),(461,'totalPenaltyAccrued','Всего начислено штр.','label.loanDetailedSummary.totalPenaltyAccrued','Всего начислено штр.'),(462,'collectedPenaltyPayment','По графику нак.штр.','label.loanDetailedSummary.collectedPenaltyPayment','По графику нак.штр.'),(463,'totalCollectedPenaltyPayment','Всего по графику нак.штр.','label.loanDetailedSummary.totalCollectedPenaltyPayment','Всего по графику нак.штр.'),(464,'collectedPenaltyDisbursed','Всего нак.штр.','label.loanDetailedSummary.collectedPenaltyDisbursed','Всего нак.штр.'),(465,'penaltyPaid','Погашение штр.','label.loanDetailedSummary.penaltyPaid','Погашение штр.'),(466,'totalPenaltyPaid','Всего погашено штр.','label.loanDetailedSummary.totalPenaltyPaid','Всего погашено штр.'),(467,'penaltyOutstanding','Остаток по штр.','label.loanDetailedSummary.penaltyOutstanding','Остаток по штр.'),(468,'penaltyOverdue','Проср. по штр.','label.loanDetailedSummary.penaltyOverdue','Проср. по штр.'),(469,'ID','ID','label.loanSummary.id','ID'),(470,'onDate','На дату','label.loanSummary.onDate','На дату'),(471,'loanAmount','Сумма по договору','label.loanSummary.loanAmount','Сумма по договору'),(472,'totalDisbursed','Всего освоено','label.loanSummary.totalDisbursed','Всего освоено'),(473,'totalPaid','Всего погашено','label.loanSummary.totalPaid','Всего погашено'),(474,'paidPrincipal','Пог. по осн.с.','label.loanSummary.paidPrincipal','Пог. по осн.с.'),(475,'paidInterest','Пог. по проц.','label.loanSummary.paidInterest','Пог. по проц.'),(476,'paidPenalty','Пог. по штр.','label.loanSummary.paidPenalty','Пог. по штр.'),(477,'paidFee','Пог. по комм.','label.loanSummary.paidFee','Пог. по комм.'),(478,'totalOutstanding','Всего ост.','label.loanSummary.totalOutstanding','Всего ост.'),(479,'outstadingPrincipal','Ост. по осн.с.','label.loanSummary.outstadingPrincipal','Ост. по осн.с.'),(480,'outstadingInterest','Ост. по проц.','label.loanSummary.outstadingInterest','Ост. по проц.'),(481,'outstadingPenalty','Ост. по штр.','label.loanSummary.outstadingPenalty','Ост. по штр.'),(482,'outstadingFee','Ост. по комм.','label.loanSummary.outstadingFee','Ост. по комм.'),(483,'totalOverdue','Всего проср.','label.loanSummary.totalOverdue','Всего проср.'),(484,'overduePrincipal','Проср. по осн.с.','label.loanSummary.overduePrincipal','Проср. по осн.с.'),(485,'overdueInterest','Проср. по проц.','label.loanSummary.overdueInterest','Проср. по проц.'),(486,'overduePenalty','Проср. по штр.','label.loanSummary.overduePenalty','Проср. по штр.'),(487,'overdueFee','Проср. по комм.','label.loanSummary.overdueFee','Проср. по комм.'),(488,'ID','ID','label.accrue.id','ID'),(489,'fromDate','с даты','label.accrue.fromDate','с даты'),(490,'toDate','по дату','label.accrue.toDate','по дату'),(491,'daysInPeriod','кол-во дней','label.accrue.daysInPeriod','кол-во дней'),(492,'interestAccrued','Начисление проц.','label.accrue.interestAccrued','Начисление проц.'),(493,'penaltyAccrued','Начисление штр.','label.accrue.penaltyAccrued','Начисление штр.'),(494,'penaltyOnPrincipalOverdue','Штр. на осн.с.','label.accrue.penaltyOnPrincipalOverdue','Штр. на осн.с.'),(495,'penaltyOnInterestOverdue','Штр. на проц.','label.accrue.penaltyOnInterestOverdue','Штр. на проц.'),(496,'lastInstPassed','Проср. график','label.accrue.lastInstPassed','Проср. график'),(497,'Number','Номери','label.agreement.table.number','Номер'),(498,'Date','Датасы','label.agreement.table.date','Дата'),(499,'Reg number','Каттоо номери','label.agreement.table.collRegNumber','Регистрационный номер'),(500,'Reg Date','Каттоо датасы','label.agreement.table.collRegDate','Дата регистрации'),(501,'Notary reg number','Нотариус каттоо номери','label.agreement.table.notaryRegNumber','Номер нотариальной регистрации'),(502,'Notary reg date','Нотариус каттоо датасы','label.agreement.table.notaryRegDate','Дата нотариальной регистрации'),(503,'Arrest reg number','Арест каттоо номери','label.agreement.table.arrestRegNumber','Номер ареста'),(504,'Arrest reg date','Арест каттоо датасы','label.agreement.table.arrestRegDate','Дата ареста'),(505,'ID','ID','label.procedure.table.id','ID'),(506,'ID','ID','label.table.id','ID'),(507,'Start date','Башталган датасы','label.procedure.table.startDate','Дата инициирования'),(508,'Close date','Буткон датасы','label.procedure.table.closeDate','Дата завершения'),(509,'Last Phase','Акыркы фазасы','label.procedure.table.lastPhase','Последняя фаза'),(510,'Last status','Акыркы статусы','label.procedure.table.lastStatusId','Последний статус'),(511,'Status','Статусу','label.procedure.table.status','Статус'),(512,'Type','Туру','label.procedure.table.type','Вид'),(513,'Name','Аталышы','label.report.name','Наименование'),(514,'Report Type','Туру','label.report.reportType','Вид'),(515,'Add Report','Жаны отчет','label.add.report','Добавить отчет'),(516,'Report Templates','Шаблондор','label.report.info','Шаблоны'),(517,'Report','Отчет','label.reports','Отчет'),(518,'Report type','Отчет туру','label.report.type','Вид отчета'),(519,'Generate','Чыгаруу','label.table.generate','Сформировать'),(520,'Printout','Кагазга чыгаруу','label.printouts','Распечатка'),(521,'Name','Аталышы','label.printout.name','Наименование'),(522,'Type','Туру','label.printout.printoutType','Вид'),(523,'Add','Жаны','label.add.printout','Добавить'),(524,'Printout','Кагазга чыгаруу','label.printouts','Настройка распечатки'),(525,'Templates','Шаблон','label.printout.templates','Шаблоны распечатки'),(526,'Name','Аталышы','label.template.name','Наименование'),(527,'Name','Аталышы','label.printoutTemplate.name','Наименование'),(528,'Report','Отчет','label.template.report','Отчет'),(529,'Save','Сактоо','label.button.save','Сохранить'),(530,'orders','Насыя беруу чечими','label.debtor.orders','Решение на выдачу кредита'),(531,'ID','ID','label.order.table.id','ID'),(532,'regNum','Номери','label.order.table.regNum','Номер'),(533,'regDate','Датасы','label.order.table.regDate','Дата'),(534,'description','Тушундурмосу','label.order.table.description','Примечание'),(535,'State','Статусу','label.order.table.creditOrderState','Статус'),(536,'Type','Туру','label.order.table.creditOrderType','Вид'),(537,'Add new','Жаны чечим','label.order.button.addNewCreditOrder','Добавить Решение на выдачу кредита'),(538,'Loans','Кредиттер','label.agreement.table.loans','Кредиты'),(539,'Owner','Куроо ээси','label.agreement.table.owner','Залогодатель'),(540,'Cancel','Жокко чыгаруу','label.button.cancel','Отменить'),(541,'Collateral agreement','Куроо келишими','label.add.collateralAgreement.title','Договор залога'),(542,'organizations','Мекемелер тизмеси','label.organizations','Список организаций'),(543,'addressData','Адрес','label.addressData','Адресные данные'),(544,'identityDocData','Кимдигин аныктаган маалымат','label.identityDocData','Идентификационные данные'),(545,'contacts','Контакт','label.contacts','Контактные данные'),(546,'add organization','Жаны мекеме','label.add.organization','Добавить организацию'),(547,'add bankData','Жаны банк маалыматы','label.add.bankData','Добавить банковские данные'),(548,'add department','Жаны болум','label.add.department','Добавить подразделение'),(549,'add information','Жаны маалымат','label.add.information','Добавить информацию'),(550,'add staff','Жаны кызматкер','label.add.staff','Добавить сотрудника'),(551,'name','Аталышы','label.organization.name','Наименование'),(552,'description','Тушундурмосу','label.organization.description','Примечание'),(553,'name','Аталышы','label.org.name','Наименование'),(554,'description','Тушундурмосу','label.org.description','Примечание'),(555,'orgForm','Мекеме формасы','label.org.orgForm','Орг. форма'),(556,'address','Адрес','label.org.address','Адрес'),(557,'region','Облусу','label.org.region','Область'),(558,'district','Району','label.org.district','Район'),(559,'aokmotu','А.окмоту','label.org.aokmotu','А.окомту'),(560,'village','Айылы','label.org.village','Село'),(561,'name','Аталышы','label.identityDoc.name','Наименование'),(562,'enabled','Статусу','label.identityDoc.enabled','Статус'),(563,'number','Номери','label.identityDoc.number','Номер'),(564,'pin','ИНН','label.identityDoc.pin','ИНН'),(565,'type','Туру','label.identityDoc.type','Вид'),(566,'givenBy','Берген мекеме','label.identityDoc.givenBy','Кем выдано'),(567,'date','Датасы','label.identityDoc.date','Дата'),(568,'firstname','Аты','label.identityDoc.firstname','Имя'),(569,'lastname','Фамилиясы','label.identityDoc.lastname','Фамилия'),(570,'midname','Атасынын аты','label.identityDoc.midname','Отчетство'),(571,'fullname','Толук аты','label.identityDoc.fullname','Полное наименование'),(572,'name','Контактар','label.contact.name','Контакты'),(573,'persons','ФЛ тизмеси','label.persons','Список физ.лиц.'),(574,'add person','жаны ФЛ','label.add.person','Добавить физ.лицо'),(575,'name','Аталышы','label.person.name','Наименование'),(576,'description','Тушундурмосу','label.person.description','Примечание'),(577,'addressLine','Адрес','label.person.addressLine','Адрес'),(578,'region','Облусу','label.person.region','Область'),(579,'district','Району','label.person.district','Район'),(580,'aokmotu','А.окмоту','label.person.aokmotu','А.окмоту'),(581,'village','Айылы','label.person.village','Село'),(602,'Order','Насыя чечими','label.add.order.title','Решение на выдачу кредита'),(603,'Order','Насыя чечими','label.order.add','Решение на выдачу кредита'),(604,'registrationNumber','Номери','label.order.registrationNumber','Номер'),(605,'registrationDate','Датасы','label.order.registrationDate','Дата'),(606,'description','Тушундурмосу','label.order.description','Примечание'),(607,'state','Статусу','label.order.state','Статус'),(608,'type','Туру','label.order.type','Вид'),(609,'save','Сактоо','label.form.save','Сохранить'),(610,'cancel','Жокко чыгаруу','label.form.cancel','Отменить'),(611,'select.date','Дата тандоаныз','label.select.date','Указать дату'),(612,'select','Тизмеден танданыз','label.select','Выбрать из списка'),(613,'entityLists','Алуучу тизмелери','label.order.entityLists','Списки получателей'),(614,'documentPackages','Документ пакети','label.order.documentPackages','Пакет документации'),(615,'terms','НАсыя шарттары','label.order.terms','Кредитные условия'),(616,'addAppliedEntityList','Жаны тизме','label.order.button.addAppliedEntityList','Добавить список'),(617,'listNum','Номери','label.appliedEntityList.table.listNum','Номер'),(618,'listDate','Датасы','label.appliedEntityList.table.listDate','Дата'),(619,'entityListState','Статусу','label.appliedEntityList.table.entityListState','Статус'),(620,'entityListType','Туру','label.appliedEntityList.table.entityListType','Вид'),(621,'listNum','Номери','label.entityList.number','Номер'),(622,'listDate','Датасы','label.entityList.date','Дата'),(623,'entityListState','Статусу','label.entityList.state','Статус'),(624,'entityListType','Туру','label.entityList.type','Вид'),(625,'entityListState','Статусу','label.appliedEntityList.table.entytiListState','Статус'),(626,'entityListType','Туру','label.appliedEntityList.table.entytiListType','Вид'),(627,'addDocumentPackage','Жаны документтер пакети','label.order.button.addDocumentPackage','Добавить пакет документации'),(628,'name','Аталышы','label.orderDocumentPackage.table.name','Наименование'),(629,'add documentPackage','Документтер пакети','label.documentPackage.add','Пакет документации'),(630,'orderDocumentPackage','Документтер пакети','label.add.orderDocumentPackage.title','Пакет документации'),(631,'name','Аталышы','label.orderDocumentPackage.name','Наименование'),(632,'addOrderTerm','Жаны шарт кошуу','label.order.button.addOrderTerm','Добавить условия кредитования'),(633,'descrition','Тушундурмосу','label.orderTerm.table.descrition','Примечание'),(634,'fund','Каржы булагы','label.orderTerm.table.fund','Источник финансирования'),(635,'amount','Суммасы','label.orderTerm.table.amount','Сумма'),(636,'currency','Валютасы','label.orderTerm.table.currency','Валюта'),(637,'frequencyQuantity','Цикл саны','label.orderTerm.table.frequencyQuantity','Кол-во циклов'),(638,'frequencyType','Цикл туру','label.orderTerm.table.frequencyType','Вид цикла'),(639,'installmentQuantity','Период саны','label.orderTerm.table.installmentQuantity','Кол-во периодов'),(640,'installmentFirstDay','Толомдун биринчи куну','label.orderTerm.table.installmentFirstDay','Первый день погашения'),(641,'firstInstallmentDate','Толомдун биринчи датасы','label.orderTerm.table.firstInstallmentDate','Первая дата погашения'),(642,'lastInstalmentdate','Толомдун акыркы датасы','label.orderTerm.table.lastInstalmentdate','Последняя дата погашения'),(643,'minDaysDisbFirstInstallment','Биринчи толомго эн аз кун','label.orderTerm.table.minDaysDisbFirstInstallment','Мин. дней первого пог.'),(644,'maxDaysDisbFirstInstallment','Биринчи толомго эн коп кун','label.orderTerm.table.maxDaysDisbFirstInstallment','Макс. дней первого пог.'),(645,'graceOnPrinciplePaymentInstallment','Негизги карызды толоого женилдетуу периодтору','label.orderTerm.table.graceOnPrinciplePaymentInstallment','Кол-во льготных периодов (пог. осн.с.)'),(646,'graceOnPrinciplePaymentDays','Негизиг карызды толоого женилдетуу кундору','label.orderTerm.table.graceOnPrinciplePaymentDays','Кол-во льготных дней (пог. осн.с.)'),(647,'graceOnInterestPaymentInstallment','пайызды толоого женилдетуу периодтору','label.orderTerm.table.graceOnInterestPaymentInstallment','Кол-во льготных периодов (пог. проц.)'),(648,'graceOnInterestPaymentDays','пайызды толоого женилдетуу кундору','label.orderTerm.table.graceOnInterestPaymentDays','Кол-во льготных дней (пог. проц.)'),(649,'graceOnInterestAcrrInstallment','пайыз саноого женилдетуу периодтору','label.orderTerm.table.graceOnInterestAcrrInstallment','Кол-во льготных периодов (нач.проц.)'),(650,'graceOnInterestAccrDays','пайыз саноого женилдетуу кундору','label.orderTerm.table.graceOnInterestAccrDays','Кол-во льготных дней (нач. проц.)'),(651,'interestRateValue','Процент','label.orderTerm.table.interestRateValue','Процентная ставка'),(652,'interestRateValuePerPeriod','Периодтогу процент','label.orderTerm.table.interestRateValuePerPeriod','Процентная ставка в период'),(653,'interestType','Процент ставкасы','label.orderTerm.table.interestType','Ставка на нач. проц.'),(654,'penaltyOnPrincipleOverdueRateValue','Негизги карызга штраф','label.orderTerm.table.penaltyOnPrincipleOverdueRateValue','Штраф за проср. по осн.с.'),(655,'penaltyOnPrincipleOverdueType','Негизги карызга штраф ставкасы','label.orderTerm.table.penaltyOnPrincipleOverdueType','Ставка на штраф за проср. по осн.с.'),(656,'penaltyOnInterestOverdueType','Процентке штраф ставкасы','label.orderTerm.table.penaltyOnInterestOverdueType','Ставка на штраф за проср. по проц.'),(657,'penaltyOnInterestOverdueRateValue','Процентке штраф','label.orderTerm.table.penaltyOnInterestOverdueRateValue','Штраф за проср. по проц.'),(658,'daysInYearMethod','Бир жылда кун саноо методу','label.orderTerm.table.daysInYearMethod','Метод рас. кол-ва дней в году'),(659,'daysInMonthMethod','Бир айда кун саноо методу','label.orderTerm.table.daysInMonthMethod','Метод рас. кол-ва дней в мес.'),(660,'transactionOrder','Толом ','label.orderTerm.table.transactionOrder','Очередность пог.'),(661,'interestAccrMethod','Процент саноо методу','label.orderTerm.table.interestAccrMethod','Метод нач. проц.'),(662,'earlyRepaymentAllowed','Алдын ала толоо','label.orderTerm.table.earlyRepaymentAllowed','Возм. досроч. пог.'),(663,'penaltyLimitPercent','Штраф чеги','label.orderTerm.table.penaltyLimitPercent','Макс. лимит. нач. штр.'),(664,'collateralFree','Куроодон бошотулган','label.orderTerm.table.collateralFree','Освоб. от зал.об.'),(665,'descrition','Тушундурмосу','label.order.term.description','Примечание'),(666,'fund','Каржы булагы','label.order.term.fund','Источник финансирования'),(667,'amount','Суммасы','label.order.term.amount','Сумма'),(668,'currency','Валютасы','label.order.term.currency','Валюта'),(669,'frequencyQuantity','Цикл саны','label.order.term.freqQuantity','Кол-во циклов'),(670,'frequencyType','Цикл туру','label.order.term.freqType','Вид цикла'),(671,'installmentQuantity','Период саны','label.order.term.installmentQuantity','Кол-во периодов'),(672,'installmentFirstDay','Толомдун биринчи куну','label.order.term.insFirstDay','Первый день погашения'),(673,'firstInstallmentDate','Толомдун биринчи датасы','label.order.term.firstInstallmentDate','Первая дата погашения'),(674,'lastInstalmentdate','Толомдун акыркы датасы','label.order.term.lastInstallmentDate','Последняя дата погашения'),(675,'minDaysDisbFirstInstallment','Биринчи толомго эн аз кун','label.order.term.minDaysDisbFirstInst','Мин. дней первого пог.'),(676,'maxDaysDisbFirstInstallment','Биринчи толомго эн коп кун','label.order.term.maxDaysDisbFirstInst','Макс. дней первого пог.'),(677,'graceOnPrinciplePaymentInstallment','Негизги карызды толоого женилдетуу периодтору','label.order.term.graceOnPrinciplePaymentInst','Кол-во льготных периодов (пог. осн.с.)'),(678,'graceOnPrinciplePaymentDays','Негизиг карызды толоого женилдетуу кундору','label.order.term.graceOnPrinciplePaymentDays','Кол-во льготных дней (пог. осн.с.)'),(679,'graceOnInterestPaymentInstallment','пайызды толоого женилдетуу периодтору','label.order.term.graceOnInterestPaymentInst','Кол-во льготных периодов (пог. проц.)'),(680,'graceOnInterestPaymentDays','пайызды толоого женилдетуу кундору','label.order.term.graceOnInterestPaymentDays','Кол-во льготных дней (пог. проц.)'),(681,'graceOnInterestAcrrInstallment','пайыз саноого женилдетуу периодтору','label.order.term.graceOnInterestAccrInst','Кол-во льготных периодов (нач.проц.)'),(682,'graceOnInterestAccrDays','пайыз саноого женилдетуу кундору','label.order.term.graceOnInterestAccrDays','Кол-во льготных дней (нач. проц.)'),(683,'interestRateValue','Процент','label.order.term.interestRateValue','Процентная ставка'),(684,'interestRateValuePerPeriod','Периодтогу процент','label.order.term.interestRateValuePerPeriod','Процентная ставка в период'),(685,'interestType','Процент ставкасы','label.order.term.interestType','Ставка на нач. проц.'),(686,'penaltyOnPrincipleOverdueRateValue','Негизги карызга штраф','label.order.term.penaltyOnPrincipleOverdueRateValue','Штраф за проср. по осн.с.'),(687,'penaltyOnPrincipleOverdueType','Негизги карызга штраф ставкасы','label.order.term.penaltyOnPrincipleOverdueType','Ставка на штраф за проср. по осн.с.'),(688,'penaltyOnInterestOverdueType','Процентке штраф ставкасы','label.order.term.penaltyOnInterestOverdueType','Ставка на штраф за проср. по проц.'),(689,'penaltyOnInterestOverdueRateValue','Процентке штраф','label.order.term.penaltyOnInterestOverdueRateValue','Штраф за проср. по проц.'),(690,'daysInYearMethod','Бир жылда кун саноо методу','label.order.term.daysInYearMethod','Метод рас. кол-ва дней в году'),(691,'daysInMonthMethod','Бир айда кун саноо методу','label.order.term.daysInMonthMethod','Метод рас. кол-ва дней в мес.'),(692,'transactionOrder','Толом ','label.order.term.transactionOrder','Очередность пог.'),(693,'interestAccrMethod','Процент саноо методу','label.order.term.interestAccrMethod','Метод нач. проц.'),(694,'earlyRepaymentAllowed','Алдын ала толоо','label.order.term.earlyRepaymentAllowed','Возм. досроч. пог.'),(695,'penaltyLimitPercent','Штраф чеги','label.order.term.penaltyLimitPercent','Макс. лимит. нач. штр.'),(696,'collateralFree','Куроодон бошотулган','label.order.term.collateralFree','Освоб. от зал.об.'),(697,'ID','ID','label.order.table.id','ID'),(709,'ID','ID','label.entityDocument.id','ID'),(710,'name','Аталышы','label.entityDocument.name','Наимнеование'),(711,'completed by','Толуктаган','label.entityDocument.completedBy','Укомплектовано'),(712,'completed date','Толукталган датасы','label.entityDocument.completedDate','Дата комплектации'),(713,'completed desc','Тушундурмосу','label.entityDocument.completedDescription','Примечание'),(714,'approved by','Тастыктаган','label.entityDocument.approvedBy','Подтверждено'),(715,'approved date','Тастыыктоо датасы','label.entityDocument.approvedDate','Дата подтверждения'),(716,'approved desc','Тушундурмосу','label.entityDocument.approvedDescription','Примечание'),(717,'reg number','Каттоо номери','label.entityDocument.registeredNumber','Регистрационный номер'),(718,'reg date','Каттоо датасы','label.entityDocument.registeredDate','Дата регистрации'),(719,'reg by','Каттаган','label.entityDocument.registeredBy','Зарегистрировано'),(720,'reg description','Тушундурмосу','label.entityDocument.registeredDescription','Примечание'),(721,'ID','ID','label.document.id','ID'),(722,'name','ID','label.document.name','Наимнеование'),(723,'completedBy','ID','label.document.completedBy','Укомплектовано'),(724,'completedDate','ID','label.document.completedDate','Дата комплектации'),(725,'completedDescription','ID','label.document.completedDescription','Примечание'),(726,'approvedBy','ID','label.document.approvedBy','Подтверждено'),(727,'approvedDate','ID','label.document.approvedDate','Дата подтверждения'),(728,'approvedDescription','ID','label.document.approvedDescription','Примечание'),(729,'registeredNumber','ID','label.document.registeredNumber','Регистрационный номер'),(730,'registeredDate','ID','label.document.registeredDate','Дата регистрации'),(731,'registeredBy','ID','label.document.registeredBy','Зарегистрировано'),(732,'registeredDescription','ID','label.document.registeredDescription','Примечание'),(733,'Add document','Документ кошуу','label.document.add','Добавить документ'),(734,'name','Аталышы','label.documentPackage.name','Наименование'),(735,'completedDate','Толукталган датасы','label.documentPackage.completedDate','Дата комплектации'),(736,'approvedDate','Тастыкталган датасы','label.documentPackage.approvedDate','Дата подтверждения'),(737,'completedRatio','Толтукталганы','label.documentPackage.completedRatio','Доля комплектации'),(738,'approvedRatio','Тастыкталганы','label.documentPackage.approvedRatio','Доля подтверждения'),(739,'registeredRatio','Катталганы','label.documentPackage.registeredRatio','Доля регистрации'),(740,'state','Статусу','label.documentPackage.state','Статус'),(741,'type','Туру','label.documentPackage.type','Вид'),(742,'documents','Документтер','label.documents','Документы'),(743,'orderTerm','Насыя шарты','label.orderTerm.add','Кредитное условие'),(744,'Number','Номери','label.entityList.listNumber','Номер'),(745,'Date','Датасы','label.entityList.listDate','Дата'),(746,'Entities','Алуучулар','label.entities','Получатели'),(747,'Name','Аталышы','label.entity.name','Наименование'),(748,'State','Статусу','label.entity.state','Статус'),(749,'Add new','Жаны алуучу','label.entity.add','Добавить получателя'),(750,'documentPackages','Документ пакеттери','label.documentPackages','Пакет документации'),(751,'State','Статусу','label.document.state','Статус'),(752,'State','Статусу','label.entityDocument.state','Статус'),(753,'Name','Аталышы','label.entity.number','Наименование'),(754,'orderDocuments','Документтер','label.orderDocuments','Документы'),(755,'Name','Аталышы','label.orderDocuments.name','Наименование'),(756,'type','Туру','label.orderDocuments.type','Вид'),(757,'Name','Аталышы','label.orderDocument.name','Наименование'),(758,'type','Туру','label.orderDocument.type','Вид'),(759,'Document','Документ','label.orderDocument.add','Документ'),(760,'type','Туру','label.orderDocument.type','Вид'),(761,'Number','Номери','label.agreement.number','Номер'),(762,'Date','Датасы','label.agreement.date','Дата'),(763,'Reg number','Каттоо номери','label.agreement.collateralOfficeRegNumber','Регистрационный номер'),(764,'Reg Date','Каттоо датасы','label.agreement.collateralOfficeRegDate','Дата регистрации'),(765,'Notary reg number','Нотариус каттоо номери','label.agreement.notaryOfficeRegNumber','Номер нотариальной регистрации'),(766,'Notary reg date','Нотариус каттоо датасы','label.agreement.notaryOfficeRegDate','Дата нотариальной регистрации'),(767,'Arrest reg number','Арест каттоо номери','label.agreement.arrestRegNumber','Номер ареста'),(768,'Arrest reg date','Арест каттоо датасы','label.agreement.arrestRegDate','Дата ареста'),(769,'ID','ID','label.collateralItem.id','ID'),(770,'name','Аталышы','label.collateralItem.name','Наименование'),(771,'description','Тушундурмосу','label.collateralItem.description','Примечание'),(772,'type','Туру','label.collateralItem.type','Вид'),(773,'quantity','Саны','label.collateralItem.quantity','Кол-во'),(774,'quantityType','Бирдиги','label.collateralItem.quantityType','Ед.изм.'),(775,'Collateral value','Куроо баасы','label.collateralItem.collateralValue','Зал.ст.'),(776,'Estimated value','Бычылган баасы','label.collateralItem.estimatedValue','Оценоч.ст.'),(777,'Condition','Акыбалы','label.collateralItem.conditionType','Состояние'),(778,'№1','№1','label.collateralItem.details1','№1'),(779,'№2','№2','label.collateralItem.details2','№2'),(780,'№3','№3','label.collateralItem.details3','№3'),(781,'№4','№4','label.collateralItem.details4','№4'),(782,'№5','№5','label.collateralItem.details5','№5'),(783,'№6','№6','label.collateralItem.details6','№6'),(784,'document','документ','label.collateralItem.document','документ'),(785,'incomplete_reason','Себеби','label.collateralItem.incomplete_reason','Причина'),(786,'goods_type','Туру','label.collateralItem.goods_type','вид'),(787,'goods_address','Адреси','label.collateralItem.goods_address','адрес'),(788,'goods_id','Коду','label.collateralItem.goods_id','ид код'),(789,'arrest_by','Камакка алган','label.collateralItem.arrest_by','наложен арест'),(790,'name','Аталышы','label.agreementItem.name','Наименование'),(791,'description','Тушундурмосу','label.agreementItem.description','Примечание'),(792,'type','Туру','label.agreementItem.itemType','Вид'),(793,'quantity','Саны','label.agreementItem.quantity','Кол-во'),(794,'quantityType','Бирдиги','label.agreementItem.quantityType','Ед.изм.'),(795,'Collateral value','Куроо баасы','label.agreementItem.collateralValue','Зал.ст.'),(796,'Estimated value','Бычылган баасы','label.agreementItem.estimatedValue','Оценоч.ст.'),(797,'Condition','Акыбалы','label.agreementItem.conditionType','Состояние'),(798,'Add item','Куроо кошуу','label.agreement.addItem','Добавить предмет залога'),(799,'arrestFreeInfo','Куроодон чыгаруу маалыматы','label.collateralItem.arrestFreeInfo','Информация о снятии с залога'),(800,'Add arrest free','Куроодон чыгаруу','label.arrestFreeInfo.add','Снять с залога'),(801,'on Date','Куроо чыккан датасы','label.arrestFreeInfo.onDate','Дата снятия'),(802,'Arrest free by','Куроодон чыгарган','label.arrestFreeInfo.arrestFreeBy','Снято'),(803,'Add inspection','Куроо текшеруусун кошуу','label.itemInspectionResult.add','Добавить акт обследования'),(804,'Date','Датасы','label.inpection.onDate','Дата обследования'),(805,'result','Жыйынтыгы','label.inpection.type','Результат'),(806,'Date','Датасы','label.arrestFree.onDate','Дата'),(807,'arrestFreeBy','Куроодон чыгарган','label.arrestFree.arrestFreeBy','Снято'),(808,'Inspection Result','Куроо текшеруусу','label.inspectionResult.add','Акт обследования'),(809,'Date','Датасы','label.inspectionResult.onDate','Дата'),(810,'Details','Тушундурмосу','label.inspectionResult.details','Примечание'),(811,'Type','Жыйынтыгы','label.inspectionResult.type','Результат'),(812,'Arrest free','Куроодон чыгаруу','label.arrestFree.add','Снятие залога');
/*!40000 ALTER TABLE `message_resource` ENABLE KEYS */;
UNLOCK TABLES;


















