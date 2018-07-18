package com.moon.jpao.data;

import com.moon.jpao.annotations.FieldMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据转换
 * @author 代浩然
 */
public class DataConvert {

    /**
     * 将DataTable转换成具体的bean列表
     * @param dt
     * @param <T>
     * @return
     */
    public static<T> List<T> convert(DataTable dt,Class<T> t)throws Exception{
        List<T> list = new ArrayList<T>();
        int rows = dt.getRowCount();
        for (int i = 0;i < rows;i++){//遍历行
            DataRow dr = dt.getRow(i);
            int cols = dr.getColumnCount();
            T obj = (T)t.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (int j = 0;j < cols;j++) { //遍历列
                DataColumn dc = dr.getColumn(j);
                for(Field field : fields){ //查找javabean与查询出的映射字段，匹配上进行赋值
                    boolean fieldHasAnno = field.isAnnotationPresent(FieldMapping.class);
                    if(fieldHasAnno){
                        FieldMapping fieldAnno = field.getAnnotation(FieldMapping.class);
                        if (fieldAnno.queryFileName().equals(dc.getColumnName())){
                            field.setAccessible(true);
                            field.set(obj,dc.getValue());
                            break;
                        }
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }
}
