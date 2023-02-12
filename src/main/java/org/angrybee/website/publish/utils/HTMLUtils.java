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
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class is dedicated to HTML operations using Jsoup library.<br>
 * It means that it works only with Jsoup library.
 * @author Charles Vissol
 */
public class HTMLUtils {
    

    static final Logger logger = Logger.getLogger(HTMLUtils.class.getName());

    /**
     * Private constructor to avoid public one
     */
    private HTMLUtils(){}


    /**
     * Load {@link org.jsoup.nodes.Document} object by using JSoup library
     * @param path Full path of the document to load
     * @return {@link org.jsoup.nodes.Document} 
     */
    public static Document doc(File path){
        try {
            return Jsoup.parse(path, "UTF-8");
        } catch (IOException e) {
            logger.info(e.getMessage());
        } //parse the html document
        return null;
    }


/**
 * Retrieve a specific HTML element based on its identifier (attribute <code>id</code>).<br>
 * This method is designed to retrieve a unique HTML element.<br>
 * If the element is not found, the method returns <code>null</code> value
 * @param doc {@link org.jsoup.Jsoup} document 
 * @param idTag String value of the identifier, means the value of <code>id</code> attribute in the HTML
 * @return The unique HTML element 
 */
	public static Element id(Document doc, String idTag){
		
		return doc.getElementById(idTag);
	}
 
    /**
     * Retrieve an element based on an expression Ex: doc.selecFirst("input[checked=true]").
     * We use here the Jsoup method of {@link org.jsoup.nodes.Document} <code>selectFirst("...")</code> method
     * @param doc {@link org.jsoup.Jsoup} document
     * @param expr String representing an expression to filter a specific tag.
     * @return The {@link org.jsoup.nodes.Element} object selected
     */
	public static Element selectOne(Document doc, String expr){
		
		return doc.selectFirst(expr);
	}    


    /**
     * Retrieve a list of {@link org.jsoup.nodes.Element} objects
     * @param doc {@link org.jsoup.Jsoup} document
     * @param expr String representing an expression to filter a list of tags.
     * @return List of {@link org.jsoup.nodes.Element} objects
     */
	public static Elements selectAny(Document doc, String expr){
		
		return doc.select(expr);
	}    



}
