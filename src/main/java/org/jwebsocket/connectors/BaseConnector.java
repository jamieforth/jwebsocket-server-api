//    ---------------------------------------------------------------------------
//    jWebSocket - Base Connector Implementation
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
package org.jwebsocket.connectors;

import java.net.InetAddress;
import java.util.Map;
import javolution.util.FastMap;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketConnectorStatus;
import org.jwebsocket.api.WebSocketEngine;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.async.IOFuture;
import org.jwebsocket.config.JWebSocketCommonConstants;
import org.jwebsocket.config.JWebSocketConfig;
import org.jwebsocket.kit.*;

/**
 * Provides the basic implementation of the jWebSocket connectors. The
 * {@code BaseConnector} is supposed to be used as ancestor for the connector
 * implementations like e.g. the {@code TCPConnector} or the
 * {@code NettyConnector }.
 * 
 * @author aschulze
 */
public class BaseConnector implements WebSocketConnector {

    /**
     * Default reserved name for shared custom variable <tt>username</tt>.
     */
    public final static String VAR_USERNAME = "$username";
    /**
     * Default reserved name for shared custom variable <tt>subprot</tt>.
     */
    public final static String VAR_SUBPROT = "$subprot";
    /**
     * Default reserved name for shared custom variable <tt>version</tt>.
     */
    public final static String VAR_VERSION = "$version";
    /**
     * Default name for shared custom variable <tt>nodeid</tt>.
     */
    public final static String VAR_NODEID = "$nodeid";
    /**
     * Is connector using SSL encryption?
     */
    private boolean mIsSSL = false;
    /*
     * Status of the connector.
     */
    private volatile WebSocketConnectorStatus mStatus = WebSocketConnectorStatus.DOWN;
    /**
     * The WebSocket protocol version.
     */
    private int mVersion = JWebSocketCommonConstants.WS_VERSION_DEFAULT;
    /**
     * Backward reference to the engine of this connector.
     */
    private WebSocketEngine mEngine = null;
    /**
     * Backup of the original request header and it's fields.
     * TODO: maybe obsolete for the future
     */
    private RequestHeader mHeader = null;
    /**
     * Session object for the WebSocket connection.
     */
    private final WebSocketSession mSession = new WebSocketSession();
    /**
     * Shared Variables container for this connector.
     */
    private final Map<String, Object> mCustomVars = new FastMap<String, Object>();

    private final Object mWriteLock = new Object();
    private final Object mReadLock = new Object();

    /**
     *
     * @param aEngine
     */
    public BaseConnector(WebSocketEngine aEngine) {
        mEngine = aEngine;
    }

    @Override
    public void startConnector() {
        if (mEngine != null) {
            mEngine.connectorStarted(this);
        }
    }

    @Override
    public void stopConnector(CloseReason aCloseReason) {
        if (mEngine != null) {
            mEngine.connectorStopped(this, aCloseReason);
        }
    }

    /**
     * Returns the current status for the connector.
     * Please refer to the WebSocketConnectorStatus enumeration.
     * 
     * @return 
     */
    @Override
    public WebSocketConnectorStatus getStatus() {
        return mStatus;
    }

    /**
     * Sets the current status for the connector.
     * Please refer to the WebSocketConnectorStatus enumeration.
     * 
     * @param aStatus 
     */
    @Override
    public void setStatus(WebSocketConnectorStatus aStatus) {
        mStatus = aStatus;
    }

