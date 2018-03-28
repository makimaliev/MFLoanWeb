$(document).ready(function() {
    changePageAndSize();
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function(evt) {
        window.location.replace("/manage/order/list?pageSize=" + this.value + "&page=1");
    });
}