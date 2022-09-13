package abe.password.cracker.attack;

import abe.password.cracker.apclogger.APCLogger;
import abe.password.cracker.constants.HashType;
import abe.password.cracker.constants.OutputType;
import abe.password.cracker.hasher.APCHasher;
import abe.password.cracker.inputhandler.APCInputInstructions;
import abe.password.cracker.response.ResponseFailed;
import abe.password.cracker.response.ResponseSuccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;

public class CommonPasswordsAttack implements APCAttack {

    private final APCLogger logger = new APCLogger(this.getClass().getSimpleName());

    private String responseFileName = "Common_Passwords_Attack_Response";

    public void attack(APCInputInstructions apcInputInstructions, HashSet<String> passwordsToCrack) {

        logger.info("Attempting common passwords attack.");

        HashSet<String> crackedPasswords = executeCommonPasswordsAttack(passwordsToCrack, apcInputInstructions.getCommonPasswordsFile(), apcInputInstructions.getHashType());

        if (crackedPasswords == null) {
            logger.warn("Failed to attempt common passwords attack.");
            return;
        }

        createAPCResponse(crackedPasswords, apcInputInstructions.getOutputType());

        logger.info("Common passwords attack complete.");
    }

    private HashSet<String> executeCommonPasswordsAttack(HashSet<String> passwordsToCrack, String commonPasswordsFileName, HashType hashType) {

        try {

            APCHasher hasher = new APCHasher();
            HashSet<String> crackedPasswords = new HashSet<>();
            BufferedReader commonPasswordsReader = new BufferedReader( new FileReader( commonPasswordsFileName ) );

            while(commonPasswordsReader.ready()) {

                String commonPassword = commonPasswordsReader.readLine();
                String hashedCommonPassword = hasher.getHash(commonPassword, hashType);

                if (passwordsToCrack.contains(hashedCommonPassword)) {
                    crackedPasswords.add(MessageFormat.format("Password : \"{0}\" - Hashed Password : \"{1}\"", commonPassword, hashedCommonPassword));
                }
            }

            commonPasswordsReader.close();

            return crackedPasswords;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createAPCResponse(HashSet<String> crackedPasswords, OutputType outputType) {

        if(crackedPasswords == null) {
            createAPCResponseFailed("Failed to crack passwords.", outputType);
            return;
        }

        createAPCResponseSuccess(crackedPasswords, outputType);
    }

    private void createAPCResponseSuccess(HashSet<String> crackedPasswords, OutputType outputType) {

        ResponseSuccess response = new ResponseSuccess();

        for(String crackedPassword : crackedPasswords) {
            response.addCrackedPassword(crackedPassword);
        }

        if (outputType == OutputType.FILE) {

            try {

                response.toFile(responseFileName);
                return;

            } catch (IOException e) {
                logger.error("Failed to write dictionary attack response to file. printing");
            }
        }

        logger.println(response.toString());
    }

    private void createAPCResponseFailed(String errorMessage, OutputType outputType) {

        ResponseFailed response = new ResponseFailed();

        response.setErrorMessage(errorMessage);

        if (outputType == OutputType.FILE) {

            try {

                response.toFile(responseFileName);
                return;

            } catch (IOException e) {
                logger.error("Failed to write dictionary attack response to file. printing");
            }
        }

        logger.info(response.toString());
    }
}
