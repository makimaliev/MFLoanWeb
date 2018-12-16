SET NAMES 'utf8';

INSERT INTO cat_document_type(id, version, internalName, name, uuid, code, regNoFormat) VALUES(1, 1, 'internal', 'Внутренний', '7683dda6-b33e-11e8-96f8-529269fb1459', 'ВН', '{No}:{ВД}-{ДД}/{ММ}');
INSERT INTO cat_document_type(id, version, internalName, name, uuid, code, regNoFormat) VALUES(2, 1, 'incoming', 'Входяший', '82f0ae3e-b33e-11e8-96f8-529269fb1459', 'ВХ', '{No}:{ВД}-{ДД}/{ММ}');
INSERT INTO cat_document_type(id, version, internalName, name, uuid, code, regNoFormat) VALUES(3, 1, 'outgoing', 'Исходяший', '890f54dc-b33e-11e8-96f8-529269fb1459', 'ИД', '{No}:{ВД}-{ДД}/{ММ}');

INSERT INTO cat_document_subtype(id, version, internalName, name, documentType_id, uuid, code) VALUES(1, 1, 'zapiska', 'Служебная записка', 1, '49474d68-b33f-11e8-96f8-529269fb1459', 'СЗ');
INSERT INTO cat_document_subtype(id, version, internalName, name, documentType_id, uuid, code) VALUES(2, 1, 'zayavleniye', 'Заявление', 1, '4947513c-b33f-11e8-96f8-529269fb1459', 'З');
INSERT INTO cat_document_subtype(id, version, internalName, name, documentType_id, uuid, code) VALUES(3, 1, 'prikaz', 'Приказ', 1, '494753e4-b33f-11e8-96f8-529269fb1459', 'ПЗ');
INSERT INTO cat_document_subtype(id, version, internalName, name, documentType_id, uuid, code) VALUES(4, 1, 'dogovor', 'Договор', 2, '4947566e-b33f-11e8-96f8-529269fb1459', 'Д');
INSERT INTO cat_document_subtype(id, version, internalName, name, documentType_id, uuid, code) VALUES(5, 1, 'protocol', 'Протокол', 3, '494758c6-b33f-11e8-96f8-529269fb1459', 'ПЛ');

INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(1, 1, 'create', 'Создан', 'Создать', '73c7f6c9-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(2, 1, 'request', 'На рассмотрении', 'На рассмотрение', '73cc07bd-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(3, 1, 'approve', 'Утвержден', 'Утвердить', '73cc0b30-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(4, 1, 'reject', 'Отклонен', 'Отклонить', '73cc0d6c-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(5, 1, 'register', 'Зарегистрирован', 'Зарегистрировать', '73cc27dc-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(6, 1, 'accept', 'Принят', 'Принять', '73d69791-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(7, 1, 'start', 'Начат', 'Начать', '73d69a69-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(8, 1, 'done', 'Завершен', 'Завершить', '73d69c5b-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(9, 1, 'archive', 'Архивирован', 'Архивировать', '73d69e37-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(10, 1, 'send', 'Отправлен на исполнение', 'Отправить на исполнение', '73d6a016-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(11, 1, 'reconcile', 'Согласован', 'Согласовать', '73d6a1e5-8e5d-11e8-8a03-b8975a79c95a');
INSERT INTO cat_document_status(id, version, internalName, name, actionName, uuid) VALUES(12, 1, 'toreconcile', 'На согласование', 'На согласовании', '73d6a3bb-8e5d-11e8-8a03-b8975a79c95a');


DROP VIEW IF EXISTS `account`;

CREATE
DEFINER = 'root'@'localhost'
VIEW mfloan.account
AS
SELECT
  `data`.`id` AS `id`,
  1 AS `record_status`,
  (SELECT UUID()) AS `uuid`,
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
  WHERE (`mfloan`.`department`.`organization_id` = 1)
  UNION ALL
  SELECT
    `mfloan`.`staff`.`id` AS `id`,
    `mfloan`.`staff`.`name` AS `name`,
    'staff' AS `internalName`
  FROM `mfloan`.`staff`
  WHERE (`mfloan`.`staff`.`organization_id` = 1)
  UNION ALL
  SELECT
    `mfloan`.`person`.`id` AS `id`,
    `mfloan`.`person`.`name` AS `name`,
    'person' AS `internalName`
  FROM `mfloan`.`person`
  WHERE (`mfloan`.`person`.`name` <> '')) `data`
ORDER BY `data`.`id`;


DROP TABLE IF EXISTS chat_users;

CREATE
DEFINER = 'root'@'localhost'
VIEW mfloan.chat_users
AS
SELECT
  `u`.`id` AS `id`,
  `u`.`username` AS `username`,
  `s`.`name` AS `name`
FROM (`users` `u`
  LEFT JOIN `staff` `s`
    ON ((`u`.`staff_id` = `s`.`id`)))
WHERE (`u`.`staff_id` IS NOT NULL);