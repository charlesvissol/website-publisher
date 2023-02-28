package org.angrybee.website.publish.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.net.URISyntaxException;

import org.angrybee.website.publish.bean.PublisherPdfBean;
import org.angrybee.website.publish.utils.FileUtils;
import org.junit.jupiter.api.Test;

class PublisherPdfTest {
    @Test
    void testPublish() {



		PublisherPdfBean pDefaultBean = new PublisherPdfBean();
        pDefaultBean.setTitleTxt("Template PDF Formatting article");
        pDefaultBean.setWatermark("Watermark!!!");
        pDefaultBean.setAuthor("Charles Vissol");
        pDefaultBean.setDate("February 20, 2023");
        pDefaultBean.setHeader("Header of the page");
        pDefaultBean.setFooter("This is a very long footer of page ");
        //pDefaultBean.setResources("/home/vissol/softs/dev-projects/angrybee-website/articles");
        File markdown = null;
        try {
            markdown = new FileUtils().getFileFromResource("cgroups.md");
        } catch (URISyntaxException e) {
            
            e.printStackTrace();
        }


        pDefaultBean.setMarkdown(markdown.getAbsolutePath());
        

        PublisherPdf pDefault = new PublisherPdf();
        pDefault.setBean(pDefaultBean);

        PublicationPdf pdfResult = (PublicationPdf) pDefault.publish();

        System.out.println("Result directory: " + pdfResult.getOutputDir());
        System.out.println("Owner password: " + pDefaultBean.getOwnerPassword());
        System.out.println("User password: " + pDefaultBean.getUserPassword());

        assertNotNull(pdfResult.getDocument());
        assertNotNull(pdfResult.getOutputDir());
        assertNotNull(pdfResult.getPdf());
        assertNotNull(pdfResult.getWatermarkPdf());
        assertNotNull(pdfResult.getProtectedPdf());

        
    }
}
