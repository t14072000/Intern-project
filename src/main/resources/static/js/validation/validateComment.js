
$().ready(function () {
    $("#comment-form").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: true,
        rules: {
            "txtName": {
                required: true,
                maxlength: 50,
            },
            "txtEmail": {
                required: true,
                maxlength: 50,
            },
        },
        messages: {
          "txtName": {
              required: " Name không được để trống",
              maxlength: " Name tối đa 50 kí tự",
          },
          "txtEmail": {
              required: " Email không được để trống",
              maxlength: " Email tối đa 50 kí tự",
          },
        },
        submitHandler: postComment
    });
});
