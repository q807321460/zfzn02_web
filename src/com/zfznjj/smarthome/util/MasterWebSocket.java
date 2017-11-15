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

import com.zfznjj.smarthome.model.ElectricOrder;
import com.zfznjj.smarthome.model.SceneOrder;


/** 
 * @Class: WebSocket
 * @Description: websocket类，这个类专门提供给移动端使用，还有一个类专门提供给主机使用
 * @author 孔翰文
 */
@ServerEndpoint(value="/websocket_master/{masterCode}")
public class MasterWebSocket {
    private Session session;
    private String masterCode;
    private static Map<String, MasterWebSocket> map;//用于保存各个主机对应的一组session，服务器主动发送数据时，会根据主机编号给该编号下的所有的session发送数据
    static {  
    	map = new HashMap<String, MasterWebSocket>();  
    }  
    //连接时执行
    @OnOpen
    public void onOpen(@PathParam("masterCode") String masterCode,Session session) throws IOException{
        this.masterCode = masterCode;
        this.session = session;
        if (!map.containsKey(masterCode)) {
        	map.put(masterCode, this);
        	System.out.println("【主机】连入主机：" + masterCode);
        }else {
        	MasterWebSocket masterWebSocket = (MasterWebSocket) map.get(masterCode);
        	masterWebSocket.session.close();
        	map.remove(masterCode);
        	map.put(masterCode, this);
        	System.out.println("【主机】重新新连入主机：" + masterCode);
        }
    }

    //关闭时执行
    @OnClose
    public void onClose(){
		if (map.containsKey(masterCode)) {
//			try {
//				session.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			map.remove(masterCode);
			System.out.println("【主机】断开主机：" + masterCode);
		}
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    	System.out.println("【主机】收到主机" + this.masterCode + "的消息：" + message);
        session.getBasicRemote().sendText("get message from "+this.masterCode+" message：" + message); //回复用户
    }

    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error) {
    	System.out.println("【主机】编号：" + this.masterCode + "出现错误：");
    	System.out.println(error);
    }
    
    public static void sendElectricOrder(String masterCode, ElectricOrder electricOrder) {
    	String message = "<" + electricOrder.getElectricCode() + electricOrder.getOrderData() + electricOrder.getOrderInfo() +"FF" + ">";
		try {
			MasterWebSocket.sendMessage(masterCode, message);
		} catch (Exception e) {
			System.out.println("【主机】failed to send to master: " +masterCode);
		}
    }
    
    public static void sendSceneOrder(String masterCode, SceneOrder sceneOrder) {
    	String message = "<" + sceneOrder.getElectricCode() + sceneOrder.getOrderData() + sceneOrder.getOrderInfo() + sceneOrder.getSceneIndex() +"*******FF" + ">";
		try {
			MasterWebSocket.sendMessage(masterCode, message);
		} catch (Exception e) {
			System.out.println("failed to send to master: " +masterCode);
		}
    }
    
    public static void sendMessage(String masterCode, String message) throws IOException{
    	if (map.containsKey(masterCode)) {
    		map.get(masterCode).session.getBasicRemote().sendText(message);
		}
    }

}