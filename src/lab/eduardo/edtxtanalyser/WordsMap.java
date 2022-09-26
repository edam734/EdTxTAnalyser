package lab.eduardo.edtxtanalyser;

import java.util.HashMap;
import java.util.Map;

public class WordsMap {

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

}
