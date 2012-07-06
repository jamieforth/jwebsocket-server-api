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
import org.json.JSONObject;

/**
 * The Base interface for plugin configuration
 * @author puran
 * @version $Id: PluginConfiguration.java 1840 2011-11-28 13:41:15Z fivefeetfurther $
 */
public interface PluginConfiguration extends Configuration {

    /**
     * @return the package
     */
    String getPackage();

    /**
     * @return the jar
     */
    String getJar();

    /**
     * @return the namespace
     */
    String getNamespace();

    /**
     * @return the list of servers
     */
    List<String> getServers();

    /**
     * @return the settings
     */
    Map<String, Object> getSettings();

    /**
     * 
     * @param aKey
     * @param aDefault
     * @return
     */
    String getString(String aKey, String aDefault);

    /**
     * 
     * @param aKey
     * @return
     */
    String getString(String aKey);

    /**
     * 
     * @param aKey
     * @param aDefault
     * @return
     */
    JSONObject getJSON(String aKey, JSONObject aDefault);

    /**
     * 
     * @param aKey
     * @return
     */
    JSONObject getJSON(String aKey);
    
    /**
     * returns the enabled status of the plug-in.
     * @return
     */
    boolean getEnabled();
}
