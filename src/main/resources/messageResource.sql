# ORGANIZATION

insert into message_resource(kgz,rus,eng,messageKey) values ('Район','Район','District','label.district');
insert into message_resource(kgz,rus,eng,messageKey) values ('Баары','Все','All','label.all');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мекеме','Организация','Organization','label.organization');
insert into message_resource(kgz,rus,eng,messageKey) values ('Маалымат','Подробная информация','Organization Details','label.details.organization');

insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Наименование','Department Name','label.department.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тушундурмосу','Примечание','Department Description','label.department.description');


insert into message_resource(kgz,rus,eng,messageKey) values ('Банктын аталышы','Наименование банка','Bank name','label.bankData.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тушундурмосу','Примечание','Bank Description','label.bankData.description');
insert into message_resource(kgz,rus,eng,messageKey) values ('БИК','БИК','Bank BIK','label.bankData.bik');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсеп','Счет','Bank account number','label.bankData.accountNumber');

insert into message_resource(kgz,rus,eng,messageKey) values ('Статусу','Статус','Department Enabled','label.department.enabled');

insert into message_resource(kgz,rus,eng,messageKey) values ('Тузулушу','Организационная структура','Organization department','label.organization.department');

insert into message_resource(kgz,rus,eng,messageKey) values ('Ишкерлери','Сотрудники','Department staffs','label.staffs');

insert into message_resource(kgz,rus,eng,messageKey) values ('Аты-жону','Ф.И.О. сотрудника','Staff name','label.staff.name');

insert into message_resource(kgz,rus,eng,messageKey) values ('кызмат Кошуу','Добавить должность','Department add position','label.department.addPosition');
insert into message_resource(kgz,rus,eng,messageKey) values ('ишкер Кошуу','Добавить сотрудника','Department add staff','label.department.addStaff');

insert into message_resource(kgz,rus,eng,messageKey) values ('Аты-жону','Ф.И.О.','Staff person ','label.staff.person');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мекемеси','Организация','Staff organization ','label.staff.organization');
insert into message_resource(kgz,rus,eng,messageKey) values ('Отдел','Отдел','Staff department ','label.staff.department');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кызматы','Должность','Staff position ','label.staff.position');

insert into message_resource(kgz,rus,eng,messageKey) values ('Маалымат','Подробная информация','Person Details','label.details.of.person');

# DEBTOR


insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщиктин аты-жону','Наименование или Ф.И.О. заемщика','Debtor name','label.debtor.owner');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тармак','Отрасль','Work sector','label.workSector');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик кошуу','Добавить заемщика','Add debtor','label.add.debtor');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аты-жону','Заемщик','debtor Owner','label.debtor.add.owner');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик түрү','Вид ','Debtor type','label.debtor.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщикти кошуу/өзгөртүү','Добавить/Редактировать заемщика','Add/Edit debtor','label.title.add.edit.debtor');

insert into message_resource(kgz,rus,eng,messageKey) values ('Жалпы маалымат','Общая информация','Debtor general information','label.debtor.tab.loan.info');
insert into message_resource(kgz,rus,eng,messageKey) values ('баштоо','Инициирование','Init','label.debtor.tab.initializaPhase');


# LOAN

insert into message_resource(kgz,rus,eng,messageKey) values ('Толонуп буткон датасы','Дата полного погашения','Loan Close date','label.loan.closeDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептер','Расчеты','Loan summary','label.loan.loanSummary');
insert into message_resource(kgz,rus,eng,messageKey) values ('Куратор','Куратор','Supervisor','label.loan.superId');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит','Кредит','Loan','label.loan');
insert into message_resource(kgz,rus,eng,messageKey) values ('Жалпы маалымат','Общая информация','General information','label.loan.credit');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит тизмеси','Список кредитов','Loan list','label.debtors');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсепти сактоо','Сохранить расчет','Calculate manual','label.button.calculate.manual');
insert into message_resource(kgz,rus,eng,messageKey) values ('График','График','Payment schedules','label.loan.paymentSchedules');



insert into message_resource(kgz,rus,eng,messageKey) values ('График кошуу','Добавить график','Add payment schedule','label.loan.add.paymentSchedule');
insert into message_resource(kgz,rus,eng,messageKey) values ('График','График','Payment schedule','label.loan.paymentSchedule');
insert into message_resource(kgz,rus,eng,messageKey) values ('Дата','Дата','Expected date','label.loan.paymentSchedule.expectedDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Алынганы','Освоение/снятие','Disbursement','label.loan.paymentSchedule.disbursement');
insert into message_resource(kgz,rus,eng,messageKey) values ('Негизги төлөм','Осн.сумма','Principal payment','label.loan.paymentSchedule.principalPayment');
insert into message_resource(kgz,rus,eng,messageKey) values ('Пайыздары','Проценты','Interest payment','label.loan.paymentSchedule.interestPayment');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мурун топтолгон пайыздар','Нак. проценты','Collected interest payment','label.loan.paymentSchedule.collectedInterestPayment');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мурун топтолгон штр.','Нак. штрафы','Collected penalty payment','label.loan.paymentSchedule.collectedPenaltyPayment');
insert into message_resource(kgz,rus,eng,messageKey) values ('Статусу','Статус','Installment state','label.loan.paymentSchedule.installmentState');


insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөмдөр','Платежи','Payments','label.payments');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөмдөр','Платежи','Payments','label.loan.payments');

insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм кошуу','Добавить платеж','Add payment','label.loan.add.payment');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм','Платеж','Payment','label.loan.payment');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм кошуу/өзгортуу','Добавить/Редактировать платеж','Add/Edit payment','label.loan.title.add.edit.payment');
insert into message_resource(kgz,rus,eng,messageKey) values ('Датасы','Дата','Payment date','label.loan.payment.paymentDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Жалпы суммасы','Общая сумма','Total amount','label.loan.payment.totalAmount');
insert into message_resource(kgz,rus,eng,messageKey) values ('Негизги суммасы','Осн. сумма','Principal','label.loan.payment.principal');
insert into message_resource(kgz,rus,eng,messageKey) values ('Пайызы','Проценты','Interest','label.loan.payment.interest');
insert into message_resource(kgz,rus,eng,messageKey) values ('Айып пул','Штрафы','Penalty','label.loan.payment.penalty');
insert into message_resource(kgz,rus,eng,messageKey) values ('Акы','Пени','Fee','label.loan.payment.fee');
insert into message_resource(kgz,rus,eng,messageKey) values ('Номери','Платежный номер','Payment number','label.loan.payment.number');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм түрү','Вид платежа','Payment type','label.loan.payment.paymentType');

