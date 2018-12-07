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
				source: dataJSONArray
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
				title: "Дата",
                width: 70
			},{
                field: "interestRateValue",
                title: "Проц. ставка",
                width: 300,
                template: function (row) {
                    var result = (row.interestRateValue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");

                    if(row.floatingRateTypeName!= '--')
                        result = result + ' + ' + row.floatingRateTypeName;

                    if(result.length>40){
                        return result.substring(0,40)+"...";
                    }
                    return result;
                }
            }, /*{
                field: "floatingRateTypeName",
                title: "Плавающая ставка"
            },*/ {
                field: "penaltyOnPrincipleOverdueRateValue",
                title: "Штраф по осн.с.",
                width: 300,
                template: function (row) {
                    var result = (row.penaltyOnPrincipleOverdueRateValue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");

                    if(row.penaltyOnPrincipleOverdueRateTypeName!= '--')
                        result = result + ' + ' + row.penaltyOnPrincipleOverdueRateTypeName;
                    if(result.length>40){
                        return result.substring(0,40)+"...";
                    }
                    return result;
                }
            }, /*{
                field: "penaltyOnPrincipleOverdueRateTypeName",
                title: "Ставка на штраф по осн.с."
            }, */{
                field: "penaltyOnInterestOverdueRateValue",
                title: "Штраф по проц.",
                width: 300,
                template: function (row) {
                    var result = (row.penaltyOnInterestOverdueRateValue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");

                    if(row.penaltyOnInterestOverdueRateTypeName!= '--')
                        result = result + ' + ' + row.penaltyOnInterestOverdueRateTypeName;
                    if(result.length>40){
                        return result.substring(0,40)+"...";
                    }
                    return result;

                }
            }, /*{
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
            }, */{
                field: "daysInMonthYearMethodName",
                title: "Метод кол-ва дней в мес./в год",
                width: 250,
                template: function (row) {

                    return row.daysInMonthMethodName + '/' + row.daysInYearMethodName;

                }
            }, {
                field: "action",
                width: 110,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';

                    result = result + '\
						<a sec:authorize="hasAnyRole(ADMIN,PERM_UPDATE_ORDERTERM)" href="/manage/debtor/'+ debtorId + '/loan/'+ loanId + '/term/' + row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
							<i class="la la-edit"></i>\
						</a>\
						<a hidden="hidden" href="/manage/debtor/'+ debtorId + '/loan/'+ loanId + '/term/' + row.id +'/delete" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Удалить">\
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