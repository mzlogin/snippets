var express = require('express');
var cheerio = require('cheerio');
var superagent = require('superagent');

var app = express();

app.get('/', function(req, res, next) {
    superagent.get('https://cnodejs.org')
        .end(function(err, sres) {
            if (err) {
                return next(err);
            }
            var $ = cheerio.load(sres.text);
            var items = [];
            $('#topic_list .cell').each(function(idx, element) {
                var $element = $(element);
                items.push({
                    title: $element.find('a.topic_title').attr('title'),
                    href: $element.find('a.topic_title').attr('href'),
                    author: $element.find('.user_avatar img').attr('title')
                });
            });
            res.send(items);
        });
});

app.listen(3000, function() {
    console.log('app is listening at port 3000');
});
