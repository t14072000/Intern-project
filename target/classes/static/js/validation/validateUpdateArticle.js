
$().ready(function () {
    $("#content-form").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: true,
        rules: {
            "txtTitle": {
                required: true,
                maxlength: 100,
            },
            "txtTemplate": {
                required: true,
            }
        },
        messages: {
            "txtTitle": {
                required: " Title không được để trống",
                maxlength: "  Title tối đa 100 kí tự",
            },
            "txtTemplate": {
                required: " Template name không được để trống",
            }
        },
        submitHandler: updatePost
    });
});
