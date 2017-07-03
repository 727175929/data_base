package zx31401425.zucc.data_base;

/**
 * Created by 赵轩 on 2017/7/3.
 */

public class Data {
    private String foregin;
    private String home;
    private double num;

    public Data (String foregin,String home,double num){
        this.foregin = foregin;
        this.home = home;
        this.num = num;
    }

    public String getForegin() {
        return foregin;
    }

    public String getHome() {
        return home;
    }

    public double getNum() {
        return num;
    }

    public void setForegin(String foregin) {
        this.foregin = foregin;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setNum(double num) {
        this.num = num;
    }
}
