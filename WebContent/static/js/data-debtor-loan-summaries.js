//== Class definition

var DatatableDataLocalSummaries = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonSummaries);

		var datatable = $('#summariesTable').mDatatable({
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
            },  {
				field: "onDate",
				title: "На дату"
			},/*{
                field: "loanAmount",
                title: "Сумма по договору",
                template: function (row) {
                    return (row.loanAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, */{
                field: "totalDisbursed",
                title: "Всего освоено",
                template: function (row) {
                    return (row.totalDisbursed).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            },{
                field: "totalPaid",
                title: "Всего погашено",
                template: function (row) {
                    return (row.totalPaid).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, /*{
                field: "paidPrincipal",
                title: "Пог. по осн.с.",
                template: function (row) {
                    return (row.paidPrincipal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "paidInterest",
                title: "Пог. по проц.",
                template: function (row) {
                    return (row.paidInterest).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "paidPenalty",
                title: "Пог. по штр.",
                template: function (row) {
                    return (row.paidPenalty).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "paidFee",
                title: "Пог. по комм.",
                template: function (row) {
                    return (row.paidFee).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, */{
                field: "totalOutstanding",
                title: "Всего ост.",
                template: function (row) {
                    return (row.totalOutstanding).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "outstadingPrincipal",
                title: "Ост. по осн.с.",
                template: function (row) {
                    return (row.outstadingPrincipal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "outstadingInterest",
                title: "Ост. по проц.",
                template: function (row) {
                    return (row.outstadingInterest).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "outstadingPenalty",
                title: "Ост. по штр.",
                template: function (row) {
                    return (row.outstadingPenalty).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, /*{
                field: "outstadingFee",
                title: "Ост. по комм.",
                template: function (row) {
                    return (row.outstadingFee).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, */{
                field: "totalOverdue",
                title: "Всего проср.",
                template: function (row) {
                    return (row.totalOverdue).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, /*{
                field: "overduePrincipal",
                title: "Проср. по осн.с.",
                template: function (row) {
                    return (row.overduePrincipal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "overdueInterest",
                title: "Проср. по проц.",
                template: function (row) {
                    return (row.overdueInterest).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "overduePenalty",
                title: "Проср. по штр.",
                template: function (row) {
                    return (row.overduePenalty).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "overdueFee",
                title: "Проср. по комм.",
                template: function (row) {
                    return (row.overdueFee).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, */{
                field: "action",
                width: 110,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';

                    result = result + '\
						<a href="/printoutTemplate/1/objectId/'+ row.id + '/generate" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Реестр погашений">\
							<i class="la la-archive"></i>\
						</a>\
						<a href="/printoutTemplate/4/objectId/'+ row.id + '/generate" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Акт сверки">\
							<i class="la la-file-text"></i>\
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

		$('#m_form_search5').on('keyup', function (e) {
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
    DatatableDataLocalSummaries.init();
});