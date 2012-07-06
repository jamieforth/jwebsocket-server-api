//    ---------------------------------------------------------------------------
//    jWebSocket - Copyright (c) 2010 Innotrade GmbH
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
package org.jwebsocket.api;

import org.jwebsocket.kit.WebSocketServerEvent;

/**
 * Interface for the low level WebSocket listeners.
 * @author aschulze
 */
public interface WebSocketServerListener {

    /**
     * This method is invoked when a new client connects to the server.
     * @param aEvent
     */
    public void processOpened(WebSocketServerEvent aEvent);

    /**
     * This method is invoked when a data packet from a client is received.
     * The event provides getter for the server and the connector to send
     * responses to back the client.
     * @param aEvent
     * @param aPacket
     */
    public void processPacket(WebSocketServerEvent aEvent, WebSocketPacket aPacket);

    /**
     * This method is invoked when a client was disconnected from the server.
     * @param aEvent
     */
    public void processClosed(WebSocketServerEvent aEvent);
}
