//        ---------------------------------------------------------------------------
//        jWebSocket - Copyright (c) 2011 jwebsocket.org
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

import org.jwebsocket.rrpc.FailureReason;

/**
 *
 * @author kyberneees
 */
public interface IRRPCOnResponseCallback {

        void success(Object response, String from);

        void failure(FailureReason reason, String from);

        boolean isValid(Object response, String from);

        String getRequiredType();

        double getSentTime();

        double getProcessingTime();

        double getElapsedTime();

        void setRequiredType(String aType);

        void setSentTime(double aSentTime);

        void setElapsedTime(double aElapsedTime);
        
        void setProcessingTime(double aProcessingTime);
}
