//        ---------------------------------------------------------------------------
//        jWebSocket - Basic server (dispatcher)
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
package org.jwebsocket.server;

import java.util.List;
import java.util.Map;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.jwebsocket.api.ServerConfiguration;
import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketEngine;
import org.jwebsocket.api.WebSocketFilter;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.api.WebSocketServer;
import org.jwebsocket.kit.BroadcastOptions;
import org.jwebsocket.kit.CloseReason;
import org.jwebsocket.api.WebSocketPlugInChain;
import org.jwebsocket.api.WebSocketFilterChain;
import org.jwebsocket.api.WebSocketPlugIn;
import org.jwebsocket.api.WebSocketServerListener;
import org.jwebsocket.async.IOFuture;
import org.jwebsocket.connectors.BaseConnector;
import org.jwebsocket.kit.WebSocketServerEvent;
import org.jwebsocket.kit.WebSocketException;

/**
 * The implementation of the basic websocket server. A server is the central
 * instance which either processes incoming data from the engines directly or
 * routes it to the chain of plug-ins. Each server maintains a FastMap of
 * underlying engines. An application can instantiate multiple servers to
 * process different kinds of data packets.
 * 
 * @author aschulze
 */
public class BaseServer implements WebSocketServer {

        private Map<String, WebSocketEngine> mEngines = null;
        private String mId = null;
        protected WebSocketPlugInChain mPlugInChain = null;
        protected WebSocketFilterChain mFilterChain = null;
        private List<WebSocketServerListener> mListeners = new FastList<WebSocketServerListener>();
        private ServerConfiguration mConfiguration;

        /**
         * Create a new instance of the Base Server. Each BaseServer maintains a
         * FastMap of all its underlying engines. Each Server has an Id whioch can
         * be used to easily address a certain server.
         * 
         * @param aId
         *            Id for the new server.
         */
        public BaseServer(ServerConfiguration aServerConfig) {
                this.mConfiguration = aServerConfig;
                mId = aServerConfig.getId();
                mEngines = new FastMap<String, WebSocketEngine>().shared();
        }

        @Override
        /**
         * {@inheritDoc }
         */
        public void addEngine(WebSocketEngine aEngine) {
                mEngines.put(aEngine.getId(), aEngine);
                aEngine.addServer(this);
        }

