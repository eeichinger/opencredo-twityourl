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

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * TODO
 *
 * @author Erich Eichinger
 * @since Mar 8, 2010
 */
public class TextFilter {

    private String regularExpression;
    private Pattern compiledExpression;
    private boolean partialMatch = false;

    public void setRegularExpression(String regularExpression) {
        this.compiledExpression = Pattern.compile(regularExpression);
        this.regularExpression = regularExpression;
    }

    public void setPartialMatch(boolean partialMatch) {
        this.partialMatch = partialMatch;
    }

    public TextFilter(String regularExpression) {
        this.setRegularExpression(regularExpression);
    }

    public boolean isMatch(String text) {
        Matcher matcher = compiledExpression.matcher(text);
        if (partialMatch)
            return matcher.find();
        else
            return matcher.matches();
    }
}
