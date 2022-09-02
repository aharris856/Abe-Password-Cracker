package test.abe.password.cracker.inputhandler;

import abe.password.cracker.constants.AttackType;
import abe.password.cracker.constants.HashType;
import abe.password.cracker.constants.OutputType;
import abe.password.cracker.inputhandler.APCInputInstructions;
import abe.password.cracker.inputhandler.UserInputHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInputHandlerTest {

    @Test
    void convertUserInputToAPCInputInstructions() {

        UserInputHandler userInputHandler = new UserInputHandler();

        String hashedPassFile = "C:\\random dir\\hashed_pass_file.txt";
        String commonPassFile = "C:\\random dir\\common_pass_file.txt";
        String dictionaryFile = "C:\\random dir\\dictionary.txt";

        String inputTest1 = "-hPf "+hashedPassFile+" -cpf "+commonPassFile+" -df "+dictionaryFile+" -at brute_force -ht md5";
        APCInputInstructions inputInstructionsTest1 = userInputHandler.convertUserInputToAPCInputInstructions(inputTest1);

        assertEquals(inputInstructionsTest1.getHashedPasswordsFile(), hashedPassFile);
        assertEquals(inputInstructionsTest1.getCommonPasswordsFile(), commonPassFile);
        assertEquals(inputInstructionsTest1.getDictionaryFile(), dictionaryFile);
        assertEquals(inputInstructionsTest1.getHashType(), HashType.MD5);
        assertEquals(inputInstructionsTest1.getOutputType(), OutputType.FILE); //default
        assertTrue(
                inputInstructionsTest1.getAttackTypes().size() == 1 &&
                        inputInstructionsTest1.getAttackTypes().contains(AttackType.BRUTE_FORCE)
        );

        String inputTest2 = "-hPf "+hashedPassFile+" -df "+dictionaryFile+" -at brute_force common_passwords dictionary -ot sysout -ht sha1";
        APCInputInstructions inputInstructionsTest2 = userInputHandler.convertUserInputToAPCInputInstructions(inputTest2);

        assertEquals(inputInstructionsTest2.getHashedPasswordsFile(), hashedPassFile);
        assertNull(inputInstructionsTest2.getCommonPasswordsFile());
        assertEquals(inputInstructionsTest2.getDictionaryFile(), dictionaryFile);
        assertEquals(inputInstructionsTest2.getHashType(), HashType.SHA1);
        assertEquals(inputInstructionsTest2.getOutputType(), OutputType.SYSOUT);
        assertEquals(inputInstructionsTest2.getAttackTypes().size(), 3);

        assertTrue(
                inputInstructionsTest2.getAttackTypes().size() == 3 &&
                inputInstructionsTest2.getAttackTypes().contains(AttackType.BRUTE_FORCE) &&
                inputInstructionsTest2.getAttackTypes().contains(AttackType.COMMON_PASSWORDS) &&
                inputInstructionsTest2.getAttackTypes().contains(AttackType.DICTIONARY)
        );
    }
}