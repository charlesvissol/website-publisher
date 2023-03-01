package org.angrybee.website.publish.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {
    @Test
    void testGeneratePassword() {

        String pswd1 = PasswordGenerator.generatePassword(12);
        String pswd2 = PasswordGenerator.generatePassword(12);

        assertEquals(pswd1.length(), pswd2.length());

        assertFalse(pswd1.equals(pswd2));        

    }

}
