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
import java.util.logging.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.svgsupport.BatikSVGDrawer;

import org.angrybee.website.publish.Publication;
import org.angrybee.website.publish.Publisher;
import org.angrybee.website.publish.bean.PublisherBean;
import org.angrybee.website.publish.bean.PublisherDefaultHtmlBean;

public class PublisherPdf implements Publisher{

	/**
	 * Logger initialization
	 */
	static final Logger logger = Logger.getLogger(PublisherDefaultHtml.class.getName());

	/**
	 * properties file initialization
	 */
	private static ResourceBundle resources = ResourceBundle.getBundle(PublisherDefaultHtml.class.getName());

	/**
	 * Default bean implementation
	 */
	private PublisherDefaultHtmlBean publisherBeanImpl;
   
    private String resourcesPath;


    public void setResourcesPath(String path){
        resourcesPath = path;
    }


    @Override
    public void setBean(PublisherBean publisherBeanImpl) {
        this.publisherBeanImpl = (PublisherDefaultHtmlBean) publisherBeanImpl;
        
    }

    @Override
    public Publication publish() {
        
        
        //PublisherDefaultHtml publisher = new PublisherDefaultHtml();
        //publisher.setBean(publisherBeanImpl);
        //PublicationHtml publicationHtml = (PublicationHtml) publisher.publish();

        try {
            this.htmlToPdf("/home/vissol/softs/dev-projects/website-publisher/src/test/resources/publish-expected-result2.html",
                        "/home/vissol/softs/dev-projects/website-publisher/src/test/resources/publish-expected-result2.pdf");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }








        return null;
    }
    
    public static void main(String[] args) {
        PublisherPdf pdf = new PublisherPdf();
        pdf.setResourcesPath("/home/vissol/softs/dev-projects/website-publisher/src/test/resources");
        pdf.publish();
    }

    private Document html5ParseDocument(String inputHTML) throws IOException{
        org.jsoup.nodes.Document doc;
        
        doc = Jsoup.parse(new File(inputHTML), "UTF-8");
        
        return new W3CDom().fromJsoup(doc);
    }



    private void htmlToPdf(String inputHTML, String outputPdf) throws IOException {
        
        Document doc = this.html5ParseDocument(inputHTML);

        String baseUri = FileSystems.getDefault().getPath(resourcesPath).toUri().toString();

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
