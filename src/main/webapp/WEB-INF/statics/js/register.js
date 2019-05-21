function checkPassword() {
    var pw = $("#password");
    var pw2 = $("#password_sure");

    if(pw.val() == pw2.val()){
        $("#password_sure").parent().removeClass("has-error");
        $("#password_sure").parent().addClass("has-success");
        $(".label_error").hide();
    }
    else{
        $("#password_sure").parent().removeClass("has-success");
        $("#password_sure").parent().addClass("has-error");
        $(".label_error").show();
    }
}

$('#btn_submit').click(function (e) {
    var pw = $("#password");
    var pw2 = $("#password_sure");

    console.log('this11')

    if(pw.val() != pw2.val()){
        $("#password_sure").parent().removeClass("has-success");
        $("#password_sure").parent().addClass("has-error");

        console.log('this')
        e.preventDefault();
    }
});
