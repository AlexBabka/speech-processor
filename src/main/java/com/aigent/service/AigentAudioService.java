package com.aigent.service;

import java.io.IOException;

public class AigentAudioService {

	public static void main(final String[] args) {

		final RestClient restClient = new RestClient();

		try {

			final String str[] = {"a", "b"};
			final RestClient.Response response = restClient.callVerbsUrl("test", str);

			System.out.println(response.responseCode + " : " + response.response);

		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}
}
