package com.example.neva_sandbox;

public class ListViewBtnItem {
    private String ssidStr ;
    private String secStr ;
    private String strStr ;

    public void setSsid(String ssid) {
        ssidStr = ssid ;
    }
    public void setSec(String sec) {
        secStr = sec ;
    }
    public void setStr(String str) {
        strStr = str ;
    }

    public String getSsid() {
        return this.ssidStr ;
    }
    public String getSec() {
        return this.secStr ;
    }
    public String getStr() {
        return this.strStr ;
    }
}
