package org.angrybee.website.publish.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PasswordGeneratorTest {
    @Test
    void testGeneratePassword() {

        String pswd1 = PasswordGenerator.generatePassword(12);
        String pswd2 = PasswordGenerator.generatePassword(12);

        assertEquals(pswd1.length(), pswd2.length());

        assertNotEquals(pswd1, pswd2);
    }

}
