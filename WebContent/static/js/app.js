$(document).ready(function() {
    changePageAndSize();
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function(evt) {
        var currentLocation = window.location.pathname;
        window.location.replace(currentLocation + "?pageSize=" + this.value + "&page=1");
    });
}