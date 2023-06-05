package com.study.board.util;

import java.sql.*;
import java.util.*;
import static java.lang.Class.forName;

public class DbConnectionUtil {

    private Connection connection;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PW;
    public static int DB_PORT;
    public static String DB_IP;

    public void connect(){
        String url="jdbc:mysql://"+DB_IP+DB_PORT+"/"+DB_NAME
                +"?serverTimeZone=Asia/Seoul&useOldAliasMetadataBehavior=true";
        String user=DB_USER;
        String pw=DB_PW;
        String driverName="com.mysql.cj.jdbc.Driver";

        try{
            connection=DriverManager.getConnection(url,user,pw);
            Class.forName(driverName);
        }catch(SQLException e){
            System.out.printf("[sql 예외]: %s\n",e.getMessage());
        }catch(ClassNotFoundException cnfe){
            System.out.printf("[드라이버 클래스 로딩 예외]: %s\n",cnfe.getMessage());
        }
    }

    public int selectRowIntValue(String sql){
        Map<String,Object> row = selectRow(sql);
        for(String key:row.keySet()){
            return (int)row.get(key);
        }
        return -1;
    }

    public String selectRowStringValue(String sql){
        Map<String,Object> row = selectRow(sql);
        for(String key:row.keySet()){
            return (String)row.get(key);
        }
        return "";
    }

    public Boolean selectRowBooleanValue(String sql){
        Map<String,Object> row = selectRow(sql);
        for(String key:row.keySet()){
            if(row.get(key) instanceof String){
                return ((String)row.get(key)).equals("1");
            } else if(row.get(key) instanceof  Integer){
                return ((int)row.get(key))==1;
            } else if(row.get(key) instanceof Boolean){
                return ((boolean)row.get(key));
            }
        }
        return false;
    }

    public Map<String,Object> selectRow(String sql){
        List<Map<String,Object>> rows = selectRows(sql);
        if(rows.size()==0){
            return new HashMap<String,Object>();
        }
        return rows.get(0);
    }

    public List<Map<String,Object>> selectRows(String sql){
       List<Map<String,Object>> rows = new ArrayList<>();
       try{
            Statement pstmt=connection.createStatement();
            ResultSet rs=pstmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnSize = metaData.getColumnCount();

            while(rs.next()){
                Map<String,Object> row= new HashMap<>();
                    for(int columnIndex=0; columnIndex<columnSize; columnIndex++){
                        String columnName = metaData.getColumnName(columnIndex+1);
                        Object value=rs.getObject(columnName);

                        if(value instanceof  Long){
                            int numValue = (int)(long)value;
                            row.put(columnName,numValue);
                        } else if(value instanceof Timestamp){
                            String dataValue=value.toString();
                            dataValue = dataValue.substring(0,dataValue.length()-2);
                            row.put(columnName,dataValue);
                        } else {
                            row.put(columnName,value);
                        }
                    }
                rows.add(row);
            }
       }catch(SQLException e){
           System.out.printf("[sql 예외, SQL: %s ]: %s\n",sql,e.getMessage());
            e.printStackTrace();
       }
       return rows;
    }

    public int delete(String sql){
        int del=0;
        Statement stmt;
        try{
            stmt= connection.createStatement();
            del=stmt.executeUpdate(sql);
        }catch(SQLException e){
            System.out.printf("[sql 예외, SQL: %s ]: %s\n",sql,e.getMessage());
        }
        return del;
    }
    public int update(String sql){
        int updload=0;
        Statement stmt;
        try{
            stmt=connection.createStatement();
            updload=stmt.executeUpdate(sql);
        }catch(SQLException e){
            System.out.printf("[sql 예외, SQL: %s ]: %s\n",sql,e.getMessage());
        }
        return updload;
    }

    public int insert(String sql){
        int inData=0;
            try{
                Statement stmt=connection.createStatement();
                stmt.execute(sql,Statement.RETURN_GENERATED_KEYS); //Statement 객체를 사용하여 sql 변수에 저장된 SQL 문을 실행
                ResultSet rs=stmt.getGeneratedKeys(); //자동 생성된 키 값을 반환하도록 지정하는 옵션 이 쿼리는 데이터베이스에 새로운 레코드를 삽입하는 작업을 수행
                //stmt 객체에서 생성된 키 값을 가져오기 위해 getGeneratedKeys() 메서드를 호출하여 ResultSet 객체 rs에 결과를 저장
                if(rs.next()){
                    inData=rs.getInt(1);
                }
            }catch(SQLException e){
                System.out.printf("[sql 예외, SQL: %s ]: %s\n",sql,e.getMessage());
            }
        return inData;
    }

    public void close(){
        if(connection!=null){
            try{
                connection.close();;
            }catch(SQLException e){
                System.out.printf("[sql 예외]: %s\n",e.getMessage());
            }
        }
    }

}
