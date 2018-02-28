<!doctype html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>巢湖人的比鸡</title>

<link rel="stylesheet" type="text/css" href="css/normalize.css" />
<link rel="stylesheet" type="text/css" href="css/default.css">
<link rel="stylesheet" type="text/css" href="css/person.css">
<link rel="stylesheet" type="text/css" href="css/sandao.css">
<link rel="stylesheet" type="text/css" href="css/register.css?1">
<link href="css/example.css" rel="stylesheet">

</head>
<body>

	<input id="name" placeholder="输入姓名" type="text" />
	<input id="phone" placeholder="输入手机号" type="text" />
	<input id="password" placeholder="输入密码" type="password" />

	<p id="register" class="pity" onClick="register()">
		注册
	</p>
	<a id="login" class="pity" href="e">
		返回登陆
	</a>
	<!--a id="login" class="pity" onClick="register()">
		注册
	</a-->
</div>


<!--script src="dist/deck.js"></script>
<script src="js/example.js"></script-->

</body>
</html>

<script src="js/jquery.min.js"></script>
<script src="js/deck.js"></script>
<script src="js/bijiDeck.js"></script>
<!--script src="js/test.js"></script-->
<script  type="text/javascript">
function register() {  
//向服务器发送请求  
	var name = document.getElementById("name").value;
	var phone = document.getElementById("phone").value;
	var password = document.getElementById("password").value;
	var obj = ajaxGet('r?phone='+phone+'&name='+name+'&password='+password);
	if(obj === 'succeed'){
		window.location.href = 'hall'; 
	}else{
		alert(obj);
	}
}

function ajaxGet(url){
	var data;
	$.ajax({
		url : url,
		type : "get",
		crossDomain:true,
		async:false,
		contentType: "application/json; charset=utf-8",
		success : function(list){
			data = list;
		},
		error:function (XMLHttpRequest, textStatus, errorThrown) {
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);
		}
	});
	return data;
}

</script>