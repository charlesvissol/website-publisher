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

package org.angrybee.website.publish;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Entry point of the application.<br>
 * We use the Adapter Pattern.<br>
 * The {@link org.angrybee.website.publish.Main} class call {@link org.angrybee.website.publish.Publisher} interface 
 * implementation:<br>
 * <ul>
 * <li>By default it is {@link org.angrybee.website.publish.impl.PublisherDefault} class</li>
 * <li>By extension, it could be any custom implementation of {@link org.angrybee.website.publish.Publisher} interface</li>
 * </ul>
 * The classname of the {@link org.angrybee.website.publish.Publisher} implementation is in <code>Main.properties</code> but you can 
 * overwrite this class name by put the new classname in main() method argument:<br>
 * Example:<br>
 *     <code>java org.angrybee.website.publish.Main org.angrybee.website.publish.impl.PublisherCustom</code>
 * @author Charles Vissol
 */
public class Main {

    
    /**
     * Logger declaration
     */
    static final Logger logger = Logger.getLogger(Main.class.getName());

	/**
	 * properties file
	 */
	private static ResourceBundle resources;
    

    /**
     * Entry point of the application.
     * The main() method accept optionally one argument to overwrite the Main.properties value (Default 
     * {@link org.angrybee.website.publish.Publisher} implementation).
     * @param args {@link org.angrybee.website.publish.Publisher} implementation alternative
     */
    public static void main(String[] args) {

        String publisherClassname = null;

        /**
         * If no argument, get value in the properies file
         */
        if(args.length == 0){
            resources = ResourceBundle.getBundle(Main.class.getName());    
            publisherClassname = resources.getString("publisher");  
        } else {
            publisherClassname = args[0];
        }

		//Initialization of the access to the properties file
  

        Class<?> loadClass = null;
        Publisher publisher = null;
        Constructor<?> cons = null;

        try {

            /**
             * Here the program find the classname (stored in Main.properties by default or retrieved from command line argument
             */
            loadClass = Class.forName(publisherClassname);
            
            logger.log(Level.WARNING, "Publisher implementation classname is {0}", publisherClassname);
            
            cons = loadClass.getDeclaredConstructor();
            
            publisher = (Publisher) cons.newInstance();
            

        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (InstantiationException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (InvocationTargetException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (SecurityException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        /**
         * Call the Interface method but run the class method (Adaptor pattern)
         */
        
        if(publisher != null) {
            logger.log(Level.WARNING, "Publisher implementation variable name is {0}",publisher.toString());

            publisher.publish();

        } else {
            logger.log(Level.SEVERE, "Publisher implementation variable is Null");
        }
            

    }

}
