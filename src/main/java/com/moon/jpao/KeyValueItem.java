package com.moon.jpao;

/**
 * 用于执行数据库传递参数
 * @author 代浩然
 */
public class KeyValueItem {
    /**key*/
    private String key;
    /**值，如果是输入参数该值为具体值，如果是输出参数该值为OracleTypes*/
    private Object value;
    /**参数类型*/
    private ProcedureParamType paramType;

    public KeyValueItem(String key,Object value,ProcedureParamType paramType){
        this.key = key;
        this.value = value;
        this.paramType = paramType;
    }

    public ProcedureParamType getParamType(){
        return this.paramType;
    }

    public String getkey(){
        return this.key;
    }

    public Object getValue() {
        return value;
    }
}
