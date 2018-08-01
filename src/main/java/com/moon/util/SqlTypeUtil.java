package com.moon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * SQL类型与JAVA类型处理
 */
public class SqlTypeUtil {

    /**
     * 将ORACLE的CLOB类型转成String
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String clobToString(Clob clob) throws Exception {
        String reString = "";
        Reader is = null;
        BufferedReader br = null;
        try {
            is = clob.getCharacterStream();// 得到流
             br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
                sb.append(s);
                s = br.readLine();
            }
            reString = sb.toString();
        } catch (Exception e){
            throw e;
        }finally {
            try{
                if (is != null){
                    is.close();
                }
                if (br != null){
                    br.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return reString;
    }
}
