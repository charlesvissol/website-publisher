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
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.angrybee.website.publish.Publication;
import org.angrybee.website.publish.Publisher;
import org.angrybee.website.publish.bean.PublisherBean;
import org.angrybee.website.publish.bean.PublisherDefaultHtmlBean;
import org.angrybee.website.publish.utils.FileUtils;
import org.angrybee.website.publish.utils.HTMLUtils;
import org.angrybee.website.publish.utils.Md2Html;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Main class of the publication process.<br>
 * The global process aims to convert Markdown document into HTML file.<br>
 * The resulting HTML has CSS and Javascript specific to Angrybee needs. 
 * The HTML file exists as a template where we inject specific information such as author, title,etc. and meta data.<br>
 * We convert Markdown into HTML before injecting it into into the body of the HTML template file.<br>
 * Once done, we post-process some tags specifically to our needs.
 * <code>template</code> variable represents the name of the HTML file which is the HTML template where the markdown.<br>
 * <code>tempDir</code> variable represents the path of the temporary directory where the transformation process runs
 * 
 * <p><img alt="PublisherAngrybee principles" src="doc-files/PublisherAngrybee.png"/></p>
 * 
 * <p>Example of usage without Json:</p>
 * <pre><code>
 * 	
 *
 *		PublisherAngrybee pDefault = new PublisherAngrybee();
 *		PublisherDefaultHtmlBean bean = new PublisherDefaultHtmlBean();
 *
 *		List<String> css = new ArrayList<>();
 *      css.add("first.css");
 *      css.add("second.css");
 *
 *      List<String> js = new ArrayList<>();
 *      js.add("first.js");
 *      js.add("second.js");
 *
 *      bean.setCss(css);
 *      bean.setJs(js);
 *      bean.setTemplate("/tmp/template.html");
 *      bean.setMetaAuthor("Charles Vissol");
 *      bean.setMetaDescription("Description of the article");
 *      bean.setMetaKeywords("article angrybee for publishing");
 *      bean.setMetaIcon("pictures/angrybee.svg");
 *      bean.setMarkdown("/tmp/article.md");
 *      bean.setTitleImg("pictures/title.svg");
 *      bean.setTitleTxt("this is a title");
 *      bean.setAuthor("Charles Vissol");
 *      bean.setDate("February 2, 2023");
 *
 *		pDefault.getBean(bean);
 *		PublicationHtml htmlPub = (PublicationHtml) pDefault.publish();
 *
 *	
 * </code>
 * </pre>
 * 
 * <p>Example with Json:</p>
 * <pre>
 * <code>
 * 
 *      PublisherAngrybee pDefault = new PublisherAngrybee();
 *      PublisherDefaultHtmlBean bean = new PublisherDefaultHtmlBean();
 * 
 *      String jsonPath = System.getProperty("java.io.tmpdir") + "/publisher.json";
 *      bean = (PublisherDefaultHtmlBean) Json.read(jsonPath, bean);
 * 
 *      pDefault.getBean(bean);
 *      PublicationHtml htmlPub = (PublicationHtml) pDefault.publish();
 * 
 * </code>
 * </pre>
 * 
 * 
 * @author Charles Vissol
 */
public class PublisherAngrybee implements Publisher {

	/**
	 * Logger initialization
	 */
	static final Logger logger = Logger.getLogger(PublisherAngrybee.class.getName());

	/**
	 * properties file initialization where we specify the HTML page
	 */
	private static ResourceBundle resources = ResourceBundle.getBundle(PublisherAngrybee.class.getName());
	
	/**
	 * Bean to load all the necessary parameters to convert Markdown to HTML
	 */
	private PublisherDefaultHtmlBean publisherBeanImpl;

	/**
	 * Default constructor
	 */
	public PublisherAngrybee(){
		/* Default constructor */
	}

	@Override
	public void setBean(PublisherBean publisherBeanImpl) {
		this.publisherBeanImpl = (PublisherDefaultHtmlBean) publisherBeanImpl;
		
	}

