package lab.eduardo.edtxtanalyser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TxTAnalyser {

    public static final String ORIGIN = "files";
    public static final String DESTINY = "output";

    public static final Charset ENCODING = StandardCharsets.ISO_8859_1;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean chosenOp = false;
        do {
            System.out.println("Analyse:");
            System.out.println("1- all");
            System.out.println("2- specific");
            try {
                int op = sc.nextInt();
                sc.nextLine();
                if (op == 1) {
                    analyseAll(TxTAnalyser.ORIGIN);
                    chosenOp = true;
                } else if (op == 2) {
                    System.out.println("File name: ");
                    String filename = sc.nextLine();
                    analyse(filename);
                    chosenOp = true;
                } else {
                    System.out.println("WARN: unknown option. Try again.");
                    sc.nextLine();
                }
            } catch (InputMismatchException e1) {
                System.out.println("WARN: must be an avaiable option. Try again.");
                sc.nextLine();
            } catch (NoSuchFileException e2) {
                System.out.println("WARN: the file dosen't exist. Try again.");
            }
        } while (!chosenOp);

        sc.close();
    }

    private static void analyse(final String filename) throws IOException, NoSuchFileException {
        String[] parts = filename.split("\\.");
        StringBuilder sb = new StringBuilder(TxTAnalyser.DESTINY).append(File.separator);
        for (int i = 0; i < parts.length - 1; i++) {
            sb.append(parts[i]);
        }
        sb.append("_ANALYSED.TXT");
        String originName = new StringBuilder(TxTAnalyser.ORIGIN).append(File.separator).append(filename).toString();
        String destinyName = sb.toString();
        Processor processor = new Processor(originName, destinyName, TxTAnalyser.ENCODING);
        processor.execute();
    }

    private static void analyseAll(final String dir) throws IOException {
        Set<String> files = getAllDirectoryFiles(dir);

//        files.stream().forEach(filename -> System.out.println(filename));
        files.stream().forEach(filename -> {
            try {
                analyse(filename);
            } catch (IOException e) {
                throw new Error(e);
            }
        });
    }

    private static Set<String> getAllDirectoryFiles(final String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream.filter(file -> !Files.isDirectory(file)).map(Path::getFileName).map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

}