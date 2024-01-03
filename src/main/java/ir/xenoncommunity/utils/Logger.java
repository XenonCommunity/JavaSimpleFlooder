package ir.xenoncommunity.utils;

import lombok.Setter;
import lombok.val;

public class Logger {
    public String osName = System.getProperty("os.name");
    public String osVersion = System.getProperty("os.version");
    private boolean sendOsName;
    private boolean sendOsVersion;
    @Setter
    public String section = "NONE";
    public Logger(final boolean sendOsName, final boolean sendOSVersion){
        this.sendOsName = sendOsName;
        this.sendOsVersion = sendOSVersion;
    }
    public void print(final LEVEL levelIn, final String message){
        val osName = sendOsName ? this.osName : "";
        val osVersion = sendOsVersion ? this.osVersion : "";
        System.out.println(String.format("[%s-%s] [%s] [%s]>> %s", osName, osVersion, levelIn, this.section, message));

    }

    public enum LEVEL{
        ERROR,
        WARN,
        INFO
    }
}
