/**
 * Created by Kris on 2015-11-26.
 */


console.log("napisz cos");


var http = require("http");

http.createServer(function (request, response) {
    response.writeHead(200, {'Content-Type': 'text/html'});
    var fs = require("fs");

    fs.readFile('1.html', function (err, data) {
        if (err) return console.error(err);
        response.end(data.toString());
        console.log("udalo sie");
    });

}).listen(1337, '127.0.0.1');

// Console will print the message
console.log('Server running at http://127.0.0.1:1337/');