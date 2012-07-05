//        ---------------------------------------------------------------------------
//        jWebSocket - BaseFilterChain Implementation
//        Copyright (c) 2010 Alexander Schulze, Innotrade GmbH
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
package org.jwebsocket.filter;

import java.util.List;
import javolution.util.FastList;
import org.jwebsocket.api.*;
import org.jwebsocket.kit.FilterResponse;

/**
 *
 * @author aschulze
 * @author Marcos Antonio González Huerta (markos0886, UCI)
 */
public class BaseFilterChain implements WebSocketFilterChain {

        private List<WebSocketFilter> mFilters = new FastList<WebSocketFilter>();
        private WebSocketServer mServer = null;

        /**
         *
         * @param aServer
         */
        public BaseFilterChain(WebSocketServer aServer) {
                mServer = aServer;
        }

        /**
         * @return the server
         */
        @Override
        public WebSocketServer getServer() {
                return mServer;
        }
        
        // TODO: Filters are currently organized in a map, which does not allow to specify an order. This needs to be changed!

        @Override
        public void addFilter(WebSocketFilter aFilter) {
                mFilters.add(aFilter);
                aFilter.setFilterChain(this);
        }

        @Override
        public void removeFilter(WebSocketFilter aFilter) {
                mFilters.remove(aFilter);
                aFilter.setFilterChain(null);
        }

        /**
         *
         * @return
         */
        @Override
        public List<WebSocketFilter> getFilters() {
                return mFilters;
        }

        @Override
        public WebSocketFilter getFilterById(String aId) {
                if (aId != null) {
                        for (WebSocketFilter lFilter : mFilters) {
                                FilterConfiguration lConfig = lFilter.getFilterConfiguration();
                                if (lConfig != null && aId.equals(lConfig.getId())) {
                                        return lFilter;
                                }
                        }
                }
                return null;
        }

        @Override
        public FilterResponse processPacketIn(WebSocketConnector aConnector, WebSocketPacket aPacket) {
                FilterResponse lResponse = new FilterResponse();
                for (WebSocketFilter lFilter : mFilters) {
                        lFilter.processPacketIn(lResponse, aConnector, aPacket);
                        if (lResponse.isRejected()) {
                                break;
                        }
                }
                return lResponse;
        }

        @Override
        public FilterResponse processPacketOut(WebSocketConnector aSource, WebSocketConnector aTarget, WebSocketPacket aPacket) {
                FilterResponse lResponse = new FilterResponse();
                for (WebSocketFilter lFilter : mFilters) {
                        lFilter.processPacketOut(lResponse, aSource, aTarget, aPacket);
                        if (lResponse.isRejected()) {
                                break;
                        }
                }
                return lResponse;
        }

        @Override
        public void addFilter(Integer aPosition, WebSocketFilter aFilter) {
                mFilters.add(aPosition, aFilter);
                aFilter.setFilterChain(this);
        }
}
