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

public class PublisherPdfBean implements PublisherBean {


    /**
     * [Optional] Watermark
     */
    private String watermark;

    /**
     * [Optional] Footer content
     */
    private String footer;

    /**
     * [Optional]Header content
     */
    private String header;

    /**
     * [Optional]Resources path if specific
     */
    private String resources;

    /**
     * [Mandatory] Path to the markdown file
     */
    private String markdown = null;

    /**
     * [Optional] Path to the template file
     */
    private String template = null;

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








    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }


    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTitleTxt() {
        return titleTxt;
    }

    public void setTitleTxt(String titleTxt) {
        this.titleTxt = titleTxt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
