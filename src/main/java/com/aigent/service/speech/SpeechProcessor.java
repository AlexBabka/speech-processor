package com.aigent.service.speech;

import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class SpeechProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(SpeechProcessor.class);

    @Autowired
    private StreamSpeechRecognizer recognizer;

    public List<String> recognizeSpeech(String fileName) throws IOException {
        List<String> recognizedWords = new ArrayList<>();

        try (InputStream stream = new ClassPathResource(fileName).getInputStream()) {
            recognizer.startRecognition(stream);
            SpeechResult result;
            while ((result = recognizer.getResult()) != null) {
                String expression = result.getHypothesis();
                LOG.info(String.format("Recognized expression: %s\n", expression));

                recognizedWords.add(expression);
            }
            recognizer.stopRecognition();
        }

        return recognizedWords;
    }
}
