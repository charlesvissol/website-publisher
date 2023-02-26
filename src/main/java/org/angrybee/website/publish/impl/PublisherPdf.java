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

import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.svgsupport.BatikSVGDrawer;

import org.angrybee.website.publish.Publication;
import org.angrybee.website.publish.Publisher;
import org.angrybee.website.publish.bean.PublisherBean;
import org.angrybee.website.publish.bean.PublisherPdfBean;
import org.angrybee.website.publish.utils.FileUtils;
import org.angrybee.website.publish.utils.HTMLUtils;
import org.angrybee.website.publish.utils.Md2Html;
import org.angrybee.website.publish.utils.PDFWatermarkUtils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;




/**
 * By default, resources (images,...) attached to the Markdown file to export to the PDF should be in the same folder than the MArkdown file. If not, you 
 * must specify the resources locations 
 * 
 * @author Charles Vissol
 */
//TODO Comment the class and add code sample
public class PublisherPdf implements Publisher{

	/**
	 * Logger initialization
	 */
	static final Logger logger = Logger.getLogger(PublisherPdf.class.getName());

	/**
	 * properties file initialization
	 */
	private static ResourceBundle resources = ResourceBundle.getBundle(PublisherPdf.class.getName());

	/**
	 * Default bean implementation
	 */
	private PublisherPdfBean publisherBeanImpl;


    @Override
    public void setBean(PublisherBean publisherBeanImpl) {
        this.publisherBeanImpl = (PublisherPdfBean) publisherBeanImpl;
        
    }

    @Override
    public Publication publish() {


    


        File fileTemplate = null;
        //File fileFont = null;

		//Load template HTML file: default template if not in the Bean
        if(publisherBeanImpl.getTemplate() != null){

			fileTemplate = new File(publisherBeanImpl.getTemplate());

        } else {

			//Get the default html template if the PublisherDefaultHtmlBean.getTemplate() is empty
			String template = resources.getString("template");	
            //String font = resources.getString("font"); 

			try {
				fileTemplate = new FileUtils().getFileFromResource(template);
                //fileFont = new FileUtils().getFileFromResource(font);

			} catch (URISyntaxException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}          
        }

		//Parse the new File (copy of the HTML template)
		org.jsoup.nodes.Document doc = HTMLUtils.doc(fileTemplate);

		/**
		 * Add meta informations to the HTML header
		 */

		 //Add title to the <title> tag + article title
		 if(publisherBeanImpl.getTitleTxt() != null){
			org.jsoup.nodes.Element metaTitleTxt = HTMLUtils.selectOne(doc, "title");
			metaTitleTxt.text(publisherBeanImpl.getTitleTxt());
            org.jsoup.nodes.Element articleTitleTxt = HTMLUtils.id(doc, "publisher.title.txt");
            articleTitleTxt.text(publisherBeanImpl.getTitleTxt());

		 }//If no title to store, do nothing
		

		//Add date and author
		if(publisherBeanImpl.getDate() != null || publisherBeanImpl.getAuthor() != null){
			org.jsoup.nodes.Element authorDate = HTMLUtils.id(doc, "publisher.author.date");
			authorDate.text(String.format("%s - %s", publisherBeanImpl.getAuthor(), publisherBeanImpl.getDate()));
		} else {
			if(publisherBeanImpl.getDate() == null && publisherBeanImpl.getAuthor() == null) {//No author nor date -> we delete Element
				org.jsoup.nodes.Element authorDate = HTMLUtils.id(doc, "publisher.author.date");
				authorDate.remove();
			}
		}	

        //Add header
        if(publisherBeanImpl.getHeader() != null){
            org.jsoup.nodes.Element header = HTMLUtils.id(doc, "header");
            header.text(publisherBeanImpl.getHeader());
        } else {//If nothing to write, remove the tag
            org.jsoup.nodes.Element header = HTMLUtils.id(doc, "header");
            header.remove();
        }

        //Add footer
        if(publisherBeanImpl.getFooter() != null){
            org.jsoup.nodes.Element footer = HTMLUtils.id(doc, "footer");
            footer.text(publisherBeanImpl.getFooter());
        } else {//If nothing to write, remove the tag
            org.jsoup.nodes.Element footer = HTMLUtils.id(doc, "footer");
            footer.remove();
        }



		File mdFile = null;
		//Load the file content in String
		if(publisherBeanImpl.getMarkdown() != null){
			mdFile = new File(publisherBeanImpl.getMarkdown());
		} else {
			//In case of no Markdown for input, we use default markdown to show error but not to block the process.
			String input = resources.getString("input");	

			try {
				mdFile = new FileUtils().getFileFromResource(input);

			} catch (URISyntaxException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}  			
			
			
		}
		
		//Convert the Markdown string into HTML string
		String html = Md2Html.convert(FileUtils.getStrContent(mdFile));		
		//Add html content (from markdown conversion)
		org.jsoup.nodes.Element content = HTMLUtils.id(doc, "publisher.content");
		content.html(html);


        /**
         * Create the table of content for the first 3 titles
         */

        Elements headers = doc.select("h1, h2, h3");

        org.jsoup.nodes.Element toc = HTMLUtils.id(doc, "doc.toc");
        

        int tocIndex = 0;

        for (Element header : headers) {
            header.id(String.valueOf(tocIndex));

            org.jsoup.nodes.Element div = new Element("div").addClass("toc-row");
            org.jsoup.nodes.Element a = new Element("a");
            a.attr("href", "#" + String.valueOf(tocIndex));
            a.attr("style", "text-decoration: none;");
            a.text(header.text());
            div.appendChild(a);

            toc.appendChild(div);

            tocIndex++;
        }

        
		PublicationHtml htmlPub = new PublicationHtml();
		htmlPub.setDocument(doc);


        Path tempPath = null;
        try {
            tempPath = Files.createTempDirectory("publisher");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }


        String PATH_HTML = tempPath + File.separator + "publish-pdf-input.html";
        String PATH_PDF = tempPath + File.separator + mdFile.getName() + ".pdf";
        
        
        String PATH_RESOURCES = null;

        if(publisherBeanImpl.getResources() != null){
            PATH_RESOURCES = publisherBeanImpl.getResources();
        } else {
            PATH_RESOURCES = mdFile.getAbsolutePath();//By default, path of the Markdown file.
        }

        try {
            FileUtils.writeFromString(PATH_HTML, doc.html());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        //Get font
        //FileUtils.duplicate(fileFont.getPath(), PATH_RESOURCES + File.separator + fileFont.getName());

        String baseUri = FileSystems.getDefault().getPath(PATH_RESOURCES).toUri().toString();


        /**
         * Create the PDF
         */
        try {
            this.htmlToPdf(PATH_HTML, PATH_PDF, baseUri);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }


        /**
         * Create watermark to each page
         */
        String PATH_PDF_WATERMARK = null;
        if(publisherBeanImpl.getWatermark() != null){
            PATH_PDF_WATERMARK = tempPath + File.separator + mdFile.getName() + "_watermark.pdf";
            
            File srcFile = new File(PATH_PDF);
            File dstFile = new File(PATH_PDF_WATERMARK);

            try (PDDocument watermarkPdf = PDDocument.load(srcFile))
            {
                for (PDPage page : watermarkPdf.getPages())
                {
                    PDFont font = PDType1Font.HELVETICA;
                    PDFWatermarkUtils.addWatermarkText(watermarkPdf, page, font, publisherBeanImpl.getWatermark());
                }
                watermarkPdf.save(dstFile);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }            
        }


        /**
         * Secure PDF with password
         */
        String PATH_PDF_PROTECTED = null;
        if(publisherBeanImpl.getWatermark() != null){
            PATH_PDF_PROTECTED = PATH_PDF_WATERMARK;
        } else {
            PATH_PDF_PROTECTED = PATH_PDF;
        }

        /**
         * Set the passwords
         */
        String ownerPassword = null;
        String userPassword = null;

        if(publisherBeanImpl.getOwnerPassword() !=null && publisherBeanImpl.getUserPassword() != null) {
            ownerPassword = publisherBeanImpl.getOwnerPassword();
            userPassword = publisherBeanImpl.getUserPassword();
        } else {
            ownerPassword = "1234";
            userPassword = "1234";
        }



        File srcFile = new File(PATH_PDF_PROTECTED);
        PDDocument permissionPdf = null;
        
        try 
        {
            
            
            permissionPdf = PDDocument.load(srcFile);
            AccessPermission ap = new AccessPermission();
            
            ap.setCanModify(false);
            ap.setCanExtractContent(false);
            ap.setCanPrint(false);
            ap.setReadOnly();

            StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPassword, userPassword, ap);

            spp.setPermissions(ap);


            permissionPdf.protect(spp);
                     
            permissionPdf.save(PATH_PDF_PROTECTED);
            permissionPdf.close();


        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } 

        //Delete HTML template file
        new File(PATH_HTML).delete();


        //TODO
        return null;
    }
    
