register = () => {
  var form = $('#register-form')[0];
  var formData = new FormData(form);
  $.ajax({
    url: 'register',
    type: 'POST',
    processData: false,
    contentType: false,
    cache: false,
    data: formData,
    success: function(result) {
      if (result != null) {
        if (result === "SUCCESS") {
          alert("Register success");
          location.reload();
        } else if (result === "User existed") {
          alert("User đã tồn tại");
        } else {
          alert("Register fail! Try again later.");
        }
      }
    },
    fail: function(xhr, textStatus, errorThrown) {
      alert('Request failed! Register fail! Try again later.');
    }
  })
}
