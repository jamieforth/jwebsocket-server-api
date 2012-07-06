//    ---------------------------------------------------------------------------
//    jWebSocket - Filter API
//    Copyright (c) 2010 Alexander Schulze, Innotrade GmbH
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

import org.jwebsocket.kit.FilterResponse;

/**
 *
 * @author aschulze
 */
public interface WebSocketFilter {

    /**
     *
     * @param aResponse
     * @param aConnector
     * @param aPacket
     */
    void processPacketIn(FilterResponse aResponse, WebSocketConnector aConnector, WebSocketPacket aPacket);

    /**
     *
     * @param aResponse
     * @param aSource
     * @param aTarget
     * @param aPacket
     */
    void processPacketOut(FilterResponse aResponse, WebSocketConnector aSource, WebSocketConnector aTarget, WebSocketPacket aPacket);

    /**
     *
     * @param aFilterChain
     */
    public void setFilterChain(WebSocketFilterChain aFilterChain);

    /**
     * @return the filterChain
     */
    public WebSocketFilterChain getFilterChain();
    
    /**
     * Returns the filter configuration object based on the configuration file
     * values
     *
     * @return the filter configuration object
     */
    public FilterConfiguration getFilterConfiguration();
    
    /**
     * @return the Id of the filter
     */
    public String getId();

    /**
     * @return the name space of the filter
     */
    public String getNS();
    
    /**
     * return the version of the plug-in.
     * @return
     */
    String getVersion();
    
    /**
     * set the version of the filter.
     */
    void setVersion(String aVersion);
    
    /**
     * return the enabled status of the filter.
     */
    boolean getEnabled();

    /**
     * set the enabled status of the filter.
     */
    void setEnabled(boolean aEnabled);

    /**
     * notifies the filter about a change in enabled status.
     */
    void processEnabled(boolean aEnabled);
    
    WebSocketServer getServer();
}
