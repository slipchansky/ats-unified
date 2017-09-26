package com.genesyslab.gks.ats.config;

import java.util.Properties;

/**
 * User: IgorMy
 * Date: 6/23/14
 * <p/>
 * Class
 */
public enum GksAtsConfig{
	INSTANCE;
	
    public Properties envProperties;

    public void setUp(String environment) throws Exception {
        envProperties = new AtsConfigActor(environment).getConfig();
    }

	
}
