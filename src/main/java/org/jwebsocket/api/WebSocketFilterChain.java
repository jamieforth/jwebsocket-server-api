//        ---------------------------------------------------------------------------
//        jWebSocket - FilterChain API
//        Copyright (c) 2010 jWebSocket.org, Alexander Schulze, Innotrade GmbH
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
package org.jwebsocket.api;

import java.util.List;
import org.jwebsocket.kit.FilterResponse;

/**
 *
 * @author aschulze
 * @author Marcos Antonio González Huerta (markos0886, UCI)
 */
public interface WebSocketFilterChain {

        void addFilter(WebSocketFilter aFilter);
        
        void addFilter(Integer aPosition, WebSocketFilter aFilter);
        
        void removeFilter(WebSocketFilter aFilter);
        
        List<WebSocketFilter> getFilters();
        
        WebSocketFilter getFilterById(String aId);

        FilterResponse processPacketIn(WebSocketConnector aSource, WebSocketPacket aPacket);
        
        FilterResponse processPacketOut(WebSocketConnector aSource, WebSocketConnector aTarget, WebSocketPacket aPacket);

        WebSocketServer getServer();
}