insert into message_resource(kgz,rus,eng,messageKey) values ('План кошуу/өзгортүү','Добавить/Редактировать план','Add/Edit plan','label.loan.title.add.edit.supervisorPlans');
insert into message_resource(kgz,rus,eng,messageKey) values ('Пландар','План','Plans','label.loan.supervisorPlans');
insert into message_resource(kgz,rus,eng,messageKey) values ('План кошуу','Добавить план','Add supervisor plan','label.loan.add.supervisorPlan');
insert into message_resource(kgz,rus,eng,messageKey) values ('План','План','Plan','label.loan.supervisorPlan');
insert into message_resource(kgz,rus,eng,messageKey) values ('План күнү','Дата','Plan date','label.loan.supervisorPlan.date');
insert into message_resource(kgz,rus,eng,messageKey) values ('План суммасы','Всего сумма','Plan amount','label.loan.supervisorPlan.amount');
insert into message_resource(kgz,rus,eng,messageKey) values ('Негизги суммасы','Осн. сумма','Principal','label.loan.supervisorPlan.principal');
insert into message_resource(kgz,rus,eng,messageKey) values ('План пайызы','Проценты','Interest','label.loan.supervisorPlan.interest');
insert into message_resource(kgz,rus,eng,messageKey) values ('План айып пулу','Штрафы','Penalty','label.loan.supervisorPlan.penalty');
insert into message_resource(kgz,rus,eng,messageKey) values ('Акы','Пени','Fee','label.loan.supervisorPlan.fee');
insert into message_resource(kgz,rus,eng,messageKey) values ('Примечание','Примечание','Description','label.loan.supervisorPlan.description');

insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгарууну кошуу/өзгортүү','Добавить/Редактировать списание','Add/Edit write offs','label.loan.title.add.edit.writeOff');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруу','Списание','Write offs','label.loan.writeOffs');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруу кошуу','Добавить списание','Add write offs','label.loan.add.writeOff');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруу','Списание','Write off','label.loan.writeOff');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруу күнү','Дата','Write offs date','label.loan.writeOff.date');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруунун жалпы суммасы','Общая сумма','Write offs total amount','label.loan.writeOff.totalAmount');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруунун негизги суммасы','Осн. сумма','Write offs principal','label.loan.writeOff.principal');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруунун пайызы','Проценты','Write offs interest','label.loan.writeOff.interest');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруунун айып пулу','Штрафы','Write offs penalty','label.loan.writeOff.penalty');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруунун акысы','Пени','Write offs fee','label.loan.writeOff.fee');
insert into message_resource(kgz,rus,eng,messageKey) values ('Примечание','Примечание','Write offs description','label.loan.writeOff.description');

insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттин шартын кошуу/өзгөртүү','Добавить/Редактировать условия кредита','Credit term','label.loan.title.add.edit.creditTerm');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит шарттарын кошуу','Добавить условия кредита','add Term','label.loan.add.supervisorTerm');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттин шарттары','Условия кредита','Credit terms','label.loan.creditTerms');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттин шарты','Условия кредита','Credit term','label.loan.creditTerm');
insert into message_resource(kgz,rus,eng,messageKey) values ('Башталышы','Начало действия','Start date','label.loan.creditTerm.startDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Пайыздык чен','Процентная ставка','Interest rate value','label.loan.creditTerm.interestRateValue');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөө мөнөтү','Период начисления','Rate period','label.loan.creditTerm.ratePeriod');
insert into message_resource(kgz,rus,eng,messageKey) values ('Туруксуз баа түрү','Вид плавающих ставок','Floating rate type','label.loan.creditTerm.floatingRateType');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өткөргөн күнүнүн негизги баасына карата айып пулу','Штраф за просрочку по осн.с.','Penalty on principal overdue rate value','label.loan.creditTerm.penaltyOnPrincipleOverdueRateValue');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өткөргөн күнүнүн негизги баасына карата айып пулу','Штраф за просрочку по осн.с.','Penalty on principal overdue rate value','label.loan.creditTerm.enaltyOnPrincipleOverdueRateValue');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өткөргөн күнүнүн негизги баасына карата айып пулунун түрү','Вид штрафа за просрочку по осн.с.','Penalty on principal overdue rate type','label.loan.creditTerm.penaltyOnPrincipleOverdueRateType');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өткөргөн күнүнүн пайыздык ченине карата айып пулу','Штраф за просрочку по процентам','Penalty on interest overdue rate value','label.loan.creditTerm.penaltyOnInterestOverdueRateValue');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өткөргөн күнүнүн пайыздык ченине карата айып пулунун түрү','Вид штрафа за просрочку по процентам','Penalty on interest overdue rate type','label.loan.creditTerm.penaltyOnInterestOverdueRateType');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эң жогорку пайыздык чек','Предельный лимит начисления штрафов','Penalty limit percent','label.loan.creditTerm.penaltyLimitPercent');
insert into message_resource(kgz,rus,eng,messageKey) values ('Айып пулдун бүтүү керектиги болгон аякы күн','Дата предельного начисления штрафов','Penalty limit end date','label.loan.creditTerm.penaltyLimitEndDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Бүтүмдөр тартиби','Очередь погашения','Transaction order','label.loan.creditTerm.transactionOrder');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күндөр','Метод расчета кол-ва дней в период','Days in method','label.loan.creditTerm.daysInMonthMethod');
insert into message_resource(kgz,rus,eng,messageKey) values ('Жылдар','Метод расчета кол0ва дней в год','Days in year method','label.loan.creditTerm.daysInYearMethod');

insert into message_resource(kgz,rus,eng,messageKey) values ('Төмөнкү кредиттер','Субкредиты','Sub loans','label.loan.childList');
insert into message_resource(kgz,rus,eng,messageKey) values ('Толук эсептөө','Дет. расчет','Loan detailed summary','label.loan.loanDetailedSummary');
insert into message_resource(kgz,rus,eng,messageKey) values ('Топтомдор','Начисление','Accrue','label.loan.accrue');
insert into message_resource(kgz,rus,eng,messageKey) values ('Датасы','Дата','Date','label.date');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасын кошуу','Претензия','Add collection phase','label.add.collection.phase');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кошумча','Дополнительно','Additional','label.additional');

