<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page import="com.todo.jdo.AdminJdo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="bootstrap/css/theme.css">
<link rel="stylesheet" href="bootstrap/css/sweetalert.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Todo</title>
<style>
* {
	margin: 15px;
	padding: 0px;
	font-size: 15px;
	font-family: helvetica, arial, sans-serif;
}

footer, section, header, aside, figure {
	display: block;
}

.button {
	position: relative;
	width: 0px;
	margin-top: 0px;
}
</style>
</head>
<body>
	<script src="bootstrap/js/jquery.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.js"></script>
	<script src="bootstrap/js/sweetalert.min.js"></script>
	<section>
	<form action="#" method="post">

		<div class="container">
			<div class="row">
				<div id="custom-search-input">
					<div class="input-group col-md-12">
						<header>
						<h1>Todo List</h1>
						</header>
						<label for="newitem"></label> <input type="text" id="newitem"
							class=" search-query form-control" placeholder="what to do?"
							autocomplete="off" />
						<div class="button">
							<a href="#save" data-toggle="modal" class="btn btn-danger button"
								style="width: 80px;">save</a>
					
						</div>
						
						<ul id="todolist"></ul>
				
					</div>
				</div>
			</div>
		</div>
	</form>
	</section>
	<div class="modal fade" id="save" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="login_heading">Todo List</h3>
					<div class="modal-body">
					<form id="store" action="/save" method="post" name="data"
							onsubmit="return(false)">
							<div class="modal-body">
								<div>
									<input class="design blue_glow input error" type="text"
										autocomplete="off" required name="username" id="username"
										style="opacity: 0.75;" placeholder="Name" title="Username"
										>
								</div>
								<div style="height: 15px;"></div>
								<div>
								<input type="submit" name="save" id="save"
									class="btn btn-primary design modal-footer login_heading"
									style="width: 85px; text-align: center; height: 44px; margin-top: 14px;"
									onclick="process()" value="save">
									 <a id="login"
									class="btn btn-large btn-success" 
									style="width: 85px; text-align: center; height: 44px;" onclick="login()"; >login</a>
									 <a id="cancel"
									class="btn btn-large btn-danger" data-dismiss="modal"
									style="width: 85px; text-align: center; height: 44px;">cancel</a>
								</div>	
							</div>
					</div>
					</form> 
					
					
				</div>

			</div>
		</div>
	</div>

	<script>
		function process() {

			var username = document.forms["store"]["username"].value;
			var datas = todList;
			var json = JSON.stringify(datas);
			alert(json);
			$.ajax({
				url : "/save",
				type : "POST",
				dataType : "json",
				data : { username,json},
				success : function(data) {
					$('#cancel').click();
				}
			});

		}
		
		function login(){
			var username = document.forms["store"]["username"].value;
			alert(username);
			$.ajax({
				url : "/login",
				type : "POST",
				dataType : "json",
				data : { username },
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Content-Type","application/json");
				},
				success : function(data) {
					console.log(data);
					
					for(var x in data.myArrayList)
					{
						
						console.log(data.myArrayList[x]);
						todo.innerHTML += '<li><input value ="' + data.myArrayList[x] + '"/></li> ' ;
					}
					$('#cancel').click();
				}
			});
			
		}
		var todList = [];
		var todo = document.querySelector('#todolist'), form = document
				.querySelector('form'), field = document
				.querySelector('#newitem');

		form.addEventListener('submit',
				function(ev) {
					var text = field.value;
					if (text !== '') {
						todo.innerHTML += '<li index ="' + text + '">' + text
								+ '</li>';
						todList.push(text);
						field.value = '';
						field.focus();
					}
					ev.preventDefault();
				}, false);

		todo.addEventListener('click', function(ev) {
			var t = ev.target;
			if (t.tagName === 'LI') {
				t.parentNode.removeChild(t);
				todList.pop(text);
			};
			ev.preventDefault();
		}, false);
	</script>
</body>
</html>