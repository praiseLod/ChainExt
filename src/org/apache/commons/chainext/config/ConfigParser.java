/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.chainext.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.chainext.Catalog;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Class to parse the contents of an XML configuration file (using Commons
 * Digester) that defines and configures commands and command chains to be
 * registered in a {@link Catalog}. Advanced users can configure the detailed
 * parsing behavior by configuring the properties of an instance of this class
 * prior to calling the <code>parse()</code> method. It is legal to call the
 * <code>parse()</code> method more than once, in order to parse more than one
 * configuration document.
 * </p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 482967 $ $Date: 2006-12-06 08:43:48 +0000 (Wed, 06 Dec
 *          2006) $
 */
public class ConfigParser {

	// ----------------------------------------------------- Instance Variables

	/**
	 * <p>
	 * The <code>Digester</code> to be used for parsing.
	 * </p>
	 */
	private Digester digester = null;

	/**
	 * <p>
	 * The <code>RuleSet</code> to be used for configuring our Digester parsing
	 * rules.
	 * </p>
	 */
	private RuleSet ruleSet = null;

	/**
	 * <p>
	 * Should Digester use the context class loader?
	 */
	private boolean useContextClassLoader = true;

	// ------------------------------------------------------------- Properties

	/**
	 * <p>
	 * Return the <code>Digester</code> instance to be used for parsing,
	 * creating one if necessary.
	 * </p>
	 * 
	 * @return A Digester instance.
	 */
	public Digester getDigester() {

		if (digester == null) {
			digester = new Digester();
			RuleSet ruleSet = getRuleSet();
			digester.setNamespaceAware(ruleSet.getNamespaceURI() != null);
			digester.setUseContextClassLoader(getUseContextClassLoader());
			digester.setValidating(false);
			digester.addRuleSet(ruleSet);
		}
		return (digester);

	}

	/**
	 * <p>
	 * Return the <code>RuleSet</code> to be used for configuring our
	 * <code>Digester</code> parsing rules, creating one if necessary.
	 * </p>
	 * 
	 * @return The RuleSet for configuring a Digester instance.
	 */
	public RuleSet getRuleSet() {

		if (ruleSet == null) {
			ruleSet = new ConfigRuleSet();
		}
		return (ruleSet);

	}

	/**
	 * <p>
	 * Set the <code>RuleSet</code> to be used for configuring our
	 * <code>Digester</code> parsing rules.
	 * </p>
	 *
	 * @param ruleSet
	 *            The new RuleSet to use
	 */
	public void setRuleSet(RuleSet ruleSet) {

		this.digester = null;
		this.ruleSet = ruleSet;

	}

	/**
	 * <p>
	 * Return the "use context class loader" flag. If set to <code>true</code>,
	 * Digester will attempt to instantiate new command and chain instances from
	 * the context class loader.
	 * </p>
	 * 
	 * @return <code>true</code> if Digester should use the context class
	 *         loader.
	 */
	public boolean getUseContextClassLoader() {

		return (this.useContextClassLoader);

	}

	/**
	 * <p>
	 * Set the "use context class loader" flag.
	 * </p>
	 *
	 * @param useContextClassLoader
	 *            The new flag value
	 */
	public void setUseContextClassLoader(boolean useContextClassLoader) {

		this.useContextClassLoader = useContextClassLoader;

	}

	// --------------------------------------------------------- Public Methods

	/**
	 * <p>
	 * Parse the XML document at the specified URL, using the configured
	 * <code>RuleSet</code>, registering top level commands into the specified
	 * {@link Catalog}. Use this method <strong>only</strong> if you have
	 * <strong>NOT</strong> included any <code>factory</code> element in your
	 * configuration resource, and wish to supply the catalog explictly.
	 * </p>
	 *
	 * @param catalog
	 *            {@link Catalog} into which configured chains are to be
	 *            registered
	 * @param url
	 *            <code>URL</code> of the XML document to be parsed
	 *
	 * @exception Exception
	 *                if a parsing error occurs
	 *
	 * @deprecated Use parse(URL) on a configuration resource with "factory"
	 *             element(s) embedded
	 */
	public void parse(Catalog catalog, URL url) throws Exception {

		// Prepare our Digester instance
		Digester digester = getDigester();
		digester.clear();
		digester.push(catalog);

		// Parse the configuration document
		digester.parse(url);

	}

