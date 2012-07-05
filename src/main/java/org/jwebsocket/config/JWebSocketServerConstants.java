//        ---------------------------------------------------------------------------
//        jWebSocket - Server Configuration Constants
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
package org.jwebsocket.config;

/**
 * Provides a global shared container for the jWebSocket configuration settings.
 *
 * @author aschulze
 */
public final class JWebSocketServerConstants {

        /**
         * Current version string of the jWebSocket package.
         */
        public static final String VERSION_STR = "1.0 beta 9 (nightly build 20613)";
        /**
         * Name space base for tokens and plug-ins.
         */
        public static final String NS_BASE = "org.jwebsocket";
        /**
         * constant for default installation
         */
        public static final String DEFAULT_INSTALLATION = "prod";
        /**
         * Constant for JWEBSOCKET_HOME environment variable
         */
        public static final String JWEBSOCKET_HOME = "JWEBSOCKET_HOME";
        /**
         * Constant for bootstrap.xml configuration file
         */
        public static final String BOOTSTRAP_XML = "bootstrap.xml";
        /**
         * Constant for jWebSocket.xml configuration file
         */
        public static final String JWEBSOCKET_XML = "jWebSocket.xml";
        /**
         * Constant for jWebSocketDevTemplate.xml configuration file
         */
        public static final String JWEBSOCKET_DEV_TEMPLATE_XML = "jWebSocketDevTemplate.xml";
        /**
         * Constant for jWebSocket.ks key store file
         */
        public static final String JWEBSOCKET_KEYSTORE = "jWebSocket.ks";
        /**
         * Default password for demo key store
         */
        public static final String JWEBSOCKET_KS_DEF_PWD = "jWebSocket";

        /**
         * Default engine for jWebSocket server.
         */
        public static String DEFAULT_ENGINE = "tcp";
        /**
         * Default node id if non given in jWebSocket.xml.
         * Empty means do not use node-id for single stand-alone systems
         */
        public static String DEFAULT_NODE_ID = "";
        
        /**
         * the default maximum number of connections allowed by an engine
         */
        public static final int DEFAULT_MAX_CONNECTIONS = 10000;
        
        /**
         * the default "on max connections reached" strategy {wait, close, reject, redirect}
         */
        public static final String DEFAULT_ON_MAX_CONNECTIONS_STRATEGY = "reject";
        
}
