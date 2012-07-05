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

import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javolution.util.FastMap;
import org.jwebsocket.config.Config;
import org.jwebsocket.config.ConfigHandler;

/**
 * Handler class that reads the server configuration
 *
 * @author puran
 * @version $Id: ServerConfigHandler.java 596 2010-06-22 17:09:54Z
 * fivefeetfurther $
 *
 */
public class ServerConfigHandler implements ConfigHandler {

        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String JAR = "jar";
        private static final String ELEMENT_THREAD_POOL = "threadPool";
        private static final String ELEMENT_SERVER = "server";

        /**
         * {@inheritDoc}
         */
        @Override
        public Config processConfig(XMLStreamReader aStreamReader) throws XMLStreamException {
                String lId = "", lName = "", lJar = "";
                Map<String, Object> lSettings = new FastMap();

                ThreadPoolConfig lThreadPoolConfig = null;
                while (aStreamReader.hasNext()) {
                        aStreamReader.next();
                        if (aStreamReader.isStartElement()) {
                                String elementName = aStreamReader.getLocalName();
                                if (elementName.equals(ID)) {
                                        aStreamReader.next();
                                        lId = aStreamReader.getText();
                                } else if (elementName.equals(NAME)) {
                                        aStreamReader.next();
                                        lName = aStreamReader.getText();
                                } else if (elementName.equals(JAR)) {
                                        aStreamReader.next();
                                        lJar = aStreamReader.getText();
                                } else if (elementName.equals(JWebSocketConfigHandler.SETTINGS)) {
                                        lSettings = JWebSocketConfigHandler.getSettings(aStreamReader);
                                } else if (elementName.equals(ELEMENT_THREAD_POOL)) {
                                        aStreamReader.next();
                                        lThreadPoolConfig = (ThreadPoolConfig) new ThreadPoolConfigHandler().processConfig(aStreamReader);
                                } else {
                                        //ignore
                                }
                        }
                        if (aStreamReader.isEndElement()) {
                                String elementName = aStreamReader.getLocalName();
                                if (elementName.equals(ELEMENT_SERVER)) {
                                        break;
                                }
                        }
                }
                return new ServerConfig(lId, lName, lJar, lThreadPoolConfig, lSettings);
        }
}
