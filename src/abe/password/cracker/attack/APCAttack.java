package abe.password.cracker.attack;

import abe.password.cracker.constants.OutputType;
import abe.password.cracker.inputhandler.APCInputInstructions;
import abe.password.cracker.response.ResponseFailed;
import abe.password.cracker.response.ResponseSuccess;
import java.io.IOException;
import java.util.HashSet;

public interface APCAttack {

    public void attack(APCInputInstructions apcInputInstructions, HashSet<String> passwordsToCrack);

    private void responseFailureHandler(String errorMessage, APCInputInstructions apcInputInstructions) {

        ResponseFailed failure = new ResponseFailed();
        failure.setErrorMessage(errorMessage);

        if (apcInputInstructions.getOutputType() == OutputType.FILE) {

            try {
                failure.toFile();
                return;
            } catch (IOException e) {
                System.out.println("Failed to put failure response in file. Printing response.");
            }
        }

        System.out.println(failure);
    }

    private void responseSuccessHandler(String crackedPassword, APCInputInstructions apcInputInstructions) {

        ResponseSuccess success = new ResponseSuccess();
        success.addCrackedPassword(crackedPassword);

        if (apcInputInstructions.getOutputType() == OutputType.FILE) {

            try {
                success.toFile();
                return;
            } catch (IOException e) {
                System.out.println("Failed to put success response in file. Printing response.");
            }
        }

        System.out.println(success);
    }
}