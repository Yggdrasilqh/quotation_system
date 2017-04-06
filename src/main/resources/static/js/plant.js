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
    var flag = false;
    if (!$('.setting').eq(0).is(':hidden')) {
        flag = true;
    }
    for (var loop = 0; loop < info.length; loop++) {
        tbody += "<tr><td hidden id=" + info[loop].id + ">" + info[loop].id + "</td>" +
            "<td class='my-td plant-name" + loop + "'>" + info[loop].name + "</td>" +
            "<td class='my-td plant-type" + loop + "'>" + info[loop].type + "</td>" +
            "<td class='my-td plant-price" + loop + "'>" + info[loop].price + "</td><td>";
        if (info[loop].image !== null) {
            tbody += "<img id='new_img" + loop + "' onclick='img" + loop + ".click()' src='plant/image?id=" + info[loop].id + "'style='height: 200px;'/>";
            tbody += '<input id="img' + loop + '" class="img" name="image" type="file" style="display:none" required/>'
        } else {
            tbody += "<a onclick=''>"
        }
        tbody += "</td>" + "<td class='my-td setting' hidden><a class='templatemo-edit-btn' href='' onclick='remove(" + info[loop].id + ")'>删除</a></td>" +
            "</tr>";
    }
    plant_table.append(tbody);
    if (flag)
        $('.setting').show();
    $.fn.editable.defaults.mode = 'inline';

    for (var loop = 0; loop < info.length; loop++) {

        $(".plant-name" + loop).editable({
            type: 'text',
            name: "name",
            pk: info[loop].id,
            url: "/plant/update"
        });

        $(".plant-type" + loop).editable({
            type: 'select',
            source: '/plantType/getSelect',
            showbuttons: false,
            sourceCache: false,
            name: "type",
            pk: info[loop].id,
            url: "/plant/update"
        });
        $(".plant-price" + loop).editable({
            type: 'number',
            step: 1,
            emptytext: '请输入成本价',
            validate: function (data) {
                if (data < 0) {
                    return "成本价不能小于0";
                }
            },
            name: "price",
            pk: info[loop].id,
            url: "/plant/update"
        });

        $("#img" + loop).change(function (e) {

            var this_img = $(this);
            for (var i = 0; i < e.target.files.length; i++) {
                var file = e.target.files.item(i);
                var freader = new FileReader();
                freader.readAsDataURL(file);
                freader.onload = function (e) {
                    var src = e.target.result;
                    this_img.prev().attr("src", src);
                    saveImage(this_img.prev());
                    // $("#preview-img").slideDown();
                }
            }
            return e;
        });


    }
}

function saveImage(image) {
    var id = image.parent().parent().children().eq(0).attr("id");
    var image = image[0].src.split("base64,")[1];
    var name = "image";
    $.ajax({
        type:"post",
        url:"/plant/update",
        dataType:"json",
        async:false,
        data:{"pk":id,"image":image,"name":name},
        success:function (data) {
            alert(data);
        }
    })
}

function remove(id) {
    $.ajax({
        url: '/plant/delete',
        async: false,
        data: {"id": id},
        success: function (data) {
            if (data === "success")
                alert("删除成功");
            else
                alert("删除失败了\n如未解决请联系:yggdrasilqh@gmail.com");
        },
        error: function () {
            alert("删除失败了\n如未解决请联系:yggdrasilqh@gmail.com");
        }
    });
    showInfo();
    $('.setting').show();
    return false;
}


$().ready(function () {
    $("#addImage").change(function (e) {
        for (var i = 0; i < e.target.files.length; i++) {
            var file = e.target.files.item(i);
            var freader = new FileReader();
            freader.readAsDataURL(file);
            freader.onload = function (e) {
                var src = e.target.result;
                $("#preview-img").slideDown();
                $("#upLoadHead").attr("src", src);

            }
        }
        return e;
    })
});

function display(sth) {
    $(sth).slideDown(
        function () {
            $(sth).show();
        }
    );
    return false;
}

function hide(sth) {
    $(sth).slideUp(
        function () {
            $(sth).hide();
            $("#preview-img").hide();
        }
    );
    return false;
}

function enableSetting(text) {
    if ($(".setting").is(":hidden")) {
        $(".setting").fadeIn(400);
        text.text = "关闭操作";
    }
    else {
        $(".setting").fadeOut(400);
        text.text = "显示操作";
    }
}