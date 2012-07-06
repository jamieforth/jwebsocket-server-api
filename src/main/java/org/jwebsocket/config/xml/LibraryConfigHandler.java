//    ---------------------------------------------------------------------------
//    jWebSocket - Copyright (c) 2010 jwebsocket.org
//    ---------------------------------------------------------------------------
//    This program is free software; you can redistribute it and/or modify it
//    under the terms of the GNU Lesser General Public License as published by the
//    Free Software Foundation; either version 3 of the License, or (at your
//    option) any later version.
//    This program is distributed in the hope that it will be useful, but WITHOUT
//    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
//    FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
//    more details.
//    You should have received a copy of the GNU Lesser General Public License along
//    with this program; if not, see <http://www.gnu.org/licenses/lgpl.html>.
//    ---------------------------------------------------------------------------
package org.jwebsocket.config.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jwebsocket.config.Config;
import org.jwebsocket.config.ConfigHandler;

/**
 * Handler class that reads the <tt>library</tt> configuration
 * @author aschulze
 */
public class LibraryConfigHandler implements ConfigHandler {

    private static final String ID = "id";
    private static final String URL = "url";
    private static final String DESCRIPTION = "description";
    private static final String ELEMENT_LIBRARY = "library";

    /**
     * {@inheritDoc}
     */
    @Override
    public Config processConfig(XMLStreamReader aStreamReader) throws XMLStreamException {
        String lId = "", lURL = "", lDescription = "";
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String elementName = aStreamReader.getLocalName();
                if (elementName.equals(ID)) {
                    aStreamReader.next();
                    lId = aStreamReader.getText();
                } else if (elementName.equals(URL)) {
                    aStreamReader.next();
                    lURL = aStreamReader.getText();
                } else if (elementName.equals(DESCRIPTION)) {
                    aStreamReader.next();
                    lDescription = aStreamReader.getText();
                } else {
                    //ignore
                }
            }
            if (aStreamReader.isEndElement()) {
                String elementName = aStreamReader.getLocalName();
                if (elementName.equals(ELEMENT_LIBRARY)) {
                    break;
                }
            }
        }
        return new LibraryConfig(lId, lURL, lDescription);
    }
}
