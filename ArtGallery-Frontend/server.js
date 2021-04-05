// source : https://github.com/McGill-ECSE321-Fall2019/project-group-16/blob/master/tutoringsystem-frontend/server.js
// server.js
var express = require('express');
var path = require('path');
var serveStatic = require('serve-static');
app = express();
app.use(serveStatic(__dirname + "/dist"));
app.route('/*')
    .get(function(req, res) {
          res.sendFile(path.join(__dirname + '/dist/index.html'));
});
module.exports = app;
var port = process.env.PORT || 8087;
app.listen(port);
console.log('server started '+ port);