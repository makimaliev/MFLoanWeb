SET NAMES 'utf8';

INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', 'Внутренний', 'internal');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', 'Входяший', 'incoming');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', 'Исходяший', 'outgoing');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', 'Архив', 'archived');

INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', 'Служебная записка', 'zapiska', 1);
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', 'Заявление', 'zayavleniye', 1);
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', 'Приказ', 'prikaz', 1);
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', 'Договор', 'dogovor', 1);
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', 'Протокол', 'protocol', 1);

INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Creat','create');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'На согласование','toreconcile');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Reconcile','reconcile');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'На утверждение','toapprove');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Approve','approve');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Reject','reject');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'На исправление','tocorrect');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Register','register');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'На выполнение','toexecute');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'На ознакомление','toinform');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Accept','accept');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Start','start');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Done','done');

CREATE
DEFINER = 'root'@'localhost'
VIEW mfloan.accounts
AS
SELECT
  `data`.`id` AS `id`,
  1 AS `version`,
  `data`.`internalName` AS `internalName`,
  `data`.`name` AS `name`
FROM (SELECT
    `mfloan`.`organization`.`id` AS `id`,
    `mfloan`.`organization`.`name` AS `name`,
    'organization' AS `internalName`
  FROM `mfloan`.`organization`
  UNION ALL
  SELECT
    `mfloan`.`department`.`id` AS `id`,
    `mfloan`.`department`.`name` AS `name`,
    'department' AS `internalName`
  FROM `mfloan`.`department`
  UNION ALL
  SELECT
    `mfloan`.`staff`.`id` AS `id`,
    `mfloan`.`staff`.`name` AS `name`,
    'staff' AS `internalName`
  FROM `mfloan`.`staff`
  UNION ALL
  SELECT
    `mfloan`.`person`.`id` AS `id`,
    `mfloan`.`person`.`name` AS `name`,
    'person' AS `internalName`
  FROM `mfloan`.`person`) `data`;