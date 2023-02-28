/** Copyright 2023 Angrybee.tech (https://angrybee.tech)

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/

package org.angrybee.website.publish.utils;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dedicated utility class to convert markdown into HTML
 * Source code downloaded from 
 * @see <a href="https://simplesolution.dev/java-convert-markdown-to-html-using-flexmark-java">https://simplesolution.dev/java-convert-markdown-to-html-using-flexmark-java</a>.
 * <br>
 * You can call directly this class to convert Markdown into HTML:
 * <pre><code>
 * 
 *      java org.angrybee.website.publish.utils.Md2Html $input-markdown $output-html
 * 
 * </code></pre>
 * <ul>
 *  <li><code>$input-markdown</code>: Input Markdown file</li>
 *  <li><code>$output-html</code>: Output HTML file</li> 
 * </ul>
 * 
 * @author Charles Vissol
 *
 */
public final class Md2Html {

    /**
     * Logger for Md2Html
     */
	static final Logger logger = Logger.getLogger(Md2Html.class.getName());

    /**
     * Private constructor empty because it has only static methods
     */
    private Md2Html(){ /* Empty constructor */ }


    /**
     * <code>main()</code> method to convert directly a Markdown file into HTML file.
     * @param args The method accept 2 mandatory arguments: {@code <input-md>} {@code <output-html>}
     */
    public static void main(String[] args) {
        if (args.length != 2)
        {
            usage();
        }
        else
        {
            Md2Html.convert(args[0], args[1]);
        }        
    }



	/**
	 * Generic static method to convert "on the fly" a Markdown content to HTML
	 * @param markdownContent Markdown content to convert
	 * @return The HTML content resulting of the conversion from Markdown
	 */
    public static String convert(String markdownContent) {
    	
    	logger.log(Level.FINE, "Entry into method convertMarkdownToHtml(String)");
        logger.log(Level.FINE, () -> String.format("Input Markdown content is %s", markdownContent));
        
    	MutableDataSet options = new MutableDataSet();

        options.set(Parser.EXTENSIONS, Arrays.asList(AbbreviationExtension.create(),
                                                        DefinitionExtension.create(),
                                                        FootnoteExtension.create(),
                                                        TablesExtension.create(),
                                                        TypographicExtension.create(),
                                                        StrikethroughExtension.create()));
        options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        
        Node document = parser.parse(markdownContent);
        

        return renderer.render(document);
    }

    
    /**
     * Generic static method to convert a Markdown file into an HTML file. 
     * If the HTML file does not exists, it creates it.
     * If the HTML file exists, it is overwritten
     * @param inputMarkdownFilePath Full path of the Markdown file (input file) 
     * @param outputHtmlFilePath Full path of the HTML file (output file)
     */
    public static void convert(String inputMarkdownFilePath, String outputHtmlFilePath) {

        logger.log(Level.FINE, "Entry into method convertMarkdownToHtml(String,String)");
        logger.log(Level.FINE, () -> String.format("Input file is %s", inputMarkdownFilePath));
        logger.log(Level.FINE, () -> String.format("Outpu file is %s", outputHtmlFilePath));


        try {
            String markdownContent = new String(Files.readAllBytes(Paths.get(inputMarkdownFilePath)), StandardCharsets.UTF_8);
            String outputHtml = convert(markdownContent);
            Files.write(Paths.get(outputHtmlFilePath), outputHtml.getBytes());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * This will print the usage.
     */
    private static void usage()
    {
        logger.log(Level.INFO, "Usage: java {0} <input-md> <output-html>", Md2Html.class.getName());
    }


}