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

    public void warn(String message) {
        log(message, "WARN");
    }

    public void error(String message) {
        log(message, "ERROR");
    }

    public void error(Exception e) {

        StackTraceElement[] stackTrace = e.getStackTrace();

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < stackTrace.length; i++) {

            if(i > 0) {
                stringBuilder.append('\t');
            }

            stringBuilder.append(stackTrace[i].toString());

            if(i < stackTrace.length - 1) {
                stringBuilder.append('\n');
            }
        }

        log(stringBuilder.toString(), "ERROR");
    }

    public void println(String message) {
        System.out.println(message);
    }

    private void log(String message, String logType) {
        String output = "["+logType+"] - ["+className+"] - <"+getTimeStamp()+"> - "+message;
        System.out.println(output);
    }

    private String getTimeStamp() {
        return ( new SimpleDateFormat("HH.mm.ss-yyyy_MM_dd") ).format(new Date());
    }
}
