//    ---------------------------------------------------------------------------
//    jWebSocket - Basic PlugIn Class
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

import java.util.Map;
import org.json.JSONObject;
import org.jwebsocket.kit.CloseReason;
import org.jwebsocket.kit.PlugInResponse;

/**
 * 
 * @author aschulze
 */
public interface WebSocketPlugIn {

    /**
     * returns the id of the plug-in.
     * @return
     */
    String getId();
    
    /**
     * return the version of the plug-in.
     * @return
     */
    String getVersion();
    
    /**
     * set the version of the plug-in.
     */
    void setVersion(String aVersion);

    /**
     * return the enabled status of the plug-in.
     * @return
     */
    boolean getEnabled();

    /**
     * set the enabled status of the plug-in.
     */
    void setEnabled(boolean aEnabled);

    /**
     * notifies the plug-in about a change in enabled status.
     */
    void processEnabled(boolean aEnabled);

    /**
     * returns the name of the plug-in.
     * @return
     */
    String getName();

    /**
     * is called by the server when the engine has been started.
     *
     * @param aEngine
     */
    void engineStarted(WebSocketEngine aEngine);

    /**
     * is called by the server when the engine has been stopped.
     *
     * @param aEngine
     */
    void engineStopped(WebSocketEngine aEngine);

    /**
     *
     * @param aConnector
     */
    void connectorStarted(WebSocketConnector aConnector);

    /**
     *
     * @param aResponse
     * @param aConnector
     * @param aDataPacket
     */
    void processPacket(PlugInResponse aResponse, WebSocketConnector aConnector, WebSocketPacket aDataPacket);

    /**
     *
     * @param aConnector
     * @param aCloseReason
     */
    void connectorStopped(WebSocketConnector aConnector, CloseReason aCloseReason);

    /**
     *
     * @param aPlugInChain
     */
    void setPlugInChain(WebSocketPlugInChain aPlugInChain);

    /**
     * @return the plugInChain
     */
    WebSocketPlugInChain getPlugInChain();

    /**
     * Set the plugin configuration
     *
     * @param configuration
     *      the plugin configuration object to set
     */
    // void setPluginConfiguration(PluginConfiguration configuration);
    /**
     * Returns the plugin configuration object based on the configuration file
     * values
     *
     * @return the plugin configuration object
     */
    PluginConfiguration getPluginConfiguration();

    /**
     *
     * @param aKey
     * @param aValue
     */
    void addString(String aKey, String aValue);

    /**
     *
     *
     * @param aSettings
     */
    // void addAllSettings(Map<String, String> aSettings);
    /**
     *
     * @param aKey
     */
    void removeSetting(String aKey);

    /**
     *
     */
    void clearSettings();

    /**
     *
     * @param aKey
     * @param aDefault
     * @return
     */
    String getString(String aKey, String aDefault);

    /**
     *
     * @param aKey
     * @return
     */
    String getString(String aKey);

    /**
     *
     * @param aKey
     * @param aDefault
     * @return
     */
    JSONObject getJSON(String aKey, JSONObject aDefault);

    /**
     *
     * @param aKey
     * @return
     */
    JSONObject getJSON(String aKey);

    /**
     *
     * @return
     */
    Map<String, Object> getSettings();
}
