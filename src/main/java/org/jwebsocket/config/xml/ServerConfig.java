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
import java.util.Map;
import org.jwebsocket.api.ServerConfiguration;
import org.jwebsocket.config.Config;
import org.jwebsocket.kit.WebSocketRuntimeException;

/**
 * Represents the server config 
 * @author puran
 * @version $Id: ServerConfig.java 616 2010-07-01 08:04:51Z fivefeetfurther $
 * 
 */
public final class ServerConfig implements Config, ServerConfiguration {

        private final String mId;
        private final String mName;
        private final String mJar;
        private final ThreadPoolConfig mThreadPoolConfig ;
        private Map<String, Object> mSettings;

        public ServerConfig(String aId, String aName, String aJar, Map aSettings) {
                this(aId, aName, aJar, new ThreadPoolConfig(), aSettings);
        }

        public ServerConfig(String aId, String aName, String aJar, ThreadPoolConfig aThreadPoolConfig,
                        Map aSettings) {
                this.mId = aId;
                this.mName = aName;
                this.mJar = aJar;
                //If the threadpoolconfig is not set, we just create a default-one.
                if (aThreadPoolConfig == null) {
                        this.mThreadPoolConfig = new ThreadPoolConfig();
                } else {
                        this.mThreadPoolConfig = aThreadPoolConfig ;
                }
                this.mSettings = aSettings;
                //validate the server configuration
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
         * @return the server thread pool configuration
         */
        @Override
        public ThreadPoolConfig getThreadPoolConfig () {
                return mThreadPoolConfig ;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void validate() {
                if ((mId != null && mId.length() > 0)
                                && (mName != null && mName.length() > 0)
                                && (mJar != null && mJar.length() > 0)) {
                        mThreadPoolConfig.validate();
                        return;
                }
                throw new WebSocketRuntimeException(
                                "Missing one of the server configuration, please check your configuration file");
        }

        @Override
        public Map<String, Object> getSettings() {
                return Collections.unmodifiableMap(mSettings); 
        }
        
        
}
