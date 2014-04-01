package com.marcelotomazini.android.antitheftadvanced;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonObject;

public class CreateTable {

	private static final String YOUR_MAP_ID = "zp4hms-VLd6s.kQUTfuDBTUHQ";
	private static final String YOUR_CLIENT_SECRET = "eXP3gMKpUyxamk8xK1CHojer";
	private static final String YOUR_CLIENT_ID = "930738437301-8fbpatffv91i8dn24kf39cmqp9fc5tbj.apps.googleusercontent.com";

	/**
	 * Be sure to specify the name of your application. If the application name
	 * is {@code null} or blank, the application will log a warning. Suggested
	 * format is "MyCompany-ProductName/1.0".
	 */
	private static final String APPLICATION_NAME = "Google-MapsEngineApiSample/1.0";

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	/** Authorizes the installed application to access user's protected data. */
	private static Credential authorize() throws Exception {
		GoogleClientSecrets.Details installedDetails = new GoogleClientSecrets.Details();
		installedDetails.setClientId(YOUR_CLIENT_ID);
		installedDetails.setClientSecret(YOUR_CLIENT_SECRET);

		GoogleClientSecrets clientSecrets = new GoogleClientSecrets();
		clientSecrets.setInstalled(installedDetails);

		// Set up a location to store retrieved credentials. This avoids having
		// to ask for authorization
		// every time the application is run
		FileCredentialStore credentialStore = new FileCredentialStore(new File(System.getProperty("user.home"), ".credentials/mapsengine.json"), JSON_FACTORY);

		// Set up the authorization flow.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Collections.singleton("https://www.googleapis.com/auth/mapsengine")).setCredentialStore(credentialStore).build();

		// Authorize this application.
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}

	public static void main(String[] args) {
		try {
			// Authorize this application to access the user's data.
			Credential credential = authorize();

			
			// Make a request to list all maps in a particular project.
			URL url = new URL("https://www.googleapis.com/mapsengine/v1/tables");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Authorization", "Bearer " + credential.getAccessToken());
			connection.setRequestProperty("Content-Type", "application/json");
			
			connection.connect();
			
			JsonObject json = new JsonObject();
			json.addProperty("projectId", "zp4hms-VLd6s");
			json.addProperty("name", "anti-theft-advanced");
			json.addProperty("draftAccessList", "Map Editors");
			
			JsonObject jsonColumns = new JsonObject();
			jsonColumns.addProperty("name", "geometry");
			jsonColumns.addProperty("type", "points");
			
			JsonObject jsonSchema = new JsonObject();
			jsonSchema.add("columns", jsonColumns);
			
			json.add("schema", jsonSchema);


			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(json.toString().getBytes());
			outputStream.close();


			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

}
