package kale.http.framework;

public class WeatherInfo {

    private int mTemp;

    private String mCity;

    private int mCityid;

    private String mSD;

    private int mIsRadar;

    private int mWSE;

    private String mW;

    private String mWD;

    private String mNjd;

    private String mTime;

    private String mRadar;

    private int mQy;

    public WeatherInfo() {

    }

    public void setTemp(int temp) {
        mTemp = temp;
    }

    public int getTemp() {
        return mTemp;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCity() {
        return mCity;
    }

    public void setCityid(int cityid) {
        mCityid = cityid;
    }

    public int getCityid() {
        return mCityid;
    }

    public void setSD(String sD) {
        mSD = sD;
    }

    public String getSD() {
        return mSD;
    }

    public void setIsRadar(int isRadar) {
        mIsRadar = isRadar;
    }

    public int getIsRadar() {
        return mIsRadar;
    }

    public void setWSE(int wSE) {
        mWSE = wSE;
    }

    public int getWSE() {
        return mWSE;
    }

    public void setW(String W) {
        mW = W;
    }

    public String getW() {
        return mW;
    }

    public void setWD(String wD) {
        mWD = wD;
    }

    public String getWD() {
        return mWD;
    }

    public void setNjd(String njd) {
        mNjd = njd;
    }

    public String getNjd() {
        return mNjd;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTime() {
        return mTime;
    }

    public void setRadar(String radar) {
        mRadar = radar;
    }

    public String getRadar() {
        return mRadar;
    }

    public void setQy(int qy) {
        mQy = qy;
    }

    public int getQy() {
        return mQy;
    }
}



