
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    final static Logger loggerError = Logger.getLogger(Main.class);
    final static Logger loggerInfo = Logger.getRootLogger();

    public static final char Disk = 'k';
    public static final String RESOURSES_FILE = Disk + ":\\INNOPOLIS CDPO  2018\\Git\\BIG LAB FILES\\AllSources.txt";
    public static final String RESULT_DIR = Disk + ":\\INNOPOLIS CDPO  2018\\Git\\BIG LAB FILES\\RESULTS\\";
    public static final String TEST_TEXT_FILE = Disk + ":\\INNOPOLIS CDPO  2018\\Git\\BIG LAB FILES\\mysources\\02 - Vasiliy Orehov - Zona Porazheniya.txt";
    public static final String WORDS_FILE = Disk + ":\\INNOPOLIS CDPO  2018\\Git\\BIG LAB FILES\\mywords.txt";
    public static final ExecutorService es = Executors.newFixedThreadPool(8);

    public static void main(String[] args) throws IOException {

        FileTest ft = new FileTest();

        Sources textSources = new Sources(RESOURSES_FILE);
        Sources wordsSources = new Sources(WORDS_FILE);

        if (!ft.checkFile(WORDS_FILE) ) {
            loggerError.error("There is no file with words to find");
            return;
        }
        if (!(ft.checkFile(RESOURSES_FILE))){
            loggerError.error("There is no file with resources to find");
            return;
        }

        Instant begin = Instant.now();

        loggerInfo.info("Program start time " + begin);

        textSources.inSourcesWordsSearch(textSources.getSources(), wordsSources.getSources(), RESULT_DIR);

        Instant end = Instant.now();
        Duration worktime = Duration.between(begin, end);
        Long minutes = worktime.toMinutes();
        Long seconds = worktime.getSeconds() - worktime.toMinutes()*60L;
        loggerInfo.info(" Work time: " +minutes+" min "+seconds+" sec");

    }
}

