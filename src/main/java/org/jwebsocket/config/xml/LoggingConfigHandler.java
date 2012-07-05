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
 * Handler for the logging configuration
 *
 * @author puran, aschulze
 * @version $Id: LoggingConfigHandler.java 616 2010-07-01 08:04:51Z fivefeetfurther $
 */
public class LoggingConfigHandler implements ConfigHandler {

        private static final String ELEMENT_LOG4J = "log4j";
        private static final String RELOAD_DELAY = "autoreload";

        /**
         * {@inheritDoc}
         * 
         * @param aStreamReader 
         */
        @Override
        public Config processConfig(XMLStreamReader aStreamReader)
                        throws XMLStreamException {
                Integer lReloadDelay = null;
                while (aStreamReader.hasNext()) {
                        aStreamReader.next();
                        if (aStreamReader.isStartElement()) {
                                String elementName = aStreamReader.getLocalName();
                                if (elementName.equals(RELOAD_DELAY)) {
                                        aStreamReader.next();
                                        lReloadDelay = Integer.parseInt(aStreamReader.getText());
                                } else {
                                        //ignore
                                }
                        }
                        if (aStreamReader.isEndElement()) {
                                String elementName = aStreamReader.getLocalName();
                                if (elementName.equals(ELEMENT_LOG4J)) {
                                        break;
                                }
                        }
                }
                return new LoggingConfig(lReloadDelay);
        }
}
