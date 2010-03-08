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

import org.opencredo.twitter.si.FilterTwitterStreamConfiguration;
import org.opencredo.twitter.si.TwitterCredentials;
import org.opencredo.twitter.si.TwitterStreamConfiguration;
import org.opencredo.twitter.si.TwitterStreamInboundChannelAdapter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO
 *
 * @author Erich Eichinger
 * @since Mar 7, 2010
 */
public class TwitYourl {

    private final static String[] configFiles = {
            "twityourl-common.xml",
            "twityourl-streamreader-context.xml",
            // alternatively use  "twityourl-connect-amq.xml" below
            // for connecting WordMap processors using RabbitMQ
//            "twityourl-connect-amq.xml",
            "twityourl-connect-direct.xml",
            "twityourl-map-context.xml",
            "twityourl-esper.xml"
    };

    public static void main(String[] args) throws IOException, TwitterException, InterruptedException {

        TwitterStreamConfiguration config = parseCommandLine(args);
        
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configFiles, TwitYourl.class);
        TwitterStreamInboundChannelAdapter twitterFeed = (TwitterStreamInboundChannelAdapter) context.getBean("twitterStreamFeed");
        twitterFeed.setTwitterStreamConfiguration(config);

        Thread.sleep(10000);
    }

    private static TwitterStreamConfiguration parseCommandLine(String[] args) {

        try {
            Collection<String> followIds = null;
            Collection<String> trackKeywords = null;

            String[] credentials = args[0].split(":");

            Collection<String> list = null;
            for (int i = 1; i < args.length; i++) {
                String arg = args[i];
                if (arg.equals("-f")) {
                    followIds = new ArrayList<String>();
                    list = followIds;
                } else if (arg.equals("-t")) {
                    trackKeywords = new ArrayList<String>();
                    list = trackKeywords;
                } else {
                    list.add(arg);
                }
            }

            if (followIds != null)
            {
                return new FilterTwitterStreamConfiguration(
                        new TwitterCredentials(credentials[0], credentials[1]),
                        followIds.toArray(new String[followIds.size()]),
                        (trackKeywords==null)
                                ?null
                                :trackKeywords.toArray(new String[trackKeywords.size()]),
                        0
                );                
            }
            else {
                return new TwitterStreamConfiguration( new TwitterCredentials(credentials[0], credentials[1])  );
            }

        } catch (Exception e) {
            System.err.println(
                    "Usage: Example username:password ... [-f twitter_id ...] [-t keyword]");
            System.exit(1);
            return null;
        }
    }
}
