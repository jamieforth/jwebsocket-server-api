//        ---------------------------------------------------------------------------
//        jWebSocket - Basic PlugIn Class
//        Copyright (c) 2010 Alexander Schulze, Innotrade GmbH
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
package org.jwebsocket.plugins;

import java.util.Map;
import javolution.util.FastMap;
import org.json.JSONObject;
import org.jwebsocket.api.*;
import org.jwebsocket.config.xml.PluginConfig;
import org.jwebsocket.kit.CloseReason;
import org.jwebsocket.kit.PlugInResponse;

/**
 * Abstract implementation of WebSocketPlugin
 *
 * @author aschulze
 * @version $Id:$
 */
public abstract class BasePlugIn implements WebSocketPlugIn {

        private String mVersion = null;
        private WebSocketPlugInChain mPlugInChain = null;
        private Map<String, Object> mSettings = new FastMap<String, Object>();
        private PluginConfiguration mConfiguration;

        /**
         * Constructor
         *
         * @param aConfiguration the plugin configuration
         */
        public BasePlugIn(PluginConfiguration aConfiguration) {
                mConfiguration = aConfiguration;
                if (null != aConfiguration) {
                        Map<String, Object> lSettings = aConfiguration.getSettings();
                        addAllSettings(lSettings);
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public PluginConfiguration getPluginConfiguration() {
                return mConfiguration;
        }

        @Override
        public abstract void engineStarted(WebSocketEngine aEngine);

        @Override
        public abstract void engineStopped(WebSocketEngine aEngine);

        /**
         *
         * @param aConnector
         */
        @Override
        public abstract void connectorStarted(WebSocketConnector aConnector);

        /**
         *
         * @param aResponse
         * @param aConnector
         * @param aDataPacket
         */
        @Override
        public abstract void processPacket(PlugInResponse aResponse, WebSocketConnector aConnector, WebSocketPacket aDataPacket);

        /**
         *
         * @param aConnector
         * @param aCloseReason
         */
        @Override
        public abstract void connectorStopped(WebSocketConnector aConnector, CloseReason aCloseReason);

        @Override
        public void processEnabled(boolean aEnabled) {
                // this is supposed to be overwritten by 
                // the plug-in implementations if required
        }

        /**
         *
         * @param aPlugInChain
         */
        @Override
        public void setPlugInChain(WebSocketPlugInChain aPlugInChain) {
                mPlugInChain = aPlugInChain;
        }

        /**
         * @return the plugInChain
         */
        @Override
        public WebSocketPlugInChain getPlugInChain() {
                return mPlugInChain;
        }

        /**
         *
         * @return
         */
        public WebSocketServer getServer() {
                WebSocketServer lServer = null;
                if (mPlugInChain != null) {
                        lServer = mPlugInChain.getServer();
                }
                return lServer;
        }

        /**
         * Convenience method, just a wrapper for token server method
         * <tt>getUsername</tt> to simplify token plug-in code.
         *
         * @param aConnector
         * @return
         */
        public String getUsername(WebSocketConnector aConnector) {
                return getServer().getUsername(aConnector);
        }

        /**
         * Convenience method, just a wrapper for token server method
         * <tt>setUsername</tt> to simplify token plug-in code.
         *
         * @param aConnector
         * @param aUsername
         */
        public void setUsername(WebSocketConnector aConnector, String aUsername) {
                getServer().setUsername(aConnector, aUsername);
        }

        /**
         * Convenience method, just a wrapper for token server method
         * <tt>removeUsername</tt> to simplify token plug-in code.
         *
         * @param aConnector
         */
        public void removeUsername(WebSocketConnector aConnector) {
                getServer().removeUsername(aConnector);
        }

        /**
         * Convenience method, just a wrapper for token server method
         * <tt>getNodeId</tt> to simplify token plug-in code.
         *
         * @param aConnector
         * @return
         */
        public String getNodeId(WebSocketConnector aConnector) {
                return getServer().getNodeId(aConnector);
        }

        /**
         * Convenience method, just a wrapper for token server method
         * <tt>setNodeId</tt> to simplify token plug-in code.
         *
         * @param aConnector
         * @param aNodeId
         */
        public void setNodeId(WebSocketConnector aConnector, String aNodeId) {
                getServer().setNodeId(aConnector, aNodeId);
        }

        /**
         * Convenience method, just a wrapper for token server method
         * <tt>removeNodeId</tt> to simplify token plug-in code.
         *
         * @param aConnector
         */
        public void removeNodeId(WebSocketConnector aConnector) {
                getServer().removeNodeId(aConnector);
        }

        /**
         * Convenience method, just a wrapper for token server method
         * <tt>getConnector</tt> to simplify token plug-in code.
         *
         * @param aId
         * @return
         */
        public WebSocketConnector getConnector(String aId) {
                return (aId != null ? getServer().getConnector(aId) : null);
        }

        /**
         * Convenience method, just a wrapper for token server method
         * <tt>getNode</tt> to simplify token plug-in code.
         *
         * @param aNodeId
         * @return
         */
        public WebSocketConnector getNode(String aNodeId) {
                return (aNodeId != null ? getServer().getNode(aNodeId) : null);
        }

        /**
         * Convenience method, just a wrapper for token server method
         * <tt>getServer().getAllConnectors().size()</tt> to simplify token plug-in
         * code.
         *
         * @return
         */
        public int getConnectorCount() {
                return getServer().getAllConnectors().size();
        }

        /**
         *
         * @param aKey
         * @param aValue
         */
        @Override
        public void addString(String aKey, String aValue) {
                if (null != aKey) {
                        mSettings.put(aKey, aValue);
                }
        }

        /**
         * @param aSettings
         */
        // @Override
        private void addAllSettings(Map<String, Object> aSettings) {
                if (null != aSettings) {
                        mSettings.putAll(aSettings);
                }
        }

        /**
         *
         * @param aKey
         */
        @Override
        public void removeSetting(String aKey) {
                if (null != aKey) {
                        mSettings.remove(aKey);
                }
        }

        /**
         *
         */
        @Override
        public void clearSettings() {
                mSettings.clear();
        }

        /**
         *
         * @param aKey
         * @param aDefault
         * @return
         */
        @Override
        public String getString(String aKey, String aDefault) {
                Object lValue = mSettings.get(aKey);
                String lRes = null;
                if (lValue != null && lValue instanceof String) {
                        lRes = (String) lValue;
                }
                return (lRes != null ? lRes : aDefault);
        }

        /**
         *
         * @param aKey
         * @return
         */
        @Override
        public String getString(String aKey) {
                return (aKey != null ? getString(aKey, null) : null);
        }

        /**
         *
         * @param aKey
         * @param aDefault
         * @return
         */
        @Override
        public JSONObject getJSON(String aKey, JSONObject aDefault) {
                Object lValue = mSettings.get(aKey);
                JSONObject lRes = null;
                if (lValue != null && lValue instanceof JSONObject) {
                        lRes = (JSONObject) lValue;
                }
                return (lRes != null ? lRes : aDefault);
        }

        /**
         *
         * @param aKey
         * @return
         */
        @Override
        public JSONObject getJSON(String aKey) {
                return (aKey != null ? getJSON(aKey, null) : null);
        }

        @Override
        public Map<String, Object> getSettings() {
                return mSettings;
        }

        /**
         * @return the id of the plug-in
         */
        @Override
        public String getId() {
                return mConfiguration.getId();
        }

        /**
         * @return the name of the plug-in
         */
        @Override
        public String getName() {
                return mConfiguration.getName();
        }

        @Override
        public boolean getEnabled() {
                return mConfiguration.getEnabled();
        }

        @Override
        public void setEnabled(boolean aEnabled) {
                Boolean lOldEnabled = mConfiguration.getEnabled();
                mConfiguration = new PluginConfig(mConfiguration.getId(),
                                mConfiguration.getName(), mConfiguration.getPackage(),
                                mConfiguration.getJar(), mConfiguration.getNamespace(),
                                mConfiguration.getServers(), mSettings, aEnabled);
                // notify plug-in for change of enabled status
                if (aEnabled != lOldEnabled) {
                        processEnabled(aEnabled);
                }
        }

        @Override
        public String getVersion() {
                return mVersion;
        }

        @Override
        public void setVersion(String aVersion) {
                this.mVersion = aVersion;
        }
}
