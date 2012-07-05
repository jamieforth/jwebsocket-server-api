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

import org.jwebsocket.api.WebSocketConnector;

/**
 * The result of an asynchronous {@link WebSocketConnector} I/O operation.
 * <p>
 * This is to support asynchronous I/O operations in <tt>jWebSocket</tt>. 
 * It means any I/O calls will return immediately with no guarantee that the
 * requested I/O operation has been completed at the end of the call. 
 * Instead, you will be returned with a {@link IOFuture} instance which gives 
 * you the information about the result or status of the <tt>jWebSocket<tt>
 * I/O operation.
 * <p>
 * A {@link IOFuture} is either <em>uncompleted</em> or <em>completed</em>.
 * When an I/O operation begins, a new future object is created. The new future
 * is uncompleted initially - it is neither succeeded, failed, nor cancelled
 * because the I/O operation is not finished yet. If the I/O operation is
 * finished either successfully, with failure, or by cancellation, the future is
 * marked as completed with more specific information, such as the cause of the
 * failure. Please note that even failure and cancellation belong to the
 * completed state.
 * 
 * <pre>
 *                                      +---------------------------+
 *                                      | Completed successfully    |
 *                                      +---------------------------+
 *                                 +---->      isDone() = <b>true</b>|
 * +--------------------------+    |    |   isSuccess() = <b>true</b>|
 * |        Uncompleted       |    |    +===========================+
 * +--------------------------+    |    | Completed with failure    |
 * |      isDone() = <b>false</b>  |    +---------------------------+
 * |   isSuccess() = false    |----+---->   isDone() = <b>true</b>  |
 * | isCancelled() = false    |    |    | getCause() = <b>non-null</b>|
 * |    getCause() = null     |    |    +===========================+
 * +--------------------------+    |    | Completed by cancellation |
 *                                 |    +---------------------------+
 *                                 +---->      isDone() = <b>true</b>|
 *                                      | isCancelled() = <b>true</b>|
 *                                      +---------------------------+
 * </pre>
 * 
 * Various methods are provided to let you check if the I/O operation has been
 * completed, wait for the completion, and retrieve the result of the I/O
 * operation. It also allows you to add {@link IOFutureListener}s so you
 * can get notified when the I/O operation is completed.
 *
 * @author <a href="http://www.purans.net/">Puran Singh</a>
 */
public interface IOFuture {
  /**
   * Returns a connector where the I/O operation associated with this
   * future takes place.
   */
  WebSocketConnector getConnector();

  /**
   * Returns {@code true} if and only if this future is
   * complete, regardless of whether the operation was successful, failed,
   * or cancelled.
   */
  boolean isDone();

  /**
   * Returns {@code true} if and only if this future was
   * cancelled by a {@link #cancel()} method.
   */
  boolean isCancelled();

  /**
   * Returns {@code true} if and only if the I/O operation was completed
   * successfully.
   */
  boolean isSuccess();

  /**
   * Returns the cause of the failed I/O operation if the I/O operation has
   * failed.
   *
   * @return the cause of the failure.
   *         {@code null} if succeeded or this future is not
   *         completed yet.
   */
  Throwable getCause();

  /**
   * Cancels the I/O operation associated with this future
   * and notifies all listeners if canceled successfully.
   *
   * @return {@code true} if and only if the operation has been canceled.
   *         {@code false} if the operation can't be canceled or is already
   *         completed.
   */
  boolean cancel();

  /**
   * Marks this future as a success and notifies all
   * listeners.
   *
   * @return {@code true} if and only if successfully marked this future as
   *         a success. Otherwise {@code false} because this future is
   *         already marked as either a success or a failure.
   */
  boolean setSuccess();

  /**
   * Marks this future as a failure and notifies all
   * listeners.
   *
   * @return {@code true} if and only if successfully marked this future as
   *         a failure. Otherwise {@code false} because this future is
   *         already marked as either a success or a failure.
   */
  boolean setFailure(Throwable cause);

  /**
   * Notifies the progress of the operation to the listeners that implements
   * {@link ChannelFutureProgressListener}. Please note that this method will
   * not do anything and return {@code false} if this future is complete
   * already.
   *
   * @return {@code true} if and only if notification was made.
   */
  boolean setProgress(long amount, long current, long total);

  /**
   * Adds the specified listener to this future.  The
   * specified listener is notified when this future is
   * {@linkplain #isDone() done}.  If this future is already
   * completed, the specified listener is notified immediately.
   */
  void addListener(IOFutureListener listener);

  /**
   * Removes the specified listener from this future.
   * The specified listener is no longer notified when this
   * future is {@linkplain #isDone() done}.  If the specified
   * listener is not associated with this future, this method
   * does nothing and returns silently.
   */
  void removeListener(IOFutureListener listener);

}
