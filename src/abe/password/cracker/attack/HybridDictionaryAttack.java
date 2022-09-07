package abe.password.cracker.attack;

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class HybridDictionaryAttack implements APCAttack {

    private String responseFileName = "Hybrid_Dictionary_Attack_Response";

    private char[] specialCharacters;

    public HybridDictionaryAttack() {
        specialCharacters = new char[42];
        char ch = '!';
        int index = 0;

        while(ch < 'A') {
            specialCharacters[index++] = ch++;
        }

        ch = '[';

        while(ch < 'a') {
            specialCharacters[index++] = ch++;
        }

        ch = '{';

        while(ch < 127) {
            specialCharacters[index++] = ch++;
        }

    }

    @Override
    public void attack(APCInputInstructions apcInputInstructions, HashSet<String> passwordsToCrack) {

        long startTime = System.currentTimeMillis();

        System.out.println("Attempting hybrid dictionary attack.");

        Queue<String> dictionary = readDictionaryFile(apcInputInstructions.getDictionaryFile());

        if(dictionary == null || dictionary.size() == 0) {
            createAPCResponseFailed("Failed to read dictionary file.", apcInputInstructions.getOutputType());
            return;
        }

        HashSet<String> crackedPasswords = executeHybridDictionaryAttack(passwordsToCrack, dictionary, apcInputInstructions.getHashType());

        createAPCResponse(crackedPasswords, apcInputInstructions.getOutputType());

        long endTime = System.currentTimeMillis();
        double time = ( (double) (endTime - startTime) ) / 1000;
        System.out.println("Hybrid Attack Time: "+time+" s");
    }

    private HashSet<String> executeHybridDictionaryAttack(HashSet<String> passwordsToCrack, Queue<String> dictionary, HashType hashType) {

        APCHasher hasher = new APCHasher();
        HashSet<String> crackedPasswords = new HashSet<>();

        while(!dictionary.isEmpty()) {

            HashSet<String> permutations = getPermutationsOfWord(dictionary.poll());

            for(String permutation : permutations) {

                String hashedPermutation = hasher.getHash(permutation, hashType);

                if(passwordsToCrack.contains(hashedPermutation)) {
                    crackedPasswords.add(MessageFormat.format("Password : \"{0}\" - Hashed Password : \"{1}\"", permutation, hashedPermutation));
                }
            }
        }

        return crackedPasswords;
    }

    private HashSet<String> getPermutationsOfWord(String word) {

        HashSet<String> permutations = new HashSet<>();
        permutations.add(word);
        char[] wordArr = word.toCharArray();

        for(char specialChar : specialCharacters) {
            for(int i = 0; i < wordArr.length; i++) {

                char ch = wordArr[i];
                wordArr[i] = specialChar;
                permutations.add(new String(wordArr));
                wordArr[i] = ch;
            }
        }

        return permutations;
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
