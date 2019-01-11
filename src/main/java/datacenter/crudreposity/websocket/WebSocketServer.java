package datacenter.crudreposity.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

//@ServerEndpoint("/websocket/{userId}/{goodsId}")
//@Component
public class WebSocketServer {  
	private final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //CopyOnWriteArrayList适合使用在读操作远远大于写操作的场景里，比如缓存。发生修改时候做copy，新老版本分离，保证读的高性能，适用于以读为主的情况。
//     *      对于CopyOnWriteArraySet<E>类： 
//             *      1）它最适合于具有以下特征的应用程序：set 大小通常保持很小，只读操作远多于可变操作，需要在遍历期间防止线程间的冲突。  
//             *      2）它是线程安全的, 底层的实现是CopyOnWriteArrayList；   
//             *      3）因为通常需要复制整个基础数组，所以可变操作（add、set 和 remove 等等）的开销很大。  
//             *      4）迭代器不支持可变 remove 操作。  
//             *      5）使用迭代器进行遍历的速度很快，并且不会与其他线程发生冲突。在构造迭代器时，迭代器依赖于不变的数组快照。  
//            ---------------------
//    作者：学无止境qwer
//    来源：CSDN
//    原文：https://blog.csdn.net/u012956987/article/details/50811703
//    版权声明：本文为博主原创文章，转载请附上博文链接！
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
//        for (WebSocketServer item : webSocketSet) {
//            try {
//                //这里可以设定只推送给这个userId的，为null则全部推送
//                if(userId==0) {
//                    item.sendMessage(message);
//                    //只给秒杀成功了的用户推送成功秒杀生成订单的消息
//                }else if(item.userId==userId && item.goodsId == goodsId){
//                    item.sendMessage(message);
//                }
//            } catch (IOException e) {
//                continue;
//            }
//        }

        //迭代器
        Iterator iter = webSocketSet.iterator();
        while(iter.hasNext()) {
            WebSocketServer item = (WebSocketServer)iter.next();
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
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
