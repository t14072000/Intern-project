updateDiscountCode = () => {
  var form = $('#content-form')[0];
  var formData = new FormData(form);
  $.ajax({
    url: '../update-discount-code',
    type: 'POST',
    enctype: 'multipart/form-data',
    processData: false,
    contentType: false,
    cache: false,
    data: formData,
    success: function(result) {
      if (result != null) {
        if (result === "SUCCESS") {
          alert("Update success.");
          location.reload();
        } else {
          alert("Update fail! Try again later.");
        }
      }
    },
    fail: function(xhr, textStatus, errorThrown) {
      alert('Request failed! Update fail! Try again later.');
    }
  })
}
