//        ---------------------------------------------------------------------------
//        jWebSocket - Connector API
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
package org.jwebsocket.api;

import org.jwebsocket.async.IOFuture;
import org.jwebsocket.kit.CloseReason;
import java.net.InetAddress;
import org.jwebsocket.kit.RequestHeader;
import org.jwebsocket.kit.WebSocketException;
import org.jwebsocket.kit.WebSocketSession;

/**
 * Specifies the API for jWebSocket connectors. Connectors are the low level
 * link to the client. Connectors are maintained by the engine only but can be
 * accessed up to the application. Each connector provides a Map for shared
 * custom variables (public) which can be used in all overlying tiers.
 * @author aschulze
 */
public interface WebSocketConnector {

        /**
         * Starts and initializes the connector. Usually a connector is implemented
         * as as thread which waits on incoming data. The listener thread should
         * implement a timeout to close a connection after a configurable time of
         * inactivity on the connection. Further the {@code connectorStarted} method
         * of the overlying engine is called if the connector successfully started.
         */
        void startConnector();

        /**
         * Stops and cleans up the connector. Usually here the listener thread for
         * this connection is stopped. Further the {@code connectorStopped} method
         * of the overlying engine is called if the connector successfully started.
         *
         * @param aCloseReason
         */
        void stopConnector(CloseReason aCloseReason);

        /**
         * Returns the current status for the connector.
         * Please refer to the WebSocketConnectorStatus enumeration.
         * 
         * @return 
         */
        WebSocketConnectorStatus getStatus();

        /**
         * Sets the current status for the connector.
         * Please refer to the WebSocketConnectorStatus enumeration.
         * 
         * @param aStatus 
         */
        void setStatus(WebSocketConnectorStatus aStatus);

        /**
         * Returns the engine the connector is bound to.
         * @return WebSocketEngine Engine the connector is bound to
         */
        WebSocketEngine getEngine();

        /**
         * Processes an incoming ping from a WebSocket client. Usually the
         * ping needs to be answered by a pong.
         * @param aDataPacket raw web socket data packet
         */
        void processPing(WebSocketPacket aDataPacket);

        /**
         * Processes an incoming pong from a WebSocket client. Usually the
         * pong packet is an answer to a previously send ping.
         * @param aDataPacket raw web socket data packet
         */
        void processPong(WebSocketPacket aDataPacket);

        /**
         * Processes an incoming datapacket from a WebSocket client. Usually the
         * data packet is not processed in any way but only passed up to the
         * {@code processPacket} method of the overlying engine.
         * @param aDataPacket raw web socket data packet
         */
        void processPacket(WebSocketPacket aDataPacket);

        /**
         * Return the synchronization object for write transactions.
         * @return
         */
        public Object getWriteLock();

        /**
         * Return the synchronization object for read transactions.
         * @return
         */
        public Object getReadLock();
        
        /**
         * Sends a data packet to a WebSocket client. Here the packet is finally
         * passed to client via the web socket connection. This packet is not 
         * synchronized. This allows to send transactions by synchronizing 
         * to the getWriteLock() object.
         * @param aDataPacket raw web socket data packet
         */
        void sendPacketInTransaction(WebSocketPacket aDataPacket) throws WebSocketException;

        /**
         * Sends a data packet to a WebSocket client. Here the packet is finally
         * passed to client via the web socket connection. This method is 
         * synchronized to ensure that not multiple threads send at the same time.
         * @param aDataPacket raw web socket data packet
         */
        void sendPacket(WebSocketPacket aDataPacket);

        /**
         * Sends a data packet to a WebSocket client asynchronously. This method immediately returns
         * the future object to the caller so that it can proceed with the processing
         * and not wait for the response.
         * @param aDataPacket raw web socket data packet
         * @return the {@link IOFuture} which will be notified when the
         *         write request succeeds or fails
         * null if there's any problem with the send operation.
         */
        IOFuture sendPacketAsync(WebSocketPacket aDataPacket);

        /**
         * Returns the request header from the client during the connection
         * establishment. In the request header all fields of the client request
         * and its URL parameters are stored.
         * @return RequestHeader object
         */
        RequestHeader getHeader();

        /**
         * Sets the request header. This methode is called after the hand shake
         * of the web socket protocol has been accomplished and all data of the
         * request header is known.
         * @param aHeader RequestHeader object
         */
        void setHeader(RequestHeader aHeader);

