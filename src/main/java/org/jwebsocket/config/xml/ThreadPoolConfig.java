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
package org.jwebsocket.config.xml;

import org.jwebsocket.config.Config;
import org.jwebsocket.kit.WebSocketRuntimeException;

/**
 * Represents the spawnThread configuration information configured via jWebSocket.xml file
 * @author Quentin
 * @version 
 */
public class ThreadPoolConfig implements Config {

  private int corePoolSize = 10;
  private int maximumPoolSize = 100;
  private int keepAliveTime = 60;
  private int blockingQueueSize = 1000;

  public ThreadPoolConfig(int aCorePoolSize, int aMaximumPoolSize, int aKeepAliveTime, int aBlockingQueueSize) {
      this.corePoolSize = aCorePoolSize;
      this.maximumPoolSize = aMaximumPoolSize;
      this.keepAliveTime = aKeepAliveTime ;
      this.blockingQueueSize = aBlockingQueueSize ;
  }
  public ThreadPoolConfig() {
  }

  @Override
  public void validate() {
      if (( corePoolSize > 0)
                && (maximumPoolSize > 0)
                && (keepAliveTime > 0)
                && (blockingQueueSize > 0)) {
            return;
        }
        throw new WebSocketRuntimeException(
                "Missing one of the thread pool configuration, "
                + "please check your configuration file");
  }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }
    public int getBlockingQueueSize() {
        return blockingQueueSize;
    }

}
