package com.zfznjj.smarthome.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/** 
* @author Ϧ����-O
* @version 2016��7��8�� ����10:38:49 
*/
public class WriteLog {
    //�����ļ�·��
    private static String path = "C:\\myLogs";// "C:\\myLogs"; //System.getProperty("user.dir");
    
    //�ļ�·��+����
    private static String filenameTemp;
    /**
     * �����ļ�
     * @param fileName  �ļ�����
     * @param filecontent   �ļ�����
     * @return  �Ƿ񴴽��ɹ����ɹ��򷵻�true
     */
    public static boolean createLog(String fileName,String filecontent){
        Boolean bool = false;
        filenameTemp = path + "\\" + fileName;//�ļ�·��+����+�ļ�����
        File file = new File(filenameTemp);
        try {
            //����ļ������ڣ��򴴽��µ��ļ�
            if(!file.exists()){
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is "+filenameTemp);
                //�����ļ��ɹ���д�����ݵ��ļ���
                writeLog(fileName, filecontent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return bool;
    }
    
    /**
     * ���ļ���д������
     * @param filepath �ļ�·��������
     * @param newstr  д�������
     * @return
     * @throws IOException
     */
    public static boolean writeLog(String fileName,String newstr) throws IOException{
        Boolean bool = false;
        Timestamp timestamp = new Timestamp(new Date().getTime());
		String sTime = SmartHomeUtil.TimestampToString(timestamp);
        String filein = sTime + "\r\n" + newstr+"\r\n";//��д����У�����
        String temp  = "";
        filenameTemp = path + "\\" + fileName;//�ļ�·��+����+�ļ�����
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
        	File file = new File(filenameTemp);//�ļ�·��(�����ļ�����)
        	//����ļ������ڣ��򴴽��µ��ļ�
        	if (!file.exists()){
                file.createNewFile();
                System.out.println("file is not exist, create file,the file is "+filenameTemp);
            }
            //���ļ�����������
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            
            //�ļ�ԭ������
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // ������֮��ķָ��� �൱�ڡ�\n��
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);
            
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //��Ҫ���ǹر�
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
    
    /**
     * ɾ���ļ�
     * @param fileName �ļ�����
     * @return
     */
    public static boolean delFile(String fileName){
        Boolean bool = false;
        filenameTemp = path+fileName+".txt";
        File file  = new File(filenameTemp);
        try {
            if(file.exists()){
                file.delete();
                bool = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bool;
    }
    
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        createLog(uuid+"myfile", "�ҵ���˵��ͣ���ȴ�,���ù�â������ʪ��ͫ��,ӳ����������ӵ�еĲʺ�,���ұ�����Ƭ��������,��Ϊ�����ҵ��� �ҵ���");
    }
}