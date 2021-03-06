-- MySQL dump 10.13  Distrib 5.7.25, for Linux (x86_64)
--
-- Host: localhost    Database: mfloan
-- ------------------------------------------------------
-- Server version	5.7.25-0ubuntu0.18.04.2

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
-- Dumping routines for database 'mfloan'
--
/*!50003 DROP FUNCTION IF EXISTS `calculateDays` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculateDays`(fromDate date, toDate date, method int) RETURNS int(11)
BEGIN

  DECLARE days INT DEFAULT 0;

  IF method = 1 THEN
    SET days = DATEDIFF(toDate, fromDate);
  ELSE
    IF DAY(fromDate) = 31 THEN
      SET fromDate = fromDate - 1;
    END IF;

    IF DAY(toDate) = 31 THEN
      SET toDate = toDate - 1;
    END IF;

    IF YEAR(fromDate) = YEAR(toDate) THEN
      SET days = ((MONTH(toDate) - MONTH(fromDate))-1) * 30 + (30 + DAY(toDate) - DAY(fromDate));
    ELSE
      SET days = (30 - DAY(fromDate)) + (12 - MONTH(fromDate)) * 30 + (MONTH(toDate)-1) * 30 + DAY(toDate) + ((YEAR(toDate) - YEAR(fromDate)-1)*360) ;
    END IF;
  END IF;

  RETURN days;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `calculateInterestAccrued` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculateInterestAccrued`(principalOutstanding double, daysInPeriod int,
                                                                      inDate date, loanId bigint) RETURNS double
BEGIN

  DECLARE rate DOUBLE DEFAULT 0;
  DECLARE dIYMethod INT DEFAULT 2;
  DECLARE nOD INT DEFAULT 365;
  DECLARE result DOUBLE DEFAULT 0;

  DECLARE cur CURSOR FOR
    SELECT term.interestRateValue, term.daysInYearMethodId
    FROM creditTerm term
    WHERE term.loanId = loanId
      AND term.startDate < inDate
      AND term.record_status = 1
    ORDER BY term.startDate DESC LIMIT 1;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET rate = 0;

  OPEN cur;
  FETCH cur INTO rate, dIYMethod;
  CLOSE cur;

  IF dIYMethod != 1 THEN SET nOD = 360;
  END IF;

  IF dIYMethod = 3 THEN SET nOD = 366;
  END IF;

  IF principalOutstanding < 0 THEN
    SET principalOutstanding = 0;
  END IF;

  SET result = (principalOutstanding*rate/nOD)/100*daysInPeriod;

  if result is null then
    set result = 0;
  end if;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `calculateLibor` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculateLibor`(val             double, fromDate date, toDate date, term_rate_type int,
                                                            term_diy_method int, term_dim_method int, loan_id bigint, grace_type int) RETURNS double
BEGIN

  DECLARE total DOUBLE DEFAULT 0;
  DECLARE tRate DOUBLE;
  DECLARE prevRate DOUBLE DEFAULT 0;
  DECLARE tDate DATE;
  DECLARE curDate DATE;
  DECLARE prevDate DATE;
  DECLARE daysInPer INT DEFAULT 0;
  DECLARE nOD INT DEFAULT 365;
  DECLARE flag BOOLEAN DEFAULT TRUE;

  DECLARE grace_princ INT DEFAULT 0;
  DECLARE grace_int INT DEFAULT 0;

  DECLARE v_finished INTEGER DEFAULT 0;

  DECLARE tCursor CURSOR FOR
    (SELECT fr.rate, fr.date
     FROM floating_rate fr
     WHERE fr.floating_rate_type_id = term_rate_type
       AND fr.date >= fromDate
       AND fr.date <= toDate)
    UNION
    (SELECT fr.rate, fr.date
     FROM floating_rate fr
     WHERE fr.floating_rate_type_id = term_rate_type
       AND fr.date <= fromDate
     ORDER BY fr.date DESC LIMIT 1)
    ORDER BY date;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  if grace_type = 1 then
    if exists(
            select graceDaysPrincipal
            from creditTerm
            where record_status = 1
              and startDate < toDate
              and loanId = loan_id
            order by startDate desc limit 1) THEN
      select graceDaysPrincipal
             into grace_princ
      from creditTerm
      where record_status = 1
        and startDate < toDate
        and loanId = loan_id
      order by startDate desc limit 1;
    end if;
  end if;

  if grace_type = 2 then
    if exists(
            select graceDaysInterest
            from creditTerm
            where record_status = 1
              and startDate < toDate
              and loanId = loan_id
            order by startDate desc limit 1) THEN
      select graceDaysInterest
             into grace_int
      from creditTerm
      where record_status = 1
        and startDate < toDate
        and loanId = loan_id
      order by startDate desc limit 1;
    end if;
  end if;

  OPEN tCursor;

  get_data: LOOP

    FETCH tCursor INTO tRate, tDate;

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

    IF term_diy_method != 1 THEN SET nOD = 360;
    END IF;

    SET daysInPer = calculateDays(prevDate, curDate, term_dim_method);

    if grace_type = 1 then
      SET daysInPer = daysInPer - grace_princ;
      if daysInPer < 0 then
        set daysInPer = 0;
      end if;
    end if;

    if grace_type = 2 then
      SET daysInPer = daysInPer - grace_int;
      if daysInPer < 0 then
        set daysInPer = 0;
      end if;
    end if;

    SET total = total + val*prevRate*daysInPer/nOD/100;

    SET prevDate = curDate;

    SET prevRate = tRate;

  END LOOP get_data;

  CLOSE tCursor;

  SET daysInPer = calculateDays(prevDate, toDate, term_dim_method);

  if grace_type = 1 then
    SET daysInPer = daysInPer - grace_princ;
    if daysInPer < 0 then
      set daysInPer = 0;
    end if;
  end if;

  if grace_type = 2 then
    SET daysInPer = daysInPer - grace_int;
    if daysInPer < 0 then
      set daysInPer = 0;
    end if;
  end if;

  SET total = total + val*tRate*daysInPer/nOD/100;

  IF val < 0 THEN
    SET total = 0;
  END IF;

  IF total IS NULL THEN
    SET total = 0;
  END IF;

  RETURN total;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `calculateLiborIO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculateLiborIO`(val double, fromDate date, toDate date, loan_id bigint) RETURNS double
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

  DECLARE grace_int INT DEFAULT 0;
  DECLARE d_period_int INT;

  DECLARE v_finished INTEGER DEFAULT 0;

  DECLARE tCursor CURSOR FOR
    (SELECT fr.rate, fr.date, term.daysInYearMethodId
     FROM floating_rate fr, creditTerm term
     WHERE term.loanId = loan_id
       AND term.penaltyOnInterestOverdueRateTypeId = fr.floating_rate_type_id
       AND fr.date >= fromDate
       AND fr.date <= toDate
       AND term.record_status = 1
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
       AND term.record_status = 1
     ORDER BY fr.date DESC LIMIT 1)
    ORDER BY date;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  if exists(
          select graceDaysInterest
          from creditTerm
          where record_status = 1
            and startDate < toDate
            and loanId = loan_id
          order by startDate desc limit 1) THEN
    select graceDaysInterest
           into grace_int
    from creditTerm
    where record_status = 1
      and startDate < toDate
      and loanId = loan_id
    order by startDate desc limit 1;
  end if;

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

    IF dIYMethod = 3 THEN SET nOD = 366;
    END IF;

    SET daysInPer = calculateDays(prevDate, curDate, dIYMethod);

    set d_period_int = daysInPer - grace_int;
    if d_period_int < 0 then
      set d_period_int = 0;
    end if;

    SET total = total + val*prevRate*d_period_int/nOD/100;

    SET prevDate = curDate;

    SET prevRate = tRate;

  END LOOP get_data;

  CLOSE tCursor;

  SET daysInPer = calculateDays(prevDate, toDate, dIYMethod);

  set d_period_int = daysInPer - grace_int;
  if d_period_int < 0 then
    set d_period_int = 0;
  end if;

  SET total = total + val*tRate*d_period_int/nOD/100;

  IF val < 0 THEN
    SET total = 0;
  END IF;

  IF total IS NULL THEN
    SET total = 0;
  END IF;

  RETURN total;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `calculateLiborPO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculateLiborPO`(val double, fromDate date, toDate date, loan_id bigint) RETURNS double
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

  DECLARE grace_princ INT DEFAULT 0;
  DECLARE d_period_princ INT;

  DECLARE v_finished INTEGER DEFAULT 0;

  DECLARE tCursor CURSOR FOR
    (SELECT fr.rate, fr.date, term.daysInYearMethodId
     FROM floating_rate fr, creditTerm term
     WHERE term.loanId = loan_id
       AND term.penaltyOnPrincipleOverdueRateTypeId = fr.floating_rate_type_id
       AND fr.date >= fromDate
       AND fr.date <= toDate
       AND term.record_status = 1
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
       AND term.record_status = 1
     ORDER BY fr.date DESC LIMIT 1)
    ORDER BY date;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  if exists(
          select graceDaysPrincipal
          from creditTerm
          where record_status = 1
            and startDate < toDate
            and loanId = loan_id
          order by startDate desc limit 1) THEN
    select graceDaysPrincipal
           into grace_princ
    from creditTerm
    where record_status = 1
      and startDate < toDate
      and loanId = loan_id
    order by startDate desc limit 1;
  end if;

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

    IF dIYMethod = 3 THEN SET nOD = 366;
    END IF;

    SET daysInPer = calculateDays(prevDate, curDate, dIYMethod);

    set d_period_princ = daysInPer - grace_princ;
    if d_period_princ < 0 then
      set d_period_princ = 0;
    end if;

    SET total = total + val*prevRate*d_period_princ/nOD/100;

    SET prevDate = curDate;

    SET prevRate = tRate;

  END LOOP get_data;

  CLOSE tCursor;

  SET daysInPer = calculateDays(prevDate, toDate, dIYMethod);

  set d_period_princ = daysInPer - grace_princ;
  if d_period_princ < 0 then
    set d_period_princ = 0;
  end if;

  SET total = total + val*tRate*d_period_princ/nOD/100;

  IF val < 0 THEN
    SET total = 0;
  END IF;

  IF total IS NULL THEN
    SET total = 0;
  END IF;

  RETURN total;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `calculatePenaltyAccrued` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculatePenaltyAccrued`(principalOverdue double, interestOverdue double,
                                                                     daysInPeriod int, inDate date,
                                                                     loan_id bigint) RETURNS double
BEGIN

  DECLARE pOIO DOUBLE DEFAULT 0;
  DECLARE pOPO DOUBLE DEFAULT 0;
  DECLARE dIYMethod INT;
  DECLARE nOD INT DEFAULT 365;
  DECLARE result_pOPO DOUBLE DEFAULT 0;
  DECLARE result_pOIO DOUBLE DEFAULT 0;
  DECLARE grace_princ INT DEFAULT 0;
  DECLARE grace_int INT DEFAULT 0;
  DECLARE d_period_princ INT;
  DECLARE d_period_int INT;
  DECLARE temp_princ_date DATE;
  DECLARE temp_int_date DATE;
  DECLARE temp_days_in_period int;

  DECLARE cur CURSOR FOR
    SELECT term.penaltyOnInterestOverdueRateValue, term.penaltyOnPrincipleOverdueRateValue, term.daysInYearMethodId
    FROM creditTerm term
    WHERE term.loanId = loan_id
      AND term.startDate < inDate
      AND term.record_status = 1
    ORDER BY term.startDate DESC LIMIT 1;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND
    BEGIN
      SET pOIO = 0;
      SET pOPO = 0;
    END;

  select graceDaysPrincipal, graceDaysInterest
         into grace_princ, grace_int
  from creditTerm
  where record_status = 1
    and startDate < inDate
    and loanId = loan_id
  order by startDate desc limit 1;

  OPEN cur;
  FETCH cur INTO pOIO, pOPO, dIYMethod;
  CLOSE cur;

  IF dIYMethod != 1 THEN SET nOD = 360;
  END IF;

  IF dIYMethod = 3 THEN SET nOD = 366;
  END IF;

  set temp_days_in_period = daysInPeriod;

  #calculate days_in_period for principal
  #get last principal payment date where principal payment > 0
  if grace_princ > 0 then
    if exists(
            select expectedDate
            from paymentSchedule
            where loanId = loan_id
              and principalPayment > 0
              and record_status = 1
              and expectedDate <= inDate
            order by expectedDate desc
            limit 1
      ) then
      select expectedDate into temp_princ_date
      from paymentSchedule
      where loanId = loan_id
        and principalPayment > 0
        and record_status = 1
        and expectedDate <= inDate
      order by expectedDate desc
      limit 1;
      set daysInPeriod = calculateDays(temp_princ_date, inDate, dIYMethod);
    end if;
  end if;

  set d_period_princ = daysInPeriod - grace_princ;
  if d_period_princ < 0 then
    set d_period_princ = 0;
  end if;

  IF principalOverdue > 0 THEN SET result_pOPO = principalOverdue*pOPO/100*d_period_princ/nOD;
  END IF;

  set daysInPeriod = temp_days_in_period;

  #calculate days_in_period for interest
  #get last interest payment where date interest payment > 0
  if grace_int > 0 then
    if exists(
            select expectedDate
            from paymentSchedule
            where loanId = loan_id
              and interestPayment > 0
              and record_status = 1
              and expectedDate <= inDate
            order by expectedDate desc
            limit 1
      ) then
      select expectedDate into temp_int_date
      from paymentSchedule
      where loanId = loan_id
        and interestPayment > 0
        and record_status = 1
        and expectedDate <= inDate
      order by expectedDate desc
      limit 1;
      set daysInPeriod = calculateDays(temp_int_date, inDate, dIYMethod);
    end if;
  end if;

  set d_period_int = daysInPeriod - grace_int;
  if d_period_int < 0 then
    set d_period_int = 0;
  end if;

  IF interestOverdue > 0 THEN SET result_pOIO = interestOverdue*pOIO/100*d_period_int/nOD;
  END IF;

  if result_pOIO is null then
    set result_pOIO = 0;
  end if;

  if result_pOPO is null then
    set result_pOPO = 0;
  end if;

  RETURN result_pOIO + result_pOPO;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `calculatePOIO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculatePOIO`(interestOverdue double, daysInPeriod int, inDate date, loanId bigint) RETURNS double
BEGIN

  DECLARE rate DOUBLE DEFAULT 0;
  DECLARE dIYMethod INT;
  DECLARE nOD INT DEFAULT 365;
  DECLARE result DOUBLE DEFAULT 0;

  DECLARE cur CURSOR FOR
    SELECT term.penaltyOnInterestOverdueRateValue, term.daysInYearMethodId
    FROM creditTerm term
    WHERE term.loanId = loanId
      AND term.record_status = 1
    ORDER BY term.startDate DESC LIMIT 1;

  OPEN cur;
  FETCH cur INTO rate, dIYMethod;
  CLOSE cur;

  IF dIYMethod = 1 THEN
    SET nOD = 360;
  ELSEIF dIYMethod = 3 THEN
    SET nOD = 366;
  END IF;

  IF interestOverdue < 0 THEN
    SET interestOverdue = 0;
  END IF;

  SET result = (interestOverdue*rate/nOD)/100*daysInPeriod;

  if result is null then
    set result = 0;
  end if;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `calculatePOPO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculatePOPO`(principalOverdue double, daysInPeriod int, inDate date, loanId bigint) RETURNS double
BEGIN

  DECLARE rate DOUBLE DEFAULT 0;
  DECLARE dIYMethod INT;
  DECLARE nOD INT DEFAULT 365;
  DECLARE result DOUBLE DEFAULT 0;

  DECLARE cur CURSOR FOR
    SELECT term.penaltyOnPrincipleOverdueRateValue, term.daysInYearMethodId
    FROM creditTerm term
    WHERE term.loanId = loanId
      AND term.record_status = 1
    ORDER BY term.startDate DESC LIMIT 1;

  OPEN cur;
  FETCH cur INTO rate, dIYMethod;
  CLOSE cur;

  IF dIYMethod = 1 THEN
    SET nOD = 360;
  ELSEIF dIYMethod = 3 THEN
    SET nOD = 366;
  END IF;

  IF principalOverdue < 0 THEN
    SET principalOverdue = 0;
  END IF;

  SET result = (principalOverdue*rate/nOD)/100*daysInPeriod;

  if result is null then
    set result = 0;
  end if;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getCollectedIntDisbursed` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getCollectedIntDisbursed`(loanId bigint, in_date date) RETURNS double
BEGIN

  DECLARE result DOUBLE DEFAULT 0;

  DECLARE cur CURSOR FOR
    select ifnull(sum(ps.collectedInterestPayment),0)
    from paymentSchedule ps
    where ps.loanId = loanId
      and ps.record_status = 1
      and ((ps.collectedInterestPayment > 0 and ps.expectedDate > in_date) or (ps.expectedDate <= in_date));

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET result = 0;

  OPEN cur;
  FETCH cur INTO result;
  CLOSE cur;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getCollectedPenDisbursed` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getCollectedPenDisbursed`(loanId bigint, in_date date) RETURNS double
BEGIN

  DECLARE result DOUBLE DEFAULT 0;

  DECLARE cur CURSOR FOR
    select ifnull(sum(ps.collectedPenaltyPayment),0)
    from paymentSchedule ps
    where ps.loanId = loanId
      and ps.record_status = 1
      and ((ps.collectedPenaltyPayment > 0 and ps.expectedDate > in_date) or (ps.expectedDate <= in_date));

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET result = 0;

  OPEN cur;
  FETCH cur INTO result;
  CLOSE cur;



  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getDIMMethod` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getDIMMethod`(inDate date, loan_id bigint) RETURNS int(11)
BEGIN

  DECLARE dIMMethod INT DEFAULT 2;

  SELECT term.daysInMonthMethodId INTO dIMMethod
  FROM creditTerm term
  WHERE term.loanId = loan_id
    AND term.startDate < inDate
    AND term.record_status = 1
  ORDER BY term.startDate DESC LIMIT 1;

  RETURN dIMMethod;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getDIYMethod` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getDIYMethod`(inDate date, loan_id bigint) RETURNS int(11)
BEGIN

  DECLARE dIYMethod INT DEFAULT 2;

  SELECT term.daysInYearMethodId INTO dIYMethod
  FROM creditTerm term
  WHERE term.loanId = loan_id
    AND term.startDate < inDate
    AND term.record_status = 1
  ORDER BY term.startDate DESC LIMIT 1;

  RETURN dIYMethod;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getLoanAmount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getLoanAmount`(loanId BIGINT) RETURNS double
BEGIN

  DECLARE result DOUBLE DEFAULT 0;

  SELECT loan.amount INTO result from loan where id = loanId;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getPenaltyLimitPercent` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getPenaltyLimitPercent`(inDate date, loan_id bigint) RETURNS int(11)
BEGIN

  DECLARE penalty_limit DOUBLE;

  select IFNULL(penaltyLimitPercent, 0) INTO penalty_limit
  from creditTerm
  where loanId = loan_id
    and startDate < inDate
  order by startDate desc limit 1;

  RETURN penalty_limit;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `getTransactionOrder` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getTransactionOrder`(inDate date, loan_id bigint) RETURNS int(11)
BEGIN

  DECLARE transactionOrder INT DEFAULT 1;

  SELECT term.transactionOrderId INTO transactionOrder
  FROM creditTerm term
  WHERE term.loanId = loan_id
    AND term.startDate < inDate
    AND term.record_status = 1
  ORDER BY term.startDate DESC LIMIT 1;

  RETURN transactionOrder;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `hasLiborType` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `hasLiborType`(loan_id bigint, inDate date) RETURNS tinyint(1)
BEGIN

  DECLARE result BOOLEAN DEFAULT FALSE;
  DECLARE rateTypeId BIGINT;

  SELECT cTerm.floatingRateTypeId
         INTO rateTypeId
  FROM creditTerm cTerm
  WHERE cTerm.loanId = loan_id
    AND cTerm.startDate < inDate
    AND cTerm.record_status = 1
  ORDER BY cTerm.startDate DESC LIMIT 1;

  IF rateTypeId != 2 THEN SET result = TRUE;
  END IF;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `hasLiborTypeIO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `hasLiborTypeIO`(loan_id bigint, inDate date) RETURNS tinyint(1)
BEGIN

  DECLARE result BOOLEAN DEFAULT FALSE;
  DECLARE rateTypeId BIGINT;

  SELECT cTerm.penaltyOnInterestOverdueRateTypeId
         INTO rateTypeId
  FROM creditTerm cTerm
  WHERE cTerm.loanId = loan_id
    AND cTerm.startDate < inDate
    AND cTerm.record_status = 1
  ORDER BY cTerm.startDate DESC LIMIT 1;

  IF rateTypeId != 2 THEN SET result = TRUE;
  END IF;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `hasLiborTypePO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `hasLiborTypePO`(loan_id bigint, inDate date) RETURNS tinyint(1)
BEGIN

  DECLARE result BOOLEAN DEFAULT FALSE;
  DECLARE rateTypeId BIGINT;

  SELECT cTerm.penaltyOnPrincipleOverdueRateTypeId
         INTO rateTypeId
  FROM creditTerm cTerm
  WHERE cTerm.loanId = loan_id
    AND cTerm.startDate < inDate
    AND cTerm.record_status = 1
  ORDER BY cTerm.startDate DESC LIMIT 1;

  IF rateTypeId != 2 THEN SET result = TRUE;
  END IF;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `isDateFirstDayOfMonth` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `isDateFirstDayOfMonth`(inDate DATE) RETURNS tinyint(1)
BEGIN

  DECLARE result BOOLEAN DEFAULT FALSE;
  DECLARE tempDate DATE;

  SELECT DATE_SUB(inDate, INTERVAL DAYOFMONTH(inDate)-1 DAY) INTO tempDate;

  IF tempDate = inDate THEN SET result = TRUE;
  END IF;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `isLoanDetailedSummaryExistsForDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `isLoanDetailedSummaryExistsForDate`(loan_id BIGINT, inDate DATE) RETURNS tinyint(1)
BEGIN

  DECLARE result BOOLEAN DEFAULT FALSE;
  DECLARE cCount INT;

  SELECT COUNT(1) FROM loanDetailedSummary tt where tt.loanId = loan_id AND tt.onDate = inDate INTO cCount;

  IF cCount > 0 THEN SET result = TRUE;
  END IF;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `isPaymentScheduleLastPaymentDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `isPaymentScheduleLastPaymentDate`(inDate date, loanId bigint) RETURNS tinyint(1)
BEGIN

  DECLARE result BOOLEAN DEFAULT FALSE;
  DECLARE expectedDate DATE;

  DECLARE cur CURSOR FOR
    SELECT ps.expectedDate FROM paymentSchedule ps
    WHERE ps.loanId = loanId
      AND (ps.principalPayment > 0 OR ps.interestPayment > 0)
      AND ps.record_status = 1
    ORDER BY ps.expectedDate DESC LIMIT 1;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET result = FALSE;

  OPEN cur;
  FETCH cur INTO expectedDate;
  CLOSE cur;

  IF DATEDIFF(inDate, expectedDate) >= 0 THEN SET result = TRUE;
  END IF;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `isPaymentSchedulePaymentDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `isPaymentSchedulePaymentDate`(inDate date, loanId bigint) RETURNS tinyint(1)
BEGIN

  DECLARE result BOOLEAN DEFAULT TRUE;
  DECLARE iPayment DOUBLE;

  DECLARE cur CURSOR FOR
    SELECT ps.interestPayment FROM paymentSchedule ps
    WHERE ps.loanId = loanId
      AND ps.expectedDate = inDate
      AND ps.record_status = 1;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET result = FALSE;

  OPEN cur;
  FETCH cur INTO iPayment;
  CLOSE cur;

  IF iPayment <= 0 THEN SET result = false;
  END IF;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `isTermFound` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `isTermFound`(loan_id bigint, inDate date) RETURNS tinyint(1)
BEGIN

  DECLARE result BOOLEAN DEFAULT FALSE;
  DECLARE cCount INT;

  SELECT COUNT(1)
  FROM creditTerm cTerm
  where cTerm.loanId = loan_id
    AND cTerm.startDate <= inDate
    AND cTerm.record_status = 1 INTO cCount;

  IF cCount > 0 THEN SET result = TRUE;
  END IF;

  RETURN result;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `calculateLoanDetailedSummaryUntilOnDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `calculateLoanDetailedSummaryUntilOnDate`(IN loan_id bigint, IN inDate date,
                                                                                      IN includeToday tinyint(1),
                                                                                      IN loan_summary_type varchar(20))
BEGIN
  DECLARE tempDate DATE;
  DECLARE srokDate DATE;
  DECLARE prevDate DATE;
  DECLARE daysInPer INT DEFAULT 0;
  DECLARE disb DOUBLE;
  DECLARE totalDisb DOUBLE DEFAULT 0;
  DECLARE princPaid DOUBLE;
  DECLARE princPaidKGS DOUBLE;
  DECLARE totalPrincPaid DOUBLE DEFAULT 0;
  DECLARE princPayment DOUBLE;
  DECLARE totalPrincPayment DOUBLE DEFAULT 0;
  DECLARE princOutstanding DOUBLE DEFAULT 0;
  DECLARE princOverdue DOUBLE DEFAULT 0;
  DECLARE intAccrued DOUBLE DEFAULT 0;
  DECLARE totalIntAccrued DOUBLE DEFAULT 0;
  DECLARE intPaid DOUBLE;
  DECLARE intPaidKGS DOUBLE;
  DECLARE totalIntPaid DOUBLE DEFAULT 0;
  DECLARE intPayment DOUBLE DEFAULT 0;
  DECLARE totalIntPayment DOUBLE DEFAULT 0;
  DECLARE collIntPayment DOUBLE;
  DECLARE totalCollIntPayment DOUBLE DEFAULT 0;
  DECLARE intOverdue DOUBLE DEFAULT 0;

  DECLARE grace_interest_amount DOUBLE DEFAULT 0;

  DECLARE penAccrued DOUBLE DEFAULT 0;
  DECLARE totalPenAccrued DOUBLE DEFAULT 0;
  DECLARE penPaid DOUBLE;
  DECLARE penPaidKGS DOUBLE;
  DECLARE totalPenPaid DOUBLE DEFAULT 0;
  DECLARE collPenPayment DOUBLE;
  DECLARE totalCollPenPayment DOUBLE DEFAULT 0;
  DECLARE penOverdue DOUBLE DEFAULT 0;
  DECLARE penOutstanding DOUBLE DEFAULT 0;
  DECLARE collPenDisbursed DOUBLE;
  DECLARE intOutstanding DOUBLE DEFAULT 0;
  DECLARE collIntDisbursed DOUBLE;
  DECLARE totalIntAccruedOnIntPayment DOUBLE DEFAULT 0;

  DECLARE interestAccruedInPeriod DOUBLE DEFAULT 0;

  DECLARE pOPO DOUBLE DEFAULT 0;
  DECLARE pOIO DOUBLE DEFAULT 0;
  DECLARE pDate DATE;
  DECLARE loan_amount DOUBLE DEFAULT 0;
  DECLARE total_outstanding DOUBLE DEFAULT 0;
  DECLARE total_overdue DOUBLE DEFAULT 0;
  DECLARE total_paid DOUBLE DEFAULT 0;
  DECLARE total_paidKGS DOUBLE DEFAULT 0;
  DECLARE cur_rate DOUBLE;

  DECLARE penalty_limit DOUBLE DEFAULT 0;
  DECLARE penalty_limit_flag BOOLEAN DEFAULT FALSE;

  DECLARE paymentTotalAmount DOUBLE;

  DECLARE paymentTotalDiff DOUBLE;

  DECLARE v_finished INTEGER DEFAULT 0;

  DECLARE errno INT;
  DECLARE msg TEXT;

  DECLARE flag BOOLEAN DEFAULT FALSE;
  DECLARE isAlreadyInserted BOOLEAN DEFAULT FALSE;

  DECLARE afterSrokDate BOOLEAN DEFAULT FALSE;

  DECLARE intOverdueOnSrokDate DOUBLE DEFAULT 0;

  DECLARE paymentSumAfterSpecDate DOUBLE DEFAULT 0;

  DECLARE paymentSumBeforeSpecDate DOUBLE DEFAULT 0;

  DECLARE limitDate DATE;

  DECLARE pType VARCHAR(20);
  DECLARE wo_princ DOUBLE;
  DECLARE wo_int DOUBLE;
  DECLARE wo_pen DOUBLE;
  DECLARE total_wo_princ DOUBLE DEFAULT 0;
  DECLARE total_wo_int DOUBLE DEFAULT 0;
  DECLARE total_wo_pen DOUBLE DEFAULT 0;

  DECLARE judge_princ DOUBLE;
  DECLARE judge_int DOUBLE;
  DECLARE judge_pen DOUBLE;
  DECLARE total_judge_princ DOUBLE DEFAULT 0;
  DECLARE total_judge_int DOUBLE DEFAULT 0;
  DECLARE total_judge_pen DOUBLE DEFAULT 0;

  DECLARE term_rate_type_id INT;
  DECLARE term_rate_po_id INT;
  DECLARE term_rate_io_id INT;
  DECLARE term_diy_method_id INT;
  DECLARE term_diy_method_popo_id INT;
  DECLARE term_diy_method_poio_id INT;
  DECLARE term_dim_method_id INT;
  DECLARE term_dim_method_popo_id INT;
  DECLARE term_dim_method_poio_id INT;
  DECLARE has_libor BOOLEAN;
  DECLARE has_libor_po BOOLEAN;
  DECLARE has_libor_io BOOLEAN;
  DECLARE date_prev_loan_summary DATE;
  DECLARE loan_summary_type_text VARCHAR(20);
  DECLARE futurePrincPaid DOUBLE DEFAULT 0;
  DECLARE totalFuturePrincPaid DOUBLE DEFAULT 0;
  DECLARE futureAccrued DOUBLE DEFAULT 0;

  DEClARE tCursor CURSOR FOR

    SELECT
      pp.type,
      pp.onDate,
      pp.disbursement,
      pp.principalPaid,
      pp.principalPayment,
      pp.interestPaid,
      pp.collectedInterestPayment,
      pp.penaltyPaid,
      pp.collectedPenaltyPayment,
      pp.pTotalAmount,
      CASE curRate WHEN 0 THEN 1 ELSE curRate END AS curRate
    FROM
      (

        SELECT
          'term' as type,
          2 as orderType,
          term.startDate AS onDate,
          0              AS disbursement,
          0              AS principalPaid,
          0              AS principalPayment,
          0 as interestPaid,
          0 as collectedInterestPayment,
          0 as penaltyPaid,
          0 as collectedPenaltyPayment,
          0 as pTotalAmount,
          1 as curRate
        FROM loan loan, creditTerm term
        WHERE loan.id = term.loanId
          AND loan.id = loan_id
          AND term.startDate < inDate
          AND term.record_status = 1

        UNION ALL

        SELECT
          'payment' as type,
          3 as orderType,
          MIN(payment.paymentDate) AS onDate,
          0                   AS disbursement,
          SUM(CASE WHEN payment.in_loan_currency and loan.currencyId > 1 THEN payment.principal*payment.exchange_rate ELSE payment.principal END) AS principalPaid,
          0                   AS principalPayment,
          SUM(CASE WHEN payment.in_loan_currency and loan.currencyId > 1 THEN payment.interest*payment.exchange_rate ELSE payment.interest END) as interestPaid,
          0 as collectedInterestPayment,
          SUM(CASE WHEN payment.in_loan_currency and loan.currencyId > 1 THEN payment.penalty*payment.exchange_rate ELSE payment.penalty END) as penaltyPaid,
          0 as collectedPenaltyPayment,
          SUM(CASE WHEN payment.in_loan_currency and loan.currencyId > 1 THEN payment.totalAmount*payment.exchange_rate ELSE payment.totalAmount END) as pTotalAmount,
          MIN(payment.exchange_rate) as curRate
        FROM loan loan, payment payment
        WHERE loan.id = payment.loanId
          AND loan.id = loan_id
          AND payment.paymentDate < inDate
          AND payment.record_status = 1
        GROUP BY payment.paymentDate

        UNION ALL

        SELECT
          'write off' as type,
          4 as orderType,
          wo.date as onDate,
          0,
          IFNULL(wo.principal, 0) as principalPaid,
          0,
          IFNULL(wo.interest, 0) as interestPaid,
          0,
          IFNULL(wo.penalty, 0) as penaltyPaid,
          0,
          0,
          1 as curRate
        FROM loan loan, writeOff wo
        WHERE loan.id = wo.loanId
          AND loan.id = loan_id
          AND wo.date < inDate
          AND wo.record_status = 1

        UNION ALL

        SELECT
          'judgement' as type,
          5 as orderType,
          jd.date as onDate,
          0,
          IFNULL(jd.principal, 0) as principalPaid,
          0,
          IFNULL(jd.interest, 0) as interestPaid,
          0,
          IFNULL(jd.penalty, 0) as penaltyPaid,
          0,
          0,
          1 as curRate
        FROM loan loan, judgement jd
        WHERE loan.id = jd.loanId
          AND loan.id = loan_id
          AND jd.date <= inDate
          AND jd.record_status = 1

        UNION ALL

        SELECT
          'payment schedule' as type,
          1 as orderType,
          ps.expectedDate                  AS onDate,
          ps.disbursement                  AS disbursement,
          0                                AS principalPaid,
          ps.principalPayment              AS principalPayment,
          0 as interestPaid,
          ps.collectedInterestPayment,
          0 as penaltyPaid,
          ps.collectedPenaltyPayment,
          0 as pTotalAmount,
          1 as curRate
        FROM loan loan, paymentSchedule ps
        WHERE loan.id = ps.loanId
          AND loan.id = loan_id
          AND ps.expectedDate < inDate
          AND ps.record_status = 1

        UNION ALL

        SELECT
          'dummy date' as type,
          5 as orderType,
          inDate AS onDate,
          0            AS disbursement,
          0            AS principalPaid,
          0            AS principalPayment,
          0 as interestPaid,
          0 as collectedInterestPayment,
          0 as penaltyPaid,
          0 as collectedPenaltyPayment,
          0 as pTotalAmount,
          1 as curRate
        FROM dual
        WHERE includeToday = 1

        UNION ALL

        SELECT
          'future payment' as type,
          7 as orderType,
          ps.expectedDate AS onDate,
          ps.disbursement AS disbursement,
          ps.principalPayment AS principalPaid,
          ps.principalPayment AS principalPayment,
          0 as interestPaid,
          ps.collectedInterestPayment,
          0 as penaltyPaid,
          ps.collectedPenaltyPayment,
          0 as pTotalAmount,
          1 as curRate
        FROM loan loan, paymentSchedule ps
        WHERE loan.id = ps.loanId
          AND loan.id = loan_id
          AND ps.expectedDate >= inDate
          AND ps.record_status = 1
          AND loan_summary_type = 'SYSTEM'
      ) pp
    ORDER BY pp.onDate, pp.orderType, pp.disbursement desc;

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

  START TRANSACTION;

  #get loan amount
  SELECT amount INTO loan_amount from loan where id = loan_id;

  #     select IFNULL(SUM(CASE WHEN payment.in_loan_currency THEN payment.penalty*payment.exchange_rate ELSE payment.penalty END), 0) INTO paymentSumAfterSpecDate from payment where loanId = loan_id and paymentDate >= '2014-11-25';

  IF !isTermFound(loan_id, inDate) THEN
    SET v_finished = 1;
    INSERT INTO logCalculateLoanSummary(version, loanId, onDate, message)
    VALUES (1,loan_id, inDate, 'No term info found');
  END IF;

  SET loan_summary_type_text = loan_summary_type;

  OPEN tCursor;

  get_data: LOOP

    FETCH tCursor INTO pType, tempDate,disb, princPaid, princPayment,
      intPaid, collIntPayment, penPaid,
      collPenPayment, paymentTotalAmount, cur_rate;

    IF v_finished = 1 THEN
      LEAVE get_data;
    END IF;

    if tempDate >= inDate and pType = 'future payment' then
      set futurePrincPaid = princPaid;
#       set totalFuturePrincPaid = totalFuturePrincPaid + futurePrincPaid;
      set princPaid = 0;
    end if;

    SET penalty_limit = getPenaltyLimitPercent(tempDate, loan_id);

    set has_libor = false;

    IF EXISTS(
            SELECT cTerm.floatingRateTypeId, cTerm.daysInYearMethodId, cTerm.daysInMonthMethodId
            FROM creditTerm cTerm
            WHERE cTerm.loanId = loan_id
              AND cTerm.startDate < tempDate
              AND cTerm.record_status = 1
            ORDER BY cTerm.startDate DESC LIMIT 1
      ) THEN
      SELECT cTerm.floatingRateTypeId, cTerm.daysInYearMethodId, cTerm.daysInMonthMethodId
             INTO term_rate_type_id, term_diy_method_id, term_dim_method_id
      FROM creditTerm cTerm
      WHERE cTerm.loanId = loan_id
        AND cTerm.startDate < tempDate
        AND cTerm.record_status = 1
      ORDER BY cTerm.startDate DESC LIMIT 1;
    END IF;

    if term_rate_type_id is not null then
      if term_rate_type_id  != 2 then
        set has_libor = true;
      end if;
    end if;

    set has_libor_po = false;

    IF EXISTS(
            SELECT cTerm.penaltyOnPrincipleOverdueRateTypeId, cTerm.daysInYearMethodId, cTerm.daysInMonthMethodId
            FROM creditTerm cTerm
            WHERE cTerm.loanId = loan_id
              AND cTerm.startDate < tempDate
              AND cTerm.record_status = 1
            ORDER BY cTerm.startDate DESC LIMIT 1
      ) THEN
      SELECT cTerm.penaltyOnPrincipleOverdueRateTypeId, cTerm.daysInYearMethodId, cTerm.daysInMonthMethodId
             INTO term_rate_po_id, term_diy_method_popo_id, term_dim_method_popo_id
      FROM creditTerm cTerm
      WHERE cTerm.loanId = loan_id
        AND cTerm.startDate < tempDate
        AND cTerm.record_status = 1
      ORDER BY cTerm.startDate DESC LIMIT 1;
    END IF;

    if term_rate_po_id is not null then
      if term_rate_po_id  != 2 then
        set has_libor_po = true;
      end if;
    end if;

    set has_libor_io = false;

    IF EXISTS(
            SELECT cTerm.penaltyOnInterestOverdueRateTypeId, cTerm.daysInYearMethodId, cTerm.daysInMonthMethodId
            FROM creditTerm cTerm
            WHERE cTerm.loanId = loan_id
              AND cTerm.startDate < tempDate
              AND cTerm.record_status = 1
            ORDER BY cTerm.startDate DESC LIMIT 1
      ) THEN
      SELECT cTerm.penaltyOnInterestOverdueRateTypeId, cTerm.daysInYearMethodId, cTerm.daysInMonthMethodId
             INTO term_rate_io_id, term_diy_method_poio_id, term_dim_method_poio_id
      FROM creditTerm cTerm
      WHERE cTerm.loanId = loan_id
        AND cTerm.startDate < tempDate
        AND cTerm.record_status = 1
      ORDER BY cTerm.startDate DESC LIMIT 1;
    END IF;

    if term_rate_io_id is not null then
      if term_rate_io_id  != 2 then
        set has_libor_io = true;
      end if;
    end if;

    IF flag = FALSE THEN
      SET prevDate = tempDate;
      SET pDate = tempDate;
    END IF;

    IF pType = 'write off' THEN
      SET wo_princ = princPaid;
      SET wo_int = intPaid;
      SET wo_pen = penPaid;
      SET princPaid = 0;
      SET intPaid = 0;
      SET penPaid = 0;

      SET total_wo_princ = total_wo_princ + wo_princ;
      SET total_wo_int = total_wo_int + wo_int;
      SET total_wo_pen = total_wo_pen + wo_pen;
    END IF;

    IF pType = 'judgement' THEN
      SET judge_princ = princPaid;
      SET judge_int = intPaid;
      SET judge_pen = penPaid;
      SET princPaid = 0;
      SET intPaid = 0;
      SET penPaid = 0;

      SET total_judge_princ = total_judge_princ + judge_princ;
      SET total_judge_int = total_judge_int + judge_int;
      SET total_judge_pen = total_judge_pen + judge_pen;
    END IF;

    SET princPaidKGS = princPaid;
    SET intPaidKGS = intPaid;
    SET penPaidKGS = penPaid;

    SET princPaid = princPaid/cur_rate;
    SET intPaid = intPaid/cur_rate;
    SET penPaid = penPaid/cur_rate;

    SET paymentTotalAmount = paymentTotalAmount/cur_rate;

    SET paymentTotalDiff = paymentTotalAmount - princPaid - intPaid - penPaid;

    IF (paymentTotalDiff>1) THEN
      IF (getTransactionOrder(inDate,loan_id)=1) THEN

        IF ((princOverdue- princPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((princOverdue- princPaid) > paymentTotalDiff) THEN
            SET princPaid = princPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE
            SET paymentTotalDiff = paymentTotalDiff - (princOverdue- princPaid);
            SET princPaid = princPaid + (princOverdue- princPaid);

          END IF;
        END IF;

        IF ((intOverdue-intPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((intOverdue-intPaid) > paymentTotalDiff) THEN
            SET intPaid = intPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (intOverdue-intPaid);
            SET intPaid = intPaid + (intOverdue-intPaid);
          END IF;
        END IF;


        IF ((penOverdue-penPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((penOverdue-penPaid) > paymentTotalDiff) THEN
            SET penPaid = penPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (penOverdue-penPaid);
            SET penPaid = penPaid + (penOverdue-penPaid);
          END IF;
        END IF;

        IF ((princOutstanding-princPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((princOutstanding-princPaid) > paymentTotalDiff) THEN
            SET princPaid = princPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (princOutstanding-princPaid);
            SET princPaid = princPaid + (princOutstanding-princPaid);
          END IF;
        END IF;

        IF ((intOutstanding-intPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((intOutstanding-intPaid) > paymentTotalDiff) THEN
            SET intPaid = intPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (intOutstanding-intPaid);
            SET intPaid = intPaid + (intOutstanding-intPaid);
          END IF;
        END IF;


        IF ((penOutstanding-penPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((penOutstanding-penPaid) > paymentTotalDiff) THEN
            SET penPaid = penPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (penOutstanding-penPaid);
            SET penPaid = penPaid + (penOutstanding-penPaid);
          END IF;
        END IF;

        IF (paymentTotalDiff > 0 ) THEN
          SET penPaid = penPaid + paymentTotalDiff;
          SET paymentTotalDiff = 0;
        END IF;

      ELSE

        IF ((princOverdue- princPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((princOverdue- princPaid) > paymentTotalDiff) THEN
            SET princPaid = princPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (princOverdue- princPaid);
            SET princPaid = princPaid + (princOverdue- princPaid);
          END IF;
        END IF;

        IF ((intOverdue-intPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((intOverdue-intPaid) > paymentTotalDiff) THEN
            SET intPaid = intPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (intOverdue-intPaid);
            SET intPaid = intPaid + (intOverdue-intPaid);
          END IF;
        END IF;


        IF ((penOverdue-penPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((penOverdue-penPaid) > paymentTotalDiff) THEN
            SET penPaid = penPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (penOverdue-penPaid);
            SET penPaid = penPaid + (penOverdue-penPaid);
          END IF;
        END IF;

        IF ((princOutstanding-princPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((princOutstanding-princPaid) > paymentTotalDiff) THEN
            SET princPaid = princPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (princOutstanding-princPaid);
            SET princPaid = princPaid + (princOutstanding-princPaid);
          END IF;
        END IF;

        IF ((intOutstanding-intPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((intOutstanding-intPaid) > paymentTotalDiff) THEN
            SET intPaid = intPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (intOutstanding-intPaid);
            SET intPaid = intPaid + (intOutstanding-intPaid);
          END IF;
        END IF;


        IF ((penOutstanding-penPaid) > 0 AND paymentTotalDiff > 0 ) THEN
          IF ((penOutstanding-penPaid) > paymentTotalDiff) THEN
            SET penPaid = penPaid + paymentTotalDiff;
            SET paymentTotalDiff = 0;
          ELSE

            SET paymentTotalDiff = paymentTotalDiff - (penOutstanding-penPaid);
            SET penPaid = penPaid + (penOutstanding-penPaid);
          END IF;
        END IF;

        IF (paymentTotalDiff > 0 ) THEN
          SET penPaid = penPaid + paymentTotalDiff;
          SET paymentTotalDiff = 0;
        END IF;

      END IF;

    END IF;

    SET princPaidKGS = princPaid*cur_rate;
    SET intPaidKGS = intPaid*cur_rate;
    SET penPaidKGS = penPaid*cur_rate;

    SET daysInPer = calculateDays(prevDate, tempDate, getDIMMethod(tempDate, loan_id));

    SET intAccrued = calculateInterestAccrued(princOutstanding, daysInPer, tempDate, loan_id);

    if tempDate > inDate then
      SET futureAccrued = calculateInterestAccrued(princOutstanding-totalFuturePrincPaid, daysInPer, tempDate, loan_id);
    end if;

    IF has_libor THEN
      SET intAccrued = intAccrued + calculateLibor(princOutstanding, prevDate, tempDate, term_rate_type_id, term_diy_method_id, term_dim_method_id, loan_id, 0);

      if tempDate > inDate then
        SET futureAccrued = futureAccrued + calculateLibor(princOutstanding-totalFuturePrincPaid, prevDate, tempDate, term_rate_type_id, term_diy_method_id, term_dim_method_id, loan_id, 0);
      end if;

    END IF;


    if tempDate >= inDate and pType = 'future payment' then
      set totalFuturePrincPaid = totalFuturePrincPaid + futurePrincPaid;
    end if;

    SET totalIntAccrued = totalIntAccrued + intAccrued;

    SET penAccrued = calculatePenaltyAccrued(princOverdue-total_judge_princ, intOverdue-total_judge_int, daysInPer, tempDate, loan_id);

    IF srokDate is not null THEN

      IF tempDate < '2010-04-02' THEN
        SET penAccrued = calculatePenaltyAccrued(princOverdue-total_judge_princ, ((intOverdueOnSrokDate-totalIntPaid+intOverdue-total_judge_int+intAccrued-intPaid)/2), daysInPer, tempDate, loan_id);

        #           SET penAccrued = princOverdue;
        IF has_libor_io THEN
          SET penAccrued = penAccrued
            + calculateLibor(((intOverdueOnSrokDate-totalIntPaid+intOverdue-total_judge_int+intAccrued-intPaid)/2), prevDate, tempDate, term_rate_io_id, term_diy_method_id, term_dim_method_id, loan_id, 2);
        END IF;



      ELSE
        IF daysInPer > 0 THEN

          SET penAccrued = penAccrued
            + calculatePenaltyAccrued(0, (intAccrued*(daysInPer-1)/(2*daysInPer)), daysInPer, tempDate, loan_id);

          IF has_libor_io THEN
            SET penAccrued = penAccrued
              + calculateLibor((intAccrued*(daysInPer-1)/(2*daysInPer)), prevDate, tempDate, term_rate_io_id, term_diy_method_id, term_dim_method_id, loan_id, 2);
          END IF;

        END IF;
      END IF;


    ELSE
      SET penAccrued = penAccrued;
    END IF;

    IF isPaymentScheduleLastPaymentDate(tempDate, loan_id) THEN
      SET srokDate = tempDate;
      SET afterSrokDate = true;
    END IF;

    IF has_libor_po THEN
      SET penAccrued = penAccrued + calculateLibor(princOverdue-total_judge_princ, prevDate, tempDate, term_rate_po_id, term_diy_method_id, term_dim_method_id, loan_id, 1);
    END IF;

    IF has_libor_io THEN
      SET penAccrued = penAccrued + calculateLibor(intOverdue-total_judge_int, prevDate, tempDate, term_rate_io_id, term_diy_method_id, term_dim_method_id, loan_id,2);
    END IF;

    IF penalty_limit_flag THEN
      SET penAccrued = 0;
    END IF;

    SET totalPenAccrued = totalPenAccrued + penAccrued;
    SET totalDisb = totalDisb + disb;

    IF NOT penalty_limit_flag THEN
      IF totalDisb > 0 and penalty_limit > 0 AND totalPenAccrued >= totalDisb*penalty_limit/100+paymentSumBeforeSpecDate AND tempDate >= '2014-11-25' THEN
        SET totalPenAccrued = totalDisb*penalty_limit/100+paymentSumBeforeSpecDate;
        SET penalty_limit_flag = TRUE;
        UPDATE creditTerm SET penaltyLimitEndDate = tempDate WHERE loanId = loan_id;
        SET limitDate = tempDate;
      END IF;
    END IF;

    #       IF NOT penalty_limit_flag THEN
    #         IF penalty_limit > 0 AND totalPenAccrued >= totalDisb*penalty_limit/100-paymentSumAfterSpecDate AND tempDate >= '2014-11-25' THEN
    #           SET totalPenAccrued = totalDisb*penalty_limit/100-paymentSumAfterSpecDate;
    #           SET penalty_limit_flag = TRUE;
    #           UPDATE creditTerm SET penaltyLimitEndDate = tempDate WHERE loanId = loan_id;
    #         END IF;
    #       END IF;

    SET totalPrincPaid = totalPrincPaid + princPaid;

    IF total_judge_princ > 0 THEN
      SET total_judge_princ = total_judge_princ - princPaid;
      IF total_judge_princ < 0 THEN
        SET total_judge_princ = 0;
      END IF;
    END IF;

    SET totalPrincPayment = totalPrincPayment + princPayment;
    SET prevDate = tempDate;

    SET princOutstanding = totalDisb - totalPrincPaid - total_wo_princ;
    SET princOverdue = totalPrincPayment - totalPrincPaid - total_wo_princ;

    #       IF pType = 'write off' THEN
    #         SET princOutstanding = princOutstanding - total_wo_princ;
    #         SET princOverdue = princOverdue - total_wo_princ;
    # #         SET princOverdue = princOverdue - total_wo_princ;
    #       end if;

    SET totalIntPaid = totalIntPaid + intPaid;

    IF total_judge_int > 0 THEN
      SET total_judge_int = total_judge_int - intPaid;
      IF total_judge_int < 0 THEN
        SET total_judge_int = 0;
      END IF;
    END IF;

    IF (tempDate != inDate OR afterSrokDate) THEN
      IF (isPaymentSchedulePaymentDate(tempDate, loan_id) OR isPaymentScheduleLastPaymentDate(tempDate, loan_id)) OR afterSrokDate THEN
        SET intPayment = totalIntAccrued - totalIntAccruedOnIntPayment;
        SET totalIntAccruedOnIntPayment = totalIntAccrued;
      ELSE
        SET intPayment = 0;
      END IF;
    ELSEIF (tempDate = inDate) THEN
      SET intPayment = 0;
    END IF;

    SET totalCollPenPayment = totalCollPenPayment + collPenPayment;
    SET totalPenPaid = totalPenPaid + penPaid;

    IF penalty_limit > 0 AND tempDate < '2014-11-25' AND penPaid > 0 THEN
      SET paymentSumBeforeSpecDate=paymentSumBeforeSpecDate+penPaid;
    END IF;

    IF penalty_limit > 0 AND tempDate >= '2014-11-25' AND penPaid > 0 THEN
      SET paymentSumAfterSpecDate=paymentSumAfterSpecDate+penPaid;
    END IF;

    SET penOverdue = totalPenAccrued + totalCollPenPayment - totalPenPaid - total_wo_pen;
    SET collPenDisbursed = getCollectedPenDisbursed(loan_id, tempDate);

    SET penOutstanding = totalPenAccrued + collPenDisbursed - totalPenPaid - total_wo_pen;

    #       IF pType = 'write off' THEN
    #         SET penOutstanding = penOutstanding - total_wo_pen;
    #         SET penOverdue = penOverdue - total_wo_pen;
    #       END IF;

    IF penalty_limit > 0 AND tempDate >= '2014-11-25' THEN
      IF penOutstanding > (totalDisb*penalty_limit/100) - paymentSumAfterSpecDate THEN
        #           SET penOutstanding = totalDisb*penalty_limit/100 + paymentSumBeforeSpecDate - paymentSumAfterSpecDate;
        SET penOutstanding = totalDisb*penalty_limit/100  - paymentSumAfterSpecDate;
      END IF;

      IF penOverdue > (totalDisb*penalty_limit/100)  - paymentSumAfterSpecDate THEN
        SET penOverdue = totalDisb*penalty_limit/100 - paymentSumAfterSpecDate;
      END IF;
    END IF;

    SET collIntDisbursed = getCollectedIntDisbursed(loan_id, tempDate);
    SET intOutstanding = totalIntAccrued + collIntDisbursed - totalIntPaid - total_wo_int;
    SET totalCollIntPayment = totalCollIntPayment + collIntPayment;

    if loan_id = 1107 then
      if tempDate <= '2016-01-01' then
        SET grace_interest_amount = grace_interest_amount+intPayment*1.0/3.0;
        set intPayment = intPayment*2.0/3.0;
      end if;

      if tempDate = '2016-07-01' then
        set totalIntPayment = totalIntPayment+grace_interest_amount;
      end if;
    end if;

    SET totalIntPayment = totalIntPayment + intPayment;

    SET intOverdue = totalIntPayment + totalCollIntPayment - totalIntPaid - total_wo_int;

    #       SET penOverdue = intPaid;

    IF isPaymentSchedulePaymentDate(tempDate, loan_id) THEN
      SET intOverdueOnSrokDate = intOverdue+totalIntPaid;
      #         SET penOverdue = intPaid;

    END IF;

    #       IF pType = 'write off' THEN
    #         SET intOutstanding = intOutstanding - total_wo_int;
    #         SET intOverdue = intOverdue - total_wo_int;
    #       END IF;

    SET total_outstanding = (CASE WHEN princOutstanding >= 0 THEN princOutstanding ELSE 0 END) +
                            (CASE WHEN intOutstanding >= 0 THEN intOutstanding ELSE 0 END) +
                            (CASE WHEN penOutstanding >= 0 THEN penOutstanding ELSE 0 END);

    SET total_overdue = (CASE WHEN princOverdue >= 0 THEN princOverdue ELSE 0 END) +
                        (CASE WHEN intOverdue >= 0 THEN intOverdue ELSE 0 END) +
                        (CASE WHEN penOverdue >= 0 THEN penOverdue ELSE 0 END);
    SET total_paid = total_paid + princPaid + intPaid + penPaid;
    SET total_paidKGS = total_paidKGS + princPaidKGS + intPaidKGS + penPaidKGS;

    #IF tempDate <= inDate THEN

    if loan_summary_type_text = 'SYSTEM' then

      INSERT INTO loanDetailedSummary(version, collectedInterestDisbursed, collectedInterestPayment, collectedPenaltyDisbursed, collectedPenaltyPayment, daysInPeriod, disbursement, interestAccrued,
                                      interestOutstanding, interestOverdue, interestPaid, interestPayment, onDate, penaltyAccrued, penaltyOutstanding, penaltyOverdue, penaltyPaid, principalOutstanding,
                                      principalOverdue, principalPaid, principalPayment, principalWriteOff, totalCollectedInterestPayment, totalCollectedPenaltyPayment, totalDisbursement,
                                      totalInterestAccrued, totalInterestAccruedOnInterestPayment, totalInterestPaid, totalInterestPayment, totalPenaltyAccrued, totalPenaltyPaid, totalPrincipalPaid,
                                      totalPrincipalPayment, totalPrincipalWriteOff, loanId)
      VALUES (CASE WHEN afterSrokDate THEN 1 ELSE 0 END, collIntDisbursed, collIntPayment, collPenDisbursed, collPenPayment, daysInPer, disb, intAccrued, intOutstanding,
              intOverdue, intPaid, intPayment, tempDate, penAccrued, penOutstanding, penOverdue, penPaid, princOutstanding,
              princOverdue, princPaid, princPayment, 0, totalCollIntPayment, totalCollPenPayment, totalDisb, totalIntAccrued, paymentSumAfterSpecDate,
              totalIntPaid, totalIntPayment, totalPenAccrued, totalPenPaid, totalPrincPaid, totalPrincPayment, total_paidKGS, loan_id);
    end if;

    #END IF;

    if loan_summary_type_text = 'MANUAL' or loan_summary_type_text = 'FIXED' then

      if exists(select 1 from loanSummary
                where loanId = loan_id
                  and onDate = tempDate
                  and (loanSummaryType = 'MANUAL' or loanSummaryType = 'FIXED')) then
        set isAlreadyInserted = true;
      end if;

    end if;

    IF flag = TRUE THEN
      SET pOPO = calculatePOPO(princOverdue-total_judge_princ, daysInPer, tempDate, loan_id);
      SET pOIO = calculatePOIO(intOverdue-total_judge_int, daysInPer, tempDate, loan_id);

      IF tempDate > inDate THEN
        SET penAccrued = 0;
        SET pOIO = 0;
        SET pOPO = 0;
      END IF;

      if (tempDate < inDate or (tempDate = inDate and (not (pType = 'future payment' )))) then
        SET interestAccruedInPeriod = interestAccruedInPeriod + intAccrued;
      else
        SET interestAccruedInPeriod = interestAccruedInPeriod + futureAccrued;
      end if;

      IF intAccrued + penAccrued + pOIO + pOPO > 0 and tempDate < inDate THEN
        if loan_summary_type_text = 'SYSTEM' or ((loan_summary_type_text = 'MANUAL' or loan_summary_type_text = 'FIXED') and tempDate = inDate) then
          INSERT INTO accrue(version, daysInPeriod, fromDate, interestAccrued, lastInstPassed, penaltyAccrued, penaltyOnInterestOverdue, penaltyOnPrincipalOverdue, toDate, loanId)
          VALUES (1, daysInPer, pDate, intAccrued, FALSE , penAccrued, interestAccruedInPeriod, pOPO, tempDate, loan_id);
        end if;
      else
        if loan_summary_type_text = 'SYSTEM' or ((loan_summary_type_text = 'MANUAL' or loan_summary_type_text = 'FIXED') and tempDate = inDate) then
          INSERT INTO accrue(version, daysInPeriod, fromDate, interestAccrued, lastInstPassed, penaltyAccrued, penaltyOnInterestOverdue, penaltyOnPrincipalOverdue, toDate, loanId)
          VALUES (1, daysInPer, pDate, futureAccrued, FALSE , penAccrued, interestAccruedInPeriod, pOPO, tempDate, loan_id);
        end if;
      END IF;

      IF isPaymentSchedulePaymentDate(tempDate, loan_id)  THEN
        SET interestAccruedInPeriod = 0;
      END IF;

      SET pDate = tempDate;

      IF tempDate <= inDate THEN

        IF (loan_summary_type_text = 'MANUAL' or loan_summary_type_text = 'FIXED') AND isAlreadyInserted THEN
          UPDATE loanSummary SET record_status = 2 WHERE loanId = loan_id AND onDate = tempDate;
        END IF;

        if loan_summary_type_text = 'SYSTEM' or ((loan_summary_type_text = 'MANUAL' or loan_summary_type_text = 'FIXED') and tempDate = inDate) then

          #select onDate into date_prev_loan_summary from loanSummary where loanId = loan_id order by onDate desc limit 1;

          aBlock: begin

            /*
            if pType = 'daily' and date_prev_loan_summary = tempDate then
              leave aBlock;
            end if;
            */

            if not (loan_summary_type_text = 'MANUAL' or loan_summary_type_text = 'FIXED')  then
              if pType = 'dummy date'  then
                set loan_summary_type_text = 'DAILY';
              end if;
            END IF;


            INSERT INTO loanSummary(version, loanSummaryType, loanAmount, onDate, outstadingFee, outstadingInterest, outstadingPenalty, outstadingPrincipal, overdueFee, overdueInterest, overduePenalty,
                                    overduePrincipal, paidFee, paidInterest, paidPenalty, paidPrincipal, totalDisbursed, totalOutstanding, totalOverdue, totalPaid, totalPaidKGS, totalInterestPaid, totalPenaltyPaid, totalPrincipalPaid, totalFeePaid, loanId, createDate)
            VALUES (1, loan_summary_type_text, loan_amount, tempDate, 0.0, intOutstanding, penOutstanding, princOutstanding, 0.0, intOverdue, penOverdue, princOverdue, 0.0, totalIntPaid, totalPrincPaid,
                    princPaid, totalDisb, total_outstanding, total_overdue, total_paid, total_paidKGS, totalIntPaid, totalPenPaid, totalPrincPaid, 0, loan_id, CURDATE());

            set loan_summary_type_text = loan_summary_type;

          end aBlock;

        end if;
      END IF;

    ELSE

      IF tempDate <= inDate THEN

        IF (loan_summary_type_text = 'MANUAL' or loan_summary_type_text = 'FIXED') AND isAlreadyInserted THEN
          UPDATE loanSummary SET record_status = 2 WHERE loanId = loan_id AND onDate = tempDate;
        END IF;

        if loan_summary_type_text = 'SYSTEM' or ((loan_summary_type_text = 'MANUAL' or loan_summary_type_text = 'FIXED') and tempDate = inDate) then

          INSERT INTO loanSummary(version, loanSummaryType, loanAmount, onDate, outstadingFee, outstadingInterest, outstadingPenalty, outstadingPrincipal, overdueFee, overdueInterest, overduePenalty,
                                  overduePrincipal, paidFee, paidInterest, paidPenalty, paidPrincipal, totalDisbursed, totalOutstanding, totalOverdue, totalPaid, totalPaidKGS, totalInterestPaid, totalPenaltyPaid, totalPrincipalPaid, totalFeePaid, loanId, createDate)
          VALUES (1, loan_summary_type_text, loan_amount, tempDate, 0.0, intOutstanding, penOutstanding, princOutstanding, 0.0, intOverdue, penOverdue, princOverdue, 0.0, totalIntPaid, totalPrincPaid,
                  princPaid, totalDisb, total_outstanding, total_overdue, total_paid, total_paidKGS, totalIntPaid, totalPenPaid, totalPrincPaid, 0, loan_id, CURDATE());

        end if;
      END IF;

    END IF;

    IF flag = FALSE THEN
      SET flag = TRUE;
    END IF;

  END LOOP get_data;

  CLOSE tCursor;

  COMMIT;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `fillLoansToEvaluateTable` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `fillLoansToEvaluateTable`(IN inDate date)
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
         AND term.record_status = 1
       UNION
       SELECT
         'payment' AS description,
         loan.id,
         payment.paymentDate AS onDate
       FROM loan loan, payment payment
       WHERE loan.id = payment.loanId
         AND payment.paymentDate = inDate
         AND payment.record_status = 1
       UNION
       SELECT
         'ps' AS description,
         loan.id,
         ps.expectedDate AS onDate
       FROM loan loan, paymentSchedule ps
       WHERE loan.id = ps.loanId
         AND ps.expectedDate = inDate
         AND ps.record_status = 1
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


    IF loan_id != prevLoan_id THEN
      INSERT INTO loansToEvaluate(version, loanId, description, onDate)
      VALUES (1, loan_id, descrip, tempDate);
    END IF;

    SET prevLoan_id = loan_id;

  END LOOP get_data;

  CLOSE tCursor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `runCalculateForDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `runCalculateLoanDetailedSummaryForAllLoans` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runCalculateLoanDetailedSummaryForAllLoans`(IN inDate date)
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE loanId BIGINT;

  DEClARE tCursor CURSOR FOR

    SELECT
      loan.id
    FROM loan loan
    WHERE loan.id NOT IN (SELECT DISTINCT parent_id FROM loan WHERE parent_id IS NOT NULL)
    ORDER BY loan.id;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  DELETE FROM loanDetailedSummary;
  DELETE FROM loanSummary WHERE loanSummaryType = 'SYSTEM';
  DELETE FROM accrue;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO loanId;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    CALL calculateLoanDetailedSummaryUntilOnDate(loanId, inDate, 1, 'SYSTEM');

  END LOOP run_calculate;

  CLOSE tCursor;

  CALL runUpdateRootLoans();

  CALL updateBankruptInfo();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `runCalculateLoanDetailedSummaryForAllLoansFixed` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runCalculateLoanDetailedSummaryForAllLoansFixed`(IN inDate date)
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE loanId BIGINT;

  DEClARE tCursor CURSOR FOR

    SELECT
      loan.id
    FROM loan loan
    WHERE loan.id NOT IN (SELECT DISTINCT parent_id FROM loan WHERE parent_id IS NOT NULL)
    ORDER BY loan.id;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO loanId;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    CALL calculateLoanDetailedSummaryUntilOnDate(loanId, inDate, 1, 'FIXED');

  END LOOP run_calculate;

  CLOSE tCursor;

  CALL runUpdateRootLoansFixed();

  CALL updateBankruptInfo();

  UPDATE fixed_date set status = 2 where date = inDate;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `runCalculateLoanDetailedSummaryForSelectedLoans` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runCalculateLoanDetailedSummaryForSelectedLoans`(IN inDate date)
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

  CALL runUpdateRootLoans();

  CALL updateBankruptInfo();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `runCalculateLoanDetailedSummaryForSelectedLoansByRegionId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runCalculateLoanDetailedSummaryForSelectedLoansByRegionId`(IN inDate DATE, IN region_id BIGINT)
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE loanId BIGINT;
  DECLARE countLoans INT DEFAULT 0;

  DECLARE start_time bigint;
  DECLARE end_time bigint;

  DEClARE tCursor CURSOR FOR

    SELECT
      loan.id
    FROM loan loan
    WHERE loan.id IN ( select lv.v_loan_id from loan_view lv where lv.v_debtor_region_id = region_id)
    ORDER BY loan.id;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  SET start_time = (UNIX_TIMESTAMP(NOW()) * 1000000 + MICROSECOND(NOW(6)));

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO loanId;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    CALL calculateLoanDetailedSummaryUntilOnDate(loanId, inDate, 1);

    SET countLoans = countLoans + 1;

  END LOOP run_calculate;

  CLOSE tCursor;

  SET end_time = (UNIX_TIMESTAMP(NOW()) * 1000000 + MICROSECOND(NOW(6)));

  select (end_time - start_time) / 1000 as runningTime, countLoans as numberOfLoans;

  CALL runUpdateRootLoans();

  CALL updateBankruptInfo();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `runPenaltyAccruedForAllLoans` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runPenaltyAccruedForAllLoans`()
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE loan_id BIGINT;
  DECLARE psLastDate DATE;

  DEClARE tCursor CURSOR FOR

    SELECT
      loan.id
    FROM loan loan
    WHERE loan.id NOT IN (SELECT DISTINCT parent_id FROM loan WHERE parent_id IS NOT NULL)
    ORDER BY loan.id;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO loan_id;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    IF EXISTS(
            SELECT ps.expectedDate
            FROM paymentSchedule ps
            WHERE ps.loanId = loan_id
              AND (ps.principalPayment > 0 OR ps.interestPayment > 0 OR ps.collectedInterestPayment > 0 OR
                   ps.collectedInterestPayment > 0)
            ORDER BY expectedDate DESC
            LIMIT 1) THEN

      SELECT ps.expectedDate
             INTO psLastDate
      FROM paymentSchedule ps
      WHERE ps.loanId = loan_id
        AND (ps.principalPayment > 0 OR ps.interestPayment > 0 OR ps.collectedInterestPayment > 0 OR
             ps.collectedInterestPayment > 0)
      ORDER BY expectedDate DESC
      LIMIT 1;

      IF EXISTS(
              SELECT 1 FROM loanDetailedSummary WHERE loanId = loan_id AND onDate > psLastDate
        ) THEN
        CALL updatePenaltyAccruedForLoan(loan_id, psLastDate);
      END IF;
    END IF;

  END LOOP run_calculate;

  CLOSE tCursor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `runUpdateRootLoan` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runUpdateRootLoan`(IN parent_loan_id bigint)
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE loan_id BIGINT;
  DECLARE loan_class INT;
  DECLARE loan_state INT;

  DEClARE tCursor CURSOR FOR

    SELECT loan.id, loan.loan_class_id, loan.loanStateId
    FROM loan loan
    WHERE loan.parent_id = parent_loan_id
    ORDER BY loan.id;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  START TRANSACTION;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO loan_id, loan_class, loan_state;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    #update parent loan amount
    IF loan_class = 2 THEN
      UPDATE loan l, (select SUM(tLoan.amount) as ss FROM loan tLoan WHERE tLoan.parent_id = loan_id AND tLoan.loanStateId != 3) t
      SET l.amount = t.ss WHERE l.id = loan_id;
    END IF;

    CALL updateRootLoanPayment(loan_id);
    CALL updateRootLoanPaymentSchedule(loan_id);
    IF loan_class = 2 THEN
      CALL updateTrancheeLoanData(loan_id);
    END IF;

    INSERT INTO loanSummary (version, loanAmount, loanSummaryType, onDate, outstadingFee, outstadingInterest, outstadingPenalty, outstadingPrincipal, overdueFee, overdueInterest, overduePenalty, overduePrincipal, paidFee, paidInterest, paidPenalty, paidPrincipal, totalDisbursed, totalFeePaid, totalInterestPaid, totalOutstanding, totalOverdue, totalPaid, totalPaidKGS, totalPenaltyPaid, totalPrincipalPaid, loanId, createDate)
    SELECT 1,
           SUM(p.loanAmount),
           p.loanSummaryType,
           p.onDate,
           SUM(p.outstadingFee),
           SUM(p.outstadingInterest),
           SUM(p.outstadingPenalty),
           SUM(p.outstadingPrincipal),
           SUM(p.overdueFee),
           SUM(p.overdueInterest),
           SUM(p.overduePenalty),
           SUM(p.overduePrincipal),
           SUM(p.paidFee),
           SUM(p.paidInterest),
           SUM(p.paidPenalty),
           SUM(p.paidPrincipal),
           SUM(p.totalDisbursed),
           SUM(p.totalFeePaid),
           SUM(p.totalInterestPaid),
           SUM(p.totalOutstanding),
           SUM(p.totalOverdue),
           SUM(p.totalPaid),
           SUM(p.totalPaidKGS),
           SUM(p.totalPenaltyPaid),
           SUM(p.totalPrincipalPaid),
           loan_id,
           CURDATE()
    FROM loanSummary p
    WHERE p.loanId IN
          (SELECT id
           FROM loan
           WHERE parent_id = loan_id
             AND loanStateId != 3)
    GROUP BY p.onDate
    ORDER BY p.onDate;

    #CALL updateRootLoanSummary(loan_id);

  END LOOP run_calculate;

  CLOSE tCursor;

  COMMIT;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `runUpdateRootLoans` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runUpdateRootLoans`()
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE loan_id BIGINT;
  DECLARE loan_class INT;
  DECLARE loan_state INT;

  DEClARE tCursor CURSOR FOR

    SELECT loan.id, loan.loan_class_id, loan.loanStateId
    FROM loan loan
    WHERE loan.id IN (SELECT DISTINCT parent_id FROM loan WHERE parent_id IS NOT NULL)
    ORDER BY loan.id;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  START TRANSACTION;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO loan_id, loan_class, loan_state;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    #update parent loan amount
    IF loan_class = 2 THEN
      UPDATE loan l, (select SUM(tLoan.amount) as ss FROM loan tLoan WHERE tLoan.parent_id = loan_id AND tLoan.loanStateId != 3) t
      SET l.amount = t.ss WHERE l.id = loan_id;
    END IF;

    #CALL updateRootLoanPayment(loan_id);
    #CALL updateRootLoanPaymentSchedule(loan_id);

    CALL update_child_loan_payments(loan_id);
    CALL update_child_loan_payment_schedules(loan_id);

    IF loan_class = 2 THEN
      CALL updateTrancheeLoanData(loan_id);
    END IF;

    INSERT INTO loanSummary (version, loanAmount, loanSummaryType, onDate, outstadingFee, outstadingInterest, outstadingPenalty, outstadingPrincipal, overdueFee, overdueInterest, overduePenalty, overduePrincipal, paidFee, paidInterest, paidPenalty, paidPrincipal, totalDisbursed, totalFeePaid, totalInterestPaid, totalOutstanding, totalOverdue, totalPaid, totalPaidKGS, totalPenaltyPaid, totalPrincipalPaid, loanId, createDate)
    SELECT 1,
           SUM(p.loanAmount),
           p.loanSummaryType,
           p.onDate,
           SUM(p.outstadingFee),
           SUM(p.outstadingInterest),
           SUM(p.outstadingPenalty),
           SUM(p.outstadingPrincipal),
           SUM(p.overdueFee),
           SUM(p.overdueInterest),
           SUM(p.overduePenalty),
           SUM(p.overduePrincipal),
           SUM(p.paidFee),
           SUM(p.paidInterest),
           SUM(p.paidPenalty),
           SUM(p.paidPrincipal),
           SUM(p.totalDisbursed),
           SUM(p.totalFeePaid),
           SUM(p.totalInterestPaid),
           SUM(p.totalOutstanding),
           SUM(p.totalOverdue),
           SUM(p.totalPaid),
           SUM(p.totalPaidKGS),
           SUM(p.totalPenaltyPaid),
           SUM(p.totalPrincipalPaid),
           loan_id,
           CURDATE()
    FROM loanSummary p
    WHERE p.loanId IN
          (SELECT id
           FROM loan
           WHERE parent_id = loan_id
             AND loanStateId != 3)
      AND p.loanSummaryType = 'SYSTEM'
      AND p.record_status = 1
    GROUP BY p.onDate
    ORDER BY p.onDate;

    INSERT INTO accrue (version, daysInPeriod, fromDate, interestAccrued, lastInstPassed, penaltyAccrued, penaltyOnInterestOverdue, penaltyOnPrincipalOverdue, toDate, loanId)
    SELECT 1, acc.daysInPeriod,
           acc.fromDate,
           acc.interestAccrued,
           acc.lastInstPassed,
           acc.penaltyAccrued,
           acc.penaltyOnInterestOverdue,
           acc.penaltyOnPrincipalOverdue,
           acc.toDate,
           loan_id
    FROM accrue acc
    WHERE acc.loanId IN
          (SELECT id
           FROM loan
           WHERE parent_id = loan_id
             AND loanStateId != 3)
    ORDER BY acc.fromDate;

    #CALL updateRootLoanSummary(loan_id);

  END LOOP run_calculate;

  CLOSE tCursor;

  COMMIT;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `runUpdateRootLoansFixed` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runUpdateRootLoansFixed`()
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE loan_id BIGINT;
  DECLARE loan_class INT;
  DECLARE loan_state INT;

  DEClARE tCursor CURSOR FOR

    SELECT loan.id, loan.loan_class_id, loan.loanStateId
    FROM loan loan
    WHERE loan.id IN (SELECT DISTINCT parent_id FROM loan WHERE parent_id IS NOT NULL)
    ORDER BY loan.id;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  START TRANSACTION;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO loan_id, loan_class, loan_state;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    #update parent loan amount
    IF loan_class = 2 THEN
      UPDATE loan l, (select SUM(tLoan.amount) as ss FROM loan tLoan WHERE tLoan.parent_id = loan_id AND tLoan.loanStateId != 3) t
      SET l.amount = t.ss WHERE l.id = loan_id;
    END IF;

    #CALL updateRootLoanPayment(loan_id);
    #CALL updateRootLoanPaymentSchedule(loan_id);

    CALL update_child_loan_payments(loan_id);
    CALL update_child_loan_payment_schedules(loan_id);

    IF loan_class = 2 THEN
      CALL updateTrancheeLoanData(loan_id);
    END IF;

    INSERT INTO loanSummary (version, loanAmount, loanSummaryType, onDate, outstadingFee, outstadingInterest, outstadingPenalty, outstadingPrincipal, overdueFee, overdueInterest, overduePenalty, overduePrincipal, paidFee, paidInterest, paidPenalty, paidPrincipal, totalDisbursed, totalFeePaid, totalInterestPaid, totalOutstanding, totalOverdue, totalPaid, totalPaidKGS, totalPenaltyPaid, totalPrincipalPaid, loanId, createDate)
    SELECT 1,
           SUM(p.loanAmount),
           p.loanSummaryType,
           p.onDate,
           SUM(p.outstadingFee),
           SUM(p.outstadingInterest),
           SUM(p.outstadingPenalty),
           SUM(p.outstadingPrincipal),
           SUM(p.overdueFee),
           SUM(p.overdueInterest),
           SUM(p.overduePenalty),
           SUM(p.overduePrincipal),
           SUM(p.paidFee),
           SUM(p.paidInterest),
           SUM(p.paidPenalty),
           SUM(p.paidPrincipal),
           SUM(p.totalDisbursed),
           SUM(p.totalFeePaid),
           SUM(p.totalInterestPaid),
           SUM(p.totalOutstanding),
           SUM(p.totalOverdue),
           SUM(p.totalPaid),
           SUM(p.totalPaidKGS),
           SUM(p.totalPenaltyPaid),
           SUM(p.totalPrincipalPaid),
           loan_id,
           CURDATE()
    FROM loanSummary p
    WHERE p.loanId IN
          (SELECT id
           FROM loan
           WHERE parent_id = loan_id
             AND loanStateId != 3)
      AND p.loanSummaryType = 'FIXED'
      AND p.record_status = 1
    GROUP BY p.onDate
    ORDER BY p.onDate;

    INSERT INTO accrue (version, daysInPeriod, fromDate, interestAccrued, lastInstPassed, penaltyAccrued, penaltyOnInterestOverdue, penaltyOnPrincipalOverdue, toDate, loanId)
    SELECT 1, acc.daysInPeriod,
           acc.fromDate,
           acc.interestAccrued,
           acc.lastInstPassed,
           acc.penaltyAccrued,
           acc.penaltyOnInterestOverdue,
           acc.penaltyOnPrincipalOverdue,
           acc.toDate,
           loan_id
    FROM accrue acc
    WHERE acc.loanId IN
          (SELECT id
           FROM loan
           WHERE parent_id = loan_id
             AND loanStateId != 3)
    ORDER BY acc.fromDate;

    #CALL updateRootLoanSummary(loan_id);

  END LOOP run_calculate;

  CLOSE tCursor;

  COMMIT;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `run_calc_manual_summary_for_loan` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `run_calc_manual_summary_for_loan`(IN loan_id bigint, IN in_date date, IN type varchar(20))
