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
    `d`.`name`                     AS `v_district_name`
  FROM ((((`mfloan`.`loan` `l`
    JOIN `mfloan`.`debtor_view` `dv` ON ((`dv`.`v_debtor_id` = `l`.`debtorId`))) JOIN `mfloan`.`creditOrder` `co`
      ON ((`co`.`id` = `l`.`creditOrderId`))) JOIN `mfloan`.`region` `r`
      ON ((`r`.`id` = `dv`.`v_debtor_region_id`))) JOIN `mfloan`.`district` `d`
      ON ((`d`.`id` = `dv`.`v_debtor_district_id`)));




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
    `p`.`paymentDate`                 AS `v_payment_payment_date`,
    `p`.`penalty`                     AS `v_payment_penalty`,
    `p`.`principal`                   AS `v_payment_principal`,
    `p`.`totalAmount`                 AS `v_payment_total_amount`,
    `p`.`paymentTypeId`               AS `v_payment_type_id`,
    `pt`.`name`                       AS `v_payment_type_name`
  FROM ((`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`payment` `p` ON ((`p`.`loanId` = `lv`.`v_loan_id`))) JOIN `mfloan`.`paymentType` `pt`
      ON ((`pt`.`id` = `p`.`paymentTypeId`)));

