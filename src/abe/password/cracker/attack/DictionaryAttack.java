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

public class DictionaryAttack implements APCAttack {

    private final APCLogger logger = new APCLogger(this.getClass().getSimpleName());
    private String responseFileName = "Dictionary_Attack_Response";

    public void attack(APCInputInstructions apcInputInstructions, HashSet<String> passwordsToCrack) {

        logger.info("Attempting dictionary attack.");

        HashSet<String> crackedPasswords = executeDictionaryAttack(passwordsToCrack, apcInputInstructions.getDictionaryFile(), apcInputInstructions.getHashType());

        if (crackedPasswords == null) {
            logger.warn("Failed to attempt dictionary attack.");
            return;
        }

        createAPCResponse(crackedPasswords, apcInputInstructions.getOutputType());

        logger.info("Dictionary attack complete.");
    }

    private HashSet<String> executeDictionaryAttack(HashSet<String> passwordsToCrack, String dictionaryFileName, HashType hashType) {

        try {

            APCHasher hasher = new APCHasher();
            HashSet<String> crackedPasswords = new HashSet<>();
            BufferedReader dictionaryReader = new BufferedReader( new FileReader( dictionaryFileName ) );

            while(dictionaryReader.ready()) {

                String dictionaryPassword = dictionaryReader.readLine();
                String hashedDictionaryPassword = hasher.getHash(dictionaryPassword, hashType);

                if (passwordsToCrack.contains(hashedDictionaryPassword)) {
                    crackedPasswords.add(MessageFormat.format("Password : \"{0}\" - Hashed Password : \"{1}\"", dictionaryPassword, hashedDictionaryPassword));
                }
            }

            dictionaryReader.close();

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

        logger.println(response.toString());
    }
}
