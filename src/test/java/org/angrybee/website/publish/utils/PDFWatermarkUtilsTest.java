package org.angrybee.website.publish.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.angrybee.website.publish.bean.PublisherPdfBean;
import org.angrybee.website.publish.impl.PublicationPdf;
import org.angrybee.website.publish.impl.PublisherPdf;
import org.apache.commons.lang.SystemUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

class PDFWatermarkUtilsTest {

	/**
	 * Logger initialization
	 */
	static final Logger logger = Logger.getLogger(PDFWatermarkUtilsTest.class.getName());



    @Test
    void testMain(){

        File inputPdf = new File(this.getClass().getClassLoader().getResource("publish-pdf-input.pdf").getFile());

        
        /**
         * Create the working directory
         */
        Path tempPath = null;
        
        //By default creates the working directory
        try {

            String prefix = "publisher";

            if(SystemUtils.IS_OS_UNIX) {
                FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
                tempPath = Files.createTempDirectory(prefix, attr); // Compliant
                }
                else {
                tempPath = Files.createTempDirectory("publisher");
                }

            System.out.println("Default working directory is: " + tempPath);
        } catch (IOException e) {
            e.printStackTrace();
        }    

        String outputPath = tempPath.toFile().getAbsolutePath() + File.separator + "publish-pdf-output.pdf";

        String[] args = {inputPdf.getAbsolutePath(), tempPath.toFile().getAbsolutePath() + File.separator + "publish-pdf-output.pdf", "Watermark"};

        try {
            PDFWatermarkUtils.main(args);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(new File(outputPath).exists());


    }




    @Test
    void testAddWatermarkText() {
        
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

        logger.log(Level.INFO, "Result directory: {0}", pdfResult.getOutputDir());
        logger.log(Level.INFO, "Owner password: {0}", pDefaultBean.getOwnerPassword());
        logger.log(Level.INFO, "User password: {0}", pDefaultBean.getUserPassword());

        /**
         * Create the working directory
         */
        Path tempPath = null;

            //By default creates the working directory
            try {

                String prefix = "publisher";

                if(SystemUtils.IS_OS_UNIX) {
                    FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwx------"));
                    tempPath = Files.createTempDirectory(prefix, attr); // Compliant
                  }
                  else {
                    tempPath = Files.createTempDirectory("publisher");
                  }

                logger.log(Level.INFO, "Default working directory is: {0}", tempPath);
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }    
        


        /**
         * Create watermark to each page
         */
        String PATH_PDF_WATERMARK = null;
        File dstFile = null;
        if(pDefaultBean.getWatermark() != null){
            PATH_PDF_WATERMARK = tempPath + File.separator + "PDFWatermarkUtilsTest.watermark.pdf";
            
            
            dstFile = new File(PATH_PDF_WATERMARK);

           

                PDDocument watermarkPdf = pdfResult.getPdf();

                for (PDPage page : watermarkPdf.getPages())//Add watermark to each page
                {
                    PDFont font = PDType1Font.HELVETICA;//Specify the Font
                    try {
                        PDFWatermarkUtils.addWatermarkText(watermarkPdf, page, font, pDefaultBean.getWatermark());
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }//Add Watermark page by page
                }
                try {
                    watermarkPdf.save(dstFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                      
        }

        if(dstFile != null){
            assertTrue(dstFile.exists());
        } else {
            assertNotNull(dstFile);
        }
        



    }
}
