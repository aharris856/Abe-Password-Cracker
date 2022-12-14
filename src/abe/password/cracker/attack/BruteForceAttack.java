package abe.password.cracker.attack;

import abe.password.cracker.apclogger.APCLogger;
import abe.password.cracker.constants.HashType;
import abe.password.cracker.constants.OutputType;
import abe.password.cracker.hasher.APCHasher;
import abe.password.cracker.inputhandler.APCInputInstructions;
import abe.password.cracker.response.ResponseFailed;
import abe.password.cracker.response.ResponseSuccess;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class BruteForceAttack implements APCAttack {

    private final APCLogger logger = new APCLogger(this.getClass().getSimpleName());

    private String defaultResponseFileName = "Brute_Force_Attack_Response";

    private final char minChar = '!';
    private final char maxChar = '~';

    public void attack(APCInputInstructions apcInputInstructions, HashSet<String> passwordsToCrack) {

        logger.info("Attempting brute force attack");

        HashSet<String> crackedPasswords = executeBruteForceAttack(passwordsToCrack, apcInputInstructions.getHashType(), apcInputInstructions.getOutputType());

        createAPCResponse(crackedPasswords, apcInputInstructions.getOutputType());

        logger.info("Brute force attack complete.");
    }

    private HashSet<String> executeBruteForceAttack(HashSet<String> passwordsToCrack, HashType hashType, OutputType outputType) {

        HashSet<String> crackedPasswords = new HashSet<>();

        int numberOfChars = 1;
        while(passwordsToCrack.size() > 0) {

            logger.info("Number of chars = "+numberOfChars);

            HashSet<String> tmpCrackedPasswords = bruteForceAttackGivenNumberOfCharacters(passwordsToCrack, numberOfChars, hashType, outputType);

            if(tmpCrackedPasswords != null && tmpCrackedPasswords.size() > 0) {
                crackedPasswords.addAll(tmpCrackedPasswords);
            }

            numberOfChars++;
        }

        return crackedPasswords;
    }

    private HashSet<String> bruteForceAttackGivenNumberOfCharacters(HashSet<String> passwordsToCrack, int numberOfChars, HashType hashType, OutputType outputType) {

        if (numberOfChars < 1) {
            return null;
        }

        HashSet<String> crackedPasswords = new HashSet<>();
        APCHasher hasher = new APCHasher();
        char[] charArr = new char[numberOfChars];

        Arrays.fill(charArr, minChar);

        int index = 0;
        while(index < charArr.length) {

            String permutation = new String(charArr);
            String hashedPermutation = hasher.getHash(permutation, hashType);

            if(passwordsToCrack.contains(hashedPermutation)) {
                String crackedPassword = MessageFormat.format("Password : \"{0}\" - Hashed Password : \"{1}\"", permutation, hashedPermutation);
                crackedPasswords.add(crackedPassword);
                createAPCResponseSuccess("Brute_Force_Attack_Single_Response", new HashSet(List.of(crackedPassword)), outputType);
            }

            for(int i = 0; i < charArr.length; i++) {

                if(charArr[i] < maxChar) {
                    charArr[i]++;
                    break;
                }

                charArr[i] = minChar;
                
                if(index == i) {
                    index++;
                }
            }
        }

        return crackedPasswords;
    }

    private void createAPCResponse(HashSet<String> crackedPasswords, OutputType outputType) {

        if(crackedPasswords == null) {
            createAPCResponseFailed(defaultResponseFileName, "Failed to crack passwords.", outputType);
            return;
        }

        createAPCResponseSuccess(defaultResponseFileName, crackedPasswords, outputType);
    }

    private void createAPCResponseSuccess(String fileName, HashSet<String> crackedPasswords, OutputType outputType) {

        ResponseSuccess response = new ResponseSuccess();

        for(String crackedPassword : crackedPasswords) {
            response.addCrackedPassword(crackedPassword);
        }

        if (outputType == OutputType.FILE) {

            try {

                response.toFile(fileName);
                return;

            } catch (IOException e) {
                logger.error("Failed to write dictionary attack response to file. printing");
            }
        }

        logger.println(response.toString());
    }

    private void createAPCResponseFailed(String fileName, String errorMessage, OutputType outputType) {

        ResponseFailed response = new ResponseFailed();

        response.setErrorMessage(errorMessage);

        if (outputType == OutputType.FILE) {

            try {

                response.toFile(fileName);
                return;

            } catch (IOException e) {
                logger.error("Failed to write dictionary attack response to file. printing");
            }
        }

        logger.println(response.toString());
    }
}

