package abe.password.cracker.attack;

import abe.password.cracker.constants.HashType;
import abe.password.cracker.constants.OutputType;
import abe.password.cracker.inputhandler.APCInputInstructions;
import abe.password.cracker.response.ResponseFailed;
import abe.password.cracker.response.ResponseSuccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class HybridDictionaryAttack implements APCAttack {

    private String responseFileName = "Hybrid_Dictionary_Attack_Response";

    @Override
    public void attack(APCInputInstructions apcInputInstructions, HashSet<String> passwordsToCrack) {

        System.out.println("Attempting hybrid dictionary attack.");

        Queue<String> dictionary = readDictionaryFile(apcInputInstructions.getDictionaryFile());

        if(dictionary == null || dictionary.size() == 0) {
            createAPCResponseFailed("Failed to read dictionary file.", apcInputInstructions.getOutputType());
            return;
        }

        HashSet<String> crackedPasswords = executeHybridDictionaryAttack(passwordsToCrack, dictionary, apcInputInstructions.getHashType());

        createAPCResponse(crackedPasswords, apcInputInstructions.getOutputType());
    }

    private HashSet<String> executeHybridDictionaryAttack(HashSet<String> passwordsToCrack, Queue<String> dictionary, HashType hashType) {
        return new HashSet<>();
    }

    private Queue<String> readDictionaryFile(String dictionaryFile) {

        try {

            BufferedReader dictionaryReader = new BufferedReader( new FileReader( dictionaryFile ) );
            Queue<String> dictionary = new LinkedList<>();

            while (dictionaryReader.ready()) {
                dictionary.add(dictionaryReader.readLine());
            }

            dictionaryReader.close();

            return dictionary;

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
                System.out.println("Failed to write dictionary attack response to file. printing");
            }
        }

        System.out.println(response);
    }

    private void createAPCResponseFailed(String errorMessage, OutputType outputType) {

        ResponseFailed response = new ResponseFailed();

        response.setErrorMessage(errorMessage);

        if (outputType == OutputType.FILE) {

            try {

                response.toFile(responseFileName);
                return;

            } catch (IOException e) {
                System.out.println("Failed to write dictionary attack response to file. printing");
            }
        }

        System.out.println(response);
    }
}
