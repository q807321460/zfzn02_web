package com.zfznjj.smarthome.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.engine.jdbc.spi.ResultSetReturn;

import com.mysql.fabric.xmlrpc.base.Data;
import com.zfznjj.smarthome.entity.ElectricState;
import com.zfznjj.smarthome.entity.OrderInfo;
import com.zfznjj.smarthome.model.ChildNode;
import com.zfznjj.smarthome.model.Electric;
import com.zfznjj.smarthome.model.ElectricOrder;

public class SmartHomeUtil {

	
	public static List<OrderInfo> ElectricOrdersToOrderInfos(List<ElectricOrder> electricOrders){
		List<OrderInfo> orderInfos = new ArrayList<>();
		for(ElectricOrder electricOrder : electricOrders){
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrderID(electricOrder.getId());
			orderInfo.setMasterCode(electricOrder.getMasterCode());
			orderInfo.setElectricCode(electricOrder.getElectricCode());
			orderInfo.setOrder(electricOrder.getOrderData());
			orderInfo.setOrderInfo(electricOrder.getOrderInfo());
			orderInfos.add(orderInfo);
		}
		return orderInfos;
	}
	
	public static ElectricState childNodeToElectricState(ChildNode childNode){
		ElectricState electricState = new ElectricState();
		electricState.setMasterCode(childNode.getMasterCode());
		electricState.setElectricCode(childNode.getElectricCode());
		electricState.setElectricState(childNode.getElectricState());
		return electricState;
	}
	
	public static List<ElectricState> childNodesToElectricStates(List<ChildNode> childNodes){
		List<ElectricState> electricStates = new ArrayList<>();
		for (ChildNode childNode : childNodes) {
			ElectricState  electricState = new ElectricState();
			electricState.setMasterCode(childNode.getMasterCode());
			electricState.setElectricCode(childNode.getElectricCode());
			electricState.setElectricState(childNode.getElectricState());
			electricState.setStateInfo(childNode.getStateInfo());
			electricStates.add(electricState);
		}
		return electricStates;
	}
	
	public static Timestamp StringToTimestamp(String string){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Timestamp(date.getTime());
	}
	
	public static String TimestampToString(Timestamp timestamp){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(timestamp == null)
			return null;
		return sdf.format(timestamp);
	}
	
	/*
	* 将10 or 13 位时间戳转为时间字符串
	* convert the number 1407449951 1407499055617 to date/time format timestamp
	*/
	public static String string2Date(String str_num,String format ) {
		//String str_num=Long.toString(num);
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    if (str_num.length() == 13) {
	        String date = sdf.format(new Date(Long.parseLong(str_num)));
	        return date;
	    } else {
	        String date = sdf.format(new Date(Integer.parseInt(str_num) * 1000L));
	        return date;
	    }
	}
	
	//16进制字符串转换成字节码
    public static byte[] hex2byte(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for(int i =0;i<bytes.length;i++) {
            bytes[i] = Integer.decode("#" + str.substring(2 * i, 2 * i + 2)).byteValue();
        }
        return bytes;
    }
    
  //字节码转换为16进制字符串
    public static String byte2hex(byte[] bytes)
    {
        String hs = "";
        String stmp = "";
        for(int i = 0;i<bytes.length;i++) {
            stmp = Integer.toHexString(bytes[i] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
    
    /** 
     * @Title:hexString2String 
     * @Description:16进制字符串转字符串 
     * @param src 
     *            16进制字符串 
     * @return 字节数组 
     * @throws 
     */  
    public static String hexString2String(String src) {  
        String temp = "";  
        for (int i = 0; i < src.length() / 2; i++) {  
            temp = temp  
                    + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),  
                            16).byteValue();  
        }  
        return temp;  
    } 
    
    /** 
     * @Title:string2HexString 
     * @Description:字符串转16进制字符串 
     * @param strPart 
     *            字符串 
     * @return 16进制字符串 
     * @throws 
     */  
    public static String string2HexString(String strPart) {  
        StringBuffer hexString = new StringBuffer();  
        for (int i = 0; i < strPart.length(); i++) {  
            int ch = (int) strPart.charAt(i);  
            String strHex = Integer.toHexString(ch);  
            hexString.append(strHex);  
        }  
        return hexString.toString();  
    } 
}
