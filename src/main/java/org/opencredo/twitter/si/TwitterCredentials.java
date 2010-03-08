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
package org.opencredo.twitter.si;

import org.springframework.util.Assert;

/**
 * TODO
 *
 * @author Erich Eichinger
 * @since Mar 7, 2010
 */
public class TwitterCredentials {
    private String screenName;
    private String password;

    public String getScreenName() {
        return screenName;
    }

    public String getPassword() {
        return password;
    }

    public TwitterCredentials(String screenName, String password) {
        Assert.hasText(screenName, "screenName must contain text");
        Assert.hasText(password, "password must contain text");

        this.screenName = screenName;
        this.password = password;
    }
}