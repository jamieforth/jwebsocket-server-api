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
package org.jwebsocket.config.xml;

import java.util.Collections;
import java.util.List;

import org.jwebsocket.config.Config;
import org.jwebsocket.kit.WebSocketRuntimeException;
/**
 * Immutable class for <tt>role</tt> configuration
 * @author puran
 * @version $Id: RoleConfig.java 596 2010-06-22 17:09:54Z fivefeetfurther $
 *
 */
public final class RoleConfig implements Config {
    
    private final String id;
    private final String description;
    private final List<String> rights;
    
    /**
     * Default constructor for role config
     * @param id the role id
     * @param description the role description
     * @param rights the list of rights for that role
     */
    public RoleConfig(String id, String description, List<String> rights) {
        this.id = id;
        this.description = description;
        this.rights = rights;
        validate();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the rights
     */
    public List<String> getRights() {
        return Collections.unmodifiableList(rights);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() {
        if ((id != null && id.length() > 0)
                && (description != null && description.length() > 0)) {
            return;
        }
        throw new WebSocketRuntimeException(
                "Missing one of the role configuration, please check your configuration file");
    }
    
}
