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
    void testEquals() {

        String fail = "fail";

        String hpf = "hpf";
        String df = "df";
        String cpf = "cpf";
        HashType sha1 = HashType.SHA1;
        HashType sha256 = HashType.SHA256;
        OutputType sysout = OutputType.SYSOUT;
        OutputType file = OutputType.FILE;
        AttackType da = AttackType.DICTIONARY;
        AttackType cpa = AttackType.COMMON_PASSWORDS;
        AttackType bfa = AttackType.BRUTE_FORCE;

        APCInputInstructions apcInputInstructions1 = new APCInputInstructions();
        APCInputInstructions apcInputInstructions2 = new APCInputInstructions();

        apcInputInstructions1.setHashedPasswordsFile(hpf);
        apcInputInstructions1.setDictionaryFile(df);
        apcInputInstructions1.setCommonPasswordsFile(cpf);
        apcInputInstructions1.setHashType(sha1);
        apcInputInstructions1.setOutputType(sysout);
        apcInputInstructions1.addAttackType(da);
        apcInputInstructions1.addAttackType(cpa);

        apcInputInstructions2.setHashedPasswordsFile(hpf);
        apcInputInstructions2.setDictionaryFile(df);
        apcInputInstructions2.setCommonPasswordsFile(cpf);
        apcInputInstructions2.setHashType(sha1);
        apcInputInstructions2.setOutputType(sysout);
        apcInputInstructions2.addAttackType(da);
        apcInputInstructions2.addAttackType(cpa);

        assertTrue(apcInputInstructions1.equals(apcInputInstructions2));

        // ------------ does not equal cases -----------------------

        //hashed pass file
        apcInputInstructions2.setHashedPasswordsFile(hpf+fail);
        assertFalse(apcInputInstructions1.equals(apcInputInstructions2));
        apcInputInstructions2.setHashedPasswordsFile(hpf);
        assertTrue(apcInputInstructions1.equals(apcInputInstructions2));

        //dictionary file
        apcInputInstructions2.setDictionaryFile(df+fail);
        assertFalse(apcInputInstructions1.equals(apcInputInstructions2));
        apcInputInstructions2.setDictionaryFile(df);
        assertTrue(apcInputInstructions1.equals(apcInputInstructions2));

        //common passwords file
        apcInputInstructions2.setCommonPasswordsFile(cpf+fail);
        assertFalse(apcInputInstructions1.equals(apcInputInstructions2));
        apcInputInstructions2.setCommonPasswordsFile(cpf);
        assertTrue(apcInputInstructions1.equals(apcInputInstructions2));

        //hash type
        apcInputInstructions2.setHashType(sha256);
        assertFalse(apcInputInstructions1.equals(apcInputInstructions2));
        apcInputInstructions2.setHashType(sha1);
        assertTrue(apcInputInstructions1.equals(apcInputInstructions2));

        //output type
        apcInputInstructions2.setOutputType(file);
        assertFalse(apcInputInstructions1.equals(apcInputInstructions2));
        apcInputInstructions2.setOutputType(sysout);
        assertTrue(apcInputInstructions1.equals(apcInputInstructions2));

        //attack type
        apcInputInstructions2.addAttackType(bfa);
        assertFalse(apcInputInstructions1.equals(apcInputInstructions2));
    }

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