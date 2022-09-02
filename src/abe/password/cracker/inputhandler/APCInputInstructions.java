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

    // for testing purposes --------------------------------------------------------
    public boolean equals(APCInputInstructions apcII) {

        return apcII.getHashedPasswordsFile().equals(this.hashedPasswordsFile) &&
                apcII.getDictionaryFile().equals(this.dictionaryFile) &&
                apcII.getCommonPasswordsFile().equals(this.commonPasswordsFile) &&
                apcII.getHashType() == this.hashType &&
                apcII.getOutputType() == this.outputType &&
                compareAttackTypes(apcII);
    }

    private boolean compareAttackTypes(APCInputInstructions apcII) {

        if (apcII.getAttackTypes().size() != attackTypes.size()) {
            return false;
        }

        for(AttackType attackType : attackTypes) {
            if(!apcII.getAttackTypes().contains(attackType)) {
                return false;
            }
        }

        return true;
    }

    // end testing used methods --------------------------------------------------

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
// OUTPUT TYPE -----------------------------------------------------------
    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public OutputType getOutputType() {
        return outputType;
    }
// -------------------------------------------------------------------------
//    public String toString() {
//        String hashTypeStr = hashType != null ? hashType.toString() : null;
//        String attackTypeStr = attackTypes != null ? attackTypes.toString() : null;
//        String outputTypeStr = outputType != null ? outputType.toString() : null;
//        return "--- APC Input Instructions ---\n" +
//                "Hashed Pass File : \"" + hashedPasswordsFile + "\"\n" +
//                "Dictionary File : \"" + dictionaryFile + "\"\n" +
//                "Common Pass File : \"" + commonPasswordsFile + "\"\n" +
//                "Hash Type : \"" + hashTypeStr + "\"\n" +
//                "Attack Types : \"" + attackTypeStr + "\"\n" +
//                "Output Type : \"" + outputTypeStr + "\"\n" +
//                "-------------------------------";
//    }
}