
# VIEWS



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
    `co`.`regNumber`               AS `v_credit_order_reg_number`,
    `co`.`regDate`                 AS `v_credit_order_reg_date`
  FROM ((`mfloan`.`loan` `l`
    JOIN `mfloan`.`debtor_view` `dv` ON ((`dv`.`v_debtor_id` = `l`.`debtorId`))) JOIN `mfloan`.`creditOrder` `co`
      ON ((`co`.`id` = `l`.`creditOrderId`)))
  WHERE (isnull(`l`.`parent_id`) AND (`l`.`loanStateId` = 2));

DROP TABLE IF EXISTS loan_last_date_view;
CREATE VIEW loan_last_date_view AS
  SELECT
    `lv`.`v_loan_id` AS `v_loan_id`,
    (SELECT `mfloan`.`paymentSchedule`.`expectedDate`
     FROM `mfloan`.`paymentSchedule`
     WHERE ((`mfloan`.`paymentSchedule`.`loanId` = `lv`.`v_loan_id`) AND
            (`mfloan`.`paymentSchedule`.`principalPayment` > 0))
     ORDER BY `mfloan`.`paymentSchedule`.`expectedDate` DESC
     LIMIT 1)        AS `last_date`
  FROM `mfloan`.`loan_view` `lv`;

