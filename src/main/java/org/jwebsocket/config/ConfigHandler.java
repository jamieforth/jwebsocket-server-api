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
package org.jwebsocket.config;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


/**
 * Base interface for config handler.
 * @author puran
 * @version $Id: ConfigHandler.java 596 2010-06-22 17:09:54Z fivefeetfurther $
 *
 */
public interface ConfigHandler {

    /**
     * Process the configuration using the give xml stream reader
     * @param streamReader the stream reader object
     * @return the config object
     * @throws XMLStreamException if exception occurs while parsing
     */
    Config processConfig(XMLStreamReader streamReader) throws XMLStreamException;
}
