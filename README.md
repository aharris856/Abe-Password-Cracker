        APC (Abe Password Cracker) is a password cracking application written in java. 
        Currently APC supports Brute force, dictionary, and common password attacks using MD5, SHA-1, and SHA-256 algorithms.

        There are 2 ways to run the application.

        1. Via the user interface. To do so run the program with no arguments

        2. The command line directly, in which case you can use the following arguments
            -hpf PATH_TO_FILE_CONTAINING HASHED PASSWORDS
            -df PATH_TO_DICTIONARY_FILE
            -cpf PATH_TO_COMMON_PASSWORDS_FILE
            -ht HASHING_ALGORITHM
            -at ATTACK_METHODS
            -ot OUTPUT_TYPE

            Example of a valid argument (can be done in any order):
            -hpf C:\tmp\hashed_pass.txt -df C:\tmp\words.txt -cpf C:\tmp\rockyou.txt -ht md5 -at brute_force dictionary common_passwords -ot sysout

            The above example will: 
            Assign C:\tmp\hashed_pass.txt as the hashed password file.
            Assign C:\tmp\words.txt as the dictionary file.
            Assign C:\tmp\rockyou.txt as the common passwords file.
            Use the md5 hashing algorithm.
            Attempt a dictionary attack, common passwords attack, and brute force attack.
            Put any cracked password responses in the command prompt.

            VALID ARGUMENTS FOR HASHING ALGORITHM, ATTACK METHOD, AND OUTPUT TYPE
            _______________________________________________________
            |     -ht     |         -at           |     -ot       |
            |_____________|_______________________|_______________|
            |     MD5     |    common_passwords   |     sysout    |
            |    SHA-1    |      dictionary       |      file     |
            |   SHA-256   |      brute_force      |               |
            |             |   hybrid_dictionary   |               |
            |_____________|_______________________|_______________|
    
    The path in which "-ot file" will place responses is in: APPLICATION_DIRECTORY\APC_Responses\
    