insert into message_resource(kgz,rus,eng,messageKey) values ('Мүлк','Товар','Good','label.loan.good');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мүлк кошуу','Добавить товар','Аdd good','label.loan.add.good');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мүлк кошуу/өзгортүү','Добавить/Редактировать товар','Add/Edit good','label.loan.title.add.edit.good');
insert into message_resource(kgz,rus,eng,messageKey) values ('Саны','Количество','Quantity','label.loan.good.quantity');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өлчөм түрү','Ед. измерения','Unit type','label.loan.good.unitType');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мүлк түрү','Вид товара','Good type','label.loan.good.goodType');

insert into message_resource(kgz,rus,eng,messageKey) values ('Карыздын которулушун кошуу/өзгортүү','Добавить/Редактировать перевод долга','Add/Edit debt transfer','label.loan.add.edit.debtTransfer');
insert into message_resource(kgz,rus,eng,messageKey) values ('Карыздын которулушу','Перевод долга','Debt transfer','label.loan.debtTransfer');
insert into message_resource(kgz,rus,eng,messageKey) values ('Карыздын которулушун кошуу','Добавить перевод долга','Add debt transfer','label.loan.add.debtTransfer');

insert into message_resource(kgz,rus,eng,messageKey) values ('Номер','Номер','Number','label.loan.debtTransfer.number');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күнү','Дата','Date','label.loan.debtTransfer.date');
insert into message_resource(kgz,rus,eng,messageKey) values ('Саны','Количество','Quantity','label.loan.debtTransfer.quantity');
insert into message_resource(kgz,rus,eng,messageKey) values ('Данасынын баасы','Цена за единицу','Price per unit','label.loan.debtTransfer.pricePerUnit');
insert into message_resource(kgz,rus,eng,messageKey) values ('Данасынын түрү','Тип единицы','Unit type','label.loan.debtTransfer.unitType');
insert into message_resource(kgz,rus,eng,messageKey) values ('Жалпы баасы','Общая стоимость','Total cost','label.loan.debtTransfer.totalCost');
insert into message_resource(kgz,rus,eng,messageKey) values ('Которуучу','От кого','Transfer person','label.loan.debtTransfer.transferPerson');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мүлктүн түрү','Вид товара','Good type','label.loan.debtTransfer.goodsType');

insert into message_resource(kgz,rus,eng,messageKey) values ('Ээси','Наименование','Item owner','label.item.owner');


insert into message_resource(kgz,rus,eng,messageKey) values ('Банкрот кошуу/өзгортүү','Добавить/Редактировать банкротство','Add/Edit bankrupt','label.loan.title.add.edit.bankrupt');
insert into message_resource(kgz,rus,eng,messageKey) values ('Банкрот','Банкротство','Bankrupt','label.loan.bankrupt');
insert into message_resource(kgz,rus,eng,messageKey) values ('Башталышы','Дата банкротства','Started date','label.loan.bankrupt.startedOnDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Бутушу','Дата завер. проц.банк.','Finished date','label.loan.bankrupt.finishedOnDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Багкрот кошуу','Добавить банкротство','Add bankrupt','label.loan.add.bankrupt');


# COLLATERAL

insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө келишими','Договор залога','Agreement','label.collateralagreement.tab.info');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө','Предмет залога','Collateral item','label.collateralagreement.tab.collateralItem');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө келишими','Договор залога','Collateral agreement','label.collateral.agreement');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө','Предмет залога','Collateral item','label.collateral.item');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнү кошуу/өзгөртүү','Добавить/Редактировать залог','Add/Edit item','label.collateral.add.edit.item');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө','Общая информация','Collateral item information','label.collateralitem.tab.info');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнү бошотуу','Снятие с ареста','Collateral item arrest free','label.collateralitem.tab.collateralArrestFree');
insert into message_resource(kgz,rus,eng,messageKey) values ('Акттар','Акты обследования','Collateral item inspection','label.collateralitem.tab.collateralInspection');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө','Добавить предмет залога','Item','label.add.collateral.item');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнү алуу','Добавить снятие с ареста','Arrest free','label.add.collateral.arrestFree');
insert into message_resource(kgz,rus,eng,messageKey) values ('Текшерүүнү жыйынтыгы','Добавить акт обследования','Inspection result','label.add.collateral.inspection.result');
insert into message_resource(kgz,rus,eng,messageKey) values ('Деталдар','Детали','Details','label.collateralItem.details');
insert into message_resource(kgz,rus,eng,messageKey) values ('Текшерүүнүн жыйынтыгы','Акт обследования','Result of inspection','label.collateral.inspection.result');
insert into message_resource(kgz,rus,eng,messageKey) values ('Текшерүүнүн жыйынтгын кошуу','Добавить акт обследования','Add inspection result','label.add.inspection.result');
insert into message_resource(kgz,rus,eng,messageKey) values ('Деталдар','Примечание','Details','label.arrestFree.details');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнү алуу','Снятие с ареста','Arrest free','label.collateral.arrestFree');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнү кошуу/өзгөртүү','Добавить/Редактировать снятие с ареста','Add/Edit arrest free','label.collateral.add.edit.arrestFree');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнү кошуу/өзгөртүү','Добавить/Редактировать акт обследования','Add/Edit arrest free','label.inspection.add.edit.result');

insert into message_resource(kgz,rus,eng,messageKey) values ('Кочурмосун алуу','Создать копию','Clone','label.table.clone');

# COLLECTION

insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасы','Фазы взыскания','Collection phases','label.collection.phases');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасы','Фаза взыскания','Collection phase','label.collection.phase');

insert into message_resource(kgz,rus,eng,messageKey) values ('Бүтүшү','Дата результата','Closing date','label.collection.phase.closeDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Башталышы','Дата инициирования','Start','label.collection.phase.startDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Статусту өзгортүү','Изменить статус','Change status','label.title.collection.change.status');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын түрү','Вид фазы','Type','label.collection.phase.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын статусу','Статус фазы','Status','label.collection.phase.status');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасын кошуу/өзгөтүү','Добавить/Редактировать фазу взыскания','Add/Edit collection phase','label.collection.add.edit.phase');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасын кошуу/өзгөтүү','Добавить/Редактировать фазу взыскания','Add/Edit collection phase','label.title.collection.add.edit.phase');

insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттер','Кредиты','Loans','label.agreement.loans');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасы','Процедура взыскания','Collection procedure','label.collection.procedure');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасын кошуу/өзгортүү','Добавить/Редактировать процедуру взыскание','Add/Edit collection procedure','label.title.collection.add.edit.procedure');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасын кошуу/өзгортүү','Добавить/Редактировать процедуру взыскание','Add/Edit collection procedure','label.collection.add.edit.procedure');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасын жаңыртуу','Обновить процедуру взыскание','Update collection phase procedure','label.update.collection.procedure');
insert into message_resource(kgz,rus,eng,messageKey) values ('Башталышы','Начало','Procedure start date','label.collection.procedure.startDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аякташы','Завершение','Procedure close date','label.collection.procedure.closeDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасынын статусу','Статус процедуры','Procedure status','label.collection.procedure.status');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасын кошуу','Добавить статус процедуры','Add procedure status','label.add.collection.procedure.status');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасы сактоо','Сохранить статус процедуры','Save procedure status','label.save.collection.procedure.status');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аякы өндүрүү процедурасы','Последняя фаза процедуры','Last phase of procedure','label.collection.procedure.lastPhase');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аякы өндүрүү процедурасынын статусу','Последний статус','Last status','label.collection.procedure.lastStatusId');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасынын түрү','Вид процедуры','Procedure type','label.collection.procedure.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасын кошуу','Добавить вид процедуры','Add procedure type','label.add.collection.procedure.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасын сактоо','Сохранить вид процедуры','Save procedure type','label.save.collection.procedure.type');




insert into message_resource(kgz,rus,eng,messageKey) values ('Күнүн тандоо','Выберите дату','Select date','label.select.date');


insert into message_resource(kgz,rus,eng,messageKey) values ('Тел. номер','Тел. номер','Phone number','label.tel.number');



insert into message_resource(kgz,rus,eng,messageKey) values ('Баары','Все','All','label.all');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщики','Заемщики','Debtors','label.title.debtors');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщики','Заемщики','Debtors','label.debtors');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик','Заемщик','Debtor','label.debtor');




insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит маалыматы','Информация о кредите','Information of credit','label.debtor.loan.info');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик маалматы','Информация о заемщике','Information of debtor','label.debtor.info');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө келишими','Договор залога','Collateral agreement','label.debtor.agreement');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү процедурасы','Процедура взыскание','Collection procedure','label.debtor.procedure');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасы','Инициализация фазы вызыскании','Initializa collection phase','label.debtor.initializePhase');

-- debtor organization
insert into message_resource(kgz,rus,eng,messageKey) values ('Облус','Область','Region','label.organization.region');
insert into message_resource(kgz,rus,eng,messageKey) values ('Район','Район','District','label.organization.district');
insert into message_resource(kgz,rus,eng,messageKey) values ('Айыл өкмөтү','Аильный округ','eng','label.organization.aokmotu');
insert into message_resource(kgz,rus,eng,messageKey) values ('Айыл','Село','Village','label.organization.village');
insert into message_resource(kgz,rus,eng,messageKey) values ('Дарек','Адрес','Address','label.organization.addressLine');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кызматкер','Сотрудник','Staff','label.organization.staff');
-- debtor person
insert into message_resource(kgz,rus,eng,messageKey) values ('Облус','Область','Region','label.person.region');
insert into message_resource(kgz,rus,eng,messageKey) values ('Район','Район','District','label.person.district');
insert into message_resource(kgz,rus,eng,messageKey) values ('Айыл өкмөтү','Аильный округ','eng','label.person.aokmotu');
insert into message_resource(kgz,rus,eng,messageKey) values ('Айыл','Село','Village','label.person.village');
insert into message_resource(kgz,rus,eng,messageKey) values ('Дарек','Адрес','Address','label.person.addressLine');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кызматкер','Сотрудник','Staff','label.person.staff');

insert into message_resource(kgz,rus,eng,messageKey) values ('Тапшырма','Задача','Jobs','label.jobs');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тапшырма','Задача','Jobs','label.jobs.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Дарек','Адрес','Address','label.person.address.line');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тел. номер','Тел. номер','Phone number','label.tel.number');



insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит','Кредит','Credit','label.credit');


insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү баштоо','Инициализация фазы вызыскании','Initializa collection phase','label.debtor.tab.initializaPhase');

insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттер','Кредиты','Credits','label.loans');
insert into message_resource(kgz,rus,eng,messageKey) values ('Корүү','Просмотр','View','label.loan.view');
insert into message_resource(kgz,rus,eng,messageKey) values ('Ысымы','Имя','Name of debtor','label.debtor.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Рег. номери','Регистрационный номер','Registration number','label.reg.number');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күндөн баштап','С даты','From date','label.fromDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күнгө чейин','До даты','To the date','label.toDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Ысымы','Имя','Name','label.v_debtor_name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Сумма','Сумма','Amount','label.v_loan_amount');
insert into message_resource(kgz,rus,eng,messageKey) values ('Катт. номери','Регистрационный номер','Registration number','label.v_loan_reg_number');
insert into message_resource(kgz,rus,eng,messageKey) values ('Катт. күнү','Дата регистрации','Registration date','label.v_loan_reg_date');
insert into message_resource(kgz,rus,eng,messageKey) values ('Көрүү','Просмотр','View','label.payment.view');

insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөө номери','Номер платежа','Payment number','label.payment.number');
insert into message_resource(kgz,rus,eng,messageKey) values ('Суммадан','Oт суммы','From amount','label.fromAmount');
insert into message_resource(kgz,rus,eng,messageKey) values ('Суммага','Hа сумму','To the amount','label.toAmount');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тартиби','Графики','Payment schedules','label.paymentSchedules');
insert into message_resource(kgz,rus,eng,messageKey) values ('Келишимдин шарттары','Условия договора','Credit order','label.credit.order');
insert into message_resource(kgz,rus,eng,messageKey) values ('План','План','Supervisor plan','label.supervisor.plans');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө','Предмет залога','Collateral item','label.collateral.items');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Наименование','Collateral item name','label.item.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Түрү','Вид','Collateral item type','label.item.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Текшерүү акты','Акт обследование','Collateral inspection name','label.collateral.inspections');
insert into message_resource(kgz,rus,eng,messageKey) values ('Текшерүү акты','Акт обследование','Collateral inspection name','label.collateral.inspection.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Текшерүү түрү','Вид обследование','Collateral inspection type','label.collateral.inspection.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөну бошотуу','Снятие залога','Collateral arrest free','label.collateral.arrestFrees');

