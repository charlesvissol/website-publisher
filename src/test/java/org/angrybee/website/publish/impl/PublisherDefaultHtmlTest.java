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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.angrybee.website.publish.bean.PublisherDefaultHtmlBean;
import org.angrybee.website.publish.utils.FileUtils;
import org.junit.jupiter.api.Test;

class PublisherDefaultHtmlTest {
    @Test
    void testPublish() {

		PublisherDefaultHtml pDefault = new PublisherDefaultHtml();
		PublisherDefaultHtmlBean pDefaultBean = new PublisherDefaultHtmlBean();

		try {
            pDefaultBean.setMarkdown(FileUtils.getStrContent(new FileUtils().getFileFromResource("publish-markdown-input.md")));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
		pDefaultBean.setAuthor("Charles Vissol");
        pDefaultBean.setMetaAuthor("Charles Vissol");
		pDefaultBean.setDate("February 13, 2023");
		pDefaultBean.setTitleImg("Linux_1_intro_terminal.png");
		pDefaultBean.setMetaDescription("Linux Basics article. Introduction to Linux basic shortcuts to know the minimum to use the Terminal");
		pDefaultBean.setMetaKeywords("Linux terminal shortcut command basics introduction");
		pDefaultBean.setMetaIcon("../pictures/angrybee-blue.svg");
		pDefaultBean.setTitleTxt("Linux Basics: Terminal survivor kit");

		pDefault.setBean(pDefaultBean);
		PublicationHtml htmlPub = (PublicationHtml) pDefault.publish();



        File expected = null;
        try {
            expected = new FileUtils().getFileFromResource("publish-expected-result2.html");
        } catch (URISyntaxException e) {
            
            e.printStackTrace();
        }


        /**
         * 
         */
        String strExpected = FileUtils.getStrContent(expected);

        String strResult = htmlPub.getDocument().html();
        try {
            FileUtils.writeFromString("/tmp/result.html", strResult);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Get each line of the expected HTML result from Markdown file
        List<String> listExpected = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(strExpected))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listExpected.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Get each line of the resulting HTML converted from the Markdown file.
        List<String> listResult = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(strResult))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listResult.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Compare HTML content line by line
        for(int i = 0; i < listExpected.size(); i++){
            if(listExpected.get(i).trim().contentEquals(listResult.get(i).trim()) != true)
                System.out.println("assertTrue="+listExpected.get(i).trim().contentEquals(listResult.get(i).trim())+"|Expected=" + listExpected.get(i).trim() + "|Result=" + listResult.get(i).trim());

            assertTrue(listExpected.get(i).trim().contentEquals(listResult.get(i).trim()));
        }


    }
}
