import sun.net.www.protocol.ftp.FtpURLConnection;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;


public class OneFTPSearch implements Runnable{
    private final String source;
    private final HashSet<String> words;
    private final String resFile;

    public OneFTPSearch(String source, HashSet<String> words, String resFile) {

        this.source = source;
        this.words = words;
        this.resFile = resFile;
    }

    @Override
    public void run() {
        SentenceAnalyser sa = new SentenceAnalyser();

        URL url = null;
        try {
            url = new URL(source);
        }
        catch (MalformedURLException e) {
            //e.printStackTrace();
            Main.loggerError.error(e);
            Thread.currentThread().interrupt();
        }
        URLConnection con = null;
        try {
            con = url.openConnection();
        }
        catch (IOException e) {
            //e.printStackTrace();
            Main.loggerError.error(e);
            Thread.currentThread().interrupt();
        }

        try (
                Reader br =  new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8);
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
