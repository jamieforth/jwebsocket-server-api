//        ---------------------------------------------------------------------------
//        jWebSocket - Copyright (c) 2010 jwebsocket.org
//        ---------------------------------------------------------------------------
//        This program is free software; you can redistribute it and/or modify it
//        under the terms of the GNU Lesser General Public License as published by the
//        Free Software Foundation; either version 3 of the License, or (at your
//        option) any later version.
//        This program is distributed in the hope that it will be useful, but WITHOUT
//        ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
//        FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
//        more details.
//        You should have received a copy of the GNU Lesser General Public License along
//        with this program; if not, see <http://www.gnu.org/licenses/lgpl.html>.
//        ---------------------------------------------------------------------------
package org.jwebsocket.config.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.jwebsocket.config.Config;
import org.jwebsocket.config.ConfigHandler;
/**
 * Handler class that reads the server configuration
 * @author puran
 * @version $Id: ServerConfigHandler.java 596 2010-06-22 17:09:54Z fivefeetfurther $
 *
 */
public class ThreadPoolConfigHandler implements ConfigHandler {

        private static final String CORE_POOL_SIZE = "corePoolSize";
        private static final String MAXIMUM_POOL_SIZE = "maximumPoolSize";
        private static final String KEEP_ALIVE_TIME = "keepAliveTime";
        private static final String BLOCKING_QUEUE_SIZE = "blockingQueueSize";
        private static final String ELEMENT_THREAD_POOL = "threadPool";        
        /**
         * {@inheritDoc}
         */
        @Override
        public Config processConfig(XMLStreamReader streamReader) throws XMLStreamException {          
                int corePoolSize = 0, maximumPoolSize = 0, keepAliveTime = 0, blockingQueueSize = 0;
                while (streamReader.hasNext()) {
                        streamReader.next();
                        if (streamReader.isStartElement()) {
                                String elementName = streamReader.getLocalName();
                                if (elementName.equals(CORE_POOL_SIZE)) {
                                        streamReader.next();
                                        try {
                                                corePoolSize = Integer.valueOf(streamReader.getText());
                                        } catch (NumberFormatException e) {
                                        }
                                } else if (elementName.equals(MAXIMUM_POOL_SIZE)) {
                                        streamReader.next();
                                        try {
                                                maximumPoolSize = Integer.valueOf(streamReader.getText());
                                        } catch (NumberFormatException e) {
                                        }
                                } else if (elementName.equals(KEEP_ALIVE_TIME)) {
                                        streamReader.next();
                                        try {
                                                keepAliveTime = Integer.valueOf(streamReader.getText());
                                        } catch (NumberFormatException e) {
                                        }
                                } else if (elementName.equals(BLOCKING_QUEUE_SIZE)) {
                                        streamReader.next();
                                        try {
                                                blockingQueueSize = Integer.valueOf(streamReader.getText());
                                        } catch (NumberFormatException e) {
                                        }
                                } else {
                                        //ignore
                                }
                        }
                        if (streamReader.isEndElement()) {
                                String elementName = streamReader.getLocalName();
                                if (elementName.equals(ELEMENT_THREAD_POOL)) {
                                        break;
                                }
                        }
                }
                return new ThreadPoolConfig(corePoolSize, maximumPoolSize, keepAliveTime, blockingQueueSize);
        }

}
