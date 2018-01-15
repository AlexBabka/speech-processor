package com.aigent.service.words_processing;

import java.io.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class WordsProcessing {

    public static class Response {
        public List<String> nounList = new ArrayList<>();
        public List<String> verbList = new ArrayList<>();
    }

    public Response process(final List<String> chunkOfText) {
        final Response response = new Response();

        for (final String item : chunkOfText) {
            processString(item, response);
        }

        return response;
    }

    private void processString(final String string, final Response response) {
        try {
            //load chunking model
            final InputStream stringInputStream = new ByteArrayInputStream(string.getBytes());
            ParserModel model = new ParserModel(stringInputStream);

            //create parse tree
            Parser parser = ParserFactory.create(model);

            //Name of the file to read. file
            final Parse topParses[] = ParserTool.parseLine(string, parser, 1);
            //call subroutine to extract noun phrases
            for (final Parse p : topParses) {
                response.nounList.add(getNounPhrases(p));
                response.verbList.add(getVerbPhrases(p));
                //     WriteToFile(new ArrayList<String>(nounPhrases));
                //   WriteToFile(new ArrayList<String>(verbPhrases));
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private String getNounPhrases(final Parse p) {
        if (p.getType().equals("NN") || p.getType().equals("NNS") ||  p.getType().equals("NNP") || p.getType().equals("NNPS")) {
            return p.getCoveredText();
        } else {
            return null;
        }
    }

    private String getVerbPhrases(final Parse p) {
        if (p.getType().equals("JJ") || p.getType().equals("JJR") || p.getType().equals("JJS")) {
            return p.getCoveredText();
        } else {
            return null;
        }
    }

    /*private void WriteToFile(List<String> content){

        String FILENAME = "E:\\test\\filename.txt";

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);
            bw.write(content.toString());

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    private List<String> readFile(String filename)
    {
        List<String> records = new ArrayList<String>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.add(line);
            }
            reader.close();
            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }*/

}
