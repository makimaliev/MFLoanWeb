//== Class definition

var DatatableDataLocalDetailedSummaries = function () {
	//== Private functions
	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonDetailedSummaries);
		var datatable = $('#detailedSummariesTable').mDatatable({
			// datasource definition
			data: {
				type: 'local',
				source: dataJSONArray
			},

			// layout definition
			layout: {
				theme: 'default', // datatable theme
				class: '', // custom wrapper class
				scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
				// height: 450, // datatable's body's fixed height
				footer: false // display/hide footer
			},

			// column sorting(refer to Kendo UI)
			sortable: false,

			// column based filtering(refer to Kendo UI)
			filterable: false,

			pagination: false,

			// inline and bactch editing(cooming soon)
			// editable: false,

			// columns definition
			columns: [{
                field: "id",
                title: "#",
                responsive: {hidden: 'xl'},
            }, {
				field: "onDate",
				title: "На дату"
			},{
                field: "disbursement",
                title: "Освоение",
                template: function (row) {
                    return (row.disbursement).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalDisbursement",
                title: "Всего освоено",
                template: function (row) {
                    return (row.totalDisbursement).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "principalPayment",
                title: "По графику по осн.с.",
                template: function (row) {
                    return (row.principalPayment).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalPrincipalPayment",
                title: "Всего по графику по осн.с.",
                template: function (row) {
                    return (row.totalPrincipalPayment).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "principalPaid",
                title: "Погашение по осн.с.",
                template: function (row) {
                    return (row.principalPaid).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalPrincipalPaid",
                title: "Всего погашено по осн.с.",
                template: function (row) {
                    return (row.totalPrincipalPaid).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "principalWriteOff",
                title: "Списание по осн.с.",
                template: function (row) {
                    return (row.principalWriteOff).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalPrincipalWriteOff",
                title: "Всего списано по осн.с.",
                template: function (row) {
                    return (row.totalPrincipalWriteOff).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "principalOutstanding",
                title: "Ост. по осн.с.",
                template: function (row) {
                    return (row.principalOutstanding).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "principalOverdue",
                title: "Ост. по осн.с.",
                template: function (row) {
                    return (row.principalOverdue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "daysInPeriod",
                title: "Кол-во дней"
            }, {
                field: "interestAccrued",
                title: "Начисление проц.",
                template: function (row) {
                    return (row.interestAccrued).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalInterestAccrued",
                title: "Всего начислено проц.",
                template: function (row) {
                    return (row.totalInterestAccrued).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalInterestAccruedOnInterestPayment",
                title: ",из них подлежит погашению",
                template: function (row) {
                    return (row.totalInterestAccruedOnInterestPayment).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "interestPayment",
                title: "По графику по проц.",
                template: function (row) {
                    return (row.interestPayment).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalInterestPayment",
                title: "Всего по графику по проц.",
                template: function (row) {
                    return (row.totalInterestPayment).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "collectedInterestPayment",
                title: "По графику нак.проц.",
                template: function (row) {
                    return (row.collectedInterestPayment).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalCollectedInterestPayment",
                title: "Всего по графику нак.проц.",
                template: function (row) {
                    return (row.totalCollectedInterestPayment).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "collectedInterestDisbursed",
                title: "Всего нак. проц.",
                template: function (row) {
                    return (row.collectedInterestDisbursed).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "interestPaid",
                title: "Погашение проц.",
                template: function (row) {
                    return (row.interestPaid).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalInterestPaid",
                title: "Всего погашено проц.",
                template: function (row) {
                    return (row.totalInterestPaid).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "interestOutstanding",
                title: "Остаток по процентам",
                template: function (row) {
                    return (row.interestOutstanding).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "interestOverdue",
                title: "Проср. по проц.",
                template: function (row) {
                    return (row.interestOverdue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "penaltyAccrued",
                title: "Начисление штр.",
                template: function (row) {
                    return (row.penaltyAccrued).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalPenaltyAccrued",
                title: "Всего начислено штр.",
                template: function (row) {
                    return (row.totalPenaltyAccrued).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "collectedPenaltyPayment",
                title: "По графику нак.штр.",
                template: function (row) {
                    return (row.collectedPenaltyPayment).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalCollectedPenaltyPayment",
                title: "Всего по графику нак.штр.",
                template: function (row) {
                    return (row.totalCollectedPenaltyPayment).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "collectedPenaltyDisbursed",
                title: "Всего нак.штр.",
                template: function (row) {
                    return (row.collectedPenaltyDisbursed).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "penaltyPaid",
                title: "Погашение штр.",
                template: function (row) {
                    return (row.penaltyPaid).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "totalPenaltyPaid",
                title: "Всего погашено штр.",
                template: function (row) {
                    return (row.totalPenaltyPaid).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "penaltyOutstanding",
                title: "Остаток по штр.",
                template: function (row) {
                    return (row.penaltyOutstanding).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "penaltyOverdue",
                title: "Проср. по штр.",
                template: function (row) {
                    return (row.penaltyOverdue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }],
            translate: {
                records: {
                    processing: 'Подождите пожалуйста...',
                    noRecords: 'Записей не найдено'
                },
                toolbar: {
                    pagination: {
                        items: {
                            default: {
                                first: 'Первая страница',
                                prev: 'Предыдущая страница',
                                next: 'Следующая страница',
                                last: 'Последняя страница',
                                more: 'Другие страницы',
                                input: 'Номер страницы',
                                select: 'Выбрать размер страницы'
                            },
                            info: 'Отображение {{start}} - {{end}} из {{total}} записей'
                        }
                    }
                }
            },

            toolbar: {
                items: {
                    // pagination
                    pagination: {
                        // page size select
                        pageSizeSelect: [5, 10, 20, 50, 100]
                    }
                }
            }
		});

		var query = datatable.getDataSourceQuery();

		$('#m_form_search4').on('keyup', function (e) {
			datatable.search($(this).val().toLowerCase());
		}).val(query.generalSearch);

		/*
		$('#m_form_status').on('change', function () {
			datatable.search($(this).val(), 'Status');
		}).val(typeof query.Status !== 'undefined' ? query.Status : '');

		$('#m_form_type').on('change', function () {
			datatable.search($(this).val(), 'Type');
		}).val(typeof query.Type !== 'undefined' ? query.Type : '');

		$('#m_form_status, #m_form_type').selectpicker();
        */
	};

	return {
		//== Public functions
		init: function () {
			// init dmeo
            mainTableInit();
		}
	};
}();

jQuery(document).ready(function () {
    DatatableDataLocalDetailedSummaries.init();
});