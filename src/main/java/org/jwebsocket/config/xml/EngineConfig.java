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
package org.jwebsocket.config.xml;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jwebsocket.api.EngineConfiguration;
import org.jwebsocket.config.Config;
import org.jwebsocket.kit.WebSocketRuntimeException;

/**
 * Class that represents the engine config
 *
 * @author puran
 * @version $Id: EngineConfig.java 616 2010-07-01 08:04:51Z fivefeetfurther $
 */
public final class EngineConfig implements Config, EngineConfiguration {

        private final String mId;
        private final String mName;
        private final String mJar;
        private final String mContext;
        private final String mServlet;
        private final Integer mPort;
        private final Integer mSSLPort;
        private final String mKeyStore;
        private final String mKeyStorePassword;
        private final int mTimeout;
        private final int mMaxframesize;
        private final List<String> mDomains;
        private final Integer mMaxConnections;
        private final String mOnMaxConnectionsStrategy;
        private Map<String, Object> mSettings;

        /**
         * Constructor for engine
         *
         * @param aId           the engine id
         * @param aName         the name of the engine
         * @param aJar          the jar file name
         * @param aPort         the port number where engine runs
         * @param aTimeout      the timeout value
         * @param aMaxFrameSize the maximum frame size that engine will
         *                                                receive without closing the connection
         * @param aDomains      list of domain names
         */
        public EngineConfig(String aId, String aName, String aJar, Integer aPort,
                        Integer aSSLPort, String aKeyStore, String aKeyStorePassword,
                        String aContext, String aServlet, int aTimeout,
                        int aMaxFrameSize, List<String> aDomains, Integer aMaxConnections, 
                        String aOnMaxConnectionsStrategy, Map<String, Object> aSettigns) {
                this.mId = aId;
                this.mName = aName;
                this.mJar = aJar;
                this.mContext = aContext;
                this.mServlet = aServlet;
                this.mPort = aPort;
                this.mSSLPort = aSSLPort;
                this.mKeyStore = aKeyStore;
                this.mKeyStorePassword = aKeyStorePassword;
                this.mTimeout = aTimeout;
                this.mMaxframesize = aMaxFrameSize;
                this.mDomains = aDomains;
                this.mMaxConnections = aMaxConnections;
                this.mOnMaxConnectionsStrategy = aOnMaxConnectionsStrategy;
                this.mSettings = aSettigns;
                
                validate();
        }

        /**
         * @return the id
         */
        @Override
        public String getId() {
                return mId;
        }

        /**
         * @return the name
         */
        @Override
        public String getName() {
                return mName;
        }

        /**
         * @return the jar
         */
        @Override
        public String getJar() {
                return mJar;
        }

        /**
         * @return the port
         */
        @Override
        public Integer getPort() {
                return mPort;
        }

        /**
         * @return the SSL port
         */
        @Override
        public Integer getSSLPort() {
                return mSSLPort;
        }

        /**
         * Returns the context for servlet based engines like Jetty
         * @return the context for servlet based engines, null for native servers
         */
        @Override
        public String getContext() {
                return mContext;
        }

        /**
         * Returns the servlet for servlet based engines like Jetty
         * @return the servlet for servlet based engines, null for native servers
         */
        @Override
        public String getServlet() {
                return mServlet;
        }

        /**
         * @return the timeout
         */
        @Override
        public int getTimeout() {
                return mTimeout;
        }

        /**
         * @return the max frame size
         */
        @Override
        public int getMaxFramesize() {
                return mMaxframesize;
        }

        /**
         * @return the domains
         */
        @Override
        public List<String> getDomains() {
                return mDomains;
        }

        /**
         * validate the engine configuration
         *
         * @throws WebSocketRuntimeException if any of the engine configuration is mising
         */
        @Override
        public void validate() {
                if ((mId != null && mId.length() > 0)
                                && (mName != null && mName.length() > 0)
                                && (mJar != null && mJar.length() > 0)
                                && (mDomains != null && mDomains.size() > 0)
                                // leaving port empty needs to be allowed eg. for Jetty
                                // when using underlying WebSocket Servlets
                                && (mPort == null || (mPort >= 0 && mPort < 65536))
                                && (mSSLPort == null
                                || (mSSLPort >= 0 && mSSLPort < 65536
                                && mKeyStore != null && mKeyStore.length() > 0
                                && mKeyStorePassword != null && mKeyStorePassword.length() > 0))
                                && mTimeout >= 0 
                                && mMaxConnections > 0
                                && null != mOnMaxConnectionsStrategy && mOnMaxConnectionsStrategy.length() > 0) {
                        return;
                }
                throw new WebSocketRuntimeException(
                                "Missing one of the engine configuration, "
                                + "please check your configuration file");
        }

        /**
         * @return the KeyStore
         */
        @Override
        public String getKeyStore() {
                return mKeyStore;
        }

        /**
         * @return the KeyStorePassword
         */
        @Override
        public String getKeyStorePassword() {
                return mKeyStorePassword;
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public Integer getMaxConnections() {
                return mMaxConnections;
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public String getOnMaxConnectionStrategy() {
                return mOnMaxConnectionsStrategy;
        }

        @Override
        public Map<String, Object> getSettings() {
                return Collections.unmodifiableMap(mSettings); 
        }
}
