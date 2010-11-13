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

import org.springframework.integration.core.MessageChannel;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;

/**
 * TODO
 *
 * @author Erich Eichinger
 * @since Mar 7, 2010
 */
public class SampleTwitterStreamInboundChannelAdapter extends TwitterStreamInboundChannelAdapter {

    public SampleTwitterStreamInboundChannelAdapter(MessageChannel statusChannel) {
        super(statusChannel);
    }

    public SampleTwitterStreamInboundChannelAdapter(String screenName, String password, MessageChannel requestChannel) throws TwitterException {
        super(screenName, password, requestChannel);
    }

    @Override
    public TwitterStream refresh(TwitterStreamConfiguration twitterStreamConfiguration) throws TwitterException {
        TwitterStream stream = super.refresh(twitterStreamConfiguration);
        stream.sample();
        return stream;
    }
}
