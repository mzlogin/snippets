/**
 * Created by mazhuang on 2016/9/18.
 */

var WebScoket = require('ws');
var ws = new WebScoket('ws://127.0.0.1:8080/');

ws.on('open', function () {
    ws.send('hi, server');
});

ws.on('message', function(data, flags) {
    console.log("received: %s", data);
});