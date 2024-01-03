package ir.xenoncommunity.utils;

import lombok.val;

public class Logger {
    public String osName = System.getProperty("os.name");
    public String osVersion = System.getProperty("os.version");
    private boolean sendOsName;
    private boolean sendOsVersion;
    public Logger(final boolean sendOsName, final boolean sendOSVersion){
        this.sendOsName = sendOsName;
        this.sendOsVersion = sendOSVersion;
    }
    public void print(final LEVEL levelIn, final String sectionIn, final String message){
        val osName = sendOsName ? this.osName : "";
        val osVersion = sendOsVersion ? this.osVersion : "";
        val section = sectionIn.equals("") ? "NONE" : sectionIn;
        System.out.println(String.format("[%s-%s] [%s] [%s]>> %s", osName, osVersion, levelIn, section, message));

    }

    public enum LEVEL{
        ERROR,
        WARN,
        INFO
    }
}
