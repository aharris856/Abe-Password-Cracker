package abe.password.cracker.userinterface;

import abe.password.cracker.constants.AttackType;
import abe.password.cracker.constants.HashType;
import abe.password.cracker.constants.OutputType;
import abe.password.cracker.controller.APCController;
import abe.password.cracker.inputhandler.APCInputInstructions;

import javax.swing.*;
import java.util.HashSet;

public class APCProgramUI extends JFrame {
    private final APCController controller = new APCController();
    private JTextField hashedPasswordFileTextField;
    private JTextField dictionaryFileTextField;
    private JTextField commonPasswordsFileTextField;
    private JRadioButton md5RadioButton;
    private JRadioButton sha1RadioButton;
    private JRadioButton sha256RadioButton;
    private JCheckBox bruteForceAttackCheckBox;
    private JCheckBox dictionaryAttackCheckBox;
    private JCheckBox commonPasswordsAttackCheckBox;
    private JCheckBox hybridDictionaryAttackCheckBox;
    private JLabel hashedPasswordFileLabel;
    private JLabel dictionaryFileLabel;
    private JLabel commonPasswordsFileLabel;
    private JLabel hashingAlgorithmLabel;
    private JLabel attackMethodsLabel;
    private JButton attackButton;
    private JPanel apcApplicationPanel;
    private JLabel responseLabel;

    public APCProgramUI() {

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(md5RadioButton);
        buttonGroup.add(sha1RadioButton);
        buttonGroup.add(sha256RadioButton);

        setContentPane(apcApplicationPanel);
        setTitle("APC Application");
        setSize(850, 550);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        attackButton.addActionListener((event) -> {
            setResponse("Launching Attack!");
            launchAttack();
        });
    }

    private void launchAttack() {

        String hashedPasswordFile = hashedPasswordFileTextField.getText();
        String dictionaryFile = dictionaryFileTextField.getText();
        String commonPasswordsFile = commonPasswordsFileTextField.getText();

        if (hashedPasswordFile.equals("")) {
            setResponse("Error: Please enter a hashed password file path.");
            return;
        }

        if (dictionaryFile.equals("")) {
            setResponse("Error: Please enter a dictionary file path.");
            return;
        }

        if (commonPasswordsFile.equals("")) {
            setResponse("Error: Please enter a common passwords file path.");
            return;
        }

        HashType hashType = md5RadioButton.isSelected() ? HashType.MD5 :
                sha1RadioButton.isSelected() ? HashType.SHA1 :
                sha256RadioButton.isSelected() ? HashType.SHA256 : null;

        if (hashType == null) {
            setResponse("Error: Please select a hashing algorithm.");
            return;
        }

        APCInputInstructions apcInputInstructions = new APCInputInstructions();
        apcInputInstructions.setHashedPasswordsFile(hashedPasswordFile);
        apcInputInstructions.setDictionaryFile(dictionaryFile);
        apcInputInstructions.setCommonPasswordsFile(commonPasswordsFile);
        apcInputInstructions.setHashType(hashType);
        apcInputInstructions.setOutputType(OutputType.FILE);


        if (commonPasswordsAttackCheckBox.isSelected()) {
            attemptAttackUsingInput(apcInputInstructions, AttackType.COMMON_PASSWORDS, "Attempting common passwords attack.");
        }

        if (dictionaryAttackCheckBox.isSelected()) {
            attemptAttackUsingInput(apcInputInstructions, AttackType.DICTIONARY, "Attempting dictionary attack.");
        }

        if(hybridDictionaryAttackCheckBox.isSelected()) {
            attemptAttackUsingInput(apcInputInstructions, AttackType.HYBRID_DICTIONARY, "Attempting hybrid dictionary attack.");
        }

        if (bruteForceAttackCheckBox.isSelected()) {
            attemptAttackUsingInput(apcInputInstructions, AttackType.BRUTE_FORCE, "Attempting brute force attack.");
        }

        setResponse("Attack(s) Complete.");
    }

    private void attemptAttackUsingInput(APCInputInstructions apcInputInstructions, AttackType attackType, String message) {
        apcInputInstructions.resetAttackTypes();
        apcInputInstructions.addAttackType(attackType);
        setResponse(message);
        controller.attemptAttack(apcInputInstructions);
    }

    private void setResponse(String message) {
        responseLabel.setText(message);
        super.update(getGraphics());
    }
}
