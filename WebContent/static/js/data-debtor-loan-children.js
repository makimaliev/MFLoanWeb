//== Class definition

var DatatableDataLocalChildren = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonChildren);

		var datatable = $('#childTable').mDatatable({
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

			pagination: false,

			// inline and bactch editing(cooming soon)
			// editable: false,

			// columns definition
			columns: [{
                field: "id",
                title: "#",
                responsive: {hidden: 'xl'},
            }, {
                field: "View",
                width: 80,
                textAlign: 'center',
                title: "Просмотр",
                selector: false,
                overflow: 'visible',
                template: function (row) {
                    return '\
						<a href="/manage/debtor/'+ debtorId + '/loan/' + row.id +'/view" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="View "><i class="la la-arrow-circle-o-right"></i></a>\
					';
                }
            }, {
                field: "regNumber",
                width: 450,
                title: "Регистрационный номер",
                template: function (row) {
                    var result = '';

                    if(row.regNumber.length <= 60)
                        result = row.regNumber;
                    else {
                        result = row.regNumber.substr(0, 60);
                        result = result + '...';
                    }

                    return result;
                }
            },{
                field: "regDate",
                title: "Дата регистрации",
                width: 100
            }, {
                field: "amount",
                title: "Сумма по договору",
                width: 120,
                template: function (row) {
                    return (row.amount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            },{
                field: "currencyName",
                title: "Валюта",
                width: 70
            }, {
                field: "loanTypeName",
                title: "Вид",
                width: 180
            }, {
                field: "loanStateName",
                title: "Статус",
                template: function (row) {
                    var status = {
                        1: {'class': ' m-badge--warning'},
                        2: {'class': ' '},
                        3: {'class': ' m-badge--danger'},
                        4: {'class': ' m-badge--success'}
                    };

                    console.log(row.loanStateId)
                    console.log( status[row.loanStateId].class)
                    return '<span class="m-badge ' + status[row.loanStateId].class + ' m-badge--wide">' + row.loanStateName + '</span>';
                }
            }, {
                field: "action",
                width: 110,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';

                    result = result + '\
						<a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_LOAN)" href="/manage/debtor/'+ debtorId + '/loan/' + row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
							<i class="la la-edit"></i>\
						</a>\
						<a hidden="hidden" href="/manage/debtor/'+ debtorId + '/loan/' + row.id +'/delete" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Удалить">\
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
    DatatableDataLocalChildren.init();
});