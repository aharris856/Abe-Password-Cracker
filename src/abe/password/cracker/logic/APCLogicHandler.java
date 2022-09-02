package abe.password.cracker.logic;

import abe.password.cracker.attack.APCAttack;
import abe.password.cracker.attack.BruteForceAttack;
import abe.password.cracker.attack.CommonPasswordsAttack;
import abe.password.cracker.attack.DictionaryAttack;
import abe.password.cracker.constants.AttackType;
import abe.password.cracker.inputhandler.APCInputInstructions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class APCLogicHandler {

    public void executeAPCInputInstructions(APCInputInstructions inputInstructions) {

        HashSet<String> passwordsToCrack = readHashedPasswordFile(inputInstructions);

        if(passwordsToCrack == null) {
            return;
        }

        HashSet<AttackType> attackTypes = inputInstructions.getAttackTypes();

        if (attackTypes == null) {
            return;
        }

        if (attackTypes.contains(AttackType.DICTIONARY)) {
            executeDictionaryAttack(inputInstructions, passwordsToCrack);
        }

        if (attackTypes.contains(AttackType.COMMON_PASSWORDS)) {
            executeCommonPasswordsAttack(inputInstructions, passwordsToCrack);
        }

        if (attackTypes.contains(AttackType.BRUTE_FORCE)) {
            executeBruteForceAttack(inputInstructions, passwordsToCrack);
        }

    }

    private HashSet<String> readHashedPasswordFile(APCInputInstructions inputInstructions) {

        String hashedPasswordFileStr = inputInstructions.getHashedPasswordsFile();

        if (hashedPasswordFileStr == null) {
            System.out.println("Input hashed password file using the \"-hpf\" command. ");
            return null;
        }

        try {

            BufferedReader hashedPasswordReader = new BufferedReader( new FileReader ( hashedPasswordFileStr ) );
            HashSet<String> hashedPasswords = new HashSet<>();

            while (hashedPasswordReader.ready()) {
                String hashedPassword = hashedPasswordReader.readLine();
                hashedPasswords.add(hashedPassword);
            }

            hashedPasswordReader.close();

            return hashedPasswords;

        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }

    }

    private void executeDictionaryAttack(APCInputInstructions inputInstructions, HashSet<String> passwordsToCrack) {
        DictionaryAttack dictionaryAttack = new DictionaryAttack();
        executeAttack(dictionaryAttack, inputInstructions, passwordsToCrack);
    }

    private void executeCommonPasswordsAttack(APCInputInstructions inputInstructions, HashSet<String> passwordsToCrack) {
        CommonPasswordsAttack commonPasswordsAttack = new CommonPasswordsAttack();
        executeAttack(commonPasswordsAttack, inputInstructions, passwordsToCrack);
    }

    private void executeBruteForceAttack(APCInputInstructions inputInstructions, HashSet<String> passwordsToCrack) {
        BruteForceAttack bruteForceAttack = new BruteForceAttack();
        executeAttack(bruteForceAttack, inputInstructions, passwordsToCrack);
    }

    private void executeAttack(APCAttack attacker, APCInputInstructions inputInstructions, HashSet<String> passwordsToCrack) {
        attacker.attack(inputInstructions, passwordsToCrack);
    }
}