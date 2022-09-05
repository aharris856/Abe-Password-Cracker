package abe.password.cracker.attack;

import abe.password.cracker.constants.HashType;
import abe.password.cracker.constants.OutputType;
import abe.password.cracker.hasher.APCHasher;
import abe.password.cracker.inputhandler.APCInputInstructions;
import abe.password.cracker.response.ResponseSuccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;

public class CommonPasswordsAttack implements APCAttack {

    public void attack(APCInputInstructions apcInputInstructions, HashSet<String> passwordsToCrack) {

        System.out.println("Attempting common passwords attack.");

        HashSet<String> crackedPasswords = executeCommonPasswordsAttack(passwordsToCrack, apcInputInstructions.getCommonPasswordsFile(), apcInputInstructions.getHashType());

        if (crackedPasswords == null) {
            System.out.println("Failed to attempt common passwords attack.");
            return;
        }

        createAPCResponse(crackedPasswords, apcInputInstructions.getOutputType());

        System.out.println("Common passwords attack complete.");
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
            System.out.println(e.toString());
            return null;
        }
    }

    private void createAPCResponse(HashSet<String> crackedPasswords, OutputType outputType) {

        ResponseSuccess response = new ResponseSuccess();

        for(String crackedPassword : crackedPasswords) {
            response.addCrackedPassword(crackedPassword);
        }

        if (outputType == OutputType.FILE) {

            try {

                response.toFile("Common_Passwords_Attack_Response");
                return;

            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }

        System.out.println(response);
    }
}
