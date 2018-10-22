        var basePath = "ws://localhost:8086/";
	    webSocket ="";
		//userId：自行追加
		var userId = 10;
		var goodsId = 1001;

        //心跳检测,每20s心跳一次
        var heartCheck = {
            timeout: 3000,
            timeoutObj: null,
            serverTimeoutObj: null,
            reset: function(){
                clearTimeout(this.timeoutObj);
                clearTimeout(this.serverTimeoutObj);
                return this;
            },
            start: function(){
                var self = this;
                this.timeoutObj = setTimeout(function(){
                    //这里发送一个心跳，后端收到后，返回一个心跳消息，
                    //onmessage拿到返回的心跳就说明连接正常
                    webSocket.send("HeartBeat");
                    console.info("客户端发送心跳：");
                    self.serverTimeoutObj = setTimeout(function(){//如果超过一定时间还没重置，说明后端主动断开了,
                        // 一般服务端收到信息就会回调js   webSocket.onmessage = function(event),在这里面就重置了这个定时器，重新发起心跳，
                        // 否则的话，肯定是服务端挂了，那么在这里定时轮询连接服务端。
                        webSocket.close();//如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
                        console.info("已经断了");
                        connectSocket();
                    }, self.timeout)
                }, this.timeout)
            }
        }

        connectSocket();

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

        function connectSocket() {

            //每次连接要重置心跳，这样保证服务端挂了还能继续轮询，保证客户端能连上服务端，如果服务端一直挂，那么客户端可以一直坚持心跳发送，一直到服务端恢复正常。
            heartCheck.reset().start();

            //$("#result").text("socket连接中，请稍后...");
            webSocket = "";
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
                //alert("websockt连接发生错误，请刷新页面重试!");
                $("#message").text("websockt连接发生错误，请刷新页面重试!");
                heartCheck.reset().start();
            };

            webSocket.onopen = function(event) {
                //心跳检测重置
                //heartCheck.reset().start();
            };

            webSocket.onclose =  function(event) {
                //alert("已经断开连接");
                $("#result").text("已经断开连接");
            };

            webSocket.onmessage = function(event) {
                var message = event.data;
                $("#result").text("恭喜您，已经连接成功了！");
                $("#message").text(message);
                //如果获取到消息，心跳检测重置
                heartCheck.reset().start();
                //alert(message)//判断秒杀是否成功、自行写逻辑
            };

        }