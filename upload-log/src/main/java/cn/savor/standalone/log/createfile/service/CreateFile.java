package cn.savor.standalone.log.createfile.service;



import cn.savor.standalone.log.createfile.bean.Item;
import com.aliyun.oss.model.OSSObjectSummary;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gy on 2017/10/26.
 */
public class CreateFile {

    public static void createFile(List<Item> removes,List<Item> adds,List<Item> playlist, String filePath,String ossBucketName,String ossKeyPrefix,String hotelName ,String listName) throws IOException {
        System.out.println(removes.toString());
        Boolean bool = false;
        //创建总目录
        createDir(filePath+"\\");
        //创建资源目录

        createDir(filePath+"\\"+"media");
        //下载宣传片节目
        MediaDownloader.execute(adds, filePath + "\\" + "media", ossBucketName, ossKeyPrefix + hotelName + "/" + listName + "/");
        //创建config目录
        createDir(filePath+"\\"+"config");
        //创建playlist.cfg
        createFile(filePath+"\\"+"config"+"\\"+"playlist.cfg");
        for (Item item : playlist) {
            if (StringUtils.equals("adv",item.getType())){
                writeFileContent(filePath+"\\"+"config"+"\\"+"playlist.cfg",item.getCnaName()+"_"+item.getType());
            }else {
                writeFileContent(filePath+"\\"+"config"+"\\"+"playlist.cfg",item.getId()+"_"+item.getType());
            }

        }
        //创建update.cfg文件
        createFile(filePath+"\\"+"update.cfg");
        writeFileContent(filePath+"\\"+"update.cfg","#FORCEL");
        writeFileContent(filePath+"\\"+"update.cfg","");
        writeFileContent(filePath+"\\"+"update.cfg","get_log");
        writeFileContent(filePath+"\\"+"update.cfg","#bin");
        writeFileContent(filePath+"\\"+"update.cfg","config");
        writeFileContent(filePath+"\\"+"update.cfg","#emenu");
        writeFileContent(filePath+"\\"+"update.cfg","#game");
        writeFileContent(filePath+"\\"+"update.cfg","#tvad");
        writeFileContent(filePath+"\\"+"update.cfg","#roomname");
        writeFileContent(filePath+"\\"+"update.cfg","#get_templog");
        writeFileContent(filePath+"\\"+"update.cfg","#get_channel");
        writeFileContent(filePath+"\\"+"update.cfg","#get_backup");
        writeFileContent(filePath+"\\"+"update.cfg","");
        for (Item item : removes) {
            System.out.println(item.getId());
            writeFileContent(filePath+"\\"+"update.cfg","rm/media/"+item.getId()+"_"+item.getType());
        }
        writeFileContent(filePath+"\\"+"update.cfg","");
        writeFileContent(filePath+"\\"+"update.cfg","media");

    }

    public static void createDir(String filePath) {
        File dir = new File(filePath);
        //判断目录是否存在
        if (!dir.exists()){
            //目录不存在创建目录
            if (dir.mkdirs()){
                //创建目录成功
                System.out.println("创建目录成功！ ["+filePath+"]");
            }else {
                System.out.println("创建目录失败！ ["+filePath+"]");
            }
        }else{
            System.out.println("目录已存在！ ["+filePath+"]");
//            boolean b = deleteDir(dir);
//            if (!b){
//                System.out.println("删除目录失败！ ["+filePath+"]");
//            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static void createFile(String filePath) {
        File update = new File(filePath);
        try {
            //如果文件不存在，则创建新的文件
            if(!update.exists()){
                update.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath,String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
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
            // TODO: handle exception
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
}