	/**
	 * <p>
	 * Parse the XML document at the specified URL using the configured
	 * <code>RuleSet</code>, registering catalogs with nested chains and
	 * commands as they are encountered. Use this method <strong>only</strong>
	 * if you have included one or more <code>factory</code> elements in your
	 * configuration resource.
	 * </p>
	 *
	 * @param url
	 *            <code>URL</code> of the XML document to be parsed
	 *
	 * @exception Exception
	 *                if a parsing error occurs
	 */
	public void parse(URL url) throws Exception {

		// Prepare our Digester instance
		Digester digester = getDigester();
		digester.clear();

		// Parse the configuration document
		digester.parse(url);

	}

	/**
	 * <p>
	 * Parse the XML document at the specified path using the configured
	 * <code>RuleSet</code>, registering catalogs with nested chains and
	 * commands as they are encountered.
	 * 
	 *
	 * @param path
	 *            <code>path</code> 可以是一个文件或一个目录，当为目录时将加载其下所有的.xml文件<br>
	 *            <blockquote> exampl path :
	 *            <ul>
	 *            <li>"" :proejct root</li>
	 *            <li>"config" :project root/config</li>
	 *            <li>"demo/config":project root/demo/config</li>
	 *            </ul>
	 *            </blockquote>
	 *
	 * @exception Exception
	 *                if a parsing error occurs
	 */
	public void parse(String path) throws Exception {
		URL url = getClassLoader().getResource(path);

		if (url == null) {
			throw new FileNotFoundException("invalide path:" + path);
		}

		if (url.getProtocol().equals("jar")) {
			parseFromJarFile(url,path);
		}else if (url.getProtocol().equals("file")) {
			parseFromSysFile(url);
		} else {
			throw new FileNotFoundException("invalide path:" + path);
		}
		
	}
	
	/**
	 * 配置文件在jar包中的解析
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	private void parseFromJarFile(URL url,String path) throws Exception {
		if(path.endsWith(".xml")){
			parse(url);
		}else{
			JarURLConnection connection = (JarURLConnection) url.openConnection();
			
			JarFile jarFile = connection.getJarFile();
			
			Enumeration<JarEntry> jarEntrys = jarFile.entries();
			
			while (jarEntrys.hasMoreElements()) {
				JarEntry jarEntry = (JarEntry) jarEntrys.nextElement();
				String entryName = jarEntry.getName();
				
				if(entryName.startsWith(path)&&entryName.endsWith(".xml")){
					URL _url = getClassLoader().getResource(jarEntry.getName());
					parse(_url);
				}
			}
		}
	}
	
	/**
	 * 配置文件在系统文件中的解析
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	private void parseFromSysFile(URL url) throws Exception {
		File rootFile = null;

		try {
			rootFile = FileUtils.toFile(url);
		} catch (Exception e) {
			rootFile = new File(url.toURI());
		}

		if (rootFile.isDirectory()) {
			IOFileFilter fileFilter = new IOFileFilter() {

				@Override
				public boolean accept(File file) {
					if (file.isDirectory()) {
						return true;
					}
					return StringUtils.substringAfterLast(file.getName(), ".").equals("xml");
				}

				@Override
				public boolean accept(File dir, String name) {
					return true;
				}

			};
			Collection<File> files = FileUtils.listFiles(rootFile, fileFilter, TrueFileFilter.TRUE);
			for (URL u : FileUtils.toURLs(files.toArray(new File[files.size()]))) {
				parse(u);
			}
		} else {
			parse(url);
		}
	}

	private ClassLoader getClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = ConfigParser.class.getClassLoader();
		}
		return loader;
	}

}
