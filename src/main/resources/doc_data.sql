INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', '����������', 'internal');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', '��������', 'incoming');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', '���������', 'outgoing');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', '�����', 'archived');

INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', '��������� �������', 'zapiska');
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', '���������', 'zayavleniye');
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', '������', 'prikaz');
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', '�������', 'dogovor');
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', '��������', 'protocol');

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
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'start','Start');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'done','Done');