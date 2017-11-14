package com.zfznjj.smarthome.util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
//import java.util.concurrent.CopyOnWriteArraySet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
//import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import javax.crypto.Mac;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;  
import javax.websocket.OnOpen;  
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


/** 
 * @Class: WebSocket
 * @Description: websocket类，这个类专门提供给App使用，还有一个类专门提供给主机使用
 * @author 孔翰文
 */
@ServerEndpoint(value="/websocket_app/{masterCode}")
public class AppWebSocket {
    private Session session;
    private String masterCode;
    private static Map<String, List<AppWebSocket>> map;//用于保存各个主机对应的一组session，服务器主动发送数据时，会根据主机编号给该编号下的所有的session发送数据
    static {  
    	map = new HashMap<String, List<AppWebSocket>>();  
    }  
    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("masterCode") String masterCode,Session session) throws IOException{
        this.masterCode = masterCode;
        this.session = session;
        if (!map.containsKey(masterCode)) {
        	List<AppWebSocket> list = new ArrayList<AppWebSocket>();
        	map.put(masterCode, list);
        }
        map.get(masterCode).add(this);
        System.out.println("【客户端】新连入第" + map.get(masterCode).size() + "个主机：" + masterCode);
    }

    //关闭时执行
    @OnClose
    public void onClose() {
    	if (map.get(masterCode).size()>0) {
    		map.get(masterCode).remove(this);
    	}
    	System.out.println("【客户端】当前主机：" + masterCode + "组中还剩下" + map.get(masterCode).size() + "个连接");
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    	System.out.println("【客户端】收到用户" + this.masterCode + "的消息：" + message);
        session.getBasicRemote().sendText("get message from "+this.masterCode+" message：" + message); //回复用户
    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error){
    	System.out.println("【客户端】主机编号为：" + this.masterCode + "出现连接错误");
    }
    
    public static void sendMessage(String masterCode, String message) throws IOException{
    	if (map.containsKey(masterCode)) {
    		for (AppWebSocket webSocket : map.get(masterCode)) {
        		webSocket.session.getBasicRemote().sendText(message);
    		}
		}
    }

    public static void sendSync(String masterCode) {
    	// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  //设置两秒的延时
        Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					if (map.containsKey(masterCode)) {
						for (AppWebSocket webSocket : map.get(masterCode)) {
							webSocket.session.getBasicRemote().sendText("Sync");
						}
					}
				} catch (Exception e) {
					System.out.println("【客户端】failed to send to master: " + masterCode);
				}
				timer.cancel();
			}
		}, 2000, 1000);
    }
}