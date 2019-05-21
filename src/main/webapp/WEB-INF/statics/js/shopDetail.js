
function addDishToCart(shopid, dishid) {
    $.ajax({
        url: "user/addDishToCart",
        type: "post",
        data:{
            shopid: shopid,
            dishid: dishid
        },
        success: function () {
            // console.log("success")
            numOfCart++;
            $('.btn_cart .badge').text(numOfCart);
        }
    })
}