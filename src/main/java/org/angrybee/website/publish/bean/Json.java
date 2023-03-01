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

package org.angrybee.website.publish.bean;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class to:
 * <ul>
 * <li>read Json file to load {@link org.angrybee.website.publish.bean.PublisherBean} implementations</li>
 * <li>Write {@link org.angrybee.website.publish.bean.PublisherBean} implementations to store attributes in a Json file</li>
 * </ul>
 * 
 * @author Charles Vissol
 */
public class Json {

    /**
     * Private consructor to avoid instantiation
     */
    private Json(){}
    
	/**
	 * Logger initialization
	 */
	static final Logger logger = Logger.getLogger(Json.class.getName());    

    /**
     * Read Json file to load parameters into {@link org.angrybee.website.publish.bean.PublisherBean} implementation
     * @param jsonPath Full path of the Json file
     * @param bean {@link org.angrybee.website.publish.bean.PublisherBean} implementation
     * @return {@link org.angrybee.website.publish.bean.PublisherBean} implementation with data
     */
    public static PublisherBean read(String jsonPath, PublisherBean bean){

        ObjectMapper mapper = new ObjectMapper();
        File json = new File(jsonPath);
        
        try {

            bean = mapper.readValue(json, bean.getClass());

        } catch (IOException | NullPointerException e) {
           logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return bean;
    }



    /**
     * Write Json file from {@link org.angrybee.website.publish.bean.PublisherBean} implementation to 
     * save the parameters
     * @param jsonPath Full path of the Json file
     * @param bean {@link org.angrybee.website.publish.bean.PublisherBean} implementation with parameters 
     */
    public static void write(String jsonPath, PublisherBean bean){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonPath), bean);

            
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            
        } 

    }

}
