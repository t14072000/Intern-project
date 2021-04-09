createNewDiscountCode = () => {
  var form = $('#content-form')[0];
  var formData = new FormData(form);
  $.ajax({
    url: 'create-discount-code',
    type: 'POST',
    enctype: 'multipart/form-data',
    processData: false,
    contentType: false,
    cache: false,
    data: formData,
    success: function(result) {
      if (result != null) {
        if (result === "SUCCESS") {
          alert("Create success.");
        } else if(result === "Discount code already existed"){
          alert("Discount code already existed.");
        } else {
          alert("Create fail! Try again later.");
        }
      }
    },
    fail: function(xhr, textStatus, errorThrown) {
      alert('Request failed! Create fail! Try again later.');
    }
  })
}
