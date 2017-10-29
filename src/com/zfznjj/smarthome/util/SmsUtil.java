package com.zfznjj.smarthome.util;
import java.security.MessageDigest;
import java.sql.Timestamp;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SmsUtil {
	
	//����·��URL
	private static final String URL_NOTIFICATION="https://api.netease.im/sms/sendtemplate.action";//֪ͨ�����
    private static final String URL_SEND="https://api.netease.im/sms/sendcode.action";//������֤��
    private static final String URL_CHECK="https://api.netease.im/sms/verifycode.action";//�����֤��
    
    //�������ŷ�����˺ţ����滻���ڹ����̨Ӧ���������Appkey
    private static final String APP_KEY="48b57114db28a54515b69ff944adb260";
    //�������ŷ������Կ�����滻���ڹ����̨Ӧ���������appSecret
    private static final String APP_SECRET="ea2e294139b1";
    
    //�����
    private static final String NONCE="123456";
    //����ģ��ID
    private static final String TEMPLATE_ALARM="3059875";//����ģ��
    private static final String TEMPLATE_ALARMRESET="3062740";//�������ģ��
    
    //��֤�볤�ȣ���Χ4��10��Ĭ��Ϊ4
    private static final String CODELEN="6";
    
	// ���㲢��ȡCheckSum
    public static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }
    
    // ���㲢��ȡmd5ֵ
    public static String getMD5(String requestBody) {
        return encode("md5", requestBody);
    }
    
    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest
                    = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
    
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' 
            };
   
    //��ָ�����ֻ�������֤��
    public static String sendSmsCode(String phoneNum) throws Exception {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_SEND);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
         //�ο�����CheckSum��java���룬�������ĵ��Ĳ����б��У���CheckSum�ļ����ĵ�ʾ��
        String checkSum = getCheckSum(APP_SECRET, NONCE, curTime);
        // ���������header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // ��������ĵĲ�����requestBody����
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("mobile", phoneNum));
        nvps.add(new BasicNameValuePair("deviceId", phoneNum));
        nvps.add(new BasicNameValuePair("codeLen", CODELEN));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // ִ������
        HttpResponse response = httpClient.execute(httpPost);
        /*
         * 1.��ӡִ�н������Ҫ�ķ�����200��315��403��404��413��414��500
         * 2.�����code������Ŀ��Բο�������Code״̬��
         */
        //System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        String str = EntityUtils.toString(response.getEntity(), "utf-8");
        Map map = JsonPluginsUtil.jsonToMap(str);
        String sCode = map.get("code").toString();
        switch (sCode) {
		case "200":
			map.put("info", "�ѳɹ�������֤��");
			break;
		case "315":
			map.put("info", "IP����");
			break;
		case "403":
			map.put("info", "�Ƿ�������û��Ȩ��");
			break;
		case "404":
			map.put("info", "���󲻴���");//����ԭ��δ֪
			break;
		case "414":
			map.put("info", "��Ч���ֻ���");//��������
			break;
		case "416":
			map.put("info", "��������Ƶ��");//Ƶ�ʿ���
			break;
		case "500":
			map.put("info", "�������ڲ�����");
			break;
		default:
			map.put("info", "����������룺"+ sCode);
			break;
		}
        str = JsonPluginsUtil.mapToJson(map);
        return str;
    }
    
    //��ָ֤���豸����֤��
    public static String checkSmsCode(String phoneNum, String code) throws Exception {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_CHECK);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
         //�ο�����CheckSum��java���룬�������ĵ��Ĳ����б��У���CheckSum�ļ����ĵ�ʾ��
        String checkSum = getCheckSum(APP_SECRET, NONCE, curTime);
        // ���������header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // ��������ĵĲ�����requestBody����
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("mobile", phoneNum));
        nvps.add(new BasicNameValuePair("code", code));//�ֶ��༭

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // ִ������
        HttpResponse response = httpClient.execute(httpPost);
        String str = EntityUtils.toString(response.getEntity(), "utf-8");
        Map map = JsonPluginsUtil.jsonToMap(str);
        String sCode = map.get("code").toString();
        switch (sCode) {
		case "200":
			map.put("info", "��֤�ɹ�");
			break;
		case "315":
			map.put("info", "IP����");
			break;
		case "403":
			map.put("info", "�Ƿ�������û��Ȩ��");
			break;
		case "404":
			map.put("info", "��Ч����֤��");//���󲻴��ڣ���δ��ȡ��֤�뵫��ǿ����֤ʱ�ķ���
			break;
		case "413":
			map.put("info", "��֤ʧ��");//��ȡ��֤��֮���������ķ���
			break;
		case "414":
			map.put("info", "��Ч���ֻ���");//��������
			break;
		case "500":
			map.put("info", "�������ڲ�����");
			break;
		default:
			map.put("info", "����������룺"+ sCode);
			break;
		}
        str = JsonPluginsUtil.mapToJson(map);
        return str;
    }
    
    //���ͱ�����Ϣ String phones, 
    public static String sendAlarm(List<String> phones, String electircName, String time, boolean isAlarm) throws Exception {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_NOTIFICATION);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
         //�ο�����CheckSum��java���룬�������ĵ��Ĳ����б��У���CheckSum�ļ����ĵ�ʾ��
        String checkSum = getCheckSum(APP_SECRET, NONCE, curTime);
        // ���������header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // ��������ĵĲ�����requestBody����
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        String sPhones = JsonPluginsUtil.listToJson(phones);
        String sParams = "[\"" + electircName + "\",\"" + time + "\"]";
        if (isAlarm) {
        	nvps.add(new BasicNameValuePair("templateid", TEMPLATE_ALARM));
		}else {
			nvps.add(new BasicNameValuePair("templateid", TEMPLATE_ALARMRESET));
		}
        nvps.add(new BasicNameValuePair("mobiles", sPhones));
        nvps.add(new BasicNameValuePair("params", sParams));//�ֶ��༭ "[\"ˮ��\", \"11ʱ44��44��\"]"
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // ִ������
        HttpResponse response = httpClient.execute(httpPost);
        String str = EntityUtils.toString(response.getEntity(), "utf-8");
        Map map = JsonPluginsUtil.jsonToMap(str);
        String sCode = map.get("code").toString();
        switch (sCode) {
		case "200":
			map.put("info", "���ͳɹ�");
			break;
		case "315":
			map.put("info", "IP����");
			break;
		case "403":
			map.put("info", "�Ƿ�������û��Ȩ��");
			break;
		case "404":
			map.put("info", "���Ӵ���");//���󲻴��ڣ���δ��ȡ��֤�뵫��ǿ����֤ʱ�ķ���
			break;
		case "413":
			map.put("info", "��֤ʧ��");//��ȡ��֤��֮���������ķ���
			break;
		case "414":
			map.put("info", "��Ч���ֻ���");//��������
			break;
		case "500":
			map.put("info", "�������ڲ�����");
			break;
		default:
			map.put("info", "����������룺"+ sCode);
			break;
		}
        str = JsonPluginsUtil.mapToJson(map);
        return str;
    }
    
}
