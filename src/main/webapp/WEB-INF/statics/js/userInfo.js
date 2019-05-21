function modifyUserInfo() {
    $('.form_basicInfo .btn_modify').hide();
    $('.form_basicInfo .btn_cancel').show();
    $('.form_basicInfo .btn_submit').show();

    $('.form_basicInfo .input').attr('readonly', false);
}