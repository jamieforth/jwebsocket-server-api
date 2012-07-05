//        ---------------------------------------------------------------------------
//        jWebSocket - Copyright (c) 2010 jwebsocket.org
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
package org.jwebsocket.kit;

import org.jwebsocket.api.WebSocketConnector;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.api.WebSocketServer;

/**
 *
 * @author aschulze
 */
public class WebSocketServerEvent {

        private WebSocketServer mServer = null;
        private WebSocketConnector mConnector = null;

        /**
         *
         * @param aConnector
         * @param aServer
         */
        public WebSocketServerEvent(WebSocketConnector aConnector, WebSocketServer aServer) {
                mConnector = aConnector;
                mServer = aServer;
        }

        /**
         * @return the sessionId
         */
        public String getSessionId() {
                return mConnector.getSession().getSessionId();
        }

        /**
         * @return the session
         */
        public WebSocketSession getSession() {
                return mConnector.getSession();
        }

        /**
         * @return the server
         */
        public WebSocketServer getServer() {
                return mServer;
        }

        /**
         * @return the connector
         */
        public WebSocketConnector getConnector() {
                return mConnector;
        }

        /**
         *
         * @param aPacket
         */
        public void sendPacket(WebSocketPacket aPacket) {
                mServer.sendPacket(mConnector, aPacket);
        }
}
