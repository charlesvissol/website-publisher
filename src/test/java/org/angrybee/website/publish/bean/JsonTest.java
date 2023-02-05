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

import org.junit.jupiter.api.Test;

public class JsonTest {
    @Test
    void testRead() {

    }

    @Test
    void testWrite() {

        PublisherDefaultBean bean = new PublisherDefaultBean();
        bean.setAuthor("Charles Vissol");
        bean.setDate("February 2, 2023");
        bean.setMarkdown("/tmp/toto.md");
        bean.setTitle("/tmp/title.png");


        Json.write("/tmp/publisher.json", bean);

        assertTrue(true);

    }
}
