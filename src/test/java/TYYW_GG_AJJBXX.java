import com.moon.jpao.annotations.FieldMapping;

public class TYYW_GG_AJJBXX {
    /**
     * 部门受案号
     */
    @FieldMapping(queryFileName = "BMSAH")
    private String bmsah;

    /**
     * 统一受案号
     */
    @FieldMapping(queryFileName = "TYSAH")
    private String tysah;

    public String getBmsah() {
        return bmsah;
    }

    public void setBmsah(String bmsah) {
        this.bmsah = bmsah;
    }

    public String getTysah() {
        return tysah;
    }

    public void setTysah(String tysah) {
        this.tysah = tysah;
    }
}
