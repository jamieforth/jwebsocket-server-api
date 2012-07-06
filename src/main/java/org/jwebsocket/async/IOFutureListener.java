// ---------------------------------------------------------------------------
// jWebSocket - Copyright (c) 2010 jwebsocket.org
// ---------------------------------------------------------------------------
// This program is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published by the
// Free Software Foundation; either version 3 of the License, or (at your
// option) any later version.
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
// more details.
// You should have received a copy of the GNU Lesser General Public License along
// with this program; if not, see <http://www.gnu.org/licenses/lgpl.html>.
// ---------------------------------------------------------------------------
package org.jwebsocket.async;

import java.util.EventListener;

/**
 * Listens to the result of a {@link IOFuture}. The result of the asynchronous 
 * {@link WebSocketConnector} I/O operation is notified once this listener is 
 * added by calling {@link IOFuture#addListener(IOFutureListener)}.
 *
 * <h3>Return the control to the caller quickly</h3>
 *
 * {@link #operationComplete(IOFuture)} is directly called by an I/O
 * thread.  Therefore, performing a time consuming task or a blocking operation
 * in the handler method can cause an unexpected pause during I/O.  If you need
 * to perform a blocking operation on I/O completion, try to execute the
 * operation in a different thread using a thread pool.
 *
 * @author <a href="http://purans.net/">Puran Singh</a>
 * @version $Id: IOFutureListener.java 1049 2010-09-20 05:07:32Z mailtopuran@gmail.com $
 */
public interface IOFutureListener extends EventListener {

    /**
     * Invoked when the I/O operation associated with the {@link IOFuture}
     * has been completed.
     *
     * @param future  the source {@link IOFuture} which called this
     *        callback
     */
    void operationComplete(IOFuture future) throws Exception;
}

