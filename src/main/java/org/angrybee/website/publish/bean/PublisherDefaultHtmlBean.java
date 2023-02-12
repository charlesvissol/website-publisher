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
 * Bean representing the Default Bean from Markdown to HTML conversion.
 * The bean get the parameters to 
 */
public class PublisherDefaultHtmlBean implements PublisherBean {


    /**
     * Description to add the <meta> tag with attribute name="description" in HTML <header>
     */
    private String metaDescription = null;

    /**
     * List of keywords to add the <meta> tag with attribute name="keywords" in HTML <header>
     */
    private String metaKeywords = null;

    /**
     * Author to add to the <meta> tag with attribute name="author" in HTML <header>
     */
    private String metaAuthor = null;


    /**
     * Icon to add to HTML <header> in <Link> tag
     */
    private String metaIcon = null;

    /**
     * Path to the markdown file
     */
    private String markdown = null;

    /**
     * Path to the markdown file
     */
    private String template = null;

    /**
     * Path to the title image
     */
    private String titleImg = null;

    /**
     * Text to add to <title> tag
     */
    private String titleTxt = null;

    /**
     * Text to set the author of the publication
     */
    private String author = null;

    /**
     * Text to set the date of the publication
     */
    private String date = null;



    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
    
    public String getMetaAuthor() {
        return metaAuthor;
    }

    public void setMetaAuthor(String metaAuthor) {
        this.metaAuthor = metaAuthor;
    }


    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaIcon() {
        return metaIcon;
    }

    public void setMetaIcon(String icon) {
        this.metaIcon = icon;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public void setTitleImg(String title) {
        this.titleImg = title;
    }

    public void setTitleTxt(String title) {
        this.titleTxt = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarkdown() {
        return markdown;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public String getTitleTxt() {
        return titleTxt;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

}
