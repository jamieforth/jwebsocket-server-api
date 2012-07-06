/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jwebsocket.config;

import java.util.List;
import javolution.util.FastList;
import org.jwebsocket.config.xml.FilterConfig;
import org.jwebsocket.config.xml.PluginConfig;

/**
 *
 * @author Marcos Antonio González Huerta (markos0886, UCI)
 */
public class AdminConfig implements Config {

    private List<PluginConfig> mPlugins;
    private List<FilterConfig> mFilters;
    
    public AdminConfig() {
        mPlugins = new FastList<PluginConfig>();
        mFilters = new FastList<FilterConfig>();
    }

    @Override
    public void validate() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<FilterConfig> getFilters() {
        return mFilters;
    }

    public void setFilters(List<FilterConfig> aFilters) {
        this.mFilters = aFilters;
    }

    public List<PluginConfig> getPlugins() {
        return mPlugins;
    }

    public void setPlugins(List<PluginConfig> aPlugins) {
        this.mPlugins = aPlugins;
    }
    
    public PluginConfig getPlugin(String aIdPlugIn) {
        if (mPlugins != null) {
            for (int i = 0; i < mPlugins.size(); i++) {
                if (mPlugins.get(i).getId().equals(aIdPlugIn)) {
                    return mPlugins.get(i);
                }
            }
        }
        return null;
    }
    
    public FilterConfig getFilter(String aIdFilter) {
        if (mFilters != null) {
            for (int i = 0; i < mFilters.size(); i++) {
                if (mFilters.get(i).getId().equals(aIdFilter)) {
                    return mFilters.get(i);
                }
            }
        }
        return null;
    }
}
