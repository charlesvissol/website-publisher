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

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Dedicated utility class to convert markdown into HTML
 * Source code downloaded from 
 * @see <a href="https://simplesolution.dev/java-convert-markdown-to-html-using-flexmark-java">https://simplesolution.dev/java-convert-markdown-to-html-using-flexmark-java</a>.
 * @author Charles Vissol
 *
 */
public final class Md2Html {
	
	static final Logger logger = Logger.getLogger(Md2Html.class.getName());

    /**
     * Public constructor empty because it has only static methods
     */
    private Md2Html(){ /* Empty constructor */ }


	/**
	 * Generic static method to convert "on the fly" a Markdown content to HTML
	 * @param markdownContent Markdown content to convert
	 * @return The HTML content resulting of the conversion from Markdown
	 */
    public static String convert(String markdownContent) {
    	
    	logger.fine("Entry into method convertMarkdownToHtml(String)");
        logger.fine(() -> String.format("Input Markdown content is %s", markdownContent));
        
    	MutableDataSet options = new MutableDataSet();
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

        logger.fine("Entry into method convertMarkdownToHtml(String,String)");
        logger.fine(() -> String.format("Input file is %s", inputMarkdownFilePath));
        logger.fine(() -> String.format("Outpu file is %s", outputHtmlFilePath));

        try {
            String markdownContent = new String(Files.readAllBytes(Paths.get(inputMarkdownFilePath)), StandardCharsets.UTF_8);
            String outputHtml = convert(markdownContent);
            Files.write(Paths.get(outputHtmlFilePath), outputHtml.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}