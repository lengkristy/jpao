package com.moon.jpao;

import com.moon.jpao.data.DataColumn;
import com.moon.jpao.data.DataRow;
import com.moon.jpao.data.DataTable;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据访问对象
 * @author 代浩然
 */
public class DataAccess {
    /**数据库连接*/
    private Connection conn;

    /**
     * 执行SQL的参数
     */
    private List<KeyValueItem> params;

    /**执行SQL后的返回结果集*/
    private Map<String,Object> result;

    private DataAccess(Connection cono){
        this.conn = cono;
        params = new ArrayList<KeyValueItem>();
        result = new HashMap<String,Object>();
    }

    private static DataAccess _DATA_ACCESS;/**静态缓存*/

    /**
     * 获取数据访问器
     * @param conn 传入数据库的连接
     * @param enableCache 如果启动缓存，则conn可以传空
     * @return
     */
    public static synchronized DataAccess getDataAccess(Connection conn,boolean enableCache){
        if (enableCache && _DATA_ACCESS != null && conn == null){
            return  _DATA_ACCESS;
        }else if (!enableCache && conn != null){
            _DATA_ACCESS = new DataAccess(conn);
            return _DATA_ACCESS;
        }else{
            return null;
        }
    }

    /**
     * 关闭数据访问器
     */
    public static synchronized void close(){
        if (_DATA_ACCESS != null){
            _DATA_ACCESS.closeConn();
            _DATA_ACCESS = null;
        }
    }

    /**
     * 关闭连接
     */
    private void closeConn(){
        if (this.conn != null){
            try{
                this.conn.close();
                this.conn = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 清空参数
     */
    public void clear(){
        if (params != null){
            params.clear();
        }
    }

    /**
     * 是否启用游标
     * @param bIsCursor
     */
    public void Basic(boolean bIsCursor){
        this.clear();
        if (bIsCursor){
            this.params.add(new KeyValueItem("p_cursor", OracleTypes.CURSOR, ProcedureParamType.OUT));
            this.params.add(new KeyValueItem("p_errmsg",OracleTypes.VARCHAR, ProcedureParamType.OUT));
        }else{
            this.params.add(new KeyValueItem("p_errmsg",OracleTypes.VARCHAR, ProcedureParamType.OUT));
        }
    }

    /**
     * 添加参数
     * @param keyValueItem
     */
    public void addParam(KeyValueItem keyValueItem){
        if (keyValueItem != null){
            this.params.add(keyValueItem);
        }
    }

    /**
     * 通过key获取执行SQL返回的参数
     * @param key
     * @return
     */
    public Object getValueByKey(String key){
        Object val = null;
        for (Map.Entry<String, Object> entry : this.result.entrySet()) {
            if (entry.getKey().equals(key)){
                val = entry.getValue();
                break;
            }
        }
        return val;
    }

    /**
     * 执行存储过程
     * @param procName 存储过程名称
     * @return 返回数据表
     */
    public DataTable doExecuteDataTable(String procName)throws Exception{
        DataTable dt = null;
        ResultSet rs = null;
        CallableStatement cstmt = null;
        try{
            cstmt=this.conn.prepareCall(preExecuteCallProcedure(procName));//conn为java.sql.Connection对象
            registOutParam(cstmt);//设置参数
            //执行存储过程
            cstmt.execute();
            //获取结果集
            if (this.params != null && this.params.size() > 0){
                for (KeyValueItem item:this.params) {
                    if (item.getParamType() == ProcedureParamType.OUT && !item.getkey().equals("p_cursor")){//获取输出参数，游标除外
                        this.result.put(item.getkey(),cstmt.getObject(item.getkey()));
                    }
                }
            }
            //获取游标
            rs = (ResultSet)cstmt.getObject("p_cursor");
            int col = rs.getMetaData().getColumnCount();
            dt = new DataTable();
            while(rs.next()){
                DataRow dr = new DataRow();
                for (int i = 1;i<=col;i++){
                    DataColumn dc = new DataColumn();
                    dc.setColumnName(rs.getMetaData().getColumnName(i));
                    if (rs.getObject(i) != null){
                        dc.setValue(rs.getObject(i));
                        //判断值的类型
                        dc.setC(rs.getObject(i).getClass());
                    }
                    dr.addColumn(dc);
                }
                dt.addRow(dr);
            }
        }catch (Exception e){
            throw e;
        }finally {
            if (rs != null){
                try{
                    rs.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (cstmt != null){
                try{
                    cstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return dt;
    }

    /**
     * 执行存储过程，不返回数据集
     * @param procName
     * @throws Exception
     */
    public void doExecuteNoQuery(String procName)throws Exception{
        CallableStatement cstmt = null;
        try{
            cstmt=this.conn.prepareCall(preExecuteCallProcedure(procName));//conn为java.sql.Connection对象
            registOutParam(cstmt);//设置参数
            //执行存储过程
            cstmt.execute();
            //获取结果集
            if (this.params != null && this.params.size() > 0){
                for (KeyValueItem item:this.params) {
                    if (item.getParamType() == ProcedureParamType.OUT && !item.getkey().equals("p_cursor")){//获取输出参数，游标除外
                        this.result.put(item.getkey(),cstmt.getObject(item.getkey()));
                    }
                }
            }
        }catch (Exception e){
            throw e;
        }finally {
            if (cstmt != null){
                try{
                    cstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 预处理执行的存储过程
     * @param procName
     * @return
     */
    private String preExecuteCallProcedure(String procName){
        String executeProcedure = "{ call " + procName;
        //预设置参数
        if (this.params != null && this.params.size() > 0){
            executeProcedure += "(";
            for (int i = 0;i < this.params.size();i++) {
                executeProcedure += "?,";
            }
            //去掉最后一个逗号
            executeProcedure = executeProcedure.substring(0,executeProcedure.length() - 1);
            executeProcedure += ")";
        }
        executeProcedure += "}";
        return executeProcedure;
    }

    /**
     * 设置存储过程参数
     * @param cstmt
     * @throws Exception
     */
    private void registOutParam(CallableStatement cstmt)throws Exception{
        if (this.params != null && this.params.size() > 0){ //设置存储过程参数
            //设置具体参数
            for (KeyValueItem item: this.params) {
                if (item.getParamType() == ProcedureParamType.IN){//输入参数
                    cstmt.setObject(item.getkey(),item.getValue());
                } else if (item.getParamType() == ProcedureParamType.OUT){
                    String type = item.getValue().getClass().getTypeName();
                    if (type.equals("java.lang.Integer")){
                        cstmt.registerOutParameter(item.getkey(),(Integer) item.getValue());
                    }else if (type.equals("java.sql.SQLType")){
                        cstmt.registerOutParameter(item.getkey(),(SQLType) item.getValue());
                    }
                }
            }
        }
    }
}
