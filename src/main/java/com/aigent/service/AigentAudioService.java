package com.aigent.service;

import java.io.IOException;

public class AigentAudioService {

	public static void main(final String[] args) {

		final RestClient restClient = new RestClient();

		try {

			final String str[] = {"a", "b"};
			final RestClient.Response response = restClient.callVerbsUrl("test", str);

			System.out.println("ResponseCode: " + response.responseCode);
			System.out.println("ResponseContent: " + response.response);

		} catch (IOException exception) {
			System.out.println("Exception: " + exception.getMessage());
			exception.printStackTrace();
		}
	}
}
