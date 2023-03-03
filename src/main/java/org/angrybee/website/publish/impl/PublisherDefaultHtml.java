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

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
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

/**
 * {@link org.angrybee.website.publish.Publisher} implementation for generic HTML.
 * It allows conversion of Markdown into any HTML
 * This class uses {@link org.angrybee.website.publish.bean.PublisherDefaultHtmlBean} to manage variables and
 * {@link org.angrybee.website.publish.impl.PublicationHtml} allows you to get the resulting publication.
 * 
 * <p><img alt="PublisherDefaultHtml principles" src="doc-files/PublisherDefaultHtml.png"/></p>
 * 
 * 
 * <p>Example of usage:</p>
 * <pre><code>
 * 
 * 		PublisherDefaultHtml pDefault = new PublisherDefaultHtml();
 *		PublisherDefaultHtmlBean pDefaultBean = new PublisherDefaultHtmlBean();
 *
 *		try {
 *           pDefaultBean.setMarkdown(FileUtils.getStrContent(new FileUtils().getFileFromResource("publish-markdown-input.md")));
 *       } catch (URISyntaxException e) {
 *           e.printStackTrace();
 *       }
 *		pDefaultBean.setAuthor("Charles Vissol");
 *      pDefaultBean.setMetaAuthor("Charles Vissol");
 *		pDefaultBean.setDate("February 13, 2023");
 *		pDefaultBean.setTitleImg("Linux_1_intro_terminal.png");
 *		pDefaultBean.setMetaDescription("Linux Basics article. Introduction to Linux basic shortcuts to know the minimum to use the Terminal");
 *		pDefaultBean.setMetaKeywords("Linux terminal shortcut command basics introduction");
 *		pDefaultBean.setMetaIcon("../pictures/angrybee-blue.svg");
 *		pDefaultBean.setTitleTxt("Linux Basics: Terminal survivor kit");
 *
 *		pDefault.setBean(pDefaultBean);
 *		PublicationHtml htmlPub = (PublicationHtml) pDefault.publish();
 * 
 * </code></pre>
 * 
 * @author Charles Vissol
 */
public class PublisherDefaultHtml implements Publisher {
    
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

	/**
	 * Default constructor
	 */
	public PublisherDefaultHtml(){
	
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

        File fileTemplate = null;

		//Load template HTML file: default template if not in the Bean
        if(publisherBeanImpl.getTemplate() != null){

			fileTemplate = new File(publisherBeanImpl.getTemplate());

        } else {

			//Get the default html template if the PublisherDefaultHtmlBean.getTemplate() is empty
			String template = resources.getString("template");	

			try {
				fileTemplate = new FileUtils().getFileFromResource(template);

			} catch (URISyntaxException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}          
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
		  * Modify HTML header is necessary
		  */
		  Element head = doc.head();
		 //Add Css if exist
		 if(publisherBeanImpl.getCss() != null){
			List<String> csss = publisherBeanImpl.getCss();
			for (String css : csss) {
				head.append("<link rel='stylesheet' type='text/css' href='" + css + "'>");
			}
		 }
		 
		 //Add javascript
		 if(publisherBeanImpl.getJs() != null){
			List<String> jss = publisherBeanImpl.getJs();
			for (String js : jss) {
				head.append("<script type='text/javascript' src='" + js + "'></script>");
			}
		 }


		/**
		 * add tag's information to target HTML
		 */

		//Add Image title 
		if(publisherBeanImpl.getTitleImg() != null){
			Element title = HTMLUtils.id(doc, "publisher.title");
			title.attr("src", String.format("titles/%s", publisherBeanImpl.getTitleImg()));
		} else {
			//If no title defined, remove the Element from the HTML document
			Element title = HTMLUtils.id(doc, "publisher.title");
			title.remove();
		}
		

		//Add date and author
		if(publisherBeanImpl.getDate() != null || publisherBeanImpl.getAuthor() != null){
			Element authorDate = HTMLUtils.id(doc, "publisher.author.date");
			authorDate.text(String.format("%s - %s", publisherBeanImpl.getAuthor(), publisherBeanImpl.getDate()));
		} else {
			if(publisherBeanImpl.getDate() == null && publisherBeanImpl.getAuthor() == null) {//No author nor date -> we delete Element
				Element authorDate = HTMLUtils.id(doc, "publisher.author.date");
				authorDate.remove();
			}
		}	

		
		//Load the Markdown file content in String and convert into HTML
		String html = null;
		if(publisherBeanImpl.getMarkdown() != null){
			html = Md2Html.convert(publisherBeanImpl.getMarkdown());
		} else {
			//In case of no Markdown for input, we use default markdown to show error but not to block the process.
			String input = resources.getString("input");	

			try {
				html = Md2Html.convert(FileUtils.getStrContent(new FileUtils().getFileFromResource(input)));

			} catch (URISyntaxException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}  			
		}
	
		//Add html content (from markdown conversion)
		Element content = HTMLUtils.id(doc, "publisher.content");
		content.html(html);



		PublicationHtml htmlPub = new PublicationHtml();
		htmlPub.setDocument(doc);


		return htmlPub;


	}

}
