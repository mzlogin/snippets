<?php
/**
 * Created by IntelliJ IDEA.
 * User: mazhuang
 * Date: 2016/9/21
 * Time: 01:01
 */

require_once(__DIR__."/../vendor/autoload.php");                // Composer autoloader

class Client {

    private $uri;
    private $close_cert_verify;

    public function __construct($uri, $close_cert_verify)
    {
        $this->uri = $uri;
        $this->close_cert_verify = $close_cert_verify;
    }

    public function send($data)
    {
        $loop = \React\EventLoop\Factory::create();

        $logger = new \Zend\Log\Logger();
        $writer = new Zend\Log\Writer\Stream("php://output");
        $logger->addWriter($writer);

        $opts = null;
        if ($this->close_cert_verify) {
            $opts = array(
                'ssl' => array(
                    'verify_peer' => false,
                    'verify_peer_name' => false,
                    'allow_self_signed' => true
                )
            );
        }

        $client = new \Devristo\Phpws\Client\WebSocket($this->uri, $loop, $logger, $opts);

        $client->on("request", function ($headers) use ($logger) {
            $logger->notice("Request object created!");
        });

        $client->on("handshake", function () use ($logger) {
            $logger->notice("Handshake received!");
        });

        $client->on("connect", function ($headers) use ($logger, $client, $data) {
            $logger->notice("Connected!");
            $client->send($data);
            $client->close();
        });

        $client->on("message", function ($message) use ($client, $logger) {
            $logger->notice("Got message: " . $message->getData());
            $client->close();
        });

        $client->open();
        $loop->run();
    }
}

$client = new Client("wss://127.0.0.1:443/", true);
$client->send("Hello, World");