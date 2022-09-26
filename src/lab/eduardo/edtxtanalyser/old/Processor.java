package lab.eduardo.edtxtanalyser.old;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Processor {

    private File destinationFile;
    private Subtitle sub;
    private Charset encoding;

    /**
     * @param originFile
     * @param destinationFile
     */
    public Processor(File originFile, File destinationFile, Charset encoding) {
    
    sub = new Subtitle(originFile);
    this.destinationFile = destinationFile;
    this.encoding = encoding;

    }

    public void execute() {
    sub.read();

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(destinationFile.getPath()), encoding)) {
        writer.write("Number of lines = " + sub.getLines());
        writer.newLine();
        writer.write("Number of words = " + sub.getWords());
        writer.newLine();
        writer.write("Number of characters = " + sub.getChars());
        writer.newLine();
        writer.write("Number of questions = " + sub.getQuestions());
        writer.newLine();
        writer.write("Total speech time = " + sub.getTotalTime());
        writer.newLine();
        writer.write(sub.getTopWords().toString());
        writer.newLine();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

}

