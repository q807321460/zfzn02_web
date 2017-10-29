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
 * @Description: ������websocket�࣬�����ר���ṩ���ƶ���ʹ�ã�����һ����ר���ṩ������ʹ��
 * @author �׺���
 */
@ServerEndpoint(value="/websocket/{masterCode}")
public class WebSocket {
    private Session session;
    private String masterCode;
    private static Map<String, List<WebSocket>> map;//���ڱ������������Ӧ��һ��session��������������������ʱ�������������Ÿ��ñ���µ����е�session��������
    static {  
    	map = new HashMap<String, List<WebSocket>>();  
    }  
    //����ʱִ��
    @OnOpen
    public void onOpen(@PathParam("masterCode") String masterCode,Session session) throws IOException{
        this.masterCode = masterCode;
        this.session = session;
        if (!map.containsKey(masterCode)) {
        	List<WebSocket> list = new ArrayList<WebSocket>();
        	map.put(masterCode, list);
        }
        map.get(masterCode).add(this);
        System.out.println("�������" + map.get(masterCode).size() + "��������" + masterCode);
    }

    //�ر�ʱִ��
    @OnClose
    public void onClose(){
    	if (map.get(masterCode).size()>0) {
    		map.get(masterCode).remove(this);
    	}
    	System.out.println("��ǰ������" + masterCode + "���л�ʣ��" + map.get(masterCode).size() + "������");
    }

    //�յ���Ϣʱִ��
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    	System.out.println("�յ��û�" + this.masterCode + "����Ϣ" + message);
        session.getBasicRemote().sendText("�յ� "+this.masterCode+" ����Ϣ "); //�ظ��û�
    }

    //���Ӵ���ʱִ��
    @OnError
    public void onError(Session session, Throwable error){
//    	if (map.get(masterCode).size()>0) {
//    		map.get(masterCode).remove(this);
//    	}
    	System.out.println("�û�idΪ��" + this.masterCode + "�����ӷ��ʹ���");
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