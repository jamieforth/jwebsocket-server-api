//    ---------------------------------------------------------------------------
//    jWebSocket - Plug in chain for incoming requests (per server)
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
package org.jwebsocket.plugins;

import java.util.List;
import javolution.util.FastList;
import org.apache.log4j.Logger;
import org.jwebsocket.api.*;
import org.jwebsocket.kit.CloseReason;
import org.jwebsocket.kit.PlugInResponse;
import org.jwebsocket.logging.Logging;

/**
 * Implements the basic chain of plug-ins which is triggered by a server
 * when data packets are received. Each data packet is pushed through the chain
 * and can be processed by the plug-ins.
 * @author aschulze
 * @author Marcos Antonio González Huerta (markos0886, UCI)
 */
public class BasePlugInChain implements WebSocketPlugInChain {

    private static Logger mLog = Logging.getLogger();
    private List<WebSocketPlugIn> mPlugins = new FastList<WebSocketPlugIn>();
    private WebSocketServer mServer = null;

    /**
     *
     * @param aServer
     */
    public BasePlugInChain(WebSocketServer aServer) {
        mServer = aServer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void engineStarted(WebSocketEngine aEngine) {
        if (mLog.isDebugEnabled()) {
            mLog.debug("Notifying plug-ins of server '" + getServer().getId() + "' that engine '" + aEngine.getId() + "' started...");
        }
        try {
            for (WebSocketPlugIn lPlugIn : getPlugIns()) {
                try {
                    lPlugIn.engineStarted(aEngine);
                } catch (Exception lEx) {
                    mLog.error("Engine '" + aEngine.getId()
                            + "' started at plug-in '"
                            + lPlugIn.getId() + "': "
                            + lEx.getClass().getSimpleName() + ": "
                            + lEx.getMessage());
                }
            }
        } catch (RuntimeException lEx) {
            mLog.error("Engine '" + aEngine.getId()
                    + "' started: "
                    + lEx.getClass().getSimpleName() + ": "
                    + lEx.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void engineStopped(WebSocketEngine aEngine) {
        if (mLog.isDebugEnabled()) {
            mLog.debug("Notifying plug-ins of server '" + getServer().getId()
                    + "' that engine '" + aEngine.getId()
                    + "' stopped...");
        }
        try {
            for (WebSocketPlugIn lPlugIn : getPlugIns()) {
                try {
                    lPlugIn.engineStopped(aEngine);
                } catch (Exception lEx) {
                    mLog.error("Engine '" + aEngine.getId()
                            + "' stopped at plug-in '"
                            + lPlugIn.getId() + "': "
                            + lEx.getClass().getSimpleName()
                            + ": " + lEx.getMessage());
                }
            }
        } catch (RuntimeException lEx) {
            mLog.error("Engine '" + aEngine.getId()
                    + "' stopped: "
                    + lEx.getClass().getSimpleName() + ": "
                    + lEx.getMessage());
        }
    }

    /**
     * @param aConnector
     */
    @Override
    public void connectorStarted(WebSocketConnector aConnector) {
        if (mLog.isDebugEnabled()) {
            mLog.debug("Notifying plug-ins that connector '"
                    + aConnector.getId() + "' started...");
        }
        try {
            for (WebSocketPlugIn lPlugIn : getPlugIns()) {
                if (lPlugIn.getEnabled()) {
                    try {
                        // log.debug("Notifying plug-in " + plugIn + " that connector started...");
                        lPlugIn.connectorStarted(aConnector);
                    } catch (Exception lEx) {
                        mLog.error("Connector '"
                                + aConnector.getId()
                                + "' started at plug-in '"
                                + lPlugIn.getId() + "': "
                                + lEx.getClass().getSimpleName() + ": "
                                + lEx.getMessage());
                    }
                }
            }
        } catch (RuntimeException lEx) {
            mLog.error("Connector '"
                    + aConnector.getId() + "' started (2): "
                    + lEx.getClass().getSimpleName() + ": "
                    + lEx.getMessage());
        }
    }

    /**
     *
     * @param aConnector
     * @return
     */
    @Override
    public PlugInResponse processPacket(WebSocketConnector aConnector, WebSocketPacket aDataPacket) {
        if (mLog.isDebugEnabled()) {
            mLog.debug("Processing packet for plug-ins on connector '" + aConnector.getId() + "'...");
        }
        PlugInResponse lPluginResponse = new PlugInResponse();
        for (WebSocketPlugIn lPlugIn : getPlugIns()) {
            if (lPlugIn.getEnabled()) {
                try {
                    lPlugIn.processPacket(lPluginResponse, aConnector, aDataPacket);
                } catch (RuntimeException lEx) {
                    mLog.error("Processing packet at connector '"
                            + aConnector.getId()
                            + "', plug-in '"
                            + lPlugIn.getId() + "': "
                            + lEx.getClass().getSimpleName() + ": "
                            + lEx.getMessage());
                }
                if (lPluginResponse.isChainAborted()) {
                    break;
                }
            }
        }
        return lPluginResponse;
    }

    /**
     *
     * @param aConnector
     * @param aCloseReason
     */
    @Override
    public void connectorStopped(WebSocketConnector aConnector, CloseReason aCloseReason) {
        if (mLog.isDebugEnabled()) {
            mLog.debug("Notifying plug-ins that connector '" + aConnector.getId() + "' stopped (" + aCloseReason.name() + ")...");
        }
        for (WebSocketPlugIn lPlugIn : getPlugIns()) {
            if (lPlugIn.getEnabled()) {
                try {
                    lPlugIn.connectorStopped(aConnector, aCloseReason);
                } catch (RuntimeException lEx) {
                    mLog.error("Connector '"
                            + aConnector.getId()
                            + "' stopped at plug-in '"
                            + lPlugIn.getId() + "': "
                            + lEx.getClass().getSimpleName() + ": "
                            + lEx.getMessage());
                }
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List<WebSocketPlugIn> getPlugIns() {
        return mPlugins;
    }

    /**
     *
     * @param aPlugIn
     */
    @Override
    public void addPlugIn(WebSocketPlugIn aPlugIn) {
        mPlugins.add(aPlugIn);
        aPlugIn.setPlugInChain(this);
    }

    /**
     *
     * @param aPlugIn
     */
    @Override
    public void removePlugIn(WebSocketPlugIn aPlugIn) {
        mPlugins.remove(aPlugIn);
        aPlugIn.setPlugInChain(null);
    }

    /**
     * returns a plug-in identified by the given id.
     */
    @Override
    public WebSocketPlugIn getPlugIn(String aId) {
        if (aId != null) {
            for (WebSocketPlugIn lPlugIn : mPlugins) {
                if (lPlugIn.getId() != null && aId.equals(lPlugIn.getId())) {
                    return lPlugIn;
                }
            }
        }
        return null;
    }

    /**
     * @return the server
     */
    @Override
    public WebSocketServer getServer() {
        return mServer;
    }

    @Override
    public void addPlugIn(Integer aPosition, WebSocketPlugIn aPlugIn) {
        mPlugins.add(aPosition, aPlugIn);
        aPlugIn.setPlugInChain(this);
    }
}
