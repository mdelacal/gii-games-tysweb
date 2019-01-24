var ws;
	function login(userName, pwd){
		var request=new XMLHttpRequest();
		request.open("GET", "http://localhost:8080/login?userName="+userName+"&pwd="+pwd);
		request.onreadystatechange = function() {
			if(request.readyState==4) {
				textosRecibidos.innerHTML=request.responseText;
				var ws=new WebSocket("ws://localhost:8080/gamews");
				ws.onopen=function(){
					add("Conectado");	
				}
				ws.onerror=function(){
					add("Error al conectar el WS");
				}
				ws.onmessage=function(message){
					var data=message.data;
					data=JSON.parse(data);
					
					if(data.TYPE=="MATCH"){
						add(JSON.stringify(data));
					}else
						add("Mensaje desconocido");
				}
				
			}
		};
		request.send();
	}
	
	function joinGame(gameName){
		var request=new XMLHttpRequest();
		request.open("GET", "http://localhost:8080/joinGame?gameName="+gameName);
		request.onreadystatechange = function() {
			if(request.readyState==4) 
				add(request.responseText);
			var ws=new WebSocket("ws://localhost:8080/gamews");
			ws.onerror=function(){
				add("Error al conectar el WS");
			}
		};
		request.send();
	}
	
	function move(coordinate){
		var p = {
			TYPE : "MOVEMENT",
			coordinate : [coordinate]}
		};
		ws.send(JSON.stringify(p));
	}
	
	function add(texto){
		var div=document.createElement("div");
		textosRecibidos.appendChild(div);
		div.innerHTML=texto;
	}