update phaseDetails phd, phase_amount_view v
set phd.paidTotalAmount = v.paid_amount,
  phd.paidPrincipal = v.paid_principal,
  phd.paidInterest = v.paid_interest,
  phd.paidPenalty = v.paid_penalty
where phd.id = v.det_id;


update collectionPhase cph
set cph.paid = (select sum(distinct det.paidTotalAmount) from phaseDetails det where det.collectionPhaseId = cph.id group by det.collectionPhaseId)






create view phase_amount_view as
  select  det.id det_id,

          det.collectionPhaseId as phase_id,
          det.loan_id as loan_id,
          ph.startDate as phase_start_date ,
          ph.closeDate as phase_close_date ,
          cp.startDate as procedure_start_date,
          cp.closeDate as procedure_close_date,
          ph.startDate as from_date ,
          (CASE WHEN (cp.closeDate is null)
            THEN
              (
                CASE WHEN (ph.closeDate is null)
                  THEN
                    (
                      select now()
                    )
                ELSE (select ph.closeDate) END

              )
           ELSE (select cp.closeDate) END) as to_date,
          ifnull(sum(det.startTotalAmount),0) as start_amount,
          ifnull(sum(det.closeTotalAmount),0) as close_amount,
          (select IFNULL(sum(p.totalAmount),0)
           from payment p
           where p.loanId = det.loan_id and
                 p.paymentDate > ph.startDate and
                 p.paymentDate < ( CASE WHEN (cp.closeDate is null)
                   THEN
                     (
                       CASE WHEN (ph.closeDate is null)
                         THEN
                           (
                             select now()
                           )
                       ELSE (select ph.closeDate) END

                     )
                                   ELSE (select cp.closeDate) END))
            as paid_amount



  from  phaseDetails det,
    collectionPhase ph,
    collectionProcedure cp
  where det.collectionPhaseId = ph.id AND
        ph.collectionProcedureId = cp.id
  group by det.id
  order by det.id desc;




