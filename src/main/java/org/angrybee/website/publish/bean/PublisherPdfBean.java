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

/**
 * Bean for Markdown to PDF conversion.
 * The goal of the bean is to store all the necessary parameters (bean attributes) to perform properly the configuration.
 * At this point, only <code>markdown</code> attributes is mandatory.
 * You must instantiate the Bean and set mandatory value minimum before publishing.
 * 
 * @author Charles Vissol
 */
public class PublisherPdfBean implements PublisherBean {

    /**
     * Default constructor
     */
    public PublisherPdfBean(){
        /* Default Constructor */
    }


    /**
     * [Optional value]<br>
     * Owner password of the PDF. 
     * If not set, the publication process set it to "1234"
     */
    private String ownerPassword;

    /**
     * [Optional value]<br>
     * User password of the PDF
     * If not set, the publication process set it to "1234"
     */
    private String userPassword;


    /**
     * [Optional value]<br>
     * Watermark text.
     */
    private String watermark;

    /**
     * [Optional value]<br>
     * Footer content
     */
    private String footer;

    /**
     * [Optional value]<br>
     * Header text
     */
    private String header;

    /**
     * [Optional value]<br>
     * Resources path if specific. The resources path is representing the root folder from where 
     * the Markdown file search all additional files such as pictures. 
     * It is important to get the right resources folder to be sure you can resolve the pictures for example 
     * during the conversion process.
     */
    private String resources;

    /**
     * [Mandatory value]<br>
     * Markdown file path to convert to PDF
     */
    private String markdown = null;

    /**
     * [Optional value]<br>
     * Template file path which is an HTML/CSS file
     * By default, the library uses <code>resources/default-pdf-template.html</code> template 
     * but you can here specify your own.
     */
    private String template = null;

    /**
     * [Optional value]<br>
     * Text to add to {@code <title>} tag
     */
    private String titleTxt = null;

    /**
     * [Optional value]<br>
     * Text to set the author of the publication
     */
    private String author = null;

    /**
     * [Optional value]<br>
     * Text to set the date of the publication
     */
    private String date = null;




    /**
     * Getter of <code>ownerPassword</code> attribute
     * @return <code>ownerPassword</code> attribute
     */
    public String getOwnerPassword() {
        return ownerPassword;
    }

    /**
     * Setter of <code>ownerPassword</code> attribute
     * @param ownerPassword <code>ownerPassword</code> value
     */
    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    /**
     * Getter of <code>userPassword</code>
     * @return <code>userPassword</code> attribute
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Setter of <code>userPassword</code>
     * @param userPassword <code>userPassword</code> value
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Getter for <code>watermark</code>
     * @return <code>watermark</code> value
     */
    public String getWatermark() {
        return watermark;
    }

    /**
     * Setter for <code>watermark</code> 
     * @param watermark <code>watermark</code> value
     */
    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }


    /**
     * Getter for <code>resources</code> attribute
     * @return <code>resources</code> value
     */
    public String getResources() {
        return resources;
    }

    /**
     * Setter for <code>resources</code>
     * @param resources <code>resources</code> value
     */
    public void setResources(String resources) {
        this.resources = resources;
    }

    /**
     * Getter for <code>footer</code>
     * @return <code>footer</code> value
     */
    public String getFooter() {
        return footer;
    }

    /**
     * Setter for <code>footer</code>
     * @param footer <code>footer</code> value
     */
    public void setFooter(String footer) {
        this.footer = footer;
    }

    /**
     * Getter for <code>header</code>
     * @return <code>header</code> value
     */
    public String getHeader() {
        return header;
    }

    /**
     * Setter for <code>header</code>
     * @param header <code>header</code> value
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Getter for <code>markdown</code>
     * @return <code>markdown</code> value
     */
    public String getMarkdown() {
        return markdown;
    }

    /**
     * Setter for <code>markdown</code>
     * @param markdown <code>markdown</code> value
     */
    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    /**
     * Getter for <code>template</code>
     * @return <code>template</code> value
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Setter of <code>template</code>
     * @param template <code>template</code> value
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Getter for <code>titleTxt</code>
     * @return <code>titleTxt</code> value
     */
    public String getTitleTxt() {
        return titleTxt;
    }

    /**
     * Setter for <code>titleTxt</code>
     * @param titleTxt <code>titleTxt</code> value
     */
    public void setTitleTxt(String titleTxt) {
        this.titleTxt = titleTxt;
    }

    /**
     * Getter of <code>author</code>
     * @return <code>author</code> value
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter of <code>author</code>
     * @param author <code>author</code> value
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Getter of <code>date</code>
     * @return <code>date</code> value
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter of <code>date</code>
     * @param date <code>date</code> value
     */
    public void setDate(String date) {
        this.date = date;
    }

}
