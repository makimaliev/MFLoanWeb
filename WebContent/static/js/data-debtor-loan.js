//== Class definition

var DatatableDataLocalLoans = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonLoans);

        var selectedLoans = {};

        // console.log(jsonLoans[0].id);
        for (var i = 0; i < dataJSONArray.length; i++) {
            selectedLoans[dataJSONArray[i].id] = dataJSONArray[i].id;
        }
        // console.log(selectedLoans);

		var datatable = $('#loansTable').mDatatable({
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
                width: 40,
                textAlign: 'center',
                selector: {class: 'm-checkbox--solid m-checkbox--brand'}
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
            },{
                field: "action",
                width: 110,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';

                    result = result + '\
						<a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_LOAN)" href="/manage/debtor/'+ debtorId + '/loan/' + row.loan_class_id + '/'+ row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
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
            }, {
				field: "regNumber",
                width: 450,
				title: "Регистрационный номер",
                template: function (row) {
                    var status = {
                        1: {'class': 'btn-warning'},
                        2: {'class': ''},
                        3: {'class': 'btn-danger'},
                        4: {'class': 'btn-metal'}
                    };
                    var result = '';

                    if(row.regNumber.length <= 60){
                        return '<a href="/manage/debtor/'+ debtorId + '/loan/' + row.id +'/view"><span class=" ' + status[row.loanStateId].class + ' ">' + row.regNumber + '</span></a>';
                    }

				    else {
                        return '<a href="/manage/debtor/'+ debtorId + '/loan/' + row.id +'/view"><span class=" ' + status[row.loanStateId].class + ' ">' + row.regNumber+ '</span></a>';
                    }

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
                field: "remainder",
                title: "Остаток",
                width: 120,
                template: function (row) {
                    return (row.remainder).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ').replace(".", ",");
                }
            },{
                field: "currencyName",
                title: "Валюта",
                width: 70
            }, {
                field: "loanTypeName",
                title: "Вид",
                width: 180
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
		// make datatable rows checkbox active
        datatable.setActiveAll('active');

		$('#m_form_search').on('keyup', function (e) {
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

        // on checkbox checked event
        $('#loansTable')
            .on('m-datatable--on-check', function (e, args) {
                var str = args.toString().split(',')
                for (var i = 0; i < str.length; i++) {
                    selectedLoans[str[i]] = str[i];
                }
                var count = datatable.setSelectedRecords().getSelectedRecords().length;
                $('#m_datatable_selected_number').html(count);
                if (count > 0) {
                    $('#m_datatable_group_action_form').collapse('show');
                }
                // console.log(selectedLoans);
            })
            .on('m-datatable--on-uncheck m-datatable--on-layout-updated', function (e, args) {
                var str = args.toString().split(',')
                for (var i = 0; i < str.length; i++) {
                    delete selectedLoans[str[i]];
                }
                var count = datatable.setSelectedRecords().getSelectedRecords().length;
                // $('#m_datatable_selected_number').html(count);
                // if (count === 0) {
                //     $('#m_datatable_group_action_form').collapse('hide');
                //     $('#phaseDetailsDiv').collapse('hide');
                //     selectedItems = [];
                // }
                // console.log(selectedLoans);
            });

        $('#bnt_init_phase')
            .on('click', function () {
                console.log(selectedLoans);
                if (!$("#phaseInitDate").val())
                {
                    var alert = $('#m_form_2_msg');
                    alert.removeClass('m--hide').show();
                    mApp.scrollTo(alert, -200);
                }
                else
                {
                    var initDate = $("#phaseInitDate").val();
                    console.log(debtorId)
                    $.ajax({
                        type : 'POST',
                        url : "/manage/debtor/"+debtorId+"/initializephase",
                        data: {selectedLoans:selectedLoans, initDater: initDate},
                        success:function (data) {
                            phaseDetailsTableInit(data);
                            var divT = $('#phaseDetailsDiv');
                            divT.collapse('show');
                            mApp.scrollTo(divT, -200);
                        }
                    });
                    //$.post( "/manage/debtor/"+debtorId+"/initializephase", );
                }
            });
        // $("#bnt_init_manualCalculator").on('click',function () {
        //     if (!$("#phaseInitDate").val())
        //     {
        //
        //         var alert = $('#m_form_2_msg');
        //         alert.removeClass('m--hide').show();
        //         mApp.scrollTo(alert, -200);
        //     }
        //     else
        //     {
        //         var initDate = $("#phaseInitDate").val();
        //         $.ajax({
        //             type : 'POST',
        //             url : "/manage/debtor/"+debtorId+"/initializeManualCalculator",
        //             data: {selectedLoans:selectedLoans, initDater: initDate}
        //         });
        //     }
        // });
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
    DatatableDataLocalLoans.init();
});