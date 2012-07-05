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
package org.jwebsocket.api;

import java.util.List;
import java.util.Map;

import org.jwebsocket.config.JWebSocketConfig;

/**
 * Base interface that defines the methods to initialize jWebSocket engine,
 * servers and plugins. The implementation of this class can initialize in
 * different way.
 *
 * {@code JWebSocketXmlConfigInitializer} performs the initialization using
 * 'jWebSocket.xml' configuration file.
 *
 * {@code JWebSocketInitializer} performs the initialization directly using the
 * source classes and packages. This class enables user to write initialization
 * code at compile time thus helping debugging their engine, servers and plugin
 * logic.
 *
 * @author puran
 * @version $Id: WebSocketInitializer.java 596 2010-06-22 17:09:54Z
 * fivefeetfurther $
 *
 */
public interface WebSocketInitializer {

        /**
         * Initializes the loggins sub system
         */
        void initializeLogging();

        /**
         * Initialize the libraries
         */
        ClassLoader initializeLibraries();

        /**
         * Initialize the engine
         *
         * @return the initialized engine, which is ready to start
         */
        Map<String, WebSocketEngine> initializeEngines();

        /**
         * Initialize the servers, these initialized servers will not have plugins
         * initialized in plugin chain.
         *
         * @return the list of initialized servers ready to start
         */
        List<WebSocketServer> initializeServers();

        /**
         * Initialize the plugins specific to server ids.
         *
         * @return the FastMap of server id to the list of plugins associated with
         * it.
         */
        Map<String, List<WebSocketPlugIn>> initializePlugins();

        /**
         * Initialize the filters specific to the server ids
         *
         * @return the FastMap of server id to the list of filters associated with
         * it.
         */
        Map<String, List<WebSocketFilter>> initializeFilters();

        /**
         * Returns the config object
         *
         * @return the jwebsocket config object
         */
        JWebSocketConfig getConfig();
}
