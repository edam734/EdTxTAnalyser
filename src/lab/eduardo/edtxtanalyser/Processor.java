package lab.eduardo.edtxtanalyser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Processor {

    private Path origin;
    private Path destiny;
    private Charset encoding;
    private WordsMap words;

    public Processor(String origin, String destiny, Charset encoding) {
        this.origin = Paths.get(origin); 
        this.destiny = Paths.get(destiny);
        this.encoding = encoding;
        words = new WordsMap();
    }

    public void execute() throws IOException {
        String line = "";
        try (BufferedReader reader = Files.newBufferedReader(origin, encoding)) {
            while((line = reader.readLine()) != null) { 
                String parts[] = line.toLowerCase().split("[^\\w&&[^']]");
                Stream.of(parts).forEach(w -> words.add(w));
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(destiny, encoding)) {
            words.getWords().entrySet().forEach(entry -> {
                try {
                    writer.append(entry.getKey() + " : " + entry.getValue());
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

}
