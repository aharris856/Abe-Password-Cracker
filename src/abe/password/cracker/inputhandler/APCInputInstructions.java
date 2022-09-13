package abe.password.cracker.inputhandler;

import abe.password.cracker.constants.AttackType;
import abe.password.cracker.constants.HashType;
import abe.password.cracker.constants.OutputType;

import java.util.HashSet;

public class APCInputInstructions {

    private String hashedPasswordsFile;

    private String dictionaryFile;

    private String commonPasswordsFile;

    private HashType hashType;

    private HashSet<AttackType> attackTypes = new HashSet<>();

    private OutputType outputType;


    public APCInputInstructions() {
        this.outputType = OutputType.FILE;
    }

// HASHED PASSWORD FILE -------------------------------------------------------
    public void setHashedPasswordsFile(String hashedPasswordsFile) {
        this.hashedPasswordsFile = hashedPasswordsFile;
    }

    public String getHashedPasswordsFile() {
        return hashedPasswordsFile;
    }
// DICTIONARY FILE ---------------------------------------------------------
    public void setDictionaryFile(String dictionaryFile) {
        this.dictionaryFile = dictionaryFile;
    }

    public String getDictionaryFile() {
        return dictionaryFile;
    }
// COMMON PASSWORD FILE -----------------------------------------------------
    public void setCommonPasswordsFile(String commonPasswordsFile) {
        this.commonPasswordsFile = commonPasswordsFile;
    }

    public String getCommonPasswordsFile() {
        return commonPasswordsFile;
    }
// HASH TYPE ----------------------------------------------------------------
    public void setHashType(HashType hashType) {
        this.hashType = hashType;
    }

    public HashType getHashType() {
        return hashType;
    }
// ATTACK TYPE ------------------------------------------------------------
    public void addAttackType(AttackType attackType) {
        attackTypes.add(attackType);
    }

    public HashSet<AttackType> getAttackTypes() {
        return attackTypes;
    }

    public void resetAttackTypes() {
        attackTypes = new HashSet<>();
    }
// OUTPUT TYPE -----------------------------------------------------------
    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public OutputType getOutputType() {
        return outputType;
    }

}