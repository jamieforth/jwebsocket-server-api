//  ---------------------------------------------------------------------------
//  jWebSocket - Copyright (c) 2010 jwebsocket.org
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

import java.util.List;
import java.util.Map;

/**
 * Base interface for filter configuration
 * 
 * @author puran, aschulze
 */
public interface FilterConfiguration extends Configuration {

    String getJar();

    String getPackageName();

    String getNamespace();

    List<String> getServers();

    Map<String, String> getSettings();

    /**
     * returns the enabled status of the plug-in.
     * @return
     */
    boolean getEnabled();
}
