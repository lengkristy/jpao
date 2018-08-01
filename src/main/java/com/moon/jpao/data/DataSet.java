package com.moon.jpao.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 代浩然
 * 数据表集合对象
 */
public class DataSet {

    /**数据表集合*/
    private List<DataTable> set;

    public DataSet(){
        set = new ArrayList<DataTable>();
    }

    public int getCount(){
        if (this.set != null){
            return this.set.size();
        }else {
            return 0;
        }
    }

    public DataTable getDataTable(int index){
        if (this.set != null){
            return this.set.get(index);
        }else{
            return null;
        }
    }

    public void addDataTable(DataTable dt){
        if (this.set != null){
            this.set.add(dt);
        }
    }

    public List<DataTable> getSet() {
        return set;
    }

    public void setSet(List<DataTable> set) {
        this.set = set;
    }
}
