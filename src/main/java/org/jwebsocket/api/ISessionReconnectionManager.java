//    ---------------------------------------------------------------------------
//    jWebSocket - Copyright (c) 2010 jwebsocket.org
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

/**
 *
 * @author kyberneees
 */
public interface ISessionReconnectionManager {

    /**
     *
     * @return The session expiration time after a client gets disconnected
     */
    Integer getSessionExpirationTime();

    /**
     *
     * @return The garbage collector process time. 
     * Remove expired session storages.
     */
    Integer getGCProcessTime();

    /**
     *
     * @return Contains the sessions identifiers to be expired
     */
    IBasicCacheStorage<String, Object> getReconnectionIndex();

    /**
     *
     * @param aSessionId The session identifier
     * @return TRUE if the session has been expired, FALSE otherwise
     */
    boolean isExpired(String aSessionId);

    /**
     * Put a session identifier in "reconnection mode", this means, the client
     * data will be stored for a future reconnection. <br> The
     * "sessionExpirationTime" is used
     *
     * @param aSessionId
     */
    void putInReconnectionMode(String aSessionId);

    /**
     *
     * @return The session identifiers trash. Contains obsolete sessions
     * identifiers
     */
    IBasicStorage<String, Object> getSessionIdsTrash();

    /**
     * @return the mStorageProvider
     */
    IStorageProvider getStorageProvider();
}
