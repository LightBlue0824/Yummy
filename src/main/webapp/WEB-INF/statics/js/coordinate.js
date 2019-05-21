var canvas = document.getElementById('cvs'),
    context = canvas.getContext('2d')
var address={
    x:0,
    y:0
}
function drawGrid(stepx , stepy){//绘制网格
    context.strokeStyle = "#7f7f7f";
    context.lineWidth = 0.5 ;

    for(var i = stepx + 20.5 ; i < context.canvas.width ; i += stepx){
        context.beginPath();
        context.moveTo(i , 0);
        context.lineTo(i , context.canvas.height-10);
        context.stroke();
    }
    for(var i = stepy + 0.5 ; i < context.canvas.height ; i += stepy){
        context.beginPath();
        context.moveTo(30 , i);
        context.lineTo(context.canvas.width , i);
        context.stroke();
    }

    for(var i=40;i<240;i+=40){
        context.font="12pt Calibri";
        context.fillStyle="black";
        context.fillText(i,i+10,240);
    }

    for(var i=40;i<240;i+=40){
        context.font="12pt Calibri";
        context.fillStyle="black";
        context.fillText(i,0,i+8);
    }
}

drawGrid(20,20);

canvas.addEventListener('click',function (e) {
        var p=getEventPosition(e);
        var circle={
            x:510,
            y:510

         };

        if(p.x>=35&&p.x<=245&&p.y>=15&&p.y<=225) {
            circle.x= p.x
            circle.y= p.y
            clear()
            drawPoint(circle)
        }
        // if(p.x%20<5&&p.x>=35&&p.x<=245) {
        //     circle.x= 20*(Math.floor(p.x/20))
        //     if(p.y%20<5&&p.y>=15&&p.y<=225) {
        //         circle.y= 20*(Math.floor(p.y/20))
        //         clear()
        //         drawPoint(circle)
        //     }
        //     else if(p.y%20>15&&p.y>=15&&p.y<=225) {
        //         circle.y=20*(1+Math.floor(p.y/20))
        //         clear()
        //         drawPoint(circle)
        //     }
        // }
        // else if(p.x%20>15&&p.x>=35&&p.x<=245) {
        //     circle.x=20*(1+Math.floor(p.x/20))
        //     if(p.y%20<5&&p.y>=15&&p.y<=225) {
        //         circle.y= 20*(Math.floor(p.y/20))
        //         clear()
        //         drawPoint(circle)
        //     }
        //     else if(p.y%20>15&&p.y>=15&&p.y<=225) {
        //         circle.y=20*(1+Math.floor(p.y/20))
        //         clear()
        //         drawPoint(circle)
        //     }
        // }

})

function getEventPosition(ev){
    var x, y;
    if (ev.layerX || ev.layerX == 0) {
            x = ev.layerX;
            y = ev.layerY;
    } else if (ev.offsetX || ev.offsetX == 0) { // Opera
        x = ev.offsetX;
        y = ev.offsetY;
    }

    return {x: x, y: y};
}

function clear() {
    canvas.height=canvas.height;
    context.strokeStyle = "#7f7f7f";
    context.lineWidth = 0.5 ;
    drawGrid(20,20);
}

function drawPoint(circle) {
    context.beginPath();
    context.arc(circle.x,circle.y,5,0,Math.PI*2,true);
    context.fillStyle="red";
    context.fill();
    context.font="12pt Calibri";
    context.fillStyle="black";
    var tempx=circle.x-20
    address.x=tempx;
    address.y=circle.y;
    if(tempx<=160)
        context.fillText("("+tempx+","+circle.y+")",circle.x+10,circle.y+5);
    else
        context.fillText("("+tempx+","+circle.y+")",circle.x-80,circle.y+5);

    changeShow(address.x, address.y);
}

function changeShow(x, y) {
    document.getElementsByClassName("address_selected")[0].innerHTML = '('+x+', '+y+')';
    $('#addInput_x').val(x);
    $('#addInput_y').val(y);
}


