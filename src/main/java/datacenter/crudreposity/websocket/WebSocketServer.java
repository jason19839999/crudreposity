package datacenter.crudreposity.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{userId}/{goodsId}")
@Component
public class WebSocketServer {  
	private final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    private  static WebSocketServer instance;

    public synchronized static WebSocketServer getInstance(){
        if(null == instance){
            instance = new WebSocketServer();
        }
        return instance;
    }

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收userId
    public int userId=0;
    //接收商品Id
    public int goodsId=0;
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam("userId") int userId,@PathParam("goodsId") int goodsId,Session session ) {
        this.session = session;
        this.userId=userId;
        this.goodsId = goodsId;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:"+userId+",当前在线人数为" + getOnlineCount());
            try {
             sendMessage("恭喜您，已经连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到来自窗口"+userId+"的信息:"+message);

        //处理用户发过来的消息，再给用户发回去，这里面是心跳应用
        if(message.equals("HeartBeat")) {

            this.session.getBasicRemote().sendText(message);
        }else{
            //群发消息
            for (WebSocketServer item : webSocketSet) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //WebSocketServer.instance.sendMessage(message);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") int userId,@PathParam("goodsId") int goodsId){
        log.info("推送消息到窗口"+userId+"，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个userId的，为null则全部推送
                if(userId==0) {
                    item.sendMessage(message);
                    //只给秒杀成功了的用户推送成功秒杀生成订单的消息
                }else if(item.userId==userId && item.goodsId == goodsId){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
  
}  
