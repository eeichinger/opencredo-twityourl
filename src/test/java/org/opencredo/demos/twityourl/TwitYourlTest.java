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

import org.junit.Test;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO
 *
 * @author Erich Eichinger
 * @since Mar 7, 2010
 */
public class TwitYourlTest {

    @Test
    public void run() throws IOException, TwitterException, InterruptedException {
        
        ArrayList<String> args = new ArrayList<String>();
        args.add(String.format("%s:%s", ConfigurationProperties.getTwitterScreenName(), ConfigurationProperties.getTwitterPassword()));
//        args.add("-f");
//        args.add("octestaccount");
//        args.add("-t");
//        args.add("testtext");

        TwitYourl.main(args.toArray(new String[args.size()]));
    }
}
