<!doctype html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>巢湖人的比鸡</title>

<link rel="stylesheet" type="text/css" href="css/normalize.css" />
<link rel="stylesheet" type="text/css" href="css/default.css">
<link rel="stylesheet" type="text/css" href="css/sandao.css">
<link rel="stylesheet" type="text/css" href="css/hall.css">
<link href="css/example.css" rel="stylesheet">

</head>
<body>

	<input id="people_number" placeholder="输入人数" type="text" />

	<p class="pity open-room">
		开房
	</p>
	
	<input id="room_number" placeholder="输入房间号" type="text" />
	<p class="pity join-room">
		加入房间
	</p>
	<p class="pity history">
		我的对战记录
	</p>
	
	

<div class="table-back">

	
	<p class="pity close" style="position:fixed">
		关闭
	</p>

</div>


<!--script src="dist/deck.js"></script>
<script src="js/example.js"></script-->

</body>
</html>

<script src="js/jquery.min.js"></script>
<!--script src="js/test.js"></script-->
<script>
var XY = {
	history:[]
}

$(".open-room").click(createRoom);
$(".join-room").click(joinRoom);
$(".history").click(history);
$(".close").click(closeTable);


function closeTable(){
	$("table").remove();
	$(".table-back").hide();
}

function createRoom() {  
//向服务器发送请求 
	var people_number = document.getElementById("people_number").value;
	var obj = ajaxGet('create-room?people_number='+people_number);
	if(obj.status === 'succeed'){
		window.location.href = 'page1'; 
	}else{
		alert(obj.message);
	}
}
function joinRoom() {  
//向服务器发送请求  
	var room_number = document.getElementById("room_number").value;	
	var obj = ajaxGet('join-room?room_number='+room_number);
	if(obj.status === 'succeed'){
		window.location.href = 'page1'; 
	}else{
		alert(obj.message);
	}
}

function history(){
	var obj = ajaxGet('history');
	if(obj.status != 'succeed'){
		alert(obj.message);
	}else{
		handleHistory(obj.data);
	}
}

function handleHistory(data){
	XY.history = data;
	var str = '';
	for(var i=0;i<data.length;i++){
		var lastMes = data[i].allMes[data[i].allMes.length-1];
		str += '<table class="history-table" id="'+i+'"><tr><th>'+(lastMes[0]?lastMes[0].person_id:'')+'</th><th>'+(lastMes[1]?lastMes[1].person_id:'')+'</th><th>'+(lastMes[2]?lastMes[2].person_id:'')+'</th><th>'+(lastMes[3]?lastMes[3].person_id:'')+'</th><th>'+(lastMes[4]?lastMes[4].person_id:'')+'</th><th>时间</th></tr>';
		str += '<tr><td>'+tableView(lastMes[0])+'</td><td>'+tableView(lastMes[1])+'</td><td>'+tableView(lastMes[2])+'</td><td>'+tableView(lastMes[3])+'</td><td>'+tableView(lastMes[4])+'</td><td><span style="font-size:12px">'+(data[i].dateStr?data[i].dateStr:'0000-00-00 00:00:00')+'</span></td></tr>'
		str += '</table>';
	}
	
	$('.table-back').append($(str));
	$(".table-back").show();
}

function tableView(mes){
	if(mes){
		return mes.total_left>0?('<span style="color:red">'+('+'+mes.total_left)+'</span>'): mes.total_left===0?('<span>'+mes.total_left+'</span>'):('<span style="color:green">'+mes.total_left+'</span>');
	}
	return '';
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