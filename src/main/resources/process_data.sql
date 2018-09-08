# procedures

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
       `mfloan`.`organization`.`id` AS `id`,
       `mfloan`.`organization`.`name` AS `name`,
       'organization' AS `internalName`
     FROM
       `mfloan`.`organization` UNION ALL SELECT
                                           `mfloan`.`department`.`id` AS `id`,
                                           `mfloan`.`department`.`name` AS `name`,
                                           'department' AS `internalName`
                                         FROM
                                           `mfloan`.`department`
                                         WHERE
                                           (`mfloan`.`department`.`organization_id` = 1) UNION ALL SELECT
                                                                                                     `mfloan`.`staff`.`id` AS `id`,
                                                                                                     `mfloan`.`staff`.`name` AS `name`,
                                                                                                     'staff' AS `internalName`
                                                                                                   FROM
                                                                                                     `mfloan`.`staff`
                                                                                                   WHERE
                                                                                                     (`mfloan`.`staff`.`organization_id` = 1) UNION ALL SELECT
                                                                                                                                                          `mfloan`.`person`.`id` AS `id`,
                                                                                                                                                          `mfloan`.`person`.`name` AS `name`,
                                                                                                                                                          'person' AS `internalName`
                                                                                                                                                        FROM
                                                                                                                                                          `mfloan`.`person`) `data`
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