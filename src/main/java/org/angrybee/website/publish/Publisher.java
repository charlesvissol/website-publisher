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

package org.angrybee.website.publish;


import java.io.File;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.angrybee.website.publish.utils.FileUtils;
import org.angrybee.website.publish.utils.HTMLUtils;
import org.angrybee.website.publish.utils.Md2Html;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * Main class of the publication process.</br>
 * The global process aims to convert Markdown document into HTML file.<br>
 * The resulting HTML has CSS and Javascript specific to Angrybee needs. 
 * The HTML file exists as a template where we inject specific information such as author, title,etc. and meta data.<br>
 * We convert Markdown into HTML before injecting it into into the body of the HTML template file.<br>
 * Once done, we post-process some tags specifically to our needs.
 * <code>template</code> variable represents the name of the HTML file which is the HTML template where the markdown.<br>
 * <code>tempDir</code> variable represents the path of the temporary directory where the transformation process runs
 */
public class Publisher {


	static final Logger logger = Logger.getLogger(Publisher.class.getName());

	/**
	 * properties file
	 */
	private static ResourceBundle resources;

	private Publisher(){
		//Initialization of the access to the properties file
		resources = ResourceBundle.getBundle(Publisher.class.getName());
	}

	public static void main (String[] argv) {
		
		//TODO #1
		String pictureTitle = argv[0];
		String author = argv[1];
		String date_publishing = argv[2];
		String markdown_path = argv[3];

		String template_path = argv[4];
		String temp_dir = argv[5];


		Publisher publisher = new Publisher();

		File file = null;

		String template = resources.getString("template");
		String tempDir = resources.getString("temp.directory");
		String newFile = resources.getString("new.file");

		long workDir = LocalTime.now().toNanoOfDay(); 

		

		if(template.length() == 0){//Case where properties is empty = no template file defined
			file = publisher.getFile("article.html");
			logger.fine("template variable is empty");
		} else {
			file = publisher.getFile(template);
			logger.fine(String.format("template variable = {0}", template));
		} 

		if(tempDir.length() == 0){
			tempDir = System.getProperty("java.io.tmpdir");
			logger.fine(String.format("tempDir variable from system = {0}",tempDir));
		}


		//Duplicate HTML template into target HTML
		FileUtils.duplicate(file.getPath(), tempDir + File.separator + newFile);

		//Create temporary HTML file (for Markdown to HTML conversion)
		File tmpHtml = new File(tempDir + File.separator);

		//Parse the new File, copy of the HTML template
		Document doc = HTMLUtils.doc(new File(tempDir + File.separator + newFile));

		//add tag's information to target HTML
		Element title = HTMLUtils.id(doc, "publisher.title");
		title.attr("src", String.format("titles/{0}", pictureTitle));

		Element authorDate = HTMLUtils.id(doc, "publisher.author.date");
		authorDate.text(String.format("{0} {1}}", author, date_publishing));


		//Convert Markdown content to HTML content
		Md2Html.convert(markdown_path, newFile);


//id="publisher.content"


		//add meta information 


		


		//Post-modif for additionnal tags
		
		/**
		 * <pre><i class="bi bi-clipboard ab-xlarge ab-clip-pos" onclick="copyCode(event,'$identifier')"></i><code id="$identifier" class="language-bash">
         *       </code></pre>
		 *
		 *
         *       <table class="ab-table-all ab-hoverable"></table>
		 * 
		 * 
		 * 
		 * 
		 */




		




		


		



		


	}

	/**
	 * Return the class loader to get resources files
	 * @return class loader for the {@link org.angrybee.website.publish.Publisher} class
	 */
	private ClassLoader getClassLoader(){
		return getClass().getClassLoader();
	}


	/**
	 * Get {@link java.io.File} instance using the class loader.
	 * Means the file is in src/main/resources 
	 * @param filename Full Path of the file 
	 * @return File in the class loader resource directory
	 */
	private File getFile(String filename){
		return new File(getClassLoader().getResource(filename).getFile());
	}








}
