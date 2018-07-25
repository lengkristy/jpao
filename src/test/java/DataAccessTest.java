import com.moon.jpao.DataAccess;
import com.moon.jpao.KeyValueItem;
import com.moon.jpao.ProcedureParamType;
import com.moon.jpao.data.DataConvert;
import com.moon.jpao.data.DataTable;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 单元测试类
 */
public class DataAccessTest {

    @Test
    public void testDao(){
        //
        // 访问数据库
        //1.加载驱动：加载数据库对应的包名oracle.jdbc.driver
        //1.（加载数据库对应的驱动类）OracleDriver.class
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //这个写法是固定的
            //2.获取数据库连接:通过java的驱动管理器
            //url-数据库地址,不同的数据库写法不同 127.0.0.1和localhost都代表本机
            //url-数据库地址：user -用户名：password-密码     Connection为连接     DriverManager驱动管理器
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.1.241:1521:jcydb",
                    "tyyw", "tyyw");
            System.out.println("连接成功");
            DataAccess da = DataAccess.getDataAccess(conn,false);

            DataAccess dataAccess = DataAccess.getDataAccess(null,true);
            da.Basic(true);
            da.addParam(new KeyValueItem("p_bmsah","深宝检预析立[2014]44030600001号", ProcedureParamType.IN));
            DataTable dt = da.doExecuteDataTable("pkg_ssjd_latj.proc_test");
            System.out.println("结果集数量：" + dt.getRowCount());
            da = DataAccess.getDataAccess(null,true);
            da.Basic(true);
            da.addParam(new KeyValueItem("p_bmsah","深宝检预析立[2014]44030600001号", ProcedureParamType.IN));
            dt = da.doExecuteDataTable("pkg_ssjd_latj.proc_test");
            List<TYYW_GG_AJJBXX> list = DataConvert.convert(dt,TYYW_GG_AJJBXX.class);
            for (TYYW_GG_AJJBXX ajxx:list) {
                System.out.println(ajxx.getBmsah() + ":" + ajxx.getTysah());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
