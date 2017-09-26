package com.genesyslab.ats.unified.actors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.log4j.lf5.util.StreamUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.genesyslab.ats.unified.environment.AtsEnvironment;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA_TYPE;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SimpleRestActor {
	private static final String HTTPS_PROTOCOL_URI_PREFIX = "https:";

	private static final String HTTP_PROTOCOL_URI_PREFIX = "http:";
	
	static Map<String, List<Cookie>> AUTH_COOKIES = new HashMap();

	public enum Method {
		HEAD, GET, POST, PUT, PATCH, DELETE
	}

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper() {
		{
			configure(SerializationFeature.INDENT_OUTPUT, true);
		}
	};

	@Getter
	private String home;

	@Getter
	private String url = "";

	@Getter
	private Method method = Method.GET;

	private Map<String, String> parameters = new LinkedHashMap<String, String>();
	private Map<String, String> headers = new LinkedHashMap<String, String>();


	// private Object responsePayload = null;

	@Getter
	private Response response;

	private String body;
	private File fileToUpload = null;
	

	private boolean useHttps;
	private String keyStorePath = null;
	private String keyStorePassword = null;

	private String formAuthUser;
	private String formAuthPassword;

	private boolean useBasicAuth;
	private String authorizationToken;


	private final static TrustStrategy trustAll = new TrustStrategy() {
		@Override
		public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			return true;
		}
	};

	private final static HostnameVerifier trustAllHosts = new HostnameVerifier() {
		@Override
		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}
	};

	public SimpleRestActor(String home) {
		if (home != null)
			home(home);
	}

	public SimpleRestActor param(String name, Object value) {
		parameters.put(name, String.valueOf(value));
		return this;
	}

	public SimpleRestActor body(Object body) {
		if (body == null || body instanceof String) {
			this.body = (String) body;
			return this;
		}
		try {
			this.body = OBJECT_MAPPER.writeValueAsString(body);
		} catch (JsonProcessingException e) {
			this.body = null;
			e.printStackTrace();
		}
		return this;
	}
	
	public SimpleRestActor method(String method) {
		this.method (Method.valueOf(method));
		return this;
	}
	

	public SimpleRestActor method(Method method) {
		this.method = method;
		return this;
	}

	public SimpleRestActor home(String home) {
		if (home.endsWith("/"))
			home = home.substring(0, home.length() - 1);
		this.home = home;
		return this;
	}

	public SimpleRestActor url(String url) {
		if (url.startsWith("/"))
			url = url.substring(1);
		this.url = url;
		return this;
	}

	public SimpleRestActor param(String name, String value) {
		parameters.put(name, value);
		return this;
	}

	public SimpleRestActor head(String name, String value) {
		headers.put(name, value);
		return this;
	}

	private String prepareEndpoint(Map<String, String> params) {
		String endpoint = home + '/' + url;

		if (endpoint.indexOf('$') >= 0) {
			// because i have to remove $ substituted parameters from all
			// parameters list
			String[] a = endpoint.split("/");
			StringBuilder recompiled = new StringBuilder();
			for (int i = 0; i < a.length; i++) {
				String section = a[i];
				if (section.startsWith("$")) {
					String paramName = section.substring(1);
					section = params.get(paramName);
					if (section != null) {
						params.remove(paramName);
					} else {
						// restore section for trying replace it using
						// translateInContext
						section = '$' + paramName;
					}

				}
				recompiled.append(section).append('/');
			}
			endpoint = recompiled.toString();
		}

		//
		endpoint = DataActor.doTranslate(endpoint, params);
		endpoint = applyParameters(endpoint, params);

		if (useHttps && endpoint.toUpperCase().startsWith(HTTP_PROTOCOL_URI_PREFIX)) {
			endpoint = HTTPS_PROTOCOL_URI_PREFIX + endpoint.substring(HTTP_PROTOCOL_URI_PREFIX.length());
		}
		return endpoint;
	}

	// duplicates code in JQLScenario
	protected String applyParameters(String endpoint, Map<String, String> params) throws Error {
		int start = endpoint.indexOf('{');
		while (start >= 0) {
			int end = endpoint.indexOf('}', start);
			if (end < 0) {
				throw new Error("Malformed url");
			}
			String paramName = endpoint.substring(start + 1, end);
			String paramValue = actualValueOf(paramName, params);

			if (paramValue == null)
				paramValue = "null";
			endpoint = new StringBuilder().append(endpoint.substring(0, start)).append(paramValue)
					.append(endpoint.substring(end + 1)).toString();
			params.remove(paramName);
			start = endpoint.indexOf('{');
		}
		return endpoint;
	}

	// duplicates code in JQLScenario
	private String actualValueOf(String name, Map<String, String> params) {
		String value = params.get(name);
		if (value != null) {
			if (value.startsWith("$")) {
					value = String.valueOf(DataActor.eval(value));
			}
			return value;
		}
		if (!name.startsWith("$"))
			name = "$" + name;
		value = String.valueOf(DataActor.eval(name));
		return value;
	}

	public Map<String, Object>  invoke() {
		Map<String, Object> responseContainer = new LinkedHashMap<String, Object>();

		try {
			sendRest();
			responseContainer.put("status", response.getStatus());
			MultivaluedMap<String, String> responseHeaders = response.getStringHeaders();
			responseContainer.put("headers", responseHeaders);
			
			if (response.hasEntity()) {
				String content = response.readEntity(String.class);
				responseContainer.put ("content", content);
				Object entity = OBJECT_MAPPER.readValue(content, Object.class);
				responseContainer.put("entity", entity);
			}


		} catch (Throwable e) {

			responseContainer.put("status", -1);
			responseContainer.put("error", e.getMessage());

		}
		return responseContainer;
	}

	protected void sendRest() {
		URI targetUri = buildUri();
		Client client = newClient();
		String sMethod = method.toString();
		
		WebTarget target = client.target(targetUri);
		Builder request = target.request(MediaType.APPLICATION_JSON_TYPE + ";charset=utf-8");

		for (String h : headers.keySet()) {
			request.header(h, actualValueOf(h, headers));
		}
		request = preprocess(request);
		
		
		if (fileToUpload != null) {
			sendAttachment (request, sMethod);
			return;
		}
		
		
		if (body==null) {
			response = request.method(sMethod);
		} else {
			response = request.method(sMethod, Entity.entity(body, MediaType.APPLICATION_JSON + ";charset=utf-8"));
		}
		

	}

	private void sendAttachment(Builder request, String sMethod) {
		FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file",
                fileToUpload,
                MediaType.APPLICATION_OCTET_STREAM_TYPE);
		
        fileDataBodyPart.setContentDisposition(
                FormDataContentDisposition.name("file")
                        .fileName(fileToUpload.getName()).build());
        
        final MultiPart multiPart = new FormDataMultiPart()
                .bodyPart(fileDataBodyPart);
        
        multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
		response = request.method(sMethod, Entity.entity(multiPart, MULTIPART_FORM_DATA_TYPE));
		return;
		
		
	}

	public URI buildUri() {
		Map<String, String> finalParameters = new HashMap<String, String>(parameters);
		String endpoint = prepareEndpoint(finalParameters);
		UriBuilder uriBuilder = UriBuilder.fromUri(endpoint);

		Set<Entry<String, String>> entries = finalParameters.entrySet();
		for (String p : finalParameters.keySet()) {
			uriBuilder = uriBuilder.queryParam(p, actualValueOf(p, finalParameters));
		}
		URI targetUri = uriBuilder.build();
		return targetUri;
	}

	protected Client newClient() {
		if (this.useHttps)
			return newHttpsClient();
		else
			return newHttpClient();
	}

	protected Client newHttpClient() {
		return ClientBuilder.newClient(clientConfig()).property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
	}

	protected Client newHttpsClient() {
		if (keyStorePath == null) {
			throw new Error("KeyStore path must be defined");
		}

		if (keyStorePassword == null) {
			throw new Error("KeyStore password must be defined");
		}

		try {
			SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(new File(keyStorePath), keyStorePassword.toCharArray(), trustAll).build();
			return ClientBuilder.newBuilder().sslContext(sslContext).hostnameVerifier(trustAllHosts)
					.newClient(clientConfig());
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | CertificateException
				| IOException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	protected ClientConfig clientConfig() {
		ClientConfig clientConfig = new ClientConfig();
		if (fileToUpload != null) {
			clientConfig.register(MultiPartFeature.class);
		}
		clientConfig.property(ClientProperties.FOLLOW_REDIRECTS, false);
		return clientConfig;
	};

	public int getResponseStatus() {
		return response.getStatus();
	}

	public boolean isResponseEntity() {
		return response.hasEntity();
	}

	public StatusType getStatusInfo() {
		return response.getStatusInfo();
	}

	/**
	 * Any late request preprocess
	 * 
	 * @param request
	 * @return
	 */
	private Builder preprocess(Builder request) {
		if (authorizationToken != null) {
			request.header("Authorization", "Basic " + authorizationToken);
		}

		if (formAuthUser != null && formAuthPassword != null) {
			formAuth(request);
		}

		return request;
	}


	private void formAuth(Builder request) {
		String formAuthUser = (String)DataActor.evalOrGetOriginal(this.formAuthUser);
		String formAuthPassword = (String)DataActor.evalOrGetOriginal(this.formAuthPassword);
		
		String key = home+"/"+formAuthUser+"/"+formAuthPassword;
		List<Cookie> cookies = AUTH_COOKIES.get(key);
		
		if (cookies == null) {
			Client client = newClient();
			WebTarget baseTarget = client.target(home);
			Form form = new Form();
			form.param("j_username", formAuthUser);
			form.param("j_password", formAuthPassword);

			Response response = baseTarget.path("/j_spring_security_check").request("application/x-www-form-urlencoded")
					.header("Content-Type", "application/x-www-form-urlencoded").post(Entity.form(form));
			
			Map<String, NewCookie> cr = response.getCookies();
			
			cookies = new ArrayList<> ();
			for (NewCookie cookie : cr.values()) {
				cookies.add(cookie.toCookie());
			}
			AUTH_COOKIES.put(key, cookies);
		}
		
		for (Cookie cookie : cookies)
			request.cookie(cookie);

	}


	public void formAuth(String userName, String password) {
		this.formAuthUser = userName;
		this.formAuthPassword = password;
	}

	public SimpleRestActor usingSSL(String keysorePath, String keystorePassword) {
		this.keyStorePath = keysorePath;
		this.keyStorePassword = keystorePassword;
		this.useHttps = true;
		return this;
	}

	public SimpleRestActor usingBasicAuth(String basicUserName, String basicUserPassword) {
		this.useBasicAuth = true;

		StringBuilder sb = new StringBuilder();
		sb.append(basicUserName).append(':');
		if (basicUserPassword != null)
			sb.append(basicUserPassword);
		authorizationToken = org.glassfish.jersey.internal.util.Base64.encodeAsString(sb.toString());
		return this;
	}


	@Override
	public String toString() {
		return method + " " + url + ", parameters: " + parameters;
	}

	public void dump(PrintStream out)  {
		try {
		out.println(toString());
		out.println("effective URL: " + buildUri());
		out.println("SSL: " + keyStorePath + "/" + keyStorePassword);
		out.println("BASICAUTH: " + useBasicAuth + "/" + authorizationToken);
		out.println("FORMAUTH: " + formAuthUser + "/" + formAuthPassword);

		out.println("\nparameters:");
		out.println(OBJECT_MAPPER.writeValueAsString(parameters));

		out.println("\nheaders:");
		out.println(OBJECT_MAPPER.writeValueAsString(headers));

		out.println("\nbody:");
		String translateInContext = AtsEnvironment.translateInContext(body, parameters);
		out.println(translateInContext);
		out.println();
		} catch (Throwable e) {
			
		}
	}

	public void attachment(Object oAttachment) {
		if (! (oAttachment instanceof String) ) {
			throw new Error ("Only attaching file bu its name is supported now");
		}
		String attachmentPath = (String)oAttachment;
		
		URL aurl = getClass().getClassLoader().getResource (attachmentPath);
		if (aurl != null) {
			fileToUpload = new File (aurl.getFile());
			return;
		}
		
		fileToUpload = new File (attachmentPath);
		if (!fileToUpload.exists()) {
			throw new Error ("Can't read neither resource nor file of '"+attachmentPath+"' as attachment");
		}
		
		if (fileToUpload.isDirectory()) {
			throw new Error ("Can't upload directory of '"+attachmentPath+"' as attachment");
		}
		
	}

}
