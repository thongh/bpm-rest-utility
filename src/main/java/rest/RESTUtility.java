package rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RESTUtility {
	
	public static String callREST(String host, String instanceId, 
			String docName, String docUrl,
			String username, String password) throws IOException {
		disableSslVerification();
		String resStatus = "";
		URL url = null;
		try {
			String restUrl = "";
			if (host == "") {
				host = "demo-machine";
			}
			if (instanceId == "") {
				instanceId = "1597117";
			}
			if (docName == "") {
				docName = "test";
			}
			if (docUrl == "") {
				docUrl = "https%3A%2F%2Fdemo-machine%3A9443%2Fportal%2Fjsp%2FecmDocument%3Foperation%3Dajax_getDocumentContent%26snapshotId%3D2064.f31e1243-18f4-49d3-bfa5-ee453a081706%26ecmServerConfigurationName%3DEMBEDDED_ECM_SERVER%26documentId%3Didd_808E0A66-0000-CB18-9328-FB73A0E7CDB5&parts=all";
			}
			restUrl = "https://" + host + ":9443/rest/bpm/wle/v1/process/" + instanceId + "?action=addDocument&name=" + docName + "&hideInPortal=false&docType=url&docUrl=" + docUrl;
				
			url = new URL(restUrl);
			
			// Open connection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// Set method		
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			// Request header
			conn.setRequestProperty("Accept", "application/json");
			
			// Auth
			if (username == "") {
				username = "thongh";
			}
			if (password == "") {
				password = "Pa55w0rd";
			}
			String encoded = Base64.getEncoder().encodeToString((username+":"+password).getBytes(StandardCharsets.UTF_8));  //Java 8
			conn.setRequestProperty("Authorization", "Basic "+encoded);
					
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			conn.disconnect();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}
		
		return resStatus;
	}
	
	private static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	        };

	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }
	}
	
	
}
