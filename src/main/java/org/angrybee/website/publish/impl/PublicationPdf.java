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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.nodes.Document;

public class PublicationPdf implements Publication{
   /**
     * Default constructor
     */
    public PublicationPdf(){
        /* Default constructor */
    }


    /**
     * Output directory were the output PDF files are located 
     */
    private String outputDir;


    /**
     * Jsoup Document object = Abstract instance of the HTML document using Jsoup library
     */
    private Document html;

    /**
     * Pdf default document
     */
    private PDDocument pdf;

    /**
     * Pdf with watermark
     */
    private PDDocument pdfWatermark;

    /**
     * Pdf with password protection
     */
    private PDDocument pdfProtected;

 
    /**
     * Retrieve the output directory
     * @return Path of the output directory
     */
    public String getOutputDir() {
        return outputDir;
    }

    /**
     * Store the output directory
     * @param outputDir Output directory path
     */
    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }    




    /**
     * Set the PDF document (default transformation without option - i.e. watermark and password protection)
     * @param pdf Default Pdf document
     */
    public void setPdf(PDDocument pdf){
        this.pdf = pdf;
    }

    /**
     * Get the default Pdf
     * @return Default Pdf
     */
    public PDDocument getPdf(){
        return this.pdf;
    }

    /**
     * Get the Pdf with Watermark
     * @return Pdf with Watermark
     */
    public PDDocument getWatermarkPdf(){
        return this.pdfWatermark;
    }

    /**
     * Get the Pdf protected by password
     * @return Pdf protected by password
     */
    public PDDocument getProtectedPdf(){
        return this.pdfProtected;
    }

    /**
     * Set the PDF document with Watermark
     * @param pdfWatermark Pdf document with watermark
     */
    public void setWatermarkPdf(PDDocument pdfWatermark){
        this.pdfWatermark = pdfWatermark;
    }

    /**
     * Set the PDF document protected by password
     * @param pdfProtected Pdf document with password protection
     */
    public void setProtectedPdf(PDDocument pdfProtected){
        this.pdfProtected = pdfProtected;
    }

    /**
     * Store the Jsoup Document object
     * @param doc Jsoup document
     */
    public void setDocument(Document doc){
        this.html = doc;
    }

    /**
     * Get the Jsoup Document object
     * @return Jsoup Document object
     */
    public Document getDocument(){
        return html;
    }


    /**
     * Return the String content of a Jsoup Document
     * @return String content of the Jsoup Document
     */
    public String html() {
        return html.html();
    }    
}
