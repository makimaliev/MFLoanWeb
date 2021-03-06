

DROP VIEW IF EXISTS `loan_view`;
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
    `l`.`loanFinGroupId`           AS `v_loan_fin_group_id`,
    `l`.`loanTypeId`               AS `v_loan_type_id`,
    `l`.`loan_class_id`            AS `v_loan_class_id`,
    `l`.`fundId`                   AS `v_loan_fund_id`,
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
    `co`.`regDate`                 AS `v_credit_order_reg_date`,
    `l`.`closeDate`                AS `v_loan_close_date`,
    `l`.`closeRate`                AS `v_loan_close_rate`,
    `l`.`lastDate`                 AS `v_last_date`,
    `l`.collateralDescription_id   as `v_collateral_description_id`,
    `l`.collectionDescription_id   as `v_collection_description_id`,
    `l`.supervisorDescription_id   as `v_supervisor_description_id`

  FROM ((`mfloan`.`loan` `l`
    JOIN `mfloan`.`debtor_view` `dv`) JOIN `mfloan`.`creditOrder` `co`)
  WHERE (isnull(`l`.`parent_id`) AND (`l`.`loanStateId` = 2) AND (`l`.`debtorId` = `dv`.`v_debtor_id`) AND
         (`l`.`creditOrderId` = `co`.`id`));



DROP VIEW IF EXISTS `collateral_item_last_condition_view`;
CREATE VIEW collateral_item_last_condition_view AS
  SELECT i.id as v_ci_id,
         p1.onDate as v_ci_inspection_date,
         p1.inspectionResultTypeId as v_ci_inspection_result_type_id,

         if(YEAR(p1.onDate)=YEAR(current_date),if(p1.inspectionResultTypeId >2,'Требуется принятие мер','Обследовано'), if(i.inspection_needed,'Требуется обследование',' Не подлежит обследованию') )       as v_ci_inspection_status

  FROM collateralItem i
    JOIN collateralItemInspectionResult p1 ON (i.id = p1.collateralItemId)
    LEFT JOIN collateralItemInspectionResult p2 ON (i.id = p2.collateralItemId AND
                                                    (p1.onDate < p2.onDate OR (p1.onDate = p2.onDate AND p1.id < p2.id)))
  WHERE p2.id IS NULL;







DROP VIEW IF EXISTS `collateral_item_details_view`;
CREATE VIEW collateral_item_details_view AS

  select  det.id as v_ci_id,

          CONCAT(IF((det.goods_id is null or LENGTH(det.goods_id)<2), '', CONCAT('Гос.номер:', IFNULL(det.goods_id, '-'))),
                 IF((det.details1 is null or LENGTH(det.details1)<2), '', CONCAT(' № двигателя:', IFNULL(det.details1, '-'))),
                 IF((det.details2 is null or LENGTH(det.details2)<2), '', CONCAT(' Заводской номер:', IFNULL(det.details2, '-')))
          ) as str


  from collateralItemDetails det,
    collateralItem i where i.id = det.id and i.itemTypeId in (1,5)


  UNION ALL

  select  det.id as v_ci_id,

          CONCAT(IF((det.goods_address is null or LENGTH(det.goods_address)<2), '', CONCAT('', IFNULL(det.goods_address, '-'))),
                 IF((det.goods_id is null or LENGTH(det.goods_id)<2), '', CONCAT(' Ид. код:', IFNULL(det.goods_id, '-')))
          ) as str

  from collateralItemDetails det,
    collateralItem i where i.id = det.id and i.itemTypeId in (3,7)

  ORDER BY v_ci_id;





