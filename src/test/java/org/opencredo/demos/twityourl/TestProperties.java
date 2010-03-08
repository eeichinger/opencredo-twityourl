/* Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opencredo.demos.twityourl;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * @author Erich Eichinger
 * 
 */
public class TestProperties {
    public static final String TWITTER_SCREENNAME = "twitter.screenName";

    public static final String TWITTER_PASSWORD = "twitter.password";

    private static Properties TEST_PROPERTIES;

    static {
        try {
            TEST_PROPERTIES = new Properties();
            TEST_PROPERTIES.load((new ClassPathResource("test.properties").getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test.properties");
        }

    }

    public static String getTwitterScreenName() {
        return get(TWITTER_SCREENNAME);
    }

    public static String getTwitterPassword() {
        return get(TWITTER_PASSWORD);
    }

    protected static String get(String propertyName) {
        String value = TEST_PROPERTIES.getProperty(propertyName);
        checkPropertySet(propertyName, value);
        return value;
    }

    protected static void checkPropertySet(String propertyName, String valueRetrieved) {
        if (!StringUtils.hasText(valueRetrieved) || valueRetrieved.equals("${" + propertyName + "}")) {
            throw new RuntimeException("No value for key " + propertyName
                    + " found you may want to add this to your maven settings.xml");
        }
    }
}
