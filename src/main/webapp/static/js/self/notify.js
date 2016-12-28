$(function () {
    var login = $("#isLogin").text();

    var call = function() {
        $.post("/notify").done(function(json){
            if(json.state == "success" && json.data > 0) {
                $("#unreadnotify").text(json.data);
            }
        });
    }

    if(login == '1') {
        call();
        setInterval(call,1000*10);
    }
});
