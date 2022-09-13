package test.abe.password.cracker.response;

import abe.password.cracker.constants.ResponseType;
import abe.password.cracker.response.ResponseFailed;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ResponseFailedTest {

    @Test
    void setGetErrorMessage() {

        ResponseFailed responseFailed = new ResponseFailed();

        String defaultMessage = "DEFAULT ERROR MESSAGE";
        assertEquals(responseFailed.getErrorMessage(), defaultMessage);

        String standardMessage = "Error: test message";
        responseFailed.setErrorMessage(standardMessage);
        assertEquals(standardMessage, responseFailed.getErrorMessage());

        responseFailed.setErrorMessage(null);
        assertNull(responseFailed.getErrorMessage());
    }

    @Test
    void testToString() {

        String errorMessage = "Error: test message";

        String expected = "Response : " + ResponseType.APC_RESPONSE_FAILED + " {\n" +
                "\tFailure Message {\n" +
                "\t\t" + errorMessage + "\n" +
                "\t}\n" +
                "}";

        ResponseFailed responseFailed = new ResponseFailed();
        responseFailed.setErrorMessage(errorMessage);

        assertEquals(expected, responseFailed.toString());
    }
}