begin

  declare is_leaf_or_normal_loan boolean default true;
  declare loan_class int;

  #if parent loan then first calculate for all child loans then calculate loan itself else just calculate for loan
  if exists(
          SELECT loan.id
          FROM loan loan
          WHERE loan.id IN (SELECT DISTINCT parent_id FROM loan WHERE parent_id IS NOT NULL)
            and loan.id = loan_id
    ) then
    set is_leaf_or_normal_loan = false;
  end if;

  if type = 'SYSTEM' then
    DELETE FROM loanDetailedSummary WHERE loanId = loan_id;
    DELETE FROM loanSummary WHERE loanId = loan_id AND loanSummaryType IN ('SYSTEM', 'DAILY');
    DELETE FROM accrue where loanId = loan_id;
  end if;

  if is_leaf_or_normal_loan then

    CALL calculateLoanDetailedSummaryUntilOnDate(loan_id, in_date, 1, type);
    CALL updateBankruptInfoForLoan(loan_id);
  else
    begin
      declare v_finished integer default 0;
      declare t_loan_id bigint;
      declare t_cursor cursor for
        SELECT loan.id
        FROM loan loan
        WHERE parent_id = loan_id
        ORDER BY loan.id;

      declare continue handler for not found
        set v_finished = 1;

      open t_cursor;

      run_calculate: loop

        fetch t_cursor into t_loan_id;

        if v_finished = 1 then
          leave run_calculate;
        end if;

        if type = 'SYSTEM' then
          DELETE FROM loanDetailedSummary WHERE loanId = t_loan_id;
          DELETE FROM loanSummary WHERE loanId = t_loan_id AND loanSummaryType IN ('SYSTEM', 'DAILY');
          DELETE FROM accrue where loanId = t_loan_id;
        end if;

        /*
        if type = 'MANUAL' then
          DELETE FROM loanDetailedSummary WHERE loanId = t_loan_id;
          DELETE FROM loanSummary WHERE loanId = t_loan_id AND loanSummaryType = 'MANUAL';
          DELETE FROM accrue where loanId = t_loan_id;
        end if;
        */

        CALL calculateLoanDetailedSummaryUntilOnDate(t_loan_id, in_date, 1, type);

        CALL updateBankruptInfoForLoan(t_loan_id);

      end loop run_calculate;

      close t_cursor;

      CALL update_child_loan_payments(loan_id);
      CALL update_child_loan_payment_schedules(loan_id);

      SELECT loan.loan_class_id into loan_class
      FROM loan loan
      WHERE loan.id = loan_id;

      IF loan_class = 2 THEN
        CALL updateTrancheeLoanData(loan_id);
      END IF;

      insert into loanSummary (version, loanAmount, loanSummaryType, onDate, outstadingFee, outstadingInterest, outstadingPenalty, outstadingPrincipal, overdueFee, overdueInterest, overduePenalty, overduePrincipal, paidFee, paidInterest, paidPenalty, paidPrincipal, totalDisbursed, totalFeePaid, totalInterestPaid, totalOutstanding, totalOverdue, totalPaid, totalPaidKGS, totalPenaltyPaid, totalPrincipalPaid, loanId, createDate)
      select 1,
             SUM(p.loanAmount),
             p.loanSummaryType,
             p.onDate,
             SUM(p.outstadingFee),
             SUM(p.outstadingInterest),
             SUM(p.outstadingPenalty),
             SUM(p.outstadingPrincipal),
             SUM(p.overdueFee),
             SUM(p.overdueInterest),
             SUM(p.overduePenalty),
             SUM(p.overduePrincipal),
             SUM(p.paidFee),
             SUM(p.paidInterest),
             SUM(p.paidPenalty),
             SUM(p.paidPrincipal),
             SUM(p.totalDisbursed),
             SUM(p.totalFeePaid),
             SUM(p.totalInterestPaid),
             SUM(p.totalOutstanding),
             SUM(p.totalOverdue),
             SUM(p.totalPaid),
             SUM(p.totalPaidKGS),
             SUM(p.totalPenaltyPaid),
             SUM(p.totalPrincipalPaid),
             loan_id,
             CURDATE()
      from loanSummary p
      where p.loanId in
            (select id
             from loan
             where parent_id = loan_id
               and loanStateId != 3)
        and p.record_status = 1
      group by p.onDate, p.loanSummaryType;

      INSERT INTO accrue (version, daysInPeriod, fromDate, interestAccrued, lastInstPassed, penaltyAccrued, penaltyOnInterestOverdue, penaltyOnPrincipalOverdue, toDate, loanId)
      SELECT 1, acc.daysInPeriod,
             acc.fromDate,
             acc.interestAccrued,
             acc.lastInstPassed,
             acc.penaltyAccrued,
             acc.penaltyOnInterestOverdue,
             acc.penaltyOnPrincipalOverdue,
             acc.toDate,
             loan_id
      FROM accrue acc
      WHERE acc.loanId IN
            (SELECT id
             FROM loan
             WHERE parent_id = loan_id
               AND loanStateId != 3)
      ORDER BY acc.fromDate;

    end;
  end if;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateBankruptInfo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateBankruptInfo`()
BEGIN

  DECLARE b_loanId BIGINT;
  DECLARE b_type TEXT;
  DECLARE b_onDate DATE;

  DECLARE v_finished INTEGER DEFAULT 0;

  DEClARE tCursor CURSOR FOR

    select tt.type,
           tt.onDate,
           tt.loanId
    from
      (
        /*
         select 'start' as type, bn.startedOnDate as onDate, bn.loanId
         from bankrupt bn
         union all
         select 'finish' as type, bn.finishedOnDate as onDate, bn.loanId
         from bankrupt bn
         union all
         */
        select 'close' as type, loan.closeDate as onDate, id as loanId
        from loan loan
        where loan.closeDate is not null
      ) tt;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  OPEN tCursor;

  get_data: LOOP

    FETCH tCursor INTO b_type, b_onDate, b_loanId;

    IF v_finished = 1 THEN
      LEAVE get_data;
    END IF;

    #IF b_type = 'finish' OR b_type = 'close' THEN
    IF b_type = 'close' THEN
      #Nullify overdue and outstanding fields on loanDetailedSummary(no action if negative) and loanSummary tables
      update loanDetailedSummary lds
      set lds.interestOverdue = case when lds.interestOverdue >= 0 then 0 else lds.interestOverdue end,
          lds.interestOutstanding = case when lds.interestOutstanding >= 0 then 0 else lds.interestOutstanding end,
          lds.principalOverdue = case when lds.principalOverdue >= 0 then 0 else lds.principalOverdue end,
          lds.principalOutstanding = case when lds.principalOutstanding >= 0 then 0 else lds.principalOutstanding end,
          lds.penaltyOverdue = case when lds.penaltyOverdue >= 0 then 0 else lds.penaltyOverdue end,
          lds.penaltyOutstanding = case when lds.penaltyOutstanding >= 0 then 0 else lds.penaltyOutstanding end
      where lds.loanId = b_loanId
        and lds.onDate >= b_onDate;

      update loanSummary ls
      set ls.overduePrincipal = 0,
          ls.outstadingPrincipal = 0,
          ls.overdueInterest = 0,
          ls.outstadingInterest = 0,
          ls.overduePenalty = 0,
          ls.outstadingPenalty = 0,
          ls.overdueFee = 0,
          ls.outstadingFee = 0,
          ls.totalOverdue = 0,
          ls.totalOutstanding = 0
      where ls.loanId = b_loanId
        and ls.onDate >= b_onDate;

    END IF;

    /*
    IF b_type = 'start' THEN
      #Nullify overdue fields loanDetailedSummary(no action if negative) and loanSummary tables
      update loanDetailedSummary lds
      set lds.interestOverdue = case when lds.interestOverdue >= 0 then 0 else lds.interestOverdue end,
          lds.principalOverdue = case when lds.principalOverdue >= 0 then 0 else lds.principalOverdue end,
          lds.penaltyOverdue = case when lds.penaltyOverdue >= 0 then 0 else lds.penaltyOverdue end
      where lds.loanId = b_loanId
      and lds.onDate >= b_onDate;

      update loanSummary ls
      set ls.overduePrincipal = 0,
          ls.overdueInterest = 0,
          ls.overduePenalty = 0,
          ls.overdueFee = 0
      where ls.loanId = b_loanId
      and ls.onDate >= b_onDate;
    END IF;
    */

  END LOOP get_data;

  CLOSE tCursor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateBankruptInfoForLoan` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateBankruptInfoForLoan`(IN loan_id bigint)
BEGIN

  DECLARE b_loanId BIGINT;
  DECLARE b_type TEXT;
  DECLARE b_onDate DATE;

  DECLARE v_finished INTEGER DEFAULT 0;

  DEClARE tCursor CURSOR FOR

    select tt.type,
           tt.onDate,
           tt.loanId
    from
      (
        /*
         select 'start' as type, bn.startedOnDate as onDate, bn.loanId
         from bankrupt bn
         union all
         select 'finish' as type, bn.finishedOnDate as onDate, bn.loanId
         from bankrupt bn
         union all
         */
        select 'close' as type, loan.closeDate as onDate, id as loanId
        from loan loan
        where loan.closeDate is not null and loan.id = loan_id
      ) tt;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  OPEN tCursor;

  get_data: LOOP

    FETCH tCursor INTO b_type, b_onDate, b_loanId;

    IF v_finished = 1 THEN
      LEAVE get_data;
    END IF;

    #IF b_type = 'finish' OR b_type = 'close' THEN
    IF b_type = 'close' THEN
      #Nullify overdue and outstanding fields on loanDetailedSummary(no action if negative) and loanSummary tables
      update loanDetailedSummary lds
      set lds.interestOverdue = case when lds.interestOverdue >= 0 then 0 else lds.interestOverdue end,
          lds.interestOutstanding = case when lds.interestOutstanding >= 0 then 0 else lds.interestOutstanding end,
          lds.principalOverdue = case when lds.principalOverdue >= 0 then 0 else lds.principalOverdue end,
          lds.principalOutstanding = case when lds.principalOutstanding >= 0 then 0 else lds.principalOutstanding end,
          lds.penaltyOverdue = case when lds.penaltyOverdue >= 0 then 0 else lds.penaltyOverdue end,
          lds.penaltyOutstanding = case when lds.penaltyOutstanding >= 0 then 0 else lds.penaltyOutstanding end
      where lds.loanId = b_loanId
        and lds.onDate >= b_onDate;

      update loanSummary ls
      set ls.overduePrincipal = 0,
          ls.outstadingPrincipal = 0,
          ls.overdueInterest = 0,
          ls.outstadingInterest = 0,
          ls.overduePenalty = 0,
          ls.outstadingPenalty = 0,
          ls.overdueFee = 0,
          ls.outstadingFee = 0,
          ls.totalOverdue = 0,
          ls.totalOutstanding = 0
      where ls.loanId = b_loanId
        and ls.onDate >= b_onDate;

    END IF;

    /*
    IF b_type = 'start' THEN
      #Nullify overdue fields loanDetailedSummary(no action if negative) and loanSummary tables
      update loanDetailedSummary lds
      set lds.interestOverdue = case when lds.interestOverdue >= 0 then 0 else lds.interestOverdue end,
          lds.principalOverdue = case when lds.principalOverdue >= 0 then 0 else lds.principalOverdue end,
          lds.penaltyOverdue = case when lds.penaltyOverdue >= 0 then 0 else lds.penaltyOverdue end
      where lds.loanId = b_loanId
      and lds.onDate >= b_onDate;

      update loanSummary ls
      set ls.overduePrincipal = 0,
          ls.overdueInterest = 0,
          ls.overduePenalty = 0,
          ls.overdueFee = 0
      where ls.loanId = b_loanId
      and ls.onDate >= b_onDate;
    END IF;
    */

  END LOOP get_data;

  CLOSE tCursor;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateLoanCloseRate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateLoanCloseRate`()
