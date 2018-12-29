var express = require('express');
var mysql = require('mysql');
var router = express.Router();

var connection = mysql.createConnection({
	host : 'localhost',
	user : 'root',
	password : 'LENflo432',
	database : 'projekt'
});

connection.connect();

/* GET home page. */
router.get('/', function(req, res) {
		connection.query('select * from inar;',  function(err, res1){
  		res.render('index',{title: "Cogdziejak.pl", inar: res1});
	});
});


router.get('/muzyka', function(req, res) {
  connection.query('select * from articles where tag="muzyka";',  function(err, result1){
  		res.render('muzyka',{title: "Cogdziejak.pl", articles:result1});
	});
});

router.get('/sport', function(req, res) {
  connection.query('select * from articles where tag="sport";',  function(err, result1){
  		res.render('sport',{title: "Cogdziejak.pl", articles:result1});
	});
});

router.get('/rodzina', function(req, res) {
  connection.query('select * from articles where tag="rodzina";',  function(err, result1){
  		res.render('rodzina',{title: "Cogdziejak.pl", articles:result1});
	});
});

router.get('/festivale', function(req, res) {
  connection.query('select * from articles where tag="festival";',  function(err, result1){
  		res.render('festivale',{title: "Cogdziejak.pl", articles:result1});
	});
});

router.get('/komentarze', function(req, res) {
	connection.query('select * from komentarze;',  function(err, result1){
  		res.render('komentarze',{title: "Cogdziejak.pl", komentarze: result1});
	});
});

router.post('/nowy', function(req,res){
	var nick=req.body.nick, email=req.body.email, komentarz=req.body.komentarz;
	var comment = {
		nick: nick,
		email: email,
		komentarz: komentarz
	};
	connection.query('INSERT INTO komentarze SET ?', comment, function(err, result) {
		if (err) {
	  		res.send("Błąd dodawania do bazy danych!");
		} else {
	  		res.redirect('/komentarze');
		}
	});
});


module.exports = router;
