//== Class definition

var DatatableDataLocalPhases = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonPhases);

		var datatable = $('#phasesTable').mDatatable({
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
				//height: 450, // datatable's body's fixed height
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
                overflow: 'visible',
                template: function (row) {
                    return '\
						<a href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id + '/view" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="View "><i class="la la-arrow-circle-o-right"></i></a>\
					';
                }
            }, {
                field: "phaseTypeName",
                title: "Вид",
                template:function (row) {
                    if(row.record_status==2){
                        return '<span class="btn-danger">' + row.phaseTypeName+ '</span>';
                    }
                    else {
                        return row.phaseTypeName;
                    }
                }
            }, {
                field: "startDate",
                title: "Дата фазы"
            }, {
                field: "startTotalAmount",
                title: "Сумма фазы",
                template: function (row) {
                    return parseFloat(row.startTotalAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "phaseStatusName",
                title: "Статус фазы"
            }, {
                field: "closeDate",
                title: "Дата завершения"
            }, {
                field: "closeTotalAmount",
                title: "Сумма завершения",
                template: function (row) {
                    return parseFloat(row.closeTotalAmount).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            }, {
                field: "action",
                width: 110,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';
                    if(hasRoleAdmin){
                        result = result + '\
                                <a sec:authorize="hasAnyAuthority(ADMIN)" href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id +'/department/change" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Изменить отделение">\
                                    <i class="fa fa-building"></i>\
                                </a>\
						    ';
                    }

                    // if(row.phaseTypeId==1){
                    //     if((departmentId==row.departmentId || row.departmentId==0) && departmentId!=0){
                    //         result = result + '\
                    //             <a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_COLLECTIONPHASE)" href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id +'/changeStatus" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Изменить статус">\
                    //                 <i class="la la-neuter"></i>\
                    //             </a>\
                    //             <a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_COLLECTIONPROCEDURE)" href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
                    //                 <i class="la la-edit"></i>\
                    //             </a>\
						//     ';
                    //     }
                    //     if(departmentId==10){
                    //         result = result + '\
                    //             <a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_COLLECTIONPROCEDURE)" href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id +'/groupAndIndex/change" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Изменить индекс и группу">\
                    //                 <i class="fa fa-object-group"></i>\
                    //             </a>\
						//     ';
                    //     }
                    //     result = result + '\
						// <a href="/printoutType/2/objectId/'+ row.id + '/generate" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Претензия">\
						// 	<i class="la la-exclamation-circle"></i>\
						// </a>\
						// <a href="/printoutType/3/objectId/'+ row.id + '/generate" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Доо талап">\
						// 	<i class="la la-exclamation-circle"></i>\
						// </a>\
                    // ';
                    // }
                    // else {
                    //     if((departmentId==row.departmentId || row.departmentId==0) && departmentId!=0){
                    //         result = result + '\
                    //             <a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_COLLECTIONPHASE)" href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id +'/changeStatus" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Изменить статус">\
                    //                 <i class="la la-neuter"></i>\
                    //             </a>\
                    //             <a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_COLLECTIONPHASE)" href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
                    //                 <i class="la la-edit"></i>\
                    //             </a>\
                    //              <a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_COLLECTIONPHASE)" href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id +'/groupAndIndex/change" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Изменить индекс и группу">\
                    //                 <i class="fa fa-object-group"></i>\
                    //             </a>\
                    //         ';
                    //     }
                    //     if(departmentId==10 ){
                    //         result = result + '\
                    //              <a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_COLLECTIONPHASE)" href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id +'/groupAndIndex/change" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Изменить индекс и группу">\
                    //                 <i class="fa fa-object-group"></i>\
                    //             </a>\
                    //         ';
                    //     }
                    //     result = result + '\
						// <a hidden="hidden" href="/manage/debtor/'+ debtorId + '/collectionprocedure/' + procId + '/collectionphase/' + row.id +'/delete" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Удалить">\
						// 	<i class="la la-trash"></i>\
						// </a>\
                    // ';
                    // }


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
    DatatableDataLocalPhases.init();
});