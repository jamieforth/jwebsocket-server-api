//        ---------------------------------------------------------------------------
//        jWebSocket - jWebSocket Spring Bean Factory
//  Copyright (c) 2011 Innotrade GmbH, jWebSocket.org
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
package org.jwebsocket.spring;

import java.util.Map;
import javolution.util.FastMap;
import org.jwebsocket.config.JWebSocketConfig;
import org.jwebsocket.util.Tools;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 * This is required to load the bootstrap.xml config file. It provides a shared
 * beanFactory for all plug-ins and this allows inter-dependencies between the
 * plug-ins and core components.
 *
 * @author alexanderschulze
 * @author kyberneees
 */
public class JWebSocketBeanFactory {

        private static GenericApplicationContext mGlobalContext = null;
        private static Map<String, GenericApplicationContext> mContextMap = new FastMap<String, GenericApplicationContext>();

        public static GenericApplicationContext getInstance() {
                if (null == mGlobalContext) {
                        mGlobalContext = new GenericApplicationContext(new DefaultListableBeanFactory());
                }
                return mGlobalContext;
        }

        public static GenericApplicationContext getInstance(String aNamespace) {
                if (!mContextMap.containsKey(aNamespace)) {
                        mContextMap.put(aNamespace, new GenericApplicationContext(new DefaultListableBeanFactory()));
                        //Setting the default (core) application context as parent
                        mContextMap.get(aNamespace).setParent(mGlobalContext);
                }
                return mContextMap.get(aNamespace);
        }

        /**
         * Load beans from a configuration file into the global bean factory
         *
         * @param aPath
         * @param aBeanClassLoader
         */
        public static void load(String aPath, ClassLoader aBeanClassLoader) {
                load(null, aPath, aBeanClassLoader);
        }

        /**
         * Load beans from a configuration file into a specific bean factory
         *
         * @param aNamespace
         * @param aPath
         * @param aClassLoader
         */
        public static void load(String aNamespace, String aPath, ClassLoader aClassLoader) {
                String lPath = Tools.expandEnvVarsAndProps(aPath);

                XmlBeanDefinitionReader lXmlReader;
                if (null != aNamespace) {
                        lXmlReader = new XmlBeanDefinitionReader(getInstance(aNamespace));
                } else {
                        lXmlReader = new XmlBeanDefinitionReader(getInstance());
                }
                
                 lXmlReader.setBeanClassLoader(aClassLoader);

                // if no JWEBSOCKET_HOME environment variable set 
                // then use the classpath resource, otherwise the file system resource
                // System.out.println("getJWebSocketHome: '" + JWebSocketConfig.getJWebSocketHome() + "'...");
                if (JWebSocketConfig.getJWebSocketHome().isEmpty()) {
                        // System.out.println("Loading resource from classpath: " + aPath + "...");
                        lXmlReader.loadBeanDefinitions(new ClassPathResource(lPath));
                } else {
                        // System.out.println("Loading resource from filesystem: " + aPath + "...");
                        lXmlReader.loadBeanDefinitions(new FileSystemResource(lPath));
                }
        }
}
