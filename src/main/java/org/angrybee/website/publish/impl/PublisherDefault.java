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

package org.angrybee.website.publish.impl;


import java.io.File;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.angrybee.website.publish.bean.Json;
import org.angrybee.website.publish.Publisher;
import org.angrybee.website.publish.bean.PublisherBean;
import org.angrybee.website.publish.bean.PublisherDefaultBean;
import org.angrybee.website.publish.utils.FileUtils;
import org.angrybee.website.publish.utils.HTMLUtils;
import org.angrybee.website.publish.utils.Md2Html;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * Main class of the publication process.<br>
 * The global process aims to convert Markdown document into HTML file.<br>
 * The resulting HTML has CSS and Javascript specific to Angrybee needs. 
 * The HTML file exists as a template where we inject specific information such as author, title,etc. and meta data.<br>
 * We convert Markdown into HTML before injecting it into into the body of the HTML template file.<br>
 * Once done, we post-process some tags specifically to our needs.
 * <code>template</code> variable represents the name of the HTML file which is the HTML template where the markdown.<br>
 * <code>tempDir</code> variable represents the path of the temporary directory where the transformation process runs
 * <img alt="openlogo" src="doc-files/openlogo.svg"/>//TODO
 * @author Charles Vissol
 */
public class PublisherDefault implements Publisher {

	/**
	 * Logger initialization
	 */
	static final Logger logger = Logger.getLogger(PublisherDefault.class.getName());

	/**
	 * properties file initialization
	 */
	private static ResourceBundle resources = ResourceBundle.getBundle(PublisherDefault.class.getName());


	private PublisherDefaultBean publisherBeanImpl;

	private String jsonPath;


	@Override
	public void getBean(PublisherBean publisherBeanImpl) {
		this.publisherBeanImpl = (PublisherDefaultBean) publisherBeanImpl;
		
	}


	@Override
	public void getJson(String path) {
		this.jsonPath = path;
		
	}


	/**
	 * Main method to describe the full process of publication
	 */
	@Override
	public void publish() {

		String template = resources.getString("template");
		
		String newFile = resources.getString("new.file");

		String tempDir = System.getProperty("java.io.tmpdir");

		//Long value corresponding to the working directory in /tmp
		long workDir = LocalTime.now().toNanoOfDay();

		
		//Load Json data
		Json.read(jsonPath, publisherBeanImpl);


		//Load template HTML file
		ClassLoader classLoader = getClass().getClassLoader();
		File fileTemplate = new File(classLoader.getResource(template).getFile());

		//Create the working directory
		File tmpWkDir = new File(tempDir + File.separator + String.valueOf(workDir));
		if (!tmpWkDir.exists()) {
			tmpWkDir.mkdir();
		}

		//Duplicate template HTML template into target HTML
		FileUtils.duplicate(fileTemplate.getPath(), tempDir + File.separator + String.valueOf(workDir) + newFile);

		//Parse the new File (copy of the HTML template)
		Document doc = HTMLUtils.doc(new File(tempDir + File.separator + String.valueOf(workDir) + newFile));

		//add tag's information to target HTML
		Element title = HTMLUtils.id(doc, "publisher.title");
		title.attr("src", String.format("titles/{0}", publisherBeanImpl.getTitle()));

		Element authorDate = HTMLUtils.id(doc, "publisher.author.date");
		authorDate.text(String.format("{0} {1}}", publisherBeanImpl.getAuthor(), publisherBeanImpl.getDate()));

		//Load the file content in String
		File mdFile = new File(publisherBeanImpl.getMarkdown());
		//Convert the Markdown string into HTML string
		String html = Md2Html.convert(FileUtils.getStrContent(mdFile));		
		//Add html content (from markdown conversion)
		Element content = HTMLUtils.id(doc, "publisher.content");
		content.text(html);


		//TODO Add meta informations
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




}