    /**
     * 
     * @return
     */
    @Override
    public Object getWriteLock() {
        return mWriteLock;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public Object getReadLock() {
        return mReadLock;
    }
    
    @Override
    public void processPacket(WebSocketPacket aDataPacket) {
        if (mEngine != null) {
            mEngine.processPacket(this, aDataPacket);
        }
    }

    @Override
    public void processPing(WebSocketPacket aDataPacket) {
        /*
        if (mEngine != null) {
        mEngine.processPing(this, aDataPacket);
        }
         */
    }

    @Override
    public void processPong(WebSocketPacket aDataPacket) {
        /*
        if (mEngine != null) {
        mEngine.processPong(this, aDataPacket);
        }
         */
    }

    @Override
    public void sendPacketInTransaction(WebSocketPacket aDataPacket) throws WebSocketException {
    }

    @Override
    public void sendPacket(WebSocketPacket aDataPacket) {
    }

    @Override
    public IOFuture sendPacketAsync(WebSocketPacket aDataPacket) {
        return null;
    }

    @Override
    public WebSocketEngine getEngine() {
        return mEngine;
    }

    @Override
    public RequestHeader getHeader() {
        return mHeader;
    }

    /**
     * @param aHeader
     * the header to set
     */
    @Override
    public void setHeader(RequestHeader aHeader) {
        // TODO: the sub protocol should be a connector variable! not part of
        // the header!
        this.mHeader = aHeader;
        // TODO: this can be improved, maybe distinguish between header and URL
        // args!
        Map lArgs = aHeader.getArgs();
        if (lArgs != null) {
            String lNodeId = (String) lArgs.get("unid");
            if (lNodeId != null) {
                setNodeId(lNodeId);
                lArgs.remove("unid");
            }
        }
    }

    @Override
    public Object getVar(String aKey) {
        return mCustomVars.get(aKey);
    }

    @Override
    public void setVar(String aKey, Object aValue) {
        mCustomVars.put(aKey, aValue);
    }

    @Override
    public Boolean getBoolean(String aKey) {
        return (Boolean) getVar(aKey);
    }

    @Override
    public boolean getBool(String aKey) {
        Boolean lBool = getBoolean(aKey);
        return (lBool != null && lBool);
    }

    @Override
    public void setBoolean(String aKey, Boolean aValue) {
        setVar(aKey, aValue);
    }

    @Override
    public String getString(String aKey) {
        return (String) getVar(aKey);
    }

    @Override
    public void setString(String aKey, String aValue) {
        setVar(aKey, aValue);
    }

    @Override
    public Integer getInteger(String aKey) {
        return (Integer) getVar(aKey);
    }

    @Override
    public void setInteger(String aKey, Integer aValue) {
        setVar(aKey, aValue);
    }

    @Override
    public void removeVar(String aKey) {
        mCustomVars.remove(aKey);
    }

    @Override
    public String generateUID() {
        return null;
    }

    @Override
    public int getRemotePort() {
        return -1;
    }

    @Override
    public InetAddress getRemoteHost() {
        return null;
    }
    
    private String mUniqueId = null;
    private static Integer mCounter = 0;

    @Override
    public String getId() {
        synchronized(mCounter) {
            if (null == mUniqueId) {
                String lNodeId = JWebSocketConfig.getConfig().getNodeId();
                mUniqueId = ((lNodeId != null && lNodeId.length() > 0) ? lNodeId + "." : "")
                        + String.valueOf(getRemotePort())
                        + "." + mCounter++;
            }
            return mUniqueId;
        }    
    }

    @Override
    public WebSocketSession getSession() {
        return mSession;
    }

    public Map<String, Object> getVars() {
        return mCustomVars;
    }

    // some convenience methods to easier process username (login-status)
    // and configured unique node id for clusters (independent from tcp port)
    @Override
    public String getUsername() {
        return getString(BaseConnector.VAR_USERNAME);
    }

    @Override
    public void setUsername(String aUsername) {
        setString(BaseConnector.VAR_USERNAME, aUsername);
    }

    @Override
    public void removeUsername() {
        removeVar(BaseConnector.VAR_USERNAME);
    }

    // some convenience methods to easier process subprot (login-status)
    // and configured unique node id for clusters (independent from tcp port)
    @Override
    public String getSubprot() {
        return getString(BaseConnector.VAR_SUBPROT);
    }

    @Override
    public void setSubprot(String aSubprot) {
        setString(BaseConnector.VAR_SUBPROT, aSubprot);
    }

    @Override
    public int getVersion() {
        return mVersion;
    }

    @Override
    public void setVersion(int aVersion) {
        mVersion = aVersion;
    }

    @Override
    public void removeSubprot() {
        removeVar(BaseConnector.VAR_SUBPROT);
    }

    @Override
    public boolean isLocal() {
        // TODO: This has to be updated for the cluster approach
        return true;
    }

    @Override
    public String getNodeId() {
        return getString(BaseConnector.VAR_NODEID);
    }

    @Override
    public void setNodeId(String aNodeId) {
        setString(BaseConnector.VAR_NODEID, aNodeId);
    }

    @Override
    public void removeNodeId() {
        removeVar(BaseConnector.VAR_NODEID);
    }

    @Override
    public boolean isSSL() {
        return mIsSSL;
    }

    @Override
    public void setSSL(boolean aIsSSL) {
        mIsSSL = aIsSSL;
    }

    @Override
    public boolean isHixie() {
        return WebSocketProtocolAbstraction.isHixieVersion(getVersion());
    }

    @Override
    public boolean isHybi() {
        return WebSocketProtocolAbstraction.isHybiVersion(getVersion());
    }
}
