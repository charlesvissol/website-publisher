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

import java.util.List;

/**
 * Default Bean for the Markdown to HTML conversion. 
 * This class is a generic conversion without any customization except those corresponding to de CSS and Javascript added by the user.
 * 
 * @author Charles Vissol
 */
public class PublisherDefaultHtmlBean implements PublisherBean {


    /**
     * Default constructor
     */
    public PublisherDefaultHtmlBean(){
        /* Default Constructor */
    }

    /**
     * [Optional] List of CSS. Just set the path to the CSS scripts
     */
    private List<String> css;

    /**
     * [Optional] List of Javascripts. Just set the path of the Javascript scripts.
     */
    private List<String> js;


    /**
     * [Optional] Description to add the {@code <meta>} tag with attribute name="description" in HTML {@code <header>}
     */
    private String metaDescription = null;

    /**
     * [Optional] List of keywords to add the {@code <meta>} tag with attribute name="keywords" in HTML {@code <header>}
     */
    private String metaKeywords = null;

    /**
     * [Optional] Author to add to the {@code <meta>} tag with attribute name="author" in HTML {@code <header>}
     */
    private String metaAuthor = null;


    /**
     * [Optional] Icon to add to HTML header in {@code <Link>} tag
     */
    private String metaIcon = null;

    /**
     * [Mandatory] Path to the markdown file
     */
    private String markdown = null;

    /**
     * [Optional] Path to the template file
     */
    private String template = null;

    /**
     * [Optional] Path to the title image
     */
    private String titleImg = null;

    /**
     * [Optional] Text to add to {@code <title>} tag
     */
    private String titleTxt = null;

    /**
     * [Optional] Text to set the author of the publication
     */
    private String author = null;

    /**
     * [Optional] Text to set the date of the publication
     */
    private String date = null;

    /**
     * Retrieve the list of CSS scripts
     * @return List of CSS scripts
     */
    public List<String> getCss() {
        return css;
    }

    /**
     * Set the list of CSS scripts
     * @param css CSS scripts list
     */
    public void setCss(List<String> css) {
        this.css = css;
    }

    /**
     * Get the list of Javascript scripts
     * @return List of Javascript scripts
     */
    public List<String> getJs() {
        return js;
    }

    /**
     * Set the list of Javascript scripts
     * @param js List of Javascript scripts
     */
    public void setJs(List<String> js) {
        this.js = js;
    }

    /**
     * Retrieve the file name of the HTML template (HTML file where the 
     * Markdown content is injected once it has been converted in HTML)
     * @return Path of the template file
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Set the filename or path of the HTML template
     * @param template Path or filename of the HTML template
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Retrieve the author for the {@code <meta>} tag
     * @return Author appearing in the {@code <meta>} tag
     */
    public String getMetaAuthor() {
        return metaAuthor;
    }

    /**
     * Set the author for the {@code <meta>} tag
     * @param metaAuthor Author appearing in the {@code <meta>} tag
     */
    public void setMetaAuthor(String metaAuthor) {
        this.metaAuthor = metaAuthor;
    }


    /**
     * Get the description of the resulting HTML page in the {@code <meta>} tag
     * @return Description of the HTML page
     */
    public String getMetaDescription() {
        return metaDescription;
    }

    /**
     * Set the description of the resulting HTML page in the {@code <meta>} tag
     * @param metaDescription Description of the HTML page
     */
    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    /**
     * Get the list of keywords in the {@code <meta>} tag
     * @return Keywords list from {@code <meta>} tag
     */
    public String getMetaKeywords() {
        return metaKeywords;
    }

    /**
     * Set the list of keywords in the {@code <meta>} tag
     * @param metaKeywords Keywords list from {@code <meta>} tag
     */
    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    /**
     * Get Icon Path from {@code <meta>} tag
     * @return Icon Path
     */
    public String getMetaIcon() {
        return metaIcon;
    }

    /**
     * Set Icon Path from {@code <meta>} tag
     * @param icon Icon Path
     */
    public void setMetaIcon(String icon) {
        this.metaIcon = icon;
    }

    /**
     * Set Makdown full file path
     * @param markdown Markdown file path
     */
    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    /**
     * [Mainly For Angrybee Article]
     * Set the image if you want an image as title of your HTML page (for articles for example)
     * @param title Filename or path of the image
     */
    public void setTitleImg(String title) {
        this.titleImg = title;
    }

    /**
     * [Mainly For Angrybee Article]
     * Set the title of the HTML page (in {@code <title>} tag and in anorther location by default)
     * @param title Title of the HTML page
     */
    public void setTitleTxt(String title) {
        this.titleTxt = title;
    }

    /**
     * [Mainly For Angrybee Article]
     * Set the author of the HTML page 
     * @param author Author of the HTML page
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * [Mainly For Angrybee Article]
     * Set the Date of the HTML page publication
     * @param date Date in String
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get Makdown full file path
     * @return Markdown file path
     */    
    public String getMarkdown() {
        return markdown;
    }

    /**
     * [Mainly For Angrybee Article]
     * Get the image if you want an image as title of your HTML page (for articles for example)
     * @return Filename or path of the image
     */
    public String getTitleImg() {
        return titleImg;
    }

    /**
     * [Mainly For Angrybee Article]
     * Get the title of the HTML page (in {@code <title>} tag and in anorther location by default)
     * @return Title of the HTML page
     */    
    public String getTitleTxt() {
        return titleTxt;
    }

    /**
     * [Mainly For Angrybee Article]
     * Get the author of the HTML page 
     * @return Author of the HTML page
     */    
    public String getAuthor() {
        return author;
    }

    /**
     * [Mainly For Angrybee Article]
     * Get the Date of the HTML page publication
     * @return Date in String
     */    
    public String getDate() {
        return date;
    }

}
