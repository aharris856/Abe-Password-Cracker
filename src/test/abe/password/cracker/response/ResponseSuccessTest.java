package test.abe.password.cracker.response;

import abe.password.cracker.constants.ResponseType;
import abe.password.cracker.response.ResponseSuccess;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseSuccessTest {

    @Test
    void addAndGetCrackedPassword() {

        String crackedPass1 = "cp 1";
        String crackedPass2 = "cp 2";
        String crackedPass3 = "cp 3";

        ResponseSuccess responseSuccess = new ResponseSuccess();
        responseSuccess.addCrackedPassword(crackedPass1);
        responseSuccess.addCrackedPassword(crackedPass2);
        responseSuccess.addCrackedPassword(crackedPass3);

        HashSet<String> resultSet = responseSuccess.getCrackedPasswords();
        HashSet<String> expectedSet = new HashSet<>(List.of(crackedPass1, crackedPass2, crackedPass3));

        assertEquals(resultSet.size(), expectedSet.size());

        for(String crackedPassword : expectedSet) {
            resultSet.remove(crackedPassword);
        }

        assertEquals(0, resultSet.size());
    }

    @Test
    void testToStringEmpty() {

        String expected = "Response : " + ResponseType.APC_RESPONSE_SUCCESS+ " {\n" +
                "\tCracked Passwords {\n" +
                "\t\tNO PASSWORDS CRACKED\n" +
                "\t}\n" +
                "}";

        ResponseSuccess responseSuccess = new ResponseSuccess();

        assertEquals(expected, responseSuccess.toString());
    }

    @Test
    void testToString() {

        String cp1 = "cp 1";
        String cp2 = "cp 2";

        String crackedPasswordsStr = "\t\t"+cp1+"\n\t\t"+cp2+"\n";

        String expected = "Response : " + ResponseType.APC_RESPONSE_SUCCESS + " {\n" +
                "\tCracked Passwords {\n" +
                crackedPasswordsStr + "\t}\n" +
                "}";

        ResponseSuccess responseSuccess = new ResponseSuccess();
        responseSuccess.addCrackedPassword(cp1);
        responseSuccess.addCrackedPassword(cp2);

        assertEquals(expected, responseSuccess.toString());
    }
}