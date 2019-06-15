
$(function () {
    window.fun = function (){
        $.session.remove("user");
        $.session.clear();
    };
});
$(document).ready(function () {$("#warning").hide();});
$(function () {$('[data-toggle="popover"]').popover()});
/**
 * 封装一个方法,可以让回复问题和回复评论同时使用
 */
function commnet(parentId,content,type) {
    if (content==null ||content ==''){
        if (type==1){
            $("#warning").show();
            $("#errormassage").html("请输入回复内容!!!");
            return false;
        } else {
            $("#basic-" + parentId).attr("data-content", "请输入回复内容!!!");
            return false;
        }
    }
    var data= JSON.stringify({"questionId":parentId,"content":content,"type": type});
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json;charset=utf-8",
        data: data,
        success: function (result) {
            if (result.code == 200) {
                window.location.reload();
            }else {
                console.log(result);
                if (type=="1" || type==1){
                    $("#errormassage").html(result.massage+":&nbsp;"+formatDate(result.timeStamp));
                    $("#warning").show();
                    if (result.code == 2000 || result.code ==2001 ){
                        $('#myModal').modal('show');
                    }
                } else {
                    $("#basic-" + parentId).attr("data-content", result.massage);
                }
                return false;
            }
        },
        dataType: "json"
    });
}

/**
 *  方法名: collapseComment
 *  功能: 展示二级回复内容
 * @param timeStamp
 * @returns {string}
 */
function collapseComment(thisComment) {
    console.log(thisComment.id);
    var commentBody=$('#collapseExample-'+thisComment.id);
    // if ($($(commentBody.children("div")).get(0)).has("div").length==0){
    //     $($(commentBody.children("div")).get(0)).html("快来坐第一个沙发吧!!!");
    //     $($(commentBody.children("div")).get(0)).addClass("nullcomment");
    // }
    if (commentBody.hasClass("in")){
        $(function () {commentBody.hide();});
        commentBody.removeClass("in");
        //$($(commentBody.children("div").get(0))).empty();
        return false;
    }
    if ($($(commentBody.children("div")).get(0)).has("div").length!=0){
        $(function () {commentBody.show();});
        commentBody.addClass("in");
        return false;
    }
    $.getJSON( "/comment/"+thisComment.id, function( result ) {
        $.each( result.data, function( index, comment) {
            var imgElement= $("<img/>", {
                "class": "media-object img-rounded myimages",
                "width" :"50px",
                "src": comment.user.avatarUrl,
                "alt":"发布者头像"
            });
            var mediaBodyElement=$("<div/>", {
                "class": "media-body"
            }).append($("<h5/>", {
                "class": "media-heading",
                "html": comment.user.name
            }),$("<div/>", {
                "html": comment.content
            }));
            var bottomlineElement=$("<div/>", {
                "class": "bottomline"
            }).append($("<span/>", {//点赞
                "class": "text-small glyphicon glyphicon-thumbs-up spancursor",
                "html": "赞",
                "aria-hidden": "true"
            }),$("<span/>", {//回复
                "class": "glyphicon glyphicon-comment text-small spancursor",
                "html":"回复",
                "aria-hidden": "true"
            }),$("<span/>", {//更新时间
                "class": "pull-right text-small",
                "html": "回复时间:"+formatDate(comment.gmtModified)
            }));
            var aElement=$("<div/>", {"href":"#"}).append(imgElement);
            var mediaLeftElement=$("<div/>", {"class": "media-left"}).append(aElement);
            var mediaElement=$("<div/>", {"class": "media"}).append(mediaLeftElement,mediaBodyElement.append(bottomlineElement));

            $(commentBody.children("div").get(0)).append(mediaElement);

            commentBody.prepend($(commentBody.children("div").get(0)));
        });
        if ($($(commentBody.children("div")).get(0)).has("div").length==0){
            $($(commentBody.children("div")).get(0)).html("快来坐第一个沙发吧!!!");
            $($(commentBody.children("div")).get(0)).addClass("nullcomment");
        }
        commentBody.addClass("in");
        $(function () {commentBody.show();});
    });

}
//时间格式处理
function formatDate(timeStamp){
    var now=new Date(timeStamp),
        year=now.getFullYear(),
        month=now.getMonth()+1,
        date=now.getDate(),
        hour=now.getHours(),
        min=now.getMinutes(),
        sec=now.getSeconds();
    return year+"年"+fixZeor(month,2)+"月"+fixZeor(date,2)+"日"+fixZeor(hour,2)+":"+fixZeor(min,2)+":"+fixZeor(sec,2)

}
function fixZeor(num,length){
    var str=""+num,
        len=str.length,
        s="";
    for (var i=length;i-->len;) {
        s+="0";
    }
    return s+str;
}