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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.JSONObject;
import org.jwebsocket.config.Config;
import org.jwebsocket.config.ConfigHandler;
import org.jwebsocket.config.JWebSocketConfig;
import org.jwebsocket.kit.WebSocketRuntimeException;

/**
 * Handler class that handles the <tt>jWebSocket.xml</tt> configuration. This
 * class starts from the root and delegates the handler to specific config
 * handler, to read the whole config file.
 * 
 * @author puran
 * @author Marcos Antonio González Huerta (markos0886, UCI)
 * @version $Id: JWebSocketConfigHandler.java 596 2010-06-22 17:09:54Z
 *      fivefeetfurther $
 */
@SuppressWarnings("StaticNonFinalUsedInInitialization")
public class JWebSocketConfigHandler implements ConfigHandler {

    // We cannot use the logging subsystem here because its config needs to be
    // loaded first!
    // private static final String ELEMENT_INSTALLATION = "installation";
    private static final String ELEMENT_PROTOCOL = "protocol";
    private static final String ELEMENT_NODE_ID = "node_id";
    // private static final String ELEMENT_INITIALIZER_CLASS = "initializerClass";
    private static final String ELEMENT_LIBRARY_FOLDER = "libraryFolder";
    private static final String ELEMENT_LIBRARIES = "libraries";
    private static final String ELEMENT_LIBRARY = "library";
    private static final String ELEMENT_ENGINES = "engines";
    private static final String ELEMENT_ENGINE = "engine";
    private static final String ELEMENT_SERVERS = "servers";
    private static final String ELEMENT_SERVER = "server";
    protected static final String ELEMENT_PLUGINS = "plugins";
    protected static final String ELEMENT_PLUGIN = "plugin";
    protected static final String ELEMENT_FILTERS = "filters";
    protected static final String ELEMENT_FILTER = "filter";
    private static final String ELEMENT_LOGGING = "logging";
    private static final String ELEMENT_LOG4J = "log4j";
    private static final String ELEMENT_RIGHTS = "rights";
    private static final String ELEMENT_RIGHT = "right";
    private static final String ELEMENT_ROLES = "roles";
    private static final String ELEMENT_ROLE = "role";
    private static final String ELEMENT_USERS = "users";
    private static final String ELEMENT_USER = "user";
    protected static final String JWEBSOCKET = "jWebSocket";
    private static final String ELEMENT_THREAD_POOL = "threadPool";
    private static Map<String, ConfigHandler> handlerContext = new FastMap<String, ConfigHandler>();
    private static String JWS_MGMT_DESK_PATH = "AdminPlugIn" + System.getProperty("file.separator") + "jwsMgmtDesk.xml";

