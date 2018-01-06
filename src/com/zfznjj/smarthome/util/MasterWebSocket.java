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
 * @Description: websocket�࣬�����ר���ṩ���ƶ���ʹ�ã�����һ����ר���ṩ������ʹ��
 * @author �׺���
 */
@ServerEndpoint(value="/websocket_master/{masterCode}")
public class MasterWebSocket {
    private Session session;
    private String masterCode;
    private static ApplicationContext ctx = null;
    private static SmarthomeService smarthomeService;
    private static Map<String, MasterWebSocket> map;//���ڱ������������Ӧ��һ��session��������������������ʱ�������������Ÿ��ñ���µ����е�session��������
    static {  
    	map = new HashMap<String, MasterWebSocket>();  
    	ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    	smarthomeService = ctx.getBean(SmarthomeService.class);
    } 
    
    //����ʱִ��
    @OnOpen
    public void onOpen(@PathParam("masterCode") String masterCode,Session session) throws IOException{
        this.masterCode = masterCode;
        this.session = session;
        if (!map.containsKey(masterCode)) {
        	map.put(masterCode, this);
        	System.out.println("������������������" + masterCode);
        }else {
        	MasterWebSocket masterWebSocket = (MasterWebSocket) map.get(masterCode);
        	masterWebSocket.session.close();
        	map.remove(masterCode);
        	map.put(masterCode, this);
        	System.out.println("������������������������" + masterCode);
        }
    }

    //�ر�ʱִ��
    @OnClose
    public void onClose(){
		if (map.containsKey(masterCode)) {
			map.remove(masterCode);
			System.out.println("���������Ͽ�������" + masterCode);
		}
    }

    //�յ���Ϣʱִ��
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    	if (message.startsWith("\"version\":")) {
    		// �����״�����ʱ����Ҫ���汾�ŷ��͸����������汾��ʽΪ����"version":"v1.0.5t23701(a)"
    		String masterVersion = message.substring(11, message.length() - 1);
    		smarthomeService.updateMasterVersion(masterCode, masterVersion);
    		
    		
    	} else { 
    		// ����ͬ����Ϣ���ظ�������ʵ��������
    		session.getBasicRemote().sendText(message); 
    	}
    }

    //���Ӵ���ʱִ��
    @OnError
    public void onError(Session session, Throwable error) {
    	System.out.println("����������ţ�" + this.masterCode + "���ִ���");
    	System.out.println(error);
    }
    
    public static void sendElectricOrder(String masterCode, ElectricOrder electricOrder) {
    	String message = "<" + electricOrder.getElectricCode() + electricOrder.getOrderData() + electricOrder.getOrderInfo() +"FF" + ">";
		try {
			MasterWebSocket.sendMessage(masterCode, message);
		} catch (Exception e) {
			System.out.println("��������failed to send to master: " +masterCode);
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