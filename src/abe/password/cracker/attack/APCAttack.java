package abe.password.cracker.attack;

import abe.password.cracker.inputhandler.APCInputInstructions;

import java.util.HashSet;

public interface APCAttack {

    public void attack(APCInputInstructions apcInputInstructions, HashSet<String> passwordsToCrack);

}