BEGIN

  DECLARE b_onDate DATE;
  DECLARE b_loanId BIGINT;
  DECLARE b_curId INT;
  DECLARE rRate DOUBLE;

  DECLARE v_finished INTEGER DEFAULT 0;

  DEClARE tCursor CURSOR FOR

    select tt.onDate,
           tt.loanId,
           (select currencyId from loan where id = tt.loanId) as curId
    from
      (
        select 1 as typeOrder, 'start' as type, bn.startedOnDate as onDate, bn.loanId
        from bankrupt bn
        union all
        select 2 as typeOrder, 'close' as type, loan.closeDate as onDate, id as loanId
        from loan loan
        where loan.closeDate is not null
      ) tt group by tt.loanId order by tt.loanId, tt.typeOrder;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  START TRANSACTION;

  OPEN tCursor;

  get_data: LOOP

    FETCH tCursor INTO b_onDate, b_loanId, b_curId;

    IF v_finished = 1 THEN
      LEAVE get_data;
    END IF;

    IF b_curId = 1 THEN
      SET rRate = 1;
    ELSE

      IF EXISTS(SELECT rate FROM currency_rate WHERE currency_type_id = b_curId AND date <= b_onDate ORDER BY date DESC LIMIT 1) THEN
        SELECT rate INTO rRate FROM currency_rate WHERE currency_type_id = b_curId AND date <= b_onDate ORDER BY date DESC LIMIT 1;
      ELSE
        SET rRate = 1;
      END IF;

    END IF;

    UPDATE loan SET closeRate = rRate
    WHERE id = b_loanId;

  END LOOP get_data;

  CLOSE tCursor;

  COMMIT;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateOwners` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updatePenaltyAccruedForLoan` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updatePenaltyAccruedForLoan`(IN loan_id bigint, IN psLastDate date)
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE penAccrued DOUBLE;
  DECLARE intAccrued DOUBLE;
  DECLARE prevPenAccrued DOUBLE;
  DECLARE prevPenOverdue DOUBLE;
  DECLARE intOverdue DOUBLE;
  DECLARE princOverdue DOUBLE;
  DECLARE prevIntOverdue DOUBLE;
  DECLARE tDate DATE;
  DECLARE dPeriod INT;

  DECLARE penOutstanding DOUBLE;
  DECLARE prevPenOutstanding DOUBLE;
  DECLARE penOverdue DOUBLE;

  DECLARE poio DOUBLE;
  DECLARE popo DOUBLE;
  DECLARE dIYMethod INT;
  DECLARE nOD INT DEFAULT 365;

  DECLARE rPOPO DOUBLE;

  DECLARE result DOUBLE;

  DECLARE flag BOOLEAN DEFAULT FALSE;

  DEClARE tCursor CURSOR FOR

    SELECT tt.penaltyAccrued, tt.interestOverdue, tt.penaltyOverdue,
           tt.penaltyOutstanding, tt.principalOverdue, tt.interestAccrued,
           tt.daysInPeriod, tt.onDate
    FROM
      (
        SELECT ls.penaltyAccrued, ls.interestOverdue, ls.penaltyOverdue,
               ls.penaltyOutstanding, ls.principalOverdue, ls.interestAccrued,
               ls.daysInPeriod, ls.onDate
        FROM loanDetailedSummary ls
        WHERE ls.loanId = loan_id
          AND ls.onDate > psLastDate

        UNION ALL

        (SELECT ls.penaltyAccrued, ls.interestOverdue, ls.penaltyOverdue,
                ls.penaltyOutstanding, ls.principalOverdue, ls.interestAccrued,
                ls.daysInPeriod, ls.onDate
         FROM loanDetailedSummary ls
         WHERE ls.loanId = loan_id
           AND ls.onDate <= psLastDate
         ORDER BY ls.onDate DESC LIMIT 1)
      )tt ORDER BY tt.onDate;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO penAccrued, intOverdue, penOverdue, penOutstanding, princOverdue, intAccrued, dPeriod, tDate;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    IF NOT flag THEN
      SET prevIntOverdue = intOverdue;
      SET prevPenAccrued = penAccrued;
      SET prevPenOverdue = penOverdue;
      SET prevPenOutstanding = penOutstanding;
      SET flag = TRUE;
    ELSE
      IF penAccrued > 0 THEN
        SELECT IFNULL(term.penaltyOnInterestOverdueRateValue, 0),
               IFNULL(term.penaltyOnPrincipleOverdueRateValue,0),
               IFNULL(term.daysInYearMethodId, 2)
               INTO poio, popo, dIYMethod
        FROM creditTerm term
        WHERE term.loanId = loan_id
          AND term.startDate < tDate
          AND term.record_status = 1
        ORDER BY term.startDate DESC LIMIT 1;

        IF dIYMethod != 1 THEN SET nOD = 360;
        END IF;

        IF dIYMethod = 3 THEN SET nOD = 366;
        END IF;

        SELECT prevIntOverdue, intOverdue, princOverdue, popo, poio, dPeriod, nOD;

        SET rPOPO = princOverdue*popo/100*dPeriod/nOD;

        SET result = (prevIntOverdue + intAccrued*(dPeriod-1)/(2*dPeriod))*poio/100*dPeriod/nOD
          + rPOPO;

        SELECT penOutstanding, penOverdue, penAccrued, intAccrued, result;

        SET penOutstanding = penOutstanding-penAccrued+result;
        SET penOverdue = penOverdue-penAccrued+result;

        UPDATE loanDetailedSummary
        SET penaltyAccrued = ROUND(result,2),
            penaltyOutstanding = ROUND(penOutstanding,2),
            penaltyOverdue = ROUND(penOverdue,2)
        WHERE loanId = loan_id
          AND onDate = tDate;

        SET prevIntOverdue = intOverdue;
        SET prevPenAccrued = penAccrued;
        SET prevPenOverdue = penOverdue;
        SET prevPenOutstanding = penOutstanding;

      END IF;
    END IF;

  END LOOP run_calculate;

  CLOSE tCursor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateRootLoanPayment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRootLoanPayment`(IN loan_id BIGINT)
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE pId BIGINT;
  DECLARE prevPId BIGINT;
  DECLARE pDate DATE;
  DECLARE prevPDate DATE DEFAULT NULL;
  DECLARE pNumber VARCHAR(200);
  DECLARE prevPNumber VARCHAR(200);

  DECLARE pFee DOUBLE;
  DECLARE pInterest DOUBLE;
  DECLARE pPenalty DOUBLE;
  DECLARE pPrincipal DOUBLE;
  DECLARE pTotalAmount DOUBLE;

  DECLARE prevPFee DOUBLE;
  DECLARE prevPInterest DOUBLE;
  DECLARE prevPPenalty DOUBLE;
  DECLARE prevPPrincipal DOUBLE;
  DECLARE prevPTotalAmount DOUBLE;

  DEClARE tCursor CURSOR FOR

    SELECT p.id, p.paymentDate, p.number, p.fee, p.interest, p.penalty, p.principal, p.totalAmount
    FROM payment p
    WHERE p.loanId = loan_id
    ORDER BY p.paymentDate, p.number;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO pId, pDate, pNumber, pFee, pInterest, pPenalty, pPrincipal, pTotalAmount;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    IF (prevPDate = pDate AND prevPNumber = pNumber AND LENGTH(pNumber) > 1) THEN
      UPDATE payment SET
                       fee = pFee + prevPFee,
                       interest = pInterest + prevPInterest,
                       penalty = pPenalty + prevPPenalty,
                       principal = pPrincipal + prevPPrincipal,
                       totalAmount = pTotalAmount + prevPTotalAmount
      WHERE id = prevPId;

      DELETE FROM payment WHERE id = pId;

      ITERATE run_calculate;

    END IF;

    SET prevPId = pId;
    SET prevPDate = pDate;
    SET prevPNumber = pNumber;
    SET prevPFee = pFee;
    SET prevPInterest = pInterest;
    SET prevPPenalty = pPenalty;
    SET prevPPrincipal = pPrincipal;
    SET prevPTotalAmount = pTotalAmount;

  END LOOP run_calculate;

  CLOSE tCursor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateRootLoanPaymentSchedule` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRootLoanPaymentSchedule`(IN loan_id BIGINT)
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE pId BIGINT;
  DECLARE prevPId BIGINT;
  DECLARE pDate DATE;
  DECLARE prevPDate DATE DEFAULT NULL;

  DECLARE pCollIntPayment DOUBLE;
  DECLARE pCollPenPayment DOUBLE;
  DECLARE pDisbursement DOUBLE;
  DECLARE pIntPayment DOUBLE;
  DECLARE pPrincipalPayment DOUBLE;

  DECLARE prevPCollIntPayment DOUBLE;
  DECLARE prevPCollPenPayment DOUBLE;
  DECLARE prevPDisbursement DOUBLE;
  DECLARE prevPIntPayment DOUBLE;
  DECLARE prevPPrincipalPayment DOUBLE;

  DEClARE tCursor CURSOR FOR

    SELECT p.id, p.expectedDate, p.collectedInterestPayment, p.collectedPenaltyPayment,
           p.disbursement, p.interestPayment, p.principalPayment
    FROM paymentSchedule p
    WHERE p.loanId = loan_id
    ORDER BY p.expectedDate;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO pId, pDate, pCollIntPayment, pCollPenPayment, pDisbursement, pIntPayment, pPrincipalPayment;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    IF (prevPDate = pDate) THEN
      UPDATE paymentSchedule SET
                               collectedInterestPayment = pCollIntPayment + prevPCollIntPayment,
                               collectedPenaltyPayment = pCollPenPayment + prevPCollPenPayment,
                               disbursement = pDisbursement + prevPDisbursement,
                               interestPayment = pIntPayment + prevPIntPayment,
                               principalPayment = pPrincipalPayment + prevPPrincipalPayment
      WHERE id = prevPId;

      DELETE FROM paymentSchedule WHERE id = pId;

      ITERATE run_calculate;

    END IF;

    SET prevPId = pId;
    SET prevPDate = pDate;
    SET prevPCollIntPayment = pCollIntPayment;
    SET prevPCollPenPayment = pCollPenPayment;
    SET prevPDisbursement = pDisbursement;
    SET prevPIntPayment = pIntPayment;
    SET prevPPrincipalPayment = pPrincipalPayment;

  END LOOP run_calculate;

  CLOSE tCursor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateRootLoanSummary` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRootLoanSummary`(IN loan_id bigint)
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE pId BIGINT;
  DECLARE prevPId BIGINT;
  DECLARE pOnDate DATE;
  DECLARE prevOnDate DATE DEFAULT NULL;

  DECLARE pLoanAmount DOUBLE;
  DECLARE pOutstadingFee DOUBLE;
  DECLARE pOutstadingInterest DOUBLE;
  DECLARE pOutstadingPenalty DOUBLE;
  DECLARE pOutstadingPrincipal DOUBLE;
  DECLARE pOverdueFee DOUBLE;
  DECLARE pOverdueInterest DOUBLE;
  DECLARE pOverduePenalty DOUBLE;
  DECLARE pOverduePrincipal DOUBLE;
  DECLARE pPaidFee DOUBLE;
  DECLARE pPaidInterest DOUBLE;
  DECLARE pPaidPenalty DOUBLE;
  DECLARE pPaidPrincipal DOUBLE;
  DECLARE pTotalDisbursed DOUBLE;
  DECLARE pTotalFeePaid DOUBLE;
  DECLARE pTotalInterestPaid DOUBLE;
  DECLARE pTotalOutstanding DOUBLE;
  DECLARE pTotalOverdue DOUBLE;
  DECLARE pTotalPaid DOUBLE;
  DECLARE pTotalPaidKGS DOUBLE;
  DECLARE pTotalPenaltyPaid DOUBLE;
  DECLARE pTotalPrincipalPaid DOUBLE;

  DECLARE prevPLoanAmount DOUBLE;
  DECLARE prevPOutstadingFee DOUBLE;
  DECLARE prevPOutstadingInterest DOUBLE;
  DECLARE prevPOutstadingPenalty DOUBLE;
  DECLARE prevPOutstadingPrincipal DOUBLE;
  DECLARE prevPOverdueFee DOUBLE;
  DECLARE prevPOverdueInterest DOUBLE;
  DECLARE prevPOverduePenalty DOUBLE;
  DECLARE prevPOverduePrincipal DOUBLE;
  DECLARE prevPPaidFee DOUBLE;
  DECLARE prevPPaidInterest DOUBLE;
  DECLARE prevPPaidPenalty DOUBLE;
  DECLARE prevPPaidPrincipal DOUBLE;
  DECLARE prevPTotalDisbursed DOUBLE;
  DECLARE prevPTotalFeePaid DOUBLE;
  DECLARE prevPTotalInterestPaid DOUBLE;
  DECLARE prevPTotalOutstanding DOUBLE;
  DECLARE prevPTotalOverdue DOUBLE;
  DECLARE prevPTotalPaid DOUBLE;
  DECLARE prevPTotalPaidKGS DOUBLE;
  DECLARE prevPTotalPenaltyPaid DOUBLE;
  DECLARE prevPTotalPrincipalPaid DOUBLE;

  DEClARE tCursor CURSOR FOR

    SELECT p.id,
           p.loanAmount,
           p.onDate,
           p.outstadingFee,
           p.outstadingInterest,
           p.outstadingPenalty,
           p.outstadingPrincipal,
           p.overdueFee,
           p.overdueInterest,
           p.overduePenalty,
           p.overduePrincipal,
           p.paidFee,
           p.paidInterest,
           p.paidPenalty,
           p.paidPrincipal,
           p.totalDisbursed,
           p.totalFeePaid,
           p.totalInterestPaid,
           p.totalOutstanding,
           p.totalOverdue,
           p.totalPaid,
           p.totalPaidKGS,
           p.totalPenaltyPaid,
           p.totalPrincipalPaid
    FROM loanSummary p
    WHERE p.loanId = loan_id
    ORDER BY p.onDate;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  OPEN tCursor;

  run_calculate: LOOP

    FETCH tCursor INTO pId, pLoanAmount, pOnDate, pOutstadingFee, pOutstadingInterest, pOutstadingPenalty,
      pOutstadingPrincipal, pOverdueFee, pOverdueInterest, pOverduePenalty,pOverduePrincipal, pPaidFee,
      pPaidInterest, pPaidPenalty, pPaidPrincipal, pTotalDisbursed, pTotalFeePaid, pTotalInterestPaid,
      pTotalOutstanding, pTotalOverdue, pTotalPaid, pTotalPaidKGS, pTotalPenaltyPaid, pTotalPrincipalPaid;

    IF v_finished = 1 THEN
      LEAVE run_calculate;
    END IF;

    IF (prevOnDate = pOnDate) THEN
      UPDATE loanSummary SET
                           loanAmount = pLoanAmount + prevPLoanAmount,
                           outstadingFee = pOutstadingFee + prevPOutstadingFee,
                           outstadingInterest = pOutstadingInterest + prevPOutstadingInterest,
                           outstadingPenalty = pOutstadingPenalty + prevPOutstadingPenalty,
                           outstadingPrincipal = pOutstadingPrincipal + prevPOutstadingPrincipal,
                           overdueFee = pOverdueFee + prevPOverdueFee,
                           overdueInterest = prevPOverdueInterest + pOverdueInterest,
                           overduePenalty = prevPOverduePenalty + pOverduePenalty,
                           overduePrincipal = prevPOverduePrincipal + pOverduePrincipal,
                           paidFee = prevPPaidFee + pPaidFee,
                           paidInterest = prevPPaidInterest + pPaidInterest,
                           paidPenalty = prevPPaidPenalty + pPaidPenalty,
                           paidPrincipal = prevPPaidPrincipal + pPaidPrincipal,
                           totalDisbursed = prevPTotalDisbursed + pTotalDisbursed,
                           totalFeePaid = prevPTotalFeePaid + pTotalFeePaid,
                           totalInterestPaid = prevPTotalInterestPaid + pTotalInterestPaid,
                           totalOutstanding = prevPTotalOutstanding + pTotalOutstanding,
                           totalOverdue = prevPTotalOverdue + pTotalOverdue,
                           totalPaid = prevPTotalPaid + pTotalPaid,
                           totalPaidKGS = prevPTotalPaidKGS + pTotalPaidKGS,
                           totalPenaltyPaid = prevPTotalPenaltyPaid + pTotalPenaltyPaid,
                           totalPrincipalPaid = prevPTotalPrincipalPaid + pTotalPrincipalPaid
      WHERE id = prevPId;

      DELETE FROM loanSummary WHERE id = pId;

      ITERATE run_calculate;

    END IF;

    SET prevPId = pId;
    SET prevOnDate = pOnDate;
    SET prevPLoanAmount = pLoanAmount;
    SET prevPOutstadingFee = pOutstadingFee;
    SET prevPOutstadingInterest = pOutstadingInterest;
    SET prevPOutstadingPenalty = pOutstadingPenalty;
    SET prevPOutstadingPrincipal = pOutstadingPrincipal;
    SET prevPOverdueFee = pOverdueFee;
    SET prevPOverdueInterest = pOverdueInterest;
    SET prevPOverduePenalty = pOverduePenalty;
    SET prevPOverduePrincipal = pOverduePrincipal;
    SET prevPPaidFee = pPaidFee;
    SET prevPPaidInterest = pPaidInterest;
    SET prevPPaidPenalty = pPaidPenalty;
    SET prevPPaidPrincipal = pPaidPrincipal;
    SET prevPTotalDisbursed = pTotalDisbursed;
    SET prevPTotalFeePaid = pTotalFeePaid;
    SET prevPTotalInterestPaid = pTotalInterestPaid;
    SET prevPTotalOutstanding = pTotalOutstanding;
    SET prevPTotalOverdue = pTotalOverdue;
    SET prevPTotalPaid = pTotalPaid;
    SET prevPTotalPaidKGS = pTotalPaidKGS;
    SET prevPTotalPenaltyPaid = pTotalPenaltyPaid;
    SET prevPTotalPrincipalPaid = pTotalPrincipalPaid;

  END LOOP run_calculate;

  CLOSE tCursor;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `updateTrancheeLoanData` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateTrancheeLoanData`(IN loan_id bigint)
BEGIN

  DECLARE v_finished INTEGER DEFAULT 0;
  DECLARE child_loan_id BIGINT;
  DECLARE loan_state INT;
  DECLARE totalPrincPaymentSum DOUBLE;
  DECLARE totalDisbSum DOUBLE;
  DECLARE flag BOOLEAN DEFAULT FALSE;

  DEClARE tCursor CURSOR FOR

    SELECT loan.id, loan.loanStateId
    FROM loan loan
    WHERE loan.parent_id = loan_id;

  DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

  OPEN tCursor;

  loop1: LOOP

    FETCH tCursor INTO child_loan_id, loan_state;

    IF v_finished = 1 THEN
      LEAVE loop1;
    END IF;

    IF loan_state != 3 THEN

      SELECT SUM(principalPayment) INTO totalPrincPaymentSum FROM paymentSchedule WHERE record_status = 1 AND loanId = child_loan_id;
      SELECT SUM(totalDisbursement) INTO totalDisbSum FROM loanDetailedSummary WHERE loanId = child_loan_id;

      #totalprincipalpayment = 0 (1 of two)
      #totaldisbursement > 0 (1 of two)
      IF totalPrincPaymentSum = 0 AND totalDisbSum >= 0 THEN
        UPDATE loanSummary SET totalDisbursed = 0, totalOutstanding = totalOutstanding - outstadingPrincipal, outstadingPrincipal = 0 WHERE loanId = child_loan_id;
        SET flag = TRUE;
      END IF;

    END IF;

  END LOOP loop1;

  CLOSE tCursor;

  IF flag THEN

    UPDATE loanSummary AS dest,
      (
        select tSum.onDate as tDate, SUM(tSum.totalDisbursed) as tDisb, SUM(tSum.outstadingPrincipal) as tOut FROM loanSummary tSum
        WHERE tSum.loanId IN (SELECT id FROM loan WHERE parent_id = loan_id AND loanStateId != 3) and tSum.record_status = 1
        group by tSum.onDate, tSum.loanSummaryType
      ) AS src
    SET
      dest.totalDisbursed = src.tDisb,
      dest.outstadingPrincipal = src.tOut,
      dest.totalOutstanding = src.tOut + outstadingInterest + outstadingPenalty + outstadingFee
    WHERE
        dest.onDate = src.tDate
      AND dest.loanId = loan_id;

  END IF;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_child_loan_payments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_child_loan_payments`(IN parent_loan_id bigint)
