package org.angrybee.website.publish.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

public class HTMLUtilsTest {
    @Test
    void testDoc() {

        String html = "<html><head><title>Test Page</title></head><body><h1>Hello, World!</h1></body></html>";
        
        // Parse the HTML string using JSoup.parse() method
        Document doc = HTMLUtils.doc(html);
        
        // Verify that the parsed document has the expected title
        assertEquals("Test Page", doc.title());
        
        // Verify that the parsed document has the expected text content
        assertEquals("Hello, World!", doc.body().getElementsByTag("h1").text());
    



    }

    @Test
    void testDoc2() throws IOException {
        // Create a temporary HTML file with some content
        String html = "<html><head><title>Test Page</title></head><body><h1>Hello, World!</h1></body></html>";
        File file = File.createTempFile("test", ".html");
        Files.write(file.toPath(), html.getBytes("UTF-8"));
        
        // Parse the HTML file using JSoup.parse() method
        Document doc = HTMLUtils.doc(file);
        
        // Verify that the parsed document has the expected title
        assertEquals("Test Page", doc.title());
        
        // Verify that the parsed document has the expected text content
        assertEquals("Hello, World!", doc.body().getElementsByTag("h1").text());
        
        // Delete the temporary file
        file.delete();
    }
}
