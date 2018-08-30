        var basePath = "ws://localhost:8086/";
	    webSocket : "",
		alert("socket连接中，请稍后...")
		//userId：自行追加
		var userId = 10;
		var goodsId = 1001;
		if ('WebSocket' in window) {
			//webSocket = new WebSocket(basePath+'websocket/' + userId);  //需要在服务端添加注解 @ServerEndpoint("/websocket/{userId}/{goodsId}")
            webSocket = new WebSocket(basePath+'websocket/' + userId +'/' +goodsId );
		}
		else if ('MozWebSocket' in window) {
			webSocket = new MozWebSocket(basePath+"websocket/" + userId+'/' +goodsId);
		}
		else {
			webSocket = new SockJS(basePath+"sockjs/websocket");
		}

        webSocket.onerror = function(event) {
			alert("websockt连接发生错误，请刷新页面重试!")
		};
		webSocket.onopen = function(event) {
			
		};

        webSocket.onclose =  function(event) {
              alert("已经断开连接");
        };

		webSocket.onmessage = function(event) {
			var message = event.data;
			alert(message)//判断秒杀是否成功、自行写逻辑
		};

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function(){
            webSocket.close();
        }

        //关闭连接
        function closeWebSocket(){
            webSocket.close();
        }

        //发送消息
        function send(){
            var message = "每天进步要飞跃式的";
            webSocket.send(message);
        }
