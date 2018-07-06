//== Class definition

var DatatableDataLocalEntityPackages = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonEntityPackages);

		var datatable = $('#entityPackagesTable').mDatatable({
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
						<a href="/manage/order/'+ orderId + '/entitylist/' + listId +'/entity/' + entityId +'/documentpackage/'+  row.id + '/view" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="View "><i class="la la-arrow-circle-o-right"></i></a>\
					';
                }
            }, {
				field: "name",
				title: "Наименование",
                width: 200,
				responsive: {visible: 'lg'}
			},{
                field: "completedDate",
                title: "Дата комплектации"
            }, {
                field: "approvedDate",
                title: "Дата подтверждения"
            }, {
                field: "completedRatio",
                title: "Доля комплектации",
                template: function (row) {
                    return  row.completedRatio + ' %';
                }
            }, {
                field: "approvedRatio",
                title: "Доля подтверждения",
                template: function (row) {
                    return  row.approvedRatio + ' %';
                }
            }, {
                field: "registeredRatio",
                title: "Доля регистрации",
                template: function (row) {
                    return  row.registeredRatio + ' %';
                }
            }, {
                field: "stateName",
                title: "Статус"
            }, {
                field: "typeName",
                title: "Вид"
            }, {
                field: "action",
                width: 50,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';

                    result = result + '\
						<a href="/manage/order/'+ orderId + '/entitylist/' + listId +'/entity/'+ entityId +'/documentpackage/' +row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
							<i class="la la-edit"></i>\
						</a>\
						<a href="/manage/order/'+ orderId + '/entitylist/' + listId +'/entity/'+ entityId +'/documentpackage/' +row.id +'/delete" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Удалить">\
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
    DatatableDataLocalEntityPackages.init();
});