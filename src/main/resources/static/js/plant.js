/**
 * Created by yggdrasil on 2017/4/1.
 */
function getAllInfo() {
    var plants = null;
    $.ajax({
        url: '/plant/allInfo',
        async: false,
        success: function (data) {
            plants = data;
        }
    });
    return plants;
}



function showInfo() {
    var plant_table = $("#plant-table");
    var tbody = "";
    plant_table.empty();
    var info = getAllInfo();
    for(var loop = 0 ; loop < info.length ; loop++){
        tbody += "<tr><td hidden>" + info[loop].id + "</td>"+
            "<td class='my-td'>" + info[loop].name + "</td>" +
            "<td class='my-td'>" + info[loop].type + "</td>" +
            "<td class='my-td'>" + info[loop].price + "</td><td>";
        if(info[loop].image !== null) {
            tbody += "<img src='plant/image?id=" + info[loop].id + "'style='height: 200px;'/>";
        } else {
            tbody += "<a onclick=''>"
        }
        tbody += "</td>" + "<td class='my-td setting' hidden><a onclick='remove("+info[loop].id+")'>删除</a></td>" +
            "</tr>";
    }
    plant_table.append(tbody);
}

function remove(id){
    $.ajax({
        url: '/plant/delete',
        async: false,
        data: {"id":id},
        success: function (data) {
            if(data === "success")
                alert("删除成功");
            else
                alert("删除失败了\n如未解决请联系:yggdrasilqh@gmail.com");
        },
        error: function () {
            alert("删除失败了\n如未解决请联系:yggdrasilqh@gmail.com");
        }
    });
    showInfo();
    return false;
}


$().ready(function(){
   $("#addImage").change(function(e){
       for (var i = 0; i < e.target.files.length; i++) {
           var file = e.target.files.item(i);
           var freader = new FileReader();
           freader.readAsDataURL(file);
           freader.onload = function(e) {
               var src = e.target.result;
               $("#upLoadHead").attr("src",src);
               $("#preview-img").slideDown();
           }
       }
       return e;
   })
});





function display(sth) {
    $(sth).slideDown(
        function(){
            $(sth).show();
        }
    );
    return false;
}

function hide(sth) {
    $(sth).slideUp(
        function(){
            $(sth).hide();
            $("#preview-img").hide();
        }
    );
    return false;
}

function enableSetting(text) {
    if($(".setting").is(":hidden")) {
        $(".setting").fadeIn(400);
        text.text = "关闭操作";
    }
    else {
        $(".setting").fadeOut(400);
        text.text = "显示操作";

    }
}