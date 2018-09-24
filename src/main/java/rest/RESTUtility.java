package rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
	
	public static void main(String[] args) {
		try {
			String status = callREST("https://demo-machine:9443/rest/bpm/wle/v1/process/1597116?action=addDocument&name=thongtest2.pdf&hideInPortal=false&docType=url&docUrl=https%3A%2F%2Fdemo-machine%3A9443%2Fportal%2Fjsp%2FecmDocument%3Foperation%3Dajax_getDocumentContent%26snapshotId%3D2064.f31e1243-18f4-49d3-bfa5-ee453a081706%26ecmServerConfigurationName%3DEMBEDDED_ECM_SERVER%26documentId%3Didd_808E0A66-0000-CB18-9328-FB73A0E7CDB5&parts=all","POST","thongh","Pa55w0rd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String callREST(String restUrl, String restMethod, String username, String password) throws IOException {
		disableSslVerification();
		String resStatus = "";
		URL url = null;
		String method = null;
		try {
			// REST URL
			if (restUrl == "") {
				url = new URL("https://192.168.0.109:9443/rest/bpm/wle/v1/process/1597109?action=addDocument&name=thongtest2.pdf&hideInPortal=false&docType=url&docUrl=https%3A%2F%2Fdemo-machine%3A9443%2Fportal%2Fjsp%2FecmDocument%3Foperation%3Dajax_getDocumentContent%26snapshotId%3D2064.b1f4387c-47dc-47bf-9e40-028fba8b60a4%26ecmServerConfigurationName%3DEMBEDDED_ECM_SERVER%26documentId%3Didd_C03C0A66-0000-C71C-9B90-4859BBD2B0FF&parts=all");			
			} else {
				url = new URL(restUrl);
			}
			
			// Open connection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// Set method
			if (restMethod == "") {
				method = "POST";
			} else {
				method = restMethod;
			}
			
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
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
			
			// Params
			String input = "{\"action\":\"addDocument\",\"name\":\"thongtest.pdf\",\"hideInPortal\":false,\"docType\":\"url\",\"docUrl\":\"https%3A%2F%2Fdemo-machine%3A9443%2Fportal%2Fjsp%2FecmDocument%3Foperation%3Dajax_getDocumentContent%26snapshotId%3D2064.4fc66b48-afc0-4a2b-a254-120496b9d934%26ecmServerConfigurationName%3DEMBEDDED_ECM_SERVER%26documentId%3Didd_C03C0A66-0000-C71C-9B90-4859BBD2B0FF%26parts%3Dall\",\"pars\":\"all\"}";
			
			// Get result
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			
			conn.disconnect();
			resStatus = output;
			
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
