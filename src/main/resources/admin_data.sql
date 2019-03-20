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

