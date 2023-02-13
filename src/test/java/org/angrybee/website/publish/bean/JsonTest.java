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


package org.angrybee.website.publish.bean;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class JsonTest {
    @Test
    void testRead() {

        PublisherDefaultHtmlBean bean = new PublisherDefaultHtmlBean();
        testWrite();
        String jsonPath = System.getProperty("java.io.tmpdir") + "/publisher.json";
        bean = (PublisherDefaultHtmlBean) Json.read(jsonPath, bean);

        assertTrue(bean.getCss().get(0).contentEquals("first.css"));
        assertTrue(bean.getCss().get(1).contentEquals("second.css"));

        assertTrue(bean.getJs().get(0).contentEquals("first.js"));
        assertTrue(bean.getJs().get(1).contentEquals("second.js"));

        assertTrue(bean.getTemplate().contentEquals("/tmp/template.html"));
        assertTrue(bean.getMetaAuthor().contentEquals("Charles Vissol"));
        assertTrue(bean.getMetaDescription().contentEquals("Description of the article"));
        assertTrue(bean.getMetaKeywords().contentEquals("article angrybee for publishing"));
        assertTrue(bean.getMetaIcon().contentEquals("pictures/angrybee.svg"));
        assertTrue(bean.getMarkdown().contentEquals("/tmp/article.md"));
        assertTrue(bean.getTitleImg().contentEquals("pictures/title.svg"));
        assertTrue(bean.getTitleTxt().contentEquals("this is a title"));
        assertTrue(bean.getAuthor().contentEquals("Charles Vissol"));
        assertTrue(bean.getDate().contentEquals("February 2, 2023"));


    }

    @Test
    void testWrite() {

        PublisherDefaultHtmlBean bean = new PublisherDefaultHtmlBean();
        List<String> css = new ArrayList<>();
        css.add("first.css");
        css.add("second.css");

        List<String> js = new ArrayList<>();
        js.add("first.js");
        js.add("second.js");

        bean.setCss(css);
        bean.setJs(js);
        bean.setTemplate("/tmp/template.html");
        bean.setMetaAuthor("Charles Vissol");
        bean.setMetaDescription("Description of the article");
        bean.setMetaKeywords("article angrybee for publishing");
        bean.setMetaIcon("pictures/angrybee.svg");
        bean.setMarkdown("/tmp/article.md");
        bean.setTitleImg("pictures/title.svg");
        bean.setTitleTxt("this is a title");
        bean.setAuthor("Charles Vissol");
        bean.setDate("February 2, 2023");

        System.out.println("Write file to " + System.getProperty("java.io.tmpdir") + "/publisher.json");

        Json.write(System.getProperty("java.io.tmpdir") + "/publisher.json", bean);

        assertTrue(true);

    }
}
