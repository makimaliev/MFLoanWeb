//== Class definition

var DatatableDataLocalPayments = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonPayments);

		var datatable = $('#paymentsTable').mDatatable({
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
			sortable: true,

			// column based filtering(refer to Kendo UI)
			filterable: false,

			pagination: true,

			// inline and bactch editing(cooming soon)
			// editable: false,

			// columns definition
			columns: [{
                field: "id",
                title: "#",
                template: function (row) {
                    return '\
						<a href="/manage/debtor/'+debtorId+'/loan/'+loanId+'/payment/'+row.id+'/view" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="View "><i class="la la-arrow-circle-o-right"></i></a>\
					';
                }
            }, {
				field: "paymentDate",
				title: "Дата",
                width: 70
			}, {
                field: "number",
                title: "Номер",
                width: 70
            }, {
                field: "totalAmount",
                title: "Итого",
                width: 80,
                template: function (row) {
                    return (row.totalAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "principal",
                title: "Осн.сумма",
                template: function (row) {
                    return (row.principal).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "interest",
                title: "Проценты",
                template: function (row) {
                    return (row.interest).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "penalty",
                title: "Штрафы",
                template: function (row) {
                    return (row.penalty).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, /*{
                field: "fee",
                title: "Комиссия",
                template: function (row) {
                    return (row.fee).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, */{
                field: "paymentTypeName",
                title: "Вид платежа",
                width: 200
            }, {
                field: "record_status",
                title: "Статус",
                width: 100,
                template: function (row) {

                    if(row.record_status==2){
                        return '<span class="m-badge m-badge--danger m-badge--wide"> Отменен</span>';
                    }
                    else{
                        return '<span class="m-badge m-badge--success m-badge--wide">Действует</span>';
                    }
                }
            }, {
                field: "action",
                width: 110,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';

                    if(isEmptyChild==true){
                        if(loggedInUserId==supervisorId || loggedInUserId==1){
                        result = result + '\
                            <a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_PAYMENT)" href="/manage/debtor/'+ debtorId + '/loan/'+ loanId + '/payment/' + row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
                                <i class="la la-edit"></i>\
                            </a>\
                            <a hidden="hidden" href="/manage/debtor/'+ debtorId + '/loan/'+ loanId + '/payment/' + row.id +'/delete" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Удалить">\
                                <i class="la la-trash"></i>\
                            </a>\
                        ';
                        }
                    }

                    // if(!hasRoleAdmin)
                    //     result = '';

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

		datatable.sort("counter","asc");
		var query = datatable.getDataSourceQuery();

		$('#m_form_search3').on('keyup', function (e) {
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
    DatatableDataLocalPayments.init();
});