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

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Entry point of the application when used as common application
 * @author Charles Vissol
 */
public class Main {

    static final Logger logger = Logger.getLogger(Main.class.getName());

	/**
	 * properties file
	 */
	private static ResourceBundle resources;
    

    public static void main(String[] args) {

		//Initialization of the access to the properties file
		resources = ResourceBundle.getBundle(Main.class.getName());    
        String publisherClassname = resources.getString("publisher");    

        Class<Publisher> publisher = null;
        

        try {

            publisher = (Class<Publisher>) Class.forName(publisherClassname);
            publisher.getDeclaredConstructor().newInstance();

        } catch (ClassNotFoundException e) {
            logger.fine(e.getMessage());
        } catch (InstantiationException e) {
            
            logger.fine(e.getMessage());
        } catch (IllegalAccessException e) {
            
            logger.fine(e.getMessage());
        } catch (IllegalArgumentException e) {
            
            logger.fine(e.getMessage());
        } catch (InvocationTargetException e) {
            
            logger.fine(e.getMessage());
        } catch (NoSuchMethodException e) {
            
            logger.fine(e.getMessage());
        } catch (SecurityException e) {
            
            logger.fine(e.getMessage());
        }



    }

}
