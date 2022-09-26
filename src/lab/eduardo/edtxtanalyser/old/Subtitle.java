package lab.eduardo.edtxtanalyser.old;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Subtitle {

    public static final Charset ENCODING = StandardCharsets.ISO_8859_1;

    private File file;
    private int lines = 0;
    private int words = 0;
    private int chars = 0;
    private int questions = 0;
    private SubtitleMap topWords;
    private Timer totalTime;

    public Subtitle(File file) {
        this.file = file;
        this.topWords = new SubtitleMap(50); // top fifty
        this.totalTime = new Timer(); // "00:00:00"
    }

    public void read() {
        try (BufferedReader reader = reader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                analyseLine(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader reader() throws IOException {
        return Files.newBufferedReader(Paths.get(file.getAbsolutePath()), ENCODING);
    }

    /**
     * Useful sites for regex:
     * <p>
     * 
     * @see <a href=
     *      "http://www.vogella.com/tutorials/JavaRegularExpressions/article.html"
     *      >http://www.vogella.com/tutorials/JavaRegularExpressions/article.
     *      html</a>
     * @see <a href=
     *      "https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.
     *      html">https://docs.oracle.com/javase/7/docs/api/java/util/regex/
     *      Pattern.html</a>
     * 
     * @param line
     */
    private void analyseLine(String line) {
        line = line.trim();
        if (isANumber(line) || line.length() == 0) {
            return;
        }
        // e.g. 00:09:53,636 --> 00:09:55,506
        if (line.matches("\\d{2}:\\d{2}:\\d{2},\\d{3} --> \\d{2}:\\d{2}:\\d{2},\\d{3}")) {

            String[] parts = line.split(" --> ");
            String start = parts[0];
            String finish = parts[1];
            Timer timerStart = new Timer(start);
            Timer timerFinish = new Timer(finish);
//        Timer period = timerFinish.subOther(timerStart);

//        totalTime.addOther(period);

            return;
        }

        chars += line.length();

        String regex = "[^\\w&&[^']]"; // A non-word character except for '
        String[] lineWords = line.split(regex);
        for (int i = 0; i < lineWords.length; i++) {
            String word = lineWords[i];
            if (word.length() == 0 || word.startsWith("[")) {
                break;
            }
            addWord(word);
            words++;
        }
        lines++;

    }

    private boolean isANumber(String line) {
        try {
            Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void addWord(String word) {
        if (!topWords.containsKey(word)) {
            topWords.put(word);
        } else {
            topWords.update(word);
        }
    }

    /**
     * @return the chars
     */
    public int getChars() {
        return chars;
    }

    /**
     * @return the questions
     */
    public int getQuestions() {
        return questions;
    }

    /**
     * @return the lines
     */
    public int getLines() {
        return lines;
    }

    /**
     * @return the words
     */
    public int getWords() {
        return words;
    }

    /**
     * @return the topWords
     */
    public SubtitleMap getTopWords() {
        return topWords;
    }

    /**
     * @return the totalTime
     */
    public Timer getTotalTime() {
        return totalTime;
    }

}