DROP TABLE IF EXISTS loan_summary_view;
CREATE VIEW loan_summary_view AS
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
    `ls`.`loanId`                    AS `v_ls_loan_id`,
    `ldv`.`last_date`                AS `v_last_date`
  FROM ((`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`loanSummary` `ls`) JOIN `mfloan`.`loan_last_date_view` `ldv`)
  WHERE ((`ls`.`loanId` = `lv`.`v_loan_id`) AND (`ls`.`loanId` = `ldv`.`v_loan_id`));

DROP TABLE IF EXISTS payment_schedule_view;
CREATE VIEW payment_schedule_view AS
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

DROP TABLE IF EXISTS payment_view;
CREATE VIEW payment_view AS
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

DROP TABLE IF EXISTS loan_accrue_view;
CREATE VIEW loan_accrue_view AS
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

DROP TABLE IF EXISTS loan_debt_transfer_view;
CREATE VIEW loan_debt_transfer_view AS
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

DROP TABLE IF EXISTS loan_write_off_view;
CREATE VIEW loan_write_off_view AS
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

DROP TABLE IF EXISTS supervisor_plan_view;
CREATE VIEW supervisor_plan_view AS
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

DROP TABLE IF EXISTS collateral_arrest_free_view;
CREATE VIEW collateral_arrest_free_view AS
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

DROP TABLE IF EXISTS collateral_inspection_view;
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
    `cir`.`id`                             AS `v_cir_id`,
    `cir`.`details`                        AS `v_cir_details`,
    `cir`.`onDate`                         AS `v_cir_onDate`,
    `cir`.`inspectionResultTypeId`         AS `v_cir_inspectionResultTypeId`
  FROM (`mfloan`.`collateral_item_view` `civ`
    JOIN `mfloan`.`collateralItemInspectionResult` `cir`)
  WHERE (`cir`.`collateralItemId` = `civ`.`v_ci_id`);

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
              'collection_procedure_status' AS `list_type`,
              `tbl`.`id`                    AS `id`,
              `tbl`.`name`                  AS `name`
            FROM `mfloan`.`procedureStatus` `tbl`;





# DATA

# output params
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
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (274, 'ширина 2. столбца', 'COLUMN_WIDTH', 2, '', 6);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (275, 'ширина 3. столбца', 'COLUMN_WIDTH', 3, '', 40);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (276, 'ширина 2. столбца (кол-во)', 'COLUMN_WIDTH', 2, '', 6);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (277, 'ширина 3. столбца (кол-во)', 'COLUMN_WIDTH', 3, '', 6);
INSERT INTO mfloan.output_parameter (id, name, outputParameterType, position, text, value) VALUES (278, 'ширина 4. столбца (наименование)', 'COLUMN_WIDTH', 4, '', 40);

# group type
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (1, null, null, null, 'v_debtor_region_id', 'Область', '(=regionMap(v_debtor_region_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (2, null, null, null, 'v_debtor_district_id', 'Район', '(=districtMap(v_debtor_district_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (3, null, null, null, 'v_debtor_work_sector_id', 'Отрасль', '(=work_sectorMap(v_debtor_work_sector_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (4, null, null, null, 'v_loan_type_id', 'Вид кредита', '(=loan_typeMap(v_loan_type_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (5, null, null, null, 'v_loan_supervisor_id', 'Куратор', '(=supervisorMap(v_loan_supervisor_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (6, null, null, null, 'v_loan_department_id', 'Отдел', '(=departmentMap(v_loan_department_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (7, null, null, null, 'v_debtor_id', 'Заемщик', '(=v_debtor_name=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (8, null, null, null, 'v_loan_id', 'Кредит', '(=v_loan_reg_number=) от (=v_loan_reg_date=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (9, null, null, null, 'v_ls_id', 'Расчет', 'Расчет');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (10, null, null, null, 'v_payment_id', 'Погашение', 'Погашение');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (11, null, null, null, 'v_ps_id', 'График', 'График');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (12, null, null, null, 'v_credit_order_id', 'Решение о выдаче кредита', '(=credit_orderMap(v_credit_order_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (13, null, null, null, 'v_applied_entity_id', 'Получатель', '(=v_owner_name=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (14, null, null, null, 'v_ael_id', 'Список получателей', '(=v_ael_listNumber=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (15, null, null, null, 'v_document_package_id', 'Пакет документации', '(=v_document_package_name=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (16, null, null, null, 'v_entity_document_id', 'Документ (оформление)', '(=v_entity_document_name=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (17, null, null, null, 'v_ca_id', 'Договор залога', '(=v_ca_agreementNumber=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (18, null, null, null, 'v_ci_id', 'Предмет залога', '(=v_ci_name=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (19, null, null, null, 'v_cp_id', 'Процедура взыскания', 'Процедура взыскания');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (20, null, null, null, 'v_cph_id', 'Фаза взыскания', '');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (21, null, null, null, 'v_applied_entity_appliedEntityStateId', 'Статус получателя', '(=applied_entity_statusMap(v_applied_entity_appliedEntityStateId)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (22, null, null, null, 'v_entity_document_entityDocumentStateId', 'Статус документа(оформление)', '(=entity_document_statusMap(v_entity_document_entityDocumentStateId)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (23, null, null, null, 'v_document_package_documentPackageStateId', 'Статус пакета док.', '(=document_package_statusMap(v_document_package_documentPackageStateId)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (24, null, null, null, 'v_entity_document_completedBy', 'Укомплектовано', '(=supervisorMap(v_entity_document_completedBy)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (25, null, null, null, 'v_entity_document_approvedBy', 'Проверено', '(=supervisorMap(v_entity_document_approvedBy)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (26, null, null, null, 'v_owner_region_id', 'Область (получатель)', '(=regionMap(v_owner_region_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (27, null, null, null, 'v_owner_district_id', 'Район (получатель)', '(=districtMap(v_owner_district_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (28, null, null, null, 'v_cph_phaseTypeId', 'Вид стадии', '(=collection_phase_typeMap(v_cph_phaseTypeId)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (29, null, null, null, 'v_cph_phaseStatusId', 'Вид результата', '(=collection_phase_statusMap(v_cph_phaseStatusId)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (30, null, null, null, 'v_cp_procedureStatusId', 'Статус процедуры', '(=collection_procedure_statusMap(v_cp_procedureStatusId)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (31, null, null, null, 'v_payment_type_id', 'Вид погашения', '(=payment_typeMap(v_payment_type_id)=)');
INSERT INTO mfloan.group_type (id, object_external_id, object_id, object_type, field_name, name, row_name) VALUES (32, null, null, null, 'v_installment_status_id', 'Статус графика', '(=installment_statusMap(v_installment_status_id)=)');

#object list
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (1, 'По республике ( все области)', 1, 1);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (2, 'По Баткенской области', 1, 1);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (3, 'По Нарынской области', 1, 1);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (4, 'По Таласской области', 1, 1);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (5, 'По Джалал-Абадской области', 1, 1);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (6, 'По Ошской области', 1, 1);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (7, 'По Чуйской области', 1, 1);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (8, 'По Иссык-Кульской области', 1, 1);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (9, 'По Баткенскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (10, 'По Ляйлякскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (11, 'По Кадамжайскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (12, 'По Алайскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (13, 'По Араванскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (14, 'По Кара-Кульджинскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (15, 'По Кара-Суйскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (16, 'По Ноокатскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (17, 'По Узгенскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (18, 'По Чон-Алайскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (19, 'По городу Ош', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (20, 'По Аксыйскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (21, 'По Алабукинскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (22, 'По Базар-Коргонскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (23, 'По Ноокенскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (24, 'По Сузакскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (25, 'По Тогуз-Тороузскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (26, 'По Токтогульскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (27, 'По Чаткальскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (28, 'По городу Жалал-Абад', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (29, 'По Аксуйскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (30, 'По Джетиогузскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (31, 'По Иссык-Кульскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (32, 'По Тонскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (33, 'По Тюпскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (34, 'По Бакай-Атинскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (35, 'По Кара-Бууринскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (36, 'По Манасскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (37, 'По Таласскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (38, 'По Аламудунскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (39, 'По Кеминскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (40, 'По Московскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (41, 'По Панфиловскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (42, 'По Сокулукскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (43, 'По Чуйскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (44, 'По Ыссык-Атинскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (45, 'По Жайылскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (46, 'По Акталинскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (47, 'По Ат-Башынскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (48, 'По Джумгальскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (49, 'По Кочкорскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (50, 'По Нарынскому району', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (51, 'По городу Бишкек', 2, 2);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (52, 'По всем отраслям', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (53, 'По АПК', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (54, 'По отраслям промышленности', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (55, 'По всем видам кредита', 4, 4);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (56, 'По бюджетным ссудам и иностранным кредитам', 4, 4);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (57, 'По грантам', 4, 4);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (58, 'По ГМР', 4, 4);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (59, 'По им. активам', 4, 4);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (60, 'По энергетическому комплексу', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (61, 'По строительному комплексу', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (62, 'По легкой промышленности', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (63, 'По пищевой и пераб. промыiленности', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (64, 'По банкам', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (65, 'По Угледоб. отрасли', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (66, 'По здравоохранению', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (67, 'По Машиностроению', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (68, 'По транспорту и связи', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (69, 'По предприятиям прочих отраслей', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (70, 'По водному хозяйству', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (71, 'По частному предпринимательству', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (72, 'по ЖСК', 3, 3);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (73, 'Баткенская область (оформление)', 26, 26);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (74, 'г. Бишкек (оформление)', 26, 26);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (75, 'Джалал-Абадская область (оформление)', 26, 26);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (76, 'Иссык-Кульская область (оформление)', 26, 26);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (77, 'Нарынская область (оформление)', 26, 26);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (78, 'Ошская область (оформление)', 26, 26);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (79, 'Таласская область (оформление)', 26, 26);
INSERT INTO mfloan.object_list (id, name, object_type_id, group_type_id) VALUES (80, 'Чуйская область (оформление)', 26, 26);

#object list values
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (1, '8', 1);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (2, '3', 1);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (3, '6', 1);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (4, '1', 1);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (5, '9', 1);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (6, '4', 1);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (7, '2', 1);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (8, '5', 1);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (9, '1', 2);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (10, '5', 3);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (11, '8', 4);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (12, '3', 5);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (13, '6', 6);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (15, '4', 8);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (18, '9', 7);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (19, '7', 7);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (20, '2', 7);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (23, '32', 11);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (24, '11', 9);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (25, '63', 9);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (26, '5', 12);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (27, '7', 13);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (29, '34', 14);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (31, '43', 16);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (32, '58', 17);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (33, '60', 18);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (34, '23', 19);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (35, '18', 15);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (36, '35', 15);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (37, '2', 20);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (38, '4', 21);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (39, '9', 22);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (42, '54', 25);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (44, '59', 27);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (45, '14', 28);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (46, '17', 26);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (47, '55', 26);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (48, '19', 24);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (49, '52', 24);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (50, '21', 23);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (51, '26', 23);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (52, '44', 23);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (53, '24', 10);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (54, '39', 10);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (55, '1', 29);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (56, '28', 30);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (57, '12', 31);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (58, '31', 31);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (59, '56', 32);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (60, '57', 33);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (61, '10', 34);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (62, '33', 35);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (63, '40', 36);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (64, '25', 37);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (65, '53', 37);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (66, '6', 38);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (67, '36', 39);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (68, '41', 40);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (69, '46', 41);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (70, '51', 42);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (71, '61', 43);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (73, '30', 45);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (74, '3', 46);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (75, '8', 47);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (76, '29', 48);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (77, '37', 49);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (79, '22', 50);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (80, '42', 50);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (81, '62', 44);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (82, '64', 44);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (83, '13', 51);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (84, '38', 51);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (85, '45', 51);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (86, '48', 51);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (87, '50', 51);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (88, '5', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (89, '1', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (90, '2', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (91, '14', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (92, '3', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (93, '4', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (94, '6', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (95, '7', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (96, '8', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (97, '9', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (98, '10', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (99, '13', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (100, '11', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (101, '12', 52);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (102, '1', 53);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (103, '3', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (104, '2', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (105, '4', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (106, '5', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (107, '6', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (108, '7', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (109, '8', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (110, '9', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (111, '13', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (112, '14', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (113, '10', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (114, '11', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (115, '12', 54);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (116, '1', 55);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (117, '2', 55);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (118, '3', 55);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (119, '4', 55);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (120, '5', 55);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (121, '6', 55);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (122, '7', 55);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (123, '8', 55);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (124, '9', 55);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (127, '3', 57);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (128, '6', 57);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (129, '7', 57);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (130, '8', 57);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (131, '9', 58);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (132, '5', 59);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (133, '1', 56);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (134, '2', 56);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (135, '9', 56);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (136, '2', 60);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (137, '3', 61);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (138, '4', 62);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (140, '5', 63);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (141, '6', 64);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (142, '7', 65);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (143, '8', 66);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (144, '9', 67);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (145, '10', 68);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (146, '11', 69);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (147, '12', 70);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (148, '13', 71);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (149, '14', 72);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (150, '1', 73);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (151, '2', 74);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (152, '3', 75);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (153, '4', 76);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (154, '5', 77);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (155, '6', 78);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (156, '8', 79);
INSERT INTO mfloan.object_list_value (id, name, object_list_id) VALUES (157, '9', 80);

#filter params
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (1, 'EQUALS', '', '', 'OBJECT_LIST', 'По Баткенской области', 2);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (2, 'EQUALS', '', '', 'OBJECT_LIST', 'По Нарынской области', 3);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (3, 'EQUALS', '', '', 'OBJECT_LIST', 'По Таласской области', 4);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (4, 'EQUALS', '', '', 'OBJECT_LIST', 'По Жалал-Абадской области', 5);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (5, 'EQUALS', '', '', 'OBJECT_LIST', 'По Ошской области', 6);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (6, 'EQUALS', '', '', 'OBJECT_LIST', 'По Чуйской области', 7);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (7, 'EQUALS', '', '', 'OBJECT_LIST', 'По Иссык-Кульской области', 8);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (8, 'EQUALS', '', '', 'OBJECT_LIST', 'По Баткенскому району', 9);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (9, 'EQUALS', '', '', 'OBJECT_LIST', 'По Ляйлякскому району', 10);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (10, 'EQUALS', '', '', 'OBJECT_LIST', 'По Кадамжайскому району', 11);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (11, 'EQUALS', '', '', 'OBJECT_LIST', 'По Алайскому району', 12);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (12, 'EQUALS', '', '', 'OBJECT_LIST', 'По Араванскому району', 13);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (13, 'EQUALS', '', '', 'OBJECT_LIST', 'По Кара-Кульджинскому району', 14);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (14, 'EQUALS', '', '', 'OBJECT_LIST', 'По Кара-Суйскому району', 15);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (15, 'EQUALS', '', '', 'OBJECT_LIST', 'По Ноокатскому району', 16);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (16, 'EQUALS', '', '', 'OBJECT_LIST', 'По Узгенскому району', 17);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (17, 'EQUALS', '', '', 'OBJECT_LIST', 'По Чон-Алайскому району', 18);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (18, 'EQUALS', '', '', 'OBJECT_LIST', 'По городу Ош', 19);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (19, 'EQUALS', '', '', 'OBJECT_LIST', 'По Аксыйскому району', 20);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (20, 'EQUALS', '', '', 'OBJECT_LIST', 'По Ала-Букинскому району', 21);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (21, 'EQUALS', '', '', 'OBJECT_LIST', 'По Базар-Коргонскому району', 22);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (22, 'EQUALS', '', '', 'OBJECT_LIST', 'По Ноокенскому району', 23);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (23, 'EQUALS', '', '', 'OBJECT_LIST', 'По Сузакскому району', 24);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (24, 'EQUALS', '', '', 'OBJECT_LIST', 'По Тогуз-Тороузскому району', 25);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (25, 'EQUALS', '', '', 'OBJECT_LIST', 'По Токтогульскому району', 26);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (26, 'EQUALS', '', '', 'OBJECT_LIST', 'По Чаткальскому району', 27);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (27, 'EQUALS', '', '', 'OBJECT_LIST', 'По Чаткальскому району', 27);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (28, 'EQUALS', '', '', 'OBJECT_LIST', 'По городу Жалал-Абад', 28);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (29, 'EQUALS', '', '', 'OBJECT_LIST', 'По Аксуйскому району', 29);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (30, 'EQUALS', '', '', 'OBJECT_LIST', 'По Джети-Огузскому району', 30);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (31, 'EQUALS', '', '', 'OBJECT_LIST', 'По Иссык-Кульскому району', 31);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (32, 'EQUALS', '', '', 'OBJECT_LIST', 'По Тонскому району', 32);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (33, 'EQUALS', '', '', 'OBJECT_LIST', 'По Тюпскому району', 33);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (34, 'EQUALS', '', '', 'OBJECT_LIST', 'По Бакай-Атинскому району', 34);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (35, 'EQUALS', '', '', 'OBJECT_LIST', 'По Кара-Бууринскому району', 35);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (36, 'EQUALS', '', '', 'OBJECT_LIST', 'По Манасскому району', 36);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (37, 'EQUALS', '', '', 'OBJECT_LIST', 'По Таласскому району', 37);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (38, 'EQUALS', '', '', 'OBJECT_LIST', 'По Аламудунскому району', 38);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (39, 'EQUALS', '', '', 'OBJECT_LIST', 'По Кеминскому району', 39);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (40, 'EQUALS', '', '', 'OBJECT_LIST', 'По Московскому району', 40);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (41, 'EQUALS', '', '', 'OBJECT_LIST', 'По Панфиловскому району', 41);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (42, 'EQUALS', '', '', 'OBJECT_LIST', 'По Сокулукскому району', 42);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (43, 'EQUALS', '', '', 'OBJECT_LIST', 'По Чуйскому району', 43);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (44, 'EQUALS', '', '', 'OBJECT_LIST', 'По Ыссык-Атинскому району', 44);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (45, 'EQUALS', '', '', 'OBJECT_LIST', 'По Жайылскому району', 45);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (46, 'EQUALS', '', '', 'OBJECT_LIST', 'По Акталинскому району', 46);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (47, 'EQUALS', '', '', 'OBJECT_LIST', 'По Атбашинскому району', 47);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (48, 'EQUALS', '', '', 'OBJECT_LIST', 'По Джумгальскому району', 48);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (49, 'EQUALS', '', '', 'OBJECT_LIST', 'По Кочкорскому району', 49);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (50, 'EQUALS', '', '', 'OBJECT_LIST', 'По Нарынскому району', 50);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (51, 'EQUALS', '', '', 'OBJECT_LIST', 'По городу Бишкек', 51);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (52, 'EQUALS', '', '', 'OBJECT_LIST', 'По АПК', 53);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (53, 'EQUALS', '', '', 'OBJECT_LIST', 'По отраслям промышленности', 54);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (54, 'EQUALS', '', '', 'OBJECT_LIST', 'По бюджетным ссудам и ИК', 56);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (55, 'EQUALS', '', '', 'OBJECT_LIST', 'По грантам АПК', 57);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (56, 'EQUALS', '', '', 'OBJECT_LIST', 'По им. активам', 59);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (57, 'EQUALS', '', '', 'OBJECT_LIST', 'По энергетическому комплексу', 60);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (58, 'EQUALS', '', '', 'OBJECT_LIST', 'По строительному комплексу', 61);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (59, 'EQUALS', '', '', 'OBJECT_LIST', 'по легкой промышленности', 62);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (60, 'EQUALS', '', '', 'OBJECT_LIST', 'по пищевой и перераб. пром.', 63);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (61, 'EQUALS', '', '', 'OBJECT_LIST', 'по банкам', 64);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (62, 'EQUALS', '', '', 'OBJECT_LIST', 'по угледоб. пром.', 65);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (63, 'EQUALS', '', '', 'OBJECT_LIST', 'по здравоохранению', 66);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (64, 'EQUALS', '', '', 'OBJECT_LIST', 'по машиностроению', 67);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (65, 'EQUALS', '', '', 'OBJECT_LIST', 'по транспорту и связи', 68);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (66, 'EQUALS', '', '', 'OBJECT_LIST', 'По прочим отраслям', 69);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (67, 'EQUALS', '', '', 'OBJECT_LIST', 'по водным хоз.', 70);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (68, 'EQUALS', '', '', 'OBJECT_LIST', 'по част. предринимательству', 71);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (69, 'EQUALS', '', '', 'OBJECT_LIST', 'по ЖСК', 72);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (70, 'EQUALS', '', '', 'OBJECT_LIST', 'Чуйская область (оформление)', 80);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (71, 'EQUALS', '', '', 'OBJECT_LIST', 'Таласская область (оформление)', 79);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (72, 'EQUALS', '', '', 'OBJECT_LIST', 'Ошская область (оформление)', 78);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (73, 'EQUALS', '', '', 'OBJECT_LIST', 'Нарынская область (оформление)', 77);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (74, 'EQUALS', '', '', 'OBJECT_LIST', 'Иссык-Кульская область (оформление)', 76);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (75, 'EQUALS', '', '', 'OBJECT_LIST', 'Джалал-Абадская область (оформление)', 75);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (76, 'EQUALS', '', '', 'OBJECT_LIST', 'г.Бишкек (оформление)', 74);
INSERT INTO mfloan.filter_parameter (id, comparator, compared_value, field_name, filterParameterType, name, object_list_id) VALUES (77, 'EQUALS', '', '', 'OBJECT_LIST', 'Баткенская область (оформление)', 73);

#content params
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
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (158, 'TEXT', 0, 0, 1, 7, null, 0, 'Информация по задолженности', 0, 'CONSTANT', '', 'Задолженность. Заголовок страницы. Строка 1. ', 1, 'PAGE_TITLE', 1, 1, 0, 0);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (159, 'TEXT', 0, 0, 1, 7, null, 0, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Задолженность. Заголовок страницы. Строка 2.', 1, 'PAGE_TITLE', 2, 2, 0, 0);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (160, 'TEXT', 0, 0, 1, 1, null, 0, 'Кол-во суб-тов.', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Кол-во субъектов', 1, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (161, 'TEXT', 0, 0, 2, 2, null, 0, 'Кол-во кред-тов', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Кол-во кредитов', 2, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (162, 'TEXT', 0, 0, 3, 3, null, 0, 'Наименование', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Наименование', 3, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (163, 'TEXT', 0, 0, 4, 4, null, 0, 'Вид кредита', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Вид кредита', 4, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (164, 'TEXT', 0, 0, 5, 5, null, 0, 'Сумма по договору', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Сумма по договору', 5, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (165, 'TEXT', 0, 0, 6, 6, null, 0, 'Всего получено', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Всего получено', 6, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (166, 'TEXT', 0, 0, 7, 7, null, 0, 'Срок возврата', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Срок возврата', 7, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (167, 'TEXT', 0, 0, 8, 8, null, 0, 'Всего погашено', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Всего погашено', 8, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (168, 'TEXT', 0, 0, 9, 9, null, 0, 'Всего остаток', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Всего остаток', 9, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (169, 'TEXT', 0, 0, 10, 12, null, 0, 'в том числе', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Всего задолженность в том числе', 4, 'TABLE_HEADER', 5, 5, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (170, 'TEXT', 0, 0, 10, 10, null, 0, 'по основной сумме', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. По основной сумме', 11, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (171, 'TEXT', 0, 0, 11, 11, null, 0, 'по процентам', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. По процентам', 12, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (172, 'TEXT', 0, 0, 12, 12, null, 0, 'по штрафам', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. По по штрафам', 13, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (173, 'TEXT', 0, 0, 13, 13, null, 0, 'Просроченная задолженность', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Просроченная задолженность', 14, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (174, 'TEXT', 0, 0, 14, 16, null, 0, 'в том числе', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Просроченная задолженность в том числе', 15, 'TABLE_HEADER', 5, 5, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (175, 'TEXT', 0, 0, 14, 14, null, 0, 'по основной сумме', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. По основной сумме', 16, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (176, 'TEXT', 0, 0, 15, 15, null, 0, 'по процентам', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. По процентам', 17, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (177, 'TEXT', 0, 0, 16, 16, null, 0, 'по штрафам', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. По по штрафам', 18, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (178, 'TEXT', 0, 0, 17, 17, null, 0, 'Примечание', 0, 'CONSTANT', '', 'Задолженность. Шапка таблицы. Примечание', 19, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (179, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Count', 'Звдолженность. Итого. Кол-во субъектов', 1, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (180, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'DetailsCount', 'Звдолженность. Итого. Кол-во кредитов', 2, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (181, 'TEXT', 0, 0, 0, 0, null, 0, 'ИТОГО', 0, 'CONSTANT', '', 'Звдолженность. Итого. Итого', 3, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (183, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'LoanAmount', 'Звдолженность. Итого. Сумма по договору', 5, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (184, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalDisbursment', 'Звдолженность. Итого. Всего получено', 6, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (185, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalPaid', 'Звдолженность. Итого. Всего погашено', 8, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (186, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingSum', 'Задолженность. Итого. Остаток задолженности', 9, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (187, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPrincipal', 'Задолженность. Итого. Остаток по основной сумме', 10, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (188, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingInterest', 'Задолженность. Итого. Остаток по процентам', 11, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (189, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPenalty', 'Задолженность. Итого. Остаток по штрафам', 12, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (190, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueAll', 'Задолженность. Итого. Просроченная задолженность', 13, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (191, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePrincipal', 'Задолженность. Итого. Просрочка по основной сумме', 14, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (192, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueInterest', 'Задолженность. Итого. Просрочка по процентам', 15, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (193, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePenalty', 'Задолженность. Итого. Просрочка по штрафам', 16, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (194, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Count', 'Задолженность. Разрез1. Кол-во субъектов', 1, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (195, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'DetailsCount', 'Задолженность. Разрез1. Кол-во кредитов', 2, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (196, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Name', 'Задолженность. Разрез1. Наименование', 3, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (197, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'LoanAmount', 'Задолженность. Разрез1. Сумма по договору', 5, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (198, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalDisbursment', 'Задолженность. Разрез1. Всего получено', 6, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (199, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalPaid', 'Задолженность. Разрез1. Всего погашено', 8, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (200, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingSum', 'Задолженность. Разрез1. Остаток задолженности', 9, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (201, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPrincipal', 'Задолженность. Разрез1. Остаток по основной сумме', 10, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (202, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingInterest', 'Задолженность. Разрез1. Остаток по процентам', 11, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (203, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPenalty', 'Задолженность. Разрез1. Остаток по штрафам', 12, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (204, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueAll', 'Задолженность. Разрез1. Просроченная задолженность', 13, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (205, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePrincipal', 'Задолженность. Разрез1. Просрочка по основной сумме', 14, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (206, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueInterest', 'Задолженность. Разрез1. Просрочка по процентам', 15, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (207, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePenalty', 'Задолженность. Разрез1. Просрочка по штрафам', 16, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (208, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Count', 'Задолженность. Разрез 2. Кол-во субъектов', 1, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (209, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'DetailsCount', 'Задолженность. Разрез 2. Кол-во кредитов', 2, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (210, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Name', 'Задолженность. Разрез 2. Наименование', 3, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (211, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'LoanAmount', 'Задолженность. Разрез 2. Сумма по договору', 5, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (212, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalDisbursment', 'Задолженность. Разрез 2. Всего получено', 6, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (213, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalPaid', 'Задолженность. Разрез 2. Всего погашено', 8, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (214, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingSum', 'Задолженность. Разрез 2. Остаток задолженности', 9, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (215, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPrincipal', 'Задолженность. Разрез 2. Остаток по основной сумме', 10, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (216, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingInterest', 'Задолженность. Разрез 2. Остаток по процентам', 11, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (217, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPenalty', 'Задолженность. Разрез 2. Остаток по штрафам', 12, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (218, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueAll', 'Задолженность. Разрез 2. Просроченная задолженность', 13, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (219, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePrincipal', 'Задолженность. Разрез 2. Просрочка по основной сумме', 14, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (220, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueInterest', 'Задолженность. Разрез 2. Просрочка по процентам', 15, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (221, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePenalty', 'Задолженность. Разрез 2. Просрочка по штрафам', 16, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (222, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Count', 'Задолженность. Разрез 3. Кол-во субъектов', 1, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (223, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'DetailsCount', 'Задолженность. Разрез 3. Кол-во кредитов', 2, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (224, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Name', 'Задолженность. Разрез 3. Наименование', 3, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (225, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'LoanAmount', 'Задолженность. Разрез 3. Сумма по договору', 5, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (226, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalDisbursment', 'Задолженность. Разрез 3. Всего получено', 6, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (227, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalPaid', 'Задолженность. Разрез 3. Всего погашено', 8, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (228, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingSum', 'Задолженность. Разрез 3. Остаток задолженности', 9, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (229, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPrincipal', 'Задолженность. Разрез 3. Остаток по основной сумме', 10, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (230, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingInterest', 'Задолженность. Разрез 3. Остаток по процентам', 11, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (231, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPenalty', 'Задолженность. Разрез 3. Остаток по штрафам', 12, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (232, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueAll', 'Задолженность. Разрез 3. Просроченная задолженность', 13, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (233, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePrincipal', 'Задолженность. Разрез 3. Просрочка по основной сумме', 14, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (234, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueInterest', 'Задолженность. Разрез 3. Просрочка по процентам', 15, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (235, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePenalty', 'Задолженность. Разрез 3. Просрочка по штрафам', 16, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (236, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Count', 'Задолженность. Разрез 4. Кол-во субъектов', 1, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (237, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'DetailsCount', 'Задолженность. Разрез 4. Кол-во кредитов', 2, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (238, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Name', 'Задолженность. Разрез 4. Наименование', 3, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (239, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'LoanAmount', 'Задолженность. Разрез 4. Сумма по договору', 5, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (240, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalDisbursment', 'Задолженность. Разрез 4. Всего получено', 6, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (241, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalPaid', 'Задолженность. Разрез 4. Всего погашено', 8, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (242, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingSum', 'Задолженность. Разрез 4. Остаток задолженности', 9, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (243, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPrincipal', 'Задолженность. Разрез 4. Остаток по основной сумме', 10, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (244, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingInterest', 'Задолженность. Разрез 4. Остаток по процентам', 11, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (245, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPenalty', 'Задолженность. Разрез 4. Остаток по штрафам', 12, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (246, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueAll', 'Задолженность. Разрез 4. Просроченная задолженность', 13, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (247, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePrincipal', 'Задолженность. Разрез 4. Просрочка по основной сумме', 14, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (248, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueInterest', 'Задолженность. Разрез 4. Просрочка по процентам', 15, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (249, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePenalty', 'Задолженность. Разрез 4. Просрочка по штрафам', 16, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (250, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Count', 'Задолженность. Разрез 5. Кол-во субъектов', 1, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (251, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'DetailsCount', 'Задолженность. Разрез 5. Кол-во кредитов', 2, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (252, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'Name', 'Задолженность. Разрез 5. Наименование', 3, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (253, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'LoanAmount', 'Задолженность. Разрез 5. Сумма по договору', 5, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (254, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalDisbursment', 'Задолженность. Разрез 5. Всего получено', 6, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (255, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'TotalPaid', 'Задолженность. Разрез 5. Всего погашено', 8, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (256, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingSum', 'Задолженность. Разрез 5. Остаток задолженности', 9, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (257, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPrincipal', 'Задолженность. Разрез 5. Остаток по основной сумме', 10, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (258, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingInterest', 'Задолженность. Разрез 5. Остаток по процентам', 11, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (259, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'RemainingPenalty', 'Задолженность. Разрез 5. Остаток по штрафам', 12, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (260, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueAll', 'Задолженность. Разрез 5. Просроченная задолженность', 13, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (261, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePrincipal', 'Задолженность. Разрез 5. Просрочка по основной сумме', 14, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (262, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverdueInterest', 'Задолженность. Разрез 5. Просрочка по процентам', 15, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (263, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SUMMARY', 'OverduePenalty', 'Задолженность. Разрез 5. Просрочка по штрафам', 16, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (264, 'TEXT', 0, 0, 1, 7, null, 0, 'Информация по погашениям', 0, 'CONSTANT', '', 'Погашения. Заголовок страницы. Строка 1. ', 1, 'PAGE_TITLE', 1, 1, 0, 0);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (265, 'TEXT', 0, 0, 1, 7, null, 0, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Погашения. Заголовок страницы. Строка 2.', 1, 'PAGE_TITLE', 2, 2, 0, 0);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (266, 'TEXT', 0, 0, 1, 1, null, 0, 'Кол-во суб-тов.', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Кол-во субъектов', 1, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (267, 'TEXT', 0, 0, 2, 2, null, 0, 'Кол-во кред-тов', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Кол-во кредитов', 2, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (268, 'TEXT', 0, 0, 3, 3, null, 0, 'Кол-во погашений', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Кол-во погашений', 3, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (269, 'TEXT', 0, 0, 4, 4, null, 0, 'Наименование', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Наименование', 4, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (270, 'TEXT', 0, 0, 5, 5, null, 0, 'Всего погашено', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Всего погашено', 5, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (271, 'TEXT', 0, 0, 6, 8, null, 0, 'в том числе', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Всего погашено в том числе', 6, 'TABLE_HEADER', 5, 5, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (272, 'TEXT', 0, 0, 6, 6, null, 0, 'по основной сумме', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. По основной сумме', 7, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (273, 'TEXT', 0, 0, 7, 7, null, 0, 'по процентам', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. По процентам', 8, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (274, 'TEXT', 0, 0, 8, 8, null, 0, 'по штрафам', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. По по штрафам', 9, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (275, 'TEXT', 0, 0, 9, 9, null, 0, 'Номер плат. документа', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Номер плат. документа', 5, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (276, 'TEXT', 0, 0, 10, 10, null, 0, 'Дата платежа', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Дата платежа', 5, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (277, 'TEXT', 0, 0, 11, 11, null, 0, 'Курс на дату платежа', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Курс на дату платежа', 5, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (278, 'TEXT', 0, 0, 12, 12, null, 0, 'Вид погашения', 0, 'CONSTANT', '', 'Погашения. Шапка таблицы. Вид погашения', 5, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (279, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentTotalAmount', 'Погашения. Итого. Всего погашено', 5, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (280, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPrincipal', 'Погашения. Итого. по осн.сумме', 6, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (281, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentInterest', 'Погашения. Итого. по процентам', 7, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (282, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPenalty', 'Погашения. Итого. по штрафам', 8, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (283, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Погашения. Итого. Кол-во заемщиков', 1, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (284, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Погашения. Итого. Кол-во кредитов', 2, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (285, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Погашения. Итого. Кол-во погашений', 3, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (286, 'TEXT', 0, 0, 0, 0, null, 0, 'ИТОГО', 0, 'CONSTANT', '', 'Погашения. Итого. Итого', 4, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (287, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentTotalAmount', 'Погашения. Разрез1. Всего погашено', 5, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (288, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPrincipal', 'Погашения. Разрез1. по осн.сумме', 6, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (289, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentInterest', 'Погашения. Разрез1. по процентам', 7, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (290, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPenalty', 'Погашения. Разрез1. по штрафам', 8, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (291, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Погашения. Разрез1. Кол-во заемщиков', 1, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (292, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Погашения. Разрез1. Кол-во кредитов', 2, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (293, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Погашения. Разрез1. Кол-во погашений', 3, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (294, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Погашения. Разрез1. Наименование', 4, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (295, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentTotalAmount', 'Погашения. Разрез2. Всего погашено', 5, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (296, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPrincipal', 'Погашения. Разрез2. по осн.сумме', 6, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (297, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentInterest', 'Погашения. Разрез2. по процентам', 7, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (298, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPenalty', 'Погашения. Разрез2. по штрафам', 8, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (299, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Погашения. Разрез2. Кол-во заемщиков', 1, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (300, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Погашения. Разрез2. Кол-во кредитов', 2, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (301, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Погашения. Разрез2. Кол-во погашений', 3, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (302, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Погашения. Разрез2. Наименование', 4, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (303, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentTotalAmount', 'Погашения. Разрез3. Всего погашено', 5, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (304, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPrincipal', 'Погашения. Разрез3. по осн.сумме', 6, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (305, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentInterest', 'Погашения. Разрез3. по процентам', 7, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (306, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPenalty', 'Погашения. Разрез3. по штрафам', 8, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (307, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Погашения. Разрез3. Кол-во заемщиков', 1, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (308, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Погашения. Разрез3. Кол-во кредитов', 2, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (309, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Погашения. Разрез3. Кол-во погашений', 3, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (310, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Погашения. Разрез3. Наименование', 4, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (311, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentTotalAmount', 'Погашения. Разрез4. Всего погашено', 5, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (312, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPrincipal', 'Погашения. Разрез4. по осн.сумме', 6, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (313, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentInterest', 'Погашения. Разрез4. по процентам', 7, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (314, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPenalty', 'Погашения. Разрез4. по штрафам', 8, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (315, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Погашения. Разрез4. Кол-во заемщиков', 1, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (316, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Погашения. Разрез4. Кол-во кредитов', 2, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (317, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Погашения. Разрез4. Кол-во погашений', 3, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (318, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Погашения. Разрез4. Наименование', 4, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (319, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentTotalAmount', 'Погашения. Разрез5. Всего погашено', 5, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (320, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPrincipal', 'Погашения. Разрез5. по осн.сумме', 6, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (321, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentInterest', 'Погашения. Разрез5. по процентам', 7, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (322, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentPenalty', 'Погашения. Разрез5. по штрафам', 8, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (323, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Погашения. Разрез5. Кол-во заемщиков', 1, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (324, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Погашения. Разрез5. Кол-во кредитов', 2, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (325, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Погашения. Разрез5. Кол-во погашений', 3, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (326, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Погашения. Разрез5. Наименование', 4, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (334, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentNumber', 'Погашения. Разрез5. Номер платежного документа', 9, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (335, 'DATE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentDate', 'Погашения. Разрез 5. Дата погашения', 10, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (336, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'ExchangeRate', 'Погашения. Разрез 5. Курс на дату погашения', 11, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (337, 'TEXT', 0, 0, 0, 0, null, 31, 'payment_type', 0, 'LOAN_PAYMENT', 'paymentType', 'Погашения. Разрез 5. Вид погашения', 12, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (338, 'TEXT', 0, 0, 1, 7, null, 0, 'Информация по графикам', 0, 'CONSTANT', '', 'Графики. Заголовок страницы. Строка 1. ', 1, 'PAGE_TITLE', 1, 1, 0, 0);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (339, 'TEXT', 0, 0, 1, 7, null, 0, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Графики. Заголовок страницы. Строка 2.', 1, 'PAGE_TITLE', 2, 2, 0, 0);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (340, 'TEXT', 0, 0, 1, 1, null, 0, 'Кол-во суб-тов.', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. Кол-во субъектов', 1, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (341, 'TEXT', 0, 0, 2, 2, null, 0, 'Кол-во кред-тов', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. Кол-во кредитов', 2, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (342, 'TEXT', 0, 0, 3, 3, null, 0, 'Кол-во погашений', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. Кол-во графиков', 3, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (343, 'TEXT', 0, 0, 4, 4, null, 0, 'Наименование', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. Наименование', 4, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (344, 'TEXT', 0, 0, 5, 5, null, 0, 'Дата', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. Дата', 5, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (345, 'TEXT', 0, 0, 6, 6, null, 0, 'Всего освоено', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. Всего освоено', 6, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (346, 'TEXT', 0, 0, 7, 7, null, 0, 'Всего по графику', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. Всего по графику', 7, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (347, 'TEXT', 0, 0, 8, 11, null, 0, 'в том числе', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. Всего по графику в том числе', 8, 'TABLE_HEADER', 5, 5, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (348, 'TEXT', 0, 0, 8, 8, null, 0, 'по основной сумме', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. По основной сумме', 9, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (349, 'TEXT', 0, 0, 9, 9, null, 0, 'по процентам', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. По процентам', 10, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (350, 'TEXT', 0, 0, 10, 10, null, 0, 'по нак. процентам', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. По нак. процентам', 11, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (351, 'TEXT', 0, 0, 11, 11, null, 0, 'по нак. штрафам', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. По нак. штрафам', 12, 'TABLE_HEADER', 6, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (352, 'TEXT', 0, 0, 12, 12, null, 0, 'Статус', 0, 'CONSTANT', '', 'Графики. Шапка таблицы. Статус', 13, 'TABLE_HEADER', 5, 6, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (353, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Графики. Итого. Кол-во заемщиков', 1, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (354, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Графики. Итого. Кол-во кредитов', 2, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (355, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Графики. Итого. Кол-во графиков', 3, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (356, 'TEXT', 0, 0, 0, 0, null, 0, 'ИТОГО', 0, 'CONSTANT', '', 'Графики. Итого. Итого', 4, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (357, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Disbursement', 'Графики. Итого. Всего освоено', 6, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (358, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'TotalPayment', 'Графики. Итого. Всего по графику', 7, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (359, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Principal_payment', 'Графики. Итого. по осн.суме', 8, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (360, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Interest_payment', 'Графики. Итого. по процентам', 9, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (361, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_interest_payment', 'Графики. Итого. по нак. процентам', 10, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (362, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_penalty_payment', 'Графики. Итого. по нак. штрафам', 11, 'TABLE_SUM', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (363, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Графики. Разрез1. Кол-во заемщиков', 1, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (364, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Графики. Разрез1. Кол-во кредитов', 2, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (365, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Графики. Разрез1. Кол-во графиков', 3, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (366, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Графики. Разрез1. Наименование', 4, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (367, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Disbursement', 'Графики. Разрез1. Всего освоено', 6, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (368, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'TotalPayment', 'Графики. Разрез1. Всего по графику', 7, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (369, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Principal_payment', 'Графики. Разрез1. по осн.суме', 8, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (370, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Interest_payment', 'Графики. Разрез1. по процентам', 9, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (371, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_interest_payment', 'Графики. Разрез1. по нак. процентам', 10, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (372, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_penalty_payment', 'Графики. Разрез1. по нак. штрафам', 11, 'TABLE_GROUP1', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (373, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Графики. Разрез2. Кол-во заемщиков', 1, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (374, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Графики. Разрез2. Кол-во кредитов', 2, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (375, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Графики. Разрез2. Кол-во графиков', 3, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (376, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Графики. Разрез2. Наименование', 4, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (377, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Disbursement', 'Графики. Разрез2. Всего освоено', 6, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (378, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'TotalPayment', 'Графики. Разрез2. Всего по графику', 7, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (379, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Principal_payment', 'Графики. Разрез2. по осн.суме', 8, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (380, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Interest_payment', 'Графики. Разрез2. по процентам', 9, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (381, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_interest_payment', 'Графики. Разрез2. по нак. процентам', 10, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (382, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_penalty_payment', 'Графики. Разрез2. по нак. штрафам', 11, 'TABLE_GROUP2', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (383, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Графики. Разрез3. Кол-во заемщиков', 1, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (384, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Графики. Разрез3. Кол-во кредитов', 2, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (385, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Графики. Разрез3. Кол-во графиков', 3, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (386, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Графики. Разрез3. Наименование', 4, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (387, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Disbursement', 'Графики. Разрез3. Всего освоено', 6, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (388, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'TotalPayment', 'Графики. Разрез3. Всего по графику', 7, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (389, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Principal_payment', 'Графики. Разрез3. по осн.суме', 8, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (390, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Interest_payment', 'Графики. Разрез3. по процентам', 9, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (391, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_interest_payment', 'Графики. Разрез3. по нак. процентам', 10, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (392, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_penalty_payment', 'Графики. Разрез3. по нак. штрафам', 11, 'TABLE_GROUP3', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (393, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Графики. Разрез4. Кол-во заемщиков', 1, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (394, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Графики. Разрез4. Кол-во кредитов', 2, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (395, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Графики. Разрез4. Кол-во графиков', 3, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (396, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Графики. Разрез4. Наименование', 4, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (397, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Disbursement', 'Графики. Разрез4. Всего освоено', 6, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (398, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'TotalPayment', 'Графики. Разрез4. Всего по графику', 7, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (399, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Principal_payment', 'Графики. Разрез4. по осн.суме', 8, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (400, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Interest_payment', 'Графики. Разрез4. по процентам', 9, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (401, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_interest_payment', 'Графики. Разрез4. по нак. процентам', 10, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (402, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_penalty_payment', 'Графики. Разрез4. по нак. штрафам', 11, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (403, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Count', 'Графики. Разрез5. Кол-во заемщиков', 1, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (404, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'DetailsCount', 'Графики. Разрез5. Кол-во кредитов', 2, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (405, 'INTEGER', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'PaymentCount', 'Графики. Разрез5. Кол-во графиков', 3, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (406, 'TEXT', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Name', 'Графики. Разрез5. Наименование', 4, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (407, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Disbursement', 'Графики. Разрез5. Всего освоено', 6, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (408, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'TotalPayment', 'Графики. Разрез5. Всего по графику', 7, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (409, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Principal_payment', 'Графики. Разрез5. по осн.суме', 8, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (410, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Interest_payment', 'Графики. Разрез5. по процентам', 9, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (411, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_interest_payment', 'Графики. Разрез5. по нак. процентам', 10, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (412, 'DOUBLE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_PAYMENT', 'Collected_penalty_payment', 'Графики. Разрез5. по нак. штрафам', 11, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (413, 'DATE', 0, 0, 0, 0, null, 0, '', 0, 'LOAN_SCHEDULE', 'ExpectedDate', 'Графики. Разрез 5. Дата графика', 5, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (414, 'TEXT', 0, 0, 0, 0, null, 32, 'installment_state', 0, 'LOAN_SCHEDULE', 'installment_state_id', 'Графики. Разрез 5. Статус', 12, 'TABLE_GROUP5', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (415, 'DATE', 0, 0, 7, 7, null, 0, '', 0, 'LOAN_SUMMARY', 'lastDate', 'Задолженность. Разрез 4. Срок возврата.', 7, 'TABLE_GROUP4', 0, 0, null, null);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (416, 'TEXT', 0, 0, 1, 7, null, 0, 'Информация по залогу', 0, 'CONSTANT', '', 'Предмет залога. Заголовок страницы. Строка 1. ', 1, 'PAGE_TITLE', 1, 1, 0, 0);
INSERT INTO mfloan.content_parameter (id, cellType, classificator_id, classificator_value_id, col_from, col_to, constantDate, constant_int, constant_text, constant_value, contentType, field_name, name, position, rowType, row_from, row_to, col_shift, row_shift) VALUES (417, 'TEXT', 0, 0, 1, 7, null, 0, 'по состоянию на (=onDate=)', 0, 'CONSTANT', '', 'Предмет залога. Заголовок страницы. Строка 2.', 1, 'PAGE_TITLE', 2, 2, 0, 0);

#reports
INSERT INTO mfloan.report (id, name, reportType) VALUES (1, 'Отчет по оформлению', 'ENTITY_DOCUMENT');
INSERT INTO mfloan.report (id, name, reportType) VALUES (2, 'Отчет по взысканию', 'COLLECTION_PHASE');
INSERT INTO mfloan.report (id, name, reportType) VALUES (3, 'Отчет по задолженности', 'LOAN_SUMMARY');
INSERT INTO mfloan.report (id, name, reportType) VALUES (4, 'Отчет по погашениям', 'LOAN_PAYMENT');
INSERT INTO mfloan.report (id, name, reportType) VALUES (5, 'Отчет по графикам', 'LOAN_SCHEDULE');
INSERT INTO mfloan.report (id, name, reportType) VALUES (6, 'Отчет по залогу', 'COLLATERAL_ITEM');

INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 1);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 3);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 4);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 5);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 6);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 7);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 8);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 9);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 10);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 11);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 12);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 13);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 14);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 15);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 16);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 17);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 18);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 19);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 20);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 21);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 22);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 23);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 24);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 25);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 26);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 27);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 28);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 29);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 30);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 31);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 32);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 33);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 34);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 35);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 36);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 37);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 38);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 39);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 40);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 41);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 42);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 43);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 44);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 45);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 46);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 47);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 48);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 49);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 50);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 51);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 52);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 53);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 54);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 55);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 56);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 57);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 61);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 62);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 63);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 64);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 65);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 66);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 67);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 68);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 69);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 75);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 81);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 87);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 93);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 99);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (1, 105);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 106);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 107);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 108);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 109);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 110);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 111);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 112);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 113);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 114);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 115);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 116);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 117);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 118);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 119);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 120);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 121);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 122);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 123);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 124);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 125);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 126);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 127);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 128);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 129);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 130);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 131);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 132);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 133);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 134);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 135);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 136);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 137);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 138);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 139);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 140);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 141);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 142);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 143);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 144);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 145);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 146);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 147);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 148);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 149);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 150);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 151);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 152);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 153);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 154);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 155);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 156);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (2, 157);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 158);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 159);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 160);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 161);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 162);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 163);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 164);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 165);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 166);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 167);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 168);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 169);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 170);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 171);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 172);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 173);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 174);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 175);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 176);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 177);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 178);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 179);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 180);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 181);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 183);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 184);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 185);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 186);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 187);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 188);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 189);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 190);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 191);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 192);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 193);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 194);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 195);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 196);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 197);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 198);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 199);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 200);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 201);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 202);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 203);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 204);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 205);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 206);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 207);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 208);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 209);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 210);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 211);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 212);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 213);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 214);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 215);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 216);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 217);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 218);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 219);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 220);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 221);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 222);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 223);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 224);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 225);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 226);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 227);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 228);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 229);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 230);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 231);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 232);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 233);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 234);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 235);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 236);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 237);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 238);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 239);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 240);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 241);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 242);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 243);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 244);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 245);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 246);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 247);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 248);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 249);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 250);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 251);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 252);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 253);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 254);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 255);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 256);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 257);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 258);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 259);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 260);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 261);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 262);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 263);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 264);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 265);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 266);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 267);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 268);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 269);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 270);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 271);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 272);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 273);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 274);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 275);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 276);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 277);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 278);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 279);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 280);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 281);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 282);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 283);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 284);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 285);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 286);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 287);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 288);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 289);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 290);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 291);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 292);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 293);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 294);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 295);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 296);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 297);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 298);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 299);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 300);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 301);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 302);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 303);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 304);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 305);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 306);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 307);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 308);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 309);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 310);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 311);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 312);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 313);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 314);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 315);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 316);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 317);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 318);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 319);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 320);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 321);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 322);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 323);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 324);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 325);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 326);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 334);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 335);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 336);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (4, 337);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 338);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 339);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 340);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 341);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 342);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 343);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 344);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 345);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 346);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 347);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 348);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 349);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 350);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 351);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 352);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 353);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 354);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 355);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 356);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 357);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 358);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 359);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 360);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 361);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 362);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 363);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 364);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 365);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 366);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 367);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 368);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 369);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 370);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 371);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 372);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 373);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 374);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 375);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 376);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 377);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 378);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 379);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 380);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 381);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 382);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 383);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 384);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 385);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 386);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 387);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 388);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 389);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 390);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 391);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 392);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 393);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 394);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 395);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 396);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 397);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 398);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 399);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 400);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 401);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 402);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 403);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 404);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 405);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 406);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 407);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 408);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 409);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 410);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 411);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 412);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 413);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (5, 414);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (3, 415);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (6, 416);
INSERT INTO mfloan.report_content_parameter (report_id, content_parameter_id) VALUES (6, 417);

INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (2, 1);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (3, 1);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (4, 1);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (5, 1);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (6, 1);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (2, 2);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (2, 3);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (2, 4);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (2, 5);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (2, 6);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (2, 7);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (1, 70);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (1, 71);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (1, 72);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (1, 73);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (1, 74);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (1, 75);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (1, 76);
INSERT INTO mfloan.report_filter_parameter (report_id, filter_parameter_id) VALUES (1, 77);

INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 1);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (3, 1);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (4, 1);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (5, 1);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (6, 1);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 2);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (3, 2);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (4, 2);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (5, 2);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (6, 2);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 3);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (3, 3);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (4, 3);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (5, 3);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (6, 3);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 4);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (3, 4);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (4, 4);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (5, 4);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (3, 5);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (3, 6);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (4, 6);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (5, 6);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 7);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (3, 7);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (4, 7);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (5, 7);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (6, 7);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (3, 8);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (4, 8);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (5, 8);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (3, 9);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (4, 10);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (5, 11);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 12);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 13);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 14);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 15);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 16);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (6, 17);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (6, 18);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 19);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 20);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 21);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 22);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 23);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 24);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 25);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 26);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (1, 27);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 28);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 29);
INSERT INTO mfloan.report_group_type (report_id, group_type_id) VALUES (2, 30);


INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 1);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 1);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (3, 1);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 1);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 1);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 1);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 2);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 3);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 3);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (3, 3);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 3);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 3);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 3);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 4);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 4);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (3, 4);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 4);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 4);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 4);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 5);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 5);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 5);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 5);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 6);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 6);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 6);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 6);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 7);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 7);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 7);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 7);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 9);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 9);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (3, 9);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 9);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 9);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 9);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 11);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 11);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 11);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 11);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 12);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 12);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 12);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 12);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 13);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (3, 13);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 15);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 15);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (3, 15);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 15);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 15);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 15);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 16);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 16);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (3, 16);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 16);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 16);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 16);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 17);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 17);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 17);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (6, 17);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 272);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (1, 273);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (2, 273);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (3, 274);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (3, 275);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 276);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 276);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 277);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 277);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (4, 278);
INSERT INTO mfloan.report_output_parameter (report_id, output_parameter_id) VALUES (5, 278);


#report templates

INSERT INTO mfloan.report_template (id, name, onDate, group_type1_id, group_type2_id, group_type3_id, group_type4_id, group_type5_id, group_type6_id, report_id) VALUES (1, 'Общий отчет по оформлению документации', '2018-01-01', 25, 27, 12, 13, 16, null, 1);
INSERT INTO mfloan.report_template (id, name, onDate, group_type1_id, group_type2_id, group_type3_id, group_type4_id, group_type5_id, group_type6_id, report_id) VALUES (2, 'Общий отчет по взысканию', '2018-01-01', 1, 2, 7, 19, 20, null, 2);
INSERT INTO mfloan.report_template (id, name, onDate, group_type1_id, group_type2_id, group_type3_id, group_type4_id, group_type5_id, group_type6_id, report_id) VALUES (3, 'Отчет по задолженности (Баткенская область)', '2018-01-01', 1, 2, 7, 8, 9, null, 3);
INSERT INTO mfloan.report_template (id, name, onDate, group_type1_id, group_type2_id, group_type3_id, group_type4_id, group_type5_id, group_type6_id, report_id) VALUES (4, 'Отчет по погашениям', '2018-01-01', 1, 2, 7, 8, 10, null, 4);
INSERT INTO mfloan.report_template (id, name, onDate, group_type1_id, group_type2_id, group_type3_id, group_type4_id, group_type5_id, group_type6_id, report_id) VALUES (5, 'Отчет по графикам', '2018-01-01', 1, 2, 7, 8, 11, null, 5);
INSERT INTO mfloan.report_template (id, name, onDate, group_type1_id, group_type2_id, group_type3_id, group_type4_id, group_type5_id, group_type6_id, report_id) VALUES (6, 'Отчет по залогу', '2018-01-01', 1, 2, 7, 17, 18, null, 6);

INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 1);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 3);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 4);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 5);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 6);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 7);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 8);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 9);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 10);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 11);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 12);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 13);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 14);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 15);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 16);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 17);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 18);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 19);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 20);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 21);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 22);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 23);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 24);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 25);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 26);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 27);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 28);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 29);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 30);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 31);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 32);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 33);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 34);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 35);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 36);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 37);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 38);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 39);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 40);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 41);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 42);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 43);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 44);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 45);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 46);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 47);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 48);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 49);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 50);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 51);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 52);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 53);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 54);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 55);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 56);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 57);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 61);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 62);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 63);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 64);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 65);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 66);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 67);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 68);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 69);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 75);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 81);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 87);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 93);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 99);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (1, 105);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 106);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 107);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 108);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 109);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 110);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 111);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 112);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 113);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 114);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 115);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 116);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 117);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 118);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 119);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 120);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 121);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 122);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 123);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 124);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 125);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 126);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 127);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 128);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 129);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 130);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 131);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 132);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 133);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 134);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 135);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 136);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 137);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 138);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 139);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 140);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 141);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 142);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 143);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 144);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 145);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 146);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 147);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 148);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 149);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 150);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 151);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 152);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 153);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 154);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 155);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 156);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (2, 157);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 158);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 159);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 160);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 161);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 162);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 163);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 164);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 165);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 166);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 167);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 168);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 169);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 170);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 171);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 172);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 173);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 174);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 175);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 176);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 177);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 178);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 179);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 180);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 181);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 183);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 184);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 185);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 186);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 187);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 188);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 189);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 190);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 191);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 192);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 193);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 194);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 195);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 196);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 197);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 198);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 199);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 200);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 201);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 202);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 203);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 204);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 205);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 206);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 207);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 208);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 209);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 210);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 211);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 212);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 213);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 214);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 215);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 216);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 217);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 218);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 219);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 220);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 221);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 223);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 224);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 225);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 226);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 227);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 228);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 229);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 230);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 231);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 232);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 233);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 234);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 235);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 238);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 239);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 240);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 241);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 242);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 243);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 244);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 245);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 246);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 247);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 248);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 249);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 252);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 253);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 254);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 255);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 256);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 257);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 258);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 259);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 260);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 261);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 262);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 263);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 264);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 265);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 266);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 267);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 268);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 269);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 270);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 271);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 272);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 273);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 274);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 275);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 276);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 277);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 278);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 279);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 280);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 281);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 282);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 283);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 284);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 285);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 286);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 287);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 288);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 289);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 290);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 291);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 292);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 293);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 294);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 295);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 296);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 297);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 298);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 299);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 300);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 301);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 302);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 303);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 304);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 305);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 306);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 307);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 308);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 309);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 310);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 311);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 312);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 313);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 314);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 315);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 316);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 317);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 318);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 319);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 320);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 321);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 322);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 323);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 324);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 325);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 326);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 334);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 335);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 336);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (4, 337);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 338);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 339);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 340);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 341);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 342);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 343);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 344);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 345);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 346);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 347);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 348);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 349);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 350);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 351);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 352);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 353);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 354);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 355);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 356);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 357);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 358);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 359);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 360);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 361);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 362);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 363);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 364);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 365);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 366);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 367);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 368);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 369);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 370);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 371);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 372);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 373);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 374);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 375);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 376);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 377);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 378);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 379);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 380);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 381);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 382);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 383);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 384);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 385);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 386);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 387);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 388);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 389);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 390);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 391);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 392);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 393);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 394);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 395);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 396);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 397);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 398);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 399);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 400);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 401);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 402);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 403);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 404);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 405);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 406);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 407);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 408);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 409);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 410);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 411);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 412);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 413);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (5, 414);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (3, 415);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (6, 416);
INSERT INTO mfloan.report_template_content_parameter (report_template_id, content_parameter_id) VALUES (6, 417);

INSERT INTO mfloan.report_template_filter_parameter (report_template_id, filter_parameter_id) VALUES (2, 1);
INSERT INTO mfloan.report_template_filter_parameter (report_template_id, filter_parameter_id) VALUES (3, 1);
INSERT INTO mfloan.report_template_filter_parameter (report_template_id, filter_parameter_id) VALUES (4, 1);
INSERT INTO mfloan.report_template_filter_parameter (report_template_id, filter_parameter_id) VALUES (5, 1);
INSERT INTO mfloan.report_template_filter_parameter (report_template_id, filter_parameter_id) VALUES (6, 1);

INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 1);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 1);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (3, 1);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 1);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 1);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 1);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 2);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 3);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 3);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (3, 3);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 3);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 3);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 3);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 4);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 4);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (3, 4);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 4);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 4);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 4);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 5);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 5);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 5);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 5);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 6);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 6);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 6);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 6);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 7);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 7);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 7);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 7);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 9);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 9);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (3, 9);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 9);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 9);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 9);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 11);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 11);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 11);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 11);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 12);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 12);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 12);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 12);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 13);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (3, 13);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 15);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 15);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (3, 15);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 15);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 15);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 15);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 16);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 16);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (3, 16);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 16);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 16);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 16);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 17);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 17);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (6, 17);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 272);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (1, 273);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (2, 273);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (3, 274);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (3, 275);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 276);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 276);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 277);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 277);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (4, 278);
INSERT INTO mfloan.report_template_output_parameter (report_template_id, output_parameter_id) VALUES (5, 278);































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



















