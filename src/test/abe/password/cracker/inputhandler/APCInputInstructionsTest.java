package test.abe.password.cracker.inputhandler;

import abe.password.cracker.constants.AttackType;
import abe.password.cracker.constants.HashType;
import abe.password.cracker.constants.OutputType;
import abe.password.cracker.inputhandler.APCInputInstructions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class APCInputInstructionsTest {

    @Test
    void getHashedPasswordsFile() {

        String hashedPassFile = "hpf";

        APCInputInstructions inputInstructions = new APCInputInstructions();

        assertNull(inputInstructions.getHashedPasswordsFile());

        inputInstructions.setHashedPasswordsFile(hashedPassFile);

        assertEquals(hashedPassFile, inputInstructions.getHashedPasswordsFile());
    }

    @Test
    void getDictionaryFile() {

        String dictionaryFile = "df";

        APCInputInstructions inputInstructions = new APCInputInstructions();

        assertNull(inputInstructions.getDictionaryFile());

        inputInstructions.setDictionaryFile(dictionaryFile);

        assertEquals(dictionaryFile, inputInstructions.getDictionaryFile());
    }

    @Test
    void getCommonPasswordsFile() {

        String commonPassFile = "cpf";

        APCInputInstructions inputInstructions = new APCInputInstructions();

        assertNull(inputInstructions.getCommonPasswordsFile());

        inputInstructions.setCommonPasswordsFile(commonPassFile);

        assertEquals(commonPassFile, inputInstructions.getCommonPasswordsFile());
    }

    @Test
    void getHashType() {

        HashType hashType = HashType.SHA256;

        APCInputInstructions inputInstructions = new APCInputInstructions();

        assertNull(inputInstructions.getHashType());

        inputInstructions.setHashType(hashType);

        assertEquals(hashType, inputInstructions.getHashType());
    }

    @Test
    void getAttackTypes() {

        HashSet<AttackType> expected = new HashSet<>();

        AttackType attackType1 = AttackType.DICTIONARY;
        AttackType attackType2 = AttackType.BRUTE_FORCE;
        AttackType attackType3 = AttackType.COMMON_PASSWORDS;
        AttackType duplicate = AttackType.BRUTE_FORCE;

        APCInputInstructions inputInstructions = new APCInputInstructions();

        assertTrue( compareAttackTypes(expected, inputInstructions.getAttackTypes()) );

        inputInstructions.addAttackType(attackType1);
        expected.add(attackType1);

        assertTrue( compareAttackTypes(expected, inputInstructions.getAttackTypes()) );

        inputInstructions.addAttackType(attackType2);
        expected.add(attackType2);

        assertTrue( compareAttackTypes(expected, inputInstructions.getAttackTypes()) );

        inputInstructions.addAttackType(attackType3);
        expected.add(attackType3);

        assertTrue( compareAttackTypes(expected, inputInstructions.getAttackTypes()) );

        inputInstructions.addAttackType(duplicate);

        assertTrue( compareAttackTypes(expected, inputInstructions.getAttackTypes()) );
    }

    @Test
    void clearAttackTypes() {

        APCInputInstructions inputInstructions = new APCInputInstructions();
        HashSet<AttackType> expected = new HashSet<>();

        assertTrue( compareAttackTypes(expected, inputInstructions.getAttackTypes()) );

        AttackType attackType1 = AttackType.DICTIONARY;
        AttackType attackType2 = AttackType.BRUTE_FORCE;

        expected.add(attackType1);
        inputInstructions.addAttackType(attackType1);

        expected.add(attackType2);
        inputInstructions.addAttackType(attackType2);

        assertTrue( compareAttackTypes(expected, inputInstructions.getAttackTypes()) );

        expected = new HashSet<>();
        inputInstructions.resetAttackTypes();

        assertTrue( compareAttackTypes(expected, inputInstructions.getAttackTypes()) );
    }

    @Test
    void getOutputType() {

        APCInputInstructions inputInstructions = new APCInputInstructions();

        assertEquals(OutputType.FILE, inputInstructions.getOutputType());

        inputInstructions.setOutputType(OutputType.SYSOUT);

        assertEquals(OutputType.SYSOUT, inputInstructions.getOutputType());
    }

    private boolean compareAttackTypes(HashSet<AttackType> set1, HashSet<AttackType> set2) {
        if(set1.size() != set2.size()) {
            return false;
        }

        for(AttackType attackType : set1) {
            if(!set2.contains(attackType)) {
                return false;
            }
        }

        return true;
    }
}