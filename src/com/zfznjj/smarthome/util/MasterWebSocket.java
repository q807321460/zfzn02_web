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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zfznjj.smarthome.dao.MasterNodeDao;
import com.zfznjj.smarthome.model.ElectricOrder;
import com.zfznjj.smarthome.model.SceneOrder;
import com.zfznjj.smarthome.service.SmarthomeService;

/** 
 * @Class: WebSocket
 * @Description: websocket类，这个类专门提供给移动端使用，还有一个类专门提供给主机使用
 * @author 孔翰文
 */
@ServerEndpoint(value="/websocket_master/{masterCode}")
public class MasterWebSocket {
    private Session session;
    private String masterCode;
    private static ApplicationContext ctx = null;
    private static SmarthomeService smarthomeService;
    private static Map<String, MasterWebSocket> map;//用于保存各个主机对应的一组session，服务器主动发送数据时，会根据主机编号给该编号下的所有的session发送数据
    static {  
    	map = new HashMap<String, MasterWebSocket>();  
    	ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    	smarthomeService = ctx.getBean(SmarthomeService.class);
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
			map.remove(masterCode);
			System.out.println("【主机】断开主机：" + masterCode);
		}
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    	if (message.startsWith("\"version\":")) {
    		// 主机首次连入时，需要将版本号发送给服务器，版本格式为——"version":"v1.0.5t23701(a)"
    		String masterVersion = message.substring(11, message.length() - 1);
    		smarthomeService.updateMasterVersion(masterCode, masterVersion);
    		
    		
    	} else { 
    		// 将相同的信息返回给主机，实现心跳包
    		session.getBasicRemote().sendText(message); 
    	}
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