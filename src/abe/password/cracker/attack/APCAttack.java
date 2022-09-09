package abe.password.cracker.attack;

import abe.password.cracker.constants.OutputType;
import abe.password.cracker.inputhandler.APCInputInstructions;
import abe.password.cracker.response.ResponseFailed;
import abe.password.cracker.response.ResponseSuccess;

import java.io.IOException;
import java.util.HashSet;

public interface APCAttack {

    public void attack(APCInputInstructions apcInputInstructions, HashSet<String> passwordsToCrack);

}