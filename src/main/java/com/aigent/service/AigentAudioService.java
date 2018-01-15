package com.aigent.service;

import com.aigent.service.speech.SpeechProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AigentAudioService implements CommandLineRunner {
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

        restClient.callNounsUrl(fileName, (String[]) recognizedSpeech.toArray());
    }

}
