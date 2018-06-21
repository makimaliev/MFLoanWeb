var phaseDetailsTableInit = function (jsonInput) {

    var dataJSONArray = JSON.parse(jsonInput);

    var datatable = $('#phaseDetailsTable').mDatatable({
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
            field: "loan_id",
            title: "#",
            sortable: false, // disable sort for this column
            width: 80,
            textAlign: 'center'
        },{
            field: "startPrincipal",
            title: "startPrincipal"
        }, {
            field: "startInterest",
            title: "startInterest"
        },{
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

};