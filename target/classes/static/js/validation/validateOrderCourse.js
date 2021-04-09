
$().ready(function () {
    $("#form-course").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: true,
        rules: {
            "txtName": {
                required: true,
                maxlength: 50,
            },
            "txtPhone": {
              required: true,
              digits: true,
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
          "txtPhone": {
              required: "Phone không được để trống",
              digits: "Chỉ nhập số",
          },
          "txtEmail": {
              required: " Email không được để trống",
              maxlength: " Email tối đa 50 kí tự",
          },
        },
        submitHandler: orderCourse
    });
});
