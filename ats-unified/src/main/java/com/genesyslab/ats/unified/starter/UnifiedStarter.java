package com.genesyslab.ats.unified.starter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.genesys.ats.basics.AtsClause;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.FeatureResultListener;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.testng.TestNgReporter;
import cucumber.runtime.Backend;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.CucumberException;
import cucumber.runtime.Reflections;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.StopWatch;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.CucumberTagStatement;
import gherkin.formatter.Reporter;


public class UnifiedStarter  {
	private ClassLoader classLoader;
	private MultiLoader resourceLoader;
	private ArrayList<Object> filters;
	

	public static UnifiedStarter INSTANCE;

	private boolean fromClassPath;
	private String basePath = "";
	private boolean doNotRunFeaturesDirectly = false;

	public UnifiedStarter() {
		super();
		INSTANCE = this;
		
		// Here is a trick for avoiding execution of test twice.
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (int i = stackTrace.length-1; i>=0; i--) {
			String hop = stackTrace[i].toString();
			if (hop.startsWith("org.apache.maven")) {
				doNotRunFeaturesDirectly = true;
				break;
			} else if (hop.startsWith("org.testng.")){
				break;
			}
			
		}
		
	}

	protected UnifiedStarter fromClassPath() {
		this.fromClassPath = true;
		return this;
	}

	protected UnifiedStarter fromFileSystem() {
		this.fromClassPath = false;
		return this;
	}

	public UnifiedStarter base(String path) {
		if (path != null) {
			if (path.endsWith("\\") || path.endsWith("/"))
				path = path.substring(0, path.length() - 1);
		}
		this.basePath = path;
		return this;
	}

	@BeforeSuite
	public void setUp() throws Exception {
		
		if (basePath==null || basePath.length()==0) {
			CucumberOptions options = getClass ().getAnnotation(CucumberOptions.class);
			if (options != null) {
				String[] features = options.features();
				if (features != null && features.length > 0) {
					String main = features[0];
					fromClassPath = true;
					if ( main.startsWith("src/test/resources/") ) basePath = main.substring("src/test/resources/".length());
					else
					if ( main.startsWith("src/main/resources/") ) basePath = main.substring("src/main/resources/".length());
					else {
						basePath = main;
						fromClassPath = false;
					}
				}
			}
		}
		
		classLoader = this.getClass().getClassLoader();
		resourceLoader = new MultiLoader(classLoader);
		filters = new ArrayList<Object>(Arrays.asList("~@excluded", "~@required"));
		String log4jConfPath = "log4j.properties";
		URL log4jConfUrl = getClass().getClassLoader().getResource("log4j.properties");
        PropertyConfigurator.configure(log4jConfUrl);
        setupMore ();
	}

	
	/**
	 *  Override this method for more initialization, for instance for initialize genesys environment using protected setUpGenenesysEnvironment() 
	 */
	protected void setupMore () throws Exception{
	}


	protected String getEnvironment() {
		return "default.properties.path = " + getEnvironmentPropertiesPath();
	}

	protected String getEnvironmentPropertiesPath() {
		return "config/environment.properties";
	}

	@AfterSuite
	public void tearDown() {
	}

	public void runFeatures(String featuresPath) {
		if (this.doNotRunFeaturesDirectly) return;
		AtsClause.reset();
		List<CucumberFeature> features = loadFeatures(featuresPath);

		if (features.size() == 0) {
			Assert.fail("There is no features at '" + featuresPath + "'");
		}
		for (CucumberFeature f : features) {
			runFeature(f, true);
			//testNGCucumberRunner.runCucumber(f);
		}
	}

