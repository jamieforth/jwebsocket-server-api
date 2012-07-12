//    ---------------------------------------------------------------------------
//    jWebSocket - Base Engine Implementation
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
package org.jwebsocket.engines;

import java.util.Map;
import javolution.util.FastMap;
import org.jwebsocket.api.*;
import org.jwebsocket.config.JWebSocketCommonConstants;
import org.jwebsocket.kit.CloseReason;
import org.jwebsocket.kit.WebSocketException;

/**
 * Provides the basic implementation of the jWebSocket engines. The
 * {@code BaseEngine} is supposed to be used as ancestor for the engine
 * implementations like e.g. the {@code TCPEngine} or the {@code NettyEngine}.
 * 
 * @author aschulze
 */
public class BaseEngine implements WebSocketEngine {

    private final Map<String, WebSocketServer> mServers =
            new FastMap<String, WebSocketServer>().shared();
    private final FastMap<String, WebSocketConnector> mConnectors =
            new FastMap<String, WebSocketConnector>().shared();
    private int mSessionTimeout = JWebSocketCommonConstants.DEFAULT_TIMEOUT;
    private EngineConfiguration mConfiguration;

    public BaseEngine(EngineConfiguration aConfiguration) {
        mConfiguration = aConfiguration;
    }

    @Override
    public void setEngineConfiguration(EngineConfiguration aConfiguration) {
        mConfiguration = aConfiguration;
    }

    @Override
    public void startEngine() throws WebSocketException {
        // this method will be overridden by engine implementations.
        // The implementation will notify server that the engine has started
        // Don't do this here: engineStarted();
    }

    @Override
    public void stopEngine(CloseReason aCloseReason) throws WebSocketException {
        try {
            // stop all connectors of this engine
            for (WebSocketConnector lConnector : mConnectors.values()) {
                lConnector.stopConnector(aCloseReason);
            }
        } catch (RuntimeException ex) {
            // log.info("Exception on sleep " + ex.getMessage());
        }
        // this method will be overridden by engine implementations.
        // The implementation will notify server that the engine has stopped
        // Don't do this here: engineStopped();
    }

    @Override
    public void engineStarted() {
        // notify servers that the engine has started
        for (WebSocketServer lServer : mServers.values()) {
            lServer.engineStarted(this);
        }
    }

    @Override
    public void engineStopped() {
        // notify servers that the engine has stopped
        for (WebSocketServer lServer : mServers.values()) {
            lServer.engineStopped(this);
        }
    }

    @Override
    public void connectorStarted(WebSocketConnector aConnector) {
        // notify servers that a connector has started
        for (WebSocketServer lServer : mServers.values()) {
            lServer.connectorStarted(aConnector);
        }
    }

    @Override
    public void connectorStopped(WebSocketConnector aConnector,
            CloseReason aCloseReason) {
        // notify servers that a connector has stopped
        for (WebSocketServer lServer : mServers.values()) {
            lServer.connectorStopped(aConnector, aCloseReason);
        }
        // once a connector stopped remove it from the list of connectors
        // FastMap ensures that the entry is being kept in shared mode
        mConnectors.remove(aConnector.getId());
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void processPacket(WebSocketConnector aConnector,
            WebSocketPacket aDataPacket) {
        Map<String, WebSocketServer> lServers = getServers();
        for (WebSocketServer lServer : lServers.values()) {
            lServer.processPacket(this, aConnector, aDataPacket);
        }
    }

    @Override
    public void sendPacket(WebSocketConnector aConnector,
            WebSocketPacket aDataPacket) {
        aConnector.sendPacket(aDataPacket);
    }

    @Override
    public void broadcastPacket(WebSocketConnector aSource,
            WebSocketPacket aDataPacket) {
        for (WebSocketConnector lConnector : mConnectors.values()) {
            lConnector.sendPacket(aDataPacket);
        }
    }

    @Override
    public void addConnector(WebSocketConnector aConnector) {
        mConnectors.put(aConnector.getId(), aConnector);
    }

    @Override
    public void removeConnector(WebSocketConnector aConnector) {
        mConnectors.remove(aConnector.getId());
    }

    @Override
    public int getSessionTimeout() {
        return mSessionTimeout;
    }

    @Override
    public void setSessionTimeout(int aSessionTimeout) {
        this.mSessionTimeout = aSessionTimeout;
    }

    @Override
    public int getMaxFrameSize() {
        return mConfiguration.getMaxFramesize();
    }

    @Override
    public Map<String, WebSocketConnector> getConnectors() {
        return mConnectors;
    }

    @Override
    public WebSocketConnector getConnectorByRemotePort(int aRemotePort) {
        for (WebSocketConnector lConnector : mConnectors.values()) {
            if (lConnector.getRemotePort() == aRemotePort) {
                return lConnector;
            }
        }
        return null;
    }

    @Override
    public Map<String, WebSocketServer> getServers() {
        return mServers; // (FastMap) (servers.unmodifiable());
    }

    @Override
    public void addServer(WebSocketServer aServer) {
        this.mServers.put(aServer.getId(), aServer);
    }

    @Override
    public void removeServer(WebSocketServer aServer) {
        this.mServers.remove(aServer.getId());
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return mConfiguration.getId();
    }

    @Override
    public EngineConfiguration getConfiguration() {
        return mConfiguration;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Integer getMaxConnections() {
        return mConfiguration.getMaxConnections();
    }
    
    
}
