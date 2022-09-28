package lab.eduardo.edtxtanalyser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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
                String parts[] = line.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
                Stream.of(parts).forEach(w -> words.add(w));
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(destiny, encoding)) {
            words.getWords().entrySet().forEach(entry -> {
                try {
                    writer.append(WordsMap.entryToString(entry));
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
    
    private static class WordsMap {

        private Map<String, Integer> words = new HashMap<>();

        public Map<String, Integer> getWords() {
            return words;
        }

        public Integer add(String word) {
            int ammount = 1;
            if (words.containsKey(word)) {
                ammount = words.get(word) + 1;
            }

            return words.put(word, ammount);
        }
        
        public static String entryToString(final java.util.Map.Entry<String, Integer> entry) {
            return new StringBuilder(entry.getKey()).append(" : ").append(entry.getValue()).toString();
        }

    }

}
