//== Class definition

var DatatableDataLocalPhaseDetails = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonPhaseDetails);

		var datatable = $('#phaseDetailsTable').mDatatable({
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
                field: "startPrincipal",
                title: "Основная сумма",
                template: function (row) {
                    return (row.startPrincipal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "startInterest",
                title: "Проценты",
                template: function (row) {
                    return (row.startInterest).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "startPenalty",
                title: "Штрафы",
                template: function (row) {
                    return (row.startPenalty).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "startTotalAmount",
                title: "Итого",
                template: function (row) {
                    return (row.startTotalAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
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
    DatatableDataLocalPhaseDetails.init();
});