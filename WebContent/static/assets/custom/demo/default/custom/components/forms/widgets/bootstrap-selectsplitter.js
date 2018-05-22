//== Class definition

var BootstrapSelectsplitter = function () {
    
    //== Private functions
    var demos = function () {
        // minimum setup
        $('#m_selectsplitter_1, #m_selectsplitter_2').selectsplitter();
    }

    return {
        // public functions
        init: function() {
            demos(); 
        }
    };
}();

jQuery(document).ready(function() {    
    BootstrapSelectsplitter.init();
});