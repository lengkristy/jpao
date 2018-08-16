package com.moon.jpao.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据表中的数据行
 * @author 代浩然
 */
public class DataRow {
    /**数据列集合*/
    private List<DataColumn> columnList;

    public DataRow(){
        columnList = new ArrayList<DataColumn>();
    }

    /**
     * 获取列数量
     * @return
     */
    public int getColumnCount(){
        if (columnList != null){
            return columnList.size();
        }else{
            return 0;
        }
    }

    /**
     * 获取数据列
     * @param index
     * @return
     */
    public DataColumn getColumn(int index){
        if (this.columnList != null && this.columnList.size() > 0){
            return this.columnList.get(index);
        }else{
            return null;
        }
    }

    /**
     * 通过列名获取数据列
     * @param columnName
     * @return
     */
    public DataColumn getColumn(String columnName){
        DataColumn dc = null;
        if (this.columnList != null && this.columnList.size() > 0){
            for (DataColumn dataColumn: this.columnList) {
                if (dataColumn.getColumnName().equals(columnName)){
                    dc = dataColumn;
                    break;
                }
            }
            return dc;
        }else{
            return null;
        }
    }

    /**
     * 添加数据列
     * @param dataColumn
     */
    public void addColumn(DataColumn dataColumn){
        if (dataColumn != null){
            this.columnList.add(dataColumn);
        }
    }

    public List<DataColumn> getColumnList() {
        return columnList;
    }
}
