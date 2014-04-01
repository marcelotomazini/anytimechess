package com.marcelotomazini.android.antitheftadvanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {

	public static String post(String url, Map<String, String> httpParameters) throws ClientProtocolException, IOException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(httpParameters.size());
		Set<String> httpParameterKeys = httpParameters.keySet();
		for (String httpParameterKey : httpParameterKeys) {
			nameValuePairs.add(new BasicNameValuePair(httpParameterKey, httpParameters.get(httpParameterKey)));
		}

		HttpPost method = new HttpPost(url);
		method.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = executeMethod(method);
		return getResponseAsString(response);
	}
	
	private static HttpResponse executeMethod(HttpRequestBase method) throws ClientProtocolException, IOException {
		HttpResponse response = null;
		HttpClient client = new DefaultHttpClient();
		response = client.execute(method);
		return response;
	}

	private static String getResponseAsString(HttpResponse response) throws IllegalStateException, IOException {
		String content = null;
		InputStream stream = null;
		try {
			if (response != null) {
				stream = response.getEntity().getContent();
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader buffer = new BufferedReader(reader);
				StringBuilder sb = new StringBuilder();
				String cur;
				while ((cur = buffer.readLine()) != null) {
					sb.append(cur + "\n");
				}
				content = sb.toString();
			}
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
		return content;
	}
}