insert into message_resource(kgz,rus,eng,messageKey) values ('Деталдары','Детали','Details','label.details');


-- collection phase




insert into message_resource(kgz,rus,eng,messageKey) values ('Статусту өзгөртүү','Изменить статус','Change status','label.collection.change.status');
-- collection phase status
insert into message_resource(kgz,rus,eng,messageKey) values ('Статусту кошуу/өзгөтүү','Добавить/Редактировать статус фазы','Add/Edit collection phase status','label.title.collection.phase.add.edit.status');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын статусу','Статус фазы','Collection phase status','label.title.collection.phase.status');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын статусун кошуу','Добавить статус фазы','Add collection phase status','label.add.collection.phase.status');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын статусун сактоо','Сохранить статус фазы','Save collection phase status','label.save.collection.phase.status');
-- collection phase type
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын түрүн кошуу/өзгөтүү','Добавить/Редактировать вид фазы','eng','label.title.collection.phase.add.edit.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын түрү','Вид фазы','eng','label.title.collection.phase.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын түрүн кошуу','Добавить вид фазы','Add collection type','label.add.collection.phase.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын түрүн сактоо','Сохранить вид фазы','Save collection type','label.save.collection.phase.type');
-- collection phase initialize
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын башын кошуу/өзгортүү','Добавить/Редактировать инициализаций фазы','Add/Edit collection phase initialization','label.title.collection.phase.add.edit.initialize');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасын баштоо','Инициализация фазы','Collection phase initialization','label.title.collection.phase.initialize');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын башын кошуу','Добавить инициализацию фазы','Initializa collection phase','label.add.collection.phase.initialize');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өндүрүү фазасынын  башын сактоо','Сохранить инициализацию фазы','Save collection phase intitalization','label.save.collection.phase.initialize');

-- collection procedure






insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик','Заемщики','Debtors','label.debtor.list');
insert into message_resource(kgz,rus,eng,messageKey) values ('Айыл өкмөт','Аильный округ','eng','label.add.aokmotu');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнүн түрү','Вид залога','Collateral item type','label.collateralItem.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Саны','Количество','Quantity','label.collateralItem.quantity}');
insert into message_resource(kgz,rus,eng,messageKey) values ('Примечание','Примечание','Description','label.collateralItem.description');
insert into message_resource(kgz,rus,eng,messageKey) values ('Значение','Значение','Value','label.collateralItem.collateralValue');
insert into message_resource(kgz,rus,eng,messageKey) values ('Баасы','Расчетная стоимость','Estimated value','label.collateralItem.estimatedValue');
insert into message_resource(kgz,rus,eng,messageKey) values ('Акыбалы','Состояние','eng','label.collateralItem.conditionType');

insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнү алуу','Снятие залога','Arrest free','label.collateralItem.arrestFreeInfo');






insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнү бошотуу','Снятие с ареста','Collateral item arrest free','label.collateralitem.tab.arrestFreeInfo');


insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө кошуу','Добавить предмет залога','Add collateral agreement','label.add.agreement');
insert into message_resource(kgz,rus,eng,messageKey) values ('Келишим куну','Дата договора','Agreement date','label.agreement.date');
insert into message_resource(kgz,rus,eng,messageKey) values ('Келишим номери','Номер договора','Agreement number','label.agreement.number');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттер','Кредиты','Loans','label.agreement.loans');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө канторасынын номери','Номер залоговый канторы','Collateral office reg. number','label.agreement.collateralOfficeRegNumber');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө канторасынын куну','Дата залоговый канторы','Collateral office reg. date','label.agreement.collateralOfficeRegDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Натариустун номери','Номер нотариуса','Notary office reg. number','label.agreement.notaryOfficeRegNumber');
insert into message_resource(kgz,rus,eng,messageKey) values ('Натариустун куну','Дата нотариуса','Notary office reg. date','label.agreement.notaryOfficeRegDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Арестке алуу номери','Номер ареста','Arrest reg. number','label.agreement.arrestRegNumber');
insert into message_resource(kgz,rus,eng,messageKey) values ('Арестке алуу куну','Дата ареста','Arrest reg.date','label.agreement.arrestRegDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('№','№','Id','label.id');
insert into message_resource(kgz,rus,eng,messageKey) values ('Издөө','Искать','Search','label.search');

insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнөн түрү','Вид залога','Item type','label.add.collateral.item.type');

insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнү кошуу/өзгөртүү','Добавить/Редактировать снятие залога','Add/Edit arrest free','label.collateral.add.edit.arrestFree');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөөнөн алынган күнү','Дата снятие залога','Date ','label.arrestFree.onDate');



insert into message_resource(kgz,rus,eng,messageKey) values ('Аты','Наименование','Item name','label.collateralItem.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тандоо','Выбирать','Select','label.select');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күрөө документи','Документ залога','Document','label.collateralItem.document');
insert into message_resource(kgz,rus,eng,messageKey) values ('Себеби','Причина','Reason','label.collateralItem.incomplete_reason');
insert into message_resource(kgz,rus,eng,messageKey) values ('Товар түрү','Вид товара','Item good type','label.collateralItem.goods_type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Товар адреси','Адрес товара','Item good address','label.collateralItem.goods_address');
insert into message_resource(kgz,rus,eng,messageKey) values ('№','№','Id','label.collateralItem.goods_id');
insert into message_resource(kgz,rus,eng,messageKey) values ('Келишим шарты','Условия договора','Credit order','label.title.credit.orders');
insert into message_resource(kgz,rus,eng,messageKey) values ('Шарттардын түрү','Вид условий','Condition types','label.condition.types');
insert into message_resource(kgz,rus,eng,messageKey) values ('Шарттардын түрүн кошуу','Добавить вид условий','Add condition types','label.add.condition.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Item name','label.collateral.item.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Шарттардын түрүн кошуу/өзгөртүү','Добавить/Редатировать вид условий','Add/Edit condition type','label.condition.add.edit.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Name','label.condition.type.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Текшерүүнүн жыйынтгын кошуу/өзгөртүү','Добавить/Редактировать результат обследование','Add/Edit inspection result','label.title.inspection.add.edit.result');

