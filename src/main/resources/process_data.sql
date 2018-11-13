-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: mfloan
-- ------------------------------------------------------
-- Server version	5.7.23-log

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
/*!50003 DROP FUNCTION IF EXISTS `calculateInterestAccrued` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculateInterestAccrued`(principalOutstanding double, daysInPeriod int, inDate date, loanId bigint) RETURNS double
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
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calculateLibor`(val double, fromDate date, toDate date, loan_id bigint) RETURNS double
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

      SET prevDate = curDate;

      SET prevRate = tRate;

    END LOOP get_data;

    CLOSE tCursor;

    SET daysInPer = DATEDIFF(toDate, prevDate);

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
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
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

      SET prevDate = curDate;

      SET prevRate = tRate;

    END LOOP get_data;

    CLOSE tCursor;

    SET daysInPer = DATEDIFF(toDate, prevDate);

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
/*!50003 DROP FUNCTION IF EXISTS `calculateLiborPO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
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

      SET prevDate = curDate;

      SET prevRate = tRate;

    END LOOP get_data;

    CLOSE tCursor;

    SET daysInPer = DATEDIFF(toDate, prevDate);

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
/*!50003 DROP FUNCTION IF EXISTS `calculatePenaltyAccrued` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 DROP FUNCTION IF EXISTS `hasLiborType` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `isTermFound`(loan_id BIGINT, inDate DATE) RETURNS tinyint(1)
BEGIN

    DECLARE result BOOLEAN DEFAULT FALSE;
    DECLARE cCount INT;

    SELECT COUNT(1) FROM creditTerm cTerm where cTerm.loanId = loan_id AND cTerm.startDate <= inDate INTO cCount;

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
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `calculateLoanDetailedSummaryUntilOnDate`(IN loan_id bigint, IN inDate date, IN includeToday tinyint(1))
BEGIN
    DECLARE tempDate DATE;
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
    DECLARE pOPO DOUBLE DEFAULT 0;
    DECLARE pOIO DOUBLE DEFAULT 0;
    DECLARE pDate DATE;
    DECLARE loan_amount DOUBLE DEFAULT 0;
    DECLARE total_outstanding DOUBLE DEFAULT 0;
    DECLARE total_overdue DOUBLE DEFAULT 0;
    DECLARE total_paid DOUBLE DEFAULT 0;
    DECLARE total_paidKGS DOUBLE DEFAULT 0;
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
        CASE curRate WHEN 0 THEN 1 ELSE curRate END AS curRate
      FROM
        (

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

      SET princPaidKGS = princPaid;
      SET intPaidKGS = intPaid;
      SET penPaidKGS = penPaid;

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

      SET isAlreadyInserted = isLoanDetailedSummaryExistsForDate(loan_id, tempDate);

      IF(isAlreadyInserted = FALSE) THEN

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

          INSERT INTO accrue(version, daysInPeriod, fromDate, interestAccrued, lastInstPassed, penaltyAccrued, penaltyOnInterestOverdue, penaltyOnPrincipalOverdue, toDate, loanId)
          VALUES (1, daysInPer, pDate, ROUND(intAccrued,2), FALSE , ROUND(penAccrued,2), ROUND(pOIO,2), ROUND(pOPO,2), tempDate, loan_id);
        END IF;

        SET pDate = tempDate;

        SET total_outstanding = princOutstanding + intOutstanding + penOutstanding;
        SET total_overdue = princOverdue + intOverdue + penOverdue;
        SET total_paid = total_paid + princPaid + intPaid + penPaid;
        SET total_paidKGS = total_paidKGS + princPaidKGS + intPaidKGS + penPaidKGS;

        IF(isAlreadyInserted = FALSE) THEN

          INSERT INTO loanSummary(version, loanAmount, onDate, outstadingFee, outstadingInterest, outstadingPenalty, outstadingPrincipal, overdueFee, overdueInterest, overduePenalty,
                                  overduePrincipal, paidFee, paidInterest, paidPenalty, paidPrincipal, totalDisbursed, totalOutstanding, totalOverdue, totalPaid, totalPaidKGS, totalInterestPaid, totalPenaltyPaid, totalPrincipalPaid, totalFeePaid, loanId)
          VALUES (1, loan_amount, tempDate, 0.0, intOutstanding, penOutstanding, princOutstanding, 0.0, intOverdue, penOverdue, princOverdue, 0.0, totalIntPaid, totalPrincPaid,
                                                                                                                                              princPaid, totalDisb, total_outstanding, total_overdue, total_paid, total_paidKGS, totalIntPaid, totalPenPaid, totalPrincPaid, 0, loan_id);
        END IF;

      END IF;

      IF flag = FALSE THEN
        SET flag = TRUE;
      END IF;

    END LOOP get_data;

    CLOSE tCursor;
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
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runCalculateLoanDetailedSummaryForAllLoans`(IN inDate date)
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
      WHERE loan.id NOT IN (SELECT DISTINCT parent_id FROM loan WHERE parent_id IS NOT NULL)
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

      CALL calculateLoanDetailedSummaryUntilOnDate(loanId, inDate, 0);

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
/*!50003 DROP PROCEDURE IF EXISTS `runCalculateLoanDetailedSummaryForSelectedLoans` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `runUpdateRootLoans`()
BEGIN

    DECLARE v_finished INTEGER DEFAULT 0;
    DECLARE loanId BIGINT;

    DEClARE tCursor CURSOR FOR

      SELECT loan.id
      FROM loan loan
      WHERE loan.id IN (SELECT DISTINCT parent_id FROM loan WHERE parent_id IS NOT NULL)
      ORDER BY loan.id;

    DECLARE CONTINUE HANDLER
    FOR NOT FOUND SET v_finished = 1;

    OPEN tCursor;

    run_calculate: LOOP

      FETCH tCursor INTO loanId;

      IF v_finished = 1 THEN
        LEAVE run_calculate;
      END IF;

      CALL updateRootLoanPayment(loanId);
      CALL updateRootLoanPaymentSchedule(loanId);

      INSERT INTO loanSummary (version, loanAmount, loanSummaryType, onDate, outstadingFee, outstadingInterest, outstadingPenalty, outstadingPrincipal, overdueFee, overdueInterest, overduePenalty, overduePrincipal, paidFee, paidInterest, paidPenalty, paidPrincipal, totalDisbursed, totalFeePaid, totalInterestPaid, totalOutstanding, totalOverdue, totalPaid, totalPenaltyPaid, totalPrincipalPaid, loanId)
        SELECT 1, p.loanAmount, p.loanSummaryType,
          p.onDate, p.outstadingFee,
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
          p.totalPenaltyPaid,
          p.totalPrincipalPaid,
          loanId
        FROM loanSummary p
        WHERE p.loanId IN
              (SELECT id
               FROM loan
               WHERE parent_id = loanId)
        ORDER BY p.onDate;

      CALL updateRootLoanSummary(loanId);

    END LOOP run_calculate;

    CLOSE tCursor;

    SELECT 'completed' as message;

  END ;;
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
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
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
      select 'start' as type, bn.startedOnDate as onDate, bn.loanId
      from bankrupt bn
      union all
      select 'finish' as type, bn.finishedOnDate as onDate, bn.loanId
      from bankrupt bn
      union all
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

      IF b_type = 'finish' OR b_type = 'close' THEN
        #Nullify overdue and outstanding fields on loanDetailedSummary(no action if negative) and loanSummary tables
        update loandetailedsummary lds
        set lds.interestOverdue = case when lds.interestOverdue >= 0 then 0 else lds.interestOverdue end,
            lds.interestOutstanding = case when lds.interestOutstanding >= 0 then 0 else lds.interestOutstanding end,
            lds.principalOverdue = case when lds.principalOverdue >= 0 then 0 else lds.principalOverdue end,
            lds.principalOutstanding = case when lds.principalOutstanding >= 0 then 0 else lds.principalOutstanding end,
            lds.penaltyOverdue = case when lds.penaltyOverdue >= 0 then 0 else lds.penaltyOverdue end,
            lds.penaltyOutstanding = case when lds.penaltyOutstanding >= 0 then 0 else lds.penaltyOutstanding end
        where lds.loanId = b_loanId
        and lds.onDate >= b_onDate;

        update loansummary ls
        set ls.overduePrincipal = 0,
            ls.outstadingPrincipal = 0,
            ls.overdueInterest = 0,
            ls.outstadingInterest = 0,
            ls.overduePenalty = 0,
            ls.outstadingPenalty = 0,
            ls.overdueFee = 0,
            ls.outstadingFee = 0
        where ls.loanId = b_loanId
        and ls.onDate >= b_onDate;

      END IF;

      IF b_type = 'start' THEN
        #Nullify overdue fields loanDetailedSummary(no action if negative) and loanSummary tables
        update loandetailedsummary lds
        set lds.interestOverdue = case when lds.interestOverdue >= 0 then 0 else lds.interestOverdue end,
            lds.principalOverdue = case when lds.principalOverdue >= 0 then 0 else lds.principalOverdue end,
            lds.penaltyOverdue = case when lds.penaltyOverdue >= 0 then 0 else lds.penaltyOverdue end
        where lds.loanId = b_loanId
        and lds.onDate >= b_onDate;

        update loansummary ls
        set ls.overduePrincipal = 0,
            ls.overdueInterest = 0,
            ls.overduePenalty = 0,
            ls.overdueFee = 0
        where ls.loanId = b_loanId
        and ls.onDate >= b_onDate;
      END IF;

    END LOOP get_data;

    CLOSE tCursor;
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
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateRootLoanSummary`(IN loan_id BIGINT)
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
        pTotalOutstanding, pTotalOverdue, pTotalPaid, pTotalPenaltyPaid, pTotalPrincipalPaid;

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-13 10:19:35
