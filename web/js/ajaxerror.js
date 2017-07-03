/**
 * Created by Administrator on 2017/5/17.
 * ajax请求出错处理
 */
$.ajaxSetup({
    error: function (XMLHttpRequest, textStatus, errorMsg){
        if(XMLHttpRequest.status==403){
            var myErrorValue = XMLHttpRequest.getResponseHeader("myErrorValue");
            if (myErrorValue == "unauthenticated"){
                alert("【权限限制】您没有此功能操作权限");
            }
            return false;
        }
    }
    //,complete:function(XMLHttpRequest,textStatus){ //在请求成功或失败之后均调用，即在 success 和 error 函数之后
    //    var sessionstatus=XMLHttpRequest.getResponseHeader("unauthenticated"); //通过XMLHttpRequest取得响应头
    //    if(sessionstatus=='unauthenticated'){
    //        alert("【权限限制】您没有此功能操作权限");
    //    }
    //}
});