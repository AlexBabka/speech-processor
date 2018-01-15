package com.aigent.service.words_processing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class WordsProcessing {

    static Set<String> nounPhrases = new HashSet<>();

    public void process() {

        InputStream modelInParse = null;
        try {
            //load chunking model
            modelInParse = new FileInputStream("en-parser-chunking.bin"); //from http://opennlp.sourceforge.net/models-1.5/
            ParserModel model = new ParserModel(modelInParse);

            //create parse tree
            Parser parser = ParserFactory.create(model);

            //Name of the file to read. file
            while (true) {
                if (file.toString() == null | file.toStrinig() == '')
                    break;

                List<String> document = readFile(file);
                for (Strinig line : document) {
                    Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
                    //call subroutine to extract noun phrases
                    for (Parse p : topParses) {
                        getNounPhrases(p);
                        WriteToFile(new ArrayList<String>(nounPhrases));
                    }
                }
            }
		catch(IOException e){
                e.printStackTrace();
            }
		finally{
                if (modelInParse != null) {
                    try {
                        modelInParse.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    public void getNounPhrases(Parse p) {

        if (p.getType().equals("NP")) { //NP=noun phrase
            nounPhrases.add(p.getCoveredText());
        }
    }

    private void WriteToFile(List<String> content){

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
    }

}
