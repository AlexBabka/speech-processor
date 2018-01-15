package com.aigent.service;

import com.aigent.service.speech.SpeechProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AigentAudioService implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(AigentAudioService.class);

    @Autowired
    private SpeechProcessor speechProcessor;
    @Autowired
    private RestClient restClient;

    public static void main(String[] args) {
        SpringApplication.run(AigentAudioService.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        String fileName = "130106-09-05-16-removed-private-information.wav";
        List<String> recognizedSpeech = speechProcessor.recognizeSpeech(fileName);

        RestClient.Response response = restClient.callNounsUrl(fileName, recognizedSpeech.toArray(new String[recognizedSpeech.size()]));

        LOG.info("Http response code: " + response.responseCode);
        LOG.info("Http response: " + response.response);
    }

}