insert into message_resource(kgz,rus,eng,messageKey) values ('Текшерүүнүн жыйынтгынын түрүн кошуу/өзгөртүү','Добавить вид результата обследование','Add inspection result type','label.add.inspection.result.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күнү','Дата','Date','label.inspectionResult.onDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Деталдары','Детали','Details','label.inspectionResult.details');
insert into message_resource(kgz,rus,eng,messageKey) values ('Текшерүүнүн жыйынтгынын түрү','Bид результата обследование','Inspection result type','label.inspectionResult.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Эч нерсе табылган жок','Ничего не найдено','No matching records found','label.no.matching.records.found');
insert into message_resource(kgz,rus,eng,messageKey) values ('Өзгөртүү','Редактировать','Edit','label.table.edit');
insert into message_resource(kgz,rus,eng,messageKey) values ('Сактоо','Сохранить','Save','label.save.inspection.result.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Name','label.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Түрү','Вид','Type','label.save.debtor.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Чоңдуктардын түрү','Вид величин','Type','label.save.item.quantity.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Чоңдуктардын түрү','Виды величин','Quantity type','label.item.quantity.types');
insert into message_resource(kgz,rus,eng,messageKey) values ('Чоңдуктардын түрүн кошуу','Добавить виды величин','Add quantity type','label.add.item.quantity.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Чоңдуктардын түрүн кошуу/өзгөртүү','Добавить/Редактировать договор','Add/Edit agreement','label.collateral.add.edit.agreement');
insert into message_resource(kgz,rus,eng,messageKey) values ('Келишим кошуу','Добавить договор','Add agreement','label.add.collateralAgreement.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Сактоо','Сохранить','Save','label.button.save');
insert into message_resource(kgz,rus,eng,messageKey) values ('Жокко чыгаруу','Отменить','Cancel','label.button.cancel');


-- loan







insert into message_resource(kgz,rus,eng,messageKey) values ('Кредитти кошуу/өзгортүү','Добавить/Редактировать кредит','Add/Edit loan','label.title.add.edit.loan');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредитти кошуу/өзгортүү','Добавить/Редактировать кредит','Add/Edit loan','label.add.edit.loan');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттер','Кредиты','Loans','label.loans');

insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит кошуу','Добавить кредит','Add loan','label.add.loan');
insert into message_resource(kgz,rus,eng,messageKey) values ('№','№','Id','label.loan.id');
insert into message_resource(kgz,rus,eng,messageKey) values ('Катт. номери','Рег. номер','Reg. number','label.loan.regNUmber');
insert into message_resource(kgz,rus,eng,messageKey) values ('Катт. күнү','Дата рег.','Reg. date','label.loan.regDate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Сумма','Сумма','Amount','label.loan.amount');
insert into message_resource(kgz,rus,eng,messageKey) values ('Валюта','Валюта','Currency','label.loan.currency');
-- loan type
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттин түрү','Вид кредитов','Loan type','label.loan.title.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттин түрүн кошуу','Добавить вид кредита','Add/edit loan type','label.loan.title.add.edit.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит түрү','Вид кредита','Loan type','label.loan.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит түрүн кошуу','Добавить вид кредита','Add loan type','label.loan.add.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит түрлөрү','Вид кредитов','Loan types','label.loan.types');

insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит статусу','Статус кредита','Loan status','label.loan.status');
-- loan state
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит статусу','Статус кредита','Loan state','label.loan.title.state');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит статусу','Статус кредита','Loan state','label.loan.state');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит статусун кошуу','Добавить статус кредита','Add loan state','label.loan.add.state');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит статусун кошуу/өзгортүү','Добавить/Редактировать статус кредита','Add/Edit loan state','label.loan.title.add.edit.state');

insert into message_resource(kgz,rus,eng,messageKey) values ('Төмөнкү кредит','Под кредит','Sub loan','label.loan.hasSubLoan');
insert into message_resource(kgz,rus,eng,messageKey) values ('Негизги','Основной','Parent loan','label.loan.partentId');
insert into message_resource(kgz,rus,eng,messageKey) values ('Негизги','Основной','parent','label.loan.parent');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредит шарттары','Условия кредита','Credit order','label.loan.creditOrderId');


-- loan payment






insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм','Платежи','Payments','label.loan.title.payments');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм','Платежи','Payments','label.loan.payments');


-- loan payment type
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм түрү','Вид платежа','Payment type','label.loan.payment.title.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм түрүн кошуу/өзгортүү','Добавить/Редактировать вид платежа ','Add/Edit payment type','label.loan.payment.title.add.edit.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм түрү','Вид платежа','Payment type','label.loan.payment.type.');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм түрүн кошуу','Добавить вид платежа','Add payment type','label.loan.payment.add.type');
-- loan supervisorPlan
insert into message_resource(kgz,rus,eng,messageKey) values ('План','Планы','Plans','label.loan.title.supervisorPlans');









-- loan writeoffs
insert into message_resource(kgz,rus,eng,messageKey) values ('Эсептен чыгаруу','Списания','Write offs','label.loan.title.writeOffs');




-- loan credit terms
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттин шарты','Условия кредита','Credit term','label.loan.title.creditTerm');




-- loan paymentSchedule

insert into message_resource(kgz,rus,eng,messageKey) values ('Ыраттамалар','Графики','Payment schedules','label.loan.title.paymentSchedules');



insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм кошуу','Добавить выплату','Add disbursement','label.loan.title.add.edit.paymentSchedule');
-- loan paymentSchedule installmentState
insert into message_resource(kgz,rus,eng,messageKey) values ('Узартуу менен акы төлөөнүн абалы','Состояние рассрочки','Installment state','label.loan.paymentSchedule.title.installmentState');
insert into message_resource(kgz,rus,eng,messageKey) values ('Узартуу менен акы төлөөнүн абалы','Состояние рассрочки','Installmetn states','label.loan.paymentSchedule.installmentStates');
insert into message_resource(kgz,rus,eng,messageKey) values ('Узартуу менен акы төлөөнүн абалы','Состояние рассрочки','Installment state','label.loan.paymentSchedule.installmentState');
insert into message_resource(kgz,rus,eng,messageKey) values ('Узартуу менен акы төлөөнү кошуу','Добавить рассрочку','Add installment state','label.loan.paymentSchedule.add.installmentState');
insert into message_resource(kgz,rus,eng,messageKey) values ('Төлөм абалын кошуу/өзгортүү','Добавить/Редактировать состояние платежа','Add/Edit installment state','label.loan.paymentSchedule.title.add.edit.installmentState');




