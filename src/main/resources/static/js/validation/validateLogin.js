$().ready(function () {
    $("#login-form").validate({
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
        },
        messages: {
          "username": {
              required: " Username không được để trống",
          },
          "password": {
              required: " Password không được để trống",
          },
        },
    });
});
