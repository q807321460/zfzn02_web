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
* @author 夕橘子-O
* @version 2016年7月8日 上午10:38:49 
*/
public class WriteLog {
    //生成文件路径
    private static String path = "C:\\myLogs";// "C:\\myLogs"; //System.getProperty("user.dir");
    
    //文件路径+名称
    private static String filenameTemp;
    /**
     * 创建文件
     * @param fileName  文件名称
     * @param filecontent   文件内容
     * @return  是否创建成功，成功则返回true
     */
    public static boolean createLog(String fileName,String filecontent){
        Boolean bool = false;
        filenameTemp = path + "\\" + fileName;//文件路径+名称+文件类型
        File file = new File(filenameTemp);
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is "+filenameTemp);
                //创建文件成功后，写入内容到文件里
                writeLog(fileName, filecontent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return bool;
    }
    
    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeLog(String fileName,String newstr) throws IOException{
        Boolean bool = false;
        Timestamp timestamp = new Timestamp(new Date().getTime());
		String sTime = SmartHomeUtil.TimestampToString(timestamp);
        String filein = sTime + "\r\n" + newstr+"\r\n";//新写入的行，换行
        String temp  = "";
        filenameTemp = path + "\\" + fileName;//文件路径+名称+文件类型
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
        	File file = new File(filenameTemp);//文件路径(包括文件名称)
        	//如果文件不存在，则创建新的文件
        	if (!file.exists()){
                file.createNewFile();
                System.out.println("file is not exist, create file,the file is "+filenameTemp);
            }
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            
            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
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
            //不要忘记关闭
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
     * 删除文件
     * @param fileName 文件名称
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
        createLog(uuid+"myfile", "我的梦说别停留等待,就让光芒折射泪湿的瞳孔,映出心中最想拥有的彩虹,带我奔向那片有你的天空,因为你是我的梦 我的梦");
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
// * 各种日志记录类
// *
// * @author duanmei 
// */
//public class WriteLog {
//
//    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd_HHmmss");
//    private final SimpleDateFormat dir_date_format = new SimpleDateFormat("yyyyMMdd");
//    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//    private final long maxLogFileSize = 100 * 1024 * 1024;//日志文件大小限制
//    private final String zeroStr = "00000000000000000000000000000000000000000000";//补0用字符串
//    private final String EmptyStr = "                     ";//补空格用字符串
//    private List<String> log;//日志记录列表
//    private File f;//日志文件打开指针
//    private String fileName = "";//日志文件名
//    private BufferedWriter output;
//    private javax.swing.JTextArea edLog;//UI窗口传入的日志显示
//    private int threadnum = 0;//记录当前使用Log的线程数，如果不为零不关闭输出流
//    private String dir = "";
//    private int logFileNum = 1;//日志文件数
//    private boolean createLogBuffering = false;
//    private boolean ZipLog = true;
//    private ZipOutputStream zout = null;
//    private long WriteLogSize = 0;
//    private boolean WriteInsertSQL = true;
//    private File file_insert_sql;
//    private BufferedWriter out_write_sql;
//
//    /**
//     * 日志类初始化
//     *
//     * @param logDir 日志保存路径
//     * @param fileName 要写入文件名
//     * @param isZipLog 是否压缩日志文件
//     * @param WriteSQLFile 是否写插入的SQL
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
//                System.out.println("配置的日志路径无效，使用当前默认路径【" + user_dir + "】");
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
//        this.Log("MainThread：" + "配置的日志路径【" + log_dir + "】当前使用日志路径【" + user_dir + "/ETL_MAXIMO_LOG/】");
//        this.Log("MainThread：" + "默认工作路径【" + System.getProperty("user.dir") + "】");
//    }
//
//    /**
//     * 清除日志
//     */
//    public synchronized void ClearLog() {
//        this.log.clear();
//    }
//
//    /**
//     *
//     * 记录日志到文件、输出显示等
//     *
//     * @param log 要记录的日志内容
//     */
//    public synchronized final void Log(String log) {
//        String log_str = this.FormatLOGStr(log);
//        System.out.println(log_str);
//        this.ShowInUIEditor(log_str);
//        this.WriteToFile(log_str);
//    }
//
//    /**
//     * 关闭文件输出流前请先判断，使用线程数是否为0，不为零要求等待。
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
//     * 取得异常的类、方法、行的信息，并返回如下字串【类|方法|行】
//     *
//     * @param ex 要处理异常
//     * @return 返回异常字符串
//     */
//    public synchronized String GetThrowStr(Exception ex) {
//        StackTraceElement[] trace = ex.getStackTrace();
//        String ret = "";
//        for (int i = 0; i < trace.length; i++) {
//            StackTraceElement ste = trace[i];
//            ret = ret + "【" + ste.getFileName() + "|" + ste.getMethodName() + "|" + ste.getLineNumber() + "】";
//        }
//        return ret;
//    }
//
//    /**
//     * 返回使用日志对象的线程数
//     *
//     * @return the threadnum
//     */
//    public synchronized int getThreadnum() {
//        return threadnum;
//    }
//
//    /**
//     * 增加日志对象使用线程计数
//     */
//    public synchronized void addThreadnum() {
//        this.threadnum++;
//    }
//
//    /**
//     * 减小日志对象使用线程计数
//     */
//    public synchronized void minusThreadnum() {
//        this.threadnum--;
//    }
//
//    /**
//     * 设置日志在UI中的显示控件
//     *
//     * @param edLog UI日志控件JTextArea
//     */
//    public synchronized void setEdLog(javax.swing.JTextArea edLog) {
//        this.edLog = edLog;
//    }
//
//    /**
//     * 创建日志文件并取得输出通道
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
//     * 将日志显示在UI的编辑窗口中
//     *
//     * @param log_str 日志字串
//     */
//    private void ShowInUIEditor(String log_str) {
//        //直接记录，记录数太多会内存溢出
//        //this.log.add(log_str);
//        //UI下日志在TextArea中显示
//        if (this.edLog != null) {
//            int txtHeight = this.edLog.getGraphics().getFontMetrics().getHeight();//显示的字体高度
//            int edHedight = this.edLog.getHeight();//控件高度
//            this.edLog.append(log_str);
//            //this.edLog.setCaretPosition(this.edLog.getText().length());
//            if (this.edLog.getLineCount() < edHedight / txtHeight) {
//                //小于可显示条数不断刷新
//                this.edLog.paint(this.edLog.getGraphics());
//            }
//            if (this.edLog.getLineCount() % 100 == 0) {
//                //每100条刷新一次
//                this.edLog.paint(this.edLog.getGraphics());
//            }
//            if (this.edLog.getLineCount() > edHedight / txtHeight) {
//                //超过可显示条数时清空
//                this.edLog.setText("");
//            }
//        }
//    }
//
//    /**
//     * 将日志写入日志文件中
//     *
//     * @param log_str 日志字串
//     */
//    private synchronized void WriteToFile(String log_str) {
//        //写入文件
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
//     * 格式化日志字串，达到要写入的要求
//     *
//     * @param log 要格式的字串
//     * @return 格式化好的字串
//     */
//    private synchronized String FormatLOGStr(String log) {
//        log = log.replace("\n", " ");
//        String log_str = df.format(new Date()) + "：" + log + "\r\n";
//        try {
//            String temp = log_str.substring(24);
//            temp = temp.substring(0, temp.indexOf("："));
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
