SET NAMES 'utf8';

INSERT INTO cat_document_type (version, name, internalName) VALUES ('1', '����������', 'internal');
INSERT INTO cat_document_type (version, name, internalName) VALUES ('1', '��������', 'incoming');
INSERT INTO cat_document_type (version, name, internalName) VALUES ('1', '���������', 'outgoing');

INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', '��������� �������', 'zapiska', 1);
INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', '���������', 'zayavleniye', 1);
INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', '������', 'prikaz', 1);
INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', '�������', 'dogovor', 2);
INSERT INTO cat_document_subtype (version, name, internalName, documentType_id) VALUES ('1', '��������', 'protocol', 3);

INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(1, 1, 'create', '������', '�������');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(2, 1, 'request', '�� ������������', '�� ������������');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(3, 1, 'approve', '���������', '���������');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(4, 1, 'reject', '��������', '���������');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(5, 1, 'register', '���������������', '����������������');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(6, 1, 'accept', '������', '�������');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(7, 1, 'start', '�����', '������');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(8, 1, 'done', '��������', '���������');
INSERT INTO cat_document_status(id, version, internalName, name, actionName) VALUES(9, 1, 'send', '��������� �� ����������', '��������� �� ����������');

DROP TABLE IF EXISTS account;

CREATE VIEW account
AS
SELECT
  id,
  (SELECT UUID()) AS uuid,
  1 AS version,
  internalName,
  name
FROM (
	SELECT
		id,
		name,
		'organization' AS internalName
	FROM organization
	UNION ALL
	SELECT
		id,
		name,
		'department' AS internalName
	FROM department
	WHERE (organization_id = 1)
	UNION ALL
	SELECT
		id,
		name,
		'staff' AS internalName
	FROM staff
	WHERE (organization_id = 1)
	UNION ALL
	SELECT
		id,
		name,
		'person' AS internalName
	FROM person
  WHERE name <> ''
  ) data
ORDER BY id;