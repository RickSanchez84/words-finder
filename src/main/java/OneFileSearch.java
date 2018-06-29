import java.io.*;
import java.util.HashSet;


public class OneFileSearch implements Runnable {

    private final String source;
    private final HashSet<String> words;
    private final String resFile;

    public OneFileSearch(String source, HashSet<String> words, String resFile) {

        this.source = source;
        this.words = words;
        this.resFile = resFile;
    }


    @Override
    public void run() {
        SentenceAnalyser sa = new SentenceAnalyser();
        try (Reader br = new InputStreamReader(new FileInputStream(source), "Cp1251")) {
            try (Writer bw = new OutputStreamWriter(new FileOutputStream(resFile), "Cp1251")) {

                sa.sentenceWordfinder(br, bw, source, words);

            }
        }
        catch (IOException e) {
            //e.printStackTrace();
            Main.loggerError.error(e);
            Thread.currentThread().interrupt();
        }
    }


}
