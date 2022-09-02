package abe.password.cracker.response;

import abe.password.cracker.constants.ResponseType;

public class ResponseFailed extends APCResponse {

    private String errorMessage = "DEFAULT ERROR MESSAGE";

    public ResponseFailed() {
        super(ResponseType.APC_RESPONSE_FAILED);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return "Response : " + responseType + " {\n" +
                "\tFailure Message {\n" +
                "\t\t" + errorMessage + "\n" +
                "\t}\n" +
                "}";
    }
}