        /**
         * Returns the given custom variable as an Object. Custom variables in a
         * connector are public and can be shared over all modules of an
         * application.
         * @param aKey Name of the shared custom variable
         * @return Object
         */
        Object getVar(String aKey);

        /**
         * Set the given custom variable to the passed value. Custom variables in a
         * connector are public and can be shared over all modules of an
         * application.
         * @param aKey Name of the shared custom variable
         * @param aValue Object
         */
        void setVar(String aKey, Object aValue);

        /**
         * Returns the boolean object of the passed variable or null if the variable
         * does not exist.
         * @param aKey Name of the shared custom variable
         * @return Boolean object
         */
        Boolean getBoolean(String aKey);

        /**
         * Returns the boolean value of the passed variable. If the variable does
         * not exist always {@code false} is returned.
         * @param aKey Name of the shared custom variable
         * @return boolean value (simple type, not an Object)
         */
        boolean getBool(String aKey);

        /**
         * Sets the boolean value of the given shared custom variable.
         * @param aKey Name of the shared custom variable
         * @param aValue Boolean value
         */
        void setBoolean(String aKey, Boolean aValue);

        /**
         * Returns the string object of the passed variable or null if the variable
         * does not exist. The default character encoding is applied.
         * @param aKey Name of the shared custom variable
         * @return String
         */
        String getString(String aKey);

        /**
         * Sets the string value of the given shared custom variable.
         * @param aKey Name of the shared custom variable
         * @param aValue String
         */
        void setString(String aKey, String aValue);

        /**
         * Returns the integer object of the passed variable or null if the variable
         * does not exist.
         * @param aKey Name of the shared custom variable
         * @return Integer object
         */
        Integer getInteger(String aKey);

        /**
         * Sets the integer value of the given shared custom variable.
         * @param aKey Name of the shared custom variable
         * @param aValue Integer value
         */
        void setInteger(String aKey, Integer aValue);

        /**
         * Removes the given shared custom variable from the connector.
         * After this operation the variable is not accessible anymore.
         * @param aKey Name of the shared custom variable
         */
        void removeVar(String aKey);

        /**
         * Generates a unique ID for this connector to be used to calculate
         * a session ID in overlying tiers.
         * @return a unique ID for this connector
         */
        String generateUID();

        /**
         * Returns the remote port of the connected client.
         * @return int Number of the remote port.
         */
        int getRemotePort();

        /**
         * Returns the IP number of the connected remote host.
         * @return InetAddress object of the given remote host
         */
        InetAddress getRemoteHost();

        /**
         * Returns the unique id of the connector. This ID is not security related,
         * but to address a certain client in the WebSocket network work only.
         * Because a multiple logins for a user are basically supported, the user-id
         * cannot be used to address a client. The descendant classes use the shared
         * custom variables of the connectors to store user specific data.
         * @return String Unique id of the connector.
         */
        String getId();

        /*
         * Returns the session for the websocket connection.
         */
        /**
         *
         * @return
         */
        WebSocketSession getSession();

        /**
         *
         * @return
         */
        String getUsername();

        /**
         *
         * @param aUsername
         */
        void setUsername(String aUsername);

        /**
         *
         */
        void removeUsername();

        /**
         *
         * @return
         */
        String getSubprot();

        /**
         *
         * @param aSubprot
         */
        void setSubprot(String aSubprot);

        /**
         *
         * @return
         */
        int getVersion();

        /**
         *
         * @param aVersion 
         */
        void setVersion(int aVersion);

        /**
         *
         */
        void removeSubprot();

        /**
         * returns if the connector is connected to a local TCP port or
         * if it is a connection on a remote (cluster) node.
         * @return
         */
        boolean isLocal();

        /**
         *
         * @return
         */
        String getNodeId();

        /**
         *
         * @param aNodeId
         */
        void setNodeId(String aNodeId);

        /**
         *
         */
        void removeNodeId();

        /**
         *
         * 
         * @return 
         */
        boolean isSSL();

        /**
         *
         * 
         * @param aIsSSL 
         */
        void setSSL(boolean aIsSSL);

        /**
         * 
         * @return
         */
        boolean isHixie();

        /**
         * 
         * @return
         */
        boolean isHybi();
}
