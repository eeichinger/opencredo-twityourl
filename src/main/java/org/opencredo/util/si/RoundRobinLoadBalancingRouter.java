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
package org.opencredo.util.si;

import org.springframework.integration.annotation.Router;
import org.springframework.integration.core.Message;
import org.springframework.integration.core.MessageChannel;
import org.springframework.util.Assert;

/**
 * TODO
 *
 * @author Erich Eichinger
 * @since Mar 8, 2010
 */
public class RoundRobinLoadBalancingRouter {
    private final MessageChannel[] channels;

    private volatile int ixLastChannel;
    
    public RoundRobinLoadBalancingRouter(MessageChannel[] channels) {
        Assert.noNullElements(channels);
        Assert.isTrue(channels.length>0);
        this.channels = channels;
        this.ixLastChannel = 0;
    }

    @Router
    public MessageChannel route(Message message) {
        this.ixLastChannel = (this.ixLastChannel + 1) % channels.length;
        return channels[ixLastChannel];
    }
}
