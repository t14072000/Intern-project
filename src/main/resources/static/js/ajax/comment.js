let editor;
ClassicEditor.create(document.querySelector('.editor'), {
    toolbar: {
      items: [
        'bold',
        'italic',
        '|',
        'bulletedList',
        'numberedList',
        '|',
        'codeBlock',
        '|',
      ]
    },
    language: 'vi',
    licenseKey: '',
  })
  .then(editorData => {
    editor = editorData;
  })
  .catch(error => {
    console.error('Oops, something went wrong!');
    console.error('Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:');
    console.warn('Build id: roljc925tgl-dp1olaxfjrgr');
    console.error(error);
  });



postComment = () => {
  const editorData = editor.getData();
  var form = $('#comment-form')[0];
  var formData = new FormData(form);
  formData.append("editor", editorData);
  if(editorData.trim() === ''){
    alert('Vui lòng nhập nội dung bình luận!');
  }
  else{
    $.ajax({
      url: 'post-comment',
      type: 'POST',
      processData: false,
      contentType: false,
      cache: false,
      data: formData,
      success: function(result) {
        if (result != null) {
          if (result === "SUCCESS") {
            location.reload();
          } else if (result === "Post not existed") {
            alert("Post đã bị xóa hoặc không tồn tại");
            location.reload();
          } else {
            alert("Comment fail! Try again later.");
          }
        }
      },
      fail: function(xhr, textStatus, errorThrown) {
        alert('Request failed! Comment fail! Try again later.');
      }
    })
  }
}
