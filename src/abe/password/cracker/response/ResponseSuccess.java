package abe.password.cracker.response;

import abe.password.cracker.constants.ResponseType;

import java.util.HashSet;

public class ResponseSuccess extends APCResponse {

    private HashSet<String> crackedPasswords = new HashSet();

    public ResponseSuccess() {
        super(ResponseType.APC_RESPONSE_SUCCESS);
    }

    public void addCrackedPassword(String crackedPassword) {
        crackedPasswords.add(crackedPassword);
    }

    public String toString() {

        String crackedPasswordsStr = crackedPasswords.isEmpty() ?
                "\t\tNO PASSWORDS CRACKED\n" : formatCrackedPasswordsSet(crackedPasswords);

        return "Response : " + responseType.toString() + " {\n" +
                "\tCracked Passwords {\n" +
                 crackedPasswordsStr + "\t}\n" +
                "}";
    }

    private String formatCrackedPasswordsSet(HashSet<String> crackedPasswords) {

        StringBuilder stringBuilder = new StringBuilder();

        for(String crackedPassword : crackedPasswords) {
            stringBuilder.append("\t\t").append(crackedPassword).append("\n");
        }

        return stringBuilder.toString();
    }
}
