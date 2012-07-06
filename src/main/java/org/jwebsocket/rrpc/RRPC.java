//  ---------------------------------------------------------------------------
//  jWebSocket - RRPC
//  Copyright (c) 2011 Innotrade GmbH, jWebSocket.org
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
package org.jwebsocket.rrpc;

import java.util.Map;
import javolution.util.FastMap;
import org.jwebsocket.api.ITokenizable;
import org.jwebsocket.token.Token;

/**
 *
 * @author kyberneees
 */
public class RRPC implements ITokenizable {

    private String procedureName;
    private String responseType;
    private Integer timeout;
    private Map<String, Object> args;
    public static final String PROCEDURE_NAME = "$pn";
    public static final String RESPONSE_TYPE = "$rt";
    public static final String ARGUMENTS = "$args";

    /**
     * Create a new RRPC
     * 
     * @param aProcedureName
     * @param aResponseType
     * @param aTimeout 
     */
    public RRPC(String aProcedureName, String aResponseType, Integer aTimeout) {
        this.procedureName = aProcedureName;
        this.responseType = aResponseType;
        this.timeout = aTimeout;
        this.args = new FastMap<String, Object>();
    }

    /**
     * Create a new RRPC
     * 
     * @param aProcedureName
     * @param aResponseType
     * @param aTimeout
     * @param aArgs 
     */
    public RRPC(String aProcedureName, String aResponseType, Integer aTimeout, Map<String, Object> aArgs) {
        this.procedureName = aProcedureName;
        this.responseType = aResponseType;
        this.timeout = aTimeout;
        this.args = aArgs;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    @Override
    public void readFromToken(Token aToken) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void writeToToken(Token aToken) {
        aToken.setString(PROCEDURE_NAME, procedureName);
        aToken.setString(RESPONSE_TYPE, responseType);
        aToken.setMap(ARGUMENTS, args);
    }
}
