package com.cerner.sriram.automateprocess;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

public class GitClientConnection {

	Logger logger = Logger.getLogger("GitClientConnection");

	public void createConnection() throws NoSuchAlgorithmException{
		
		TrustManager[] trustAllCerts = new TrustManager[] {
			       new X509TrustManager() {
			          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			            return null;
			          }

			          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

			          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

			       }
			    };

			    SSLContext sc = SSLContext.getInstance("SSL");
			    try {
					sc.init(null, trustAllCerts, new java.security.SecureRandom());
				} catch (KeyManagementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			    // Create all-trusting host name verifier
			    HostnameVerifier allHostsValid = new HostnameVerifier() {
			        public boolean verify(String hostname, SSLSession session) {
			          return true;
			        }
			    };
			    // Install the all-trusting host verifier
			    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			    /*
			     * end of the fix
			     */
		
		
		
		ClientConfig configuration = new DefaultClientConfig();
		Client client = Client.create(configuration);
		WebResource service= client.resource(UriBuilder.fromUri("https://webmail.roadrunner.com/").build());
		try{
			service.accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
		}catch(ClientHandlerException e){
			logger.log(Level.SEVERE, "Exception due to SSL certificate missing");
		}
	}
	public static void main(String[] args) throws NoSuchAlgorithmException {
		GitClientConnection connect = new GitClientConnection();
		

		connect.createConnection();

	}

}
