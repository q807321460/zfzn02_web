package com.zfznjj.smarthome.util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
//import java.util.concurrent.CopyOnWriteArraySet;
import java.util.List;
import java.util.Map;
//import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
//import javax.crypto.Mac;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;  
import javax.websocket.OnOpen;  
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zfznjj.smarthome.dao.MasterNodeDao;
import com.zfznjj.smarthome.model.ElectricOrder;
import com.zfznjj.smarthome.model.SceneOrder;
import com.zfznjj.smarthome.service.SmarthomeService;
import com.zfznjj.smarthome.servlet.base.BaseServlet;

/** 
 * @Class: WebSocket
 * @Description: websocket类，这个类专门提供给移动端使用，还有一个类专门提供给主机使用
 * @author 孔翰文
 */
@ServerEndpoint(value="/websocket_master/{masterCode}")
public class MasterWebSocket {
    private Session session;
    private String masterCode;
//    private ApplicationContext ctx; // Spring应用上下文环境
//    private static SmarthomeService smarthomeService;
    private static Map<String, MasterWebSocket> map;//用于保存各个主机对应的一组session，服务器主动发送数据时，会根据主机编号给该编号下的所有的session发送数据
    static {  
    	map = new HashMap<String, MasterWebSocket>();  
//    	// 应该是这里的初始化失败了
//    	ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
//		smarthomeService = ctx.getBean(SmarthomeService.class);
    } 
    
    /*
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     *通过传递applicationContext参数初始化成员变量applicationContext
     */
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//    	ctx = applicationContext;
//    	smarthomeService = ctx.getBean(SmarthomeService.class);
//    	System.out.println("【DEBUG】测试setApplicationContext是否被正常调用");
//    }
    
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
    public void onClose() throws IOException{
		if (map.containsKey(masterCode)) {
			MasterWebSocket masterWebSocket = (MasterWebSocket) map.get(masterCode);
        	masterWebSocket.session.close();
			map.remove(masterCode);
			System.out.println("【主机】断开主机：" + masterCode);
		}
    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    		// 将相同的信息返回给主机，实现心跳包
    		session.getBasicRemote().sendText(message); 
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