-- loan debttransfer
insert into message_resource(kgz,rus,eng,messageKey) values ('Карыздын которулушун кошуу/өзгортүү','Добавить/Редактировать перевод долга','Add/Edit debt transfer','label.loan.title.add.edit.debtTransfer');

-- loan bankrupt



-- loan collateral
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттик камсыздоо кошуу/өзгортүү','Добавить/Редактировать кредитное обеспечение','Add/Edit collateral','label.loan.title.add.edit.collateral');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттик камсыздоо','Обеспечение','Collateral','label.loan.collateral');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кредиттик камсыздоо кошуу','Добавить обеспечение','Add collateral','label.loan.add.collateral');
-- loan goods



insert into message_resource(kgz,rus,eng,messageKey) values ('Мүлк','Товар','Good','label.loan.good');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мүлк кошуу','Добавить товар','Add good','label.loan.add.good');

-- organization form
insert into message_resource(kgz,rus,eng,messageKey) values ('Уюмдун формасы','Форма организации','Organization form','label.title.orgForm');
insert into message_resource(kgz,rus,eng,messageKey) values ('Уюмдун формасын кошуу','Добавить / Редактировать форма организаций','Add/Edit organization form','label.title.add.edit.orgForm');
insert into message_resource(kgz,rus,eng,messageKey) values ('Уюмдун формасы','Форма организации','Organization form','label.orgForm');
insert into message_resource(kgz,rus,eng,messageKey) values ('Уюмдун формасын кошуу','Добавить Форма организации','Add organization form','label.add.orgForm');
-- debtor types
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик түрү','Вид заемщика','Debtor types','label.debtor.types');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик түрү','Вид заемщика','Debtor type','label.debtor.title.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик түрүн кошуу/өзгортүү','Добавить/Редактировать вид заемщиков','Add/Edit debtor type','label.debtor.title.add.edit.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик түрү','Вид заемщика','Debtor type','label.debtor.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Заемщик түрүн кошуу','Добавить вид','Add type','label.debtor.add.type');
-- workSector
insert into message_resource(kgz,rus,eng,messageKey) values ('Тармак','Отрасль','Work sector','label.title.workSector');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тармак кошуу/өзгөртүү','Добавить/Редактировать отрасль','Add/Edit work sector','label.title.add.edit.workSector');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тармактар','Отрасли','Work sectors','label.workSectors');

insert into message_resource(kgz,rus,eng,messageKey) values ('Тармак кошуу','Добавить отрасль','Add work sector','label.add.workSector');

insert into message_resource(kgz,rus,eng,messageKey) values ('Өзгөртүү','Редактировать','Edit','label.edit');
insert into message_resource(kgz,rus,eng,messageKey) values ('Көрүү','Просмотр','View','label.view');

insert into message_resource(kgz,rus,eng,messageKey) values ('Объект талаасы','Поле объекта','Object field','label.objectField.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Сыпатто','Примечание','Description','label.objectField.description');
insert into message_resource(kgz,rus,eng,messageKey) values ('Метод аты','Имя метода','Method name','label.objectField.methodName');

