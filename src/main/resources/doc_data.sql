SET NAMES 'utf8';

INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', '����������', 'internal');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', '��������', 'incoming');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', '���������', 'outgoing');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', '�����', 'archived');

INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', '��������� �������', 'zapiska', 1);
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', '���������', 'zayavleniye', 1);
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', '������', 'prikaz', 1);
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', '�������', 'dogovor', 1);
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`, documentSubTypes) VALUES ('1', '��������', 'protocol', 1);

INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Creat','create');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� ������������','toreconcile');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Reconcile','reconcile');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� �����������','toapprove');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Approve','approve');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Reject','reject');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� �����������','tocorrect');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Register','register');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� ����������','toexecute');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� ������������','toinform');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Accept','accept');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Start','start');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'Done','done');

CREATE
DEFINER = 'root'@'localhost'
VIEW mfloan.accounts
AS
SELECT
  `data`.`id` AS `id`,
  `data`.`name` AS `name`,
  `data`.`atype` AS `atype`
FROM (SELECT
    `mfloan`.`organization`.`id` AS `id`,
    `mfloan`.`organization`.`name` AS `name`,
    1 AS `atype`
  FROM `mfloan`.`organization`
  UNION ALL
  SELECT
    `mfloan`.`department`.`id` AS `id`,
    `mfloan`.`department`.`name` AS `name`,
    2 AS `atype`
  FROM `mfloan`.`department`
  UNION ALL
  SELECT
    `mfloan`.`staff`.`id` AS `id`,
    `mfloan`.`staff`.`name` AS `name`,
    3 AS `atype`
  FROM `mfloan`.`staff`
  UNION ALL
  SELECT
    `mfloan`.`person`.`id` AS `id`,
    `mfloan`.`person`.`name` AS `name`,
    4 AS `atype`
  FROM `mfloan`.`person`) `data`;