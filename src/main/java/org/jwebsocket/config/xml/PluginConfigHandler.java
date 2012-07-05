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

import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javolution.util.FastList;
import org.jwebsocket.config.Config;
import org.jwebsocket.config.ConfigHandler;

/**
 * Config handler for reading plugins configuration
 * @author puran
 * @version $Id: PluginConfigHandler.java 596 2010-06-22 17:09:54Z fivefeetfurther $
 * 
 */
public class PluginConfigHandler implements ConfigHandler {

        private static final String ELEMENT_PLUGIN = "plugin";
        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String PACKAGE = "package";
        private static final String JAR = "jar";
        private static final String NAMESPACE = "ns";
        private static final String ENABLED = "enabled";
        private static final String SERVERS = "server-assignments";
        private static final String SERVER = "server-assignment";

        /**
         * {@inheritDoc}
         */
        @Override
        public Config processConfig(XMLStreamReader aStreamReader)
                        throws XMLStreamException {
                String lId = "", lName = "", lPackage = "", lJar = "", lNamespace = "";
                boolean lEnabled = true;
                List<String> lServers = new FastList<String>();
                Map<String, Object> lSettings = null;
                while (aStreamReader.hasNext()) {
                        aStreamReader.next();
                        if (aStreamReader.isStartElement()) {
                                String lElementName = aStreamReader.getLocalName();
                                if (lElementName.equals(ID)) {
                                        aStreamReader.next();
                                        lId = aStreamReader.getText();
                                } else if (lElementName.equals(NAME)) {
                                        aStreamReader.next();
                                        lName = aStreamReader.getText();
                                } else if (lElementName.equals(PACKAGE)) {
                                        aStreamReader.next();
                                        lPackage = aStreamReader.getText();
                                } else if (lElementName.equals(JAR)) {
                                        aStreamReader.next();
                                        lJar = aStreamReader.getText();
                                } else if (lElementName.equals(NAMESPACE)) {
                                        aStreamReader.next();
                                        lNamespace = aStreamReader.getText();
                                } else if (lElementName.equals(JWebSocketConfigHandler.SETTINGS)) {
                                        lSettings = JWebSocketConfigHandler.getSettings(aStreamReader);
                                } else if (lElementName.equals(SERVERS)) {
                                        lServers = getServers(aStreamReader);
                                } else if (lElementName.equals(ENABLED)) {
                                        aStreamReader.next();
                                        try {
                                                lEnabled = Boolean.parseBoolean(aStreamReader.getText());
                                        } catch (Exception ex) {
                                                // ignore, per default true
                                        }
                                } else {
                                        // ignore
                                }
                        }
                        if (aStreamReader.isEndElement()) {
                                String lElementName = aStreamReader.getLocalName();
                                if (lElementName.equals(ELEMENT_PLUGIN)) {
                                        break;
                                }
                        }
                }
                return new PluginConfig(lId, lName, lPackage, lJar,
                                lNamespace, lServers, lSettings, lEnabled);
        }

        /**
         * private method that reads the list of servers from the plugin configuration 
         * @param aStreamReader the stream reader object
         * @return the list of right ids
         * @throws XMLStreamException if exception while reading
         */
        private List<String> getServers(XMLStreamReader aStreamReader)
                        throws XMLStreamException {
                List<String> lServers = new FastList<String>();
                while (aStreamReader.hasNext()) {
                        aStreamReader.next();
                        if (aStreamReader.isStartElement()) {
                                String lElementName = aStreamReader.getLocalName();
                                if (lElementName.equals(SERVER)) {
                                        aStreamReader.next();
                                        String lServer = aStreamReader.getText();
                                        lServers.add(lServer);
                                }
                        }
                        if (aStreamReader.isEndElement()) {
                                String lElementName = aStreamReader.getLocalName();
                                if (lElementName.equals(SERVERS)) {
                                        break;
                                }
                        }
                }
                return lServers;
        }

        
}
