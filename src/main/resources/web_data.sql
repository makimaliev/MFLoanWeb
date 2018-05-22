/* COMMON */

-- Table

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('ID', 'ID', 'label.table.id', 'ID');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('View', 'View', 'label.table.view', 'View');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Edit', 'Edit', 'label.table.edit', 'Edit');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Delete', 'Delete', 'label.table.delete', 'Delete');

-- Pagination

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Start', 'Start', 'label.pagination.start', 'Start');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('First', 'First', 'label.pagination.prev', 'First');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Next', 'Next', 'label.pagination.next', 'Next');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('End', 'End', 'label.pagination.end', 'End');

-- Toolbar

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Search', 'Search', 'label.search', 'Search');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Show', 'Show', 'label.pageSize', 'Show');


/* DEBTOR */

-- General

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Debtor Page Title', 'Debtor Page Title', 'label.debtor.page.title', 'Debtor Page Title');

-- Headers

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Debtors', 'Debtors', 'label.debtor.debtors', 'Debtors');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Loans', 'Loans', 'label.loans', 'Loans');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Collateral Agreements', 'Collateral Agreements', 'label.agreements', 'Collateral Agreements');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Collection Procedures', 'Collection Procedures', 'label.procedures', 'Collection Procedures');

-- Table

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Name', 'Name', 'label.debtor.table.name', 'Name');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Type', 'Type', 'label.debtor.table.type', 'Type');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Organization form', 'Organization form', 'label.debtor.table.orgForm', 'Organization form');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Work sector', 'Work sector', 'label.debtor.table.workSector', 'Work sector');

-- Buttons

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Add New Debtor', 'Add New Debtor', 'label.debtor.addDebtor', 'Add New Debtor');]
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Add New Loan', 'Add New Loan', 'label.debtor.addLoan', 'Add New Loan');]
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Add New Collateral Agreement', 'Add New Collateral Agreement', 'label.debtor.addAgreement', 'Add New Collateral Agreement');]
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Add New Collection Procedure', 'Add New Collection Procedure', 'label.debtor.addProcedure', 'Add New Collection Procedure');]

-- Loans

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Registration number', 'Registration number', 'label.loan.table.regNumber', 'Registration number');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Registration date', 'Registration date', 'label.loan.table.regDate', 'Registration date');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Amount', 'Amount', 'label.loan.table.amount', 'Amount');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Currency', 'Currency', 'label.loan.table.currency', 'Currency');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Loan type', 'Loan type', 'label.loan.table.type', 'Loan type');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Loan state', 'Loan state', 'label.loan.table.state', 'Loan state');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Supervisor id', 'Supervisor id', 'label.loan.table.superId', 'Supervisor id');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Has sub loan', 'Has sub loan', 'label.loan.table.hasSubLoan', 'Has sub loan');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Parent loan id', 'Parent loan id', 'label.loan.table.parentId', 'Parent loan id');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Credit order id', 'Credit order id', 'label.loan.table.creditOrderId', 'Credit order id');

-- Collateral Agreements

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Agreement number', 'Agreement number', 'label.agreement.table.number', 'Agreement number');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Agreement date', 'Agreement date', 'label.agreement.table.date', 'Agreement date');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Collateral office reg num', 'Collateral office reg num', 'label.agreement.table.collRegNumber', 'Collateral office reg num');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Collateral office reg date', 'Collateral office reg date', 'label.agreement.table.collRegDate', 'Collateral office reg date');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Notary office reg number', 'Notary office reg number', 'label.agreement.table.notaryRegNumber', 'Notary office reg number');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Notary office reg date', 'Notary office reg date', 'label.agreement.table.notaryRegDate', 'Notary office reg date');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Arrest reg number', 'Arrest reg number', 'label.agreement.table.arrestRegNumber', 'Arrest reg number');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Arrest reg date', 'Arrest reg date', 'label.agreement.table.arrestRegDate', 'Arrest reg date');

-- Collection Procedures

INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Start Date', 'Start Date', 'label.procedure.table.startDate', 'Start Date');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Close Date', 'Close Date', 'label.procedure.table.closeDate', 'Close Date');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Last Phase', 'Last Phase', 'label.procedure.table.lastPhase', 'Last Phase');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Last Status Id', 'Last Status Id', 'label.procedure.table.lastStatusId', 'Last Status Id');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Procedure Status', 'Procedure Status', 'label.procedure.table.status', 'Procedure Status');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('Procedure Type', 'Procedure Type', 'label.procedure.table.type', 'Procedure Type');



INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
INSERT INTO `mfloan`.`message_resource` (`eng`, `kgz`, `messageKey`, `rus`) VALUES ('', '', 'label.debtor.', '');
