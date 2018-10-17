## 项目说明
本项目主要是方便java调用oracle存储过程

## 调用说明
```java
public class JpaoTest {
    public static void main(String[] args){
      DataAccess da = getDataAccess(true,
                          new KeyValueItem("p_labm", labm, ProcedureParamType.IN),
                          new KeyValueItem("p_dwbm", dwbm, ProcedureParamType.IN),
                          new KeyValueItem("p_cxtj", cxtj, ProcedureParamType.IN));
          
      DataTable dt = da.doExecuteDataTable("package.procedure_name");
      
      Po po = DataConvert.convert(dt, Po.class);
    }
    
}

```