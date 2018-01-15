package com.aigent.service;

import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class RestClient {

    public static class Response {
        public int responseCode;
        public StringBuffer response;

        public Response(int responseCode, StringBuffer response) {
            this.responseCode = responseCode;
            this.response = response;
        }
    }

    private static final String HOST = "https://api.aigent.co/assessments/nouns-and-verbs/";
    private static final String TEAM_NAME = "team3/";
    private static final String FILENAME_PLACEHOLDER = "{filename}";
    private static final String NOUNS_URL = HOST + "nouns/" + FILENAME_PLACEHOLDER + "/" + TEAM_NAME;
    private static final String VERBS_URL = HOST + "verbs/" + FILENAME_PLACEHOLDER + "/" + TEAM_NAME;

    public Response callVerbsUrl(String filename, String verbs[]) throws IOException {
        return callUrl(VERBS_URL.replace(FILENAME_PLACEHOLDER, filename), verbs);
    }

    public Response callNounsUrl(String filename, String nouns[]) throws IOException {
        return callUrl(NOUNS_URL.replace(FILENAME_PLACEHOLDER, filename), nouns);
    }

    private Response callUrl(final String urlString, final String values[]) throws IOException {
        final URL url = new URL(urlString);
        final HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        final DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes("{[" + mkString(values, ',') + "]}");
        final int responseCode = connection.getResponseCode();

        InputStream inputStream;
        if (responseCode != HttpURLConnection.HTTP_OK) {
            inputStream = connection.getErrorStream();
        } else {
            inputStream = connection.getInputStream();
        }

        final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuffer response = new StringBuffer();

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new Response(responseCode, response);
    }

    private static String mkString(final String stringArray[], char separator) {
        final StringBuffer buffer = new StringBuffer();

        for (final String aStringArray : stringArray) {
            buffer.append(aStringArray).append(separator);
        }

        return buffer.toString();
    }

}
