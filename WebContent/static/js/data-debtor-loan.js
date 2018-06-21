//== Class definition

var DatatableDataLocalLoans = function () {
	//== Private functions

	// demo initializer
	var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonLoans);

		var datatable = $('#loansTable').mDatatable({
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

			pagination: true,

			// inline and bactch editing(cooming soon)
			// editable: false,

			// columns definition
			columns: [{
                field: "id",
                title: "#",
                sortable: false, // disable sort for this column
                width: 40,
                textAlign: 'center',
                selector: {class: 'm-checkbox--solid m-checkbox--brand'}
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
						<a href="/manage/debtor/'+ debtorId + '/loan/' + row.id +'/view" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="View "><i class="la la-arrow-circle-o-right"></i></a>\
					';
                }
            }, {
				field: "regNumber",
                sortable: 'asc',
				title: "Регистрационный номер",
				responsive: {visible: 'lg'}
			},{
                field: "regDate",
                title: "Дата регистрации"
            }, {
                field: "amount",
                title: "Сумма по договору"
            },{
                field: "currencyName",
                title: "Валюта"
            }, {
                field: "loanTypeName",
                title: "Вид"
            }, {
                field: "loanStateName",
                title: "Статус"
            }, {
                field: "action",
                width: 110,
                title: "",
                sortable: false,
                template: function (row) {
                    return '\
						<a href="/manage/debtor/'+ debtorId + '/loan/' + row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
							<i class="la la-edit"></i>\
						</a>\
						<a href="/manage/debtor/'+ debtorId + '/loan/' + row.id +'/delete" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Удалить">\
							<i class="la la-trash"></i>\
						</a>\
					';
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

        var selectedLoans = {};
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
            })
            .on('m-datatable--on-uncheck m-datatable--on-layout-updated', function (e, args) {
                var str = args.toString().split(',')
                for (var i = 0; i < str.length; i++) {
                    delete selectedLoans[str[i]];
                }
                var count = datatable.setSelectedRecords().getSelectedRecords().length;
                $('#m_datatable_selected_number').html(count);
                if (count === 0) {
                    $('#m_datatable_group_action_form').collapse('hide');
                    $('#phaseDetailsDiv').collapse('hide');
                    selectedItems = [];
                }
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
                    $.ajax({
                        type : 'POST',
                        url : "/manage/debtor/"+debtorId+"/initializephase",
                        data: {selectedLoans:selectedLoans, initDate: initDate},
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