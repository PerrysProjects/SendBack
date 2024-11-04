package net.sendback.util.files;

import net.sendback.util.logging.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {
    private final String filePath;

    public FileReaderUtil(String filePath) {
        this.filePath = filePath;
    }

    public List<String> readAllLines() {
        List<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch(IOException e) {
            Logger.log(e);
        }
        return lines;
    }

    public String readFirstLine() {
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine();
        } catch(IOException e) {
            Logger.log(e);
        }
        return null;
    }
}