	protected List<CucumberFeature> loadFeatures(String featuresPath) {
		if (featuresPath.startsWith("\\") || featuresPath.startsWith("/"))
			featuresPath = featuresPath.substring(1);
		if (basePath != null) {
			if (basePath.endsWith("/") || basePath.endsWith("\\")) {
				basePath = basePath.substring(0, basePath.length() - 1);
			}
			if (basePath.length() > 0)
				featuresPath = basePath + '/' + featuresPath;
		}
		
		try {
		    // resolving of relative path 
    		URL url = getClass().getClassLoader ().getResource(featuresPath);
    		if (url != null) {
    		    String file = url.getFile();
    		    String a [] = file.split("target/classes/");
    		    if (a.length == 2) {
                    featuresPath = a[1];
    		    }
    		}
		} catch (Throwable e) {
		    //
		}
		
		if (fromClassPath)
			featuresPath = "classpath:" + featuresPath;

		List<CucumberFeature> features = cucumber.runtime.model.CucumberFeature.load(resourceLoader,
				Arrays.asList(featuresPath), filters, System.out);
		return features;
	}

	public void runFeaturesSilently(String featurePath) {
		List<CucumberFeature> features = loadFeatures(featurePath);
		boolean output = AtsLogConsole.getOutput ();
		for (CucumberFeature cucumberFeature : features) {
			runFeature(cucumberFeature, false);
		}
		AtsLogConsole.enableOutput (output);
	}
	
	private void executeIt (CucumberFeature cucumberFeature, boolean output) {
		AtsLogConsole.enableOutput (output);
		
		ClassLoader classLoader = getClass().getClassLoader();
		MultiLoader resourceLoader = new MultiLoader(classLoader);
		RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(this.getClass());
		RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();
		Appendable out = new StringBuilder();
		final Reporter reporter = new TestNgReporter(out);
		
		
		
		
		ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
		Reflections reflections = new Reflections(classFinder);
		Collection<? extends Backend> backends = reflections.instantiateSubclasses(Backend.class, "cucumber.runtime",
				new Class[] { ResourceLoader.class }, new Object[] { resourceLoader });

		StopWatch stopWatch = new StopWatch() {
			private final ThreadLocal<Long> start = new ThreadLocal<Long>();

			@Override
			public void start() {
				start.set(System.nanoTime());
			}

			@Override
			public long stop() {
				Long duration = System.nanoTime() - start.get();
				start.set(null);
				return duration;
			}
		};
		
		SilentFormatter formatter = new SilentFormatter();//runtimeOptions.formatter(classLoader);
		FeatureResultListener resultListener = null; //new FeatureResultListener(formatter, runtimeOptions.isStrict());
		Runtime runtime = new Runtime(resourceLoader, classLoader, backends, runtimeOptions, stopWatch, null);
		
		formatter.uri(cucumberFeature.getPath());
		formatter.feature(cucumberFeature.getGherkinFeature());
		
		AtsLogConsole.log("\n"+cucumberFeature.getPath());
		
		for (CucumberTagStatement cucumberTagStatement : cucumberFeature.getFeatureElements()) {
			AtsLogConsole.enableOutput (output);
			String visualName = cucumberTagStatement.getVisualName();
			AtsLogConsole.log("\n  "+visualName+"\n");
			CucumberContext.scenario(visualName);
			cucumberTagStatement.run(formatter, reporter, runtime);
			AtsClause.closeAll();
		}
		
		AtsLogConsole.enableOutput (output);
		if (output) {
			formatter.eof();
		}
		

		List<Throwable> errors = runtime.getErrors();
		if (errors.size() > 0) {
			throw new CucumberException(errors.get(0));
		}

	}

	private void runFeature(CucumberFeature cucumberFeature, boolean output) {
		try {
			CucumberContext.start (cucumberFeature.getPath());
			executeIt (cucumberFeature, output);
		} catch (Throwable e) {
			throw (e);
		} finally {
			CucumberContext.end();
		}

	}
	
	
    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
    	AtsClause.reset();
    	if (!this.doNotRunFeaturesDirectly) return;
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }
    
    

    /**
     * @return returns two dimensional array of {@link CucumberFeatureWrapper} objects.
     */
    @DataProvider
    public Object[][] features() {
    	if (!doNotRunFeaturesDirectly) return new Object[0][0];
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }
    
    
	
	

}
