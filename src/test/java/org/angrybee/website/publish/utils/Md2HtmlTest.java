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
import static org.junit.jupiter.api.Assertions.assertEquals;
// ! Important to use jupiter API for assertion because it is specific to Junit 5
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit Test not necessary to the current application because the methods 
 * use directly the flexmark library {@link com.vladsch.flexmark.parser.Parser}
 */
class Md2HtmlTest {

	static final Logger logger = Logger.getLogger(Md2HtmlTest.class.getName());


	@ParameterizedTest
	@CsvSource({
	    "<h1>Some history</h1>, # Some history",
	    "<p><strong>cgroup</strong></p>, **cgroup**",
		"<h2>Some history</h2>, ## Some history"
	})
	void testConvertStr(String expected, String proceed) {
		
		logger.info(proceed);
		logger.info(expected);
		logger.info(Md2Html.convert(proceed).trim());

		//Verify the transformation from Markdown to HTML 
		assertEquals(expected, Md2Html.convert(proceed).trim());
		
	}

	/**
	 * Method to test the Md to HTML conversion specifically for full 
	 * @param inputMarkdownFilePath
	 * @param outputHtmlFilePath
	 */
	@Test
	void testConvertFiles() {

		ClassLoader classLoader = getClass().getClassLoader();
		File fileTemplate = new File(classLoader.getResource("template.md").getFile());

		Md2Html.convert(fileTemplate.toPath().toString(), System.getProperty("java.io.tmpdir") + File.separator + "template.html");

		File resultExpected = new File(classLoader.getResource("template.html").getFile());		
		File resultToCompare = new File(System.getProperty("java.io.tmpdir") + File.separator + "template.html");

		List<String> resultListExpected = FileUtils.readLineByLine(resultExpected.toPath().toString());
		List<String> resultListToCompare = FileUtils.readLineByLine(resultToCompare.toPath().toString());

		//Read Line by line the result/expected file and verify if it is equals
		for (int i = 0; i < resultListExpected.size(); i++) {
			assertTrue(resultListExpected.get(i).equals(resultListToCompare.get(i)));
			logger.info("Expected=" + resultListExpected.get(i) + "||" + resultListToCompare.get(i));
		  }


	


	}

}
