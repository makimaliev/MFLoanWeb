SET NAMES 'utf8';

INSERT INTO cat_document_type (version, name, internalName) VALUES ('1', 'Внутренний', 'internal');
INSERT INTO cat_document_type (version, name, internalName) VALUES ('1', 'Входяший', 'incoming');
INSERT INTO cat_document_type (version, name, internalName) VALUES ('1', 'Исходяший', 'outgoing');

INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', 'Служебная записка', 'zapiska', 1);
INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', 'Заявление', 'zayavleniye', 1);
INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', 'Приказ', 'prikaz', 1);
INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', 'Договор', 'dogovor', 2);
INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', 'Протокол', 'protocol', 3);

INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(1, 1, 'create', 'Создан', 'Создать');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(2, 1, 'request', 'На рассмотрении', 'На рассмотрение');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(3, 1, 'approve', 'Утвержден', 'Утвердить');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(4, 1, 'reject', 'Отклонен', 'Отклонить');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(5, 1, 'register', 'Зарегистрирован', 'Зарегистрировать');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(6, 1, 'accept', 'Принят', 'Принять');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(7, 1, 'start', 'Начат', 'Начать');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(8, 1, 'done', 'Завершен', 'Завершить');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(9, 1, 'send', 'Отправлен на исполнение', 'Отправить на исполнение');

DROP TABLE IF EXISTS accounts;

CREATE VIEW mfloan.accounts
AS
SELECT
  data.id AS id,
  (SELECT UUID()) AS uuid,
  1 AS version,
  data.internalName AS internalName,
  data.name AS name
FROM (
	SELECT
		organization.id AS id,
		organization.name AS name,
		'organization' AS internalName
	FROM organization
	UNION ALL
	SELECT
		department.id AS id,
		department.name AS name,
		'department' AS internalName
	FROM department
	WHERE (department.organization_id = 1)
	UNION ALL
	SELECT
		staff.id AS id,
		staff.name AS name,
		'staff' AS internalName
	FROM staff
	WHERE (staff.organization_id = 1)
	UNION ALL
	SELECT
		person.id AS id,
		person.name AS name,
		'person' AS internalName
	FROM person
  ) data
ORDER BY data.id;