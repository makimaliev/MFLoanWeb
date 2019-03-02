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


