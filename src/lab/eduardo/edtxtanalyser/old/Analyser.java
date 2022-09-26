package lab.eduardo.edtxtanalyser.old;

import java.io.File;

//import javax.annotation.processing.Processor;

public class Analyser {

    public static final String ORIGIN_FOLDER = "C:\\Users\\Eduardo\\Dropbox\\Mesa de Trabalho\\7Art\\Subtitles";
    public static final String DESTINATION_FOLDER = "C:\\Users\\Eduardo\\Dropbox\\Mesa de Trabalho\\7Art\\Carrer\\Análises";

    public static final String SRT = ".srt";
    public static final String TXT = ".txt";

    public static void main(String[] args) {
        File originFolder = new File(ORIGIN_FOLDER);
        File[] listOfFiles = originFolder.listFiles();
        for (File file : listOfFiles) {
            String filename = file.getName();
            if (!filename.endsWith(SRT)) {
                continue;
            }
            File originFile = new File(ORIGIN_FOLDER + File.separator + filename);
            File destinationFile = new File(DESTINATION_FOLDER + File.separator + filename + "_ANALYSIS" + TXT);

            Processor processor = new Processor(originFile, destinationFile, Subtitle.ENCODING);
            processor.execute();
        }

    }

}
