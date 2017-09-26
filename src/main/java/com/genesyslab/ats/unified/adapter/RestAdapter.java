package com.genesyslab.ats.unified.adapter;

import com.genesys.ats.basics.AtsClause;
import com.genesyslab.ats.unified.scenario.RestAction;
import com.genesyslab.ats.unified.scenario.RestConfigureAction;

import cucumber.api.java.en.And;

///**
// * Created by SLypchan on 02.06.2017.
// */
public class RestAdapter {
    @And("^using REST$")
    public void usingREST() throws Throwable {
        new RestAction().touch();
    }

    @And("^using REST \"([^\"]*)\"$")
    public void usingREST(String label) throws Throwable {
        new RestAction(label);
    }
    

    @And("^home = \"([^\"]*)\"$")
    public void home(String home) throws Throwable {
        //((CallRestScenario)Clause.activeClause).home(home);
    	new RestConfigureAction.RestHomeScenario(home).touch ();
    }
    
    @And("^home = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
    public void home1(String home) throws Throwable {
        //((CallRestScenario)Clause.activeClause).home(home);
    	new RestConfigureAction.RestHomeScenario(home).touch ();
    }
    
    

    @And("^url \"([^\"]*)\"$")
    public void url(String url) throws Throwable {
        //((CallRestScenario)Clause.activeClause).url(url);
    	new RestConfigureAction.RestURLScenario(url).touch ();
    }
    
    @And("^url = \"([^\"]*)\"$")
    public void urlEq(String url) throws Throwable {
    	//url (url);
    	new RestConfigureAction.RestURLScenario(url).touch ();
    }
    
    @And("^attachment = \"([^\"]*)\"$")
    public void attachment(String attachment) throws Throwable {
    	new RestConfigureAction.RestAttachmentScenario(attachment).touch ();
    }
    
    @And("^attachment = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
    public void attachmentByRef(String attachment) throws Throwable {
    	new RestConfigureAction.RestAttachmentScenario(attachment).touch ();
    }
    

    @And("^url = (\\$[\\w|.]*)$")
    public void url1(String url) throws Throwable {
        url(url);
    }

    @And("^method \"([^\"]*)\"$")
    public void method(final String method) throws Throwable {
    	//((CallRestScenario)Clause.activeClause).method(method);
    	new RestConfigureAction.RestMethodScenario(method).touch ();

    }
    
    @And("^method (\\w*)$")
    public void method1(final String method) throws Throwable {
    	method (method);
    }

    
    @And("^set param (\\w*) = \"([^\"]*)\"$")
    public void setParameterTo(final String name, final String value) throws Throwable {
    	//((CallRestScenario)Clause.activeClause).param(name, value); 
    	new RestConfigureAction.RestParamScenario(name, value).touch ();
    }
    
    
//    @And("^(parameter|param) (\"([^\"]*)\"|(\\w*)) (to|=) (\"([^\"]*)\"|(\\$[\\w]*))$")
//    public void setParameterToNoSet(final String name, final String value) throws Throwable {
//    	setParameterTo(name, value);
//    }
    
    @And("^param (\\w*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
    public void setParameterToNoSet1(final String name, final String value) throws Throwable {
    	setParameterTo(name, value);
    }
    
    @And("^param (\\w*)$")
    public void setParameterByName(final String name) throws Throwable {
    	setParameterTo(name, '$'+name);
    }
    
    
    @And("^param (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)]*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
    public void setParameterByReferenceToReference (final String name, final String value) throws Throwable {
    	setParameterTo(name, value);
    }
    

    @And("^param (\\w*) = \"([^\"]*)\"$")
    public void setParameterToNoSet2(final String name, final String value) throws Throwable {
    	setParameterTo(name, value);
    }
    
    @And("^param \"(.*)\" = \"([^\"]*)\"$")
    public void setParameterToNoSet3(final String name, final String value) throws Throwable {
    	setParameterTo(name, value);
    }
    
    

    @And("^set header (\\w*) = \"([^\"]*)\"$")
    public void setHeaderTo(final String name, final String value) throws Throwable {
    	//((CallRestScenario)Clause.activeClause).head(name, value);
    	new RestConfigureAction.RestHeadScenario(name, value).touch ();
    }
    
    @And("^header (\\w*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
    public void setHeaderToNoSet(final String name, final String value) throws Throwable {
    	setHeaderTo(name, value);
    }
    
    @And("^header (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)]*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
    public void setHeaderByRefToValueByRef(final String name, final String value) throws Throwable {
    	setHeaderTo(name, value);
    }
    

    @And("^header (\\w*) = \"([^\"]*)\"$")
    public void setHeaderToString(final String name, final String value) throws Throwable {
    	setHeaderTo(name, value);
    }

    @And("^body:$")
    public void body(final String value) throws Throwable {
    	//((CallRestScenario)Clause.activeClause).body(value);
    	new RestConfigureAction.RestBodyScenario(value).touch ();

    }
    
    @And("^body = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
    public void bodyUsingVariable(final String bodyvar) throws Throwable {
    	new RestConfigureAction.RestBodyScenario(bodyvar).touch ();
    }
    
    @And("^body = \"([^\"]*)\"$")
    public void bodyUsingStr(final String bodyvar) throws Throwable {
    	bodyUsingVariable(bodyvar); 
    }

    @And("^send$")
    public void send() throws Throwable {
        AtsClause.close();
    }
    
    @And("^SSL keystore \"([^\"]*)\" password \"([^\"]*)\"$")
    public void sslKeyStoreAndPassword(final String pathToKeystore, final String keystorePassword) throws Throwable {
    	//((CallRestScenario)Clause.activeClause).usingSSL(pathToKeystore, keystorePassword);
    	new RestConfigureAction.RestSSLScenario(pathToKeystore, keystorePassword).touch ();
    	
    }
    
    @And("^BASICAUTH username \"([^\"]*)\" password \"([^\"]*)\"$")
    public void basicAuthUserNameAndPassword(final String basicUserName, final String basicUserPassword) throws Throwable {
    	//((CallRestScenario)Clause.activeClause).usingBasicAuth(basicUserName, basicUserPassword);
    	new RestConfigureAction.RestBasicScenario(basicUserName, basicUserPassword).touch ();
    	
    }
        
    @And("^FORMAUTH username \"([^\"]*)\" password \"([^\"]*)\"$")
    public void formAuthUserNameAndPassword(final String userName, final String password) throws Throwable {
    	//((CallRestScenario)Clause.activeClause).formAuth (userName, password);
    	new RestConfigureAction.RestFormAuthScenario(userName, password).touch ();
    }
    
    @And("^FORMAUTH username (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) password (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
    public void formAuthUserNameAndPassword1(final String userName, final String password) throws Throwable {
    	formAuthUserNameAndPassword (userName, password);
    }
}