        @Override
        /**
         * {@inheritDoc }
         */
        public void removeEngine(WebSocketEngine aEngine) {
                mEngines.remove(aEngine.getId());
                aEngine.removeServer(this);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void startServer() throws WebSocketException {
                // this method is supposed to be overwritten by descending classes.
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public boolean isAlive() {
                // this method is supposed to be overwritten by descending classes.
                return false;
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void stopServer() throws WebSocketException {
                // this method is supposed to be overwritten by descending classes.
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void engineStarted(WebSocketEngine aEngine) {
                // this method is supposed to be overwritten by descending classes.
                // e.g. to notify the overlying appplications or plug-ins
                // about the engineStarted event
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void engineStopped(WebSocketEngine aEngine) {
                // this method is supposed to be overwritten by descending classes.
                // e.g. to notify the overlying appplications or plug-ins
                // about the engineStopped event
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void connectorStarted(WebSocketConnector aConnector) {
                // this method is supposed to be overwritten by descending classes.
                // e.g. to notify the overlying appplications or plug-ins
                // about the connectorStarted event
                WebSocketServerEvent lEvent = new WebSocketServerEvent(aConnector, this);
                for (WebSocketServerListener lListener : mListeners) {
                        if (lListener != null) {
                                lListener.processOpened(lEvent);
                        }
                }
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void connectorStopped(WebSocketConnector aConnector, CloseReason aCloseReason) {
                // this method is supposed to be overwritten by descending classes.
                // e.g. to notify the overlying appplications or plug-ins
                // about the connectorStopped event
                WebSocketServerEvent lEvent = new WebSocketServerEvent(aConnector, this);
                for (WebSocketServerListener lListener : mListeners) {
                        if (lListener != null) {
                                lListener.processClosed(lEvent);
                        }
                }
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void processPacket(WebSocketEngine aEngine, WebSocketConnector aConnector, WebSocketPacket aDataPacket) {
                // this method is supposed to be overwritten by descending classes.
                WebSocketServerEvent lEvent = new WebSocketServerEvent(aConnector, this);
                for (WebSocketServerListener lListener : getListeners()) {
                        if (lListener != null) {
                                lListener.processPacket(lEvent, aDataPacket);
                        }
                }
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void sendPacket(WebSocketConnector aConnector, WebSocketPacket aDataPacket) {
                // send a data packet to the passed connector
                aConnector.sendPacket(aDataPacket);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public IOFuture sendPacketAsync(WebSocketConnector aConnector, WebSocketPacket aDataPacket) {
                // send a data packet to the passed connector
                return aConnector.sendPacketAsync(aDataPacket);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void broadcastPacket(WebSocketConnector aSource, WebSocketPacket aDataPacket, BroadcastOptions aBroadcastOptions) {
                for (WebSocketConnector lConnector : getAllConnectors().values()) {
                        if (!aSource.equals(lConnector) || aBroadcastOptions.isSenderIncluded()) {
                                if (aBroadcastOptions.isAsync()) {
                                        sendPacketAsync(lConnector, aDataPacket);
                                } else {
                                        sendPacket(lConnector, aDataPacket);
                                }
                        }
                }
        }

        /**
         * returns the FastMap of all underlying engines. Each engine has its own
         * unique id which is used as key in the FastMap.
         * 
         * @return FastMap with the underlying engines.
         */
        public Map<String, WebSocketEngine> getEngines() {
                return (mEngines != null ? mEngines : null);
        }

        /**
         * returns all connectors of the passed engine as a FastMap. Each connector
         * has its own unique id which is used as key in the connectors FastMap.
         * 
         * @param aEngine
         * @return the engines
         */
        @Override
        public Map<String, WebSocketConnector> getConnectors(WebSocketEngine aEngine) {
                // TODO: does this need to be so nested?
                return aEngine.getConnectors();
        }

        /**
         * returns a thread safe ma of connectors of all engines connected to the
         * server. Each connector has its own unique id which is used as key in
         * the connectors FastMap.
         * 
         * @return the engines
         */
        @Override
        public Map<String, WebSocketConnector> getAllConnectors() {
                Map<String, WebSocketConnector> lClients =
                                new FastMap<String, WebSocketConnector>().shared();
                for (WebSocketEngine lEngine : mEngines.values()) {
                        lClients.putAll(lEngine.getConnectors());
                }
                return lClients;
        }

        /**
         * returns a thread-safe map of only those connectors that match the passed
         * shared variables. The search criteria is passed as a FastMap with
         * key/value pairs. The key represents the name of the shared custom
         * variable for the connector and the value the value for that variable.
         * If multiple key/value pairs are passed they are combined by a logical
         * 'and'. Each connector has its own unique id which is used as key in the
         * connectors FastMap.
         * 
         * @param aFilter
         *            FastMap of key/values pairs as search criteria.
         * @return FastMap with the selected connector or empty FastMap if no
         *         connector matches the search criteria.
         */
        @Override
        public Map<String, WebSocketConnector> selectConnectors(Map<String, Object> aFilter) {
                FastMap<String, WebSocketConnector> lClients =
                                new FastMap<String, WebSocketConnector>().shared();
                // iterate through all engines
                for (WebSocketEngine lEngine : mEngines.values()) {
                        // and through all connectors of each engine
                        for (WebSocketConnector lConnector : lEngine.getConnectors().values()) {
                                boolean lMatch = true;
                                for (String lKey : aFilter.keySet()) {
                                        Object lVarVal = lConnector.getVar(lKey);
                                        lMatch = (lVarVal != null);
                                        if (lMatch) {
                                                Object lFilterVal = aFilter.get(lKey);
                                                if (lVarVal instanceof String && lFilterVal instanceof String) {
                                                        lMatch = ((String) lVarVal).matches((String) lFilterVal);
                                                } else if (lVarVal instanceof Boolean) {
                                                        lMatch = ((Boolean) lVarVal).equals((Boolean) lFilterVal);
                                                } else {
                                                        lMatch = lVarVal.equals(lFilterVal);
                                                }
                                                if (!lMatch) {
                                                        break;
                                                }
                                        }
                                }
                                if (lMatch) {
                                        lClients.put(lConnector.getId(), lConnector);
                                }
                        }
                }
                return lClients;
        }

        /**
         * Returns the connector identified by it's connector-id or <tt>null</tt> if
         * no connector with that id could be found. This method iterates through
         * all embedded engines.
         * 
         * @param aId
         *            id of the connector to be returned.
         * @return WebSocketConnector with the given id or <tt>null</tt> if not
         *         found.
         */
        @Override
        public WebSocketConnector getConnector(String aFilterId, Object aFilterValue) {
                for (WebSocketEngine lEngine : mEngines.values()) {
                        for (WebSocketConnector lConnector : lEngine.getConnectors().values()) {
                                Object lVarVal = lConnector.getVar(aFilterId);
                                if (lVarVal instanceof String && aFilterValue instanceof String) {
                                        if (((String) lVarVal).matches((String) aFilterValue)) {
                                                return lConnector;
                                        }
                                } else if (lVarVal instanceof Boolean) {
                                        if (((Boolean) lVarVal).equals((Boolean) aFilterValue)) {
                                                return lConnector;
                                        }
                                } else {
                                        if (lVarVal.equals(aFilterValue)) {
                                                return lConnector;
                                        }
                                }
                        }
                }
                return null;
        }

        /**
         * Returns the connector identified by it's connector-id or <tt>null</tt> if
         * no connector with that id could be found. This method iterates through
         * all embedded engines.
         * 
         * @param aId
         *            id of the connector to be returned.
         * @return WebSocketConnector with the given id or <tt>null</tt> if not
         *         found.
         */
        @Override
        public WebSocketConnector getConnector(String aId) {
                for (WebSocketEngine lEngine : mEngines.values()) {
                        WebSocketConnector lConnector = lEngine.getConnectors().get(aId);
                        if (lConnector != null) {
                                return lConnector;
                        }
                }
                return null;
        }

        /**
         * Returns the connector identified by it's node-id or <tt>null</tt> if no
         * connector with that id could be found. This method iterates through all
         * embedded engines.
         * 
         * @param aId
         *            id of the connector to be returned.
         * @return WebSocketConnector with the given id or <tt>null</tt> if not
         *         found.
         */
        @Override
        public WebSocketConnector getNode(String aNodeId) {
                if (aNodeId != null) {
                        for (WebSocketEngine lEngine : mEngines.values()) {
                                for (WebSocketConnector lConnector : lEngine.getConnectors().values()) {
                                        if (aNodeId.equals(lConnector.getString(BaseConnector.VAR_NODEID))) {
                                                return lConnector;
                                        }
                                }
                        }
                }
                return null;
        }

        /**
         * Returns the connector identified by it's connector-id or <tt>null</tt> if
         * no connector with that id could be found. Only the connectors of the
         * engine identified by the passed engine are considered. If not engine with
         * that id could be found <tt>null</tt> is returned.
         * 
         * @param aEngine
         *            id of the engine of the connector.
         * @param aId
         *            id of the connector to be returned
         * @return WebSocketConnector with the given id or <tt>null</tt> if not
         *         found.
         */
        public WebSocketConnector getConnector(String aEngine, String aId) {
                WebSocketEngine lEngine = mEngines.get(aEngine);
                if (lEngine != null) {
                        return lEngine.getConnectors().get(aId);
                }
                return null;
        }

        /**
         * Returns the connector identified by it's connector-id or <tt>null</tt> if
         * no connector with that id could be found. Only the connectors of the
         * passed engine are considered. If no engine is passed <tt>null</tt> is
         * returned.
         * 
         * @param aEngine
         *            reference to the engine of the connector.
         * @param aId
         *            id of the connector to be returned
         * @return WebSocketConnector with the given id or <tt>null</tt> if not
         *         found.
         */
        public WebSocketConnector getConnector(WebSocketEngine aEngine, String aId) {
                if (aEngine != null) {
                        return aEngine.getConnectors().get(aId);
                }
                return null;
        }

        /**
         * Returns the unique id of the server. Once set by the constructor the id
         * cannot be changed anymore by the application.
         * 
         * @return Id of this server instance.
         */
        @Override
        public String getId() {
                return mId;

        }

        @Override
        public WebSocketPlugInChain getPlugInChain() {
                return mPlugInChain;
        }

        @Override
        public WebSocketPlugIn getPlugInById(String aId) {
                return mPlugInChain.getPlugIn(aId);
        }

        @Override
        public WebSocketFilterChain getFilterChain() {
                return mFilterChain;
        }

        @Override
        public WebSocketFilter getFilterById(String aId) {
                return mFilterChain.getFilterById(aId);
        }

        @Override
        public void addListener(WebSocketServerListener aListener) {
                mListeners.add(aListener);
        }

        @Override
        public void removeListener(WebSocketServerListener aListener) {
                mListeners.remove(aListener);
        }

        /**
         * @return the listeners
         */
        @Override
        public List<WebSocketServerListener> getListeners() {
                return mListeners;
        }

        @Override
        public String getUsername(WebSocketConnector aConnector) {
                return aConnector.getString(BaseConnector.VAR_USERNAME);
        }

        @Override
        public void setUsername(WebSocketConnector aConnector, String aUsername) {
                aConnector.setString(BaseConnector.VAR_USERNAME, aUsername);
        }

        @Override
        public void removeUsername(WebSocketConnector aConnector) {
                aConnector.removeVar(BaseConnector.VAR_USERNAME);
        }

        @Override
        public String getNodeId(WebSocketConnector aConnector) {
                return aConnector.getString(BaseConnector.VAR_NODEID);
        }

        @Override
        public void setNodeId(WebSocketConnector aConnector, String aNodeId) {
                aConnector.setString(BaseConnector.VAR_NODEID, aNodeId);
        }

        @Override
        public void removeNodeId(WebSocketConnector aConnector) {
                aConnector.removeVar(BaseConnector.VAR_NODEID);
        }

        @Override
        public void setServerConfiguration(ServerConfiguration aConfiguraion) {
                this.mConfiguration = aConfiguraion;
        }

        @Override
        public ServerConfiguration getServerConfiguration() {
                return mConfiguration;
        }
}