DROP VIEW IF EXISTS `collateral_item_view`;
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
    `lv`.`v_loan_id`                       AS `v_loan_id`,
    `lv`.`v_loan_amount`                   AS `v_loan_amount`,
    `lv`.`v_loan_reg_date`                 AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`               AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`            AS `v_loan_supervisor_id`,
    `lv`.`v_loan_fin_group_id`             AS `v_loan_fin_group_id`,
    `lv`.`v_loan_credit_order_id`          AS `v_loan_credit_order_id`,
    `lv`.`v_loan_currency_id`              AS `v_loan_currency_id`,
    `lv`.`v_loan_debtor_id`                AS `v_loan_debtor_id`,
    `lv`.`v_loan_state_id`                 AS `v_loan_state_id`,
    `lv`.`v_loan_type_id`                  AS `v_loan_type_id`,
    `lv`.`v_credit_order_type_id`          AS `v_credit_order_type_id`,
    `lv`.`v_credit_order_reg_number`       AS `v_credit_order_reg_number`,
    `lv`.`v_credit_order_reg_date`         AS `v_credit_order_reg_date`,
    `ci`.`id`                              AS `v_ci_id`,
    `ci`.`version`                         AS `v_ci_version`,
    `ci`.`collateralValue`                 AS `v_ci_collateralValue`,
    `ci`.`demand_rate`                     AS `v_ci_demand_rate`,
    `ci`.`description`                     AS `v_ci_description`,
    `ci`.`estimatedValue`                  AS `v_ci_estimatedValue`,
    `ci`.`name`                            AS `v_ci_name`,
    `ci`.`ownerId`                         AS `v_ci_ownerId`,
    `ci`.`quantity`                        AS `v_ci_quantity`,
    `ci`.`risk_rate`                       AS `v_ci_risk_rate`,
    `ci`.`collateralAgreementId`           AS `v_ci_collateralAgreementId`,
    `ci`.`conditionTypeId`                 AS `v_ci_condition_type`,
    `ci`.`itemTypeId`                      AS `v_ci_itemTypeId`,
    `ci`.`quantityTypeId`                  AS `v_ci_quantityTypeId`,
    `ci`.`organization`                    AS `v_ci_org_id`,
    `org`.`name`                           AS `v_ci_arestPlace`,
    `item_owner`.`name`                    AS `v_ci_ownerName`,
    `lcv`.`v_ci_inspection_date`           AS `v_ci_inspection_date`,
    `ins`.`name`                           AS `v_ci_last_condition`,
    `cidv`.`str`                           AS `v_ci_details`,
    `item_desc`.`text`                     AS `v_ci_collateral_description`,
    `ci`.arrestFreeStatusId as `v_ci_arrestFree_status`,
    `ci`.inspectionStatusId as `v_ci_inspection_status`

  FROM (((((((((`mfloan`.`collateral_agreement_view` `cav`
    JOIN `mfloan`.`collateralItem` `ci`) JOIN `mfloan`.`loan_view` `lv`) JOIN
    `mfloan`.`loanCollateralAgreement` `lca`) LEFT JOIN `mfloan`.`owner` `org`
      ON ((`org`.`id` = `ci`.`organization`))) LEFT JOIN `mfloan`.`owner` `item_owner`
      ON ((`item_owner`.`id` = `ci`.`ownerId`))) LEFT JOIN `mfloan`.`collateral_item_last_condition_view` `lcv`
      ON ((`lcv`.`v_ci_id` = `ci`.`id`))) LEFT JOIN `mfloan`.`inspectionResultType` `ins`
      ON ((`ins`.`id` = `lcv`.`v_ci_inspection_result_type_id`))) LEFT JOIN
    `mfloan`.`collateral_item_details_view` `cidv` ON ((`cidv`.`v_ci_id` = `ci`.`id`))) LEFT JOIN
    `mfloan`.`description` `item_desc` ON ((`item_desc`.`id` = `lv`.`v_collateral_description_id`)))
  WHERE ((`ci`.`collateralAgreementId` = `cav`.`v_ca_id`) AND (`lv`.`v_loan_id` = `lca`.`loanId`) AND
         (`lca`.`collateralAgreementId` = `cav`.`v_ca_id`));


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
              `u`.`id`     AS `id`,
              `tbl`.`name` AS `name`
            FROM (`mfloan`.`staff` `tbl`
              JOIN `mfloan`.`users` `u`)
            WHERE (
              (`tbl`.`organization_id` = 1) AND (`tbl`.`enabled` = TRUE) AND (`tbl`.`department_id` IN (4, 5, 6, 7, 16))
              AND (`u`.`staff_id` = `tbl`.`id`))
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
              'item_inspection_status' AS `list_type`,
              `tbl`.`id`               AS `id`,
              `tbl`.`name`             AS `name`
            FROM `mfloan`.`inspectionStatus` `tbl`
  UNION ALL SELECT
              'item_arrestFree_status' AS `list_type`,
              `tbl`.`id`               AS `id`,
              `tbl`.`name`             AS `name`
            FROM `mfloan`.`arrestFreeStatus` `tbl`
  UNION ALL SELECT
              'item_condition_type' AS `list_type`,
              `tbl`.`id`            AS `id`,
              `tbl`.`name`          AS `name`
            FROM `mfloan`.`collateralConditionType` `tbl`
  UNION ALL SELECT
              'staff'      AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`staff` `tbl`
            WHERE (`tbl`.`organization_id` = 1)
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
            WHERE `tbl`.`department_id` IN (SELECT `mfloan`.`department`.`id`
                                            FROM `mfloan`.`department`
                                            WHERE (`mfloan`.`department`.`organization_id` = 1))
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
              'fin_group'  AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`loanGinGroup` `tbl`
  UNION ALL SELECT
              'loan_fund'  AS `list_type`,
              `tbl`.`id`   AS `id`,
              `tbl`.`name` AS `name`
            FROM `mfloan`.`orderTermFund` `tbl`
  UNION ALL SELECT
              'collection_phase_group' AS `list_type`,
              `tbl`.`id`               AS `id`,
              `tbl`.`name`             AS `name`
            FROM `mfloan`.`collectionPhaseGroup` `tbl`
  UNION ALL SELECT
              'collection_phase_index' AS `list_type`,
              `tbl`.`id`               AS `id`,
              `tbl`.`name`             AS `name`
            FROM `mfloan`.`collectionPhaseIndex` `tbl`
  UNION ALL SELECT
              'collection_phase_sub_index' AS `list_type`,
              `tbl`.`id`                   AS `id`,
              `tbl`.`name`                 AS `name`
            FROM `mfloan`.`CollectionPhaseSubIndex` `tbl`
  UNION ALL SELECT
              'collection_procedure_status' AS `list_type`,
              `tbl`.`id`                    AS `id`,
              `tbl`.`name`                  AS `name`
            FROM `mfloan`.`procedureStatus` `tbl`;


