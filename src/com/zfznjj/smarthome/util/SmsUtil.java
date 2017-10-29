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
	
	//各种路径URL
	private static final String URL_NOTIFICATION="https://api.netease.im/sms/sendtemplate.action";//通知类短信
    private static final String URL_SEND="https://api.netease.im/sms/sendcode.action";//发送验证码
    private static final String URL_CHECK="https://api.netease.im/sms/verifycode.action";//检测验证码
    
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String APP_KEY="48b57114db28a54515b69ff944adb260";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET="ea2e294139b1";
    
    //随机数
    private static final String NONCE="123456";
    //短信模板ID
    private static final String TEMPLATE_ALARM="3059875";//报警模板
    private static final String TEMPLATE_ALARMRESET="3062740";//解除报警模板
    
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN="6";
    
	// 计算并获取CheckSum
    public static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }
    
    // 计算并获取md5值
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
   
    //向指定的手机发送验证码
    public static String sendSmsCode(String phoneNum) throws Exception {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_SEND);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
         //参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
        String checkSum = getCheckSum(APP_SECRET, NONCE, curTime);
        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("mobile", phoneNum));
        nvps.add(new BasicNameValuePair("deviceId", phoneNum));
        nvps.add(new BasicNameValuePair("codeLen", CODELEN));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        /*
         * 1.打印执行结果，主要的返回码200、315、403、404、413、414、500
         * 2.具体的code有问题的可以参考官网的Code状态表
         */
        //System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
        String str = EntityUtils.toString(response.getEntity(), "utf-8");
        Map map = JsonPluginsUtil.jsonToMap(str);
        String sCode = map.get("code").toString();
        switch (sCode) {
		case "200":
			map.put("info", "已成功发送验证码");
			break;
		case "315":
			map.put("info", "IP限制");
			break;
		case "403":
			map.put("info", "非法操作或没有权限");
			break;
		case "404":
			map.put("info", "对象不存在");//具体原因未知
			break;
		case "414":
			map.put("info", "无效的手机号");//参数错误
			break;
		case "416":
			map.put("info", "操作过于频繁");//频率控制
			break;
		case "500":
			map.put("info", "服务器内部错误");
			break;
		default:
			map.put("info", "其它错误代码："+ sCode);
			break;
		}
        str = JsonPluginsUtil.mapToJson(map);
        return str;
    }
    
    //验证指定设备的验证码
    public static String checkSmsCode(String phoneNum, String code) throws Exception {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_CHECK);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
         //参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
        String checkSum = getCheckSum(APP_SECRET, NONCE, curTime);
        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("mobile", phoneNum));
        nvps.add(new BasicNameValuePair("code", code));//手动编辑

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        String str = EntityUtils.toString(response.getEntity(), "utf-8");
        Map map = JsonPluginsUtil.jsonToMap(str);
        String sCode = map.get("code").toString();
        switch (sCode) {
		case "200":
			map.put("info", "验证成功");
			break;
		case "315":
			map.put("info", "IP限制");
			break;
		case "403":
			map.put("info", "非法操作或没有权限");
			break;
		case "404":
			map.put("info", "无效的验证码");//对象不存在，尚未获取验证码但是强行验证时的返回
			break;
		case "413":
			map.put("info", "验证失败");//获取验证码之后输入错误的返回
			break;
		case "414":
			map.put("info", "无效的手机号");//参数错误
			break;
		case "500":
			map.put("info", "服务器内部错误");
			break;
		default:
			map.put("info", "其它错误代码："+ sCode);
			break;
		}
        str = JsonPluginsUtil.mapToJson(map);
        return str;
    }
    
    //发送报警信息 String phones, 
    public static String sendAlarm(List<String> phones, String electircName, String time, boolean isAlarm) throws Exception {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL_NOTIFICATION);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
         //参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
        String checkSum = getCheckSum(APP_SECRET, NONCE, curTime);
        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        String sPhones = JsonPluginsUtil.listToJson(phones);
        String sParams = "[\"" + electircName + "\",\"" + time + "\"]";
        if (isAlarm) {
        	nvps.add(new BasicNameValuePair("templateid", TEMPLATE_ALARM));
		}else {
			nvps.add(new BasicNameValuePair("templateid", TEMPLATE_ALARMRESET));
		}
        nvps.add(new BasicNameValuePair("mobiles", sPhones));
        nvps.add(new BasicNameValuePair("params", sParams));//手动编辑 "[\"水浸\", \"11时44分44秒\"]"
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        String str = EntityUtils.toString(response.getEntity(), "utf-8");
        Map map = JsonPluginsUtil.jsonToMap(str);
        String sCode = map.get("code").toString();
        switch (sCode) {
		case "200":
			map.put("info", "发送成功");
			break;
		case "315":
			map.put("info", "IP限制");
			break;
		case "403":
			map.put("info", "非法操作或没有权限");
			break;
		case "404":
			map.put("info", "连接错误");//对象不存在，尚未获取验证码但是强行验证时的返回
			break;
		case "413":
			map.put("info", "验证失败");//获取验证码之后输入错误的返回
			break;
		case "414":
			map.put("info", "无效的手机号");//参数错误
			break;
		case "500":
			map.put("info", "服务器内部错误");
			break;
		default:
			map.put("info", "其它错误代码："+ sCode);
			break;
		}
        str = JsonPluginsUtil.mapToJson(map);
        return str;
    }
    
}
