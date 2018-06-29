import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

public class Sources {
    private HashSet<String> Sources;

    public Sources(String sources) throws IOException {
        Sources = this.fillSources(sources);
    }

    public HashSet<String> getSources() {
        return Sources;
    }

    public HashSet<String> fillSources(String FileName) throws IOException {

        HashSet<String> Result = new HashSet<>();

        List<String> lines = Files.readAllLines(Paths.get(FileName), StandardCharsets.UTF_8);

        for (String line : lines) {
            Result.add(line);
        }

        for (String s : Result
                ) {
            Main.loggerInfo.info(s);
        }
        if (Result.size() == 0) Main.loggerError.error("Resource " + FileName + " has no lines");

        return Result;
    }

    public String SourceType(String source) {
        Predicate<String> FILE = (src) -> src.contains(".txt") && src.contains(":\\");
        Predicate<String> HTTP = (src) -> src.contains("http://");
        Predicate<String> HTTPS = (src) -> src.contains("https://");
        Predicate<String> FTP = (src) -> src.contains("ftp://");
        Predicate<String> DIR = (src) -> src.contains(":\\");

        if (FILE.test(source)) return "FILE";
        if (HTTP.test(source)) return "HTTP";
        if (HTTPS.test(source)) return "HTTPS";
        if (FTP.test(source)) return "FTP";
        if (DIR.test(source)) return "DIR";

        return "unknown";
    }

    public void inSourcesWordsSearch(HashSet<String> Sources, HashSet<String> words, String resDir) throws IOException {

        if (Sources == null || words == null || Sources.size() == 0 || words.size() == 0) return;

        for (String source : Sources
                ) {
            FileTest ft = new FileTest();
            Path path = Paths.get(source.replaceAll("[\\/:*?\"<>|]", "."));

            switch (SourceType(source)) {

                case "HTTP":

                    String resFile = resDir + "Search Result for(" + path + ").txt";
                    ft.checkFileOrCreate(resFile);
                    OneURLSearch s1 = new OneURLSearch(source, words, resFile);
                    Main.es.execute(s1);
                    break;

                case "HTTPS":
                    resFile = resDir + "Search Result for(" + path + ").txt";
                    ft.checkFileOrCreate(resFile);
                    OneURLSearch s2 = new OneURLSearch(source, words, resFile);
                    Main.es.execute(s2);
                    break;

                case "FTP":
                    resFile = resDir + "Search Result for(" + path + ").txt";
                    ft.checkFileOrCreate(resFile);
                    OneFTPSearch s3 = new OneFTPSearch(source, words, resFile);
                    Main.es.execute(s3);
                    break;

                case "DIR":
                    File[] files = ft.getFilteredFilesList(source);

                    for (File f : files
                            ) {
                        resFile = resDir + "Search Result for(" + f.getName() + ").txt";
                        if (ft.checkFileOrCreate(resFile)) {
                            OneFileSearch s4 = new OneFileSearch(f.getPath(), words, resFile);
                            Main.es.execute(s4);
                        }
                    }
                    break;

                case "FILE":
                    File f = new File(source);

                    resFile = resDir + "Search Result for(" + f.getName() + ").txt";

                    if (f.exists() && ft.checkFileOrCreate(resFile)) {
                        OneFileSearch s5 = new OneFileSearch(source, words, resFile);
                        Main.es.execute(s5);
                    }
                    break;
            }
        }

        Main.es.shutdown();
        while (!Main.es.isTerminated()) {
        }
        Main.loggerInfo.info("Finished all threads");

    }

}
