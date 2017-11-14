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
 * @Description: websocket�࣬�����ר���ṩ��Appʹ�ã�����һ����ר���ṩ������ʹ��
 * @author �׺���
 */
@ServerEndpoint(value="/websocket_app/{masterCode}")
public class AppWebSocket {
    private Session session;
    private String masterCode;
    private static Map<String, List<AppWebSocket>> map;//���ڱ������������Ӧ��һ��session��������������������ʱ�������������Ÿ��ñ���µ����е�session��������
    static {  
    	map = new HashMap<String, List<AppWebSocket>>();  
    }  
    //����ʱִ��
    @OnOpen
    public void onOpen(@PathParam("masterCode") String masterCode,Session session) throws IOException{
        this.masterCode = masterCode;
        this.session = session;
        if (!map.containsKey(masterCode)) {
        	List<AppWebSocket> list = new ArrayList<AppWebSocket>();
        	map.put(masterCode, list);
        }
        map.get(masterCode).add(this);
        System.out.println("���ͻ��ˡ��������" + map.get(masterCode).size() + "��������" + masterCode);
    }

    //�ر�ʱִ��
    @OnClose
    public void onClose() {
    	if (map.get(masterCode).size()>0) {
    		map.get(masterCode).remove(this);
    	}
    	System.out.println("���ͻ��ˡ���ǰ������" + masterCode + "���л�ʣ��" + map.get(masterCode).size() + "������");
    }

    //�յ���Ϣʱִ��
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    	System.out.println("���ͻ��ˡ��յ��û�" + this.masterCode + "����Ϣ��" + message);
        session.getBasicRemote().sendText("get message from "+this.masterCode+" message��" + message); //�ظ��û�
    }

    //���Ӵ���ʱִ��
    @OnError
    public void onError(Session session, Throwable error){
    	System.out.println("���ͻ��ˡ��������Ϊ��" + this.masterCode + "�������Ӵ���");
    }
    
    public static void sendMessage(String masterCode, String message) throws IOException{
    	if (map.containsKey(masterCode)) {
    		for (AppWebSocket webSocket : map.get(masterCode)) {
        		webSocket.session.getBasicRemote().sendText(message);
    		}
		}
    }

    public static void sendSync(String masterCode) {
    	// �ڶ�������Ϊ�״�ִ�е���ʱʱ�䣬����������Ϊ��ʱִ�еļ��ʱ��  //�����������ʱ
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
					System.out.println("���ͻ��ˡ�failed to send to master: " + masterCode);
				}
				timer.cancel();
			}
		}, 2000, 1000);
    }
}