    public static void main(String[] args) {


       
		PublisherPdfBean pDefaultBean = new PublisherPdfBean();
        pDefaultBean.setTitleTxt("Template PDF Formatting article");
        pDefaultBean.setWatermark("Watermark!!!");
        pDefaultBean.setAuthor("Charles Vissol");
        pDefaultBean.setDate("February 20, 2023");
        pDefaultBean.setHeader("Header of the page");
        pDefaultBean.setFooter("This is a very long footer of page ");
        //pDefaultBean.setResources("/home/vissol/softs/dev-projects/angrybee-website/articles");
        pDefaultBean.setMarkdown("/home/vissol/softs/dev-projects/angrybee-website/articles/cgroups.md");
        //pDefaultBean.setMarkdown("/home/vissol/softs/dev-projects/website-publisher/src/test/resources/publish-pdf-input.md");
       
        
        PublisherPdf pDefault = new PublisherPdf();
        pDefault.setBean(pDefaultBean);
        pDefault.publish();
    }

    private Document html5ParseDocument(String inputHTML) throws IOException{
        org.jsoup.nodes.Document doc;
        
        doc = Jsoup.parse(new File(inputHTML), "UTF-8");
        
        return new W3CDom().fromJsoup(doc);
    }



    private void htmlToPdf(String inputHTML, String outputPdf, String baseUri) throws IOException {
        
        Document doc = this.html5ParseDocument(inputHTML);

        OutputStream os = new FileOutputStream(outputPdf);
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withUri(outputPdf);
        //SVG support
        builder.useSVGDrawer(new BatikSVGDrawer());

        builder.toStream(os);
        
        builder.withW3cDocument(doc, baseUri);
        
        
        builder.run();
        
        os.close();
      }







}
