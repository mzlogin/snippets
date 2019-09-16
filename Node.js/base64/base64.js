const fs = require('fs');

function readSyncByfs(tips) {
    var response;

    tips = tips || '> ';
    process.stdout.write(tips);
    process.stdin.pause();

    var buf = Buffer.allocUnsafe(10000);
    response = fs.readSync(process.stdin.fd, buf, 0, 10000, 0);
    process.stdin.end();

    response = buf.toString('utf8', 0, response);

    return response.trim();
}

var str = readSyncByfs('请输入要 base64 的字符串：');

var b = new Buffer(str);

console.log(b.toString('base64'));
