$('.datepicker').datepicker({
    format: "yyyy-mm-dd",
    // startDate: "new Date()",
    todayBtn: "linked",
    language: "zh-CN"
});

$('.modal .checkbox input:checkbox.limited').change(function () {
    $(this).parents(".checkbox").next().toggle();
    // console.log($(this).prop('checked'));
    //设置时间选择器是否是必须字段
    if($(this).prop('checked')){
        $(this).parents(".checkbox").next().children('input').attr('required', true);
        //设置隐藏的表单input值
        // console.log($(this).parents(".checkbox").children('input').val())
        $(this).parents(".checkbox").children('input').val(1);
    }
    else{
        $(this).parents(".checkbox").next().children('input').attr('required', false);
        // console.log($(this).parents(".checkbox").children('input').val())
        $(this).parents(".checkbox").children('input').val(0);
    }
})

$('.modal .checkbox input:checkbox.discount').change(function () {
    // console.log($(this).parents(".checkbox").next())
    $(this).parents(".checkbox").next().toggle();
    // $('.modal .discount_container').toggle();
    // console.log($(this).prop('checked'));
    //设置折扣价是否是必须字段
    if($(this).prop('checked')){
        $(this).parents(".checkbox").next().children('input').attr('required', true);
        //设置隐藏的表单input值
        // console.log($(this).parents(".checkbox").children('input').val())
        $(this).parents(".checkbox").children('input').val(1);
    }
    else{
        $(this).parents(".checkbox").next().children('input').attr('required', false);
        // console.log($(this).parents(".checkbox").children('input').val())
        $(this).parents(".checkbox").children('input').val(0);
    }
})

function addPackageDish() {
    var selectedDish = $('#selectPackageDish').val();
    console.log(selectedDish)
    if(selectedDish != ""){
        var packageDishNum = $('#packageDishNum').val();
        $('#packageContent').append("<br>"+selectedDish+" "+"×"+packageDishNum);
        $('#btn_addPackage').attr('disabled', false);
        var brief = $('#packageBrief').val();
        $('#packageBrief').val(brief + selectedDish+"×"+packageDishNum+" ")
    }
}