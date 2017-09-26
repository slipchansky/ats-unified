package com.genesyslab.gks.ats.config;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Class convert string of ats properties written on java properties notation with
 * Each property separated by ";".
 * 
 * If properties contains "default.properties.path" property,
 * than first driver properties will be loaded from one.
 */
public final class AtsConfigActor {
	private final static Logger LOG = Logger.getLogger(AtsConfigActor.class.getName());
    public static final String DEFAULT_PROPERTIES_FILEPATH = "default.properties.path";

    private final Properties config;

    /** 
     * Converts the path returned be the system to the Properties-required path format  
     * @return
     */
    private static String getAtsHomePath() {
    	String path = System.getenv("ATS_HOME");
    	
    	if (!StringUtils.isBlank(path)) {
    		path = path.replace("\\", "\\\\");
    	}
    	return path;
    }

//TODO: split it to path processing and file/stream manipulation methods.    
    private InputStream getStreamByPath(String path) throws Exception {
    	// it's not necessary to perform the convertion of path cause it's already correctly formatted
    	// or allready broken
    	String streamPath = path;
        
    	if (StringUtils.contains(path, "ats:")) {
            String atsHome = getAtsHomePath();
            if (StringUtils.isBlank(atsHome))
                throw new Exception(
                        "\"ATS_HOME\" doesn't exist in environment variables. Please add it before ats launch!");

            streamPath = path.replaceFirst("ats:", "file:" + atsHome);
        }
        
        if (StringUtils.contains(streamPath, "file:")) {
            return new FileInputStream(new File(new URL(streamPath).getFile()));
        }

        if (StringUtils.contains(path, "url:")) {
            streamPath = path.replaceFirst("url:", "");
            throw new Exception("ATS doesn't support \"url:\" path type");
        }

        streamPath = path.replaceFirst("classpath:", "");
        
        InputStream stream = getClass().getClassLoader().getResourceAsStream(streamPath); 
        if ( stream == null ) {
        	throw new FileNotFoundException("Failed to open " + streamPath);
        }
        return stream; 
    }

    /**
     * Create Ats Configuration adapter
     * 
     * @param suiteParameterConfig String of properties separated by ";"
     * @throws java.io.IOException
     */
    public AtsConfigActor(String suiteParameterConfig) throws Exception {

        String p = suiteParameterConfig.replace(";", "\n").trim();
        Properties initialProp = new Properties();
        StringReader initialPropReader = new StringReader(p);
        initialProp.load(initialPropReader);

        if (initialProp.containsKey(DEFAULT_PROPERTIES_FILEPATH)) {
            String path = initialProp.getProperty(DEFAULT_PROPERTIES_FILEPATH);
            
            config = new Properties();
            config.load(getStreamByPath(path));

            Enumeration e = initialProp.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = initialProp.getProperty(key);
                config.setProperty(key, value);
            }
        } else {
            config = initialProp;
        }
    }

    public Properties getConfig() {
        return extendBySystemProperties(config);
    }

	private static Properties extendBySystemProperties(Properties rawConfig) {
		Properties extended = new Properties();
		extended.putAll(rawConfig);
		for (String name: extended.stringPropertyNames()) {
			if (System.getProperty(name) != null) {
				extended.setProperty(name, System.getProperty(name));
			}
		}
		return extended;
	}
}
