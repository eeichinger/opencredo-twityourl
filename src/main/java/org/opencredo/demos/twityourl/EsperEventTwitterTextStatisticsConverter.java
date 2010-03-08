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

import com.espertech.esper.client.EventBean;
import org.springframework.integration.annotation.Transformer;

/**
 * Converts from 
 *
 * @author Erich Eichinger
 * @since Mar 8, 2010
 */
public class EsperEventTwitterTextStatisticsConverter {

    private String urlKey = "url";
    private String totalKey = "total";

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public void setTotalKey(String totalKey) {
        this.totalKey = totalKey;
    }

    @Transformer
    public TextStatistics convert( EventBean eventBean ) {
        return new TextStatistics( (String)eventBean.get(urlKey), (Long)eventBean.get(totalKey) ) ;
    }
}