//  ---------------------------------------------------------------------------
//  jWebSocket Protocol DataType Enum - Copyright (c) 2011 Innotrade GmbH
//  ---------------------------------------------------------------------------
//  This program is free software; you can redistribute it and/or modify it
//  under the terms of the GNU Lesser General Public License as published by the
//  Free Software Foundation; either version 3 of the License, or (at your
//  option) any later version.
//  This program is distributed in the hope that it will be useful, but WITHOUT
//  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
//  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
//  more details.
//  You should have received a copy of the GNU Lesser General Public License along
//  with this program; if not, see <http://www.gnu.org/licenses/lgpl.html>.
//  ---------------------------------------------------------------------------
package org.jwebsocket.api;

/**
 * These enumeration specifies the supported data types for data exchange 
 * between multiple platforms.
 * @author aschulze
 */
public enum WebSocketConnectorStatus {

    /** connector is down, data cannot be send or received */
    DOWN(0),
    /** connector is up, data can be send and received */
    UP(1);
    
    private int mStatus;

    WebSocketConnectorStatus(int aStatus) {
        mStatus = aStatus;
    }

    /**
     * @return the status int value
     */
    public int getStatus() {
        return mStatus;
    }
}
