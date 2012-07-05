/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jwebsocket.config.xml;

import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.jwebsocket.config.AdminConfig;
import org.jwebsocket.config.ConfigHandler;
import org.jwebsocket.kit.WebSocketRuntimeException;

/**
 *
 * @author Marcos Antonio González Huerta (markos0886, UCI)
 */
public class AdminConfigHandler extends JWebSocketConfigHandler implements ConfigHandler {

        @Override
        public AdminConfig processConfig(XMLStreamReader aStreamReader) {
                AdminConfig lConfig = new AdminConfig();

                try {
                        while (aStreamReader.hasNext()) {
                                aStreamReader.next();
                                if (aStreamReader.isStartElement()) {
                                        String lElementName = aStreamReader.getLocalName();
                                        if (lElementName.equals(ELEMENT_PLUGINS)) {
                                                List<PluginConfig> lPlugins = handlePlugins(aStreamReader);
                                                lConfig.setPlugins(lPlugins);
                                        } else if (lElementName.equals(ELEMENT_FILTERS)) {
                                                List<FilterConfig> lFilters = handleFilters(aStreamReader);
                                                lConfig.setFilters(lFilters);
                                        } else {
                                                // ignore
                                        }
                                }
                                if (aStreamReader.isEndElement()) {
                                        String lElementName = aStreamReader.getLocalName();
                                        if (lElementName.equals(JWEBSOCKET)) {
                                                break;
                                        }
                                }
                        }
                } catch (XMLStreamException lEx) {
                        throw new WebSocketRuntimeException("Error parsing jwsMgmtDesk.xml configuration file", lEx);
                }

                return lConfig;
        }
}
