package org.angrybee.website.publish.utils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

class FileUtilsTest {

    static final Logger logger = Logger.getLogger(FileUtilsTest.class.getName());



    @Test
    void testGetFileFromResourceAsStream(){

        FileUtils fUtils = new FileUtils();
        InputStream iStream = fUtils.getFileFromResourceAsStream("publish-pdf-input.pdf");

        assertNotNull(iStream);

    }



    /**
     * Test of FileUtils.getStrContent() method based on hashcode
     */
    @Test
    void testGetStrContent(){
        
        File file = this.getFile("GetStrContent.txt");
        int expected = 204710453;


        String content = FileUtils.getStrContent(file);

        assertEquals(expected, content.hashCode());
        
    }

    @Test
    void testConvert(){

        String content = "<html>\n<body>\n<p>My Content</p>\n</body>\n</html>";

        String tempDir = System.getProperty("java.io.tmpdir");

        File fOriginal = new File(tempDir + File.separator + "test.txt");
        File fCopy =  new File(tempDir + File.separator + "test-copy.txt");

        logger.info(fOriginal.getPath());
        logger.info(fCopy.getPath());

        if(fOriginal.exists()){
            fOriginal.delete();
        }

        if(fCopy.exists()){
            fCopy.delete();
        }


        try {
            FileUtils.writeFromString(tempDir + File.separator + "test.txt", content);
        } catch (IOException e) {
            e.printStackTrace();
        }



        FileUtils.duplicate(tempDir + File.separator + "test.txt", tempDir + File.separator + "test-copy.txt");        

        String original = null;
        String copy = null;

        original = FileUtils.getStrContent(new File(tempDir + File.separator + "test.txt"));
        copy = FileUtils.getStrContent(new File(tempDir + File.separator + "test-copy.txt"));
        
        
        assertEquals(original, copy);


    }


    @Test
    void testGetFileFromResource(){
        
        File markdown = null;
        try {
            markdown = new FileUtils().getFileFromResource("cgroups.md");
        } catch (URISyntaxException e) {
            
            e.printStackTrace();
        }

        System.out.println(markdown.getAbsolutePath());
        System.out.println(markdown.exists());
        System.out.println(markdown.getName());

        assertTrue("cgroups.md".equalsIgnoreCase(markdown.getName()));

        assertTrue(markdown.exists());
    }



	/**
	 * Return the class loader to get resources files
	 * @return class loader for the {@link org.angrybee.website.publish.Publisher} class
	 */
	private ClassLoader getClassLoader(){
		return getClass().getClassLoader();
	}


	/**
	 * Get {@link java.io.File} instance using the class loader.
	 * Means the file is in src/main/resources 
	 * @param filename Full Path of the file 
	 * @return File in the class loader resource directory
	 */
	private File getFile(String filename){
		return new File(getClassLoader().getResource(filename).getFile());
	}





}