BEGIN

  delete from payment where loanId = parent_loan_id;

  insert into payment (record_status, version, details, exchange_rate, fee, in_loan_currency, interest, number, paymentDate, penalty, principal, totalAmount, loanId, paymentTypeId)
  select 1,
         1,
         pt.details,
         pt.exchange_rate,
         sum(pt.fee),
         pt.in_loan_currency,
         sum(pt.interest),
         pt.number,
         pt.paymentDate,
         sum(pt.penalty),
         sum(pt.principal),
         sum(pt.totalAmount),
         parent_loan_id,
         pt.paymentTypeId
  from payment pt
  where pt.loanId in
        (select id
         from loan
         where parent_id = parent_loan_id
           and loanStateId != 3)
    and pt.record_status = 1
  group by pt.paymentDate, pt.number;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_child_loan_payment_schedules` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_child_loan_payment_schedules`(IN parent_loan_id bigint)
BEGIN

  delete from paymentSchedule where loanId = parent_loan_id;

  insert into paymentSchedule (record_status, version, collectedInterestPayment, collectedPenaltyPayment, disbursement, expectedDate, interestPayment, principalPayment, installmentStateId, loanId)
  select 1,
         1,
         sum(ps.collectedInterestPayment),
         sum(ps.collectedPenaltyPayment),
         sum(ps.disbursement),
         ps.expectedDate,
         sum(ps.interestPayment),
         sum(ps.principalPayment),
         ps.installmentStateId,
         parent_loan_id
  from paymentSchedule ps
  where ps.loanId in
        (select id
         from loan
         where parent_id = parent_loan_id
           and loanStateId != 3)
    and ps.record_status = 1
  group by ps.expectedDate;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-18 10:24:06