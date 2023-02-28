/** Copyright 2023 Angrybee.tech (https://angrybee.tech)

Licensed under the Apache License, Version 2.0 (the ''License'');
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an ''AS IS'' BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.angrybee.website.publish.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class to generate random password
 * With the help of ChatGPT :-) but some refacoring to make it runnable
 * 
 * @author ChatGPT & Charles Vissol
 */
public class PasswordGenerator {

    private static final Random RANDOM = new SecureRandom();

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    private static final String PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE);



    /**
     * main() method to call directly this class
     * @param args {@code <integer>} Lenght of the generated password
     */
    public static void main(String... args) {
        
        String password = generatePassword(Integer.valueOf(args[0]));
        System.out.println("Password: " + password);
    }

    /**
     * Main method to generate Random password
     * @param length Integer representing the lenght of the password
     * @return Password in String
     */
    public static String generatePassword(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        sb.append(CHAR_LOWER.charAt(RANDOM.nextInt(CHAR_LOWER.length())));
        sb.append(CHAR_UPPER.charAt(RANDOM.nextInt(CHAR_UPPER.length())));
        sb.append(NUMBER.charAt(RANDOM.nextInt(NUMBER.length())));
        sb.append(OTHER_CHAR.charAt(RANDOM.nextInt(OTHER_CHAR.length())));

        for (int i = 4; i < length; ++i)
            sb.append(PASSWORD_ALLOW_BASE_SHUFFLE.charAt(RANDOM.nextInt(PASSWORD_ALLOW_BASE_SHUFFLE.length())));

        return sb.toString();
    }

    /**
     * Method to shuffle strings
     * @param string String to shuffle
     * @return String shuffled
     */
    public static String shuffleString(String string) {
        char[] charArray = string.toCharArray();
        for (int i = charArray.length - 1; i > 0; i--) {
            int index = RANDOM.nextInt(i + 1);
            char tmp = charArray[index];
            charArray[index] = charArray[i];
            charArray[i] = tmp;
        }
        return new String(charArray);
    }
}

