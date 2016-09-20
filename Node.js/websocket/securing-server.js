/**
 * Created by mazhuang on 2016/9/19.
 */

var express = require('express');
var ws = require('ws');
var fs = require('fs');
var https = require('https');

var options = {
    key : fs.readFileSync('certs/server.key'),
    cert: fs.readFileSync('certs/server.crt')
};

var app = express();

$port = 443;

var server = https.createServer(options, app).listen($port, function() {
    console.log('wss server listening %d', $port);
});

var wss = new ws.Server({
    server : server
});

wss.on('connection', function(ws) {
    console.log('received a connection');

    ws.on('message', function(message) {
        console.log('received: %s', message);
    });

    ws.send('hi, client');
});