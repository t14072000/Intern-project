$().ready(function() {
  $("#register-form").validate({
    onfocusout: false,
    onkeyup: false,
    onclick: true,
    rules: {
      "username": {
        required: true,
      },
      "password": {
        required: true,
      },
      "confirmPassword": {
        required: true,
        equalTo: "#password",
      },
    },
    messages: {
      "username": {
        required: " Username không được để trống",
      },
      "password": {
        required: " Password không được để trống",
      },
      "confirmPassword": {
        required: " Confirm password không được để trống",
        equalTo: " Confirm password phải giống password",
      },
    },
    submitHandler: register,
  });
});
