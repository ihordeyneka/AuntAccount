var express = require('express');
var path = require('path');
var minimist = require('minimist');
var mock_router = require('./mock_router')
var bodyParser = require('body-parser');

// Express App
var app = express();
var args = minimist(process.argv.slice(2));

var PORT = args.port;
var DIST_DIR = path.join(__dirname, '..', 'dist');

app.use(bodyParser.json()); // for parsing application/json
app.use(bodyParser.urlencoded({ extended: true })); // for parsing application/x-www-form-urlencoded
//app.use(multer()); // for parsing multipart/form-data

// Send static files from client directory
app.use('/client', express.static(DIST_DIR + '/client'));
app.use('/lib', express.static(DIST_DIR + '/lib'));

var router = express.Router();

// Send home url to index.html.
router.get('/', function(req, res) {
  res.sendFile(DIST_DIR + '/client/index.html');
});

router.get('/authcode*', function(req, res) {
  res.sendFile(DIST_DIR + '/client/authcode.html');
});

mock_router.init(router);

// Send any other urls notfound.html.
router.get('*', function(req,res) {
  res.statusCode = 404;
  res.sendFile(DIST_DIR + '/client/notfound.html');
});

app.use('/', router);

app.listen(PORT, function() {
  console.log('Server started at http://localhost:' + PORT);
});
