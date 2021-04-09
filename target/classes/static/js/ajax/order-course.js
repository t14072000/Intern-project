orderCourse = () => {
  var form = $('#form-course')[0];
  var formData = new FormData(form);
  $.ajax({
    url: 'order-course',
    type: 'POST',
    processData: false,
    contentType: false,
    cache: false,
    data: formData,
    success: function(result) {
      if (result != null) {
        if (result === "SUCCESS") {
          alert("Đăng ký thành công");
          location.reload();
        } else if (result === "Address is empty") {
          alert("Vui lòng nhập address");
        } else {
          alert("Order fail! Try again later.");
        }
      }
    },
    fail: function(xhr, textStatus, errorThrown) {
      alert('Request failed! Order fail! Try again later.');
    }
  })
}