    // initialize the different config handler implementations
    static {
        handlerContext.put("library", new LibraryConfigHandler());
        handlerContext.put("engine", new EngineConfigHandler());
        handlerContext.put("plugin", new PluginConfigHandler());
        handlerContext.put("server", new ServerConfigHandler());
        handlerContext.put("user", new UserConfigHandler());
        handlerContext.put("role", new RoleConfigHandler());
        handlerContext.put("right", new RightConfigHandler());
        handlerContext.put("filter", new FilterConfigHandler());
        handlerContext.put("log4j", new LoggingConfigHandler());
        handlerContext.put("threadPool", new ThreadPoolConfigHandler());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Config processConfig(XMLStreamReader aStreamReader) {
        JWebSocketConfig.Builder lConfigBuilder = new JWebSocketConfig.Builder();

        try {
            while (aStreamReader.hasNext()) {
                aStreamReader.next();
                if (aStreamReader.isStartElement()) {
                    String lElementName = aStreamReader.getLocalName();
                    if (lElementName.equals(ELEMENT_PROTOCOL)) {
                        aStreamReader.next();
                        lConfigBuilder.setProtocol(aStreamReader.getText());
                    } else if (lElementName.equals(ELEMENT_NODE_ID)) {
                        aStreamReader.next();
                        lConfigBuilder.setNodeId(aStreamReader.getText());
                    } else if (lElementName.equals(ELEMENT_LIBRARY_FOLDER)) {
                        aStreamReader.next();
                        lConfigBuilder.setLibraryFolder(aStreamReader.getText());
                    } else if (lElementName.equals(ELEMENT_LIBRARIES)) {
                        List<LibraryConfig> lLibraries = handleLibraries(aStreamReader);
                        lConfigBuilder = lConfigBuilder.setLibraries(lLibraries);
                    } else if (lElementName.equals(ELEMENT_ENGINES)) {
                        List<EngineConfig> lEngines = handleEngines(aStreamReader);
                        lConfigBuilder = lConfigBuilder.setEngines(lEngines);
                    } else if (lElementName.equals(ELEMENT_SERVERS)) {
                        List<ServerConfig> lServers = handleServers(aStreamReader);
                        lConfigBuilder = lConfigBuilder.setServers(lServers);
                    } else if (lElementName.equals(ELEMENT_PLUGINS)) {
                        List<PluginConfig> lPlugins = handlePlugins(aStreamReader);
                        lConfigBuilder = lConfigBuilder.setPlugins(lPlugins);
                    } else if (lElementName.equals(ELEMENT_FILTERS)) {
                        List<FilterConfig> lFilters = handleFilters(aStreamReader);
                        lConfigBuilder = lConfigBuilder.setFilters(lFilters);
                    } else if (lElementName.equals(ELEMENT_LOGGING)) {
                        List<LoggingConfig> loggingConfigs = handleLoggingConfigs(aStreamReader);
                        lConfigBuilder = lConfigBuilder.setLoggingConfig(loggingConfigs);
                    } else if (lElementName.equals(ELEMENT_RIGHTS)) {
                        List<RightConfig> lGlobalRights = handleRights(aStreamReader);
                        lConfigBuilder = lConfigBuilder.setGlobalRights(lGlobalRights);
                    } else if (lElementName.equals(ELEMENT_ROLES)) {
                        List<RoleConfig> lRoles = handleRoles(aStreamReader);
                        lConfigBuilder = lConfigBuilder.setGlobalRoles(lRoles);
                    } else if (lElementName.equals(ELEMENT_USERS)) {
                        List<UserConfig> lUsers = handleUsers(aStreamReader);
                        lConfigBuilder = lConfigBuilder.setUsers(lUsers);
                    } else {
                        // ignore
                    }
                }
                if (aStreamReader.isEndElement()) {
                    String lElementName = aStreamReader.getLocalName();
                    if (lElementName.equals(JWEBSOCKET)) {
                        break;
                    }
                }
            }
        } catch (XMLStreamException lEx) {
            throw new WebSocketRuntimeException("Error parsing jWebSocket.xml configuration file", lEx);
        }

        // if no filters where given in the .xml file
        // initialize empty filter list here
        if (lConfigBuilder.getFilters() == null) {
            lConfigBuilder.setFilters(new FastList<FilterConfig>());
        }

        // now return the config object, this is the only one config object that
        // should exists
        // in the system
        return lConfigBuilder.buildConfig();
    }

    /**
     * private method to handle the user config.
     *
     * @param aStreamReader
     *      the stream reader object
     * @return the list of user config
     * @throws XMLStreamException
     *       if there's any exception reading configuration
     */
    private List<UserConfig> handleUsers(XMLStreamReader aStreamReader)
            throws XMLStreamException {
        List<UserConfig> lUsers = new FastList<UserConfig>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_USER)) {
                    UserConfig lUser =
                            (UserConfig) handlerContext.get(lElementName).processConfig(aStreamReader);
                    lUsers.add(lUser);
                }
            }
            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_USERS)) {
                    break;
                }
            }
        }
        return lUsers;
    }

    /**
     * method that reads the roles configuration
     *
     * @param aStreamReader
     *      the stream reader object
     * @return the list of roles config
     * @throws XMLStreamException
     *       if there's any exception reading configuration
     */
    private List<RoleConfig> handleRoles(XMLStreamReader aStreamReader) throws XMLStreamException {
        List<RoleConfig> lRoles = new FastList<RoleConfig>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_ROLE)) {
                    RoleConfig lRole =
                            (RoleConfig) handlerContext.get(lElementName).processConfig(aStreamReader);
                    lRoles.add(lRole);
                }
            }
            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_ROLES)) {
                    break;
                }
            }
        }
        return lRoles;
    }

    /**
     * private method to read the list of rights configuration
     *
     * @param aStreamReader
     *      the stream reader object
     * @return the list of rights configuration
     * @throws XMLStreamException
     *       if there's any exception reading configuration
     */
    private List<RightConfig> handleRights(XMLStreamReader aStreamReader) throws XMLStreamException {
        List<RightConfig> lRights = new FastList<RightConfig>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_RIGHT)) {
                    RightConfig lRight =
                            (RightConfig) handlerContext.get(lElementName).processConfig(aStreamReader);
                    lRights.add(lRight);
                }
            }
            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_RIGHTS)) {
                    break;
                }
            }
        }
        return lRights;
    }

    /**
     * private method that reads the config for plugins
     *
     * @param aStreamReader
     *      the stream reader object
     * @return the list of plugin configs
     * @throws XMLStreamException
     *       if exception occurs while reading
     */
    protected List<PluginConfig> handlePlugins(XMLStreamReader aStreamReader) throws XMLStreamException {
        List<PluginConfig> lPlugins = new FastList<PluginConfig>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_PLUGIN)) {
                    PluginConfig lPlugin =
                            (PluginConfig) handlerContext.get(lElementName).processConfig(aStreamReader);
                    lPlugins.add(lPlugin);
                }
            }
            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_PLUGINS)) {
                    break;
                }
            }
        }
        return lPlugins;
    }

    /**
     * private method that reads the config for filters
     *
     * @param aStreamReader
     *      the stream reader object
     * @return the list of filter configs
     * @throws XMLStreamException
     *       if exception occurs while reading
     */
    protected List<FilterConfig> handleFilters(XMLStreamReader aStreamReader) throws XMLStreamException {
        List<FilterConfig> lFilters = new FastList<FilterConfig>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_FILTER)) {
                    FilterConfig lFilter =
                            (FilterConfig) handlerContext.get(lElementName).processConfig(aStreamReader);
                    lFilters.add(lFilter);
                }
            }
            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_FILTERS)) {
                    break;
                }
            }
        }
        return lFilters;
    }

    /**
     * private method that reads the config for logging
     *
     * @param aStreamReader
     *      the stream reader object
     * @return the list of logging configs
     * @throws XMLStreamException
     *       if exception occurs while reading
     */
    private List<LoggingConfig> handleLoggingConfigs(XMLStreamReader aStreamReader) throws XMLStreamException {
        List<LoggingConfig> loggingConfigs = new FastList<LoggingConfig>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_LOG4J)) {
                    LoggingConfig loggingConfig =
                            (LoggingConfig) handlerContext.get(lElementName).processConfig(aStreamReader);
                    loggingConfigs.add(loggingConfig);
                }
            }
            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_LOGGING)) {
                    break;
                }
            }
        }
        return loggingConfigs;
    }

    /**
     * private method that reads the list of server configs
     *
     * @param aStreamReader
     *      the stream reader object
     * @return the list of server configs
     * @throws XMLStreamException
     *       if exception occurs reading xml
     */
    private List<ServerConfig> handleServers(XMLStreamReader aStreamReader) throws XMLStreamException {
        List<ServerConfig> lServers = new FastList<ServerConfig>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_SERVER)) {
                    ServerConfig lServer = (ServerConfig) handlerContext.get(lElementName).processConfig(aStreamReader);
                    lServers.add(lServer);
                }
            }
            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_SERVERS)) {
                    break;
                }
            }
        }
        return lServers;
    }

    /**
     * private method that reads the list of engines config from the xml file
     *
     * @param aStreamReader
     *      the stream reader object
     * @return the list of engine configs
     * @throws XMLStreamException
     *       if exception occurs while reading
     */
    private List<LibraryConfig> handleLibraries(XMLStreamReader aStreamReader) throws XMLStreamException {
        List<LibraryConfig> lLibraries = new FastList<LibraryConfig>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_LIBRARY)) {
                    LibraryConfig lLibrary =
                            (LibraryConfig) handlerContext.get(lElementName).processConfig(aStreamReader);
                    lLibraries.add(lLibrary);
                }
            }
            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_LIBRARIES)) {
                    break;
                }
            }
        }
        return lLibraries;
    }

    /**
     * private method that reads the list of engines config from the xml file
     *
     * @param aStreamReader
     *      the stream reader object
     * @return the list of engine configs
     * @throws XMLStreamException
     *       if exception occurs while reading
     */
    private List<EngineConfig> handleEngines(XMLStreamReader aStreamReader) throws XMLStreamException {
        List<EngineConfig> lEngines = new FastList<EngineConfig>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_ENGINE)) {
                    EngineConfig lEngine =
                            (EngineConfig) handlerContext.get(lElementName).processConfig(aStreamReader);
                    lEngines.add(lEngine);
                }
            }
            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(ELEMENT_ENGINES)) {
                    break;
                }
            }
        }
        return lEngines;
    }

    protected Document getDocument(String aPath) throws Exception {
        SAXBuilder lBuilder = new SAXBuilder();
        File lFile = new File(aPath);
        return (Document) lBuilder.build(lFile);
    }

    protected void saveChange(Document aDoc, String aPath) throws IOException {
        XMLOutputter lXmlOutput = new XMLOutputter();
        lXmlOutput.setFormat(Format.getPrettyFormat());
        lXmlOutput.output(aDoc, new FileWriter(aPath));
    }

    public void setEnabledPlugIn(String aId, Boolean aEnabled) throws Exception {
        Document lDoc = getDocument(JWebSocketConfig.getConfigPath());
        Element lRootNode = lDoc.getRootElement();
        Element lPlugins = lRootNode.getChild(ELEMENT_PLUGINS);
        List<Element> lPluginsList = lPlugins.getChildren(ELEMENT_PLUGIN);

        for (Element lElement : lPluginsList) {
            if (aId.equals(lElement.getChildText("id"))) {
                if (lElement.getChildText("enabled") == null) {
                    lElement.addContent(3, new Element("enabled").setText(aEnabled.toString()));
                } else {
                    lElement.getChild("enabled").setText(aEnabled.toString());
                }
            }
        }

        saveChange(lDoc, JWebSocketConfig.getConfigPath());
    }

    public void setEnabledFilter(String aId, Boolean aEnabled) throws Exception {
        Document lDoc = getDocument(JWebSocketConfig.getConfigPath());
        Element lRootNode = lDoc.getRootElement();
        Element lFilters = lRootNode.getChild(ELEMENT_FILTERS);
        List<Element> lFiltersList = lFilters.getChildren(ELEMENT_FILTER);

        for (Element lElement : lFiltersList) {
            if (aId.equals(lElement.getChildText("id"))) {
                if (lElement.getChildText("enabled") == null) {
                    lElement.addContent(3, new Element("enabled").setText(aEnabled.toString()));
                } else {
                    lElement.getChild("enabled").setText(aEnabled.toString());
                }
            }
        }

        saveChange(lDoc, JWebSocketConfig.getConfigPath());
    }

    public void addPlugInConfig(String aId) throws Exception {
        Document lDoc = getDocument(JWebSocketConfig.getConfigPath());
        Document lDocAdmin = getDocument(JWebSocketConfig.getConfigFolder(JWS_MGMT_DESK_PATH));

        Element lRootNodeAdmin = lDocAdmin.getRootElement();
        Element lPluginsAdmin = lRootNodeAdmin.getChild(ELEMENT_PLUGINS);
        List<Element> lPluginsList = lPluginsAdmin.getChildren(ELEMENT_PLUGIN);

        Element lRootNode = lDoc.getRootElement();
        Element lPlugins = lRootNode.getChild(ELEMENT_PLUGINS);

        for (int i = 0; i < lPluginsList.size(); i++) {
            if (aId.equals(lPluginsList.get(i).getChildText("id"))) {
                lPlugins.addContent((Element)lPluginsList.get(i).clone());
                lPluginsList.remove(i);
                break;
            }
        }

        saveChange(lDoc, JWebSocketConfig.getConfigPath());
        saveChange(lDocAdmin, JWebSocketConfig.getConfigFolder(JWS_MGMT_DESK_PATH));
    }

    public void addFilterConfig(String aId) throws Exception {
        Document lDoc = getDocument(JWebSocketConfig.getConfigPath());
        Document lDocAdmin = getDocument(JWebSocketConfig.getConfigFolder(JWS_MGMT_DESK_PATH));

        Element lRootNodeAdmin = lDocAdmin.getRootElement();
        Element lFiltersAdmin = lRootNodeAdmin.getChild(ELEMENT_FILTERS);
        List<Element> lFiltersList = lFiltersAdmin.getChildren(ELEMENT_FILTER);

        Element lRootNode = lDoc.getRootElement();
        Element lFilters = lRootNode.getChild(ELEMENT_FILTERS);

        for (int i = 0; i < lFiltersList.size(); i++) {
            if (aId.equals(lFiltersList.get(i).getChildText("id"))) {
                lFilters.addContent((Element)lFiltersList.get(i).clone());
                lFiltersList.remove(i);
                break;
            }
        }

        saveChange(lDoc, JWebSocketConfig.getConfigPath());
        saveChange(lDocAdmin, JWebSocketConfig.getConfigFolder(JWS_MGMT_DESK_PATH));
    }

    public void removePlugInConfig(String aId) throws Exception {
        Document lDoc = getDocument(JWebSocketConfig.getConfigPath());
        Document lDocAdmin = getDocument(JWebSocketConfig.getConfigFolder(JWS_MGMT_DESK_PATH));

        Element lRootNode = lDoc.getRootElement();
        Element lPlugins = lRootNode.getChild(ELEMENT_PLUGINS);
        List<Element> lPluginsList = lPlugins.getChildren(ELEMENT_PLUGIN);

        Element lRootNodeAdmin = lDocAdmin.getRootElement();
        Element lPluginsAdmin = lRootNodeAdmin.getChild(ELEMENT_PLUGINS);
        List<Element> lPluginsAdminList = lPluginsAdmin.getChildren(ELEMENT_PLUGIN);
        
        Boolean lExist = false;
        for (int i = 0; i < lPluginsAdminList.size(); i++) {
            if (aId.equals(lPluginsAdminList.get(i).getChildText("id"))) {
                lExist = true;
                break;
            }
        }

        for (int i = 0; i < lPluginsList.size(); i++) {
            if (aId.equals(lPluginsList.get(i).getChildText("id"))) {
                if(false == lExist) {
                    lPluginsAdmin.addContent((Element)lPluginsList.get(i).clone());
                }
                lPluginsList.remove(i);
                break;
            }
        }

        saveChange(lDoc, JWebSocketConfig.getConfigPath());
        saveChange(lDocAdmin, JWebSocketConfig.getConfigFolder(JWS_MGMT_DESK_PATH));
    }

    public void removeFilterConfig(String aId) throws Exception {
        Document lDoc = getDocument(JWebSocketConfig.getConfigPath());
        Document lDocAdmin = getDocument(JWebSocketConfig.getConfigFolder(JWS_MGMT_DESK_PATH));

        Element lRootNode = lDoc.getRootElement();
        Element lFilters = lRootNode.getChild(ELEMENT_FILTERS);
        List<Element> lFiltersList = lFilters.getChildren(ELEMENT_FILTER);

        Element lRootNodeAdmin = lDocAdmin.getRootElement();
        Element lFiltersAdmin = lRootNodeAdmin.getChild(ELEMENT_FILTERS);

        for (int i = 0; i < lFiltersList.size(); i++) {
            if (aId.equals(lFiltersList.get(i).getChildText("id"))) {
                lFiltersAdmin.addContent((Element)lFiltersList.get(i).clone());
                lFiltersList.remove(i);
                break;
            }
        }

        saveChange(lDoc, JWebSocketConfig.getConfigPath());
        saveChange(lDocAdmin, JWebSocketConfig.getConfigFolder(JWS_MGMT_DESK_PATH));
    }

    public void changeOrderOfPlugInConfig(String aId, Integer aSteps) throws Exception {
        Document lDoc = getDocument(JWebSocketConfig.getConfigPath());
        Element lRootNode = lDoc.getRootElement();
        Element lPlugins = lRootNode.getChild(ELEMENT_PLUGINS);
        List<Element> lPluginsList = lPlugins.getChildren(ELEMENT_PLUGIN);
        
        for (int i = 0; i < lPluginsList.size(); i++) {
            if (aId.equals(lPluginsList.get(i).getChildText("id"))) {
                Element lPlugIn = (Element)lPluginsList.get(i).clone();
                lPluginsList.set(i, (Element)lPluginsList.get(i + aSteps).clone());
                lPluginsList.set(i + aSteps, lPlugIn);
                break;
            }
        }

        saveChange(lDoc, JWebSocketConfig.getConfigPath());
    }
    
    public void changeOrderOfFilterConfig(String aId, Integer aSteps) throws Exception {
        Document lDoc = getDocument(JWebSocketConfig.getConfigPath());
        Element lRootNode = lDoc.getRootElement();
        Element lFilters = lRootNode.getChild(ELEMENT_FILTERS);
        List<Element> lFiltersList = lFilters.getChildren(ELEMENT_FILTER);
        
        for (int i = 0; i < lFiltersList.size(); i++) {
            if (aId.equals(lFiltersList.get(i).getChildText("id"))) {
                Element lFilter = (Element)lFiltersList.get(i).clone();
                lFiltersList.set(i, (Element)lFiltersList.get(i + aSteps).clone());
                lFiltersList.set(i + aSteps, lFilter);
                break;
            }
        }

        saveChange(lDoc, JWebSocketConfig.getConfigPath());
    }
    
    
    public static final String SETTINGS = "settings";
    public static final String SETTING = "setting";
    /**
     * Read the map of plug-in specific settings
     * @param aStreamReader
     *        the stream reader object
     * @return the list of domains for the engine
     * @throws XMLStreamException
     *         in case of stream exception
     */
    public static Map<String, Object> getSettings(XMLStreamReader aStreamReader)
            throws XMLStreamException {

        Map<String, Object> lSettings = new FastMap<String, Object>();
        while (aStreamReader.hasNext()) {
            aStreamReader.next();
            if (aStreamReader.isStartElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(SETTING)) {
                    String lKey = aStreamReader.getAttributeValue(null, "key");
                    String lType = aStreamReader.getAttributeValue(null, "type");

                    aStreamReader.next();
                    String lValue = aStreamReader.getText();

                    if (lKey != null && lValue != null) {
                        if ("json".equalsIgnoreCase(lType)) {
                            JSONObject lJSON = null;
                            try {
                                lJSON = new JSONObject(lValue);
                            } catch (Exception lEx) {
                                // TODO: handle invalid JSON code in settings properly!
                            }
                            lSettings.put(lKey, lJSON);
                        } else {
                            lSettings.put(lKey, lValue);
                        }
                    }
                }
            }

            if (aStreamReader.isEndElement()) {
                String lElementName = aStreamReader.getLocalName();
                if (lElementName.equals(SETTINGS)) {
                    break;
                }
            }
        }
        
        return lSettings;
    }
}
