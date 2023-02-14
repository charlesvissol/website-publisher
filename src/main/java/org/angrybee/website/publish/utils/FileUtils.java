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


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Utility class to manage files
 * @author Charles Vissol
 */
public class FileUtils {



    static final Logger logger = Logger.getLogger(FileUtils.class.getName());

	/**
	 * List of of files
	 */
	List<String> listFiles;
	
	

	
	/**
	 * Method to get the String content of a file
	 * @param file File object representing the file 
	 * @return String content of the file
	 */
    public static String getStrContent(File file) {
        
        StringBuilder result = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(file);)
        {
            
            byte[] buff = new byte[1024];
            int cnt = -1;
            while ((cnt = fis.read(buff)) != -1) {
                result.append(new String(buff, 0, cnt));
            }
        
        } catch (IOException e){
            logger.log(Level.SEVERE, e.getMessage(), e);
        } 
        
        return result.toString();
    }	


    /**
     * Retrieve the text file content in line by line
     * @param path Full path of the file
     * @return List of string lines
     */
    public static List<String> readLineByLine(String path) {
        
        List<String> listLines = new ArrayList<>();
        
        File file = new File(path);
        try(Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine())
                listLines.add(sc.nextLine());
                
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return listLines;
    }


    
    /**
     * Write a file from a direct stream content
     * 
     * @param sourceFile ImputStream of source file
     * @param pathFile File path to create.
     * @throws IOException
     */
    public static void writeFromStream(InputStream sourceFile, String pathFile) throws IOException{
    	
        
    	try (FileOutputStream lFos = new FileOutputStream(pathFile)) {
    	
	        
	        byte[] buff = new byte[1024];
	        int cnt = -1;

	        while ((cnt = sourceFile.read(buff)) != -1) {
	            lFos.write(buff, 0, cnt);
	        }            throw new IllegalArgumentException(String.format("File not found! %s ", pathFile));


            
    	} catch(IOException e) {
    		logger.log(Level.SEVERE, e.getMessage(), e);
    	}
    }
    
    /**
     * Write a text file directly from String content
     * 
     * @param file Full path of the file to write
     * @param fileContent String content to write in the file
     * @throws IOException
     */
    public static void writeFromString(String file, String fileContent) throws IOException
    {
        
        File fileDir = new File(file); 

    	try(Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir),StandardCharsets.UTF_8))) {
	    	
	    	out.append(fileContent);
	    	out.flush();
	    	
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}  	
    
    } 
    
    /**
     * Get recursive paths of files inside a directory
     * @param file Fichier de base pour le parcours.
     * @param isPath <b>true</b> if return the full path of files, <b>false</b> if return only the name of file
     */
    private void list(File file, boolean isPath)
    {
    	
        if (file.isDirectory()) 
        {
            File[] childs = file.listFiles();
            for (int i = 0; i < childs.length; i++)
            {
            	if(isPath)
            		list(childs[i], true);//Full path
            	else
            		list(childs[i], false);//only names
            }
        }
        if(isPath)
        	listFiles.add(file.getAbsolutePath());//Full path
        else
        	listFiles.add(file.getName());//Only names
    }
    
    /**
     * return a list of files names (format: $file.getName()) recursively obtained by 
     * analysis a root directory
     * @param file Root directory
     * @return List of String representing $file.getName()
     */
    public List<String> getFilesName(File file){
    	listFiles = new ArrayList<>();
    	list(file, false);
    	
    	return listFiles;
    }
    
    /**
     * return a list of files names (format: $file.getAbsolutePath()) recursively obtained by 
     * analysis a root directory
     * @param file Root directory
     * @return List of String representing $file.getAbsolutePath()
     */
    public List<String> getPath(File file){
    	listFiles = new ArrayList<>();
    	list(file, true);
    	
    	return listFiles;
    }
    

    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
    public InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    /*
        The resource URL is not working in the JAR
        If we try to access a file that is inside a JAR,
        It throws NoSuchFileException (linux), InvalidPathException (Windows)

        Resource URL Sample: file:java-io.jar!/json/file1.json
     */
    public File getFileFromResource(String fileName) throws URISyntaxException{

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            return new File(resource.toURI());
        }

    }
    
    /**
     * Duplicate a file 
     * @param original Original file to duplicate
     * @param duplicate Result of the file duplicated
     */
    public static void duplicate(String original, String duplicate){

        Path originalFile = Paths.get(original);
        Path duplicateFile = Paths.get(duplicate);
        try {
            Files.copy(originalFile,duplicateFile);
        } catch (IOException e) {
           logger.info(e.getMessage());
        }
    }


    /**
     * Delete recursively files and directory
     * @param directory Base directory to clean
     */
    public static boolean delDir(File directory) throws NoSuchFileException, DirectoryNotEmptyException, IOException {
        //TODO move from File to Files (java.nio.file.Files#delete)
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    delDir(file);
                }
            }
        }

        return directory.delete();
    }


}
