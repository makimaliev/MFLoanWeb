//== Class definition

var DatatableDataLocalEntityDocuments = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonEntityDocuments);

		var datatable = $('#entityDocumentsTable').mDatatable({
			// datasource definition
			data: {
				type: 'local',
				source: dataJSONArray,
				pageSize: 10
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

			pagination: false,

			// inline and bactch editing(cooming soon)
			// editable: false,

			// columns definition
			columns: [{
                field: "id",
                title: "#",
                sortable: 'desc',
                responsive: {hidden: 'xl'},
            }, {
                field: "View",
                width: 80,
                textAlign: 'center',
                title: "Просмотр",
                sortable: false,
                selector: false,
                overflow: 'visible',
                template: function (row) {
                    return '\
						<a href="/manage/order/'+ orderId + '/entitylist/' + listId +'/entity/' + entityId +'/documentpackage/'+ dpId + '/entitydocument/'+  row.id + '/view" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="View "><i class="la la-arrow-circle-o-right"></i></a>\
					';
                }
            }, {
				field: "docName",
				title: "Наименование",
                width: 200,
				responsive: {visible: 'lg'}
			},{
                field: "statusName",
                title: "Статус"
            },{
                field: "progress",
                title: "Прогресс",
                template: function (row) {
                    return  row.progress + ' %';
                }
            },{
                field: "action",
                width: 150,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';

                    if (row.statusId == 1) {
                        result = result + '\
                        <a href="#" data-toggle="modal" data-target="#m_modal_5" data-whatever="completed" data-doc="'+row.id+'" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Завершить коплектацию">\
							<i class="la la-check-square"></i>\
						</a>\
						';
                    } else if (row.statusId == 3) {
                        result = result + '\
                        <a href="#" data-toggle="modal" data-target="#m_modal_5" data-whatever="approved" data-doc="'+row.id+'" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Завершить проверку">\
							<i class="la la-leaf"></i>\
						</a>\
                        ';
                    }

                    /*
                    else if (row.statusId == 5){
                        result = result + '\
                        <a href="#" data-toggle="modal" data-target="#m_modal_5" data-whatever="registered" data-doc="'+row.id+'" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Завершить регистрация">\
							<i class="la la-print"></i>\
						</a>\
						';
                    }
                    */

                    result = result + '\
                        <a href="/manage/order/'+ orderId + '/entitylist/' + listId +'/entity/'+ entityId +'/documentpackage/' + dpId + '/entitydocument/' +row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
							<i class="la la-edit"></i>\
						</a>\
						<a hidden="hidden" href="/manage/order/'+ orderId + '/entitylist/' + listId +'/entity/'+ entityId +'/documentpackage/' + dpId + '/entitydocument/' +row.id +'/delete" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Удалить">\
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
    DatatableDataLocalEntityDocuments.init();
});