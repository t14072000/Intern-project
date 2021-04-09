$.validator.addMethod('minStrict', function (value, el, param) {
    return value > param;
});

$().ready(function () {
    $("#content-form").validate({
        onfocusout: false,
        onkeyup: false,
        onclick: true,
        rules: {
            "txtCourseName": {
                required: true,
                maxlength: 100,
            },
            "txtLink": {
                required: true,
            },
            txtPrice: {
                required: true,
                number: true,
                minStrict: 0,
            },
            "txtDiscount": {
                required: true,
                digits: true,
                max: 100,
            },
            txtDiscountPrice: {
                required: true,
                number: true,
                minStrict: 0,
                max: parseInt($('#txtPrice').val()),
            },
            "txtType": {
                required: true,
            },
            "txtCategory": {
                required: true,
            },
            "txtTemplate": {
                required: true,
            }
        },
        messages: {
            "txtCourseName": {
                required: " Course name không được để trống",
                maxlength: "  Course name tối đa 100 kí tự",
            },
            "txtLink": {
                required: " Link không được để trống",
            },
            "txtPrice": {
                required: " Price không được để trống",
                number: " Price phải là số",
                minStrict: " Price phải lớn hơn 0",
            },
            "txtDiscount": {
                required: " Discount không được để trống",
                digits: "  Discount phải là số tự nhiên",
                max: " Discount phải lớn hơn 0 và nhỏ hơn hoặc bằng 100",
            },
            "txtDiscountPrice": {
              required: " Discounted price không được để trống",
              number: " Discounted price phải là số",
              minStrict: " Discounted price phải lớn hơn 0",
              max: " Discounted price phải nhỏ hơn hoặc bằng Price",
            },
            "txtType": {
                required: " Type không được để trống",
            },
            "txtCategory": {
                required: " Category không được để trống",
            },
            "txtTemplate": {
                required: " Template name không được để trống",
            }
        },
        submitHandler: createNewCourse
    });

    $('#txtPrice').change(function() {
        $('#txtDiscountPrice').rules('remove', 'max');
        $('#txtDiscountPrice').rules('add', {
             max: parseInt($('#txtPrice').val())
        });
    });
});
