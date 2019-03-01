//== Class definition
var Typeahead = function() {

    var states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
            'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
            'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
            'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
            'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
            'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
            'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
            'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
            'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'
        ];

    //== Private functions
    var demo1 = function() {
        var substringMatcher = function(strs) {
            return function findMatches(q, cb) {
                var matches, substringRegex;

                // an array that will be populated with substring matches
                matches = [];

                // regex used to determine if a string contains the substring `q`
                substrRegex = new RegExp(q, 'i');

                // iterate through the pool of strings and for any string that
                // contains the substring `q`, add it to the `matches` array
                $.each(strs, function(i, str) {
                    if (substrRegex.test(str)) {
                        matches.push(str);
                    }
                });

                cb(matches);
            };
        };

        $('#m_typeahead_1, #m_typeahead_1_modal, #m_typeahead_1_validate, #m_typeahead_2_validate, #m_typeahead_3_validate').typeahead({
            hint: true,
            highlight: true,
            minLength: 1
        }, {
            name: 'states',
            source: substringMatcher(states)
        });
    }

    var demo2 = function() {
        // constructs the suggestion engine
        var bloodhound = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            // `states` is an array of state names defined in "The Basics"
            //local: states
            remote:{
                url: '/api/loans/search?q=%QUERY',
                wildcard: '%QUERY'
            }
        });

        $('#loan_typeahead').typeahead({
            hint: true,
            highlight: true,
            minLength: 1
        },
        {
            name: 'loans',
            source: bloodhound
        }); 
    }

    var demo3 = function() {
        // constructs the suggestion engine
        var bloodhound = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            // `states` is an array of state names defined in "The Basics"
            //local: states
            remote:{
                url: '/api/owners/search?q=%QUERY',
                wildcard: '%QUERY'
            }
        });

        $('#owner_typeahead').typeahead({
                hint: true,
                highlight: true,
                minLength: 1
            },
            {
                name: 'owners',
                source: bloodhound
            });
        $('#guarantor_typeahead').typeahead({
                hint: true,
                highlight: true,
                minLength: 1
            },
            {
                name: 'owners',
                source: bloodhound
            });
        $('#organization_typeahead').typeahead({
                hint: true,
                highlight: true,
                minLength: 1
            },
            {
                name: 'owners',
                source: bloodhound
            });
    }

    var demo4 = function() {
        // constructs the suggestion engine
        var bloodhound = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            // `states` is an array of state names defined in "The Basics"
            //local: states
            remote:{
                url: '/api/staffs/search?q=%QUERY',
                wildcard: '%QUERY'
            }
        });

        $('#staff_typeahead').typeahead({
                hint: true,
                highlight: true,
                minLength: 1
            },
            {
                name: 'staffs',
                source: bloodhound
            });
    }

    var demo5 = function() {
        // constructs the suggestion engine
        var bloodhound = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            // `states` is an array of state names defined in "The Basics"
            //local: states
            remote:{
                url: '/api/orders/search?q=%QUERY',
                wildcard: '%QUERY'
            }
        });

        $('#order_typeahead').typeahead({
                hint: true,
                highlight: true,
                minLength: 1
            },
            {
                name: 'orders',
                source: bloodhound
            });
    }

    return {
        // public functions
        init: function() {
            demo1();
            demo2();
            demo3();
            demo4();
            demo5();
        }
    };
}();

jQuery(document).ready(function() {
    Typeahead.init();
});