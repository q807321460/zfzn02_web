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
//package com.zfznjj.smarthome.util;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
///**
// * ������־��¼��
// *
// * @author duanmei 
// */
//public class WriteLog {
//
//    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd_HHmmss");
//    private final SimpleDateFormat dir_date_format = new SimpleDateFormat("yyyyMMdd");
//    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//    private final long maxLogFileSize = 100 * 1024 * 1024;//��־�ļ���С����
//    private final String zeroStr = "00000000000000000000000000000000000000000000";//��0���ַ���
//    private final String EmptyStr = "                     ";//���ո����ַ���
//    private List<String> log;//��־��¼�б�
//    private File f;//��־�ļ���ָ��
//    private String fileName = "";//��־�ļ���
//    private BufferedWriter output;
//    private javax.swing.JTextArea edLog;//UI���ڴ������־��ʾ
//    private int threadnum = 0;//��¼��ǰʹ��Log���߳����������Ϊ�㲻�ر������
//    private String dir = "";
//    private int logFileNum = 1;//��־�ļ���
//    private boolean createLogBuffering = false;
//    private boolean ZipLog = true;
//    private ZipOutputStream zout = null;
//    private long WriteLogSize = 0;
//    private boolean WriteInsertSQL = true;
//    private File file_insert_sql;
//    private BufferedWriter out_write_sql;
//
//    /**
//     * ��־���ʼ��
//     *
//     * @param logDir ��־����·��
//     * @param fileName Ҫд���ļ���
//     * @param isZipLog �Ƿ�ѹ����־�ļ�
//     * @param WriteSQLFile �Ƿ�д�����SQL
//     */
//    public WriteLog(String logDir, String fileName, boolean isZipLog, boolean WriteSQLFile) {
//        this.WriteInsertSQL = WriteSQLFile;
//        this.ZipLog = isZipLog;
//        if (null == fileName || "".equals(fileName)) {
//            this.fileName = this.dateformat.format(new Date());
//        }
//        String log_dir = logDir;
//        if (null == log_dir) {
//            log_dir = "";
//        }
//        log_dir = log_dir.replace("\\", "/");
//        File dir_in = new File(log_dir);
//        String user_dir = System.getProperty("user.dir");
//        if (dir_in.exists()) {
//            user_dir = log_dir;
//        } else {
//            dir_in.mkdirs();
//            if (!dir_in.exists()) {
//                System.out.println("���õ���־·����Ч��ʹ�õ�ǰĬ��·����" + user_dir + "��");
//            } else {
//                user_dir = log_dir;
//            }
//        }
//        File dir_etl = new File(user_dir + "/MAXIMO_LOG/");
//        if (!dir_etl.exists()) {
//            dir_etl.mkdir();
//        }
//        this.dir = user_dir + "/MAXIMO_LOG/" + dir_date_format.format(new Date());
//        File file_dir = new File(dir);
//        file_dir.mkdir();
//        log = new ArrayList<String>();
//        this.createStream();
//        this.Log("MainThread��" + "���õ���־·����" + log_dir + "����ǰʹ����־·����" + user_dir + "/ETL_MAXIMO_LOG/��");
//        this.Log("MainThread��" + "Ĭ�Ϲ���·����" + System.getProperty("user.dir") + "��");
//    }
//
//    /**
//     * �����־
//     */
//    public synchronized void ClearLog() {
//        this.log.clear();
//    }
//
//    /**
//     *
//     * ��¼��־���ļ��������ʾ��
//     *
//     * @param log Ҫ��¼����־����
//     */
//    public synchronized final void Log(String log) {
//        String log_str = this.FormatLOGStr(log);
//        System.out.println(log_str);
//        this.ShowInUIEditor(log_str);
//        this.WriteToFile(log_str);
//    }
//
//    /**
//     * �ر��ļ������ǰ�����жϣ�ʹ���߳����Ƿ�Ϊ0����Ϊ��Ҫ��ȴ���
//     */
//    public synchronized void Save() {
//        try {
//            this.output.close();
//        } catch (IOException ex) {
//            Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /**
//     *
//     * ȡ���쳣���ࡢ�������е���Ϣ�������������ִ�����|����|�С�
//     *
//     * @param ex Ҫ�����쳣
//     * @return �����쳣�ַ���
//     */
//    public synchronized String GetThrowStr(Exception ex) {
//        StackTraceElement[] trace = ex.getStackTrace();
//        String ret = "";
//        for (int i = 0; i < trace.length; i++) {
//            StackTraceElement ste = trace[i];
//            ret = ret + "��" + ste.getFileName() + "|" + ste.getMethodName() + "|" + ste.getLineNumber() + "��";
//        }
//        return ret;
//    }
//
//    /**
//     * ����ʹ����־������߳���
//     *
//     * @return the threadnum
//     */
//    public synchronized int getThreadnum() {
//        return threadnum;
//    }
//
//    /**
//     * ������־����ʹ���̼߳���
//     */
//    public synchronized void addThreadnum() {
//        this.threadnum++;
//    }
//
//    /**
//     * ��С��־����ʹ���̼߳���
//     */
//    public synchronized void minusThreadnum() {
//        this.threadnum--;
//    }
//
//    /**
//     * ������־��UI�е���ʾ�ؼ�
//     *
//     * @param edLog UI��־�ؼ�JTextArea
//     */
//    public synchronized void setEdLog(javax.swing.JTextArea edLog) {
//        this.edLog = edLog;
//    }
//
//    /**
//     * ������־�ļ���ȡ�����ͨ��
//     */
//    private synchronized void createStream() {
//        if (this.createLogBuffering) {
//            return;
//        }
//        this.createLogBuffering = true;
//        String LogFileNumStr = this.zeroStr.substring(0, 5 - String.valueOf(this.logFileNum).length()) + String.valueOf(this.logFileNum);
//        String FileName = this.dir + "/" + this.fileName + "_" + LogFileNumStr + ".log";
//        if (this.WriteInsertSQL) {
//            this.file_insert_sql = new File(this.dir + "/" + this.fileName + "_" + LogFileNumStr + ".sql");
//            try {
//                FileOutputStream fos = new FileOutputStream(this.file_insert_sql);
//                Writer out = new OutputStreamWriter(fos, "UTF-8");
//                BufferedWriter outTemp = new BufferedWriter(out);
//                BufferedWriter old = this.out_write_sql;
//                this.out_write_sql = outTemp;
//                if (this.logFileNum > 1) {
//                    old.close();
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if (this.ZipLog) {
//            this.f = new File(FileName + ".zip");
//        } else {
//            this.f = new File(FileName);
//        }
//        try {
//            if (!f.exists()) {
//                f.createNewFile();
//            }
//            FileOutputStream fos = new FileOutputStream(this.f);
//            if (this.ZipLog) {
//                ZipOutputStream ZipTemp = new ZipOutputStream(fos);
//                String ZipFileName = FileName.substring(FileName.lastIndexOf("/") + 1);
//                ZipTemp.putNextEntry(new ZipEntry(ZipFileName));
//                Writer out = new OutputStreamWriter(ZipTemp, "UTF-8");
//                BufferedWriter outTemp = new BufferedWriter(out);
//                BufferedWriter old = this.output;
//                ZipOutputStream ZipOld = this.zout;
//                this.zout = ZipTemp;
//                this.output = outTemp;
//                if (this.logFileNum > 1) {
//                    old.close();
//                    ZipOld.closeEntry();
//                    ZipOld.close();
//                }
//            } else {
//                Writer out = new OutputStreamWriter(fos, "UTF-8");
//                BufferedWriter outTemp = new BufferedWriter(out);
//                BufferedWriter old = this.output;
//                this.output = outTemp;
//                if (this.logFileNum > 1) {
//                    old.close();
//                }
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        this.createLogBuffering = false;
//        this.logFileNum++;
//    }
//
//    /**
//     * ����־��ʾ��UI�ı༭������
//     *
//     * @param log_str ��־�ִ�
//     */
//    private void ShowInUIEditor(String log_str) {
//        //ֱ�Ӽ�¼����¼��̫����ڴ����
//        //this.log.add(log_str);
//        //UI����־��TextArea����ʾ
//        if (this.edLog != null) {
//            int txtHeight = this.edLog.getGraphics().getFontMetrics().getHeight();//��ʾ������߶�
//            int edHedight = this.edLog.getHeight();//�ؼ��߶�
//            this.edLog.append(log_str);
//            //this.edLog.setCaretPosition(this.edLog.getText().length());
//            if (this.edLog.getLineCount() < edHedight / txtHeight) {
//                //С�ڿ���ʾ��������ˢ��
//                this.edLog.paint(this.edLog.getGraphics());
//            }
//            if (this.edLog.getLineCount() % 100 == 0) {
//                //ÿ100��ˢ��һ��
//                this.edLog.paint(this.edLog.getGraphics());
//            }
//            if (this.edLog.getLineCount() > edHedight / txtHeight) {
//                //��������ʾ����ʱ���
//                this.edLog.setText("");
//            }
//        }
//    }
//
//    /**
//     * ����־д����־�ļ���
//     *
//     * @param log_str ��־�ִ�
//     */
//    private synchronized void WriteToFile(String log_str) {
//        //д���ļ�
//        try {
//            if (this.output != null) {
//                //this.output.write(log_str, 0, log_str.length());
//                this.output.write(log_str);
//                if (this.WriteInsertSQL) {
//                    int index_insertSQL = log_str.indexOf("InsertSQL:");
//                    if (index_insertSQL > 0) {
//                        this.out_write_sql.write(log_str.substring(index_insertSQL + 11));
//                    }
//                    this.out_write_sql.flush();
//                }
//                this.output.flush();
//                synchronized (this) {
//                    this.WriteLogSize += log_str.getBytes().length;
//                    if (this.WriteLogSize >= this.maxLogFileSize) {
//                        this.WriteLogSize = 0;
//                        this.createStream();
//                    }
//                }
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(WriteLog.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /**
//     * ��ʽ����־�ִ����ﵽҪд���Ҫ��
//     *
//     * @param log Ҫ��ʽ���ִ�
//     * @return ��ʽ���õ��ִ�
//     */
//    private synchronized String FormatLOGStr(String log) {
//        log = log.replace("\n", " ");
//        String log_str = df.format(new Date()) + "��" + log + "\r\n";
//        try {
//            String temp = log_str.substring(24);
//            temp = temp.substring(0, temp.indexOf("��"));
//            System.out.println(temp);
//            int MaxWriteTitleLen = 21;
//            if (temp.length() < MaxWriteTitleLen) {
//                log_str = log_str.replace(temp, this.EmptyStr.substring(0, MaxWriteTitleLen - temp.length()) + temp);
//            }
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//        return log_str;
//    }
//}
