package com.moon.jpao.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 从数据库中查询出来的结果集数据表
 * @author 代浩然
 */
public class DataTable {

    /**数据行集合*/
    private List<DataRow> rowList;

    /**数据表名称*/
    private String name;

    public DataTable(){
        rowList = new ArrayList<DataRow>();
    }

    /**
     * 获取行数量
     * @return
     */
    public int getRowCount(){
        if (rowList != null){
            return rowList.size();
        }else{
            return 0;
        }
    }

    /**
     * 获取数据行
     * @param index
     * @return
     */
    public DataRow getRow(int index){
        if (this.rowList != null && this.rowList.size() > 0){
            return this.rowList.get(index);
        }else{
            return null;
        }
    }

    /**
     * 获取数据行列表
     * @return
     */
    public List<DataRow> getRowList(){
        return rowList;
    }

    /**
     * 添加数据行
     * @param dataRow
     */
    public void addRow(DataRow dataRow){
        if (dataRow != null) {
            this.rowList.add(dataRow);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
