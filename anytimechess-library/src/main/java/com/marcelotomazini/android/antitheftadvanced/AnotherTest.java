package com.marcelotomazini.android.antitheftadvanced;

import java.io.InputStreamReader;
import java.net.URL;

import org.mortbay.util.UrlEncoded;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AnotherTest {

	public static void main(String[] args) throws Exception {
		URL trainPositions = new URL("https://www.googleapis.com/mapsengine/v1/zp4hms-VLd6s.kQUTfuDBTUHQ/features?version=published&maxResults=600&key=AIzaSyB5EZ_HkmH_tWpVkrjZWI5kQ9MRWmYCfyE");

        JsonParser parser = new JsonParser();
        JsonObject o = (JsonObject) parser.parse(new InputStreamReader(trainPositions.openStream()));

        JsonArray trains = o.getAsJsonArray("features");
        System.out.println(trains);
	}
}
