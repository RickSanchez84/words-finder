import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

public class OneURLSearch implements Runnable {

    private final String source;
    private final HashSet<String> words;
    private final String resFile;

    public OneURLSearch(String source, HashSet<String> words, String resFile) {

        this.source = source;
        this.words = words;
        this.resFile = resFile;
    }

    @Override
    public void run() {
        SentenceAnalyser sa = new SentenceAnalyser();

        try (
                InputStream in = new URL(source).openStream();
                Reader br = new InputStreamReader(in, StandardCharsets.UTF_8);
                Writer bw = new OutputStreamWriter(new FileOutputStream(resFile), StandardCharsets.UTF_8)
        ) {
            sa.sentenceWordfinder(br, bw, source, words);

        }

        catch (MalformedURLException e) {
            Main.loggerError.error(e);
            Thread.currentThread().interrupt();
        }

        catch (IOException e) {
            Main.loggerError.error(e);
            Thread.currentThread().interrupt();
        }
    }
}
