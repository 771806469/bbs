
var ajax = {};

ajax.sendPost = function(url,data,fn,errorfn){
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("post",url);
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function () {
        if(xmlHttp.readyState == 4) {
            if(xmlHttp.status == 200) {
                var success = xmlHttp.responseText;
                    fn(success);
            } else {
                errorfn();
            }
        }
    };

    console.log(data)
    xmlHttp.send(data);

};