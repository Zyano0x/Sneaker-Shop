$(document).ready(function () {
    $('.quantity').change(function () {
        let quantity = $(this).val();
        let id = $(this).data('id');
        $.ajax({
            url: '/cart/updateCart/' + id + '/' + quantity,
            type: 'GET',
            success: function () {
                location.reload();
            },
            error: function (xhr, status, error) {
                console.log(error); // Handle the error gracefully
            }
        });
    });
});