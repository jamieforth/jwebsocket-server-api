//    ---------------------------------------------------------------------------
//    jWebSocket - Server API
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

import java.util.List;
import java.util.Map;
import org.jwebsocket.async.IOFuture;
import org.jwebsocket.kit.BroadcastOptions;
import org.jwebsocket.kit.CloseReason;
import org.jwebsocket.kit.WebSocketException;

/**
 * Specifies the API of the jWebSocket server core and its capabilities. Each 
 * server can be bound to one or multiple engines. Each engine can drive or
 * more servers above.
 * The servers usually are not supposed to directly implement any business
 * logic - except for very small or special non token based applications.
 * For applications it is recommended to implement them in plug-ins based on
 * the token server.
 * @author aschulze
 * @version $Id: WebSocketServer.java 625 2010-07-06 17:33:33Z fivefeetfurther $
 */
public interface WebSocketServer {

    /**
     * Starts the server and all underlying engines.
     * @throws WebSocketException
     */
    void startServer() throws WebSocketException;

    /**
     * States if at least one of the engines is still running.
     * @return Boolean state if at least one of the underlying engines is still running.
     */
    boolean isAlive();

    /**
     * Stops the server and all underlying engines.
     * @throws WebSocketException
     */
    void stopServer() throws WebSocketException;

    /**
     * Adds a new engine to the server.
     * @param aEngine to be added to the server.
     */
    void addEngine(WebSocketEngine aEngine);

    /**
     * Removes a already bound engine from the server.
     * @param aEngine to be removed from the server.
     */
    void removeEngine(WebSocketEngine aEngine);

    /**
     * Is called from the underlying engine when the engine is started.
     * @param aEngine
     */
    void engineStarted(WebSocketEngine aEngine);

    /**
     * Is called from the underlying engine when the engine is stopped.
     * @param aEngine
     */
    void engineStopped(WebSocketEngine aEngine);

    /**
     * Notifies the application that a client connector has been started.
     * @param aConnector the new connector that has been instantiated.
     */
    void connectorStarted(WebSocketConnector aConnector);

    /**
     * Notifies the application that a client connector has been stopped.
     * @param aConnector
     * @param aCloseReason
     */
    void connectorStopped(WebSocketConnector aConnector, CloseReason aCloseReason);

    /**
     * Is called when the underlying engine received a packet from a connector.
     * @param aEngine
     * @param aConnector
     * @param aDataPacket
     */
    void processPacket(WebSocketEngine aEngine, WebSocketConnector aConnector, WebSocketPacket aDataPacket);

    /**
     * Sends a packet to a certain connector.
     * @param aConnector 
     * @param aDataPacket
     */
    void sendPacket(WebSocketConnector aConnector, WebSocketPacket aDataPacket);

    /**
     * Sends the data packet asynchronously to the output channel through the given target
     * connector. This is a asynchronous output process which returns the future object 
     * to check the status and control the output operation.
     *  
     * @param aConnector the target connector to use for the packet output
     * @param aDataPacket the data packet 
     * @return the future object for this output operation
     */
    IOFuture sendPacketAsync(WebSocketConnector aConnector, WebSocketPacket aDataPacket);

    /**
     * Broadcasts a datapacket to all connectors.
     * @param aSource 
     * @param aDataPacket
     * @param aBroadcastOptions
     */
    void broadcastPacket(WebSocketConnector aSource, WebSocketPacket aDataPacket,
            BroadcastOptions aBroadcastOptions);

    /**
     * Returns the unique ID of the server. Because the jWebSocket model
     * supports multiple servers based on one or more engines (drivers)
     * each server has its own ID so that it can be addressed properly.
     * @return String Unique ID of the Server.
     */
    String getId();

    /**
     * Returns the plugin chain for the server .
     * @return the plugInChain
     */
    WebSocketPlugInChain getPlugInChain();

    /**
     * Returns plugin identified by id for the server.
     * @return the plugInChain
     */
    WebSocketPlugIn getPlugInById(String aId);

    /**
     * Returns the filter chain for the server.
     * @return the filterChain
     */
    WebSocketFilterChain getFilterChain();

    /**
     * Returns filter identified by Id for the server.
     * @return the filterChain
     */
    WebSocketFilter getFilterById(String aId);

    /**
     * 
     * @param aListener
     */
    void addListener(WebSocketServerListener aListener);

    /**
     *
     * @param aListener
     */
    void removeListener(WebSocketServerListener aListener);

    /**
     * Returns the list of listeners for the server.
     * @return the filterChain
     */
    List<WebSocketServerListener> getListeners();

    /**
     *
     * @param aConnector
     * @return
     */
    String getUsername(WebSocketConnector aConnector);

    /**
     *
     * @param aConnector
     * @param aUsername
     */
    void setUsername(WebSocketConnector aConnector, String aUsername);

    /**
     *
     * @param aConnector
     */
    void removeUsername(WebSocketConnector aConnector);

    /**
     *
     * @param aConnector
     * @return
     */
    String getNodeId(WebSocketConnector aConnector);

    /**
     *
     * @param aConnector
     * @param aNodeId
     */
    void setNodeId(WebSocketConnector aConnector, String aNodeId);

    /**
     *
     * @param aConnector
     */
    void removeNodeId(WebSocketConnector aConnector);

    WebSocketConnector getConnector(String aId);

    WebSocketConnector getConnector(String aFilterId, Object aFilterValue);

    WebSocketConnector getNode(String aNodeId);

    Map<String, WebSocketConnector> getConnectors(WebSocketEngine aEngine);

    Map<String, WebSocketConnector> selectConnectors(Map<String, Object> aFilter);

    Map<String, WebSocketConnector> getAllConnectors();

    void setServerConfiguration(ServerConfiguration configuration);

    ServerConfiguration getServerConfiguration();
}
