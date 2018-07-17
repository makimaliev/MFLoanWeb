//== Class definition

var DatatableDataLocalTerms = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonTerms);

		var datatable = $('#termsTable').mDatatable({
			// datasource definition
			data: {
				type: 'local',
				source: dataJSONArray,
				pageSize: 5
			},

			// layout definition
			layout: {
				theme: 'default', // datatable theme
				class: '', // custom wrapper class
				scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
				height: 450, // datatable's body's fixed height
				footer: false // display/hide footer
			},

			// column sorting(refer to Kendo UI)
			sortable: false,

			// column based filtering(refer to Kendo UI)
			filterable: false,

			pagination: true,

			// inline and bactch editing(cooming soon)
			// editable: false,

			// columns definition
			columns: [{
                field: "id",
                title: "#",
                responsive: {hidden: 'xl'},
            }, {
				field: "startDate",
				title: "Дата"
			},{
                field: "interestRateValue",
                title: "Процентная ставка",
                template: function (row) {
                    return (row.interestRateValue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "ratePeriodName",
                title: "Период начисления процентов"
            }, {
                field: "floatingRateTypeName",
                title: "Плавающая ставка"
            }, {
                field: "penaltyOnPrincipleOverdueRateValue",
                title: "Штраф по осн.с.",
                template: function (row) {
                    return (row.penaltyOnPrincipleOverdueRateValue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "penaltyOnPrincipleOverdueRateTypeName",
                title: "Ставка на штраф по осн.с."
            }, {
                field: "penaltyOnInterestOverdueRateValue",
                title: "Штраф по процентам",
                template: function (row) {
                    return (row.penaltyOnInterestOverdueRateValue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "penaltyOnInterestOverdueRateTypeName",
                title: "Ставка на штраф по проц."
            }, {
                field: "penaltyLimitPercent",
                title: "Лимит начисления штр.",
                template: function (row) {
                    return (row.penaltyLimitPercent).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "penaltyLimitEndDate",
                title: "Дата приост. нач.штр."
            }, {
                field: "transactionOrderName",
                title: "Очередь погашения"
            }, {
                field: "daysInMonthMethodName",
                title: "Метод кол-ва дней в мес."
            }, {
                field: "daysInYearMethodName",
                title: "Метод кол-ва дней в год"
            }, {
                field: "action",
                width: 110,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';

                    result = result + '\
						<a href="/manage/debtor/'+ debtorId + '/loan/'+ loanId + '/term/' + row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
							<i class="la la-edit"></i>\
						</a>\
						<a href="/manage/debtor/'+ debtorId + '/loan/'+ loanId + '/term/' + row.id +'/delete" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Удалить">\
							<i class="la la-trash"></i>\
						</a>\
					';

                    if(!hasRoleAdmin)
                        result = '';

                    return result;
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

		$('#m_form_search1').on('keyup', function (e) {
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
    DatatableDataLocalTerms.init();
});