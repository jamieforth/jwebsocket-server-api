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
import org.jwebsocket.config.Config;
import org.jwebsocket.kit.WebSocketRuntimeException;
/**
 * immutable class that represents the <tt>right</tt> configuration 
 * @author puran
 * @version $Id: RightConfig.java 596 2010-06-22 17:09:54Z fivefeetfurther $
 *
 */
public final class RightConfig implements Config {

        private final String mId;
        private final String mNamespace;
        private final String mDescription;
        
        /**
         * default constructor
         * @param aId the right id
         * @param aNamespace the right namespace
         * @param aDescription the description
         */
        public RightConfig(String aId, String aNamespace, String aDescription) {
                this.mId = aId;
                this.mNamespace = aNamespace;
                this.mDescription = aDescription;
                //validate right config
                validate();
        }
        
        /**
         * @return the id
         */
        public String getId() {
                return mId;
        }

        /**
         * @return the namespace
         */
        public String getNamespace() {
                return mNamespace;
        }

        /**
         * @return the description
         */
        public String getDescription() {
                return mDescription;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void validate() {
                if ((mId != null && mId.length() > 0)
                                && (mNamespace != null && mNamespace.length() > 0)
                                && (mDescription != null && mDescription.length() > 0)) {
                        return;
                }
                throw new WebSocketRuntimeException(
                                "Missing one of the right configuration, please check your configuration file");
        }

}
