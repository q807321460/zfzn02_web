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
 * @Description: websocket�࣬�����ר���ṩ���ƶ���ʹ�ã�����һ����ר���ṩ������ʹ��
 * @author �׺���
 */
@ServerEndpoint(value="/websocket_master/{masterCode}")
public class MasterWebSocket {
    private Session session;
    private String masterCode;
    private static Map<String, MasterWebSocket> map;//���ڱ������������Ӧ��һ��session��������������������ʱ�������������Ÿ��ñ���µ����е�session��������
    static {  
    	map = new HashMap<String, MasterWebSocket>();  
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
//			try {
//				session.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			map.remove(masterCode);
			System.out.println("���������Ͽ�������" + masterCode);
		}
    }

    //�յ���Ϣʱִ��
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
//    	System.out.println("���������յ�����" + this.masterCode + "����Ϣ��" + message);
        session.getBasicRemote().sendText(message); //�ظ��û�
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