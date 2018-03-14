INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', 'Внутренний', 'internal');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', 'Входяший', 'incoming');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', 'Исходяший', 'outgoing');
INSERT INTO `mfloan`.`cat_document_type` (`version`, `name`, `internalName`) VALUES ('1', 'Архив', 'archived');

INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', 'Служебная записка', 'zapiska');
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', 'Заявление', 'zayavleniye');
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', 'Приказ', 'prikaz');
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', 'Договор', 'dogovor');
INSERT INTO `mfloan`.`cat_document_subtype` (`version`, `name`, `internalName`) VALUES ('1', 'Протокол', 'protocol');

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
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'start','Start');
INSERT INTO `mfloan`.`cat_document_status` (`version`, `name`, `internalName`) VALUES (1,'done','Done');