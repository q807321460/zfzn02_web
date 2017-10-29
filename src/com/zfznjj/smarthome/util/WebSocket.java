package com.zfznjj.smarthome.util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
//import java.util.concurrent.CopyOnWriteArraySet;
import java.util.List;
import java.util.Map;
//import java.util.concurrent.CopyOnWriteArraySet;

//import javax.crypto.Mac;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;  
import javax.websocket.OnOpen;  
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

//import org.hibernate.annotations.common.util.impl.LoggerFactory;  

/** 
 * @Class: WebSocket
 * @Description: 服务器websocket类，这个类专门提供给移动端使用，还有一个类专门提供给主机使用
 * @author 孔翰文
 */
@ServerEndpoint(value="/websocket/{masterCode}")
public class WebSocket {
    private Session session;
    private String masterCode;
    private static Map<String, List<WebSocket>> map;//用于保存各个主机对应的一组session，服务器主动发送数据时，会根据主机编号给该编号下的所有的session发送数据
    static {  
    	map = new HashMap<String, List<WebSocket>>();  
    }  
    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("masterCode") String masterCode,Session session) throws IOException{
        this.masterCode = masterCode;
        this.session = session;
        if (!map.containsKey(masterCode)) {
        	List<WebSocket> list = new ArrayList<WebSocket>();
        	map.put(masterCode, list);
        }
        map.get(masterCode).add(this);
        System.out.println("新连入第" + map.get(masterCode).size() + "个主机：" + masterCode);
    }

    //关闭时执行
    @OnClose
    public void onClose(){
    	if (map.get(masterCode).size()>0) {
    		map.get(masterCode).remove(this);
    	}
    	System.out.println("当前主机：" + masterCode + "组中还剩下" + map.get(masterCode).size() + "个连接");
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    	System.out.println("收到用户" + this.masterCode + "的消息" + message);
        session.getBasicRemote().sendText("收到 "+this.masterCode+" 的消息 "); //回复用户
    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error){
//    	if (map.get(masterCode).size()>0) {
//    		map.get(masterCode).remove(this);
//    	}
    	System.out.println("用户id为：" + this.masterCode + "的连接发送错误");
//        error.printStackTrace();
    }
    
    public static void sendMessage(String masterCode, String message) throws IOException{
    	if (map.containsKey(masterCode)) {
    		for (WebSocket webSocket : map.get(masterCode)) {
        		webSocket.session.getBasicRemote().sendText(message);
    		}
		}
    }

}