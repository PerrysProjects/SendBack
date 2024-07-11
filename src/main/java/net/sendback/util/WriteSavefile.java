package net.sendback.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteSavefile {

    public static void saveGame(String[] saveData) {


        try {
            BufferedWriter savemaker = new BufferedWriter(new FileWriter("save.properties"));

            for (String data : saveData) {
                savemaker.write("\n" + data);
            }


            savemaker.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}