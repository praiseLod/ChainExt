package org.apache.commons.chainext;

import org.apache.commons.chainext.config.ConfigParser;

public abstract class CatalogXMLSupportTemplate {
	private final static String CONFIGPATH = "config/";
	private static CatalogFactory catalogFactory;
	
	static{
		ConfigParser parser = new ConfigParser();
		try {
			parser.parse(CONFIGPATH);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("configPath:"+CONFIGPATH);
		}
		catalogFactory = CatalogFactory.getInstance();
	}
	
	public static Catalog getCatalog(String name){
		return catalogFactory.getCatalog(name);
	}
	
}