insert into message_resource(kgz,rus,eng,messageKey) values ('Роль кошуу','Добавить роль','Add role','label.add.role');
insert into message_resource(kgz,rus,eng,messageKey) values ('Уруксат кошуу','Добавить разрешение','Add permission','label.add.permission');
insert into message_resource(kgz,rus,eng,messageKey) values ('Кошумча кредит кошуу','Добавить дополнительный кредит','Add sub loan','label.information.add.child');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тиркеме кошуу','Добавить приложение','Add attachment','label.information.add.attachment');
insert into message_resource(kgz,rus,eng,messageKey) values ('Фильтирлөөнүн параметри','Параметр фильтраций','Filter parameter','label.filterParameter.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Фильтирлөөнүн параметрин кошуу','Добавить параметр фильтра','Add filter parameter','label.filterParameter.add');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Name','label.filterParameter.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Түрү','Вид','Type','label.filterParameter.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Шарты','Условия','Comparator','label.filterParameter.comparator');
insert into message_resource(kgz,rus,eng,messageKey) values ('Салтырылчу мааниси','Сравнимое значение','Compared value','label.filterParameter.comparedValue');
insert into message_resource(kgz,rus,eng,messageKey) values ('Деталдар','Детали','Details','label.filterParameter.details');
insert into message_resource(kgz,rus,eng,messageKey) values ('Объектердин тизмеси','Список объектов','Object list','label.objectList');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Name','label.objectList.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Объектердин тизмесин кошуу','Добавить cписок объектов','Add object list','label.objectList.add');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мазмунунун түрү','Тип содержимого','Content type','label.objectList.contentType');
insert into message_resource(kgz,rus,eng,messageKey) values ('Объектердин тизмеси','Список объектов','Object list','label.objectList.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Деталдар','Детали','Details','label.objectList.detail.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Колдонуучулар','Пользователи','Users','label.users');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Name','label.contentParameter.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Саптын түрү','Тип строки','Row type','label.contentParameter.rowType');
insert into message_resource(kgz,rus,eng,messageKey) values ('Чөнөктүн түрү','Тип ячейки','Cell type','label.contentParameter.cellType');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мазмунунун түрү','Тип содержимого','Content type','label.contentParameter.contentType');
insert into message_resource(kgz,rus,eng,messageKey) values ('Талаанын аты','Имя поля','Field name','label.contentParameter.fieldName');
insert into message_resource(kgz,rus,eng,messageKey) values ('Туруктуу мааниси','Постоянное значение','Constant value','label.contentParameter.constantValue');
insert into message_resource(kgz,rus,eng,messageKey) values ('Туруктуу сан','Постоянное число','Constant integer','label.contentParameter.constantInt');
insert into message_resource(kgz,rus,eng,messageKey) values ('Туруктуу текст','Постоянный текст','Constant text','label.contentParameter.constantText');
insert into message_resource(kgz,rus,eng,messageKey) values ('Позиция','Позиция','Position','label.contentParameter.position');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тилкеден','Колонка из','Column from','label.contentParameter.columnFrom');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тилкеге','Колонка к','Column to','label.contentParameter.columnTo');
insert into message_resource(kgz,rus,eng,messageKey) values ('Саптан','Cтрока из','Row from','label.contentParameter.rowFrom');
insert into message_resource(kgz,rus,eng,messageKey) values ('Сапка','Строка к','Row to','label.contentParameter.rowTo');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Name','label.outputParameter.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Түрү','Вид','Type','label.outputParameter.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Мааниси','Значение','Value','label.outputParameter.value');
insert into message_resource(kgz,rus,eng,messageKey) values ('Текст','Текст','Text','label.outputParameter.text');
insert into message_resource(kgz,rus,eng,messageKey) values ('Позиция','Позиция','Position','label.outputParameter.position');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Modal name','label.outputParameter.modal.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Топтун түрү','Вид группы','Group type','label.groupType.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Топтун түрүн кошуу','Добавить вид группы','Add group type','label.groupType.add');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Name','label.groupType.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Талаанын аты','Имя поля','Field Name','label.groupType.fieldName');
insert into message_resource(kgz,rus,eng,messageKey) values ('Саптын аты','Имя строки','Row name','label.groupType.rowName');
insert into message_resource(kgz,rus,eng,messageKey) values ('Топтун түрү','Вид группы','Group type','label.groupType.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Туруксуз чендер','Плавающие ставки','Floating rate','label.floatingRate.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Туруксуз чендер','Плавающие ставки','Floating rate','label.floatingRate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Туруксуз чендер','Плавающие ставки','Floating rate list','label.floatingRate.list');
insert into message_resource(kgz,rus,eng,messageKey) values ('Түрү','Вид','Type','label.floatingRate.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Туруксуз чендерди кошуу','Добавить плавающие ставки','Add floating rate','label.floatingRate.add');
insert into message_resource(kgz,rus,eng,messageKey) values ('Чен','Ставка','Rate','label.floatingRate.rate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Чен түрү','Вид ставки','Rate type','label.floatingRate.rate.type');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күнү','Дата','Date','label.floatingRate.date');
insert into message_resource(kgz,rus,eng,messageKey) values ('Валюта','Валюта','Currency','label.currencyRate.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Валюта курсу','Курс валюты','Currency Rate list','label.currencyRate.list');
insert into message_resource(kgz,rus,eng,messageKey) values ('Валюта курсун кошуу','Добавить курс валюты','Add currency rate','label.currencyRate.add');
insert into message_resource(kgz,rus,eng,messageKey) values ('Чен','Ставка','Rate','label.currencyRate.rate');
insert into message_resource(kgz,rus,eng,messageKey) values ('Валюта','Валюта','Currency','label.currencyRate.currency');
insert into message_resource(kgz,rus,eng,messageKey) values ('Күнү','Дата','Date','label.currencyRate.date');
insert into message_resource(kgz,rus,eng,messageKey) values ('Токтот','Стоп','Stop','label.job.stop');
insert into message_resource(kgz,rus,eng,messageKey) values ('Башта','Начните','Start','label.job.start');
insert into message_resource(kgz,rus,eng,messageKey) values ('Статус','Статус','Job state','label.job.state');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аракет','Действие','Action','label.job.action');
insert into message_resource(kgz,rus,eng,messageKey) values ('Тапырманы кошуу/өзгөртүү','Добавить/Сохранить задачу','Add/Edit job','label.job.add');
insert into message_resource(kgz,rus,eng,messageKey) values ('Иштеп жатат','Работает','Running','label.job.running');
insert into message_resource(kgz,rus,eng,messageKey) values ('Токтотулган','Остановлен','Stopped','label.job.stopped');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Name','label.job.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Азыр','Сейчас','Run now','label.job.runNow');
insert into message_resource(kgz,rus,eng,messageKey) values ('Крон туюнтмасы','Крон выражение','Cron expression','label.job.cronExpression');
insert into message_resource(kgz,rus,eng,messageKey) values ('Иштетилген','Включен','Enabled','label.job.enabled');
insert into message_resource(kgz,rus,eng,messageKey) values ('Таблица','Таблица','Table','label.table.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Таблица','Таблица','Table','label.table');
insert into message_resource(kgz,rus,eng,messageKey) values ('Таблица кошуу','Добавить таблицу','Add table','label.table.add');
insert into message_resource(kgz,rus,eng,messageKey) values ('Иш жүзүндөгү аталышы','Фактическое название','Actual name','label.table.actualName');
insert into message_resource(kgz,rus,eng,messageKey) values ('Чыгыштын аталышы','Имя выхода','Output name','label.table.outputName');
insert into message_resource(kgz,rus,eng,messageKey) values ('Негизги талаа','Основное поле','Primary field','label.table.primaryField');
insert into message_resource(kgz,rus,eng,messageKey) values ('Талаалар','Поля','Fields','label.table.fields');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аракет','Действие','Action','label.table.action');
insert into message_resource(kgz,rus,eng,messageKey) values ('Таблицаны кошуу/өзгөртүү','Добавить/Редактировать таблицу','Add/Edit table','label.table.add.edit.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Байламта','Связка','Join','label.join.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Байламта кошуу','Добавить/Редактировать связки','Add/Edit join','label.join.add.edit.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Байламталар','Связки','Joins','label.joins');
insert into message_resource(kgz,rus,eng,messageKey) values ('Байламта','Связка','Join','label.join');
insert into message_resource(kgz,rus,eng,messageKey) values ('Байламта кошуу','Добавить связку','Add join','label.join.add');
insert into message_resource(kgz,rus,eng,messageKey) values ('Сол таблицанын аталышы','Название левой таблицы','Left table name','label.join.leftTableName');
insert into message_resource(kgz,rus,eng,messageKey) values ('Оң таблицанын аталышы','Название правой таблицы','Right table name','label.join.rightTableName');
insert into message_resource(kgz,rus,eng,messageKey) values ('Байламтанын тексти','Текст связки','Join text','label.join.joinText');
insert into message_resource(kgz,rus,eng,messageKey) values ('Иргегич','Классификатор','Classificator','label.classificator.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Иргегич кошуу/өзгөртүү','Добавить/Редактировать классификатор','Add/Edit classificator','label.classificator.add.edit.page.title');
insert into message_resource(kgz,rus,eng,messageKey) values ('Иргегич кошуу','Добавить классификатор','Add Classificator','label.classificator.add');
insert into message_resource(kgz,rus,eng,messageKey) values ('Аталышы','Имя','Name','label.classificator.name');
insert into message_resource(kgz,rus,eng,messageKey) values ('Иргегичтер','Классификаторы','Classifications','label.classificator.classifications');
insert into message_resource(kgz,rus,eng,messageKey) values ('Иргегич','Классификатор','Classificator','label.classificator');

