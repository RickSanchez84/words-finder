import java.io.*;
import java.util.HashSet;

public class SentenceAnalyser {

    public void sentenceWordfinder(Reader br, Writer bw, String source, HashSet<String> words) throws IOException {

        long currentTimeMillis = System.currentTimeMillis();
        String separator = System.getProperty("line.separator");
        StringBuffer Sentence = new StringBuffer();
        bw.write(separator + separator + "Begin working with resource: " + source + separator);

        Long SentCount = 0L;
        Long SentWithWordCount = 0L;
        // чтение посимвольно
        char c;

        while ((c = (char) br.read()) != (char) -1) {

            if (c == '.' || c == '!' || c == '?') {
                SentCount++;
                Sentence.append(' ');

                for (String word : words) {
                    String SeparatedWord = ' ' + word + ' ';

                    if (Sentence.toString().contains(SeparatedWord)) {
                        SentWithWordCount++;
                        Sentence.append(c);
                        /*+ word + */
                        bw.write("[" + SentCount + "] " +
                                     "[" + SentWithWordCount + "]" +
                                     "[" + word + "]" +
                                     "" + Sentence.toString().replace(SeparatedWord, " [" + word + "] ") + separator);
                        //Main.logger.info("[" + SentCount + "] [" + SentWithWordCount + "] [" + word + "] " + Sentence);
                    }
                }
                Sentence .delete(0,Sentence.length()-1);
                c = ' ';
            }
            Sentence.append(c);
        }

        long currentTimeMillis2 = System.currentTimeMillis();
        double time_seconds = (currentTimeMillis2 - currentTimeMillis) / 1000.0;
        Main.loggerInfo.info("File "+source+" scan take " + time_seconds + " seconds" + separator);

    }
}
