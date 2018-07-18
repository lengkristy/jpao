package com.moon.jpao.data;

/**
 * 数据行中的数据列
 * @author 代浩然
 */
public class DataColumn {
    /**列名称*/
    private String columnName;
    /**列类型*/
    private Class<?> c;
    /**列的值*/
    private Object value;

    public DataColumn(){

    }

    public DataColumn(String columnName){
        this.columnName = columnName;
    }

    public DataColumn(String columnName,Class<?> c){
        this.columnName = columnName;
        this.c = c;
    }

    public DataColumn(String columnName,Class<?> c,String value){
        this.columnName = columnName;
        this.c = c;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Class<?> getC() {
        return c;
    }

    public void setC(Class<?> c) {
        this.c = c;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
