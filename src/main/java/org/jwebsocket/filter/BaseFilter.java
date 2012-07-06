//    ---------------------------------------------------------------------------
//    jWebSocket - BaseFilter Implementation
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
package org.jwebsocket.filter;

import org.jwebsocket.kit.FilterResponse;
import org.jwebsocket.api.FilterConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketFilter;
import org.jwebsocket.api.WebSocketFilterChain;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.api.WebSocketServer;
import org.jwebsocket.config.xml.FilterConfig;

/**
 * 
 * @author aschulze
 */
public class BaseFilter implements WebSocketFilter {
    // every filter has a backward reference to its filter chain
    private String mVersion = null;
    private WebSocketFilterChain mFilterChain = null;
    private FilterConfiguration mConfiguration = null;

    public BaseFilter(FilterConfiguration aConfiguration) {
        this.mConfiguration = aConfiguration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilterConfiguration getFilterConfiguration() {
        return mConfiguration;
    }
    
    @Override
    public String toString() {
        return mConfiguration.getId();
    }

    @Override
    public void processPacketIn(FilterResponse aResponse, WebSocketConnector aConnector, WebSocketPacket aPacket) {
    }

    @Override
    public void processPacketOut(FilterResponse aResponse, WebSocketConnector aSource, WebSocketConnector aTarget, WebSocketPacket aPacket) {
    }

    /**
     * 
     * @param aFilterChain
     */
    @Override
    public void setFilterChain(WebSocketFilterChain aFilterChain) {
        mFilterChain = aFilterChain;
    }

    /**
     * @return the filterChain
     */
    @Override
    public WebSocketFilterChain getFilterChain() {
        return mFilterChain;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public WebSocketServer getServer() {
        WebSocketServer lServer = null;
        if (mFilterChain != null) {
            lServer = mFilterChain.getServer();
        }
        return lServer;
    }

    /**
     * @return the id of the filter
     */
    @Override
    public String getId() {
        return mConfiguration.getId();
    }

    /**
     * @return the name space of the filter
     */
    @Override
    public String getNS() {
        return mConfiguration.getNamespace();
    }
    
    @Override
    public boolean getEnabled() {
        return mConfiguration.getEnabled();
    }

    @Override
    public void setEnabled(boolean aEnabled) {
        Boolean lOldEnabled = mConfiguration.getEnabled();
        mConfiguration = new FilterConfig(mConfiguration.getId(), 
                mConfiguration.getName(), mConfiguration.getPackageName(), 
                mConfiguration.getJar(), mConfiguration.getNamespace(), 
                mConfiguration.getServers(), mConfiguration.getSettings(), aEnabled);
        // notify filter for change of enabled status
        if (aEnabled != lOldEnabled) {
            processEnabled(aEnabled);
        }
    }

    @Override
    public void processEnabled(boolean aEnabled) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getVersion() {
        return mVersion;
    }

    @Override
    public void setVersion(String aVersion) {
        mVersion = aVersion;
    }
}