-- reference_view
create view reference_view as select 'region' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                              from `mfloan`.`region` `tbl`
                              union all select 'district' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`district` `tbl`
                              union all select 'work_sector' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`workSector` `tbl`
                              union all select 'loan_type' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`loanType` `tbl`
                              union all select 'supervisor' AS `list_type`, `u`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from (`mfloan`.`staff` `tbl` join `mfloan`.`users` `u`)
                                        where ((`tbl`.`organization_id` = 1) and (`tbl`.`enabled` = TRUE) and
                                               (`tbl`.`department_id` in (4, 5, 6, 7, 16)) and
                                               (`u`.`staff_id` = `tbl`.`id`))
                              union all select 'collection_phase_group' AS `list_type`,
                                               `tbl`.`id`               AS `id`,
                                               `tbl`.`name`             AS `name`
                                        from `mfloan`.`collectionPhaseGroup` `tbl`
                              union all select 'collection_phase_index' AS `list_type`,
                                               `tbl`.`id`               AS `id`,
                                               `tbl`.`name`             AS `name`
                                        from `mfloan`.`collectionPhaseIndex` `tbl`
                              union all select 'collection_phase_sub_index' AS `list_type`,
                                               `tbl`.`id`                   AS `id`,
                                               `tbl`.`name`                 AS `name`
                                        from `mfloan`.`CollectionPhaseSubIndex` `tbl`
                              union all select 'fin_group' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`loanGinGroup` `tbl`
                              union all select 'department' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`department` `tbl`
                                        where (`tbl`.`organization_id` = 1)
                              union all select 'credit_order'    AS `list_type`,
                                               `tbl`.`id`        AS `id`,
                                               `tbl`.`regNumber` AS `regNumber`
                                        from `mfloan`.`creditOrder` `tbl`
                              union all select 'applied_entity_status' AS `list_type`,
                                               `tbl`.`id`              AS `id`,
                                               `tbl`.`name`            AS `name`
                                        from `mfloan`.`appliedEntityState` `tbl`
                              union all select 'entity_document_status' AS `list_type`,
                                               `tbl`.`id`               AS `id`,
                                               `tbl`.`name`             AS `name`
                                        from `mfloan`.`entityDocumentState` `tbl`
                              union all select 'document_package_status' AS `list_type`,
                                               `tbl`.`id`                AS `id`,
                                               `tbl`.`name`              AS `name`
                                        from `mfloan`.`documentPackageState` `tbl`
                              union all select 'collection_phase_type' AS `list_type`,
                                               `tbl`.`id`              AS `id`,
                                               `tbl`.`name`            AS `name`
                                        from `mfloan`.`phaseType` `tbl`
                              union all select 'collection_phase_status' AS `list_type`,
                                               `tbl`.`id`                AS `id`,
                                               `tbl`.`name`              AS `name`
                                        from `mfloan`.`phaseStatus` `tbl`
                              union all select 'payment_type' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`paymentType` `tbl`
                              union all select 'installment_state' AS `list_type`,
                                               `tbl`.`id`          AS `id`,
                                               `tbl`.`name`        AS `name`
                                        from `mfloan`.`installmentState` `tbl`
                              union all select 'currency_type' AS `list_type`,
                                               `tbl`.`id`      AS `id`,
                                               `tbl`.`name`    AS `name`
                                        from `mfloan`.`orderTermCurrency` `tbl`
                              union all select 'item_type' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`collateralItemType` `tbl`
                              union all select 'inspection_result_type' AS `list_type`,
                                               `tbl`.`id`               AS `id`,
                                               `tbl`.`name`             AS `name`
                                        from `mfloan`.`inspectionResultType` `tbl`
                              union all select 'staff' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`staff` `tbl`
                              union all select 'staff_event_type' AS `list_type`,
                                               `tbl`.`id`         AS `id`,
                                               `tbl`.`name`       AS `name`
                                        from `mfloan`.`employment_history_event_type` `tbl`
                              union all select 'staff_position' AS `list_type`,
                                               `tbl`.`id`       AS `id`,
                                               `tbl`.`name`     AS `name`
                                        from `mfloan`.`position` `tbl`
                              union all select 'doc_type' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`cat_document_type` `tbl`
                              union all select 'doc_subType' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`cat_document_subtype` `tbl`
                              union all select 'doc_status' AS `list_type`, `tbl`.`id` AS `id`, `tbl`.`name` AS `name`
                                        from `mfloan`.`cat_document_status` `tbl`
                              union all select 'collection_procedure_status' AS `list_type`,
                                               `tbl`.`id`                    AS `id`,
                                               `tbl`.`name`                  AS `name`
                                        from `mfloan`.`procedureStatus` `tbl`;

DROP VIEW IF EXISTS `payment_schedule_view`;
CREATE VIEW payment_schedule_view AS
  SELECT
    `lv`.`v_loan_id`                 AS `v_loan_id`,
    `lv`.`v_loan_amount`             AS `v_loan_amount`,
    `lv`.`v_loan_reg_date`           AS `v_loan_reg_date`,
    `lv`.`v_loan_reg_number`         AS `v_loan_reg_number`,
    `lv`.`v_loan_supervisor_id`      AS `v_loan_supervisor_id`,
    `lv`.`v_loan_fin_group_id`       AS `v_loan_fin_group_id`,
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
    (CASE WHEN (`ps`.`interestPayment` = 10 or `ps`.`interestPayment` = 20)
      THEN ifnull((SELECT sum(`acc`.`penaltyOnInterestOverdue`)
                   FROM `mfloan`.`accrue` `acc`
                   WHERE ((`acc`.`loanId` = `ps`.`loanId`) AND
                          (`acc`.`toDate` = `ps`.`expectedDate`))),0)
     ELSE `ps`.`interestPayment` END) AS `v_ps_interest_payment`,

    `ps`.`principalPayment`          AS `v_ps_principal_payment`,
    `ps`.`installmentStateId`        AS `v_ps_installment_state_id`,
    `ps`.`loanId`                    AS `v_ps_loan_id`
  FROM (`mfloan`.`loan_view` `lv`
    JOIN `mfloan`.`paymentSchedule` `ps`)
  WHERE (`ps`.`loanId` = `lv`.`v_loan_id`);
