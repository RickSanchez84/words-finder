//Пример использования фильтра FileFilter

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTest
{
    public File[] getFilteredFilesList(String DirPath)
    {
        // Определение директории
        File dir = new File(DirPath);
        // Чтение полного списка файлов каталога
        File[] lst1 = dir.listFiles();
        // Чтение списка файлов каталога с расширениями "png" и "jpg"
        File[] lst2 = dir.listFiles(new Filter("txt"));
       // Main.loggerInfo.info("lst1.length = " + lst1.length + ", lst2.length = " + lst2.length);
        return lst2;
    }

    public boolean checkFileOrCreate(String file) throws IOException{
        Path path = Paths.get(file);

       /* if (Files.exists(path)) {
            Main.loggerInfo.info(path + " Already exists");

        }*/
       try {
           Files.deleteIfExists(path);
       }
       catch (IOException e){
           Main.loggerError.error(e);
           return false;
       }

        if (Files.notExists(path)) {
            File f = new File(file);
            f.createNewFile();

            Main.loggerInfo.info(path + "Created");
            return true;
        }
        return false;
    }

    public boolean checkFile(String file) {
        Path path = Paths.get(file);
        if (Files.exists(path)) {
            Main.loggerInfo.info(file + " exists");

            return true;
        } else {
            Main.loggerError.warn(file + " not exists");
            return false;
        }
    }
}

