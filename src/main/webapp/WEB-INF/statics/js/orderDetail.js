function loadBalance() {
    $.ajax({
        url: "user/getEBankBalance",
        type: 'get',
        success: function (data) {
            // console.log(data)
            var result = JSON.parse(data);
            $('#balance').text(result.balance);
        }
    })
}