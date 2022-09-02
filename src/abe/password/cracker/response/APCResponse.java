package abe.password.cracker.response;

import abe.password.cracker.constants.ResponseType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class APCResponse {

    private String defaultDir = System.getProperty("user.dir") + "\\APC_Responses\\";

    protected ResponseType responseType;

    public APCResponse(ResponseType responseType) {
        this.responseType = responseType;
    }

    public abstract String toString();

    public void toFile() throws IOException {

        toFile(responseType.toString());
    }

    public void toFile(String customFileName) throws IOException {

        String fileName = customFileName + "_" + getTimeStamp() + ".apc";
        String filePath = defaultDir + fileName;
        File folder = new File(defaultDir);

        if (!folder.exists()) {
            folder.mkdir();
        }

        PrintWriter pw = new PrintWriter( new BufferedWriter( new FileWriter( filePath ) ) );
        pw.println(this);
        pw.close();
    }

    public String getTimeStamp() {
        return ( new SimpleDateFormat("HH.mm.ss-yyyy_MM_dd") ).format(new Date());
    }
}
