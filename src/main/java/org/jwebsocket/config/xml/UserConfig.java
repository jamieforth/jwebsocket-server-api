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

import org.jwebsocket.config.Config;
import org.jwebsocket.kit.WebSocketRuntimeException;

/**
 * @authors puran, aschulze
 * @version $Id: UserConfig.java 596 2010-06-22 17:09:54Z fivefeetfurther $
 * 
 */
public final class UserConfig implements Config {

        private final String mUUID;
        private final String mLoginname;
        private final String mFirstname;
        private final String mLastname;
        private final String mPassword;
        private final String mDescription;
        private final int mStatus;
        private final List<String> mRoles;

        /**
         * Default user config constructor
         * @param aLoginname the login name
         * @param aFirstname the first name 
         * @param aLastname the last name
         * @param aPassword the password
         * @param aDescription the descritpion 
         * @param aStatus the user status
         * @param aRoles the user roles
         */
        public UserConfig(String aUUID, String aLoginname, String aFirstname, String aLastname,
                        String aPassword, String aDescription, int aStatus, List<String> aRoles) {
                mUUID = aUUID;
                mLoginname = aLoginname;
                mFirstname = aFirstname;
                mLastname = aLastname;
                mPassword = aPassword;
                mDescription = aDescription;
                mStatus = aStatus;
                mRoles = aRoles;
                //validate user config
                validate();
        }

        /**
         * @return the mUUID
         */
        public String getUUID() {
                return mUUID;
        }

        /**
         * @return the loginname
         */
        public String getLoginname() {
                return mLoginname;
        }

        /**
         * @return the firstname
         */
        public String getFirstname() {
                return mFirstname;
        }

        /**
         * @return the lastname
         */
        public String getLastname() {
                return mLastname;
        }

        /**
         * @return the password
         */
        public String getPassword() {
                return mPassword;
        }

        /**
         * @return the description
         */
        public String getDescription() {
                return mDescription;
        }

        /**
         * @return the status
         */
        public int getStatus() {
                return mStatus;
        }

        /**
         * @return the list of roles
         */
        public List<String> getRoles() {
                return Collections.unmodifiableList(mRoles);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void validate() {
                if ((mLoginname != null && mLoginname.length() > 0)
                                && (mFirstname != null && mFirstname.length() > 0)
                                && (mLastname != null && mLastname.length() > 0)
                                && (mDescription != null && mDescription.length() > 0)
                                && (mStatus > 0)) {
                        return;
                }
                throw new WebSocketRuntimeException(
                                "Missing one of the user configuration, please check your configuration file");
        }
}
