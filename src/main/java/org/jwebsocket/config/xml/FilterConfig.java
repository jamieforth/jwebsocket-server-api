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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jwebsocket.api.FilterConfiguration;
import org.jwebsocket.config.Config;
import org.jwebsocket.kit.WebSocketRuntimeException;

/**
 * Class that represents the filter config
 * @author puran
 * @version $Id: FilterConfig.java 596 2010-06-22 17:09:54Z fivefeetfurther $
 * 
 */
public final class FilterConfig implements Config, FilterConfiguration {

    private final String mId;
    private final String mName;
    private final String mJar;
    private final String mPackageName;
    private final String mNamespace;
    private final List<String> mServers;
    private final Map<String, String> mSettings;
    private final boolean mEnabled;

    /**
     * default constructor
     * @param id the plugin id
     * @param name the plugin name
     * @param jar the plugin jar
     * @param namespace the namespace 
     * @param settings map of settings key and value
     */
    public FilterConfig(String aId, String aName, String aPackageName, String aJar, String aNamespace,
            List<String> aServers, Map<String, String> aSettings, boolean aEnabled) {
        mId = aId;
        mName = aName;
        mPackageName = aPackageName;
        mJar = aJar;
        mNamespace = aNamespace;
        mServers = aServers;
        mSettings = aSettings;
        mEnabled = aEnabled;
        validate();
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return mId;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return mName;
    }

    /**
     * @return the package 
     */
    public String getPackage() {
        return mPackageName;
    }

    /**
     * @return the jar
     */
    @Override
    public String getJar() {
        return mJar;
    }

    /**
     * @return the namespace
     */
    @Override
    public String getNamespace() {
        return mNamespace;
    }

    /**
     * @return the list of servers
     */
    @Override
    public List<String> getServers() {
        return (null == mServers)? null : Collections.unmodifiableList(mServers);
    }

    /**
     * @return the settings
     */
    @Override
    public Map<String, String> getSettings() {
        return (null == mSettings)? null : Collections.unmodifiableMap(mSettings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        if ((mId != null && mId.length() > 0)
                && (mName != null && mName.length() > 0)
                && (mJar != null && mJar.length() > 0)
                && (mNamespace != null && mNamespace.length() > 0)) {
            return;
        }
        throw new WebSocketRuntimeException(
                "Missing one of the filter configuration, please check your configuration file");
    }

    @Override
    public String getPackageName() {
        return mPackageName;
    }

    @Override
    public boolean getEnabled() {
        return mEnabled;
    }
}
