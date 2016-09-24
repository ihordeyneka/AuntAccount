var express = require('express');
var path = require('path');
var minimist = require('minimist');

// Express App
var app = express();
var args = minimist(process.argv.slice(2), {default: {port: '7000'}});

var PORT = args.port;
var DIST_DIR = path.join(__dirname, '..', 'dist');

// Send static files from client directory
app.use('/client', express.static(DIST_DIR + '/client'));

var router = express.Router();

// Send any other urls to the client app to load.
router.get('*', function(req, res) {
  res.sendFile(DIST_DIR + '/client/index.html');
});

app.use('/', router);

app.listen(PORT, function() {
  console.log('Server started at http://localhost:' + PORT);
});
