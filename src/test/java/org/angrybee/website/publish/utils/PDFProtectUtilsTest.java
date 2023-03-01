package org.angrybee.website.publish.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.apache.commons.lang.SystemUtils;
import org.junit.jupiter.api.Test;

public class PDFProtectUtilsTest {
    @Test
    void testMain() {

    }

    @Test
    void testProtect() {

        File inputPdf = new File(this.getClass().getClassLoader().getResource("publish-pdf-input.pdf").getFile());

        String ownerPassword = PasswordGenerator.generatePassword(12);
            
        String userPassword = PasswordGenerator.generatePassword(12);


        /**
         * Create the working directory
         */
        Path tempPath = null;
        
            //By default creates the working directory
            try {

                String prefix = "publisher";

                if(SystemUtils.IS_OS_UNIX) {
                    FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
                    tempPath = Files.createTempDirectory(prefix, attr); // Compliant
                  }
                  else {
                    tempPath = Files.createTempDirectory("publisher");
                  }

                System.out.println("Default working directory is: " + tempPath);
            } catch (IOException e) {
                e.printStackTrace();
            }    
        



    }
}
