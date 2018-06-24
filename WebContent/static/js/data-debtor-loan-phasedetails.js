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
            field: "loanId",
            title: "#",
            sortable: false, // disable sort for this column
            width: 50,
            textAlign: 'center'
        },{
            field: "loanRegNumber",
            sortable: 'asc',
            title: "Регистрационный номер",
            responsive: {visible: 'lg'}
        }, {
            field: "startPrincipal",
            title: "По осн.с.",
            template: function (row) {
                return '\
						<input type="text" class="form-control m-input" id="startPrinc'+row.loanId+'" value="'+ row.startPrincipal + '">\
					';
            }
        },{
            field: "startInterest",
            title: "По проц.",
            template: function (row) {
                return '\
						<input type="text" class="form-control m-input" id="startInt'+row.loanId+'" value="'+ row.startInterest +'">\
					';
            }
        },{
            field: "startPenalty",
            title: "По штр.",
            template: function (row) {
                return '\
						<input type="text" class="form-control m-input" id="startPen'+row.loanId+'" value="'+ row.startPenalty +'">\
					';
            }
        },{
            field: "startTotalAmount",
            title: "Итого",
            width: 160,
            template: function (row) {
                return '\
						<input type="text" class="form-control m-input" id="total'+row.loanId+'" value="'+ row.startTotalAmount +'" disabled>\
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

    $('#initPhaseSaveBtn')
        .on('click', function () {
            var initDate = $("#phaseInitDate").val();
            var phaseDetailsModels = [];
            for (var i = 0; i < dataJSONArray.length; i++) {
                var obj = dataJSONArray[i];
                $.map(obj, function(val, key) {
                    if(key=='loanId')
                    {
                        var PhaseDetailsModel = {};
                        var startPrincInput ="#startPrinc" + val;
                        var startIntInput ="#startInt" + val;
                        var startPenInput ="#startPen" + val;
                        var totalInput = "#total" + val;

                        PhaseDetailsModel['loanId'] = val;
                        PhaseDetailsModel['startPrincipal'] = parseFloat($(startPrincInput).val());
                        PhaseDetailsModel['startInterest'] = parseFloat($(startIntInput).val());
                        PhaseDetailsModel['startPenalty'] = parseFloat($(startPenInput).val());
                        PhaseDetailsModel['startTotalAmount'] = parseFloat($(totalInput).val());
                        PhaseDetailsModel['initDate'] = initDate;
                        phaseDetailsModels.push(PhaseDetailsModel);
                    }
                });
            }
            console.log(phaseDetailsModels);
            console.log(initDate);
            phaseDetailsModels = JSON.stringify({
                'phaseDetailsModels' : phaseDetailsModels
            });
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type : 'POST',
                url : "/manage/debtor/"+debtorId+"/initializephasesave",
                data: phaseDetailsModels,
                dataType: 'json',
                success:function (data) {
                    if(data == 'OK')
                        window.location.replace("/manage/debtor/"+debtorId+"/view");
                }
            });
        });

    for (var i = 0; i < dataJSONArray.length; i++) {
        var obj = dataJSONArray[i];
        $.map(obj, function(val, key) {
            if(key=='loanId')
            {
                console.log(val);
                var startPrincInput ="#startPrinc" + val;
                var startIntInput ="#startInt" + val;
                var startPenInput ="#startPen" + val;
                var totalInput = "#total" + val;

                $(startPrincInput).on('input', function () {
                    updateTotal();
                });

                $(startIntInput).on('input', function () {
                    updateTotal();
                });

                $(startPenInput).on('input', function () {
                    updateTotal();
                });

                function updateTotal() {
                    var sum = 0.0;
                    sum = parseFloat($(startPrincInput).val()) + parseFloat($(startIntInput).val()) + parseFloat($(startPenInput).val());
                    $(totalInput).val(sum);
                }
            }
        });
    }

    /*
    $('#m_form_type').on('change', function () {
        datatable.search($(this).val(), 'Type');
    }).val(typeof query.Type !== 'undefined' ? query.Type : '');

    $('#m_form_status, #m_form_type').selectpicker();
    */

};