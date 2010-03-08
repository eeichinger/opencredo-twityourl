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

import org.springframework.integration.channel.MessageChannelTemplate;
import org.springframework.integration.core.MessageChannel;
import org.springframework.util.Assert;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;

/**
 * TODO
 *
 * @author Erich Eichinger
 * @since Mar 7, 2010
 */
public class FilterTwitterStreamInboundChannelAdapter extends TwitterStreamInboundChannelAdapter {

    public void setTwitterStreamConfiguration(FilterTwitterStreamConfiguration twitterStreamConfiguration) throws TwitterException {
        super.setTwitterStreamConfiguration(twitterStreamConfiguration);
    }

    public FilterTwitterStreamInboundChannelAdapter(MessageChannel statusChannel) {
        super(statusChannel);
    }

    public FilterTwitterStreamInboundChannelAdapter(String screenName, String password, MessageChannel requestChannel, String[] followIds) throws TwitterException {
        super(screenName, password, requestChannel);
        initialize(screenName, password, followIds, null, 0);
    }

    public FilterTwitterStreamInboundChannelAdapter(String screenName, String password, MessageChannelTemplate messageChannelTemplate, String[] followIds) throws TwitterException {
        super(screenName, password, messageChannelTemplate);
        initialize(screenName, password, followIds, null, 0);
    }

    private void initialize(String screenName, String password, String[] followIds, String[] trackKeywords, int numberOfHistoricStatusesToLoad) throws TwitterException {
        Assert.notNull(followIds);
        Assert.isTrue(followIds.length > 0);
        setTwitterStreamConfiguration(
                new FilterTwitterStreamConfiguration(
                        new TwitterCredentials(screenName, password),
                        followIds,
                        null,
                        0
                ));
    }

    @Override
    public TwitterStream refresh(TwitterStreamConfiguration twitterStreamConfiguration) throws TwitterException {
        TwitterStream twitterStream = super.refresh(twitterStreamConfiguration);

        FilterTwitterStreamConfiguration config = (FilterTwitterStreamConfiguration) twitterStreamConfiguration;
        int[] userIds = getUserIds(config.getCredentials(), config.getFollowScreenNames());
        String[] trackKeywords = config.getTrackKeywords();
        int numberOfHistoricStatusesToLoad = config.getNumberOfHistoricStatusesToLoad();
        logger.info(String.format("following userIds '%s', tracking keywords '%s'", StringUtils.join(userIds, ","), StringUtils.join(trackKeywords, ",")));
        twitterStream.filter(numberOfHistoricStatusesToLoad, userIds, trackKeywords);
        return twitterStream;
    }

    private int[] getUserIds(TwitterCredentials credentials, String[] screenNames) throws TwitterException {
        Twitter twitter = new TwitterFactory().getInstance(credentials.getScreenName(), credentials.getScreenName());
        int[] userIds = new int[screenNames.length];
        for (int ix = 0; ix < screenNames.length; ix++) {
            userIds[ix] = twitter.showUser(screenNames[ix]).getId();
        }
        return userIds;
    }
}
