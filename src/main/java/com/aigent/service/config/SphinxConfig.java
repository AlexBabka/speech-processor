package com.aigent.service.config;

import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class SphinxConfig {
    @Bean
    public StreamSpeechRecognizer speechRecognizer() throws IOException {
        edu.cmu.sphinx.api.Configuration configuration = new edu.cmu.sphinx.api.Configuration();

        configuration.setSampleRate(8000);

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        return new StreamSpeechRecognizer(configuration);
    }
}
