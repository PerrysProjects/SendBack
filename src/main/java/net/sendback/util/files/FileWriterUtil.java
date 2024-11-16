package net.sendback.util.files;

import net.sendback.util.logging.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterUtil {
    private final String filePath;

    public FileWriterUtil(String filePath) {
        this.filePath = filePath;

        File file = new File(filePath);
        try {
            File parentDir = file.getParentFile();
            if(parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            file.createNewFile();
        } catch(IOException e) {
            Logger.log(e);

        }
    }

    public void writeLine(String line) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(line);
            writer.newLine();
        } catch(IOException e) {
            Logger.log(e);
        }
    }

    public void writeLines(List<String> lines) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch(IOException e) {
            Logger.log(e);
        }
    }

    public void appendLine(String line) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(line);
            writer.newLine();
        } catch(IOException e) {
            Logger.log(e);
        }
    }

    public void appendLines(List<String> lines) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for(String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch(IOException e) {
            Logger.log(e);
        }
    }

    public void clearFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Writing nothing to the file effectively clears its content
        } catch(IOException e) {
            Logger.log(e);
        }
    }
}
