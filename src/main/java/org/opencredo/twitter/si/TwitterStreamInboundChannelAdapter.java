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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.channel.MessageChannelTemplate;
import org.springframework.integration.core.MessageChannel;
import org.springframework.integration.message.MessageBuilder;
import org.springframework.util.Assert;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * TODO
 *
 * @author Erich Eichinger
 * @since Mar 7, 2010
 */
public abstract class TwitterStreamInboundChannelAdapter implements StatusListener {
    protected final Logger logger = LoggerFactory.getLogger(TwitterStreamInboundChannelAdapter.class);
    private final MessageChannelTemplate statusMessageChannelTemplate;

    private TwitterStreamConfiguration twitterStreamConfiguration;
    private TwitterStream twitterStream;

    public void setTwitterStreamConfiguration(TwitterStreamConfiguration twitterStreamConfiguration) throws TwitterException {
        Assert.notNull(twitterStreamConfiguration, "twitterStreamConfiguration must not be null");
        this.twitterStreamConfiguration = twitterStreamConfiguration;

        if (this.twitterStream != null) {
            this.twitterStream.cleanup();
        }

        twitterStream = refresh(twitterStreamConfiguration);
    }

    public MessageChannelTemplate getStatusMessageChannelTemplate() {
        return statusMessageChannelTemplate;
    }

    public TwitterStreamInboundChannelAdapter(MessageChannel statusChannel) {
        this.statusMessageChannelTemplate = new MessageChannelTemplate(statusChannel);
    }

    public TwitterStreamInboundChannelAdapter(String screenName, String password, MessageChannel statusChannel) throws TwitterException {
        this(screenName, password, new MessageChannelTemplate(statusChannel));
    }

    public TwitterStreamInboundChannelAdapter(String screenName, String password, MessageChannelTemplate statusChannelTemplate) throws TwitterException {
        Assert.notNull(screenName, "screenName must not be null");
        Assert.notNull(password, "password must not be null");
        Assert.notNull(statusChannelTemplate, "statusMessageChannelTemplate must not be null");
        this.twitterStreamConfiguration = new TwitterStreamConfiguration(new TwitterCredentials(screenName, password));
        this.statusMessageChannelTemplate = statusChannelTemplate;
    }

    public TwitterStream refresh(TwitterStreamConfiguration twitterStreamConfiguration) throws TwitterException
    {
        TwitterCredentials credentials = twitterStreamConfiguration.getCredentials();
        logger.info(String.format("opening twitter stream using credentials %s:%s", credentials.getScreenName(), credentials.getPassword()));
        TwitterStream ts = getTwitterStreamFactory()
                .getInstance(credentials.getScreenName(), credentials.getPassword());
        return ts;
    }

    public void onStatus(Status status) {
        logger.debug("received status " + status );
        try {
            statusMessageChannelTemplate.send(MessageBuilder.withPayload(status).build());
        } catch (Exception e) {
            logger.error("error sending status message", e);
        }
    }

    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        logger.debug("received deletion notice " + statusDeletionNotice );
    }

    public void onTrackLimitationNotice(int count) {
        logger.warn("received TrackLimitationNotice " + count);
    }

    public void onException(Exception e) {
        logger.error("received Exception ", e);
    }

    protected TwitterStreamFactory getTwitterStreamFactory() {
        return new TwitterStreamFactory(this);
    }
}
