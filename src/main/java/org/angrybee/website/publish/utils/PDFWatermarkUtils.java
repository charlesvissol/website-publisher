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

package org.angrybee.website.publish.utils;


import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.blend.BlendMode;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;





/**
 * This class is specialized for PDF modification to add a 
 * diagonal watermark text to each page of a PDF
 * this code is provided by PDFBox project <a href="https://svn.apache.org/viewvc/pdfbox/trunk/examples/src/main/java/org/apache/pdfbox/examples/util/AddWatermarkText.java?revision=1871629&view=co">here</a>.
 * 
 * <br>
 * You can directly call this class to add a watermark to each page of your PDF.
 * Simply call:
 * <pre><code>
 * 
 *      java org.angrybee.website.publish.utils.PDFWatermarkUtils $input-pdf $output-pdf $watermark-text
 * 
 * </code></pre>
 * <ul>
 * <li><code>$input-pdf</code>: Input PDF file (without watermark)</li>
 * <li><code>$output-pdf</code>: Output PDF file (with watermark)</li>
 * <li><code>$watermark-text</code>: Text od the watermark to include in each page</li>
 * </ul>
 * 
 * 
 * @author Charles Vissol
 */
public class PDFWatermarkUtils {
    
    /**
     * Default private constructor to avoid direct instantiation
     */
    private PDFWatermarkUtils(){
        
    }

    /**
     * <code>main()</code> method to call direclty the watermark feature on an existing PDF.
     * @param args The method accept 3 mandatory arguments: {@code <input-pdf>} {@code <output-pdf>} {@code <short text>}
     * @throws IOException If input file does not exist or if output file can't be written
     */
    public static void main(String[] args) throws IOException
    {
        if (args.length != 3)
        {
            usage();
        }
        else
        {
            File srcFile = new File(args[0]);
            File dstFile = new File(args[1]);
            String text = args[2];

            try (PDDocument doc = PDDocument.load(srcFile))
            {
                for (PDPage page : doc.getPages())
                {
                    PDFont font = PDType1Font.HELVETICA;
                    addWatermarkText(doc, page, font, text);
                }
                doc.save(dstFile);
            }
        }
    }

    /**
     * Method to add watermark to all the pages of the PDF
     * @param doc PDF document to modify
     * @param page Page of the PDF
     * @param font Font of the watermark
     * @param text Text of the watermark
     * @throws IOException If PDF not accessible, exception...
     */
    public static void addWatermarkText(PDDocument doc, PDPage page, PDFont font, String text)
            throws IOException
    {
        try (PDPageContentStream cs
                = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true))
        {
            float fontHeight = 100; // arbitrary for short text
            float width = page.getMediaBox().getWidth();
            float height = page.getMediaBox().getHeight();
            float stringWidth = font.getStringWidth(text) / 1000 * fontHeight;
            float diagonalLength = (float) Math.sqrt(width * width + height * height);
            float angle = (float) Math.atan2(height, width);
            float x = (diagonalLength - stringWidth) / 2; // "horizontal" position in rotated world
            float y = -fontHeight / 4; // 4 is a trial-and-error thing, this lowers the text a bit
            cs.transform(Matrix.getRotateInstance(angle, 0, 0));
            cs.setFont(font, fontHeight);
            // cs.setRenderingMode(RenderingMode.STROKE) // for "hollow" effect

            PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
            gs.setNonStrokingAlphaConstant(0.2f);
            gs.setStrokingAlphaConstant(0.2f);
            gs.setBlendMode(BlendMode.MULTIPLY);
            gs.setLineWidth(3f);
            cs.setGraphicsStateParameters(gs);

            cs.setNonStrokingColor(Color.red);
            cs.setStrokingColor(Color.red);

            cs.beginText();
            cs.newLineAtOffset(x, y);
            cs.showText(text);
            cs.endText();
        }
    }

    /**
     * This will print the usage.
     */
    private static void usage()
    {
        System.err.println("Usage: java " + PDFWatermarkUtils.class.getName() + " <input-pdf> <output-pdf> <short text>");
    }
}
