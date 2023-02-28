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

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

/**
 * Utility class dedicated to the protection of PDF files
 * You can directly call it using command line:
 * <pre><code>
 * 
 *      java org.angrybee.website.publish.utils.PDFProtectUtils $input-pdf $output-pdf $owner-password $user-password
 * 
 * </code></pre>
 * <ul>
 * <li><code>$input-pdf</code>: Input PDF file to encrypt</li>
 * <li><code>$output-pdf</code>: Encrypted output PDF file</li>
 * <li><code>$owner-password</code>: Text representing the owner password</li>
 * <li><code>$user-password</code>: Text representing the user password</li>
 * </ul> 
 * 
 */
public class PDFProtectUtils {
    
	/**
	 * Logger initialization
	 */
	static final Logger logger = Logger.getLogger(PDFProtectUtils.class.getName());

    /**
	 * Default constructor
	 */
	private PDFProtectUtils(){
		/* Default constructor */
	}

    /**
     * <code>main()</code> method to call directly from command line to generate an encrypted PDF.
     * Encrypted PDF does not overwrite the input PDF.
     * @param args The method accept 3 mandatory arguments: {@code <input-pdf>} {@code <output-pdf>} {@code <owner-password} {@code <user-password>}
     */
    public static void main(String[] args) {
        if (args.length != 4)
        {
            usage();
        }
        else
        {
            PDFProtectUtils.protect(args[0], args[1], args[2], args[3]);
        }
            
    }

    /**
     * Perform the encryption of the PDF 
     * @param inputPdf Full path of the input PDF (PDF to encrypt)
     * @param outputPdf Full path of the new encrypted output PDF 
     * @param ownerPassword Password of the owner
     * @param userPassword Password of the user
     */
    public static void protect(String inputPdf, String outputPdf, String ownerPassword, String userPassword){

        File srcFile = new File(inputPdf);
        PDDocument permissionPdf = null;
        
        try 
        {
            
            permissionPdf = PDDocument.load(srcFile);
            AccessPermission ap = new AccessPermission();
            
            ap.setCanModify(false);
            ap.setCanExtractContent(false);
            ap.setCanPrint(false);
            ap.setReadOnly();

            StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPassword, userPassword, ap);

            spp.setPermissions(ap);


            permissionPdf.protect(spp);
                     
            permissionPdf.save(outputPdf);
            permissionPdf.close();


        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } 

    }

    /**
     * This will print the usage.
     */
    private static void usage()
    {
        logger.log(Level.INFO, "Usage: java {0} <input-pdf> <output-pdf> <owner password> <user password>", PDFProtectUtils.class.getName());
    }


}
