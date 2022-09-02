package abe.password.cracker.controller;

import abe.password.cracker.inputhandler.APCInputInstructions;
import abe.password.cracker.inputhandler.UserInputHandler;
import abe.password.cracker.logic.APCLogicHandler;

public class APCController {

    public void parseUserInputAndAttemptAttack(String userInput) {

        if (userInput == null) {
            System.out.println("Please enter valid APC commands.");
            return;
        }

        UserInputHandler userInputHandler = new UserInputHandler();
        APCInputInstructions apcInputInstructions = userInputHandler.convertUserInputToAPCInputInstructions(userInput);
        attemptAttack(apcInputInstructions);
    }

    public void attemptAttack(APCInputInstructions apcInputInstructions) {

        if (apcInputInstructions == null) {
            System.out.println("Null APCInputInstructions object.");
            return;
        }

        APCLogicHandler apcLogicHandler = new APCLogicHandler();
        apcLogicHandler.executeAPCInputInstructions(apcInputInstructions);
    }
}
