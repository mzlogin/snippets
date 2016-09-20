/**
 * Created by mazhuang on 2016/9/18.
 */

var WebSocketServer = require('ws').Server,
    wss = new WebSocketServer({ port: 8080});

wss.on('connection', function connection(ws) {
    ws.on('message', function incoming(message) {
        console.log('received: %s', message);
    });

    ws.send('hi, client');
});