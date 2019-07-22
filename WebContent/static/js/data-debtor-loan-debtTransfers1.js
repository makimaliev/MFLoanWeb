//== Class definition

var DatatableDataLocalDebtTransfers = function () {
    //== Private functions

    // demo initializer
    var mainTableInit = function () {

        var dataJSONArray = JSON.parse(jsonDTs);

        var datatable = $('#DTsTable').mDatatable({
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
                template: function (row) {
                    return '\
						<a href="/manage/debtor/'+debtorId+'/loan/'+loanId+'/debttransfer/'+row.id+'/view" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="View "><i class="la la-arrow-circle-o-right"></i></a>\
					';
                }
            }, {
                field: "number",
                title: "Номер"
            },{
                field: "date",
                title: "Дата"
            }, /*{
                field: "quantity",
                title: "Кол-во"
            }, {
                field: "pricePerUnit",
                title: "Цена"
            }, {
                field: "unitTypeId",
                title: "Ед. изм."
            }, */{
                field: "totalCost",
                title: "Сумма"
            }, /*{
                field: "transferPaymentId",
                title: "Платеж"
            }, {
                field: "transferCreditId",
                title: "Кредит"
            }, */{
                field: "transferPersonName",
                title: "Должник",
                width:300,
                template: function (row) {
                    var name=row.transferPersonName;
                    if(name.length>40){
                        name=name.substr(0,40)+"...";
                    }
                    return '\
						<a href="/manage/debtor/'+row.transferPersonId+'/view" class="" title="View">'+name+'</a>\
					';
                }
            }, {
                field: "goodsType",
                title: "Товар"
            }, {
                field: "action",
                width: 110,
                title: "",
                sortable: false,
                template: function (row) {

                    var result = '';

                    result = result + '\
						<a sec:authorize="hasAnyAuthority(ADMIN,PERM_UPDATE_DEBTTRANSFER)" href="/manage/debtor/'+ debtorId + '/loan/'+ loanId + '/debttransfer/' + row.id +'/save" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="Редактировать">\
							<i class="la la-edit"></i>\
						</a>\
						<a hidden="hidden" href="/manage/debtor/'+ debtorId + '/loan/'+ loanId + '/debttransfer/' + row.id +'/delete" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill" title="Удалить">\
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

        $('#m_form_search10').on('keyup', function (e) {
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
    DatatableDataLocalDebtTransfers.init();
});