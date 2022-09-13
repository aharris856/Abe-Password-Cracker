package abe.password.cracker.apclogger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class APCLogger {

    private String className;

    public APCLogger(String className) {
        this.className = className;
    }

    public void info(String message) {
        log(message, "INFO");
    }

    public void error(String message) {
        log(message, "ERROR");
    }

    private void log(String message, String logType) {
        String output = "["+logType+"] - ["+className+"] - <"+getTimeStamp()+"> - "+message;
        System.out.println(output);
    }

    private String getTimeStamp() {
        return ( new SimpleDateFormat("HH.mm.ss-yyyy_MM_dd") ).format(new Date());
    }
}