	/**
	 * Main method to describe the full process of publication
	 */
	@Override
	public Publication publish() {

		//Get html template
		String template = resources.getString("template");

		//Load template HTML file
		File fileTemplate = null;
		try {
			fileTemplate = new FileUtils().getFileFromResource(template);
		} catch (URISyntaxException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		//Parse the new File (copy of the HTML template)
		Document doc = HTMLUtils.doc(fileTemplate);

		/**
		 * Add meta informations to the HTML header
		 */

		 final String CONTENT = "content";

		 //Add description to <meta> tag with attribute name="description". Description set in "content" attribute
		 if(publisherBeanImpl.getMetaDescription() != null){
			Element metaDescripton = HTMLUtils.selectOne(doc, "meta[name=description]");
			metaDescripton.attr(CONTENT, publisherBeanImpl.getMetaDescription());
			//Add SEO and social media tag
			Element metaDescripton2 = HTMLUtils.selectOne(doc, "meta[property=og:description]");
			metaDescripton2.attr(CONTENT, publisherBeanImpl.getMetaDescription());
		 }

		 //Add keywords to <meta name="keywords" content="$keywords$">
		 if(publisherBeanImpl.getMetaKeywords() != null){
			Element metaKeywords = HTMLUtils.selectOne(doc, "meta[name=keywords]");
			metaKeywords.attr(CONTENT, publisherBeanImpl.getMetaKeywords());
		 }

		 //Add author to <meta name="author" content="vissol">
		 if(publisherBeanImpl.getMetaAuthor() != null){
			Element metaAuthor = HTMLUtils.selectOne(doc, "meta[name=author]");
			metaAuthor.attr(CONTENT, publisherBeanImpl.getMetaAuthor());
		 }

		 //Add icon to <link rel="shortcut icon" href="../pictures/angrybee-blue.svg">
		 if(publisherBeanImpl.getMetaIcon() != null){
			Element metaIcon = HTMLUtils.selectOne(doc, "link[rel=shortcut icon]");
			metaIcon.attr("href", publisherBeanImpl.getMetaIcon());
		 }

		 //Add title to the <title> tag 
		 if(publisherBeanImpl.getTitleTxt() != null){
			Element metaTitleTxt = HTMLUtils.selectOne(doc, "title");
			metaTitleTxt.text(publisherBeanImpl.getTitleTxt());
			//Add SEO and social media tag
			Element metaTitleTxt2 = HTMLUtils.selectOne(doc, "meta[property=og:title]");
			metaTitleTxt2.attr(CONTENT, publisherBeanImpl.getTitleTxt());
		 }

		/**
		 * add tag's information to target HTML
		 */

		//Add Image for title
		if(publisherBeanImpl.getTitleImg() != null){
			Element title = HTMLUtils.id(doc, "publisher.title");
			title.attr("src", String.format("titles/%s", publisherBeanImpl.getTitleImg()));
		}
		
		//Add author and date for the article release
		if(publisherBeanImpl.getDate() != null){
			Element authorDate = HTMLUtils.id(doc, "publisher.author.date");
			authorDate.text(String.format("%s - %s", publisherBeanImpl.getAuthor(), publisherBeanImpl.getDate()));
		}	


		//Load the file content in String
		File mdFile = new File(publisherBeanImpl.getMarkdown());
		//Convert the Markdown string into HTML string
		String html = Md2Html.convert(FileUtils.getStrContent(mdFile));		
		//Add html content (from markdown conversion)
		Element content = HTMLUtils.id(doc, "publisher.content");
		content.html(html);


		//Add attribute data-aos="fade-up" to <section>

		Elements sections = HTMLUtils.selectAny(doc, "section");
		for(Element section : sections) {
			section.attr("data-aos", "fade-up");
		}


		/**
		 * Add code customization
		 */
		Elements codes = HTMLUtils.selectAny(doc, "code[class]");
		int count = 1;
		for(Element code : codes){
			code.before("<i class=\"bi bi-clipboard ab-xlarge ab-clip-pos\" onclick=\"copyCode(event,'"+ count +"')\"></i>");

			code.id(String.valueOf(count));

			count++;
		}

 
		/**
		 * Add class to table
		 */
		Elements tables = HTMLUtils.selectAny(doc, "table");
		for(Element table : tables){
			table.attr("class", "ab-table-all ab-hoverable");
		}


		PublicationHtml htmlPub = new PublicationHtml();
		htmlPub.setDocument(doc);


		return htmlPub;


	}


}
