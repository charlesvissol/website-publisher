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

package org.angrybee.website.publish;

import org.angrybee.website.publish.bean.PublisherBean;

/**
 * Interface that represents the main process of publication
 * @author Charles Vissol
 */
public interface Publisher {

    /**
     * Get {@link org.angrybee.website.publish.bean.PublisherBean} implementation depending 
     * on the Publishing process.
     * @param publisherBeanImpl {@link org.angrybee.website.publish.bean.PublisherBean} implementation
     */
    public void getBean(PublisherBean publisherBeanImpl);

    /**
     * Generic method to implement if you want to create your own chain of publishing
     * @return {@link org.angrybee.website.publish.Publication} implementation
     */
    public Publication publish();

}
