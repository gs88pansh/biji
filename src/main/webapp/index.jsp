<!DOCTYPE html>  
<html>  
<head>  
<title>HTML5 WebSocket测试</title>  
</head>  
<body>  







<div class="card spades spades8" style="transition: all 0.01s cubic-bezier(0.215, 0.61, 0.355, 1) 0s;"></div>

<div class="topleft">9</div>
<div class="bottomright">9</div>
<div class="face"></div>

</div>


















    <div>  
	
		
		<input id="people_number" type="text" />
	
		<input type="button" value="createRoom" onclick="createRoom()" />
		
		
		<input id="room_number" type="text" />
		
		<input type="button" value="joinRoom" onclick="joinRoom()" />  
		
		<input type="button" value="prepare" onclick="prepare()" /> 
		
		<input id="pokerIds" type="text" />
		
		<input type="button" value="submit" onclick="submit()" /> 
		
    </div>  
    <div id="messages"></div>  
	<script src="http://code.jquery.com/jquery-3.2.1.js"
			  integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
			  crossorigin="anonymous"></script>
    <script type="text/javascript">  
		var colors = ["<span style='color:red'>♦</span>","♣","<span style='color:red'>♥</span>","♠"];
		var pokeStr = ["2","3","4","5","6","7","8","9","10","J","Q","K","A"]
		
		
        var webSocket = new WebSocket('ws://localhost:8080/biji/webSocketServer');  
        webSocket.onerror = function(event) {  
            alert(event.data);  
        };  
        //与WebSocket建立连接  
        webSocket.onopen = function(event) {  
            document.getElementById('messages').innerHTML = '与服务器端建立连接';  
        };  
        //处理服务器返回的信息  
        webSocket.onmessage = function(event) {  
        	
			document.getElementById('messages').innerHTML += '<br />';
			document.getElementById('messages').innerHTML += '<br />';
        	var data = event.data;
			
			var o = JSON.parse(data);
			document.getElementById('messages').innerHTML += resHandle(o);
        };  
        function start() {  
            //向服务器发送请求  
            webSocket.send('我是jCuckoo');  
        }

		
        function createRoom() {  
            //向服务器发送请求 
			var people_number = document.getElementById("people_number").value;
			
			
			var obj = ajaxGet('create-room?people_number='+people_number);
			
			document.getElementById('messages').innerHTML += resHandle(obj);
			
        }
		
        function joinRoom() {  
            //向服务器发送请求  
			var room_number = document.getElementById("room_number").value;
			
			var obj = ajaxGet('join-room?room_number='+room_number);
			
			document.getElementById('messages').innerHTML += resHandle(obj);
			
        }

		function prepare() {  
            //向服务器发送请求
			var obj = ajaxGet('prepare');
			document.getElementById('messages').innerHTML += resHandle(obj);
			
        }
		
		function submit() {  
            //向服务器发送请求
			
			var pokerIds = document.getElementById("pokerIds").value;
			var obj = ajaxGet('submit?pokerIds='+pokerIds);
			document.getElementById('messages').innerHTML += resHandle(obj);
			
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

var localVar = {
	people:[],// person_id:int 0--->离线    1--->在线    2--->已经submit
	global:{},
	mySelf:""
};

var indexStr = ['2','3','4','5','6','7','8','9','10','J','Q','K','A'];
var colorStr = ["<span style='color:red'>♦</span>","<span style='color:black'>♣</span>","<span style='color:red'>♥</span>","<span style='color:black'>♠</span>"];

function resHandle(o){

	var res_error_msg = "res_error_msg";
	var res_global_msg = "res_global_msg";
	var res_user_reconnected = "res_user_reconnected";
	var res_user_prepare = "res_user_prepare";
	var res_new_join = "res_new_join";
	
	
	if(o.type == res_error_msg){
		
		return o.message;
		// 提示错误信息
		
	}else if(o.type == res_user_reconnected){
		
		// 提示那个人重新链接了
		return o.data + o.message;
		
	}else if(o.type == res_user_prepare){
		
		// 提示哪个人准备好了
		return o.data + o.message;
	}else if(o.type == res_global_msg){
		
		// 根据global 来看看具体怎么处理
		
		var global = o.data;
		
		localVar.global = global;
		return handleGlobal(global);
	}
	
}




function handleGlobal(global){
	
		
	var localPeople = localVar.people;
	var people_num = global.people_num;
	var thisMes = global.thisMes;
	var allMes = global.allMes;
	
	
	
	// 已经全部提交，比分已经出来了
	if(thisMes.length ==0 && allMes.length > 0){
		
		
		var lastMes = allMes[allMes.length-1];
		var biFenDetails = lastMes[0].biFenDetail;
		var xiPaiFenDetails = lastMes[0].xiPaiFenDetail;
		var str = '<table>';
		str = str + '<tr><th></th><th>牌型</th><th colspan="'+biFenDetails.length+'">比分</th><th colspan="'+(xiPaiFenDetails.length==0?1:xiPaiFenDetails.length)+'">喜牌</th><th>小计</th><th>总计</th></tr>';
			
		for(var i=0;i<lastMes.length;i++){
			
			var person_id = lastMes[i].person_id;
			var ninepokers = lastMes[i].pukes;
			var biFenDetails = lastMes[i].biFenDetail;
			var xiPaiFenDetails = lastMes[i].xiPaiFenDetail;
			var total = lastMes[i].total;
			var total_left = lastMes[i].total_left;
			// person_id / ninepokers / biFenDetails / xiPaiFenDetails / total / total_left
			
			str += '<tr>';
			
			// person_id
			str += '<td>' + person_id + (ninepokers.giveup?'（弃牌）':'') +'</td>';
			
			// ninepokers
			str += '<td>';
			for(var j=0;j<ninepokers.tpukes.length;j++){
				for(var k=0;k<ninepokers.tpukes[0].pukes.length;k++)
					str += indexStr[ninepokers.tpukes[j].pukes[k].index] + colorStr[ninepokers.tpukes[j].pukes[k].color] + (k!==2?',':'');
				str += ';'
			}
			str += '</td>';
			
			// biFenDetails
			
			for(var j=0;j<biFenDetails.length;j++){
				str += '<td>' + biFenDetails[j].point + '</td>';
			}
			
			// xiPaiFenDetails
			
			if(xiPaiFenDetails.length == 0){
				str += '<td>0</td>';
			}
			for(var j=0;j<xiPaiFenDetails.length;j++){
				str += '<td>' + xiPaiFenDetails[j].point + '（' + xiPaiFenDetails[j].note + '）</td>';
			}
			
			str += '<td>' + total + '</td>';
			str += '<td>' + total_left + '</td>';
			
			str += '</tr>';
		}
		str += '</table>';
		
		
		// 将这个表格输出出去
		
		return str;
	}
	
	
	str = '';
	for(var i=0;i<thisMes.length;i++){
		
		// 处理人的头像状态
		
		var person_id = thisMes[i].person_id;
		var mes_status = 1;
		if(thisMes[i].pukes != null){
			mes_status = 2;
		}
		if(localPeople[person_id] === undefined){
			localPeople[person_id] = mes_status;
			
			// 新加一个div{#person_id} 显示头像
		}else if(localPeople[person_id] === 0){
			localPeople[person_id] = mes_status;
			
			// 提示此人重新链接
		}else if(localPeople[person_id] === 1){
			
			localPeople[person_id] = mes_status;
			
		}else if(localPeople[person_id] === 2){
			
			localPeople[person_id] = mes_status;
			// 这个提交状态取消
		}
		
		
		str += '房间号：' + global.room_id + '</br>';
		str += person_id + ' 状态：' + localPeople[person_id] + '（0---离线    1---在线    2---已经submit） </br>';
		
		// 处理扑克牌
		
		
		var orderedPukes = thisMes[i].orderedPukes;
		var pukes = thisMes[i].pukes;
		
		if(pukes != null){
			// 显示 NinePukes (关闭的九宫格)
			
			str += person_id + '已经提交;</br>';
			
		}else if(orderedPukes.length != 0){
			// 显示连续的9张牌
			
			for(var k=0;k<orderedPukes.length;k++)
				str += indexStr[orderedPukes[k].index] + colorStr[orderedPukes[k].color];
			
			str += '</br>';
			
		}else{
			// 准备状态
			str += person_id + '已经准备就绪;</br>';
		}
	}
	
	return str;
}










		
    </script>  
</body>  
</html>  
