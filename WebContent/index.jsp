<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%

	session.invalidate();


%>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/index.css">
<!-- Compiled and minified Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Minified JS library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- Compiled and minified Bootstrap JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
	//Funcion que cambia entre formularios, el de registro y login

	function visualiza_login() {
		document.getElementById('login').style.visibility = 'visible';
		document.getElementById('login').style.display = 'block';
		document.getElementById('registro').style.visibility = 'hidden';
		document.getElementById('registro').style.display = 'none';
	};
	function visualiza_registro() {
		document.getElementById('registro').style.visibility = 'visible';
		document.getElementById('registro').style.display = 'block';

		document.getElementById('login').style.visibility = 'hidden';
		document.getElementById('login').style.display = 'none';
	};
	var check = function() {
		if (document.getElementById('password').value == document
				.getElementById('confirm_password').value) {
			document.getElementById('message').style.color = 'green';
			document.getElementById('message').innerHTML = 'matching';
		} else {
			document.getElementById('message').style.color = 'red';
			document.getElementById('message').innerHTML = 'not matching';
		}
	}
	function __(id) {
		return document.getElementById(id);
	}

	//Validar contrase�a
	function validarContrasena() {
		var pass = __('contrasena').value, pass2 = __('contrasena2').value;
		if (pass != '' && pass2 != '') {
			if (pass != pass2) {
				//si las contraseñas no coinciden
				__('resultado').innerHTML = '<p class="error"><strong>Error: </strong>�Las contrase�as no coinciden!</p>';
			} else {
				//Si todo esta correcto 
				document.getElementById("registro").submit();
			}
		} else {
			//si los campos o uno, este vacio
			__('resultado').innerHTML = '<p class="error"><strong>Error: </strong>�Los campos no deben estar vacios!</p>';
		}
	}

	//enviar formulario con la tecla ENTER
	function enterEnviar(event) {
		if (event.keyCode == 13) {
			validarContrasena()
		}
	}
</script>

<title>SnowMedia Pagina de inicio</title>
</head>
<body>


	<div class="sidenav">
		<div class="login-main-text">
			<h2>
				SnowMedia<br> Pagina de registro
			</h2>
			<p>Introduce tu usuario, o contrase�a para acceder, o registrate</p>


		</div>
		<br> <br> <br>

		<!-- Carrusel de imagenes -->
		<div class="login-main">
			<div id="myCarousel" class="carousel slide" data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
				</ol>


				<div class="carousel-inner">
					<div class="item active">
						<img
							src="https://proyectofinalamm.s3.eu-west-3.amazonaws.com/imagenespag/login/tres.JPG"
							alt="">
					</div>
					<div class="item">
						<img
							src="https://proyectofinalamm.s3.eu-west-3.amazonaws.com/imagenespag/login/video.JPG"
							alt="">
					</div>
					<div class="item">
						<img
							src="https://proyectofinalamm.s3.eu-west-3.amazonaws.com/imagenespag/login/dino.jpg"
							alt="">
					</div>
				</div>


				<a class="left carousel-control" href="#myCarousel"
					data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left"></span> <span
					class="sr-only">Previous</span>
				</a> <a class="right carousel-control" href="#myCarousel"
					data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right"></span> <span
					class="sr-only">Next</span>
				</a>
			</div>
		</div>
	</div>
	<div class="main">

		<br>
		<div class="jumbotron jumbotron-fluid">

			<h1 class="display-4">SnowMedia</h1>
			<p class="lead">Servidor de contenido multimedia en Streaming</p>
		</div>

		<div class="col-md-6 col-sm-12">


			<div class="login-form">

				<!-- LOGIN 1 -->

				<form id="login" style="visibility: visible; display: block;"
					action="controladorproyecto" method="post">
					<div id="login" class="form-group">
						<label>Usuario o correo</label> <input type="text"
							class="form-control" placeholder="Usuario o correo" name="user"
							required>
					</div>
					<div id="login" class="form-group">
						<label>Contrase�a</label> <input type="password"
							class="form-control" placeholder="Contrase�a" name="passwd"
							required>
					</div>
					<input type="hidden" name="action" value="1" />
					<button id="login" type="submit" class="btn btn-black">Login</button>
					<button id="login" type="button" class="btn btn-secondary"
						onclick="visualiza_registro()">Registro</button>
				</form>

				<!-- FORMULARIO DE REGISTRO  -->
				<form id="registro" style="visibility: hidden; display: none;"
					action="controladorproyecto" method="post">
					<div class="form-group">
						<label>Correo electronico*</label> <input type="text"
							class="form-control" placeholder="Correo electronico*"
							name="correo" required>
					</div>
					<div class="form-group">

						<label>Nombre de usuario* </label> <input type="text"
							class="form-control" placeholder="Nombre de usuario*"
							name="nombre" required>
					</div>
					<div class="form-group">
						<label>Contrase�a*</label> <input id="contrasena" type="password"
							class="form-control" placeholder="Contrase�a*" onkeyup="check();"
							name="clave" required>
					</div>
					<div class="form-group">
						<label>Repetir contrase�a</label> <input id="contrasena2"
							type="password" class="form-control"
							placeholder="Repetir contrase�a" onkeyup="check();" required>
					</div>
					<div id="resultado"></div>
					<input type="hidden" name="action" value="2" />
					<button type="button" class="btn btn-black"
						onclick="validarContrasena()">Registrame</button>
					<button type="button" class="btn btn-secondary"
						onclick="visualiza_login()">Ya tengo cuenta</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>