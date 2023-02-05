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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.angrybee.website.publish.bean.Json;
import org.angrybee.website.publish.bean.PublisherBean;


/**
 * Entry point of the application.<br>
 * We use the Adapter Pattern.<br>
 * The {@link org.angrybee.website.publish.WebSitePublisherMain} class call {@link org.angrybee.website.publish.Publisher} interface 
 * implementation:<br>
 * <ul>
 * <li>By default it is {@link org.angrybee.website.publish.impl.PublisherDefault} class</li>
 * <li>By extension, it could be any custom implementation of {@link org.angrybee.website.publish.Publisher} interface</li>
 * </ul>
 * The classname of the {@link org.angrybee.website.publish.Publisher} implementation is in <code>Main.properties</code> but you can 
 * overwrite this class name by put the new classname in main() method argument:<br>
 * Example:<br>
 *     <code>
 *          
 *              java org.angrybee.website.publish.WebSitePublisherMain org.angrybee.website.publish.impl.PublisherDefault org.angrybee.website.publish.bean.PublisherDefaultBean /my/path/pub.json
 *     </code>
 * 
 * @author Charles Vissol
 */
public class WebSitePublisherMain {

    //@TODO @charlesvissol: Add the path parameter to json -> Json used to set all the input values
    /**
     * Logger declaration
     */
    static final Logger logger = Logger.getLogger(WebSitePublisherMain.class.getName());
    
    private Publisher publisher;
    private PublisherBean publisherBean;


    /**
     * You can run the main() method directly but you need 3 arguments:
     * <ul>
     *  <li>1st argument: fullname of the Publisher implementation (default is "org.angrybee.website.publish.impl.PublisherDefault")</li>
     *  <li>2nd argument: fullname of the PublisherBean implementation (default is "org.angrybee.website.publish.bean.PublisherDefaultBean")</li>
     *  <li>3rd argument: full path of JSon file
     * @param args
     */
    public static void main(String[] args) {

        /**
         * Verification of the arguments
         */
        if(args[0] == null){
            logger.log(Level.SEVERE, "1st argument cannot be null. Default should be \"org.angrybee.website.publish.impl.PublisherDefault\"");
            throw new IllegalArgumentException("1st argument cannot be null. Default should be \"org.angrybee.website.publish.impl.PublisherDefault\"");
        }
            

        if(args[1] == null){
            logger.log(Level.SEVERE, "2nd argument cannot be null. Default should be \"org.angrybee.website.publish.bean.PublisherDefaultBean\"");
            throw new IllegalArgumentException("2nd argument cannot be null. Default should be \"org.angrybee.website.publish.bean.PublisherDefaultBean\"");
        }
            

        if(args[2] == null){
            logger.log(Level.SEVERE, "3rd argument cannot be null. Default should be the full path to the Json file");
            throw new IllegalArgumentException("3rd argument cannot be null. Default should be the full path to the Json file");
        }
            

        /**
         * Publishing process execution
         */
        WebSitePublisherMain wsMain = new WebSitePublisherMain();
        
        wsMain.getPublisher(args[0]);

        wsMain.getBean(args[1], args[2]);

        wsMain.publish();

    }

	/**
	 * Load the {@link org.angrybee.website.publish.Publisher} implementation from 
     * the String name of the implementation.
     * @param publisher {@link org.angrybee.website.publish.Publisher} implementation
	 */
	private void getPublisher(String publisher){
        try {

            Class<?> clazz = Class.forName(publisher);
            this.publisher = (Publisher) clazz.getDeclaredConstructor().newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
		
	}


	/**
	 * Load the {@link org.angrybee.website.publish.bean.PublisherBean} implementation from 
     * the String name of the implementation.
     * @param publisherBean {@link org.angrybee.website.publish.bean.PublisherBean} implementation
     * @param jsonPath Full path of the Json file to load data into {@link org.angrybee.website.publish.bean.PublisherBean} implementation
	 */
	private void getBean(String publisherBean, String jsonPath){
        try {

            Class<?> clazz = Class.forName(publisherBean);
            this.publisherBean = (PublisherBean) clazz.getDeclaredConstructor().newInstance();
            //Load data into the bean object
            Json.read(jsonPath, this.publisherBean);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
		
	}


    /**
     * Call publish() method of the {@link org.angrybee.website.publish.Publisher} implementation
     */
    public void publish(){
        this.publisher.publish();
    }




}
