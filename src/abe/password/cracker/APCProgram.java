//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
package abe.password.cracker;

import abe.password.cracker.controller.APCController;
import abe.password.cracker.userinterface.APCProgramUI;

public class APCProgram {

    public static void main(String[] args) {

        if (args.length == 0) {

            launchApplicationUI();

        } else {

            runApplicationWithArgs(String.join("", args));
        }

    }

    private static void runApplicationWithArgs(String userInput) {
        APCController apcController = new APCController();
        apcController.parseUserInputAndAttemptAttack(userInput);
    }

    private static void launchApplicationUI() {
        new APCProgramUI();
    }
}