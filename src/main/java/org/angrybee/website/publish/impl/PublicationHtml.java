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

package org.angrybee.website.publish.impl;

import org.angrybee.website.publish.Publication;
import org.jsoup.nodes.Document;

/**
 * Representation of an HTML output document
 * @author Charles Vissol
 */
public class PublicationHtml implements Publication {
    
    /**
     * Jsoup Document object
     */
    private Document doc;

    /**
     * Store the Jsoup Document object
     * @param doc Jsoup document
     */
    public void setDocument(Document doc){
        this.doc = doc;
    }

    /**
     * Get the Jsoup Document object
     * @return Jsoup Document object
     */
    public Document getDocument(){
        return doc;
    }


    /**
     * Return the String content of a Jsoup Document
     * @return String content of the Jsoup Document
     */
    public String html() {
        return doc.html();
    }
}
