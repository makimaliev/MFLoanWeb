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

INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'������','create');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� ������������','toreconcile');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'����������','reconcile');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� �����������','toapprove');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'���������','approve');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'��������','reject');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� �����������','tocorrect');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'���������������','register');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� ����������','toexecute');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�� ������������','toinform');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'������','accept');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�����','start');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'��������','done');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'�����������','archived');

DROP TABLE IF EXISTS `mfloan`.`accounts`;

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
  FROM `mfloan`.`person`) `data`
  order by id;