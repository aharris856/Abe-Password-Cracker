package abe.password.cracker.inputhandler;

import abe.password.cracker.constants.AttackType;
import abe.password.cracker.constants.Command;
import abe.password.cracker.constants.HashType;
import abe.password.cracker.constants.OutputType;
import java.util.HashSet;
import java.util.Iterator;

public class UserInputHandler {
    private HashSet<String> validCommands = new HashSet<>();

    private final char commandIdentifier = '-';
    private String HASHED_PASS_FILE_CMD;
    private String DICTIONARY_FILE_CMD;
    private String COMMON_PASS_FILE_CMD;
    private String HASH_TYPE;
    private String ATTACK_TYPE;
    private String OUTPUT_TYPE;

    public UserInputHandler() {
        HASHED_PASS_FILE_CMD = commandIdentifier + Command.HPF.toString();
        DICTIONARY_FILE_CMD = commandIdentifier + Command.DF.toString();
        COMMON_PASS_FILE_CMD = commandIdentifier + Command.CPF.toString();
        HASH_TYPE = commandIdentifier + Command.HT.toString();
        ATTACK_TYPE = commandIdentifier + Command.AT.toString();
        OUTPUT_TYPE = commandIdentifier + Command.OT.toString();
        validCommands.add(HASHED_PASS_FILE_CMD);
        validCommands.add(DICTIONARY_FILE_CMD);
        validCommands.add(COMMON_PASS_FILE_CMD);
        validCommands.add(HASH_TYPE);
        validCommands.add(ATTACK_TYPE);
        validCommands.add(OUTPUT_TYPE);
    }

    public APCInputInstructions convertUserInputToAPCInputInstructions(String userInput) {
        return parseUserInput(userInput.split("\\s+"));
    }

    private APCInputInstructions parseUserInput(String[] userInputArr) {

        if(userInputArr.length <= 1 || !isValidCommand(userInputArr[0])) {
            return null;
        }

        APCInputInstructions apcInputInstructions = new APCInputInstructions();
        String hashedPasswordFile = null;
        String dictionaryFile = null;
        String commonPasswordsFile = null;
        HashType hashType = null;
        HashSet<AttackType> attackTypes = new HashSet<>();
        OutputType outputType = OutputType.FILE;

        for (int i = 0; i < userInputArr.length; i++) {

            String currentInput = userInputArr[i];

            if (isValidCommand(currentInput) && i < userInputArr.length - 1) {

                if (currentInput.equalsIgnoreCase(HASHED_PASS_FILE_CMD)) {

                    hashedPasswordFile = userInputArr[i+1];

                } else if (currentInput.equalsIgnoreCase(DICTIONARY_FILE_CMD)) {

                    dictionaryFile = userInputArr[i+1];

                } else if (currentInput.equalsIgnoreCase(COMMON_PASS_FILE_CMD)) {

                    commonPasswordsFile = userInputArr[i+1];

                } else if (currentInput.equalsIgnoreCase(HASH_TYPE)) {

                    hashType = determineHashType(userInputArr[i+1]);

                } else if (currentInput.equalsIgnoreCase(ATTACK_TYPE)) {

                    attackTypes = determineAttackTypes(userInputArr, i+1);

                } else if (currentInput.equalsIgnoreCase(OUTPUT_TYPE)) {

                    outputType = determineOutputType(userInputArr[i+1]);
                }
            }
        }

        apcInputInstructions.setHashedPasswordsFile(hashedPasswordFile);
        apcInputInstructions.setDictionaryFile(dictionaryFile);
        apcInputInstructions.setCommonPasswordsFile(commonPasswordsFile);
        apcInputInstructions.setHashType(hashType);
        apcInputInstructions.setOutputType(outputType);

        for(AttackType attackType : attackTypes) {
            apcInputInstructions.addAttackType(attackType);
        }

        return apcInputInstructions;
    }

    private HashType determineHashType(String hashTypeStr) {

        HashType[] hashTypes = HashType.values();

        for(HashType hashType : hashTypes) {
            if(hashType.toString().equalsIgnoreCase(hashTypeStr)) {
                return hashType;
            }
        }

        return null;
    }

    private OutputType determineOutputType(String outputTypeStr) {

        OutputType[] outputTypes = OutputType.values();

        for(OutputType outputType : outputTypes) {
            if(outputType.toString().equalsIgnoreCase(outputTypeStr)) {
                return outputType;
            }
        }

        return null;
    }

    private HashSet<AttackType> determineAttackTypes(String[] arr, int startIndex) {

        HashSet<AttackType> attackTypes = new HashSet<>();
        AttackType[] validAttackTypes = AttackType.values();

        for(int i = startIndex; i < arr.length; i++) {

            String potentialAttackType = arr[i];

            if(isValidCommand(potentialAttackType)) {
                break;
            }

            for(AttackType attackType : attackTypes) {
                if(attackType.toString().equalsIgnoreCase(potentialAttackType)) {
                    attackTypes.add(attackType);
                }
            }
        }

        return attackTypes;
    }

    private boolean isValidCommand(String str) {
        return validCommands.contains(str.toUpperCase());
    }
}
