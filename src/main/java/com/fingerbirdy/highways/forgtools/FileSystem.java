package com.fingerbirdy.highways.forgtools;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class FileSystem {

    public static final String DIR = ForgTools.mc.gameDir.getAbsolutePath() + "\\ForgTools";
    public static final String[] DIRS = new String[] { "", "\\logs", "\\config" };
    public static final HashMap<String, String> FILES = new HashMap<>();

    public static void init() {

        FILES.put("CONFIG", DIR + "\\config\\config.txt");
        FILES.put("GUI_SETTINGS", DIR + "\\config\\gui.json");

        for (String dir : DIRS) {
            try { Files.createDirectories(Paths.get(dir)); } catch (IOException e) { e.printStackTrace(); }
        }
        for (String dir : FILES.values()) {
            try { Files.createFile(Paths.get(dir)); } catch (IOException e) { e.printStackTrace(); }
        }

    }

    public static String readAllByPath(String path) {

        try {

            StringBuilder return_value = new StringBuilder();
            for (String line : Files.readAllLines(Paths.get(path))) {
                return_value.append(line);
            }
            return return_value.toString();

        } catch (Exception e) {

            return null;

        }

    }

    public static String readAll(String file_id) {

        if (FILES.containsKey(file_id)) {
            return readAllByPath(FILES.get(file_id));
        } else {
            return null;
        }

    }

    public static List<String> readLinesByPath(String path) {

        try {

            return Files.readAllLines(Paths.get(path));

        } catch (Exception e) {

            return null;

        }

    }

    public static List<String> readLines(String file_id) {

        if (FILES.containsKey(file_id)) {
            return readLinesByPath(FILES.get(file_id));
        } else {
            return null;
        }

    }

    public static boolean saveByPath(String path, String contents) {

        PrintWriter file_out = null;
        boolean success = false;

        try {

            file_out = new PrintWriter(path);
            file_out.write(contents);
            success = true;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (file_out != null) {

                file_out.close();

            }

        }

        return success;

    }

    public static boolean save(String file_id, String contents) {

        if (FILES.containsKey(file_id)) {
            return saveByPath(FILES.get(file_id), contents);
        } else {
            return false;
        }

    }

}
