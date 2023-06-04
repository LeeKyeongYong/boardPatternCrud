package com.study.board.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DfldUtil {

    //현재 날짜 문장
    public static String getNowDateStr(){
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr=date.format(cal.getTime());
        return dateStr;
    }
    
    //파일에 내용 쓰기
    public static void writeFileContents(String filePath,int data){
        writeFileContents(filePath,data+"");
    }



    //첫문자 소문자화
    public static String lcfirst(String str){
        String newStr="";
        newStr+=str.charAt(0);
        newStr+=newStr.toLowerCase();
        return newStr+str.substring(1);
    }

    //파일존재여부
    public static boolean isFileExists(String filePath){
        File f=new File(filePath);
        if(f.isFile()){
            return true;
        }
        return false;
    }
    //파일내용 읽어오기
    public static String getFileContents(String filePath){
        String rs=null;
        try{

            //바이트 단위로 파일 읽기
            FileInputStream filestream = null; //파일 스트림
            filestream = new FileInputStream(filePath);//파일 스트림 생성

            //버퍼선언
            byte[] readBuffer=new byte[filestream.available()];

            while(filestream.read(readBuffer)!=-1){
                
            }
            rs=new String(readBuffer);
            filestream.close();//스트림닫기

        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    //파일쓰기
    private static void writeFileContents(String filePath, String contents) {
        BufferedOutputStream bs=null;
        try{
            bs=new BufferedOutputStream(new FileOutputStream(filePath));
            bs.write(contents.getBytes());//Byte형으로만 넣을 수 있음
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                bs.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    //디렉토리 생성
    public static void mkDir(String dirPath){
        File dir=new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }
}
