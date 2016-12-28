/**
 * Created by mazhuang on 2016/9/22.
 */

// reference https://github.com/websockets/ws/blob/master/examples%2Fssl.js

(function(){

    "use strict";

    var fs = require('fs');

    // you'll probably load configuration from config
    var cfg = {
        ssl: true,
        port: 443,
        ssl_key: 'certs/server.key',
        ssl_cert: 'certs/server.crt'
    };

    var httpServ = ( cfg.ssl ) ? require('https') : require('http');

    var WebSocketServer   = require('ws').Server;

    var app      = null;

    // dummy request processing
    var processRequest = function( req, res ) {

        res.writeHead(200);
        res.end("All glory to WebSockets!\n");
    };

    if ( cfg.ssl ) {

        app = httpServ.createServer({

            // providing server with  SSL key/cert
            key: fs.readFileSync( cfg.ssl_key ),
            cert: fs.readFileSync( cfg.ssl_cert )

        }, processRequest ).listen( cfg.port, function() {
            console.log('wss server listening %d', cfg.port);
        });

    } else {

        app = httpServ.createServer( processRequest ).listen( cfg.port );
    }

    // passing or reference to web server so WS would knew port and SSL capabilities
    var wss = new WebSocketServer( { server: app } );


    wss.on( 'connection', function ( wsConnect ) {

        console.log( 'received a connection' );

        wsConnect.on( 'message', function ( message ) {

            console.log( message );

        });

    });


}());